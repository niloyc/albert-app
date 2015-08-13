package com.argonauts.gratis;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private String name;
	private String id;
	private String parent;
	private String image_url;
	private int quantity;
	private double price;
	private boolean is_cat;

	public Item(String id, String name, String image_url, int quantity, double price, boolean is_cat, String parent) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.is_cat = is_cat;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean getIs_cat(){
		return is_cat;
	}

	public void setIs_cat(boolean is_cat){
		this.is_cat = is_cat;
	}

	public String getParent(){
		return parent;
	}

	public void setParent(String parent){
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean is_cat() {
		return is_cat;
	}
}
