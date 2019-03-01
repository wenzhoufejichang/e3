package com.hzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzm.dao.TbContentMapper;
import com.hzm.daomain.TbContent;
import com.hzm.daomain.TbContentExample;
import com.hzm.daomain.TbContentExample.Criteria;
import com.hzm.pojo.EasyUiPager;
import com.hzm.service.ContentManagementInterface;
import com.hzm.service.JedisClient;
import com.hzm.utils.JsonUtils;

/**
 * 
 * 
 * 内容管理服务层
 */
@Service
@Transactional
public class ContentManagementInterfaceImpl implements ContentManagementInterface {

	@Resource(name = "tbContentMapper")
	private TbContentMapper tcm;
	@Autowired
	private JedisClient jc;
	@Value("${content_big_ad}")
	private String content_big_ad;//首页展示商品在redis集群中的key

	/**
	 * 
	 * 添加categoryId下的新内容显示项
	 * 
	 * 
	 * 并删除redis集群中的对应content_big_ad的categoryId字段的内容
	 */

	@Override
	public void save(TbContent tc) {
		// TODO Auto-generated method stub
		tcm.insert(tc);
		// 删除redis集群中的content_big_ad 为键的hash表中的categoryId字段的内容
		jc.hdel(content_big_ad, tc.getCategoryId().toString());
	}

	/**
	 * 
	 * PageHelper 对mybatis进行分页
	 * 
	 * 
	 * @param categoryId
	 *                       内容类categoryId
	 * 
	 * @param page
	 *                       第几页
	 * 
	 * @param rows
	 *                       每页显示个数
	 * 
	 */
	@Override
	public EasyUiPager<TbContent> getByCategoryId(Long categoryId, Integer page, Integer rows) {
		// TODO Auto-generated method stub

		PageHelper.startPage(page, rows);
		TbContentExample te = new TbContentExample();
		Criteria criteria = te.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tcm.selectByExample(te);
		PageInfo<TbContent> pi = new PageInfo<>(list);
		List<TbContent> list2 = pi.getList();
		EasyUiPager<TbContent> e = new EasyUiPager<>();// easyui-datagrid 接受json对象 需要 total 和rows参数
		e.setRows(list2);// 每页显示记得json集合
		e.setTotal(pi.getTotal());// 总记录数
		return e;
	}

	/**
	 * 
	 * 从数据库和redis集群中加载首页商品信息
	 * 
	 * 
	 * @param categoryId
	 *                       首页商品轮播图的模块id
	 * 
	 */
	@Override
	public List<TbContent> getByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub

		String string = jc.hget(content_big_ad, String.valueOf(categoryId));// 如果redis集群中没有商品信息,则从数据加载再放入redis集群
		if (string == null) {

			TbContentExample te = new TbContentExample();
			Criteria criteria = te.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			List<TbContent> list = tcm.selectByExample(te);
			jc.hset(content_big_ad, String.valueOf(categoryId), JsonUtils.objectToJson(list));
			jc.expire(content_big_ad, 3600);
			return list;
		} else {
			List<TbContent> list = (List<TbContent>) JsonUtils.jsonToList(string, TbContent.class);
			return list;
		}

	}

}
