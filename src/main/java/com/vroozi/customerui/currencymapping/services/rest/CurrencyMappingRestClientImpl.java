/**
 *
 */
package com.vroozi.customerui.currencymapping.services.rest;

import com.vroozi.customerui.currencymapping.controller.CurrencyMappingService;
import com.vroozi.customerui.currencymapping.model.CurrencyMapping;
import com.vroozi.customerui.currencymapping.model.CurrencyMappingPost;
import com.vroozi.customerui.currencymapping.model.CurrencyMappingStatus;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RestServiceUrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Tahir on 6/13/14.
 */

@Component
public class CurrencyMappingRestClientImpl implements CurrencyMappingService {

  private final Logger LOGGER = LoggerFactory.getLogger(CurrencyMappingRestClientImpl.class);

  @Autowired
  RestServiceUrl restServiceUrl;


  @Override
  public void uploadCurrencyMapping(CurrencyMappingPost currencyMappingPost) {
    new RestTemplate()
        .postForLocation(restServiceUrl.currencyMappingUploadURI(), currencyMappingPost);
  }

  @Override
  public CurrencyMappingStatus getCurrencyMappingStatus(String unitId) {
    try {
      CurrencyMappingStatus
          mappingStatus =
          new RestTemplate()
              .getForObject(restServiceUrl.currencyMappingStatusURI(), CurrencyMappingStatus.class,
                            unitId);
      return mappingStatus;
    } catch (Exception e) {
      LOGGER.error("ERROR WHILE CHECKING PROFILE GROUP STATUS...", e);
    }
    return null;
  }


  @Override
  public Page<CurrencyMapping> getPagedCurrencyMapping(String unitId, int pageNumber,
                                                       int pageSize, String searchTerm) {
    Long
        count =
        new RestTemplate()
            .getForObject(restServiceUrl.currencyMappingCountURI(), Long.class, unitId, searchTerm);
    List<CurrencyMapping>
        list =
        (List<CurrencyMapping>) new RestTemplate()
            .getForObject(restServiceUrl.currencyMappingPageURI(), List.class, unitId, pageNumber,
                          pageSize, searchTerm);
    Page<CurrencyMapping> page = new Page<CurrencyMapping>();
    page.setPageItems(list);
    page.setPageNumber(pageNumber);
    page.setTotalRecords(count);
    long pageCount = count / pageSize;
    if (count > pageSize * pageCount) {
      pageCount++;
    }
    page.setPagesAvailable((int) pageCount);
    return page;
  }

  @Override
  public void downloadMappings(String unitid, String path) throws Exception {
    new RestTemplate().getForObject(
        restServiceUrl.downloadCurrencyMappingURI() + "/unitid/" + unitid + "?filename=" + path,
        Integer.class);
  }
}
