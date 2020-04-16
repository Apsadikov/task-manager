package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findUsers(String name) {
        return UserDto.from(userRepository.findUsersByName(name));
    }

    @Override
    public void signUp(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .build();
        userRepository.save(user);
        userDto.setId(user.getId());
    }
}