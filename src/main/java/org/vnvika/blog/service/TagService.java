package org.vnvika.blog.service;

import org.vnvika.blog.dto.TagCloudDto;
import org.vnvika.blog.model.Tag;

import java.util.List;

public interface TagService {
    List<TagCloudDto> getCountArticles();
}
