package com.vroozi.customerui.materialgroup.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.vroozi.customerui.catalog.model.MaterialGroupForm;
import com.vroozi.customerui.catalog.model.MaterialGroupPost;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.materialgroup.model.MaterialGroupProxy;
import com.vroozi.customerui.materialgroup.model.MaterialGroupStateProxy;
import com.vroozi.customerui.matgroup.services.MaterialGroupService;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.VendorMapping;
import com.vroozi.customerui.supplier.model.VendorMappingStateProxy;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Consts;
import com.vroozi.customerui.util.Page;

@Controller
public class MaterialGroupUIController {
    private final Logger logger = LoggerFactory.getLogger(MaterialGroupUIController.class);

    @Autowired
    AppConfig appConfig;

    @Autowired
    MaterialGroupService matGroupService;

    @Autowired
    SupplierService supplierService;

    @RequestMapping(value = "/datamapping")
    public String navigateToDataMappingPage(ModelMap map , HttpServletRequest request){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);

            String currentStatus = null;
            String messageString = null;
            boolean errorsFound = false;
            MaterialGroupStateProxy materialGroupStateProxy = null;
            VendorMappingStateProxy vendorMappingStateProxy = null;
            try {
                materialGroupStateProxy = matGroupService.getCurrentStateOfMaterialGroupMapping(user.getUnitId());
            } catch (Exception e) {
                logger.error("Exception occured while retrieving current state of material group mapping for company");
            }
            if (materialGroupStateProxy!=null) {
                currentStatus = materialGroupStateProxy.getMaterialGroupMappingProcessState();
                errorsFound = !materialGroupStateProxy.isValidFile();
                map.addAttribute("fileHandle", materialGroupStateProxy.getProcessId());
            }
            logger.debug("Current status of material group mapping process:*********"+currentStatus+"*****Errors found?:***************" + errorsFound);
            if (null!=currentStatus) {
                if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
                    messageString = "The material group mapping file has been submitted for processing.";
                } else if (currentStatus.equalsIgnoreCase("VALIDATE_MAT_GROUP")) {
                    messageString = "The material group mapping file is being validated...";
                } else if (currentStatus.equalsIgnoreCase("VALIDATED_MAT_GROUP")||currentStatus.equalsIgnoreCase("MATERIAL_GROUP_MAPPING_SAVING")) {
//                    map.addAttribute("errorReport", errorsFound);
                    messageString = "The material group mappings are being saved...";
                } else if (currentStatus.equalsIgnoreCase("MATERIAL_GROUP_MAPPING_SAVED")) {
                    map.addAttribute("errorReport", errorsFound);
                }
            }


            if (null!=messageString) {
                map.addAttribute("message", messageString);
            }

            int pageNo = 1;
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();

            if (messageString==null) {
                Page<MaterialGroupProxy> materialGroupPage = matGroupService.getPagedDataForMaterialGroupMapping(user.getUnitId(), pageNo, (pageSize<1)?Consts.PAGE_SIZE:pageSize, null);
//                Page<MaterialGroupProxy> materialGroupPage = matGroupService.getPagedDataForMaterialGroupMapping(Integer.parseInt(user.getUnitId()), pageNo, pageSize, null);
                map.addAttribute("materialGroups",materialGroupPage.getPageItems());
                map.addAttribute("pageNumber",materialGroupPage.getPageNumber());
                map.addAttribute("pagesAvailable",materialGroupPage.getPagesAvailable());
                map.addAttribute("totalRecords", materialGroupPage.getTotalRecords());
                map.addAttribute("recordsPerPage", pageSize);
                map.addAttribute("updating", new Boolean(false));
                map.addAttribute("cmProcessedReportStyle", materialGroupPage.getTotalRecords()>0?"":"display:none");
                map.addAttribute("cmProcessedCount", materialGroupPage.getTotalRecords());

            }

            String currentStatusSupplier = null;
            String messageStringSupplier = null;
            boolean errorsFoundSupplier = false;
            try {
                vendorMappingStateProxy = matGroupService.getCurrentStateOfSupplierMapping(user.getUnitId());
            } catch (Exception e) {
                logger.error("Exception occured while retrieving current state of material group mapping for company");
            }
            if (vendorMappingStateProxy!=null) {
                currentStatusSupplier = vendorMappingStateProxy.getSupplierMappingProcessState();
                errorsFoundSupplier = !vendorMappingStateProxy.isValidFile();

            }

            if (null!=currentStatusSupplier) {
                if (currentStatusSupplier.equalsIgnoreCase("SUBMITTED")) {
                    messageStringSupplier = "The custom supplier mapping file has been submitted for processing.";
                } else if (currentStatusSupplier.equalsIgnoreCase("VALIDATE_VENDOR_GROUP")) {
                    messageStringSupplier = "The custom supplier mapping file is being validated...";
                }else if (currentStatusSupplier.equalsIgnoreCase("VALIDATED_VENDOR_GROUP")) {
//                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    messageStringSupplier = "The custom supplier mapping file has been validated...";
                }else if (currentStatusSupplier.equalsIgnoreCase("DEDUPE_VENDOR_GROUP")) {
//                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    messageStringSupplier = "The custom supplier mapping file is being checked for duplicates...";
                } else if (currentStatusSupplier.equalsIgnoreCase("DEDUPED_VENDOR_GROUP")||currentStatusSupplier.equalsIgnoreCase("VENDOR_MAPPING_SAVING")) {
//                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    messageStringSupplier = "The custom supplier mappings are being saved...";
                } else if (currentStatusSupplier.equalsIgnoreCase("VENDOR_MAPPING_SAVED")) {
                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    map.addAttribute("fileHandleSupplier", vendorMappingStateProxy.getProcessId());
                }
            }

            if (null!=messageStringSupplier) {
                map.addAttribute("messageSupplier", messageStringSupplier);
            }

            if (messageStringSupplier==null) {
              //  Page<VendorMapping> supplierMappingPage = matGroupService.getPagedDataForCustomSupplierMapping(Integer.parseInt(user.getUnitId()), pageNo, (pageSize<1)?Consts.PAGE_SIZE:pageSize, null, null);
                
                List<Supplier> suppliers= supplierService.getActiveSupplierByUnitId(user.getUnitId());
                List<String> uniSupplierIds = new ArrayList<>();
               
                for (Supplier supplier : suppliers) {
  						uniSupplierIds.add(supplier.getUniqueSupplierId());
                }
                Page<VendorMapping> supplierMappingPage =  matGroupService.getPagedDataForCustomSupplierMapping(Integer.parseInt(user.getUnitId()), pageNo, (pageSize<1)?Consts.PAGE_SIZE:pageSize, null, null, uniSupplierIds );
                
                map.addAttribute("supplierMappings",supplierMappingPage.getPageItems());
                map.addAttribute("pageNumberSupplier",supplierMappingPage.getPageNumber());
                map.addAttribute("pagesAvailableSupplier",supplierMappingPage.getPagesAvailable());
                map.addAttribute("totalRecordsSupplier", supplierMappingPage.getTotalRecords());
                map.addAttribute("updating", new Boolean(false));
                map.addAttribute("vmProcessedReportStyle", supplierMappingPage.getTotalRecords()>0?"":"display:none");
                map.addAttribute("vmProcessedCount", supplierMappingPage.getTotalRecords());
                
                if(map.get("recordsPerPage")== null){
                	map.put("recordsPerPage", pageSize);
                }
                
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "forward:/profilegroupmapping";
    }


    @RequestMapping(value = "/datamapping/materialgroupmapping/page")
    public String getMaterialGroupMappingPage(ModelMap map , HttpServletRequest request, @RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "searchTerm", required = false) String searchTerm, @RequestParam(value = "fileUploading", required = false) String fileUploading){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            String messageString = null;


            if (fileUploading!=null && !fileUploading.isEmpty()) {
                String currentStatus = null;
                boolean errorsFound = false;
                boolean processFailed = false;
                MaterialGroupStateProxy materialGroupStateProxy = null;
                try {
                    materialGroupStateProxy = matGroupService.getCurrentStateOfMaterialGroupMapping(user.getUnitId());
                } catch (Exception e) {
                    logger.error("Exception occured while retrieving current state of material group mapping for company");
                }
                if (materialGroupStateProxy != null) {
                    currentStatus = materialGroupStateProxy.getMaterialGroupMappingProcessState();
                    errorsFound = !materialGroupStateProxy.isValidFile();
                    processFailed = materialGroupStateProxy.isProcessFailed();
                    map.addAttribute("fileHandle", materialGroupStateProxy.getProcessId());
                }
                logger.debug("Current status of material group mapping process:*********" + currentStatus + "*****Errors found?:***************" + errorsFound);
                if(processFailed) {
                	map.addAttribute("processFailed", processFailed);
                	messageString = currentStatus;
                } else if (null != currentStatus) {
                    if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
                        messageString = "The material group mapping file has been submitted for processing.";
                    } else if (currentStatus.equalsIgnoreCase("VALIDATE_MAT_GROUP")) {
                        messageString = "The material group mapping file is being validated...";
                    } else if (currentStatus.equalsIgnoreCase("VALIDATED_MAT_GROUP") || currentStatus.equalsIgnoreCase("MATERIAL_GROUP_MAPPING_SAVING")) {
                        messageString = "The material group mappings are being saved...";
                    } else if (currentStatus.equalsIgnoreCase("MATERIAL_GROUP_MAPPING_SAVED")) {
                        map.addAttribute("errorReport", errorsFound);
                    }
                }

                if (null != messageString) {
                    map.addAttribute("message", messageString);
                }
            }

            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();

            if (messageString==null) {
                Page<MaterialGroupProxy> materialGroupPage = matGroupService.getPagedDataForMaterialGroupMapping(user.getUnitId(), pageNumber, (pageSize<1)?Consts.PAGE_SIZE:pageSize, searchTerm);
//                Page<MaterialGroupProxy> materialGroupPage = matGroupService.getPagedDataForMaterialGroupMapping(Integer.parseInt(user.getUnitId()), pageNo, pageSize, null);
                map.addAttribute("materialGroups",materialGroupPage.getPageItems());
                map.addAttribute("pageNumber",materialGroupPage.getPageNumber());
                map.addAttribute("pagesAvailable",materialGroupPage.getPagesAvailable());
                map.addAttribute("totalRecords", materialGroupPage.getTotalRecords());
                map.addAttribute("cmProcessedReportStyle", materialGroupPage.getTotalRecords()>0?"":"display:none");
                map.addAttribute("cmProcessedCount", materialGroupPage.getTotalRecords());

            }
            map.addAttribute("recordsPerPage", pageSize);
            map.addAttribute("updating", new Boolean(true));



        }catch (Exception e) {
            e.printStackTrace();
        }

        return Consts.MATERIALGROUP_MAPPING_TABLE_FRAGMENT;
    }

    @RequestMapping(value = "/view-material-group-error-report")
    public void viewErrorReport(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            String fileHandle = request.getParameter("fileHandle");


            String fileName= fileHandle+"_"+"MATERIAL_GROUP_MAPPING"+".pdf";
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(((appConfig.errorReportPath.endsWith("/"))?appConfig.errorReportPath:appConfig.errorReportPath+"/")+fileName)));
            response.setContentType("application/pdf");
            //response.addHeader("Content-Disposition",  "attachment; filename="+fileName);
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
//            return "report_failure";
        }
        //return "";
    }

    @RequestMapping(value = "/view-supplier-mapping-error-report")
    public void viewSupplierErrorReport(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            String fileHandle = request.getParameter("fileHandle");


            String fileName= fileHandle+"_"+"SUPPLIER_MAPPING"+".pdf";
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(((appConfig.errorReportPath.endsWith("/"))?appConfig.errorReportPath:appConfig.errorReportPath+"/")+fileName)));
            response.setContentType("application/pdf");
            //response.addHeader("Content-Disposition",  "attachment; filename="+fileName);
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
//            return "report_failure";
        }
        //return "";
    }

    @RequestMapping(value = "/datamapping/vendormapping/page")
    public String getVendorMappingPage(ModelMap map , HttpServletRequest request, @RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "searchTerm", required = false) String searchTerm, @RequestParam(value = "dunsNumber", required = false) String dunsNumber, @RequestParam(value = "fileUploading", required = false) String fileUploading){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);

            VendorMappingStateProxy vendorMappingStateProxy = null;

            String currentStatusSupplier = null;
            String messageStringSupplier = null;
            boolean errorsFoundSupplier = false;
            boolean processFailed = false;
            try {
                vendorMappingStateProxy = matGroupService.getCurrentStateOfSupplierMapping(user.getUnitId());
            } catch (Exception e) {
                logger.error("Exception occured while retrieving current state of material group mapping for company");
            }
            if (vendorMappingStateProxy!=null) {
                currentStatusSupplier = vendorMappingStateProxy.getSupplierMappingProcessState();
                errorsFoundSupplier = !vendorMappingStateProxy.isValidFile();
                processFailed = vendorMappingStateProxy.isProcessFailed();
            }

            if(processFailed) {
                map.addAttribute("supplierProcessFailed", processFailed);
                messageStringSupplier = currentStatusSupplier;
            } else if (null!=currentStatusSupplier) {
                if (currentStatusSupplier.equalsIgnoreCase("SUBMITTED")) {
                    messageStringSupplier = "The custom supplier mapping file has been submitted for processing.";
                } else if (currentStatusSupplier.equalsIgnoreCase("VALIDATE_VENDOR_GROUP")) {
                    messageStringSupplier = "The custom supplier mapping file is being validated...";
                }else if (currentStatusSupplier.equalsIgnoreCase("VALIDATED_VENDOR_GROUP")) {
//                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    messageStringSupplier = "The custom supplier mapping file has been validated...";
                }else if (currentStatusSupplier.equalsIgnoreCase("DEDUPE_VENDOR_GROUP")) {
//                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    messageStringSupplier = "The custom supplier mapping file is being checked for duplicates...";
                } else if (currentStatusSupplier.equalsIgnoreCase("DEDUPED_VENDOR_GROUP")||currentStatusSupplier.equalsIgnoreCase("VENDOR_MAPPING_SAVING")) {
//                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    messageStringSupplier = "The custom supplier mappings are being saved...";
                } else if (currentStatusSupplier.equalsIgnoreCase("VENDOR_MAPPING_SAVED")) {
                    map.addAttribute("errorReportSupplier", errorsFoundSupplier);
                    map.addAttribute("fileHandleSupplier", vendorMappingStateProxy.getProcessId());
                }
            }

            if (null!=messageStringSupplier) {
                map.addAttribute("messageSupplier", messageStringSupplier);
            }
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();
            List<Supplier> suppliers= supplierService.getActiveSupplierByUnitId(user.getUnitId());
            List<String> uniSupplierIds = new ArrayList<>();
           
            for (Supplier supplier : suppliers) {
						uniSupplierIds.add(supplier.getUniqueSupplierId());
            }
            
            if (messageStringSupplier==null) {
                Page<VendorMapping> page = matGroupService.getPagedDataForCustomSupplierMapping(Integer.parseInt(user.getUnitId()), pageNumber, (pageSize<1)?Consts.PAGE_SIZE:pageSize, searchTerm, dunsNumber,uniSupplierIds);
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
//            logger.error("Error in writing to file(" + destFileName + ")", exception);
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/findMaterialGroup", method=RequestMethod.GET)
    public String findMaterialGroupMappings(ModelMap map , HttpServletRequest request){
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            map.addAttribute("materialGroups",matGroupService.findMaterialGroups(user.getUnitId()));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "data_mapping";
    }

    @RequestMapping(value = "/uploadMaterialGroup", method=RequestMethod.POST, produces="text/html")
    @ResponseBody
    public String uploadMaterialGroup(HttpServletRequest request , @ModelAttribute MaterialGroupForm materialGroupForm, ModelMap map){
        MaterialGroupPost materialGroupPost = new MaterialGroupPost();

        if(materialGroupForm.getMaterialGroupFile() != null && !materialGroupForm.getMaterialGroupFile().isEmpty()) {
            if(writeToFile(materialGroupForm.getMaterialGroupFile().getBytes(), materialGroupForm.getMaterialGroupFile().getOriginalFilename())){
                materialGroupPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" +materialGroupForm.getMaterialGroupFile().getOriginalFilename());
            }
        }

        String messageString = null;
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            materialGroupPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" + materialGroupForm.getMaterialGroupFile().getOriginalFilename());
            materialGroupPost.setSubmitter(Integer.parseInt(user.getUserId()));
            materialGroupPost.setUnitId(Integer.parseInt(user.getUnitId()));
            matGroupService.uploadMaterialGroup(materialGroupPost);
            messageString = "The material group mapping file has been submitted for processing.";
            map.addAttribute("message", messageString);


        }catch (Exception e) {
            e.printStackTrace();
        }
        return Consts.SUCCESS;

    }

    @RequestMapping(value = "/uploadsuppliermapping", method=RequestMethod.POST, produces="text/html")
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
            matGroupService.uploadSupplierMapping(materialGroupPost);
            messageStringSupplier = "The custom supplier mapping file has been submitted for processing.";
            map.addAttribute("messageSupplier", messageStringSupplier);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return Consts.SUCCESS;
    }

    @RequestMapping(value = "/vendormapping.xlsx")
    public void viewProcessedMapping(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return ;
            }
            
            int pageNo = 1;
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();
            List<Supplier> suppliers= supplierService.getActiveSupplierByUnitId(user.getUnitId());
            List<String> uniSupplierIds = new ArrayList<>();
           
            for (Supplier supplier : suppliers) {
						uniSupplierIds.add(supplier.getUniqueSupplierId());
            }

            
            Page<VendorMapping> supplierMappingPage = matGroupService.getPagedDataForCustomSupplierMapping(Integer.parseInt(user.getUnitId()), pageNo, (pageSize<1)?Consts.PAGE_SIZE:pageSize, null, null,uniSupplierIds);
           
            VendorMappingStateProxy vendorMappingStateProxy = null;
            try {
                vendorMappingStateProxy = matGroupService.getCurrentStateOfSupplierMapping(user.getUnitId());
            } catch (Exception e) {
                logger.error("Exception occured while retrieving current state of material group mapping for company");
            }            
            
            
            String fileName = "";
            if((supplierMappingPage.getPagesAvailable() == 0 && supplierMappingPage.getTotalRecords()== 0)){
            	fileName= appConfig.processedMappingPath+"VENDOR_MAPPING.xlsx";
            }else{
               
            	
            	fileName= "VENDOR_MAPPING_"+System.currentTimeMillis()+"_"+user.getUnitId()+".xlsx";
            	matGroupService.downloadSupplierMappings(user.getUnitId(),fileName,uniSupplierIds);
            	fileName = appConfig.processedMappingPath+fileName;
            }
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
            response.setContentType("application/text");

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
        	logger.error("Processed File Report: "+ e);
        }

    }
        


    @RequestMapping(value = "/categorymapping.xlsx")
    public void viewProcessedCategoryMapping(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return ;
            }
            
            
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();
            Page<MaterialGroupProxy> page = matGroupService.getPagedDataForMaterialGroupMapping(user.getUnitId(), 1, (pageSize<1)?Consts.PAGE_SIZE:pageSize, null);            
            
            MaterialGroupStateProxy materialGroupStateProxy=null;
            try {
                materialGroupStateProxy = matGroupService.getCurrentStateOfMaterialGroupMapping(user.getUnitId());
            } catch (Exception e) {
                logger.error("Exception occured while retrieving current state of material group mapping for company");
            }            
            String fileName = "";
            if((page.getPagesAvailable() == 0 && page.getTotalRecords()== 0) || materialGroupStateProxy == null){
                fileName = appConfig.processedMappingPath+"CATEGORY_MAPPING.xlsx";
            }else{
                fileName= appConfig.processedMappingPath+"CATEGORY_MAPPING_"+user.getUnitId()+".xlsx";
            }
            
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
            response.setContentType("application/text");

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
        	logger.error("Processed File Report: "+ e);
        }

    }
    
    
}
