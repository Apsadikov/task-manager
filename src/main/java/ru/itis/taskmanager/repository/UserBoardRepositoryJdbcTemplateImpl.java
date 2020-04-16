package ru.itis.taskmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.UserBoard;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserBoardRepositoryJdbcTemplateImpl implements UserBoardRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserBoardRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserBoard> findAll() {
        return null;
    }

    @Override
    public Optional<UserBoard> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(UserBoard id) {

    }

    @Override
    public void save(UserBoard userBoard) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO user_board (user_id, board_id) VALUE (?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userBoard.getUser().getId());
            statement.setLong(2, userBoard.getBoard().getId());
            return statement;
        }, keyHolder);
    }

    @Override
    public boolean isUserHasBoard(Long userId, Long boardId) {
        String sql = "SELECT COUNT(*) FROM  user_board WHERE user_id = ? AND board_id = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, boardId}, Integer.class) > 0;
    }
}
