package com.vroozi.customerui.catalog.controller;


import com.vroozi.customerui.acl.DisplayAccessControl;
import com.vroozi.customerui.acl.model.Permission;
import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.appConfig.model.AdminUICache;
import com.vroozi.customerui.catalog.common.CatalogDataDisplayPopulator;
import com.vroozi.customerui.catalog.model.CatType;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.model.CatalogTypeEnum;
import com.vroozi.customerui.catalog.model.CreateCatalogForm;
import com.vroozi.customerui.catalog.model.CreateCatalogPost;
import com.vroozi.customerui.catalog.model.EditCatalogForm;
import com.vroozi.customerui.catalog.model.EditCatalogPost;
import com.vroozi.customerui.catalog.model.ExternalCatalogFields;
import com.vroozi.customerui.catalog.services.CatalogService;
import com.vroozi.customerui.company.model.Announcements;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.exception.RequestFailedException;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Consts;
import com.vroozi.customerui.util.DateTimeUtils;
import com.vroozi.customerui.util.ViewHelper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vroozi.customerui.common.SessionDataRetriever.getLoggedInUser;
import static com.vroozi.customerui.util.Consts.CATALOG_DIV_FRAGMENT;
import static com.vroozi.customerui.util.Consts.CATALOG_STATE_APPROVE;
import static com.vroozi.customerui.util.Consts.CATALOG_STATE_DEACTIVATE;
import static com.vroozi.customerui.util.Consts.CATALOG_STATE_PUBLISH;
import static com.vroozi.customerui.util.Consts.CATALOG_STATE_REJECT;
import static com.vroozi.customerui.util.Consts.CATALOG_STATE_UNPUBLISH;
import static com.vroozi.customerui.util.Consts.CATALOG_TYPE_EXTERNAL;
import static com.vroozi.customerui.util.Consts.CATALOG_TYPE_INTERNAL;
import static com.vroozi.customerui.util.Consts.CATALOG_TYPE_QUOTE;
import static com.vroozi.customerui.util.Consts.FAILURE;
import static com.vroozi.customerui.util.Consts.HOME_PAGE;
import static com.vroozi.customerui.util.Consts.LANDING_FRAMED_PAGE;
import static com.vroozi.customerui.util.Consts.SUCCESS;
import static java.net.URLEncoder.encode;

//import com.vroozi.customerui.approver.controller.ApproverUIController;
//import com.vroozi.customerui.approver.services.ApproverService;
//import com.vroozi.customerui.common.SessionDataRetriever;
//import com.vroozi.customerui.property.service.PropertyService;


/**
 * Provides endpoint controller for adminUI Catalog Summary Page.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since  ${DATE}:${TIME}
 */
@Controller
public class CatalogSummaryController {
    private final Logger logger = LoggerFactory.getLogger(CatalogSummaryController.class);


    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserManagementService userManagementService;

//    /@Autowired
//    private ApproverService approverService;

    @Autowired
    private ProfileService profileService;

//    @Autowired
//    private PropertyService propertyService;

    @Autowired
    private CatalogDataDisplayPopulator catalogDataDisplayPopulator;

//    @Autowired
//    private ApproverUIController approverUIController;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
  private UploadService uploadService;

