/**
 * 
 */
package com.vroozi.customerui.supplier.model;

/**
 * @author mhabib
 *
 */
public class CurrencyCode {

	private int currencyCodeId;
	private String code;
	private String name;
	private String symbol;
	private String country;
	
	/**
	 * 
	 */
	public CurrencyCode() {
		// TODO Auto-generated constructor stub
	}

	public int getCurrencyCodeId() {
		return currencyCodeId;
	}

	public void setCurrencyCodeId(int currencyCodeId) {
		this.currencyCodeId = currencyCodeId;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
