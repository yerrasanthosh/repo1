package com.vroozi.customerui.user.services.rest;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.myaccount.model.MyAccountPost;
import com.vroozi.customerui.notification.services.NotificationService;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.security.controller.model.UserSessionData;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.user.services.exception.UserServiceException;
import com.vroozi.customerui.user.services.user.model.Result;
import com.vroozi.customerui.user.services.user.model.SupplierUser;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.user.services.user.model.UserFilter;
import com.vroozi.customerui.user.services.user.model.UsersDTO;
import com.vroozi.customerui.user.services.user.model.UsersPage;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RestServiceUrl;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author mhabib
 *
 */
public class UserRestClientImpl implements UserRestClient {
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;

    @Autowired
    NotificationService notificationService;

    @Autowired
    CompanyService companyService;

    @Autowired
    AppConfig appConfig;

    // Default Constructor
    public UserRestClientImpl() {
    }

    @Override
    public MyAccountPost getDropDownData(String unitId) throws UserServiceException {
    	try{
    	RestTemplate restTemplate = new RestTemplate();
    	MyAccountPost accountData = restTemplate.getForObject(restServiceUrl.userDataServiceURI()+"/myAccountData?unitid="+unitId, MyAccountPost.class);
    	return accountData;
    	}catch (Exception e) {
    		e.printStackTrace();
    		return null;
		}
    }    

    
    @Override
    public User getUser(String userId) throws Exception {
        User user = null;

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        user = restTemplate.getForObject(restServiceUrl.userDataServiceURI()+ "/users/userId/" + userId, User.class);

        return user;
    }

    public User getUserByUserName(String userName, String unitid){
        User user = null;

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        User[] userArray = restTemplate.getForObject(restServiceUrl.userDataServiceURI()
                + "/users?username="+userName+"&unitid="+unitid, User[].class);

        if (userArray!=null && userArray.length>0) {
            user = userArray[0];
        }

        ///users/username/{username}
        return user;
    }

    @Override
    public SupplierUser getTempUserByName(String userName, String unitId) {
    	SupplierUser user = null;
        RestTemplate restTemplate = new RestTemplate();
        user = restTemplate.getForObject(restServiceUrl.userDataServiceURI()+ "/tempusers?username="+userName+"&unitid="+unitId, SupplierUser.class);
        return user;
    }
    
    @Override
    public SupplierUser getTempUserById(String userId) {
    	SupplierUser user = null;
        RestTemplate restTemplate = new RestTemplate();
        user = restTemplate.getForObject(restServiceUrl.userDataServiceURI()+ "/tempusers/"+userId, SupplierUser.class);
        return user;
    }

    @Override
    public User authenticate(User user) throws Exception {
        return new RestTemplate().postForObject(restServiceUrl.loginDataServiceURI(), user, User.class);
    }
    @Override
    public User forgotPassword(User user) throws Exception{
        return new RestTemplate().postForObject(restServiceUrl.forgotPasswordURI(), user, User.class);
    }

    @Override
    public String getSupplierName(String supplierId) throws Exception {
        String supplierName = "";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        supplierName = restTemplate.getForObject(restServiceUrl.userDataServiceURI()
                + "/supplierName/" + supplierId, String.class);

        return supplierName;
    }

    @Override
    public Boolean checkSession(String id, String sessionId) throws Exception {
        if(id==null || id.isEmpty() || sessionId==null || sessionId.isEmpty()){
            return false;
        }

        RestTemplate restTemplate = new RestTemplate();
        String loggedInUser = restTemplate.getForObject(restServiceUrl.userDataServiceURI()
                + "/user/{id}/checkSession/{sessionId}", String.class, id, sessionId);

        return (loggedInUser.equalsIgnoreCase("true"));
    }

    @Override
    public List<User> getApproverListForUser(String unitId, String userId)throws Exception {
        User[] approverList = new User[0];
        String roles  =Role.APPROVER.toString() +","+ Role.MASTER_ADMINISTRATOR.toString();
        approverList = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/organization/{unitid}/roles/{roleids}/curuser/{curuser}/users/", User[].class, unitId, roles,userId);
        return  new ArrayList<User>(Arrays.asList(approverList));
    }
    
