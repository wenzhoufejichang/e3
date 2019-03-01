package com.hzm.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzm.dao.TbItemMapper;
import com.hzm.daomain.TbItem;
import com.hzm.daomain.TbItemExample;
public class PageTest {
	//@Test
	public void pagerHelp() {

		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext_dao.xml");

		TbItemMapper bean = ac.getBean(TbItemMapper.class);

		PageHelper.startPage(1, 10);
		TbItemExample te = new TbItemExample();
		List<TbItem> list = bean.selectByExample(te);
		PageInfo<TbItem> pi = new PageInfo<>(list);

		List<TbItem> list2 = pi.getList();
		int i = pi.getPages();

		System.out.println(pi.getTotal());
		System.out.println(list2.size());
		System.out.println(i);

	}
}
