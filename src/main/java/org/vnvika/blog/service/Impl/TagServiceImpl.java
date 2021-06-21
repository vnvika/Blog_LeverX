package org.vnvika.blog.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vnvika.blog.dto.TagDto;
import org.vnvika.blog.mapper.TagMapper;
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
    public List<TagDto> getCountArticles() {
        List<Tag> tags = tagRepository.findTagCloud();
        List<TagDto> tagDtos = new ArrayList<>();
        for (Tag tag : tags) {
            TagDto tagDto = TagMapper.INSTANCE.toDTO(tag);
            tagDtos.add(tagDto);
        }
        return tagDtos;
    }
}
