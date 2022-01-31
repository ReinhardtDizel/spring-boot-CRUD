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

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<User>> getAll() {
        return Optional
                .ofNullable(userService.getAll())
                .map(users -> ResponseEntity.ok().body(users))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto user) {
        try {
            return Optional
                    .ofNullable(userService.saveUser(user, roleService.getRoleById(user.getRoles())))
                    .map(u -> ResponseEntity.ok().body(u))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (UserAlreadyExist exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Юзер с таким email уже есть");
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody UserDto user) {
        try {
            return Optional
                    .ofNullable(userService.updateUser(user, roleService.getRoleById(user.getRoles())))
                    .map(u -> ResponseEntity.ok().body(u))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_MODIFIED).build());
        } catch (UserAlreadyExist exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Юзер с таким email уже есть");
        }
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
