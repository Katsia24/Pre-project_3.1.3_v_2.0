package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users")
    public String printUsers(ModelMap model) {
        model.addAttribute("userList", userService.listUsers());
        model.addAttribute("roleList", roleService.findAll());
        return "/admin/users";
    }

    @GetMapping("/newUser")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleService.findAll());
        return "/admin/newUser";
    }

    @PostMapping("/newUser")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          @RequestParam("roles") List<Long> roles) {
        if (bindingResult.hasErrors()) {
            return "/admin/users";
        }
        userService.save(user, roles);
        return "redirect:/admin/users";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/editUser")
    public String editUser(ModelMap model, @RequestParam("id") Long id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roleList", roleService.findAll());
        return "/admin/editUser";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult,
                           @RequestParam("id") Long id,
                           @RequestParam("roles") List<Long> roles) {
        if (bindingResult.hasErrors()) {
            return "/admin/editUser";
        }
        userService.update(user, id, roles);
        return "redirect:/admin/users";
    }

}
