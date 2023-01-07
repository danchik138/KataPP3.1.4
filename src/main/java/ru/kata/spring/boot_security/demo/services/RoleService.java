package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;
import java.util.List;

@Service
public interface RoleService {
    @Transactional
    List<Role> getAllRolesList();

    @Transactional
    void saveOrUpdateRoles(Collection<Role> roles);
}
