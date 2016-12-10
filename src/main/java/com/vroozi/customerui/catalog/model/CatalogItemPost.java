package com.vroozi.customerui.catalog.model;

import java.io.Serializable;
import java.util.List;

public class CatalogItemPost implements Serializable{
	private int approved;
	private int rejected;
	private int pending;
	private String newStatus;
	private String catalogId;
	private List<String> itemIds;
	private String userid;
    private boolean reviewFlag;
    private Integer addRating;
    private Integer removeRating;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<String> getItemIds() {
		return itemIds;
	}
	public void setItemIds( List<String> itemIds) {
		this.itemIds = itemIds;
	}
	public String getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public int getApproved() {
		return approved;
	}
	public void setApproved(int approved) {
		this.approved = approved;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
    public boolean isReviewFlag() {
        return reviewFlag;
    }
    public void setReviewFlag(boolean reviewFlag) {
        this.reviewFlag = reviewFlag;
    }
    public Integer getAddRating() {
        return addRating;
    }
    public void setAddRating(Integer addRating) {
        this.addRating = addRating;
    }
    public Integer getRemoveRating() {
        return removeRating;
    }
    public void setRemoveRating(Integer removeRating) {
        this.removeRating = removeRating;
    }
}
