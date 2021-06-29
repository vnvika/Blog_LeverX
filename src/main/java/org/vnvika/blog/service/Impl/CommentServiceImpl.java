package org.vnvika.blog.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vnvika.blog.dto.CommentDto;
import org.vnvika.blog.dto.CommentPageDto;
import org.vnvika.blog.mapper.ArticleMapper;
import org.vnvika.blog.mapper.CommentMapper;
import org.vnvika.blog.model.Article;
import org.vnvika.blog.model.Comment;
import org.vnvika.blog.repository.ArticleRepository;
import org.vnvika.blog.repository.CommentRepository;
import org.vnvika.blog.repository.UserRepository;
import org.vnvika.blog.service.CommentService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto getComment(long article_id, long comment_id) {
        final Comment comment = commentRepository.findByIdAndArticleId(article_id, comment_id);
        log.info("getComment {}", comment);
        return CommentMapper.INSTANCE.toDTO(comment);
    }

    @Override
    public CommentPageDto getArticleAllComments(long article_id, Pageable pageable) {
        final Page<Comment> commentsPage = commentRepository.findByArticleId(article_id, pageable);
        log.info("getArticleAllComments {}", commentsPage.getSize());
        return getCommentPageDto(commentsPage, pageable);
    }

    @Override
    public Comment save(long article_id, CommentDto commentDto) {
        final Comment comment = CommentMapper.INSTANCE.fromDTO(commentDto);
        final Article article = articleRepository.getById(article_id);
        comment.setCreatedAt(LocalDate.now());
        comment.setArticle(article);
        comment.setUser(userRepository.getByUsername(getUsernameOfCurrentUser()));
        log.info("Created comment {}", comment);
        return commentRepository.save(comment);
    }

    @Override
    public void delete(long article_id, long comment_id) {
        final Comment comment = commentRepository.getByIdAndArticleId(comment_id, article_id);
        if (comment != null && comment.getUser().getUsername().equals(getUsernameOfCurrentUser())) {
            commentRepository.deleteById(comment.getId());
            log.info("Delete completed");
        } else {
            throw new IllegalArgumentException("Comment not found or you don't have access");
        }
    }

    private CommentPageDto getCommentPageDto(Page<Comment> commentsPage, Pageable pageable) {
        final List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : commentsPage.getContent()) {
            final CommentDto commentDto = CommentMapper.INSTANCE.toDTO(comment);
            commentDtos.add(commentDto);
        }
        return new CommentPageDto(commentDtos, pageable.getPageNumber(), commentsPage.getTotalPages());
    }

    private String getUsernameOfCurrentUser() {
        final String username;
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
