package org.vnvika.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vnvika.blog.dto.TagDto;
import org.vnvika.blog.service.TagService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/tags-cloud")
    public ResponseEntity<List<TagDto>> getCountArticles() {
        return ResponseEntity.ok(tagService.getCountArticles());
    }

//    @GetMapping("/articles?tags={tag1},{tag2}")
//    public ResponseEntity<TagDto> get(){
//
//    }
}
