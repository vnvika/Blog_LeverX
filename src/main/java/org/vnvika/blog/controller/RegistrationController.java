package org.vnvika.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.vnvika.blog.dto.UserDto;
import org.vnvika.blog.mapper.UserMapper;
import org.vnvika.blog.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@Valid @RequestBody UserDto user) {
        UserMapper.INSTANCE.toDTO(userService.register(user));
    }

    @GetMapping("/activate/{code}")
    public void activate(@PathVariable String code, HttpServletResponse response) throws IOException {
        UserMapper.INSTANCE.toDTO(userService.activateUser(code));
        response.sendRedirect("http://localhost:9090/api/auth/login");
    }
}
