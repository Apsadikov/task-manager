package ru.itis.taskmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/sign-in")
    public String signIn(Authentication authentication) {
        if (authentication != null) {
                return "redirect:/";
        }
        return "sign-in";
    }
}
