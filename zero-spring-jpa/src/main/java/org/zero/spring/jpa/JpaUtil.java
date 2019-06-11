package org.zero.spring.jpa;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import zero.commons.basics.ObjectUtil;

public class JpaUtil<T> {

	public static ExampleMatcher getMatcher(Object obj) {
		try {
			ExampleMatcher matcher = ExampleMatcher.matching();
			// 忽略大小写
			matcher.withIgnoreCase(true);
			// 改变默认字符串匹配方式：模糊查询
			matcher.withStringMatcher(StringMatcher.CONTAINING);
			// 忽略空值
			matcher.withIgnoreNullValues();
			// 遍历属性值，匹配查询条件
			List<Map<String, Object>> list = ObjectUtil.getFiledsInfo(obj);
			if (!list.isEmpty()) {
				for (Map<String, Object> map : list) {
					String name = map.get("name").toString();
					if (StringUtils.equals(map.get("type").toString(), "class java.lang.String")) {
						if (map.get("value") != null && StringUtils.isNotBlank(map.get("value").toString())) {
							matcher.withMatcher(name, GenericPropertyMatchers.contains());
						}
					} else {
						if (map.get("value") != null) {
							matcher.withMatcher(name, GenericPropertyMatchers.startsWith());
						}
					}
				}
			}
			matcher.withIgnorePaths("flagEnabled");
			return matcher;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean validate(T entity) {
		List<Field> list = ObjectUtil.getFields(entity.getClass());
		try {
			if (list.isEmpty()) {
				return false;
			}
			for (Field field : list) {
				Column col = field.getDeclaredAnnotation(Column.class);
				if (col.unique()) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
