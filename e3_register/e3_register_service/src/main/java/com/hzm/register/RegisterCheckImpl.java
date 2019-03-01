package com.hzm.register;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.dao.TbUserMapper;
import com.hzm.daomain.TbUser;
import com.hzm.daomain.TbUserExample;
import com.hzm.daomain.TbUserExample.Criteria;
import com.hzm.pojo.Message;

/**
 * 
 * 注册检测服务层
 */
@Service
@Transactional
public class RegisterCheckImpl implements RegisterCheckInterface {
	@Resource(name = "tbUserMapper")
	private TbUserMapper tm;

	/**
	 * 
	 * 注册前检测用户名和手机是否被占用
	 * 
	 * @param value
	 *                  用户名或者手机
	 * 
	 * @param type
	 *                  1检测用户名 2检测手机
	 */

	@Override
	public Message check(String value, int type) {
		// TODO Auto-generated method stub
		Boolean b = true;
		TbUserExample te = new TbUserExample();
		Message m = new Message();
		Criteria criteria = te.createCriteria();
		if (type == 1) {
			criteria.andUsernameEqualTo(value);

		} else if (type == 2) {
			criteria.andPhoneEqualTo(value);
		} else {
			b = false;
			m.setData(b);
			return m;
		}

		List<TbUser> selectByExample = tm.selectByExample(te);

		if (selectByExample != null && !selectByExample.isEmpty()) {
			b = false;
		}
		m.setData(b);
		return m;
	}

}
