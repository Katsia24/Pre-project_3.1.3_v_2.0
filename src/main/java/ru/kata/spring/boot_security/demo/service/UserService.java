package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void save(User user, List<Long> roles);

    void delete(long id);

    void update(User user, Long id, List<Long> roles);

    User getById(Long id);

    List<User> listUsers();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
