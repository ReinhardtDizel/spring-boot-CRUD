package kata.academy.service;

import kata.academy.dto.UserDto;
import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void updateUser(UserDto user, List<Role> roles) {
        User updated = getUserByLogin(user.getLogin());
        if (updated != null) {
            if (Objects.equals(updated.getId(), user.getId())) {
                updated.setName(user.getName());
                updated.setLogin(user.getLogin());
                updated.setPassword(passwordEncoder.encode(user.getPassword()));
                updated.setRoles(new HashSet<>(roles));
            } else {
                throw new UserAlreadyExist();
            }
        }
    }

    @Override
    public User getById(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User getUserByLogin(String s) {
        return userRepository.findUserByLogin(s);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user, List<Role> roles) {
        if (getUserByLogin(user.getLogin()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            getUserByLogin(user.getLogin()).setRoles(new HashSet<>(roles));
        } else {
            throw new UserAlreadyExist();
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
