package org.vnvika.blog.service.Impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.vnvika.blog.model.Role;
import org.vnvika.blog.model.StatusUser;
import org.vnvika.blog.model.User;
import org.vnvika.blog.repository.RoleRepository;
import org.vnvika.blog.repository.UserRepository;
import org.vnvika.blog.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        final Role roleUser = roleRepository.findByName("ROLE_USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(singletonList(roleUser));
        user.setStatus(StatusUser.ACTIVE);

        User registerUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registerUser);

        return registerUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String name) {
        User result = userRepository.findByUsername(name).orElseThrow(()->new UsernameNotFoundException("User is not found"));
        log.info("IN findByUsername - user: {} found by username: {}", result, name);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User is not found"));

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
