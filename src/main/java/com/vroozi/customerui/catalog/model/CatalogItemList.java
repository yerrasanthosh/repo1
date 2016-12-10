package com.vroozi.customerui.catalog.model;

import java.io.Serializable;
import java.util.List;

import com.vroozi.customerui.catalogItem.model.Item;
import com.vroozi.customerui.catalogItem.model.OciCatalogItemProxy;

public class CatalogItemList implements Serializable{
	private int size;
	private List<Item> dataList;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<Item> getDataList() {
		return dataList;
	}
	public void setDataList(List<Item> dataList) {
		this.dataList = dataList;
	}
	
	
} 
