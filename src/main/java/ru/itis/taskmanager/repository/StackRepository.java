package ru.itis.taskmanager.repository;

import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.Stack;

import java.util.List;

@Repository
public interface StackRepository extends CrudRepository<Stack, Long> {
    List<Stack> getStacksByBoardId(Long boardId);
}
