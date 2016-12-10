package com.vroozi.customerui.powershopper;

import java.util.List;

import com.vroozi.customerui.query.Result;
import com.vroozi.customerui.user.services.user.model.User;

public interface ContentShareMappingService {

  Result<ContentShareMapping> getContentShareMappings(String unitId, Integer pageNumber,
                                                      Integer pageSize, String searchItem,
                                                      String userName);

  ContentShareMapping createContentShareMapping(ContentShareMapping contentShareMapping);

  int deleteContentShareMapping(List<String> contentShareMappingId);

  int updateContentShareMapping(String contentShareMappingId,
                                ContentShareMapping contentShareMapping);

  List<User> getUserByUserName(String unitId, String userName);

  void updateUser(String userId, String updateString);

  ContentShareMapping getContentShareMappingByUsername(String username);


  void uploadContentShareMapping(String unitId, String userId, String filePath);

  ContentShareMappingStatus getContentShareMappingStatus(String unitId);

  String generateContentShareMappingFile(String unitId, String filename);
}
