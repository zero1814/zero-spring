package org.zero.spring.jpa;

import java.lang.reflect.Field;
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
	@Transactional
	@Override
	public EntityResult<T> create(T entity) {
		log.info("前端请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			entity.setCode(CodeHelper.getCode(entity.getClass()));
			entity.setUid(CodeHelper.getUUID());
			entity.setCreateTime(new Date());
			entity.setUpdateUser(entity.getCreateUser());
			entity.setUpdateTime(entity.getCreateTime());
			completionFieldValue(entity);
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
	public EntityResult<T> update(T entity, ID id) {
		log.info("请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			T selEntity = repository.findById(id).get();
			if (selEntity != null) {
				entity.setUpdateTime(new Date());
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
	public PageResult<T> page(T entity) {
		log.info("请求参数：" + JSON.toJSONString(entity));
		PageResult<T> result = new PageResult<T>();
		try {
			T _obj = (T) ObjectUtil.newObject(entity);
			ExampleMatcher matcher = JpaUtil.getMatcher(_obj);
			Pageable request = PageRequest.of(entity.getPage() - 1, entity.getSize());
			Page<T> page = repository.findAll(Example.of(_obj, matcher), request);
			if (page == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询分页数据为空");
				return result;
			}
			result.setCode(ResultType.SUCCESS);
			result.setData(page.getContent());
			result.setTotal(page.getTotalElements());
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

	@SuppressWarnings("rawtypes")
	private void completionFieldValue(T entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		try {
			for (Field f : fields) {
				if (StringUtils.equalsAny(f.getName(), "serialVersionUID")) {
					continue;
				}
				Object obj = ObjectUtil.getFieldValueByName(f.getName(), entity);
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
								Class superClazz = object.getClass().getSuperclass();
								Field[] _fields = superClazz.getDeclaredFields();
								for (Field field : _fields) {
									if (StringUtils.equals("serialVersionUID", field.getName())) {
										continue;
									}
									Object val = ObjectUtil.getFieldValueByName(field.getName(), superClazz);
									String name = field.getName();
									if (StringUtils.equals(field.getName(), "uid")) {
										if (val == null) {
											ObjectUtil.setFieldValueByName(name, CodeHelper.getUUID(), object);
										}
									} else if (StringUtils.equals(field.getName(), "code")) {
										if (val == null) {
											ObjectUtil.setFieldValueByName(name, CodeHelper.getCode(entity.getClass()),
													object);
										}
									} else if (StringUtils.equals(field.getName(), "createUser")
											|| StringUtils.equals(field.getName(), "updateUser")) {
										ObjectUtil.setFieldValueByName(name, entity.getCreateUser(), object);

									} else if (StringUtils.equals(field.getName(), "createTime")
											|| StringUtils.equals(field.getName(), "updateTime")) {
										ObjectUtil.setFieldValueByName(name, entity.getCreateTime(), object);
									}
								}
							}
						}
					} else if (flag2) {
						boolean isEntityExtends = BaseEntity.class.isAssignableFrom(entity.getClass());
						if (isEntityExtends) {
							Object object = ObjectUtil.getFieldValueByName(f.getName(), entity);
							Class superClazz = object.getClass().getSuperclass();
							Field[] _fields = superClazz.getDeclaredFields();
							for (Field field : _fields) {
								if (StringUtils.equals("serialVersionUID", field.getName())) {
									continue;
								}
								Object val = ObjectUtil.getFieldValueByName(field.getName(), superClazz);
								String name = field.getName();
								if (StringUtils.equals(field.getName(), "uid")) {
									if (val == null) {
										ObjectUtil.setFieldValueByName(name, CodeHelper.getUUID(), object);
									}
								} else if (StringUtils.equals(field.getName(), "code")) {
									if (val == null) {
										ObjectUtil.setFieldValueByName(name, CodeHelper.getCode(entity.getClass()),
												object);
									}
								} else if (StringUtils.equals(field.getName(), "createUser")
										|| StringUtils.equals(field.getName(), "updateUser")) {
									ObjectUtil.setFieldValueByName(name, entity.getCreateUser(), object);

								} else if (StringUtils.equals(field.getName(), "createTime")
										|| StringUtils.equals(field.getName(), "updateTime")) {
									ObjectUtil.setFieldValueByName(name, entity.getCreateTime(), object);
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
}
