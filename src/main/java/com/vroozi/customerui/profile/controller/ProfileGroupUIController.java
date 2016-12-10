package com.vroozi.customerui.profile.controller;


import com.vroozi.customerui.catalog.model.MaterialGroupForm;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.model.SystemDefinition;
import com.vroozi.customerui.company.service.SystemDefinitionService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.profile.model.*;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Consts;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RandomStringUtil;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.vroozi.customerui.catalog.model.ContentAccessForm;

import static com.vroozi.customerui.util.Consts.*;

/**
 * 
 * @author Khansaj
 *
 */
@Controller
public class ProfileGroupUIController {
    private static final int PAGE_SIZE = 7;
	private final Logger LOGGER = LoggerFactory.getLogger(ProfileGroupUIController.class);
	private final String ASSOCIATED_PROFILES="associatedProfiles";
	private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);
	@Autowired
	private ProfileGroupService profileGroupService;
	
	@Autowired
	private ProfileService profileService;

    @Autowired
    private SystemDefinitionService systemDefinitionService;
    @Autowired
    AppConfig appConfig;
	
    private boolean isEditMode(HttpServletRequest request){
        return StringUtils.isNotEmpty(getProfileGroupId(request));
    }
    
    
/*    @RequestMapping(value = "/profilegroup/page")
    public String getProfileGroupPage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="pageNum", required=false ) Integer pageNum,
            @RequestParam(value="sortBy", required=false ) String sortBy,
            @RequestParam(value="sortDirection", required=false ) String sortDirection,
            @RequestParam(value="searchWithin", required=false ) String searchWithin,
            ModelMap modelMap){
    	
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
    	
    	profileGroupService.getPagedProfileGroups(buildProfileGroupFilter(pageNum, sortBy, sortDirection, searchWithin), user.getUnitId());
    	
    	return null;
    	
    }
*/    
    
    private ProfileGroupFilter buildProfileGroupFilter(int pageNo , String sortBy, String sortDirection, String searchTerm){
    	
    	
    	ProfileGroupFilter filter = new ProfileGroupFilter();
		if(sortBy != null && !sortBy.equals("0")) {
			filter.setSortField(sortBy);
			filter.setSortAscending(sortDirection.equals("up"));
		}
		if(searchTerm!=null) {
			if(searchTerm.equals("Search Within...")) {
				searchTerm = "";
			}
			filter.setSearchText(searchTerm);
		}
		if(pageNo<=0) { pageNo = 1; }
		filter.setPageNumber(pageNo);

		return filter;
    }
    
    
    @RequestMapping(value="/gotonotassoicatedprofilepage", method=RequestMethod.POST)
    public String goToNAProfilePage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin,
            @RequestParam(value="profileGroupId", required=true ) String groupId, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<Profile> associatedProfiles= new ArrayList<Profile>();
            if(!isEditMode(request)){
            	associatedProfiles = (List<Profile>)request.getSession().getAttribute(ASSOCIATED_PROFILES);
                if(associatedProfiles == null)associatedProfiles = new ArrayList<Profile>();
            }else{
            	 ProfileGroup profileGroup = null;
                 if(groupId.length()>0){
                 	profileGroup = profileGroupService.getProfileGroupsById(groupId);
                 }
                 if(profileGroup.getAssociatedProfiles().size() > 0){
     	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
     	            associatedProfiles = convertToProfileList(profileProxyList);
                 }               
            }


            List<Profile> allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());

            setProfilePagination(pageNum, allProfiles, associatedProfiles, modelMap, user.getUnitId(), getProfileGroupId(request) , sortBy, sortDirection, searchWithin, user.getRowsPerPage());

            
        }catch (Exception exp){
            LOGGER.error("Error going to profile pageNum(" + pageNum  + ")" );
        }

        return GROUPS_PROFILE_FRAGMENT;
    }    
    
    
    @RequestMapping(value = "/searchWithinNAProfile", method = RequestMethod.POST)
    public String searchWithinNAProfile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<Profile> associatedProfiles= new ArrayList<Profile>();
            if(!isEditMode(request)){
            	associatedProfiles = (List<Profile>)request.getSession().getAttribute(ASSOCIATED_PROFILES);
                if(associatedProfiles == null)associatedProfiles = new ArrayList<Profile>();
            }else{
            	 ProfileGroup profileGroup = null;
                 String groupId = getProfileGroupId(request);
                 if(groupId.length()>0){
                 	profileGroup = profileGroupService.getProfileGroupsById(groupId);
                 }
                 
                 if(profileGroup.getAssociatedProfiles().size() > 0){
     	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
     	            associatedProfiles = convertToProfileList(profileProxyList);
                 }               
            	
            }

            List<Profile> allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());

            setProfilePagination(1, allProfiles, associatedProfiles, modelMap, user.getUnitId(), getProfileGroupId(request) , sortBy, sortDirection, searchWithin,user.getRowsPerPage());

            return GROUPS_PROFILE_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in /searchWithinProfile" + e.getMessage());
            return FAILURE;
        }
    }    
    

    @RequestMapping(value = "/sortNAGroupProfiles", method = RequestMethod.POST)
    public String sortNAGroupProfiles(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<Profile> associatedProfiles = new ArrayList<Profile>();
            if(!isEditMode(request)){
            	associatedProfiles = (List<Profile>)request.getSession().getAttribute(ASSOCIATED_PROFILES);
                if(associatedProfiles == null)associatedProfiles = new ArrayList<Profile>();
            }else{

            	 ProfileGroup profileGroup = null;
                 String groupId = getProfileGroupId(request);
                 if(groupId.length()>0){
                 	profileGroup = profileGroupService.getProfileGroupsById(groupId);
                 }
                 
                 if(profileGroup.getAssociatedProfiles().size() > 0){
     	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
     	            associatedProfiles = convertToProfileList(profileProxyList);
                 }
                
            }

            List<Profile> allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());

            setProfilePagination(1, allProfiles, associatedProfiles, modelMap, user.getUnitId(), getProfileGroupId(request) , sortBy, sortDirection, searchWithin,user.getRowsPerPage());
            

            return GROUPS_PROFILE_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in /sortProfiles" + e.getMessage());
            return FAILURE;
        }
    }
    
    @RequestMapping(value = "/createToken", method=RequestMethod.GET)
    @ResponseBody public String createNewToken(HttpServletRequest request, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
        
        String newToken = RandomStringUtil.createToken();
        modelMap.put("token", newToken);
        return newToken;
    }
    
    
    @RequestMapping(value = "/profileGroupTokenExists", method=RequestMethod.POST)
    @ResponseBody public String profileGroupTokenExists(HttpServletRequest request, @RequestParam(required = false) String groupId, @RequestParam String token, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
    	ProfileGroup profileGroup = profileGroupService.getProfileGroupByToken(token,user.getUnitId());
    	if(profileGroup!=null) {
	    	if(groupId!=null && !groupId.equals("")){
	    		 if(Integer.parseInt(groupId) != profileGroup.getGroupId())return "true";
	    	}else if(user.getUnitId().equals(profileGroup.getUnitId())){
	    		return "true";
	    	}
    	}    	
    	return "false";
    }
    
    @RequestMapping(value = "/profileGroupExists", method=RequestMethod.POST)
    @ResponseBody public String profileGroupExists(HttpServletRequest request, @RequestParam(required = false) String groupId, @RequestParam String groupName, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
    	ProfileGroup profileGroup = profileGroupService.getProfileGroupByName(groupName,user.getUnitId());
    	if(profileGroup!=null) {
	    	if(groupId!=null && !groupId.equals("")){
	    		 if(Integer.parseInt(groupId) != profileGroup.getGroupId())return "true";
	    	}else if(user.getUnitId().equals(profileGroup.getUnitId())){
	    		return "true";
	    	}
    	}    	
    	return "false";
    }
    
    
    
    @RequestMapping(value = "/sortGroupProfile", method = RequestMethod.POST)
    public String sortGroupProfile(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value="sortBy", required=true ) String sortBy,
           @RequestParam(value="sortDirection", required=true ) 
    		String sortDirection, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<Profile> associatedProfiles = new ArrayList<Profile>();
            if(!isEditMode(request)){
            	associatedProfiles = (List<Profile>)request.getSession().getAttribute(ASSOCIATED_PROFILES);
                if(associatedProfiles == null)associatedProfiles = new ArrayList<Profile>();
            }else{
            	
                ProfileGroup profileGroup = null;
                String groupId = getProfileGroupId(request);
                if(groupId.length()>0){
                	profileGroup = profileGroupService.getProfileGroupsById(groupId);
                }
                
                if(profileGroup.getAssociatedProfiles().size() > 0){
    	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
    	            associatedProfiles = convertToProfileList(profileProxyList);
                }
            }

            setGroupProfilePagination(1, modelMap, associatedProfiles, getProfileGroupId(request), sortBy, sortDirection, user.getRowsPerPage());

            return GROUPS_ASSIGNED_PROFILE_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in /searchWithinProfile" + e.getMessage());
            return FAILURE;
        }
    }    
    
    
    @RequestMapping(value="/searchWithinProfileGroup", method=RequestMethod.POST)
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

            setprofileGroupPaginationAttributes(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user.getRowsPerPage());
            

        }catch (Exception exp){
            LOGGER.error("Error retrieving profile groups pageNum(" + pageNum  + ")" );
        }

        return PROFILE_GROUPS_PAGE_FRAMGMENT;
    }

    

    @RequestMapping(value="/sortProfileGroups", method=RequestMethod.POST)
    public String sortProfileGroups(HttpServletRequest request, HttpServletResponse response,
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

            setprofileGroupPaginationAttributes(user.getUnitId(),1, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user.getRowsPerPage());
        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILE_GROUPS_PAGE_FRAMGMENT;
    }    
    
    
    @RequestMapping(value = "/disAssociateGroupProfile", method = RequestMethod.POST)
    public String disAssociateGroupProfile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
