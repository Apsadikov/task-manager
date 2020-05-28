package ru.itis.taskmanager.repository;

import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.entity.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
    List<Document> findAll();
}