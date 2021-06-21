package org.vnvika.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.vnvika.blog.dto.CommentDto;
import org.vnvika.blog.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto toDTO(Comment comment);

    Comment fromDTO(CommentDto commentDto);
}
