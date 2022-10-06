package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery(" select distinct u from User u join fetch u.roles r order by u.id ",
                User.class).getResultList();
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void delUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updUser(User user) {
        User userFromDb = userRepository.getById(user.getId());
        if (!userFromDb.getPassword().equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);

    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findByMail(String mail) {
        return userRepository.findByMail(mail);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByMail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(

                user.getMail(),
                user.getPassword(),
                user.getAuthorities());
    }
}
