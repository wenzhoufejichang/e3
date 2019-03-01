package com.hzm.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.dao.TbItemMapper;
import com.hzm.daomain.TbItem;
import com.hzm.daomain.TbItemExample;
import com.hzm.daomain.TbItemExample.Criteria;

import e3_cart_interface.SettlementInterface;

@Service
@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
public class SettleMentImpl implements SettlementInterface {
	@Resource(name = "tbItemMapper")
	private TbItemMapper tm;

	@Override
	public List<TbItem> buy(Set<String> set) {
		// TODO Auto-generated method stub
		TbItemExample te = new TbItemExample();
		Criteria criteria = te.createCriteria();
		List<Long> l = new ArrayList<>();
		for (String s : set) {
			l.add(Long.valueOf(s));
		}
		criteria.andIdIn(l);
		List<TbItem> list = tm.selectByExample(te);

		return list;
	}

}
