package com.vroozi.customerui.report.controller;

import com.vroozi.customerui.catalog.model.CatalogExportTask;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.report.model.BarChart;
import com.vroozi.customerui.report.model.BarChartDataSet;
import com.vroozi.customerui.report.model.CategorySpendReport;
import com.vroozi.customerui.report.model.MonthlySpend;
import com.vroozi.customerui.report.model.MonthlySpendData;
import com.vroozi.customerui.report.model.PieChartEntry;
import com.vroozi.customerui.report.model.Report;
import com.vroozi.customerui.report.services.ReportService;
import com.vroozi.customerui.report.services.impl.XlsxReportCreator;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.MapUtil;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vroozi.customerui.util.Consts.FAILURE;
import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;
import static com.vroozi.customerui.util.Consts.REPORTS_PAGE;

@Controller
public class ReportUIController {

  private final Logger logger = LoggerFactory.getLogger(ReportUIController.class);
  private static final List<String> CHART_COLORS = Arrays.asList(new String[]{"#ffa500" , "#ADD8E6", "#ff0000", "#000080", "#00FF00"});
  private static final List<String> HIGHLIGHT_COLORS = Arrays.asList(new String[] {"#ECF7F5", "#ECF7F5", "#ECF7F5", "#ECF7F5", "#ECF7F5"});
  private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  AppConfig appConfig;
  @Autowired
  private ReportService reportService;
  @Autowired
  UserManagementService userService;
  @Autowired
  XlsxReportCreator xlsxReportCreator;
  @Autowired
  private CompanyService companyService;
  
  @RequestMapping("/reports")
  public String suppliers(HttpServletRequest request, HttpServletResponse response,
                          ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      modelMap.put("downloadAllCatalogs", companySettings.getDownloadAllCatalogs());
    } catch (Exception e) {
      logger.error("Error retrieving report");
      return FAILURE;
    }

