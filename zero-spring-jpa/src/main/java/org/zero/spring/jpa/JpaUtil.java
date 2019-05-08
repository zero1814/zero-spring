package org.zero.spring.jpa;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

import zero.commons.basics.ObjectUtil;

public class JpaUtil<T> {

	public static ExampleMatcher getMatcher(Object obj) {
		try {
			ExampleMatcher matcher = ExampleMatcher.matching();
			// 遍历属性值，匹配查询条件
			List<Map<String, Object>> list = ObjectUtil.getFiledsInfo(obj);
			if (!list.isEmpty()) {
				for (Map<String, Object> map : list) {
					if (StringUtils.equals(map.get("type").toString(), "class java.lang.String")) {
						String name = map.get("name").toString();
						if (map.get("value") != null && StringUtils.isNotBlank(map.get("value").toString())) {
							matcher.withMatcher(name, GenericPropertyMatchers.contains());
						}
					}
				}
			}
			// 忽略大小写
			matcher.withIgnoreCase();
			return matcher;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
