package com.vroozi.customerui.user.services.user.model;

import java.util.ArrayList;
import java.util.List;

import com.vroozi.customerui.acl.model.Role;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;


/**
 * 
 * @author mhabib
 *
 */
public class UserBean {

	private List<User> users = new ArrayList<User>();
	private List<User> filteredUsers = new ArrayList<User>();
	private List<User> filteredNormalUsers = new ArrayList<User>();
	private List<User> filteredSystemUsers = new ArrayList<User>();
	
	private int masterAdminCount = 0;
	private int administratorCount = 0;
	private int buyerCount = 0;
	private int approverCount = 0;
	private int supplierCount = 0;
	private int webSearchUserCount = 0;
	private int systemShopperCount = 0;
	private int totalCount = 0;
	private int totalSystemUserCount = 0;
	private int totalNormalUserCount = 0;
	
	private boolean masterAdmin = true;
    private boolean administrator = true;
    private boolean approver = true;
    private boolean buyer = true;
    private boolean supplier = true;
    private boolean webSearchUser = true;
    private boolean systemShopper = true;

	private boolean activeNormalUser = true;
	private boolean activeSystemUser = true;
	private boolean inactiveNormalUser = true;
	private boolean inactiveSystemUser = true;

	String searchNormalUser = null;
	String searchSystemUser = null;
	
	String sortNormalUser = null;
	String sortSystemUser = null;

	PagedListHolder<User> systemUserPagedList;
	PagedListHolder<User> normalUserPagedList;

    int pageSize = 8;

    public UserBean() {
	}
    
	public UserBean(List<User> users) {
		setUsers(users);		
	}
	
