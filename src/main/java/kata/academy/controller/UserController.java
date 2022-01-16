package kata.academy.controller;

import kata.academy.dto.UserDto;
import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.User;
import kata.academy.service.RoleService;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("possible_roles", roleService.getAll());
        model.addAttribute("error", "");
        return "createUser";
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute("user") UserDto user) {
        User createdUser = new User();
        createdUser.setName(user.getName());
        createdUser.setLogin(user.getLogin());
        createdUser.setPassword(user.getPassword());
        try {
            userService.saveUser(createdUser, roleService.getRoleById(user.getRoles()));
            return "redirect:/";
        } catch (UserAlreadyExist exception) {
            model.addAttribute("user", user);
            model.addAttribute("possible_roles", roleService.getAll());
            model.addAttribute("error", "такой login уже есть!");
        }
        return "createUser";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("possible_roles", roleService.getAll());
        model.addAttribute("error", "");
        return "editUser";
    }

    @PostMapping("/edit/{id}")
    public String edit(Model model, @ModelAttribute("userDto") UserDto user) {
        try {
            userService.updateUser(user, roleService.getRoleById(user.getRoles()));
            return "redirect:/";
        } catch (UserAlreadyExist exception) {
            model.addAttribute("user", user);
            model.addAttribute("possible_roles", roleService.getAll());
            model.addAttribute("error", "такой login уже есть!");
        }
        return "editUser";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

}
