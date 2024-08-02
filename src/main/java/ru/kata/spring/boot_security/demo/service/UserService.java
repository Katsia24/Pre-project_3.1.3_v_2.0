package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void save(User user, List<Long> roles);

    void delete(long id);

    void update(User user, Long id, List<Long> roles);

    User findById(Long id);

    List<User> listUsers();

    //временный
    User findByUsername(String username);

}
