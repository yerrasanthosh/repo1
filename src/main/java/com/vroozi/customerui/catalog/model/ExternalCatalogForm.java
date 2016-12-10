package com.vroozi.customerui.catalog.model;

/**
 * User: Administrator
 * Date: 9/10/12
 * Time: 12:00 PM
 */
public class ExternalCatalogForm extends CreateCatalogForm{

    private String productDetailView;
    private String contractNumber;
    private String contractLineItem;
    private String method;

    public String getProductDetailView() {
        return productDetailView;
    }

    public void setProductDetailView(String productDetailView) {
        this.productDetailView = productDetailView;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractLineItem() {
        return contractLineItem;
    }

    public void setContractLineItem(String contractLineItem) {
        this.contractLineItem = contractLineItem;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
