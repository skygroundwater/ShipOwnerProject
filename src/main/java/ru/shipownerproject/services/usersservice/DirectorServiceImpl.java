package ru.shipownerproject.services.usersservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shipownerproject.databases.usersdatabase.UsersRepository;
import ru.shipownerproject.models.user.Role;
import ru.shipownerproject.models.user.User;
import ru.shipownerproject.utils.exceptions.AlreadyAddedToBaseException;
import ru.shipownerproject.utils.exceptions.NotFoundInBaseException;

import static ru.shipownerproject.services.usersservice.UsersServiceImpl.NU;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public static String THAT_USER = "That user with that username and ";

    @Autowired
    public DirectorServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private User processingForUser(User user, Role role) {
        user.setAllBooleansFieldsToTrue();
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkIfPresentByUsername(user, user.getRole());
        return user;
    }

    private void checkIfPresentByUsername(User user, Role role) {
        if (usersRepository.findByUsername(user.getUsername()).isPresent())
            throw new AlreadyAddedToBaseException(THAT_USER + role.toString());
    }

    private User checkIfNotPresent(String username) {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundInBaseException(NU));
    }

    @Override
    public void registerNewAdmin(User user){
        usersRepository.save(processingForUser(user, Role.ROLE_ADMIN));
    }

    @Override
    public void registerNewUser(User user){
        usersRepository.save(processingForUser(user, Role.ROLE_USER));
    }

    @Override
    public void registerNewDirector(User user) {
        usersRepository.save(processingForUser(user, Role.ROLE_DIRECTOR));
    }

    @Override
    public void deleteUser(String username) {
        usersRepository.delete(checkIfNotPresent(username));
    }
}
