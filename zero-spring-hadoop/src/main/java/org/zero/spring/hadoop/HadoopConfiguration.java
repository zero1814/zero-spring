package org.zero.spring.hadoop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HadoopConfiguration {

	@Value("${spring.hadoop.config.fs.defaultFS}")
	private String defaultFs;
	@Value("${spring.hadoop.user}")
	private String user;

	@Bean("fileSystem")
	public FileSystem getFileSystem() throws IOException, InterruptedException, URISyntaxException {
		FileSystem fs = FileSystem.get(new URI(defaultFs), getConfiguration(), user);
		return fs;
	}

	private org.apache.hadoop.conf.Configuration getConfiguration() {
		org.apache.hadoop.conf.Configuration config = new org.apache.hadoop.conf.Configuration();
		config.set("fs.defaultFS", defaultFs);
		return config;
	}
}
