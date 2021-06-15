package org.vnvika.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vnvika.blog.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
