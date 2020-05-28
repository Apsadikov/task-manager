package ru.itis.taskmanager.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.entity.Card;
import ru.itis.taskmanager.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentJpaRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> findAllByCard(Card card) {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.card.id = :card_id ORDER BY c.id DESC", Comment.class)
                .setParameter("card_id", card.getId())
                .getResultList();
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    @Transactional
    public Comment save(Comment entity) {
        entityManager.persist(entity);
        return entity;
    }
}
