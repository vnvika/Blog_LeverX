package org.vnvika.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vnvika.blog.dto.ArticleDto;
import org.vnvika.blog.dto.ArticlePageDto;
import org.vnvika.blog.model.Article;

public interface ArticleService {
    ArticlePageDto getAllPublicArticles(Pageable pageable);
    ArticlePageDto getAllUserArticles(Pageable pageable);
    Article save(ArticleDto articleDto);
    Article update(ArticleDto articleDto, Long articleId);
    void delete(Long articleId);
    ArticlePageDto getArticlePageDto(Page<Article> articlesPage, Pageable pageable);
}
