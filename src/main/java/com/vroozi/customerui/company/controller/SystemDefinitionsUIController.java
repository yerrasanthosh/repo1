/**
 * 
 */
package com.vroozi.customerui.company.controller;

import static com.vroozi.customerui.util.Consts.FAILURE;
import static com.vroozi.customerui.util.Consts.SYSTEM_DEFINITION_TABLE_FRAMGMENT;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.model.SystemDefinition;
import com.vroozi.customerui.company.service.SystemDefinitionService;
import com.vroozi.customerui.user.services.user.model.User;


/**
 * @author Mamoon Habib
 *
 */
@Controller
public class SystemDefinitionsUIController {
    private final Logger LOGGER = LoggerFactory.getLogger(SystemDefinitionsUIController.class);

    @Autowired
    SystemDefinitionService systemDefService;

    @RequestMapping("/system-definitions")
    public String systemDefinitions(HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        try {
            
            List<SystemDefinition> systemDefinitions = systemDefService.getSystemDefinitions(user.getUnitId());
            modelMap.addAttribute("systemDefinitions", systemDefinitions);

            return SYSTEM_DEFINITION_TABLE_FRAMGMENT;
        } catch (Exception e) {
            LOGGER.error("Company Settings:",e);
            return FAILURE;
        }
    }
    
    @RequestMapping("/system-definitions/name/{systemname}")
    public @ResponseBody String getSystemDefinitionsIdByName(HttpServletRequest request, HttpServletResponse response, 
            @PathVariable("systemname")String systemName) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        try {
            
            List<SystemDefinition> systemDefinitions = systemDefService.findSystemDefinitions(user.getUnitId(), systemName);

            if(systemDefinitions !=null && systemDefinitions.size()>0) {
                return systemDefinitions.get(0).getSystemId();
            }
        } catch (Exception e) {
            LOGGER.error("Company Settings:",e);
        }
        return "";
    }
    
    @RequestMapping(value = "/system-definitions", method = RequestMethod.POST)
    public String addUpdateSystemDefinition(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("user") SystemDefinition systemDefinition, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            systemDefinition.setUnitId(user.getUnitId());
            if(systemDefinition.getSystemId()!=null && systemDefinition.getSystemId().length()>0) {
                systemDefService.updateSystemDefinition(systemDefinition);
            } else {
                systemDefService.addSystemDefinition(systemDefinition);
            }
            
            List<SystemDefinition> systemDefinitions = systemDefService.getSystemDefinitions(user.getUnitId());
            modelMap.addAttribute("systemDefinitions", systemDefinitions);
            return SYSTEM_DEFINITION_TABLE_FRAMGMENT;
        } catch (Exception exp) {
            LOGGER.error("Error removing catalog approvers ", exp);
            return FAILURE;
        }
    }
    
   @RequestMapping(value = "/system-definitions/{systemids}", method = RequestMethod.DELETE)
    public String removeCatalogApprovers(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("systemids") String systemIdStr, ModelMap modelMap) {
        try {
            String[] ids = systemIdStr.split(",");
            List<String> systemIds = Arrays.asList(ids);
            
            User user = SessionDataRetriever.getLoggedInUser(request);
            systemDefService.removeSystemDefinition(systemIds);
            
            List<SystemDefinition> systemDefinitions = systemDefService.getSystemDefinitions(user.getUnitId());
            modelMap.addAttribute("systemDefinitions", systemDefinitions);
            return SYSTEM_DEFINITION_TABLE_FRAMGMENT;
        } catch (Exception exp) {
            LOGGER.error("Error removing catalog approvers ", exp);
            return FAILURE;
        }
    }


}
