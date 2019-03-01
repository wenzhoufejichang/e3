package com.hzm.register;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.dao.TbUserMapper;
import com.hzm.daomain.TbUser;
import com.hzm.daomain.TbUserExample;
import com.hzm.daomain.TbUserExample.Criteria;
import com.hzm.pojo.Message;
import com.hzm.utils.JsonUtils;

@Service
@Transactional
public class LoginInterfaceImpl implements LoginInterface {

	@Resource(name = "tbUserMapper")
	private TbUserMapper tm;
	@Value("${REDIS_TOKEN_TIME}")
	private Integer REDIS_TOKEN_TIME;
	@Autowired
	private JedisClient jc;

	@Override
	public Message login(String username, String password) {
		// TODO Auto-generated method stub
		Message m = new Message();
		TbUserExample te = new TbUserExample();
		Criteria criteria = te.createCriteria();
		criteria.andUsernameEqualTo(username);
		String md5Hex = DigestUtils.md5Hex(password);
		criteria.andPasswordEqualTo(md5Hex);
		List<TbUser> list = tm.selectByExample(te);
		if (list == null || list.isEmpty()) {
			m.setStatus(400);
		} else {
			String token = UUID.randomUUID().toString().replace("-", "");
			TbUser user = list.get(0);
			user.setPassword(null);
			jc.set("SESSIONID-" + token, JsonUtils.objectToJson(user));
			jc.expire("SESSIONID-" + token, REDIS_TOKEN_TIME);
			m.setData(token);
			m.setStatus(200);
		}

		return m;
	}

	@Override
	public Message getName(String token) {
		// TODO Auto-generated method stub

		String value = jc.get("SESSIONID-" + token);
		Message m = new Message();
		if (value == null) {

			m.setStatus(400);
			m.setData("token已过期或者不存在");
		} else {
			m.setStatus(200);
			TbUser jsonToPojo = JsonUtils.jsonToPojo(value, TbUser.class);
			m.setData(jsonToPojo);
			jc.expire("SESSIONID-" + token, REDIS_TOKEN_TIME);
		}

		return m;
	}

	@Override
	public void remove(String token) {
		// TODO Auto-generated method stub

		jc.delete("SESSIONID-" + token);

	}

}
