package org.zero.spring.mybatis.annotation;

/**
 * 
 * 类: Table <br>
 * 描述: 数据表注解 <br>
 * 作者: zhy<br>
 * 时间: 2019年2月14日 下午3:03:12
 */
public @interface Table {

	/**
	 * 
	 * 方法: name <br>
	 * 描述: 表名称 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午3:02:49
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 
	 * 方法: comment <br>
	 * 描述: 表描述 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午3:03:02
	 * 
	 * @return
	 */
	public String comment();
}
