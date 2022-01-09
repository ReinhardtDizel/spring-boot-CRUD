package kata.academy.service;

import kata.academy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);

    void delete(User user);

    void deleteById(Long id);

    boolean existById(Long id);

    Optional<User> getUserByLogin(String login);

    Optional<User> getById(Long id);

    void updateUser(User user);

    List<User> getUsers();

    List<User> getUsersWithoutAdminInfo();
}

