package com.vroozi.customerui.profile.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileGroupMapping implements Serializable{

	private static final long serialVersionUID = 1L;
	private String mappingId;
	private int groupId;
	private String groupName;
	private String unitId;
	private String sapUser;
    String uniqueSystemId;
    String comments;
	public String getMappingId() {
		return mappingId;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getSapUser() {
		return sapUser;
	}
	public void setSapUser(String sapUser) {
		this.sapUser = sapUser;
	}
    public String getUniqueSystemId() {
        return uniqueSystemId;
    }
    public void setUniqueSystemId(String uniqueSystemId) {
        this.uniqueSystemId = uniqueSystemId;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