    return REPORTS_PAGE;
  }
  
  /*
   * Get data For Spend By Category Chart 
   */

  @RequestMapping(value = "/getspendbycategory/{unitid}", produces = "application/json",
      method = RequestMethod.GET)
  public @ResponseBody String getDataForPieCharts(@PathVariable("unitid") String unitId,
      @RequestParam(value = "chartType",required = false) String chartType,
      @RequestParam(value = "fromDate",required = false) String fromDate, 
      @RequestParam(value = "toDate",required = false) String toDate) {
    List<PieChartEntry> pieChartData = null;
    Report pieChartResponse = null;
    String jsonResponse = "";
    ObjectMapper mapper = new ObjectMapper();
    try {
      if (StringUtils.isNotBlank(unitId)) {
        if (StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
          SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
          Calendar calender = Calendar.getInstance();
          calender.add(Calendar.MONTH, -1);
          calender.set(Calendar.DATE, 1);
          fromDate = myFormat.format(calender.getTime());// fromDate is now the 1st of Last month
          toDate = myFormat.format(new Date());
        }
        
        if ("category".equals(chartType)) {
          pieChartResponse = reportService.getDataForSpendByCategory(unitId, fromDate, toDate);
        } else {
          pieChartResponse = reportService.getDataForSpendBySupplier(unitId, fromDate, toDate, null, 0, 0);
        }
        if(MapUtils.isNotEmpty((Map<String, Double>)pieChartResponse.getData())) {
        pieChartData =
            preparePieChartEntries(MapUtil.sortOnValues(
                (HashMap<String, Double>) pieChartResponse.getData(), MapUtil.DESCENDING), 4);
       
        jsonResponse = mapper.writeValueAsString(pieChartData);
        }
        else {
          jsonResponse = mapper.writeValueAsString("null");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return jsonResponse;
  }
  
  /*
   * Get data For Internal And External Chart 
   */
  @RequestMapping(value = "/getinternalexternalchartdata/{unitid}", produces = "application/json",
      method = RequestMethod.GET)
  public @ResponseBody String getDataForInternalExternalChart(@PathVariable("unitid") String unitId,
      @RequestParam(value = "fromDate",required = false) String fromDate, 
      @RequestParam(value = "toDate",required = false) String toDate) {
   
    Report spendByCatalogType = null;
    Map<String,BigDecimal> spendByCatalogTypeData = new HashMap<String, BigDecimal>();
    String jsonResponse = "";
    BarChart internalExternalChart= new BarChart();
    try {
      if (StringUtils.isNotBlank(unitId)) {
        if (StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
          SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
          Calendar calender = Calendar.getInstance();
          calender.add(Calendar.MONTH, -1);
          calender.set(Calendar.DATE, 1);
          fromDate = myFormat.format(calender.getTime());// fromDate is now the 1st of Last month
          toDate = myFormat.format(new Date());
        }
        spendByCatalogType = reportService.getDataForInternalExternalChart(unitId, fromDate, toDate);
        internalExternalChart = prepareInternalExternaslChartData((Map<String, Double>) spendByCatalogType.getData());
        ObjectMapper mapper = new ObjectMapper();
        jsonResponse = mapper.writeValueAsString(internalExternalChart);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return jsonResponse;
  }
  /*
   * Get Data for Spend by month Chart By UnitId 
   */
  
  @RequestMapping(value = "/getspendbymonthchartdata/{unitid}", produces = "application/json",
      method = RequestMethod.GET)
  public @ResponseBody String getDataForSpendByMonthChart(@PathVariable("unitid") String unitId,
      @RequestParam(value = "fromDate",required = false) String fromDate, 
      @RequestParam(value = "toDate",required = false) String toDate) {
   
    Report spendByMonth = null;
    Map<String,Double> month = new HashMap<String, Double>();
    String jsonResponse = "";
    ObjectMapper mapper = new ObjectMapper();
    try {
      if (StringUtils.isNotBlank(unitId)) {
        //Prepare Date If not pass from front End
        if (StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
          SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
          Calendar calender = Calendar.getInstance();
          calender.add(Calendar.MONTH, -12);
          calender.set(Calendar.DATE, 1);
          fromDate = myFormat.format(calender.getTime());// fromDate is now the 1st of Last month
          toDate = myFormat.format(new Date());
        }
        // Call SpendByMonth service in Reporting API 
        spendByMonth = reportService.getDataForSpendByMonthChart(unitId, fromDate, toDate);
        if(MapUtils.isNotEmpty((Map<String, Double>)spendByMonth.getData())) {
          // Prepare Month data from Last one Year 
          month = prepareMonthlyData();
          month.putAll((Map<String, Double>)spendByMonth.getData());
          jsonResponse = mapper.writeValueAsString(prepareSpendByMonthChartData(month));
        } else {
          jsonResponse = mapper.writeValueAsString("null");
        }
        
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return jsonResponse;
  }
  
  /**
   * Generates and returns xlsx report for spend by category
   * 
   * @param fromDate start date of report in format yyyy-MM-dd
   * @param toDate end date of report in format yyyy-MM-dd
   */
  @RequestMapping(value = "/spend-by-category.xlsx")
  public void getSpendByCategoryReport(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "fromDate", required = false) String fromDate,
      @RequestParam(value = "toDate", required = false) String toDate) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return;
      }
      if (StringUtils.isBlank(fromDate)) {
        fromDate = defaultFromDate();
      }
      if (StringUtils.isBlank(toDate)) {
        toDate = defaultToDate();
      }

      CategorySpendReport spendReport =
          reportService.getCategorySpending(user.getUnitId(), fromDate, toDate);

      String reportPath = xlsxReportCreator.generateCategorySpendingReport(user.getUnitId(), spendReport);
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setHeader("Expires:", "0"); // eliminates browser caching
      response.setHeader("Content-Disposition", "attachment; filename=spend-by-category.xlsx");
      
      BufferedInputStream is = new BufferedInputStream(new FileInputStream(reportPath));
      IOUtils.copy(is, response.getOutputStream());

      response.flushBuffer();
    } catch (Exception e) {
      logger.error("Spend by category report failed.", e);
    }
  }

  /**
   * Generates and returns xlsx report for spend by shopper and category
   * 
   * @param userId Optional userId, if provided then report will be generated only for this user
   * @param fromDate start date of report in format yyyy-MM-dd
   * @param toDate end date of report in format yyyy-MM-dd
   */
  @RequestMapping(value = "/spend-by-shopper.xlsx")
  public void getSpendByShopperReport(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap, @RequestParam(value = "userId", required = false) String userId,
      @RequestParam(value = "fromDate", required = false) String fromDate,
      @RequestParam(value = "toDate", required = false) String toDate) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return;
      }
      if (StringUtils.isBlank(fromDate)) {
        fromDate = defaultFromDate();
      }
      if (StringUtils.isBlank(toDate)) {
        toDate = defaultToDate();
      }

      Map<String, String> userMap;
      CategorySpendReport spendReport;
      if (userId == null || userId.isEmpty() || userId.equalsIgnoreCase("All")) {
        spendReport = reportService.getSpendByCategory(user.getUnitId(), fromDate, toDate, 0, 0);
        userMap = getUserIdNameMap(user.getUnitId(), null);
      } else {
        spendReport =
            reportService.getSpendByCategory(user.getUnitId(), userId, fromDate, toDate, 0, 0);
        userMap = getUserIdNameMap(user.getUnitId(), userId);
      }

      String reportPath = xlsxReportCreator.generateShopperSpendingReport(user.getUnitId(), userMap, spendReport);
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setHeader("Expires:", "0"); // eliminates browser caching
      response.setHeader("Content-Disposition", "attachment; filename=spend-by-shopper.xlsx");

      BufferedInputStream is = new BufferedInputStream(new FileInputStream(reportPath));
      IOUtils.copy(is, response.getOutputStream());

      response.flushBuffer();
    } catch (Exception e) {
      logger.error("Spend by category report failed.", e);
    }
  }

  /**
   * Generates and returns xlsx report for spend by supplier
   * 
   * @param supplierId Optional supplierId, if provided then report will be generated only for this
   *        supplier
   * @param fromDate start date of report in format yyyy-MM-dd
   * @param toDate end date of report in format yyyy-MM-dd
   */
  @RequestMapping(value = "/spend-by-supplier.xlsx")
  public void getSpendBySupplierReport(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap, @RequestParam(value = "supplierId", required = false) String supplierId,
      @RequestParam(value = "fromDate", required = false) String fromDate,
      @RequestParam(value = "toDate", required = false) String toDate) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return;
      }
      if (StringUtils.isBlank(fromDate)) {
        fromDate = defaultFromDate();// fromDate is now the 1st of Last month
      }
      if (StringUtils.isBlank(toDate)) {
        toDate = defaultToDate();
      }

      Report spendReport;
      if (supplierId == null || supplierId.isEmpty() || supplierId.equalsIgnoreCase("All")) {
        spendReport =
            reportService.getDataForSpendBySupplier(user.getUnitId(), fromDate, toDate, null, 0, 0);
      } else {
        spendReport = reportService.getDataForSpendBySupplier(user.getUnitId(), fromDate, toDate,
            supplierId, 0, 0);
      }

      String reportPath = xlsxReportCreator.generateSupplierSpendingReport(user.getUnitId(), spendReport);
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setHeader("Expires:", "0"); // eliminates browser caching
      response.setHeader("Content-Disposition", "attachment; filename=\"spend-by-supplier.xlsx\"");
