package com.vroozi.customerui.report.services.impl;

import com.vroozi.customerui.catalog.model.CatalogExportTask;
import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.Report;
import com.vroozi.customerui.report.services.ReportService;
import com.vroozi.customerui.report.services.rest.ReportServiceRestClient;
import com.vroozi.customerui.util.RestServiceUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Tahir on 4/23/2015.
 */

@Component
public class ReportServiceImpl implements ReportService {


  @Autowired
  ReportServiceRestClient reportServiceRest;

  @Autowired
  RestServiceUrl restServiceUrl;
  
  @Override
  public Report getDataForSpendByCategory(String unitId, String fromDate, String toDate) {
    return reportServiceRest.getDataForSpendByCategory(unitId, fromDate, toDate);
  }
  
  @Override
  public CategorySpendReport getCategorySpending(String unitId, String fromDate, String toDate) {
    return reportServiceRest.getCategorySpending(unitId, fromDate, toDate);
  }
  
  @Override
  public CategorySpendReport getSpendByCategory(String unitId, String fromDate, String toDate,
      int startItem, int numItems) {
    return reportServiceRest.getSpendByCategory(unitId, fromDate, toDate, startItem, numItems);
  }

  @Override
  public CategorySpendReport getSpendByCategory(String unitId, String userId, String fromDate,
      String toDate, int startItem, int numItems) {
    return reportServiceRest.getSpendByCategory(unitId, userId, fromDate, toDate, startItem,
        numItems);
  }
  
  @Override
  public Report getDataForSpendBySupplier(String unitId, String fromDate, String toDate,
      String supplierId, int startItem, int numItems) {
    return reportServiceRest.getDataForSpendBySupplier(unitId, fromDate, toDate, supplierId,
        startItem, numItems);
  }
  
  @Override
  public Report getDataForInternalExternalChart(String unitId, String fromDate, String toDate) {
    return reportServiceRest.getDataForInternalExternalChart(unitId, fromDate, toDate);
  }
  
  @Override
  public Report getDataForSpendByMonthChart(String unitId, String fromDate, String toDate) {
    return reportServiceRest.getDataForSpendByMonthChart(unitId, fromDate, toDate);
  }

	@Override
	public CatalogExportTask downloadAllCatalogs(CatalogExportTask catalogExportTask) {
		return new RestTemplate().postForObject(restServiceUrl.downloadAllCatalogs(), catalogExportTask,
				CatalogExportTask.class);
	}
}
