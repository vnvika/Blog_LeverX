package org.vnvika.blog.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vnvika.blog.model.User;
import org.vnvika.blog.security.jwt.JwtUser;
import org.vnvika.blog.security.jwt.JwtUserFactory;
import org.vnvika.blog.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        final JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadedUserByUsername - user with username: {} successfully loaded", username);

        return jwtUser;
    }
}
