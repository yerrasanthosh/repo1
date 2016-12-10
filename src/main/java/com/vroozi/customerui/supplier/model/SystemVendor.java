package com.vroozi.customerui.supplier.model;

import java.io.Serializable;

/**
* Created by IntelliJ IDEA.
* User: rashidha
* Date: 9/19/12
* Time: 7:33 PM
* To change this template use File | Settings | File Templates.
*/
public class SystemVendor implements Serializable {
    private int row;
    private String vendorId;
    private String systemId;
    private String clientId;
    private String createdOn;
    private String unitId;
    private String active;
    private String supplierId;
    private String vendorName;       // will be used to store values in vendor mapping table uploaded from excel sheet.
    private String vendorDuns;       // will be used to store values in vendor mapping table uploaded from excel sheet.


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorDuns() {
        return vendorDuns;
    }

    public void setVendorDuns(String vendorDuns) {
        this.vendorDuns = vendorDuns;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
