package ru.itis.taskmanager.repository;

import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.Document;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class DocumentJpaRepository implements DocumentRepository {
    private static final String HQL_FIND_ALL = "SELECT f FROM Document f";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Document save(Document entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public List findAll() {
        return entityManager.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    public Optional<Document> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
