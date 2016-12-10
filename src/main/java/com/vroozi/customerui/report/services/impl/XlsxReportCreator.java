/**
 * 
 */
package com.vroozi.customerui.report.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.CategorySpending;
import com.vroozi.customerui.report.model.Report;
import com.vroozi.customerui.util.ExcelWriter;

/**
 * @author Mamoon Habib
 *
 */
@Component
public class XlsxReportCreator {

  @Autowired
  AppConfig appConfig;

  protected String getReportsDir() {
    return appConfig.errorReportPath.endsWith("/") ? appConfig.errorReportPath
        : appConfig.errorReportPath + "/";
  }

  /**
   * Generates xlsx report for spend by category using given data.
   * 
   * @param unitId
   * @param spendReport Report data
   * @return Absolute path of generated report
   * @throws IOException
   */
  public String generateCategorySpendingReport(String unitId, CategorySpendReport spendReport)
      throws IOException {
    File file = new File(getReportsDir(), "spend-by-category-" + unitId + ".xlsx");
    OutputStream out = new FileOutputStream(file);
    ExcelWriter fwProcessed = new ExcelWriter(out, "spend-by-category");
    generateCategorySpendingReport(fwProcessed, spendReport);
    return file.getAbsolutePath();
  }

  protected void generateCategorySpendingReport(ExcelWriter fwProcessed,
      CategorySpendReport spendReport) throws IOException {
    String[] header = new String[] {"Row Number", "Category Label", "Company Category Code",
        "Total Sales Amount"};
    fwProcessed.writeHeader(header);
    String[] data = null;
    BigDecimal total = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_EVEN);
    int rowId = 1;
    for (CategorySpending category : (List<CategorySpending>) spendReport.getData()) {
      data = new String[4];
      data[0] = "" + rowId++;
      data[1] = category.getCompanyLabel() == null || category.getCompanyLabel().isEmpty()
          ? "Unknown" : category.getCompanyLabel();
      data[2] = category.getCompanyCategoryCode();
      data[3] = category.getTotal().toString();
      total = total.add(category.getTotal());
      fwProcessed.writeRow(data);
    }
    data = new String[4];
    data[0] = "";
    data[1] = "Total Amount";
    data[2] = "";
    data[3] = total.toString();
    fwProcessed.writeRow(data);
    fwProcessed.flushFile(true);
  }

  /**
   * Generates xlsx report for spend by shopper using given data.
   * 
   * @param unitId
   * @param spendReport Report data
   * @return Absolute path of generated report
   * @throws IOException
   */
  public String generateShopperSpendingReport(String unitId, Map<String, String> userMap,
      CategorySpendReport spendReport) throws IOException {
    File file = new File(getReportsDir(), "spend-by-shopper-" + unitId + ".xlsx");
    OutputStream out = new FileOutputStream(file);
    ExcelWriter fwProcessed = new ExcelWriter(out, "spend-by-category");
    generateShopperSpendingReport(fwProcessed, userMap, spendReport);
    return file.getAbsolutePath();
  }

  protected void generateShopperSpendingReport(ExcelWriter fwProcessed, Map<String, String> userMap,
      CategorySpendReport spendReport) throws IOException {
    String[] header =
        new String[] {"Row Number", "User Name", "Category Code", "Total Sales Amount"};

    fwProcessed.writeHeader(header);
    String[] data = null;
    int rowId = 1;
    if (spendReport.getData() == null || spendReport.getData().size() == 0) {
      data = new String[4];
      data[0] = "";
      data[1] = "";
      data[2] = "";
      data[3] = "0";
      fwProcessed.writeRow(data);
    } else {
      for (CategorySpending category : (List<CategorySpending>) spendReport.getData()) {
        data = new String[4];
        data[0] = "" + rowId++;
        data[1] = userMap.get(category.getUserId());
        data[2] = category.getCompanyLabel() == null || category.getCompanyLabel().isEmpty()
            ? "Unknown" : category.getCompanyLabel();
        data[3] = category.getTotal().toString();
        fwProcessed.writeRow(data);
      }

    }
    fwProcessed.flushFile(true);
  }

  /**
   * Generates xlsx report for spend by supplier using given data.
   * 
   * @param unitId
   * @param spendReport Report data
   * @return Absolute path of generated report
   * @throws IOException
   */
  public String generateSupplierSpendingReport(String unitId, Report spendReport)
      throws IOException {
    File file = new File(getReportsDir(), "spend-by-supplier-" + unitId + ".xlsx");
    OutputStream out = new FileOutputStream(file);
    ExcelWriter fwProcessed = new ExcelWriter(out, "spend-by-supplier");
    generateSupplierSpendingReport(fwProcessed, spendReport);

    return file.getAbsolutePath();
  }

  protected void generateSupplierSpendingReport(ExcelWriter fwProcessed, Report spendReport)
      throws IOException {
    String[] header = new String[] {"Row Number", "Supplier Name", "Total Sales Amount"};

    fwProcessed.writeHeader(header);
    String[] data = null;
    int rowId = 1;
    if (spendReport.getData() == null || ((Map) spendReport.getData()).size() == 0) {
      data = new String[3];
      data[0] = "";
      data[1] = "";
      data[2] = "0";
      fwProcessed.writeRow(data);
    } else {
      for (Entry<String, Double> entry : ((Map<String, Double>) spendReport.getData()).entrySet()) {
        data = new String[3];
        data[0] = "" + rowId++;
        data[1] = entry.getKey() == null || entry.getKey().toString().isEmpty() ? "Unknown"
            : entry.getKey().toString();
        data[2] = entry.getValue().toString();
        fwProcessed.writeRow(data);
      }
    }
    fwProcessed.flushFile(false);
  }

}
