package kata.academy.service;

import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.User;
import kata.academy.repository.UserRepository;
import kata.academy.security.UserPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        if (userRepository.getUserByLogin(user.getLogin()).isEmpty()) {

            userRepository.save(user);
        } else {
            throw new UserAlreadyExist();
        }
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        getById(id).ifPresent(userRepository::delete);
    }

    @Override
    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public Optional<User> getById(Long id) {
        try {
            return userRepository.getUserById(id);
        } catch (EntityNotFoundException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersWithoutAdminInfo() {
        return getUsers()
                .stream()
                .filter(user -> user
                        .getRoles()
                        .stream()
                        .noneMatch(role -> role
                                .getAuthority().equals(UserPermissions.ADMIN.getValue())))
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void updateUser(User user) {
        try {
            userRepository.getUserById(user.getId()).ifPresent(
                    exist -> {
                        exist.setLogin(user.getLogin());
                        exist.setName(user.getName());
                        exist.setPassword(user.getPassword());
                       
                    });

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
