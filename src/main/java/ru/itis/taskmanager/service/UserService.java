package ru.itis.taskmanager.service;

import ru.itis.taskmanager.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findUsers(String name);

    void signUp(UserDto userDto);
}
