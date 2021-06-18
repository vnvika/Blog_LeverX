package org.vnvika.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.vnvika.blog.model.StatusArticle;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleDto {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private StatusArticle status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}