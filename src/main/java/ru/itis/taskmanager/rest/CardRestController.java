package ru.itis.taskmanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.aspect.BoardAccess;
import ru.itis.taskmanager.dto.AddCardForm;
import ru.itis.taskmanager.dto.CardDto;
import ru.itis.taskmanager.dto.StackDto;
import ru.itis.taskmanager.security.details.UserDetailsImpl;
import ru.itis.taskmanager.service.CardService;
import ru.itis.taskmanager.util.exception.AccessException;
import ru.itis.taskmanager.util.exception.NotFoundException;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
public class CardRestController {
    private CardService cardService;

    @Autowired
    public CardRestController(CardService cardService) {
        this.cardService = cardService;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/cards/{id}", method = RequestMethod.GET)
    public ResponseEntity getCard(@PathVariable("id") Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            return ResponseEntity.ok(cardService.getCard(cardId, userDetails.getUser().getId()).get());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/stacks/{stack_id}/cards", method = RequestMethod.POST)
    public ResponseEntity addCard(@PathVariable("stack_id") Long stackId, @Valid AddCardForm addCardForm, BindingResult bindingResult,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("title") String title) {
        try {
            CardDto cardDto = CardDto.builder()
                    .stack(StackDto.builder().id(stackId).build())
                    .title(title)
                    .build();
            cardService.addCard(cardDto, userDetails.getUser().getId());
            return ResponseEntity.ok(cardDto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @BoardAccess
    @RequestMapping(value = "/api/boards/{board_id}/stacks/{stack_id}/cards/{id}", method = RequestMethod.PUT)
    public ResponseEntity moveCard(@PathVariable("id") Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable("board_id") Long boardId,
                                   @PathVariable("stack_id") Long stackId) {
        try {
            cardService.moveCard(CardDto.builder()
                    .id(cardId)
                    .stack(StackDto.builder()
                            .id(stackId)
                            .build())
                    .build(), userDetails.getUser().getId());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/cards/{id}/description", method = RequestMethod.PUT)
    public ResponseEntity updateDescription(@PathVariable("id") Long cardId, @RequestParam("description") String description,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            cardService.updateDescription(description, cardId, userDetails.getUser().getId());
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/cards/{id}/deadline", method = RequestMethod.PUT)
    public ResponseEntity updateDeadline(@PathVariable("id") Long cardId, @RequestParam("deadline") String deadline,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = format.parse(deadline);
            cardService.updateDeadline(date, cardId, userDetails.getUser().getId());
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NotFoundException | ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/api/cards/{id}/comment", method = RequestMethod.POST)
    public ResponseEntity addComment(@PathVariable("id") Long cardId, @RequestParam("comment") String comment,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            cardService.addComment(comment, cardId, userDetails.getUser().getId());
        } catch (AccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }
}
