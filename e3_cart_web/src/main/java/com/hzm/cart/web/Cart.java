package com.hzm.cart.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hzm.cart.domain.NewITem;
import com.hzm.daomain.TbItem;
import com.hzm.service.Iteminterface;
import com.hzm.utils.CookieUtils;
import com.hzm.utils.JsonUtils;

import e3_cart_interface.JedisClient;
import e3_cart_interface.SettlementInterface;

/**
 * 
 * 购物车变现层
 * 
 */
@Controller
public class Cart {

	@Value("${COOKIE_NAME}")
	private String COOKIE_NAME;
	@Value("${COOKIE_AGE}")
	private Integer COOKIE_AGE;
	@Value("${COOKIES_TOKEN_NAME}")
	private String COOKIES_TOKEN_NAME;
	@Resource(name = "settleMentImpl")
	private SettlementInterface si;
	@Autowired
	private JedisClient jc;
	@Resource(name = "iteminterface")
	private Iteminterface iif;

	/**
	 * 
	 * 在未登录的状态下将商品添加到购物车并写入到cookie中
	 * 
	 * @param product_id
	 *                       商品id
	 * 
	 * @param num
	 *                       商品数量
	 * 
	 */
	@RequestMapping("/cart/add/{product_id}")
	public String add(@PathVariable String product_id, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (COOKIE_NAME.equals(c.getName())) {
				String string = c.getValue();
				HashMap<String, Integer> jsonToPojo = JsonUtils.jsonToPojo(string, HashMap.class);

				if (jsonToPojo.containsKey(product_id)) {

					Integer integer = jsonToPojo.get(product_id);
					integer += num;
					jsonToPojo.put(product_id, integer);

				} else {

					jsonToPojo.put(product_id, num);
				}

				String objectToJson = JsonUtils.objectToJson(jsonToPojo);
				CookieUtils.setCookie(request, response, COOKIE_NAME, objectToJson, COOKIE_AGE);
				return "cartSuccess";
			}
		}
		// "{\"154661410454517\":4,\"154757087873996\":3}"
		HashMap<String, Integer> lm = new HashMap<>();
		lm.put(product_id, num);
		String objectToJson = JsonUtils.objectToJson(lm);
		CookieUtils.setCookie(request, response, COOKIE_NAME, objectToJson, COOKIE_AGE);
		return "cartSuccess";
	}

	/**
	 * 
	 * 登录状态下将cookie中的商品添加到redis
	 */
	@RequestMapping("/cart/cart")
	public String Settlement(HttpServletRequest request, HttpServletResponse response) {

		String token = (String) request.getAttribute(COOKIES_TOKEN_NAME);

		if (token != null) {
			Boolean exists = jc.exists(token);
			String attribute = (String) request.getAttribute(COOKIE_NAME);
			HashMap<String, Integer> jsonToPojo = JsonUtils.jsonToPojo(attribute, HashMap.class);
			Set<String> set = jsonToPojo.keySet();
			if (exists) {

				for (String id : set) {
					Boolean e = jc.hexists(token, id);
					if (e) {
						String string = jc.hget(token, id);
						TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
						Integer num1 = item.getNum();
						Integer integer = jsonToPojo.get(id);
						item.setNum(num1 + integer);
						String toJson = JsonUtils.objectToJson(item);
						jc.hset(token, id, toJson);

					} else {

						TbItem item = iif.getByid(Long.parseLong(id));
						item.setNum(jsonToPojo.get(id));
						String toJson = JsonUtils.objectToJson(item);
						jc.hset(token, id, toJson);
					}

				}

			} else {
				List<TbItem> list = si.buy(set);
				for (TbItem t : list) {

					Long id = t.getId();
					Integer integer = jsonToPojo.get(String.valueOf(id));
					t.setNum(integer);

					jc.hset(token, id + "", JsonUtils.objectToJson(t));
				}

			}
			CookieUtils.setCookie(request, response, COOKIE_NAME, "{}");
			// CookieUtils.deleteCookie(request, response, COOKIE_NAME);

			Map<String, String> map = jc.hgtall(token);
			Collection<String> values = map.values();
			List<NewITem> list = new ArrayList<>();

			for (String s : values) {
				TbItem toPojo = JsonUtils.jsonToPojo(s, TbItem.class);
				NewITem nt = new NewITem(toPojo);
				list.add(nt);
			}
			request.setAttribute("cartList", list);

			return "cart";
		}

		Cookie[] cookies = request.getCookies();

		for (Cookie c : cookies) {

			if (COOKIE_NAME.equals(c.getName())) {
				String value = c.getValue();
				HashMap<String, Integer> jsonToPojo = JsonUtils.jsonToPojo(value, HashMap.class);

				Set<String> set = jsonToPojo.keySet();
				if (set.size() == 0)
					return "cart";

				List<TbItem> list = si.buy(set);
				List<NewITem> l = new ArrayList<>();
				for (TbItem t : list) {

					Long id = t.getId();
					Integer integer = jsonToPojo.get(String.valueOf(id));
					t.setNum(integer);
					NewITem nt = new NewITem(t);
					l.add(nt);
				}
				request.setAttribute("cartList", l);
				break;
			}
		}

		return "cart";

	}

	/**
	 * 
	 * 
	 * 在未登录状态下修改商品数量并写入cookie
	 */
	@RequestMapping("/cart/update/num/{itemId}/{number}.action")
	@ResponseBody
	public String changeNumber(@PathVariable String itemId, @PathVariable Integer number, HttpServletRequest request,
			HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (COOKIE_NAME.equals(c.getName())) {
				String value = c.getValue();
				HashMap<String, Integer> jsonToPojo = JsonUtils.jsonToPojo(value, HashMap.class);
				if (jsonToPojo.containsKey(itemId)) {

					jsonToPojo.put(itemId, number);
					String objectToJson = JsonUtils.objectToJson(jsonToPojo);
					CookieUtils.setCookie(request, response, COOKIE_NAME, objectToJson, COOKIE_AGE);

					break;
				}
			}
		}

		return "cart";
	}

	/**
	 * 
	 * 在未登录状态下删除cookie中的商品
	 * 
	 */
	@RequestMapping("/cart/delete/{id}")
	public String deleteItem(@PathVariable String id, HttpServletRequest request, HttpServletResponse reponse) {

		if (!StringUtils.isBlank(id)) {
			Cookie[] cookies = request.getCookies();

			for (Cookie c : cookies) {

				if (COOKIE_NAME.equals(c.getName())) {
					String string = c.getValue();
					HashMap<String, Integer> jsonToPojo = JsonUtils.jsonToPojo(string, HashMap.class);

					jsonToPojo.remove(id);
					String objectToJson = JsonUtils.objectToJson(jsonToPojo);
					CookieUtils.setCookie(request, reponse, COOKIE_NAME, objectToJson, COOKIE_AGE);
					break;
				}
			}

		}

		return "redirect:/cart/cart.html";
	}

}
