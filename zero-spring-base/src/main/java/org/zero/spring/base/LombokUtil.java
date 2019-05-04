package org.zero.spring.base;

import java.lang.reflect.Field;

import lombok.Lombok;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LombokUtil {

	public static boolean isNull(Class<?> clazz) {
		boolean flag = false;
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Lombok.checkNotNull(field, "是否为空");
			}
		} catch (Exception e) {
			log.error("判断对象属性是否为空报错，错误原因：\n" + e.getMessage());
		}
		return flag;
	}
}
