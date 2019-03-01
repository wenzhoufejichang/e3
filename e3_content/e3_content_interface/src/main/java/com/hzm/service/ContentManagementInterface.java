package com.hzm.service;

import java.util.List;

import com.hzm.daomain.TbContent;
import com.hzm.pojo.EasyUiPager;

public interface ContentManagementInterface {

	public abstract void save(TbContent tc);

	public abstract EasyUiPager<TbContent> getByCategoryId(Long categoryId ,Integer page,Integer rows);
	public abstract List<TbContent> getByCategoryId(Long categoryId );

}
