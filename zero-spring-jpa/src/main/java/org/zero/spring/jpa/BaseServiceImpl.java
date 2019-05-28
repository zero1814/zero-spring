package org.zero.spring.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
			completionFieldValue(entity, OperationType.Insert);
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
	@Transactional
	public EntityResult<T> update(T entity, ID id) {
		log.info("请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			if (id == null || StringUtils.isBlank(id.toString())) {
				result.setCode(ResultType.ERROR);
				result.setMessage("主键编码为空");
				return result;
			}
			T selEntity = repository.findById(id).get();
			if (selEntity != null) {
				completionFieldValue(entity, OperationType.Update);
				T t = repository.save(entity);
				repository.flush();
				result.setEntity(t);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("编辑成功");
			} else {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
			}
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行编辑方法报错，错误原因：\n" + e.getMessage());
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

	private void completionFieldValue(T entity, OperationType type) {
		List<Field> fields = getFields(entity.getClass());
		String user = null;
		Date time = null;
		if (type == OperationType.Insert) {
			if (ObjectUtil.isExistsFiled("createUser", entity)) {
				user = (String) ObjectUtil.getFieldValueByName("createUser", entity);
			}
			if (ObjectUtil.isExistsFiled("createTime", entity)) {
				if (ObjectUtil.getFieldValueByName("createTime", entity) != null) {
					time = (Date) ObjectUtil.getFieldValueByName("createTime", entity);
				} else {
					time = new Date();
				}
			}
		} else if (type == OperationType.Update) {
			if (ObjectUtil.isExistsFiled("updateUser", entity)) {
				user = (String) ObjectUtil.getFieldValueByName("updateUser", entity);
			}
			if (ObjectUtil.isExistsFiled("updateTime", entity)) {
				if (ObjectUtil.getFieldValueByName("updateTime", entity) != null) {
					time = (Date) ObjectUtil.getFieldValueByName("updateTime", entity);
				} else {
					time = new Date();
				}

			}
		}
		try {
			for (Field f : fields) {
				if (StringUtils.equalsAny(f.getName(), "serialVersionUID")) {
					continue;
				}
				Object obj = ObjectUtil.getFieldValueByName(f.getName(), entity);
				if (type == OperationType.Insert) {
					String name = f.getName();
					if (StringUtils.equals(name, "uid")) {
						f.setAccessible(true);
						f.set(entity, CodeHelper.getUUID());
					} else if (StringUtils.equals(f.getName(), "code")) {
						f.setAccessible(true);
						f.set(entity, CodeHelper.getCode(entity.getClass()));
					} else if (StringUtils.equals(f.getName(), "updateUser")) {
						f.setAccessible(true);
						f.set(entity, user);
					} else if (StringUtils.equals(f.getName(), "createTime")
							|| StringUtils.equals(f.getName(), "updateTime")) {
						f.setAccessible(true);
						f.set(entity, time);
					}
				} else if (type == OperationType.Update) {
					if (StringUtils.equals(f.getName(), "updateTime")) {
						f.setAccessible(true);
						f.set(entity, time);
					}
				}
				if (obj == null) {
					continue;
				}
				// 是否存在一对多的关系
				boolean flag = f.isAnnotationPresent(OneToMany.class);
				// 是否存在多对一
				boolean flag2 = f.isAnnotationPresent(ManyToOne.class);
				if (flag) {
					// 是否继承BaseEntity
					boolean isListExtends = List.class.isAssignableFrom(obj.getClass());
					if (isListExtends) {
						List<?> list = (List<?>) obj;
						for (Object object : list) {
							boolean isEntityExtends = BaseEntity.class.isAssignableFrom(object.getClass());
							if (isEntityExtends) {
								List<Field> _fields = getFields(object.getClass());
								for (Field field : _fields) {
									if (StringUtils.equals("serialVersionUID", field.getName())) {
										continue;
									}
									field.setAccessible(true);
									Object val = field.get(object);
									if (val != null) {
										continue;
									}
									String name = field.getName();
									if (StringUtils.equals(name, "uid")) {
										field.set(object, CodeHelper.getUUID());
									} else if (StringUtils.equals(field.getName(), "code")) {
										field.set(object, CodeHelper.getCode(entity.getClass()));
									} else if (StringUtils.equals(field.getName(), "createUser")
											|| StringUtils.equals(field.getName(), "updateUser")) {
										field.set(object, user);
									} else if (StringUtils.equals(field.getName(), "createTime")
											|| StringUtils.equals(field.getName(), "updateTime")) {
										field.set(object, time);
									}
								}
							}
						}
					} else if (flag2) {
						boolean isEntityExtends = BaseEntity.class.isAssignableFrom(entity.getClass());
						if (isEntityExtends) {
							Object object = ObjectUtil.getFieldValueByName(f.getName(), entity);

							List<Field> _fields = getFields(object.getClass());
							for (Field field : _fields) {
								if (StringUtils.equals("serialVersionUID", field.getName())) {
									continue;
								}
								field.setAccessible(true);
								Object val = field.get(object);
								if (val != null) {
									continue;
								}
								String name = field.getName();
								if (StringUtils.equals(name, "uid")) {
									field.set(object, CodeHelper.getUUID());
								} else if (StringUtils.equals(field.getName(), "code")) {
									field.set(object, CodeHelper.getCode(entity.getClass()));
								} else if (StringUtils.equals(field.getName(), "createUser")
										|| StringUtils.equals(field.getName(), "updateUser")) {
									field.set(object, user);
								} else if (StringUtils.equals(field.getName(), "createTime")
										|| StringUtils.equals(field.getName(), "updateTime")) {
									field.set(object, time);
								}
							}
						}
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
}
