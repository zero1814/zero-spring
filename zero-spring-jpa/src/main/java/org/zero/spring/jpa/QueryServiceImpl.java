package org.zero.spring.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.ResultType;

public class QueryServiceImpl<T, ID> implements IQueryService<T, ID> {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public EntityResult<T> selectByPrimaryKey(T entity, ID key) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			Object obj = entityManager.find(entity.getClass(), key);
			if (obj == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象为空");
				return result;
			}
			result.setCode(ResultType.SUCCESS);
			result.setEntity((T) obj);
			result.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("根据主键查询数据对象报错");
		}
		return result;
	}

	@Override
	public EntityResult<T> updateEntityAttributeByPrimaryKey(T entity, ID key) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			Object obj = entityManager.find(entity.getClass(), key);
			if (obj == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象为空");
				return result;
			}
			T _entity = entityManager.merge(entity);
			result.setCode(ResultType.SUCCESS);
			result.setEntity(_entity);
			result.setMessage("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("更新数据报错");
		}
		return result;
	}

}
