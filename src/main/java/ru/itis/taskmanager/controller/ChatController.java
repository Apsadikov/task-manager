package ru.itis.taskmanager.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/api/messages")
    public void addMessage(Authentication authentication, @RequestParam("board_id") Long boardId, @RequestParam("text") String text) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        messageService.addMessage(MessageDto.builder()
                .userDto(UserDto.builder().id(user.getId()).build())
                .boardDto(BoardDto.builder().id(boardId).build())
                .text(text)
                .build());
    }

    @SneakyThrows
    @RequestMapping(value = "/api/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(Authentication authentication, @RequestParam("board_id") Long boardId) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        return ResponseEntity.ok(messageService.getMessages(user.getId(), boardId));
    }
}
