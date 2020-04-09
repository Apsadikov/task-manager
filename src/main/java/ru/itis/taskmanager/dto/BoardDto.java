package ru.itis.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardDto {
    private Long id;
    private String title;
    private User user;

    public static BoardDto from(Board board) {
        return BoardDto.builder().id(board.getId()).title(board.getTitle()).build();
    }

    public static List<BoardDto> from(List<Board> boards) {
        return boards.stream().map(BoardDto::from).collect(Collectors.toList());
    }
}
