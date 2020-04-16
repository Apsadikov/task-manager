package ru.itis.taskmanager.repository;

import ru.itis.taskmanager.entity.UserBoard;

public interface UserBoardRepository extends CrudRepository<UserBoard, Long> {
    boolean isUserHasBoard(Long userId, Long boardId);
}
