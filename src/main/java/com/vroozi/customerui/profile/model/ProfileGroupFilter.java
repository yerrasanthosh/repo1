package com.vroozi.customerui.profile.model;

/**
 * 
 * @author mhabib
 *
 */
public class ProfileGroupFilter {

	private boolean active = true;
	private boolean inactive = true;
	private String searchText = null;
	private String sortField = null;
	private boolean sortAscending = true;
	private int pageSize = 8;
	private int pageNumber = 0;

    public ProfileGroupFilter() {
	}
    
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean isSortAscending() {
		return sortAscending;
	}

	public void setSortAscending(boolean ascending) {
		this.sortAscending = ascending;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	
}
