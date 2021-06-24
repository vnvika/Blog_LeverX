package org.vnvika.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vnvika.blog.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String name);
    User getByUsername(String name);
    User findByEmail(String email);
}
