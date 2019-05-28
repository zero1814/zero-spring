package org.zero.spring.mybatis;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 类: BaseDto <br>
 * 描述: 页面参数基类 <br>
 * 作者: zhy<br>
 * 时间: 2018年12月21日 上午11:09:16
 */
@Getter
@Setter
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
}
