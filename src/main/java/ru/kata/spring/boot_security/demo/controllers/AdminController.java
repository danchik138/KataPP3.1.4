package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
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
        model.addAttribute("roles", roleService.getAllRolesList());
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
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        if (((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()==user.getId()) {
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        return "redirect:/admin";
    }

}
