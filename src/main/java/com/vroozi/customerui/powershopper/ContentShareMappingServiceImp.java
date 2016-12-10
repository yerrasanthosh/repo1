package com.vroozi.customerui.powershopper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.query.Result;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.RestServiceUrl;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ContentShareMappingServiceImp implements ContentShareMappingService {

  private static final Logger LOGGER = getLogger(ContentShareMappingServiceImp.class);
  @Autowired
  RestServiceUrl restServiceUrl;

  @Value("${uploadContentShareMappingURI}")
  public String uploadContentShareMappingURI;

  @Value("${contentShareMappingStatusURI}")
  public String contentShareMappingStatusURI;

  @Value("${generateContentShareMappingFileURI}")
  public String generateContentShareMappingFileURI;


  @Override
  public Result<ContentShareMapping> getContentShareMappings(String unitId, Integer pageNumber,
                                                             Integer pageSize, String searchTerm,
                                                             String userName) {

    return (Result<ContentShareMapping>) new RestTemplate()
        .getForObject(restServiceUrl.getfindContentShareMappingsURI(), Result.class, unitId,
                      pageNumber, pageSize, searchTerm, userName);
  }

  @Override
  public ContentShareMapping createContentShareMapping(ContentShareMapping contentShareMapping) {
    return new RestTemplate()
        .postForObject(restServiceUrl.getContentShareMappingsURI(), contentShareMapping,
                       ContentShareMapping.class);
  }

  @Override
  public int deleteContentShareMapping(List<String> contentShareMappingIds) {
    Map<String, Object> responseMap = new RestTemplate()
        .postForObject(restServiceUrl.getdeleteContentShareMappingsURI(), contentShareMappingIds,
                       Map.class);

    return (Integer)responseMap.get("deletedRecords");

  }

  @Override
  public int updateContentShareMapping(String contentShareMappingId,
                                       ContentShareMapping contentShareMapping) {
    int result = 1;

    try {
      new RestTemplate().put(restServiceUrl.getUpdateContentShareMappingURI(), contentShareMapping,
                             contentShareMappingId);
    } catch (RestClientException e) {
      result = 0;
      LOGGER
          .error("An exception occurred while calling rest api to update content share mapping", e);
    }
    return result;
  }

  @Override
  public List<User> getUserByUserName(String unitId, String userName) {
    return Arrays.asList(new RestTemplate()
                             .getForObject(restServiceUrl.getUserByUserNameURI(), User[].class,
                                           unitId, userName));
  }

  @Override
  public void updateUser(String userId, String updateString) {
    new RestTemplate().put(restServiceUrl.getUpdateUserSpecificKeysURI(), updateString, userId);
  }

  @Override
  public ContentShareMapping getContentShareMappingByUsername(String username) {
    return new RestTemplate().getForObject(restServiceUrl.getFindContentShareMappingByUsernameURI(),
                                           ContentShareMapping.class, username);
  }

    @Override
    public void uploadContentShareMapping(String unitId, String userId, String filePath) {
        ContentShareMappingPost contentShareMappingPost =
                new ContentShareMappingPost(unitId, userId, filePath);

        new RestTemplate().postForLocation(uploadContentShareMappingURI,
                contentShareMappingPost);
    }

    @Override
    public ContentShareMappingStatus getContentShareMappingStatus(String unitId) {
        return new RestTemplate().getForObject(contentShareMappingStatusURI,
                ContentShareMappingStatus.class, unitId);
    }

    @Override
    public String generateContentShareMappingFile(String unitId, String filename) {
        return new RestTemplate().getForObject(generateContentShareMappingFileURI,
                String.class, unitId, filename);
    }

}
