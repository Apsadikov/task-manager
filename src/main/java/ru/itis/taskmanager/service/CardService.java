package ru.itis.taskmanager.service;

import org.springframework.stereotype.Service;
import ru.itis.taskmanager.dto.CardDto;
import ru.itis.taskmanager.dto.FullCardDto;
import ru.itis.taskmanager.util.exception.AccessException;
import ru.itis.taskmanager.util.exception.NotFoundException;


import java.util.Date;
import java.util.Optional;

@Service
public interface CardService {
    void addCard(CardDto cardDto, Long userId) throws NotFoundException, AccessException;

    void moveCard(CardDto cardDto, Long userId) throws AccessException, NotFoundException;

    void updateDescription(String description, Long cardId, Long userId) throws AccessException, NotFoundException;

    void addComment(String comment, Long cardId, Long userId) throws AccessException, NotFoundException;

    void updateDeadline(Date deadline, Long cardId, Long userId) throws AccessException, NotFoundException;

    Optional<FullCardDto> getCard(Long cardId, Long id) throws NotFoundException, AccessException;
}
