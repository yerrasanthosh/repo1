package com.vroozi.customerui.search.services;

import com.vroozi.customerui.search.model.SearchResults;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 9/24/12
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SearchService {
	SearchResults searchOnUnitId(String keyword, int unitId , String userId, Integer pageNumber, Integer recordsPerPage);
}
