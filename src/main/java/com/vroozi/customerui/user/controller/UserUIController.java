package com.vroozi.customerui.user.controller;

import static com.vroozi.customerui.util.Consts.FAILURE;
import static com.vroozi.customerui.util.Consts.FORMAT_DATE_DD_MM_YYYY_HH_MM_SS;
import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;
import static com.vroozi.customerui.util.Consts.MY_ACCOUNT_PAGE;
import static com.vroozi.customerui.util.Consts.USER_ADD_PROFILES;
import static com.vroozi.customerui.util.Consts.USER_DETAIS_SECTION;
import static com.vroozi.customerui.util.Consts.USER_EDIT;
import static com.vroozi.customerui.util.Consts.USER_EDIT_PROFILES;
import static com.vroozi.customerui.util.Consts.USER_MANAGEMENT;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vroozi.customerui.acl.model.Role;

import com.vroozi.customerui.supplier.model.*;
import com.vroozi.customerui.supplier.model.TimeZone;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.myaccount.model.MyAccountPost;
import com.vroozi.customerui.notification.services.NotificationService;
import com.vroozi.customerui.profile.model.ContentAccessDTO;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.supplier.services.rest.SupplierRestClient;
import com.vroozi.customerui.user.services.UserManagementService;
//import com.vroozi.customerui.user.services.user.model.Role;
import com.vroozi.customerui.user.services.user.model.SupplierUser;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.user.services.user.model.UserBean;
import com.vroozi.customerui.user.services.user.model.UserDetail;
import com.vroozi.customerui.user.services.user.model.UserFilter;
import com.vroozi.customerui.user.services.user.model.UsersPage;

/**
 * 
 * @author mhabib
 * 
 */
@Controller
public class UserUIController {
	private final Logger LOGGER = LoggerFactory.getLogger(UserUIController.class);

