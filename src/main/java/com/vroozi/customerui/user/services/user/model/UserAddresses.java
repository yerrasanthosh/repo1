package com.vroozi.customerui.user.services.user.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * User: msundell
 * Date: 4/23/13
 * Time: 2:28 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAddresses implements Serializable {
    private static final long serialVersionUID = 5176809238003261716L;

    private String shipping;
    private String company;
    private String[] custom;
    
    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String[] getCustom() {
        return custom;
    }

    public void setCustom(String[] custom) {
        this.custom = custom;
    }
}
