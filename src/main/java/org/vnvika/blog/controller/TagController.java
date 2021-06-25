package org.vnvika.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vnvika.blog.dto.ArticlePageDto;
import org.vnvika.blog.dto.TagCloudDto;
import org.vnvika.blog.dto.TagDto;
import org.vnvika.blog.service.TagService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/tags-cloud")
    public ResponseEntity<List<TagCloudDto>> getCountArticles() {
        return ResponseEntity.ok(tagService.getCountArticles());
    }

    @GetMapping("/articles")
    public ResponseEntity<ArticlePageDto> getArticleByTags(@RequestParam(value = "tags") List<String> tags, Pageable pageable) {
        if(tags.size() != 2){
            throw new IllegalArgumentException("Inappropriate number or type of arguments");
        }
        return ResponseEntity.ok(tagService.getArticlesByTags(tags, pageable));
    }
}
