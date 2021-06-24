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
import org.vnvika.blog.model.ActivateCode;
import org.vnvika.blog.model.StatusUser;
import org.vnvika.blog.model.User;
import org.vnvika.blog.repository.ActivateCodeRepository;
import org.vnvika.blog.repository.UserRepository;
import org.vnvika.blog.service.MailSender;
import org.vnvika.blog.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSender mailSender;
    private final ActivateCodeRepository activateCodeRepository;


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
        if (existUser == user) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User is exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(StatusUser.NOT_ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        ActivateCode activateCode = setActivateCode(user);
        sendMessageRegistration(user, activateCode.getActivateCode());

        log.info("Register - user: {} should activate account", user);
        return userRepository.save(user);
    }

    @Override
    public void activateUser(String code) {
        List<ActivateCode> activateCodes = (List<ActivateCode>) activateCodeRepository.findAll();
        ActivateCode activateCode = activateCodes.stream()
                .filter(currentCode -> code.equals(currentCode.getActivateCode()))
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException("Code not found"));
        try {
            if (activateCode == null) {
                throw new IllegalArgumentException("Invalid user activation code");
            }
            activateCode.setActivateCode(null);
            activateCode.setActivate(false);
            User user = userRepository.getByUsername(activateCode.getUsername());
            user.setStatus(StatusUser.ACTIVE);
            activateCodeRepository.save(activateCode);
            userRepository.save(user);

            log.info("User - {} was activated",user);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Access denied, user already activated or not registered", ex);
        }
    }

    @Override
    public User forgotPassword(UserDto userDto) {
        User user = UserMapper.INSTANCE.fromDTO(userDto);
        User existUser = userRepository.findByEmail(user.getEmail());
        if (existUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Mail is not registered");
        }
        ActivateCode activateCode = setActivateCode(existUser);
        sendMessageResetPassword(existUser, activateCode.getActivateCode());

        log.info("forgotPasswordUser - user: {} should activate account", existUser);
        return existUser;
    }

    @Override
    public User resetPassword(String code, UserDto userDto) {
        User user = UserMapper.INSTANCE.fromDTO(userDto);
        user = userRepository.getByUsername(user.getUsername());
        List<ActivateCode> activateCodes = (List<ActivateCode>) activateCodeRepository.findAll();
        ActivateCode activateCode = activateCodes.stream()
                .filter(currentCode -> code.equals(currentCode.getActivateCode()))
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException("Code not found"));
        try {
            if (!user.getUsername().equals(activateCode.getUsername())) {
                throw new IllegalArgumentException("Invalid user activation code");
            }
            activateCode.setActivateCode(null);
            activateCode.setActivate(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUpdated(new Date());

            log.info("ActivateUser - user: {} successfully activated", user);

            activateCodeRepository.save(activateCode);
            return userRepository.save(user);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Access denied, user already activated or not registered", ex);
        }
    }


    private ActivateCode setActivateCode(User user) {
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Email is invalid");
        }
        ActivateCode activateCode = new ActivateCode();
        activateCode.setId(UUID.randomUUID());
        activateCode.setUsername(user.getUsername());
        activateCode.setActivate(true);
        activateCode.setActivateCode(UUID.randomUUID().toString());
        return activateCodeRepository.save(activateCode);
    }

    private void sendMessageRegistration(User user, String code) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to blog. Please, visit next link: http://localhost:9090/api/registration/activate/%s",
                user.getUsername(),
                code);

        mailSender.send(user.getEmail(), "Activation Code", message);
    }

    private void sendMessageResetPassword(User user, String code) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to blog. Please, visit next link: http://localhost:9090/api/auth/reset/%s",
                user.getUsername(),
                code);

        mailSender.send(user.getEmail(), "Activation Code", message);
    }
}
