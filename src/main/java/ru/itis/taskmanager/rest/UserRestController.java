package ru.itis.taskmanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.service.UserService;

import java.util.List;

@Controller
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/api/users")
    public ResponseEntity<List<UserDto>> findMembers(@RequestParam("name") String name) {
        return ResponseEntity.ok(userService.findUsers(name));
    }
}
