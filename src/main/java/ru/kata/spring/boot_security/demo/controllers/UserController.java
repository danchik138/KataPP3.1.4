package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public String showMainUserPage(Model model) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> userList = new LinkedList<>();
        userList.add(current);
        model.addAttribute("users", userList);
        model.addAttribute("currentUser", current);
        return "user/main";
    }
}
