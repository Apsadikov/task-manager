package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.BoardDto;
import ru.itis.taskmanager.dto.ListDto;
import ru.itis.taskmanager.entity.Board;
import ru.itis.taskmanager.repository.BoardRepository;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<BoardDto> getAllBoards(Long userId) {
        return BoardDto.from(boardRepository.findBoardsByUserId(userId));
    }

    @Override
    public void save(BoardDto boardDto) {
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .user(boardDto.getUser())
                .build();
        boardRepository.save(board);
        boardDto.setId(board.getId());
    }

    @Override
    public void save(ListDto listDto) {

    }
}
