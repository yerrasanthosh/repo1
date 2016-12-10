package com.vroozi.customerui.catalog.model;

public class ExternalCatalogPost {
    private String catalogName;
    private String catalogSourcePath;
    private String catalogOrigin;
    private int catalogSubmitter;
    String catalogImagesPath;

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    private int supplierId;


    private int unitId;

    String catalogId;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogSourcePath() {
        return catalogSourcePath;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public void setCatalogSourcePath(String catalogSourcePath) {
        this.catalogSourcePath = catalogSourcePath;
    }

    public String getCatalogOrigin() {
        return catalogOrigin;
    }

    public void setCatalogOrigin(String catalogOrigin) {
        this.catalogOrigin = catalogOrigin;
    }

    public int getCatalogSubmitter() {
        return catalogSubmitter;
    }

    public void setCatalogSubmitter(int catalogSubmitter) {
        this.catalogSubmitter = catalogSubmitter;
    }

    public String getCatalogImagesPath() {
        return catalogImagesPath;
    }

    public void setCatalogImagesPath(String catalogImagesPath) {
        this.catalogImagesPath = catalogImagesPath;
    }
}
