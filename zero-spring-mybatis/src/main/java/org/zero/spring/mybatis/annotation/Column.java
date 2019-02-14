package org.zero.spring.mybatis.annotation;

/**
 * 
 * 类: Column <br>
 * 描述: 表字段注解 <br>
 * 作者: zhy<br>
 * 时间: 2019年2月14日 下午3:08:46
 */
public @interface Column {

	/**
	 * 
	 * 方法: name <br>
	 * 描述: 列名称 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午3:10:00
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 
	 * 方法: nullable <br>
	 * 描述: 是否允许为空 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午3:11:00
	 * 
	 * @return
	 */
	public boolean nullable() default true;

	/**
	 * 
	 * 方法: comment <br>
	 * 描述: 列描述 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午3:10:07
	 * 
	 * @return
	 */
	public String comment() default "";

	/**
	 * 
	 * 方法: insertable <br>
	 * 描述: 是否可以添加字段值 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午3:10:17
	 * 
	 * @return
	 */
	public boolean insertable() default true;

	/**
	 * 
	 * 方法: updateable <br>
	 * 描述: 是否可以编辑字段值 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午3:10:30
	 * 
	 * @return
	 */
	public boolean updateable() default true;
}
