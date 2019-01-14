package zero.spring.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

/**
 * 
 * 类: MongoConfig <br>
 * 描述: mongodb配置 <br>
 * 作者: zhy<br>
 * 时间: 2018年12月7日 下午4:48:02
 */
@Configuration
@ConfigurationProperties(prefix = "spring.mongodb")
public class MongoConfig {

	private String host;

	private int port;

	private String database;

	@Bean
	public MongoTemplate template() {
		return new MongoTemplate(factory());
	}

	public MongoDbFactory factory() {
		return new SimpleMongoDbFactory(new MongoClient(host, port), database);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
