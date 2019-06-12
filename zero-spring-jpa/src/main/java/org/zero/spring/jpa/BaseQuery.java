package org.zero.spring.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class BaseQuery<T extends BaseEntity, D, R extends BaseRepository<T, String>> {

	@Autowired
	private R repository;

	public List<T> list(T entity, D dto) {
		ExampleMatcher matcher = JpaUtil.getMatcher(dto);
		Example<T> example = Example.of(entity, matcher);
		List<T> list = repository.findAll(example);
		return list;
	}

}
