package org.zero.spring.hadoop;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// 这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下
@SpringBootTest(classes = HadoopApplication.class)
public class HdfsTest {

	@Test
	public void mkDir() {
		try {
			Configuration config = new Configuration();
			FileSystem fs = FileSystem.get(new URI("hdfs://192.168.99.100:9000"), config);
			fs.mkdirs(new Path("/temp/sss"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
