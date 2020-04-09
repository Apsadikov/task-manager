package ru.itis.taskmanager.mapper;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.taskmanager.entity.Board;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BoardRowMapper implements RowMapper<Board> {
    @Override
    public Board mapRow(ResultSet row, int rowNum) throws SQLException {
        return Board.builder().title(row.getString("title"))
                .id(row.getLong("id"))
                .build();
    }
}
