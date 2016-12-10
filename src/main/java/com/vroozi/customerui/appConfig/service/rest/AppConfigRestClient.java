package com.vroozi.customerui.appConfig.service.rest;

import com.vroozi.customerui.appConfig.model.AppConfig;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 12/17/12:2:07 PM
 */
public interface AppConfigRestClient {
    AppConfig getAppConfig(String unitId);
    AppConfig getAppConfig();
}