  @RequestMapping(value = {"catalog"})
  public String home(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
    String supplierId = "";
    String catType = "";
    String filter = "";
    Map<String, String> map = new HashMap<String, String>();
    boolean filterAction = false;
    String noSupplier = "";


    if (request.getParameter("filterAction") != null
        && !request.getParameter("filterAction").equals("")) {
      filterAction = true;
    }
    if (request.getParameter("noSupplier") == null
        || !request.getParameter("noSupplier").equals("")) {
      noSupplier = "checked:true";
    }
    List<CatType> catTypeList = new ArrayList<CatType>();
    if (filterAction) {
      catType = request.getParameter("catType");
    }
    if (filterAction) {
      supplierId = request.getParameter("supplierId");
    }
    filter = request.getParameter("filter");

    List<Supplier> supplierList = new ArrayList<Supplier>();
    List<CatalogSummary> stagingCatalogs = new ArrayList<>();
    List<CatalogSummary> offlineCatalogs = new ArrayList<>();
    List<CatalogSummary> liveCatalogs = new ArrayList<>();
    User user = getLoggedInUser(request);

    List<Supplier> supplierCompanyList = new ArrayList<Supplier>();
    try {
      supplierCompanyList = supplierService.getActiveSupplierByUnitId(user.getUnitId());
      Supplier element = new Supplier();
      element.setCompanyId("0");
      element.setCompanyName("No Supplier");
      element.setChecked("");
      // supplierCompanyList.add( element);
      if (!filterAction) {
        supplierList.add(element);
        // supplierId = element.getCompanyId()+",";
        for (Supplier sup : supplierCompanyList) {
          if (!sup.isNonCatalogSupplier())
            supplierList.add(sup);
          // supplierId+=sup.getCompanyId()+",";

        }
      } else {
        supplierList.add(element);
        for (Supplier sup : supplierCompanyList) {
          if (!sup.isNonCatalogSupplier())
            supplierList.add(sup);
        }
      }

      for (Supplier sup : supplierList) {
        boolean found = false;
        StringTokenizer tok = new StringTokenizer(supplierId, ",");
        while (tok.hasMoreElements()) {
          String companyId = tok.nextToken();
          // if(!companyId.equals("0")){
          if (sup.getCompanyId().equals(companyId)) {
            found = true;
          }
        }
        if (found) {
          sup.setChecked("checked='true'");
        } else {
          sup.setChecked("");
        }
      }



    } catch (Exception e) {
      // TODO adding print stack trace for now since we need to handle this rest call which is
      // failing
      logger
          .error("Exception occurred while trying to fetch suppliers from user data service." + e);
    }

    if (filter == null || filter.equals(""))
      filter = "Search Within...";
    if (catType.equals("") || catType.equals(",")) {
      catType = "-1";
    }
    if (supplierId.equals("") || supplierId.equals(",")) {
      supplierId = "-1";
    }
    List<CatalogSummary> catalogs = new ArrayList<CatalogSummary>();
    if (!catType.equals("") && !catType.equals(",") && !supplierId.equals("")
        && !supplierId.equals(",")) {
      catalogs =
          catalogService.getAllCatalogsFiltered(user.getUnitId(), supplierId, filter, catType);
    }

    if (!isAdminUser(user)) {
      List<Profile> profiles =
          profileService.getProfilesByUserProfileIds(user.getAssignedProfiles());
      if (isApprover(user)) {
        catalogs = filterCatalogsApprover(catalogs, profiles, user.getUserId());
      } else if (isSupplierAdmin(user)) {
        catalogs = filterCatalogsSupplierAdmin(catalogs, profiles, user.getUserId());
      } else {
        catalogs = filterCatalogsPerProfile(catalogs, profiles, user.getUserId());
      }
    }


    sortCatalogs(catalogs, stagingCatalogs, offlineCatalogs, liveCatalogs);

    ViewHelper.Column[] columns =
        {new ViewHelper.Column(), new ViewHelper.Column(), new ViewHelper.Column()};
    columns[0].id = "working";
    columns[0].css = "working-col";
    columns[0].data = stagingCatalogs;
    columns[1].id = "approved";
    columns[1].css = "approved";
    columns[1].data = offlineCatalogs;
    columns[2].id = "live";
    columns[2].css = "live";
    columns[2].data = liveCatalogs;
    modelMap.addAttribute("columns", columns);

    modelMap.addAttribute("userName", user.getFullName());
    modelMap.addAttribute("smartOCIAppBaseURI", appConfig.smartOCIAppBaseURI);
    modelMap.addAttribute("catalogServiceURI", appConfig.catalogServiceURI);
    modelMap.addAttribute("externalCatalogList",
        catalogService.getCatalogsByType(user.getUnitId(), 1));

    String adminUIPath = appConfig.environmentUrl;
    if (adminUIPath.endsWith("login")) {
      adminUIPath = adminUIPath.substring(0, adminUIPath.lastIndexOf("login"));
    }
    adminUIPath = adminUIPath + "checkout";
    modelMap.addAttribute("adminUIPath", adminUIPath);

    if (isSupplierAdmin(user)) {
      modelMap.addAttribute("supplierAdminId", getsupplierid(user.getUserId(), user.getUnitId()));
    }

    List<User> allApprovers = getAllApprovers(user.getUnitId(), user.getUserId());
    modelMap.addAttribute("allApprovers", allApprovers);
    modelMap.addAttribute("profileList", profileService.getProfilesByUser(user));
    modelMap.addAttribute("approverList", populateApprovers(user));


    // approverUIController.renderBothSections(user.getUnitId(), catalogId, null, 1, 1, modelMap,
    // request);

    modelMap.addAttribute("supplierCompanyList", supplierCompanyList);
    modelMap.addAttribute("supplierList", supplierList);

    for (CatalogSummary catalog : catalogs) {
      if (map.get(catalog.getCatalogType()) == null && catalog.getCatalogType() != null
          && !catalog.getCatalogType().equals("")) {
        map.put(catalog.getCatalogType(), catalog.getCatalogType());
      }
    }


    populateCatTypeList(catType, catTypeList, filterAction, map);

    modelMap.addAttribute("supplierId", supplierId);
    modelMap.addAttribute("catType", catType);
    modelMap.addAttribute("filter", filter);
    modelMap.addAttribute("searchTerm", filter);
    modelMap.addAttribute("catTypeList", catTypeList);
    modelMap.addAttribute("noSupplier", noSupplier);
    modelMap.addAttribute("ViewHelper", new ViewHelper());
    return HOME_PAGE;
  }


  List<Profile> getProfileListForCompany(String unitId) {
    List<Profile> profiles = new ArrayList<>();
    try {
      profiles = profileService.getActiveProfilesByUnitId(unitId);
    } catch (Exception exp) {
      logger.error("Error retrieving Profiles for UnitId(" + unitId + ") ", exp);
    }
    return profiles;
  }

  /**
   * Populates list of catalog types which are to be shown under Filter Options
   *
   * @param catType {@link String} containing comma-separated catalog type ids
   * @param catTypeList {@link List} which is to be populated
   * @param filterApplied <code>boolean</code> indicating whether filters were applied or not
   * @param map {@link HashMap} containing already added catalog types
   * @return {@link List} containing catalog types according to filters applied
   * @author Muhammad Haris
   */
  private void populateCatTypeList(String catType, List<CatType> catTypeList,
      boolean filterApplied, Map<String, String> map) {

    List<String> catalogTypesToFilter = Arrays.asList(catType.split(","));

    CatType externalCatalogType =
        createCatType(CatalogTypeEnum.EXTERNAL.getTypeId(), CatalogTypeEnum.EXTERNAL.getTypeName(), "");
    CatType internalCatalogType =
        createCatType(CatalogTypeEnum.INTERNAL.getTypeId(), CatalogTypeEnum.INTERNAL.getTypeName(), "");
    CatType quoteCatalogType =
        createCatType(CatalogTypeEnum.QUOTE.getTypeId(), CatalogTypeEnum.QUOTE.getTypeName(), "");

    /* if one or more filters are applied */
    if (filterApplied) {
      if (catalogTypesToFilter.contains(CatalogTypeEnum.QUOTE.getTypeId())) {
        quoteCatalogType.setChecked("checked='true'");
      }

      if (catalogTypesToFilter.contains(CatalogTypeEnum.INTERNAL.getTypeId())) {
        internalCatalogType.setChecked("checked='true'");
      }

      if (catalogTypesToFilter.contains(CatalogTypeEnum.EXTERNAL.getTypeId())) {
        externalCatalogType.setChecked("checked='true'");
      }
    }

    catTypeList.add(quoteCatalogType);
    catTypeList.add(internalCatalogType);
    catTypeList.add(externalCatalogType);
  }

