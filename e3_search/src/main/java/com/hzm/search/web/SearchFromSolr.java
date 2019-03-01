package com.hzm.search.web;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hzm.pojo.EasyUiPager;
import com.hzm.pojo.SolrPoje;

import e3_solr_interface.SolrToWeb;

/**
 * 
 * 商品搜索表现层
 */
@Controller
public class SearchFromSolr {
	@Value("${rows}")
	private Integer rows;
	@Resource(name = "solrToWebImpl")
	private SolrToWeb sw;

	/**
	 * 
	 * 
	 * 从solr索引库搜索商品
	 * 
	 * @param keyword
	 *                    关键词
	 * 
	 * @param m
	 *                    返回视图层 用model封装数据
	 * 
	 * @param page
	 *                    默认从第一页开始显示
	 */

	@RequestMapping("/search")
	public String search(String keyword, @RequestParam(defaultValue = "1") Integer page, Model m) {
		// 0 1 3
		// 3 2 3
		// 6 3 3

		try {
			keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
			Integer start = (page - 1) * rows;
			EasyUiPager<SolrPoje> formSolr = sw.formSolr(keyword, start, rows);
			int totalPages = (int) Math.ceil(formSolr.getTotal().doubleValue() / rows);
			m.addAttribute("recourdCount", formSolr.getTotal());
			m.addAttribute("totalPages", totalPages);
			m.addAttribute("itemList", formSolr.getRows());
			m.addAttribute("query", keyword);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "search";
	}

}
