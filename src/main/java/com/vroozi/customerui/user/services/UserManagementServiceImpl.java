package com.vroozi.customerui.user.services;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.myaccount.model.MyAccountPost;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.security.controller.model.UserSessionData;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.user.services.exception.UserServiceException;
import com.vroozi.customerui.user.services.rest.UserRestClient;
import com.vroozi.customerui.user.services.user.model.SupplierUser;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.user.services.user.model.UserFilter;
import com.vroozi.customerui.user.services.user.model.UsersPage;
import com.vroozi.customerui.util.Page;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    private final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

    @Autowired
    UserRestClient userRestClient;

    // Default Constructor
    public UserManagementServiceImpl() {
    }

    @Override
    public User getUser(String userId) throws UserServiceException {
        try{
            return userRestClient.getUser(userId);

        } catch( Exception exp) {
            logger.error("Error retrieving User w/ userId(" + userId + ")");
            throw new UserServiceException(exp);
        }
    }

    @Override
    public SupplierUser getTempUserByName(String userName, String unitid)
    		throws UserServiceException {
        try{
            return userRestClient.getTempUserByName(userName, unitid);
        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
    }
    
    @Override
    public SupplierUser getTempUserById(String userId)
    		throws UserServiceException {
        try{
            return userRestClient.getTempUserById(userId);
        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
    }
    
    @Override
    public User getUserByUserName(String userName, String unitid) throws UserServiceException{
        try{

        	
            return userRestClient.getUserByUserName(userName, unitid);

        } catch( Exception exp) {

            throw new UserServiceException(exp);
        }
    }

    @Override
    public User authenticate(User user) throws UserServiceException {
        try{

            return userRestClient.authenticate(user);

        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
    }

    @Override
    public UserSessionData getUserSessionData(String ssoToken) throws UserServiceException {
        try{
            return userRestClient.getUserSessionData(ssoToken);
        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
    }

    @Override
    public User forgotPassword(User user) throws UserServiceException{
        try{

            return userRestClient.forgotPassword(user);

        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
    }
    @Override
    public String getSupplierNameBySupplierId(String supplierId) throws UserServiceException {
        try{
            if(supplierId != null){
                throw new IllegalArgumentException("supplierId must positive number");
            }

            return userRestClient.getSupplierName(supplierId);

        } catch( Exception exp) {
            logger.error("Error retrieving User for supplierId(" + supplierId + ")");
           // throw new UserServiceException(exp);
        }
        return "";
    }

//    @Override
//    public Boolean validateSession(String id, String sessionId) {
//        try{
//            if(StringUtils.isEmpty(id) || StringUtils.isEmpty(sessionId)) {
//                throw new IllegalArgumentException("supplierId must positive number");
//            }
//
//            return userRestClient.checkSession(id, sessionId);
//
//        } catch( Exception exp) {
//            logger.error("Error checking session for id(" + id + ") & sessionId(" + sessionId + " )");
//            throw new UserServiceException(exp);
//        }
//    }

    @Override
    public java.util.List<User> getApproverListForUser(String unitId, String userId) throws UserServiceException {
        if(StringUtils.isEmpty(unitId) || StringUtils.isEmpty(unitId)) {
            throw new IllegalArgumentException("unitId must positive number");
        }

        try{
            return userRestClient.getApproverListForUser(unitId,userId);

        } catch( Exception exp) {
            logger.error("Error retrieving Approves for unitId(" + unitId + ")");
            throw new UserServiceException(exp);
        }
    	
    }
    
    @Override
    public List<User> getApproverListForCompany(String unitId) throws UserServiceException {
        if(StringUtils.isEmpty(unitId) || StringUtils.isEmpty(unitId)) {
            throw new IllegalArgumentException("unitId must positive number");
        }

        try{
            return userRestClient.getApproverListForCompany(unitId);

        } catch( Exception exp) {
            logger.error("Error retrieving Approves for unitId(" + unitId + ")");
            throw new UserServiceException(exp);
        }
    }

    @Override
    public List<Profile> getProfileListForCompany(String unitId) throws UserServiceException {
        if(StringUtils.isEmpty(unitId) || StringUtils.isEmpty(unitId)) {
            throw new IllegalArgumentException("unitId must positive number");
        }

        try{
            return userRestClient.getProfileListForCompany(unitId);

        } catch( Exception exp) {
            logger.error("Error retrieving Approves for unitId(" + unitId + ")");
            throw new UserServiceException(exp);
        }
    }

    @Override
    public List<Supplier> getAllSuppliers(String unitId) throws UserServiceException {
        if(StringUtils.isEmpty(unitId) || StringUtils.isEmpty(unitId)) {
            throw new IllegalArgumentException("unitId must positive number");
        }

        try{
            return userRestClient.getAllSuppliers(unitId);

        } catch( Exception exp) {
            logger.error("Error retrieving Suppliers for unitId(" + unitId + ")");
            throw new UserServiceException(exp);
        }
    }
    @Override
    public List<Supplier> getSuppliersByUnitId(String unitId){
        if(org.apache.commons.lang.StringUtils.isEmpty(unitId) || org.apache.commons.lang.StringUtils.isEmpty(unitId)) {
            throw new IllegalArgumentException("unitId must positive number");
        }

        try{
            return userRestClient.getAllSuppliers(unitId);

        } catch( Exception exp) {
            logger.error("Error retrieving Suppliers for unitId(" + unitId + ")");
            throw new UserServiceException(exp);
        }
    }

  @Override
  public Map<String, Long> getRoleWiseUserCounts(String unitId) {
    logger.debug("Getting role wise user counts for unitId: {}", unitId);
    return userRestClient.getRoleWiseUsersCount(unitId);
  }

  @Override
	public List<User> getAllUsers(String unitId) {
		if(StringUtils.isEmpty(unitId) || StringUtils.isEmpty(unitId)) {
            throw new IllegalArgumentException("unitId must positive number");
        }
		
		try{
            return userRestClient.getAllUsers(unitId);

        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
	}

	@Override
	public User addUpdateUser(User user) {
		try{
			user = userRestClient.addUpdateUser(user);
			return user;
        }catch (Exception exp){
            throw new UserServiceException(exp);
        }
	}

	@Override
	public int deleteUsers(List<String> userIds) {
		try{
			int count = userRestClient.deleteUsers(userIds);
			return count;
        }catch (Exception exp){
            throw new UserServiceException(exp);
        }
	}

	@Override
	public int deleteTempUsers(List<String> userIds) {
		try{
			int count = userRestClient.deleteTempUser(userIds);
			return count;
        }catch (Exception exp){
            throw new UserServiceException(exp);
        }
	}
	
	@Override
	public int setUsersStatus(List<String> userIds, boolean active) {
		try{
			int count = userRestClient.setUsersStatus(userIds, active);
			return count;
        }catch (Exception exp){
            throw new UserServiceException(exp);
        }
	}

	@Override
	public void setUserProfiles(String userId, List<Integer> profileIds) {
		userRestClient.setUserProfiles(userId, profileIds);
		
	}
	
	
	@Override
	public User getUserDetails(int unitId, String userId){
		try{
			return userRestClient.getUserDetails(unitId, userId);
	    }catch (Exception exp){
	        throw new UserServiceException(exp);
	    }
	}

    @Override
    public MyAccountPost getDropDownData(String unitId)
    		throws UserServiceException {
    	return userRestClient.getDropDownData(unitId);
    	
    }

	@Override
	public List<User> getUsersByRole(String companyCode,
			List<Role> userRoles) {
		return userRestClient.getUsersByRole(companyCode, userRoles);
	}
    @Override
    public List<User> getUsersByIds(String unitId,List<String> ids){
        return userRestClient.getUsersByIds(unitId, ids);
    }

	@Override
	public UsersPage getFilteredUsers(String unitId, UserFilter userFilter) {
		if(StringUtils.isEmpty(unitId) || StringUtils.isEmpty(unitId)) {
            throw new IllegalArgumentException("unitId must positive number");
        }
		
		try{
            return userRestClient.getFilteredUsers(unitId, userFilter);

        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
	}
	
	@Override
	public Page<User> getUserByIds(String unitId, UserFilter filter) {
		try{
            return userRestClient.getUserByIds(unitId, filter);

        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }

	}

    @Override
    public List<User> searchUsers(String unitId, String keyword, String roles, int currentPage, int pageSize) {
      try {
        return userRestClient.searchUsers(unitId, keyword, roles, currentPage, pageSize);
      } catch (Exception exp) {
        throw new UserServiceException(exp);
      }
    }
	
	@Override
	public Page<User> getSupplierUserByIds(String unitId, UserFilter filter) {
		try{
            return userRestClient.getSupplierUserByIds(unitId, filter);

        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }
	}

	@Override
	public List<User> getUserBySupplierId(String unitId, String supplierId) {
		try{
            return userRestClient.getUserBySupplier(unitId, supplierId);

        } catch( Exception exp) {
            throw new UserServiceException(exp);
        }

	}
	
	@Override
	public SupplierUser addSupplierUser(SupplierUser supplierUser){
		try{
			 return userRestClient.addSupplierUser(supplierUser);
       }catch (Exception exp){
           throw new UserServiceException(exp);
       }
		
	}
	
	
	
	@Override
	public int setNewUsersStatus(List<String> userIds, boolean active) {
		try{
			int count = userRestClient.setNewUsersStatus(userIds, active);
			return count;
        }catch (Exception exp){
            throw new UserServiceException(exp);
        }
	}

	@Override
	public SupplierUser updateSupplierUser(SupplierUser supplierUser) {
	
		return userRestClient.updateSupplierUser(supplierUser);
	}
	
}