package com.vroozi.customerui.supplier.model;

import java.util.ArrayList;
import java.util.List;

import com.vroozi.customerui.user.services.user.model.DataError;
import com.vroozi.customerui.user.services.user.model.Pagination;

public class SupplierResult {

  private List<Supplier> dataItems = new ArrayList<>();

  private Pagination pagination;

  private List<DataError> errors = new ArrayList<>();

  boolean success;
  
  public void addDataItem(Supplier item) {
    dataItems.add(item);
  }

  public List<Supplier> getDataItems() {
    return dataItems;
  }

  public void setDataItems(List<Supplier> dataItems) {
    this.dataItems = dataItems;
  }

  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  public void addError(String code, String message) {
    errors.add(new DataError(code, message));
  }

  public List<DataError> getErrors() {
    return errors;
  }

  public void setErrors(List<DataError> errors) {
    this.errors = errors;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

}
