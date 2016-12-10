package com.vroozi.customerui.supplier.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.approver.controller.ApproverUIController;
import com.vroozi.customerui.approver.services.ApproverService;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.model.SystemDefinition;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.company.service.SystemDefinitionService;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.util.Consts;
import com.vroozi.customerui.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.user.services.user.model.SupplierUser;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.user.services.user.model.UserDetail;
import com.vroozi.customerui.user.services.user.model.UserFilter;
import com.vroozi.customerui.supplier.model.*;
import com.vroozi.customerui.matgroup.services.MaterialGroupService;
import com.vroozi.customerui.notification.services.NotificationService;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RandomNumberUtil;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.supplier.model.TimeZone;
import com.vroozi.customerui.user.services.UserManagementService;

import static com.vroozi.customerui.util.Consts.*;

/**
 * Provides Supplier functionality.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/9/12:3:37 PM
 */
@Controller
public class SupplierUIController {

  private static enum ViewFilter {
    NONE("NONE"), ALL("ALL"), ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private final String filterOptionValue;

    ViewFilter(String filterOptionValue) {
      this.filterOptionValue = filterOptionValue;
    }
  }


  private final Logger logger = LoggerFactory.getLogger(SupplierUIController.class);

  private static final SimpleDateFormat dateformat = new SimpleDateFormat(
      FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

  @Autowired
  AppConfig appConfig;

  @Autowired
  SupplierService supplierService;

  @Autowired
  UserManagementService userManagementService;

  @Autowired
  MaterialGroupService materialGroupService;

  @Autowired
  UploadService uploadService;

  @Autowired
  ApproverService approverService;

  @Autowired
  ApproverUIController approverUIController;

  @Autowired
  NotificationService notificationService;

  @Autowired
  CompanyService companyService;

  @Autowired
  ProfileService profileService;

  @Autowired
  private SystemDefinitionService systemDefinitionService;

  private static java.text.DateFormat df = new SimpleDateFormat("ddMMyyyyhhmmssSSS");

  @RequestMapping("/suppliers")
  public String suppliers(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);

    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      int pageNum = 1; // First Page

      setSupplierPagination(user.getUnitId(), pageNum, ViewFilter.NONE, "Name", "up", "", modelMap,
          "Catalog Suppliers", user.getRowsPerPage());

    } catch (Exception e) {
      logger.error("Error retrieving profiles");
      return FAILURE;
    }

