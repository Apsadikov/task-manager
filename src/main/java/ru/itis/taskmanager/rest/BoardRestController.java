package ru.itis.taskmanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.taskmanager.dto.BoardDto;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.UserBoardService;

@RestController
public class BoardRestController {
    private UserBoardService userBoardService;

    @Autowired
    public BoardRestController(UserBoardService userBoardService) {
        this.userBoardService = userBoardService;
    }

    @PostMapping("/api/boards/{id}/users")
    public ResponseEntity addUserToBoard(Authentication authentication,
                                         @PathVariable("id") Long boardId, @RequestParam("user_id") Long userId) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        userBoardService.addUserToBoard(user.getId(), userId, boardId);
        return ResponseEntity.ok().build();
    }
}
