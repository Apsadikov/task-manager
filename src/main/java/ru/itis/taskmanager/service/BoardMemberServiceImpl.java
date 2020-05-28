package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.dto.UserDto;
import ru.itis.taskmanager.entity.BoardMember;
import ru.itis.taskmanager.entity.BoardMemberKey;
import ru.itis.taskmanager.repository.BoardMemberRepository;
import ru.itis.taskmanager.repository.BoardRepository;
import ru.itis.taskmanager.util.exception.NotFoundException;

import java.util.List;

@Service
public class BoardMemberServiceImpl implements BoardMemberService {
    private BoardRepository boardRepository;
    private BoardMemberRepository boardMemberRepository;

    @Autowired
    public BoardMemberServiceImpl(BoardMemberRepository boardMemberRepository, BoardRepository boardRepository) {
        this.boardMemberRepository = boardMemberRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public void addBoardMember(Long invitedUserId, Long memberUserId, Long boardId) throws NotFoundException {
        if (boardRepository.findById(boardId).isPresent()) {
            boardMemberRepository.save(BoardMember.builder()
                    .boardMemberKey(BoardMemberKey.builder()
                            .boardId(boardId)
                            .userId(invitedUserId)
                            .build())
                    .build());
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public boolean isBoardMemberExist(Long boardId, Long userId) {
        return boardMemberRepository.isBoardMemberExist(boardId, userId).isPresent();
    }

    @Override
    public List<UserDto> getBoardMembers(Long boardId) {
        return UserDto.from(boardMemberRepository.getMembersByBoardId(boardId));
    }

    @Override
    @Transactional
    public void deleteBoardMember(Long deletedUserId, Long memberUserId, Long boardId) throws NotFoundException {
        if (boardRepository.findById(boardId).isPresent()) {
            boardMemberRepository.delete(BoardMember.builder()
                    .boardMemberKey(BoardMemberKey.builder()
                            .boardId(boardId)
                            .userId(deletedUserId)
                            .build())
                    .build());
        } else {
            throw new NotFoundException();
        }
    }
}