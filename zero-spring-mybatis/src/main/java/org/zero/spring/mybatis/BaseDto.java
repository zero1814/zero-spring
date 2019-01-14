package org.zero.spring.mybatis;

import java.io.Serializable;

/**
 * 
 * 类: BaseDto <br>
 * 描述: 页面参数基类 <br>
 * 作者: zhy<br>
 * 时间: 2018年12月21日 上午11:09:16
 */
public class BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	/**
	 * 当前页
	 */
	private Integer page;

	/**
	 * 页面显示最大数量
	 */
	private Integer size;

	/**
	 * 数据起始下标
	 */
	private Integer start;

	/**
	 * 数据总量
	 */
	private Integer total;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
