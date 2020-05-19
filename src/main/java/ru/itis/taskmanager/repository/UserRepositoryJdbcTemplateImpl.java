package ru.itis.taskmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.Role;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.util.ConfirmationTokenGenerator;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJdbcTemplateImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;

    @Autowired
    public UserRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate, RowMapper<User> userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(User id) {

    }

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO user(email, password_hash, name, confirmation_token, role) " +
                                    "VALUE (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getName());
            statement.setString(4, ConfirmationTokenGenerator.generate());
            statement.setString(5, Role.USER.toString());
            return statement;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ? LIMIT 1";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByConfirmationToken(String token) {
        String sql = "SELECT * FROM user WHERE confirmation_token = ? LIMIT 1";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{token}, userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findUserByNameContaining(String name) {
        String sql = "SELECT * FROM user WHERE name LIKE ? LIMIT 1";
        try {
            return jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void confirmedUserEmail(Long id) {
        String sql = "UPDATE user SET is_confirmed = 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
