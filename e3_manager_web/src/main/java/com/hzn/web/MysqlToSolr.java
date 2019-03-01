package com.hzn.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzm.pojo.Message;

import e3_solr_interface.SolrInterface;

/**
 * 
 * 
 * solr索引库维护表现层
 */
@Controller
public class MysqlToSolr {

	@Resource(name = "solrInterfaceImpl")
	private SolrInterface sf;

	/**
	 * 
	 * 将所有商品存入solr集群
	 */
	@RequestMapping(value = "/index/item/import", method = RequestMethod.POST)
	@ResponseBody
	public Message getSolrFromMysql() {

		Message mysql = sf.getFromMysql();

		return mysql;
	}
}
