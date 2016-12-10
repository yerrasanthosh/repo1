package com.vroozi.customerui.currencymapping.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyMappingPost implements Serializable {

  /**
   * Created by Tahir on 6/13/14.
   */
  private static final long serialVersionUID = 1L;

  private String filePath;
  private int submitter;
  private int unitId;

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public int getSubmitter() {
    return submitter;
  }

  public void setSubmitter(int submitter) {
    this.submitter = submitter;
  }

  public int getUnitId() {
    return unitId;
  }

  public void setUnitId(int unitId) {
    this.unitId = unitId;
  }
}