//            List<Profile> notAssociatedProfiles= null;
            List<Profile> associatedProfiles = new ArrayList<Profile>();
            List<Profile> allProfiles = null;
            String[] disAssociatedProfileIds = request.getParameterValues("associatedProfileIds");
            if((disAssociatedProfileIds != null) && (disAssociatedProfileIds.length > 0)){
            	
                if(!isEditMode(request)){
                	associatedProfiles = (List<Profile>)request.getSession().getAttribute(ASSOCIATED_PROFILES);
                    if(associatedProfiles == null)associatedProfiles = new ArrayList<Profile>();
                    
                    associatedProfiles = removeAttachedProfiles(associatedProfiles, disAssociatedProfileIds);
                    
                    
                    allProfiles = unSelectedProfiles(associatedProfiles, profileService.getActiveProfilesByUnitId(user.getUnitId()));
                    request.getSession().setAttribute(ASSOCIATED_PROFILES, associatedProfiles);
                }else{
                    String groupId = getProfileGroupId(request);
                    if(StringUtils.isEmpty(groupId)) throw new Exception("'groupId' is missing!");
                    List<Integer> groupIds = new ArrayList<Integer>();
                    groupIds.add(Integer.parseInt(groupId));
                    for(String profileId:disAssociatedProfileIds){
                    	profileGroupService.deleteGroupProfiles(profileId, groupIds);
                    }
                    ProfileGroup profileGroup = null;
                    if(groupId.length()>0){
                    	profileGroup = profileGroupService.getProfileGroupsById(groupId);
                    }
                    
                    if(profileGroup.getAssociatedProfiles().size() > 0){
        	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
        	            associatedProfiles = convertToProfileList(profileProxyList);
                    }
                    allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());
                    
                }
                modelMap.put("totalGroupProfileCount", associatedProfiles.size());
                setProfilePagination(1, allProfiles, associatedProfiles, modelMap, user.getUnitId(), getProfileGroupId(request), "Name", "up", "",user.getRowsPerPage());
                setGroupProfilePagination(1, modelMap, associatedProfiles, getProfileGroupId(request), "Name", "up",user.getRowsPerPage());
                
            }
            return GROUP_PROFILE_BOTH_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error retrieving groups and profiles");
            return FAILURE;
        }
    }    
    
	
	@RequestMapping(value="/goToGroupProfilePage", method=RequestMethod.POST)
    public String goToGroupProfilePage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="pageNum", required=true ) Integer pageNum,  ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            List<Profile> associatedProfiles = new ArrayList<Profile>();
            if(!isEditMode(request)){
            	associatedProfiles = (List<Profile>)request.getSession().getAttribute(ASSOCIATED_PROFILES);
            }else{
	            String profileGroupId = getProfileGroupId(request);
	            ProfileGroup profileGroup = null;
	            if(profileGroupId.length()>0){
	            	profileGroup = profileGroupService.getProfileGroupsById(profileGroupId);
	            }
	            
	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
	            associatedProfiles = convertToProfileList(profileProxyList);
            }

	       setGroupProfilePagination(pageNum, modelMap, associatedProfiles, getProfileGroupId(request), "Name", "up",user.getRowsPerPage());


        }catch (Exception exp){
            LOGGER.error("Error retrieving profile pageNum(" + pageNum  + ")" );
        }

        return GROUPS_ASSIGNED_PROFILE_FRAGMENT;
    }
	
	@RequestMapping(value="/goToPageProfileGroups", method=RequestMethod.POST)
    public String getProfileGroupPage(HttpServletRequest request, HttpServletResponse response,
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

            setprofileGroupPaginationAttributes(user.getUnitId(), pageNum, ViewFilter.NONE, "Name", "up", "", modelMap,user.getRowsPerPage());

        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILE_GROUPS_PAGE_FRAMGMENT;
    }	
	

	
	@RequestMapping(value = "/deleteProfileGroups", method = RequestMethod.POST)
    public String deleteProfileGroups(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
            @RequestParam(value="pageNum") int pageNum,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam( value="profileGroupIds") Integer[] profileGroupIds,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            if(profileGroupIds != null && profileGroupIds.length > 0){
            	profileGroupService.deleteProfileGroups(Arrays.asList(profileGroupIds));
            }
            setprofileGroupPaginationAttributes(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user.getRowsPerPage());
        } catch (Exception exp) {
            LOGGER.error("Error deleting profileGroupIds (" + profileGroupIds + ") ", exp);
            return FAILURE;
        }

        return PROFILE_GROUPS_PAGE_FRAMGMENT;
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
	
	
	@RequestMapping("/editProfileGroup")
    public String editProfileGroups(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            String profileGroupId = getProfileGroupId(request);
            ProfileGroup profileGroup = null;
            if(profileGroupId.length()>0){
            	profileGroup = profileGroupService.getProfileGroupsById(profileGroupId);
            }
            
            List<Profile> associatedProfiles = new ArrayList<Profile>();
            if(profileGroup.getAssociatedProfiles().size() > 0){
	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
	            associatedProfiles = convertToProfileList(profileProxyList);
            }
            
            List<Profile> allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());
            
            setGroupProfilePagination(1, modelMap, associatedProfiles, profileGroupId, "Name", "up",user.getRowsPerPage());
            
            modelMap.addAttribute("groupName", profileGroup.getGroupName());
            modelMap.addAttribute("token", profileGroup.getToken());
            modelMap.addAttribute("displayOnRegistrationForm", profileGroup.isDisplayOnRegistrationForm());
            modelMap.addAttribute("profileGroupActive", (profileGroup.isActive())?"Yes":"No");
            modelMap.addAttribute("groupId", profileGroup.getGroupId());
            
            modelMap.addAttribute("pageName", "Edit Content View Group");

            setProfilePagination(1,allProfiles ,associatedProfiles , modelMap, user.getUnitId(), getProfileGroupId(request), "Name", "up", "",user.getRowsPerPage());            
            return CREATE_PROFILE_GROUP_PAGE;
        } catch (Exception e) {
            String errorMsg = "Error retrieving profile: " + e.getMessage();
            LOGGER.error(errorMsg);
            request.setAttribute("errorMsg", errorMsg);
            return FAILURE;
        }
    }	
	
	
	@RequestMapping(value = "/activeProfileGroups", method = RequestMethod.POST)
    public String activeProfileGroups(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
            @RequestParam(value="active") boolean active,
            @RequestParam(value="pageNum") int pageNum,
            @RequestParam(value="viewFilter") String viewFilter,
            @RequestParam( value="profileGroupIds") Integer[] profileGroupIds,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin) {

        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            if(profileGroupIds != null && profileGroupIds.length > 0){
            	profileGroupService.updateProfileGroupStatus(active, Arrays.asList(profileGroupIds));
            }
            setprofileGroupPaginationAttributes(user.getUnitId(), pageNum, ViewFilter.valueOf(viewFilter), sortBy, sortDirection, searchWithin, modelMap,user.getRowsPerPage());
        } catch (Exception exp) {
            LOGGER.error("Error activating profileGroupIds (" + profileGroupIds + ") ", exp);
            return FAILURE;
        }

        return PROFILE_GROUPS_PAGE_FRAMGMENT;
    }	
	
	
    @RequestMapping(value = "/associateProfileToGroup", method = RequestMethod.POST)
    public String associateProfileToGroup(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="sortBy", required=true ) String sortBy,
            @RequestParam(value="sortDirection", required=true ) String sortDirection,
            @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            List<Profile> associatedProfiles =new ArrayList<Profile>();
            List<Profile> notAssociatedProfiles = null;
            String[] associatedProfileIds = request.getParameterValues("associatedProfileIds");
            if((associatedProfileIds != null) && (associatedProfileIds.length > 0)){
            	if(!isEditMode(request)){
	            	associatedProfiles = (List<Profile>)request.getSession().getAttribute(ASSOCIATED_PROFILES);
	                if(associatedProfiles == null)associatedProfiles = new ArrayList<Profile>();
	                notAssociatedProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());
	                List<Profile> newlySelectedProfiles = getSelectedProfiles(notAssociatedProfiles, associatedProfileIds);
	                associatedProfiles.addAll(newlySelectedProfiles);
	                request.getSession().setAttribute(ASSOCIATED_PROFILES , associatedProfiles);
            	}else{
            		String groupId = getProfileGroupId(request);
                    if(StringUtils.isEmpty(groupId)) throw new Exception("'groupId' is missing!");

                    List<Integer> groupIds = new ArrayList<Integer>(); 
                    groupIds.add(Integer.parseInt(groupId));
                    
                    for(String profileId:associatedProfileIds){
                    	profileGroupService.addGroupProfiles(profileId, groupIds);
                    }

                    ProfileGroup profileGroup = null;
                    profileGroup = profileGroupService.getProfileGroupsById(groupId);

                    if(profileGroup.getAssociatedProfiles().size() > 0){
        	            List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(profileGroup.getAssociatedProfiles());
        	            associatedProfiles = convertToProfileList(profileProxyList);
                    }
                    notAssociatedProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());
            	}
                
                modelMap.put("totalGroupProfileCount", associatedProfiles.size());
                setProfilePagination(1,notAssociatedProfiles ,associatedProfiles , modelMap, user.getUnitId(), getProfileGroupId(request), "Name", "up", "",user.getRowsPerPage());
                setGroupProfilePagination(1, modelMap, associatedProfiles, getProfileGroupId(request), "Name", "up",user.getRowsPerPage());

            }
            return GROUP_PROFILE_BOTH_FRAGMENT;
        } catch (Exception e) {
            LOGGER.error("Error in associateCatalogToProfile" + e.getMessage());
            return FAILURE;
        }
    }	
	
    private List<Profile> unSelectedProfiles(List<Profile> selectedProfiles , List<Profile> allProfiles){
    	
    	List<Profile> notSelectedProfiles = new ArrayList<Profile>();

    	for (Profile profile : allProfiles) {
    		boolean matched = false;
        	for (Profile selProfile : selectedProfiles) {
        		if(selProfile.getProfileId() == profile.getProfileId()){
        			matched = true;
        			break;
        		}
        	}
        	if(!matched)notSelectedProfiles.add(profile);
		}
    	
    	
    	
    	
    	return notSelectedProfiles;
    }
    
    private void setProfilePagination(int PageNum,List<Profile> allProfiles, List<Profile> selectedProfiles,  ModelMap modelMap, String unitId, String groupId, String sortBy,  String sortDirection, String searchWithin, int pageSize) throws  Exception{

        try{
            List<Profile> profileList = unSelectedProfiles(selectedProfiles, allProfiles);
    
            

            // Search Within filter
            if(!StringUtils.isEmpty(searchWithin) && !searchWithin.equalsIgnoreCase("Search within...")){
            	profileList = filterWithinProfiles(searchWithin, profileList);
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

            Comparator<Profile> comparator = null;
            if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("up")){
                comparator = new ProfileNameComparatorAsc();
            }else if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("down")){
                comparator = new ProfileNameComparatorDes();
            }


            // Sort
            Collections.sort(profileList, comparator);

            // Pagination Info
            int totalProfileCount = profileList.size();
            modelMap.addAttribute("notAssociatedProfileCount", new Integer(totalProfileCount));

            //if(totalCatalogCount == 0) return;

            if(totalProfileCount <= (PageNum-1)*pageSize){
                PageNum = 1; // Reset to 1 or go back one page
            }
            modelMap.addAttribute("profileCurrentPageNum", new Integer(PageNum));


            int groupProfileTotalPageNum = ( (totalProfileCount % pageSize) == 0)? totalProfileCount/pageSize : totalProfileCount/pageSize + 1;
            modelMap.addAttribute("profilesTotalPageNum", groupProfileTotalPageNum);

            int catalogPageFirstItemNum = (PageNum-1)*pageSize + 1;
            modelMap.addAttribute("profilePageFirstItemNum", catalogPageFirstItemNum);

            int groupProfilePageLastItemNum = (PageNum*pageSize < totalProfileCount)? catalogPageFirstItemNum + pageSize-1 : totalProfileCount;
            modelMap.addAttribute("profilePageLastItemNum", new Integer(groupProfilePageLastItemNum));

            modelMap.addAttribute("goToPrevNAProfilePage", "goToNAProfilePage(" + (new Integer(PageNum) - 1) + ");");
            modelMap.addAttribute("goToNextNAProfilePage", "goToNAProfilePage(" + (new Integer(PageNum) + 1) + ");");

            List<Profile> pagedProfileList = new ArrayList<Profile>();
            if(!profileList.isEmpty()) {
            	pagedProfileList = profileList.subList(catalogPageFirstItemNum-1, groupProfilePageLastItemNum);
            }

            // Data
            modelMap.addAttribute("profiles", pagedProfileList);

            modelMap.addAttribute("groupId", groupId);

        }catch (Exception exp){
            LOGGER.error("Error during Profiles pagination", exp);
        }
    }    
    
    private List<Profile> filterWithinProfiles(String searchWithin, List<Profile> filterTotalProfileList){
        if(StringUtils.isEmpty(searchWithin)) return filterTotalProfileList;

        searchWithin = searchWithin.toLowerCase();
        List<Profile> filteredWithinProfiles= new ArrayList<Profile>();
        for(Profile profile: filterTotalProfileList){
            if(profile.getProfileName().toLowerCase().contains(searchWithin))
            	filteredWithinProfiles.add(profile);
        }
        return filteredWithinProfiles;
    }
    
	@RequestMapping(value="/filterProfileGroups", method=RequestMethod.POST)
    public String filterProfileGroups(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value="viewFilter") String viewFilter,
        @RequestParam(value="pageNum", required=true ) Integer pageNum,
        @RequestParam(value="searchWithin", required=true ) String searchWithin, ModelMap modelMap){
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            setprofileGroupPaginationAttributes(user.getUnitId(), 1, ViewFilter.valueOf(viewFilter), "Name", "up", searchWithin, modelMap,user.getRowsPerPage());
        }catch (Exception exp){
            LOGGER.error("Error retrieving profiles pageNum(" + pageNum  + ")" );
        }

        return PROFILE_GROUPS_PAGE_FRAMGMENT;
    }
	
	
	@RequestMapping(value = "/saveProfileGroup", method = RequestMethod.POST)
	public String saveProfileGroup(HttpServletRequest request,@ModelAttribute(value = "profileGroup") ProfileGroup profileGroup,BindingResult bindingResult, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		try{
//			if(!isEditMode(request)){
			if(profileGroup.getGroupId() <= 0){
		      List<Profile> associatedProfiles = (List<Profile>)request.getSession().getAttribute("associatedProfiles");
				  profileGroup.setAssociatedProfiles(getAssociatedProfileIds(associatedProfiles));
				  profileGroup.setCreatedBy(user.getUsername());
				  profileGroup.setUnitId(user.getUnitId());
				  profileGroup.setCreatedByName(user.getFullName());
				  profileGroup.setActive(request.getParameter("activeStr").equals("Yes"));
				  profileGroup.setCreatedOn(new Date());
				  profileGroupService.addProfileGroup(profileGroup);
				  associatedProfiles = new ArrayList<>();
	            request.getSession().setAttribute(ASSOCIATED_PROFILES, associatedProfiles);
	            List<Profile> notAssociatedProfiles= profileService.getActiveProfilesByUnitId(user.getUnitId());
	            modelMap.addAttribute("associatedProfileCount", 0);
	            modelMap.addAttribute("notAssociatedProfileCount", notAssociatedProfiles.size());
	            modelMap.addAttribute("profiles", notAssociatedProfiles);
	            modelMap.addAttribute(ASSOCIATED_PROFILES, associatedProfiles);
            }else{
            	
            	ProfileGroup profileGroupNew = profileGroupService.getProfileGroupsById(""+profileGroup.getGroupId());
            	profileGroupNew.setGroupName(profileGroup.getGroupName());
            	profileGroupNew.setActive(request.getParameter("activeStr").equals("Yes"));
              profileGroupNew.setDisplayOnRegistrationForm(profileGroup.isDisplayOnRegistrationForm());
            	profileGroupService.updateProfileGroup(profileGroupNew);
            	List<Profile> notAssociatedProfiles= profileService.getActiveProfilesByUnitId(user.getUnitId());
            	modelMap.addAttribute("associatedProfileCount", new Integer(0));
 	            modelMap.addAttribute("notAssociatedProfileCount", notAssociatedProfiles.size());
 	            modelMap.addAttribute("profiles", notAssociatedProfiles);
 	            modelMap.addAttribute(ASSOCIATED_PROFILES, new ArrayList<ProfileGroup>());
            }
			modelMap.put("token", RandomStringUtil.createToken());
			modelMap.addAttribute("selectedTab","2");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "forward:/profiles";
	}	
	
	
	private List<Integer> getAssociatedProfileIds(List<Profile> associatedProfiles){
        List<Integer> associatedProfileIds = new ArrayList<Integer>();
        for(Profile profile: associatedProfiles) {
            associatedProfileIds.add(profile.getProfileId());
        }
        return associatedProfileIds;
    }
	
	
	
	
	
	
    @RequestMapping("/createProfileGroup")
    public String createProfileGroup(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            modelMap.addAttribute("pageName", "Create Content View Group");
            modelMap.addAttribute("profileGroupActive", "Yes");

            request.getSession().setAttribute("associatedProfiles", new ArrayList<ProfileGroup>());
            
            modelMap.put("token", RandomStringUtil.createToken());
            modelMap.put("displayOnRegistrationForm", false);
            setGroupProfilePagination(1, modelMap, user.getUnitId(), getProfileGroupId(request), "Name", "up","", user.getRowsPerPage());
 
            
            return CREATE_PROFILE_GROUP_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving profiles");
            return FAILURE;
        }
    }
    
    
	
	
	@RequestMapping("/profileGroups")
	public String profileGroups(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

        try {
            int pageNum = 1;  // First Page

            setprofileGroupPaginationAttributes(user.getUnitId(), pageNum, ViewFilter.NONE, "Name", "up", "", modelMap,user.getRowsPerPage());
            

		} catch (Exception e) {
			LOGGER.error("Error retrieving profiles");
			return FAILURE;
		}

        return PROFILES_PAGE;
	}	
	
    private void setGroupProfilePagination(int PageNum, ModelMap modelMap, List<Profile> profileList, String groupId, String sortBy,  String sortDirection, int pageSize) throws  Exception{

	    try{
	        modelMap.addAttribute("groupId", groupId);
	
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
	        }
	        // Sort
	        Collections.sort(profileList, comparator);

	        // Pagination Info
	        int totalGroupProfileCount = profileList.size();
	        
	        modelMap.addAttribute("totalProfileGroupsCount", new Integer(totalGroupProfileCount));
	
	        if(totalGroupProfileCount <= (PageNum-1)*pageSize){
	            PageNum = 1; // Reset to 1 or go back one page
	        }
	        modelMap.addAttribute("groupProfileCurrentPageNum", new Integer(PageNum));
	
	
	        int groupProfileTotalPageNum = ( (totalGroupProfileCount % pageSize) == 0)? totalGroupProfileCount/pageSize : totalGroupProfileCount/pageSize + 1;
	        modelMap.addAttribute("groupProfileTotalPageNum", groupProfileTotalPageNum);
	
	        int groupProfilePageFirstItemNum = (PageNum-1)*pageSize + 1;
	        modelMap.addAttribute("groupProfilePageFirstItemNum", groupProfilePageFirstItemNum);
	
	        int groupProfilePageLastItemNum = (PageNum*pageSize < totalGroupProfileCount)? groupProfilePageFirstItemNum + pageSize-1 : totalGroupProfileCount;
	        modelMap.addAttribute("groupProfilePageLastItemNum", new Integer(groupProfilePageLastItemNum));
	
			List<Profile> groupProfileList1 = new ArrayList<Profile>();
			groupProfileList1 = profileList.subList(groupProfilePageFirstItemNum - 1,groupProfilePageLastItemNum);
			// Data
			modelMap.addAttribute(ASSOCIATED_PROFILES, groupProfileList1);
	
	    }catch (Exception exp){
	        LOGGER.error("Error during Catalog pagination", exp);
	    }
    }    
	
    private List<ProfileGroup> filterProfileGroups(List<ProfileGroup> profileGroupList, ViewFilter viewFilter){
        List<ProfileGroup> filteredProfileGroupList = new ArrayList<ProfileGroup>();

        if(viewFilter.equals(ViewFilter.INACTIVE)){
            for(ProfileGroup profileGroup: profileGroupList){
                if(!profileGroup.isActive()){
                    filteredProfileGroupList.add(profileGroup);
                }
            }
        } else  if(viewFilter.equals(ViewFilter.ACTIVE)){
            for(ProfileGroup profileGroup: profileGroupList){
                if(profileGroup.isActive()){
                    filteredProfileGroupList.add(profileGroup);
                }
            }
        }else if(viewFilter.equals(ViewFilter.NONE)){
            filteredProfileGroupList = profileGroupList;
        }

        return filteredProfileGroupList;
    }
	
    private void setGroupProfilePagination(int PageNum, ModelMap modelMap, String unitId , String groupId, String sortBy,  String sortDirection, String searchWithin, int pageSize) throws  Exception{

        try{
            List<Profile> profileList = profileService.getActiveProfilesByUnitId(unitId);

            // Search Within filter
/*            if(!StringUtils.isEmpty(searchWithin) && !searchWithin.equalsIgnoreCase("Search within")){
                profileList = filterWithinCatalogs(searchWithin, profileList);
                modelMap.addAttribute("searchWithin", searchWithin);
            }else{
                modelMap.addAttribute("searchWithin", "");
            }
*/
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
            }

            // Sort
            Collections.sort(profileList, comparator);

            // Pagination Info
            int totalProfileCount = profileList.size();
            modelMap.addAttribute("notAssociatedProfileCount", new Integer(totalProfileCount));

            //if(totalCatalogCount == 0) return;

            if(totalProfileCount <= (PageNum-1)*pageSize){
                PageNum = 1; // Reset to 1 or go back one page
            }
            modelMap.addAttribute("profileCurrentPageNum", new Integer(PageNum));


            int profilesTotalPageNum = ( (totalProfileCount % pageSize) == 0)? totalProfileCount/pageSize : totalProfileCount/pageSize + 1;
            modelMap.addAttribute("profilesTotalPageNum", profilesTotalPageNum);

            int profilePageFirstItemNum = (PageNum-1)*pageSize + 1;
            modelMap.addAttribute("profilePageFirstItemNum", profilePageFirstItemNum);

            int profilePageLastItemNum = (PageNum*pageSize < totalProfileCount)? profilePageFirstItemNum + pageSize-1 : totalProfileCount;
            modelMap.addAttribute("profilePageLastItemNum", new Integer(profilePageLastItemNum));

            modelMap.addAttribute("isProfileGroupPage", Boolean.TRUE);
            modelMap.addAttribute("goToPrevNAProfilePage", "goToNAProfilePage(" + (new Integer(PageNum) - 1) + ");");
            modelMap.addAttribute("goToNextNAProfilePage", "goToNAProfilePage(" + (new Integer(PageNum) + 1) + ");");

            List<Profile> pagedProfileList = new ArrayList<Profile>();
            if(!profileList.isEmpty()) {
                pagedProfileList = profileList.subList(profilePageFirstItemNum-1, profilePageLastItemNum);
            }

            // Data
            modelMap.addAttribute("profiles", pagedProfileList);

            modelMap.addAttribute("groupId", groupId);

        }catch (Exception exp){
            LOGGER.error("Error during Catalog pagination", exp);
        }
    }    
    
    private List<ProfileGroup> filterWithinProfileGroups(String searchWithin, List<ProfileGroup> filterTotalProfileGroupList){
        if(StringUtils.isEmpty(searchWithin)) return filterTotalProfileGroupList;

        searchWithin = searchWithin.toLowerCase();
        List<ProfileGroup> filteredWithinProfileGroups = new ArrayList<ProfileGroup>();
        for(ProfileGroup profileGroup : filterTotalProfileGroupList){
            if(profileGroup.getGroupName().toLowerCase().contains(searchWithin) || profileGroup.getToken().toLowerCase().contains(searchWithin))
                filteredWithinProfileGroups.add(profileGroup);
        }
        return filteredWithinProfileGroups;
    }

	
    private List<ProfileGroup> getFilteredProfileGroupPage(int pageNum, List<ProfileGroup>  filterTotalProfileGroupList, int pageSize){
        List<ProfileGroup> filteredProfileGroupPage = new ArrayList<ProfileGroup>();

        int offset =  pageSize*(pageNum-1) ;

        int maxCount = (filterTotalProfileGroupList.size() > pageSize + offset)? pageSize + offset: filterTotalProfileGroupList.size() ;
        for(int i = offset; i <  maxCount; ++i ){
            filteredProfileGroupPage.add(filterTotalProfileGroupList.get(i));
        }

        return filteredProfileGroupPage;
    }
    
    
    private void setprofileGroupPaginationAttributes(String companyCode, int profileGroupCurrentPageNum, ViewFilter profileGroupViewFiler,
            String sortBy,  String sortDirection, String searchWithin, ModelMap modelMap, int pageSize) throws  Exception{

        List<ProfileGroup> totalProfileGroupList = profileGroupService.getProfileGroupsByUnitId(companyCode);
        int numOfActiveProfileGroups = 0;
        for(ProfileGroup profileGroup:totalProfileGroupList){
            if(profileGroup.isActive()){
                numOfActiveProfileGroups += 1;
            }
        }
        // These are for upper portion of the page
        modelMap.addAttribute("totalProfilesGroupCount", new Integer(totalProfileGroupList.size()));
        modelMap.addAttribute("numOfActiveProfileGroup", new Integer(numOfActiveProfileGroups));

        // Pagination Info
        List<ProfileGroup>  filterTotalProfileGroupList = filterProfileGroups(totalProfileGroupList, profileGroupViewFiler);

        // Search Within filter
        if(!StringUtils.isEmpty(searchWithin) && !"Search within...".equals(searchWithin)){
            filterTotalProfileGroupList = filterWithinProfileGroups(searchWithin, filterTotalProfileGroupList);
            modelMap.addAttribute("searchWithin", searchWithin);
        }else{
            modelMap.addAttribute("searchWithin", "Search within...");
        }

        int totalFilteredProfilesCount = filterTotalProfileGroupList.size();
        modelMap.addAttribute("totalFilteredProfileGroupCount", new Integer(totalFilteredProfilesCount));

        if(filterTotalProfileGroupList.size() <= (profileGroupCurrentPageNum-1)*pageSize){
            profileGroupCurrentPageNum = 1; // Reset to 1 or go back one page
        }

        if(StringUtils.isEmpty(sortBy)){
            sortBy = "Name";
        }
        modelMap.addAttribute("sortBy", sortBy);

        if(StringUtils.isEmpty(sortDirection)){
            sortDirection= "up";
        }
        modelMap.addAttribute("sortDirection", sortDirection);

        Comparator<ProfileGroup> comparator = null;
        if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("up")){
            comparator = new GroupNameComparatorAsc();
        }else if(sortBy.equalsIgnoreCase("Name") && sortDirection.equalsIgnoreCase("down")){
            comparator = new GroupNameComparatorDes();
        }if(sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("up")){
            comparator = new DateComparatorAsc();
        }else if(sortBy.equalsIgnoreCase("Date") && sortDirection.equalsIgnoreCase("down")){
            comparator = new DateComparatorDes();
        }

        // Sort
        Collections.sort(filterTotalProfileGroupList, comparator);

        // Data
        modelMap.addAttribute("profileGroups", getFilteredProfileGroupPage(profileGroupCurrentPageNum, filterTotalProfileGroupList,pageSize));

        modelMap.addAttribute("profileGroupCurrentPageNum", new Integer(profileGroupCurrentPageNum));
        int profileGroupTotalPageNum = ( (totalFilteredProfilesCount % pageSize) == 0)? totalFilteredProfilesCount/pageSize : totalFilteredProfilesCount/pageSize + 1;
        modelMap.addAttribute("profileGroupTotalPageNum", profileGroupTotalPageNum);

        int profileGroupPageFirstItemNum = (profileGroupCurrentPageNum-1)*pageSize + 1;
        modelMap.addAttribute("profileGroupPageFirstItemNum", profileGroupPageFirstItemNum);

        int profileGroupPageLastItemNum = (profileGroupCurrentPageNum*pageSize < totalFilteredProfilesCount)? profileGroupPageFirstItemNum + pageSize-1 : totalFilteredProfilesCount;
        modelMap.addAttribute("profileGroupPageLastItemNum", new Integer(profileGroupPageLastItemNum));

        modelMap.addAttribute("profileGroupTotalPageNum", new Integer(profileGroupTotalPageNum));

        modelMap.addAttribute("isProfileGroupPage", Boolean.TRUE);
        modelMap.addAttribute("goToPrevGroupPage", "goToGroupPage(" + (profileGroupCurrentPageNum - 1) + ");");
        modelMap.addAttribute("goToNextGroupPage", "goToGroupPage(" + (profileGroupCurrentPageNum + 1) + ");");
    }
	
    
    private String getProfileGroupId(HttpServletRequest request){
        
    	
    	if(request.getParameter("profileGroupId") != null){
            return request.getParameter("profileGroupId");
        }
        
        
        return "";
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
    

   private List<Profile> removeAttachedProfiles(List<Profile> profiles, String[] associatedProfileIds){
    	
        List<Profile> selectedProfiles= new ArrayList<Profile>();
        for(Profile profile: profiles){
        	boolean matched = false;
        	for(String profileId: associatedProfileIds) {
                if(profile.getProfileId() == Integer.parseInt(profileId)){
                    matched = true;
                    break;
                }
            }
        	
        	if(!matched)selectedProfiles.add(profile);
        }
        return selectedProfiles;
    }    
    
    private List<Profile> getSelectedProfiles(List<Profile> profiles, String[] associatedProfileIds){
    	
        List<Profile> selectedProfiles= new ArrayList<Profile>();
        for(String profileId: associatedProfileIds) {
        	int index = 0;
            for(Profile profile: profiles){
                if(profile.getProfileId() == Integer.parseInt(profileId)){
                    selectedProfiles.add(profile);
//                    profiles.remove(index);
                    break;
                }
                index++;
            }
        }
        return selectedProfiles;
    }



    /*
     * COMPARATORS SECTION
     */
    
    
    private class GroupNameComparatorAsc implements Comparator<ProfileGroup>{

        @Override
        public int compare(ProfileGroup p1, ProfileGroup p2) {
            return p1.getGroupName().compareTo(p2.getGroupName());
        }
    }
    private class GroupNameComparatorDes implements Comparator<ProfileGroup>{

        @Override
        public int compare(ProfileGroup p1, ProfileGroup p2) {
            return p2.getGroupName().compareTo(p1.getGroupName());
        }
    }
    private class DateComparatorAsc implements Comparator<ProfileGroup>{

        @Override
        public int compare(ProfileGroup p1, ProfileGroup p2) {
          if (p1 == null || p1.getCreatedOn() == null || p2 == null || p2.getCreatedOn() == null)
            return -1;

          return p1.getCreatedOn().compareTo(p2.getCreatedOn());
        }
    }

    private class DateComparatorDes implements Comparator<ProfileGroup>{

        @Override
        public int compare(ProfileGroup p1, ProfileGroup p2) {
          if(p1 == null || p1.getCreatedOn() == null || p2 == null || p2.getCreatedOn() == null)
            return -1;

          return p2.getCreatedOn().compareTo(p1.getCreatedOn());
        }
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
    

    
    
    
    /******************************** PROFILE GROUP MAPPING CODE ****************************/
    @RequestMapping(value = "/profilegroupmapping")
    public String loadProfileGroupMapping(ModelMap map , HttpServletRequest request,HttpServletResponse response){
    	ProfileGroupMappingStatus status = new ProfileGroupMappingStatus();
        User user = SessionDataRetriever.getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
        String currentStatus = "";
        boolean errorsFound = false;
        String message="";
        ProfileGroupMappingStatus mappingStatus  = profileGroupService.getProfileGroupMappingStatus(user.getUnitId());
        try{
	        List<ProfileGroup> profileGroups = profileGroupService.getActiveGroupsByUnitId(user.getUnitId());
	        
	        if(profileGroups != null){
	        	map.put("profileGroupList", profileGroups);
	        }
        }catch (Exception e) {
			// TODO: handle exception
		}
        if(mappingStatus != null){
        	currentStatus = mappingStatus.getProfileGroupMappingProcessState();
            errorsFound = !mappingStatus.isValidFile();
            map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
        }

        if (currentStatus.length() > 0) {
            if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
            	message = "The content access mapping file has been submitted for processing.";
            } else if (currentStatus.equalsIgnoreCase("VALIDATE_PROFILE_GROUP")) {
            	message = "The content access mapping file is being validated...";
            } else if (currentStatus.equalsIgnoreCase("VALIDATED_PROFILE_GROUP")||currentStatus.equalsIgnoreCase("PROFILE_GROUP_MAPPING_SAVING")) {
            	message = "The content access mappings are being saved...";
            } else if (currentStatus.equalsIgnoreCase("PROFILE_GROUP_MAPPING_SAVED")) {
                map.addAttribute("pgErrorReportStyle", errorsFound?"":"display:none;");
                map.addAttribute("pgErrorReport", errorsFound);

            }
        }
        
        if (null!=message) {
            map.addAttribute("pgMessage", message);
        }
        
        if(message.length()==0){
	        int pageNo = 1;
	        int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();
	        Page<ProfileGroupMapping> mappingPage = profileGroupService.getPagedProfileGroupMapping(user.getUnitId(), pageNo, pageSize, null);
	        map.addAttribute("profileGroups",mappingPage.getPageItems());
	        map.addAttribute("pgPageNumber",mappingPage.getPageNumber());
	        map.addAttribute("pgPagesAvailable",mappingPage.getPagesAvailable());
	        map.addAttribute("pgTotalRecords", mappingPage.getTotalRecords());
	        map.addAttribute("pgRecordsPerPage", pageSize);
	        map.addAttribute("pgUpdating", new Boolean(false));
            map.addAttribute("pgErrorReportStyle", errorsFound?"":"display:none;");
            map.addAttribute("pgErrorReport", errorsFound);
            map.addAttribute("pgProcessedReportStyle", mappingPage.getTotalRecords()>0?"":"display:none");
            map.addAttribute("pgProcessedCount", mappingPage.getTotalRecords());

        }
        
        map.addAttribute("profileGroupMapping",new ProfileGroupMapping());
    	return DATA_MAPPING_PAGE;
    }

    
    
    
    @RequestMapping(value = "/uploadProfileGroup", method=RequestMethod.POST, produces="text/html")
    @ResponseBody
    public String uploadMapping(HttpServletRequest request , @ModelAttribute MaterialGroupForm materialGroupForm, ModelMap map){
        ProfileGroupMappingPost profileGroupPost = new ProfileGroupMappingPost();

        if(materialGroupForm.getProfileGroupFile() != null && !materialGroupForm.getProfileGroupFile().isEmpty()) {
            if(writeToFile(materialGroupForm.getProfileGroupFile().getBytes(), materialGroupForm.getProfileGroupFile().getOriginalFilename())){
                profileGroupPost.setFilePath(appConfig.fileUploadPath + "/" +materialGroupForm.getProfileGroupFile().getOriginalFilename());
            }
        }

        
        
        
        
        String messageString = null;
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            profileGroupPost.setFilePath(appConfig.fileUploadPath + "/" + materialGroupForm.getProfileGroupFile().getOriginalFilename());
            profileGroupPost.setSubmitter(Integer.parseInt(user.getUserId()));
            profileGroupPost.setUnitId(Integer.parseInt(user.getUnitId()));
            profileGroupService.uploadProfileGroupMapping(profileGroupPost);
            messageString = "The content access mapping file has been submitted for processing.";
            map.addAttribute("pgMessage", messageString);


        }catch (Exception e) {
            e.printStackTrace();
        }
        return Consts.SUCCESS;
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
    

    @RequestMapping(value = "/profilegroupmapping/page")
    public String getProfileGroupMappingPage(ModelMap map , HttpServletRequest request, @RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "searchTerm", required = false) String searchTerm, @RequestParam(value = "fileUploading", required = false) String fileUploading){

        try{
        	boolean processingFinished = false;
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
        	
            String currentStatus = "";
            boolean errorsFound = false;
            String message="";
            ProfileGroupMappingStatus mappingStatus  = profileGroupService.getProfileGroupMappingStatus(user.getUnitId());
            List<ProfileGroup> profileGroups = profileGroupService.getActiveGroupsByUnitId(user.getUnitId());
            
            if(profileGroups != null){
            	map.put("profileGroupList", profileGroups);
            }
            
            if(mappingStatus != null){
            	currentStatus = mappingStatus.getProfileGroupMappingProcessState();
                errorsFound = !mappingStatus.isValidFile();
                map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
            }

            if (currentStatus.length() > 0) {
                if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
                	message = "The content access mapping file has been submitted for processing.";
                } else if (currentStatus.equalsIgnoreCase("VALIDATE_PROFILE_GROUP")) {
                	message = "The content access mapping file is being validated...";
                } else if (currentStatus.equalsIgnoreCase("VALIDATED_PROFILE_GROUP")||currentStatus.equalsIgnoreCase("PROFILE_GROUP_MAPPING_SAVING")) {
                	message = "The content access mappings are being saved...";
                }else if(currentStatus.equalsIgnoreCase("FAILED")){
                	processingFinished = true;
                }else if (currentStatus.equalsIgnoreCase("PROFILE_GROUP_MAPPING_SAVED")) {
                    map.addAttribute("pgErrorReportStyle", errorsFound?"":"display:none;");
                    map.addAttribute("pgErrorReport", errorsFound);
                    processingFinished = true;
                }
            }
            
            
            
            if (message.length()>0) {
                map.addAttribute("pgMessage", message);
            }            
            
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();
            if(mappingStatus == null){
                processingFinished = true;
            }
            if (message.length()==0 && processingFinished) {
            	
            	Page<ProfileGroupMapping> mappingPage = profileGroupService.getPagedProfileGroupMapping(user.getUnitId(), pageNumber, pageSize, searchTerm);
                map.addAttribute("profileGroups",mappingPage.getPageItems());
                map.addAttribute("pgPageNumber",mappingPage.getPageNumber());
                map.addAttribute("pgPagesAvailable",mappingPage.getPagesAvailable());
                map.addAttribute("pgTotalRecords", mappingPage.getTotalRecords());
                map.addAttribute("pgErrorReportStyle", errorsFound?"":"display:none;");
                map.addAttribute("pgErrorReport", errorsFound);
                map.addAttribute("pgProcessedReportStyle", mappingPage.getTotalRecords()>0?"":"display:none");
                map.addAttribute("pgProcessedCount", mappingPage.getTotalRecords());
            }
            map.addAttribute("pgRecordsPerPage", pageSize);
            map.addAttribute("pgUpdating", new Boolean(false));



        }catch (Exception e) {
            e.printStackTrace();
        }

        return Consts.PROFILEGROUP_MAPPING_TABLE_FRAGMENT;
    }    
    
    @RequestMapping(value = "/profilegroupmappingstatus/page")
    public String getProfileGroupMappingUploadPage(ModelMap map , HttpServletRequest request, @RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "searchTerm", required = false) String searchTerm, @RequestParam(value = "fileUploading", required = false) String fileUploading){

        try{
        	boolean processingFinished = false;
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            String currentStatus = "";
            boolean errorsFound = false;
            String message="";
            ProfileGroupMappingStatus mappingStatus  = profileGroupService.getProfileGroupMappingStatus(user.getUnitId());
            List<ProfileGroup> profileGroups = profileGroupService.getActiveGroupsByUnitId(user.getUnitId());
            
            if(profileGroups != null){
            	map.put("profileGroupList", profileGroups);
            }
            
            if(mappingStatus != null){
            	currentStatus = mappingStatus.getProfileGroupMappingProcessState();
                errorsFound = !mappingStatus.isValidFile();
                map.addAttribute("pgFileHandle", mappingStatus.getProcessId());
                message = mappingStatus.getMessage();
                map.addAttribute("pgProcessFailed", mappingStatus.isProcessFailed());
            }

            if (currentStatus.length() > 0) {
                if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
                	message = "The content access mapping file has been submitted for processing.";
                } else if (currentStatus.equalsIgnoreCase("VALIDATE_PROFILE_GROUP")) {
                	message = "The content access mapping file is being validated...";
                } else if (currentStatus.equalsIgnoreCase("VALIDATED_PROFILE_GROUP")||currentStatus.equalsIgnoreCase("PROFILE_GROUP_MAPPING_SAVING")) {
                	message = "The content access mappings are being saved...";
                }else if(currentStatus.equalsIgnoreCase("FAILED")){
                	processingFinished = true;
                }else if (currentStatus.equalsIgnoreCase("PROFILE_GROUP_MAPPING_SAVED")) {
                    map.addAttribute("pgErrorReportStyle", errorsFound?"":"display:none;");
                    map.addAttribute("pgErrorReport", errorsFound);
                    processingFinished = true;
                }
            }
            
            
            
            
            if (message.length()>0) {
                map.addAttribute("pgMessage", message);
            }            
            
            int pageSize = SessionDataRetriever.getLoggedInUser(request).getRowsPerPage();

            if (processingFinished) {
            	
            	Page<ProfileGroupMapping> mappingPage = profileGroupService.getPagedProfileGroupMapping(user.getUnitId(), pageNumber, pageSize, searchTerm);
                map.addAttribute("profileGroups",mappingPage.getPageItems());
                map.addAttribute("pgPageNumber",mappingPage.getPageNumber());
                map.addAttribute("pgPagesAvailable",mappingPage.getPagesAvailable());
                map.addAttribute("pgTotalRecords", mappingPage.getTotalRecords());
                map.addAttribute("pgErrorReportStyle", errorsFound?"":"display:none;");
                map.addAttribute("pgErrorReport", errorsFound);
                map.addAttribute("pgProcessedReportStyle", mappingPage.getTotalRecords()>0?"":"display:none");
                map.addAttribute("pgProcessedCount", mappingPage.getTotalRecords());
            }
            map.addAttribute("pgRecordsPerPage", pageSize);
            map.addAttribute("pgUpdating", new Boolean(!processingFinished));



        }catch (Exception e) {
            e.printStackTrace();
        }

        return Consts.PROFILEGROUP_MAPPING_TABLE_FRAGMENT;
    }      
        
    
    @RequestMapping(value = "/viewmappingerrors")
    public void viewErrorReport(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            String fileHandle = request.getParameter("pgFileHandle");


            String fileName= fileHandle+"_"+"PROFILE_GROUP_MAPPING"+".pdf";
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(((appConfig.errorReportPath.endsWith("/"))?appConfig.errorReportPath:appConfig.errorReportPath+"/")+fileName)));
            response.setContentType("application/pdf");

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
        	LOGGER.error("Error Report: "+ e);
            try{
                String fileName= "errorReportFailure.pdf";
                BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(appConfig.errorReportPath+fileName)));
                response.setContentType("application/pdf");
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }catch (Exception el){
            	LOGGER.error("Error Report: "+ e);
            }
        }

    }
    

    @RequestMapping(value = "/viewprocessedmapping.xlsx")
    public void viewProcessedMapping(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            if (user == null) {
                return ;
            }

            ProfileGroupMappingStatus mappingStatus  = profileGroupService.getProfileGroupMappingStatus(user.getUnitId());
            String fileName= "";
            if(mappingStatus == null){
            	fileName = appConfig.processedMappingPath+"CONTENT_ACCESS_PROCESS.xlsx";
            }else{
            	fileName= "CONTENT_ACCESS_"+System.currentTimeMillis()+"_"+user.getUnitId()+".xlsx";
            	profileGroupService.downloadMappings(user.getUnitId(),fileName);
            	fileName = appConfig.processedMappingPath+fileName;
            }
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
            response.setContentType("application/text");

            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
        	LOGGER.error("Processed File Report: "+ e);
        }

    }
    
    
    @RequestMapping(value="/savecontentaccess", method=RequestMethod.POST)
    public String saveContentAccessMapping(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ProfileGroupMapping contentAccessForm, ModelMap map){
    	try{
    		User user = SessionDataRetriever.getLoggedInUser(request);
    		contentAccessForm.setUnitId(user.getUnitId());
    		profileGroupService.saveContentAccess(contentAccessForm);	
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	return "";
    }    
    
    @RequestMapping(value="/deletecontentaccess", method=RequestMethod.POST)
    public void removeContentAccess(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ProfileGroupMapping contentAccessForm, ModelMap map){
    	try{
	    	String[] data = request.getParameterValues("profileMappings");
	    	List<String> contents = new ArrayList<String>();
	    	for (String contentAccess : data) {
				contents.add(contentAccess);
			}
	    	
	    	profileGroupService.removeContentAccess(contents);
	    
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    @RequestMapping(value="/validateuniqueSystemSAPUser" , method=RequestMethod.POST)
    public @ResponseBody boolean validateUniqueSystemSAPUser(HttpServletRequest request , @RequestParam(value="groupname", required=false) String groupname, @RequestParam(value= "user") String user,@RequestParam(value="uniqueSystemId")String uniqueSystemId, ModelMap map){
        User usr = SessionDataRetriever.getLoggedInUser(request);
        if (usr == null) {
            return false;
        }
        if(uniqueSystemId!=null && !uniqueSystemId.equals("")){
            SystemDefinition defination=systemDefinitionService.findSystemDefinitionByUniqueId(usr.getUnitId(), uniqueSystemId);
            if(defination==null){
                map.put("errorMsg","Unique System ID "+uniqueSystemId+" does not exist in system!");
                return true;
            }
        }
        return false;
    }
        @RequestMapping(value="/validatecontentaccess" , method=RequestMethod.POST)
    public @ResponseBody boolean validateMapping(HttpServletRequest request , @RequestParam(value="groupname", required=false) String groupname, @RequestParam(value= "user") String user,@RequestParam(value="uniqueSystemId")String uniqueSystemId, ModelMap map){
    	
        User usr = SessionDataRetriever.getLoggedInUser(request);
        
        if (usr == null) {
            return false;
        }
        user = user.replace("+", "\\+");
    	ProfileGroupMapping mapping = profileGroupService.findContentAccess(groupname, user, Integer.parseInt(usr.getUnitId()),uniqueSystemId);
    	if(mapping !=null){
    		map.put("errorMsg", "MAPPING already exist");
    		//return FAILURE;
    		return true;
    	}
       
    	return false;
    	//return "SUCCESS";
    }
    
}


