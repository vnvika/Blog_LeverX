package org.vnvika.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageDto {
    private List<ArticleDto> articles = new ArrayList<>();
    private int currentPage;
    private int totalPage;
}
