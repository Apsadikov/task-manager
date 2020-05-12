package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.repository.UserRepository;
import ru.itis.taskmanager.util.exception.EmailIsAlreadyUse;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findUsers(String name) {
        return UserDto.from(userRepository.findUsersByName(name));
    }

    @Override
    public void signUp(UserDto userDto) throws EmailIsAlreadyUse {
        if (!userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            User user = User.builder()
                    .email(userDto.getEmail())
                    .passwordHash(passwordEncoder.encode(userDto.getPassword()))
                    .name(userDto.getName())
                    .build();
            userRepository.save(user);
            userDto.setId(user.getId());
        } else {
            throw new EmailIsAlreadyUse();
        }
    }
}