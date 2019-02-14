package org.zero.spring.mybatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface BaseMapperAnnotation<T> {

	@InsertProvider(type = BaseProvider.class, method = "insert")
	int insert(T entity);

	@UpdateProvider(type = BaseProvider.class, method = "update")
	int update(T entity);

	@SelectProvider(type = BaseProvider.class, method = "selectByCode")
	@Results(id = "baseResult", value = { @Result(property = "uid", column = "uid") })
	T selectByCode(String code);

	@SelectProvider(type = BaseProvider.class, method = "select")
	T select(T entity);

	@DeleteProvider(type = BaseProvider.class, method = "deleteByCode")
	int deleteByCode(String code);

	@DeleteProvider(type = BaseProvider.class, method = "delete")
	int delete(T entity);

}
