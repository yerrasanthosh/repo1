package com.vroozi.customerui.appConfig.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.vroozi.customerui.appConfig.service.AppConfigService;
import com.vroozi.customerui.appConfig.model.AppConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 12/17/12:12:34 PM
 */
@Controller
public class AppConfigController {
    private final static Logger LOG = LoggerFactory.getLogger(AppConfigController.class);

    @Autowired
    private AppConfigService appConfigService;

    @RequestMapping(value = "/releaseNum" , method = RequestMethod.GET )
    public @ResponseBody String addCatalogApprovers(HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value="company") String univrooziDBConfigtid){

        AppConfig appConfig = null;

        try {
            appConfig = appConfigService.getAppConfig();
        }catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }

        String relNum = "";
        if(appConfig != null){
            relNum = appConfig.getReleaseNum();
        }

        return (StringUtils.isNotEmpty(relNum))? relNum:"";
    }

}
