package com.hzm.service;

import java.util.List;

import com.hzm.pojo.EasyUiNote;
import com.hzm.pojo.Message;

public interface Contentinterface {

	public abstract List<EasyUiNote> getByparentId(Long id);

	public abstract Message addTreeNote(Long parentId, String name);

	public abstract void updateByid(Long id, String name);

	public abstract Message deleteByid(Long id);
}
