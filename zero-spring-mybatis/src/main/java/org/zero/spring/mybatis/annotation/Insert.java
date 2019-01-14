package org.zero.spring.mybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * 类: Insert <br>
 * 描述: 添加时验证 <br>
 * 作者: zhy<br>
 * 时间: 2019年1月14日 下午2:14:45
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Insert {

	public boolean vertify() default false;

	public String alert() default "";
}
