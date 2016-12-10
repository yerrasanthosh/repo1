package com.vroozi.customerui.report.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.CategorySpending;
import com.vroozi.customerui.report.model.Report;
import com.vroozi.customerui.report.services.ReportService;
import com.vroozi.customerui.report.services.impl.XlsxReportCreator;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;

public class ReportUIControllerTest {

  ReportUIController reportUIController;
  HttpServletRequest request;
  HttpServletResponse response;
  User loggedInUser;
  XlsxReportCreator xlsxReportCreator;

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Before
  public void setup() throws IOException {
    reportUIController = new ReportUIController();
    loggedInUser = new User();
    loggedInUser.setUserId("123");
    loggedInUser.setUnitId("1024");
    loggedInUser.setUsername("abc@vroozi.com");
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    when(request.getSession(false)).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(loggedInUser);
    ServletOutputStream out = mock(ServletOutputStream.class);
    when(response.getOutputStream()).thenReturn(out);

    xlsxReportCreator = mock(XlsxReportCreator.class);
    reportUIController.setXlsxReportCreator(xlsxReportCreator);
  }

  @Test
  public void testGetSpendByShopperReportForAllUsersAndDateRange() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String userId = this.loggedInUser.getUserId();
    String fromDate = "2015-09-12";
    String toDate = "2015-12-12";


    List<User> users = makeUsersList(unitId, userId);
    CategorySpendReport spendReport = makeCategorySpendReport(unitId);

    UserManagementService userService = mock(UserManagementService.class);
    when(userService.getAllUsers(unitId)).thenReturn(users);
    reportUIController.setUserService(userService);

    ReportService reportService = mock(ReportService.class);
    when(reportService.getSpendByCategory(unitId, fromDate, toDate, 0, 0)).thenReturn(spendReport);
    reportUIController.setReportService(reportService);

    ServletOutputStream out = response.getOutputStream();
    Map<String, String> userMap = reportUIController.getUserIdNameMap(unitId, null);

    // when
    reportUIController.getSpendByShopperReport(request, response, null, null, fromDate, toDate);

