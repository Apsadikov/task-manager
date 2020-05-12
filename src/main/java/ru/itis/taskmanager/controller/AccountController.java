package ru.itis.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.taskmanager.dto.SignUpForm;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.service.AccountService;
import ru.itis.taskmanager.util.exception.EmailIsAlreadyUse;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class AccountController {
    private AccountService accountService;
    private MessageSource messageSource;

    @Autowired
    public AccountController(AccountService accountService, MessageSource messageSource) {
        this.accountService = accountService;
        this.messageSource = messageSource;
    }

    @GetMapping("/sign-in")
    @PreAuthorize("isAnonymous()")
    public String signIn(@RequestParam(required = false, defaultValue = "false") String error, Model model) {
        model.addAttribute("css", "sign-in");
        model.addAttribute("link", "/sign-up");
        model.addAttribute("linkTitle", "sign-up-title");
        model.addAttribute("isAuthError", error);
        return "sign-in";
    }

    @GetMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String signUp(Model model) {
        model.addAttribute("css", "sign-up");
        model.addAttribute("link", "/sign-in");
        model.addAttribute("linkTitle", "sign-in");
        model.addAttribute("signUpForm", new SignUpForm());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String signUp(Model model, @Valid SignUpForm signUpForm,
                         BindingResult bindingResult, Locale locale) {
        if (!bindingResult.hasErrors()) {
            try {
                accountService.signUp(UserDto.builder()
                        .email(signUpForm.getEmail())
                        .name(signUpForm.getName())
                        .password(signUpForm.getPassword())
                        .build());
                return "redirect:/sign-in";
            } catch (EmailIsAlreadyUse emailIsAlreadyUse) {
                bindingResult.rejectValue("email", "Exist",
                        messageSource.getMessage("error.email.exist", new String[]{signUpForm.getEmail()}, locale));
            }
        }
        model.addAttribute("css", "sign-up");
        model.addAttribute("link", "/sign-in");
        model.addAttribute("linkTitle", "sign-in");
        model.addAttribute("signUpForm", signUpForm);
        return "sign-up";
    }
}
