package ru.itis.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private Long id;

    private String email;

    private String passwordHash;

    private String password;

    private String name;

    private List<Board> boards;
}
