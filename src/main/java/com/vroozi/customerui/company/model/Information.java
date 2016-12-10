/**
 * 
 */
package com.vroozi.customerui.company.model;

import java.util.Date;
import java.util.List;

/**
 * @author Mamoon Habib
 *
 */
public class Information {
    
    private String id;
    private String unitId;
    private String information;
    private List<String> contentViewGroups;
    private Date date;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }
    public List<String> getContentViewGroups() {
        return contentViewGroups;
    }
    public void setContentViewGroups(List<String> contentViewGroups) {
        this.contentViewGroups = contentViewGroups;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
