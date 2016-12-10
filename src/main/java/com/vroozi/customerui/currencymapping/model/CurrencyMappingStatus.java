package com.vroozi.customerui.currencymapping.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyMappingStatus implements Serializable {

  /**
   * Created by Tahir on 6/13/14.
   */
  private static final long serialVersionUID = 1L;
  private String unitId;
  private String currencyMappingProcessState;
  private boolean validFile = true;
  private String processId;
  private boolean processFailed = false;
  private String message;


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public boolean isValidFile() {
    return validFile;
  }

  public void setValidFile(boolean validFile) {
    this.validFile = validFile;
  }

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  public boolean isProcessFailed() {
    return processFailed;
  }

  public void setProcessFailed(boolean processFailed) {
    this.processFailed = processFailed;
  }


  public String getCurrencyMappingProcessState() {
    return currencyMappingProcessState;
  }

  public void setCurrencyMappingProcessState(String currencyMappingProcessState) {
    this.currencyMappingProcessState = currencyMappingProcessState;
  }
}
