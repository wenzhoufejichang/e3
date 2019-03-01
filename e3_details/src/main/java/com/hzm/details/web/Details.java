package com.hzm.details.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzm.daomain.NewTbItem;
import com.hzm.daomain.TbItem;
import com.hzm.daomain.TbItemDesc;
import com.hzm.service.Iteminterface;

@Controller
public class Details {
	@Resource(name = "itemimpl")
	private Iteminterface iif;

	@RequestMapping("/item/{id}")
	public String getItemById(@PathVariable Long id, Model m) {

		TbItem item = iif.getByid(id);
		TbItemDesc desc = iif.getBtid(id);
		NewTbItem ntm=new NewTbItem(item);
		
		m.addAttribute("item", ntm);
		m.addAttribute("itemDesc", desc);
		return "item";

	}

}
