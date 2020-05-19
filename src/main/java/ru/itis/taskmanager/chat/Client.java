package ru.itis.taskmanager.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Client {
    private Long userId;
    private Long boardId;
    private Long lastReadMessageId;
}
