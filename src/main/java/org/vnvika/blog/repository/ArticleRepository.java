package org.vnvika.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.vnvika.blog.model.Article;

import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    Optional<Article> findById(Integer id);
}
