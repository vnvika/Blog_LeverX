package org.vnvika.blog.dto;

import javax.validation.constraints.NotBlank;

public class TagDto {
    private Long id;
    @NotBlank
    private String name;
}
