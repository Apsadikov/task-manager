package ru.itis.taskmanager.service;

import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.StackDto;

import java.util.List;

@Service
public interface StackService {
    void addStack(StackDto stackDto, Long userId, Long boardId);

    List<StackDto> getStacks(Long boardId);
}
