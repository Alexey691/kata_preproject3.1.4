package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void saveUser(User user);

    void delUser(long id);

    void updUser(User user);

    Optional<User> getUser(long id);

    List<User> getAllUsers();

    User findByName(String name);

    List<Role> getAllRoles();
}
