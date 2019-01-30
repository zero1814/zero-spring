package org.zero.spring.hadoop.config;

import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HdfsConfiguration {

	@Value("${hadoop.uri}")
	private String uri;

	@Value("${hadoop.home}")
	private String home = "";

	@Bean
	@Scope("prototype")
	public FileSystem fileSystem() {
		System.setProperty("hadoop.home.dir", home);
		org.apache.hadoop.conf.Configuration config = new org.apache.hadoop.conf.Configuration();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(new URI(uri), config, "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fs;
	}

}
