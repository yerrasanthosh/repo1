package com.vroozi.customerui.supplier.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: rashidha
 * Date: 9/22/12
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorMappingGroupForm implements Serializable {
    private CommonsMultipartFile vendorMappingFile;

//    public CommonsMultipartFile getVendorMappingFile() {
//        return vendorMappingFile;
//    }
//
//    public void setVendorMappingFile(CommonsMultipartFile vendorMappingFile) {
//        this.vendorMappingFile = vendorMappingFile;
//    }

    public CommonsMultipartFile getVendorMappingFile() {
        return vendorMappingFile;
    }

    public void setVendorMappingFile(CommonsMultipartFile vendorMappingFile) {
        this.vendorMappingFile = vendorMappingFile;
    }
}
