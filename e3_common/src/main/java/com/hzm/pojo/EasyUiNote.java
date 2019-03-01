package com.hzm.pojo;

import java.io.Serializable;

public class EasyUiNote implements Serializable{

	//
	// CREATE TABLE `tb_item_cat` (
	// `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类目ID',
	// `parent_id` bigint(20) DEFAULT NULL COMMENT '父类目ID=0时，代表的是一级的类目',
	// `name` varchar(50) DEFAULT NULL COMMENT '类目名称',
	// `status` int(1) DEFAULT '1' COMMENT '状态。可选值:1(正常),2(删除)',
	// `sort_order` int(4) DEFAULT NULL COMMENT
	// '排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数',
	// `is_parent` tinyint(1) DEFAULT '1' COMMENT '该类目是否为父类目，1为true，0为false',
	// `created` datetime DEFAULT NULL COMMENT '创建时间',
	// `updated` datetime DEFAULT NULL COMMENT '创建时间',
	// PRIMARY KEY (`id`),
	// KEY `parent_id` (`parent_id`,`status`) USING BTREE,
	// KEY `sort_order` (`sort_order`)
	// ) ENGINE=InnoDB AUTO_INCREMENT=1183 DEFAULT CHARSET=utf8 COMMENT='商品类目'

	private Long id;
	private String text;
	private String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
