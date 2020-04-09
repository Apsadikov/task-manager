package ru.itis.taskmanager.service;

import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.UserDto;

@Service
public interface UserService {
    UserDto signUp(UserDto userDto);
}
