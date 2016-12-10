package com.vroozi.customerui.company.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Announcements {

	private int id;
	private int unitId;
    private String announcements;
    private List<String> mailingList;
    private String type;
    private String userid;
    private Date date;
    private boolean updated;
    private String formatedDate;

    private String viewStatus;
    private Set<String> filterUsers;
    
    
    
    public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}
    
    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getAnnouncements() {
		return announcements;
	}
	public void setAnnouncements(String announcements) {
		this.announcements = announcements;
	}
	public List<String> getMailingList() {
		return mailingList;
	}
	public void setMailingList(List<String> mailingList) {
		this.mailingList = mailingList;
	}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public String getFormatedDate() {
        return formatedDate;
    }

    public void setFormatedDate(String formatedDate) {
        this.formatedDate = formatedDate;
    }

	public Set<String> getFilterUsers() {
		return filterUsers;
	}

	public void setFilterUsers(Set<String> filterUsers) {
		this.filterUsers = filterUsers;
	}
}
