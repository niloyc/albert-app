package com.argonauts.gratisuser.util;

public class Customer {
	private String name;
	private int points;
	private String id;

	public Customer(String id, String name, int points) {
		this.name = name;
		this.points = points;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
