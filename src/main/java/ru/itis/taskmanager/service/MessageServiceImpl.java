package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.taskmanager.dto.MessageDto;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.entity.Message;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.repository.MessageRepository;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageDto> getMessages(Long userId, Long boardId, Long messageStartId) {
        return MessageDto.from(messageRepository.getMessages(userId, boardId, messageStartId));
    }

    @Override
    public void addMessage(MessageDto messageDto) {
        Message message = Message.builder()
                .text(messageDto.getText())
                .user(User.builder().id(messageDto.getUserDto().getId()).build())
                .board(Board.builder().id(messageDto.getBoardDto().getId()).build())
                .build();
        messageRepository.save(message);
        messageDto.setId(message.getId());
    }
}
