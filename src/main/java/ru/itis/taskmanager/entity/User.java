package ru.itis.taskmanager.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements Serializable {
    private Long id;

    private String email;

    private String passwordHash;

    private String name;

    private String confirmationToken;

    private boolean isConfirmed;

    private Role role;
}
