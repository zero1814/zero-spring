package org.zero.spring.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.zero.spring.jpa.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@ApiModel(value = "通用字段定义")
public class CommonDefine extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("编码")
	@Id
	@Column(name = "code", length = 50, updatable = false)
	protected String code;

	@ApiModelProperty("名称")
	@Column(name = "name", length = 100, nullable = false)
	protected String name;

	@ApiModelProperty("是否可用 0 可用 1 不可用")
	@Column(name = "flag_enabled", columnDefinition = "int default 0 ", nullable = false)
	private Integer flagEnabled;

	@ApiModelProperty("创建人")
	@Column(name = "create_user", length = 50, insertable = true, updatable = false, nullable = false)
	private String createUser;

	@ApiModelProperty("创建时间")
	@Column(name = "create_time", insertable = true, updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@ApiModelProperty("修改人")
	@Column(name = "update_user", length = 50, insertable = true, updatable = true, nullable = false)
	private String updateUser;

	@ApiModelProperty("修改时间")
	@Column(name = "update_time", insertable = true, updatable = true, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

}
