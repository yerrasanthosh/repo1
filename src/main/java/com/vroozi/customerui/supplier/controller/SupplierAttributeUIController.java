package com.vroozi.customerui.supplier.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.supplier.model.SupplierAttributeForm;
import com.vroozi.customerui.upload.service.UploadService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.supplier.model.SupplierAttributes;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.service.CompanyService;

import static com.vroozi.customerui.util.Consts.*;

/**
 * Provides supplierAttribute attribute service.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/19/12:4:36 PM
 */
@Controller
public class SupplierAttributeUIController {
    public static final String[] attributeNames = {"Green certified", "Preferred contract", "Service Disabled Veteran", "Veteran Owned", "Minority Owned", "Woman Owned", "Hubzone", "Tier II Contract"};
    public static final String[] iconPaths = {"icon-green.png", "icon-preferred.png", "icon-dv.png",  "icon-vo.png", "icon-mo.png", "icon-wo.png", "icon-hz.png", "icon-tier-2.png"};

    private static enum ViewFilter {
        NONE("NONE"),
        ALL("ALL"),
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");

        private final String filterOptionValue;

        ViewFilter(String filterOptionValue){
            this.filterOptionValue = filterOptionValue;
        }
    }

    private final Logger logger = LoggerFactory.getLogger(SupplierUIController.class);

    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

   // private static final int PAGE_SIZE = 7;

    @Autowired
    AppConfig appConfig;

    @Autowired
    SupplierService supplierService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    UploadService uploadService;

    @RequestMapping("/supplierAttributes")
    public String supplierAttributes(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,
                                     @RequestParam(value="fromEditAttribute", required=false ) Boolean fromEditAttribute) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            int pageNum = 1;  // First Page

            CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
            if(!companySettings.isCompanyAttributesConsumed()){
                // Add default attributes
                addDefaultSupplierAttribute(user);

                // Update the flag
                companyService.updateField(companySettings.getUnitId(), "companyAttributesConsumed", true);
            }
            if(fromEditAttribute != null) {
                modelMap.addAttribute("fromEditAttribute",fromEditAttribute);
            }
            setSupplierAttributePagination(user.getUnitId(), pageNum, ViewFilter.NONE, "Name", "up", "", modelMap, user.getRowsPerPage());

        } catch (Exception e) {
            logger.error("Error retrieving supplier attributes(" + e.getMessage() + ")");
            return FAILURE;
        }

        return SUPPLIER_ATTRIBUTE_PAGE;
    }

    @RequestMapping(value="/goToPageSupplierAttributes", method= RequestMethod.POST)
    public String getSupplierPage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            setSupplierAttributePagination(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, "", modelMap,user.getRowsPerPage());

        }catch (Exception exp){
            logger.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return SUPPLIERS_ATTRIBUTE_PAGE_FRAMGMENT;
    }

    @RequestMapping(value="/filterSupplierAttributes", method= RequestMethod.POST)
    public String filterSupplierAttributes(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam(value="pageNum", required=true ) Integer pageNum, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            setSupplierAttributePagination(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), "Name", "up", "", modelMap,user.getRowsPerPage());

        }catch (Exception exp){
            logger.error("Error filtering SupplierAttributes pageNum(" + pageNum  + ")" );
        }

        return SUPPLIERS_ATTRIBUTE_PAGE_FRAMGMENT;
    }

    @RequestMapping(value = "/activeSupplierAttributes", method = RequestMethod.POST)
    public String activeSupplierAttributes(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
            @RequestParam(value="active") boolean active,
            @RequestParam(value="pageNum") int pageNum,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam( value="supplierAttributeIds") String[] supplierAttributeIds,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            if(supplierAttributeIds != null && supplierAttributeIds.length > 0){
                supplierService.updateSupplierAttributesStatus(active, supplierAttributeIds);
            }

            setSupplierAttributePagination(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, "", modelMap,user.getRowsPerPage());


        } catch (Exception exp) {
            logger.error("Error activating/deactivating supplierAttributeIds (" + supplierAttributeIds + ") ", exp);
            return FAILURE;
        }

        return SUPPLIERS_ATTRIBUTE_PAGE_FRAMGMENT;
    }

    @RequestMapping(value = "/deleteSupplierAttributes", method = RequestMethod.POST)
    public String deleteSupplierAttributes(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
            @RequestParam(value="pageNum") int pageNum,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam( value="supplierAttributeIds") String[] supplierAttributeIds,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            if(supplierAttributeIds != null && supplierAttributeIds.length > 0){
                supplierService.deleteSupplierAttributes(supplierAttributeIds, user.getUnitId());
            }

            setSupplierAttributePagination(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, "", modelMap,user.getRowsPerPage());
        } catch (Exception exp) {
            logger.error("Error deleting supplierAttributeIds (" + supplierAttributeIds + ") ", exp);
            return FAILURE;
        }

        return SUPPLIERS_ATTRIBUTE_PAGE_FRAMGMENT;
    }

  //@Author: MashoodJ
  //@return: String supplierAttributePageURL
  //this micro-service is used for both add & edit supplier attribute case.
  @RequestMapping("/addSupplierAttribute")
  public String createSupplierAttribute(HttpServletRequest request,
                                        @ModelAttribute SupplierAttributeForm supplierAttributeForm) {
    if (supplierAttributeForm == null){
      return "redirect:/supplierAttributes";
    }
    User user = SessionDataRetriever.getLoggedInUser(request);

    SupplierAttributes supplierAttributes = new SupplierAttributes();

    try {
      String iconFilePath = null;
      String attributeName = null;
      String attributeDesc = null;

      java.text.DateFormat df = new SimpleDateFormat("ddMMyyyy-HHmmss");
      String fileNamePostfix = "_" + df.format(new Date());

      if (StringUtils.isNotBlank(supplierAttributeForm.getAttributeId())) {
        //Edit Mode
        try {
          supplierAttributes =
              supplierService.getSupplierAttributeDetails(supplierAttributeForm.getAttributeId(),
                                                          user.getUnitId());
        } catch (Exception e) {
          logger.error("Exception occurred while getting Supplier Attribute having id" + supplierAttributeForm.getAttributeId(), e);
        }
          editSupplierAttribute(supplierAttributeForm, supplierAttributes, fileNamePostfix);
        return "redirect:/supplierAttributes?fromEditAttribute=true";
      } else {
        //Add Mode
        try {
          if (supplierAttributeForm.getIconFile() != null && !supplierAttributeForm.getIconFile()
              .isEmpty()) {
            String iconFilename = supplierAttributeForm.getIconFile().getOriginalFilename();
            iconFilename =
                iconFilename.substring(0, iconFilename.lastIndexOf(".")) + fileNamePostfix
                + iconFilename.substring(iconFilename.lastIndexOf("."));
            if (uploadService
                .writeToFile(supplierAttributeForm.getIconFile().getInputStream(), iconFilename,
                             appConfig.itemImagesPath + File.separator + "supplier")) {
              //                        Utils.generateImage(appConfig.itemImagesPath + File.separator + "supplier" + File.separator + iconFilename, iconFilename, appConfig.itemImagesPath + File.separator + "supplier", 15, 15);
              iconFilePath = iconFilename;
            }
          }

          if (!StringUtils.isEmpty(supplierAttributeForm.getAttributeName())) {
            attributeName = supplierAttributeForm.getAttributeName();
          }
          if (!StringUtils.isEmpty(supplierAttributeForm.getAttributeDescription())) {
            attributeDesc = supplierAttributeForm.getAttributeDescription();
          }

          addSupplierAttribute(attributeName, attributeDesc, iconFilePath, user.getUnitId(),
                               user.getCreatedBy());
        } catch (Exception e) {
          logger.error("Exception occurred while adding supplier attribute", e);
        }

      }
    } catch (Exception exp) {
      logger.error(
          "Exception occurred in createSupplierAttribute method in SupplierAttributeUIController class", exp);
    }

    return "redirect:/supplierAttributes";
  }

  public void editSupplierAttribute(SupplierAttributeForm supplierAttributeForm,
                                    SupplierAttributes supplierAttributes, String fileNamePostfix) {
    try {
      mapSupplierAttributeProperties(supplierAttributeForm, supplierAttributes);
      if (supplierAttributeForm.getIconFile() != null && !supplierAttributeForm.getIconFile()
          .isEmpty()) {
        //update supplier attribute icon if exists.
        updateSupplierAttributeIcon(fileNamePostfix, supplierAttributeForm, supplierAttributes);
      }
      supplierService.updateSupplierAttributes(supplierAttributes);
    } catch (Exception e) {
      logger.error(
          "Exception occurred while updating supplier attribute in editSupplierAttribute method",
          e);
    }
  }

  public void mapSupplierAttributeProperties(SupplierAttributeForm supplierAttributeForm,
                                             SupplierAttributes supplierAttributes) {
    if (supplierAttributeForm.getAttributeName() != null){
      supplierAttributes.setAttributeName(supplierAttributeForm.getAttributeName());
    }
    if (supplierAttributeForm.getAttributeDescription() != null){
      supplierAttributes.setAttributeDescription(supplierAttributeForm.getAttributeDescription());
    }
    supplierAttributes.setLastUpdated(new Date());
  }

  public void updateSupplierAttributeIcon(String fileNamePostfix,
                                          SupplierAttributeForm supplierAttributeForm,
                                          SupplierAttributes supplierAttributes) {
    String iconFilename = supplierAttributeForm.getIconFile().getOriginalFilename();
    iconFilename =
        iconFilename.substring(0, iconFilename.lastIndexOf(".")) + fileNamePostfix
        + iconFilename.substring(iconFilename.lastIndexOf("."));

    try {
      if (uploadService
          .writeToFile(supplierAttributeForm.getIconFile().getInputStream(), iconFilename,
                       appConfig.itemImagesPath + File.separator + "supplier")) {
        supplierAttributes.setAttributeImagePath(iconFilename);
      }
    } catch (IOException e) {
      logger.error("Exception occurred while updating supplier attribute image", e);

    }
  }

  @RequestMapping(value = "/supplierAttributeExists", method=RequestMethod.POST)
    @ResponseBody public String attributeExists(HttpServletRequest request, @RequestParam(required = false) String attributeId, @RequestParam String attributeName, ModelMap modelMap) {
    	try {
	    	User user = SessionDataRetriever.getLoggedInUser(request);
	    	SupplierAttributes attr = supplierService.getSupplierAttributeByName(attributeName, user.getUnitId());
	    	if(attr!=null && attr.getAttributeName().equalsIgnoreCase(attributeName)) {
	    		if(attributeId!=null && !attributeId.equals("") && attr.getAttributeId().equals(attributeId)) {
	    			return "false";
	    		}
	    		return "true";
	    	}
	    }catch(Exception exp){
	    	logger.error("Error to check attribute ", exp);
	    }
    	return "false";
    }

    private void addSupplierAttribute(String name, String desc, String iconFilePath, String unitId, String creator) throws Exception{
        SupplierAttributes supplierAttributes = new SupplierAttributes();
        supplierAttributes.setAttributeImagePath(iconFilePath);
        supplierAttributes.setAttributeName(name);
        supplierAttributes.setAttributeDescription(desc);
        supplierAttributes.setActive(true);
        supplierAttributes.setUnitId(unitId);
        supplierAttributes.setBoostValue(1);
        supplierAttributes.setCreatedBy(creator);
        supplierAttributes.setCreatedOn(dateformat.format(new Date()));

        supplierService.addCompanyAttributes(supplierAttributes);
    }

    private void addDefaultSupplierAttribute(User user) throws Exception{
        for(int i = 0; i < attributeNames.length; ++i){
            addSupplierAttribute(attributeNames[i], attributeNames[i], iconPaths[i], user.getUnitId(), user.getCreatedBy());
        }
    }

