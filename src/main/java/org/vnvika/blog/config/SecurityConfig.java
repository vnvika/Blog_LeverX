package org.vnvika.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.vnvika.blog.security.jwt.JwtTokenFilter;
import org.vnvika.blog.security.jwt.TokenProvider;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private static final String LOGIN_ENDPOINTS = "/api/auth/**";
    private static final String REGISTER_ENDPOINTS = "/api/registration";
    private static final String ACTIVATE_ENDPOINTS = "/api/registration/activate/*";
    private static final String MAIN_ENDPOINTS = "/api/articles/all";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINTS, MAIN_ENDPOINTS, REGISTER_ENDPOINTS, ACTIVATE_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(SC_UNAUTHORIZED))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
