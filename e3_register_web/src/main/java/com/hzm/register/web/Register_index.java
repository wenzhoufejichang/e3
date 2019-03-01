package com.hzm.register.web;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hzm.daomain.TbUser;
import com.hzm.pojo.Message;
import com.hzm.register.RegisterCheckInterface;
import com.hzm.register.RegiterregisterInface;

/**
 * 
 * 
 * 注册登录表现层
 */
@Controller
public class Register_index {
	@Resource(name = "registerCheckInterface")
	private RegisterCheckInterface rci;
	@Resource(name = "regiterregisterInface")
	private RegiterregisterInface ri;

	/**
	 * 
	 * 访问主页页面
	 * 
	 */
	@RequestMapping("/e3/register")
	public String index() {

		return "register";
	}

	/**
	 * 
	 * 访问登录页面
	 * 
	 */
	@RequestMapping("/page/login")
	public String login() {

		return "login";
	}

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
	@ResponseBody
	@RequestMapping("/user/check")
	public Message check(String value, int type) {
		Message message = rci.check(value, type);
		return message;
	}

	@RequestMapping("/user/register")
	@ResponseBody
	public Message register(TbUser tu) {
		Message m = null;
		if (StringUtils.isBlank(tu.getUsername()) || StringUtils.isBlank(tu.getPassword())
				|| StringUtils.isBlank(tu.getPhone()) || !tu.getPhone().matches("^1[3-9][0-9]\\d{8}$")) {

			m = new Message();
			m.setStatus(400);

		} else {
			tu.setCreated(new Date());
			tu.setUpdated(new Date());
			String hex = DigestUtils.md5Hex(tu.getPassword());
			tu.setPassword(hex);
			m = ri.register(tu);
		}
		return m;

	}
}
