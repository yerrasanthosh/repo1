package com.vroozi.customerui.catalog.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * User: SURYA MANDADAPU
 * Date: 10/24/12
 * Time: 10:41 PM
 */
public class ExternalCatalogFields implements Serializable {
    private int sequence;
    private String name;
    private String value;
    private String dynamicValue;

    public int getSequence() {
        return sequence;
    }
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDynamicValue() {
        return dynamicValue;
    }

    public void setDynamicValue(String dynamicValue) {
        this.dynamicValue = dynamicValue;
    }

    public static class SequenceComparator implements Comparator<ExternalCatalogFields> {
        @Override
        public int compare(ExternalCatalogFields e1, ExternalCatalogFields e2) {
            return e1.getSequence()-e2.getSequence();
        }
    }
}
