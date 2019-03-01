package com.hzm.index.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.hzm.daomain.TbContent;
import com.hzm.service.ContentManagementInterface;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 
 * 商城首页表现层
 */
@Controller
public class Index {

	
	
	@Resource(name = "contentManagementInterfaceImpl")
	private ContentManagementInterface cif;
	@Value("${inedx_Big_ad}")
	private Long inedx_Big_ad;// 首页轮播图的模块id
	@Resource(name = "freeMarkerConfig")

	private FreeMarkerConfig fmc;

	/**
	 * 
	 * 访问WEB-INF/jsp/index.jsp
	 * 
	 */
	@RequestMapping("/index.html")
	public String go(Model m) {

		List<TbContent> byCategoryId = cif.getByCategoryId(inedx_Big_ad);
		m.addAttribute("ad1List", byCategoryId);
		return "index";

	}

	/**
	 * 
	 * 
	 * 测试freemark
	 */
	@RequestMapping("/t")
	@ResponseBody
	public String test() {

		Configuration configuration = fmc.getConfiguration();
		try {
			Template template = configuration.getTemplate("test.html");
			Map<String, Object> map = new HashMap<>();
			map.put("name", "你好");
			List<String> list = new ArrayList<String>();
			list.add("1");
			list.add("a");
			map.put("key", list);
			Writer w = new FileWriter(new File("测试.html"));
			template.process(map, w);
			w.close();
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "好的";

	}

}
