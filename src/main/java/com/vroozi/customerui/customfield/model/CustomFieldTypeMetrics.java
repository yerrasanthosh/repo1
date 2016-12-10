package com.vroozi.customerui.customfield.model;

import java.io.Serializable;

/**
 * User: Muhammad Salman Nafees
 * Date: 9/26/12
 * Time: 5:36 PM
 */
public class CustomFieldTypeMetrics implements Serializable {
    private int id;
    private String fieldType;
    private int count;
    private int unitId;

    public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
