package com.vroozi.customerui.home.service.impl;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.catalog.common.CatalogDataDisplayPopulator;
import com.vroozi.customerui.catalog.model.CatalogState;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.model.GroupedCatalogCountDTO;
import com.vroozi.customerui.catalog.services.CatalogService;
import com.vroozi.customerui.home.service.HomeService;
import com.vroozi.customerui.home.service.model.SummaryCard;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.vroozi.customerui.acl.model.Role.ADMINISTRATOR;
import static com.vroozi.customerui.acl.model.Role.APPROVER;
import static com.vroozi.customerui.acl.model.Role.BUYER;
import static com.vroozi.customerui.acl.model.Role.MASTER_ADMINISTRATOR;
import static com.vroozi.customerui.acl.model.Role.SHOPPER;
import static com.vroozi.customerui.acl.model.Role.WEB_SEARCH_USER;
import static com.vroozi.customerui.catalog.model.CatalogState.APPROVED;
import static com.vroozi.customerui.catalog.model.CatalogState.REJECTED;
import static com.vroozi.customerui.catalog.model.CatalogState.WORKING;

/**
 * User: SURYA MANDADAPU
 * Date: 10/27/12
 * Time: 1:32 PM
 */
@Service
public class HomeServiceImpl implements HomeService {


    private final Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);

    @Autowired
    SupplierService supplierService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    ProfileService profileService;

	@Override
	public SummaryCard getSummary(User user) {
		SummaryCard summaryCard  = new SummaryCard();
		if(isMasterAdmin(user.getRoles())){
			summaryCard = getSummaryForMasterAdmin(user.getUnitId());
			summaryCard.setMasterAdmin(1);
		}else{
			Set<CatalogSummary> catalogSet = new HashSet<CatalogSummary>();
	        List<Profile> profiles = getProfilesByUser(user.getAssignedProfiles());
	        List<CatalogSummary> catalogs = catalogService.getAllCatalogs(user.getUnitId());

	        if(isApprover(user.getRoles())){
	        	catalogSet = filterCatalogsApprover(catalogs, profiles,user.getUserId());
	        }else if(isSupplierAdmin(user)){
	        	catalogSet = filterCatalogsSupplierAdmin(catalogs, profiles, user.getUserId());
            }else{
	        	catalogSet = filterCatalogsPerProfile(catalogs, profiles,user.getUserId());
	        }

	        summaryCard.setTotalCatalogsCount(catalogSet.size());
	        summaryCard.setApprovalWaitCatalogsCount(this.getApprovalWaitCatalogsCount(catalogSet));
            summaryCard.setRejectedCatalogsCount(this.getRejectedCatalogsCount(catalogSet));
	        summaryCard.setErrorCatalogsCount(this.getErrorCatalogsCount(catalogSet));
	        summaryCard.setPublishWaitCatalogsCount(this.getPublishWaitCatalogsCount(catalogSet));
	        summaryCard.setMasterAdmin(0);
		}

		return summaryCard;
	}

  /**
   * Gets home page summary data for master admin user of this unitId
   *
   * @param unitId
   * @return
   * @author Muhammad Haris (Refactored and Optimized)
   */
  protected SummaryCard getSummaryForMasterAdmin(String unitId) {
    logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    logger.debug("GETTING HOME PAGE SUMMARY FOR COMPANY: {}", unitId);
    logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    SummaryCard summaryCard = new SummaryCard();
    setCatalogsSummary(unitId, summaryCard);
    setSuppliersSummary(unitId, summaryCard);
    setUsersSummary(unitId, summaryCard);
    return summaryCard;
  }

  /**
   * Sets catalog counts in summary card by retrieving them from respective service
   *
   * @param unitId
   * @param summaryCard
   * @author Muhammad Haris
   */
  protected void setCatalogsSummary(String unitId, SummaryCard summaryCard) {
    try {
      logger.debug("Setting Catalogs Summary for Company: {}", unitId);
      logger.debug("-------------------------------------------------------");

      List<GroupedCatalogCountDTO> groupedCatalogCounts = catalogService.getGroupedCatalogCounts(
          unitId, "catalogStateId");

    /* if counts are available for this company */
      if (!CollectionUtils.isEmpty(groupedCatalogCounts)) {
        Map<CatalogState, Long> stateWiseCatalogCounts =
            getCatalogStateWiseCounts(groupedCatalogCounts);

        summaryCard.setTotalCatalogsCount(getTotalCatalogsCount(groupedCatalogCounts));
        summaryCard.setApprovalWaitCatalogsCount(MapUtils.getLongValue(stateWiseCatalogCounts,
                                                                       WORKING, 0));
        summaryCard.setRejectedCatalogsCount(MapUtils.getLongValue(
            stateWiseCatalogCounts, REJECTED, 0));
        summaryCard.setPublishWaitCatalogsCount(MapUtils.getLongValue(
            stateWiseCatalogCounts, APPROVED, 0));
      }

      summaryCard.setErrorCatalogsCount(catalogService.getErroneousCatalogsCount(unitId));
    } catch (Exception e) {
      logger.error("Exception in setting catalogs summary for unitId: " + unitId, e);
    }
  }

  /**
   * Sets user counts in summary card by retrieving them from respective service
   *
   * @param unitId
   * @param summaryCard
   * @author Muhammad Haris
   */
  protected void setUsersSummary(String unitId, SummaryCard summaryCard) {
    try {
      logger.debug("-------------------------------------------------------");
      logger.debug("Setting Users Summary for Company: {}", unitId);
      logger.debug("-------------------------------------------------------");

      Map<String, Long> roleWiseUserCounts = userManagementService.getRoleWiseUserCounts(unitId);

      summaryCard.setAdminUsersCount(getAdminUsersCount(roleWiseUserCounts));
      summaryCard
          .setShopperUsersCount(MapUtils.getLongValue(roleWiseUserCounts, SHOPPER.toString(), 0));
      summaryCard
          .setWebSearchUsersCount(
              MapUtils.getLongValue(roleWiseUserCounts, WEB_SEARCH_USER.toString(), 0));
      summaryCard.setBuyerUsersCount(MapUtils.getLongValue(roleWiseUserCounts, BUYER.toString(), 0));
      summaryCard
          .setApproverUsersCount(MapUtils.getLongValue(roleWiseUserCounts, APPROVER.toString(), 0));
    } catch (Exception e) {
      logger.error("Exception in setting users summary for unitId: " + unitId, e);
    }
  }

  protected void setSuppliersSummary(String unitId, SummaryCard summaryCard) {
    try {
      logger.debug("-------------------------------------------------------");
      logger.debug("Setting Suppliers Summary for Company: {}", unitId);
      logger.debug("-------------------------------------------------------");

      Map<String, Long> statusWiseSupplierCounts =
          supplierService.getStatusWiseSupplierCounts(unitId);
      summaryCard
          .setActiveSuppliersCount(MapUtils.getLongValue(statusWiseSupplierCounts, "ACTIVE", 0));
      summaryCard.setInactiveSuppliersCount(
          MapUtils.getLongValue(statusWiseSupplierCounts, "INACTIVE", 0));
    } catch (Exception e) {
      logger.error("Exception in setting suppliers summary for unitId: " + unitId, e);
    }
  }

  protected long getTotalCatalogsCount(List<GroupedCatalogCountDTO> groupedCatalogCountDTOs) {
    long totalCatalogsCount = 0;
    for (GroupedCatalogCountDTO groupedCatalogCountDTO : groupedCatalogCountDTOs) {
      totalCatalogsCount += groupedCatalogCountDTO.getCount().longValue();
    }

    return totalCatalogsCount;
  }

  protected Map<CatalogState, Long> getCatalogStateWiseCounts(
      List<GroupedCatalogCountDTO> groupedCatalogCounts) {
    Map<CatalogState, Long> stateWiseCounts = new HashMap<>(groupedCatalogCounts.size());

    for (GroupedCatalogCountDTO groupedCatalogCount : groupedCatalogCounts) {

      if (groupedCatalogCount.getCatalogStateId() == WORKING.getStateId()) {
        stateWiseCounts.put(WORKING, groupedCatalogCount.getCount().longValue());
      } else if (groupedCatalogCount.getCatalogStateId() == APPROVED.getStateId()) {
        stateWiseCounts.put(APPROVED, groupedCatalogCount.getCount().longValue());
      } else if (groupedCatalogCount.getCatalogStateId() == REJECTED.getStateId()) {
        stateWiseCounts.put(REJECTED, groupedCatalogCount.getCount().longValue());
      }
    }

    return stateWiseCounts;
  }

  protected long getAdminUsersCount(Map<String, Long> roleWiseUserCounts) {
    long count = MapUtils.getLongValue(roleWiseUserCounts, MASTER_ADMINISTRATOR.toString(), 0);
    count += MapUtils.getLongValue(roleWiseUserCounts, ADMINISTRATOR.toString(), 0);
    return count;
  }


    private boolean isSupplierAdmin(User user){
        for(Role role:user.getRoles()){
            if(role == Role.SUPPLIER_ADMIN)return  true;
        }
        return false;
    	
    }
    
    private Set<CatalogSummary> filterCatalogsSupplierAdmin(List<CatalogSummary> allCatalogs, List<Profile> profiles,String userId){
        Set<String>  associatedCatalogIds = new HashSet<String>();
        for(Profile profile: profiles){
        	if(null!= profile.getAssociatedCatalogs()){
        		associatedCatalogIds.addAll(profile.getAssociatedCatalogs());
        	}
        }
        
        for (CatalogSummary catSum : allCatalogs) {
        
			if(catSum.getSubmitterId().equals(userId)){
				associatedCatalogIds.add(catSum.getCatalogId());
			}
			
			
			if(CatalogDataDisplayPopulator.isValidSupplierId(catSum.getSupplierId())){
				Supplier supplier = supplierService.getSupplier(catSum.getUnitId() , catSum.getSupplierId());
				if(null != supplier){
                    List<String> supAdmins = supplier.getSupplierAdmins();
                    if(supAdmins != null){
                        for (String adminid : supAdmins) {
                            if(adminid.equals(userId)){
                                associatedCatalogIds.add(catSum.getCatalogId());
                            }
                        }
                    }
                }
			}
		}
        
        return subtractCatalogs(allCatalogs, associatedCatalogIds);
    }    
        
	
    private Set<CatalogSummary> filterCatalogsApprover(List<CatalogSummary> allCatalogs, List<Profile> profiles,String userId){
        Set<String>  associatedCatalogIds = new HashSet<String>();
        
        for(Profile profile: profiles){
        	if(profile.isActive() && null!= profile.getAssociatedCatalogs()){
        		associatedCatalogIds.addAll(profile.getAssociatedCatalogs());
        	}
        }
        
        for (CatalogSummary catSum : allCatalogs) {
        	List<String> approvers =catSum.getApprovers();
        	if(approvers != null){
	        	for (String approverId : approvers) {
					if(approverId.equals(userId)){
						associatedCatalogIds.add(catSum.getCatalogId());
					}
				}
        	}
        	
			if(catSum.getSubmitterId().equals(userId)){
				associatedCatalogIds.add(catSum.getCatalogId());
			}
			
			if(CatalogDataDisplayPopulator.isValidSupplierId(catSum.getSupplierId())){
				Supplier supplier = supplierService.getSupplier(catSum.getUnitId() , catSum.getSupplierId());
				if(null != supplier){
					approvers = supplier.getApproverIds();
		        	if(approvers != null){
			        	for (String approverId : approvers) {
							if(approverId.equals(userId)){
								associatedCatalogIds.add(catSum.getCatalogId());
							}
						}
		        	}
				}
			}			
		}
        return subtractCatalogs(allCatalogs, associatedCatalogIds);
    }  	
	
    private Set<CatalogSummary> filterCatalogsPerProfile(List<CatalogSummary> allCatalogs, List<Profile> profiles,String userId){
        Set<String>  associatedCatalogIds = new HashSet<String>();
        
        for(Profile profile: profiles){
        	if(profile.isActive() && null!= profile.getAssociatedCatalogs()){
        		associatedCatalogIds.addAll(profile.getAssociatedCatalogs());
        	}
        }
        
        for (CatalogSummary catSum : allCatalogs) {
			if(catSum.getSubmitterId().equals(userId)){
				associatedCatalogIds.add(catSum.getCatalogId());
			}
		}
        return subtractCatalogs(allCatalogs, associatedCatalogIds);
    }	
	
    private Set<CatalogSummary> subtractCatalogs(List<CatalogSummary> catalogSummaries, Set<String> ids){
        if(catalogSummaries == null || catalogSummaries.size() == 0 || ids == null || ids.size() == 0){
            return new HashSet<CatalogSummary>();
        }
        Set<CatalogSummary> remainderCatalogs = new HashSet<CatalogSummary>();
        for(String userId: ids) {
            for(CatalogSummary catalogSummary: catalogSummaries){
                if(catalogSummary.getCatalogId().equals(userId)){
                    remainderCatalogs.add(catalogSummary);
                    catalogSummaries.remove(catalogSummary);
                    break;
                }

            }
        }
        return remainderCatalogs;
    }
    
    
	   private List<Profile> getProfilesByUser(List<Integer> associatedProfiles){
	    	try{
	    		if(null != associatedProfiles && associatedProfiles.size()>0){
			    	List<ProfileProxy> profileProxyList = profileService.getProfilesByIds(associatedProfiles);
			    	return convertToProfileList(profileProxyList);
	    		}
	    	}catch (Exception e) {
			}
	    	
	    	return new ArrayList<Profile>();

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

	private boolean isApprover(List<Role> roles){
		if(roles != null){
			for (Role role : roles) {
				if(role.toString().equals(Role.APPROVER.toString())){
					return true;
				}
			}
		}
		
		return false;
	}
    
	private boolean isMasterAdmin(List<Role> roles){
		if(roles != null){
			for (Role role : roles) {
				if(role.toString().equals(MASTER_ADMINISTRATOR.toString())){
					return true;
				}
			}
		}
		
		return false;
	}



    private int getRejectedCatalogsCount(Set<CatalogSummary> catalogs){

        int count =0;
        for(CatalogSummary catalogSummary:catalogs){
            if(catalogSummary.getCatalogStateId()==3){
                count++;
            }
        }
        return count;

    }
    
    private int getErrorCatalogsCount(Set<CatalogSummary> catalogs){
        int count =0;

        for(CatalogSummary catalogSummary:catalogs){
            if((catalogSummary.getErrorDescription()!=null && !"".equals(catalogSummary.getErrorDescription().trim())) || (catalogSummary.getFailedRecords()!=null && catalogSummary.getFailedRecords()>0)){
                count++;
            }
        }
        return count;
    }
    private int getApprovalWaitCatalogsCount(Set<CatalogSummary> catalogs){
        int count =0;
        for(CatalogSummary catalogSummary:catalogs){
            if(catalogSummary.getCatalogStateId()==1){
                count++;
            }
        }
        return count;

    }    
    private int getPublishWaitCatalogsCount(Set<CatalogSummary> catalogs){
        int count =0;
        for(CatalogSummary catalogSummary:catalogs){
            if(catalogSummary.getCatalogStateId()==2){
                count++;
            }
        }
        return count;
    }
}
