package com.vroozi.customerui.user.services.user.model;

import com.vroozi.customerui.acl.model.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserProxy implements Serializable {
    private static final long serialVersionUID = 4303023283136880563L;    
    
    private String userId;
    private String email;
    private String username;
    private boolean emailAsUsername;
    private String password;
    private String unitId;
    private String fullName;
    private boolean loggedIn;
    private String logoUrl;
    private String sessionId;
    
    private String firstName;
    private String lastName;

    private boolean resetPassword;
    private Date resetPasswordDate;
    private boolean active;
    private String createdBy="";
    private Date createdOn;
    private Date lastLogin;
    private String lastLoginCGRoupToken="";
    private String contentViewGroupToken="";
    private String shopperViewURL="";
    
    private String language;
    private String decimalNotation;
    private String timeZone;
    private String dateFormat;
    private String userStatus;
    private boolean invited;

    private List<Integer> assignedProfiles;
    
    private List<Role> roles;
    private boolean deleted;

    private UserAddresses addresses;
    private String defaultCostCenter;
    private String defaultCurrency;

    private RolesPerApp rolesPerApp;
    private String pin;
    private boolean selected;
    private String defaultApproverId;
    private String nextApproverId;

    private Integer spendLimit;
    private Integer approvalLimit;

    public UserProxy() {
        addresses = new UserAddresses();
    }

    public Integer getSpendLimit() {
        return spendLimit;
    }

    public void setSpendLimit(Integer spendLimit) {
        this.spendLimit = spendLimit;
    }

    public Integer getApprovalLimit() {
        return approvalLimit;
    }

    public void setApprovalLimit(Integer approvalLimit) {
        this.approvalLimit = approvalLimit;
    }

    public String getNextApproverId() {
        return nextApproverId;
    }

    public void setNextApproverId(String nextApproverId) {
        this.nextApproverId = nextApproverId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailAsUsername() {
		return emailAsUsername;
	}

	public void setEmailAsUsername(boolean emailAsUsername) {
		this.emailAsUsername = emailAsUsername;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDecimalNotation() {
        return decimalNotation;
    }

    public void setDecimalNotation(String decimalNotation) {
        this.decimalNotation = decimalNotation;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public List<Integer> getAssignedProfiles() {
        return assignedProfiles;
    }

    public void setAssignedProfiles(List<Integer> assignedProfiles) {
        this.assignedProfiles = assignedProfiles;
    }

    public List<Role> getRoles() {
        return roles;
    }


    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isInvited() {
        return invited;
    }

    public void setInvited(boolean invited) {
        this.invited = invited;
    }

    public UserAddresses getAddresses() {
        return addresses;
    }

    public void setAddresses(UserAddresses addresses) {
        this.addresses = addresses;
    }

    public String getDefaultCostCenter() {
        return defaultCostCenter;
    }

    public void setDefaultCostCenter(String defaultCostCenter) {
        this.defaultCostCenter = defaultCostCenter;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public RolesPerApp getRolesPerApp() {
        return rolesPerApp;
    }

    public void setRolesPerApp(RolesPerApp rolesPerApp) {
        this.rolesPerApp = rolesPerApp;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

	public String getDefaultApproverId() {
		return defaultApproverId;
	}

	public void setDefaultApproverId(String defaultApproverId) {
		this.defaultApproverId = defaultApproverId;
	}

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Date getResetPasswordDate() {
        return resetPasswordDate;
    }

    public void setResetPasswordDate(Date resetPasswordDate) {
        this.resetPasswordDate = resetPasswordDate;
    }

	public String getLastLoginCGRoupToken() {
		return lastLoginCGRoupToken;
	}

	public void setLastLoginCGRoupToken(String lastLoginCGRoupToken) {
		this.lastLoginCGRoupToken = lastLoginCGRoupToken;
	}

    public String getContentViewGroupToken() {
        return contentViewGroupToken;
    }

    public void setContentViewGroupToken(String contentViewGroupToken) {
        this.contentViewGroupToken = contentViewGroupToken;
    }

    public String getShopperViewURL() {
        return shopperViewURL;
    }

    public void setShopperViewURL(String shopperViewURL) {
        this.shopperViewURL = shopperViewURL;
    }
}
