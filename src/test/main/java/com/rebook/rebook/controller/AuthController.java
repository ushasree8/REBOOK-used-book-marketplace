package com.rebook.rebook.controller;


import com.rebook.rebook.entity.Role;
import com.rebook.rebook.entity.User;
import com.rebook.rebook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        try {
            userService.registerUser(user);
            return "redirect:/login";

        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", Role.values());

            return "register";
        }

    }
    @GetMapping("/login")
public String loginPage() {
    return "login";
}

}