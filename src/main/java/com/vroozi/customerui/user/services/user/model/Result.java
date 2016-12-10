package com.vroozi.customerui.user.services.user.model;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

  private List<T> dataItems = new ArrayList<>();

  private Pagination pagination;

  private List<DataError> errors = new ArrayList<>();

  private boolean success;

  public void addDataItem(T item) {
    dataItems.add(item);
  }

  public List<T> getDataItems() {
    return dataItems;
  }

  public void setDataItems(List<T> dataItems) {
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
