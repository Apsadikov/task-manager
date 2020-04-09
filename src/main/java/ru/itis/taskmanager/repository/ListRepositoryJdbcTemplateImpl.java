package ru.itis.taskmanager.repository;

import ru.itis.taskmanager.entity.List;

import java.util.Optional;

public class ListRepositoryJdbcTemplateImpl implements ListRepository {
    @Override
    public java.util.List<List> findAll() {
        return null;
    }

    @Override
    public Optional<List> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(List id) {

    }

    @Override
    public void save(List entity) {

    }
}
