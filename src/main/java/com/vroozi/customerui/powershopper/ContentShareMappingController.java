package com.vroozi.customerui.powershopper;

import static com.vroozi.customerui.util.Consts.FAILURE;
import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vroozi.customerui.catalog.model.MaterialGroupForm;
import com.vroozi.customerui.config.AppConfig;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.query.Result;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Consts;

@Controller
public class ContentShareMappingController {

  private static final Logger LOGGER = getLogger(ContentShareMappingController.class);

  @Autowired
  private ContentShareMappingService contentShareMappingService;

  @Autowired
  private ProfileGroupService profileGroupService;
  @Value("${fileUploadPath}")
  public String fileUploadPath;

  @Value("${file.processed.path}")
  public String processedMappingPath;

  @Value("${file.error.reportPath}")
  public String errorReportPath;

  @RequestMapping(value = "/content-sharing-mappings/page", method = GET)
  public String navigateToContentShareMappingPage(ModelMap map, HttpServletRequest request,
                                                  @RequestParam(
                                                          value = "pageNumber") Integer pageNumber,
                                                  @RequestParam(value = "searchTerm", required = false) String searchTerm) {

    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }
      int pageSize = user.getRowsPerPage();
      setMappingStatusAttributes(map, user.getUnitId());

      if (map.get("pgMessage") == null || map.get("pgMessage").toString().isEmpty()) {
        Result<ContentShareMapping> contentSharingMappings =
                contentShareMappingService.getContentShareMappings(user.getUnitId(), pageNumber, pageSize,
                        searchTerm, null);

        if (contentSharingMappings != null) {
          map.addAttribute("powerShoppers", contentSharingMappings.getDataItems());
          map.addAttribute("pgPageNumber", contentSharingMappings.getPagination().getCurrentPage());
          map.addAttribute("pgPagesAvailable", contentSharingMappings.getPagination().getPageCount());
          map.addAttribute("pgTotalRecords", contentSharingMappings.getPagination().getItemCount());
          map.addAttribute("pgProcessedReportStyle", contentSharingMappings.getPagination()
                  .getItemCount() > 0 ? "" : "display:none");
          map.addAttribute("pgProcessedCount", contentSharingMappings.getPagination().getItemCount());
        }

        List<ProfileGroup> profileGroups =
                profileGroupService.getActiveGroupsByUnitId(user.getUnitId());

        if (profileGroups != null) {
          map.addAttribute("profileGroupList", profileGroups);
        }
        map.addAttribute("pgRecordsPerPage", pageSize);
      } else {
        map.addAttribute("pgPagesAvailable", false);
        map.addAttribute("pgProcessFailed", false);

      }
    } catch (Exception e) {
      LOGGER.error("An exception occurred fetching power shopper mapping data.", e);
    }
    return Consts.POWER_SHOPPER_TABLE_FRAGMENT;
  }

  @RequestMapping(value = "/content-sharing-mappings/save", method = POST)
  @ResponseBody
  public String addContentShareMapping(HttpServletRequest request,
                                       @RequestBody ContentShareMapping contentShareMapping) {

    String result = "Failed";
    try {

      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }

      ContentShareMapping
              conShareMapping =
              contentShareMappingService
                      .getContentShareMappingByUsername(contentShareMapping.getUsername());
      if (conShareMapping != null) {
        return "mappingExists";
      }

      contentShareMapping.setUnitId(user.getUnitId());
      ContentShareMapping csm =
              contentShareMappingService.createContentShareMapping(contentShareMapping);
      List<User> users =
              contentShareMappingService.getUserByUserName(user.getUnitId(),
                      contentShareMapping.getUsername());
      updateUser(csm, users);
      result = "success";
    } catch (Exception e) {
      LOGGER.error("An exception occurred creating content mapping mapping data.", e);
    }
    return result;
  }

  private void updateUser(ContentShareMapping csm, List<User> users) {
    if (users != null && !users.isEmpty()) {
      User userArg = users.get(0);
      userArg.setEmail(csm.getEmail());
      userArg.setFirstName(csm.getFirstName());
      userArg.setLastName(csm.getLastName());
      updateUserRole(userArg);
    }
  }

  @RequestMapping(value = "/content-sharing-mappings/delete", method = POST)
  @ResponseBody
  public String deleteContentShareMapping(HttpServletRequest request,
                                          @RequestBody List<String> contentShareMappingIds) {

    String status = "Failed to Delete";
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      int result = 0;
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }
      if (contentShareMappingIds != null && !contentShareMappingIds.isEmpty()) {
        result = contentShareMappingService.deleteContentShareMapping(contentShareMappingIds);
      }
      if (result > 0) {
        status = "content share mapping deleted successfully";
      }
    } catch (Exception e) {
      LOGGER.error("An exception occurred deleting content share mapping data.", e);
    }
    return status;
  }

  @RequestMapping(value = "/content-sharing-mappings/{id}", method = PUT)
  @ResponseBody
  public String updateContentShareMapping(HttpServletRequest request, @PathVariable(
          value = "id") String contentShareMappingId,
                                          @RequestBody ContentShareMapping contentShareMapping) {
    String status = "Failed";
    boolean updateMapping = true;
    int result;
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }
      contentShareMapping.setUnitId(user.getUnitId());
      //Fetch mapping based on username and unit id
      Result<ContentShareMapping> contentShareMappingResult =
              contentShareMappingService.getContentShareMappings(user.getUnitId(), null, null, null,
                      contentShareMapping.getUsername());
      // unable to find mapping in DB? It is a new mapping for username
      if (contentShareMappingResult == null
              || contentShareMappingResult.getDataItems().isEmpty()) {
        updateMapping = false;
      }
      if (updateMapping) {
      /*
      Convert Object/LinkedHashMap to ContentShareMappingObject. If conversion fails, we
      catch exception, LOG error and return failure
       */
        ObjectMapper mapper = new ObjectMapper();
        ContentShareMapping
                contentShareMappingFromDB =
                mapper.convertValue(contentShareMappingResult.getDataItems().get(0),
                        ContentShareMapping.class);
        /*
        If Id doesn't match, we are trying to insert two mappings against the same username.
        Return error
         */
        if (!contentShareMappingFromDB.getId().equals(contentShareMapping.getId())) {
          return "mappingExists";
        }
      }
      //Update mapping in DB
      result =
              contentShareMappingService.updateContentShareMapping(contentShareMappingId,
                      contentShareMapping);
      // Update successful? We need to update user record now
      if (result > 0) {
        List<User> users =
                contentShareMappingService.getUserByUserName(user.getUnitId(),
                        contentShareMapping.getUsername());
        updateUser(contentShareMapping, users);
        status = "updated";
      }
    } catch (Exception e) {
      LOGGER.error("An exception occurred while updating content share mapping.", e);
    }
    return status;
  }

  protected boolean updateUserRole(User user) {

    boolean isShopper = false;
    boolean isPowerShopper = false;
    boolean flag = false;

    try {
      for (Role role : user.getRoles()) {
        if ("SHOPPER".equals(role.toString())) {
          isShopper = true;
        }
        if ("POWER_SHOPPER".equals(role.toString())) {
          isPowerShopper = true;
        }
      }
      if (isShopper) {
        List<Role> roles = user.getRoles();
        if (!isPowerShopper) {
          roles.add(Role.POWER_SHOPPER);
        }

        Map<String, Object> values = new HashMap<>();
        values.put("roles", roles);
        values.put("email", user.getEmail());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());

        ObjectMapper mapper = new ObjectMapper();
        String updateData = mapper.writeValueAsString(values);
        contentShareMappingService.updateUser(user.getUserId(), updateData);
        flag = true;
      }
    } catch (Exception e) {
      LOGGER.error("An exception occurred updating user data.", e);
    }
    return flag;
  }


  @RequestMapping(value = "/powershopper/upload", method = RequestMethod.POST,
      produces = "text/html")
  @ResponseBody
  public String uploadMapping(HttpServletRequest request,
                              @ModelAttribute MaterialGroupForm mappingForm, ModelMap map) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return INVALID_SESSION_PAGE;
      }

      if (mappingForm.getPowerShopperMappingFile() != null
          && !mappingForm.getPowerShopperMappingFile().isEmpty()) {
        String filePath = writeToFile(mappingForm.getPowerShopperMappingFile().getBytes(),
                                      mappingForm.getPowerShopperMappingFile().getOriginalFilename());
        if (filePath != null) {
          contentShareMappingService.uploadContentShareMapping(user.getUnitId(), user.getUserId(),
                                                               filePath);
          map.addAttribute("pgMessage",
                           "The unit of measure mapping file has been submitted for processing.");
          return Consts.SUCCESS;
        }
      }
    } catch (Exception e) {
      LOGGER.error("Failed to upload power shopper mapping.", e);
      map.addAttribute("pgMessage", "Upload failed. " + e.getMessage());
    }
    return Consts.FAILURE;
  }


  @RequestMapping(value = "/contentsharemapping.xlsx")
  public void downloadContentShareMapping(HttpServletRequest request, HttpServletResponse response,
                                          ModelMap modelMap) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      if (user == null) {
        return;
      }


      String fileName =String.format("CONTENT_SHARE_PROCESS_%d_%s.xlsx", System.currentTimeMillis(), user.getUnitId());
      contentShareMappingService.generateContentShareMappingFile(user.getUnitId(), fileName);

      BufferedInputStream is = getBufferdInputStream(fileName);
      response.setContentType("application/text");

      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();
    } catch (Exception e) {
      LOGGER.error("Processed File Report: " + e);
    }
  }

  @RequestMapping(value = "/contentsharemappingerrors")
  public void viewCSMErrorReport(HttpServletRequest request, HttpServletResponse response,
                                 ModelMap modelMap) {
    try {
      String fileHandle = request.getParameter("pgFileHandle");

      String fileName = String.format("%s_CONTENT_SHARE_MAPPING.pdf", fileHandle);

      BufferedInputStream is = getBufferedInputStreamForErrorReport(fileName, true);
      response.setContentType("application/pdf");
      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();
    } catch (Exception e) {
      LOGGER.error("Error Report: " , e);
      try {
        String fileName = "errorReportFailure.pdf";
        BufferedInputStream is = getBufferedInputStreamForErrorReport(fileName, false);
        response.setContentType("application/pdf");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
      } catch (Exception el) {
        LOGGER.error("Error Report: " , e);
      }
    }
  }

  public void setContentShareMappingService(ContentShareMappingService contentShareMappingService) {
    this.contentShareMappingService = contentShareMappingService;
  }

  private String writeToFile(byte[] source, String destFileName) throws IOException {

    File file = new File( String.format(fileUploadPath+"%s%s", "/", destFileName));
    BufferedOutputStream bufferedOutputStream =
        new BufferedOutputStream(new FileOutputStream(file));
    bufferedOutputStream.write(source);
    bufferedOutputStream.flush();
    bufferedOutputStream.close();
    return file.getAbsolutePath();
  }

  private BufferedInputStream getBufferdInputStream(String fileName) throws Exception {
    BufferedInputStream is = new BufferedInputStream(
        new FileInputStream(new File(processedMappingPath, fileName)));
    return is;
  }

  private BufferedInputStream getBufferedInputStreamForErrorReport(String fileName, boolean flag) throws Exception {
    BufferedInputStream is;
    if (flag) {
      is = new BufferedInputStream(new FileInputStream(new File(((errorReportPath
                                                                      .endsWith("/"))
                                                                 ? errorReportPath : errorReportPath + "/") + fileName)));
    } else {
      is = new BufferedInputStream(new FileInputStream(new File(errorReportPath + fileName)));
    }
    return is;
  }

  private void setMappingStatusAttributes(ModelMap map, String unitId) {
    boolean processingFinished = false;
    String currentStatus = "";
    boolean errorsFound = false;
    String message = "";
    ContentShareMappingStatus mappingStatus =
        contentShareMappingService.getContentShareMappingStatus(unitId);

    if (mappingStatus != null) {
      currentStatus = mappingStatus.getProcessState();
      errorsFound = !mappingStatus.isValidFile();
      map.addAttribute("pgFileHandle", mappingStatus.getProcessId());

      if (currentStatus.equalsIgnoreCase("SUBMITTED")) {
        message = "The content share mapping file has been submitted for processing.";
      } else if (currentStatus.equalsIgnoreCase("VALIDATE_CONTENT_SHARE_MAPPING")) {
        message = "The content share mapping file is being validated...";
      } else if (currentStatus.equalsIgnoreCase("VALIDATED_CONTENT_SHARE_MAPPING")
                 || currentStatus.equalsIgnoreCase("CONTENT_SHARE_MAPPING_SAVING")) {
        message = "The content share mapping are being saved...";
      } else if (currentStatus.equalsIgnoreCase("FAILED")) {
        map.addAttribute("pgErrorReport", true);
        processingFinished = true;
      } else if (currentStatus.equalsIgnoreCase("CONTENT_SHARE_MAPPING_SAVED")) {
        map.addAttribute("pgErrorReportStyle", errorsFound ? "" : "display:none;");
        map.addAttribute("pgErrorReport", errorsFound);
        processingFinished = true;
      }
      if (!message.isEmpty()) {
        map.addAttribute("pgMessage", message);
      }
    } else {
      processingFinished = true;
    }

    map.addAttribute("pgUpdating", !processingFinished);
  }

}