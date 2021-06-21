package org.vnvika.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CommentDto {
    @NotBlank
    private String message;
}
