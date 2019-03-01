package com.hzm.register.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hzm.pojo.Message;
import com.hzm.register.LoginInterface;

/**
 * 
 * 拦截登入后再次访问登录界面
 * 
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "loginInterfaceImpl")
	private LoginInterface lif;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("COOKIES_TOKEN".equals(c.getName())) {
					String token = c.getValue();
					if (!StringUtils.isBlank(token)) {
						Message message = lif.getName(token);
						if (message.getStatus() == 200) {

							response.sendRedirect("http://localhost:8085");
						}
						return false;
					}
				}
			}

		}

		return true;
	}

}
