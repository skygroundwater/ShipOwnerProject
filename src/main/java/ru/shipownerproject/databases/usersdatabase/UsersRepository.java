package ru.shipownerproject.databases.usersdatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shipownerproject.models.user.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
