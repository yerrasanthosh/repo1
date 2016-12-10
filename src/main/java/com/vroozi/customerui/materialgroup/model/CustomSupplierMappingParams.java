package com.vroozi.customerui.materialgroup.model;

import java.util.List;

public class CustomSupplierMappingParams {
	private Integer unitId;
	private Integer pageNo;
	private Integer pageSize;
	private String searchTerm;
	private String uniqueSupplierId;
	private List<String> supplierIds;
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public String getUniqueSupplierId() {
		return uniqueSupplierId;
	}
	public void setUniqueSupplierId(String uniqueSupplierId) {
		this.uniqueSupplierId = uniqueSupplierId;
	}
	public List<String> getSupplierIds() {
		return supplierIds;
	}
	public void setSupplierIds(List<String> supplierIds) {
		this.supplierIds = supplierIds;
	}
}