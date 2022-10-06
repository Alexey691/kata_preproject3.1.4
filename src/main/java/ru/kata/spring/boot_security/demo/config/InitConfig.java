package ru.kata.spring.boot_security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

import java.util.*;


@Component
public class InitConfig {

    private final RoleService roleService;
    private final UserService userService;
    private final User user = new User(
            "User",
            "Useroff",
            "user@mail.ru",
            22, "111");
    private final User admin = new User(
            "Алексей",
            "Adminoff",
            "admin@mail.ru",
            33, "999");

    @Autowired
    public InitConfig(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    private void assignRoles(User user, String roleName) {
        Role role = new Role(roleName);
        List<Role> guestRoles = new ArrayList<>();
        guestRoles.add(role);
        roleService.save(role);
        user.setRoles(guestRoles);
    }

    @PostConstruct
    public void init() {

        if (roleService.findByName("ROLE_ADMIN") == null) {
            assignRoles(admin, "ROLE_ADMIN");
            userService.saveUser(admin);
        }

        if (roleService.findByName("ROLE_USER") == null) {
            assignRoles(user, "ROLE_USER");
            userService.saveUser(user);
        }
    }
}
