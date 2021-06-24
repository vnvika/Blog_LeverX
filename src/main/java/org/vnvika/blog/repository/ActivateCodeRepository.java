package org.vnvika.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vnvika.blog.model.ActivateCode;

@Repository
public interface ActivateCodeRepository extends CrudRepository<ActivateCode, String> {
}
