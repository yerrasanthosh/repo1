package com.vroozi.customerui.supplier.model;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 10/17/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorMappingStateProxy {
    private String companyCode;
    private String supplierMappingProcessState;
    private boolean validFile = true;
    private String processId;
    private boolean processFailed = false;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSupplierMappingProcessState() {
        return supplierMappingProcessState;
    }

    public void setSupplierMappingProcessState(String supplierMappingProcessState) {
        this.supplierMappingProcessState = supplierMappingProcessState;
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
