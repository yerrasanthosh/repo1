package com.vroozi.customerui.search.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.search.model.SearchResults;
import com.vroozi.customerui.search.services.SearchService;
import com.vroozi.customerui.util.RestServiceUrl;

/**
 * User: qureshit
 */
@Component
public class SearchServiceRestClientImpl implements SearchService{

    @Autowired
    private RestServiceUrl restServiceUrl;

    @Override
    public SearchResults searchOnUnitId(String keyword, int unitId,String userId, Integer pageNumber, Integer recordsPerPage) {
    	String searchServicePath = restServiceUrl.geSearchServiceSearchByUnitIdPath()+"&userId={userId}";
    	if(pageNumber!=null) {
    		searchServicePath += "&pageNumber="+pageNumber;
    	}
    	if(recordsPerPage!=null) {
    		searchServicePath += "&recordsPerPage="+recordsPerPage;
    	}
        SearchResults searchResults = null;

        try {
            searchResults = new RestTemplate().getForObject(searchServicePath, SearchResults.class, unitId, keyword,userId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return searchResults;
    }
}
