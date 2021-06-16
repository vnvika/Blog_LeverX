package org.vnvika.blog.dto;

import lombok.Data;
import org.vnvika.blog.model.StatusArticle;
import org.vnvika.blog.model.User;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class ArticleDto {
    private final Integer id;
    private final String title;
    private final String text;
    private final StatusArticle status;
    private final Long userId;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;
}
