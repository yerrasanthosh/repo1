package com.vroozi.customerui.report.services.rest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.Report;
import com.vroozi.customerui.util.RestServiceUrl;

@Component
public class ReportServiceRestCilentImpl implements ReportServiceRestClient {

  @Autowired
  RestServiceUrl restServiceUrl;

  @Override
  public Report getDataForSpendByCategory(String unitId, String fromDate, String toDate) {
    Report spendByCategory = null;
    if (StringUtils.isNotBlank(unitId)) {
      spendByCategory = new RestTemplate()
          .getForObject(restServiceUrl.getReportingApiURI() + restServiceUrl.getSpendByCategoryURI()
              + "?startDate=" + fromDate + "&endDate=" + toDate, Report.class, unitId);
    }
    return spendByCategory;
  }

  @Override
  public CategorySpendReport getCategorySpending(String unitId, String fromDate, String toDate) {
    CategorySpendReport spendByCategory = null;
    if (StringUtils.isNotBlank(unitId)) {
      spendByCategory =
          new RestTemplate().getForObject(
              restServiceUrl.getReportingApiURI() + restServiceUrl.getCategorySpendingURI()
                  + "?startDate=" + fromDate + "&endDate=" + toDate +"&startItem=0&numItems=0",
              CategorySpendReport.class, unitId);
    }
    return spendByCategory;
  }
  
  @Override
  public CategorySpendReport getSpendByCategory(String unitId, String fromDate, String toDate,
      int startItem, int numItems) {
    CategorySpendReport spendByCategory = null;
    if (StringUtils.isNotBlank(unitId)) {
      spendByCategory = new RestTemplate().getForObject(restServiceUrl.getReportingApiURI()
          + restServiceUrl.getSpendTotalByCategoryURI() + "?startDate=" + fromDate + "&endDate="
          + toDate + "&startItem=" + startItem + "&numItems=" + numItems, CategorySpendReport.class,
          unitId);
    }
    return spendByCategory;
  }

  @Override
  public CategorySpendReport getSpendByCategory(String unitId, String userId, String fromDate,
      String toDate, int startItem, int numItems) {
    CategorySpendReport spendByCategory = null;
    if (StringUtils.isNotBlank(unitId)) {
      spendByCategory = new RestTemplate().getForObject(
          restServiceUrl.getReportingApiURI()
              + restServiceUrl.getSpendTotalByShopperAndCategoryURI() + "?startDate=" + fromDate
              + "&endDate=" + toDate + "&startItem=" + startItem + "&numItems=" + numItems,
          CategorySpendReport.class, unitId, userId);
    }
    return spendByCategory;
  }

  @Override
  public Report getDataForSpendBySupplier(String unitId, String fromDate, String toDate,
      String supplierId, int startItem, int numItems) {
    Report spendBySupplier = null;
    if (StringUtils.isNotBlank(unitId)) {
      String url = restServiceUrl.getReportingApiURI() + restServiceUrl.getSpendBySupplierURI()
          + "?fromDate=" + fromDate + "&toDate=" + toDate + "&startItem=" + startItem + "&numItems="
          + numItems;
      if (supplierId != null) {
        url += "&supplierId=" + supplierId;
      }
      spendBySupplier = new RestTemplate().getForObject(url, Report.class, unitId);
    }
    return spendBySupplier;
  }

  @Override
  public Report getDataForInternalExternalChart(String unitId, String fromDate, String toDate) {
    Report spendingByCatalogType = new Report();
    if (StringUtils.isNotBlank(unitId)) {
      spendingByCatalogType = new RestTemplate().getForObject(
          restServiceUrl.getReportingApiURI() + restServiceUrl.getSpendByCatalogTypeURI()
              + "?startDate=" + fromDate + "&endDate=" + toDate,
          Report.class, unitId);
    }
    return spendingByCatalogType;
  }

  @Override
  public Report getDataForSpendByMonthChart(String unitId, String fromDate, String toDate) {
    Report spendingByCatalogType = new Report();
    if (StringUtils.isNotBlank(unitId)) {
      spendingByCatalogType = new RestTemplate().getForObject(restServiceUrl.getReportingApiURI()
          + restServiceUrl.getSpendByMonthURI() + "?startDate=" + fromDate + "&endDate=" + toDate,
          Report.class, unitId);
    }
    return spendingByCatalogType;
  }
}
