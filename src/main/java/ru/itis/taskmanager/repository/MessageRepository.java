package ru.itis.taskmanager.repository;

import ru.itis.taskmanager.entity.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> getMessages(Long userId, Long boardId, Long messageStartId);
}
