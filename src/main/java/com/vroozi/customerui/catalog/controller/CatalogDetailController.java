package com.vroozi.customerui.catalog.controller;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.approver.controller.ApproverUIController;
import com.vroozi.customerui.approver.services.ApproverService;
import com.vroozi.customerui.catalog.common.CatalogDataDisplayPopulator;
import com.vroozi.customerui.catalog.exception.HttpInternalServerError;
import com.vroozi.customerui.catalog.model.CatalogCustomField;
import com.vroozi.customerui.catalog.model.CatalogCustomFieldForm;
import com.vroozi.customerui.catalog.model.CatalogItemCollection;
import com.vroozi.customerui.catalog.model.CatalogItemPost;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.model.CustomField;
import com.vroozi.customerui.catalog.model.ExternalCatalogFields;
import com.vroozi.customerui.catalog.model.PaginatedCatalogItems;
import com.vroozi.customerui.catalog.services.CatalogService;
import com.vroozi.customerui.catalogItem.model.Item;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.localization.EndUserMessage;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.property.service.PropertyService;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.DecimalNotation;
import com.vroozi.customerui.util.FileUtility;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RestServiceUrl;
import com.vroozi.customerui.util.StringUtils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vroozi.customerui.util.Consts.ASS_CUSTOM_FIELD_SECTION;
import static com.vroozi.customerui.util.Consts.CATALOG_DETAIL_PAGE;
import static com.vroozi.customerui.util.Consts.CATALOG_PROFILES_PAGE_FRAMGMENT;
import static com.vroozi.customerui.util.Consts.CUSTOM_FIELD_SECTION;
import static com.vroozi.customerui.util.Consts.FAILURE;
import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;
import static com.vroozi.customerui.util.Consts.NOTASS_PROFILES_PAGE_FRAMGMENT;
import static com.vroozi.customerui.util.Consts.PROFILES_PAGE_FRAMGMENT;
import static com.vroozi.customerui.util.Consts.SUCCESS;

//import org.apache.commons.codec.language.bm.Rule.RPattern;

/**
 * Provides endpoint controller for adminUI Catalog Detail Page.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 9/14/12:1:46 PM
 */
@Controller
public class CatalogDetailController {


    private final Logger logger = LoggerFactory.getLogger(CatalogDetailController.class);

    public static final String  FORMAT_DATE_DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy  hh:mm a z";
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

    @Autowired
    RestServiceUrl restServiceUrl;

    @Autowired
    AppConfig appConfig;

    @Autowired
    CatalogService catalogService;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    ApproverService approverService;

    @Autowired
    ProfileService profileService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    UploadService uploadService;

    @Autowired
    CatalogDataDisplayPopulator catalogDataDisplayPopulator;

    @Autowired
    ApproverUIController approverUIController;

    @RequestMapping("/catalogDetail")
    public String catalogDetailPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        try{
            String catalogId = request.getParameter("catalogId");

            User user = SessionDataRetriever.getLoggedInUser(request);
            CatalogSummary catalogSummary = catalogService.getCatalog(catalogId);

            catalogDataDisplayPopulator.populateCatalogDisplayNames(catalogSummary);

            modelMap.addAttribute("catalogSummary", (Object) catalogSummary);

            List<User> allApprovers = getAllApprovers(user.getUnitId(),user.getUserId());
            modelMap.addAttribute("allApprovers", allApprovers);

            approverUIController.renderBothSections(user.getUnitId(), catalogId, null, 1, 1, modelMap, request);

            setCatalogProfilePaginationAttributes(user.getUnitId(), catalogId, 1, modelMap,user);
            setProfilePaginationAttributes(user.getUnitId(), catalogId, 1, modelMap,user);

            Map<String, CatalogCustomField> catalogCustomFieldsMap = getCatalogCustomFieldListForCompany(catalogId, logger);
            modelMap.addAttribute("catalogCustomFields", (Object) catalogCustomFieldsMap);

            modelMap.addAttribute("userName", user.getFullName());
            modelMap.addAttribute("smartOCIAppBaseURI", appConfig.smartOCIAppBaseURI);

            if(catalogSummary.getOutputRecords()==null){
                catalogSummary.setOutputRecords(0);
            }

            modelMap.addAttribute("externalCatalogList", catalogService.getCatalogsByType(user.getUnitId(), 1));
            /*List<Supplier> supplierCompanyList = supplierService.getActiveSupplierByUnitId(user.getUnitId());
            modelMap.addAttribute("supplierCompanyList", supplierCompanyList);*/

            CatalogSummary catatlog = catalogService.getCatalog(catalogId);

            List<ExternalCatalogFields> externalCatalogFieldses = null;
            if(catatlog.getFields() != null && catatlog.getFields().size() > 0){
                externalCatalogFieldses = catatlog.getFields();
            }else{
                externalCatalogFieldses = new ArrayList<ExternalCatalogFields>();
            }
            modelMap.addAttribute("catalogExternalFields", externalCatalogFieldses);
            modelMap.addAttribute("extCatalogMethod", catatlog.getExtCatalogMethod());
            modelMap.addAttribute("extCommunicationMethod", catatlog.getExtCommunicationMethod());
//            List<User> allApprovers = getAllApprovers(user.getUnitId(),user.getUserId());
            modelMap.addAttribute("allApprovers", allApprovers);
            modelMap.addAttribute("profileList", populateProfiles(user));
            modelMap.addAttribute("approverList",populateApprovers(user));
            String adminUIPath = appConfig.environmentUrl;
            if(adminUIPath.endsWith("login")) {
            	adminUIPath = adminUIPath.substring(0, adminUIPath.lastIndexOf("login"));
            }
            adminUIPath = adminUIPath + "checkout";
            modelMap.addAttribute("adminUIPath", adminUIPath);
        }catch (Exception exp){
            logger.error("Error in Pagination of profiles", exp);
        }

