package ru.zyablitseva.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.zyablitseva.NauJava.entity.User;
import ru.zyablitseva.NauJava.service.UserService;

/**
 * Контроллер регистрации и входа.
 */
@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            return "registration";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}