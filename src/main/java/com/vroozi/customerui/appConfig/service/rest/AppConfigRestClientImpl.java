package com.vroozi.customerui.appConfig.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.util.RestServiceUrl;
import com.vroozi.customerui.appConfig.model.AppConfig;
import com.vroozi.customerui.exception.AdminUIException;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 12/17/12:2:08 PM
 */
public class AppConfigRestClientImpl implements AppConfigRestClient{
    private final Logger logger = LoggerFactory.getLogger(AppConfigRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;

    @Override
    public AppConfig getAppConfig(String unitId) {

        AppConfig appConfig = null;

        try {
            appConfig =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/appconfig/organizations/" + unitId + "/apps/adminui/groups/0", AppConfig.class);

        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }

        return appConfig;
    }

    @Override
    public AppConfig getAppConfig() {

        AppConfig appConfig = null;

        try {
            appConfig =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/appconfig/apps/adminui/groups/0", AppConfig.class);

        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }

        return appConfig;
    }
}
