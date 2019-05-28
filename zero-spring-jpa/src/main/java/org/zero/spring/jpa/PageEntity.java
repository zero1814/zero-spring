package org.zero.spring.jpa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageEntity<T extends BaseEntity> {

	private T entity;
	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 页面显示最大数
	 */
	private int size;
}
