package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    @Transactional
    User findByUserName(String username);
    @Override
    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Transactional
    void saveUser(User user);

    @Transactional
    void updateUser(User user);

    @Transactional
    void deleteAllUsers();

    @Transactional
    User getUserById(long id);

    @Transactional
    void deleteUserById(long id);

    @Transactional
    List<User> getAllUsersList();
}