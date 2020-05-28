package ru.itis.taskmanager.rest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.dto.BoardDto;
import ru.itis.taskmanager.dto.MessageDto;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.BoardMemberService;
import ru.itis.taskmanager.service.BoardService;
import ru.itis.taskmanager.service.MessageService;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("isAuthenticated()")
public class ChatController {
    private MessageService messageService;
    private BoardService boardService;
    private BoardMemberService boardMemberService;

    @Autowired
    public ChatController(MessageService messageService, BoardService boardService, BoardMemberService boardMemberService) {
        this.messageService = messageService;
        this.boardService = boardService;
        this.boardMemberService = boardMemberService;
    }

    @PostMapping("/api/messages")
    public ResponseEntity addMessage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("board_id") Long boardId, @RequestParam("text") String text) {
        Optional<Board> boardOptional = boardService.getBoard(boardId);

        if (!boardOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!boardMemberService.isBoardMemberExist(boardId, userDetails.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        messageService.addMessage(MessageDto.builder()
                .userDto(UserDto.builder().id(userDetails.getUser().getId()).build())
                .boardDto(BoardDto.builder().id(boardId).build())
                .text(text)
                .build());

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @RequestMapping(value = "/api/messages", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("board_id") Long boardId) {
        Optional<Board> boardOptional = boardService.getBoard(boardId);

        if (!boardOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!boardMemberService.isBoardMemberExist(boardId, userDetails.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(messageService.getMessages(userDetails.getUser().getId(), boardId));
    }
}
