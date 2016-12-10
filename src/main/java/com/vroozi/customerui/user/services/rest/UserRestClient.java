package com.vroozi.customerui.user.services.rest;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.myaccount.model.MyAccountPost;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.security.controller.model.UserSessionData;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.user.services.exception.UserServiceException;
import com.vroozi.customerui.user.services.user.model.SupplierUser;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.user.services.user.model.UserFilter;
import com.vroozi.customerui.user.services.user.model.UsersDTO;
import com.vroozi.customerui.user.services.user.model.UsersPage;
import com.vroozi.customerui.util.Page;

import java.util.List;
import java.util.Map;

public interface UserRestClient {
    public User forgotPassword(User user) throws Exception ;
    public User authenticate(User user) throws Exception ;
    public UserSessionData getUserSessionData(String token) throws Exception;

    public User getUser(String userId) throws Exception;
    public User getUserByUserName(String userName, String unitid);

     public String getSupplierName(String supplierId) throws Exception;
    public Boolean checkSession(String id, String sessionId) throws Exception;
    List<User> getApproverListForCompany(String unitId) throws Exception;
    List<User> getApproverListForUser(String unitId,String userId) throws Exception;
    List<Profile> getProfileListForCompany(String unitId) throws Exception;
    public List<Supplier> getAllSuppliers(String unitId) throws Exception;
    
	public List<User> getAllUsers(String unitId);
	public User addUpdateUser(User user);
	public int deleteUsers(List<String> userIds);
	public int setUsersStatus(List<String> userIds, boolean active);
	public void setUserProfiles(String userId, List<Integer> profileIds);
	
	MyAccountPost getDropDownData(String unitId)throws UserServiceException;
	
	User getUserDetails(int unitId , String userId);
	public UsersPage getFilteredUsers(String unitId, UserFilter userFilter);
	Page<User> getUserByIds(String unitId, UserFilter filter);

	Page<User> getSupplierUserByIds(String unitId, UserFilter filter);
	public List<User> getUsersByRole(String companyCode,
			List<Role> userRoles);

    public List<User> getUsersByIds(String unitId,
                                    List<String> ids);
    ///organization/{unitid}/ids/{ids}/users
    SupplierUser addSupplierUser(SupplierUser supplierUser);
	
	SupplierUser updateSupplierUser(SupplierUser supplierUser);
	
	List<User> getUserBySupplierAndNotify(String unitId, String supplierId);
    List<User> getUserBySupplier(String unitId, String supplierId);
	int setNewUsersStatus(List<String> userIds, boolean active);
	int deleteTempUser(List<String> userIds);
	List<User> addUserBatch(UsersDTO usersDTO);
	SupplierUser getTempUserByName(String userName, String unitId);
	SupplierUser getTempUserById(String userId);
    List<User> searchUsers(String unitId, String keyword, String roles, int currentPage, int pageSize);

  /**
   * Gets user counts against each user role by invoking respective REST API
   *
   * @param unitId
   * @return
   * @author Muhammad Haris
   */
  Map<String, Long> getRoleWiseUsersCount(String unitId);

}
