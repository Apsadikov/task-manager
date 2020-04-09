package ru.itis.taskmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.Board;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryJdbcTemplateImpl implements BoardRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Board> boardRowMapper;

    @Autowired
    public BoardRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate, RowMapper<Board> boardRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.boardRowMapper = boardRowMapper;
    }


    @Override
    public List<Board> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Board id) {

    }

    @Override
    public void save(Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO board (title) VALUE (?)",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, board.getTitle());
            return statement;
        }, keyHolder);
        board.setId(keyHolder.getKey().longValue());

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO user_board (user_id, board_id) VALUE (?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, board.getUser().getId());
            statement.setLong(2, board.getId());
            return statement;
        }, keyHolder);
    }

    @Override
    public List<Board> findBoardsByUserId(Long userId) {
        String sql = "SELECT board.id, board.title FROM board INNER JOIN user_board ON board.id = user_board.board_id " +
                "AND user_board.user_id = ? ORDER by board.id DESC";
        return jdbcTemplate.query(sql, new Object[]{userId}, boardRowMapper);
    }
}
