package com.vroozi.customerui.supplier.controller;

import com.vroozi.customerui.catalog.model.MaterialGroupForm;
import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierBulkUploadStatus;
import com.vroozi.customerui.supplier.services.SupplierBulkUploadService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SupplierBulkUploadUIController {
    private final Logger logger = LoggerFactory.getLogger(SupplierBulkUploadUIController.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    SupplierBulkUploadService supplierBulkUploadService;

    @RequestMapping(value = "/view-supplier-bulk-upload-error-report")
    public void viewSupplierErrorReport(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            String fileHandle = request.getParameter("fileHandle");


            String fileName= fileHandle+"_"+"SUPPLIER_BULK_UPOLOAD_PROCESS"+".pdf";
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(((appConfig.errorReportPath.endsWith("/"))?appConfig.errorReportPath:appConfig.errorReportPath+"/")+fileName)));
            response.setContentType("application/pdf");

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("Error Report: "+ e);
            try{
                String fileName= "errorReportFailure.pdf";
                BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(appConfig.errorReportPath+fileName)));
                response.setContentType("application/pdf");
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }catch (Exception el){
                logger.error("Error Report: "+ e);
            }
        }

    }

    @RequestMapping(value = "/supplierbulkupload/supplier_upload/page")
    public String getVendorMappingPage(ModelMap map , HttpServletRequest request, @RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "searchTerm", required = false) String searchTerm, @RequestParam(value = "dunsNumber", required = false) String dunsNumber, @RequestParam(value = "fileUploading", required = false) String fileUploading){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);

            SupplierBulkUploadStatus vendorMappingStateProxy = null;

            String currentStatusSupplier = null;
            String messageStringSupplier = null;
            boolean errorsFoundSupplier = false;
            boolean processFailed = false;
            try {
                vendorMappingStateProxy = supplierBulkUploadService.getCurrentStateOfSupplierBulkUpload(user.getUnitId());
            } catch (Exception e) {
                logger.error("Exception occured while retrieving current state of material group mapping for company");
            }
            if (vendorMappingStateProxy!=null) {
                currentStatusSupplier = vendorMappingStateProxy.getMaterialGroupMappingProcessState();
                errorsFoundSupplier = !vendorMappingStateProxy.isValidFile();
                processFailed = vendorMappingStateProxy.isProcessFailed();
            }

            if(processFailed) {
                map.addAttribute("supplierProcessFailed", processFailed);
                messageStringSupplier = currentStatusSupplier;
            } else if (null!=currentStatusSupplier) {
                if (currentStatusSupplier.equalsIgnoreCase("SUBMITTED")) {
                    messageStringSupplier = "The supplier bulk upload file has been submitted for processing.";
                } else if (currentStatusSupplier.equalsIgnoreCase("VALIDATE_SUPPLIER_BULK_UPLOAD")) {
                    messageStringSupplier = "The supplier bulk upload file is being validated...";
                }else if (currentStatusSupplier.equalsIgnoreCase("VALIDATED_SUPPLIER_BULK_UPLOAD")) {

                    messageStringSupplier = "The supplier bulk upload file has been validated...";

                } else if (currentStatusSupplier.equalsIgnoreCase("SUPPLIER_BULK_UPLOAD_PROCESS_STATE_SAVING")) {

                    messageStringSupplier = "The supplier bulk upload file is being saved...";
                } else if (currentStatusSupplier.equalsIgnoreCase("SUPPLIER_BULK_UPLOAD_PROCESS_STATE_SAVED")) {
                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    map.addAttribute("fileHandleSupplier", vendorMappingStateProxy.getProcessId());
                }
            }

            if (null!=messageStringSupplier) {
                map.addAttribute("messageSupplier", messageStringSupplier);
            }
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();

            if (messageStringSupplier==null) {
                Page<Supplier> page = supplierBulkUploadService.getPagedDataForCustomSupplierBulkUpload(Integer.parseInt(user.getUnitId()), pageNumber, (pageSize<1)?Consts.PAGE_SIZE:pageSize, searchTerm, dunsNumber);
                map.addAttribute("supplierMappings",page.getPageItems());
                map.addAttribute("pageNumberSupplier",page.getPageNumber());
                map.addAttribute("pagesAvailableSupplier",page.getPagesAvailable());
                map.addAttribute("totalRecordsSupplier", page.getTotalRecords());
                map.addAttribute("vmProcessedReportStyle", page.getTotalRecords()>0?"":"display:none");
                map.addAttribute("vmProcessedCount", page.getTotalRecords());

            }
            map.addAttribute("recordsPerPage", pageSize);
            map.addAttribute("updating", new Boolean(true));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return Consts.SUPPLIER_MAPPING_TABLE_FRAGMENT;
    }


    private boolean writeToFile(byte[] source, String destFileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(appConfig.fileUploadPath + "/" + destFileName));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(source);bufferedOutputStream.flush();bufferedOutputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @RequestMapping(value = "/supplierbulkupload", method=RequestMethod.POST, produces="text/html")
    @ResponseBody
    public String uploadSupplierMapping(HttpServletRequest request , @ModelAttribute MaterialGroupForm materialGroupForm, ModelMap map){
        MaterialGroupPost materialGroupPost = new MaterialGroupPost();

        if(materialGroupForm.getSupplierFile() != null && !materialGroupForm.getSupplierFile().isEmpty()) {
            if(writeToFile(materialGroupForm.getSupplierFile().getBytes(), materialGroupForm.getSupplierFile().getOriginalFilename())){
                materialGroupPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" +materialGroupForm.getSupplierFile().getOriginalFilename());
            }
        }

        String messageStringSupplier = null;
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            materialGroupPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" + materialGroupForm.getSupplierFile().getOriginalFilename());
            materialGroupPost.setSubmitter(Integer.parseInt(user.getUserId()));
            materialGroupPost.setUnitId(Integer.parseInt(user.getUnitId()));
            supplierBulkUploadService.uploadSupplierBulk(materialGroupPost);
            messageStringSupplier = "The custom supplier mapping file has been submitted for processing.";
            map.addAttribute("messageSupplier", messageStringSupplier);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return Consts.SUCCESS;
    }

    @RequestMapping(value = "/supplierbulkupload.xlsx")
    public void viewProcessedMapping(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return ;
            }

            int pageNo = 1;
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();

            Page<Supplier> supplierMappingPage = supplierBulkUploadService.getPagedDataForCustomSupplierBulkUpload(Integer.parseInt(user.getUnitId()), pageNo, (pageSize < 1) ? Consts.PAGE_SIZE : pageSize, null, null);

            SupplierBulkUploadStatus vendorMappingStateProxy = null;
            try {
                vendorMappingStateProxy = supplierBulkUploadService.getCurrentStateOfSupplierBulkUpload(user.getUnitId());
            } catch (Exception e) {
                logger.error("Exception occured while retrieving current state of material group mapping for company");
            }


            String fileName = "";
            boolean fetchSuppliers = true;
            if((supplierMappingPage.getPagesAvailable() == 0 && supplierMappingPage.getTotalRecords()== 0)){
            	fileName= "SUPPLIER_BULK_UPLOAD.xlsx";
              fetchSuppliers = false;
            }else{
            	fileName= "SUPPLIER_BULK_UPLOAD_"+System.currentTimeMillis()+"_"+user.getUnitId()+".xlsx";
            }

            supplierBulkUploadService.downloadSupplierBulkUpload(user.getUnitId(), fileName,
                                                               fetchSuppliers);
            fileName = appConfig.processedMappingPath+fileName;
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
            response.setContentType("application/text");

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
        	logger.error("Processed File Report: "+ e);
        }

    }
    
    
}
