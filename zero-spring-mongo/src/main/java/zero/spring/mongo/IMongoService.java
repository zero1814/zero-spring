package zero.spring.mongo;

import java.util.Map;

import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.DataResult;
import zero.commons.basics.result.EntityResult;

/**
 * 
 * 类: MongoService <br>
 * 描述: mongo公共处理接口 <br>
 * 作者: zhy<br>
 * 时间: 2018年12月7日 下午4:48:48
 * 
 * @param <T>
 */
public interface IMongoService<T, ID> {

	/**
	 * 
	 * 方法: save <br>
	 * 描述: 添加对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月7日 下午4:49:01
	 * 
	 * @param entity
	 * @return
	 */
	EntityResult<T> save(T entity);

	/**
	 * 
	 * 方法: update <br>
	 * 描述: 编辑对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月7日 下午4:49:31
	 * 
	 * @param entity
	 * @return
	 */
	EntityResult<T> update(T entity, ID id);

	/**
	 * 
	 * 方法: find <br>
	 * 描述: 根据主键查询对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月7日 下午4:50:11
	 * 
	 * @param id
	 * @return
	 */
	EntityResult<T> find(ID id);

	/**
	 * 
	 * 方法: find <br>
	 * 描述: 根据键值对查询对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月7日 下午4:52:13
	 * 
	 * @param params
	 * @return
	 */
	EntityResult<T> find(Map<String, Object> params);

	/**
	 * 
	 * 方法: findAll <br>
	 * 描述: 根据查询条件查询对象列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月7日 下午4:50:21
	 * 
	 * @param entity
	 * @return
	 */
	DataResult<T> findAll(T entity);

	/**
	 * 
	 * 方法: findAll <br>
	 * 描述: 根据键值对查询对象列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月7日 下午4:52:32
	 * 
	 * @param params
	 * @return
	 */
	DataResult<T> findAll(Map<String, Object> params);

	/**
	 * 
	 * 方法: delete <br>
	 * 描述: 删除对象 <br>
	 * 作者: zhy<br>
	 * 时间: 2018年12月7日 下午4:50:45
	 * 
	 * @param id
	 * @return
	 */
	BaseResult delete(ID id);
}
