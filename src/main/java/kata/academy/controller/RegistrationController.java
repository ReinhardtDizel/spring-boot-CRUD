package kata.academy.controller;

import kata.academy.dto.UserDto;
import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.User;
import kata.academy.service.RoleService;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/signup")
public class RegistrationController {

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

    @GetMapping
    public String save(Model model) {

        model.addAttribute("user", new UserDto());
        model.addAttribute("error", "");
        return "signup";
    }

    @PostMapping
    public String save(Model model, @ModelAttribute("user") UserDto user) {
        try {
            User createdUser = new User();
            createdUser.setName(user.getName());
            createdUser.setLogin(user.getLogin());
            createdUser.setPassword(user.getPassword());
            userService.saveUser(createdUser, List.of(roleService.getRoleByName("ROLE_USER")));
            return "redirect:login";
        } catch (UserAlreadyExist exception) {
            model.addAttribute("error", " такой login уже есть!");
        }
        return "signup";
    }
}
