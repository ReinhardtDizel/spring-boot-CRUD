package kata.academy.service;

import kata.academy.dto.UserDto;
import kata.academy.model.Role;
import kata.academy.model.User;

import java.util.List;

public interface UserService {
    void updateUser(UserDto user, List<Role> roles);

    User getById(long id);

    User getUserByLogin(String s);

    List<User> getAll();

    User saveUser(User user, List<Role> roles);

    void deleteUser(long id);
}

