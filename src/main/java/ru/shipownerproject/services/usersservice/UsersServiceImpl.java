package ru.shipownerproject.services.usersservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.usersdatabase.UsersRepository;
import ru.shipownerproject.models.user.User;
import ru.shipownerproject.security.UserWrapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public static final String NU = "That user is not registered. ";

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    private User checkByUsername(String username) {
        return usersRepository.findByUsername(username).stream().findAny()
                .orElseThrow(() -> new UsernameNotFoundException(NU));
    }

    public List<UserWrapper> allUsersFromDB() {
        List<UserWrapper> userWrappers = new ArrayList<>();
        usersRepository.findAll().forEach(user -> userWrappers.add(new UserWrapper(user)));
        return userWrappers;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserWrapper(checkByUsername(username));
    }
}
