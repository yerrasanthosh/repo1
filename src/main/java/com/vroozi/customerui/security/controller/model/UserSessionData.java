package com.vroozi.customerui.security.controller.model;

import java.util.Date;

/**
 * User: SURYA MANDADAPU
 * Date: 11/14/12
 * Time: 2:39 PM
 */
public class UserSessionData {

    private String id;
    private String userName;
    private String jSessionId;
    private Date lastLoginTimeStamp;
    private String unitId;
    private String originUri;
    private String originUriParams;
    private String lastLoginCGRoupToken;

    public String getOriginUri() {
        return originUri;
    }

    public void setOriginUri(String originUri) {
        this.originUri = originUri;
    }

    public String getOriginUriParams() {
        return originUriParams;
    }

    public void setOriginUriParams(String originUriParams) {
        this.originUriParams = originUriParams;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }

    public Date getLastLoginTimeStamp() {
        return lastLoginTimeStamp;
    }

    public void setLastLoginTimeStamp(Date lastLoginTimeStamp) {
        this.lastLoginTimeStamp = lastLoginTimeStamp;
    }

	public String getLastLoginCGRoupToken() {
		return lastLoginCGRoupToken;
	}

	public void setLastLoginCGRoupToken(String lastLoginCGRoupToken) {
		this.lastLoginCGRoupToken = lastLoginCGRoupToken;
	}
}

