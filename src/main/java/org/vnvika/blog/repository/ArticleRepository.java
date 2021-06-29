package org.vnvika.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vnvika.blog.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long id);

    @Query(value = "select * from article where status = 'PUBLIC'", nativeQuery = true)
    Page<Article> findAll(Pageable pageable);

    Page<Article> findByUser_Username(String username, Pageable page);

    @Query(value = "select * from article a inner join article_tags at on a.id = at.article_id inner join tag t on at.tag_id = t.id where t.name IN :tags", nativeQuery = true)
    Page<Article> findByTags(@Param("tags") List<String> tags, Pageable page);
}
