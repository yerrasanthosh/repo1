/**
 * 
 */
package com.vroozi.customerui.profile.services.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.user.services.user.model.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.catalog.services.rest.CatalogRestClientImpl;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.profile.model.ContentAccessDTO;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.util.RestServiceUrl;

/**
 * @author mhabib
 * 
 */

public class ProfileRestClientImpl implements ProfileService {

	private final Logger LOGGER = LoggerFactory.getLogger(CatalogRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;

	@Override
	public List<Profile> getProfilesByUnitId(String unitId) throws Exception {
        try {
        	Profile[] profilesArray =  new RestTemplate().getForObject(restServiceUrl.catalogProfileURI() + "/unitid/" + unitId, Profile[].class);
        	List<Profile> profiles = Arrays.asList(profilesArray);
        	return profiles;
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
	}

    @Override
    public List<Profile> getActiveProfilesByUnitId(String unitId) throws Exception {
        try {
            Profile[] profilesArray =  new RestTemplate().getForObject(restServiceUrl.catalogProfileURI() + "/active/unitid/" + unitId, Profile[].class);
            List<Profile> profiles = Arrays.asList(profilesArray);
            return profiles;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public List<Profile> getProfilesPageNationDataByUnitId(String unitId, int pageNum) throws Exception {
        try {
            Profile[] profilesArray =  new RestTemplate().getForObject(restServiceUrl.catalogProfileURI() + "/unitid/" + unitId + "/pageno/" + pageNum, Profile[].class);
            List<Profile> profiles = Arrays.asList(profilesArray);
            return profiles;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
    }

    public List<Profile> getProfilesPageNationDataByUnitId(String unitId, int pageNum, String filter) throws Exception{
        try {
            Profile[] profilesArray =  new RestTemplate().getForObject(restServiceUrl.catalogProfileURI() + "/unitid/" + unitId + "/pageno/" + pageNum + "/active/" + filter, Profile[].class);
            List<Profile> profiles = Arrays.asList(profilesArray);
            return profiles;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
    }

	@Override
	public List<ProfileProxy> getProfilesByIds(List<Integer> profileIds) throws Exception {
		try {
			String profileIdString = StringUtils.join(profileIds, ',');
        	ProfileProxy[] profilesArray =  new RestTemplate().getForObject(
        			restServiceUrl.catalogProfileURI() + "/profileids/"+profileIdString, ProfileProxy[].class);
        	List<ProfileProxy> profiles = Arrays.asList(profilesArray);
        	return profiles;
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
	}

    @Override
    public List<Profile> getProfilesByUser(User user) {
        List<Profile> profiles = new ArrayList<Profile>();
        try{
            List<Role> userRoles = user.getRoles();
            boolean isMaster = false;
            for (Role role : userRoles) {
                if(role == role.MASTER_ADMINISTRATOR){
                    isMaster = true;
                    break;
                }
            }


            if(isMaster){
                profiles = getActiveProfilesByUnitId(user.getUnitId());
            }else{
                if(user.getAssignedProfiles()!=null && !user.getAssignedProfiles().isEmpty()){
                    List<ProfileProxy> allProfileProxies = getProfilesByIds(user.getAssignedProfiles());
                    profiles = convertToProfileList(allProfileProxies);
                }
            }
        }catch (Exception e) {
            //logger.error(e.getMessage(),e);
        }

        return profiles;
    }

    public List<Profile> getProfilesByUserProfileIds(List<Integer> associatedProfiles){
        try{
            if(null != associatedProfiles && associatedProfiles.size()>0){
                List<ProfileProxy> profileProxyList = getProfilesByIds(associatedProfiles);
                return convertToProfileList(profileProxyList);
            }
        }catch (Exception e) {
            //logger.error("EXCEPTION WHILE FETCHING PROFILES.......",e);
        }

        return new ArrayList<Profile>();

    }

    public List<Profile> convertToProfileList(List<ProfileProxy> profileProxyList){
        List<Profile> profiles = new ArrayList<Profile>();
        Profile profile = null;
        for (ProfileProxy profileProxy : profileProxyList) {
            if(profileProxy.isActive()){
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
        }

        return profiles;
    }

    @Override
	public List<ProfileProxy> getProfilesByCatalogId(String unitId, String catalogId) throws Exception {
        try {
        	ProfileProxy[] profilesArray =  new RestTemplate().getForObject(restServiceUrl.catalogProfileURI()+ "/forcatalog/unitid/"+unitId + "/catalogId/"+catalogId, ProfileProxy[].class);
        	List<ProfileProxy> profiles = Arrays.asList(profilesArray);
        	return profiles;
        } catch(RestClientException rse) {
        	LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
        	LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
	}
	
    @Override
    public List<Integer> getProfileListForCatalog(String unitId, String catalogId){
        Integer[] profilesIds = new Integer[0];
        try{
            profilesIds = new RestTemplate().getForObject(restServiceUrl.catalogProfileURI()+ "/profileIds/unitid/"+unitId + "/catalogid/{catalogId}", Integer[].class, catalogId);
        }catch (Exception exp) {
        	LOGGER.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
        return Arrays.asList(profilesIds);
    }

	@Override
	public ProfileProxy addProfile(ProfileProxy profile) throws Exception {
		try {
//			ProfileProxy p1 = new ProfileProxy();
//	    	p1.setProfileId(1);
//	    	p1.setProfileName("profile name");
//	    	p1.setProfileDesc("profile desc1");
//	    	p1.setProductRating(12);
//	    	p1.setActive("Active");
//	    	p1.setCreatedBy("setCreatedBy");
//	    	p1.setCreatedOn("setCreatedOn");

			profile = new RestTemplate().postForObject(
					restServiceUrl.catalogProfileURI(), profile,
					ProfileProxy.class);
		} catch (RestClientException rse) {
			LOGGER.error("Error saving profile!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error saving profile!", exp);
			throw new AdminUIException(exp);
		}
        return profile;
	}

	@Override
	public int updateProfileStatus(boolean active,
			List<Integer> profileIds)
			throws Exception {
		try {
			String action = active? "A" : "D";
			String profileIdString = StringUtils.join(profileIds, ',');
			new RestTemplate().put(
					restServiceUrl.catalogProfileURI() + "/action/"+action+"/profileids/"+profileIdString, "");
		} catch (RestClientException rse) {
			LOGGER.error("Error updating profile!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error updating profile!", exp);
			throw new AdminUIException(exp);
		}
		return 0;
	}

    @Override
    public void updateProfile(ProfileProxy profile) throws Exception {
        try {
            new RestTemplate().put(restServiceUrl.catalogProfileURI(), profile);
        } catch (RestClientException rse) {
            LOGGER.error("Error updating profile!", rse);
            throw rse;
        } catch (Exception exp) {
            LOGGER.error("Error updating profile!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
	public int deleteProfiles(String unitId, List<Integer> profileIds)
			throws Exception {
		try {
			String profileIdString = StringUtils.join(profileIds, ',');
			new RestTemplate().delete(
					restServiceUrl.catalogProfileURI() + "/unitid/"+unitId + "/profileids/"+profileIdString);
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
	public int addCatalogProfiles(String catalogId, List<Integer> profileIds)
			throws Exception {
		try {
			String profileIdString = StringUtils.join(profileIds, ',');
			new RestTemplate().put(
					restServiceUrl.catalogProfileURI() + "/catalogid/"+catalogId+"/profileids/"+profileIdString, "");
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
    public int addCatalogProfiles(String catalogId, String[] profileIds){
        List<Integer> profileIdList = new ArrayList<Integer>(profileIds.length);
        for(String profileId:profileIds){
            try{
                profileIdList.add(Integer.parseInt(profileId));
            }catch (Exception exp){}
        }
        try{
            return addCatalogProfiles(catalogId, profileIdList);
        }catch (Exception exp){
            LOGGER.error("Error deleting profile!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void associateCatalogToSupplierProfiles(String unitId, String catalogId, String supplierId) throws Exception{
      try {
           new RestTemplate().put(
                    restServiceUrl.userDataServiceURI() + "/organization/"+unitId+"/suppliers/"+supplierId+"/catalog/"+catalogId+"/associate",null);
        } catch (RestClientException rse) {
            LOGGER.error("Error associate profile!", rse);
            throw rse;
        } catch (Exception exp) {
            LOGGER.error("Error associate profile!", exp);
            throw new AdminUIException(exp);
        }
    }
    @Override
    public void disAssociateCatalogToSupplierProfiles(String unitId, String catalogId, String supplierId) throws Exception{
        try {
            new RestTemplate().put(
                    restServiceUrl.userDataServiceURI() + "/organization/"+unitId+"/suppliers/"+supplierId+"/catalog/"+catalogId+"/disassociate",null);
        } catch (RestClientException rse) {
            LOGGER.error("Error associate profile!", rse);
            throw rse;
        } catch (Exception exp) {
            LOGGER.error("Error associate profile!", exp);
            throw new AdminUIException(exp);
        }
    }
	
	@Override
	public int deleteCatalogProfiles(String catalogId, List<Integer> profileIds)
			throws Exception {
		try {
			String profileIdString = StringUtils.join(profileIds, ',');
			new RestTemplate().delete(
					restServiceUrl.catalogProfileURI() + "/catalogid/"+catalogId+"/profileids/"+profileIdString);
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
    public int deleteCatalogProfiles(String catalogId, String[] profileIds) throws Exception {
        List<Integer> profileIdList = new ArrayList<Integer>(profileIds.length);
        for(String profileId:profileIds){
            try{
                profileIdList.add(Integer.parseInt(profileId));
            }catch (Exception exp){}
        }
        try{
            return deleteCatalogProfiles(catalogId, profileIdList);
        }catch (Exception exp){
            LOGGER.error("Error deleting profile!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
	public boolean profileExists(String profileName,String unitId) {
		Boolean exists = new RestTemplate().getForObject(
				restServiceUrl.catalogProfileURI() + "/profileName/"+profileName+"/unitId/"+unitId, Boolean.class);
		return exists;
	}

    @Override
	public Profile getProfileByName(String profileName , String unitId) {
		Profile profile = new RestTemplate().getForObject(
				restServiceUrl.catalogProfileURI() + "/profileByName/"+profileName+"/unitId/"+unitId, Profile.class);
		return profile;
	}
    
    
    @Override
    public List<Profile> getProfilesByGroup(String groupId, String unitId) throws Exception {
        try {
            Profile[] profilesArray =  new RestTemplate().getForObject(restServiceUrl.catalogProfileURI() + "/groups/"+groupId+"/unitid/" + unitId, Profile[].class);
            List<Profile> profiles = Arrays.asList(profilesArray);
            return profiles;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
    }
	@Override
	public ContentAccessDTO findProfilesByUser(int unitId, String userid, String cGroupToken)throws Exception {
        try {
        	ContentAccessDTO response =  new RestTemplate().getForObject(restServiceUrl.getUserProfilesPath()+"?unitid="+unitId+"&userid=" +userid+"&token=" +cGroupToken, ContentAccessDTO.class);

            return response;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving profiles!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving profiles!", exp);
            throw new AdminUIException(exp);
        }
	}
}
