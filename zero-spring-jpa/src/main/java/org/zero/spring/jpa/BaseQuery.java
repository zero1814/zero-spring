package org.zero.spring.jpa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import zero.commons.basics.ObjectUtil;

public class BaseQuery<T extends BaseEntity, D, R extends BaseRepository<T, String>> {

	@Autowired
	private R repository;

	public List<T> list(T entity, D dto) {
		ExampleMatcher matcher = ExampleMatcher.matching();
		List<Map<String, Object>> fieldData = ObjectUtil.getFiledsInfo(dto);
		for (Map<String, Object> map : fieldData) {
		}
		Example<T> example = Example.of(entity, matcher);
		List<T> list = repository.findAll(example);
		return list;
	}
}
