package org.vnvika.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vnvika.blog.dto.ArticleDto;
import org.vnvika.blog.dto.ArticlePageDto;
import org.vnvika.blog.mapper.ArticleMapper;
import org.vnvika.blog.model.Article;
import org.vnvika.blog.service.ArticleService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/articles/all")
    public ResponseEntity<ArticlePageDto> getAllPublicArticles(Pageable pageable) {
        return ResponseEntity.ok(articleService.getAllPublicArticles(pageable));
    }

    @GetMapping("/articles/my")
    public ResponseEntity<ArticlePageDto> getAllUserArticles(Pageable pageable) {
        return ResponseEntity.ok(articleService.getAllUserArticles(pageable));
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleDto> save(@Valid @RequestBody ArticleDto articleDto) {
        Article article = articleService.save(articleDto);
        return ResponseEntity.ok(ArticleMapper.INSTANCE.toDTO(article));
    }

    @PutMapping("/articles/{articleId}")
    public ResponseEntity<ArticleDto> update(@Valid @RequestBody ArticleDto articleDto, @PathVariable Long articleId) {
        Article article = articleService.update(articleDto, articleId);
        return ResponseEntity.ok(ArticleMapper.INSTANCE.toDTO(article));
    }

    @DeleteMapping("/articles/{articleId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable long articleId) {
        articleService.delete(articleId);
    }
}