    public static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);



    @Autowired
    UserManagementService userManagementService;

	@Autowired
	ProfileService profileService;

	@Autowired
    SupplierRestClient supplierRestClient;
	
	@Autowired
	NotificationService notificationService;

    @Autowired
    CompanyService companyService;
    
    @Autowired
    AppConfig appConfig;

    @RequestMapping(value = "/supplier" , produces = "application/json" , method = RequestMethod.GET )
    public @ResponseBody
    List<Supplier> getSuppliersByUnitId(HttpServletRequest request){
        User user = SessionDataRetriever.getLoggedInUser(request);
        return userManagementService.getSuppliersByUnitId(user.getUnitId());
    }

	@RequestMapping("/user_management")
	public String users(HttpServletRequest request,
			@RequestParam(value = "userType", required = false) String userType,
			HttpServletResponse response, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);


		return userManagementView(modelMap, user, userType);
	}

    @RequestMapping("/user_management_master_admins")
    public String masterAdminUsers(HttpServletRequest request,
                        @RequestParam(value = "userType", required = false) String userType,
                        HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        modelMap.addAttribute("filterUser", "masterAdmins");
        return userManagementView(modelMap, user, userType);
    }

    @RequestMapping("/user_management_admins")
    public String adminUsers(HttpServletRequest request,
                                   @RequestParam(value = "userType", required = false) String userType,
                                   HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        modelMap.addAttribute("filterUser", "admins");
        return userManagementView(modelMap, user, userType);
    }

    @RequestMapping("/user_management_buyers")
    public String buyerUsers(HttpServletRequest request,
                                   @RequestParam(value = "userType", required = false) String userType,
                                   HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        modelMap.addAttribute("filterUser", "buyers");
        return userManagementView(modelMap, user, userType);
    }

    @RequestMapping("/user_management_approvers")
    public String approverUsers(HttpServletRequest request,
                                   @RequestParam(value = "userType", required = false) String userType,
                                   HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        modelMap.addAttribute("filterUser", "approvers");
        return userManagementView(modelMap, user, userType);
    }

    @RequestMapping("/user_management_shoppers")
    public String shopperUsers(HttpServletRequest request,
                                   @RequestParam(value = "userType", required = false) String userType,
                                   HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        modelMap.addAttribute("filterUser", "shoppers");
        return userManagementView(modelMap, user, userType);
    }

	@RequestMapping("/navigateUserPage")
	public String navigateUserPage(HttpServletRequest request,
			@RequestParam(value = "userType") String userType,
			@RequestParam(value = "filterOptions", required=false) List<String> filterOptions,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "ascending", required = false) Boolean ascending,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(value = "page") String page,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			HttpServletResponse response, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		if(pageNo==null) {
			pageNo = 0;
		}
		if(page.equals("next")) {
			pageNo += 1 ;
		} else if(page.equals("prev")) {
			pageNo -= 1 ;
		} else if(page.equals("last")) {
			
		} else if(page.equals("first")) {
			
		} else {
			try {
				pageNo = Integer.parseInt(page);
			} catch (Exception e) {
				LOGGER.error("Invalid page", e);
			}
		}
		
		return updateUsersDetailsView(userType, filterOptions, sort, ascending, status,
				searchText, pageNo, modelMap, user);
	}

	@RequestMapping(value = "/deleteUsers", method = RequestMethod.POST)
	public String deleteUsers(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userType") String userType,
			@RequestParam(value = "filterOptions", required=false) List<String> filterOptions,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "ascending", required = false) Boolean ascending,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(value = "userIds") String[] userIds,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

		try {
			List<String> usersToDelete = new ArrayList<String>();
			for (String userId : userIds) {
				if(!userId.equals(user.getUserId())) {
					usersToDelete.add(userId);
				}
			}
			
			userManagementService.deleteUsers(usersToDelete);
			return updateUsersDetailsView(userType, filterOptions, sort, ascending,
					status, searchText, pageNo, modelMap, user);
		} catch (Exception exp) {
			LOGGER.error("Error deleting profileIds (" + userIds + ") ", exp);
			return FAILURE;
		}
	}

	@RequestMapping(value = "/activateUsers", method = RequestMethod.POST)
	public String activateUsers(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userType") String userType,
			@RequestParam(value = "filterOptions", required=false) List<String> filterOptions,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "ascending", required = false) Boolean ascending,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(value = "active") boolean active,
			@RequestParam(value = "userIds") String[] userIds,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		List<String> usersToDelete = new ArrayList<String>();
		for (String userId : userIds) {
			if(!userId.equals(user.getUserId())) {
				usersToDelete.add(userId);
			}
		}
		try {
			userManagementService.setUsersStatus(usersToDelete, active);
			return updateUsersDetailsView(userType, filterOptions, sort, ascending,
					status, searchText, pageNo, modelMap, user);
		} catch (Exception exp) {
			LOGGER.error("Error activating profileIds (" + userIds + ") ", exp);
			return FAILURE;
		}
	}
	
	@RequestMapping(value = "/filterUsers", method = RequestMethod.POST)
	public String filterUsers(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userType") String userType,
			@RequestParam(value = "filterOptions", required=false) List<String> filterOptions,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "ascending", required = false) Boolean ascending,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "searchText", required = false) String searchText,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			 ModelMap modelMap) {

		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

		try {
			return updateUsersDetailsView(userType, filterOptions, sort, ascending,
					status, searchText, pageNo, modelMap, user);
		} catch (Exception exp) {
			LOGGER.error("Error setting filters ", exp);
			return FAILURE;
		}
	}
	
	@RequestMapping("/editUser")
	public String createUpdateUser(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "userType", required = false) String userType,
			@RequestParam(value = "supplierId", required = false) String supplierId,
			@RequestParam(value = "unitId", required = false) String unitId,

			ModelMap modelMap) {
		
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		
		if(getManageableNormalRoles(user).size()<=0) {
			modelMap.addAttribute("errorMsg", (Object) "You are not authorized to access this page.");
			return FAILURE;
		}
		
		User userToAdd = new User();
		List<ProfileProxy> userProfiles = new ArrayList<ProfileProxy>();
        List<ProfileProxy> finalUserProfiles = new ArrayList<ProfileProxy>();
		try {
			MyAccountPost accountInfo = userManagementService.getDropDownData(user.getUnitId());
			
			List<Language> languagesList = accountInfo.getLanguagesList();
			List<DecimalNotation> decimalNotationList = accountInfo.getDecimalNotationList();
			List<TimeZone> timeFormatList = accountInfo.getTimeFormatList();
			List<DateFormat> dateFormatList = accountInfo.getDateFormatList();
		        
			if(userId != null) {
				userToAdd = getUser(userId, user.getUnitId());
				if(!editAuthorized(user, userToAdd)) {
					LOGGER.error("Insufficient rights to create user.");
					return FAILURE;
				}
                if(userToAdd.getAssignedProfiles()!=null && userToAdd.getAssignedProfiles().size()>0) {
                    userProfiles = profileService.getProfilesByIds(userToAdd.getAssignedProfiles());
                }
                if(!user.getRoles().contains(Role.MASTER_ADMINISTRATOR)){
                    List<Integer> profilesOfThisUser = user.getAssignedProfiles();

                    for(ProfileProxy profile:userProfiles){

                        if(profilesOfThisUser.contains(profile.getProfileId())){
                            finalUserProfiles.add(profile);
                        }

                    }
                    userProfiles= finalUserProfiles;
                }

			} else {
				if(!createAuthorized(user, userToAdd)) {
					LOGGER.error("Insufficient rights to create user.");
					return FAILURE;
				}

				// TODO set these settings from user company.
				userToAdd.setLanguage(languagesList.get(0).getLanguageId()+"");
				userToAdd.setDateFormat(dateFormatList.get(0).getDateId()+"");
				userToAdd.setDecimalNotation(decimalNotationList.get(0).getDecimalId()+"");
				userToAdd.setTimeZone(timeFormatList.get(0).getTimeZoneId()+"");
			}
			
			modelMap.addAttribute("userRoles", user.getRoles());
			modelMap.addAttribute("user", (Object) userToAdd);
			modelMap.addAttribute("userProfiles", (Object) userProfiles);
	        modelMap.addAttribute("userName", user.getFirstName()+" "+user.getLastName());
	        modelMap.addAttribute("languagesList", languagesList);
            modelMap.addAttribute("decimalNotationList", decimalNotationList);
            modelMap.addAttribute("timeFormatList", timeFormatList);
            modelMap.addAttribute("dateFormatList", dateFormatList);
            modelMap.addAttribute("contentViewGroupList", accountInfo.getContentViewGroups());
            modelMap.addAttribute("isShopper",isShopper(userToAdd.getRoles()));
            modelMap.addAttribute("isSupplier",isSupplierAdmin(userToAdd.getRoles()));
            boolean isShopper = isShopper(userToAdd.getRoles());
            if(isShopper){
            	ContentAccessDTO dto = profileService.findProfilesByUser(Integer.parseInt(user.getUnitId()), userToAdd.getUsername(), userToAdd.getLastLoginCGRoupToken());
            	if(null != dto.getGroupToken()){
            		modelMap.addAttribute("groupToken",dto.getGroupToken());
            	}
            	if(null != dto.getProfiles()){
            		modelMap.addAttribute("userProfiles",dto.getProfiles());
            	}
            	
            }
            
            
            modelMap.addAttribute("unitID",unitId == null? "":unitId);
            modelMap.addAttribute("supplierID",supplierId==null?"":supplierId);
            modelMap.addAttribute("shopperViewUrl", getShopperViewUrl(user.getUnitId()));
		} catch (Exception e) {
			LOGGER.error("Error retrieving profiles",e);
		}
        
		return USER_EDIT;
	}
	
	
	private boolean isShopper(List<Role> roles){
		if(roles != null){
			for (Role role : roles) {
				if(role.toString().equals(Role.SHOPPER.toString())){
					return true;
				}
			}
		}		
		return false;
	}

	private boolean isSupplierAdmin(List<Role> roles){
		if(roles != null){
			for (Role role : roles) {
				if(role.toString().equals(Role.SUPPLIER_ADMIN.toString())){
					return true;
				}
			}
		}		
		return false;
	}

	
	private boolean isAdmin(List<Role> roles){
		if(roles != null){
			for (Role role : roles) {
				if(role.toString().equals(Role.ADMINISTRATOR.toString())){
					return true;
				}
			}
		}		
		return false;
	}

	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String addUpdateUser(HttpServletRequest request,
			@ModelAttribute User userToAdd, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);

		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		try {
			boolean newUser = (userToAdd.getUserId() == null || userToAdd.getUserId().equals(""));
			boolean resetPassword = false;
			if((newUser && !createAuthorized(user, userToAdd)) || (!newUser && !editAuthorized(user, userToAdd))) {
				LOGGER.error("Insufficient rights to create user.");
				return FAILURE;
			}
			if(newUser) { //Add new user
				
				if(!userExists(null, userToAdd.getUsername(), user.getUnitId())) {
					UserDetail details = new UserDetail();
					details.setContactName(userToAdd.getFirstName()+" "+userToAdd.getLastName());
					userToAdd.setDetails(details);
					userToAdd.setCreatedBy(user.getUserId());
					userToAdd.setUnitId(user.getUnitId());
					userToAdd.setCreatedOn(new Date());
					userToAdd.setResetPassword(true);
					if(userToAdd.getPassword() == null || userToAdd.getPassword().trim().equals("")) {
						userToAdd.setPassword(RandomStringUtils.randomAlphanumeric(16));
						resetPassword = true;
					}
					userToAdd.setActive(true);
					userToAdd.setDeleted(false);
					userToAdd.setLoggedIn(false);
					userToAdd = userManagementService.addUpdateUser(userToAdd);
				} else {
					return FAILURE;
				}
			} else { //Update existing user
				User us = getUser(userToAdd.getUserId(), user.getUnitId());
				us.setUsername(userToAdd.getUsername());
				us.setFirstName(userToAdd.getFirstName());
				us.setLastName(userToAdd.getLastName());
				us.setLanguage(userToAdd.getLanguage());
				us.setDecimalNotation(userToAdd.getDecimalNotation());
				us.setDateFormat(userToAdd.getDateFormat());
				us.setTimeZone(userToAdd.getTimeZone());
				us.setRoles(userToAdd.getRoles());
				us.setContentViewGroupToken(userToAdd.getContentViewGroupToken());
				us.setShopperViewURL(userToAdd.getShopperViewURL());
				if(userToAdd.getPassword()!=null && !userToAdd.getPassword().trim().equals("")){
					us.setPassword(userToAdd.getPassword());
				}
				userToAdd = userManagementService.addUpdateUser(us);
			}
			
			if(resetPassword) {
				notificationService.sendNewUserNotification(userToAdd, user);
			}
			
			String userType = UserBean.isSystemUser(userToAdd)? "system": "normal";
			
			return userManagementView(modelMap, user, userType);
		} catch (Exception exp) {
			LOGGER.error("Error creating user ", exp);
			return FAILURE;
		}
	}

	@RequestMapping(value = "/usernameExists", method=RequestMethod.POST)
    @ResponseBody public String usernameExists(HttpServletRequest request, 
    		@RequestParam(required=false) String userId, @RequestParam String username, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
    	return userExists(userId, username, user.getUnitId())? "true": "false";
    }

	/**
	 * Search user by given keyword and roles.
	 * @param keyword Search query
	 * @param roles Comma separated list of roles to search. 
	 * @param currentPage
	 * @param pageSize
	 * @return List of users matching the criteria
	 */
    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    @ResponseBody
    public List<User> searchUser(HttpServletRequest request, 
        @RequestParam(value = "keyword", required=false) String keyword,
        @RequestParam(value = "roles", required=false) String roles,
        @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
      User user = SessionDataRetriever.getLoggedInUser(request);
  
      return userManagementService.searchUsers(user.getUnitId(), keyword, roles, currentPage, pageSize);
    }
	   
	@RequestMapping(value = "/resetPassword", method=RequestMethod.POST)
    @ResponseBody public String resetPassword(HttpServletRequest request, @RequestParam String userId, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

		User userToAdd = getUser(userId, user.getUnitId());
		userToAdd.setResetPassword(true);
		userToAdd.setPassword(RandomStringUtils.randomAlphanumeric(16));
		
		userToAdd = userManagementService.addUpdateUser(userToAdd);
		
		notificationService.sendResetPasswordNotification(userToAdd, user);
		
		return "ok";
    }

	/**
	 * Creates Add Profile Popup view
	 */
	@RequestMapping(value = "/addUserProfiles", method = RequestMethod.GET)
	public String addUserProfiles(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "assignedProfiles", required = false) List<Integer> profileIds,
			ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		
		return addUserProfilesView(userId, profileIds, modelMap, user, 0);
	}
	
	@RequestMapping("/navigateProfilePage")
	public String navigateProfilePage(HttpServletRequest request,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "assignedProfiles", required = false) List<Integer> profileIds,
			@RequestParam(value = "page") String page,
			HttpServletResponse response, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

		int p = 0;
		try {
			p = Integer.parseInt(page);
		} catch (Exception e) {
			LOGGER.error("Invalid page", e);
		}
		
		return addUserProfilesView(userId, profileIds, modelMap, user, p);
	}

	@RequestMapping(value = "/assignUserProfiles", method = RequestMethod.POST)
	public String assignUserProfiles(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "assignedProfiles") List<Integer> profileIds,
			ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

		User userToUpdate = new User();
		if(userId != null && !userId.equals("")) {
			userToUpdate = getUser(userId, user.getUnitId());
		}

