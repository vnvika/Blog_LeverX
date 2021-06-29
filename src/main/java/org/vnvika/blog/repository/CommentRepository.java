package org.vnvika.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vnvika.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from comment where id = ?1 and article_id = ?2", nativeQuery = true)
    Comment findByIdAndArticleId(long comment_id, long article_id);

    @Query(value = "select * from comment where article_id = ?1", nativeQuery = true)
    Page<Comment> findByArticleId(long article_id, Pageable pageable);

    Comment getByIdAndArticleId(long comment_id, long article_id);
}
