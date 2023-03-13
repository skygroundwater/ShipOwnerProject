package ru.shipownerproject.services.usersservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.usersdatabase.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;


    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}
