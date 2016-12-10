package com.vroozi.customerui.home.controller;

import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.home.service.HomeService;
import com.vroozi.customerui.home.service.model.SummaryCard;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.ViewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.vroozi.customerui.util.Consts.HOME_PAGE;
import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;

/**
 * User: SURYA MANDADAPU
 * Date: 10/27/12
 * Time: 2:31 PM
 */
@Controller
public class HomeController {

    @Autowired
    HomeService homeService;

    @RequestMapping(value = "/organization/{unitid}/summary" ,  method = RequestMethod.GET )
    public @ResponseBody
    SummaryCard  summaryCard(HttpServletRequest request, HttpServletResponse response, @PathVariable("unitid")String unitid){
    	
        User user = SessionDataRetriever.getLoggedInUser(request);
        return homeService.getSummary(user);
    }
    
    
    
    
    
}
