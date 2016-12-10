package com.vroozi.customerui.supplier.model;

import java.io.Serializable;



public class RecordsPerPage implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String value;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
    
    
}
