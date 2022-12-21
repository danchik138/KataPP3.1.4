package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import java.security.Principal;

@RestController
public class MainController {
    @GetMapping("/")
    public String homePage() {
        return "home";
    }
    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return "secured part of service " + principal.getName();
    }

    @GetMapping("/admin")
    public String pageForAdmins(Principal principal) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return "secured part for admins " + principal.getName();
    }
    @GetMapping("/user")
    public String pageForUsers(Principal principal) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return "secured part for users " + principal.getName();
    }
}
