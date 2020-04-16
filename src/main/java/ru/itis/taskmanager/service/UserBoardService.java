package ru.itis.taskmanager.service;

import ru.itis.taskmanager.dto.BoardDto;
import ru.itis.taskmanager.dto.UserDto;

public interface UserBoardService {
    void addUserToBoard(Long memberId, Long userId, Long boardId);
}
