package org.vnvika.blog.service;

import org.springframework.data.domain.Pageable;
import org.vnvika.blog.dto.ArticlePageDto;
import org.vnvika.blog.dto.TagCloudDto;
import org.vnvika.blog.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagCloudDto> getCountArticles();
    ArticlePageDto getArticlesByTags(List<String> tags, Pageable pageable);
}
