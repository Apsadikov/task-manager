package ru.itis.taskmanager.repository;

import ru.itis.taskmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    List<User> findUsersByName(String name);
}
