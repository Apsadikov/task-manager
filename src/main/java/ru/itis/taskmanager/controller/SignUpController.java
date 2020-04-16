package ru.itis.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.service.UserService;

@Controller
public class SignUpController {
    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    @PreAuthorize("permitAll()")
    public String signUp(Authentication authentication, Model model) {
        if (authentication != null) {
            return "redirect:/";
        }
        model.addAttribute("css", "sign-up");
        model.addAttribute("link", "/sign-in");
        model.addAttribute("linkTitle", "Sign In");
        return "sign-up";
    }

    @PostMapping("/sign-up")
    @PreAuthorize("permitAll()")
    public String signUp(UserDto userDto) {
        userService.signUp(userDto);
        if (userDto.getId() != null) {
            return "redirect:/";
        }
        return "sign-up";
    }
}
