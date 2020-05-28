package ru.itis.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.taskmanager.entity.Message;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageDto {
    private Long id;
    private UserDto userDto;
    private BoardDto boardDto;
    private String text;

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .boardDto(BoardDto.from(message.getBoard()))
                .userDto(UserDto.from(message.getUser()))
                .text(message.getMessage())
                .build();
    }

    public static List<MessageDto> from(List<Message> messageList) {
        return messageList.stream().map(MessageDto::from).collect(Collectors.toList());
    }
}
