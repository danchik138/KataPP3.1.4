package ru.kata.spring.boot_security.demo.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByLogin(String username) {
        return userRepository.findByLogin(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByLoginOrEmail(String loginOrEmail) throws UsernameNotFoundException {
        User user;
        user = userRepository.findByLogin(loginOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(loginOrEmail);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String nameOrEmail) throws UsernameNotFoundException {
        User user = findByLogin(nameOrEmail);
        if (user != null) {
            user.setUserDetailsName(user.getUsername());
        } else {
            user = findByEmail(nameOrEmail);
            if (user != null) {
                user.setUserDetailsName(user.getEmail());
            }
        }
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user with uch name or email: " + nameOrEmail
            );
        }
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleService.saveOrUpdateRoles(user.getRoles());
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            if (user.getPassword() == null || user.getPassword().equals("")) {
                user.setPassword(
                        getUserById(user.getId()).getPassword()
                );
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            roleService.saveOrUpdateRoles(user.getRoles());
            userRepository.save(user);
        }
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsersList() {
        return userRepository.findAll();
    }

    @Override
    public List<Long> getAllIds() {
        return userRepository .getAllIds();
    }
}
