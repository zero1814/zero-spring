package org.zero.spring.cache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCache {

	@Autowired
	private RedisTemplate<String, Object> template;

	/**
	 * 
	 * 方法: setHash <br>
	 * 描述: 存储hash <br>
	 * 作者: zhy<br>
	 * 时间: 2019年3月26日 下午4:28:07
	 * 
	 * @param key
	 * @param map
	 */
	public void setHash(String key, Map<String, Object> map) {
		boolean flag = template.hasKey(key);
		if (flag) {
			template.delete(key);
		}
		template.opsForHash().putAll(key, map);
	}

}
