package com.vroozi.customerui.report.services;

import com.vroozi.customerui.catalog.model.CatalogExportTask;
import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.Report;


public interface ReportService {

  Report getDataForSpendByCategory(String unitId, String fromDate, String toDate);

  CategorySpendReport getCategorySpending(String unitId, String fromDate, String toDate);
  
  CategorySpendReport getSpendByCategory(String unitId, String fromDate, String toDate,
      int startItem, int numItems);

  CategorySpendReport getSpendByCategory(String unitId, String userId, String fromDate,
      String toDate, int startItem, int numItems);

  Report getDataForSpendBySupplier(String unitId, String fromDate, String toDate, String supplierId,
      int startItem, int numItems);

  Report getDataForInternalExternalChart(String unitId, String fromDate, String toDate);

  Report getDataForSpendByMonthChart(String unitId, String fromDate, String toDate);

  CatalogExportTask downloadAllCatalogs(CatalogExportTask catalogExportTask);
}
