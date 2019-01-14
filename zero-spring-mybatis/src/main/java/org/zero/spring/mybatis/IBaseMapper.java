package org.zero.spring.mybatis;

import java.util.List;
import java.util.Map;

/**
 * 
 * 类: BaseMapper <br>
 * 描述: 数据库访问接口基类 <br>
 * 作者: zhy<br>
 * 时间: 2016年7月27日 上午9:35:41
 * 
 * @param <T>
 */
public interface IBaseMapper<T extends BaseEntity, DTO extends BaseDto> {

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
	int insert(T entity);

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
	int update(T entity);

	/**
	 * 
	 * 方法: update <br>
	 * 描述: 编辑现有对象，dto查询对象是否存在 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月21日 上午11:13:28
	 * 
	 * @param entity
	 * @param dto
	 * @return
	 */
	int updateByDto(T entity, DTO dto);

	/**
	 * 
	 * 方法: updateByMap <br>
	 * 描述: 编辑现有对象，map查询对象是否存在 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月21日 上午11:14:35
	 * 
	 * @param entity
	 * @param map
	 * @return
	 */
	int updateByMap(T entity, Map<String, Object> map);

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
	int delete(String code);

	/**
	 * 
	 * 方法: deleteByDto <br>
	 * 描述: 根据参数删除对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年1月22日 下午1:36:36
	 * 
	 * @param dto
	 * @return
	 */
	int deleteByDto(DTO dto);

	/**
	 * 
	 * 方法: deleteByMap <br>
	 * 描述: 根据map参数删除对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:26:39
	 * 
	 * @param map
	 * @return
	 */
	int deleteByMap(Map<String, Object> map);

	/**
	 * 
	 * 方法: select <br>
	 * 描述: 根据编码查询现有对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午1:38:02
	 * 
	 * @param code
	 * @return
	 */
	T select(String code);

	/**
	 * 
	 * 方法: selectByDto <br>
	 * 描述: 根据对象属性查询对象信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午1:56:19
	 * 
	 * @param dto
	 * @return
	 */
	T selectByDto(DTO dto);

	/**
	 * 
	 * 方法: selectByMap <br>
	 * 描述: 根据map查询对象信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:27:16
	 * 
	 * @param map
	 * @return
	 */
	T selectByMap(Map<String, Object> map);

	/**
	 * 
	 * 方法: selectAll <br>
	 * 描述: 根据对象属性查询所有对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午1:38:35
	 * 
	 * @return
	 */
	List<T> selectAll(DTO dto);

	/**
	 * 
	 * 方法: selectAllByMap <br>
	 * 描述: 根据map查询所有对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:27:54
	 * 
	 * @param map
	 * @return
	 */
	List<T> selectAllByMap(Map<String, Object> map);

	/**
	 * 
	 * 方法: page <br>
	 * 描述: 根据对象属性查询所有对象进行分页 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月21日 下午2:08:48
	 * 
	 * @param dto
	 * @return
	 */
	List<T> page(DTO dto);

	/**
	 * 
	 * 方法: page <br>
	 * 描述: 根据map查询所有对象进行分页 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:28:29
	 * 
	 * @param map
	 * @return
	 */
	List<T> pageByMap(Map<String, Object> map);

	/**
	 * 
	 * 方法: selectAllCount <br>
	 * 描述: 根据对象属性查询所有对象总数 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月21日 下午2:06:52
	 * 
	 * @param dto
	 * @return
	 */
	Long pageTotal(DTO dto);

	/**
	 * 
	 * 方法: pageTotalByMap <br>
	 * 描述: 根据map查询所有对象总数 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年5月23日 上午11:28:57
	 * 
	 * @param map
	 * @return
	 */
	Long pageTotalByMap(Map<String, Object> map);
}
