package org.zero.spring.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 类: BaseEntity <br>
 * 描述: 对象基类 <br>
 * 作者: zhy<br>
 * 时间: 2018年12月5日 上午11:34:53
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "uid", length = 50, unique = true, updatable = false)
	private String uid;

	/**
	 * 当前页
	 */
	@Transient
	private Integer page;

	/**
	 * 页面显示最大数
	 */
	@Transient
	private Integer size;

}
