package ru.itis.taskmanager.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.entity.Card;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CardJpaRepositoryImpl implements CardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Card> findAll() {
        return null;
    }

    @Override
    @Transactional
    public Optional<Card> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Card.class, id));
    }

    @Override
    public void delete(Long id) {

    }

    @Transactional
    @Override
    public Card save(Card entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void move(Card card) {
        entityManager.createQuery("update Card c set c.stack.id = :stack_id where c.id = :id")
                .setParameter("stack_id", card.getStack().getId())
                .setParameter("id", card.getId())
                .executeUpdate();
    }

    @Override
    public void updatedDeadline(Card card) {
        entityManager.createQuery("update Card c set c.deadline = :deadline where c.id = :id")
                .setParameter("deadline", card.getDeadline())
                .setParameter("id", card.getId())
                .executeUpdate();
    }

    @Override
    public void updatedDescription(Card card) {
        entityManager.createQuery("update Card c set c.description = :description where c.id = :id")
                .setParameter("description", card.getStack().getId())
                .setParameter("id", card.getId())
                .executeUpdate();
    }
}
