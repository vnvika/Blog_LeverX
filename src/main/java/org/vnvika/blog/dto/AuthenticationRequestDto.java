package org.vnvika.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDto {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
}
