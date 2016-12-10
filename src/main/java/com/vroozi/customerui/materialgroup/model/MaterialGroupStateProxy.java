package com.vroozi.customerui.materialgroup.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 10/17/12
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class MaterialGroupStateProxy implements Serializable {
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