//    @RequestMapping(value = "/createSupplierAttribute", method=RequestMethod.POST)
//    public String createSupplierAttribute(HttpServletRequest request, HttpServletResponse response,
//            @RequestParam(value="pageNum") int pageNum,
//            @RequestParam(value="viewFilter") String viewFilter,
//            @RequestParam(value="sortBy", required=true ) String sortBy,
//            @RequestParam(value="sortDirection", required=true ) String sortDirection,
//            @RequestParam(value="attributeName") String attributeName, @RequestParam(value="attributeDescription") String attributeDescription, ModelMap modelMap) {
//
//        User user = SessionDataRetriever.getLoggedInUser(request);
//
//        try {
//            SupplierAttributes supplierAttributes = new SupplierAttributes();
//            DateFormat df = new SimpleDateFormat("ddMMyyyyhhmmssSSS");
//            String fileNamePostfix = "_" + df.format(new Date());
//            //if(supplierAttributeForm != null){
////                if(supplierAttributeForm.getIconFile() != null && !supplierAttributeForm.getIconFile().isEmpty()) {
////                    String iconFilename = supplierAttributeForm.getIconFile().getOriginalFilename();
////                    iconFilename = iconFilename.substring(0, iconFilename.lastIndexOf(".")) + fileNamePostfix + iconFilename.substring(iconFilename.lastIndexOf("."));
////                    if(writeToFile(supplierAttributeForm.getIconFile().getBytes(), iconFilename, appConfig.flagIconUploadPath)){
////                        supplierAttributes.setAttributeImagePath(appConfig.flagIconUploadPath + File.separator + iconFilename);
////                    }
////                }
////                if(!StringUtils.isEmpty(supplierAttributeForm.getAttributeName()) && !StringUtils.isEmpty(supplierAttributeForm.getAttributeName().trim())){
////                    supplierAttributes.setAttributeName(supplierAttributeForm.getAttributeName());
////                }
////                if(!StringUtils.isEmpty(supplierAttributeForm.getAttributeDescription()) && !StringUtils.isEmpty(supplierAttributeForm.getAttributeDescription().trim())){
////                    supplierAttributes.setAttributeDescription(supplierAttributeForm.getAttributeDescription());
////                }
//
//                if(!StringUtils.isEmpty(attributeName)){
//                    supplierAttributes.setAttributeName(attributeName);
//                }
//                if(!StringUtils.isEmpty(attributeDescription)){
//                    supplierAttributes.setAttributeDescription(attributeDescription);
//                }
//
//                supplierAttributes.setActive(true);
//                supplierAttributes.setUnitId(user.getUnitId());
//                supplierAttributes.setBoostValue(1);
//                supplierAttributes.setCreatedBy(user.getCreatedBy());
//                supplierAttributes.setCreatedOn(dateformat.format(new Date()));
//
//                supplierService.addCompanyAttributes(supplierAttributes);
//
//                setSupplierAttributePagination(user.getUnitId(), 1, ViewFilter.valueOf("NONE"), "Name", "up", "", modelMap);
//            //}
//
//        } catch (Exception exp) {
//            logger.error("Error creating supplier Attribute", exp);
//            return FAILURE;
//        }
//
//        return SUPPLIERS_ATTRIBUTE_PAGE_FRAMGMENT;
//    }

    private boolean writeToFile(byte[] source, String destFileName, String uploadPath){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadPath + "/" + destFileName));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(source);bufferedOutputStream.flush();bufferedOutputStream.close();
        } catch (IOException exception) {
            logger.error("Error in writing to file(" + destFileName + ")", exception);
            return false;
        }
        return true;
    }

    private List<SupplierAttributes> filterSupplierAttributes(List<SupplierAttributes> supplierAttributeList, ViewFilter viewFilter){
        List<SupplierAttributes> filteredSupplierAttributeList = new ArrayList<SupplierAttributes>();

        if(viewFilter.equals(ViewFilter.INACTIVE)){
            for(SupplierAttributes supplierAttribute: supplierAttributeList){
                if(!supplierAttribute.isActive()){
                    filteredSupplierAttributeList.add(supplierAttribute);
                }
            }
        } else  if(viewFilter.equals(ViewFilter.ACTIVE)){
            for(SupplierAttributes suplier: supplierAttributeList){
                if(suplier.isActive()){
                    filteredSupplierAttributeList.add(suplier);
                }
            }
        }else if(viewFilter.equals(ViewFilter.NONE)){
            filteredSupplierAttributeList = supplierAttributeList;
        }

        return filteredSupplierAttributeList;
    }

    private List<SupplierAttributes> filterWithinSupplierAttributes(String searchWithin, List<SupplierAttributes> filterTotalSupplierAttributeList){
        if(StringUtils.isEmpty(searchWithin)) return filterTotalSupplierAttributeList;

        searchWithin = searchWithin.toLowerCase();
        List<SupplierAttributes> filteredWithinSupplierAttributes = new ArrayList<SupplierAttributes>();
        for(SupplierAttributes supplierAttribute: filterTotalSupplierAttributeList){
            if(supplierAttribute.getAttributeName().toLowerCase().contains(searchWithin))
                filteredWithinSupplierAttributes.add(supplierAttribute);
        }
        return filteredWithinSupplierAttributes;
    }

    private List<CatalogSummary> filterWithinCatalogs(String searchWithin, List<CatalogSummary> filterTotalSupplierAttributeList){
        if(StringUtils.isEmpty(searchWithin)) return filterTotalSupplierAttributeList;

        searchWithin = searchWithin.toLowerCase();
        List<CatalogSummary> filteredWithinCatalogs = new ArrayList<CatalogSummary>();
        for(CatalogSummary catalogSummary: filterTotalSupplierAttributeList){
            if(catalogSummary.getCatalogNameNoSpace().toLowerCase().contains(searchWithin))
                filteredWithinCatalogs.add(catalogSummary);
        }
        return filteredWithinCatalogs;
    }

    private List<SupplierAttributes> getFilteredSupplierAttributePage(int pageNum, List<SupplierAttributes>  filterTotalSupplierAttributeList, int pageSize){
        List<SupplierAttributes> filteredSupplierAttributePage = new ArrayList<SupplierAttributes>();

        int offset =  pageSize*(pageNum-1) ;

        int maxCount = (filterTotalSupplierAttributeList.size() > pageSize + offset)? pageSize + offset: filterTotalSupplierAttributeList.size() ;

        filteredSupplierAttributePage = filterTotalSupplierAttributeList.subList(offset, maxCount);

        return filteredSupplierAttributePage;
    }    

    private void setSupplierAttributePagination(String companyCode, int supplierAttributeCurrentPageNum, ViewFilter supplierAttributeViewFiler,
            String sortBy,  String sortDirection, String searchWithin, ModelMap modelMap, int pageSize) throws  Exception{

        List<SupplierAttributes> totalSupplierAttributeList = supplierService.getSupplierAttributesByUnitId(companyCode);

        int numOfActiveSupplierAttribute = 0;
        for(SupplierAttributes supplierAttribute: totalSupplierAttributeList){
            if(supplierAttribute.isActive()){
                numOfActiveSupplierAttribute += 1;
            }
        }
        // These are for upper portion of the page
        modelMap.addAttribute("totalSupplierAttributesCount", new Integer(totalSupplierAttributeList.size()));
        modelMap.addAttribute("numOfActiveSupplierAttribute", new Integer(numOfActiveSupplierAttribute));

        // Pagination Info
        List<SupplierAttributes> filterTotalSupplierAttributeList = filterSupplierAttributes(totalSupplierAttributeList, supplierAttributeViewFiler);

        // Search Within filter
        if(!StringUtils.isEmpty(searchWithin) && !searchWithin.equalsIgnoreCase("Search within")){
            filterTotalSupplierAttributeList = filterWithinSupplierAttributes(searchWithin, filterTotalSupplierAttributeList);
            modelMap.addAttribute("searchWithin", searchWithin);
        }else{
            modelMap.addAttribute("searchWithin", "");
        }

        int totalFilteredSupplierAttributesCount = filterTotalSupplierAttributeList.size();
        modelMap.addAttribute("totalFilteredSupplierAttributesCount", new Integer(totalFilteredSupplierAttributesCount));

        if(filterTotalSupplierAttributeList.size() <= (supplierAttributeCurrentPageNum-1)*pageSize){
            supplierAttributeCurrentPageNum = 1; // Reset to 1 or go back one page
        }

        if(StringUtils.isEmpty(sortBy)){
            sortBy = "Name";
        }
        modelMap.addAttribute("sortBy", sortBy);

        if(StringUtils.isEmpty(sortDirection)){
            sortDirection= "up";
        }
        modelMap.addAttribute("sortDirection", sortDirection);

//        Comparator<SupplierAttributes> comparator = null;
//        if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("up")){
//            comparator = new SupplierAttributeNameComparatorAsc();
//        }else if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("down")){
//            comparator = new SupplierAttributeNameComparatorDes();
//        }if(sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("up")){
//            comparator = new DateComparatorAsc();
//        }else if(sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("down")){
//            comparator = new DateComparatorDes();
//        }else if(sortBy.equalsIgnoreCase("Creator") && sortDirection.equalsIgnoreCase("up")){
//            comparator = new CreatorComparatorAsc();
//        }else if(sortBy.equalsIgnoreCase("Creator") && sortDirection.equalsIgnoreCase("down")){
//            comparator = new CreatorComparatorDes();
//        }else if(sortBy.equalsIgnoreCase("Status") && sortDirection.equalsIgnoreCase("up")){
//            comparator = new StatusComparatorAsc();
//        }else if(sortBy.equalsIgnoreCase("Status") && sortDirection.equalsIgnoreCase("down")){
//            comparator = new StatusComparatorDes();
//        }

        // Sort
        //Collections.sort(filterTotalSupplierAttributeList, comparator);

        // Data
        modelMap.addAttribute("supplierAttributes", getFilteredSupplierAttributePage(supplierAttributeCurrentPageNum, filterTotalSupplierAttributeList, pageSize));

        modelMap.addAttribute("supplierAttributeCurrentPageNum", new Integer(supplierAttributeCurrentPageNum));
        int supplierAttributeTotalPageNum = ( (totalFilteredSupplierAttributesCount % pageSize) == 0)? totalFilteredSupplierAttributesCount/pageSize : totalFilteredSupplierAttributesCount/pageSize + 1;
        modelMap.addAttribute("supplierAttributeTotalPageNum", supplierAttributeTotalPageNum);

        int supplierAttributePageFirstItemNum = (supplierAttributeCurrentPageNum-1)*pageSize + 1;
        modelMap.addAttribute("supplierAttributePageFirstItemNum", supplierAttributePageFirstItemNum);

        int supplierAttributePageLastItemNum = (supplierAttributeCurrentPageNum*pageSize < totalFilteredSupplierAttributesCount)? supplierAttributePageFirstItemNum + pageSize-1 : totalFilteredSupplierAttributesCount;
        modelMap.addAttribute("supplierAttributePageLastItemNum", new Integer(supplierAttributePageLastItemNum));

        modelMap.addAttribute("supplierAttributeTotalPageNum", new Integer(supplierAttributeTotalPageNum));

        modelMap.addAttribute("isSupplierAttributePage", Boolean.TRUE);
        modelMap.addAttribute("goToPrevSupplierAttributePage", "goToSupplierAttributePage(" + (supplierAttributeCurrentPageNum - 1) + ");");
        modelMap.addAttribute("goToNextSupplierAttributePage", "goToSupplierAttributePage(" + (supplierAttributeCurrentPageNum + 1) + ");");
    }
}
