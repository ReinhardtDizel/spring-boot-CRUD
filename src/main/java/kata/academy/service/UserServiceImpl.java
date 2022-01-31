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
    public User getById(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User getUserByLogin(String s) {
        return userRepository.findUsersByEmail(s);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User saveUser(UserDto user, List<Role> roles) {
        if (getUserByLogin(user.getEmail()) == null) {
            User createdUser = new User(user.getFirstName(),
                    user.getLastName(),
                    user.getAge(),
                    user.getEmail(),
                    passwordEncoder.encode(user.getPassword()),
                    new HashSet<>(roles));
            return userRepository.save(createdUser);
        } else {
            throw new UserAlreadyExist();
        }
    }

    @Override
    @Transactional
    public User updateUser(UserDto user, List<Role> roles) {
        User updated = getById(user.getId());
        if (updated != null) {
            if (getUserByLogin(user.getEmail()) != null) {
                if (!Objects.equals(updated.getId(), getUserByLogin(user.getEmail()).getId())) {
                    throw new UserAlreadyExist();
                }
            }
            updated.setFirstName(user.getFirstName());
            updated.setLastName(user.getLastName());
            updated.setAge(updated.getAge());
            updated.setEmail(user.getEmail());
            updated.setPassword(passwordEncoder.encode(user.getPassword()));
            updated.setRoles(new HashSet<>(roles));
            return updated;
        }
        return null;
    }

    @Override
    @Transactional
    public int deleteUser(long id) {
        return userRepository.deleteById(id);
    }
}
