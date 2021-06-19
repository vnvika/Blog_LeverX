package org.vnvika.blog.service;

import org.vnvika.blog.dto.UserDto;
import org.vnvika.blog.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User findByUsername(String name);

    User register(UserDto userDto);

    User activateUser(String code);

    User findById(Long id);

    void delete(Long id);
}
