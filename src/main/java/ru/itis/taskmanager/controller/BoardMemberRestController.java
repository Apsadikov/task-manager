package ru.itis.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.aspect.BoardAccess;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.BoardMemberService;
import ru.itis.taskmanager.util.exception.NotFoundException;

@RestController
public class BoardMemberRestController {

    private BoardMemberService boardMemberService;

    @Autowired
    public BoardMemberRestController(BoardMemberService boardMemberService) {
        this.boardMemberService = boardMemberService;
    }

    @PreAuthorize("isAuthenticated()")
    @BoardAccess
    @RequestMapping(value = "/api/boards/{board_id}/members", method = RequestMethod.POST)
    public ResponseEntity addMember(@PathVariable("board_id") Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestParam("invited_user_id") Long invitedUserId) {
        try {
            boardMemberService.addBoardMember(invitedUserId, userDetails.getUser().getId(), boardId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @BoardAccess
    @RequestMapping(value = "/api/boards/{board_id}/members", method = RequestMethod.DELETE)
    public ResponseEntity deleteMember(@PathVariable("board_id") Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestParam("deleted_user_id") Long deletedUserId) {
        try {
            boardMemberService.deleteBoardMember(deletedUserId, userDetails.getUser().getId(), boardId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }
}
