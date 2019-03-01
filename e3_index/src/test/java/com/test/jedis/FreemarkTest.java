package com.test.jedis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkTest {
	//@Test
	public void t1() {

		// E:\android\workspace1\WEB\e3_index\target\test-classes\test.txt
		// E:\android\workspace1\WEB\e3_index\target\test-classes\test.txt
		try {
			String path = this.getClass().getResource("/").getPath();
			System.out.println(path);
			Configuration c = new Configuration(Configuration.getVersion());
			// 模板所在的文件夹
			c.setDirectoryForTemplateLoading(new File(path));
			// 设置编码格式
			c.setDefaultEncoding("utf-8");
			// 加载模板
			Template template = c.getTemplate("测试.txt");
			// 创建输出流
			Writer w = new FileWriter("新建文本文档.txt");
			Map<String, Object> map = new HashMap<>();
			// 设置数据集 key与模板中的${xxx} 一致
			map.put("name", 1);
			template.process(map, w);
			w.close();

		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
