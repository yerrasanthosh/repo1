package com.vroozi.customerui.search.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 2/2/13
 * Time: 2:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class ItemAttribute implements Serializable {
    private String attributeName;
    private String attributeIcon;
    private String attributeWeight;
    private String count;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeIcon() {
        return attributeIcon;
    }

    public void setAttributeIcon(String attributeIcon) {
        this.attributeIcon = attributeIcon;
    }

    public String getAttributeWeight() {
        return attributeWeight;
    }

    public void setAttributeWeight(String attributeWeight) {
        this.attributeWeight = attributeWeight;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
