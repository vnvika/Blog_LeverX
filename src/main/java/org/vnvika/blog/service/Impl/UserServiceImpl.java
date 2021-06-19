package org.vnvika.blog.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vnvika.blog.model.Role;
import org.vnvika.blog.model.StatusUser;
import org.vnvika.blog.model.User;
import org.vnvika.blog.repository.RoleRepository;
import org.vnvika.blog.repository.UserRepository;
import org.vnvika.blog.service.UserService;

import java.util.List;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ROLE_USER = "ROLE_USER";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        final Role roleUser = roleRepository.findByName(ROLE_USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(singleton(roleUser));
        user.setStatus(StatusUser.ACTIVE);

        final User registerUser = userRepository.save(user);

        log.info("Register - user: {} successfully registered", registerUser);

        return registerUser;
    }

    @Override
    public List<User> getAll() {
        final List<User> result = userRepository.findAll();
        log.info("GetAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String name) {
        final User result = userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        log.info("FindByUsername - user: {} found by username: {}", result, name);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User is not found"));

        log.info("FindById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("Delete - user with id: {} successfully deleted", id);
    }
}
