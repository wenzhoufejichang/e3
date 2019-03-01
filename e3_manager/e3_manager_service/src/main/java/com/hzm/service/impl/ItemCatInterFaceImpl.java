package com.hzm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.dao.TbItemCatMapper;
import com.hzm.daomain.TbItemCat;
import com.hzm.daomain.TbItemCatExample;
import com.hzm.daomain.TbItemCatExample.Criteria;
import com.hzm.pojo.EasyUiNote;
import com.hzm.service.ItemCatInterface;

@Service
@Transactional
public class ItemCatInterFaceImpl implements ItemCatInterface {
	@Resource(name = "tbItemCatMapper")
	private TbItemCatMapper icm;

	/**
	 * 
	 * 
	 * 查询商品类目
	 */

	@Override
	public List<EasyUiNote> getById(Long id) {
		// TODO Auto-generated method stub
		TbItemCatExample tce = new TbItemCatExample();
		Criteria criteria = tce.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbItemCat> list = icm.selectByExample(tce);
		List<EasyUiNote> listssss = new ArrayList<>();
		if (list != null) {

			for (TbItemCat tc : list) {

				EasyUiNote e = new EasyUiNote();

				e.setId(tc.getId());
				e.setState(tc.getIsParent() ? "closed" : "open");
				e.setText(tc.getName());
				listssss.add(e);
			}
			return listssss;
		}

		return null;
	}

}
