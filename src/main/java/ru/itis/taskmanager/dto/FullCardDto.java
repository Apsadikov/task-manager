package ru.itis.taskmanager.dto;

import lombok.*;
import ru.itis.taskmanager.entity.Card;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FullCardDto {
    private Long id;

    private String title;

    private StackDto stack;

    private String description;

    private Date deadline;

    private List<CommentDto> comments;
    private List<FileDto> files;

    private boolean isArchived;

    public static FullCardDto from(Card card) {
        return FullCardDto.builder()
                .id(card.getId())
                .deadline(card.getDeadline())
                .description(card.getDescription())
                .title(card.getTitle())
                .build();
    }

    public static List<FullCardDto> from(List<Card> cards) {
        return cards.stream().map(FullCardDto::from).collect(Collectors.toList());
    }
}
