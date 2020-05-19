package ru.itis.taskmanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.taskmanager.dto.AddMemberDto;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.service.AccountService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserRestController {
    private AccountService accountService;

    @Autowired
    public UserRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseBody
    @RequestMapping(value = "/api/users")
    public List<UserDto> findMembers(@Valid AddMemberDto addMemberDto, BindingResult bindingResult) {
//        return accountService.findUsers(addMemberDto.getName());
        return new ArrayList<>();
    }
}
