package com.vroozi.customerui.appConfig.service;

import com.vroozi.customerui.appConfig.service.rest.AppConfigRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vroozi.customerui.appConfig.model.AppConfig;


/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 12/17/12:12:28 PM
 */
@Component
public class AppConfigServiceImpl implements AppConfigService {
    private final static Logger LOG = LoggerFactory.getLogger(AppConfigServiceImpl.class);

    @Autowired
    AppConfigRestClient appConfigRestClient;

    @Override
    public AppConfig getAppConfig(String unitId) {
        return appConfigRestClient.getAppConfig(unitId);
    }

    @Override
    public AppConfig getAppConfig() {
        return appConfigRestClient.getAppConfig();
    }
}
