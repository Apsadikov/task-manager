package ru.itis.taskmanager.service;

import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.util.exception.ConfirmationTokenInvalid;
import ru.itis.taskmanager.util.exception.EmailIsAlreadyUse;

public interface AccountService {

    void signUp(UserDto userDto) throws EmailIsAlreadyUse;

    void confirmEmail(String token) throws ConfirmationTokenInvalid;
}
