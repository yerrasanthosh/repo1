package com.vroozi.customerui.profile.model;

import java.io.Serializable;

public class Profile extends ProfileProxy implements Serializable {

    private boolean attached;
    private boolean previouslyAttached;
    private String rating;
    private String activeStr;
    
    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public boolean isPreviouslyAttached() {
        return previouslyAttached;
    }

    public void setPreviouslyAttached(boolean previouslyAttached) {
        this.previouslyAttached = previouslyAttached;
    }

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getActiveStr() {
		return activeStr;
	}

	public void setActiveStr(String activeStr) {
		this.activeStr = activeStr;
	}

}
