package ru.itis.taskmanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.taskmanager.dto.AddMemberDto;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.service.AccountService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserRestController {
    private AccountService accountService;

    @Autowired
    public UserRestController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @RequestMapping(value = "/api/users")
//    public ResponseEntity<List<UserDto>> findMembers(@RequestParam("name") String name) {
//        return ResponseEntity.ok(accountService.findUsers(name));
//    }

    @RequestMapping(value = "/api/users")
    public ResponseEntity<List<UserDto>> findMembers(@Valid AddMemberDto addMemberDto, BindingResult bindingResult) {
        return ResponseEntity.ok(accountService.findUsers(addMemberDto.getName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationError(MethodArgumentNotValidException exception) {
        HashMap<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors().forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return errors;
    }
}