    return SUPPLIERS_PAGE;
  }

  @RequestMapping("/activeSuppliers")
  public String activeSuppliers(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      int pageNum = 1; // First Page

      setSupplierPagination(user.getUnitId(), pageNum, ViewFilter.NONE, "Name", "up", "", modelMap,
          "Catalog Suppliers", user.getRowsPerPage());
      modelMap.addAttribute("activeSup", true);
      modelMap.addAttribute("inactiveSup", false);

    } catch (Exception e) {
      logger.error("Error retrieving profiles");
      return FAILURE;
    }

    return SUPPLIERS_PAGE;
  }

  @RequestMapping("/inactiveSuppliers")
  public String inactiveSuppliers(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);

    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      int pageNum = 1; // First Page

      setSupplierPagination(user.getUnitId(), pageNum, ViewFilter.NONE, "Name", "up", "", modelMap,
          "Catalog Suppliers", user.getRowsPerPage());
      modelMap.addAttribute("activeSup", false);
      modelMap.addAttribute("inactiveSup", true);

    } catch (Exception e) {
      logger.error("Error retrieving profiles");
      return FAILURE;
    }

    return SUPPLIERS_PAGE;
  }

  @RequestMapping(value = "/goToPageSuppliers", method = RequestMethod.POST)
  public String getSupplierPage(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "viewFilter") String viewFilter, @RequestParam(value = "pageNum",
          required = true) Integer pageNum,
      @RequestParam(value = "sortBy", required = true) String sortBy, @RequestParam(
          value = "sortDirection", required = true) String sortDirection, @RequestParam(
          value = "searchWithin", required = true) String searchWithin, ModelMap modelMap,
      @RequestParam(value = "supplierType", required = true) String supplierType) {

    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }

      setSupplierPagination(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy,
          sortDirection, searchWithin, modelMap, supplierType, user.getRowsPerPage());

    } catch (Exception exp) {
      logger.error("Error retrieving profiles pageNum(" + pageNum + ")");
    }

    return SUPPLIERS_PAGE_FRAMGMENT;
  }

  @RequestMapping(value = "/filterSuppliers", method = RequestMethod.POST)
  public String filterSuppliers(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "viewFilter") String viewFilter, @RequestParam(value = "pageNum",
          required = true) Integer pageNum,
      @RequestParam(value = "searchWithin", required = true) String searchWithin,
      ModelMap modelMap, @RequestParam(value = "supplierType", required = true) String supplierType) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }

      setSupplierPagination(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), "Name", "up",
          searchWithin, modelMap, supplierType, user.getRowsPerPage());

    } catch (Exception exp) {
      logger.error("Error retrieving profiles pageNum(" + pageNum + ")");
    }

    return SUPPLIERS_PAGE_FRAMGMENT;
  }

  private List<Supplier> filterSuppliers(List<Supplier> supplierList, ViewFilter viewFilter) {
    List<Supplier> filteredSupplierList = new ArrayList<Supplier>();

    if (viewFilter.equals(ViewFilter.INACTIVE)) {
      for (Supplier suplier : supplierList) {
        if (!suplier.isActive()) {
          filteredSupplierList.add(suplier);
        }
      }
    } else if (viewFilter.equals(ViewFilter.ACTIVE)) {
      for (Supplier suplier : supplierList) {
        if (suplier.isActive()) {
          filteredSupplierList.add(suplier);
        }
      }
    } else if (viewFilter.equals(ViewFilter.NONE)) {
      filteredSupplierList = supplierList;
    }

    return filteredSupplierList;
  }

  private List<Supplier> filterWithinSuppliers(String searchWithin,
      List<Supplier> filterTotalSupplierList) {
    if (StringUtils.isEmpty(searchWithin))
      return filterTotalSupplierList;

    searchWithin = searchWithin.toLowerCase();
    List<Supplier> filteredWithinSuppliers = new ArrayList<Supplier>();
    for (Supplier supplier : filterTotalSupplierList) {
      if (supplier.getCompanyName().toLowerCase().contains(searchWithin))
        filteredWithinSuppliers.add(supplier);

      if (supplier.getUniqueSupplierId().toLowerCase().contains(searchWithin))
        filteredWithinSuppliers.add(supplier);
    }
    return filteredWithinSuppliers;
  }

  private List<CatalogSummary> filterWithinCatalogs(String searchWithin,
      List<CatalogSummary> filterTotalSupplierList) {
    if (StringUtils.isEmpty(searchWithin))
      return filterTotalSupplierList;

    searchWithin = searchWithin.toLowerCase();
    List<CatalogSummary> filteredWithinCatalogs = new ArrayList<CatalogSummary>();
    for (CatalogSummary catalogSummary : filterTotalSupplierList) {
      if (catalogSummary.getCatalogNameNoSpace().toLowerCase().contains(searchWithin))
        filteredWithinCatalogs.add(catalogSummary);
    }
    return filteredWithinCatalogs;
  }

  private List<Supplier> getFilteredSupplierPage(int pageNum,
      List<Supplier> filterTotalSupplierList, int pageSize) {
    List<Supplier> filteredSupplierPage = new ArrayList<Supplier>();

    int offset = pageSize * (pageNum - 1);

    int maxCount =
        (filterTotalSupplierList.size() > pageSize + offset) ? pageSize + offset
            : filterTotalSupplierList.size();

    filteredSupplierPage = filterTotalSupplierList.subList(offset, maxCount);

    // for(int i = offset; i < maxCount; ++i ){
    // filteredSupplierPage.add(filterTotalSupplierList.get(i));
    // }

    return filteredSupplierPage;
  }

  @RequestMapping(value = "/activeSuppliers", method = RequestMethod.POST)
  public String activeSuppliers(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap, @RequestParam(value = "active") boolean active, @RequestParam(
          value = "pageNum") int pageNum, @RequestParam(value = "viewFilter") String viewFilter,
      @RequestParam(value = "companyIds") String[] companyIds, @RequestParam(value = "sortBy",
          required = true) String sortBy,
      @RequestParam(value = "sortDirection", required = true) String sortDirection, @RequestParam(
          value = "searchWithin", required = true) String searchWithin, @RequestParam(
          value = "supplierType", required = true) String supplierType) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      if (companyIds != null && companyIds.length > 0) {
        supplierService.updateSupplierStatus(active, companyIds);
      }

      setSupplierPagination(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy,
          sortDirection, searchWithin, modelMap, supplierType, user.getRowsPerPage());

    } catch (Exception exp) {
      logger.error("Error deleting supplierIds (" + companyIds + ") ", exp);
      return FAILURE;
    }

    return SUPPLIERS_PAGE_FRAMGMENT;
  }

  @RequestMapping(value = "/deleteSuppliers", method = RequestMethod.POST)
  public String deleteSuppliers(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap, @RequestParam(value = "pageNum") int pageNum, @RequestParam(
          value = "viewFilter") String viewFilter,
      @RequestParam(value = "companyIds") String[] companyIds, @RequestParam(value = "sortBy",
          required = true) String sortBy,
      @RequestParam(value = "sortDirection", required = true) String sortDirection, @RequestParam(
          value = "searchWithin", required = true) String searchWithin, @RequestParam(
          value = "supplierType", required = true) String supplierType) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      if (companyIds != null && companyIds.length > 0) {
        supplierService.deleteSuppliers(companyIds, user.getUnitId());
      }

      setSupplierPagination(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy,
          sortDirection, searchWithin, modelMap, supplierType, user.getRowsPerPage());
    } catch (Exception exp) {
      logger.error("Error deleting supplierIds (" + companyIds + ") ", exp);
      return FAILURE;
    }

    return SUPPLIERS_PAGE_FRAMGMENT;
  }


  @RequestMapping(value = "/searchWithinSupplier", method = RequestMethod.POST)
  public String searchWithinSuppliers(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "viewFilter") String viewFilter, @RequestParam(value = "pageNum",
          required = true) Integer pageNum,
      @RequestParam(value = "sortBy", required = true) String sortBy, @RequestParam(
          value = "sortDirection", required = true) String sortDirection, @RequestParam(
          value = "searchWithin", required = true) String searchWithin, ModelMap modelMap,
      @RequestParam(value = "supplierType", required = true) String supplierType) {

    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }

      if (searchWithin.equalsIgnoreCase("Search Within...")) {
        searchWithin = "";
      }

      setSupplierPagination(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), sortBy,
          sortDirection, searchWithin, modelMap, supplierType, user.getRowsPerPage());

    } catch (Exception exp) {
      logger.error("Error retrieving profiles pageNum(" + pageNum + ")");
    }

    return SUPPLIERS_PAGE_FRAMGMENT;
  }

  @RequestMapping(value = "/sortSuppliers", method = RequestMethod.POST)
  public String sortSupplier(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "viewFilter") String viewFilter, @RequestParam(value = "pageNum",
          required = true) Integer pageNum,
      @RequestParam(value = "sortBy", required = true) String sortBy, @RequestParam(
          value = "sortDirection", required = true) String sortDirection, @RequestParam(
          value = "searchWithin", required = true) String searchWithin, ModelMap modelMap,
      @RequestParam(value = "supplierType", required = true) String supplierType) {

    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }

      setSupplierPagination(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), sortBy,
          sortDirection, searchWithin, modelMap, supplierType, user.getRowsPerPage());

    } catch (Exception exp) {
      logger.error("Error retrieving profiles pageNum(" + pageNum + ")");
    }

    return SUPPLIERS_PAGE_FRAMGMENT;
  }


  // ////////////////////////////////////////SUPPLIER DETAIL
  // PAGE//////////////////////////////////////////////////////
  @RequestMapping("/supplierDetail")
  public String supplierDetail(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    String pageName = "Create Supplier";
    Supplier supplier = new Supplier();
    String supplierId = request.getParameter("supplierId");

    // Clear session variables here
    deleteSupplierIdsFromSession(request);
    List<ProfileProxy> supplierContentView = new ArrayList<>();
    List<ProfileProxy> finalSupplierContentView = new ArrayList<>();

    try {
      if (StringUtils.isNotEmpty(supplierId)) { // Edit Mode
        pageName = "Edit Supplier";
        supplier = getSupplier(request);
        modelMap.addAttribute("supplier", supplier);

        // get supplier mappings
        if (StringUtils.isNotEmpty(supplier.getUniqueSupplierId())) {
          int pageSize = user.getRowsPerPage() > 0 ? user.getRowsPerPage() : Consts.PAGE_SIZE;
          Page<VendorMapping> listSupplierMappings =
              materialGroupService.getPagedDataForCustomSupplierMapping(
                  Integer.parseInt(user.getUnitId()), 1, pageSize, null,
                  supplier.getUniqueSupplierId(), null);
          if (null != listSupplierMappings && null != listSupplierMappings.getPageItems()
              && !listSupplierMappings.getPageItems().isEmpty()) {
            modelMap.addAttribute("supplierMappings", listSupplierMappings.getPageItems());
          }
        }

        request.setAttribute("supplierLogo", supplier.getLogo());

        List<SupplierAttributes> supplierAttributes =
            supplierService.getSupplierAttributesByUnitId(user.getUnitId());
        if (supplier != null) {
          List<SupplierAttributePair> supplierAttributePairs = supplier.getSupplierAttributePairs();
          if (supplierAttributePairs != null && supplierAttributePairs.size() > 0) {
            for (SupplierAttributePair supplierAttributePair : supplierAttributePairs) {
              for (SupplierAttributes supplierAttribute : supplierAttributes) {
                if (supplierAttributePair.getAttributeId().equals(
                    supplierAttribute.getAttributeId())) {
                  supplierAttribute.setBoostValue(supplierAttributePair.getBoostValue());
                }
              }
            }
          }
          setSupplierApproversPaginationAttributes(
              userManagementService.getAllUsers(supplier.getUnitId()), 1, modelMap,
              user.getRowsPerPage());
          if (supplier.getSupplierAdmins() != null && !supplier.getSupplierAdmins().isEmpty()) {
            setSupplierUsersPaginationAttributes(supplier.getSupplierAdmins(), modelMap, user, 1);
          }
        }

        if (supplier.getAssignedContentViews() != null
            && supplier.getAssignedContentViews().size() > 0) {
          supplierContentView = profileService.getProfilesByIds(supplier.getAssignedContentViews());
        }

        if (supplierContentView != null && supplierContentView.size() > 0) {
          List<Integer> conViewOfSupplier = supplier.getAssignedContentViews();

          for (ProfileProxy profile : supplierContentView) {

            if (conViewOfSupplier.contains(profile.getProfileId())) {
              finalSupplierContentView.add(profile);
            }

          }
          supplierContentView = finalSupplierContentView;
        }
        modelMap.addAttribute("unitId", supplier.getUnitId());
        modelMap.addAttribute("supplierAttributes", supplierAttributes);
        modelMap.addAttribute("supplierContentView", supplierContentView);
        modelMap.addAttribute("uniqueSupplierId", supplier.getUniqueSupplierId());
        modelMap.addAttribute("minOrder", supplier.getMinOrder());
        modelMap.addAttribute("includeSupplierCard", supplier.isIncludeSupplierCard());
        modelMap.addAttribute("nonCatalogSupplier", supplier.isNonCatalogSupplier());
        modelMap.addAttribute("singleSourceSupplier", supplier.isSingleSourceSupplier());
        modelMap.addAttribute("disableBrowse", supplier.isDisableBrowse());

        approverUIController.renderBothSections(user.getUnitId(), null, supplierId, 1, 1, modelMap,
            request);
      } else {
        // set default values for new supplier
        User userDetail =
            userManagementService.getUserDetails(Integer.parseInt(user.getUnitId()),
                user.getUserId());

        supplier.setUnitId(userDetail.getUnitId());
        supplier.setCountry("233");
        supplier.setLanguage(userDetail.getLanguage());
        supplier.setDateFormat(userDetail.getDateFormat());
        supplier.setDecimalNotation(userDetail.getDecimalNotation());
        supplier.setTimeZone(userDetail.getTimeZone());
        supplier.setCurrencyCode("USD");
        modelMap.addAttribute("supplier", supplier);

        List<SupplierAttributes> supplierAttributes =
            supplierService.getSupplierAttributesByUnitId(user.getUnitId());
        modelMap.addAttribute("supplierAttributes", supplierAttributes);
        modelMap.addAttribute("unitId", supplier.getUnitId());
        supplier.setUniqueSupplierId(String.valueOf(RandomNumberUtil.generateNineDigitRandomInteger()));
        modelMap.addAttribute("uniqueSupplierId", supplier.getUniqueSupplierId());

        modelMap.addAttribute("minOrder", "0.00");
        modelMap.addAttribute("includeSupplierCard", Boolean.TRUE);
        modelMap.addAttribute("nonCatalogSupplier", Boolean.FALSE);
        modelMap.addAttribute("singleSourceSupplier", Boolean.FALSE);
        modelMap.addAttribute("disableBrowse", Boolean.FALSE);
        approverUIController.renderBothSections(user.getUnitId(), null, null, 1, 1, modelMap,
            request);
      }

      modelMap.addAttribute("pageName", pageName);

      List<Country> countries = supplierService.getAllCountries();
      modelMap.addAttribute("countries", countries);

      List<State> states = supplierService.getAllStates();
      modelMap.addAttribute("states", states);

      List<Language> languages = supplierService.getAllLanguages();
      modelMap.addAttribute("languages", languages);

      List<DecimalNotation> decimalNotations = supplierService.getAllDecimalNotation();
      modelMap.addAttribute("decimalNotations", decimalNotations);

      List<TimeZone> timeZones = supplierService.getAllTimeFormats();
      modelMap.addAttribute("timeZones", timeZones);

      List<DateFormat> dateFormats = supplierService.getAllDateFormats();
      modelMap.addAttribute("dateFormats", dateFormats);

      List<CurrencyCode> currencyCodes = supplierService.getAllCurrencyCodes();
      modelMap.addAttribute("currencyCodes", currencyCodes);

      List<SupplierAttributePair> supplierAttributePairs =
          (List<SupplierAttributePair>) request.getSession().getAttribute("supplierAttributePairs");
      if (supplierAttributePairs != null) {
        request.getSession().removeAttribute("supplierAttributePairs");
      }
      modelMap.put("supplierId", supplierId);
      CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
      modelMap.put("parentCompanyName", companySettings.getCompanyName());

      List<SystemDefinition> sysDefs =
          systemDefinitionService.getSystemDefinitions(user.getUnitId());
      modelMap.addAttribute("systemDefinitions", sysDefs);

    } catch (Exception exp) {
      logger.error("Cant get to Supplier Detail Page", exp);
      return FAILURE;
    }

    return SUPPLIER_DETAIL;
  }

  @RequestMapping(value = "/getUnassignedContentViews", method = RequestMethod.GET)
  public String getUnassignedContentViews(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierId", required = false) String supplierId, @RequestParam(
          value = "assignedProfiles", required = false) List<Integer> profileIds, ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }
    if (StringUtils.isNotEmpty(supplierId)) {
      return paginationContentViewsExistingSupplier(supplierId, profileIds, modelMap, user, 0);
    } else {// For new Suppliers
      return paginationContentViewsNewSupplier(profileIds, modelMap, user, 0);
    }
  }

  @RequestMapping("/navigateSupplierContentView")
  public String navigateProfilePage(HttpServletRequest request,
      @RequestParam(value = "supplierId") String supplierId, @RequestParam(
          value = "assignedProfiles", required = false) List<Integer> profileIds, @RequestParam(
          value = "page") String page, HttpServletResponse response, ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    int p = 0;
    try {
      p = Integer.parseInt(page);
    } catch (Exception e) {
      logger.error("Invalid page", e);
      return FAILURE;
    }
    if (StringUtils.isNotEmpty(supplierId)) {
      return paginationContentViewsExistingSupplier(supplierId, profileIds, modelMap, user, p);
    } else {// For new Suppliers
      return paginationContentViewsNewSupplier(profileIds, modelMap, user, p);
    }
  }

  @RequestMapping("/supplierAttributesCreation")
  public String supplierAttiribute(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    String pageName = "Create Attributes";
    SupplierAttributes supplierAttribute = new SupplierAttributes();
    String supplierAttributeId = request.getParameter("supplierAttribute");
    try {
      if (StringUtils.isNotEmpty(supplierAttributeId)) { // Edit Mode
        pageName = "Edit Attributes";
        supplierAttribute = supplierService.getSupplierAttributeDetails(supplierAttributeId, request.getParameter("unitId"));
        modelMap.addAttribute("attributeId", supplierAttributeId);
        modelMap.addAttribute("unitId", supplierAttribute.getUnitId());
        modelMap.addAttribute("attributeName", supplierAttribute.getAttributeName());
        modelMap.addAttribute("attributeDescription", supplierAttribute.getAttributeDescription());
        modelMap.addAttribute("attributeImagePath", supplierAttribute.getAttributeImagePath());
      }
    } catch (Exception e){
      logger.error("Cant get to Supplier Attribute Detail Page", e);
      return FAILURE;
    }
    modelMap.addAttribute("pageName", pageName);
    return SUPPLIER_ATTRIBUTE_CREATE;
  }

  @RequestMapping(value = "/addSupplierContentViews", method = RequestMethod.POST)
  public String addSupplierContentViews(HttpServletRequest request,
      @ModelAttribute ProfileProxy profile, @RequestParam(value = "supplierId") String supplierId,
      @RequestParam(value = "assignedProfiles", required = false) List<Integer> profileIds,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }
    try {
      profile.setCreatedBy(user.getUsername());
      profile.setCreatedByName(user.getFullName());
      profile.setUnitId(user.getUnitId());
      profile.setActive(true);
      profile.setCreatedOn(new Date());
      profile = profileService.addProfile(profile);
      if (StringUtils.isNotEmpty(supplierId)) {
        List<Integer> assignedProfiles = new ArrayList<Integer>();
        if (profileIds != null) {
          assignedProfiles.addAll(profileIds);
        }
        assignedProfiles.add(profile.getProfileId());
        supplierService.setSupplierContentViews(supplierId, assignedProfiles);
        request.getSession(false);
        request.getSession().removeAttribute("supplierContentViewIds");
        request.getSession().setAttribute("supplierContentViewIds", assignedProfiles);
        return userProfilesView(modelMap, assignedProfiles);
      } else { // For new Suppliers
        if (profileIds == null) {
          profileIds = new ArrayList<Integer>();
        }
        profileIds.add(profile.getProfileId());
        request.getSession(false);
        request.getSession().removeAttribute("supplierContentViewIds");
        request.getSession().setAttribute("supplierContentViewIds", profileIds);
        return userProfilesView(modelMap, profileIds);
      }
    } catch (Exception exp) {
      logger.error("Error creating profile ", exp);
      return FAILURE;
    }
  }

  @RequestMapping(value = "/assignSupplierContentView", method = RequestMethod.POST)
  public String assignSupplierContentView(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierId") String supplierId, @RequestParam(
          value = "assignedProfiles") List<Integer> profileIds, ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }
    try {
      if (StringUtils.isNotEmpty(supplierId)) {
        Supplier supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
        supplier.setAssignedContentViews(new ArrayList<Integer>());

        for (Integer pid : profileIds) {
          if (!supplier.getAssignedContentViews().contains(pid)) {
            supplier.getAssignedContentViews().add(pid);
          }
        }
        supplierService.setSupplierContentViews(supplierId, supplier.getAssignedContentViews());
        request.getSession(false);
        request.getSession().removeAttribute("supplierContentViewIds");
        request.getSession().setAttribute("supplierContentViewIds",
            supplier.getAssignedContentViews());
        return userProfilesView(modelMap, supplier.getAssignedContentViews());
      } else {// For new Suppliers
        request.getSession(false);
        request.getSession().removeAttribute("supplierContentViewIds");
        request.getSession().setAttribute("supplierContentViewIds", profileIds);
        return userProfilesView(modelMap, profileIds);
      }
    } catch (Exception exp) {
      logger.error("Exception in Assigning Content Views ", exp);
      return FAILURE;
    }
  }
  
  @RequestMapping(value = "/deleteSupplierContentView", method = RequestMethod.POST)
  public String deleteSupplierContentView(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierId") String supplierId, @RequestParam(
          value = "assignedProfiles") List<Integer> profileIds, ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }
    try {
      if (StringUtils.isNotEmpty(supplierId)) {
        Supplier supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
        for (Integer pid : profileIds) {
          if (supplier.getAssignedContentViews().contains(pid)) {
            supplier.getAssignedContentViews().remove(pid);
          }
        }
        supplierService.setSupplierContentViews(supplierId, supplier.getAssignedContentViews());
        request.getSession(false);
        request.getSession().removeAttribute("supplierContentViewIds");
        request.getSession().setAttribute("supplierContentViewIds",
            supplier.getAssignedContentViews());
        return userProfilesView(modelMap, supplier.getAssignedContentViews());
      } else {// For new Suppliers
        request.getSession(false);
        List<Integer> contentViewID =
            (List<Integer>) request.getSession().getAttribute("supplierContentViewIds");
        for (Integer id : profileIds) {
          if (contentViewID.contains(id)) {
            contentViewID.remove(id);
          }
        }
        request.getSession(false);
        request.getSession().removeAttribute("supplierContentViewIds");
        request.getSession().setAttribute("supplierContentViewIds", contentViewID);
        return userProfilesView(modelMap, contentViewID);
      }
    } catch (Exception exp) {
      logger.error("Exception in deleting Content Views ", exp);
      return FAILURE;
    }
  }

  private String userProfilesView(ModelMap modelMap, List<Integer> assignedProfiles) {
    List<ProfileProxy> supplierContentView = new ArrayList<ProfileProxy>();
    try {
      if (assignedProfiles != null && assignedProfiles.size() > 0) {
        supplierContentView = profileService.getProfilesByIds(assignedProfiles);
      }
    } catch (Exception e) {
      logger.error("Error in adding profiles to user",e);
      return FAILURE;
    }
    modelMap.addAttribute("supplierContentView", (Object) supplierContentView);
    return SUPPLIER_DETAIL_CONTENTVIEW;
  }

  @RequestMapping("/activateSupplierUser")
  // TODO: Needs to have supplierId as parameter
  public String activateSupplierApprover(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierApproverIds", required = true) String[] supplierApproverIds,
      @RequestParam(value = "pageNum", required = true) Integer pageNum, @RequestParam(
          value = "active", required = true) Boolean active, ModelMap modelMap) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      userManagementService.setUsersStatus(Arrays.asList(supplierApproverIds), active);


      // setSupplierUsersPaginationAttributes(userManagementService.getApproverListForCompany(user.getUnitId()),
      // 1, modelMap);
    } catch (Exception exp) {
      logger.error("Can't activate supplier approver", exp);
      return FAILURE;
    }

    return SUPPLIER_USERS_FRAGMENT;
  }


  @RequestMapping("/updatesupplieruser")
  public String activateSupplierUsers(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierUserIds", required = true) String[] supplierUserIds,
      @RequestParam(value = "userSupplierId", required = true) String supplierId, @RequestParam(
          value = "pageNum", required = true) Integer pageNum, @RequestParam(
          value = "uniquesupplierid", required = true) String uniqueSupplierId, @RequestParam(
          value = "active", required = true) Boolean active, ModelMap modelMap) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {

      if (supplierId != null && !supplierId.isEmpty()) {
        Supplier supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
        userManagementService.setUsersStatus(Arrays.asList(supplierUserIds), active);
        if (supplier.getSupplierAdmins() != null && !supplier.getSupplierAdmins().isEmpty()) {
          setSupplierUsersPaginationAttributes(supplier.getSupplierAdmins(), modelMap, user, 1);
        }
      } else {
        userManagementService.setNewUsersStatus(Arrays.asList(supplierUserIds), active);
        setNewSupplierUserPagination(uniqueSupplierId, modelMap, user, pageNum);
      }
      modelMap.put("supplierId", supplierId);
    } catch (Exception exp) {
      logger.error("Can't activate supplier approver", exp);
      return FAILURE;
    }

    return SUPPLIER_USERS_FRAGMENT;
  }

  @RequestMapping("/getsupplieruserpage")
  public String gotoSupplierUserPage(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierid", required = true) String supplierId, @RequestParam(
          value = "pagenum", required = true) Integer pageNum, ModelMap modelMap) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      Supplier supplier = supplierService.getSupplier(user.getUnitId(), supplierId);

      if (supplier.getSupplierAdmins() != null && !supplier.getSupplierAdmins().isEmpty()) {
        setSupplierUsersPaginationAttributes(supplier.getSupplierAdmins(), modelMap, user, pageNum);
      }
      modelMap.put("supplierId", supplierId);
    } catch (Exception exp) {
      logger.error("Can't activate supplier approver", exp);
      return FAILURE;
    }

    return SUPPLIER_USERS_FRAGMENT;
  }

  @RequestMapping("/deletesupplieruser")
  public String deleteSupplierUsers(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierUserIds", required = false) String[] supplierUserIds,
      @RequestParam(value = "userSupplierId", required = false) String supplierId, @RequestParam(
          value = "uniquesupplierid", required = true) String uniqueSupplierId, @RequestParam(
          value = "pageNum", required = false) Integer pageNum, ModelMap modelMap) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {

      if (supplierId != null && !supplierId.isEmpty()) {
        Supplier supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
        userManagementService.deleteUsers(Arrays.asList(supplierUserIds));

        List<String> newAssociatedUser =
            deleteSupplierUserAssociation(supplier.getSupplierAdmins(),
                Arrays.asList(supplierUserIds));
        supplier.setSupplierAdmins(newAssociatedUser);

        supplierService.editSupplier(supplier);

        if (supplier.getSupplierAdmins() != null && !supplier.getSupplierAdmins().isEmpty()) {
          setSupplierUsersPaginationAttributes(supplier.getSupplierAdmins(), modelMap, user, 1);
        }
      } else {
        userManagementService.deleteTempUsers(Arrays.asList(supplierUserIds));
        setNewSupplierUserPagination(uniqueSupplierId, modelMap, user, pageNum);
      }
      modelMap.put("supplierId", supplierId);
    } catch (Exception exp) {
      logger.error("Can't activate supplier approver", exp);
      return FAILURE;
    }

    return SUPPLIER_USERS_FRAGMENT;
  }


  private List<String> deleteSupplierUserAssociation(List<String> supplierUsers,
      List<String> deletedUsers) {
    List<String> attachedUsers = new ArrayList<String>();

    for (String supplierUser : supplierUsers) {
      boolean userDeleted = false;
      for (String deletedUser : deletedUsers) {
        if (supplierUser.equals(deletedUser)) {
          userDeleted = true;
        }
      }

      if (!userDeleted) {
        attachedUsers.add(supplierUser);
      }
    }
    return attachedUsers;

  }


  private List<User> getAllApprovers(String unitId) {
    List<User> approvers = new ArrayList<User>();
    try {
      approvers = userManagementService.getApproverListForCompany(unitId);
    } catch (Exception exp) {
      logger.error("Error retrieving Approvers", exp);
    }
    return approvers;
  }

  private List<User> getSupplierApprover(List<User> allApprovers, Supplier supplier) {
    if (supplier == null)
      return new ArrayList<User>();
    return getSupplierApprover(allApprovers, supplier.getApproverIds());
  }

  private List<User> getSupplierApprover(List<User> allApprovers, List<String> supplierApproverIds) {
    List<User> supplierApprovers = new ArrayList<User>();
    try {
      if (supplierApproverIds != null) {
        supplierApprovers = subtractUsers(allApprovers, supplierApproverIds);
      }
    } catch (Exception exp) {
      logger.error("Error retrieving Supplier Approvers", exp);
    }
    return supplierApprovers;
  }

  private List<User> subtractUsers(List<User> users, List<String> ids) {
    List<User> remainderCatalogs = new ArrayList<User>();
    if (ids != null) {
      for (String userId : ids) {
        for (User user : users) {
          if (user.getUserId().equals(userId)) {
            remainderCatalogs.add(user);
            users.remove(user);
            break;
          }
        }
      }
    }
    return remainderCatalogs;
  }


  private void setNewSupplierUserPagination(String supplierId, ModelMap modelMap, User user,
      Integer pageNo) {

    List<User> users = userManagementService.getUserBySupplierId(user.getUnitId(), supplierId);
    List<String> userIds = new ArrayList<String>();
    for (User user2 : users) {
      userIds.add(user2.getUserId());
    }
    int totalSupplierUserCount = userIds.size();
    modelMap.addAttribute("totalSupplierUserCount", new Integer(totalSupplierUserCount));
    modelMap.addAttribute("supplierUserCurrentPageNum", new Integer(pageNo));
    int supplierUserTotalPageNum =
        ((totalSupplierUserCount % user.getRowsPerPage()) == 0) ? totalSupplierUserCount
            / user.getRowsPerPage() : totalSupplierUserCount / user.getRowsPerPage() + 1;
    modelMap.addAttribute("supplierUserTotalPageNum", supplierUserTotalPageNum);
    int supplierUserPageFirstItemNum = (pageNo - 1) * user.getRowsPerPage() + 1;
    modelMap.addAttribute("supplierUserPageFirstItemNum", supplierUserPageFirstItemNum);
    int supplierUserPageLastItemNum =
        (pageNo * user.getRowsPerPage() < totalSupplierUserCount) ? supplierUserPageFirstItemNum
            + user.getRowsPerPage() - 1 : totalSupplierUserCount;
    modelMap.addAttribute("supplierUserPageLastItemNum", new Integer(supplierUserPageLastItemNum));
    modelMap.addAttribute("supplierUserTotalPageNum", new Integer(supplierUserTotalPageNum));

    UserFilter filter = getFilteredUsers("", user, pageNo, userIds);

    Page<User> userPage = userManagementService.getSupplierUserByIds(user.getUnitId(), filter);

    List<User> pagedSupplierUserList = userPage.getPageItems();
    modelMap.addAttribute("pagedSupplierUserList", pagedSupplierUserList);
  }

  private void setSupplierUsersPaginationAttributes(List<String> userIds, ModelMap modelMap,
      User user, Integer pageNo) {
    int totalSupplierUserCount = userIds.size();
    modelMap.addAttribute("totalSupplierUserCount", new Integer(totalSupplierUserCount));
    modelMap.addAttribute("supplierUserCurrentPageNum", new Integer(pageNo));
    int supplierUserTotalPageNum =
        ((totalSupplierUserCount % user.getRowsPerPage()) == 0) ? totalSupplierUserCount
            / user.getRowsPerPage() : totalSupplierUserCount / user.getRowsPerPage() + 1;
    modelMap.addAttribute("supplierUserTotalPageNum", supplierUserTotalPageNum);
    int supplierUserPageFirstItemNum = (pageNo - 1) * user.getRowsPerPage() + 1;
    modelMap.addAttribute("supplierUserPageFirstItemNum", supplierUserPageFirstItemNum);
    int supplierUserPageLastItemNum =
        (pageNo * user.getRowsPerPage() < totalSupplierUserCount) ? supplierUserPageFirstItemNum
            + user.getRowsPerPage() - 1 : totalSupplierUserCount;
    modelMap.addAttribute("supplierUserPageLastItemNum", new Integer(supplierUserPageLastItemNum));
    modelMap.addAttribute("supplierUserTotalPageNum", new Integer(supplierUserTotalPageNum));

    UserFilter filter = getFilteredUsers("", user, pageNo, userIds);

    Page<User> userPage = userManagementService.getUserByIds(user.getUnitId(), filter);

    List<User> pagedSupplierUserList = userPage.getPageItems();
    modelMap.addAttribute("pagedSupplierUserList", pagedSupplierUserList);
  }

  private void setSupplierApproversPaginationAttributes(List<User> supplierApprovers,
      int supplierApproverCurrentPageNum, ModelMap modelMap, int pageSize) throws Exception {
    int totalSupplierApproverCount = supplierApprovers.size();
    modelMap.addAttribute("totalSupplierApproverCount", new Integer(totalSupplierApproverCount));

    modelMap.addAttribute("supplierApproverCurrentPageNum", new Integer(
        supplierApproverCurrentPageNum));
    int supplierApproverTotalPageNum =
        ((totalSupplierApproverCount % pageSize) == 0) ? totalSupplierApproverCount / pageSize
            : totalSupplierApproverCount / pageSize + 1;
    modelMap.addAttribute("supplierApproverTotalPageNum", supplierApproverTotalPageNum);

    int supplierApproverPageFirstItemNum = (supplierApproverCurrentPageNum - 1) * pageSize + 1;
    modelMap.addAttribute("supplierApproverPageFirstItemNum", supplierApproverPageFirstItemNum);

    int supplierApproverPageLastItemNum =
        (supplierApproverCurrentPageNum * pageSize < totalSupplierApproverCount) ? supplierApproverPageFirstItemNum
            + pageSize - 1
            : totalSupplierApproverCount;
    modelMap.addAttribute("supplierApproverPageLastItemNum", new Integer(
        supplierApproverPageLastItemNum));

    modelMap
        .addAttribute("supplierApproverTotalPageNum", new Integer(supplierApproverTotalPageNum));

    List<User> pagedSupplierApproverList =
        supplierApprovers.subList(supplierApproverPageFirstItemNum - 1,
            supplierApproverPageLastItemNum);

    modelMap.addAttribute("pagedSupplierApproverList", pagedSupplierApproverList);
  }


  @RequestMapping(value = "/createEditSupplier", method = RequestMethod.POST)
  public @ResponseBody String createCatalog(HttpServletRequest request,
      HttpServletResponse response, @ModelAttribute SupplierForm supplierForm,
      BindingResult bindingResult) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      if (isSupplierDuplicate(user.getUnitId(), supplierForm.getCompanyName(),
          supplierForm.getSupplierId(), supplierForm.getDefaultVendorId())) {
        throw new Exception("Supplier with name " + supplierForm.getCompanyName()
            + ", default supplier id " + supplierForm.getDefaultVendorId() + " already exists!");
      }

      Supplier supplier = null;

      String supplierId = request.getParameter("supplierId");
      if (StringUtils.isNotEmpty(supplierId)) {
        supplier = getSupplier(user.getUnitId(), supplierId);
      } else {
        supplier = new Supplier();
      }

      String companyId = supplierForm.getSupplierId();
      if (!StringUtils.isEmpty(companyId)) {
        supplier.setCompanyId(companyId);
      }
      supplier.setUniqueSupplierId(supplierForm.getUniqueSupplierId());
      supplier.setCompanyName(supplierForm.getCompanyName());
      supplier.setDunsNumber(supplierForm.getDunsNumber());
      supplier.setDefaultVendorId(supplierForm.getSupplierId());
      supplier.setAddress1(supplierForm.getAddressLine1());
      supplier.setAddress2(supplierForm.getAddressLine2());
      supplier.setCity(supplierForm.getCity());
      supplier.setState(supplierForm.getStateCounty());
      supplier.setCountry(supplierForm.getCountryRegion());
      supplier.setZip(supplierForm.getZipPostalCode());
      supplier.setContactName(supplierForm.getContactName());
      supplier.setContactRole(supplierForm.getContactRole());
      supplier.setPhone(supplierForm.getPhoneNumber());
      supplier.setFax(supplierForm.getFaxNumber());
      supplier.setEmail(supplierForm.getEmailAddress());
      supplier.setLanguage(supplierForm.getLanguage());
      supplier.setDecimalNotation(supplierForm.getDecimalNotation());
      supplier.setTimeZone(supplierForm.getTimezone());
      supplier.setDateFormat(supplierForm.getDateFormat());
      supplier.setRowsPerPage(user.getRowsPerPage());
      supplier.setDefaultVendorId(supplierForm.getDefaultVendorId());
      supplier.setMinOrder(supplierForm.getMinOrder());
      supplier.setCurrencyCode(supplierForm.getCurrencyCode());
      supplier.setSingleSourceSupplier(supplierForm.isSingleSourceSupplier());
      supplier.setIncludeSupplierCard(supplierForm.isIncludeSupplierCard());
      supplier.setNonCatalogSupplier(supplierForm.isNonCatalogSupplier());
      supplier.setDisableBrowse(supplierForm.isDisableBrowse());
      supplier.setVendorId(supplierForm.getDefaultVendorId());

      logger.debug("Vendor Id: ", supplier.getVendorId());

      request.getSession(false);
      List<Integer> contentViewId =
          (List<Integer>) request.getSession().getAttribute("supplierContentViewIds");

      setSupplierDefaultsIfMissing(supplierForm, supplier, user);

      if (contentViewId != null && contentViewId.size() > 0) {
        supplier.setAssignedContentViews((List<Integer>) request.getSession().getAttribute(
            "supplierContentViewIds"));
      } else {
        supplier.setAssignedContentViews(supplier.getAssignedContentViews());
      }

      // Min order value
      String minOderValue = (String) request.getSession().getAttribute("supplierMinOrder");
      if (StringUtils.isNotEmpty(minOderValue)) {
        supplier.setMinOrder(minOderValue);
      }

      // Include Card
      String includeCard = (String) request.getSession().getAttribute("supplierIncludeCard");
      if (StringUtils.isNotEmpty(includeCard)) {
        supplier.setIncludeSupplierCard(Boolean.valueOf(includeCard));
      }

      // Logo
      if (!StringUtils.isEmpty(supplierForm.getLogo())) {
        supplier.setLogo(supplierForm.getLogo());
      }

      supplier.setUnitId(user.getUnitId());
      supplier.setCreatedBy(user.getUsername());
      supplier.setActive(true); // Active by default

      List<String> sessionApproverIds = getSupplierIdsFromSession(request);
      if (sessionApproverIds != null && sessionApproverIds.size() > 0) {
        supplier.setApproverIds(sessionApproverIds);
      }

      if (StringUtils.isEmpty(supplierId)) { // Create Mode
        List<SupplierAttributePair> supplierAttributePairs =
            (List<SupplierAttributePair>) request.getSession().getAttribute(
                "supplierAttributePairs");
        if (supplierAttributePairs != null) {
          supplier.setSupplierAttributePairs(supplierAttributePairs);
        } else {
          supplier.setSupplierAttributePairs(new ArrayList<SupplierAttributePair>());
        }
        // Set it only in Create Mode
        supplier.setUniqueSupplierId(supplierForm.getUniqueSupplierId());
        supplier.setCreatedOn(new Date());
        supplierService.addSupplier(supplier);

        request.getSession().removeAttribute("supplierAttributePairs");
        request.getSession().removeAttribute("supplierMinOrder");
        request.getSession().removeAttribute("supplierIncludeCard");
        deleteSupplierIdsFromSession(request);
      } else { // Edit Mode
        supplierService.editSupplier(supplier);
      }


    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  public void setSupplierDefaultsIfMissing(SupplierForm supplierForm, Supplier supplier,
                                           User user) {

    if (supplierForm.getRowsPerPage() <= 0) {
      supplier.setRowsPerPage(user.getRowsPerPage() > 0 ? user.getRowsPerPage()
                                                        : Integer.parseInt(appConfig.rowsPerPage));
    }
    if (StringUtils.isBlank(supplierForm.getDateFormat())) {
      supplier
          .setDateFormat(StringUtils.isNotBlank(user.getDateFormat()) ? user.getDateFormat()
                                                                      : appConfig.dateFormat);
    }
    if (StringUtils.isBlank(supplierForm.getDecimalNotation())) {
      supplier.setDecimalNotation(
          StringUtils.isNotBlank(user.getDecimalNotation()) ? user.getDecimalNotation()
                                                            : appConfig.decimalNotation);
    }
    if (StringUtils.isBlank(supplierForm.getTimezone())) {
      supplier.setTimeZone(
          StringUtils.isNotBlank(user.getTimeZone()) ? user.getTimeZone() : appConfig.timeZone);
    }
    if (StringUtils.isBlank(supplierForm.getLanguage())) {
      supplier.setLanguage(
          StringUtils.isNotBlank(user.getLanguage()) ? user.getLanguage() : appConfig.language);
    }
  }

  @RequestMapping(value = "/boostSupplierAttribute", method = RequestMethod.POST)
  public @ResponseBody String boostSupplierAttribute(HttpServletRequest request,
      HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "attributeId",
          required = true) String attributeId,
      @RequestParam(value = "supplierId", required = true) String supplierId, @RequestParam(
          value = "boostLevel", required = true) Integer boostLevel) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      if (isEditMode(request)) { // Edit Mode
        Supplier supplier = getSupplier(user.getUnitId(), supplierId);
        SupplierAttributePair supplierAttributePair =
            findSupplierAttributePair(supplier.getSupplierAttributePairs(), attributeId);
        if (supplierAttributePair != null) {
          supplierAttributePair.setBoostValue(boostLevel);
          supplierService.editSupplier(supplier);
        }
      } else { // Create Mode
        List<SupplierAttributePair> supplierAttributePairs =
            (List<SupplierAttributePair>) request.getSession().getAttribute(
                "supplierAttributePairs");
        if (supplierAttributePairs == null) {
          supplierAttributePairs = new ArrayList<SupplierAttributePair>();
          request.getSession().setAttribute("supplierAttributePairs", supplierAttributePairs);
        }
        SupplierAttributePair supplierAttributePair =
            findSupplierAttributePair(supplierAttributePairs, attributeId);
        if (supplierAttributePair != null) {
          supplierAttributePair.setBoostValue(boostLevel);
        }
      }
    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  @RequestMapping(value = "/updateSupplierMinOrder", method = RequestMethod.POST)
  public @ResponseBody String updateSupplierMinOrder(HttpServletRequest request,
      HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "editSupplierId",
          required = true) String supplierId,
      @RequestParam(value = "newValue", required = true) String newValue) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    if (newValue != null) {
      request.getSession().setAttribute("supplierMinOrder", newValue);
    }

    try {
      if (!"000".equals(supplierId)) { // Edit Mode
        Supplier supplier = getSupplier(user.getUnitId(), supplierId);
        if (newValue != null) {
          supplier.setMinOrder(newValue);
          supplierService.editSupplier(supplier);
        }
      }
    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  @RequestMapping(value = "/updateSupplierCatalog", method = RequestMethod.POST)
  public @ResponseBody String updateSupplierCatalog(HttpServletRequest request,
      HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "editSupplierId",
          required = true) String supplierId,
      @RequestParam(value = "newValue", required = true) String newValue) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      if (!"000".equals(supplierId)) { // Edit Mode
        Supplier supplier = getSupplier(user.getUnitId(), supplierId);
        if (newValue != null) {
          supplier.setNonCatalogSupplier(Boolean.valueOf(newValue));
          supplierService.editSupplier(supplier);
        }
      }
    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  @RequestMapping(value = "/update-single-source-supplier", method = RequestMethod.POST)
  public @ResponseBody String updateSingleSourceSupplier(HttpServletRequest request,
                                                         @RequestParam(value = "editSupplierId") String supplierId,
                                                         @RequestParam(value = "newValue") String newValue) {

    final String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    final String EDIT_MODE = "000";
    if (!EDIT_MODE.equals(supplierId)) {
      final Supplier supplier = getSupplier(user.getUnitId(), supplierId);
      if (newValue != null) {
        supplier.setSingleSourceSupplier(Boolean.valueOf(newValue));
        supplierService.editSupplier(supplier);
      }
    }

    return retValue;
  }

  @RequestMapping(value = "/updateSupplierIncludeCard", method = RequestMethod.POST)
  public @ResponseBody String updateSupplierIncludeCard(HttpServletRequest request,
      HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "editSupplierId",
          required = true) String supplierId,
      @RequestParam(value = "newValue", required = true) String newValue) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    if (newValue != null) {
      request.getSession().setAttribute("supplierIncludeCard", newValue);
    }

    try {
      if (!"000".equals(supplierId)) { // Edit Mode
        Supplier supplier = getSupplier(user.getUnitId(), supplierId);
        if (newValue != null) {
          supplier.setIncludeSupplierCard(Boolean.valueOf(newValue));
          supplierService.editSupplier(supplier);
        }
      }
    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  @RequestMapping(value = "/updateSupplierDisableBrowse", method = RequestMethod.POST)
  public @ResponseBody String updateSupplierDisableBrowse(HttpServletRequest request,
      HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "editSupplierId",
          required = true) String supplierId,
      @RequestParam(value = "newValue", required = true) String newValue) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    if (newValue != null) {
      request.getSession().setAttribute("disableBrowse", newValue);
    }

    try {
      if (!"000".equals(supplierId)) { // Edit Mode
        Supplier supplier = getSupplier(user.getUnitId(), supplierId);
        if (newValue != null) {
          supplier.setDisableBrowse(Boolean.valueOf(newValue));
          supplierService.editSupplier(supplier);
        }
      }
    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  @RequestMapping(value = "/updateSupplierCurrencyCode", method = RequestMethod.POST)
  public @ResponseBody String updateSupplierCurrencyCode(HttpServletRequest request,
      HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "editSupplierId",
          required = true) String supplierId,
      @RequestParam(value = "newValue", required = true) String newValue) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      if (!"000".equals(supplierId)) { // Edit Mode
        Supplier supplier = getSupplier(user.getUnitId(), supplierId);
        if (newValue != null) {
          supplier.setCurrencyCode(newValue);
          supplierService.editSupplier(supplier);
        }
      } else { // Create Mode
        // if(newValue != null){
        // request.getSession().setAttribute("supplierIncludeCard", newValue);
        // }
      }
    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  @RequestMapping(value = "/changeAttributeAssociation", method = RequestMethod.POST)
  public @ResponseBody String changeAttributeAssociation(HttpServletRequest request,
      HttpServletResponse response, ModelMap modelMap, @RequestParam(value = "attributeId",
          required = true) String attributeId,
      @RequestParam(value = "supplierId", required = true) String supplierId, @RequestParam(
          value = "associate", required = true) Boolean associate, @RequestParam(
          value = "boostLevel", required = true) Integer boostLevel, @RequestParam(
          value = "attributeName", required = true) String attributeName) {

    String retValue = "Success";

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      if (isEditMode(request)) { // Edit Mode
        Supplier supplier = getSupplier(user.getUnitId(), supplierId);
        if (supplier == null) {
          throw new Exception("Can not get supplier(" + supplierId + ")");
        }
        SupplierAttributePair supplierAttributePair =
            findSupplierAttributePair(supplier.getSupplierAttributePairs(), attributeId);
        if (associate) {
          if (supplierAttributePair == null) {
            SupplierAttributes supplierAttributes =
                supplierService.getSupplierAttributeDetails(attributeId, user.getUnitId());
            if (supplier.getSupplierAttributePairs() == null) {
              supplier.setSupplierAttributePairs(new ArrayList<SupplierAttributePair>());
            }
            supplier.getSupplierAttributePairs().add(
                new SupplierAttributePair(attributeId, supplierId, boostLevel, attributeName));
          }
        } else {
          if (supplierAttributePair != null) {
            supplier.getSupplierAttributePairs().remove(supplierAttributePair);
          }
        }

        supplierService.editSupplier(supplier);
      } else { // Create Mode
        List<SupplierAttributePair> supplierAttributePairs =
            (List<SupplierAttributePair>) request.getSession().getAttribute(
                "supplierAttributePairs");
        if (supplierAttributePairs == null) {
          supplierAttributePairs = new ArrayList<SupplierAttributePair>();
          request.getSession().setAttribute("supplierAttributePairs", supplierAttributePairs);
        }
        SupplierAttributePair supplierAttributePair =
            findSupplierAttributePair(supplierAttributePairs, attributeId);
        if (associate) {
          if (supplierAttributePair == null) {
            SupplierAttributes supplierAttributes =
                supplierService.getSupplierAttributeDetails(attributeId, user.getUnitId());
            supplierAttributePairs.add(new SupplierAttributePair(attributeId, supplierId,
                boostLevel, attributeName));
          }
        } else {
          if (supplierAttributePair != null) {
            supplierAttributePairs.remove(supplierAttributePair);
          }
        }
      }
    } catch (Exception exp) {
      retValue = "Failure:" + exp.getMessage();
    }

    return retValue;
  }

  @RequestMapping(value = "/uploadSupplierLogo", method = RequestMethod.POST,
      produces = "text/html")
  public @ResponseBody String uploadSupplierImage(HttpServletRequest request,
      HttpServletResponse response, @ModelAttribute SupplierLogoForm supplierLogoForm,
      BindingResult bindingResult) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    String supplierLogo = null;
    try {


      if (supplierLogoForm != null) {
        if (isEditMode(request)) { // Edit Mode
          Supplier supplier = getSupplier(user.getUnitId(), supplierLogoForm.getSupplierId());
          if (supplier == null) {
            throw new Exception("Can not get supplier(" + supplierLogoForm.getSupplierId() + ")");
          }
          supplierLogo = saveSupplierLogo(supplierLogoForm);
          if (supplierLogo != null) {
            supplier.setLogo(supplierLogo);
            supplierService.editSupplier(supplier);
            // request.setAttribute("supplierLogo", supplierLogo);
          }
        } else { // Create Mode
          supplierLogo = saveSupplierLogo(supplierLogoForm);
        }
      }

    } catch (Exception exp) {
      logger.error(exp.getMessage(), exp);
      return "Failure:" + exp.getMessage();
    }

    return supplierLogo;
  }

  private String saveSupplierLogo(SupplierLogoForm supplierLogoForm) throws Exception {
    String supplierLogo = null;
    String fileNamePostfix = "_" + df.format(new Date());
    if (supplierLogoForm.getLogoFile() != null && !supplierLogoForm.getLogoFile().isEmpty()) {
      String logoFilename = supplierLogoForm.getLogoFile().getOriginalFilename();
      logoFilename =
          logoFilename.substring(0, logoFilename.lastIndexOf(".")) + fileNamePostfix
              + logoFilename.substring(logoFilename.lastIndexOf(".")).toLowerCase();
      logoFilename = logoFilename.replaceAll(" ", "");
      if (writeToFile(supplierLogoForm.getLogoFile().getBytes(), logoFilename,
          appConfig.itemImagesPath + File.separator + "supplier")) {
        // request.setAttribute("supplierLogo", "image/" + logoFilename);
        Utils.generateImage(appConfig.itemImagesPath + File.separator + "supplier" + File.separator
            + logoFilename, logoFilename, appConfig.itemImagesPath + File.separator + "supplier");
        supplierLogo = "image/LARGE_" + logoFilename;
      }

    }
    return supplierLogo;
  }

  @RequestMapping(value = "/deleteSupplierLogo", method = RequestMethod.POST)
  public String deleteSupplierLogo(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap, @RequestParam(value = "supplierId", required = true) String supplierId) {

    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    Supplier supplier = null;
    try {
      if (isEditMode(request)) {
        supplier = getSupplier(user.getUnitId(), supplierId);
        if (supplier == null) {
          throw new Exception("Can not get supplier(" + supplierId + ")");
        }
        String logFilePath = supplier.getLogo();
        if (logFilePath.endsWith("/") || logFilePath.endsWith("\\")) {
          logFilePath = logFilePath.substring(0, logFilePath.length() - 1);
        }
        File logFilePathFile = new File(appConfig.itemImagesPath + File.separator + logFilePath);
        if (logFilePathFile.exists()) {
          logFilePathFile.delete();
        }
        supplier.setLogo(null);
        supplierService.editSupplier(supplier);
      } else {

      }

    } catch (Exception exp) {
      return "Failure:" + exp.getMessage();
    }

    return SUPPLIER_LOGO_FRAGMENT;
  }



  @RequestMapping("/updatesuppliermappings")
  public String updateSupplierMappingss(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      String uniqueSupplierId = request.getParameter("uniqueSupplierIdForm");
      String unitId = request.getParameter("unitIdMappingForm");
      String[] supplierIds = request.getParameterValues("supplier_Id");
      String[] systemIds = request.getParameterValues("systemId");
      String[] mapingIds = request.getParameterValues("supplierMappingIds");
      if (supplierIds != null && supplierIds.length > 0) {
        for (int i = 0; i < supplierIds.length; i++) {
          if ((supplierIds[i] != null && !supplierIds[i].isEmpty())
              && (systemIds[i] != null && !systemIds[i].isEmpty())) {
            if (!supplierService.checkMappingExists(supplierIds[i], uniqueSupplierId, systemIds[i])) {
              supplierService.updateSupplierMapping(supplierIds[i], uniqueSupplierId, unitId,
                  mapingIds[i], systemIds[i]);
            } else {
              // validation error
            }
          }
        }
      }
      int pageSize = user.getRowsPerPage() > 0 ? user.getRowsPerPage() : 8;
      // get supplier mappings
      Page<VendorMapping> listSupplierMappings =
          materialGroupService.getPagedDataForCustomSupplierMapping(
              Integer.parseInt(user.getUnitId()), 1, pageSize, null, uniqueSupplierId, null);
      if (null != listSupplierMappings && null != listSupplierMappings.getPageItems()
          && !listSupplierMappings.getPageItems().isEmpty()) {
        modelMap.addAttribute("supplierMappings", listSupplierMappings.getPageItems());
      }
      // System.out.println(supplierId);
    } catch (Exception e) {
      logger.error("Error retrieving profiles", e);
      return FAILURE;
    }

    return SUPPLIER_DETAILS_PAGE_MAPPING_SECTION_FRAGMENT;
  }



  @RequestMapping("/canclemappingupdate")
  public String cancelUpdate(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      Map<String, String> map = request.getParameterMap();
      String uniqueSupplierId = request.getParameter("uniqueSupplierIdForm");
      String unitId = request.getParameter("unitIdMappingForm");
      int pageSize = user.getRowsPerPage() > 0 ? user.getRowsPerPage() : 8;

      Page<VendorMapping> listSupplierMappings =
          materialGroupService.getPagedDataForCustomSupplierMapping(
              Integer.parseInt(user.getUnitId()), 1, pageSize, null, uniqueSupplierId, null);
      if (null != listSupplierMappings && null != listSupplierMappings.getPageItems()
          && !listSupplierMappings.getPageItems().isEmpty()) {
        modelMap.addAttribute("supplierMappings", listSupplierMappings.getPageItems());
      }

    } catch (Exception e) {
      logger.error("Error retrieving profiles", e);
      return FAILURE;
    }

    return SUPPLIER_DETAILS_PAGE_MAPPING_SECTION_FRAGMENT;
  }


  @RequestMapping(value = "/suppliermappings/validate/{systemids}", method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody List<String> validateUniqueSystemIds(HttpServletRequest request,
      HttpServletResponse response, @PathVariable("systemids") String systemIdStr) {
    try {
      String[] ids = systemIdStr.split(",");
      List<String> systemIds = Arrays.asList(ids);
      List<String> invalidSystemIds = new ArrayList<String>();

      User user = SessionDataRetriever.getLoggedInUser(request);
      Set<String> systemIdsAndNames = getSystemIdsAndNames(user.getUnitId());

      for (String systemId : systemIds) {
        if (!systemIdsAndNames.contains(systemId.toLowerCase())) {
          invalidSystemIds.add(systemId);
        }
      }

      return invalidSystemIds;
    } catch (Exception exp) {
      logger.error("Error validating system ids", exp);
      return null;
    }
  }


  @RequestMapping("/savesuppliermappings")
  public String saveSupplierMappingss(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      Map<String, String> map = request.getParameterMap();
      String companyNameForMapping = request.getParameter("companyNameForMapping");
      String uniqueSupplierId = request.getParameter("uniqueSupplierIdForm");
      String unitId = request.getParameter("unitIdMappingForm");
      String[] supplierIds = request.getParameterValues("supplier_Id");
      String[] systemIds = request.getParameterValues("systemId");
      if (supplierIds != null && supplierIds.length > 0) {
        for (int i = 0; i < supplierIds.length; i++) {
          if ((supplierIds[i] != null && !supplierIds[i].isEmpty())
              && (systemIds[i] != null && !systemIds[i].isEmpty())) {
            if (!supplierService.checkMappingExists(supplierIds[i], uniqueSupplierId, systemIds[i])) {
              supplierService.addSupplierMapping(supplierIds[i], uniqueSupplierId, unitId,
                  systemIds[i], companyNameForMapping);
            } else {
              // validation error
            }
          }
        }
      }
      int pageSize = user.getRowsPerPage() > 0 ? user.getRowsPerPage() : 8;
      // get supplier mappings
      Page<VendorMapping> listSupplierMappings =
          materialGroupService.getPagedDataForCustomSupplierMapping(
              Integer.parseInt(user.getUnitId()), 1, pageSize, null, uniqueSupplierId, null);
      if (null != listSupplierMappings && null != listSupplierMappings.getPageItems()
          && !listSupplierMappings.getPageItems().isEmpty()) {
        modelMap.addAttribute("supplierMappings", listSupplierMappings.getPageItems());
      }
      List<SystemDefinition> sysDefs = systemDefinitionService.getSystemDefinitions(unitId);
      modelMap.addAttribute("systemDefinitions", sysDefs);
      // System.out.println(supplierId);
    } catch (Exception e) {
      logger.error("Error retrieving profiles", e);
      return FAILURE;
    }

    return SUPPLIER_DETAILS_PAGE_MAPPING_SECTION_FRAGMENT;
  }

  @RequestMapping("/deletesuppliermappings")
  public String deleteSupplierMappingss(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      return INVALID_SESSION_PAGE;
    }

    try {
      // Map<String, String> map = request.getParameterMap();
      String uniqueSupplierId = request.getParameter("uniqueSupplierIdForm");
      String unitId = request.getParameter("unitIdMappingForm");
      String[] supplierMappingIds = request.getParameterValues("supplierMappingIds");

      if (supplierMappingIds != null && supplierMappingIds.length > 0) {
        for (int i = 0; i < supplierMappingIds.length; i++) {
          supplierService.deleteSupplierMapping(supplierMappingIds[i]);
        }
      }
      // get supplier mappings
      Page<VendorMapping> listSupplierMappings =
          materialGroupService.getPagedDataForCustomSupplierMapping(
              Integer.parseInt(user.getUnitId()), 1, user.getRowsPerPage(), null, uniqueSupplierId,
              null);
      if (null != listSupplierMappings && null != listSupplierMappings.getPageItems()
          && !listSupplierMappings.getPageItems().isEmpty()) {
        modelMap.addAttribute("supplierMappings", listSupplierMappings.getPageItems());
      }
      List<SystemDefinition> sysDefs = systemDefinitionService.getSystemDefinitions(unitId);
      modelMap.addAttribute("systemDefinitions", sysDefs);

      // System.out.println(supplierId);
    } catch (Exception e) {
      logger.error("Error retrieving profiles");
      return FAILURE;
    }

    return SUPPLIER_DETAILS_PAGE_MAPPING_SECTION_FRAGMENT;
  }


  private boolean writeToFile(byte[] source, String destFileName, String uploadPath)
      throws Exception {
    try {
      File parentFolder = new File(uploadPath.trim());
      if (!parentFolder.exists()) {
        parentFolder.mkdirs();
      }

      File file = new File(parentFolder, destFileName);
      if (file.exists()) {
        file.delete();
      }

      FileOutputStream fileOutputStream = new FileOutputStream(file);
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
      bufferedOutputStream.write(source);
      bufferedOutputStream.flush();
      bufferedOutputStream.close();
    } catch (IOException exception) {
      logger.error("Error in writing to file(" + destFileName + ")", exception);
      return false;
    }
    return true;
  }

  private SupplierAttributePair findSupplierAttributePair(List<SupplierAttributePair> pairList,
      String supplierAttributeId) {
    if (pairList == null || pairList.size() == 0 || supplierAttributeId == null
        || supplierAttributeId.length() == 0)
      return null;

    for (SupplierAttributePair supplierAttributePair : pairList) {
      if (supplierAttributePair.getAttributeId().equals(supplierAttributeId))
        return supplierAttributePair;
    }
    return null;
  }

  private boolean isSupplierDuplicate(String unitId, String newSupplierName, String supplierId,
      String defaultVendorId) {
    boolean duplicate = false;

    List<Supplier> suppliers = supplierService.getSupplierByUnitId(unitId);

    for (Supplier supp : suppliers) {
      if (supp.getCompanyName().equalsIgnoreCase(newSupplierName)
          && supp.getDefaultVendorId().equalsIgnoreCase(defaultVendorId)) {
        if (!StringUtils.isEmpty(supplierId)) {
          if (supp.getCompanyId().equalsIgnoreCase(supplierId)) {
            continue;
          } else {
            duplicate = true;
            break;
          }

        } else {
          duplicate = true;
          break;
        }
      }
    }

    return duplicate;
  }

  private Supplier getSupplier(HttpServletRequest request) {
    String unitId = request.getParameter("unitId");
    String supplierId = request.getParameter("supplierId");

    return getSupplier(unitId, supplierId);
  }

  private Supplier getSupplier(String unitId, String supplierId) {
    if (StringUtils.isEmpty(unitId) || StringUtils.isEmpty(supplierId)) {
      return null;
    }

    return supplierService.getSupplier(unitId, supplierId);
  }

  private boolean isEditMode(HttpServletRequest request) {
    String profileId = request.getParameter("supplierId");
    return StringUtils.isNotEmpty(profileId);
  }

  // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // Helper methods //

  private void setSupplierPagination(String companyCode, int supplierCurrentPageNum,
      ViewFilter supplierViewFiler, String sortBy, String sortDirection, String searchWithin,
      ModelMap modelMap, String supplierType, int pageSize) throws Exception {

    List<Supplier> filteredSupplierList = new ArrayList<Supplier>();
    List<Supplier> totalSupplierList = supplierService.getSupplierByUnitId(companyCode);
    int totalSupplierCount = totalSupplierList.size();
    int activeSupplierCount = 0;
    int inActiveSupplierCount = 0;
    for (Supplier supp : totalSupplierList) {
      if (supp.isActive()) {
        activeSupplierCount++;
      } else {
        inActiveSupplierCount++;
      }

      if (supp.isNonCatalogSupplier() && "Non Catalog Suppliers".equals(supplierType)) {
        filteredSupplierList.add(supp);
      } else if (!supp.isNonCatalogSupplier() && "Catalog Suppliers".equals(supplierType)) {
        filteredSupplierList.add(supp);
      } else if ("All".equals(supplierType)) {
        filteredSupplierList.add(supp);
      }
    }
    totalSupplierList = filteredSupplierList;
    filteredSupplierList = new ArrayList<Supplier>();
    int numOfActiveSupplier = 0;
    for (Supplier supplier : totalSupplierList) {
      if (supplier.isActive()) {
        numOfActiveSupplier += 1;
      }
    }
    // These are for upper portion of the page
    modelMap.addAttribute("totalSuppliersCount", new Integer(totalSupplierCount));
    modelMap.addAttribute("numOfActiveSupplier", new Integer(activeSupplierCount));

    // Pagination Info
    List<Supplier> filterTotalSupplierList = filterSuppliers(totalSupplierList, supplierViewFiler);

    // Search Within filter
    if (!StringUtils.isEmpty(searchWithin)) {
      filterTotalSupplierList = filterWithinSuppliers(searchWithin, filterTotalSupplierList);
      modelMap.addAttribute("searchWithin", searchWithin);
    } else {
      modelMap.addAttribute("searchWithin", "");
    }

    int totalSupplierApproverCount = filterTotalSupplierList.size();
    modelMap.addAttribute("totalSupplierApproverCount", new Integer(totalSupplierApproverCount));

    if (filterTotalSupplierList.size() <= (supplierCurrentPageNum - 1) * pageSize) {
      supplierCurrentPageNum = 1; // Reset to 1 or go back one page
    }

    if (StringUtils.isEmpty(sortBy)) {
      sortBy = "Name";
    }
    modelMap.addAttribute("sortBy", sortBy);

    if (StringUtils.isEmpty(sortDirection)) {
      sortDirection = "up";
    }
    modelMap.addAttribute("sortDirection", sortDirection);

    Comparator<Supplier> comparator = null;
    if (sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("up")) {
      comparator = new SupplierNameComparatorAsc();
    } else if (sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("down")) {
      comparator = new SupplierNameComparatorDes();
    }
    if (sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("up")) {
      comparator = new DateComparatorAsc();
    } else if (sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("down")) {
      comparator = new DateComparatorDes();
    } else if (sortBy.equalsIgnoreCase("Creator") && sortDirection.equalsIgnoreCase("up")) {
      comparator = new CreatorComparatorAsc();
    } else if (sortBy.equalsIgnoreCase("Creator") && sortDirection.equalsIgnoreCase("down")) {
      comparator = new CreatorComparatorDes();
    } else if (sortBy.equalsIgnoreCase("Status") && sortDirection.equalsIgnoreCase("up")) {
      comparator = new StatusComparatorAsc();
    } else if (sortBy.equalsIgnoreCase("Status") && sortDirection.equalsIgnoreCase("down")) {
      comparator = new StatusComparatorDes();
    }

    // Sort
    Collections.sort(filterTotalSupplierList, comparator);

    modelMap.addAttribute("totalFilteredSuppliersCount", filterTotalSupplierList.size());

    // Data
    modelMap.addAttribute("suppliers",
        getFilteredSupplierPage(supplierCurrentPageNum, filterTotalSupplierList, pageSize));

    modelMap.addAttribute("supplierCurrentPageNum", new Integer(supplierCurrentPageNum));
    int supplierTotalPageNum =
        ((totalSupplierApproverCount % pageSize) == 0) ? totalSupplierApproverCount / pageSize
            : totalSupplierApproverCount / pageSize + 1;
    modelMap.addAttribute("supplierTotalPageNum", supplierTotalPageNum);

    int supplierPageFirstItemNum = (supplierCurrentPageNum - 1) * pageSize + 1;
    modelMap.addAttribute("supplierPageFirstItemNum", supplierPageFirstItemNum);

    int supplierPageLastItemNum =
        (supplierCurrentPageNum * pageSize < totalSupplierApproverCount) ? supplierPageFirstItemNum
            + pageSize - 1 : totalSupplierApproverCount;
    modelMap.addAttribute("supplierPageLastItemNum", new Integer(supplierPageLastItemNum));

    modelMap.addAttribute("supplierTotalPageNum", new Integer(supplierTotalPageNum));

    modelMap.addAttribute("isSupplierPage", Boolean.TRUE);
    modelMap.addAttribute("goToPrevSupplierPage", "goToSupplierPage("
        + (supplierCurrentPageNum - 1) + ");");
    modelMap.addAttribute("goToNextSupplierPage", "goToSupplierPage("
        + (supplierCurrentPageNum + 1) + ");");

    modelMap.addAttribute("companyId", companyCode);
  }

  private class SupplierNameComparatorAsc implements Comparator<Supplier> {
    @Override
    public int compare(Supplier p1, Supplier p2) {
      return p1.getCompanyName().compareTo(p2.getCompanyName());
    }
  }
  private class SupplierNameComparatorDes implements Comparator<Supplier> {
    @Override
    public int compare(Supplier p1, Supplier p2) {
      return p2.getCompanyName().compareTo(p1.getCompanyName());
    }
  }

  private class CreatorComparatorAsc implements Comparator<Supplier> {
    @Override
    public int compare(Supplier p1, Supplier p2) {
      return p1.getCreatedBy().compareTo(p2.getCreatedBy());
    }
  }
  private class CreatorComparatorDes implements Comparator<Supplier> {
    @Override
    public int compare(Supplier p1, Supplier p2) {
      return p2.getCreatedBy().compareTo(p1.getCreatedBy());
    }
  }

  private class StatusComparatorAsc implements Comparator<Supplier> {
    @Override
    public int compare(Supplier p1, Supplier p2) {
      Boolean b1 = new Boolean(p1.isActive());
      Boolean b2 = new Boolean(p2.isActive());
      return b1.compareTo(b2);
    }
  }
  private class StatusComparatorDes implements Comparator<Supplier> {
    @Override
    public int compare(Supplier p1, Supplier p2) {
      Boolean b1 = new Boolean(p1.isActive());
      Boolean b2 = new Boolean(p2.isActive());
      return b2.compareTo(b1);
    }
  }

  private class DateComparatorAsc implements Comparator<Supplier>{
    @Override
    public int compare(Supplier p1, Supplier p2) {
      if (p1 == null || p1.getCreatedOn() == null || p2 == null || p2.getCreatedOn() == null)
        return -1;

      return p1.getCreatedOn().compareTo(p2.getCreatedOn());
    }
}

