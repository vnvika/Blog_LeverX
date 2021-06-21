package org.vnvika.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.vnvika.blog.dto.ArticleDto;
import org.vnvika.blog.dto.TagDto;
import org.vnvika.blog.model.Article;
import org.vnvika.blog.model.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toDTO(Tag tag);

    Tag fromDTO(TagDto tagDto);
}
