package kata.academy.service;

import kata.academy.dto.UserDto;
import kata.academy.model.Role;
import kata.academy.model.User;

import java.util.List;

public interface UserService {
    User getById(long id);

    User getUserByLogin(String s);

    List<User> getAll();

    void saveUser(UserDto user, List<Role> roles);

    void updateUser(UserDto user, List<Role> roles);

    void deleteUser(long id);
}

