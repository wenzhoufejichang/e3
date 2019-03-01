package com.hzm.register;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.dao.TbUserMapper;
import com.hzm.daomain.TbUser;
import com.hzm.pojo.Message;

@Service
@Transactional
public class RegiterregisterImpl implements RegiterregisterInface {
	@Resource(name = "tbUserMapper")
	private TbUserMapper tm;

	@Override
	public Message register(TbUser tu) {
		// TODO Auto-generated method stub
		int insert = tm.insert(tu);

		Message m = new Message();
		if (insert == 1) {
			m.setStatus(200);
		} else {
			m.setStatus(400);
		}

		return m;
	}

}
