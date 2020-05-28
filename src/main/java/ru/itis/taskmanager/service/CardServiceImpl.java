package ru.itis.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.dto.CardDto;
import ru.itis.taskmanager.dto.CommentDto;
import ru.itis.taskmanager.dto.FileDto;
import ru.itis.taskmanager.dto.FullCardDto;
import ru.itis.taskmanager.entity.Card;
import ru.itis.taskmanager.entity.Comment;
import ru.itis.taskmanager.entity.Stack;
import ru.itis.taskmanager.entity.User;
import ru.itis.taskmanager.repository.BoardMemberRepository;
import ru.itis.taskmanager.repository.CardRepository;
import ru.itis.taskmanager.repository.CommentRepository;
import ru.itis.taskmanager.repository.StackRepository;
import ru.itis.taskmanager.util.exception.AccessException;
import ru.itis.taskmanager.util.exception.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;
    private BoardMemberRepository boardMemberRepository;
    private StackRepository stackRepository;
    private CommentRepository commentRepository;
    private FileService fileService;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, BoardMemberRepository boardMemberRepository,
                           StackRepository stackRepository, FileService fileService, CommentRepository commentRepository) {
        this.cardRepository = cardRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.stackRepository = stackRepository;
        this.fileService = fileService;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void addCard(CardDto cardDto, Long userId) throws NotFoundException, AccessException {
        Optional<Stack> optionalStack = stackRepository.findById(cardDto.getStack().getId());
        if (optionalStack.isPresent()) {
            if (boardMemberRepository.isBoardMemberExist(optionalStack.get().getBoard().getId(), userId).isPresent()) {
                Card card = cardRepository.save(Card.builder()
                        .title(cardDto.getTitle())
                        .stack(Stack.builder()
                                .id(cardDto.getStack().getId())
                                .build())
                        .build());
                cardDto.setId(card.getId());
            } else {
                throw new AccessException();
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void addComment(String comment, Long cardId, Long userId) throws AccessException, NotFoundException {
        Card card = getExistCard(cardId, userId);
        commentRepository.save(Comment.builder()
                .card(card)
                .user(User.builder().id(userId).build())
                .message(comment)
                .build());
    }

    @Override
    public void moveCard(CardDto cardDto, Long userId) throws AccessException, NotFoundException {
        Optional<Stack> optionalStack = stackRepository.findById(cardDto.getStack().getId());
        if (optionalStack.isPresent()) {
            if (boardMemberRepository.isBoardMemberExist(optionalStack.get().getBoard().getId(), userId).isPresent()) {
                Optional<Card> optionalCard = cardRepository.findById(cardDto.getId());
                if (optionalCard.isPresent()) {
                    optionalCard.get().setStack((Stack.builder()
                            .id(cardDto.getStack().getId())
                            .build()));
                    cardRepository.move(optionalCard.get());
                }
            } else {
                throw new AccessException();
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Transactional
    @Override
    public void updateDescription(String description, Long cardId, Long userId) throws AccessException, NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Optional<Stack> optionalStack = stackRepository.findById(optionalCard.get().getId());
            if (optionalStack.isPresent()) {
                if (boardMemberRepository.isBoardMemberExist(optionalStack.get().getBoard().getId(), userId).isPresent()) {
                    optionalCard.get().setDescription(description);
                    cardRepository.updatedDescription(optionalCard.get());
                } else {
                    throw new AccessException();
                }
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public void updateDeadline(Date deadline, Long cardId, Long userId) throws AccessException, NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Optional<Stack> optionalStack = stackRepository.findById(optionalCard.get().getId());
            if (optionalStack.isPresent()) {
                if (boardMemberRepository.isBoardMemberExist(optionalStack.get().getBoard().getId(), userId).isPresent()) {
                    optionalCard.get().setDeadline(deadline);
                    cardRepository.updatedDeadline(optionalCard.get());
                } else {
                    throw new AccessException();
                }
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Optional<FullCardDto> getCard(Long cardId, Long userId) throws NotFoundException, AccessException {
        Card card = getExistCard(cardId, userId);
        FullCardDto fullCardDto = FullCardDto.from(card);
        List<FileDto> files = fileService.getFiles(cardId, userId);
        fullCardDto.setFiles(files);
        fullCardDto.setComments(CommentDto.from(commentRepository.findAllByCard(card)));
        return Optional.of(fullCardDto);
    }

    private Card getExistCard(Long cardId, Long userId) throws AccessException, NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Optional<Stack> optionalStack = stackRepository.findById(optionalCard.get().getStack().getId());
            if (optionalStack.isPresent()) {
                if (boardMemberRepository.isBoardMemberExist(optionalStack.get().getBoard().getId(), userId).isPresent()) {
                    return optionalCard.get();
                } else {
                    throw new AccessException();
                }
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new NotFoundException();
        }
    }
}
