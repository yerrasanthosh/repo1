package com.vroozi.customerui.supplier.model;

import java.lang.String; /**
 * Created by IntelliJ IDEA.
 * User: rashidha
 * Date: 9/22/12
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorMappingFilePost {
    private String catalogSourcePath;
    private int submitter;
    private int unitId;
    public String getCatalogSourcePath() {
        return catalogSourcePath;
    }
    public void setCatalogSourcePath(String catalogSourcePath) {
        this.catalogSourcePath = catalogSourcePath;
    }
    public int getSubmitter() {
        return submitter;
    }
    public void setSubmitter(int submitter) {
        this.submitter = submitter;
    }
    public int getUnitId() {
        return unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
