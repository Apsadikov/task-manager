package ru.itis.taskmanager.service;

import ru.itis.taskmanager.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> getAllBoards(Long userId);

    void save(BoardDto boardDto);
}
