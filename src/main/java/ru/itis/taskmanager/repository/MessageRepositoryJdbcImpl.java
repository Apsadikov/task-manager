package ru.itis.taskmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.taskmanager.entity.Message;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryJdbcImpl implements MessageRepository {
    private RowMapper<Message> messageRowMapper;

    private UserBoardRepository userBoardRepository;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryJdbcImpl(RowMapper<Message> messageRowMapper, UserBoardRepository userBoardRepository, JdbcTemplate jdbcTemplate) {
        this.messageRowMapper = messageRowMapper;
        this.userBoardRepository = userBoardRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Message id) {

    }

    @Override
    public Message save(Message entity) {
        if (userBoardRepository.isUserHasBoard(entity.getUser().getId(), entity.getBoard().getId())) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO message(user_id, board_id, text) VALUE (?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, entity.getUser().getId());
                statement.setLong(2, entity.getBoard().getId());
                statement.setString(3, entity.getText());
                return statement;
            }, keyHolder);

            entity.setId(keyHolder.getKey().longValue());
            return entity;
        }
        return entity;
    }

    @Override
    public List<Message> getMessages(Long userId, Long boardId, Long messageStartId) {
        if (userBoardRepository.isUserHasBoard(userId, boardId)) {
            String sql = "SELECT message.id, board_id, user_id, text, user.name " +
                    "FROM message INNER JOIN user ON user_id = user.id WHERE board_id = ? AND message.id > ? ORDER BY message.id DESC";
                return jdbcTemplate.query(sql, new Object[]{boardId, messageStartId}, messageRowMapper);
        }
        return new ArrayList<>();
    }
}
