package com.vroozi.customerui.profile.controller;

import java.util.*;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.common.SessionDataRetriever;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.services.CatalogService;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.user.services.user.model.User;

import static com.vroozi.customerui.util.Consts.*;

/**
 * 
 * @author mhabib
 * 
 */
@Controller
public class ProfileUIController {

	private final Logger LOGGER = LoggerFactory.getLogger(ProfileUIController.class);

	private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

  //  private static final int PAGE_SIZE = 7;

	@Autowired
	ProfileService profileService;

    @Autowired
    CatalogService catalogService;

	@RequestMapping("/profiles")
	public String profiles(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

        try {
            //List<Profile> profileList = null;//profileService.getProfilesPageNationDataByUnitId(user.getUnitId(), 1);
            //modelMap.addAttribute("profiles", profileList);

            int pageNum = 1;  // First Page

            setProfilePaginationAttributes(user.getUnitId(), pageNum, ViewFilter.NONE, "Name", "up", "", modelMap,user);
            
		} catch (Exception e) {
			LOGGER.error("Error retrieving profiles");
			return FAILURE;
		}

        return "forward:/profileGroups";//PROFILES_PAGE;
	}

    @RequestMapping(value="/goToPageProfiles", method=RequestMethod.POST)
    public String getProfilePage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            //List<Profile> profileList = null;//profileService.getProfilesPageNationDataByUnitId(user.getUnitId(), pageNum);

