package org.zero.spring.jpa;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.alibaba.fastjson.JSON;

import zero.commons.basics.helper.CodeHelper;
import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.DataResult;
import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.PageResult;
import zero.commons.basics.result.ResultType;

public class BaseServiceImpl<T extends BaseEntity, ID, R extends BaseRepository<T, ID>> implements IBaseService<T, ID> {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

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
	public EntityResult<T> insert(T entity) {
		logger.info("请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			entity.setUid(CodeHelper.getUUID());
			entity.setCreateTime(new Date());
			entity.setUpdateUser(entity.getCreateUser());
			entity.setUpdateTime(entity.getCreateTime());
			T t = repository.saveAndFlush(entity);
			result.setEntity(t);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("添加成功");
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), BaseServiceImpl.class);
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
		logger.info("请求参数：" + JSON.toJSONString(entity));
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
			logger.error(e.getMessage(), BaseServiceImpl.class);
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
		logger.info("请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			Example<T> example = Example.of(entity);
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
			logger.error(e.getMessage(), BaseServiceImpl.class);
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
			T selEntity = repository.findById(id).get();
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
			logger.error(e.getMessage(), BaseServiceImpl.class);
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
		logger.info("请求参数：" + JSON.toJSONString(entity));
		EntityResult<T> result = new EntityResult<T>();
		try {
			Example<T> example = Example.of(entity);
			T selEntity = repository.findOne(example).get();
			if (selEntity != null) {
				repository.delete(selEntity);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("编辑成功");
			} else {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), BaseServiceImpl.class);
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
			T selEntity = repository.findById(id).get();
			if (selEntity != null) {
				repository.delete(selEntity);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("编辑成功");
			} else {
				result.setCode(ResultType.NULL);
				result.setMessage("查询对象不存在");
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行删除方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
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
				Example<T> example = Example.of(entity);
				data = repository.findAll(example);
			} else {
				data = repository.findAll(Sort.by(Order.asc("create_time")));
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
			logger.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行查询所有方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
	}

	@Override
	public PageResult<T> page(T entity) {
		logger.info("请求参数：" + JSON.toJSONString(entity));
		PageResult<T> result = new PageResult<T>();
		try {
			Pageable request = PageRequest.of(entity.getPage() - 1, entity.getSize());
			Page<T> page = repository.findAll(request);
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
			logger.error(e.getMessage(), BaseServiceImpl.class);
			result.setCode(ResultType.ERROR);
			result.setMessage("执行查询分页方法报错，错误原因：\n" + e.getMessage());
			return result;
		}
	}
}
