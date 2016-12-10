package com.vroozi.customerui.search.model;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 1/10/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class SupplierCompany {
    private String supplierId;
    private String supplierName;
    private String supplierImageUrl;



    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierImageUrl() {
        return supplierImageUrl;
    }

    public void setSupplierImageUrl(String supplierImageUrl) {
        this.supplierImageUrl = supplierImageUrl;
    }
}
