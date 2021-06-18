package org.vnvika.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vnvika.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
