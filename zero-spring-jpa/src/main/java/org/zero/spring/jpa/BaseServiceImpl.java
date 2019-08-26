package org.zero.spring.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;
import org.zero.spring.jpa.annotaion.OperationType;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import zero.commons.basics.ObjectUtil;
import zero.commons.basics.helper.CodeHelper;
import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.DataResult;
import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.PageResult;
import zero.commons.basics.result.ResultType;

@SuppressWarnings("unchecked")
@Slf4j
public class BaseServiceImpl<T extends BaseEntity, ID, R extends BaseRepository<T, ID>> implements IBaseService<T, ID> {

	@Autowired
	private R repository;
	@Autowired
	private EntityManager em;

	/**
	 * 
	 * 方法: insert <br>
	 * 
	 * @param entity
	 * @return
	 * @see org.zero.spring.jpa.IBaseService#insert(org.zero.spring.jpa.BaseEntity)
	 */
	@Override
	@Transactional
	public EntityResult<T> create(T entity) {
		log.info("前端请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			completionEntity(entity, OperationType.Insert);
			log.info("完整请求参数：" + JSON.toJSONString(entity));
			T t = repository.saveAndFlush(entity);
			result.setEntity(t);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("添加成功");
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行添加方法报，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	/**
	 * 
	 * 方法: update <br>
	 * 
	 * @param entity
	 * @return
	 * @see org.zero.spring.jpa.IBaseService#update(org.zero.spring.jpa.BaseEntity)
	 */
	@Override
	public EntityResult<T> update(T entity) {
		log.info("前端请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			completionEntity(entity, OperationType.Update);
			log.info("完整请求参数：" + JSON.toJSONString(entity));
			T t = repository.saveAndFlush(entity);
			result.setEntity(t);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("编辑成功");
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行编辑方法报，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	/**
	 * 
	 * 方法: select <br>
	 * 
	 * @param entity
	 * @return
	 * @see org.zero.spring.jpa.IBaseService#select(org.zero.spring.jpa.BaseEntity)
	 */
	@Override
	@Transactional(readOnly = true)
	public EntityResult<T> select(T entity) {
		log.info("请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			Example<T> example = Example.of(entity);
			if (!repository.findOne(example).isPresent()) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
				return result;
			}
			T selEntity = repository.findOne(example).get();
			if (selEntity == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
				return result;
			}
			result.setEntity(selEntity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("查询成功");
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行查询方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	/**
	 * 
	 * 方法: select <br>
	 * 
	 * @param id
	 * @return
	 * @see org.zero.spring.jpa.IBaseService#select(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly = true)
	public EntityResult<T> select(ID id) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			if (!repository.findById(id).isPresent()) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
				return result;
			}
			T selEntity = repository.findById(id).get();
			result.setEntity(selEntity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("查询成功");
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行查询方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	/**
	 * 
	 * 方法: delete <br>
	 * 
	 * @param entity
	 * @return
	 * @see org.zero.spring.jpa.IBaseService#delete(org.zero.spring.jpa.BaseEntity)
	 */
	@Override
	@Transactional
	public BaseResult delete(T entity) {
		log.info("请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			Example<T> example = Example.of(entity);
			if (!repository.findOne(example).isPresent()) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
				return result;
			}
			T selEntity = repository.findOne(example).get();
			repository.delete(selEntity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("删除成功");
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行删除方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	/**
	 * 
	 * 方法: delete <br>
	 * 
	 * @param id
	 * @return
	 * @see org.zero.spring.jpa.IBaseService#delete(java.lang.Object)
	 */
	@Override
	@Transactional
	public BaseResult delete(ID id) {
		EntityResult<T> result = new EntityResult<T>();
		try {
			if (!repository.findById(id).isPresent()) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
				return result;
			}
			T selEntity = repository.findById(id).get();
			repository.delete(selEntity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("删除成功");
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行删除方法报错，错误原因：\n" + e.getMessage());

		}
		return result;
	}

	/**
	 * 
	 * 方法: selectAll <br>
	 * 
	 * @param entity
	 * @return
	 * @see org.zero.spring.jpa.IBaseService#selectAll(org.zero.spring.jpa.BaseEntity)
	 */
	@Override
	@Transactional(readOnly = true)
	public DataResult<T> selectAll(T entity) {
		DataResult<T> result = new DataResult<T>();
		try {
			List<T> data = null;
			if (entity != null) {
				T _obj = (T) ObjectUtil.newObject(entity);
				ExampleMatcher matcher = JpaUtil.getMatcher(_obj);
				Example<T> example = Example.of(entity, matcher);
				data = repository.findAll(example, Sort.by(Order.asc("createTime")));
			} else {
				data = repository.findAll(Sort.by(Order.asc("createTime")));
			}
			if (data != null && data.size() > 0) {
				result.setData(data);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("查询所有成功");
			} else {
				result.setCode(ResultType.NULL);
				result.setMessage("查询数据为空");
			}
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行查询所有方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<T> page(T entity) {
		log.info("请求参数：" + JSON.toJSONString(entity));
		PageResult<T> result = new PageResult<T>();
		try {
			T _obj = (T) ObjectUtil.newObject(entity);
			System.out.println("整合后查询条件\n" + JSON.toJSONString(_obj));
			ExampleMatcher matcher = JpaUtil.getMatcher(_obj);
			Pageable request = PageRequest.of(entity.getPage() - 1, entity.getSize());
			Page<T> _page = repository.findAll(Example.of(_obj, matcher), request);
			if (_page == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询分页数据为空");
				return result;
			}
			result.setCode(ResultType.SUCCESS);
			result.setData(_page.getContent());
			result.setTotal(_page.getTotalElements());
			result.setMessage("查询分页数据成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行查询分页方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	protected void completionEntity(Object entity, OperationType type) {
		try {
			// 获取所有属性
			List<Field> fields = getFields(entity.getClass());
			completionObject(entity, type, fields);
			for (Field field : fields) {
				if (StringUtils.equalsAny(field.getName(), "serialVersionUID")) {
					continue;
				}
				Object object = ObjectUtil.getFieldValueByName(field.getName(), entity);
				if(object == null) {
					continue;
				}
				if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class)) {
					if (List.class.isAssignableFrom(object.getClass())) {
						List<?> data = (List<?>) object;
						for (Object obj : data) {
							completionObject(obj, type, getFields(obj.getClass()));
						}
					} else if (Set.class.isAssignableFrom(object.getClass())) {
						Set<?> data = (Set<?>) object;
						for (Object obj : data) {
							completionObject(obj, type, getFields(obj.getClass()));
						}
					}
				} else if (field.isAnnotationPresent(ManyToOne.class)) {
					boolean isEntityExtends = BaseEntity.class.isAssignableFrom(object.getClass());
					if (isEntityExtends) {
						completionObject(object, type, getFields(object.getClass()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Field> getFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		while (clazz != null) {
			list.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		return list;
	}

	protected boolean isExistsEntityById(T entity) {
		try {
			List<Field> list = ObjectUtil.getFields(entity.getClass());
			String primaryKeyName = "";
			for (Field field : list) {
				boolean isPrimary = field.isAnnotationPresent(Id.class);
				if (isPrimary) {
					primaryKeyName = field.getName();
					break;
				}
			}
			Object value = ObjectUtil.getFieldValueByName(primaryKeyName, entity);
			Query query = em.createQuery("SELECT COUNT(1) FROM " + entity.getClass().getName() + " where "
					+ primaryKeyName + "='" + value + "'");
			Object result = query.getSingleResult();
			if (result != null && Integer.valueOf(result.toString()) > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * 方法: getPrimaryKeyColumnName <br>
	 * 描述: 获取主键字段名称 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年6月21日 下午3:25:03
	 * 
	 * @param entity
	 * @return
	 */
	protected String getPrimaryKeyColumnName(T entity) {
		List<Field> list = ObjectUtil.getFields(entity.getClass());
		String primaryKeyName = "";
		for (Field field : list) {
			boolean isPrimary = field.isAnnotationPresent(Id.class);
			if (isPrimary) {
				primaryKeyName = field.getName();
				break;
			}
		}
		return primaryKeyName;
	}

	private void completionObject(Object object, OperationType type, List<Field> fields)
			throws IllegalArgumentException, IllegalAccessException {
		// 获取所有属性
		String user = null;
		Date time = null;
		if (type == OperationType.Insert) {
			// 添加操作，取值
			if (ObjectUtil.isExistsFiled("createUser", object)) {
				user = (String) ObjectUtil.getFieldValueByName("createUser", object);
			}
			if (ObjectUtil.isExistsFiled("createTime", object)) {
				if (ObjectUtil.getFieldValueByName("createTime", object) != null) {
					time = (Date) ObjectUtil.getFieldValueByName("createTime", object);
				} else {
					time = new Date();
				}
			}
			if (ObjectUtil.isExistsFiled("updateTime", object)) {
				if (ObjectUtil.getFieldValueByName("updateTime", object) != null) {
					time = (Date) ObjectUtil.getFieldValueByName("updateTime", object);
				} else {
					time = new Date();
				}
			}
		} else if (type == OperationType.Update) {
			// 修改操作取值
			if (ObjectUtil.isExistsFiled("updateUser", object)) {
				user = (String) ObjectUtil.getFieldValueByName("updateUser", object);
			}
			if (ObjectUtil.isExistsFiled("updateTime", object)) {
				if (ObjectUtil.getFieldValueByName("updateTime", object) != null) {
					time = (Date) ObjectUtil.getFieldValueByName("updateTime", object);
				} else {
					time = new Date();
				}
			}
		}
		// 属性循环
		for (Field f : fields) {
			if (StringUtils.equalsAny(f.getName(), "serialVersionUID")) {
				continue;
			}
			Object obj = ObjectUtil.getFieldValueByName(f.getName(), object);
			// 属性赋值
			if (type == OperationType.Insert) {
				// 添加操作，默认赋值 uid,code,createUser,creatTime,updateUser =creatUser,updateTime =
				// createTime
				String name = f.getName();
				if (StringUtils.equals(name, "uid") && obj == null) {
					f.setAccessible(true);
					f.set(object, CodeHelper.getUUID());
				} else if (StringUtils.equals(f.getName(), "code") && obj == null) {
					f.setAccessible(true);
					f.set(object, CodeHelper.getCode(object.getClass()));
				} else if (StringUtils.equals(f.getName(), "updateUser")) {
					f.setAccessible(true);
					f.set(object, user);
				} else if (StringUtils.equals(f.getName(), "createTime")
						|| StringUtils.equals(f.getName(), "updateTime")) {
					f.setAccessible(true);
					f.set(object, time);
				}
			} else if (type == OperationType.Update) {
				// 修改操作赋值 updateUser,updateTime
				if (StringUtils.equals(f.getName(), "updateTime")) {
					f.setAccessible(true);
					f.set(object, time);
				}
			}
		}
	}
}
