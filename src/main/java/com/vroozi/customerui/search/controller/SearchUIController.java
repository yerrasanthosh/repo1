package com.vroozi.customerui.search.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.search.model.SearchResultVO;
import com.vroozi.customerui.search.model.SearchResults;
import com.vroozi.customerui.search.services.SearchService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Page;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 9/24/12
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SearchUIController {
    private final Logger LOGGER = LoggerFactory.getLogger(SearchUIController.class);

    @Autowired
    private SearchService searchService;

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchOnUnitId(@RequestParam(value="keyword", required=true) String keyword,
    		@RequestParam(value="unitid", required=true) String unitId, 
    		@RequestParam(value="pageNumber", required = false) Integer pageNumber,
    		HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
    	User user = SessionDataRetriever.getLoggedInUser(request);
    	Integer recordsPerPage = 10;
    	SearchResults searchResults = searchService.searchOnUnitId(keyword, Integer.parseInt(unitId),user.getUserId(), pageNumber, recordsPerPage);
    	List<SearchResultVO> searchList = new ArrayList<SearchResultVO>();
    	
    	int totalRecords = 0;
    	if(searchResults != null) {
    		searchList = searchResults.getProductOffers();
    		totalRecords = (int) searchResults.getHitsFound();
    	}
    	if(pageNumber == null) {
    		pageNumber = 0;
    	}
    	if(recordsPerPage == null) {
    		recordsPerPage = totalRecords;
    	}

        if (null != searchResults) {
            Page<SearchResultVO> searchPage = new Page<SearchResultVO>(searchList, pageNumber, recordsPerPage, totalRecords);
            modelMap.addAttribute("searchPage", searchPage);
            modelMap.addAttribute("renderTable", true);

        } else {
            modelMap.addAttribute("renderTable", false);

        }
        return "searchResults";

    }
}
