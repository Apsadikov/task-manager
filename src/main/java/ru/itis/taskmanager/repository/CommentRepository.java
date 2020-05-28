package ru.itis.taskmanager.repository;

import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.Card;
import ru.itis.taskmanager.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByCard(Card card);
}
