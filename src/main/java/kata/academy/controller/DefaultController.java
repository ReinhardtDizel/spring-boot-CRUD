package kata.academy.controller;

import kata.academy.exception.UserAlreadyExist;
import kata.academy.model.Role;
import kata.academy.model.User;
import kata.academy.security.UserPermissions;
import kata.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Set;

@Controller
@RequestMapping("/")
public class DefaultController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String helloPage(Model model) {
        model.addAttribute("users", userService.getUsersWithoutAdminInfo());
        return "index";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("signup")
    public String sigUp(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("error", "");
        return "signup";
    }

    @PostMapping("signup")
    public ModelAndView sigUpRegister(Model model,
                                      @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("signup");
        try {
            Role defaultRole = new Role();
            defaultRole.setName(UserPermissions.USER.getValue());
            user.setRoles(Set.of(defaultRole));
            userService.save(user);
            if (userService.getUserByLogin(user.getLogin()).isPresent()) {
                return new ModelAndView(new RedirectView("login", true));
            }
        } catch (UserAlreadyExist userAlreadyExist) {
            modelAndView.addObject("error", " такой email уже есть!");
        }
        return modelAndView;
    }
}
