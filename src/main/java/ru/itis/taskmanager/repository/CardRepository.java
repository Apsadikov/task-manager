package ru.itis.taskmanager.repository;

import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    void move(Card card);

    void updatedDeadline(Card card);
    void updatedDescription(Card card);
}
