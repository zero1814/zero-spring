package org.zero.spring.jpa;

import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.DataResult;
import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.PageResult;

/**
 * 
 * 类: IBaseService <br>
 * 描述: 基于JPA的公共操作service接口 <br>
 * 作者: zhy<br>
 * 时间: 2018年12月5日 下午2:42:02
 * 
 * @param <T>
 * @param <ID>
 */
public interface IBaseService<T extends BaseEntity, ID> {

	/**
	 * 
	 * 方法: insert <br>
	 * 描述: 添加对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:41:54
	 * 
	 * @param entity
	 * @return
	 */
	EntityResult<T> insert(T entity);

	/**
	 * 
	 * 方法: update <br>
	 * 描述: 编辑对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:42:22
	 * 
	 * @param entity
	 * @return
	 */
	EntityResult<T> update(T entity);

	/**
	 * 
	 * 方法: select <br>
	 * 描述: 查询对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:42:32
	 * 
	 * @param entity
	 * @return
	 */
	EntityResult<T> select(T entity);

	/**
	 * 
	 * 方法: select <br>
	 * 描述: 根据主键ID查询对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:42:43
	 * 
	 * @param id
	 * @return
	 */
	EntityResult<T> select(ID id);

	/**
	 * 
	 * 方法: delete <br>
	 * 描述: 删除对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:42:56
	 * 
	 * @param entity
	 * @return
	 */
	BaseResult delete(T entity);

	/**
	 * 
	 * 方法: delete <br>
	 * 描述: 根据主键ID删除对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:43:09
	 * 
	 * @param id
	 * @return
	 */
	BaseResult delete(ID id);

	/**
	 * 
	 * 方法: selectAll <br>
	 * 描述: 查询所有对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:43:21
	 * 
	 * @param entity
	 * @return
	 */
	DataResult<T> selectAll(T entity);

	/**
	 * 
	 * 方法: page <br>
	 * 描述: 分页数据 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月5日 下午2:46:50
	 * 
	 * @param entity
	 * @return
	 */
	PageResult<T> page(T entity);
}
