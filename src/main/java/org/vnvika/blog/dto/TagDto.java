package org.vnvika.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TagDto {
    private Long id;
    @NotBlank
    private String name;
}
