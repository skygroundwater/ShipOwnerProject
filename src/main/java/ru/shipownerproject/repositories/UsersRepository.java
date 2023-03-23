package ru.shipownerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shipownerproject.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
