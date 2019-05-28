package org.zero.spring.jpa;

import zero.commons.basics.result.EntityResult;

public interface IQueryService<T, ID> {

	/**
	 * 
	 * 方法: selectByPrimaryKey <br>
	 * 描述: 根据主键查询对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年5月20日 上午11:33:35
	 * 
	 * @param key
	 * @return
	 */
	EntityResult<T> selectByPrimaryKey(T entity,ID key);

	/**
	 * 
	 * 方法: updateEntityAttributeByPrimaryKey <br>
	 * 描述: 根据主键变更对象属性 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年5月20日 上午11:33:44
	 * 
	 * @param entity
	 * @return
	 */
	EntityResult<T> updateEntityAttributeByPrimaryKey(T entity, ID key);
}
