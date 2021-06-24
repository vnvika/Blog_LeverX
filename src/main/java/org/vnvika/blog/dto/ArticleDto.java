package org.vnvika.blog.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vnvika.blog.model.StatusArticle;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

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
    private Set<TagDto> tags;
}