package ru.itis.taskmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

    @GetMapping("/sign-in")
    public String signIn(Authentication authentication, Model model) {
        if (authentication != null) {
            return "redirect:/";
        }
        model.addAttribute("css", "sign-in");
        model.addAttribute("link", "/sign-up");
        model.addAttribute("linkTitle", "Sign Up");
        return "sign-in";
    }
}
