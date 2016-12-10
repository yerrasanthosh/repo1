package com.vroozi.customerui.uom.controller;


import com.vroozi.customerui.catalog.model.MaterialGroupForm;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.uom.model.UnitOfMeasureMapping;
import com.vroozi.customerui.uom.model.UnitOfMeasureMappingPost;
import com.vroozi.customerui.uom.model.UnitOfMeasureMappingStatus;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Consts;
import com.vroozi.customerui.util.Page;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;

import static com.vroozi.customerui.util.Consts.FORMAT_DATE_DD_MM_YYYY_HH_MM_SS;
import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;


/**
 * Created by Tahir on 6/6/14.
 */
@Controller
public class UnitOfMeasureMappingUIController {
    private static final int PAGE_SIZE = 7;
    private final Logger LOGGER = LoggerFactory.getLogger(UnitOfMeasureMappingUIController.class);
    private final String ASSOCIATED_PROFILES = "associatedProfiles";
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);
    @Autowired
    private UnitOfMeasureMappingService uomServervice;

    @Autowired
    AppConfig appConfig;


    @RequestMapping(value = "/uploadUOMMapping", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String uploadMapping(HttpServletRequest request, @ModelAttribute MaterialGroupForm uomMappingForm, ModelMap map) {
        UnitOfMeasureMappingPost unitOfMeasureMappingPost = new UnitOfMeasureMappingPost();

        if (uomMappingForm.getUomMappingFile() != null && !uomMappingForm.getUomMappingFile().isEmpty()) {
            if (writeToFile(uomMappingForm.getUomMappingFile().getBytes(), uomMappingForm.getUomMappingFile().getOriginalFilename())) {
                unitOfMeasureMappingPost.setFilePath(appConfig.fileUploadPath + "/" + uomMappingForm.getUomMappingFile().getOriginalFilename());
            }
        }

        String messageString = null;
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            unitOfMeasureMappingPost.setFilePath(appConfig.fileUploadPath + "/" + uomMappingForm.getUomMappingFile().getOriginalFilename());
            unitOfMeasureMappingPost.setSubmitter(Integer.parseInt(user.getUserId()));
            unitOfMeasureMappingPost.setUnitId(Integer.parseInt(user.getUnitId()));
            uomServervice.uploadUOMMapping(unitOfMeasureMappingPost);
            messageString = "The unit of measure mapping file has been submitted for processing.";
            map.addAttribute("pgMessage", messageString);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return Consts.SUCCESS;
    }

    private boolean writeToFile(byte[] source, String destFileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(appConfig.fileUploadPath + "/" + destFileName));
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


    @RequestMapping(value = "/uommapping/page")
    public String getUOMMappingPage(ModelMap map, HttpServletRequest request, @RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "searchTerm", required = false) String searchTerm, @RequestParam(value = "fileUploading", required = false) String fileUploading) {

        try {
            boolean processingFinished = false;
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            String currentStatus = "";
            boolean errorsFound = false;
            String message = "";
            UnitOfMeasureMappingStatus mappingStatus = uomServervice.getUOMMappingStatus(user.getUnitId());


            if (mappingStatus != null) {
                currentStatus = mappingStatus.getUomMappingProcessState();
                errorsFound = !mappingStatus.isValidFile();
                map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
            }

            if (currentStatus.length() > 0) {
                if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
                    message = "The unit of measure mapping file has been submitted for processing.";
                } else if (currentStatus.equalsIgnoreCase("VALIDATE_UOM_MAPPING")) {
                    message = "The unit of measure mapping file is being validated...";
                } else if (currentStatus.equalsIgnoreCase("VALIDATE_UOM_MAPPING") || currentStatus.equalsIgnoreCase("UOM_MAPPING_SAVING")) {
                    message = "The unit of measure mappings are being saved...";
                } else if (currentStatus.equalsIgnoreCase("FAILED")) {
                    processingFinished = true;
                } else if (currentStatus.equalsIgnoreCase("UOM_MAPPING_SAVED")) {
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

                Page<UnitOfMeasureMapping> mappingPage = uomServervice.getPagedUOMMapping(user.getUnitId(), pageNumber, (pageSize < 1) ? Consts.PAGE_SIZE : pageSize, searchTerm);
                map.addAttribute("profileGroups", mappingPage.getPageItems());
                map.addAttribute("pgPageNumber", mappingPage.getPageNumber());
                map.addAttribute("pgPagesAvailable", mappingPage.getPagesAvailable());
                map.addAttribute("pgTotalRecords", mappingPage.getTotalRecords());
                map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
                map.addAttribute("pgErrorReport", errorsFound);
                map.addAttribute("pgProcessedReportStyle", mappingPage.getTotalRecords() > 0 ? "" : "display:none");
                map.addAttribute("pgProcessedCount", mappingPage.getTotalRecords());
            }
            map.addAttribute("pgRecordsPerPage", pageSize);
            map.addAttribute("pgUpdating", new Boolean(false));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return Consts.UOM_MAPPING_TABLE_FRAGMENT;
    }

    @RequestMapping(value = "/uommappingstatus/page")
    public String getUOMMappingUploadPage(ModelMap map, HttpServletRequest request, @RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "searchTerm", required = false) String searchTerm, @RequestParam(value = "fileUploading", required = false) String fileUploading) {

        try {
            boolean processingFinished = false;
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            String currentStatus = "";
            boolean errorsFound = false;
            String message = "";
            UnitOfMeasureMappingStatus mappingStatus = uomServervice.getUOMMappingStatus(user.getUnitId());


            if (mappingStatus != null) {
                currentStatus = mappingStatus.getUomMappingProcessState();
                errorsFound = !mappingStatus.isValidFile();
                map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
                message = mappingStatus.getMessage();
                map.addAttribute("pgProcessFailed", mappingStatus.isProcessFailed());
            }

            if (currentStatus.length() > 0) {
                if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
                    message = "The unit of measure mapping file has been submitted for processing.";
                } else if (currentStatus.equalsIgnoreCase("VALIDATE_UOM_MAPPING")) {
                    message = "The unit of measure mapping file is being validated...";
                } else if (currentStatus.equalsIgnoreCase("UOM_MAPPING_SAVING") || currentStatus.equalsIgnoreCase("UOM_MAPPING_SAVING")) {
                    message = "The unit of measure mappings are being saved...";
                } else if (currentStatus.equalsIgnoreCase("FAILED")) {
                    processingFinished = true;
                } else if (currentStatus.equalsIgnoreCase("UOM_MAPPING_SAVED")) {
                    map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
                    map.addAttribute("pgErrorReport", errorsFound);
                    processingFinished = true;
                }
            }


            if (message.length() > 0) {
                map.addAttribute("pgMessage", message);
            }

            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();

            if (processingFinished) {

                Page<UnitOfMeasureMapping> mappingPage = uomServervice.getPagedUOMMapping(user.getUnitId(), pageNumber, (pageSize < 1) ? Consts.PAGE_SIZE : pageSize, searchTerm);
                map.addAttribute("profileGroups", mappingPage.getPageItems());
                map.addAttribute("pgPageNumber", mappingPage.getPageNumber());
                map.addAttribute("pgPagesAvailable", mappingPage.getPagesAvailable());
                map.addAttribute("pgTotalRecords", mappingPage.getTotalRecords());
                map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
                map.addAttribute("pgErrorReport", errorsFound);
                map.addAttribute("pgProcessedReportStyle", mappingPage.getTotalRecords() > 0 ? "" : "display:none");
                map.addAttribute("pgProcessedCount", mappingPage.getTotalRecords());
            }
            map.addAttribute("pgRecordsPerPage", pageSize);
            map.addAttribute("pgUpdating", new Boolean(!processingFinished));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return Consts.UOM_MAPPING_TABLE_FRAGMENT;
    }


    @RequestMapping(value = "/viewuommapping.xlsx")
    public void viewProcessedMapping(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return;
            }

            UnitOfMeasureMappingStatus mappingStatus = uomServervice.getUOMMappingStatus(user.getUnitId());
            String fileName = "";
            if (mappingStatus == null) {
                fileName = appConfig.processedMappingPath + "UOM_MAPPING_PROCESS.xlsx";
            } else {
                fileName = "UOM_MAPPING_" + System.currentTimeMillis() + "_" + user.getUnitId() + ".xlsx";
                uomServervice.downloadMappings(user.getUnitId(), fileName);
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

    @RequestMapping(value = "/viewuommappingerrors")
    public void viewUOMErrorReport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        try {
            String fileHandle = request.getParameter("pgFileHandle");

            String fileName = fileHandle + "_" + "UOM_MAPPING" + ".pdf";
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(((appConfig.errorReportPath.endsWith("/")) ? appConfig.errorReportPath : appConfig.errorReportPath + "/") + fileName)));
            response.setContentType("application/pdf");

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            LOGGER.error("Error Report: " + e);
            try {
                String fileName = "errorReportFailure.pdf";
                BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(appConfig.errorReportPath + fileName)));
                response.setContentType("application/pdf");
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            } catch (Exception el) {
                LOGGER.error("Error Report: " + e);
            }
        }

    }

}


