/**
 * 
 */
package com.vroozi.customerui.company.service.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.company.model.SystemDefinition;
import com.vroozi.customerui.company.service.SystemDefinitionService;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.util.RestServiceUrl;

/**
 * @author Mamoon Habib
 *
 */
@Component
public class SystermDefinitionRestClientImpl implements SystemDefinitionService {

    private final Logger logger = LoggerFactory.getLogger(SystermDefinitionRestClientImpl.class);
    
    @Autowired
    RestServiceUrl restServiceUrl;

    @Override
    public List<SystemDefinition> getSystemDefinitions(String unitId) {
        try {
            SystemDefinition[] sysDefs =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/system/definitions/unitid/{unitid}", SystemDefinition[].class, unitId);
            return new ArrayList<SystemDefinition>(Arrays.asList(sysDefs));
            
        } catch(RestClientException rse) {
            logger.error("Error retrieving system definitions!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving system definitions!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public List<SystemDefinition> findSystemDefinitions(String unitId,
            String name) {
        try {
            SystemDefinition[] sysDefs =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/system/definitions/unitid/{unitid}/name/{systemname}", SystemDefinition[].class, unitId, name);
            return new ArrayList<SystemDefinition>(Arrays.asList(sysDefs));
            
        } catch(RestClientException rse) {
            logger.error("Error finding system definitions!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error finding system definitions!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public SystemDefinition findSystemDefinitionByUniqueId(String unitId, String uniqueSystemId) {
        try {
            SystemDefinition sysDef =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/system/definitions/unitid/{unitid}/uniqueid/{uniqueid}", SystemDefinition.class, unitId, uniqueSystemId);
            return sysDef;
            
        } catch(RestClientException rse) {
            logger.error("Error finding system definitions!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error finding system definitions!", exp);
            throw new AdminUIException(exp);
        }
    }
    
    @Override
    public SystemDefinition addSystemDefinition(SystemDefinition systemDefinition) {
        try {
            return new RestTemplate().postForObject(restServiceUrl.userDataServiceURI()+"/system/definitions", systemDefinition, SystemDefinition.class);
        } catch(RestClientException rse) {
            logger.error("Error saving system definitions!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error saving system definitions!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public SystemDefinition updateSystemDefinition(SystemDefinition systemDefinition) {
        try {
            new RestTemplate().put(restServiceUrl.userDataServiceURI()+"/system/definitions", systemDefinition, SystemDefinition.class);
            return systemDefinition;
        } catch(RestClientException rse) {
            logger.error("Error saving system definitions!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error saving system definitions!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void removeSystemDefinition(List<String> ids) {
        try {
            String idStr = StringUtils.join(ids, ',');
            new RestTemplate().delete(restServiceUrl.userDataServiceURI() + "/system/definitions/{systemids}", idStr); ;
        } catch(RestClientException rse) {
            logger.error("Error in deleting system definitions!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error in deleting system definitions!", exp);
            throw new AdminUIException(exp);
        }
    }
}
