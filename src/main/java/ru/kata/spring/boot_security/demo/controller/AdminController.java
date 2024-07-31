package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
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


    @GetMapping(value = "/users")
    public String printUsers(ModelMap model) {
        model.addAttribute("userList", userService.listUsers());
        model.addAttribute("roleList", roleRepository.findAll());
        return "/admin/users";
    }

    @GetMapping("/newUser")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleRepository.findAll());
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

//    @PostMapping("/addUser")
//    public String addUser(@RequestParam("username") String username,
//                          @RequestParam("password") String password,
//                          @RequestParam(value = "email", required = false) String email,
//                          @RequestParam(value = "yearOfBirth", required = false) int yearOfBirth) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//        user.setYearOfBirth(yearOfBirth);
//        userService.add(user);
//
//        return "redirect:/admin/users";
//    }

    // работает
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        if (userService.findById(user.getId()) != null) {
            userService.update(user);
        }
        return "redirect:/admin/users";
    }

    // в отдельной html
    @GetMapping(value = "/updateUser")
    public ModelAndView updateUserById(@RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("admin/userUpdate");
        User user = userService.findById(id);
        mav.addObject("user", user);

        List<Role> roles = (List<Role>) roleRepository.findAll();
        mav.addObject("allRoles", roles);

        return mav;
    }
}
