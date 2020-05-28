package ru.itis.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.BoardMemberService;
import ru.itis.taskmanager.service.BoardService;
import ru.itis.taskmanager.service.StackService;

import java.util.Optional;

@Controller
public class BoardController {
    private BoardMemberService boardMemberService;
    private StackService stackService;
    private BoardService boardService;

    @Autowired
    public BoardController(BoardMemberService boardMemberService, BoardService boardService, StackService stackService) {
        this.boardMemberService = boardMemberService;
        this.boardService = boardService;
        this.stackService = stackService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/boards/{board_id}")
    public String board(@PathVariable("board_id") Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Optional<Board> boardOptional = boardService.getBoard(boardId);
        if (!boardOptional.isPresent()) {
            return "redirect:/error";
        }

        if (!boardMemberService.isBoardMemberExist(boardId, userDetails.getUser().getId())) {
            return "redirect:/error";
        }

        model.addAttribute("stacks", stackService.getStacks(boardId));
        model.addAttribute("members", boardMemberService.getBoardMembers(boardId));
        model.addAttribute("board", boardOptional.get());
        model.addAttribute("userId", userDetails.getUser().getId());
        return "board";
    }
}