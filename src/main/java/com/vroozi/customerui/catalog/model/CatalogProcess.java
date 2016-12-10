package com.vroozi.customerui.catalog.model;

import java.util.Date;

public class CatalogProcess {
    private int processId;
    private String catalogName;
    private String catalogOrigin;
    private int fileResourceId;
    private int catalogStateId;
    private int processStateId;
    private int currentStepNo;
    private String processNode;
    private Date startTime;
    private Date endTime;
    private Date lastUpdated;
    private int submitter;
    private int unitId;

    private String catalogId;
    private int revisionTag;
    private int inputRecords;
    private int outputRecords;
    private int failedRecords;
    private int supplierId;
    private String languageCode;
    private Date validFrom; // todo: missing from REST request
    private Date validUntil; // todo: missing from REST request
    private String contractNumber; // todo: how do we get this info?
    private String contractLineItem; // todo: how do we get this info?
    private String ociNewItemExtCategory; // todo: what is this item?



    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
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

    public String getOciNewItemExtCategory() {
        return ociNewItemExtCategory;
    }

    public void setOciNewItemExtCategory(String ociNewItemExtCategory) {
        this.ociNewItemExtCategory = ociNewItemExtCategory;
    }

    public int getInputRecords() {
        return inputRecords;
    }

    public int getFailedRecords() {
        return failedRecords;
    }

    public void setFailedRecords(int failedRecords) {
        this.failedRecords = failedRecords;
    }

    public int getOutputRecords() {
        return outputRecords;
    }

    public void setOutputRecords(int outputRecords) {
        this.outputRecords = outputRecords;
    }

    public void setInputRecords(int inputRecords) {
        this.inputRecords = inputRecords;

    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogOrigin() {
        return catalogOrigin;
    }

    public void setCatalogOrigin(String catalogOrigin) {
        this.catalogOrigin = catalogOrigin;
    }

    public int getFileResourceId() {
        return fileResourceId;
    }

    public void setFileResourceId(int fileResourceId) {
        this.fileResourceId = fileResourceId;
    }

    public int getCatalogStateId() {
        return catalogStateId;
    }

    public void setCatalogStateId(int catalogStateId) {
        this.catalogStateId = catalogStateId;
    }

    public int getProcessStateId() {
        return processStateId;
    }

    public void setProcessStateId(int processStateId) {
        this.processStateId = processStateId;
    }

    public int getCurrentStepNo() {
        return currentStepNo;
    }

    public void setCurrentStepNo(int currentStepNo) {
        this.currentStepNo = currentStepNo;
    }

    public String getProcessNode() {
        return processNode;
    }

    public void setProcessNode(String processNode) {
        this.processNode = processNode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getSubmitter() {
        return submitter;
    }

    public void setSubmitter(int submitter) {
        this.submitter = submitter;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public int getRevisionTag() {
        return revisionTag;
    }

    public void setRevisionTag(int revisionTag) {
        this.revisionTag = revisionTag;
    }
}
