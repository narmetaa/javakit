package com.hswiggy.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="additem",schema="emp")
public class FoodItempojo {

	
	
@Id

@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemid;
	private String  itemname;
	private String price;
	private String qty;
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	private  String typeofitem;
	
	
	
	
	
	
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQty() { 
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getTypeofitem() {
		return typeofitem;
	}
	public void setTypeofitem(String typeofitem) {
		this.typeofitem = typeofitem;
	}
	
	
	
	
	
	
	
	
}
