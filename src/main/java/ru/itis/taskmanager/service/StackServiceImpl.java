package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.StackDto;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.entity.Stack;
import ru.itis.taskmanager.repository.StackRepository;

import java.util.List;

@Service
public class StackServiceImpl implements StackService {
    private StackRepository stackRepository;

    @Autowired
    public StackServiceImpl(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    @Override
    public void addStack(StackDto stackDto, Long userId, Long boardId) {
        Stack stack = stackRepository.save(Stack.builder()
                .title(stackDto.getTitle())
                .board(Board.builder()
                        .id(boardId)
                        .build())
                .build());
        stackDto.setId(stack.getId());
    }

    @Override
    public List<StackDto> getStacks(Long boardId) {
        return StackDto.from(stackRepository.getStacksByBoardId(boardId));
    }
}
