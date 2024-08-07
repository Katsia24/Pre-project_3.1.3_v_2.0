package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserDao userDao) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void add(User user) {

        userDao.add(user);
    }

    @Transactional
    @Override
    public void save(User user, List<Long> roles) {
        User savedUser = userRepository.save(user);
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> savedRoles = roleRepository.findAllById(roles);
        savedUser.setRoles(new HashSet<>(savedRoles));
        userRepository.save(savedUser);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional
    public void update(User user, Long id, List<Long> roles) {
        User savedUser = userRepository.getById(id);
        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        savedUser.setEmail(user.getEmail());
        savedUser.setYearOfBirth(user.getYearOfBirth());
        List<Role> savedRoles = roleRepository.findAllById(roles);
        savedUser.setRoles(new HashSet<>(savedRoles));
        userRepository.save(savedUser);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDao.listUsers();
    }
}
