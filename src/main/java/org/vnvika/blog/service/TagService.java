package org.vnvika.blog.service;

import org.vnvika.blog.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> getCountArticles();
}