  private CatType createCatType(String typeId, String typeName, String checked) {
    CatType catType = new CatType();
    catType.setId(typeId);
    catType.setName(typeName);
    catType.setChecked(checked);

    return catType;
  }

    private List<User> populateApprovers(User user){

    	List<User> users = new ArrayList<>();
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
        List<User> approvers = new ArrayList<>();
        try{
            approvers = userManagementService.getApproverListForCompany(unitId);
        }catch (Exception exp){
            logger.error("Error retrieving Approvers for unitId(" + unitId + ") ", exp);
        }
        return approvers;
    }

    private List<User> getAllApprovers(String unitId,String userId){
        List<User> approvers = new ArrayList<>();
        try{
            approvers = userManagementService.getApproverListForUser(unitId,userId);
        }catch (Exception exp){
            logger.error("Error retrieving Approvers", exp);
        }
        return approvers;
    }

    private boolean isAdminUser(User user){
        for(Role role:user.getRoles()){
            if(role == Role.MASTER_ADMINISTRATOR)return  true;
        }
        return false;
    }


    private boolean isApprover(User user){
        for(Role role:user.getRoles()){
            if(role == Role.APPROVER)return  true;
        }
        return false;

    }


    private boolean isSupplierAdmin(User user){
        for(Role role:user.getRoles()){
            if(role == Role.SUPPLIER_ADMIN)return  true;
        }
        return false;

    }


    private String getsupplierid(String userid, String unitid){
    	return supplierService.getSupplierByAdmin(unitid, userid);
    }


    private List<CatalogSummary> filterCatalogsSupplierAdmin(List<CatalogSummary> allCatalogs, List<Profile> profiles,String userId){
        List<String>  associatedCatalogIds = new ArrayList<>();

        for(Profile profile: profiles){
        	if(null!= profile.getAssociatedCatalogs()){
        		associatedCatalogIds.addAll(profile.getAssociatedCatalogs());
        	}
        }

        for (CatalogSummary catSum : allCatalogs) {

			if(catSum.getSubmitterId().equals(userId)){
				associatedCatalogIds.add(catSum.getCatalogId());
			}


			if(CatalogDataDisplayPopulator.isValidSupplierId(catSum.getSupplierId())){
                Supplier supplier = supplierService.getSupplier(catSum.getUnitId() , catSum.getSupplierId());
                if(null != supplier){
                    List<String> supAdmins = supplier.getSupplierAdmins();
                    if(supAdmins != null){
                        for (String adminid : supAdmins) {
                            if(adminid.equals(userId)){
                                associatedCatalogIds.add(catSum.getCatalogId());
                            }
                        }
                    }
                }
			}
		}

        return subtractCatalogs(allCatalogs, associatedCatalogIds);
    }


    private List<CatalogSummary> filterCatalogsApprover(List<CatalogSummary> allCatalogs, List<Profile> profiles,String userId){
        List<String>  associatedCatalogIds = new ArrayList<String>();

        for(Profile profile: profiles){
        	if(null!= profile.getAssociatedCatalogs()){
        		associatedCatalogIds.addAll(profile.getAssociatedCatalogs());
        	}
        }

        for (CatalogSummary catSum : allCatalogs) {

        	if(catSum.getCatalogTypeId()==5){
        		continue;
        	}

        	List<String> approvers =catSum.getApprovers();
        	if(approvers != null){
	        	for (String approverId : approvers) {
					if(approverId.equals(userId)){
						associatedCatalogIds.add(catSum.getCatalogId());
					}
				}
        	}

			if(catSum.getSubmitterId().equals(userId)){
				associatedCatalogIds.add(catSum.getCatalogId());
			}


			if(CatalogDataDisplayPopulator.isValidSupplierId(catSum.getSupplierId())){
				Supplier supplier = supplierService.getSupplier(catSum.getUnitId() , catSum.getSupplierId());
				if(null != supplier){
					approvers = supplier.getApproverIds();
		        	if(approvers != null){
			        	for (String approverId : approvers) {
							if(approverId.equals(userId)){
								associatedCatalogIds.add(catSum.getCatalogId());
							}
						}
		        	}
				}
			}
		}
        return subtractCatalogs(allCatalogs, associatedCatalogIds);
    }

    private List<CatalogSummary> filterCatalogsPerProfile(List<CatalogSummary> allCatalogs, List<Profile> profiles,String userId){
        List<String>  associatedCatalogIds = new ArrayList<String>();

        for(Profile profile: profiles){
        	if(null!= profile.getAssociatedCatalogs()){
        		associatedCatalogIds.addAll(profile.getAssociatedCatalogs());
        	}
        }

        for (CatalogSummary catSum : allCatalogs) {
			if(catSum.getSubmitterId().equals(userId)){
				associatedCatalogIds.add(catSum.getCatalogId());
			}
		}
        return subtractCatalogs(allCatalogs, associatedCatalogIds);
    }

    private List<CatalogSummary> subtractCatalogs(List<CatalogSummary> catalogSummaries, List<String> ids){
        if(catalogSummaries == null || catalogSummaries.size() == 0 || ids == null || ids.size() == 0){
            return new ArrayList<>();
        }
        List<CatalogSummary> remainderCatalogs = new ArrayList<>();
        for(String userId: ids) {
            for(CatalogSummary catalogSummary: catalogSummaries){
                if(catalogSummary.getCatalogId().equals(userId)){
                    remainderCatalogs.add(catalogSummary);
                    catalogSummaries.remove(catalogSummary);
                    break;
                }
            }
        }
        return remainderCatalogs;
    }

    @RequestMapping(value={"landing-frame"})
    public String landingFramed(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        return LANDING_FRAMED_PAGE;
    }

    @RequestMapping(value = "/addCatalogPage", method=RequestMethod.POST)
    @ResponseBody
    public String addCatalog(@ModelAttribute(value="catalog") CatalogSummary catalog, BindingResult bindingResult) {
        return null;
    }