//      response.setHeader("Content-Disposition", "inline; filename=\"spend-by-supplier.xlsx\"");

      BufferedInputStream is = new BufferedInputStream(new FileInputStream(reportPath));
      IOUtils.copy(is, response.getOutputStream());
      
      response.flushBuffer();
    } catch (Exception e) {
      logger.error("Spend by shopper report failed.", e);
    }
  }

	@RequestMapping(value = "/download-all-catalogs", method = RequestMethod.POST)
	public @ResponseBody String downloadAllCatalogs(HttpServletRequest request,
			@ModelAttribute CatalogExportTask catalogExportTask) {
		User user = SessionDataRetriever.getLoggedInUser(request);

		try {
			catalogExportTask.setUnitId(user.getUnitId());
			catalogExportTask.setCreatedOn(new Date());
			catalogExportTask.setLastUpdated(new Date());
			catalogExportTask.setSubmitterId(user.getUserId());
			catalogExportTask.setStatus("UNPROCESSED");
			CatalogExportTask obj = reportService.downloadAllCatalogs(catalogExportTask);
			if (obj.getErrorMsg() != null && !obj.getErrorMsg().equals("")) {
				return obj.getErrorMsg();
			}
		} catch (Exception e) {
			logger.error("Exception in invoking downloading all catalogs", e);
		}
		return "";
	}

  protected static String defaultFromDate() {
    Calendar calender = Calendar.getInstance();
    calender.add(Calendar.MONTH, -12);
    return DATE_FORMAT.format(calender.getTime());
  }
  protected static String defaultToDate() {
    return DATE_FORMAT.format(new Date());
  }
 
  protected Map<String, String> getUserIdNameMap(String unitId, String userId) {
    Map<String, String> userMap = new HashMap<>();
    if (userId == null) {
      List<User> users = userService.getAllUsers(unitId); 
      for (User user : users) {
        userMap.put(user.getUserId(), user.getUsername());
      }
    } else {
      User user = userService.getUser(userId);
      userMap.put(userId, user.getUsername());
    }
    
    return userMap;
  }

  /*
   * Prepare Last 12 Month Data (from current month till last 12 months) 
   */
  private Map<String, Double> prepareMonthlyData() {
    
    Map<String, Double> monthlyData = new LinkedHashMap<String, Double>();
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)-1);

    for (int i=1; i <= 12; i++) {
      calendar.add(Calendar.MONTH, 1);
      monthlyData.put(calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR), 0.0);
    }
    
    return monthlyData;
  }
  
  /*
   * Prepare Data as per Chart API Format. For the last 12 months 
   */
  
  private MonthlySpend prepareSpendByMonthChartData(Map<String, Double> month) {

    MonthlySpend monthlySpend = new MonthlySpend();
    MonthlySpendData spendData = new MonthlySpendData();

    List<String> labels = new ArrayList<String>();
    List<Integer> values = new ArrayList<Integer>();

    for (Map.Entry<String, Double> entry : month.entrySet()) {
      String key = entry.getKey();
      SimpleDateFormat myFormat = new SimpleDateFormat("MMM");
      String[] keys = key.split("-");
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.MONTH, Integer.parseInt(keys[0]));
      labels.add(myFormat.format(cal.getTime()) + "-" + keys[1]);
      values.add(entry.getValue().intValue());
    }

    spendData.setLabel("");
    spendData.setFillColor("rgba(220,220,220,0.2)");
    spendData.setStrokeColor("rgba(192,192,192,1)");
    spendData.setPointColor("rgba(220,220,220,1)");
    spendData.setPointStrokeColor("#fff");
    spendData.setPointHighlightFill("#fff");
    spendData.setPointHighlightStroke("rgba(220,220,220,1)");
    spendData.setData(values);

    monthlySpend.setLabels(labels);
    monthlySpend.addDataset(spendData);

    return monthlySpend;
  }
  
  /*
   * Prepare Data as per Chart API Format. For Internal,External and Free Text Chart
   */
  private BarChart prepareInternalExternaslChartData(Map<String, Double> spendByCatalogTypeData) {
    
    BarChart barChart = new BarChart();
    List<String> labels = new ArrayList<>();
    List<BarChartDataSet> datasets = new ArrayList<>();
    BarChartDataSet barChartDataSet = new BarChartDataSet();
    barChartDataSet.setLabel("Months");
    barChartDataSet.setFillColor("rgba(0, 125, 0, 0.8)");
    barChartDataSet.setHighlightFill("rgba(0, 125, 0,0.5)");
    datasets.add(barChartDataSet);
    
    barChart.setLabels(labels);
    barChart.setDatasets(datasets);    
    
    double internal=0.0;
    double external= 0.0;
    double freeText= 0.0;
    
    if (spendByCatalogTypeData.size() > 0 ){
      double total =  Double.parseDouble(spendByCatalogTypeData.get("TOTAL").toString());
      
      if(spendByCatalogTypeData.get("EXTERNAL") > 0 ) {
        external = Double.parseDouble(spendByCatalogTypeData.get("EXTERNAL").toString());
        BigDecimal externalPrecentage = new BigDecimal((external / total) * 100);
        externalPrecentage =  externalPrecentage.setScale(2, BigDecimal.ROUND_HALF_UP);
        labels.add("External");
        barChartDataSet.addIntoData(externalPrecentage);        
      } else {
        labels.add("External");
        barChartDataSet.addIntoData(new BigDecimal("0.00"));        
      }
      
      if(spendByCatalogTypeData.get("INTERNAL") > 0 ) {
        internal = Double.parseDouble(spendByCatalogTypeData.get("INTERNAL").toString());
        BigDecimal internalPrecentage = new BigDecimal((internal / total) * 100);
        internalPrecentage = internalPrecentage.setScale(2, BigDecimal.ROUND_HALF_UP);
        labels.add("Internal");
        barChartDataSet.addIntoData(internalPrecentage);        
      } else {
          labels.add("Internal");
          barChartDataSet.addIntoData(new BigDecimal("0.00"));        
        }
    
      
      if(spendByCatalogTypeData.get("FREETEXT") > 0 ) {
        freeText = Double.parseDouble(spendByCatalogTypeData.get("FREETEXT").toString());
        BigDecimal freeTextPercentage = new BigDecimal((freeText / total) * 100);
        freeTextPercentage = freeTextPercentage.setScale(2, BigDecimal.ROUND_HALF_UP);
        labels.add("Free Text");
        barChartDataSet.addIntoData(freeTextPercentage);
      } else {
        labels.add("Free Text");
        barChartDataSet.addIntoData(new BigDecimal("0.00"));        
      }    
      
    }
    return barChart;
  }

  /**
   * Prepares data in a format suitable to display in Pie Chart on Reports tab
   * 
   * @param labelAndValueMap
   * @param entriesLimit - limit after which all values should be shown as "Others"
   * @return
   * @author Muhammad Shoaib
   * @author Muhammad Haris (Refactored and Optimized)
   */
  private List<PieChartEntry> preparePieChartEntries(Map<String, Double> labelAndValueMap,
      int entriesLimit) {

    List<PieChartEntry> pieChartEntries = new ArrayList<PieChartEntry>();

    if (MapUtils.isNotEmpty(labelAndValueMap)) {
      int count = 0;
      double otherValues = 0.0;
      Iterator<Map.Entry<String, Double>> iterator = labelAndValueMap.entrySet().iterator();

      /* iterate through item wise totals */
      while (iterator.hasNext()) {
        Map.Entry<String, Double> pair = iterator.next();

        if (count < entriesLimit) {
          if (StringUtils.isNotBlank(pair.getKey())) {
            pieChartEntries.add(composePieChartEntry(pair.getKey(), pair.getValue().toString(),
                count));
          } else {
            otherValues += pair.getValue();
            count--;
          }
        } else {
          otherValues += pair.getValue();
        }
        count++;
      }

      if (otherValues > 0) {
        pieChartEntries
            .add(composePieChartEntry("Others", String.valueOf(otherValues), entriesLimit));
      }
    }
    return pieChartEntries;
  }

  private PieChartEntry composePieChartEntry(String label, String value, int index) {
    PieChartEntry chartEntry = new PieChartEntry();
    chartEntry.setLabel(label);
    chartEntry.setValue(new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    chartEntry.setColor(CHART_COLORS.get(index));
    chartEntry.setHighlight(HIGHLIGHT_COLORS.get(index));
    return chartEntry;
  }

  public void setReportService(ReportService reportService) {
    this.reportService = reportService;
  }

  public void setUserService(UserManagementService userService) {
    this.userService = userService;
  }

  public void setXlsxReportCreator(XlsxReportCreator xlsxReportCreator) {
    this.xlsxReportCreator = xlsxReportCreator;
  }
  
}