package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.MailDto;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.entity.Role;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.repository.UserRepository;
import ru.itis.taskmanager.util.ConfirmationTokenGenerator;
import ru.itis.taskmanager.util.exception.ConfirmationTokenInvalid;
import ru.itis.taskmanager.util.exception.EmailIsAlreadyUse;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private EmailService emailService;

    @Autowired
    public AccountServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                              @Qualifier(value = "confirmationEmailService") EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void signUp(UserDto userDto) throws EmailIsAlreadyUse {
        if (!userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            User user = userRepository.save(User.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .passwordHash(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .confirmationToken(ConfirmationTokenGenerator.generate())
                    .name(userDto.getName())
                    .role(Role.USER)
                    .build());
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("token", user.getConfirmationToken());
            emailService.send(MailDto.builder()
                    .subject("Confirm email")
                    .to(user.getEmail())
                    .map(parameters)
                    .template("confirmation-mail.ftlh")
                    .build());
        } else {
            throw new EmailIsAlreadyUse();
        }
    }

    @Override
    public void confirmEmail(String token) throws ConfirmationTokenInvalid {
        Optional<User> optionalUser = userRepository.findUserByConfirmationToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getConfirmationToken().equals(token)) {
                user.setConfirmed(true);
                userRepository.confirmedUserEmail(user.getId());
            } else {
                throw new ConfirmationTokenInvalid();
            }
        }
    }
}