    // then
    verify(reportService).getSpendByCategory(unitId, fromDate, toDate, 0, 0);
    verify(xlsxReportCreator).generateShopperSpendingReport(unitId, userMap, spendReport);
  }

  @Test
  public void testGetSpendByShopperReportForDateRange() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String userId = this.loggedInUser.getUserId();
    String fromDate = "2015-09-12";
    String toDate = "2015-12-12";

    CategorySpendReport spendReport = makeCategorySpendReport(unitId);

    UserManagementService userService = mock(UserManagementService.class);
    when(userService.getUser(userId)).thenReturn(loggedInUser);
    reportUIController.setUserService(userService);

    ReportService reportService = mock(ReportService.class);
    when(reportService.getSpendByCategory(unitId, userId, fromDate, toDate, 0, 0))
        .thenReturn(spendReport);
    reportUIController.setReportService(reportService);

    ServletOutputStream out = response.getOutputStream();
    Map<String, String> userMap = reportUIController.getUserIdNameMap(unitId, userId);
    // doReturn(true).when(reportUIController).generateCategorySpendingXlsx(out, userMap,
    // spendReport);

    // when
    reportUIController.getSpendByShopperReport(request, response, null, userId, fromDate, toDate);

    // then
    verify(reportService).getSpendByCategory(unitId, userId, fromDate, toDate, 0, 0);
    verify(xlsxReportCreator).generateShopperSpendingReport(unitId, userMap, spendReport);
  }

  @Test
  public void testGetSpendByShopperReportForNoDates() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String userId = this.loggedInUser.getUserId();
    String fromDate = defaultFromDate();
    String toDate = defaultToDate();

    CategorySpendReport spendReport = makeCategorySpendReport(unitId);

    UserManagementService userService = mock(UserManagementService.class);
    when(userService.getUser(userId)).thenReturn(loggedInUser);
    reportUIController.setUserService(userService);

    ReportService reportService = mock(ReportService.class);
    when(reportService.getSpendByCategory(unitId, userId, fromDate, toDate, 0, 0))
        .thenReturn(spendReport);
    reportUIController.setReportService(reportService);

    ServletOutputStream out = response.getOutputStream();
    Map<String, String> userMap = reportUIController.getUserIdNameMap(unitId, userId);

    // when
    reportUIController.getSpendByShopperReport(request, response, null, userId, null, null);

    // then
    verify(reportService).getSpendByCategory(unitId, userId, fromDate, toDate, 0, 0);
    verify(xlsxReportCreator).generateShopperSpendingReport(unitId, userMap, spendReport);
  }

  @Test
  public void testGetSpendByCategoryReportDateRange() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String fromDate = "2015-09-12";
    String toDate = "2015-12-12";


    CategorySpendReport spendReport = makeCategorySpendReport(unitId);
    ReportService reportService = mock(ReportService.class);
    when(reportService.getCategorySpending(unitId, fromDate, toDate)).thenReturn(spendReport);
    reportUIController.setReportService(reportService);

    ServletOutputStream out = response.getOutputStream();

    // when
    reportUIController.getSpendByCategoryReport(request, response, fromDate, toDate);

    // then
    verify(reportService).getCategorySpending(unitId, fromDate, toDate);
    verify(xlsxReportCreator).generateCategorySpendingReport(unitId, spendReport);
  }

  @Test
  public void testGetSpendByCategoryReportForNoDates() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String fromDate = defaultFromDate();
    String toDate = defaultToDate();

    CategorySpendReport spendReport = makeCategorySpendReport(unitId);

    ReportService reportService = mock(ReportService.class);
    when(reportService.getCategorySpending(unitId, fromDate, toDate)).thenReturn(spendReport);
    reportUIController.setReportService(reportService);
    ServletOutputStream out = response.getOutputStream();

    // when
    reportUIController.getSpendByCategoryReport(request, response, fromDate, toDate);

    // then
    verify(reportService).getCategorySpending(unitId, fromDate, toDate);
    verify(xlsxReportCreator).generateCategorySpendingReport(unitId, spendReport);
  }

  @Test
  public void testGetSpendBySupplierReportForAllSuppliersAndDateRange() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String fromDate = "2015-09-12";
    String toDate = "2015-12-12";

    Report spendReport = makeSupplierSpendReport(unitId);

    ReportService reportService = mock(ReportService.class);
    when(reportService.getDataForSpendBySupplier(unitId, fromDate, toDate, null, 0, 0))
        .thenReturn(spendReport);
    reportUIController.setReportService(reportService);

    ServletOutputStream out = response.getOutputStream();

    // when
    reportUIController.getSpendBySupplierReport(request, response, null, null, fromDate, toDate);

    // then
    verify(reportService).getDataForSpendBySupplier(unitId, fromDate, toDate, null, 0, 0);
    verify(xlsxReportCreator).generateSupplierSpendingReport(unitId, spendReport);
  }

  @Test
  public void testGetSpendBySupplierReportForAllUsersAndDateRange() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String supplierId = "457";
    String fromDate = "2015-09-12";
    String toDate = "2015-12-12";

    Report spendReport = makeSupplierSpendReport(unitId);

    ReportService reportService = mock(ReportService.class);
    when(reportService.getDataForSpendBySupplier(unitId, fromDate, toDate, supplierId, 0, 0))
        .thenReturn(spendReport);
    reportUIController.setReportService(reportService);

    ServletOutputStream out = response.getOutputStream();

    // when
    reportUIController.getSpendBySupplierReport(request, response, null, supplierId, fromDate,
        toDate);

    // then
    verify(reportService).getDataForSpendBySupplier(unitId, fromDate, toDate, supplierId, 0, 0);
    verify(xlsxReportCreator).generateSupplierSpendingReport(unitId, spendReport);
  }

  @Test
  public void testGetSpendBySupplierReportForNoDates() throws IOException {
    // given
    reportUIController = spy(this.reportUIController);
    String unitId = this.loggedInUser.getUnitId();
    String supplierId = "457";
    String fromDate = defaultFromDate();
    String toDate = defaultToDate();

    Report spendReport = makeSupplierSpendReport(unitId);

    ReportService reportService = mock(ReportService.class);
    when(reportService.getDataForSpendBySupplier(unitId, fromDate, toDate, supplierId, 0, 0))
        .thenReturn(spendReport);
    reportUIController.setReportService(reportService);

    ServletOutputStream out = response.getOutputStream();

    // when
    reportUIController.getSpendBySupplierReport(request, response, null, supplierId, null, null);

    // then
    verify(reportService).getDataForSpendBySupplier(unitId, fromDate, toDate, supplierId, 0, 0);
    verify(xlsxReportCreator).generateSupplierSpendingReport(unitId, spendReport);
  }

  private String defaultFromDate() {
    Calendar calender = Calendar.getInstance();
    calender.add(Calendar.MONTH, -12);
    return DATE_FORMAT.format(calender.getTime());// fromDate is now the 1st of Last month
  }

  private String defaultToDate() {
    return DATE_FORMAT.format(new Date());
  }

  private List<User> makeUsersList(String unitId, String firstUserId) {
    List<User> users = new ArrayList<>(3);
    users.add(loggedInUser);
    User user1 = new User();
    user1.setUserId("456");
    user1.setUnitId(unitId);
    user1.setUsername("xyc@vroozi.com");
    users.add(user1);
    User user2 = new User();
    user2.setUserId("789");
    user2.setUnitId(unitId);
    user2.setUsername("sad@vroozi.com");
    users.add(user2);
    return users;
  }

  private CategorySpendReport makeCategorySpendReport(String unitId) {
    CategorySpendReport spendReport = new CategorySpendReport();
    List<CategorySpending> spending = new ArrayList<>();
    CategorySpending s1 = new CategorySpending();
    s1.setCatalogCategoryCode("45*");
    s1.setCompanyLabel("Electronics");
    s1.setTotal(new BigDecimal(25.12));
    s1.setUserId("123");
    spending.add(s1);
    CategorySpending s2 = new CategorySpending();
    s2.setCatalogCategoryCode("55*");
    s2.setCompanyLabel("Toys");
    s2.setTotal(new BigDecimal(11.45));
    s2.setUserId("456");
    spending.add(s2);
    CategorySpending s3 = new CategorySpending();
    s3.setCatalogCategoryCode("65*");
    s3.setCompanyLabel("Furniture");
    s3.setTotal(new BigDecimal(11.45));
    s3.setUserId("789");
    spending.add(s3);
    spendReport.setData(spending);
    spendReport.setItemCount(3);
    spendReport.setPageSize(10);
    spendReport.setUnitId(unitId);
    spendReport.setCurrentPage(0);
    return spendReport;
  }

  private Report makeSupplierSpendReport(String unitId) {
    Report report = new Report();
    Map<String, Double> data = new LinkedHashMap<>();
    data.put("s1", 12.3);
    data.put("s2", 25.43);
    report.setData(data);
    report.setItemCount(2);
    report.setPageSize(10);
    report.setUnitId(unitId);
    report.setCurrentPage(0);
    return report;
  }

}
