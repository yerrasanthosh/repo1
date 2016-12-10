package com.vroozi.customerui.property.controller;

import static com.vroozi.customerui.util.Consts.PROPERTY_DETAIL_SECTION;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.property.model.PropertyProxy;
import com.vroozi.customerui.property.service.PropertyService;
import com.vroozi.customerui.user.services.user.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;

/**
 * User: Administrator
 * Date: 9/12/12
 * Time: 2:24 PM
  */
@Controller
public class PropertyUIController {

    @Autowired
    PropertyService propertyService;

    private final Logger LOGGER = LoggerFactory
            .getLogger(PropertyUIController.class);

    @RequestMapping(value = "/createProperty", method = RequestMethod.POST)
    public String createProperty(HttpServletRequest request,
                                    @ModelAttribute PropertyProxy property, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
        try {
            propertyService.createProperty(property);
            List<PropertyProxy> catalogProperties = propertyService.getPropertiesByCatalogId(property.getCatalogId());
            //catalogProperties.add(property);
            modelMap.addAttribute("catalogProperties", (Object) catalogProperties);
        } catch (Exception exp) {
            LOGGER.error("Error creating profile ", exp);
        }
        return PROPERTY_DETAIL_SECTION;
    }
}
