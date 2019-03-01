package com.hzm.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hzm.daomain.NewTbItem;
import com.hzm.daomain.TbItem;
import com.hzm.daomain.TbItemDesc;
import com.hzm.service.Iteminterface;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * activitymq监听商品维护变现层发送的一对多的topic
 * 
 * 监听到后对相关id的商品执行商品详情网页静态化
 * 
 */
public class ItemFreeMarkListemer implements MessageListener {
	@Resource(name = "itemimpl")
	private Iteminterface iif;
	@Resource(name = "freeMarkerConfig")
	private FreeMarkerConfigurer fcr;
	@Value("${FreeMark_Generated_documents}")
	private String FreeMark_Generated_documents;// 网页静态化后的网页存储的本地磁盘,然后通过三个niginx访问静态资源

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

		TextMessage tm = (TextMessage) message;
		Writer w = null;
		try {
			String text = tm.getText();

			TbItem item = iif.getByid(Long.parseLong(text));
			TbItemDesc desc = iif.getBtid(Long.parseLong(text));
			Configuration configuration = fcr.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");

			Map<String, Object> map = new HashMap<>();
			NewTbItem newTbItem = new NewTbItem(item);
			newTbItem.setPrice(newTbItem.getPrice() / 100);
			map.put("item", newTbItem);
			map.put("itemDesc", desc);
			w = new FileWriter(new File(FreeMark_Generated_documents + text + ".html"));
			template.process(map, w);

		} catch (JMSException | IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
