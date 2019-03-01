package com.hzm.cart.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hzm.pojo.Message;
import com.hzm.register.LoginInterface;

/**
 * 
 * 
 * 拦截请求将cookie中的token和商品添加到request域方便后续的操作
 * 
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Value("${COOKIE_NAME}")
	private String COOKIE_NAME;

	@Value("${COOKIES_TOKEN_NAME}")
	private String COOKIES_TOKEN_NAME;
	@Resource(name = "loginInterface")
	private LoginInterface lif;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub

		Cookie[] cookies = request.getCookies();

		for (Cookie c : cookies) {
			if (COOKIES_TOKEN_NAME.equals(c.getName())) {
				String value = c.getValue();
				if (!StringUtils.isBlank(value)) {

					Message message = lif.getName(value);
					if (message.getStatus() == 200) {
						request.setAttribute(COOKIES_TOKEN_NAME, value);

					}
				}
			} else if (COOKIE_NAME.equals(c.getName())) {
				String value = c.getValue();

				if (!StringUtils.isBlank(value)) {
					request.setAttribute(COOKIE_NAME, value);
				}
			}

		}

		return true;
	}

}
