package com.hzm.service;

import com.hzm.daomain.TbItem;
import com.hzm.daomain.TbItemDesc;
import com.hzm.pojo.EasyUiPager;

public interface Iteminterface {

	public abstract TbItem getByid(Long id);

	public abstract TbItemDesc getBtid(Long id);

	public abstract EasyUiPager<TbItem> geteasypager(int page, int rows);

	public abstract void insert(TbItem ti, TbItemDesc td);

}
