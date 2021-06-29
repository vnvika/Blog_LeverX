package org.vnvika.blog.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vnvika.blog.dto.ArticlePageDto;
import org.vnvika.blog.dto.TagCloudDto;
import org.vnvika.blog.model.Article;
import org.vnvika.blog.repository.ArticleRepository;
import org.vnvika.blog.repository.TagRepository;
import org.vnvika.blog.service.ArticleService;
import org.vnvika.blog.service.TagService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    @Override
    public List<TagCloudDto> getCountArticles() {
        List<TagCloudDto> tags = tagRepository.findTagCloud();
//        List<TagDto> tagDtos = new ArrayList<>();
//        for (Tag tag : tags) {
//            TagDto tagDto = TagMapper.INSTANCE.toDTO(tag);
//            tagDtos.add(tagDto);
//        }
        return tags;
    }

    @Override
    public ArticlePageDto getArticlesByTags(List<String> tags, Pageable pageable) {
        final Page<Article> articlePage = articleRepository.findByTags(tags, pageable);
        return articleService.getArticlePageDto(articlePage, pageable);
    }

}
