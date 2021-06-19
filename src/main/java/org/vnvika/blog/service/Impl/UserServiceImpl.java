package org.vnvika.blog.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.vnvika.blog.dto.UserDto;
import org.vnvika.blog.mapper.UserMapper;
import org.vnvika.blog.model.User;
import org.vnvika.blog.repository.RoleRepository;
import org.vnvika.blog.repository.UserRepository;
import org.vnvika.blog.service.MailSender;
import org.vnvika.blog.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ROLE_USER = "ROLE_USER";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSender mailSender;


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
    public User register(UserDto userDto) {
        User user = UserMapper.INSTANCE.fromDTO(userDto);
        User existUser = userRepository.getByUsername(user.getUsername());
        if (existUser != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User is exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivate(false);
        user.setActivationCode(UUID.randomUUID().toString());
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Email is invalid");
        }
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to here. Please, visit next link: http://localhost:9090/api/registration/activate/%s",
                user.getUsername(),
                user.getActivationCode());

        mailSender.send(user.getEmail(), "Activation Code", message);
        User registeredUser = userRepository.save(user);
        log.info("Register - user: {} should activate account", registeredUser);
        return registeredUser;
    }

    @Override
    public User activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        try {
            if (user == null) {
                throw new IllegalArgumentException("Invalid user activation code");
            }
            user.setActivationCode(null);
            user.setActivate(true);
            log.info("ActivateUser - user: {} successfully activated", user);
            return userRepository.save(user);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Access denied, user already activated or not registered", ex);
        }
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
