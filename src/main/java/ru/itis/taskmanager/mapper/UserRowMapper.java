package ru.itis.taskmanager.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.taskmanager.entity.Role;
import ru.itis.taskmanager.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet row, int rowNum) throws SQLException {
        return User.builder()
                .id(row.getLong("id"))
                .passwordHash(row.getString("password_hash"))
                .email(row.getString("email"))
                .confirmationToken(row.getString("confirmation_token"))
                .role(row.getString("role") == null ? null : Role.valueOf(row.getString("role")))
                .isConfirmed(row.getBoolean("is_confirmed"))
                .name(row.getString("name"))
                .build();
    }
}
