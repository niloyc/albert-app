package com.company.mypaymentapplication.qrcode;

import java.util.List;

public class Order {
	private String id;
	private String customer_id;
	private boolean paid;
	private List<OrderItem> items;

	public Order(String customer_id, boolean paid, List<OrderItem> items) {
		this.customer_id = customer_id;
		this.paid = paid;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

}
