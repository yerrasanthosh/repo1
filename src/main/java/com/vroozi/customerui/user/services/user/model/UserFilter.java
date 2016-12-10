package com.vroozi.customerui.user.services.user.model;

import com.vroozi.customerui.acl.model.Role;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author mhabib
 *
 */
public class UserFilter {

	private List<Role> roles = new ArrayList<Role>();
	private boolean active = true;
	private boolean inactive = true;

	private String searchText = null;
	private String sortField = null;
	private boolean sortAscending = true;

	private int pageSize = 8;
	private int pageNumber = 0;

	private List<String> excludedUserIds = null;
	private List<Integer> adminProfiles = null;
	private List<String> includedUserIds = null;
	private String createdBy;
	
    public UserFilter() {
	}
    
    
    
    
    
    
	public List<String> getIncludedUserIds() {
		return includedUserIds;
	}






	public void setIncludedUserIds(List<String> includedUserIds) {
		this.includedUserIds = includedUserIds;
	}






	public String getCreatedBy() {
		return createdBy;
	}






	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<String> getExcludedUserIds() {
		return excludedUserIds;
	}

	public void setExcludedUserIds(List<String> excludedUserIds) {
		this.excludedUserIds = excludedUserIds;
	}

	public List<Integer> getAdminProfiles() {
		return adminProfiles;
	}

	public void setAdminProfiles(List<Integer> adminProfiles) {
		this.adminProfiles = adminProfiles;
	}
	
}
