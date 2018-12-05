package org.zero.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

}
