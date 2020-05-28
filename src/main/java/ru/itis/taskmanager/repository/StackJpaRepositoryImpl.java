package ru.itis.taskmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.entity.Stack;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class StackJpaRepositoryImpl implements StackRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Stack> findAll() {
        return entityManager.createQuery("SELECT s FROM Stack s").getResultList();
    }

    @Override
    @Transactional
    public Optional<Stack> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Stack.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Stack> optionalStack = findById(id);
        optionalStack.ifPresent(stack -> entityManager.remove(stack));
    }

    @Override
    @Transactional
    public Stack save(Stack entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Stack> getStacksByBoardId(Long boardId) {
        EntityGraph entityGraph = entityManager.getEntityGraph("stack-card");
        return entityManager.createQuery("SELECT s FROM Stack s WHERE s.board.id = :board_id ORDER BY s.id DESC", Stack.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setParameter("board_id", boardId)
                .getResultList();
    }
}
