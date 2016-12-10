package com.vroozi.customerui.report.services.impl;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.CategorySpending;
import com.vroozi.customerui.report.model.Report;
import com.vroozi.customerui.util.ExcelWriter;

public class XlsxReportCreatorTest {
  XlsxReportCreator xlsxReportCreator;
  ExcelWriter fwProcessed;

  @Before
  public void setup() {
    XlsxReportCreator xlsxReportCreator1 = new XlsxReportCreator();
    xlsxReportCreator = spy(xlsxReportCreator1);
    doReturn("/tmp").when(xlsxReportCreator).getReportsDir();

    fwProcessed = mock(ExcelWriter.class);
  }

  @Test
  public void testGenerateCategorySpendingReportForValidData() throws IOException {
    // given
    CategorySpendReport spendReport = new CategorySpendReport();
    List<CategorySpending> data = new ArrayList<>(2);
    CategorySpending s1 = new CategorySpending();
    s1.setCatalogCategoryCode("45*");
    s1.setCompanyCategoryCode("45000000");
    s1.setCompanyLabel("Electronics");
    s1.setTotal(new BigDecimal("12.24"));
    data.add(s1);
    CategorySpending s2 = new CategorySpending();
    s2.setCatalogCategoryCode("55*");
    s2.setCompanyCategoryCode("55000000");
    s2.setCompanyLabel("Furniture");
    s2.setTotal(new BigDecimal("42.59"));
    data.add(s2);
    spendReport.setData(data);

    // when
    xlsxReportCreator.generateCategorySpendingReport(fwProcessed, spendReport);

    // then
    String[] header = new String[] {"Row Number", "Category Label", "Company Category Code",
        "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] row1 = new String[] {"1", "Electronics", "45000000", "12.24"};
    verify(fwProcessed).writeRow(row1);
    String[] row2 = new String[] {"2", "Furniture", "55000000", "42.59"};
    verify(fwProcessed).writeRow(row2);
    String[] total = new String[] {"", "Total Amount", "", "54.83"};
    verify(fwProcessed).writeRow(total);
  }

  @Test
  public void testGenerateCategorySpendingReportForUnknownCategory() throws IOException {
    // given
    CategorySpendReport spendReport = new CategorySpendReport();
    List<CategorySpending> data = new ArrayList<>(2);
    CategorySpending s1 = new CategorySpending();
    s1.setCatalogCategoryCode("");
    s1.setCompanyCategoryCode("");
    s1.setCompanyLabel("");
    s1.setTotal(new BigDecimal("12.24"));
    data.add(s1);
    spendReport.setData(data);

    // when
    xlsxReportCreator.generateCategorySpendingReport(fwProcessed, spendReport);

    // then
    String[] header = new String[] {"Row Number", "Category Label", "Company Category Code",
        "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] row1 = new String[] {"1", "Unknown", "", "12.24"};
    verify(fwProcessed).writeRow(row1);
    String[] total = new String[] {"", "Total Amount", "", "12.24"};
    verify(fwProcessed).writeRow(total);
  }

  @Test
  public void testGenerateCategorySpendingReportForEmptyCategory() throws IOException {
    // given
    CategorySpendReport spendReport = new CategorySpendReport();
    List<CategorySpending> data = new ArrayList<>(0);
    spendReport.setData(data);

    // when
    xlsxReportCreator.generateCategorySpendingReport(fwProcessed, spendReport);

    // then
    String[] header = new String[] {"Row Number", "Category Label", "Company Category Code",
        "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] total = new String[] {"", "Total Amount", "", "0.00"};
    verify(fwProcessed).writeRow(total);
  }

  //////////////////////////////////////////////////
  @Test
  public void testGenerateShopperSpendingReportForValidData() throws IOException {
    // given
    CategorySpendReport spendReport = new CategorySpendReport();
    List<CategorySpending> data = new ArrayList<>(2);
    CategorySpending s1 = new CategorySpending();
    s1.setUserId("123");
    s1.setCompanyLabel("Electronics");
    s1.setTotal(new BigDecimal("12.24"));
    data.add(s1);
    CategorySpending s2 = new CategorySpending();
    s2.setUserId("456");
    s2.setCompanyLabel("Furniture");
    s2.setTotal(new BigDecimal("42.59"));
    data.add(s2);
    spendReport.setData(data);

    Map<String, String> userMap = new HashMap<>();
    userMap.put("123", "Admin");
    userMap.put("456", "Shopper");

    // when
    xlsxReportCreator.generateShopperSpendingReport(fwProcessed, userMap, spendReport);

    // then
    String[] header =
        new String[] {"Row Number", "User Name", "Category Code", "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] row1 = new String[] {"1", "Admin", "Electronics", "12.24"};
    verify(fwProcessed).writeRow(row1);
    String[] row2 = new String[] {"2", "Shopper", "Furniture", "42.59"};
    verify(fwProcessed).writeRow(row2);
  }

  @Test
  public void testGenerateShopperSpendingReportForUnknownCategory() throws IOException {
    // given
    CategorySpendReport spendReport = new CategorySpendReport();
    List<CategorySpending> data = new ArrayList<>(2);
    CategorySpending s1 = new CategorySpending();
    s1.setUserId("123");
    s1.setCompanyLabel("");
    s1.setTotal(new BigDecimal("12.24"));
    data.add(s1);
    spendReport.setData(data);

    Map<String, String> userMap = new HashMap<>();
    userMap.put("123", "Admin");

    // when
    xlsxReportCreator.generateShopperSpendingReport(fwProcessed, userMap, spendReport);

    // then
    String[] header =
        new String[] {"Row Number", "User Name", "Category Code", "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] row1 = new String[] {"1", "Admin", "Unknown", "12.24"};
    verify(fwProcessed).writeRow(row1);
  }

  @Test
  public void testGenerateShopperSpendingReportForEmptyCategory() throws IOException {
    // given
    CategorySpendReport spendReport = new CategorySpendReport();
    List<CategorySpending> data = new ArrayList<>(0);
    spendReport.setData(data);

    Map<String, String> userMap = new HashMap<>();

    // when
    xlsxReportCreator.generateShopperSpendingReport(fwProcessed, userMap, spendReport);

    // then
    String[] header =
        new String[] {"Row Number", "User Name", "Category Code", "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] total = new String[] {"", "", "", "0"};
    verify(fwProcessed).writeRow(total);
  }

  ///////////////////////////////////////////////////////
  @Test
  public void testGenerateSupplierSpendingReportForValidData() throws IOException {
    // given
    Report spendReport = new Report();
    Map<String, Double> data = new LinkedHashMap<>();
    data.put("Electronics", new Double(12.24));
    data.put("Furniture", new Double(42.59));
    spendReport.setData(data);

    // when
    xlsxReportCreator.generateSupplierSpendingReport(fwProcessed, spendReport);

    // then
    String[] header = new String[] {"Row Number", "Supplier Name", "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] row1 = new String[] {"1", "Electronics", "12.24"};
    verify(fwProcessed).writeRow(row1);
    String[] row2 = new String[] {"2", "Furniture", "42.59"};
    verify(fwProcessed).writeRow(row2);
  }

  @Test
  public void testGenerateSupplierSpendingReportForUnknownCategory() throws IOException {
    // given
    Report spendReport = new Report();
    Map<String, Double> data = new HashMap<>();
    data.put("", new Double(12.24));
    spendReport.setData(data);

    // when
    xlsxReportCreator.generateSupplierSpendingReport(fwProcessed, spendReport);

    // then
    String[] header = new String[] {"Row Number", "Supplier Name", "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] row1 = new String[] {"1", "Unknown", "12.24"};
    verify(fwProcessed).writeRow(row1);
  }

  @Test
  public void testGenerateSupplierSpendingReportForEmptyCategory() throws IOException {
    // given
    Report spendReport = new Report();
    Map<String, Double> data = new HashMap<>();
    spendReport.setData(data);

    // when
    xlsxReportCreator.generateSupplierSpendingReport(fwProcessed, spendReport);

    // then
    String[] header = new String[] {"Row Number", "Supplier Name", "Total Sales Amount"};
    verify(fwProcessed).writeHeader(header);
    String[] total = new String[] {"", "", "", "0"};
  }
}
