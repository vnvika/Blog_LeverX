package org.vnvika.blog.service;

import org.vnvika.blog.dto.UserDto;
import org.vnvika.blog.model.User;

public interface UserService {


    User findByUsername(String name);

    User register(UserDto userDto);

    void activateUser(String code);

    User forgotPassword(UserDto userDto);

    User resetPassword(String code, UserDto userDto);

}
