package ru.itis.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.taskmanager.entity.UserBoard;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserBoardDto {
    private UserDto user;
    private BoardDto board;

    public static UserBoardDto from(UserBoard userBoard) {
        return UserBoardDto.builder()
                .board(BoardDto.from(userBoard.getBoard()))
                .user(UserDto.from(userBoard.getUser()))
                .build();
    }

    public static List<UserBoardDto> from(List<UserBoard> userBoards) {
        return userBoards.stream().map(UserBoardDto::from).collect(Collectors.toList());
    }
}
