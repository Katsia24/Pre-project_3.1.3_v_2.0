package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDetailsService userDetailsService;
    private final UserDetailsServiceImp userDetailsServiceImp;

    public UserController(UserDetailsService userDetailsService, UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsService = userDetailsService;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @GetMapping("/showData")
    public String userPage(Principal principal, ModelMap model) {
        User user = userDetailsServiceImp.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user/showData";
    }
}
