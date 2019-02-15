package org.zero.spring.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 
 * 类: BaseEntity <br>
 * 描述: 对象基类 <br>
 * 作者: zhy<br>
 * 时间: 2018年12月5日 上午11:34:53
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "uid", length = 50, unique = true)
	private String uid;

	@Id
	@Column(name = "code", length = 50, unique = true)
	private String code;

	@Column(name = "create_user", length = 50, insertable = true, updatable = false, nullable = false)
	private String createUser;

	@Column(name = "create_time", insertable = true, updatable = false, nullable = false)
	private Date createTime;

	@Column(name = "update_user", length = 50, insertable = true, updatable = true, nullable = false)
	private String updateUser;

	@Column(name = "update_time", length = 50, insertable = true, updatable = true, nullable = false)
	private Date updateTime;

	/**
	 * 当前页
	 */
	@Transient
	private int page;

	/**
	 * 页面显示最大数
	 */
	@Transient
	private int size;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
