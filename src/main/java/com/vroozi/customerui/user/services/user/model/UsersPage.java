package com.vroozi.customerui.user.services.user.model;

import java.util.List;
import java.util.Map;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.util.Page;


/**
 * 
 * @author mhabib
 *
 */
public class UsersPage extends Page<User> {

	private int masterAdminCount = 0;
	private int administratorCount = 0;
	private int buyerCount = 0;
	private int approverCount = 0;
	private int supplierCount = 0;
	private int shopperViewOnlyCount = 0;
	
	private int webSearchUserCount = 0;
	private int systemShopperCount = 0;
	
	private int systemUserCount = 0;
	private int normalUserCount = 0;
	
	public UsersPage(List<User> users, int pageNumber, int pageSize, long totalRecords) {
		setPageItems(users);
		setTotalRecords(totalRecords);
		setPageNumber(pageNumber);
		setPageSize(pageSize);
		
		
		int pageCount = (int) (totalRecords / pageSize);
        if (totalRecords > pageSize * pageCount) {
            pageCount++;
        }
        setPagesAvailable(pageCount);
        
        firstElementOnPage = (getPageNumber()-1)*pageSize+1;
        lastElementOnPage = firstElementOnPage+getPageItems().size()-1;
        
        if(totalRecords==0) {
        	setPageNumber(0);
        	firstElementOnPage--;
        }
//        updateCounts();
	}
	
	public void setUserCounts(Map<String, Integer> counts) {
		masterAdminCount = counts.get(Role.MASTER_ADMINISTRATOR.toString());
		administratorCount = counts.get(Role.ADMINISTRATOR.toString());
		buyerCount = counts.get(Role.BUYER.toString());
		approverCount = counts.get(Role.APPROVER.toString());
		supplierCount = counts.get(Role.SUPPLIER.toString());
		shopperViewOnlyCount = counts.get(Role.SHOPPER_VIEW_ONLY.toString());

		webSearchUserCount = counts.get(Role.WEB_SEARCH_USER.toString());
		systemShopperCount = counts.get(Role.SHOPPER.toString());

		systemUserCount = counts.get("system")!=null?counts.get("system"):0;
		normalUserCount = counts.get("normal")!=null?counts.get("normal"):0;
	}
	
	public void updateCounts() {
		masterAdminCount = 0;
		administratorCount = 0;
		buyerCount = 0;
		approverCount = 0;
		supplierCount = 0;
		shopperViewOnlyCount = 0;

		webSearchUserCount = 0;
		systemShopperCount = 0;
		
		for (User user : getPageItems()) {
			user.setFullName(user.getFirstName()+" "+user.getLastName());
			if(user.getRoles()!=null) {
				// Normal users
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
				} if (user.getRoles().contains(Role.SHOPPER_VIEW_ONLY)) {
                    shopperViewOnlyCount++;
                } 
				
				// System users
				if (user.getRoles().contains(Role.WEB_SEARCH_USER)) {
					webSearchUserCount++;
				} if (user.getRoles().contains(Role.SHOPPER)) {
					systemShopperCount++;
				}
			}
		}
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

	public int getShopperViewOnlyCount() {
        return shopperViewOnlyCount;
    }

    public void setShopperViewOnlyCount(int shopperViewOnlyCount) {
        this.shopperViewOnlyCount = shopperViewOnlyCount;
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

	public int getSystemUserCount() {
		return systemUserCount;
	}

	public void setSystemUserCount(int systemUserCount) {
		this.systemUserCount = systemUserCount;
	}

	public int getNormalUserCount() {
		return normalUserCount;
	}

	public void setNormalUserCount(int normalUserCount) {
		this.normalUserCount = normalUserCount;
	}

}
