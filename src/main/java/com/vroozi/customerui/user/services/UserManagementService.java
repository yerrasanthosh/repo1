package com.vroozi.customerui.user.services;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.myaccount.model.MyAccountPost;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.security.controller.model.UserSessionData;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.user.services.exception.UserServiceException;
import com.vroozi.customerui.user.services.user.model.SupplierUser;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.user.services.user.model.UserFilter;
import com.vroozi.customerui.user.services.user.model.UsersPage;
import com.vroozi.customerui.util.Page;

import java.util.List;
import java.util.Map;

public interface UserManagementService {
    User forgotPassword(User user) throws UserServiceException ;
    User authenticate(User user) throws UserServiceException ;
    UserSessionData getUserSessionData(String ssoToken) throws UserServiceException ;


    User getUser(String userId) throws UserServiceException;
    User getUserByUserName(String userName, String unitid) throws UserServiceException;
    List<User> searchUsers(String unitId, String keyword, String roles, int currentPage, int pageSize);
    //User getUserBy(int userId) throws UserServiceException;
    String getSupplierNameBySupplierId(String supplierId) throws UserServiceException;
  //  Boolean validateSession(String id, String sessionId);
    List<User> getApproverListForCompany(String unitId) throws UserServiceException;
    List<User> getApproverListForUser(String unitId , String userId) throws UserServiceException;
    
    List<Profile> getProfileListForCompany(String unitId) throws UserServiceException;
    public List<Supplier> getAllSuppliers(String unitId) throws UserServiceException;
    
    List<User> getAllUsers(String unitId);
    User addUpdateUser(User user);
    int deleteUsers(List<String> userIds);
    int deleteTempUsers(List<String> userIds);
    int setUsersStatus(List<String> userIds, boolean active);
	void setUserProfiles(String userId, List<Integer> profileIds);

	
	User getUserDetails(int unitId , String userId)throws UserServiceException;
	MyAccountPost getDropDownData(String unitId)throws UserServiceException;
	UsersPage getFilteredUsers(String companyCode, UserFilter userFilter);
	Page<User> getUserByIds(String unitId , UserFilter filter);
	List<User> getUsersByRole(String companyCode,List<Role> userRoles);
    public List<User> getUsersByIds(String unitId,
                                    List<String> ids);
	
	SupplierUser addSupplierUser(SupplierUser supplierUser);
	
	Page<User> getSupplierUserByIds(String unitId , UserFilter filter);
	List<User> getUserBySupplierId(String unitId , String supplierId);
	SupplierUser getTempUserByName(String userName, String unitid) throws UserServiceException;	
	public SupplierUser getTempUserById(String userId) throws UserServiceException;
	int setNewUsersStatus(List<String> userIds, boolean active);
	SupplierUser updateSupplierUser(SupplierUser supplierUser);

    List<Supplier> getSuppliersByUnitId(String unitId);

  Map<String, Long> getRoleWiseUserCounts(String unitId);
}