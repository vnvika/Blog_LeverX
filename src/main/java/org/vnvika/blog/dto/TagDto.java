package org.vnvika.blog.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class TagDto {
    private Long id;
    @NotBlank
    private String name;
}
