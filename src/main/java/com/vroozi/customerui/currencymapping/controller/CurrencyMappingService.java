package com.vroozi.customerui.currencymapping.controller;

import com.vroozi.customerui.currencymapping.model.CurrencyMapping;
import com.vroozi.customerui.currencymapping.model.CurrencyMappingPost;
import com.vroozi.customerui.currencymapping.model.CurrencyMappingStatus;
import com.vroozi.customerui.util.Page;

/**
 * Created by Tahir on 6/6/14.
 */
public interface CurrencyMappingService {

  void uploadCurrencyMapping(CurrencyMappingPost currencyMappingPost);

  CurrencyMappingStatus getCurrencyMappingStatus(String unitId);

  Page<CurrencyMapping> getPagedCurrencyMapping(String unitId, int pageNumber, int pageSize,
                                                String searchTerm);

  void downloadMappings(String unitId, String fileName) throws Exception;
}
