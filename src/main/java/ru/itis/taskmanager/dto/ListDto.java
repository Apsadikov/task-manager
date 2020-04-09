package ru.itis.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ListDto {
    private Long id;
    private String title;
    private Long boardId;
    private BoardDto boardDto;
}
