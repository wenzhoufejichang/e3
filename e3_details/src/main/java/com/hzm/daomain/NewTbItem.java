package com.hzm.daomain;

/**
 * 
 * 
 * 对从数据获取的商品类进行包装只获取image字段中的一条图片信息
 */
public class NewTbItem extends TbItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] images;

	public NewTbItem(TbItem ti) {
		// TODO Auto-generated constructor stub

		setId(ti.getId());
		setImage(ti.getImage());
		setPrice(ti.getPrice());
		setTitle(ti.getTitle());
		setImages(ti.getImage());
		setSellPoint(ti.getSellPoint());
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String image) {

		this.images = image.split(",");

	}

}
