package ru.itis.taskmanager.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.taskmanager.chat.Chat;
import ru.itis.taskmanager.chat.Client;
import ru.itis.taskmanager.dto.MessageDto;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.entity.Message;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.repository.MessageRepository;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;
    private Chat chat;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, Chat chat) {
        this.messageRepository = messageRepository;
        this.chat = chat;
    }

    @Override
    @SneakyThrows
    public List<MessageDto> getMessages(Long userId, Long boardId) {
        Client client = Client.builder().userId(userId).boardId(boardId).build();
        if (!chat.isPresent(client)) {
            chat.join(client);
            List<MessageDto> messages = MessageDto.from(messageRepository.getMessages(userId, boardId, 0L));
            if (!messages.isEmpty()) {
                client.setLastReadMessageId(messages.get(0).getId());
            }
            return messages;
        }

        return chat.observe(() -> {
            List<MessageDto> newMessages =
                    MessageDto.from(messageRepository.getMessages(userId, boardId, 0L));
            if (!newMessages.isEmpty()) {
                chat.getClient(client).setLastReadMessageId(newMessages.get(0).getId());
            }
            return newMessages;
        }, chat.getClient(client));
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
        if (messageDto.getId() != null) {
            Client client = Client.builder()
                    .userId(message.getUser().getId())
                    .boardId(message.getBoard().getId())
                    .build();
            chat.onChange(client);
            chat.getClient(client).setLastReadMessageId(messageDto.getId());
        }
    }
}
