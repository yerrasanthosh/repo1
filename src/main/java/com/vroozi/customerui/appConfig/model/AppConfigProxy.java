package com.vroozi.customerui.appConfig.model;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 12/17/12:1:47 PM
 */
public class AppConfigProxy {
    private String id;
    private String unitId;
    private String appId;
    private String groupId;
    private String releaseNum;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getReleaseNum() {
        return releaseNum;
    }
    public void setReleaseNum(String releaseNum) {
        this.releaseNum = releaseNum;
    }

    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

}
