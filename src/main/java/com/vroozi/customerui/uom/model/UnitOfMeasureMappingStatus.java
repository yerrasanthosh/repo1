package com.vroozi.customerui.uom.model;


import java.io.Serializable;

public class UnitOfMeasureMappingStatus implements Serializable {

    /**
     * Created by Tahir on 6/13/14.
     */
    private static final long serialVersionUID = 1L;
    private String unitId;
    private String uomMappingProcessState;
    private boolean validFile = true;
    private String processId;
    private boolean processFailed = false;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUomMappingProcessState() {
        return uomMappingProcessState;
    }

    public void setUomMappingProcessState(
            String uomMappingProcessState) {
        this.uomMappingProcessState = uomMappingProcessState;
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
