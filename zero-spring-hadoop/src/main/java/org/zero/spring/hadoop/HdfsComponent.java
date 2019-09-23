package org.zero.spring.hadoop;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import zero.commons.basics.result.BaseResult;

@Component
@Slf4j
public class HdfsComponent {

	@Autowired
	private FileSystem fs;

	/**
	 * 
	 * 方法: createMkdir <br>
	 * 描述: hdfs创建文件夹 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年9月20日 下午2:33:44
	 * 
	 * @param path
	 */
	public BaseResult createMkdir(String path) {
		try {
			boolean flag = fs.exists(new Path(path));
			if (flag) {
				return BaseResult.error("文件目录已存在");
			}
			// 设置目录权限 user,group,other
			FsPermission fp = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.READ);
			fs.mkdirs(new Path(path), fp);
			return BaseResult.success("创建文件目录成功");
		} catch (Exception e) {
			log.error("hdfs创建文件夹错误：" + e.getMessage());
			return BaseResult.error("创建文件目录失败");
		}
	}

	/**
	 * 
	 * 方法: delDir <br>
	 * 描述: hdfs删除文件夹 <br>
	 * 作者: zhy<br>
	 * 时间: 2019年9月20日 下午2:41:16
	 * 
	 * @param path
	 * @return
	 */
	public BaseResult delDir(String path) {
		try {
			boolean flag = fs.exists(new Path(path));
			if (!flag) {
				return BaseResult.empty("文件目录不存在");
			}
			fs.delete(new Path(path), true);
			return BaseResult.success("删除成功");
		} catch (Exception e) {
			log.error("hdfs删除文件目录错误：" + e.getMessage());
			return BaseResult.error("hdfs删除文件目录报错");
		}
	}

	public BaseResult uploadFile(String path, MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			boolean flag = fs.exists(new Path(path));
			if (!flag) {
				// 设置目录权限 user,group,other
				FsPermission fp = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.READ);
				fs.mkdirs(new Path(path), fp);
			}
			Path p = new Path(path + "/" + fileName);
			FSDataOutputStream out = fs.create(p, true);
			out.write(file.getBytes());
			out.close();
			return BaseResult.success("文件上传成功");
		} catch (Exception e) {
			log.error("文件上传报错，错误原因：" + e.getMessage());
			return BaseResult.error("文件上传报错");
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
