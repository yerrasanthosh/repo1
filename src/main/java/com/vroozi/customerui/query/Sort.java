package com.vroozi.customerui.query;

public class Sort {
  
  private final String fieldName;
  
  private final Order order;
  
  public Sort(String fieldName, Order order) {
    this.fieldName = fieldName;
    this.order = order;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Order getOrder() {
    return order;
  }

  public static enum Order {
    ASCENDING, DESCENDING
  }

}
