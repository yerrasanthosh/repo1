package com.vroozi.customerui.supplier.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Provides upload form for Supplier logo.
 *
 * @author <a href="mailto:ara.tatous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/26/12:9:42 AM
 */
public class SupplierLogoForm {
    private String supplierId;
    private CommonsMultipartFile logoFile;

    // PROPERTY: supplierId
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    // PROPERTY: logoFile
    public CommonsMultipartFile getLogoFile() {
        return logoFile;
    }
    public void setLogoFile(CommonsMultipartFile logoFile) {
        this.logoFile = logoFile;
    }


}