            setProfilePaginationAttributes(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user);

        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILES_PAGE_FRAMGMENT;
    }

    @RequestMapping(value="/filterProfiles", method=RequestMethod.POST)
    public String filterProfiles(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value="viewFilter") String viewFilter,
        @RequestParam(value="pageNum", required=true ) Integer pageNum,
        @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap){
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            //List<Profile> profileList = null;// profileService.getProfilesPageNationDataByUnitId(user.getUnitId(), 1);


            setProfilePaginationAttributes(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), "Name", "up", searchWithin, modelMap,user);

        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILES_PAGE_FRAMGMENT;
    }

    private List<Profile> filterProfiles(List<Profile> profileList, ViewFilter viewFilter){
        List<Profile> filteredProfileList = new ArrayList<Profile>();

        if(viewFilter.equals(ViewFilter.INACTIVE)){
            for(Profile profile: profileList){
                if(!profile.isActive()){
                    filteredProfileList.add(profile);
                }
            }
        } else  if(viewFilter.equals(ViewFilter.ACTIVE)){
            for(Profile profile: profileList){
                if(profile.isActive()){
                    filteredProfileList.add(profile);
                }
            }
        }else if(viewFilter.equals(ViewFilter.NONE)){
            filteredProfileList = profileList;
        }

        return filteredProfileList;
    }

    private List<Profile> filterWithinProfiles(String searchWithin, List<Profile> filterTotalProfileList){
        if(StringUtils.isEmpty(searchWithin)) return filterTotalProfileList;

        searchWithin = searchWithin.toLowerCase();
        List<Profile> filteredWithinProfiles = new ArrayList<Profile>();
        for(Profile profile: filterTotalProfileList){
            if(profile.getProfileName().toLowerCase().contains(searchWithin))
                filteredWithinProfiles.add(profile);
        }
        return filteredWithinProfiles;
    }

    private List<CatalogSummary> filterWithinCatalogs(String searchWithin, List<CatalogSummary> filterTotalProfileList){
        if(StringUtils.isEmpty(searchWithin)) return filterTotalProfileList;

        searchWithin = searchWithin.toLowerCase();
        List<CatalogSummary> filteredWithinCatalogs = new ArrayList<CatalogSummary>();
        for(CatalogSummary catalogSummary: filterTotalProfileList){
            if(catalogSummary.getCatalogNameNoSpace().toLowerCase().contains(searchWithin))
                filteredWithinCatalogs.add(catalogSummary);
        }
        return filteredWithinCatalogs;
    }

    private List<Profile> getFilteredProfilePage(int pageNum, List<Profile>  filterTotalProfileList, int pageSize){
        List<Profile> filteredProfilePage = new ArrayList<Profile>();

        int offset =  pageSize*(pageNum-1) ;

        int maxCount = (filterTotalProfileList.size() > pageSize + offset)? pageSize + offset: filterTotalProfileList.size() ;
        for(int i = offset; i <  maxCount; ++i ){
            filteredProfilePage.add(filterTotalProfileList.get(i));
        }

        return filteredProfilePage;
    }

    @RequestMapping(value = "/activeProfiles", method = RequestMethod.POST)
    public String activeProfiles(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
            @RequestParam(value="active") boolean active,
            @RequestParam(value="pageNum") int pageNum,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam( value="profileIds") Integer[] profileIds,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            if(profileIds != null && profileIds.length > 0){
                profileService.updateProfileStatus(active, Arrays.asList(profileIds));
            }
            //List<Profile> profileList = null;// profileService.getProfilesPageNationDataByUnitId(user.getUnitId(), pageNum);

            setProfilePaginationAttributes(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user);

        } catch (Exception exp) {
            LOGGER.error("Error deleting profileIds (" + profileIds + ") ", exp);
            return FAILURE;
        }

        return PROFILES_PAGE_FRAMGMENT;
    }

    @RequestMapping(value = "/deleteProfiles", method = RequestMethod.POST)
    public String deleteProfiles(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
            @RequestParam(value="pageNum") int pageNum,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam( value="profileIds") Integer[] profileIds,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            if(profileIds != null && profileIds.length > 0){
                profileService.deleteProfiles(user.getUnitId(), Arrays.asList(profileIds));
            }
            //List<Profile> profileList = null;// profileService.getProfilesPageNationDataByUnitId(user.getUnitId(), pageNum);

            setProfilePaginationAttributes(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user);
        } catch (Exception exp) {
            LOGGER.error("Error deleting profileIds (" + profileIds + ") ", exp);
            return FAILURE;
        }

        return PROFILES_PAGE_FRAMGMENT;
    }

    private ProfileProxy getProfile(HttpServletRequest request) throws Exception {
        String profileId = request.getParameter("profileId");
        if(StringUtils.isEmpty(profileId)) throw new Exception("'profileId' is missing!");

        List<Integer> profileIds = new ArrayList<Integer>();
        profileIds.add(Integer.parseInt(profileId));
        List<ProfileProxy> profiles = profileService.getProfilesByIds(profileIds);
        if(profiles == null || profiles.size() == 0){
            throw new Exception("Invalid 'profileId'");
        }

        ProfileProxy profile = profiles.get(0);

        return profile;
    }

    @RequestMapping("/createProfile")
    public String createProfiles(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            modelMap.addAttribute("pageName", "Create Content View");
            modelMap.addAttribute("profileProductActive", "Yes");

            request.getSession().setAttribute("associatedCatalogs", new ArrayList<CatalogSummary>());
            setCatalogProfilePagination(1, modelMap, new ArrayList<CatalogSummary>(), getProfileId(request), "Name", "up", user.getRowsPerPage());

            List<CatalogSummary> catalogs = catalogService.getAllCatalogs(user.getUnitId());
            setCatalogPagination(1, modelMap, catalogs, getProfileId(request), "Name", "up", "", user.getRowsPerPage());
            return CREATE_PROFILE_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving profiles");
            return FAILURE;
        }
    }

    @RequestMapping("/editProfile")
    public String editProfiles(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

                ProfileProxy profile = getProfile(request);

            List<CatalogSummary> associatedCatalogs = catalogService.getCatalogsByIds(profile.getAssociatedCatalogs());

            setCatalogProfilePagination(1, modelMap, associatedCatalogs, getProfileId(request), "Name", "up",user.getRowsPerPage());

            List<CatalogSummary> notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
            subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);

            modelMap.addAttribute("profileName", profile.getProfileName());
            modelMap.addAttribute("profileDesc", profile.getProfileDesc());
            modelMap.addAttribute("profileProductRating", (profile.getProductRating()==1)?"checked":"");
            modelMap.addAttribute("profileProductActive", (profile.isActive())?"Yes":"No");

            modelMap.addAttribute("pageName", "Edit Content View");

            setCatalogPagination(1, modelMap, notAssociatedCatalogs, getProfileId(request), "Name", "up", "",user.getRowsPerPage());

            return CREATE_PROFILE_PAGE;
        } catch (Exception e) {
            String errorMsg = "Error retrieving profile: " + e.getMessage();
            LOGGER.error(errorMsg);
            request.setAttribute("errorMsg", errorMsg);
            return FAILURE;
        }
    }

    private boolean isEditMode(HttpServletRequest request){
        String profileId = request.getParameter("profileId");
        return StringUtils.isNotEmpty(profileId);
    }

    private String getProfileId(HttpServletRequest request){
        if(request.getParameter("profileId") != null){
            return request.getParameter("profileId");
        }
        return "";
    }

    private List<CatalogSummary> getSelectedCatalogs(List<CatalogSummary> catalogs, String[] associatedCatalogIds){
        List<CatalogSummary> selectedCatalogs = new ArrayList<CatalogSummary>();
        for(String catalogId: associatedCatalogIds) {
            for(CatalogSummary catalog: catalogs){
                if(catalog.getCatalogId().equals(catalogId)){
                    selectedCatalogs.add(catalog);
                    catalogs.remove(catalog);
                    break;
                }
            }
        }
        return selectedCatalogs;
    }


    private void subtractCatalogList(List<CatalogSummary> src, List<CatalogSummary> dest){
        for(CatalogSummary catalogSummary1: dest){
            for(CatalogSummary catalogSummary2: src){
                if(catalogSummary1.getCatalogId().equals(catalogSummary2.getCatalogId())){
                    src.remove(catalogSummary2);
                    break;
                }
            }
        }
    }

    @RequestMapping(value = "/searchWithinCatalog", method = RequestMethod.POST)
    public String searchWithinCatalog(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<CatalogSummary> associatedCatalogs = null;
            if(!isEditMode(request)){
                associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
            }else{
                ProfileProxy profile = getProfile(request);
                associatedCatalogs = catalogService.getCatalogsByIds(profile.getAssociatedCatalogs());
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
            }

            List<CatalogSummary> notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
            subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);

            setCatalogPagination(1, modelMap, notAssociatedCatalogs, getProfileId(request), "Name", "up", searchWithin,user.getRowsPerPage());

            return PROFILE_CATALOG_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in /searchWithinCatalog" + e.getMessage());
            return FAILURE;
        }
    }

    @RequestMapping(value = "/sortCatalogs", method = RequestMethod.POST)
    public String sortCatalogs(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<CatalogSummary> associatedCatalogs = null;
            if(!isEditMode(request)){
                associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
            }else{
                ProfileProxy profile = getProfile(request);
                associatedCatalogs = catalogService.getCatalogsByIds(profile.getAssociatedCatalogs());
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
            }

            List<CatalogSummary> notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
            subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);

            setCatalogPagination(1, modelMap, notAssociatedCatalogs, getProfileId(request), sortBy, sortDirection, searchWithin,user.getRowsPerPage());

            return PROFILE_CATALOG_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in /searchWithinCatalog" + e.getMessage());
            return FAILURE;
        }
    }

    @RequestMapping(value = "/sortProfileCatalogs", method = RequestMethod.POST)
    public String sortProfileCatalogs(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value="sortBy", required=true ) String sortBy,
           @RequestParam(value="sortDirection", required=true ) String sortDirection, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<CatalogSummary> associatedCatalogs = null;
            if(!isEditMode(request)){
                associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
            }else{
                ProfileProxy profile = getProfile(request);
                associatedCatalogs = catalogService.getCatalogsByIds(profile.getAssociatedCatalogs());
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
            }

            setCatalogProfilePagination(1, modelMap, associatedCatalogs, getProfileId(request), sortBy, sortDirection,user.getRowsPerPage());

            return PROFILE_ASS_CATALOG_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in /searchWithinCatalog" + e.getMessage());
            return FAILURE;
        }
    }

    @RequestMapping(value = "/associateCatalogToProfile", method = RequestMethod.POST)
    public String associateCatalog(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            List<CatalogSummary> associatedCatalogs =null;
            List<CatalogSummary> notAssociatedCatalogs = null;
            String[] associatedCatalogIds = request.getParameterValues("associatedCatalogIds");
            if((associatedCatalogIds != null) && (associatedCatalogIds.length > 0)){
                if(!isEditMode(request)){
                    associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
                    if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
                    notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                    List<CatalogSummary> newlySelectedCatalogs = getSelectedCatalogs(notAssociatedCatalogs, associatedCatalogIds);
                    associatedCatalogs.addAll(newlySelectedCatalogs);
                    request.getSession().setAttribute("associatedCatalogs", associatedCatalogs);
                    subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);
                }else{
                    String profileId = request.getParameter("profileId");
                    if(StringUtils.isEmpty(profileId)) throw new Exception("'profileId' is missing!");

                    List<Integer> profileIds = new ArrayList<Integer>(); profileIds.add(Integer.parseInt(profileId));
                    for(String catalogId:associatedCatalogIds){
                        profileService.addCatalogProfiles(catalogId, profileIds);
                    }

                    // Readback after update
                    ProfileProxy profile = getProfile(request);

                    associatedCatalogs = catalogService.getCatalogsByIds(profile.getAssociatedCatalogs());
                    notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                    subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);
                }
                setCatalogPagination(1, modelMap, notAssociatedCatalogs, getProfileId(request), "Name", "up", "",user.getRowsPerPage());
                setCatalogProfilePagination(1, modelMap, associatedCatalogs, getProfileId(request), "Name", "up",user.getRowsPerPage());
            }
            return PROFILE_CATALOG_BOTH_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in associateCatalogToProfile" + e.getMessage());
            return FAILURE;
        }
    }

    @RequestMapping(value = "/disAssociateCatalogFromProfile", method = RequestMethod.POST)
    public String disAssociateCatalog(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            List<CatalogSummary> notAssociatedCatalogs = null;
            List<CatalogSummary> associatedCatalogs = null;

            String[] disAssociatedCatalogIds = request.getParameterValues("associatedCatalogIds");
            if((disAssociatedCatalogIds != null) && (disAssociatedCatalogIds.length > 0)){
                if(!isEditMode(request)){
                    associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
                    if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
                    getSelectedCatalogs(associatedCatalogs, disAssociatedCatalogIds);
                    notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                    subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);
                    request.getSession().setAttribute("associatedCatalogs", associatedCatalogs);
                }else{
                    String profileId = request.getParameter("profileId");
                    if(StringUtils.isEmpty(profileId)) throw new Exception("'profileId' is missing!");

                    List<Integer> profileIds = new ArrayList<Integer>(); profileIds.add(Integer.parseInt(profileId));
                    for(String catalogId:disAssociatedCatalogIds){
                        profileService.deleteCatalogProfiles(catalogId, profileIds);
                    }

                    // Readback after update
                    ProfileProxy profile = getProfile(request);

                    associatedCatalogs = catalogService.getCatalogsByIds(profile.getAssociatedCatalogs());
                    notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                    subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);
                }

                setCatalogPagination(1, modelMap, notAssociatedCatalogs, getProfileId(request), "Name", "up", "",user.getRowsPerPage());
                setCatalogProfilePagination(1, modelMap, associatedCatalogs, getProfileId(request), "Name", "up",user.getRowsPerPage());
            }
            return PROFILE_CATALOG_BOTH_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error retrieving profiles");
            return FAILURE;
        }
    }

    private List<String> getAssociatedCatalogIds(List<CatalogSummary> associatedCatalogs){
        List<String> associatedCatalogIds = new ArrayList<String>();
        for(CatalogSummary catalog: associatedCatalogs) {
            associatedCatalogIds.add(catalog.getCatalogId());
        }
        return associatedCatalogIds;
    }

    @RequestMapping(value = "/createNewProfile", method = RequestMethod.POST)
    public String createNewProfile(HttpServletRequest request,
              @ModelAttribute(value = "profile") Profile profile, BindingResult bindingResult, ModelMap modelMap) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            if(!isEditMode(request)){
                ProfileProxy proxy = new ProfileProxy();
                proxy.setProfileName(profile.getProfileName());
                proxy.setProfileDesc(profile.getProfileDesc());
                proxy.setActive(profile.getActiveStr().equals("Yes"));
                int productRating = profile.getRating()!=null && profile.getRating().equals("on")?1:0;
                proxy.setProductRating(productRating);
                List<CatalogSummary> associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
                proxy.setAssociatedCatalogs(getAssociatedCatalogIds(associatedCatalogs));
                proxy.setCreatedOnView("Profiles");
                proxy.setCreatedBy(user.getUsername());
                proxy.setCreatedByName(user.getFullName());
                proxy.setUnitId(user.getUnitId());
                proxy.setCreatedOn(new Date());
                profileService.addProfile(proxy);

                associatedCatalogs = new ArrayList<CatalogSummary>();
                request.getSession().setAttribute("associatedCatalogs", associatedCatalogs);
                List<CatalogSummary> notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                modelMap.addAttribute("associatedCatalogsCount", new Integer(0));
                modelMap.addAttribute("notAssociatedCatalogsCount", notAssociatedCatalogs.size());
                modelMap.addAttribute("catalogs", notAssociatedCatalogs);
                modelMap.addAttribute("associatedCatalogs", associatedCatalogs);
            }else{
                ProfileProxy proxy = getProfile(request);
                proxy.setProfileName(profile.getProfileName());
                proxy.setProfileDesc(profile.getProfileDesc());
                int productRating = profile.getRating()!=null && profile.getRating().equals("on")?1:0;
                proxy.setProductRating(productRating);
                proxy.setActive(profile.getActiveStr().equals("Yes"));

                profileService.updateProfile(proxy);

                // Readback
                proxy = getProfile(request);

                List<CatalogSummary> associatedCatalogs = catalogService.getCatalogsByIds(proxy.getAssociatedCatalogs());
                setCatalogProfilePagination(1, modelMap, associatedCatalogs, getProfileId(request), "Name", "up",user.getRowsPerPage());
                List<CatalogSummary> notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);

                modelMap.addAttribute("profileName", proxy.getProfileName());
                modelMap.addAttribute("profileDesc", proxy.getProfileDesc());
                modelMap.addAttribute("profileProductRating", (proxy.getProductRating()==1)?"checked":"");
                modelMap.addAttribute("profileProductActive", (proxy.isActive())?"Yes":"No");
                setCatalogPagination(1, modelMap, notAssociatedCatalogs, getProfileId(request), "Name", "up", "",user.getRowsPerPage());
            }

            return PROFILE_CATALOG_BOTH_FRAGMENT;
        } catch (Exception exp) {
            LOGGER.error("Error: Failed creating profile ", exp);
            return "Failure" + exp.getMessage();
        }
    }

    @RequestMapping(value="/goToProfileCatalogPage", method=RequestMethod.POST)
    public String goToProfileCatalogPage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,  ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<CatalogSummary> associatedCatalogs = null;
            if(!isEditMode(request)){
                associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
            }else{
                ProfileProxy proxy = getProfile(request);
                associatedCatalogs = catalogService.getCatalogsByIds(proxy.getAssociatedCatalogs());
            }

            setCatalogProfilePagination(pageNum, modelMap, associatedCatalogs, getProfileId(request), "Name", "up",user.getRowsPerPage());

        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILE_ASS_CATALOG_FRAGMENT;
    }

    @RequestMapping(value="/goToCatalogPage", method=RequestMethod.POST)
    public String goToCatalogPage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin,
            @RequestParam(value="profileId", required=true ) String profileId, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<CatalogSummary> associatedCatalogs = null;
            List<CatalogSummary> notAssociatedCatalogs = null;
            if(!isEditMode(request)){
                associatedCatalogs = (List<CatalogSummary>)request.getSession().getAttribute("associatedCatalogs");
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
                notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);
            }else {
                ProfileProxy proxy = getProfile(request);
                associatedCatalogs = catalogService.getCatalogsByIds(proxy.getAssociatedCatalogs());
                if(associatedCatalogs == null)associatedCatalogs = new ArrayList<CatalogSummary>();
                notAssociatedCatalogs = catalogService.getAllCatalogs(user.getUnitId());
                subtractCatalogList(notAssociatedCatalogs, associatedCatalogs);
            }

            setCatalogPagination(pageNum, modelMap, notAssociatedCatalogs, getProfileId(request), sortBy, sortDirection, searchWithin,user.getRowsPerPage());

        }catch (Exception exp){
            LOGGER.error("Error going to catalog pageNum(" + pageNum  + ")" );
        }

        return PROFILE_CATALOG_FRAGMENT;
    }

    private void setCatalogProfilePagination(int PageNum, ModelMap modelMap, List<CatalogSummary> profileCatalogList, String profileId,
                String sortBy,  String sortDirection, int pageSize) throws  Exception{

        try{
            modelMap.addAttribute("profileId", profileId);

            if(StringUtils.isEmpty(sortBy)){
                sortBy = "Name";
            }
            modelMap.addAttribute("sortBy", sortBy);

            if(StringUtils.isEmpty(sortDirection)){
                sortDirection= "up";
            }
            modelMap.addAttribute("sortDirection", sortDirection);

            Comparator<CatalogSummary> comparator = null;
            if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("up")){
                comparator = new CatalogNameComparatorAsc();
            }else if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("down")){
                comparator = new CatalogNameComparatorDes();
            }

            // Sort
            Collections.sort(profileCatalogList, comparator);

            // Pagination Info
            int totalProfileCatalogCount = profileCatalogList.size();
            modelMap.addAttribute("totalProfileCatalogCount", new Integer(totalProfileCatalogCount));

            if(totalProfileCatalogCount <= (PageNum-1)*pageSize){
                PageNum = 1; // Reset to 1 or go back one pageSize
            }
            modelMap.addAttribute("profileCatalogCurrentPageNum", new Integer(PageNum));


            int profileCatalogTotalPageNum = ( (totalProfileCatalogCount % pageSize) == 0)? totalProfileCatalogCount/pageSize : totalProfileCatalogCount/pageSize + 1;
            modelMap.addAttribute("profileCatalogTotalPageNum", profileCatalogTotalPageNum);

            int profileCatalogPageFirstItemNum = (PageNum-1)*pageSize + 1;
            modelMap.addAttribute("profileCatalogPageFirstItemNum", profileCatalogPageFirstItemNum);

            int profileCatalogPageLastItemNum = (PageNum*pageSize < totalProfileCatalogCount)? profileCatalogPageFirstItemNum + pageSize-1 : totalProfileCatalogCount;
            modelMap.addAttribute("profileCatalogPageLastItemNum", new Integer(profileCatalogPageLastItemNum));

            List<CatalogSummary> pagedProfileCatalogList = new ArrayList<CatalogSummary>();
            pagedProfileCatalogList =  profileCatalogList.subList(profileCatalogPageFirstItemNum-1, profileCatalogPageLastItemNum);

            // Data
            modelMap.addAttribute("associatedCatalogs", pagedProfileCatalogList);

        }catch (Exception exp){
            LOGGER.error("Error during Catalog pagination", exp);
        }
    }

    private List<CatalogSummary> getActiveCatalogs(List<CatalogSummary> allCatalogList){
        for(Iterator iter = allCatalogList.iterator(); iter.hasNext();){
            CatalogSummary catalogSummary = (CatalogSummary)iter.next();
            if(!catalogSummary.getActive()) iter.remove();
        }
        return allCatalogList;
    }

    private void setCatalogPagination(int PageNum, ModelMap modelMap, List<CatalogSummary> allCatalogList, String profileId,
            String sortBy,  String sortDirection, String searchWithin, int pageSize) throws  Exception{

        try{
            List<CatalogSummary> catalogList = getActiveCatalogs(allCatalogList);

            // Search Within filter
            if(!StringUtils.isEmpty(searchWithin) && !searchWithin.equalsIgnoreCase("Search within")){
                catalogList = filterWithinCatalogs(searchWithin, catalogList);
                modelMap.addAttribute("searchWithin", searchWithin);
            }else{
                modelMap.addAttribute("searchWithin", "");
            }

            if(StringUtils.isEmpty(sortBy)){
                sortBy = "Name";
            }
            modelMap.addAttribute("sortBy", sortBy);

            if(StringUtils.isEmpty(sortDirection)){
                sortDirection= "up";
            }
            modelMap.addAttribute("sortDirection", sortDirection);

            Comparator<CatalogSummary> comparator = null;
            if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("up")){
                comparator = new CatalogNameComparatorAsc();
            }else if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("down")){
                comparator = new CatalogNameComparatorDes();
            }

            // Sort
            Collections.sort(catalogList, comparator);

            // Pagination Info
            int totalCatalogCount = catalogList.size();
            modelMap.addAttribute("notAssociatedCatalogsCount", new Integer(totalCatalogCount));

            //if(totalCatalogCount == 0) return;

            if(totalCatalogCount <= (PageNum-1)*pageSize){
                PageNum = 1; // Reset to 1 or go back one page
            }
            modelMap.addAttribute("catalogCurrentPageNum", new Integer(PageNum));


            int catalogTotalPageNum = ( (totalCatalogCount % pageSize) == 0)? totalCatalogCount/pageSize : totalCatalogCount/pageSize + 1;
            modelMap.addAttribute("catalogTotalPageNum", catalogTotalPageNum);

            int catalogPageFirstItemNum = (PageNum-1)*pageSize + 1;
            modelMap.addAttribute("catalogPageFirstItemNum", catalogPageFirstItemNum);

            int catalogPageLastItemNum = (PageNum*pageSize < totalCatalogCount)? catalogPageFirstItemNum + pageSize-1 : totalCatalogCount;
            modelMap.addAttribute("catalogPageLastItemNum", new Integer(catalogPageLastItemNum));

            List<CatalogSummary> pagedCatalogList = new ArrayList<CatalogSummary>();
            if(!catalogList.isEmpty()) {
                pagedCatalogList = catalogList.subList(catalogPageFirstItemNum-1, catalogPageLastItemNum);
            }

            // Data
            modelMap.addAttribute("catalogs", pagedCatalogList);

            modelMap.addAttribute("profileId", profileId);

        }catch (Exception exp){
            LOGGER.error("Error during Catalog pagination", exp);
        }
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
    
    private void setProfilePaginationAttributes(String companyCode, int profileCurrentPageNum, ViewFilter profileViewFiler,
            String sortBy,  String sortDirection, String searchWithin, ModelMap modelMap , User user) throws  Exception{

    	int pageSize = user.getRowsPerPage();
    	List<Role> userRoles = user.getRoles();
    	boolean isMaster = false;
    	for (Role role : userRoles) {
			if(role == role.MASTER_ADMINISTRATOR){
				isMaster = true;
				break;
			}
		}
    	
    	List<Profile> totalProfileList = new ArrayList<Profile>();
    	if(isMaster){
    		totalProfileList = profileService.getProfilesByUnitId(user.getUnitId());
    	}else{
    		if(user.getAssignedProfiles()!=null && !user.getAssignedProfiles().isEmpty()){
	    		List<ProfileProxy> allProfileProxies = profileService.getProfilesByIds(user.getAssignedProfiles());
	    		totalProfileList = convertToProfileList(allProfileProxies);
    		}
    	}    	
    	
//    	List<Profile> totalProfileList = profileService.getProfilesByUnitId(companyCode);
        int numOfActiveProfile = 0;
        for(Profile profile:totalProfileList){
            if(profile.isActive()){
                numOfActiveProfile += 1;
            }
        }
        // These are for upper portion of the page
        modelMap.addAttribute("totalProfilesCount", new Integer(totalProfileList.size()));
        modelMap.addAttribute("numOfActiveProfile", new Integer(numOfActiveProfile));

        // Pagination Info
        List<Profile>  filterTotalProfileList = filterProfiles(totalProfileList, profileViewFiler);

        // Search Within filter
        if(!StringUtils.isEmpty(searchWithin)){
            filterTotalProfileList = filterWithinProfiles(searchWithin, filterTotalProfileList);
            modelMap.addAttribute("searchWithin", searchWithin);
        }else{
            modelMap.addAttribute("searchWithin", "");
        }

        int totalFilteredProfilesCount = filterTotalProfileList.size();
        modelMap.addAttribute("totalFilteredProfilesCount", new Integer(totalFilteredProfilesCount));

        if(filterTotalProfileList.size() <= (profileCurrentPageNum-1)*pageSize){
            profileCurrentPageNum = 1; // Reset to 1 or go back one page
        }

        if(StringUtils.isEmpty(sortBy)){
            sortBy = "Name";
        }
        modelMap.addAttribute("sortBy", sortBy);

        if(StringUtils.isEmpty(sortDirection)){
            sortDirection= "up";
        }
        modelMap.addAttribute("sortDirection", sortDirection);

        Comparator<Profile> comparator = null;
        if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("up")){
            comparator = new ProfileNameComparatorAsc();
        }else if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("down")){
            comparator = new ProfileNameComparatorDes();
        }if(sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("up")){
            comparator = new DateComparatorAsc();
        }else if(sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("down")){
            comparator = new DateComparatorDes();
        }

        // Sort
        Collections.sort(filterTotalProfileList, comparator);

        // Data
        modelMap.addAttribute("profiles", getFilteredProfilePage(profileCurrentPageNum, filterTotalProfileList, pageSize));

        modelMap.addAttribute("profileCurrentPageNum", new Integer(profileCurrentPageNum));
        int profileTotalPageNum = ( (totalFilteredProfilesCount % pageSize) == 0)? totalFilteredProfilesCount/pageSize : totalFilteredProfilesCount/pageSize + 1;
        modelMap.addAttribute("profileTotalPageNum", profileTotalPageNum);

        int profilePageFirstItemNum = (profileCurrentPageNum-1)*pageSize + 1;
        modelMap.addAttribute("profilePageFirstItemNum", profilePageFirstItemNum);

        int profilePageLastItemNum = (profileCurrentPageNum*pageSize < totalFilteredProfilesCount)? profilePageFirstItemNum + pageSize-1 : totalFilteredProfilesCount;
        modelMap.addAttribute("profilePageLastItemNum", new Integer(profilePageLastItemNum));

        modelMap.addAttribute("profileTotalPageNum", new Integer(profileTotalPageNum));

        modelMap.addAttribute("isProfilePage", Boolean.TRUE);
        modelMap.addAttribute("goToPrevProfilePage", "goToProfilePage(" + (profileCurrentPageNum - 1) + ");");
        modelMap.addAttribute("goToNextProfilePage", "goToProfilePage(" + (profileCurrentPageNum + 1) + ");");
    }


    @RequestMapping(value = "/addProfile", method = RequestMethod.POST)
	public String addProfile(HttpServletRequest request,
			@ModelAttribute(value = "profile") Profile profile, BindingResult bindingResult) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

		try {
			ProfileProxy proxy = new ProfileProxy();
			proxy.setProfileName(profile.getProfileName());
			proxy.setProfileDesc(profile.getProfileDesc());
			proxy.setActive(profile.getActiveStr().equals("Yes"));
			int productRating = profile.getRating()!=null && profile.getRating().equals("on")?1:0;
			proxy.setProductRating(productRating);
			proxy.setAssociatedCatalogs(profile.getAssociatedCatalogs());
			proxy.setCreatedOnView("Profiles");
			proxy.setCreatedBy(user.getUsername());
			proxy.setCreatedByName(user.getFullName());
			proxy.setUnitId(user.getUnitId());
			proxy.setCreatedOn(new Date());
			profileService.addProfile(proxy);
			return "redirect:"+PROFILES_PAGE;
		} catch (Exception exp) {
			LOGGER.error("Error creating profile ", exp);
			return FAILURE;
		}
	}

    @RequestMapping(value="/searchWithinProfile", method=RequestMethod.POST)
    public String searchWithinProfiles(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true) String sortDirection,
            @RequestParam(value="searchWithin", required=true) String searchWithin, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            if(searchWithin.equalsIgnoreCase("Search Within...")) {
                searchWithin = "";
            }
            setProfilePaginationAttributes(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user);

        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILES_PAGE_FRAMGMENT;
    }

    @RequestMapping(value="/sortProfiles", method=RequestMethod.POST)
    public String sortProfile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            setProfilePaginationAttributes(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user);

        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILES_PAGE_FRAMGMENT;
    }


    @RequestMapping(value = "/profileExists", method=RequestMethod.POST)
    @ResponseBody public String profileExists(HttpServletRequest request, @RequestParam(required = false) String profileId, @RequestParam String profileName, ModelMap modelMap) {
    	
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

    	
    	Profile profile = profileService.getProfileByName(profileName,user.getUnitId());
    	
    	if(profile!=null) {
	    	if(profileId!=null && !profileId.equals("")){
	    		 if(!profileId.equals(""+profile.getProfileId()))return "true";
	    	}else if(user.getUnitId().equals(profile.getUnitId())){
	    		return "true";
	    	}
    	}    	
    	return "false";
    }
    


    private class ProfileNameComparatorAsc implements Comparator<Profile>{

        @Override
        public int compare(Profile p1, Profile p2) {
            return p1.getProfileName().compareTo(p2.getProfileName());
        }
    }
    private class ProfileNameComparatorDes implements Comparator<Profile>{

        @Override
        public int compare(Profile p1, Profile p2) {
            return p2.getProfileName().compareTo(p1.getProfileName());
        }
    }

    private class CatalogNameComparatorAsc implements Comparator<CatalogSummary>{

        @Override
        public int compare(CatalogSummary c1, CatalogSummary c2) {
            return c1.getCatalogNameNoSpace().compareTo(c2.getCatalogNameNoSpace());
        }
    }
    private class CatalogNameComparatorDes implements Comparator<CatalogSummary>{

        @Override
        public int compare(CatalogSummary c1, CatalogSummary c2) {
            return c2.getCatalogNameNoSpace().compareTo(c1.getCatalogNameNoSpace());
        }
    }

    private class DateComparatorAsc implements Comparator<Profile>{

        @Override
        public int compare(Profile p1, Profile p2) {
          if (p1 == null || p1.getCreatedOn() == null || p2 == null || p2.getCreatedOn() == null)
            return -1;

          return p1.getCreatedOn().compareTo(p2.getCreatedOn());
        }
    }

    private class DateComparatorDes implements Comparator<Profile>{

        @Override
        public int compare(Profile p1, Profile p2) {
          if(p1 == null || p1.getCreatedOn() == null || p2 == null || p2.getCreatedOn() == null)
            return -1;

          return p2.getCreatedOn().compareTo(p1.getCreatedOn());
        }
    }

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
}
