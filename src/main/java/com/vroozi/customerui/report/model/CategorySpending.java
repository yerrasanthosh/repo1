package com.vroozi.customerui.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class CategorySpending {
  private String id;
  private String unitId;
  private String userId;
  private Date date;
  private BigDecimal total;
  private int count;
  private String currencyCode;
  private String catalogCategoryCode;
  private String companyCategoryCode;
  private String companyLabel;

  public CategorySpending() {
  }

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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getCatalogCategoryCode() {
    return catalogCategoryCode;
  }

  public void setCatalogCategoryCode(String catalogCategoryCode) {
    this.catalogCategoryCode = catalogCategoryCode;
  }

  public String getCompanyCategoryCode() {
    return companyCategoryCode;
  }

  public void setCompanyCategoryCode(String companyCategoryCode) {
    this.companyCategoryCode = companyCategoryCode;
  }

  public String getCompanyLabel() {
    return companyLabel;
  }

  public void setCompanyLabel(String companyLabel) {
    this.companyLabel = companyLabel;
  }

  @Override
  public String toString() {
    return "CategorySpending [id=" + id + ", unitId=" + unitId + ", userId=" + userId + ", date="
        + date + ", total=" + total + ", count=" + count + ", currencyCode=" + currencyCode
        + ", catalogCategoryCode=" + catalogCategoryCode + ", companyCategoryCode="
        + companyCategoryCode + ", companyLabel=" + companyLabel + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((catalogCategoryCode == null) ? 0 : catalogCategoryCode.hashCode());
    result = prime * result + ((companyCategoryCode == null) ? 0 : companyCategoryCode.hashCode());
    result = prime * result + ((companyLabel == null) ? 0 : companyLabel.hashCode());
    result = prime * result + count;
    result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((total == null) ? 0 : total.hashCode());
    result = prime * result + ((unitId == null) ? 0 : unitId.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CategorySpending other = (CategorySpending) obj;
    if (catalogCategoryCode == null) {
      if (other.catalogCategoryCode != null)
        return false;
    } else if (!catalogCategoryCode.equals(other.catalogCategoryCode))
      return false;
    if (companyCategoryCode == null) {
      if (other.companyCategoryCode != null)
        return false;
    } else if (!companyCategoryCode.equals(other.companyCategoryCode))
      return false;
    if (companyLabel == null) {
      if (other.companyLabel != null)
        return false;
    } else if (!companyLabel.equals(other.companyLabel))
      return false;
    if (count != other.count)
      return false;
    if (currencyCode == null) {
      if (other.currencyCode != null)
        return false;
    } else if (!currencyCode.equals(other.currencyCode))
      return false;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (total == null) {
      if (other.total != null)
        return false;
    } else if (!total.equals(other.total))
      return false;
    if (unitId == null) {
      if (other.unitId != null)
        return false;
    } else if (!unitId.equals(other.unitId))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

}
