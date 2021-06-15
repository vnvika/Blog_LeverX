package org.vnvika.blog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDto {
    private final String username;
    private final String token;
}
