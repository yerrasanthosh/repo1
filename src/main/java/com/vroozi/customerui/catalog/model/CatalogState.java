package com.vroozi.customerui.catalog.model;

/**
 * @author Muhammad Haris
 * @date 9/30/2016
 */
public enum CatalogState {
  WORKING(1), APPROVED(2), REJECTED(3), PUBLISHED(4);

  private int stateId;

  CatalogState(int stateId) {
    this.stateId = stateId;
  }

  public int getStateId() {
    return stateId;
  }

}
