package org.vnvika.blog.service;

import org.vnvika.blog.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUsername(String name);

    User findById(Long id);

    void delete(Long id);
}
