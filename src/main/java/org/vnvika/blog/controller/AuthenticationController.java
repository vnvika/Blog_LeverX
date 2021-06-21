package org.vnvika.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.vnvika.blog.dto.AuthenticationRequestDto;
import org.vnvika.blog.dto.TokenResponseDto;
import org.vnvika.blog.dto.UserDto;
import org.vnvika.blog.mapper.UserMapper;
import org.vnvika.blog.security.jwt.TokenProvider;
import org.vnvika.blog.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid AuthenticationRequestDto requestDto) {

        final String username = requestDto.getUsername();
        final String password = requestDto.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = tokenProvider.createToken(username);

        final TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                .username(username)
                .token(token)
                .build();

        return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("forgot_password")
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@Valid @RequestBody UserDto user) {
        UserMapper.INSTANCE.toDTO(userService.forgotPassword(user));
    }

    @GetMapping("reset/{code}")
    public void activate(@PathVariable String code, @Valid @RequestBody UserDto user, HttpServletResponse response) throws IOException {
        UserMapper.INSTANCE.toDTO(userService.resetPassword(code,user));
    }
}
