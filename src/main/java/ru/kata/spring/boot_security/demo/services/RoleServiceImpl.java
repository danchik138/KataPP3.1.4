package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.Repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRolesList() {
        return roleRepository.findAll();
    }

    @Override
    public void saveOrUpdateRoles(Collection<Role> roles) {
        for (Role role : roles) {
            if (roleRepository.existsByName(role.getName())) {
                role.setId(roleRepository.findByName(role.getName()).getId());
            } else {
                roleRepository.save(role);
            }
        }
    }
}
