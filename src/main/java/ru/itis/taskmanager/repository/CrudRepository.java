package ru.itis.taskmanager.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, T> {
    List<E> findAll();

    Optional<E> findById(T id);

    void delete(E id);

    void save(E entity);
}
