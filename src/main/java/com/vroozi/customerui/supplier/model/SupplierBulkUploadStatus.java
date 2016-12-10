package com.vroozi.customerui.supplier.model;



import java.io.Serializable;

public class SupplierBulkUploadStatus implements Serializable {
    private String companyCode;
    private String materialGroupMappingProcessState;
    private boolean validFile = true;
    private String processId;
    private boolean processFailed = false;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getMaterialGroupMappingProcessState() {
        return materialGroupMappingProcessState;
    }

    public void setMaterialGroupMappingProcessState(String materialGroupMappingProcessState) {
        this.materialGroupMappingProcessState = materialGroupMappingProcessState;
    }

    public boolean isValidFile() {
        return validFile;
    }

    public void setValidFile(boolean validFile) {
        this.validFile = validFile;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public boolean isProcessFailed() {
        return processFailed;
    }

    public void setProcessFailed(boolean processFailed) {
        this.processFailed = processFailed;
    }
}