        //ie issues.. caching .. backbone calling is failing..
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, pre-check=0, post-check=0, private");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return CATALOG_DETAIL_PAGE;
    }

    

    
    private List<Profile> populateProfiles(User user){
    	List<Profile> profiles = new ArrayList<Profile>();
    	try{
	    	List<Role> userRoles = user.getRoles();
	    	boolean isMaster = false;
	    	for (Role role : userRoles) {
				if(role == role.MASTER_ADMINISTRATOR){
					isMaster = true;
					break;
				}
			}
	    	
	    	
	    	if(isMaster){
	    		profiles = new ArrayList<Profile>(getProfileListForCompany(user.getUnitId()));
	    	}else{
	    		if(user.getAssignedProfiles()!=null && !user.getAssignedProfiles().isEmpty()){
		    		List<ProfileProxy> allProfileProxies = profileService.getProfilesByIds(user.getAssignedProfiles());
		    		profiles = convertToProfileList(allProfileProxies);
	    		}
	    	}
    	}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    	
    	return profiles;
    }
    
    
    private List<User> populateApprovers(User user){
    	
    	List<User> users = new ArrayList<User>();
    	try{
	    	List<Role> userRoles = user.getRoles();
	    	boolean isMaster = false;
	    	for (Role role : userRoles) {
				if(role == role.MASTER_ADMINISTRATOR){
					isMaster = true;
					break;
				}
			}
	    	
	    	if(isMaster){
	    		users = getAllApprovers(user.getUnitId());
	    	}else{
	    		users = getAllApprovers(user.getUnitId(),user.getUserId());
	    	}
    	}catch (Exception e) {
    		logger.error(e.getMessage(),e);
		}
    	
    	return users;
    }
    
    private List<User> getAllApprovers(String unitId){
        List<User> approvers = new ArrayList<User>();
        try{
            approvers = userManagementService.getApproverListForCompany(unitId);
        }catch (Exception exp){
            logger.error("Error retrieving Approvers for unitId(" + unitId + ") ", exp);
        }
        return approvers;
    }
    // ++++++++++++++++++++++++++++++++++++ START PROFILE SECTION  ++++++++++++++++++++++++++++++++++++++++++++++

    private int findProfileByName(List<Profile>  profiles, String profileName){
        for(int i= 0; i < profiles.size(); ++i){
            Profile profile = profiles.get(i);
            if(profile.getProfileName().equalsIgnoreCase(profileName)){
                return i;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/addCatalogProfile", method = RequestMethod.POST)
    public String addCatalogProfile(HttpServletRequest request,
                                    @ModelAttribute ProfileProxy profile, ModelMap modelMap) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if(user == null){
            return INVALID_SESSION_PAGE;
        }

        try {
            String catalogId = request.getParameter("catalogId");

            List<Profile> allProfiles = getProfiles(catalogId, user);

            if(findProfileByName(allProfiles, profile.getProfileName()) == -1){
                profile.setCreatedBy(user.getUsername());
                profile.setCreatedByName(user.getFullName());
                profile.setUnitId(user.getUnitId());
                profile.setCreatedOn(new Date());
                profile.setActive(true);
                
                List<String>  associatedCatalogs = new ArrayList<String>();
                associatedCatalogs.add(catalogId);
                profile.setAssociatedCatalogs(associatedCatalogs);
                profileService.addProfile(profile);
            }

            setCatalogProfilePaginationAttributes(user.getUnitId(), catalogId,1, modelMap,user);
        } catch (Exception exp) {
            logger.error("Error creating profile ", exp);

        }
        return PROFILES_PAGE_FRAMGMENT;
    }

    @RequestMapping(value = "/addCatalogProfiles", method = RequestMethod.POST)
    public String addCatalogProfiles(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam( value="catalogId") String catalogId, ModelMap modelMap) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if(user == null){
            return INVALID_SESSION_PAGE;

        }

        String[] selectedProfileIds = request.getParameterValues("profileIds");
        try {
            profileService.addCatalogProfiles(catalogId, selectedProfileIds);
            setProfilePaginationAttributes(user.getUnitId(), catalogId, 1, modelMap,user);
            setCatalogProfilePaginationAttributes(user.getUnitId(), catalogId, 1, modelMap,user);
        } catch (Exception exp) {
            logger.error("Error adding profiles for catalog "+catalogId, exp);


        }
        return CATALOG_PROFILES_PAGE_FRAMGMENT;
    }

    private List<Profile> getProfiles(String catalogId, User user) {
        List<Profile> profileList = getProfileListForCompany(user.getUnitId());
        List<Integer> profileIds1 = getProfileListForCatalog(user.getUnitId(), catalogId);
        List<Profile> allProfiles = setProfilesForCompany(profileList, profileIds1);
        return allProfiles;
    }

    private List<Profile> setProfilesForCompany(List<Profile> profiles, List<Integer> catalogIds){
        for (Profile profile : profiles) {
            profile.setAttached(catalogIds.contains(profile.getProfileId()));
        }
        return profiles;
    }

    List<Profile> getProfileListForCompany(String unitId){
        List<Profile> profiles = new ArrayList<Profile>();
        try{
            profiles = profileService.getActiveProfilesByUnitId(unitId);
        }catch (Exception exp){
            logger.error("Error retrieving Profiles for UnitId(" + unitId + ") ", exp);
        }
        return profiles;
    }

    private List<Integer> getProfileListForCatalog(String unitId, String catalogId){

        try{
            return profileService.getProfileListForCatalog(unitId, catalogId);
        }catch (Exception exp){
            logger.error("Error retrieving profiles for catalogId(" + catalogId + ") ", exp);
        }
        return null;
    }


    @RequestMapping(value = "/removeCatalogProfiles", method = RequestMethod.POST)
    public String deleteProfiles(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam( value="catalogId") String catalogId, ModelMap modelMap) {

        try {

            User user = SessionDataRetriever.getLoggedInUser(request);

            String[] selectedProfileIds = request.getParameterValues("profileIds");
            profileService.deleteCatalogProfiles(catalogId, selectedProfileIds);
            setProfilePaginationAttributes(user.getUnitId(), catalogId, 1, modelMap,user);
            setCatalogProfilePaginationAttributes(user.getUnitId(), catalogId, 1, modelMap,user);

        } catch (Exception exp) {
            logger.error("Error disassociating profiles from catalog (" + catalogId + ") ", exp);
            return FAILURE;
        }

        return CATALOG_PROFILES_PAGE_FRAMGMENT;
    }

    @RequestMapping(value="/goToCatalogProfilePage", method=RequestMethod.POST)
    public String goToProfileCatalogPage(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value="pageNum", required=true ) Integer pageNum,  @RequestParam(value="catalogId", required=true ) String catalogId, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);

            setCatalogProfilePaginationAttributes(user.getUnitId(), catalogId, pageNum, modelMap,user);

        }catch (Exception exp){
            logger.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILES_PAGE_FRAMGMENT;
    }

    private void setCatalogProfilePaginationAttributes(String companyCode, String catalogId, int profileCurrentPageNum, ModelMap modelMap,User user) throws  Exception{
        List<Profile> totalProfileList;
        if(user.getRoles().contains(Role.MASTER_ADMINISTRATOR)){
            totalProfileList = catalogService.getProfileListForCatalog(companyCode,catalogId);
        }
        else{
            List<Profile> fianlProfile=new ArrayList<Profile>();

            List<Integer> profilesOfThisUser = user.getAssignedProfiles();
            List<Profile> allProfilesWithThisCatalog = catalogService.getProfileListForCatalog(companyCode, catalogId);

            for(Profile profile:allProfilesWithThisCatalog){

                if(profilesOfThisUser.contains(profile.getProfileId())){
                    fianlProfile.add(profile);
                }
            }
            totalProfileList= fianlProfile;
        }
        int pageSize= user.getRowsPerPage();
        int totalProfilesCount = totalProfileList.size();
        modelMap.addAttribute("totalProfilesCount", new Integer(totalProfilesCount));
        modelMap.addAttribute("totalFilteredProfilesCount", new Integer(totalProfilesCount));

        // Pagination Info
        if(totalProfilesCount <= (profileCurrentPageNum-1)*pageSize){
            profileCurrentPageNum = 1; // Reset to 1 or go back one page
        }

        // Data
        modelMap.addAttribute("profiles", getPageItems(profileCurrentPageNum, totalProfileList,pageSize));

        modelMap.addAttribute("profileCurrentPageNum", new Integer(profileCurrentPageNum));
        int profileTotalPageNum = ( (totalProfilesCount % pageSize) == 0)? totalProfilesCount/pageSize : totalProfilesCount/pageSize + 1;
        modelMap.addAttribute("profileTotalPageNum", profileTotalPageNum);

        int profilePageFirstItemNum = (profileCurrentPageNum-1)*pageSize + 1;
        modelMap.addAttribute("profilePageFirstItemNum", profilePageFirstItemNum);

        int profilePageLastItemNum = (profileCurrentPageNum*pageSize < totalProfilesCount)? profilePageFirstItemNum + pageSize-1 : totalProfilesCount;
        modelMap.addAttribute("profilePageLastItemNum", new Integer(profilePageLastItemNum));

        modelMap.addAttribute("profileTotalPageNum", new Integer(profileTotalPageNum));

        modelMap.addAttribute("catalogId", catalogId);
        modelMap.addAttribute("isProfilePage", Boolean.FALSE);
        modelMap.addAttribute("goToPrevProfilePage", "goToCatalogProfilePage(" + (profileCurrentPageNum - 1) + ");");
        modelMap.addAttribute("goToNextProfilePage", "goToCatalogProfilePage(" + (profileCurrentPageNum + 1) + ");");
    }

    private List<Profile> subtractProfiles(List<Profile> profiles, List<Integer> profileIds){
        List<Profile> remainderCatalogs = new ArrayList<Profile>();
        for(Integer profileId: profileIds) {
            for(Profile profile: profiles){
                if(profile.getProfileId() == profileId.intValue()){
                    remainderCatalogs.add(profile);
                    profiles.remove(profile);
                    break;
                }
            }
        }

        return remainderCatalogs;
    }

    @RequestMapping(value="/goToCatalogNotAssProfilePage", method=RequestMethod.POST)
    public String goToNotAssProfilePage(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value="pageNum", required=true ) Integer pageNum,
                                        @RequestParam(value="catalogId", required=true ) String catalogId, ModelMap modelMap){
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);

            setProfilePaginationAttributes(user.getUnitId(), catalogId, pageNum, modelMap,user);

        }catch (Exception exp){
            logger.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return NOTASS_PROFILES_PAGE_FRAMGMENT;
    }

    
	private List<Profile> convertToProfileList(List<ProfileProxy> profileProxyList){
		List<Profile> profiles = new ArrayList<Profile>();
		Profile profile = null;
		for (ProfileProxy profileProxy : profileProxyList) {
			profile = new Profile();
			
			profile.setProfileId(profileProxy.getProfileId());
			profile.setProfileName(profileProxy.getProfileName());
			profile.setProfileDesc(profileProxy.getProfileDesc());
			profile.setCreatedOn(profileProxy.getCreatedOn());
			profile.setCreatedOnView(profileProxy.getCreatedOnView());
			profile.setCreatedBy(profileProxy.getCreatedBy());
			profile.setActive(profileProxy.isActive());
			profile.setUnitId(profileProxy.getUnitId());
			profile.setProductRating(profileProxy.getProductRating());
			profile.setAssociatedCatalogs(profileProxy.getAssociatedCatalogs());

			profiles.add(profile);			
		}
		
		return profiles;
	}    
    
    private void setProfilePaginationAttributes(String companyCode, String catalogId, int profileCurrentPageNum, ModelMap modelMap,User user) throws  Exception{

    	
    	List<Role> userRoles = user.getRoles();
    	boolean isMaster = false;
    	for (Role role : userRoles) {
			if(role == role.MASTER_ADMINISTRATOR){
				isMaster = true;
				break;
			}
		}
    	
    	int pageSize= user.getRowsPerPage();
    	List<Profile> totalNotAssProfileList = new ArrayList<Profile>();
    	if(isMaster){
    		totalNotAssProfileList = new ArrayList<Profile>(getProfileListForCompany(companyCode));
    	}else{
    		if(user.getAssignedProfiles()!=null && !user.getAssignedProfiles().isEmpty()){
	    		List<ProfileProxy> allProfileProxies = profileService.getProfilesByIds(user.getAssignedProfiles());
	    		totalNotAssProfileList = convertToProfileList(allProfileProxies);
    		}
    	}
	
    	
//        List<Profile> totalNotAssProfileList = 
        
        
        
        List<Integer> catalogProfileIds = getProfileListForCatalog(companyCode, catalogId);

        subtractProfiles(totalNotAssProfileList, catalogProfileIds);

        int totalProfilesCount = totalNotAssProfileList.size();
        modelMap.addAttribute("totalNotAssProfilesCount", new Integer(totalProfilesCount));

        // Pagination Info
        if(totalProfilesCount <= (profileCurrentPageNum-1)*pageSize){
            profileCurrentPageNum = 1; // Reset to 1 or go back one page
        }

        // Data
        modelMap.addAttribute("notAssProfiles", getPageItems(profileCurrentPageNum, totalNotAssProfileList, pageSize));

        modelMap.addAttribute("notAssProfileCurrentPageNum", new Integer(profileCurrentPageNum));
        int profileTotalPageNum = ( (totalProfilesCount % pageSize) == 0)? totalProfilesCount/pageSize : totalProfilesCount/pageSize + 1;
        modelMap.addAttribute("notAssProfileTotalPageNum", profileTotalPageNum);

        int profilePageFirstItemNum = (profileCurrentPageNum-1)*pageSize + 1;
        modelMap.addAttribute("notAssProfilePageFirstItemNum", profilePageFirstItemNum);

        int profilePageLastItemNum = (profileCurrentPageNum*pageSize < totalProfilesCount)? profilePageFirstItemNum + pageSize-1 : totalProfilesCount;
        modelMap.addAttribute("notAssProfilePageLastItemNum", new Integer(profilePageLastItemNum));

        modelMap.addAttribute("notAssProfileTotalPageNum", new Integer(profileTotalPageNum));

        modelMap.addAttribute("catalogId", catalogId);
        modelMap.addAttribute("goToPrevNotAssProfilePage", "goToCatalogNotAssProfilePage(" + (profileCurrentPageNum - 1) + ");");
        modelMap.addAttribute("goToNextNotAssProfilePage", "goToCatalogNotAssProfilePage(" + (profileCurrentPageNum + 1) + ");");
    }

    public static List<? extends Object> getPageItems(int pageNum, List<? extends Object> list, int pageSize){
        List<? extends Object> pageItems = new ArrayList<Object>();

        int offset =  pageSize*(pageNum-1) ;

        int maxCount = (list.size() > pageSize + offset)? pageSize + offset: list.size() ;

        pageItems  = list.subList(offset, maxCount);

        return pageItems;
    }

    // ++++++++++++++++++++++++++++++++++++ END PROFILE SECTION  ++++++++++++++++++++++++++++++++++++++++++++++


    private void getCatalogSupplierName(CatalogSummary catalog){
        String supplierName = "";
        try{
            if(CatalogDataDisplayPopulator.isValidSupplierId(catalog.getSupplierId())){
                supplierName = userManagementService.getSupplierNameBySupplierId(catalog.getSupplierId());
            }
        }catch (AdminUIException auie){
            logger.error("Error retrieving catalog(" + catalog.getName() + ") supplierName", auie);
        }catch (Exception e){
            logger.error("Error retrieving catalog(" + catalog.getName() + ") supplierName", e);
        }
        catalog.setSupplierName(supplierName);
    }

        List<User> approvers = new ArrayList<User>();
        private List<User> getAllApprovers(String unitId,String userId){
        try{
            approvers = userManagementService.getApproverListForUser(unitId,userId);
        }catch (Exception exp){
            logger.error("Error retrieving Approvers", exp);
        }
        return approvers;
    }


    private List<User> getCatalogApprover(List<User> allApprovers , String catalogId){
        List<User> catalogApprovers = new ArrayList<User>();
        try{
            List<String> approverIds = approverService.getApproversByCatalogId(catalogId);
            if(approverIds == null || approverIds.size() == 0){
                return catalogApprovers;
            }
            catalogApprovers = subtractUsers(allApprovers, approverIds);
        }catch (Exception exp){
            logger.error("Error retrieving Approvers", exp);
        }
        return catalogApprovers;
    }

    private List<User> subtractUsers(List<User> users, List<String> ids){
        List<User> remainderCatalogs = new ArrayList<User>();

        if(ids!=null){
            for(String userId: ids) {
                for(User user: users){
                    if(user.getUserId().equals(userId)){
                        remainderCatalogs.add(user);
                        users.remove(user);
                        break;
                    }
                }
            }
        }else{
            return users;
        }
        return remainderCatalogs;
    }

    @RequestMapping(value="/getCatalogCustomField", method=RequestMethod.POST)
    public @ResponseBody String getCatalogCustomField(HttpServletRequest request, @RequestParam String catalogCustomFieldId){

        User user = SessionDataRetriever.getLoggedInUser(request);

        String catalogCustomFieldFormJSON = "";
        try {
            CatalogCustomFieldForm catalogCustomFieldForm = new CatalogCustomFieldForm();
            CatalogCustomField catalogCustomField = catalogService.getCatalogCustomField(catalogCustomFieldId);
            CustomField customField = catalogService.getCustomField(catalogCustomField.getCustomFieldId());

            catalogCustomFieldForm.setCatalogCustomField(catalogCustomField);
            catalogCustomFieldForm.setDisplayName(customField.getDisplayName()!=null?customField.getDisplayName():"");
            catalogCustomFieldForm.setFieldDesc(customField.getFieldDesc()!=null?customField.getFieldDesc():"");

            catalogCustomFieldFormJSON = catalogCustomFieldForm.getCatalogCustomFieldFormJSON();
        } catch (Exception exception) {
            logger.error("Error in adding custom fields", exception);
        }
        return catalogCustomFieldFormJSON;
    }

    @RequestMapping(value = "/updateCatalogCustomField", method=RequestMethod.POST)
    public String updateCatalogCustomField(HttpServletRequest request, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        String catalogCustomFieldIds = catalogCustomFieldForm.getCatalogCustomFieldIds();

        try {
            //          catalogService.deleteCatalogCustomFieldByCatalogId(catalogCustomFieldIds);
        } catch (Exception exception) {
            logger.error("Error in deleting custom fields", exception);
        }

        return "redirect:catalogDetail?catalogId="+catalogCustomFieldForm.getCatalogId();
    }


    @RequestMapping(value = "/deleteCatalogCustomField", method=RequestMethod.POST)
    public String deleteCatalogCustomField(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        if(user == null) return INVALID_SESSION_PAGE;

        String catalogCustomFieldIds = catalogCustomFieldForm.getCatalogCustomFieldIds();
        String catalogId = catalogCustomFieldForm.getCatalogId();

        try {
            catalogService.deleteCatalogCustomFieldByCatalogId(catalogId, catalogCustomFieldIds);

            Map<String, CatalogCustomField> catalogCustomFieldsMap = getCatalogCustomFieldListForCompany(catalogId, logger);
            modelMap.addAttribute("catalogCustomFields", (Object) catalogCustomFieldsMap);

        } catch (Exception exception) {
            logger.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return CUSTOM_FIELD_SECTION;
    }

    @RequestMapping(value = "/updateCatalogCustomFieldList", method=RequestMethod.POST)
    public String updateCatalogCustomFieldList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        List<CatalogCustomField> fieldList = new ArrayList<CatalogCustomField>();
        List<String> ids = catalogCustomFieldForm.getCatalogCustomFieldId();
        String catalogId = catalogCustomFieldForm.getCatalogId();

        try {
            int i = 0;
            for(String id : ids){
                i++;

                CatalogCustomField ccf = new CatalogCustomField();
                ccf.setDisplayOrder(i);
                ccf.setId(id);
                ccf.setCatalogId(catalogId);
                if(catalogCustomFieldForm.getCatalogCustomRequired().contains("required_"+id)){
                    ccf.setRequired(true);
                } else {
                    ccf.setRequired(false);
                }

                if(catalogCustomFieldForm.getCatalogCustomPostFilterable().contains("postFilterable_"+id)){
                    ccf.setPostFilterable(true);
                } else {
                    ccf.setPostFilterable(false);
                }

                if(catalogCustomFieldForm.getCatalogCustomSearchable().contains("searchable_"+id)){
                    ccf.setSearchable(true);
                } else {
                    ccf.setSearchable(false);
                }

                if(catalogCustomFieldForm.getMappingIdList().contains("0_"+id)){
                    ccf.setMappingId("0");
                } else if(catalogCustomFieldForm.getMappingIdList().contains("1_"+id)){
                    ccf.setMappingId("1");
                } else if(catalogCustomFieldForm.getMappingIdList().contains("2_"+id)){
                    ccf.setMappingId("2");
                } else if(catalogCustomFieldForm.getMappingIdList().contains("3_"+id)){
                    ccf.setMappingId("3");
                } else if(catalogCustomFieldForm.getMappingIdList().contains("4_"+id)){
                    ccf.setMappingId("4");
                } else if(catalogCustomFieldForm.getMappingIdList().contains("5_"+id)){
                    ccf.setMappingId("5");
                } else if(catalogCustomFieldForm.getMappingIdList().contains("6_"+id)){
                    ccf.setMappingId("6");
                }

                fieldList.add(ccf);
            }

            catalogService.updateCatalogCustomFields(fieldList,catalogId);

            Map<String, CatalogCustomField> catalogCustomFieldsMap = getCatalogCustomFieldListForCompany(catalogId, logger);
            modelMap.addAttribute("catalogCustomFields", (Object) catalogCustomFieldsMap);

        } catch (Exception exception) {
            logger.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return CUSTOM_FIELD_SECTION;
    }

    @RequestMapping(value = "/addCatalogCustomField", method=RequestMethod.POST)
    public String addCatalogCustomField(HttpServletRequest request, @ModelAttribute CustomField customField,@ModelAttribute FileUtility customFieldFileUtility, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        String catalogId = request.getParameter("catalogId");
        try {
            customField.setUnitId(user.getUnitId());

            if(customField.getFieldType().equalsIgnoreCase("flag") && customFieldFileUtility.getIconFile() != null && !customFieldFileUtility.getIconFile().isEmpty()) {
                String ext = customFieldFileUtility.getIconFile().getOriginalFilename().substring(customFieldFileUtility.getIconFile().getOriginalFilename().lastIndexOf("."), customFieldFileUtility.getIconFile().getOriginalFilename().length());
                customField.setIcon(ext.trim());
            }
            if(!customField.getFieldType().equalsIgnoreCase("list")){
                customField.setDropDownValues(null);
            } else {
                customField.getDropDownValues().remove(0);
            }
            customField.setActive(true);
            customField = catalogService.addCustomField(customField);
            if(customField.getFieldType().equalsIgnoreCase("flag") && customFieldFileUtility.getIconFile() != null && !customFieldFileUtility.getIconFile().isEmpty()) {
                if(!uploadService.writeToFile(customFieldFileUtility.getIconFile().getInputStream(), customField.getIcon().trim(), appConfig.flagIconUploadPath)){
                    System.out.println("error uploading flag icon.");
                }
            }

            CatalogCustomField catalogCustomField = new CatalogCustomField(customField);
            catalogCustomField.setCatalogId(catalogId);
            catalogCustomField.setCustomFieldId(customField.getId());


            catalogService.addCatalogCustomField(catalogCustomField);

            Map<String, CatalogCustomField> catalogCustomFieldsMap = getCatalogCustomFieldListForCompany(catalogId, logger);
            modelMap.addAttribute("catalogCustomFields", (Object) catalogCustomFieldsMap);

        } catch (Exception exception) {
            logger.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return CUSTOM_FIELD_SECTION;
        //return "redirect:catalogDetail?catalogId="+catalogId;
    }

    @RequestMapping(value = "/editCatalogCustomField", method=RequestMethod.POST)
    public String editCatalogCustomField(HttpServletRequest request, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        //catalogCustomFieldForm.setUnitId(13);
        catalogCustomFieldForm.setLastUpdated(new Date());


        try {
//            catalogService.updateCustomField(catalogCustomFieldForm.getCustomField());
            catalogService.updateCatalogCustomField(catalogCustomFieldForm.getCatalogCustomField());

            Map<String, CatalogCustomField> catalogCustomFieldsMap = getCatalogCustomFieldListForCompany(catalogCustomFieldForm.getCatalogId(), logger);
            modelMap.addAttribute("catalogCustomFields", (Object) catalogCustomFieldsMap);

        } catch (Exception exception) {
            logger.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return CUSTOM_FIELD_SECTION;
        //return "redirect:catalogDetail?catalogId="+catalogCustomFieldForm.getCatalogId();
    }

    

    @RequestMapping(value = "/getCustomFields", method=RequestMethod.POST)
    public String getCustomFields(HttpServletRequest request, @RequestParam String catalogId,@RequestParam(value="catalogType", required=false ) String catalogType, @RequestParam(value="pageNumber", required=false ) Integer pageNumber, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        CatalogCustomFieldForm catalogCustomFieldForm = new CatalogCustomFieldForm();
        //String customFieldFormJSON = "";

        try {
            Map<String, CustomField> customFieldMap = catalogService.getCustomFieldsByStatus(user.getUnitId(),"true");
            
            Map<String, CatalogCustomField> catalogCustomFieldMap= catalogService.getCatalogCustomFieldByCatalogId(catalogId);
            
            
            if(pageNumber==null) {
            	pageNumber = 1;
            }
            int pageSize = user.getRowsPerPage();
            
            List<CustomField> customFields = catalogCustomFieldForm.getCustomFields(customFieldMap.values(), catalogCustomFieldMap.keySet(),catalogType == null ? false : catalogType.equals("1"));
            
            int offset =  pageSize*(pageNumber-1) ;
            int maxCount = (customFields.size() > pageSize + offset)? pageSize + offset: customFields.size() ;
            List<CustomField> pageItems  = customFields.subList(offset, maxCount);
            
            Page<CustomField> customFieldsPage = new Page<CustomField>(pageItems, pageNumber, pageSize, customFields.size());
            
            modelMap.addAttribute("customFieldsPage", customFieldsPage);
            modelMap.addAttribute("catalogId", catalogId);
            modelMap.addAttribute("catalogType", catalogType);

        } catch (Exception exception) {
            logger.error("Error in adding custom fields", exception);
            return FAILURE;
        }
        return ASS_CUSTOM_FIELD_SECTION;
    }


    @RequestMapping(value = "/associateCatalogCustomField", method=RequestMethod.POST)
    public String associateCatalogCustomField(HttpServletRequest request, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        String catalogId = request.getParameter("catalogId");
        try {

            List<String> customFieldList = catalogCustomFieldForm.getCustomFieldIdList();

            CustomField customField;

            for(String customFieldId : customFieldList) {
                customField = catalogService.getCustomField(customFieldId);
                customField.setUnitId(user.getUnitId());

                CatalogCustomField catalogCustomField = new CatalogCustomField(customField);
                catalogCustomField.setCatalogId(catalogId);
                catalogCustomField.setCustomFieldId(customField.getId());


                catalogService.addCatalogCustomField(catalogCustomField);
            }

            Map<String, CatalogCustomField> catalogCustomFieldsMap = getCatalogCustomFieldListForCompany(catalogId, logger);
            modelMap.addAttribute("catalogCustomFields", (Object) catalogCustomFieldsMap);

        } catch (Exception exception) {
            logger.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return CUSTOM_FIELD_SECTION;
    }

    /**
     * Update Items status
     * @author khansaj
     * @param request
     * @param response
     * @param itemIds
     */
    @RequestMapping("/updateItemsStatus")
    public void updateItemStatusItems(HttpServletRequest request,
                                      HttpServletResponse response,@RequestParam( value="itemIds") List<String> itemIds){
        String changedItemsCount = request.getParameter("changedItemsCount");
        String changedItemCountTokens [] = changedItemsCount.split("~~");
        User user = SessionDataRetriever.getLoggedInUser(request);
        CatalogItemPost itemPost = new CatalogItemPost();
        itemPost.setPending(Integer.parseInt(changedItemCountTokens[0]));
        itemPost.setApproved(Integer.parseInt(changedItemCountTokens[1]));
        itemPost.setRejected(Integer.parseInt(changedItemCountTokens[2]));
        itemPost.setNewStatus(request.getParameter("newStatusId"));
        itemPost.setCatalogId(request.getParameter("catalogId"));
        itemPost.setItemIds(itemIds);
        itemPost.setUserid(user.getUserId());
        catalogService.updateCatalogItemStatus(itemPost);
    }

    @RequestMapping(value = "/removeCatalogItems", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCatalogItems(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("catalogSummary") CatalogSummary catalogSummary,
                                     ModelMap modelMap) {
        //List<Item> catalogItems = new ArrayList<Item>();
       // CatalogItemList catalogItemList =null;
      //  int pNo = Integer.parseInt(request.getParameter("pageNum"));
        String catalogId   = request.getParameter("catalogId");

        try {

            int count = catalogService.deleteCatalogItems(catalogId, catalogSummary.getItemIds());
            // itemList = catalogDataDisplayPopulator.getCatalogItems(request.getParameter("catalogId"),pNo,logger);
//            catalogItemList =  catalogService.getCatalogItems(catalogId,null, pNo);
//            catalogItems = catalogItemList.getDataList();
//            if(catalogItems.size() <= 0 && pNo!= 1){
//                pNo = pNo-1;
//                // itemList = catalogDataDisplayPopulator.getCatalogItems(request.getParameter("catalogId"),pNo,logger);
//                catalogItemList =  catalogService.getCatalogItems(catalogId,null, pNo);
//                catalogItems = catalogItemList.getDataList();
//            }
//
//            if(catalogItems.size() <= 0 && pNo== 1 ){
//                pNo = pNo+1;
//                //itemList = catalogDataDisplayPopulator.getCatalogItems(request.getParameter("catalogId"),pNo,logger);
//                catalogItemList =  catalogService.getCatalogItems(catalogId,null, pNo);
//                catalogItems = catalogItemList.getDataList();
//                if(catalogItems.size() == 0)  //all items deleted
//                {
//                    pNo = 0;
//                }
//            }

          //  CatalogSummary catalogSummary1 = catalogService.getCatalog(catalogId);
            //catalogSummary.setOutputRecords(catalogSummary1.getOutputRecords()-catalogSummary.getItemIds().size());
            //catalogService.updateCatalogByCatalogId(String.valueOf(catalogSummary.getUnitId()),catalogSummary.getCatalogId(),String.valueOf(catalogSummary.getOutputRecords()));

        } catch (Exception exp) {
            logger.error("Error disassociating profiles from catalog ", exp);
            return FAILURE;
        }
      //  modelMap.addAttribute("catalogItems", (Object) catalogItems );
      //  modelMap.addAttribute("outputRecords",catalogSummary.getOutputRecords());
        //modelMap.addAttribute("pNo",pNo);
        //return CATALOGITEM_TABLE_FRAGMENT;
        return "OK";
    }
    @RequestMapping(value = "/view-error-report")
    public void viewErrorReport(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            String catalogId = request.getParameter("catId");
            CatalogSummary catalogSummary = catalogService.getCatalog(catalogId);
            catalogId = org.apache.commons.lang.StringUtils.isNotBlank(
                catalogSummary.getParentCatalogId()) ? catalogSummary.getParentCatalogId() : catalogSummary.getCatalogId();
            String fileName= StringUtils.stringCleanser(catalogSummary.getName())+"_"+catalogId+"_"+ catalogSummary.getRevision()+".pdf";
            BufferedInputStream is = new BufferedInputStream(new FileInputStream( ( appConfig.errorReportPath.endsWith("/")?appConfig.errorReportPath:appConfig.errorReportPath+"/")+fileName));

            response.setContentType("application/pdf");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("Error Report: "+ e);
            try{
                String fileName= "errorReportFailure.pdf";
                BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(appConfig.errorReportPath + "/" + fileName)));
                response.setContentType("application/pdf");
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }catch (Exception el){
                logger.error("Error Report: "+ e);
            }
        }
    }

    @RequestMapping(value = "/update-catalog")
    public String updateCatalogByCatalogId(HttpServletRequest request, ModelMap modelMap, String unitId, String catalogId, String outputRecords) {
        try {
        	
        	
        	
            User user = SessionDataRetriever.getLoggedInUser(request);
            catalogService.updateCatalogByCatalogId(unitId, catalogId, outputRecords,user.getUserId());

        } catch (Exception exp) {
            logger.error("Error opening error report ", exp);
            return FAILURE;
        }
        return SUCCESS;
    }

    private String getTierdPriceValue(String rawValue){
    	
    	String[] tokens = rawValue.split(";");
    	String value = "";
    	
    		if(tokens[0].indexOf("[") > -1){
    			value = tokens[0].substring(0,tokens[0].indexOf("[") ).trim();
    		}else{
    			value = tokens[0].trim();
    		}
    		
    		if(tokens.length > 1){
    			String temp = tokens[tokens.length-2];
	    		if(temp.indexOf("[") > -1){
	    			value+= " - "+temp.substring(0,tokens[0].indexOf("[") ).trim();
	    			if(value.indexOf("[") >-1){
	    				value = value.substring(0, value.indexOf("[")).trim();
	    			}
	    		}else{
	    			value+= " - "+temp.trim();
	    		}
    		}

    	
    	return value;
    	
    }
    
    /**
     * This method is used to retrieve paginated catalog items result
     * GET /catalogs/{catalogId}/items?searchTerm={searchTerm}
     * @param request HTTP servlet request
     * @param catalogId catalog ID
     * @param searchQuery search query
     * @return <code>PaginatedCatalogItems</code> object
     */
    @RequestMapping(value = "/catalogs/{catalogId}/paginated-items", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody
    PaginatedCatalogItems getCatalogsByItemId(HttpServletRequest request,HttpServletResponse response,
                                              @PathVariable("catalogId") String catalogId,
                                              @RequestParam(value = "searchTerm", required = false) String searchQuery, @RequestParam(value = "pageNo", required = false) String pageNo) throws HttpInternalServerError {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if(user.getDecimalNotation()==null) {
        user.setDecimalNotation("1");
        user.setDateFormat("2");
      }
      Integer userDecimalNotation = Integer.parseInt(user.getDecimalNotation());
     
      String price="";
      // parameter validation and initialization
        if(searchQuery == null) {
            searchQuery = "";
        }

        int pageNumber = pageNo!=null&&!"".equals(pageNo.trim())?Integer.parseInt(pageNo):1;
       // int pageSize = Consts.PAGE_SIZE;
        int pageSize = user.getRowsPerPage();
        
        long pageStartIndex = (pageNumber-1) * pageSize;

        CatalogItemCollection itemCollection;
        List<Item> items = new ArrayList<Item>();
        try {
            itemCollection = catalogService.getCatalogItems(catalogId, pageStartIndex, pageSize, searchQuery);
            
            for (Item catItem: itemCollection.getData().getItems()) {                  
                
              if(catItem.getPrice()!= null && !catItem.getPrice().isEmpty())
                catItem.setPrice(DecimalNotation.convertDecimalNotation(Double.parseDouble(catItem.getPrice()), userDecimalNotation));
    
                  if(null == catItem.getNewItemDescription() || catItem.getNewItemDescription().length()==0){
            		catItem.setNewItemDescription(catItem.getNewItemMatnr());
            	}
            	
            	catItem.setNewItemManufactmat(catItem.getNewItemManufactmat()!=null?catItem.getNewItemManufactmat():"");
            	
        		if(null!= catItem.getTieredPricing() && catItem.getTieredPricing().length() > 0){
        		 price = getTierdPriceValue(catItem.getTieredPricing());
        		 String[] priceRange =  price.split("-");
        		   for(int ii=0; ii<priceRange.length; ii++){
        		       if(ii==0)
        		         price=DecimalNotation.convertDecimalNotation(Double.parseDouble(priceRange[ii]), userDecimalNotation);
        		       else
        		         price+="-"+DecimalNotation.convertDecimalNotation(Double.parseDouble(priceRange[ii]), userDecimalNotation);
        		   }
        		 catItem.setPrice(price);
        		}
				items.add(catItem);
			}
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpInternalServerError(EndUserMessage.SEARCH_FAILED.toString(), e);
        }

        int totalNumOfPages = (int) ((itemCollection.getInfo().getTotalRecords() + pageSize - 1) / pageSize);

        PaginatedCatalogItems paginatedCatalogItems = new PaginatedCatalogItems();

        paginatedCatalogItems.setPageSize(pageSize);
        paginatedCatalogItems.setTotalNumberOfItems(itemCollection.getInfo().getTotalRecords());
        paginatedCatalogItems.setTotalNumberOfPages(totalNumOfPages);
        paginatedCatalogItems.setRecordStart(pageStartIndex + 1);
        paginatedCatalogItems.setRecordEnd(pageStartIndex + itemCollection.getData().getItems().size());
        paginatedCatalogItems.setFilterQuery(searchQuery);
//        paginatedCatalogItems.setCatalogItems(itemCollection.getData().getItems());
        paginatedCatalogItems.setCatalogItems(items);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, pre-check=0, post-check=0, private");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return paginatedCatalogItems;
    }

//    @RequestMapping(value ="/filterItems" , method = RequestMethod.POST)
//    public String filterItems(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap ){
//
//        List<Item> items = new LinkedList<Item>();
//        int totalNumOfPages = 0;
//        try{
//            User user = catalogDataDisplayPopulator.getLoggedInUser(request);
//            if (user == null) {
//                return INVALID_SESSION_PAGE;
//            }
//
//            String catalogId = request.getParameter("catalogId");
//            String searchStr = request.getParameter("searchValue");
//
//            CatalogItemList catalogItemList =  catalogService.getCatalogItems(catalogId,searchStr, 1);
//
//            if(catalogItemList!=null && catalogItemList.getDataList()!=null){
//                items  = catalogItemList.getDataList();
//                totalNumOfPages = (items.size() >= catalogItemList.getSize())? 1:((catalogItemList.getSize()% Consts.PAGE_SIZE > 0)?catalogItemList.getSize()/Consts.PAGE_SIZE + 1 : catalogItemList.getSize()/Consts.PAGE_SIZE);
//            }
//            modelMap.addAttribute("catalogItemPageSize", new Integer(Consts.PAGE_SIZE));
//            modelMap.addAttribute("catalogItemTotalNumOfItems", catalogItemList.getSize());
//            modelMap.addAttribute("catalogItemTotalNumOfPages", new Integer(totalNumOfPages));
//            modelMap.addAttribute("catalogItemCurrentNumOfItems", new Integer(items.size()));
//            modelMap.addAttribute("filterQuery", searchStr );
//            modelMap.addAttribute("catalogItems", items);
//
//        }catch (Exception e) {
//            logger.error("Filter Items: " +e);
//        }
//
//        return CATALOGITEM_TABLE_FRAGMENT;
//    }

    private void writeToResponse(PrintWriter os, String message) throws Exception {
        os.print(message);
        os.close();
    }

    private static enum CatalogItemFilter {
        NONE("NONE"),
        ALL("ALL"),
        APPROVED("APPROVED"),
        REJECTED("REJECTED"),
        PUBLISHED("PUBLISHED"),
        ;

        private final String filterOptionValue;

        CatalogItemFilter(String filterOptionValue){
            this.filterOptionValue = filterOptionValue;
        }
    }

    private Map<String, CatalogCustomField> getCatalogCustomFieldListForCompany(String catalogId, Logger logger){
        Map<String, CatalogCustomField> customFieldValueList = new HashMap<String, CatalogCustomField>();
        try{
            customFieldValueList = catalogService.getCatalogCustomFieldByCatalogId(catalogId);
        }catch (Exception exp){
            logger.error("Error retrieving CustomField for UnitId(" + catalogId + ") ", exp);
        }
        return customFieldValueList;
    }

  /**
   * Finds suppliers against given unit id and keyword. It matches the keyword with beginning of
   * supplier name.
   * <p>
   * It is intended to be used where suppliers are to be suggested in a typeahead box.
   * 
   * @param keyword
   * @param unitId
   * @param nonCatalogSupplier {@link Boolean} indicating whether nonCatalogSuppliers or
   *        catalogSuppliers are needed. If this parameter is not passed at all, all suppliers
   *        should be checked against keyword.
   * @param maxResults
   * @return {@link List} containing matched {@link Supplier} objects.
   * @author Muhammad Haris
   */
  @RequestMapping(value = "/getSuppliersForTypeAhead/{unitId}", produces = "application/json", method = RequestMethod.GET)
  public @ResponseBody List<Supplier> getSuppliersAgainstKeywordAndUnitId(
      @PathVariable String unitId, @RequestParam(required = false) String keyword,
      @RequestParam(required = false) Boolean nonCatalogSupplier,
      @RequestParam(required = false, defaultValue = "50") int maxResults) {
    List<Supplier> suppliers = new ArrayList<Supplier>();

    try {
      suppliers =
          supplierService.getSuppliersAgainstKeywordAndUnitId(unitId, keyword, nonCatalogSupplier,
              maxResults);
    } catch (Exception e) {
      logger.error("Exception in Getting Suppliers against Keyword " + keyword + " for Typeahead",
          e.getMessage());
      e.printStackTrace();
    }

    return suppliers;
  }

}
