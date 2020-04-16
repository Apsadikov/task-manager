package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.entity.UserBoard;
import ru.itis.taskmanager.repository.UserBoardRepository;

@Service
public class UserBoardServiceImpl implements UserBoardService {
    private UserBoardRepository userBoardRepository;

    @Autowired
    public UserBoardServiceImpl(UserBoardRepository userBoardRepository) {
        this.userBoardRepository = userBoardRepository;
    }

    @Override
    public void addUserToBoard(Long memberId, Long userId, Long boardId) {
        if (!memberId.equals(userId) && userBoardRepository.isUserHasBoard(memberId, boardId)) {
            userBoardRepository.save(UserBoard.builder()
                    .board(Board.builder().id(boardId).build())
                    .user(User.builder().id(userId).build())
                    .build());
        }
    }
}
