package ru.itis.taskmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJdbcTemplateImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;
    private BCryptPasswordEncoder passwordEncoder;
    private RowMapper<User> userRowMapper;

    @Autowired
    public UserRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder, RowMapper<User> userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
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
    public void save(User user) {
        if (!findUserByEmail(user.getEmail()).isPresent()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO user(email, password_hash, name) VALUE (?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getEmail());
                statement.setString(2, passwordEncoder.encode(user.getPassword()));
                statement.setString(3, user.getName());
                return statement;
            }, keyHolder);

            user.setId(keyHolder.getKey().longValue());
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, userRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
