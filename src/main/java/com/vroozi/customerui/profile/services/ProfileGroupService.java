/**
 * 
 */
package com.vroozi.customerui.profile.services;

import java.util.List;

import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.model.ProfileGroupFilter;
import com.vroozi.customerui.profile.model.ProfileGroupMapping;
import com.vroozi.customerui.profile.model.ProfileGroupMappingPost;
import com.vroozi.customerui.profile.model.ProfileGroupMappingStatus;
import com.vroozi.customerui.util.Page;

/**
 * @author Muhammad Sajid Khan
 * 
 */
public interface ProfileGroupService {
	ProfileGroup getProfileGroupsById(String groupId)throws Exception;
	List<ProfileGroup> getProfileGroupsByUnitId(String unitId) throws Exception;
    List<ProfileGroup> getProfileGroupsPageNationDataByUnitId(String unitId, int pageNum) throws Exception;
    List<ProfileGroup> getProfileGroupsPageNationDataByUnitId(String unitId, int pageNum, String filter) throws Exception;
	List<ProfileGroup> getProfileGroupsByIds(List<Integer> groupIds) throws Exception;
	List<ProfileGroup> getGroupsByProfileId(String unitId, String profileId) throws Exception;
	List<Integer> getGroupListForProfiles(String unitId, String profileId);
	ProfileGroup addProfileGroup(ProfileGroup profileGroup) throws Exception;
	
	int deleteProfileGroups(List<Integer> groupIds) throws Exception;
	int updateProfileGroupStatus(boolean active, List<Integer> groupIds) throws Exception;
    void updateProfileGroup(ProfileGroup profileGroup) throws Exception;
	int deleteGroupProfiles(String profileId, List<Integer> groupIds) throws Exception;
    int deleteGroupProfiles(String profileId, String[] groupIds) throws Exception;
	int addGroupProfiles(String profileId, List<Integer> groupIds) throws Exception;
    int addGroupProfiles(String profileId, String[] groupIds) throws Exception;
	boolean groupExists(String groupName);

	ProfileGroup getProfileGroupByName(String groupName , String unitId);
	ProfileGroup getProfileGroupByToken(String token,String unitId);
	List<ProfileGroup> getActiveGroupsByUnitId(String unitId) throws Exception;
	ProfileGroupMappingStatus getProfileGroupMappingStatus(String unitId);
	Page<ProfileGroupMapping> getPagedProfileGroupMapping(String unitId, int pageNumber, int pageSize, String searchTerm);	
	void uploadProfileGroupMapping(ProfileGroupMappingPost profileGroupPost) throws Exception;
	
	Page<ProfileGroup> getPagedProfileGroups(ProfileGroupFilter searchFilter,String unitId) ;
	void saveContentAccess(ProfileGroupMapping groupMapping)throws Exception;
	void removeContentAccess(List<String> contentAccess);
	void downloadMappings(String unitid,String path)throws Exception; 
	ProfileGroupMapping findContentAccess(String groupName, String user , int unitId,String uniqueSystemId);
}
