package ru.itis.taskmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.taskmanager.dto.FileDto;
import ru.itis.taskmanager.util.exception.AccessException;
import ru.itis.taskmanager.util.exception.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface FileService {
    List<FileDto> getFiles(Long cardId, Long userId) throws AccessException, NotFoundException;

    Optional<FileDto> getFile(Long userId, String file);
    String addFile(Long userId, MultipartFile file, Long cardId) throws AccessException, NotFoundException, IOException;
}
