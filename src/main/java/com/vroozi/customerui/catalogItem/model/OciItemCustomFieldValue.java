package com.vroozi.customerui.catalogItem.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * User: SURYA MANDADAPU
 * Date: 8/14/12
 * Time: 10:38 PM
 */
public class OciItemCustomFieldValue implements Serializable {
    private String customFieldId;
    private String priceAdder;
    private String value;
    private String fieldName;
    private String fieldType;
    private boolean active;
    private boolean deleted;
    private Integer displayOrder;


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId) {
        this.customFieldId = customFieldId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public String getPriceAdder() {
        return priceAdder;
    }

    public void setPriceAdder(String priceAdder) {
        this.priceAdder = priceAdder;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
