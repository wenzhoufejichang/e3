package com.hzm.pojo;

import java.io.Serializable;

public class SolrPoje implements Serializable {

	// field column="id" name="id" />
	// <field column="title" name="item_title"/>
	// <field column="price" name="item_price"/>
	// <field column="cid" name="item_cid"/>
	// <field column="sell_point" name="item_sell_point"/>
	// <field column="image" name="item_image" />
	// <field column="name" name="item_cid_name" />

	/**
	 * 
	 */
	private static final long serialVersionUID = -8331454735192979189L;
	private Long id;
	private String title;
	private Long price;
	private Integer cid;
	private String sell_point;
	private String image;
	private String name;
	private String images;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getSell_point() {
		return sell_point;
	}

	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImages() {

		String image2 = getImage();
		if (image2.matches(".+,http://.+")) {
			String[] split = image2.split(",");
			images = split[0];
		} else {
			images = image2;
		}

		return images;
	}

	// public void setImages(String images) {
	//
	//
	//
	// this.images = images;
	// }

}
