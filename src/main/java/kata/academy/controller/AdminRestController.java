package kata.academy.controller;

import kata.academy.dto.UserDto;
import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.User;
import kata.academy.service.RoleService;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(
        value = "/rest/admin", produces = {"application/json"},
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(HttpServletRequest request) {
        List<User> users = userService.getAll();
        return users != null
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto user) {
        User createdUser = new User();
        createdUser.setFirstName(user.getFirstName());
        createdUser.setLastName(user.getLastName());
        createdUser.setAge(user.getAge());
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(user.getPassword());
        try {
            createdUser = userService.saveUser(createdUser, roleService.getRoleById(user.getRoles()));
            return createdUser != null
                    ? new ResponseEntity<>(createdUser, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserAlreadyExist exist) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody UserDto user) {
        try {
            User editedUser = userService.updateUser(user, roleService.getRoleById(user.getRoles()));
            return editedUser != null
                    ? new ResponseEntity<>(editedUser, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch (UserAlreadyExist exist) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping
    public int delete(@RequestBody Long id) {
        return userService.deleteUser(id);
    }
}
