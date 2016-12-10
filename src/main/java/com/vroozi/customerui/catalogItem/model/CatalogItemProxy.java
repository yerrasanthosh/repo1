package com.vroozi.customerui.catalogItem.model;

import java.io.Serializable;

public abstract class CatalogItemProxy implements Serializable {
    private String catalogItemId;
    private String catalogId;

    public String getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
}