private class DateComparatorDes implements Comparator<Supplier>{
    @Override
    public int compare(Supplier p1, Supplier p2) {
      if(p1 == null || p1.getCreatedOn() == null || p2 == null || p2.getCreatedOn() == null)
        return -1;

      return p2.getCreatedOn().compareTo(p1.getCreatedOn());
    }
}

  private void renderSupplierApproverBothSections(String companyCode, Supplier supplier,
      ModelMap modelMap, int pageSize) {
    List<User> allApprovers = getAllApprovers(companyCode);
    List<User> supplierApprovers = getSupplierApprover(allApprovers, supplier);

    renderSupplierApproversSection(1, supplierApprovers, modelMap, pageSize);
    renderApproversSection(1, allApprovers, modelMap, pageSize);
  }

  private void renderSupplierApproversSection(int pageNum, List<User> supplierApproverUsers,
      ModelMap modelMap, int pageSize) {
    int totalSupplierApproverCount = supplierApproverUsers.size();
    modelMap.addAttribute("totalSupplierApproverCount", new Integer(totalSupplierApproverCount));

    if (totalSupplierApproverCount <= (pageNum - 1) * pageSize) {
      pageNum = 1; // Reset to 1 or go back one page
    }
    modelMap.addAttribute("supplierApproverCurrentPageNum", new Integer(pageNum));


    int approverSupplierTotalPageNum =
        ((totalSupplierApproverCount % pageSize) == 0) ? totalSupplierApproverCount / pageSize
            : totalSupplierApproverCount / pageSize + 1;
    modelMap.addAttribute("supplierApproverTotalPageNum", approverSupplierTotalPageNum);

    int approverSupplierPageFirstItemNum = (pageNum - 1) * pageSize + 1;
    modelMap.addAttribute("approverSupplierPageFirstItemNum", approverSupplierPageFirstItemNum);

    int approverSupplierPageLastItemNum =
        (pageNum * pageSize < totalSupplierApproverCount) ? approverSupplierPageFirstItemNum
            + pageSize - 1 : totalSupplierApproverCount;
    modelMap.addAttribute("approverSupplierPageLastItemNum", new Integer(
        approverSupplierPageLastItemNum));

    List<User> pagedApproverSupplierList = new ArrayList<User>();
    pagedApproverSupplierList =
        supplierApproverUsers.subList(approverSupplierPageFirstItemNum - 1,
            approverSupplierPageLastItemNum);

    modelMap.addAttribute("supplierApproversForSupplier", pagedApproverSupplierList);
  }

  private void renderApproversSection(int pageNum, List<User> approverUsers, ModelMap modelMap,
      int pageSize) {
    int totalNonSupplierApproverCount = approverUsers.size();
    modelMap.addAttribute("totalNonSupplierApproverCount", new Integer(
        totalNonSupplierApproverCount));

    if (totalNonSupplierApproverCount <= (pageNum - 1) * pageSize) {
      pageNum = 1; // Reset to 1 or go back one page
    }
    modelMap.addAttribute("nonSupplierApproverCurrentPageNum", new Integer(pageNum));

    int approverTotalPageNum =
        ((totalNonSupplierApproverCount % pageSize) == 0) ? totalNonSupplierApproverCount
            / pageSize : totalNonSupplierApproverCount / pageSize + 1;
    modelMap.addAttribute("nonSupplierApproverTotalPageNum", approverTotalPageNum);

    int nonSupplierApproverPageFirstItemNum = (pageNum - 1) * pageSize + 1;
    modelMap.addAttribute("nonSupplierApproverPageFirstItemNum",
        nonSupplierApproverPageFirstItemNum);

    int nonSupplierApproverPageLastItemNum =
        (pageNum * pageSize < totalNonSupplierApproverCount) ? approverTotalPageNum + pageSize - 1
            : totalNonSupplierApproverCount;
    modelMap.addAttribute("nonSupplierApproverPageLastItemNum", new Integer(
        nonSupplierApproverPageLastItemNum));

    List<User> pagedApproverSupplierList = new ArrayList<User>();
    pagedApproverSupplierList =
        approverUsers.subList(nonSupplierApproverPageFirstItemNum - 1,
            nonSupplierApproverPageLastItemNum);

    modelMap.addAttribute("nonSupplierApprovers", pagedApproverSupplierList);
  }

  // private void addApproverRole(List<Integer> approverRoles){
  // for(Integer role: approverRoles){
  // if(role.intValue() == Role.APPROVER){
  // return;
  // }
  // }
  // approverRoles.add(Role.APPROVER);
  // }

  public void deleteSupplierIdsFromSession(HttpServletRequest request) {
    request.getSession().removeAttribute("supplierApproverIds");
  }

  public List<String> getSupplierIdsFromSession(HttpServletRequest request) {
    Set<String> supplierApproverIdSet =
        (Set<String>) request.getSession().getAttribute("supplierApproverIds");
    if (supplierApproverIdSet == null)
      supplierApproverIdSet = new HashSet<String>();
    return new ArrayList<String>(supplierApproverIdSet);
  }


  @RequestMapping(value = "/addsupplieruser", method = RequestMethod.POST)
  public String createEditSupplierUser(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "supplierid") String supplierId,
      @ModelAttribute("user") User userToAdd,
      @RequestParam(value = "uniquesupplierid") String uniqueSupplierId, @RequestParam(
          value = "message") String message, @RequestParam(value = "notify") boolean notify,
      @RequestParam(value = "password") String pass, ModelMap modelMap) {

    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      Supplier supplier = null;
      if (supplierId != null && supplierId.length() > 0) {
        supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
        if (notify) {
          userToAdd.setInvited(true);
          userToAdd.setUserStatus("Invited");
        } else {
          userToAdd.setInvited(false);
          userToAdd.setUserStatus("Invite Supplier");
        }
        createEditSupplierUser(supplier, userToAdd, user, modelMap, request, pass);
        if (notify) {
          try {
            CompanySettings companySettings =
                companyService.getCompanySettingsByUnitId(user.getUnitId());
            String emailMessage =
                message
                    + "<br/><br/>To log into the Marketplace, please use the following credentials: <br/><br/>"
                    + "URL: " + appConfig.environmentUrl + "<br/>" + "Username: "
                    + userToAdd.getUsername() + "<br/>" + "Password: " + userToAdd.getPassword()
                    + "<br/><br/>" + "Regards<br/>" + companySettings.getCompanyName();
            notificationService.sendEmail("Welcome to " + companySettings.getCompanyName()
                + " Marketplace!", emailMessage, userToAdd, user);
          } catch (Exception e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File
                                 // Templates.
          }
        }
        if (supplier.getSupplierAdmins() != null && !supplier.getSupplierAdmins().isEmpty()) {
          setSupplierUsersPaginationAttributes(supplier.getSupplierAdmins(), modelMap, user, 1);
        }
        modelMap.put("supplierId", supplierId);
      } else {
        supplier = new Supplier();
        supplier.setUniqueSupplierId(uniqueSupplierId);
        createNewTempUser(supplier, userToAdd, user, modelMap, request, message, notify);
        setNewSupplierUserPagination(uniqueSupplierId, modelMap, user, 1);
      }


    } catch (Exception exp) {
      return FAILURE;
    }

    return SUPPLIER_USERS_FRAGMENT;
  }

  @RequestMapping(value = "/suppliers/search", produces = "application/json",
      method = RequestMethod.GET)
  public @ResponseBody List<Supplier> searchSuppliers(HttpServletRequest request,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws Exception {
    List<Supplier> suppliers = new ArrayList<Supplier>();
    User user = SessionDataRetriever.getLoggedInUser(request);
    if (user == null) {
      throw new Exception("Invalid session");
    }
    try {
      suppliers = supplierService.searchSuppliers(user.getUnitId(), keyword, currentPage, pageSize);
    } catch (Exception e) {
      logger.error("Exception in Getting Suppliers against Keyword " + keyword, e);
    }

    return suppliers;
  }

  private String createNewTempUser(Supplier supplier, User userToAdd, User loggedInUser,
      ModelMap modelMap, HttpServletRequest request, String emailMessage, boolean sendEmail) {
    try {

      if (!createAuthorized(loggedInUser)) {
        return "Insufficient rights to create user.";
      }


      boolean newUser = false;

      SupplierUser supplierUser = null;
      if (userToAdd.getUserId() != null && userToAdd.getUserId().length() > 0) {
        supplierUser = userManagementService.getTempUserById(userToAdd.getUserId());
      }

      if (null == supplierUser || supplierUser.getUserId() == null) {
        supplierUser = new SupplierUser();
        supplierUser.setCreatedBy(loggedInUser.getUserId());
        supplierUser.setUnitId(loggedInUser.getUnitId());
        supplierUser.setCreatedOn(new Date());
        supplierUser.setResetPassword(true);


        supplierUser.setActive(true);
        supplierUser.setEmailMessage(emailMessage);
        supplierUser.setSendEmail(sendEmail);
        newUser = true;
      }

      if (userToAdd.getPassword() != null && userToAdd.getPassword().length() > 0) {
        supplierUser.setPassword(userToAdd.getPassword());
      } else {
        supplierUser.setPassword(RandomStringUtils.randomAlphanumeric(16));
      }

      if (supplierUser.getRoles() == null) {
        supplierUser.setRoles(new ArrayList<Role>());
      }
      UserDetail detail = new UserDetail();
      detail.setContactName(userToAdd.getFirstName() + " " + userToAdd.getLastName());
      supplierUser.setDetails(detail);
      List<Role> roles = new ArrayList<Role>();

      roles.add(Role.SUPPLIER_ADMIN);
      supplierUser.setRoles(roles);
      supplierUser.setUsername(userToAdd.getUsername());
      supplierUser.setFirstName(userToAdd.getFirstName());
      supplierUser.setLastName(userToAdd.getLastName());
      supplierUser.setUniqueSupplierId(supplier.getUniqueSupplierId());


      if (newUser) {
        supplierUser = userManagementService.addSupplierUser(supplierUser);
        List<String> supplierList = new ArrayList<String>();
        supplierList.add(supplierUser.getUserId());
        if (null != supplier) {
          if (supplier.getSupplierAdmins() == null)
            supplier.setSupplierAdmins(new ArrayList<String>());
          supplier.getSupplierAdmins().add(supplierUser.getUserId());
        }
      } else {
        userManagementService.updateSupplierUser(supplierUser);
      }

    } catch (Exception exp) {

      return FAILURE;
    }

    return SUPPLIER_USERS_FRAGMENT;
  }


  private String createEditSupplierUser(Supplier supplier, User userToAdd, User loggedInUser,
      ModelMap modelMap, HttpServletRequest request, String pass) {
    try {
      User supplierUser = null;
      if (userToAdd.getUserId() != null && userToAdd.getUserId().length() > 0) {
        supplierUser = userManagementService.getUser(userToAdd.getUserId());
      }

      if (((supplierUser != null) && !createAuthorized(loggedInUser))
          || ((supplierUser != null) && !editAuthorized(loggedInUser, userToAdd))) {

        return "Insufficient rights to create user.";
      }

      if (supplierUser == null) { // Add new user
        userToAdd.setCreatedBy(loggedInUser.getUserId());
        userToAdd.setUnitId(loggedInUser.getUnitId());
        userToAdd.setCreatedOn(new Date());
        userToAdd.setResetPassword(true);
        if (pass != null && pass.length() > 0) {
          userToAdd.setPassword(pass);
        } else {
          userToAdd.setPassword(RandomStringUtils.randomAlphanumeric(16));
        }
        userToAdd.setActive(true);
        if (userToAdd.getRoles() == null) {
          userToAdd.setRoles(new ArrayList<Role>());
        }

        UserDetail detail = new UserDetail();
        detail.setContactName(userToAdd.getFirstName() + " " + userToAdd.getLastName());
        userToAdd.setDetails(detail);


        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.SUPPLIER_ADMIN);
        userToAdd.setRoles(roles);

        ProfileGroup group = supplierService.getSupplierProfileGroup(supplier);
        if (group != null) {
          userToAdd.setAssignedProfiles(group.getAssociatedProfiles());
        }
        //set the supplier user default preferences as per Master Admin.
        setSupplierUserDefaultsIfMissing(userToAdd,loggedInUser);

        supplierUser = userManagementService.addUpdateUser(userToAdd);
        userToAdd.setUserId(supplierUser.getUserId());
        // notificationService.sendNewUserNotification(userToAdd,loggedInUser);

        if (null != supplier) {
          if (supplier.getSupplierAdmins() == null)
            supplier.setSupplierAdmins(new ArrayList<String>());
          supplier.getSupplierAdmins().add(supplierUser.getUserId());
          supplierService.editSupplier(supplier);
        }
      } else { // Update existing user
        supplierUser.setUsername(userToAdd.getUsername());
        supplierUser.setFirstName(userToAdd.getFirstName());
        supplierUser.setLastName(userToAdd.getLastName());
        supplierUser.setLanguage(userToAdd.getLanguage());
        supplierUser.setDecimalNotation(userToAdd.getDecimalNotation());
        supplierUser.setDateFormat(userToAdd.getDateFormat());
        supplierUser.setTimeZone(userToAdd.getTimeZone());
        supplierUser.setAssignedProfiles(userToAdd.getAssignedProfiles());
        if (!supplierUser.isInvited()) {
          supplierUser.setUserStatus(userToAdd.getUserStatus());
          supplierUser.setInvited(userToAdd.isInvited());
        }
        if (pass != null && pass.length() > 0) {
          supplierUser.setPassword(pass);
        }
        if (supplierUser.getRoles() == null) {
          supplierUser.setRoles(new ArrayList<Role>());
        }

        addSupplierRole(supplierUser.getRoles());

        ProfileGroup group = supplierService.getSupplierProfileGroup(supplier);
        if (group != null) {
          userToAdd.setAssignedProfiles(group.getAssociatedProfiles());
        }

        // Update approver
        supplierUser = userManagementService.addUpdateUser(supplierUser);
      }

    } catch (Exception exp) {
      logger.error(exp.getMessage(), exp);
      return FAILURE;
    }

    return SUPPLIER_USERS_FRAGMENT;

    // (catalogId != null)? CATALOG_APPROVER_BOTH_SECTION : SUPPLIER_APPROVER_BOTH_SECTION;
  }

  public void setSupplierUserDefaultsIfMissing(User user,
                                               User adminUser) {
    user.setRowsPerPage(adminUser.getRowsPerPage() > 0 ? adminUser.getRowsPerPage()
                                                       : Integer.parseInt(appConfig.rowsPerPage));
    user
        .setDateFormat(
            StringUtils.isNotBlank(adminUser.getDateFormat()) ? adminUser.getDateFormat()
                                                              : appConfig.dateFormat);
    user.setDecimalNotation(
        StringUtils.isNotBlank(adminUser.getDecimalNotation()) ? adminUser.getDecimalNotation()
                                                               : appConfig.decimalNotation);

    user.setTimeZone(
        StringUtils.isNotBlank(adminUser.getTimeZone()) ? adminUser.getTimeZone()
                                                        : appConfig.timeZone);
    user.setLanguage(
        StringUtils.isNotBlank(adminUser.getLanguage()) ? adminUser.getLanguage()
                                                        : appConfig.language);
  }

  private void addSupplierRole(List<Role> roles) {
    for (Role role : roles) {
      if (role == Role.SUPPLIER_ADMIN) {
        return;
      }
    }
    roles.add(Role.SUPPLIER_ADMIN);
  }

  private boolean editAuthorized(User user, User userToAdd) {
    if (user.getRoles().contains(Role.MASTER_ADMINISTRATOR)
        || ((user.getRoles().contains(Role.ADMINISTRATOR) && !user.getUsername().equals(
            userToAdd.getUsername())))) {
      return true;
    }
    return false;
  }

  private boolean createAuthorized(User user) {
    if (user.getRoles().contains(Role.ADMINISTRATOR)
        || user.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {
      return true;
    }
    return false;
  }


  private UserFilter getFilteredUsers(String searchText, User loggedInUser, Integer pageNo,
      List<String> includedUsers) {

    UserFilter userFilter = new UserFilter();

    List<Role> roles = new ArrayList<Role>();
    roles.add(Role.SUPPLIER_ADMIN);
    userFilter.setRoles(roles);



    if (pageNo == null || pageNo <= 0) {
      pageNo = 1;
    }
    userFilter.setPageNumber(pageNo);
    userFilter.setPageSize(loggedInUser.getRowsPerPage());
    userFilter.setIncludedUserIds(includedUsers);


    return userFilter;
  }



  private void addUserToSession(HttpServletRequest request, User user) {
    Object usersObj = request.getSession().getAttribute("supplierUsers");
    List<User> users = usersObj == null ? new ArrayList<User>() : (List<User>) usersObj;
    users.add(user);
    request.getSession().setAttribute("supplierUsers", users);
  }


  private List<User> getUsersFromSession(HttpServletRequest request) {
    Object usersObj = request.getSession().getAttribute("supplierUsers");
    List<User> users = usersObj == null ? new ArrayList<User>() : (List<User>) usersObj;
    return users;
  }

  public Set<String> getSystemIdsAndNames(String unitId) {
    List<SystemDefinition> sysDefs = systemDefinitionService.getSystemDefinitions(unitId);
    Set<String> sysDefIdAndNames = new HashSet<String>(sysDefs.size() * 2);
    for (SystemDefinition systemDefinition : sysDefs) {
      sysDefIdAndNames.add(systemDefinition.getUniqueSystemId().toLowerCase());
      sysDefIdAndNames.add(systemDefinition.getSystemName().toLowerCase());
    }
    return sysDefIdAndNames;
  }

  private String paginationContentViewsExistingSupplier(String supplierId, List<Integer> profileIds,
      ModelMap modelMap, User user, int page) {
    try {

      if (StringUtils.isNotBlank(supplierId)) {
        Supplier supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
        List<Profile> allProfiles = new ArrayList<Profile>();
        allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());
        Set<Integer> assignedProfileIds = new HashSet<Integer>();
        List<Profile> profiles = new ArrayList<Profile>();
        if (profileIds != null && profileIds.size() > 0) {
          assignedProfileIds.addAll(profileIds);
        }
        if (supplier.getAssignedContentViews() != null
            && supplier.getAssignedContentViews().size() > 0) {
          assignedProfileIds.addAll(supplier.getAssignedContentViews());
        }

        for (Profile profile : allProfiles) {
          if (!assignedProfileIds.contains(profile.getProfileId())) {
            profiles.add(profile);
          }
        }
        PagedListHolder<Profile> profileListHolder = new PagedListHolder<Profile>(profiles);
        profileListHolder.setPage(page);
        int pageSize = user.getRowsPerPage() > 0 ? user.getRowsPerPage() : 8;
        profileListHolder.setPageSize(pageSize);

        modelMap.addAttribute("profilesForCompany", (Object) profileListHolder);
        modelMap.addAttribute("supplierId", supplierId);
        int nextPage =
            page >= profileListHolder.getPageCount() ? profileListHolder.getPageCount() : page + 1;
        int prevPage = page <= 0 ? 0 : page - 1;
        modelMap.addAttribute("nextUserProfilesPage", (Object) nextPage);
        modelMap.addAttribute("prevUserProfilesPage", (Object) prevPage);
      }
    } catch (Exception e) {
      logger.error("Error retrieving Content View", e);
      return FAILURE;
    }

    return SUPPLIER_CONTENT_VIEW;
  }

  private String paginationContentViewsNewSupplier(List<Integer> profileIds, ModelMap modelMap, User user,
      int page) {
    try {
      List<Profile> allProfiles = new ArrayList<Profile>();
      allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());
      Set<Integer> assignedProfileIds = new HashSet<Integer>();
      List<Profile> profiles = new ArrayList<Profile>();
      if (profileIds != null && profileIds.size() > 0) {
        assignedProfileIds.addAll(profileIds);
      }
      for (Profile profile : allProfiles) {
        if (!assignedProfileIds.contains(profile.getProfileId())) {
          profiles.add(profile);
        }
      }
      PagedListHolder<Profile> profileListHolder = new PagedListHolder<Profile>(profiles);
      profileListHolder.setPage(page);
      int pageSize = user.getRowsPerPage() > 0 ? user.getRowsPerPage() : 8;
      profileListHolder.setPageSize(pageSize);

      modelMap.addAttribute("profilesForCompany", (Object) profileListHolder);
      int nextPage =
          page >= profileListHolder.getPageCount() ? profileListHolder.getPageCount() : page + 1;
      int prevPage = page <= 0 ? 0 : page - 1;
      modelMap.addAttribute("nextUserProfilesPage", (Object) nextPage);
      modelMap.addAttribute("prevUserProfilesPage", (Object) prevPage);

    } catch (Exception e) {
      logger.error("Error retrieving Content View", e);
      return FAILURE;
    }

    return SUPPLIER_CONTENT_VIEW;
  }


  private List<Profile> convertToProfileList(List<ProfileProxy> profileProxyList) {
    List<Profile> profiles = new ArrayList<Profile>();
    Profile profile;
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

  public void setAppConfig(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

}
