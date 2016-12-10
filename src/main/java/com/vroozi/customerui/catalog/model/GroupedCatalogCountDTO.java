package com.vroozi.customerui.catalog.model;

/**
 * @author Muhammad Haris
 * @date 9/29/2016
 */
public class GroupedCatalogCountDTO {
  private int catalogTypeId;
  private int catalogStateId;
  private String catalogStatus;
  private Double count;

  public int getCatalogTypeId() {
    return catalogTypeId;
  }

  public void setCatalogTypeId(int catalogTypeId) {
    this.catalogTypeId = catalogTypeId;
  }

  public int getCatalogStateId() {
    return catalogStateId;
  }

  public void setCatalogStateId(int catalogStateId) {
    this.catalogStateId = catalogStateId;
  }

  public String getCatalogStatus() {
    return catalogStatus;
  }

  public void setCatalogStatus(String catalogStatus) {
    this.catalogStatus = catalogStatus;
  }

  public Double getCount() {
    return count;
  }

  public void setCount(Double count) {
    this.count = count;
  }
}