//		if( user.getAssignedProfiles()== null) {
			userToUpdate.setAssignedProfiles(new ArrayList<Integer>());
//		}
		for (Integer pid : profileIds) {
			if(!userToUpdate.getAssignedProfiles().contains(pid)) {
				userToUpdate.getAssignedProfiles().add(pid);
			}
		}
		if(userId !=null && userId.length()>0) {
			userManagementService.setUserProfiles(userId, userToUpdate.getAssignedProfiles());
		}
		return userProfilesView(modelMap, userToUpdate.getAssignedProfiles());
	}

	@RequestMapping(value = "/deleteUserProfiles", method = RequestMethod.POST)
	public String deleteUserProfiles(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "assignedProfiles") List<Integer> profileIds,
			ModelMap modelMap) {
		
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

		User userToUpdate = null;
		if(userId !=null && userId.length()>0) {
			userToUpdate = getUser(userId, user.getUnitId());
			for (Integer pid : profileIds) {
				if(userToUpdate.getAssignedProfiles().contains(pid)) {
					userToUpdate.getAssignedProfiles().remove(pid);
				}
			}
			userManagementService.setUserProfiles(userId, userToUpdate.getAssignedProfiles());
		}
		return userProfilesView(modelMap, userToUpdate.getAssignedProfiles());
	}
	
	@RequestMapping(value = "/addUserProfile", method = RequestMethod.POST)
    public String addUserProfile(HttpServletRequest request,
                           @ModelAttribute ProfileProxy profile, 
                           @RequestParam(value = "assignedProfiles", required = false) List<Integer> profileIds,
                           ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		String userId = request.getParameter("userId");

		try {
		   profile.setCreatedBy(user.getUsername());
      	   profile.setCreatedByName(user.getFullName());
      	   profile.setUnitId(user.getUnitId());
      	   profile.setActive(true);
      	   profile.setCreatedOn(new Date());
      	   List<String>  associatedUsers = new ArrayList<String>();
      	   associatedUsers.add(userId);
//      	   profile.setAssociatedUsers(userId);
      	   profile = profileService.addProfile(profile);
      	   
			List<Integer> assignedProfiles = new ArrayList<Integer>();
			if (profileIds != null) {
				assignedProfiles.addAll(profileIds);
			}
			assignedProfiles.add(profile.getProfileId());
			if (userId != null && userId.length() > 0) {
				userManagementService.setUserProfiles(userId, assignedProfiles);
			}
			return userProfilesView(modelMap, assignedProfiles);
		} catch (Exception exp) {
			LOGGER.error("Error creating profile ", exp);
		}
		return FAILURE;
	}

	@RequestMapping(value = "/verifyUserPassword", method=RequestMethod.POST)
    @ResponseBody public String verifyUserPassword(HttpServletRequest request, @RequestParam String username,
    		@RequestParam String password, ModelMap modelMap) {
		
        User loggedInUser = SessionDataRetriever.getLoggedInUser(request);
        
		User user = userManagementService.getUserByUserName(username, loggedInUser.getUnitId());
		if(user!=null && user.getPassword().equals(password)) {
			return "true";
		}
		
    	return "false";
    }


	private List<Role> getManageableNormalRoles(User user) {
		List<Role> mroles = new ArrayList<Role>();
		if(!user.getRoles().contains(Role.MASTER_ADMINISTRATOR)
				&& !user.getRoles().contains(Role.ADMINISTRATOR)) {
			return mroles;
		}
			
		if(user.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {
				mroles.add(Role.MASTER_ADMINISTRATOR);
		}
		mroles.add(Role.ADMINISTRATOR);
		
		mroles.add(Role.APPROVER);
//		mroles.add(Role.ROLE_SUPPLIER);
//		mroles.add(Role.ROLE_SUPPLIER_ADMIN);
		mroles.add(Role.BUYER);
		mroles.add(Role.SHOPPER_VIEW_ONLY);
		
		return mroles;
	}

	private List<Role> getManageableSystemRoles(User user) {
		List<Role> mroles = new ArrayList<Role>();
		if(!user.getRoles().contains(Role.MASTER_ADMINISTRATOR)
				&& !user.getRoles().contains(Role.ADMINISTRATOR)) {
			return mroles;
		}
		
		mroles.add(Role.SHOPPER);
//		mroles.add(Role.WEB_SEARCH_USER);
		
		return mroles;
	}

	public static boolean editAuthorized(User user, User userToAdd) {
		if(user.getRoles().contains(Role.MASTER_ADMINISTRATOR)
				|| ((user.getRoles().contains(Role.ADMINISTRATOR)
						&& !userToAdd.getRoles().contains(Role.MASTER_ADMINISTRATOR)
						&& !user.getUserId().equals(userToAdd.getUserId()) ))) {
			return true;
		}
		return false;
	}
    public static boolean createAuthorized(User user, User userToAdd) {
		if(user.getRoles().contains(Role.ADMINISTRATOR)
				|| user.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {
			return true;
		}
		return false;
	}
	
	private UsersPage getFilteredUsers(String userType,
			List<String> filterOptions, String sort, Boolean ascending, Integer status,
			String searchText, User loggedInUser, Integer pageNo) {
		
		UserFilter userFilter = new UserFilter();
		if(sort != null && !sort.equals("0")) {
			userFilter.setSortField(sort);
			userFilter.setSortAscending(ascending!=null & ascending==true);
		}
		if(status!=null) {
			userFilter.setActive(status == 1 || status == 2);
			userFilter.setInactive(status == 1 || status == 3);
		}
		if(searchText!=null) {
			if(searchText.equals("Search Users")) {
				searchText = "";
			}
			userFilter.setSearchText(searchText);
		}
		
		List<Role> roles;
		if(userType.equals("system")) {
			roles = getManageableSystemRoles(loggedInUser);
		} else {
			roles = getManageableNormalRoles(loggedInUser);
		}
		if(filterOptions!=null) {
			for (int i = 0; i < roles.size(); i++) {
				if(!filterOptions.contains(roles.get(i).toString())) {
					roles.remove(i--);
				}
			}
		}
		
		roles = invertRoles(roles);
		userFilter.setRoles(roles);
//		if(!loggedInUser.getRoles().contains(Role.ROLE_MASTER_ADMIN)) {
//			List<String> excludedUserIds = new ArrayList<String>();
//			excludedUserIds.add(loggedInUser.getUserId());
//			userFilter.setExcludedUserIds(excludedUserIds);
//		}
		
		if(loggedInUser.getRoles().contains(Role.ADMINISTRATOR)
				&& !loggedInUser.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {
			
			List<Integer> assignedProfiles = new ArrayList<Integer>();
			if(loggedInUser.getAssignedProfiles() != null) {
				assignedProfiles = loggedInUser.getAssignedProfiles();
			}
			userFilter.setAdminProfiles(assignedProfiles);
			userFilter.setCreatedBy(loggedInUser.getUserId());
		}
		
		
		
		if(pageNo == null || pageNo<=0) { pageNo = 1; }
		userFilter.setPageNumber(pageNo);
		
		int pageSize = loggedInUser.getRowsPerPage()>0?loggedInUser.getRowsPerPage():8;
		userFilter.setPageSize(pageSize);


		UsersPage usersPage =null;
        try{
            usersPage = userManagementService.getFilteredUsers(loggedInUser.getUnitId(), userFilter);
            
            
            
            
            if(userType.equals("system")) {
            	int systemUserCount = 0;
//    	        if(userFilter.getRoles().contains(Role.ROLE_SYSTEM_SHOPPER)) {
    	        	systemUserCount += usersPage.getSystemShopperCount();
//    	        }
    	        	
    	        if(!loggedInUser.getRoles().contains(Role.ADMINISTRATOR)
    	    				&& !loggedInUser.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {    	        	
    	        	
//    	        if(userFilter.getRoles().contains(Role.ROLE_WEB_SEARCH_USER)) {
    	        	systemUserCount += usersPage.getWebSearchUserCount();
//        	        }
    	        	
    	        	
    	        }

    	        usersPage.setSystemUserCount(systemUserCount);
    		} else {
    			int normalUserCount = 0;
    			
    	        if(loggedInUser.getRoles().contains(Role.ADMINISTRATOR)
    	    				&& !loggedInUser.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {

    	        	normalUserCount += usersPage.getBuyerCount();
    	        	normalUserCount += usersPage.getApproverCount();
    	        	
    	        }else{
    	            normalUserCount += usersPage.getAdministratorCount();
    	        	normalUserCount += usersPage.getMasterAdminCount();
    	        	normalUserCount += usersPage.getBuyerCount();
    	        	normalUserCount += usersPage.getApproverCount();
//    	        	normalUserCount += usersPage.getSupplierCount();
    	        	normalUserCount += usersPage.getShopperViewOnlyCount();
    	        }
    	        usersPage.setNormalUserCount(normalUserCount);
    	        
    		}
	        
        }catch (Exception e){
            LOGGER.error("GET_USERS",e);
        }
		
		return usersPage;
	}
	
	private List<Role> invertRoles(List<Role> roles) {
		List<Role> nroles = new ArrayList<Role>(Arrays.asList(Role.values()));
		for (int i = 0; i < roles.size(); i++) {
			nroles.remove(nroles.indexOf(roles.get(i)));
		}
		return nroles;
	}

	private boolean userExists(String userId, String username, String unitid) {
    	User user = userManagementService.getUserByUserName(username, unitid);
    	SupplierUser tempUser = userManagementService.getTempUserByName(username, unitid);
    	
		return ( (user!=null && user.getUserId()!=null && (userId==null || userId.equals("") || !user.getUserId().equals(userId))) ||
				(tempUser!=null && tempUser.getUserId()!=null && (userId==null || userId.equals("") || !tempUser.getUserId().equals(userId))) );
		
	}

	private User getUser(String userId, String unitId) {
//		return userManagementService.getUser(Integer.parseInt(userId));
//		TODO Rectify getUserById
		
//		List<User> users = userManagementService.getAllUsers(unitId);
//		for (User user : users) {
//			if(user.getUserId().equals(userId)) {
//				return user;
//			}
//		}
		
		return userManagementService.getUserDetails(Integer.parseInt(unitId), userId);
	}

	private String userManagementView(ModelMap modelMap, User user, String userType) {
		
		if(getManageableNormalRoles(user).size()<=0) {
			modelMap.addAttribute("errorMsg", (Object) "You are not authorized to access this page.");
			return FAILURE;
		}

		UsersPage normalUserPage = getFilteredUsers("normal", null, null, null, null, null, user, 1);
		UsersPage systemUserPage = getFilteredUsers("system", null, null, null, null, null, user, 1);
		
		modelMap.addAttribute("normalUserPage", (Object) normalUserPage);
		modelMap.addAttribute("systemUserPage", (Object) systemUserPage);
		
		modelMap.addAttribute("userName", user.getFirstName()+" "+user.getLastName());
		modelMap.addAttribute("loggedInUserId", user.getUserId());
		modelMap.addAttribute("userRoles", user.getRoles());
		modelMap.addAttribute("userType", userType);


		modelMap.addAttribute("webSearchUserCount", systemUserPage!=null?(Object) systemUserPage.getWebSearchUserCount():0);
		modelMap.addAttribute("systemShopperCount", systemUserPage!=null?(Object) systemUserPage.getSystemShopperCount():0);
		modelMap.addAttribute("totalSystemCount", systemUserPage!=null?(Object) systemUserPage.getSystemUserCount():0);
			
		modelMap.addAttribute("masterAdminCount", normalUserPage!=null?(Object) normalUserPage.getMasterAdminCount():0);
		modelMap.addAttribute("administratorCount", normalUserPage!=null?(Object) normalUserPage.getAdministratorCount():0);
		modelMap.addAttribute("buyerCount", normalUserPage!=null?(Object) normalUserPage.getBuyerCount():0);
		
		modelMap.addAttribute("approverCount", normalUserPage!=null?(Object) normalUserPage.getApproverCount():0);
		modelMap.addAttribute("supplierCount", normalUserPage!=null?(Object) normalUserPage.getSupplierCount():0);
		modelMap.addAttribute("shopperViewOnlyCount", normalUserPage!=null?(Object) normalUserPage.getShopperViewOnlyCount():0);
		modelMap.addAttribute("totalNormalCount", normalUserPage!=null?(Object) normalUserPage.getNormalUserCount():0);
		modelMap.addAttribute("isAdminLogedin",isAdmin(user.getRoles()));
//	    modelMap.addAttribute("masterAdmin", (Boolean) user.getRoles().contains(Role.ROLE_MASTER_ADMIN));
//		modelMap.addAttribute("administrator", (Boolean) true);
//		modelMap.addAttribute("approver", (Boolean) true);
//		modelMap.addAttribute("buyer", (Boolean) true);
//		modelMap.addAttribute("supplier", (Boolean) true);
//		modelMap.addAttribute("webSearchUser", (Boolean) true);
//		modelMap.addAttribute("systemShopper", (Boolean) true);
		
		return USER_MANAGEMENT;
	}
	
	private String updateUsersDetailsView(String userType,
			List<String> filterOptions, String sort, Boolean ascending, Integer status,
			String searchText, Integer pageNo, ModelMap modelMap, User loggedInUser) {
		
		UsersPage pagedList = getFilteredUsers(userType, filterOptions, sort, ascending, status, searchText, loggedInUser, pageNo);
		
		modelMap.addAttribute("usersPage", (Object) pagedList);
		modelMap.addAttribute("userType", (Object) userType);
		modelMap.addAttribute("loggedInUserId", loggedInUser.getUserId());
		modelMap.addAttribute("userRoles", loggedInUser.getRoles());
		
		modelMap.addAttribute("webSearchUserCount", (Object) pagedList.getWebSearchUserCount());
		modelMap.addAttribute("systemShopperCount", (Object) pagedList.getSystemShopperCount());
		modelMap.addAttribute("totalSystemCount", (Object) pagedList.getSystemUserCount());
			
		modelMap.addAttribute("masterAdminCount", (Object) pagedList.getMasterAdminCount());
		modelMap.addAttribute("administratorCount", (Object) pagedList.getAdministratorCount());
		modelMap.addAttribute("buyerCount", (Object) pagedList.getBuyerCount());
		modelMap.addAttribute("approverCount", (Object) pagedList.getApproverCount());
		modelMap.addAttribute("supplierCount", (Object) pagedList.getSupplierCount());
		modelMap.addAttribute("shopperViewOnlyCount", (Object) pagedList.getShopperViewOnlyCount());
		modelMap.addAttribute("totalNormalCount", (Object) pagedList.getNormalUserCount());
		modelMap.addAttribute("isAdminLogedin",isAdmin(loggedInUser.getRoles()));
		return USER_DETAIS_SECTION;
	}

	private String userProfilesView(ModelMap modelMap, List<Integer> assignedProfiles) {
		List<ProfileProxy> userProfiles = new ArrayList<ProfileProxy>();
		try {
			if(assignedProfiles!=null && assignedProfiles.size()>0) {
				userProfiles = profileService.getProfilesByIds(assignedProfiles);
			}
		} catch (Exception e) {
			LOGGER.error("Error in adding profiles to user",e);
		}
		
		modelMap.addAttribute("userProfiles", (Object) userProfiles);
		return USER_EDIT_PROFILES;
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
	
	private String addUserProfilesView(String userId, List<Integer> profileIds,
			ModelMap modelMap, User user, int page) {
		try {
			
			

	    	List<Role> userRoles = user.getRoles();
	    	boolean isMaster = false;
	    	for (Role role : userRoles) {
				if(role == role.MASTER_ADMINISTRATOR){
					isMaster = true;
					break;
				}
			}
	    	
	    	List<Profile> allProfiles = new ArrayList<Profile>();
	    	if(isMaster){
	    		allProfiles = profileService.getActiveProfilesByUnitId(user.getUnitId());
	    	}else{
	    		if(user.getAssignedProfiles()!=null && !user.getAssignedProfiles().isEmpty()){
		    		List<ProfileProxy> allProfileProxies = profileService.getProfilesByIds(user.getAssignedProfiles());
		    		allProfiles = convertToProfileList(allProfileProxies);
	    		}
	    	}
					
			
			
			Set<Integer> assignedProfileIds = new HashSet<Integer>();
			List<Profile> profiles = new ArrayList<Profile>();
			if(profileIds!=null && profileIds.size()>0) {
				assignedProfileIds.addAll(profileIds);
			}
			if(userId != null && !userId.equals("")) {
				User userToAdd = getUser(userId, user.getUnitId());
				if(userToAdd.getAssignedProfiles()!=null && userToAdd.getAssignedProfiles().size()>0) {
					assignedProfileIds.addAll(userToAdd.getAssignedProfiles());
				}
			}
			for (Profile profile : allProfiles) {
				if(!assignedProfileIds.contains(profile.getProfileId())) {
					profiles.add(profile);
				}
			}
			PagedListHolder<Profile> profileListHolder = new PagedListHolder<Profile>(profiles);
			profileListHolder.setPage(page);
			int pageSize = user.getRowsPerPage()>0?user.getRowsPerPage():8;
			profileListHolder.setPageSize(pageSize);
			modelMap.addAttribute("profilesForCompany", (Object) profileListHolder);
			
			int nextPage = page >= profileListHolder.getPageCount() ? profileListHolder.getPageCount(): page+1;
			int prevPage = page <=0 ? 0: page-1;
			
			
			modelMap.addAttribute("nextUserProfilesPage", (Object) nextPage);
			modelMap.addAttribute("prevUserProfilesPage", (Object) prevPage);
		} catch (Exception e) {
			LOGGER.error("Error retrieving profiles", e);
			return FAILURE;
		}
	
		return USER_ADD_PROFILES;
	}
	
	@RequestMapping("myAccount")
	public String loadMyAccountData(HttpServletRequest request , ModelMap map , HttpServletResponse response){
		
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		
		return myAccountView(map, user);
	}

	@RequestMapping(value = "/myAccount", method = RequestMethod.POST)
	public @ResponseBody String updateMyAccount(HttpServletRequest request , ModelMap map , HttpServletResponse response,
			@ModelAttribute User userToUpdate, 
			@ModelAttribute UserDetail details, @ModelAttribute(value = "pwdNew") String newPwd){

		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}

        try{
		user.setUsername(userToUpdate.getUsername());
		user.setTimeZone(userToUpdate.getTimeZone());
		user.setLanguage(userToUpdate.getLanguage());
		user.setDateFormat(userToUpdate.getDateFormat());
		user.setDecimalNotation(userToUpdate.getDecimalNotation());
		user.setRowsPerPage(userToUpdate.getRowsPerPage());
		

		
		if(!details.getCountry().equals("1")){
			String state = request.getParameter("stateCounty");
			details.setState(state == null ? "" : state);
		}
		user.setDetails(details);
		
		if(newPwd !=null && newPwd.length() > 0){ 
			user.setPassword(newPwd);
			notificationService.sendResetPasswordNotification(user, user);
		}
		
		userManagementService.addUpdateUser(user);


        }catch (Exception exp){
            return "Error: " + exp.getMessage();
        }

        return "Success";
	}
	
	private String myAccountView(ModelMap map, User user) {
		MyAccountPost accountInfo = userManagementService.getDropDownData(user.getUnitId());
		map.addAttribute("languagesList", accountInfo.getLanguagesList());
		map.addAttribute("decimalNotationList", accountInfo.getDecimalNotationList());
		map.addAttribute("timeFormatList", accountInfo.getTimeFormatList());
		map.addAttribute("dateFormatList", accountInfo.getDateFormatList());
		map.addAttribute("contentViewGroupList", accountInfo.getContentViewGroups());
        
		map.addAttribute("countryList", accountInfo.getCountriesList());
		map.addAttribute("stateList", accountInfo.getStateList());
		map.addAttribute("rowsList", accountInfo.getRowsPerPageList());
		
		User userDetail = userManagementService.getUserDetails(Integer.parseInt(user.getUnitId()), user.getUserId());
		if(userDetail.getDetails()==null) {
			userDetail.setDetails(new UserDetail());
		}
		if(userDetail.getDetails().getContactName() == null || userDetail.getDetails().getContactName().equals("")) {
			userDetail.getDetails().setContactName(userDetail.getFirstName() +" "+userDetail.getLastName());
		}
		if(userDetail.getDetails().getCountry()==null || userDetail.getDetails().getCountry().equals("")) {
			userDetail.getDetails().setCountry("233");
		}
		
		map.addAttribute("shopperViewUrl", getShopperViewUrl(user.getUnitId()));
		map.put("userDetail", userDetail);
		
		return MY_ACCOUNT_PAGE;
	}
	
    private String getShopperViewUrl(String unitId) {
        CompanySettings companySettings = companyService.getCompanySettingsByUnitId(unitId);
        String shopperViewUrl = appConfig.ssoPath+"/oci?USERNAME="+companySettings.getUserName()+"&PASSWORD="+companySettings.getPassword()+"&COMPANYCODE="+companySettings.getCompanyCode();
        return shopperViewUrl;
    }

}
