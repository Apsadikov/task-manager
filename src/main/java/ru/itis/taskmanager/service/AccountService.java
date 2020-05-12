package ru.itis.taskmanager.service;

import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.util.exception.EmailIsAlreadyUse;

import java.util.List;

public interface AccountService {
    List<UserDto> findUsers(String name);

    void signUp(UserDto userDto) throws EmailIsAlreadyUse;
}
