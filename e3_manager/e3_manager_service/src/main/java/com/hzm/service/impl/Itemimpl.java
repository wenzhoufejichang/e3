package com.hzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzm.dao.TbItemDescMapper;
import com.hzm.dao.TbItemMapper;
import com.hzm.daomain.TbItem;
import com.hzm.daomain.TbItemDesc;
import com.hzm.daomain.TbItemExample;
import com.hzm.daomain.TbItemExample.Criteria;
import com.hzm.pojo.EasyUiPager;
import com.hzm.service.Iteminterface;
import com.hzm.service.JedisClient;
import com.hzm.utils.JsonUtils;

/**
 * 商品维护服务层
 * 
 */

@Service
@Transactional
public class Itemimpl implements Iteminterface {
	@Resource(name = "tbItemMapper")
	TbItemMapper tim;
	@Resource(name = "tbItemDescMapper")
	TbItemDescMapper tdm;
	@Autowired
	private JedisClient jc;
	@Value("${ITEM_DETAILS}")
	private String itemdetails;
	@Value("${ITEM_DESC}")
	private String itemdesc;
	@Value("${time}")
	private Integer time;

	/**
	 * 
	 * 从数据库或者redis集群中获取数据
	 * 
	 */
	@Override
	public TbItem getByid(Long id) {
		// TODO Auto-generated method stub

		String string = jc.get(itemdetails + ":" + id);
		TbItem item = null;
		if (string == null) {

			TbItemExample te = new TbItemExample();
			Criteria criteria = te.createCriteria();
			criteria.andIdEqualTo(id);
			List<TbItem> list = tim.selectByExample(te);
			if (list != null && list.size() > 0) {
				item = list.get(0);
				jc.set(itemdetails + ":" + id, JsonUtils.objectToJson(item));
				jc.expire(itemdetails + ":" + id, time);
			}
		} else {

			item = JsonUtils.jsonToPojo(string, TbItem.class);

		}
		return item;

	}

	/**
	 * 
	 * 使用 PageHelper 分页插件 对mybatis进行分页
	 * 
	 * "easyui-datagrid" 接受返回值json 对象需要 total 个 rows
	 * 
	 * 
	 * 两个参数 使用包装类 对list 集合进行封装
	 */
	@Override
	public EasyUiPager<TbItem> geteasypager(int page, int rows) {

		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		TbItemExample te = new TbItemExample();
		List<TbItem> list = tim.selectByExample(te);
		EasyUiPager<TbItem> ep = new EasyUiPager<>();

		PageInfo<TbItem> pi = new PageInfo<>(list);
		ep.setRows(list);
		ep.setTotal(pi.getTotal());
		return ep;
	}

	/**
	 * 
	 * 
	 * 新增商品
	 * 
	 * 
	 * 
	 * 
	 * @param ti
	 *               在web层对商品进行后续的时间封装
	 * 
	 * 
	 * @param td
	 *               商品描述
	 */
	@Override
	public void insert(TbItem ti, TbItemDesc td) {
		// TODO Auto-generated method stub

		tim.insert(ti);
		tdm.insert(td);

	}

	/**
	 * 
	 * 
	 * 从数据库或者redis集群中获取商品描述信息
	 * 
	 */
	@Override
	public TbItemDesc getBtid(Long id) {
		// TODO Auto-generated method stub

		String string = jc.get(itemdesc + ":" + id);
		TbItemDesc itemDesc = null;
		if (string == null) {

			itemDesc = tdm.selectByPrimaryKey(id);
			jc.set(itemdesc + ":" + id, JsonUtils.objectToJson(itemDesc));
			jc.expire(itemdesc + ":" + id, time);
		} else {
			itemDesc = JsonUtils.jsonToPojo(string, TbItemDesc.class);

		}
		return itemDesc;

	}

}
