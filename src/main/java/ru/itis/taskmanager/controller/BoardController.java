package ru.itis.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.taskmanager.dto.ListDto;
import ru.itis.taskmanager.service.BoardService;

@Controller
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/boards/{id}")
    public String board(@PathVariable("id") Long id, Model model) {
        model.addAttribute("boardId", id);
        return "board";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/lists")
    public String addList(ListDto listDto) {
        boardService.save(listDto);
        return "redirect:/boards/" + listDto.getBoardId();
    }
}
