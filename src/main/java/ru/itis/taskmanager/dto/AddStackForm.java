package ru.itis.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.taskmanager.util.validation.Title;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddStackForm {
    @Title
    private String title;
}
