/**
 * 
 */
package com.vroozi.customerui.profile.services;

import java.util.List;

import com.vroozi.customerui.profile.model.ContentAccessDTO;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.user.services.user.model.User;

/**
 * @author mhabib
 * 
 */
public interface ProfileService {

	List<Profile> getProfilesByUnitId(String unitId) throws Exception;

    List<Profile> getProfilesPageNationDataByUnitId(String unitId, int pageNum) throws Exception;

    List<Profile> getProfilesPageNationDataByUnitId(String unitId, int pageNum, String filter) throws Exception;

	List<ProfileProxy> getProfilesByIds(List<Integer> profileIds) throws Exception;

    List<Profile> getProfilesByUser(User user);

    List<Profile> convertToProfileList(List<ProfileProxy> profileProxyList);
    List<Profile> getProfilesByUserProfileIds(List<Integer> associatedProfiles);

	List<ProfileProxy> getProfilesByCatalogId(String unitId, String catalogId) throws Exception;

	List<Integer> getProfileListForCatalog(String unitId, String catalogId);
	
	ProfileProxy addProfile(ProfileProxy profile) throws Exception;

	int deleteProfiles(String unitId, List<Integer> profileIds) throws Exception;

	int updateProfileStatus(boolean active, List<Integer> profileIds) throws Exception;

    void updateProfile(ProfileProxy profile) throws Exception;

	int deleteCatalogProfiles(String catalogId, List<Integer> profileIds) throws Exception;

    int deleteCatalogProfiles(String catalogId, String[] profileIds) throws Exception;

	int addCatalogProfiles(String catalogId, List<Integer> profileIds) throws Exception;

    int addCatalogProfiles(String catalogId, String[] profileIds) throws Exception;

	boolean profileExists(String profileName,String unitId);
	
	Profile getProfileByName(String profileName,String unitId);
	List<Profile> getActiveProfilesByUnitId(String unitId) throws Exception;
    List<Profile> getProfilesByGroup(String groupId, String unitId) throws Exception;

    public void associateCatalogToSupplierProfiles(String unitId, String catalogId, String supplierId) throws Exception;
    public void disAssociateCatalogToSupplierProfiles(String unitId, String catalogId, String supplierId) throws Exception;
    
    ContentAccessDTO findProfilesByUser(int unitId,String userid, String cGroupToken)throws Exception;
    
    
}
