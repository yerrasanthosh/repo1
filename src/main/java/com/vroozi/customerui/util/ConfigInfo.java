package com.vroozi.customerui.util;

import com.vroozi.customerui.config.AppConfig;
import org.springframework.stereotype.Component;

public class ConfigInfo {
    AppConfig appConfig;

    // Default constructor
    public ConfigInfo(){
    }

    public ConfigInfo(AppConfig appConfig){
        this.appConfig = appConfig;
    }

    public String getFileUploadPath(){
        return appConfig.fileUploadPath;
    }
}
