package org.vnvika.blog.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CommentDto {
    private Long id;
    @NotBlank
    private String message;
    private ArticleDto article;
    private LocalDate createdAt;
}
