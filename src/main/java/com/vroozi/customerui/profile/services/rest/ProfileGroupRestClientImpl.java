/**
 * 
 */
package com.vroozi.customerui.profile.services.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.materialgroup.model.MaterialGroupProxy;
import com.vroozi.customerui.materialgroup.model.MaterialGroupStateProxy;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.model.ProfileGroupFilter;
import com.vroozi.customerui.profile.model.ProfileGroupMapping;
import com.vroozi.customerui.profile.model.ProfileGroupMappingPost;
import com.vroozi.customerui.profile.model.ProfileGroupMappingStatus;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.util.Page;
import com.vroozi.customerui.util.RestServiceUrl;

/**
 * @author Muhammad Sajid Khan
 * 
 */

@Component
public class ProfileGroupRestClientImpl implements ProfileGroupService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProfileGroupRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;


    @Override
    public Page<ProfileGroup> getPagedProfileGroups(ProfileGroupFilter searchFilter, String unitId) {
    	
		Long count = new RestTemplate().getForObject(restServiceUrl.profileGroupURI()+"/count/unitid/{unitId}?searchTerm={searchTerm}", Long.class, unitId , searchFilter.getSearchText());
		List<ProfileGroup> list = (List<ProfileGroup>) new RestTemplate().getForObject(restServiceUrl.profileGroupPage(), List.class, unitId,searchFilter.getPageNumber(),searchFilter.getPageSize(),searchFilter.getSearchText(),searchFilter.getSortField(),searchFilter.isSortAscending());
        Page<ProfileGroup> page = new Page<ProfileGroup>();
        page.setPageItems(list);
        page.setPageNumber(searchFilter.getPageNumber());
        page.setTotalRecords(count);
        long pageCount = count / searchFilter.getPageSize();
        if (count > searchFilter.getPageSize() * pageCount) {
            pageCount++;
        }
        page.setPagesAvailable((int)pageCount);
        return page;  	
    }
    
    @Override
	public int addGroupProfiles(String profileId, List<Integer> groupIds)throws Exception {
		try {
			String groupIdString = StringUtils.join(groupIds, ',');
			new RestTemplate().put(restServiceUrl.profileGroupURI()+ "/profileId/"+profileId+"/groupsIds/"+groupIdString, "");
		} catch (RestClientException rse) {
			LOGGER.error("Error inserting profile group!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error inserting profile group!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
	}
	    
	
	@Override
	public int addGroupProfiles(String profileId, String[] groupIds)throws Exception {
        List<Integer> groupIdList = new ArrayList<Integer>(groupIds.length);
        for(String groupId:groupIds){
            try{
                groupIdList.add(Integer.parseInt(groupId));
            }catch (Exception exp){}
        }
        try{
            return addGroupProfiles(profileId, groupIdList);
        }catch (Exception exp){
            LOGGER.error("Error inserting profile groups!", exp);
            throw new AdminUIException(exp);
        }
	}	
	
	
	@Override
	public ProfileGroup addProfileGroup(ProfileGroup profileGroup)throws Exception {
		try {
			profileGroup = new RestTemplate().postForObject(restServiceUrl.profileGroupURI(), profileGroup,ProfileGroup.class);
		} catch (RestClientException rse) {
			LOGGER.error("Error saving profile!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error saving profile!", exp);
			throw new AdminUIException(exp);
		}
		return profileGroup;
	}
	
	
	@Override
	public int deleteProfileGroups(List<Integer> groupIds) throws Exception {
		try {
			String groupIdString = StringUtils.join(groupIds, ',');
			new RestTemplate().delete(restServiceUrl.profileGroupURI() + "/groupIds/"+groupIdString);
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting profile groups!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting profile groups!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
	}
	
	
	@Override
	public List<ProfileGroup> getActiveGroupsByUnitId(String unitId)throws Exception {
        try {
            ProfileGroup[] groupsArray =  new RestTemplate().getForObject(restServiceUrl.profileGroupURI() + "/user/active/unitid/" + unitId, ProfileGroup[].class);
            List<ProfileGroup> profileGroups = Arrays.asList(groupsArray);
            return profileGroups;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving profile groups!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving profile groups!", exp);
            throw new AdminUIException(exp);
        }
	}
	
	@Override
	public List<Integer> getGroupListForProfiles(String unitId, String profileId) {
        Integer[] groupIds = new Integer[0];
        try{
            groupIds = new RestTemplate().getForObject(restServiceUrl.profileGroupURI()+ "/groupIds/unitid/"+unitId + "/profileId/{profileId}", Integer[].class, profileId);
        }catch (Exception exp) {
        	LOGGER.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
        return Arrays.asList(groupIds);
	}

	@Override
	public ProfileGroup getProfileGroupByName(String groupName , String unitId) {
		ProfileGroup profileGroup = new RestTemplate().getForObject(restServiceUrl.profileGroupURI() + "/groupName/"+groupName+"/unitId/"+unitId, ProfileGroup.class);
		return profileGroup;
	}	

	@Override
	public List<ProfileGroup> getProfileGroupsByIds(List<Integer> groupIds)throws Exception {
		try {
			String groupIdString = StringUtils.join(groupIds, ',');
        	ProfileGroup[] groupArray =  new RestTemplate().getForObject(restServiceUrl.profileGroupURI()+ "/groupIds/"+groupIdString, ProfileGroup[].class);
        	List<ProfileGroup> profileGroups = Arrays.asList(groupArray);
        	return profileGroups;
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profile groups!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profile groups!", exp);
            throw new AdminUIException(exp);
        }
	}

	@Override
	public ProfileGroup getProfileGroupsById(String groupId)throws Exception {
		try {
			
        	ProfileGroup[] groupArray =  new RestTemplate().getForObject(restServiceUrl.profileGroupURI()+ "/groupIds/"+groupId, ProfileGroup[].class);
        	List<ProfileGroup> profileGroups = Arrays.asList(groupArray);
        	
        	if(profileGroups == null || profileGroups.size() == 0){
        		return null;
        	}else{
        		return profileGroups.get(0); 	
        	}
        	
        	
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profile groups!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profile groups!", exp);
            throw new AdminUIException(exp);
        }
	}	
	
	@Override
	public List<ProfileGroup> getProfileGroupsByUnitId(String unitId)throws Exception {
		try {
        	ProfileGroup[] profileGroupsArray =  new RestTemplate().getForObject(restServiceUrl.profileGroupURI() + "/unitid/" + unitId, ProfileGroup[].class);
        	List<ProfileGroup> profileGroups = Arrays.asList(profileGroupsArray);
        	return profileGroups;
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profile groups!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profile groups!", exp);
            throw new AdminUIException(exp);
        }
		
	}	

	
	
	@Override
	public List<ProfileGroup> getProfileGroupsPageNationDataByUnitId(String unitId, int pageNum, String filter) throws Exception {
        try {
            ProfileGroup[] profileGroupsArray =  new RestTemplate().getForObject(restServiceUrl.profileGroupURI() + "/unitid/" + unitId + "/pageno/" + pageNum + "/active/" + filter, ProfileGroup[].class);
            List<ProfileGroup> profileGroups = Arrays.asList(profileGroupsArray);
            return profileGroups;
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profile groups!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profile groups!", exp);
            throw new AdminUIException(exp);
        }
	}
	
	@Override
	public boolean groupExists(String groupName) {
		try{
			Boolean exists = new RestTemplate().getForObject(restServiceUrl.profileGroupURI() + "/groupName/"+groupName, Boolean.class);
			return exists;
	    } catch(RestClientException rse) {
	    	LOGGER.error("Error retrieving profile groups!", rse);
	        throw rse;
	    } catch(Exception exp) {
	    	LOGGER.error("Error retrieving profile groups!", exp);
	        throw new AdminUIException(exp);
	    }
	}


	@Override
	public void updateProfileGroup(ProfileGroup profileGroup) throws Exception {
        try {
            new RestTemplate().put(restServiceUrl.profileGroupURI(), profileGroup);
        } catch (RestClientException rse) {
            LOGGER.error("Error updating profile group!", rse);
            throw rse;
        } catch (Exception exp) {
            LOGGER.error("Error updating profile group!", exp);
            throw new AdminUIException(exp);
        }
		
	}
	
	@Override
	public int updateProfileGroupStatus(boolean active, List<Integer> groupIds)throws Exception {
		try {
			String action = active? "A" : "D";
			String groupIdString = StringUtils.join(groupIds, ',');
			new RestTemplate().put(restServiceUrl.profileGroupURI() + "/action/"+action+"/groupIds/"+groupIdString, "");
        } catch (RestClientException rse) {
            LOGGER.error("Error updating profile group!", rse);
            throw rse;
        } catch (Exception exp) {
            LOGGER.error("Error updating profile group!", exp);
            throw new AdminUIException(exp);
        }
		return 0;
	}
	
	@Override
	public List<ProfileGroup> getProfileGroupsPageNationDataByUnitId(String unitId, int pageNum) throws Exception {
	    try {
	        ProfileGroup[] profileGroupsArray =  new RestTemplate().getForObject(restServiceUrl.profileGroupURI() + "/unitid/" + unitId + "/pageno/" + pageNum, ProfileGroup[].class);
	        List<ProfileGroup> profileGroups = Arrays.asList(profileGroupsArray);
	        return profileGroups;
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profile groups!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profile groups!", exp);
            throw new AdminUIException(exp);
        }
	}	
	
	
	
	
	@Override
	public int deleteGroupProfiles(String profileId, String[] groupIds)throws Exception {
        List<Integer> groupIdList = new ArrayList<Integer>(groupIds.length);
        for(String groupId:groupIds){
            try{
                groupIdList.add(Integer.parseInt(groupId));
            }catch (Exception exp){}
        }
        try{
            return deleteGroupProfiles(profileId, groupIdList);
        }catch (Exception exp){
            LOGGER.error("Error deleting profile!", exp);
            throw new AdminUIException(exp);
        }

	}

	
	@Override
	public ProfileGroup getProfileGroupByToken(String token,String unitId){
		try {
			ProfileGroup profileGroup = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/profilegroups/groupbytoken/"+token+"/unitid/"+unitId, ProfileGroup.class);
			return profileGroup;

		} catch (RestClientException rse) {
			LOGGER.error("Error searching by token!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error searching by token!", exp);
			throw new AdminUIException(exp);
		}
	}	
	
	@Override
	public int deleteGroupProfiles(String profileId, List<Integer> groupIds)throws Exception {
		try {
			String groupIdString = StringUtils.join(groupIds, ',');
			new RestTemplate().delete(restServiceUrl.profileGroupURI() + "/profileId/"+profileId+"/groupids/"+groupIdString);
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting profile!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting profile!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
		
		
	}

	@Override
	public List<ProfileGroup> getGroupsByProfileId(String unitId,String profileId) throws Exception {

		return null;
	}
	

	@Override
	public ProfileGroupMappingStatus getProfileGroupMappingStatus(String unitId) {
		
		try{
	        ProfileGroupMappingStatus mappingStatus = new RestTemplate().getForObject(restServiceUrl.profileGroupMappingStatusURI(), ProfileGroupMappingStatus.class, unitId);
			return mappingStatus;
		}catch (Exception e) {
			LOGGER.error("ERROR WHILE CHECKING PROFILE GROUP STATUS...",e);
		}
		return null;
	}
	
	@Override
	public Page<ProfileGroupMapping> getPagedProfileGroupMapping(String unitId,int pageNumber, int pageSize, String searchTerm) {
		
		Long count = new RestTemplate().getForObject(restServiceUrl.profileGroupMappingCountURI(), Long.class, unitId, searchTerm);
        List<ProfileGroupMapping> list=(List<ProfileGroupMapping>)new RestTemplate().getForObject(restServiceUrl.profileGroupMappingPageURI(), List.class, unitId, pageNumber, pageSize, searchTerm);
        Page<ProfileGroupMapping> page = new Page<ProfileGroupMapping>();
        page.setPageItems(list);
        page.setPageNumber(pageNumber);
        page.setTotalRecords(count);
        long pageCount = count / pageSize;
        if (count > pageSize * pageCount) {
            pageCount++;
        }
        page.setPagesAvailable((int)pageCount);
        return page;
	}
	
	@Override
	public void uploadProfileGroupMapping(ProfileGroupMappingPost profileGroupPost) throws Exception {
		new RestTemplate().postForLocation(restServiceUrl.profileGroupMappingUploadURI(), profileGroupPost);
	}

	
	@Override
	public void saveContentAccess(ProfileGroupMapping groupMapping)throws Exception {
		new RestTemplate().postForLocation(restServiceUrl.contentAccessURI(), groupMapping);
		
	}
	
	@Override
	public void removeContentAccess(List<String> contentAccess) {
		new RestTemplate().postForLocation(restServiceUrl.deleteContentAccessURI(), contentAccess);
		
	}

	@Override
	public ProfileGroupMapping findContentAccess(String groupName, String user, int unitId,String uniqueSystemId) {
		ProfileGroupMapping mapping = new RestTemplate().getForObject(restServiceUrl.contentAccessURI()+"?groupname="+groupName+"&user="+user+"&unitid="+unitId+"&uniqueSystemId="+uniqueSystemId, ProfileGroupMapping.class);
		return mapping;
	}	

	@Override
	public void downloadMappings(String unitid, String path) throws Exception {
		new RestTemplate().getForObject(restServiceUrl.downloadContentAccessURI()+"/unitid/"+unitid+"?filename="+path, Integer.class);
	}
}