    @RequestMapping(value="/deleteCatalog", method=RequestMethod.POST)
    public @ResponseBody String deleteCatalog(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "catalogId", required = false) String catalogId) {

        if(deleteCatalog(catalogId)){
            return SUCCESS;
        }
        return FAILURE;
    }

    @RequestMapping(value="/rejectCatalog", method=RequestMethod.POST)
    public @ResponseBody String rejectCatalog(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value="catalogId", required=false ) String catalogId){
        User user = getLoggedInUser(request);
        if(rejectCatalog(catalogId,user.getUserId())){
            return SUCCESS;
        }
        return FAILURE;
    }

    @RequestMapping(value="/getCatalogJson", method=RequestMethod.POST)
    public @ResponseBody String getCatalogJson(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(value="catalogId", required=false ) String catalogId){
      User user = getLoggedInUser(request);
      logger.info("Getting Catalog For Editing Against CatalogId: {}", catalogId);
      CatalogSummary catalog = getCatalogForEditing(catalogId,user.getUnitId());
      String catalogJson = "";
      
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        catalogJson = objectMapper.writeValueAsString(catalog);
        logger.debug("Catalog JSON: {}", catalogJson);
      } catch (Exception e) {
        logger.error("Exception in getting Catalog JSON", e);
      }
      
      return catalogJson;
    }

    @RequestMapping(value="/getCatalogStatus", method=RequestMethod.POST)
    public @ResponseBody String getCatalogStatus(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(value="catalogId", required=false ) String catalogId){
        return catalogService.getCatalog(catalogId).getApprovedStatus();
    }


    @RequestMapping(value = "/createCatalog", method=RequestMethod.POST, produces="text/html")
    public @ResponseBody String createCatalog(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute CreateCatalogForm createCatalogForm, BindingResult bindingResult) {

        User user = getLoggedInUser(request);
        CreateCatalogPost createCatalogPost = new CreateCatalogPost();

        String catalogDivFragmentReply = "";
        DateFormat df = new SimpleDateFormat("ddMMyyyy-HHmmss");
        String fileNamePostfix = "_" + df.format(new Date());
        try {
            if (createCatalogForm!= null) {
                if(createCatalogForm.getCatalogFile() != null && !createCatalogForm.getCatalogFile().isEmpty()) {
                    String catalogFilename = createCatalogForm.getCatalogFile().getOriginalFilename();
                    catalogFilename = catalogFilename.substring(0, catalogFilename.lastIndexOf(".")) + fileNamePostfix + catalogFilename.substring(catalogFilename.lastIndexOf("."));
                    if(uploadService.writeToFile(createCatalogForm.getCatalogFile().getInputStream(), catalogFilename, appConfig.fileUploadPath)){
                        createCatalogPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" + catalogFilename);
                    }
                }
                if(createCatalogForm.getImageFile() != null && !createCatalogForm.getImageFile().isEmpty()) {
                    String imageFilename = createCatalogForm.getImageFile().getOriginalFilename();
                    imageFilename = imageFilename.substring(0, imageFilename.lastIndexOf(".")) + fileNamePostfix + imageFilename.substring(imageFilename.lastIndexOf("."));
                    if(uploadService.writeToFile(createCatalogForm.getImageFile().getInputStream(),  imageFilename, appConfig.itemImagesPath)){
                        createCatalogPost.setCatalogImagesPath(appConfig.itemImagesPath + "/" + imageFilename);
                    }
                }

                createCatalogPost.setCatalogSubmitter(user.getUserId());
                createCatalogPost.setCatalogName(createCatalogForm.getCatalogName());
                createCatalogPost.setCatalogOrigin("1");
                createCatalogPost.setClientCatalogId(createCatalogForm.getClientCatalogId());
                createCatalogPost.setExtCatalogMethod(createCatalogForm.getExtCatalogMethod());
                createCatalogPost.setExtCatalogImageField(createCatalogForm.getExtCatalogImageField());
                createCatalogPost.setExtCommunicationMethod(createCatalogForm.getExtCommunicationMethod());
                createCatalogPost.setEncodingType(createCatalogForm.getEncodingType());
                createCatalogPost.setContractLineItem(createCatalogForm.getContractLineItem());
                createCatalogPost.setContractNumber(createCatalogForm.getContractNumber());
                createCatalogPost.setUnitId(user.getUnitId());

                if(createCatalogForm.getQuoteId()!=null && !createCatalogForm.getQuoteId().equals(""))
                    createCatalogPost.setQuoteId(createCatalogForm.getQuoteId());

                if(createCatalogForm.getRfqNumber()!=null && !createCatalogForm.getRfqNumber().equals(""))
                    createCatalogPost.setRfqNumber(createCatalogForm.getRfqNumber());

                createCatalogPost.setApproverIds(createCatalogForm.getApproverIds());
                createCatalogPost.setProfileIds(createCatalogForm.getProfileIds());

                if(createCatalogForm.getSearchAllowed() != null){
                	createCatalogPost.setSearchAllowed(createCatalogForm.getSearchAllowed());
                }else{
                	createCatalogPost.setSearchAllowed(false);
                }

                createCatalogPost.setQuantityLocked(createCatalogForm.isQuantityLocked());
                
                if(createCatalogForm.getExternalCatalog() != null && createCatalogForm.getExternalCatalog()){
                    createCatalogPost.setCatalogTypeId(CATALOG_TYPE_EXTERNAL); //external catalog
                }else{
                    createCatalogPost.setCatalogTypeId(CATALOG_TYPE_INTERNAL); //internal catalog
                }
                if(createCatalogForm.getQuoteId()!=null && !createCatalogForm.getQuoteId().equals(""))
                    createCatalogPost.setCatalogTypeId(CATALOG_TYPE_QUOTE);

                if(createCatalogForm.getFields()!=null&& createCatalogForm.getFields().size()>0){
                    createCatalogPost.setFields(dropEmptyFields(createCatalogForm.getFields()));
                }

                if(createCatalogForm.getSupplierName()!=null && !"".equals(createCatalogForm.getSupplierName().trim())){
                    createCatalogPost.setSupplierId(createCatalogForm.getSupplierName());
                }

                String timeZoneValue = AdminUICache.getTimeZonesMap().get(createCatalogForm.getTimeZoneId()).getTimeZoneValue();

                if (StringUtils.isNotBlank(createCatalogForm.getStartDate())) {

                    if (createCatalogForm.getStartDate().trim().length() <= Consts.CATALOG_SCHEDULE_DATE_LENGTH){
                      createCatalogForm.setStartDate(createCatalogForm.getStartDate().trim() + " 00:00");
                    }

                    /* Store start date by parsing it according to the selected time zone */
                    Date startDate = DateTimeUtils.parseDateForTimeZone(createCatalogForm.getStartDate(), appConfig.catalogScheduleDateFormat, timeZoneValue);
                    createCatalogPost.setValidFrom(startDate);
                }

                if (StringUtils.isNotBlank(createCatalogForm.getEndDate())) {

                    if (createCatalogForm.getEndDate().trim().length() <= Consts.CATALOG_SCHEDULE_DATE_LENGTH){
                        createCatalogForm.setEndDate(createCatalogForm.getEndDate().trim() + " 00:00");
                    }

                    /* Store end date by parsing it according to the selected time zone */
                    Date endDate = DateTimeUtils.parseDateForTimeZone(createCatalogForm.getEndDate(), appConfig.catalogScheduleDateFormat, timeZoneValue);
                    createCatalogPost.setValidUntil(endDate);
                }
                
                createCatalogPost.setCatalogTimeZone(timeZoneValue);
                createCatalogPost.setActive(true);

                CatalogSummary catalogSummary = catalogService.createCatalog(createCatalogPost);
                catalogDivFragmentReply =  catalogSummary.getCatalogId();
            }
        } catch (Exception exception) {
            logger.error("Error in calling create catalog service",exception);
            String errorMsg = exception.getMessage();
            return "Failed:" + (errorMsg.substring(errorMsg.lastIndexOf(":") + 1));
        }

        return catalogDivFragmentReply;
    }

    private List<ExternalCatalogFields> dropEmptyFields(List<ExternalCatalogFields> fieldsList){
        if(fieldsList == null || fieldsList.size() == 0) return fieldsList;

        List<ExternalCatalogFields> filteredFieldList = new ArrayList<ExternalCatalogFields>();
        for(Iterator<ExternalCatalogFields> iterator = fieldsList.iterator(); iterator.hasNext();){
            ExternalCatalogFields externalCatalogFields = iterator.next();
            if(null == externalCatalogFields.getName() || externalCatalogFields.getName().equals("Enter Name")){
                externalCatalogFields.setName("");
            }
            if(null == externalCatalogFields.getValue() || externalCatalogFields.getValue().equals("Enter Value")){
                externalCatalogFields.setValue("");
            }
            if(!StringUtils.isEmpty(externalCatalogFields.getName())){
                filteredFieldList.add(externalCatalogFields);
            }
        }

        Comparator<ExternalCatalogFields> seqComparator = new ExternalCatalogFields.SequenceComparator();

        // Sort
        Collections.sort(filteredFieldList, seqComparator);

        return filteredFieldList;
    }

    @RequestMapping(value = "/updateExternalFields", method=RequestMethod.POST)
    public @ResponseBody String updateExternalFields(HttpServletRequest request, @ModelAttribute EditCatalogForm editCatalogForm, BindingResult bindingResult) {
        try {

            User user = getLoggedInUser(request);
            if(user == null){
                return "Failed: Session time out";
            }

            CatalogSummary catatlog = catalogService.getCatalog(editCatalogForm.getCatalogId());

            EditCatalogPost editCatalogPost = new EditCatalogPost();

            if(StringUtils.isNotEmpty(editCatalogForm.getExtCatalogMethod())){
                editCatalogPost.setExtCatalogMethod(editCatalogForm.getExtCatalogMethod());
            }
            editCatalogPost.setExtCatalogImageField(editCatalogForm.getExtCatalogImageField());
            editCatalogPost.setExtCommunicationMethod(editCatalogForm.getExtCommunicationMethod());
            if(editCatalogForm.getFields()!=null&& editCatalogForm.getFields().size() > 0){
                editCatalogPost.setFields(dropEmptyFields(editCatalogForm.getFields()));
            }

            editCatalogPost.setCatalogName(catatlog.getName());
            editCatalogPost.setCatalogId(editCatalogForm.getCatalogId());
            editCatalogPost.setUnitId(Integer.parseInt(user.getUnitId()));
            editCatalogPost.setCatalogSubmitter(Integer.parseInt(user.getUserId()));
            editCatalogPost.setCatalogOrigin("1");

            catalogService.editCatalog(editCatalogPost);

        } catch (Exception exception) {
            logger.error("Error in calling 'updateExternalFields'");
            String errorMsg = exception.getMessage();
            return "Failed:" + (errorMsg.substring(errorMsg.lastIndexOf(":")+1));
        }

        return "Success";
    }

    @RequestMapping(value = "/editCatalog", method=RequestMethod.POST, produces="text/html")
    public @ResponseBody String editCatalog(HttpServletRequest request, @ModelAttribute EditCatalogForm editCatalogForm, BindingResult bindingResult) {

        try {
            User user = getLoggedInUser(request);

            if(StringUtils.isEmpty(editCatalogForm.getCatalogName())){
                throw new Exception("Catalog Name can not be blank");
            }

            EditCatalogPost editCatalogPost = new EditCatalogPost();

            DateFormat df = new SimpleDateFormat("ddMMyyyy-HHmmss");
            String fileNamePostfix = "_" + df.format(new Date());

            if (editCatalogForm!= null) {

                if(editCatalogForm.getQuoteId()!=null && !editCatalogForm.getQuoteId().equals("")){
                    editCatalogPost.setQuoteId(editCatalogForm.getQuoteId());
                }
                if(editCatalogForm.getRfqNumber()!=null){
                    editCatalogPost.setRfqNumber(editCatalogForm.getRfqNumber());
                }

                if(editCatalogForm.getCatalogFile() != null && !editCatalogForm.getCatalogFile().isEmpty()) {
                    String catalogFilename = editCatalogForm.getCatalogFile().getOriginalFilename();
                    catalogFilename = catalogFilename.substring(0, catalogFilename.lastIndexOf(".")) + fileNamePostfix + catalogFilename.substring(catalogFilename.lastIndexOf("."));
                    if(uploadService.writeToFile(editCatalogForm.getCatalogFile().getInputStream(), catalogFilename, appConfig.fileUploadPath )){
                        editCatalogPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" + catalogFilename);
                    }
                }
                if(editCatalogForm.getImageFile() != null && !editCatalogForm.getImageFile().isEmpty()) {
                    String imageFilename = editCatalogForm.getImageFile().getOriginalFilename();
                    imageFilename = imageFilename.substring(0, imageFilename.lastIndexOf(".")) + fileNamePostfix + imageFilename.substring(imageFilename.lastIndexOf("."));
                    if(uploadService.writeToFile(editCatalogForm.getImageFile().getInputStream(), imageFilename, appConfig.itemImagesPath  )){
                        editCatalogPost.setCatalogImagesPath(appConfig.itemImagesPath + "/" + imageFilename);
                    }
                }

                editCatalogPost.setCatalogName(editCatalogForm.getCatalogName());

                if(!StringUtils.isEmpty(editCatalogForm.getSupplierName())) {
                    editCatalogPost.setSupplierId(editCatalogForm.getSupplierName());
                }
                if(!StringUtils.isEmpty(editCatalogForm.getExternalCatalogId()) && !StringUtils.isEmpty(editCatalogForm.getExternalCatalogId().trim())){
                    editCatalogPost.setExternalCatalogId(Integer.parseInt(editCatalogForm.getExternalCatalogId()));
                }
                if(!StringUtils.isEmpty(editCatalogForm.getCatalogEditOption()) && !StringUtils.isEmpty(editCatalogForm.getCatalogEditOption().trim())){
                    editCatalogPost.setMethodType(editCatalogForm.getCatalogEditOption());
                }

                if(!StringUtils.isEmpty(editCatalogForm.getClientCatalogId()) && !StringUtils.isEmpty(editCatalogForm.getClientCatalogId().trim())){
                    editCatalogPost.setClientCatalogId(editCatalogForm.getClientCatalogId());
                }


                if((editCatalogForm.getFields()!=null&& editCatalogForm.getFields().size()>0)
                        || ("POST".equalsIgnoreCase(editCatalogForm.getExtCatalogMethod())
                        || "GET".equalsIgnoreCase(editCatalogForm.getExtCatalogMethod())) ){
                    editCatalogPost.setFields(dropEmptyFields(editCatalogForm.getFields()));
                }
                if(StringUtils.isNotEmpty(editCatalogForm.getExtCatalogMethod())){
                    editCatalogPost.setExtCatalogMethod(editCatalogForm.getExtCatalogMethod());
                }
                editCatalogPost.setExtCatalogImageField(editCatalogForm.getExtCatalogImageField());
                editCatalogPost.setExtCommunicationMethod(editCatalogForm.getExtCommunicationMethod());
                editCatalogPost.setEncodingType(editCatalogForm.getEncodingType());
                
                if(editCatalogForm.getSearchAllowed() != null){
                	editCatalogPost.setSearchAllowed(editCatalogForm.getSearchAllowed());
                }else{
                	editCatalogPost.setSearchAllowed(false);
                }
                
                editCatalogPost.setQuantityLocked(editCatalogForm.isQuantityLocked());

                if(editCatalogForm.getExternalCatalog() != null && editCatalogForm.getExternalCatalog()){
                	editCatalogPost.setCatalogTypeId(1); //external catalog
                }else{
                	editCatalogPost.setCatalogTypeId(2); //internal catalog
                }
                
                String timeZoneValue = AdminUICache.getTimeZonesMap().get(editCatalogForm.getTimeZoneId()).getTimeZoneValue();

                if (StringUtils.isNotBlank(editCatalogForm.getStartDate())) {

                  if (editCatalogForm.getStartDate().trim().length() <= Consts.CATALOG_SCHEDULE_DATE_LENGTH){
                    editCatalogForm.setStartDate(editCatalogForm.getStartDate().trim() + " 00:00");
                  }

                  /* Store start date by parsing it according to the selected time zone */
                  Date startDate = DateTimeUtils.parseDateForTimeZone(editCatalogForm.getStartDate(), appConfig.catalogScheduleDateFormat, timeZoneValue);
                  editCatalogPost.setValidFrom(startDate);
                }
                
                if (StringUtils.isNotBlank(editCatalogForm.getEndDate())) {

                  if (editCatalogForm.getEndDate().trim().length() <= Consts.CATALOG_SCHEDULE_DATE_LENGTH){
                      editCatalogForm.setEndDate(editCatalogForm.getEndDate().trim() + " 00:00");
                  }

                  /* Store end date by parsing it according to the selected time zone */
                  Date endDate = DateTimeUtils.parseDateForTimeZone(editCatalogForm.getEndDate(), appConfig.catalogScheduleDateFormat, timeZoneValue);
                  editCatalogPost.setValidUntil(endDate);
                }

                editCatalogPost.setCatalogTimeZone(timeZoneValue);
                editCatalogPost.setCatalogId(editCatalogForm.getCatalogId());
                editCatalogPost.setUnitId(Integer.parseInt(user.getUnitId()));
                editCatalogPost.setCatalogSubmitter(Integer.parseInt(user.getUserId()));
                editCatalogPost.setCatalogOrigin("1");
                editCatalogPost.setContractLineItem(editCatalogForm.getContractLineItem());
                editCatalogPost.setContractNumber(editCatalogForm.getContractNumber());
                editCatalogPost.setCreateNewVersion(editCatalogForm.getCreateVersion() == null? false : editCatalogForm.getCreateVersion());

                editCatalogPost.setApproverIds(editCatalogForm.getApproverIds());
                editCatalogPost.setProfileIds(editCatalogForm.getProfileIds());

                CatalogSummary oldCatalog =  catalogService.getCatalog(editCatalogForm.getCatalogId());
                catalogService.editCatalog(editCatalogPost);

                // associate with supplier profile

                DisplayAccessControl aclManager = new DisplayAccessControl();
                if(aclManager.allow(request, Permission.CHANGE_SUPPLIER)) {
                  if (!"0".equals(editCatalogPost.getSupplierId())) {
                    editCatalogPost.setSupplierId(StringUtils.stripEnd(editCatalogPost.getSupplierId(), ","));
                    profileService.associateCatalogToSupplierProfiles(
                        String.valueOf(editCatalogPost.getUnitId()), editCatalogPost.getCatalogId(),
                        editCatalogPost.getSupplierId());
                  } else if ("0".equals(editCatalogPost.getSupplierId())
                      && (CatalogDataDisplayPopulator.isValidSupplierId(oldCatalog.getSupplierId()))) {
                    oldCatalog.setSupplierId(StringUtils.stripEnd(oldCatalog.getSupplierId(), ","));
                    profileService.disAssociateCatalogToSupplierProfiles(oldCatalog.getUnitId(),
                        oldCatalog.getCatalogId(), oldCatalog.getSupplierId());
                  }

                }
            }
        } catch (Exception exception) {
            logger.error("Error in calling 'EditCatalog' service");
            String errorMsg = exception.getMessage();
            return "Failed:" + (errorMsg.substring(errorMsg.lastIndexOf(":")+1));
        }

       // return getCatalogJson(catalogId);
        return "OK";
    }

    @RequestMapping(value="/updateCatalogStatus", method=RequestMethod.POST)
    @ResponseBody
    public String updateCatalogStatus(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="catalogId", required=false ) String catalogId,
            @RequestParam( value="status", required=false ) String status){
        try {

        	User user = getLoggedInUser(request);

            catalogService.updateCatalogStatus(catalogId, status,user.getUserId());

            if(CATALOG_STATE_APPROVE.equalsIgnoreCase(status)){
                return "Approving";
            } else if(CATALOG_STATE_PUBLISH.equalsIgnoreCase(status)){
               return "Publishing";
            }else if(CATALOG_STATE_UNPUBLISH.equalsIgnoreCase(status)){
                return "Unpublishing";
            }else if(CATALOG_STATE_REJECT.equalsIgnoreCase(status)){
                return "Rejecting";
            }else if(CATALOG_STATE_DEACTIVATE.equalsIgnoreCase(status)){
                return "Disapproving";
            }else {
                return "N/A";
            }

        }catch (Exception e) {
            throw new RequestFailedException();
        }
       // return HOME_PAGE;
    }

    @RequestMapping(value="/getCatalogByNameJson", method=RequestMethod.POST)
    public @ResponseBody String getCatalogByNameJson(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="catalogName", required=false ) String catalogName){

        User user = getLoggedInUser(request);
        String catalogSummaryJson = "";
        try {

            String catalogSummary = catalogService.getCatalogByNameJson(catalogName, user.getUnitId(), 2);// 2 for internal for now
            if(StringUtils.isEmpty(catalogSummary)){
            	catalogSummary = catalogService.getCatalogByNameJson(catalogName, user.getUnitId(), 4);
            }
            if(!StringUtils.isEmpty(catalogSummary)){
                catalogSummaryJson = catalogSummary;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return catalogSummaryJson;
    }
    @RequestMapping(value="/getQuoteByQuoteIdJson", method=RequestMethod.POST)
     public @ResponseBody String getQuoteByQuoteIdJson(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestParam(value="quoteId", required=false ) String quoteId){

        User user = getLoggedInUser(request);
        String catalogSummaryJson = "";
        try {
            String catalogSummary = catalogService.getQuoteByQuoteIdJson(quoteId, user.getUnitId(), 2);// 2 for internal for now
            if(!StringUtils.isEmpty(catalogSummary)){
                catalogSummaryJson = catalogSummary;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return catalogSummaryJson;
    }

    @RequestMapping(value="/getQuoteByQuoteIdJsonEdit", method=RequestMethod.POST)
    public @ResponseBody String getQuoteByQuoteIdJsonEdit(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(value="quoteId", required=false ) String quoteId,@RequestParam(value="catalogId", required=false ) String catalogId,@RequestParam(value="parentcat", required=false ) String parentcat){

        User user = getLoggedInUser(request);
        String catalogSummaryJson = "";
        try {
            String catalogSummary = catalogService.getQuoteByQuoteIdJsonEdit(quoteId, user.getUnitId(), 2,catalogId,parentcat);// 2 for internal for now
            if(!StringUtils.isEmpty(catalogSummary)){
                catalogSummaryJson = catalogSummary;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return catalogSummaryJson;
    }
    // Helper methods
    public void sortCatalogs(List<CatalogSummary> catalogs, List<CatalogSummary> stagingCatalogs,
                             List<CatalogSummary> offlineCatalogs , List<CatalogSummary> liveCatalogs ) {

        try {
        	for (int i = catalogs.size()-1; i >= 0; i--) {
        		CatalogSummary catalog = catalogs.get(i);

                catalogDataDisplayPopulator.populateCatalogDisplayNames(catalog);

                try{
                    if (catalog.isPublished()){
                        liveCatalogs.add(catalog);
                    } else if (catalog.isApproved()){
                        offlineCatalogs.add(catalog);
                    } else if(catalog.isPending() || isRejected(catalog)){
                        stagingCatalogs.add(catalog);
                    }else {
                        throw new IllegalStateException("Invalid State for Catalog(" + catalog.getName() + ")");
                    }
                }catch (Exception exp){
                    logger.error("Error in catalogs", exp);
                }
            }
        } catch( Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
        }
    }

    private boolean deleteCatalog(String catalogId){
        if(StringUtils.isEmpty(catalogId)) return false;

        try{
            CatalogSummary oldCatalog = catalogService.getCatalog(catalogId);
            catalogService.deleteCatalog(catalogId);
            // associate with supplier profile
            if(CatalogDataDisplayPopulator.isValidSupplierId(oldCatalog.getSupplierId())) {
                profileService.disAssociateCatalogToSupplierProfiles(oldCatalog.getUnitId(),oldCatalog.getCatalogId(),oldCatalog.getSupplierId());
            }
        }catch (Exception exp){
            logger.error("Error deleting catalogId(" + catalogId + ") ", exp);
            return false;
        }

        return true;
    }


    private boolean isRejected(CatalogSummary catalog){
    	if(catalog.getRejectedItems()!=null && catalog.getRejectedItems() > 0 && catalog.getRejectedItems() == catalog.getOutputRecords()){
    		return true;
    	}else if(catalog.getRejectedItems()!=null && catalog.getRejectedItems() > 0 && catalog.getRejectedItems() != catalog.getOutputRecords()){
    		return true;
    	}

    	return false;
    }

    private boolean rejectCatalog(String catalogId,String userid){
        if(StringUtils.isEmpty(catalogId)) return false;

        try{
            catalogService.rejectCatalog(catalogId,userid);
        }catch (Exception exp){
            logger.error("Error rejecting catalogId(" + catalogId + ") ", exp);
            return false;
        }

        return true;
    }
    
    /**
     * Modified the existing method to return {@link CatalogSummary} object instead of String, so
     * values can be set in an object oriented way instead of manually appending strings in JSON
     * 
     * @param catalogId
     * @param unitId
     * @return
     * @author Muhammad Haris
     */
    private CatalogSummary getCatalogForEditing(String catalogId, String unitId) {
      CatalogSummary catalog = catalogService.getCatalog(catalogId);

      try {            
        /* set profile ids */
        List<Profile> profileList = catalogService.getProfileListForCatalog(unitId, catalogId);
        List<Integer> profileIdsList = new ArrayList<>(profileList.size());      
        for (Profile profile : profileList) {
          profileIdsList.add(profile.getProfileId());
        }      
        catalog.setProfileList(profileIdsList);

        if (CatalogDataDisplayPopulator.isValidSupplierId(catalog.getSupplierId())) {
          Supplier supplier = supplierService.getSupplier(unitId, catalog.getSupplierId());
          if (supplier != null) {
            catalog.setSupplierName(supplier.getCompanyName());
          }
        }

        /* if catalog time zone has been specified */
        if (StringUtils.isNotBlank(catalog.getCatalogTimeZone())) {
          /* set time zone Id by fetching it from cache */
          catalog.setTimeZoneId(
              AdminUICache.getTimeZonesByOffsetMap().get(catalog.getCatalogTimeZone())
                  .getTimeZoneId());
        }

      } catch (Exception exp) {
        logger.error("Error in fetching catalogId(" + catalogId + ") for Editing", exp);
      }

      return catalog;
    }

    public String getSupplierId(String jsonObject) throws IOException,
            JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonObject);

        if (actualObj != null && actualObj.get("supplierId") != null) {
            return actualObj.get("supplierId").getTextValue();
        }

        return null;
    }

    @RequestMapping(value = "/catalogDivFragment", method=RequestMethod.POST)
    public String getCatalogDivFragment(String catalogId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String catalogFragment = "";
        try{
            CatalogSummary catalog = catalogService.getCatalog(catalogId);
            modelMap.addAttribute("catalog", catalog);

        }catch (Exception exp){
            logger.error("Error getting catalog div fragment(" + catalogId + ") ", exp);
        }

        return CATALOG_DIV_FRAGMENT;
    }





    @RequestMapping(value="/updateCatalogState", method=RequestMethod.POST)
    @ResponseBody
    public void updateCatalogState(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="catalogId", required=false ) String catalogId,
            @RequestParam( value="state", required=false ) String state){
        try {

        	User user = getLoggedInUser(request);
            catalogService.updateCatalogState(catalogId, state,user.getUnitId(),user.getUserId());
       }catch (Exception e) {
           throw new RequestFailedException();
        }
      //  return HOME_PAGE;
    }

    @RequestMapping(value="/exportcatalog", method=RequestMethod.POST)
    public @ResponseBody void exportCatalog(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(value="catalogId", required=false ) String catalogId){
    	User user = getLoggedInUser(request);
    	System.out.println("Export Catalog "+catalogId);
    	catalogService.exportCatalog(catalogId,user.getUserId());
//        return getCatalogJson(catalogId,user.getUnitId());


    }

    @RequestMapping(value="/changereport", method=RequestMethod.POST)
    public @ResponseBody void catalogChangeHistory(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(value="catalogId", required=false ) String catalogId){
        User user = getLoggedInUser(request);
        System.out.println("Export Catalog "+catalogId);
        catalogService.catalogChangeHistory(catalogId,user.getUserId());
    }


    @RequestMapping(value="/downloadcatalog.csv", method=RequestMethod.GET)
    public @ResponseBody void downloadFile(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestParam(value="catalogid", required=false ) String catalogid,
                                           @RequestParam(value="announcement") int announcementId) {

    	try {

    		final User user = getLoggedInUser(request);
    		final String filePath = catalogService.getExportedFilePath(catalogid, user.getUnitId());
            final File file = new File(filePath);
            final BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
            response.setContentType("application/text");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + encode(file.getName(), "UTF-8") +'"');
            response.setHeader("Content-Transfer-Encoding", "binary");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();

            final Announcements announcement = new Announcements();
            announcement.setId(announcementId);
            announcement.setViewStatus("Downloaded");
    		companyService.updateAnnouncementStatus(announcement);

    	} catch (Exception e) {
    		e.printStackTrace();
		}

    }


}
