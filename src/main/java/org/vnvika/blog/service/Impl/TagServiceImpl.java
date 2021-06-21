package org.vnvika.blog.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vnvika.blog.dto.TagCloudDto;
import org.vnvika.blog.dto.TagDto;
import org.vnvika.blog.dto.TokenResponseDto;
import org.vnvika.blog.model.Tag;
import org.vnvika.blog.repository.TagRepository;
import org.vnvika.blog.service.TagService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<TagCloudDto> getCountArticles() {
        List<Tag> tags = tagRepository.findAll();
        List<TagCloudDto> tagCloudDtos = new ArrayList<>();

        return null;
    }
}
