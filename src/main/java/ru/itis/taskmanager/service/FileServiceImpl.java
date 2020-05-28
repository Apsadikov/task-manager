package ru.itis.taskmanager.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.taskmanager.dto.FileDto;
import ru.itis.taskmanager.entity.Card;
import ru.itis.taskmanager.entity.Stack;
import ru.itis.taskmanager.repository.BoardMemberRepository;
import ru.itis.taskmanager.repository.CardRepository;
import ru.itis.taskmanager.repository.FileRepository;
import ru.itis.taskmanager.repository.StackRepository;
import ru.itis.taskmanager.util.FileNameGenerator;
import ru.itis.taskmanager.util.exception.AccessException;
import ru.itis.taskmanager.util.exception.NotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    private CardRepository cardRepository;
    private FileRepository fileRepository;
    private BoardMemberRepository boardMemberRepository;
    private StackRepository stackRepository;
    @Value("${storage.path}")
    private String storage;

    @Autowired
    public FileServiceImpl(CardRepository cardRepository, BoardMemberRepository boardMemberRepository,
                           StackRepository stackRepository, FileRepository fileRepository) {
        this.cardRepository = cardRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.stackRepository = stackRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public List<FileDto> getFiles(Long cardId, Long userId) throws AccessException, NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Optional<Stack> optionalStack = stackRepository.findById(optionalCard.get().getStack().getId());
            if (optionalStack.isPresent()) {
                if (boardMemberRepository.isBoardMemberExist(optionalStack.get().getBoard().getId(), userId).isPresent()) {
                    return FileDto.from(fileRepository.findByCard(Card.builder().id(cardId).build()));
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
    public Optional<FileDto> getFile(Long userId, String fileName) {
        Optional<ru.itis.taskmanager.entity.File> fileOptional = fileRepository.findByFile(fileName);
        FileDto fileDto = null;
        if (fileOptional.isPresent()) {
            File file = new File(storage + fileName);
            fileDto = FileDto.builder()
                    .file(fileName)
                    .realFile(file)
                    .build();
        }
        return Optional.ofNullable(fileDto);
    }

    @Override
    public String addFile(Long userId, MultipartFile file, Long cardId) throws AccessException, NotFoundException, IOException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Optional<Stack> optionalStack = stackRepository.findById(optionalCard.get().getStack().getId());
            if (optionalStack.isPresent()) {
                if (boardMemberRepository.isBoardMemberExist(optionalStack.get().getBoard().getId(), userId).isPresent()) {
                    String extension = file.getOriginalFilename()
                            .substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
                    File newFile = new File(storage + FileNameGenerator.generate() + extension);
                    IOUtils.copyLarge(
                            file.getInputStream(),
                            new FileOutputStream(newFile)
                    );
                    fileRepository.save(ru.itis.taskmanager.entity.File.builder()
                            .card(Card.builder().id(cardId).build())
                            .file(newFile.getName())
                            .build());
                    return newFile.getName();
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
