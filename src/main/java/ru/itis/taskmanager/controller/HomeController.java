package ru.itis.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.taskmanager.dto.BoardDto;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.BoardService;

@Controller
public class HomeController {
    private BoardService boardService;

    @Autowired
    public HomeController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String home(Authentication authentication, Model model) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        model.addAttribute("boards", boardService.getAllBoards(user.getId()));
        return "home";
    }

    @PostMapping("/boards")
    public String addBoard(Authentication authentication, BoardDto boardDto) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        boardDto.setUser(user);
        boardService.save(boardDto);
        return "redirect:/";
    }
}
