package com.vroozi.customerui.profile.model;

import java.io.Serializable;
import java.util.List;



public class ContentAccessDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String groupToken;
	private List<Profile> profiles ;
	public String getGroupToken() {
		return groupToken;
	}
	public void setGroupToken(String groupToken) {
		this.groupToken = groupToken;
	}
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	
}
