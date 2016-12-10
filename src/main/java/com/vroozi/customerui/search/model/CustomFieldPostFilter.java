package com.vroozi.customerui.search.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 1/31/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomFieldPostFilter {
    private String customFieldId;
    private String customFieldName;
    private List<String> customFieldValues = new ArrayList<String>();

    public String getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(String customFieldId) {
        this.customFieldId = customFieldId;
    }

    public String getCustomFieldName() {
        return customFieldName;
    }

    public void setCustomFieldName(String customFieldName) {
        this.customFieldName = customFieldName;
    }

    public List<String> getCustomFieldValues() {
        return customFieldValues;
    }

    public void setCustomFieldValues(List<String> customFieldValues) {
        this.customFieldValues = customFieldValues;
    }
}