	public void updateFilters() {
		masterAdminCount = 0;
		administratorCount = 0;
		buyerCount = 0;
		approverCount = 0;
		supplierCount = 0;
		webSearchUserCount = 0;
		systemShopperCount = 0;
		totalCount = 0;
		totalSystemUserCount = 0;
		totalNormalUserCount = 0;
		
		filteredUsers = new ArrayList<User>(users.size());
		filteredNormalUsers = new ArrayList<User>(users.size());
		filteredSystemUsers = new ArrayList<User>(users.size());
		for (User user : users) {
			user.setFullName(user.getFirstName()+" "+user.getLastName());
			if(user.getRoles()!=null) {
				if (user.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {
					masterAdminCount++;
				} if (user.getRoles().contains(Role.ADMINISTRATOR)) {
	            	administratorCount++;
				} if (user.getRoles().contains(Role.BUYER)) {
	            	buyerCount++;
				} if (user.getRoles().contains(Role.APPROVER)) {
					approverCount++;
				} if (user.getRoles().contains(Role.SUPPLIER) || user.getRoles().contains(Role.SUPPLIER_ADMIN)) {
					supplierCount++;
				} if (user.getRoles().contains(Role.WEB_SEARCH_USER)) {
					webSearchUserCount++;
				} if (user.getRoles().contains(Role.SHOPPER)) {
					systemShopperCount++;
				}
			}
			
			totalCount++;
			if(user.getRoles().contains(Role.ADMINISTRATOR) || user.getRoles().contains(Role.MASTER_ADMINISTRATOR)
					|| user.getRoles().contains(Role.BUYER) || user.getRoles().contains(Role.APPROVER)
					|| user.getRoles().contains(Role.SUPPLIER) || user.getRoles().contains(Role.SUPPLIER_ADMIN)) {
				totalNormalUserCount++;
			}
			if(user.getRoles().contains(Role.WEB_SEARCH_USER) || user.getRoles().contains(Role.SHOPPER)) {
				totalSystemUserCount++;
			}

			boolean normalUser = filterNormalUser(user);
			boolean systemUser = filterSystemUser(user);
			if (normalUser || systemUser) {
				filteredUsers.add(user);
			}
			if (normalUser) {
				filteredNormalUsers.add(user);
			}
			if (systemUser) {
				filteredSystemUsers.add(user);
			}
		}
			
		systemUserPagedList = new PagedListHolder<User>(filteredSystemUsers);
		systemUserPagedList.setPageSize(pageSize);
		systemUserPagedList.setPage(0);
		normalUserPagedList = new PagedListHolder<User>(filteredNormalUsers);
		normalUserPagedList.setPageSize(pageSize);
		normalUserPagedList.setPage(0);
			
		if(sortNormalUser!=null && !sortNormalUser.equals("")) {
			normalUserPagedList.setSort(new MutableSortDefinition(sortNormalUser, true, true));
			normalUserPagedList.resort();
		}
		if(sortSystemUser!=null && !sortSystemUser.equals("")) {
			systemUserPagedList.setSort(new MutableSortDefinition(sortSystemUser, true, true));
			systemUserPagedList.resort();
		}
//	        int totalNormalPages = (filteredNormalUsers.size()%pageSize > 0)?filteredNormalUsers.size()/pageSize + 1 : filteredNormalUsers.size()/pageSize;
//	        int totalSystemPages = (filteredSystemUsers.size()%pageSize > 0)?filteredSystemUsers.size()/pageSize + 1 : filteredSystemUsers.size()/pageSize;
	}
	
	private boolean filterNormalUser(User user) {
		return (activeNormalUser == user.isActive() || (activeNormalUser && inactiveNormalUser)) 
				&& (user.getRoles()!=null && 
				((administrator && user.getRoles().contains(Role.ADMINISTRATOR))
				|| (masterAdmin && user.getRoles().contains(Role.MASTER_ADMINISTRATOR))
				|| (approver && user.getRoles().contains(Role.APPROVER))
				|| (buyer && user.getRoles().contains(Role.BUYER))
				|| (supplier && (user.getRoles().contains(Role.SUPPLIER) || user.getRoles().contains(Role.SUPPLIER_ADMIN)))
				)) 
				&& (searchNormalUser==null || searchNormalUser.equals("") || user.getUsername().contains(searchNormalUser) 
				|| user.getFirstName().contains(searchNormalUser) || user.getLastName().contains(searchNormalUser));
	}
	
	private boolean filterSystemUser(User user) {
		return (activeSystemUser == user.isActive() || (activeSystemUser && inactiveSystemUser)) 
				&& (user.getRoles()!=null && 
				((webSearchUser && user.getRoles().contains(Role.WEB_SEARCH_USER))
				|| (systemShopper && user.getRoles().contains(Role.SHOPPER))
				))
				&& (searchSystemUser==null || searchSystemUser.equals("") || user.getUsername().contains(searchSystemUser) 
				|| user.getFirstName().contains(searchSystemUser) || user.getLastName().contains(searchSystemUser));
	}

	public static boolean isNormalUser(User user) {
		return (user.getRoles().contains(Role.ADMINISTRATOR)
				|| user.getRoles().contains(Role.MASTER_ADMINISTRATOR)
				|| user.getRoles().contains(Role.APPROVER)
				|| user.getRoles().contains(Role.BUYER)
				|| user.getRoles().contains(Role.SUPPLIER)
				|| user.getRoles().contains(Role.SUPPLIER_ADMIN)
				);
	}
	public static boolean isSystemUser(User user) {
		return (user.getRoles().contains(Role.WEB_SEARCH_USER)
				|| user.getRoles().contains(Role.SHOPPER)
				);
	}
	public List<User> getFilteredNormalUsers() {
		return filteredNormalUsers;
	}

	public List<User> getFilteredSystemUsers() {
		return filteredSystemUsers;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setUsers(List<User> users) {
		this.users = users;
		updateFilters();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public int getMasterAdminCount() {
		return masterAdminCount;
	}

	public void setMasterAdminCount(int masterAdminCount) {
		this.masterAdminCount = masterAdminCount;
	}

	public int getAdministratorCount() {
		return administratorCount;
	}

	public void setAdministratorCount(int administratorCount) {
		this.administratorCount = administratorCount;
	}

	public int getBuyerCount() {
		return buyerCount;
	}

	public void setBuyerCount(int buyerCount) {
		this.buyerCount = buyerCount;
	}

	public int getApproverCount() {
		return approverCount;
	}

	public void setApproverCount(int approverCount) {
		this.approverCount = approverCount;
	}

	public int getSupplierCount() {
		return supplierCount;
	}

	public void setSupplierCount(int supplierCount) {
		this.supplierCount = supplierCount;
	}

	public int getWebSearchUserCount() {
		return webSearchUserCount;
	}

	public void setWebSearchUserCount(int webSearchUserCount) {
		this.webSearchUserCount = webSearchUserCount;
	}

	public int getSystemShopperCount() {
		return systemShopperCount;
	}

	public void setSystemShopperCount(int systemShopperCount) {
		this.systemShopperCount = systemShopperCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalSystemUserCount() {
		return totalSystemUserCount;
	}

	public void setTotalSystemUserCount(int totalSystemUserCount) {
		this.totalSystemUserCount = totalSystemUserCount;
	}

	public int getTotalNormalUserCount() {
		return totalNormalUserCount;
	}

	public void setTotalNormalUserCount(int totalNormalUserCount) {
		this.totalNormalUserCount = totalNormalUserCount;
	}

	public boolean isWebSearchUser() {
		return webSearchUser;
	}

	public void setWebSearchUser(boolean webSearchUser) {
		this.webSearchUser = webSearchUser;
	}

	public boolean isSystemShopper() {
		return systemShopper;
	}

	public void setSystemShopper(boolean systemShopper) {
		this.systemShopper = systemShopper;
	}

	public boolean isMasterAdmin() {
		return masterAdmin;
	}

	public void setMasterAdmin(boolean masterAdmin) {
		this.masterAdmin = masterAdmin;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean isApprover() {
		return approver;
	}

	public void setApprover(boolean approver) {
		this.approver = approver;
	}

	public boolean isBuyer() {
		return buyer;
	}

	public void setBuyer(boolean buyer) {
		this.buyer = buyer;
	}

	public boolean isSupplier() {
		return supplier;
	}

	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}

	public boolean isActiveNormalUser() {
		return activeNormalUser;
	}

	public void setActiveNormalUser(boolean activeNormalUser) {
		this.activeNormalUser = activeNormalUser;
	}

	public boolean isActiveSystemUser() {
		return activeSystemUser;
	}

	public void setActiveSystemUser(boolean activeSystemUser) {
		this.activeSystemUser = activeSystemUser;
	}

	public boolean isInactiveNormalUser() {
		return inactiveNormalUser;
	}

	public void setInactiveNormalUser(boolean inactiveNormalUser) {
		this.inactiveNormalUser = inactiveNormalUser;
	}

	public boolean isInactiveSystemUser() {
		return inactiveSystemUser;
	}

	public void setInactiveSystemUser(boolean inactiveSystemUser) {
		this.inactiveSystemUser = inactiveSystemUser;
	}

	public PagedListHolder<User> getSystemUserPagedList() {
		return systemUserPagedList;
	}

	public void setSystemUserPagedList(PagedListHolder<User> systemUserPagedList) {
		this.systemUserPagedList = systemUserPagedList;
	}

	public PagedListHolder<User> getNormalUserPagedList() {
		return normalUserPagedList;
	}

	public void setNormalUserPagedList(PagedListHolder<User> normalUserPagedList) {
		this.normalUserPagedList = normalUserPagedList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchNormalUser() {
		return searchNormalUser;
	}

	public void setSearchNormalUser(String searchNormalUser) {
		this.searchNormalUser = searchNormalUser;
	}

	public String getSearchSystemUser() {
		return searchSystemUser;
	}

	public void setSearchSystemUser(String searchSystemUser) {
		this.searchSystemUser = searchSystemUser;
	}
	
	public String getSortNormalUser() {
		return sortNormalUser;
	}

	public void setSortNormalUser(String sortNormalUser) {
		this.sortNormalUser = sortNormalUser;
	}

	public String getSortSystemUser() {
		return sortSystemUser;
	}

	public void setSortSystemUser(String sortSystemUser) {
		this.sortSystemUser = sortSystemUser;
	}

//	public User getUser(String userId) {
//		for (User user : users) {
//			if(user.getUserId().equals(userId)) {
//				return user;
//			}
//		}
//		return null;
//	}
//
//	public boolean userExists(String username) {
//		for (User user : users) {
//			if(user.getUsername().equals(username)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean removeUser(String userId) {
//		for (int i = 0; i < users.size(); i++) {
//			User user = users.get(i);
//			if(user.getUserId().equals(userId)) {
//				users.remove(i);
//				return true;
//			}
//		}
//		return false;
//	}

}
