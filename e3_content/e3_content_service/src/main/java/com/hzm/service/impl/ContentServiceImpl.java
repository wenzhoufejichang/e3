package com.hzm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.dao.TbContentCategoryMapper;
import com.hzm.daomain.TbContentCategory;
import com.hzm.daomain.TbContentCategoryExample;
import com.hzm.daomain.TbContentCategoryExample.Criteria;
import com.hzm.pojo.EasyUiNote;
import com.hzm.pojo.Message;
import com.hzm.service.Contentinterface;

/**
 * 
 * 内容展示服务层
 * 
 */
@Service
@Transactional
public class ContentServiceImpl implements Contentinterface {

	@Resource(name = "tbContentCategoryMapper")
	TbContentCategoryMapper tcm;

	/**
	 * 
	 * 根据父类id查询本类节点
	 */
	@Override
	public List<EasyUiNote> getByparentId(Long id) {
		// TODO Auto-generated method stub

		TbContentCategoryExample tce = new TbContentCategoryExample();
		Criteria criteria = tce.createCriteria();

		criteria.andParentIdEqualTo(id);
		List<TbContentCategory> list = tcm.selectByExample(tce);
		if (list != null) {
			List<EasyUiNote> list2 = new ArrayList<>();
			for (TbContentCategory tcc : list) {
				EasyUiNote e = new EasyUiNote();
				e.setId(tcc.getId());
				// 查找的本类节点下没有子类节点则state为open 如果还有本类节点则state为closed
				e.setState(tcc.getIsParent() ? "closed" : "open");
				e.setText(tcc.getName());
				list2.add(e);
			}
			return list2;
		}

		return null;
	}

	/**
	 * 
	 * 
	 * 添加子节点
	 */
	@Override
	public Message addTreeNote(Long parentId, String name) {
		// TODO Auto-generated method stub

		TbContentCategory tc = new TbContentCategory();
		TbContentCategory tc2 = new TbContentCategory();
		// 添加子类节点
		tc.setIsParent(false);
		tc.setName(name);
		tc.setParentId(parentId);
		tc.setCreated(new Date());
		// 默认1
		tc.setSortOrder(1);
		// 状态。可选值:1(正常),2(删除)
		tc.setStatus(1);
		tc.setUpdated(new Date());
		tcm.insert(tc);
		tc2.setId(parentId);
		tc2.setIsParent(true);
		// 选择性更新本类 如果传入的java对象的成员变量为空则不更新
		tcm.updateByPrimaryKeySelective(tc2);
		Message m = new Message();
		m.setStatus(200);
		m.setData(tc);
		return m;
	}

	/**
	 * 
	 * 
	 * 更新本节点
	 */
	@Override
	public void updateByid(Long id, String name) {
		// TODO Auto-generated method stub
		TbContentCategory tc = new TbContentCategory();
		tc.setId(id);
		tc.setName(name);
		tcm.updateByPrimaryKeySelective(tc);

	}

	/**
	 * 
	 * 删除本节点
	 * 
	 * 
	 * @param id
	 *               本节点id
	 */
	@Override
	public Message deleteByid(Long id) {
		// TODO Auto-generated method stub
		TbContentCategory category = tcm.selectByPrimaryKey(id);
		Message m = new Message();
		// 如果本节点下还有子节点提示删除失败
		if (category.getIsParent()) {
			m.setStatus(400);

		} else {// 删除本节点成功,修改本节点的父节点isParent的属性

			tcm.deleteByPrimaryKey(id);
			Long id2 = category.getParentId();
			TbContentCategoryExample tce = new TbContentCategoryExample();
			Criteria criteria = tce.createCriteria();
			criteria.andParentIdEqualTo(id2);
			List<TbContentCategory> list = tcm.selectByExample(tce);

			if (list != null && list.size() == 0) {// 删除的本节点是父节点唯一的子节点,则修改IsParent为false

				TbContentCategory category2 = new TbContentCategory();
				category2.setId(id2);
				category2.setIsParent(false);
				tcm.updateByPrimaryKeySelective(category2);
			}

			m.setStatus(200);
		}
		return m;
	}

}
