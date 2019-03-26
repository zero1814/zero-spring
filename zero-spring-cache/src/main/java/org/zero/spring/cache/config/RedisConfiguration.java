package org.zero.spring.cache.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfiguration {

	@SuppressWarnings("all")
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);

		// key采用String的序列化方式
		template.setKeySerializer(keySerializer());
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(keySerializer());
		// value序列化方式采用jackson
		template.setValueSerializer(valueSerializer());
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(valueSerializer());
		template.afterPropertiesSet();
		return template;
	}

	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		// 缓存配置对象
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L))// 设置缓存的默认超时时间：30分钟
				.disableCachingNullValues()// 如果是空值，不缓存
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer())) // 设置key序列化器
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((valueSerializer()))); // 设置value序列化器
		return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(redisCacheConfiguration).build();
	}

	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	@SuppressWarnings("all")
	private RedisSerializer<Object> valueSerializer() {
		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer jackson = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson.setObjectMapper(mapper);
		return jackson;
	}
}
