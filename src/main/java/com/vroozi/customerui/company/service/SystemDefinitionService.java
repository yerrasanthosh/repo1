package com.vroozi.customerui.company.service;

import java.util.List;

import com.vroozi.customerui.company.model.SystemDefinition;

public interface SystemDefinitionService {
    List<SystemDefinition> getSystemDefinitions(String unitId);
    List<SystemDefinition> findSystemDefinitions(String unitId, String name);
    SystemDefinition findSystemDefinitionByUniqueId(String unitId, String uniqueSystemId);
    SystemDefinition addSystemDefinition(SystemDefinition def);
    SystemDefinition updateSystemDefinition(SystemDefinition systemDefinition);
    void removeSystemDefinition(List<String> ids);
}
