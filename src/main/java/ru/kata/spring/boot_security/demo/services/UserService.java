package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;

@Service
public interface UserService extends UserDetailsService {
    @Transactional
    User findByUserName(String username);
    @Override
    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}