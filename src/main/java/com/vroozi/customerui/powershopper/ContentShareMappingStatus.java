package com.vroozi.customerui.powershopper;
import java.io.Serializable;

public class ContentShareMappingStatus implements Serializable {

    private static final long serialVersionUID = 8741186225847687025L;

    private String unitId;
    private String processState;
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

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
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
