package org.vnvika.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vnvika.blog.dto.AuthenticationRequestDto;
import org.vnvika.blog.dto.TokenResponseDto;
import org.vnvika.blog.security.jwt.TokenProvider;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

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
}
