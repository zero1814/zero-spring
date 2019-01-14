package org.zero.spring.mybatis;

import java.util.Map;

import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.DataResult;
import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.PageResult;

/**
 * 
 * 类: IBaseService <br>
 * 描述: 业务逻辑处理接口基类 <br>
 * 作者: zhy<br>
 * 时间: 2016年7月27日 上午9:41:19
 */
public interface IBaseService<T extends BaseEntity, DTO extends BaseDto> {

	/**
	 * 
	 * 方法: insert <br>
	 * 描述: 添加新的对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午1:37:28
	 * 
	 * @param entity
	 * @return
	 */
	BaseResult insert(T entity);

	/**
	 * 
	 * 方法: insert <br>
	 * 描述: 根据查询条件确认是否存在对象，添加对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年2月28日 上午9:23:59
	 * 
	 * @param entity
	 * @param dto
	 * @return
	 */
	BaseResult insert(T entity, DTO dto);

	/**
	 * 
	 * 方法: update <br>
	 * 描述: 编辑现有对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午1:37:38
	 * 
	 * @param entity
	 * @return
	 */
	BaseResult update(T entity);

	/**
	 * 
	 * 方法: update <br>
	 * 描述: 编辑现有对象，dto查询对象是否存在 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年1月24日 下午2:41:14
	 * 
	 * @param entiyt
	 * @param dto
	 * @return
	 */
	BaseResult update(T entity, DTO dto);

	/**
	 * 
	 * 方法: update <br>
	 * 描述: 编辑现有对象，map查询对象是否存在 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年1月24日 下午2:41:14
	 * 
	 * @param entiyt
	 * @param dto
	 * @return
	 */
	BaseResult update(T entity, Map<String, Object> map);

	/**
	 * 
	 * 方法: delete <br>
	 * 描述: 根据编码删除现有对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午8:18:48
	 * 
	 * @param code
	 * @return
	 */
	BaseResult delete(String code);

	/**
	 * 
	 * 方法: delete <br>
	 * 描述: 根据参数删除数据 <br>
	 * 作者: zhy<br>
	 * 时间: 2017年1月22日 下午3:49:56
	 * 
	 * @param codes
	 * @return
	 */
	BaseResult delete(DTO dto);

	/**
	 * 
	 * 方法: delete <br>
	 * 描述:根据map删除数据 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:30:07
	 * 
	 * @param map
	 * @return
	 */
	BaseResult delete(Map<String, Object> map);

	/**
	 * 
	 * 方法: select <br>
	 * 描述: 根据主键查询现有对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午1:38:02
	 * 
	 * @param pk
	 * @return
	 */
	EntityResult<T> select(String code);

	/**
	 * 
	 * 方法: select <br>
	 * 描述: 根据对象属性查询对象信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午1:56:19
	 * 
	 * @param dto
	 * @return
	 */
	EntityResult<T> select(DTO dto);

	/**
	 * 
	 * 方法: select <br>
	 * 描述: 根据对象属性查询对象信息 map参数 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:24:35
	 * 
	 * @param map
	 * @return
	 */
	EntityResult<T> select(Map<String, Object> map);

	/**
	 * 
	 * 方法: selectAll <br>
	 * 描述: 查询所有数据 <br>
	 * 作者: zhy<br>
	 * 时间: 2017年10月25日 上午11:29:28
	 * 
	 * @param dto
	 * @return
	 */
	DataResult<T> selectAll(DTO dto);

	/**
	 * 
	 * 方法: selectAll <br>
	 * 描述: 查询所有数据 map参数 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:25:07
	 * 
	 * @param map
	 * @return
	 */
	DataResult<T> selectAll(Map<String, Object> map);

	/**
	 * 
	 * 方法: page <br>
	 * 描述: 分页接口逻辑处理 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月21日 下午2:07:55
	 * 
	 * @param dto
	 * @return
	 */
	PageResult<T> page(DTO dto);

	/**
	 * 
	 * 方法: page <br>
	 * 描述: 分页接口逻辑处理 map传参 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:25:39
	 * 
	 * @param map
	 * @return
	 */
	PageResult<T> page(Map<String, Object> map);
}
