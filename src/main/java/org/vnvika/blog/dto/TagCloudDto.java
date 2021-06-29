package org.vnvika.blog.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class TagCloudDto {
    private final String name;
    private final Long count;
}
