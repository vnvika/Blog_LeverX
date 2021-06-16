package org.vnvika.blog.security.jwt;

import org.springframework.security.core.Authentication;
import org.vnvika.blog.model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TokenProvider {
    String createToken(String username, List<Role> roles);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String resolveToken(HttpServletRequest req);

    boolean validateToken(String token);
}
