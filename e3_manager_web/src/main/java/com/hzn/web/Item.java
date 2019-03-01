package com.hzn.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzm.daomain.TbItem;
import com.hzm.daomain.TbItemDesc;
import com.hzm.pojo.EasyUiNote;
import com.hzm.pojo.EasyUiPager;
import com.hzm.pojo.Message;
import com.hzm.service.ItemCatInterface;
import com.hzm.service.Iteminterface;
import com.hzm.utils.IDUtils;

/**
 * 
 * 商品管理表现层
 * 
 */
@Controller
@RequestMapping("/item")
public class Item {

	@Resource(name = "itemimpl")
	Iteminterface its;
	@Resource(name = "itemCatInterFaceImpl")
	ItemCatInterface ici;

	@Resource(name = "jmsTemplate")
	private JmsTemplate jt;
	@Resource(name = "topicDestination")
	private Topic t;

	/**
	 * 测试用的
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/id/{id}")
	@ResponseBody
	public TbItem getByid(@PathVariable Long id) {

		TbItem item = its.getByid(id);

		return item;

	}

	/**
	 * 
	 * 进入主页
	 */
	@RequestMapping("/")
	public String gotoindex() {

		// throw new RuntimeException("xxxx");

		return "index";
	}

	/**
	 * 
	 * /item/item-add ,/item/item-list ,/item/item-param-list ,/item/index-item,
	 * /item/content-category 的路口
	 */
	@RequestMapping("/{name}")
	public String gotointem_list(@PathVariable String name) {

		// System.out.println(name);

		return name;
	}

	/**
	 * 
	 * 载入商品列表
	 * 
	 * @param page
	 *                 第几页
	 * 
	 * @param rows
	 *                 每页显示个数
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiPager<TbItem> findItemList(int page, int rows) {

		EasyUiPager<TbItem> pager = its.geteasypager(page, rows);

		return pager;

	}

	/**
	 * 
	 * 添加商品时查询商品类目
	 * 
	 * @param id
	 *               eaysui tree型发送默认id 请求后端响应
	 * 
	 */
	@RequestMapping("/cat/lists")
	@ResponseBody
	public List<EasyUiNote> getByParentId(@RequestParam(defaultValue = "0") String id) {

		Long ids = Long.parseLong(id);
		List<EasyUiNote> list = ici.getById(ids);

		return list;

	}

	/**
	 * 
	 * 
	 * 新增商品并对商品详情信息网页静态化和对此商品存入solr索引库中
	 * 
	 * 
	 * 
	 * 
	 * @param tbitem
	 *                   商品变量与jsp上的name 自动映射
	 * 
	 * 
	 * @param desc
	 *                   商品描述
	 */

	@RequestMapping("/save")
	@ResponseBody
	public Message insertItem(TbItem tbitem, String desc) {

		tbitem.setId(IDUtils.genItemId());
		// 商品状态，1-正常，2-下架，3-删除
		tbitem.setStatus((byte) 1);
		tbitem.setCreated(new Date());
		tbitem.setUpdated(new Date());
		TbItemDesc td = new TbItemDesc();
		td.setItemDesc(desc);
		td.setItemId(tbitem.getId());
		td.setCreated(new Date());
		td.setUpdated(new Date());

		its.insert(tbitem, td);
		Message m = new Message();
		m.setStatus(200);

		jt.send(t, new MessageCreator() {// 在web给商品详情表现层发送消息队列根据商品id执行该商品详情页面网页静态化和solr索引库添加商品

			@Override
			public javax.jms.Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage message = session.createTextMessage(String.valueOf(tbitem.getId()));
				return message;
			}
		});

		return m;

	}

}
