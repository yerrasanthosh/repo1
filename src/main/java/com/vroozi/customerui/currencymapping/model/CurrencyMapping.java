package com.vroozi.customerui.currencymapping.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tahir on 6/13/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyMapping implements Serializable {


  private static final long serialVersionUID = 1L;


  private String mappingId;
  private String uniqueSystemId;
  private String uniqueSupplierId;
  private String supplierCurrency;
  private String companyCurrency;
  private String comments;
  private String description;
  private boolean display;
  private String unitId;
  private List<String> contentViews;
  private String contentViewsToDisplay;
  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

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

  public String getUniqueSupplierId() {
    return uniqueSupplierId;
  }

  public void setUniqueSupplierId(String uniqueSupplierId) {
    this.uniqueSupplierId = uniqueSupplierId;
  }

  public String getSupplierCurrency() {
    return supplierCurrency;
  }

  public void setSupplierCurrency(String supplierCurrency) {
    this.supplierCurrency = supplierCurrency;
  }

  public String getCompanyCurrency() {
    return companyCurrency;
  }

  public void setCompanyCurrency(String companyCurrency) {
    this.companyCurrency = companyCurrency;
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

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public List<String> getContentViews() {
    return this.contentViews;
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