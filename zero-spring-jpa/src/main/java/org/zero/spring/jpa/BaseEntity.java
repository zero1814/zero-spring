package org.zero.spring.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

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
	@JSONField(serialize=false)
	private String uid;

	@Id
	@Column(name = "code", length = 50, unique = true, updatable = false)
	@JSONField(serialize=false)
	private String code;

	@Column(name = "create_user", length = 50, insertable = true, updatable = false, nullable = false)
	@JSONField(serialize=false)
	private String createUser;

	@Column(name = "create_time", insertable = true, updatable = false, nullable = false)
	@JSONField(serialize=false)
	private Date createTime;

	@Column(name = "update_user", length = 50, insertable = true, updatable = true, nullable = false)
	@JSONField(serialize=false)
	private String updateUser;

	@Column(name = "update_time", insertable = true, updatable = true, nullable = false)
	@JSONField(serialize=false)
	private Date updateTime;

	/**
	 * 当前页
	 */
	@Transient
	@JSONField(serialize=false)
	private int page;

	/**
	 * 页面显示最大数
	 */
	@Transient
	@JSONField(serialize=false)
	private int size;

}
