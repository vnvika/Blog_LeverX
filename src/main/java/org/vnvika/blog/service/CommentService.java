package org.vnvika.blog.service;

import org.springframework.data.domain.Pageable;
import org.vnvika.blog.dto.CommentDto;
import org.vnvika.blog.dto.CommentPageDto;
import org.vnvika.blog.model.Comment;

public interface CommentService {
    CommentDto getComment(long article_id, long comment_id);

    CommentPageDto getArticleAllComments(long article_id, Pageable pageable);

    Comment save(long article_id, CommentDto commentDto);

    void delete(long article_id, long comment_id);
}
