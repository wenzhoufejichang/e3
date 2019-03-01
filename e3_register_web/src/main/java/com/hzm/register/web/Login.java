package com.hzm.register.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hzm.pojo.Message;
import com.hzm.register.LoginInterface;
import com.hzm.utils.CookieUtils;

/**
 * 
 * 登录表现层
 */
@Controller
public class Login {
	@Value("${COOKIES_TOKEN_NAME}")
	private String COOKIES_TOKEN_NAME;
	@Resource(name = "loginInterfaceImpl")
	private LoginInterface lif;

	/**
	 * 
	 * 当点登录功能
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public Message loginUser(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {

		Message message = lif.login(username, password);

		if (message.getStatus() == 200) {
			String data = (String) message.getData();
			// 登录成功后生成token 写入到cookie中
			CookieUtils.setCookie(request, response, COOKIES_TOKEN_NAME, data);
			message.setData(null);
		}

		return message;

	}

	/**
	 * 
	 * ajax的跨域请求登录
	 * 
	 * @param callback
	 *                     解决 ajax 跨域请求
	 * 
	 * @param token
	 *                     从浏览器发送的cookie 然后根据这个键获取rendis集群中的用户信息
	 * 
	 */
	@RequestMapping(value = "/user/name/{token}")
	@ResponseBody
	public Object getName(@PathVariable String token, String callback) {

		Message name = lif.getName(token);

		if (!StringUtils.isBlank(callback)) {
			MappingJacksonValue mjv = new MappingJacksonValue(name);
			mjv.setJsonpFunction(callback);
			return mjv;
		}

		return name;

	}

	/**
	 * 
	 * ajax 登出功能
	 * 
	 * @param callback
	 *                     解决 ajax 跨域请求
	 * 
	 * @param token
	 *                     从浏览器发送的cookie 然后根据这个键获取rendis集群中的用户信息
	 * 
	 */
	@RequestMapping(value = "/remove/user/{token}")
	@ResponseBody
	public Object loginOut(String token, HttpServletRequest request, HttpServletResponse response, String callback) {

		CookieUtils.deleteCookie(request, response, COOKIES_TOKEN_NAME);
		lif.remove(token);
		Message m = new Message();
		m.setStatus(200);

		if (!StringUtils.isBlank(callback)) {
			MappingJacksonValue mjv = new MappingJacksonValue(m);
			mjv.setJsonpFunction(callback);
			return mjv;
		}

		return m;

	}

}
