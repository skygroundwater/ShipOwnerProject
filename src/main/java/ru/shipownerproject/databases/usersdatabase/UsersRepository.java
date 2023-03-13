package ru.shipownerproject.databases.usersdatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shipownerproject.models.user.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

}
