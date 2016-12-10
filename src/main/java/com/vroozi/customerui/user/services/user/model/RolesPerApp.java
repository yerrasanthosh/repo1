package com.vroozi.customerui.user.services.user.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 5/7/13
 * Time: 2:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class RolesPerApp implements Serializable {
    private static final long serialVersionUID = -3231957488989909342L;
    private List<String> adminRoles;
    private List<String> purchaseManagerRoles;


    public List<String> getAdminRoles() {
        return adminRoles;
    }

    public void setAdminRoles(List<String> adminRoles) {
        this.adminRoles = adminRoles;
    }

    public List<String> getPurchaseManagerRoles() {
        return purchaseManagerRoles;
    }

    public void setPurchaseManagerRoles(List<String> purchaseManagerRoles) {
        this.purchaseManagerRoles = purchaseManagerRoles;
    }
}
