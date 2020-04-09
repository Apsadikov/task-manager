package ru.itis.taskmanager.repository;

import ru.itis.taskmanager.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends CrudRepository<Board, Long> {
    List<Board> findBoardsByUserId(Long userId);
}
