package org.vnvika.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vnvika.blog.dto.TagCloudDto;
import org.vnvika.blog.model.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    @Query(value = "select t.name, count(*) from tag t inner join article_tags at on t.id = at.tag_id group by t.id, at.article_id, at.tag_id",
            nativeQuery = true)
    List<TagCloudDto> findTagCloud();
}
