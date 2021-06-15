package org.vnvika.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vnvika.blog.Repository.ArticleRepository;
import org.vnvika.blog.model.Article;
import org.vnvika.blog.model.StatusArticle;

import java.util.Map;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Article> articles = articleRepository.findAll();

        model.put("articles", articles);

        return "main";
    }

    @PostMapping
    public String add(@RequestParam String title, @RequestParam String text, Map<String, Object> model) {
        Article article = new Article(title,text, StatusArticle.PUBLIC,0);

        articleRepository.save(article);

        Iterable<Article> articles = articleRepository.findAll();

        model.put("articles", articles);

        return "main";
    }
}
