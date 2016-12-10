package com.vroozi.customerui.powershopper;



import java.io.Serializable;

public class ContentShareMappingPost implements Serializable {

    private static final long serialVersionUID = -962660934755469742L;

    private String filePath;
    private String userId;
    private String unitId;

    public ContentShareMappingPost() {}

    public ContentShareMappingPost(String unitId, String userId, String filePath) {
        this.unitId = unitId;
        this.userId = userId;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

}