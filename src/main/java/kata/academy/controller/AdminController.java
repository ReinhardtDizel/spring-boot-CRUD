package kata.academy.controller;

import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.security.UserPermissions;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String printAdmin(Model model) {
        model.addAttribute(
                "users",
                userService.getUsers());
        return "admin";
    }

    @GetMapping("/editUser/{id}")
    public String printEditedUser(Model model,
                                  @PathVariable Long id) {

        return userService.getById(id).map(user -> {
                    model.addAttribute("error", "");
                    model.addAttribute("userEdit", user);
                    model.addAttribute("user_roles", user.getRoles());
                    model.addAttribute("possible_roles",
                            Arrays.stream(UserPermissions.values())
                                    .map(UserPermissions::getValue)
                                    .map(Role::new)
                                    .collect(Collectors.toSet()));
                    return "editUser";
                })
                .orElse("redirect:/addUser");
    }

    @GetMapping("/addUser")
    public String printAddUser(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("userDto", new User());
        model.addAttribute("permissions",
                Arrays.stream(UserPermissions.values())
                        .map(UserPermissions::getValue)
                        .map(Role::new)
                        .collect(Collectors.toSet()));
        return "addUser";
    }

    @PostMapping("/addUser")
    public ModelAndView addNewUser(Model model,
                                   @ModelAttribute("userDto") User user) {
        ModelAndView modelAndView = new ModelAndView("addUser");

        try {
            userService.save(user);
            if (userService.getUserByLogin(user.getLogin()).isPresent()) {
                return new ModelAndView(new RedirectView("/admin", true));
            }
        } catch (UserAlreadyExist userAlreadyExist) {
            modelAndView.addObject("error", " такой email уже есть!");
        }
        return modelAndView;
    }

    @PostMapping("/editUser")
    public ModelAndView editedUser(Model model,
                                   @ModelAttribute("userDto") User user) {
        System.out.println(user);
        //userService.updateUser(user);
        return new ModelAndView(new RedirectView("editUser/" + user.getId(), true));
    }

    @PostMapping("/{request}={id}")
    public ModelAndView deleteUser(Model model,
                                   @PathVariable String request,
                                   @PathVariable Long id) {
        if (request.equals("delete")) {
            userService.deleteById(id);
        } else if (request.equals("edit")) {
            return new ModelAndView(new RedirectView("editUser/" + id, true));
        }
        return new ModelAndView(new RedirectView("/admin", true));
    }
}
