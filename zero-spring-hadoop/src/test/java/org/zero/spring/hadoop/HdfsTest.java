package org.zero.spring.hadoop;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下
@SpringBootTest(classes = HadoopApplication.class)
public class HdfsTest {

	@Autowired
	private FileSystem fs;
	
	@Test
	public void mkDir() {
		try {
			fs.mkdirs(new Path("/process/task/user"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
