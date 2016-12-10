package com.vroozi.customerui.catalog.model;

import java.io.Serializable;

/**
 * User: SURYA MANDADAPU
 * Date: 11/9/12
 * Time: 11:52 AM
 */
public class CatalogItemCollection implements Serializable {

    private CatalogItemCollectionInfo info;
    private CatalogItemCollectionData data;

    public CatalogItemCollectionInfo getInfo() {
        return info;
    }

    public void setInfo(CatalogItemCollectionInfo info) {
        this.info = info;
    }

    public CatalogItemCollectionData getData() {
        return data;
    }

    public void setData(CatalogItemCollectionData data) {
        this.data = data;
    }
}
