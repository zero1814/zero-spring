package org.zero.spring.mybatis;

import java.io.Serializable;

import org.zero.spring.mybatis.annotation.Column;

public class BaseEntityAnnotation implements Serializable {

	private static final long serialVersionUID = -7413493430850812403L;

	@Column(name = "id")
	private Long id;

	@Column(name = "uid", nullable = false)
	private String uid;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "create_user", nullable = false, updateable = false)
	private String createUser;

	@Column(name = "create_time", nullable = false, updateable = false)
	private String createTime;

	@Column(name = "update_user", nullable = false)
	private String updateUser;

	@Column(name = "update_time", nullable = false)
	private String updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
