package com.vroozi.customerui.security.controller.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: SURYA MANDADAPU
 * Date: 2/8/13
 * Time: 2:18 PM
 */
public class VrooziCookie {
    public String userId;
    public String unitId;
    private String username;
    private int recordsPerPage;
    private String hookUrl;
    private String cGroupToken;
    public String firstAccess = String.valueOf(System.currentTimeMillis());
    public String lastAccess = String.valueOf(System.currentTimeMillis());
    public String noImageLogo;
    public String systemId;
    public String clientId;
    public String uuid;
    public String token;
    public String userType;

    public VrooziCookie() {
    }

    public VrooziCookie(String userId,String unitId){
        this.userId = userId;
        this.unitId =unitId;
    }
    @JsonProperty("userType")
    public String getUserType() {
        return userType;
    }
    @JsonProperty("userType")
    public void setUserType(String userType) {
        this.userType = userType;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }
    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }
    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("unitId")
    public String getUnitId() {
        return unitId;
    }

    @JsonProperty("unitId")
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @JsonProperty("firstAccess")
    public String getFirstAccess() {
        return firstAccess;
    }

    @JsonProperty("firstAccess")
    public void setFirstAccess(String firstAccess) {
        this.firstAccess = firstAccess;
    }

    @JsonProperty("lastAccess")
    public String getLastAccess() {
        return lastAccess;
    }

    @JsonProperty("lastAccess")
    public void setLastAccess(String lastAccess) {
        this.lastAccess = lastAccess;
    }

    @JsonProperty("cGroupToken")
    public String getcGroupToken() {
        return cGroupToken;
    }

    @JsonProperty("cGroupToken")
    public void setcGroupToken(String cGroupToken) {
        this.cGroupToken = cGroupToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public String getHookUrl() {
        return hookUrl;
    }

    public void setHookUrl(String hookUrl) {
        this.hookUrl = hookUrl;
    }

    public String getNoImageLogo() {
        return noImageLogo;
    }

    public void setNoImageLogo(String noImageLogo) {
        this.noImageLogo = noImageLogo;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
