package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String printUsers(ModelMap model) {
        model.addAttribute("userList", userService.listUsers());
        return "/admin/users";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam(value = "email", required = false) String email,
                          @RequestParam(value = "yearOfBirth", required = false) int yearOfBirth) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setYearOfBirth(yearOfBirth);
        userService.add(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(userService.findById(id));
        return "redirect:/admin/users";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user) {
        if (userService.findById(user.getId()) != null) {
            userService.update(user);
        }
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/updateUser")
    public ModelAndView updateUserbyId(@RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("userUpdate");
        User user = userService.findById(id);
        mav.addObject("user", user);

        return mav;
    }
}
