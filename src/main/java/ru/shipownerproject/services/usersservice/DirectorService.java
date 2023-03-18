package ru.shipownerproject.services.usersservice;

import ru.shipownerproject.models.user.User;

public interface DirectorService {

    void registerNewAdmin(User user);

    void registerNewUser(User user);

    void registerNewDirector(User user);

    void deleteUser(String username);
}
