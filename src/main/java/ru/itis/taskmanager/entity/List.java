package ru.itis.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class List {
    private Long id;
    private String title;
    private Board board;
    private Long boardId;
}
