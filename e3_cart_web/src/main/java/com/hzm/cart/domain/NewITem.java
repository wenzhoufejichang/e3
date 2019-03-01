package com.hzm.cart.domain;

import com.hzm.daomain.TbItem;

public class NewITem extends TbItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewITem() {
		// TODO Auto-generated constructor stub
	}

	private String[] images;

	public NewITem(TbItem t) {
		// TODO Auto-generated constructor stub
		setId(t.getId());
		setBarcode(t.getBarcode());
		setCid(t.getCid());
		setCreated(t.getCreated());
		setImage(t.getImage());
		setNum(t.getNum());
		setPrice(t.getPrice());
		setSellPoint(t.getSellPoint());
		setStatus(t.getStatus());
		setTitle(t.getTitle());
		setUpdated(t.getUpdated());
		setImages(getImage());

	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String images) {

		String[] split = images.split(",");
		this.images = split;

	}

}
