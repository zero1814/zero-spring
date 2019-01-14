package zero.spring.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import zero.commons.basics.ObjectUtil;
import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.DataResult;
import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.ResultType;

@Component
public class MongoServiceImpl<T, ID> implements IMongoService<T, ID> {

	private static Logger logger = LoggerFactory.getLogger(MongoServiceImpl.class);

	@Autowired
	MongoTemplate template;

	@Override
	public EntityResult<T> save(T entity) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			T t = template.save(entity);
			result.setEntity(t);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("添加成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("添加对象报错，错误原因：" + e.getMessage());
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public EntityResult<T> update(T entity, ID id) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Field[] fields = entity.getClass().getFields();
			Update update = new Update();
			for (Field field : fields) {
				String key = field.getName();
				Object value = ObjectUtil.getFieldValueByName(key, entity);
				update.set(key, value);
			}
			Class<T> clazz = getTClass();
			T t = (T) template.updateFirst(query, update, clazz);
			result.setEntity(t);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("编辑成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("编辑对象报错，错误原因：" + e.getMessage());
			return result;
		}
	}

	@Override
	public EntityResult<T> find(ID id) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Class<T> clazz = getTClass();
			T entity = template.findOne(query, clazz);
			result.setEntity(entity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("查询对象成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("查询对象报错，错误原因：" + e.getMessage());
			return result;
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public EntityResult<T> find(Map<String, Object> params) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			Query query = new Query();
			if (params != null) {
				Set<String> keys = params.keySet();
				for (String key : keys) {
					query.query(Criteria.where(key).is(params.get(key)));
				}
				Class<T> clazz = getTClass();
				T entity = template.findOne(query, clazz);
				result.setEntity(entity);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("查询对象成功");
				return result;
			} else {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象参数不能为空");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("查询对象报错，错误原因：" + e.getMessage());
			return result;
		}
	}

	@Override
	public DataResult<T> findAll(T entity) {
		DataResult<T> result = new DataResult<T>();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("查询对象列表报错，错误原因：" + e.getMessage());
			return result;
		}
		return null;
	}

	@Override
	public DataResult<T> findAll(Map<String, Object> params) {
		return null;
	}

	@Override
	public BaseResult delete(ID id) {
		return null;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getTClass() {
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return clazz;
	}
}
