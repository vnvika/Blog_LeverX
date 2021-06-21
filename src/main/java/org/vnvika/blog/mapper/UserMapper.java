package org.vnvika.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.vnvika.blog.dto.UserDto;
import org.vnvika.blog.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    UserDto toDTO(User user);
    User fromDTO(UserDto userDto);
}
