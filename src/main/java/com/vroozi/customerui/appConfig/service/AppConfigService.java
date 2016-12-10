package com.vroozi.customerui.appConfig.service;

import com.vroozi.customerui.appConfig.model.AppConfig;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 12/17/12:12:27 PM
 */
public interface AppConfigService {
    AppConfig getAppConfig(String unitId);
    AppConfig getAppConfig();
}
