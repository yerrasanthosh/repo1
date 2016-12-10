package com.vroozi.customerui.uom.model;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Tahir on 6/13/14.
 */
public class UnitOfMeasureMapping implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String mappingId;
    private String uniqueSystemId;
    private List<String> contentViews;
    private String supplierUnit;
    private String companyUnit;
    private String comments;
    private String description;
    private boolean display;
    private String unitId;
    private String contentViewsToDisplay;

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getUniqueSystemId() {
        return uniqueSystemId;
    }

    public void setUniqueSystemId(String uniqueSystemId) {
        this.uniqueSystemId = uniqueSystemId;
    }

    public String getSupplierUnit() {
        return supplierUnit;
    }

    public void setSupplierUnit(String supplierUnit) {
        this.supplierUnit = supplierUnit;
    }

    public String getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(String companyUnit) {
        this.companyUnit = companyUnit;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getContentViews() {
      return this.contentViews.toString();
    }

    public void setContentViews(List<String> contentViews) {
      this.contentViews = contentViews;
    }
    public String getContentViewsToDisplay() {
      return contentViewsToDisplay;
    }

    public void setContentViewsToDisplay(String contentViewsToDisplay) {
      this.contentViewsToDisplay = contentViewsToDisplay;
    }
    
}