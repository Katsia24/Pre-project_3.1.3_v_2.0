package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    // работает
    @GetMapping(value = "/users")
    public String printUsers(ModelMap model) {
        model.addAttribute("userList", userService.listUsers());
        model.addAttribute("roleList", roleRepository.findAll());
        return "/admin/users";
    }

    // работает
    @GetMapping("/newUser")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleRepository.findAll());
        return "/admin/newUser";
    }

    // работает
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


    // работает
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    // работает
    @GetMapping("/editUser")
    public String editUser(ModelMap model, @RequestParam("id") Long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roleList", roleRepository.findAll());
        return "/admin/editUser";
    }

    // работает
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
