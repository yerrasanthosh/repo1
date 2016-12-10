package com.vroozi.customerui.search.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 2/1/13
 * Time: 11:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompanyAttribute implements Serializable {
    private String attributeName;
    private String count;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
