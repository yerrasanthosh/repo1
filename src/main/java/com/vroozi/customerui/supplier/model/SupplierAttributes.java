package com.vroozi.customerui.supplier.model;

import java.util.Date;

public class SupplierAttributes {
    private String attributeId;
    private String attributeName;
    private String attributeDescription;
    private String unitId;
    private boolean active;
    private String createdOn;
    private String createdBy;
    private Date lastUpdated;
    private String attributeImagePath;
    private int boostValue;

    // PROPERTY: attributeName
    public String getAttributeName() {
        return attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    // PROPERTY: attributeId
    public String getAttributeId() {
        return attributeId;
    }
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    // PROPERTY: active
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    // PROPERTY: attributeDescription
    public String getAttributeDescription() {
        return attributeDescription;
    }
    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }

    // PROPERTY: createdOn
    public String getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    // PROPERTY: createdBy
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // PROPERTY: unitId
    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    // PROPERTY: attributeImagePath
    public String getAttributeImagePath() {
        return attributeImagePath;
    }
    public void setAttributeImagePath(String attributeImagePath) {
        this.attributeImagePath = attributeImagePath;
    }

    // PROPERTY: boostValue
    public int getBoostValue() {
        return boostValue;
    }
    public void setBoostValue(int boostValue) {
        this.boostValue = boostValue;
    }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }
}
