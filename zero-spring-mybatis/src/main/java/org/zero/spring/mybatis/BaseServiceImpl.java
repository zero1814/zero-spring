package org.zero.spring.mybatis;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zero.commons.basics.DateUtil;
import zero.commons.basics.StringUtils;
import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.DataResult;
import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.PageResult;
import zero.commons.basics.result.ResultType;

/**
 * 
 * 类: BaseServiceImpl <br>
 * 描述: 业务处理接口基类实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年7月27日 上午9:45:43
 * 
 * @param <T>
 * @param <PK>
 * @param <M>
 */
public class BaseServiceImpl<T extends BaseEntity, M extends IBaseMapper<T, DTO>, DTO extends BaseDto>
		implements IBaseService<T, DTO> {

	private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Autowired
	private M mapper;

	/**
	 * 
	 * 方法: insert <br>
	 * 
	 * @param entity
	 * @return
	 * @see org.spring.commons.base.IBaseService#insert(org.spring.commons.base.BaseModel)
	 */
	@Override
	public BaseResult insert(T entity) {
		logger.info("开始执行insert(T entity)方法");
		BaseResult result = new BaseResult();
		try {
			if (StringUtils.isNotBlank(entity.getCode())) {
				T t = mapper.select(entity.getCode());
				if (t != null) {
					result.setCode(ResultType.ERROR);
					result.setMessage("数据已存在，请检查后添加");
					return result;
				}
			}
			if (entity.getUid() == null || "".equals(entity.getUid())) {
				entity.setUid(UUID.randomUUID().toString().replace("-", ""));
			}
			if (entity.getCreateTime() == null || "".equals(entity.getCreateTime())) {
				entity.setCreateTime(DateUtil.curSystemTime());
			}
			entity.setUpdateUser(entity.getCreateUser());
			entity.setUpdateTime(entity.getCreateTime());
			mapper.insert(entity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("执行添加方法成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行insert(T entity)报错");
			logger.error("执行insert(T entity)报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行insert(T entity)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: insert <br>
	 * 
	 * @param entity
	 * @param dto
	 * @return
	 * @see org.spring.commons.base.IBaseService#insert(org.spring.commons.base.BaseModel,
	 *      org.zero.spring.mybatis.spring.commons.base.BaseDto)
	 */
	@Override
	public BaseResult insert(T entity, DTO dto) {
		logger.info("开始执行insert(T entity, DTO dto)方法");
		BaseResult result = new BaseResult();
		if (entity == null || dto == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			if (dto != null) {
				T t = mapper.selectByDto(dto);
				if (t != null) {
					result.setCode(ResultType.ERROR);
					result.setMessage("数据已存在，请检查后添加");
					return result;
				}
			}
			if (entity.getUid() == null || "".equals(entity.getUid())) {
				entity.setUid(UUID.randomUUID().toString().replace("-", ""));
			}
			if (entity.getCreateTime() == null || "".equals(entity.getCreateTime())) {
				entity.setCreateTime(DateUtil.curSystemTime());
			}
			entity.setUpdateUser(entity.getCreateUser());
			entity.setUpdateTime(entity.getCreateTime());
			mapper.insert(entity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("执行添加方法成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行insert(T entity, DTO dto)报错");
			logger.error("执行insert(T entity, DTO dto)报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行insert(T entity, DTO dto)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: update <br>
	 * 
	 * @param entity
	 * @return
	 * @see org.spring.commons.base.IBaseService#update(org.spring.commons.base.BaseModel)
	 */
	@Override
	public BaseResult update(T entity) {
		logger.info("开始执行update(T entity)方法");
		BaseResult result = new BaseResult();
		if (entity == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.select(entity.getCode());
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
				return result;
			}
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(DateUtil.curSystemTime());
			}
			mapper.update(entity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("执行编辑方法成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行update(T entity)报错");
			logger.error("执行update(T entity)报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行update(T entity)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: update <br>
	 * 
	 * @param entity
	 * @param dto
	 * @return
	 * @see org.commons.base.IBaseService#update(org.commons.base.BaseModel,
	 *      org.zero.spring.mybatis.base.BaseDto)
	 */
	@Override
	public BaseResult update(T entity, DTO dto) {
		logger.info("开始执行update(T entity, DTO dto)方法");
		BaseResult result = new BaseResult();
		if (entity == null || dto == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.selectByDto(dto);
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
				return result;
			}
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(DateUtil.curSystemTime());
			}
			mapper.update(entity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("执行编辑方法成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行update(T entity, DTO dto)报错");
			logger.error("执行update(T entity, DTO dto)报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行update(T entity, DTO dto)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: update <br>
	 * 
	 * @param entity
	 * @param map
	 * @return
	 * @see org.zero.spring.mybatis.IBaseService#update(org.zero.spring.mybatis.BaseEntity,
	 *      java.util.Map)
	 */
	@Override
	public BaseResult update(T entity, Map<String, Object> map) {
		logger.info("开始执行update(T entity, DTO dto)方法");
		BaseResult result = new BaseResult();
		if (entity == null || map == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.selectByMap(map);
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
				return result;
			}
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(DateUtil.curSystemTime());
			}
			mapper.update(entity);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("执行编辑方法成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行update(T entity, DTO dto)报错");
			logger.error("执行update(T entity, DTO dto)报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行update(T entity, DTO dto)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: delete <br>
	 * 
	 * @param code
	 * @return
	 * @see org.spring.commons.base.IBaseService#delete(java.lang.String)
	 */
	@Override
	public BaseResult delete(String code) {
		logger.info("开始执行delete(String code)方法");
		BaseResult result = new BaseResult();
		if (StringUtils.isBlank(code)) {
			result.setCode(ResultType.NULL);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.select(code);
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
				return result;
			}
			mapper.delete(code);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("执行delete(String code)方法成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行delete(String code)方法报错");
			logger.error("执行delete(String code)方法报错，报错原因：" + e.getMessage());
		} finally {
			logger.info("执行删除方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: delete <br>
	 * 
	 * @param dto
	 * @return
	 * @see org.spring.commons.base.IBaseService#delete(org.zero.spring.mybatis.spring.commons.base.BaseDto)
	 */
	@Override
	public BaseResult delete(DTO dto) {
		logger.info("开始执行delete(DTO dto)方法");
		BaseResult result = new BaseResult();
		if (dto == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.selectByDto(dto);
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
				return result;
			}
			mapper.deleteByDto(dto);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("执行删除方法成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行delete(DTO dto)方法报错");
			logger.error("执行delete(DTO dto)方法报错，报错原因：" + e.getMessage());
		} finally {
			logger.info("执行delete(DTO dto)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: delete <br>
	 * 
	 * @param map
	 * @return
	 * @see org.spring.commons.base.IBaseService#delete(java.util.Map)
	 */
	@Override
	public BaseResult delete(Map<String, Object> map) {
		BaseResult result = new BaseResult();
		if (map == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T entity = mapper.selectByMap(map);
			if (entity == null) {
				result.setCode(ResultType.ERROR);
				result.setMessage("对象不存在");
				return result;
			}
			mapper.deleteByMap(map);
			result.setCode(ResultType.SUCCESS);
			result.setMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行delete(Map<String, Object> map)方法报错");
			logger.error("执行delete(Map<String, Object> map)方法报错，报错原因：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * 方法: select <br>
	 * 
	 * @param code
	 * @return
	 * @see org.spring.commons.base.IBaseService#select(java.lang.String)
	 */
	@Override
	public EntityResult<T> select(String code) {
		logger.info("开始执行select(String code)方法");
		EntityResult<T> result = new EntityResult<T>();
		if (StringUtils.isBlank(code)) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.select(code);
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
			} else {
				result.setCode(ResultType.SUCCESS);
				result.setEntity(t);
				result.setMessage("查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行select(String code)方法报错");
			logger.error("执行select(String code)方法报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行select(String code)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: select <br>
	 * 
	 * @param dto
	 * @return
	 * @see org.spring.commons.base.IBaseService#select(org.zero.spring.mybatis.spring.commons.base.BaseDto)
	 */
	@Override
	public EntityResult<T> select(DTO dto) {
		logger.info("开始执行select(DTO dto)方法");
		EntityResult<T> result = new EntityResult<T>();
		if (dto == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.selectByDto(dto);
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
			} else {
				result.setEntity(t);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行select(DTO dto)方法报错");
			logger.error("执行select(DTO dto)方法报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行select(DTO dto)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: select <br>
	 * 
	 * @param map
	 * @return
	 * @see org.spring.commons.base.IBaseService#select(java.util.Map)
	 */
	@Override
	public EntityResult<T> select(Map<String, Object> map) {
		logger.info("开始执行select(Map<String, Object> map)方法");
		EntityResult<T> result = new EntityResult<T>();
		if (map == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			T t = mapper.selectByMap(map);
			if (t == null) {
				result.setCode(ResultType.NULL);
				result.setMessage("对象不存在");
			} else {
				result.setEntity(t);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("查询成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResultType.ERROR);
			result.setMessage("执行select(Map<String, Object> map)方法报错");
			logger.error("执行select(Map<String, Object> map)方法报错，错误原因：" + e.getMessage());
		} finally {
			logger.info("执行select(Map<String, Object> map)方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: selectAll <br>
	 * 
	 * @param dto
	 * @return
	 * @see org.spring.commons.base.IBaseService#selectAll(org.zero.spring.mybatis.spring.commons.base.BaseDto)
	 */
	@Override
	public DataResult<T> selectAll(DTO dto) {
		logger.info("开始执行selectAll方法");
		DataResult<T> result = new DataResult<T>();
		try {
			List<T> list = mapper.page(dto);
			if (list.isEmpty()) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询数据为空");
			} else {
				result.setCode(ResultType.SUCCESS);
				result.setData(list);
				result.setMessage("查询数据成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行selectAll方法报错，错误原因：" + e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("执行selectAll方法报错");
		} finally {
			logger.info("执行selectAll方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: selectAll <br>
	 * 
	 * @param map
	 * @return
	 * @see org.spring.commons.base.IBaseService#selectAll(java.util.Map)
	 */
	@Override
	public DataResult<T> selectAll(Map<String, Object> map) {
		logger.info("开始执行selectAll方法");
		DataResult<T> result = new DataResult<T>();
		if (map == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		try {
			List<T> list = mapper.selectAllByMap(map);
			if (list.isEmpty()) {
				result.setCode(ResultType.NULL);
				result.setMessage("查询数据为空");
			} else {
				result.setCode(ResultType.SUCCESS);
				result.setData(list);
				result.setMessage("查询数据成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行selectAll方法报错，错误原因：" + e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("执行selectAll方法报错");
		} finally {
			logger.info("执行selectAll方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: page <br>
	 * 
	 * @param dto
	 * @return
	 * @see org.spring.commons.base.IBaseService#page(org.zero.spring.mybatis.spring.commons.base.BaseDto)
	 */
	@Override
	public PageResult<T> page(DTO dto) {
		PageResult<T> result = new PageResult<T>();
		logger.info("开始执行page方法");
		if (dto == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		if (dto.getPage() == null || dto.getPage() <= 0) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象number应为大于0的正整数");
			return result;
		}
		if (dto.getSize() == null || dto.getSize() <= 0) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象size应为大于0的正整数");
			return result;
		}
		try {
			int offset = (dto.getPage() - 1) * dto.getSize();
			PageInfo<T> page = PageHelper.offsetPage(offset, dto.getSize()).doSelectPageInfo(new ISelect() {

				@Override
				public void doSelect() {
					mapper.page(dto);
				}
			});
			List<T> list = page.getList();
			if (list != null && list.size() > 0) {
				Long total = page.getTotal();
				result.setData(list);
				result.setTotal(total);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("查询数据成功");
			} else {
				result.setCode(ResultType.NULL);
				result.setMessage("查询数据为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行page方法报错，错误原因：" + e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("执行分页方法失败");
		} finally {
			logger.info("执行page方法结束");
		}
		return result;
	}

	/**
	 * 
	 * 方法: page <br>
	 * 
	 * @param map
	 * @return
	 * @see org.spring.commons.base.IBaseService#page(java.util.Map)
	 */
	@Override
	public PageResult<T> page(Map<String, Object> map) {
		PageResult<T> result = new PageResult<T>();
		logger.info("开始执行page方法");
		if (map == null) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象不能为空");
			return result;
		}
		if (map.get("number") == null || Integer.valueOf(map.get("number").toString()) == null
				|| Integer.valueOf(map.get("number").toString()) <= 0) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象number应为大于0的正整数");
			return result;
		}
		if (map.get("size") == null || Integer.valueOf(map.get("size").toString()) == null
				|| Integer.valueOf(map.get("size").toString()) <= 0) {
			result.setCode(ResultType.ERROR);
			result.setMessage("参数对象size应为大于0的正整数");
			return result;
		}
		try {
			int number = Integer.valueOf(map.get("number").toString());
			int size = Integer.valueOf(map.get("size").toString());
			int offset = (number - 1) * size;
			PageInfo<T> page = PageHelper.offsetPage(offset, size).doSelectPageInfo(new ISelect() {
				@Override
				public void doSelect() {
					mapper.pageByMap(map);
				}
			});
			List<T> list = page.getList();
			if (list != null && list.size() > 0) {
				Long total = page.getTotal();
				result.setData(list);
				result.setTotal(total);
				result.setCode(ResultType.SUCCESS);
				result.setMessage("查询数据成功");
			} else {
				result.setCode(ResultType.NULL);
				result.setMessage("查询数据为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行page方法报错，错误原因：" + e.getMessage());
			result.setCode(ResultType.ERROR);
			result.setMessage("执行分页方法失败");
		} finally {
			logger.info("执行page方法结束");
		}
		return result;
	}

}
