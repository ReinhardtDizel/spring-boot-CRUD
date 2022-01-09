package kata.academy.security;

import kata.academy.exception.UserToEditNotFound;
import kata.academy.model.User;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserSecurityDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.getUserByLogin(username);
        return optionalUser.orElseThrow(UserToEditNotFound::new);
    }
}
