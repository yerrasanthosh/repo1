package com.vroozi.customerui.currencymapping.controller;


import com.vroozi.customerui.catalog.model.CurrencyMappingForm;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.currencymapping.model.CurrencyMapping;
import com.vroozi.customerui.currencymapping.model.CurrencyMappingPost;
import com.vroozi.customerui.currencymapping.model.CurrencyMappingStatus;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Consts;
import com.vroozi.customerui.util.Page;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vroozi.customerui.util.Consts.FORMAT_DATE_DD_MM_YYYY_HH_MM_SS;
import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;


/**
 * Created by Tahir on 6/6/14.
 */
@Controller
public class CurrencyMappingUIController {

  private static final int PAGE_SIZE = 7;
  private final Logger LOGGER = LoggerFactory.getLogger(CurrencyMappingUIController.class);

  private static final SimpleDateFormat
      dateformat =
      new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);
  @Autowired
  private CurrencyMappingService currencyMappingService;

  @Autowired
  AppConfig appConfig;


  @RequestMapping(value = "/currencymappings/upload", method = RequestMethod.POST, produces = "text/html")
  @ResponseBody
  public String uploadMapping(HttpServletRequest request,
                              @ModelAttribute CurrencyMappingForm currencyMappingForm, ModelMap map) {
    CurrencyMappingPost currencyMappingPost = new CurrencyMappingPost();

    if (currencyMappingForm.getCurrencyMappingFile() != null && !currencyMappingForm
        .getCurrencyMappingFile().isEmpty()) {
      if (writeToFile(currencyMappingForm.getCurrencyMappingFile().getBytes(),
                      currencyMappingForm.getCurrencyMappingFile().getOriginalFilename())) {
        currencyMappingPost.setFilePath(
            appConfig.fileUploadPath +File.separator + currencyMappingForm.getCurrencyMappingFile()
                .getOriginalFilename());
      }
    }

    String messageString = null;
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      currencyMappingPost.setFilePath(
          appConfig.fileUploadPath + File.separator + currencyMappingForm.getCurrencyMappingFile()
              .getOriginalFilename());
      currencyMappingPost.setSubmitter(Integer.parseInt(user.getUserId()));
      currencyMappingPost.setUnitId(Integer.parseInt(user.getUnitId()));
      currencyMappingService.uploadCurrencyMapping(currencyMappingPost);
      messageString = "The unit of measure mapping file has been submitted for processing.";
      map.addAttribute("pgMessage", messageString);


    } catch (Exception e) {
      e.printStackTrace();
    }
    return Consts.SUCCESS;
  }

  private boolean writeToFile(byte[] source, String destFileName) {
    try {
      FileOutputStream
          fileOutputStream =
          new FileOutputStream(new File(appConfig.fileUploadPath + File.separator + destFileName));
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
      bufferedOutputStream.write(source);
      bufferedOutputStream.flush();
      bufferedOutputStream.close();
    } catch (IOException exception) {
      exception.printStackTrace();
      return false;
    }
    return true;
  }


  @RequestMapping(value = "/currencymappings/page")
  public String getCurrencyMappingPage(ModelMap map, HttpServletRequest request,
                                       @RequestParam(value = "pageNumber") int pageNumber,
                                       @RequestParam(value = "searchTerm", required = false) String searchTerm) {

    try {
      boolean processingFinished = false;
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }

      String currentStatus = "";
      boolean errorsFound = false;
      String message = "";
      CurrencyMappingStatus mappingStatus = currencyMappingService.getCurrencyMappingStatus(
          user.getUnitId());

      if (mappingStatus != null) {
        currentStatus = mappingStatus.getCurrencyMappingProcessState();
        errorsFound = !mappingStatus.isValidFile();
        map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
      }

      if (currentStatus.length() > 0) {
        if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
          message = "The unit of measure mapping file has been submitted for processing.";
        } else if (currentStatus.equalsIgnoreCase("VALIDATE_CURRENCY_MAPPING")) {
          message = "The unit of measure mapping file is being validated...";
        } else if (currentStatus.equalsIgnoreCase("VALIDATE_CURRENCY_MAPPING") || currentStatus
            .equalsIgnoreCase("CURRENCY_MAPPING_SAVING")) {
          message = "The unit of measure mappings are being saved...";
        } else if (currentStatus.equalsIgnoreCase("FAILED")) {
          processingFinished = true;
        } else if (currentStatus.equalsIgnoreCase("CURRENCY_MAPPING_SAVED")) {
          map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
          map.addAttribute("pgErrorReport", errorsFound);
          processingFinished = true;
        }
      }

      if (message.length() > 0) {
        map.addAttribute("pgMessage", message);
      }

      int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();
      if (mappingStatus == null) {
        processingFinished = true;
      }
      if (message.length() == 0 && processingFinished) {

        Page<CurrencyMapping> mappingPage = currencyMappingService.getPagedCurrencyMapping(
            user.getUnitId(), pageNumber, (pageSize < 1) ? Consts.PAGE_SIZE : pageSize,
            searchTerm);
        map.addAttribute("currencyMappings", mappingPage.getPageItems());
        map.addAttribute("pgPageNumber", mappingPage.getPageNumber());
        map.addAttribute("pgPagesAvailable", mappingPage.getPagesAvailable());
        map.addAttribute("pgTotalRecords", mappingPage.getTotalRecords());
        map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
        map.addAttribute("pgErrorReport", errorsFound);
        map.addAttribute("pgProcessedReportStyle",
                         mappingPage.getTotalRecords() > 0 ? "" : "display:none");
        map.addAttribute("pgProcessedCount", mappingPage.getTotalRecords());
      }
      map.addAttribute("pgRecordsPerPage", pageSize);
      map.addAttribute("pgUpdating", new Boolean(false));


    } catch (Exception e) {
      e.printStackTrace();
    }

    return Consts.CURRENCY_MAPPING_TABLE_FRAGMENT;
  }

  @RequestMapping(value = "/currencymappingstatus/page")
  public String getCurrencyMappingUploadPage(ModelMap map, HttpServletRequest request,
                                             @RequestParam(value = "pageNumber") int pageNumber,
                                             @RequestParam(value = "searchTerm", required = false) String searchTerm) {

    try {
      boolean processingFinished = false;
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }
      String currentStatus = "";
      boolean errorsFound = false;
      String message = "";
      CurrencyMappingStatus
          mappingStatus = currencyMappingService.getCurrencyMappingStatus(user.getUnitId());

      if (mappingStatus != null) {
        currentStatus = mappingStatus.getCurrencyMappingProcessState();
        errorsFound = !mappingStatus.isValidFile();
        map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
        message = mappingStatus.getMessage();
        map.addAttribute("pgProcessFailed", mappingStatus.isProcessFailed());

        if (currentStatus.length() > 0) {
          if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
            message = "Currency mapping file has been submitted for processing.";
          } else if (currentStatus.equalsIgnoreCase("VALIDATE_CURRENCY_MAPPING")) {
            message = "Currency mapping file is being validated...";
          } else if (currentStatus.equalsIgnoreCase("CURRENCY_MAPPING_SAVING") || currentStatus
              .equalsIgnoreCase("CURRENCY_MAPPING_SAVING")) {
            message = "Currency mappings are being saved...";
          } else if (currentStatus.equalsIgnoreCase("FAILED")) {
            processingFinished = true;
          } else if (currentStatus.equalsIgnoreCase("CURRENCY_MAPPING_SAVED")) {
            map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
            map.addAttribute("pgErrorReport", errorsFound);
            processingFinished = true;
            message = "Currency mappings are saved successfully...";

          }
          if (message.length() > 0) {
            map.addAttribute("pgMessage", message);
          }

          int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();

          if (processingFinished) {

            Page<CurrencyMapping>
                mappingPage =
                currencyMappingService.getPagedCurrencyMapping(user.getUnitId(),
                                                               pageNumber,
                                                               (pageSize
                                                                < 1)
                                                               ? Consts.PAGE_SIZE
                                                               : pageSize,
                                                               searchTerm);
            map.addAttribute("currencyMappings", mappingPage.getPageItems());
            map.addAttribute("pgPageNumber", mappingPage.getPageNumber());
            map.addAttribute("pgPagesAvailable", mappingPage.getPagesAvailable());
            map.addAttribute("pgTotalRecords", mappingPage.getTotalRecords());
            map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
            map.addAttribute("pgErrorReport", errorsFound);
            map.addAttribute("pgProcessedReportStyle",
                             mappingPage.getTotalRecords() > 0 ? "" : "display:none");
            map.addAttribute("pgProcessedCount", mappingPage.getTotalRecords());
          }
          map.addAttribute("pgRecordsPerPage", pageSize);
          map.addAttribute("pgUpdating", new Boolean(!processingFinished));
        }

      }else {
          mappingStatus = new CurrencyMappingStatus();
          map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
          message = mappingStatus.getMessage();
          map.addAttribute("pgProcessFailed", mappingStatus.isProcessFailed());
          map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
          map.addAttribute("pgErrorReport", errorsFound);
          map.addAttribute("pgPageNumber", 0);
          map.addAttribute("pgPagesAvailable", 0);
          map.addAttribute("pgTotalRecords", 0);
          map.addAttribute("pgProcessedCount", 0);
          map.addAttribute("pgRecordsPerPage", 0);
          map.addAttribute("pgUpdating", false);
        }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return Consts.CURRENCY_MAPPING_TABLE_FRAGMENT;
  }


  @RequestMapping(value = "/viewcurrencymapping.xlsx")
  public void viewProcessedMapping(HttpServletRequest request, HttpServletResponse response,
                                   ModelMap modelMap) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return;
      }

      CurrencyMappingStatus mappingStatus = currencyMappingService.getCurrencyMappingStatus(
          user.getUnitId());
      String fileName = "";
      if (mappingStatus == null) {
        fileName = appConfig.processedMappingPath + "CURRENCY_MAPPING_PROCESS.xlsx";
      } else {
        fileName =
            "CURRENCY_MAPPING_" + System.currentTimeMillis() + "_" + user.getUnitId() + ".xlsx";
        currencyMappingService.downloadMappings(user.getUnitId(), fileName);
        fileName = appConfig.processedMappingPath + fileName;
      }
      BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
      response.setContentType("application/text");

      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();
    } catch (Exception e) {
      LOGGER.error("Processed File Report: " + e);
    }

  }

  @RequestMapping(value = "/viewcurrencymappingerrors")
  public void viewCurrencyErrorReport(HttpServletRequest request, HttpServletResponse response,
                                      ModelMap modelMap) {
    try {
      String fileHandle = request.getParameter("pgFileHandle");

      String fileName = fileHandle + "_" + "CURRENCY_MAPPING_PROCESS" + ".pdf";
      BufferedInputStream
          is =
          new BufferedInputStream(new FileInputStream(new File(
              ((appConfig.errorReportPath.endsWith(File.separator)) ? appConfig.errorReportPath
                                                         : appConfig.errorReportPath + File.separator)
              + fileName)));
      response.setContentType("application/pdf");

      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();
    } catch (Exception e) {
      LOGGER.error("Error Report: " + e);
      try {
        String fileName = "errorReportFailure.pdf";
        BufferedInputStream
            is =
            new BufferedInputStream(
                new FileInputStream(new File(appConfig.errorReportPath + fileName)));
        response.setContentType("application/pdf");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
      } catch (Exception el) {
        LOGGER.error("Error Report: " + e);
      }
    }

  }

}


