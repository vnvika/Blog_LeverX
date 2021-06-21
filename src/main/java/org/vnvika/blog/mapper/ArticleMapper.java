package org.vnvika.blog.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.vnvika.blog.dto.ArticleDto;
import org.vnvika.blog.model.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDto toDTO(Article article);

    Article fromDTO(ArticleDto articleDto);
}
