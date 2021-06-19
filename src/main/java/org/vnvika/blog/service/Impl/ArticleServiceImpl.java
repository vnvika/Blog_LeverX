package org.vnvika.blog.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vnvika.blog.dto.ArticleDto;
import org.vnvika.blog.dto.ArticlePageDto;
import org.vnvika.blog.mapper.ArticleMapper;
import org.vnvika.blog.model.Article;
import org.vnvika.blog.model.Tag;
import org.vnvika.blog.repository.ArticleRepository;
import org.vnvika.blog.repository.TagRepository;
import org.vnvika.blog.repository.UserRepository;
import org.vnvika.blog.service.ArticleService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Override
    public ArticlePageDto getAllPublicArticles(Pageable pageable) {
        final Page<Article> articlePage = articleRepository.findAll(pageable);
        log.info("GetAllPublicArticles - {} articles found", articlePage.getSize());
        return getArticlePageDto(articlePage, pageable);
    }

    @Override
    public ArticlePageDto getAllUserArticles(Pageable pageable) {
        final Page<Article> articlesPage = articleRepository.findByUser_Username(getUsernameOfCurrentUser(), pageable);
        log.info("GetAllUserArticles - {} articles found", articlesPage.getSize());
        return getArticlePageDto(articlesPage, pageable);
    }

    @Override
    @Transactional
    public Article save(ArticleDto articleDto) {
        final Article article = ArticleMapper.INSTANCE.fromDTO(articleDto);
        article.setTags(iterateTags(article));
        article.setUser(userRepository.getByUsername(getUsernameOfCurrentUser()));
        article.setCreatedAt(LocalDate.now());
        article.setUpdatedAt(LocalDate.now());
        log.info("Created article {}", article);
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public Article update(ArticleDto articleDto, Long articleId) {
        final Article article = articleRepository.getById(articleId);
        if (article == null || !article.getUser().getUsername().equals(getUsernameOfCurrentUser())) {
            throw new IllegalArgumentException("Article not found or you don't have access");
        }
        article.setUpdatedAt(LocalDate.now());
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public void delete(Long articleId) {
        final Article article = articleRepository.getById(articleId);
        if (article != null && article.getUser().getUsername().equals(getUsernameOfCurrentUser())) {
            articleRepository.delete(article);
            log.info("Delete completed");
        } else {
            throw new IllegalArgumentException("Article not found or you don't have access");
        }
    }

    private ArticlePageDto getArticlePageDto(Page<Article> articlesPage, Pageable pageable) {
        final List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articlesPage.getContent()) {
            final ArticleDto articleDto = ArticleMapper.INSTANCE.toDTO(article);
            articleDtos.add(articleDto);
        }
        return new ArticlePageDto(articleDtos, pageable.getPageNumber(), articlesPage.getTotalPages());
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

    private Set<Tag> iterateTags(Article article) {
        final Set<Tag> tags = new HashSet<>();
        for (Tag tag : article.getTags()) {
            Tag currentTag = tagRepository.findByName(tag.getName());
            if (currentTag == null) {
                currentTag = tagRepository.save(tag);
            }
            tags.add(currentTag);
        }
        return tags;
    }

}
