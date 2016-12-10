package com.vroozi.customerui.catalog.model;

import java.util.Date;

/**
 * @author Tahir
 * @date 7/13/2015
 */
public class CatalogExportTask {

  private String unitId;
  private Boolean singleFile;
  private String submitterId;
  private Date createdOn;
  private Date lastUpdated;
  private String status;
  private String errorMsg;


  public CatalogExportTask(){

  }
  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public Boolean getSingleFile() {
    return singleFile;
  }

  public void setSingleFile(Boolean singleFile) {
    this.singleFile = singleFile;
  }

  public String getSubmitterId() {
    return submitterId;
  }

  public void setSubmitterId(String submitterId) {
    this.submitterId = submitterId;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}
