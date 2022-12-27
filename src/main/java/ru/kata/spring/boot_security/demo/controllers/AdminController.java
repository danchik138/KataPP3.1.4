package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String mainAdminPage(Model model){
        model.addAttribute("users", userService.getAllUsersList());
        User user = new User();
        User current = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentUser", current);
        model.addAttribute("user", user);
        model.addAttribute("ids", userService.getAllIds());
        return "admin/main";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") String id){
        userService.deleteUserById(Integer.parseInt(id));
        if (((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()==Integer.parseInt(id)) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/admin";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        user.setRoles(createRoles(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        user.setRoles(createRoles(role));
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        if (((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()==user.getId()) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        return "redirect:/admin";
    }

    Collection<Role> createRoles(String role) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        if (role.equals("ROLE_ADMIN")) {
            roles.add(new Role("ROLE_ADMIN"));
        }
        return roles;
    }
}
