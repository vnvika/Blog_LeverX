package org.vnvika.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vnvika.blog.dto.ArticleDto;
import org.vnvika.blog.dto.CommentDto;
import org.vnvika.blog.dto.CommentPageDto;
import org.vnvika.blog.mapper.CommentMapper;
import org.vnvika.blog.model.Comment;
import org.vnvika.blog.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/articles")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{article_id}/comments/{comment_id}")
    public ResponseEntity<CommentPageDto> getComments(@PathVariable long article_id,@PathVariable long comment_id, Pageable pageable){
        return ResponseEntity.ok(commentService.getComment(article_id,comment_id,pageable));
    }

    @GetMapping("/{article_id}/comments")
    public ResponseEntity<CommentPageDto> getArticleAllComments(@PathVariable long article_id, Pageable pageable){
        return ResponseEntity.ok(commentService.getArticleAllComments(article_id,pageable));
    }

    @PostMapping ("/{article_id}/comments")
    public ResponseEntity<CommentDto> save(@PathVariable long article_id, @Valid @RequestBody CommentDto commentDto){
        Comment comment = commentService.save(article_id,commentDto);
        return ResponseEntity.ok(CommentMapper.INSTANCE.toDTO(comment));
    }

    @DeleteMapping("/{article_id}/comments/{comment_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable long article_id,@PathVariable long comment_id) {
        commentService.delete(article_id,comment_id);
    }
}
