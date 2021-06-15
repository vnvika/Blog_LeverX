package org.vnvika.blog.Repository;

import org.springframework.data.repository.CrudRepository;
import org.vnvika.blog.model.Article;

import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Integer> {
    Optional<Article> findById(Integer id);
}
