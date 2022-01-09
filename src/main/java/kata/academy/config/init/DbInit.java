package kata.academy.config.init;

import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.service.UserService;

import java.util.List;
import java.util.Set;

public class DbInit {

    private final UserService userService;

    private String password;
    private String login;
    private String name;
    private String role;

    public DbInit(UserService userService) {
        this.userService = userService;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void init() {
        if (userService.getUserByLogin(login).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(role);
            User admin = new User();
            admin.setLogin(login);
            admin.setPassword(password);
            admin.setName(name);
            admin.setRoles(Set.of(adminRole));
            userService.save(admin);
        }
    }
}