    @Override
    public List<User> getApproverListForCompany(String unitId) throws Exception {
        User[] approverList = new User[0];

        String roles  =Role.APPROVER.toString() +","+ Role.MASTER_ADMINISTRATOR.toString();
        approverList = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/organization/{unitId}/roles/{roles}/users", User[].class, unitId,roles);

        return  new ArrayList<User>(Arrays.asList(approverList));
    }

    @Override
    public List<Profile> getProfileListForCompany(String unitId) throws Exception{
        Profile[] profileList = new Profile[0];

        profileList = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/profiles/{unitId}", Profile[].class, unitId);

        return  new ArrayList<Profile>(Arrays.asList(profileList));
    }

    @Override
    public List<Supplier> getAllSuppliers(String unitId) throws Exception {

        Supplier[] supplierCompanies = new Supplier[0];

        supplierCompanies = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/suppliers/unitid/"+unitId, Supplier[].class);

        return  new ArrayList<Supplier>(Arrays.asList(supplierCompanies));
    }

    /**
     * Following methods call user services on catalogService
     */
    
	@Override
	public List<User> getAllUsers(String unitId) {
		try {
	        User[] usersList = new User[0];
	
	        usersList = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/unitId/{unitId}", User[].class, unitId);
	
	        return  new ArrayList<User>(Arrays.asList(usersList));
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
	}

	@Override
	public User addUpdateUser(User user) {
		try {
			user = new RestTemplate().postForObject(restServiceUrl.catalogUserURI(), user, User.class);
			return user;
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
	}

	@Override
	public int deleteUsers(List<String> userIds) {
		try {
			String userIdString = StringUtils.join(userIds, ',');
			new RestTemplate().delete(
					restServiceUrl.catalogUserURI() + "/userIds/"+userIdString);
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
	}

	@Override
	public int deleteTempUser(List<String> userIds) {
		try {
			String userIdString = StringUtils.join(userIds, ',');
			new RestTemplate().delete(
					restServiceUrl.catalogUserURI() + "/tempuser/userIds/"+userIdString);
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
	}
	
	@Override
	public int setNewUsersStatus(List<String> userIds, boolean active) {
		try {
			String action = active? "A" : "D";
			String userIdString = StringUtils.join(userIds, ',');
			new RestTemplate().delete(restServiceUrl.catalogUserURI() + "/newuser/status/"+action+"/userIds/"+userIdString);
		} catch (RestClientException rse) {
			LOGGER.error("Error updating user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error updating user!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
	}
	
	@Override
	public int setUsersStatus(List<String> userIds, boolean active) {
		try {
			String action = active? "A" : "D";
			String userIdString = StringUtils.join(userIds, ',');
			new RestTemplate().delete(
					restServiceUrl.catalogUserURI() + "/status/"+action+"/userIds/"+userIdString);
		} catch (RestClientException rse) {
			LOGGER.error("Error updating user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error updating user!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
	}

	@Override
	public void setUserProfiles(String userId, List<Integer> profileIds) {
		try {
			String userIdString = StringUtils.join(profileIds, ',');
			if(userIdString.length()==0) 
				userIdString = "-1";
			new RestTemplate().delete(
					restServiceUrl.catalogUserURI() + "/userId/"+userId+"/profileIds/"+userIdString);
		} catch (RestClientException rse) {
			LOGGER.error("Error updating user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error updating user!", exp);
			throw new AdminUIException(exp);
		}
	}
	
	@Override
	public User getUserDetails(int unitId, String userId) {
        

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
//        /users/unitId/{unitId}/userId/{userId}
        User userDetail = restTemplate.getForObject(restServiceUrl.userDataServiceURI()+ "/users/unitId/" + unitId+"/userId/"+userId, User.class);
        
        return userDetail;
		
	}

	@Override
	public List<User> getUsersByRole(String unitId,
			List<Role> userRoles) {
		try {
			String userRoleString = StringUtils.join(userRoles, ',');
			
			User[] usersList = new User[0];
			
	        usersList = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/organization/"+unitId+ "/roles/"+userRoleString+"/users", User[].class);

            if(usersList!=null){
			    return  new ArrayList<User>(Arrays.asList(usersList));
            }else{
                return  new ArrayList<User>();
            }
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
	}
	
    @Override
	public UsersPage getFilteredUsers(String unitId, UserFilter userFilter) {
		try {
	        User[] usersList = new User[0];
	
	        StringBuffer query = new StringBuffer();
	        
	        query.append("?active="+(userFilter.isActive()?"true":"false"));
	        query.append("&inactive="+(userFilter.isInactive()?"true":"false"));
	        query.append("&pageSize="+userFilter.getPageSize());
	        
	        
	        if(!StringUtils.isEmpty(userFilter.getCreatedBy())){
	        	query.append("&createdBy="+userFilter.getCreatedBy());
	        }
	        
	        if(userFilter.getSearchText()!=null && userFilter.getSearchText().length()>0) {
	        	query.append("&searchText="+userFilter.getSearchText());	
	        }
	        if(userFilter.getSortField()!=null && userFilter.getSortField().length()>0) {
	        	query.append("&sortField="+userFilter.getSortField());
	        	query.append("&sortAscending="+userFilter.isSortAscending());
	        }
	        if(userFilter.getExcludedUserIds()!=null && userFilter.getExcludedUserIds().size()>0) {
	        	String userIdString = StringUtils.join(userFilter.getExcludedUserIds(), ',');
	        	query.append("&excludedUserIds="+userIdString);
	        }
	        
	        
	        if(userFilter.getRoles()!=null) {
	        	String userRoleString = StringUtils.join(userFilter.getRoles(), ',');
	        	query.append("&roles="+userRoleString);	
	        }
	        if(userFilter.getAdminProfiles()!=null) {
	        	String adminProfiles = StringUtils.join(userFilter.getAdminProfiles(), ',');
	        	query.append("&adminProfiles="+adminProfiles);	
	        }
	        
	        Long totalCount = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/unitId/"+unitId+"/count"+query, Long.class);
	        
	        int pageCount = (int) (totalCount / userFilter.getPageSize());
	        if (totalCount > userFilter.getPageSize() * pageCount) {
	            pageCount++;
	        }
	        if(userFilter.getPageNumber() > pageCount && userFilter.getPageNumber()>1) {
	        	userFilter.setPageNumber(pageCount);
	        }
	        
	        usersList = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/unitId/"+unitId+"/page/"+userFilter.getPageNumber()+query, User[].class);
            ArrayList<User> users =null;
            if(usersList != null){
	            users = new ArrayList<User>(Arrays.asList(usersList));
            }else{
                users = new ArrayList<User>();
            }
            

	        Map<String, Integer> userCounts = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/unitId/"+unitId+"/summary"+query, Map.class);
	        
	        UsersPage usersPage = new UsersPage(users, userFilter.getPageNumber(), userFilter.getPageSize(), totalCount);
	        usersPage.setUserCounts(userCounts);
	        
	        return usersPage;

		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
	}
    @Override
    public List<User> getUsersByIds(String unitId,
                                    List<String> ids){
        try {
            String userIdsString = StringUtils.join(ids, ',');

            User[] usersList = new User[0];

            usersList = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/organization/"+unitId+ "/ids/"+userIdsString+"/users", User[].class);

            if(usersList!=null){
                return  new ArrayList<User>(Arrays.asList(usersList));
            }else{
                return  new ArrayList<User>();
            }
        } catch (RestClientException rse) {
            LOGGER.error("Error deleting user!", rse);
            throw rse;
        } catch (Exception exp) {
            LOGGER.error("Error deleting user!", exp);
            throw new AdminUIException(exp);
        }

    }

    ///organization/{unitid}/ids/{ids}/users

    @Override
    public Page<User> getSupplierUserByIds(String unitId, UserFilter filter) {
		try {
			SupplierUser[] usersList = new SupplierUser[0];
	
	        StringBuffer query = new StringBuffer();
	        
	        query.append("?active="+(filter.isActive()?"true":"false"));
	        query.append("&inactive="+(filter.isInactive()?"true":"false"));
	        query.append("&pageSize="+filter.getPageSize());
	        
	        
	        if(!StringUtils.isEmpty(filter.getCreatedBy())){
	        	query.append("&createdBy="+filter.getCreatedBy());
	        }

	        if(filter.getIncludedUserIds() != null && !filter.getIncludedUserIds().isEmpty()){
	        	String userIdString = StringUtils.join(filter.getIncludedUserIds(), ',');
	        	query.append("&userIds="+userIdString);
	        }
	        if(filter.getRoles()!=null) {
	        	String userRoleString = StringUtils.join(filter.getRoles(), ',');
	        	query.append("&roles="+userRoleString);	
	        }
	        
	        Long totalCount = (long)filter.getIncludedUserIds().size();
	        
	        int pageCount = (int) (totalCount / filter.getPageSize());
	        if (totalCount > filter.getPageSize() * pageCount) {
	            pageCount++;
	        }
	        if(filter.getPageNumber() > pageCount && filter.getPageNumber()>1) {
	        	filter.setPageNumber(pageCount);
	        }
	        
	        usersList = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/temp/userIds/unitId/"+unitId+"/page/"+filter.getPageNumber()+query, SupplierUser[].class);

	        ArrayList<User> users =null;
            if(usersList != null){
            	
            	users = new ArrayList<User>();
            	User user = null;
            	
            	for (SupplierUser supplierUsr : usersList) {
    				user = new User();
    				user.setUsername(supplierUsr.getUsername());
    				user.setFirstName(supplierUsr.getFirstName());
    				user.setLastName(supplierUsr.getLastName());
    				user.setLanguage(supplierUsr.getLanguage());
    				user.setDecimalNotation(supplierUsr.getDecimalNotation());
    				user.setDateFormat(supplierUsr.getDateFormat());
    				user.setTimeZone(supplierUsr.getTimeZone());
                    user.setAssignedProfiles(supplierUsr.getAssignedProfiles());
                    user.setCreatedBy(supplierUsr.getCreatedBy());
                    user.setUserId(supplierUsr.getUserId());
                    user.setUnitId(supplierUsr.getUnitId());
                    user.setCreatedOn(new Date());
                    user.setResetPassword(true);
                    user.setPassword(supplierUsr.getPassword());
                    user.setActive(supplierUsr.isActive());
                    user.setCreatedByName(supplierUsr.getCreatedByName());
                    if(user.getRoles() == null){
                    	user.setRoles(new ArrayList<Role>());
                    }

                    user.setRoles(supplierUsr.getRoles());
                    users.add(user);
    			}
            }else{
                users = new ArrayList<User>();
            }
            
            Page<User> userPage = new Page<User>();
            userPage.setPageItems(users);
            userPage.setPageNumber(filter.getPageNumber());
            userPage.setTotalRecords(filter.getIncludedUserIds().size());
            return userPage;

		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
    }
    
    @Override
    public Page<User> getUserByIds(String unitId, UserFilter filter) {
    		try {
    	        User[] usersList = new User[0];
    	
    	        StringBuffer query = new StringBuffer();
    	        
    	        query.append("?active="+(filter.isActive()?"true":"false"));
    	        query.append("&inactive="+(filter.isInactive()?"true":"false"));
    	        query.append("&pageSize="+filter.getPageSize());
    	        
    	        
    	        if(!StringUtils.isEmpty(filter.getCreatedBy())){
    	        	query.append("&createdBy="+filter.getCreatedBy());
    	        }
    	        

/*    	        if(userFilter.getSortField()!=null && userFilter.getSortField().length()>0) {
    	        	query.append("&sortField="+userFilter.getSortField());
    	        	query.append("&sortAscending="+userFilter.isSortAscending());
    	        }
*/
/*    	        if(userFilter.getExcludedUserIds()!=null && userFilter.getExcludedUserIds().size()>0) {
    	        	String userIdString = StringUtils.join(userFilter.getExcludedUserIds(), ',');
    	        	query.append("&excludedUserIds="+userIdString);
    	        }*/
    	        
    	        if(filter.getIncludedUserIds() != null && !filter.getIncludedUserIds().isEmpty()){
    	        	String userIdString = StringUtils.join(filter.getIncludedUserIds(), ',');
    	        	query.append("&userIds="+userIdString);
    	        }
    	        if(filter.getRoles()!=null) {
    	        	String userRoleString = StringUtils.join(filter.getRoles(), ',');
    	        	query.append("&roles="+userRoleString);	
    	        }
    	        
    	        Long totalCount = (long)filter.getIncludedUserIds().size();
    	        
    	        int pageCount = (int) (totalCount / filter.getPageSize());
    	        if (totalCount > filter.getPageSize() * pageCount) {
    	            pageCount++;
    	        }
    	        if(filter.getPageNumber() > pageCount && filter.getPageNumber()>1) {
    	        	filter.setPageNumber(pageCount);
    	        }
    	        
    	        usersList = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/userIds/unitId/"+unitId+"/page/"+filter.getPageNumber()+query, User[].class);
                ArrayList<User> users =null;
                if(usersList != null){
    	            users = new ArrayList<User>(Arrays.asList(usersList));
                }else{
                    users = new ArrayList<User>();
                }
                
                Page<User> userPage = new Page<User>();
                userPage.setPageItems(users);
                userPage.setPageNumber(filter.getPageNumber());
                userPage.setTotalRecords(filter.getIncludedUserIds().size());
                return userPage;

    		} catch (RestClientException rse) {
    			LOGGER.error("Error deleting user!", rse);
    			throw rse;
    		} catch (Exception exp) {
    			LOGGER.error("Error deleting user!", exp);
    			throw new AdminUIException(exp);
    		}
    	
    }
    
    @Override
    public UserSessionData getUserSessionData(String token) throws UserServiceException {
        return new RestTemplate().getForObject(restServiceUrl.userSessionDataURI()+"?token="+token,  UserSessionData.class);
    }

    
    @Override
    public List<User> getUserBySupplierAndNotify(String unitId, String supplierId) {
    	SupplierUser[] usersList = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/supplier/unitId/"+unitId+"?uniquesupplierid="+supplierId, SupplierUser[].class);
        
        ArrayList<User> users =null;
        if(usersList != null){
        	
        	users = new ArrayList<User>();
        	User user = null;
        	
        	for (SupplierUser supplierUsr : usersList) {
				user = new User();
				user.setUsername(supplierUsr.getUsername());
				user.setUserId(supplierUsr.getUserId());
				user.setFirstName(supplierUsr.getFirstName());
				user.setLastName(supplierUsr.getLastName());
				user.setLanguage(supplierUsr.getLanguage());
				user.setDecimalNotation(supplierUsr.getDecimalNotation());
				user.setDateFormat(supplierUsr.getDateFormat());
				user.setTimeZone(supplierUsr.getTimeZone());
                user.setAssignedProfiles(supplierUsr.getAssignedProfiles());
                user.setCreatedBy(supplierUsr.getCreatedBy());
                user.setUnitId(supplierUsr.getUnitId());
                user.setCreatedOn(new Date());
                user.setResetPassword(true);
                if(supplierUsr.getPassword()!= null && supplierUsr.getPassword().length() > 0){
                	user.setPassword(supplierUsr.getPassword());
                }else{
                	user.setPassword(RandomStringUtils.randomAlphanumeric(16));
                }                  
                
                user.setActive(true);                    
                if(user.getRoles() == null){
                	user.setRoles(new ArrayList<Role>());
                }

                user.setRoles(supplierUsr.getRoles());
                users.add(user);
                if(supplierUsr.isSendEmail()){
                    try {
                        CompanySettings companySettings = companyService.getCompanySettingsByUnitId(supplierUsr.getUnitId());
                        String message = supplierUsr.getEmailMessage()+"<br/><br/>To log into the Marketplace, please use the following credentials: <br/><br/>" +
                                "URL: "+appConfig.environmentUrl+"<br/>" +
                                "Username: "+user.getUsername()+"<br/>" +
                                "Password: "+user.getPassword()+"<br/><br/>" +
                                "Regards<br/>" +
                                companySettings.getCompanyName();
                        notificationService.sendEmail( "Welcome to "+companySettings.getCompanyName()+" Marketplace!", message,supplierUsr,getUser(supplierUsr.getCreatedBy()) );
                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        	

        }else{
            users = new ArrayList<User>();
        }


        return users;
    }

    @Override
    public List<User> getUserBySupplier(String unitId, String supplierId) {
        SupplierUser[] usersList = new RestTemplate().getForObject(restServiceUrl.catalogUserURI() + "/supplier/unitId/"+unitId+"?uniquesupplierid="+supplierId, SupplierUser[].class);

        ArrayList<User> users =null;
        if(usersList != null){

            users = new ArrayList<User>();
            User user = null;

            for (SupplierUser supplierUsr : usersList) {
                user = new User();
                user.setUsername(supplierUsr.getUsername());
                user.setUserId(supplierUsr.getUserId());
                user.setFirstName(supplierUsr.getFirstName());
                user.setLastName(supplierUsr.getLastName());
                user.setLanguage(supplierUsr.getLanguage());
                user.setDecimalNotation(supplierUsr.getDecimalNotation());
                user.setDateFormat(supplierUsr.getDateFormat());
                user.setTimeZone(supplierUsr.getTimeZone());
                user.setAssignedProfiles(supplierUsr.getAssignedProfiles());
                user.setCreatedBy(supplierUsr.getCreatedBy());
                user.setUnitId(supplierUsr.getUnitId());
                user.setCreatedOn(new Date());
                user.setResetPassword(true);
                user.setPassword(RandomStringUtils.randomAlphanumeric(16));
                user.setActive(true);
                if(user.getRoles() == null){
                    user.setRoles(new ArrayList<Role>());
                }

                user.setRoles(supplierUsr.getRoles());
                users.add(user);
            }


        }else{
            users = new ArrayList<User>();
        }


        return users;
    }
    
    @Override
    public SupplierUser addSupplierUser(SupplierUser supplierUser) {
		try {
			SupplierUser insertedUser  = new RestTemplate().postForObject(restServiceUrl.tempUserURI(),  supplierUser , SupplierUser.class);
			return insertedUser;
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
    }

    
    @Override
    public SupplierUser updateSupplierUser(SupplierUser supplierUser) {
		try {
			new RestTemplate().put(restServiceUrl.updateTempUserURI(),  supplierUser);
			return supplierUser;
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}
    }

    @Override
    public List<User> addUserBatch(UsersDTO usersDTO) {
		try {
			User [] newUsers = new RestTemplate().postForObject(restServiceUrl.userBatchURI(),
                                                                            usersDTO, User[].class);
			return Arrays.asList(newUsers);
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting user!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting user!", exp);
			throw new AdminUIException(exp);
		}

    }
    
    @Override
    public List<User> searchUsers(String unitId, String keyword, String roles, int currentPage, int pageSize) {
      try {
        String url = restServiceUrl.userFindURI() + "?unitId=" + unitId + "&q=" + keyword
            + "&currentPage=" + currentPage + "&pageSize=" + pageSize;
        if (roles != null && !roles.isEmpty()) {
          url += "&roles=" + roles;
        }
        Result<User> result = new RestTemplate().getForObject(url, Result.class);
        return result.getDataItems();
      } catch (RestClientException rse) {
        LOGGER.error("Error searching user!", rse);
        throw rse;
      } catch (Exception exp) {
        LOGGER.error("Error searching user!", exp);
        throw new AdminUIException(exp);
      }
    }

  @Override
  public Map<String, Long> getRoleWiseUsersCount(String unitId) {
    Map<String, Long> userCountsMap = new HashMap<>();
    try {
      userCountsMap = new RestTemplate()
          .getForObject(restServiceUrl.getUserRoleWiseCountsURI(), Map.class, unitId);
    } catch (RestClientException e) {
      LOGGER.error("Exception in getting role wise user counts for unitId " + unitId, e);
    }

    return userCountsMap;
  }

}
