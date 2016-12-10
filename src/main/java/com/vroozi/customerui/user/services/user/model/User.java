package com.vroozi.customerui.user.services.user.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends UserProxy {

    private int rowsPerPage;
    private UserDetail details;
    private String createdByName="";
    
    
    //private String createdByName;
    
    public User() {
        rowsPerPage = 8;
    }
    
    public void setValueObject(User bean) {
        this.setUsername(bean.getUsername());
        this.setUnitId(bean.getUnitId());
        this.setFullName(bean.getFullName());
        this.setPassword(bean.getPassword());
        this.setUserId(bean.getUserId());
    }

    public boolean isLoggedIn() {
        //return CatalogManagerUIServlet.validateSession(userId, sessionId);
        return true;
    }


    public UserDetail getDetails() {
            return details;
    }
    public void setDetails(UserDetail details) {
            this.details = details;
    }

    public int getRowsPerPage() {
            return rowsPerPage;
    }
    public void setRowsPerPage(int rowsPerPage) {
            this.rowsPerPage = rowsPerPage;
    }

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
    
    
    

//    // PROPERTY: createdByName
//    public String getCreatedByName() {
//        return createdByName;
//    }
//    public void setCreatedByName(String createdByName) {
//        this.createdByName = createdByName;
//    }
}
