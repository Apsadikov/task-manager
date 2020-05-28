package ru.itis.taskmanager.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.entity.Message;
import ru.itis.taskmanager.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MessageRowMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Message.builder()
                .id(rs.getLong("id"))
                .board(Board.builder()
                        .id(rs.getLong("board_id"))
                        .build()
                )
                .user(User.builder()
                        .id(rs.getLong("user_id"))
                        .name(rs.getString("name"))
                        .build()
                )
                .message(rs.getString("message"))
                .build();
    }
}
