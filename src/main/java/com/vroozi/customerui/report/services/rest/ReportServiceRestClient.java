package com.vroozi.customerui.report.services.rest;

import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.Report;


public interface ReportServiceRestClient {

  public Report getDataForSpendByCategory(String unitId, String fromDate, String toDate);
  
  public CategorySpendReport getCategorySpending(String unitId, String fromDate, String toDate);
  
  public CategorySpendReport getSpendByCategory(String unitId, String userId, String fromDate, String toDate, int startItem, int numItems);

  public CategorySpendReport getSpendByCategory(String unitId, String fromDate, String toDate, int startItem, int numItems); 

  public Report getDataForSpendBySupplier(String unitId, String fromDate, String toDate, String supplierId, int startItem, int numItems);
  
  public Report getDataForInternalExternalChart(String unitId, String fromDate, String toDate);
  
  public Report getDataForSpendByMonthChart(String unitId, String fromDate, String toDate);

}
