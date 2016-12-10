package com.vroozi.customerui.property.service.rest;

import com.vroozi.customerui.catalog.services.rest.CatalogRestClientImpl;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.property.model.PropertyProxy;
import com.vroozi.customerui.property.service.PropertyService;
import com.vroozi.customerui.util.RestServiceUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * User: Administrator
 * Date: 9/12/12
 * Time: 2:09 PM
 */
public class PropertyServiceImpl implements PropertyService {

    private final Logger LOGGER = LoggerFactory.getLogger(CatalogRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;

    @Override
    public List<PropertyProxy> getPropertiesByCatalogId(String catalogId) throws Exception {
        try {
            PropertyProxy[] propertiesArray =  new RestTemplate().getForObject(restServiceUrl.catalogPropertyURI()+ "/catalogid/"+catalogId, PropertyProxy[].class);
            List<PropertyProxy> properties = Arrays.asList(propertiesArray);
            return properties;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public PropertyProxy createProperty(PropertyProxy property) throws Exception {
        try {
            property = new RestTemplate().postForObject(
                    restServiceUrl.catalogPropertyURI(), property,
                    PropertyProxy.class);
        } catch (RestClientException rse) {
            LOGGER.error("Error saving property!", rse);
            throw rse;
        } catch (Exception exp) {
            LOGGER.error("Error saving property!", exp);
            throw new AdminUIException(exp);
        }
        return property;
    }
}
