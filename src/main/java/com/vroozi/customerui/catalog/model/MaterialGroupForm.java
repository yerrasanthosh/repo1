package com.vroozi.customerui.catalog.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class MaterialGroupForm implements Serializable {
    private CommonsMultipartFile materialGroupFile;
    private CommonsMultipartFile supplierFile;
    private CommonsMultipartFile profileGroupFile;
    private CommonsMultipartFile currencyMappingFile;
    private CommonsMultipartFile uomMappingFile;
    private MultipartFile powerShopperMappingFile;

    public CommonsMultipartFile getMaterialGroupFile() {
        return materialGroupFile;
    }

    public void setMaterialGroupFile(CommonsMultipartFile materialGroupFile) {
        this.materialGroupFile = materialGroupFile;
    }


    public CommonsMultipartFile getSupplierFile() {
        return supplierFile;
    }

    public void setSupplierFile(CommonsMultipartFile supplierFile) {
        this.supplierFile = supplierFile;
    }

    public CommonsMultipartFile getProfileGroupFile() {
        return profileGroupFile;
    }

    public void setProfileGroupFile(CommonsMultipartFile profileGroupFile) {
        this.profileGroupFile = profileGroupFile;
    }

    public CommonsMultipartFile getUomMappingFile() {
        return uomMappingFile;
    }

    public void setUomMappingFile(CommonsMultipartFile uomMappingFile) {
        this.uomMappingFile = uomMappingFile;
    }

    public CommonsMultipartFile getCurrencyMappingFile() {
        return currencyMappingFile;
    }

    public void setCurrencyMappingFile(CommonsMultipartFile currencyMappingFile) {
        this.currencyMappingFile = currencyMappingFile;
    }

    public MultipartFile getPowerShopperMappingFile() {
        return powerShopperMappingFile;
    }

    public void setPowerShopperMappingFile(MultipartFile powerShopperMappingFile) {
        this.powerShopperMappingFile = powerShopperMappingFile;
    }

}