package ru.itis.taskmanager.service;

import ru.itis.taskmanager.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessages(Long userId, Long boardId);

    void addMessage(MessageDto messageDto);
}
