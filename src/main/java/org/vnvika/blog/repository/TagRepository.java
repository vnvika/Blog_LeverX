package org.vnvika.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vnvika.blog.model.Tag;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
}
