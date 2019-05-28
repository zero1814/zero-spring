package org.zero.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiOperation;
import zero.commons.basics.ObjectUtil;
import zero.commons.basics.result.BaseResult;
import zero.commons.basics.result.EntityResult;
import zero.commons.basics.result.PageResult;
import zero.commons.basics.result.WebResult;

public class BaseController<T extends BaseEntity, S extends IBaseService<T, String>> {

	@Autowired
	private S service;

	@ApiOperation("添加数据")
	@PostMapping(value = "create", consumes = "application/json")
	public WebResult create(@RequestBody T entity) {
		BaseResult result = service.create(entity);
		return WebResult.result(result);
	}

	@ApiOperation("编辑现有数据")
	@PostMapping(value = "update", consumes = "application/json")
	public WebResult update(@RequestBody T entity) {
		String code = (String) ObjectUtil.getFieldValueByName("code", entity);
		BaseResult result = service.update(entity, code);
		return WebResult.result(result);
	}

	@ApiOperation("根据编码查询数据信息")
	@GetMapping("get/{code}")
	public WebResult select(@PathVariable("code") String code) {
		EntityResult<T> result = service.select(code);
		return WebResult.obj(result);
	}

	@ApiOperation("根据编码删除数据信息")
	@GetMapping("del/{code}")
	public WebResult delete(@PathVariable("code") String code) {
		BaseResult result = service.delete(code);
		return WebResult.result(result);
	}

	@ApiOperation("分页显示数据列表")
	@PostMapping(value = "page", consumes = "application/json")
	public WebResult page(@RequestBody T entity) {
		PageResult<T> result = service.page(entity);
		return WebResult.page(result);
	}

}
