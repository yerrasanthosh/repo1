package com.vroozi.customerui.catalog.model;

import java.io.Serializable;

/**
 * User: Muhammad Salman Nafees
 * Date: 9/28/12
 * Time: 19:06
 */

public class FieldFilter implements Serializable {

	private String type;
	private String search;
	private String status;
	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
