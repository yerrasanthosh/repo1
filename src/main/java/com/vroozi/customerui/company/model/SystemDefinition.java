/**
 * 
 */
package com.vroozi.customerui.company.model;

/**
 * @author Mamoon Habib
 *
 */
public class SystemDefinition {

    private String systemId;
    private String systemName;
    private String unitId;
    private CommunicationMethod communicationMethod;
    private String uniqueSystemId;
    
    public SystemDefinition() {
    }

    public SystemDefinition(String systemName, String unitId,
            CommunicationMethod communicationMethod, String uniqueSystemId) {
        super();
        this.systemName = systemName;
        this.unitId = unitId;
        this.communicationMethod = communicationMethod;
        this.uniqueSystemId = uniqueSystemId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public CommunicationMethod getCommunicationMethod() {
        return communicationMethod;
    }

    public void setCommunicationMethod(CommunicationMethod communicationMethod) {
        this.communicationMethod = communicationMethod;
    }

    public String getUniqueSystemId() {
        return uniqueSystemId;
    }

    public void setUniqueSystemId(String uniqueSystemId) {
        this.uniqueSystemId = uniqueSystemId;
    }
}
