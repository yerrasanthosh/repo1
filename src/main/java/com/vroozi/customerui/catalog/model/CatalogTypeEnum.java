/**
 *
 */
package com.vroozi.customerui.catalog.model;

/**
 * Represents Catalog Types
 *
 * @author Muhammad Haris
 */
public enum CatalogTypeEnum {

  EXTERNAL("1", "External Catalog"), INTERNAL("2", "Internal Catalog"), QUOTE("4", "Quote");

  private String typeId;
  private String typeName;

  private CatalogTypeEnum(String typeId, String typeName) {
    this.typeId = typeId;
    this.typeName = typeName;
  }

  public String getTypeId() {
    return typeId;
  }

  public String getTypeName() {
    return typeName;
  }
}
