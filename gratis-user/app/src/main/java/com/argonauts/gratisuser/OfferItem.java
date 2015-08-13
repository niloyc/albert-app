package com.argonauts.gratisuser;

import android.graphics.drawable.Drawable;

public class OfferItem {
	private String name, location;
	private Drawable icon;

	public OfferItem(String name, String location, Drawable icon) {
		this.name = name;
		this.location = location;
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location){
		this.location  = location;
	}

	public String getLocation(){
		return location;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon){
		this.icon = icon;
	}
}
