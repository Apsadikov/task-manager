package ru.itis.taskmanager.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.chat.Chat;
import ru.itis.taskmanager.chat.Client;
import ru.itis.taskmanager.dto.BoardDto;
import ru.itis.taskmanager.dto.MessageDto;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.MessageService;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class ChatController {
    private MessageService messageService;
    private Chat chat;

    @Autowired
    public ChatController(MessageService messageService, Chat chat) {
        this.messageService = messageService;
        this.chat = chat;
    }

    @PostMapping("/api/messages")
    public void addMessage(Authentication authentication, @RequestParam("board_id") Long boardId, @RequestParam("text") String text) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        MessageDto messageDto = MessageDto.builder()
                .userDto(UserDto.builder().id(user.getId()).build())
                .boardDto(BoardDto.builder().id(boardId).build())
                .text(text)
                .build();
        messageService.addMessage(messageDto);
        if (messageDto.getId() != null) {
            Client client = Client.builder().userId(user.getId()).boardId(boardId).build();
            chat.onChange(client);
            chat.getClient(client).setLastReadMessageId(messageDto.getId());
        }
    }

    @SneakyThrows
    @RequestMapping(value = "/api/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(Authentication authentication, @RequestParam("board_id") Long boardId) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Client client = Client.builder().userId(user.getId()).boardId(boardId).build();
        if (!chat.isPresent(client)) {
            chat.join(client);
            List<MessageDto> messages = messageService.getMessages(user.getId(), boardId, 0L);
            if (!messages.isEmpty()) {
                client.setLastReadMessageId(messages.get(0).getId());
            }
            return ResponseEntity.ok(messages);
        }

        List<MessageDto> messages = chat.observe(() -> {
            List<MessageDto> newMessages =
                    messageService.getMessages(user.getId(), boardId, chat.getClient(client).getLastReadMessageId());
            if (!newMessages.isEmpty()) {
                chat.getClient(client).setLastReadMessageId(newMessages.get(0).getId());
            }
            return newMessages;
        }, chat.getClient(client));
        return ResponseEntity.ok(messages);
    }
}
