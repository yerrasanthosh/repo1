package com.vroozi.customerui.company.controller;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.model.Announcements;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.model.CompanySettingsVO;
import com.vroozi.customerui.company.model.Information;
import com.vroozi.customerui.company.model.SystemDefinition;
import com.vroozi.customerui.company.model.WelcomeMessage;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.company.service.SystemDefinitionService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.FileUtility;
import com.vroozi.customerui.util.Utils;
import static com.vroozi.customerui.util.Consts.CUSTOM_HELP_CONTENT_SECTION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import static com.vroozi.customerui.util.Consts.ANNOUNCEMENTS_SECTION_PAGE;
import static com.vroozi.customerui.util.Consts.COMPANY_DEFAULT_SECTION_PAGE;
import static com.vroozi.customerui.util.Consts.CONTACT_SECTION_PAGE;
import static com.vroozi.customerui.util.Consts.CONTENTSETTINGS_PAGE;
import static com.vroozi.customerui.util.Consts.FAILURE;
import static com.vroozi.customerui.util.Consts.FAQ_SECTION_PAGE;
import static com.vroozi.customerui.util.Consts.HELP_PAGE;
import static com.vroozi.customerui.util.Consts.INFORMATION_SECTION_PAGE;
import static com.vroozi.customerui.util.Consts.MESSAGES_SECTION_PAGE;
import static com.vroozi.customerui.util.Consts.POLICY_SECTION_PAGE;
import static com.vroozi.customerui.util.Consts.SUCCESS;
import static com.vroozi.customerui.util.Consts.WORK_FLOW_SECTION_PAGE;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.model.Announcements;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.model.CompanySettingsVO;
import com.vroozi.customerui.company.model.HelpContent;
import com.vroozi.customerui.company.model.Information;
import com.vroozi.customerui.company.model.SystemDefinition;
import com.vroozi.customerui.company.model.WelcomeMessage;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.company.service.SystemDefinitionService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.FileUtility;
import com.vroozi.customerui.util.SessionUtil;
import com.vroozi.customerui.util.Utils;

/**
 * User: Muhammad Salman Nafees
 * Date: 10/10/12
 * Time: 20:08
 */
@Controller
public class CompanyUIController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(CompanyUIController.class);

    public static final String  FORMAT_DATE_DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy  hh:mm a z";
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

    @Autowired
    AppConfig appConfig;
    
    @Autowired
    CompanyService companyService;

    @Autowired
    ProfileGroupService profileGroupService;
    
    @Autowired
    UserManagementService userManagementService;

    @Autowired
    SystemDefinitionService systemDefService;

    @Autowired
    UploadService uploadService;

    @Value("${fileStorageServiceURI}")
    private String fileStorageServiceURI;


    @RequestMapping(value="/announcements", method=RequestMethod.GET)
    public @ResponseBody
    List<Announcements> getAnnouncmentsByUser(HttpServletRequest request, HttpServletResponse response){

        User user = SessionDataRetriever.getLoggedInUser(request);
        return companyService.getAnnouncementsByUser(Integer.parseInt(user.getUnitId()),user.getUserId());
    }


    @RequestMapping("/company")
    public String companySettings(HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        try {
        	CompanySettings cs = companyService.getCompanySettingsByUnitId(user.getUnitId());
        	modelMap.addAttribute("companySettings",cs);
        	
            List<WelcomeMessage> messageList = companyService.getMessages(Integer.parseInt(user.getUnitId()));
            modelMap.addAttribute("messages",messageList);
            modelMap.addAttribute("messageSize",messageList.size());
            
            List<SystemDefinition> systemDefinitions = systemDefService.getSystemDefinitions(cs.getUnitId());
            modelMap.addAttribute("systemDefinitions", systemDefinitions);

            setAnnouncementsModel(modelMap, user.getUnitId());
            setInformationsModel(modelMap, user.getUnitId());


            return CONTENTSETTINGS_PAGE;
        } catch (Exception e) {
            LOGGER.error("Company Settings:",e);
            return FAILURE;
        }
    }

    @RequestMapping("/help")
    public String helpPage(HttpServletRequest request,
                                  HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        try {
            CompanySettings cs = companyService.getCompanySettingsByUnitId(user.getUnitId());
            modelMap.addAttribute("companySettings", cs);

            return HELP_PAGE;
        } catch (Exception e) {
            LOGGER.error("Company Settings:",e);
            return FAILURE;
        }
    }

    @RequestMapping(value = "/upload-welcome-image", method= RequestMethod.POST, produces="text/html")
    public  @ResponseBody String addWelcomeImage(HttpServletRequest request, @ModelAttribute FileUtility companySettingsUtility, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
//        companySettings.setWelcomeImage(companySettingsVO.getWelcomeImage());
//        if("false".equalsIgnoreCase(companySettingsVO.getRemoveCompanyIcon())){
        if(companySettingsUtility.getWelcomeImage() != null && !companySettingsUtility.getWelcomeImage().isEmpty()){
            try{
                BufferedImage image = ImageIO.read(companySettingsUtility.getWelcomeImage().getInputStream());
                int height = image.getHeight();
                int width = image.getWidth();
                if(companySettingsUtility.getWelcomeImage().getBytes().length > Integer.parseInt(appConfig.welcomeImageUploadSize)){
                    return "ERROR:Welcome Image file size should not exceed "+String.valueOf(Integer.parseInt(appConfig.welcomeImageUploadSize)/1024)+"kb.";
                }
                if(height > 200 || width > 505){
                    return "ERROR:Welcome Image file dimensions have been exceeded, please resize accordingly(505 x 200).";
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        if(companySettingsUtility.getWelcomeImage() != null && !companySettingsUtility.getWelcomeImage().isEmpty()) {
            String ext = companySettingsUtility.getWelcomeImage().getOriginalFilename();
            if(writeToFile(companySettingsUtility.getWelcomeImage().getBytes(), companySettings.getId() + "_welcome_" + ext, appConfig.flagIconUploadPath)){
                companySettings.setWelcomeImage(companySettings.getId() + "_welcome_" + ext.trim());
            }
        }

        try {
            companyService.updateField(companySettings.getUnitId(), 
                "welcomeImage", companySettings.getWelcomeImage());
            modelMap.addAttribute("companySettings",companySettings);
            HttpSession session = request.getSession(false);

            session.setAttribute("companySettings", companySettings);
        } catch (Exception e) {
            LOGGER.error("error saving company Settings",e);
        }
        return companySettings.getWelcomeImage();
    }

    @RequestMapping(value = "/removeWelcomeImage", method= RequestMethod.POST, produces="text/html")
    public  @ResponseBody String removeWelcomeImage(HttpServletRequest request, @ModelAttribute CompanySettingsVO companySettingsVO,@ModelAttribute FileUtility companySettingsUtility, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        try {
            companyService.updateField(user.getUnitId(), "welcomeImage", "");
        } catch (Exception e) {
            LOGGER.error("error saving company Settings",e);
        }
        return null;
    }

    @RequestMapping(value = "/addCompanySetting", method= RequestMethod.POST, produces="text/html")
    public  @ResponseBody String updateCompanySettings(HttpServletRequest request, @ModelAttribute CompanySettingsVO companySettingsVO,@ModelAttribute FileUtility companySettingsUtility, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        String companyIcon="";

        CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
        companySettings.setCategoryMapping(companySettingsVO.isCategoryMapping());
        companySettings.setUnitOfMeasure(companySettingsVO.isUnitOfMeasure());
        companySettings.setCurrency(companySettingsVO.isCurrencyMapping());
        companySettings.setNotInCatalog(companySettingsVO.isNotInCatalog());
        companySettings.setDownloadAllCatalogs(companySettingsVO.isDownloadAllCatalogs());
        companySettings.setTransferCustFieldDescription(companySettingsVO.isTransferCustFieldDescription());
        companySettings.setCompanyIcon(companySettingsVO.getCompanyIcon());
        companySettings.setNoCompanyIcon(companySettingsVO.getNoCompanyIcon());
        companySettings.setFindVendorIcon(companySettingsVO.getFindVendorIcon());
        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("categoryMapping", companySettingsVO.isCategoryMapping());
        updateMap.put("unitOfMeasure", companySettingsVO.isUnitOfMeasure());
        updateMap.put("currency", companySettingsVO.isCurrencyMapping());
        updateMap.put("notInCatalog", companySettingsVO.isNotInCatalog());
        updateMap.put("downloadAllCatalogs", companySettingsVO.isDownloadAllCatalogs());
        updateMap.put("limitRequest", companySettingsVO.isLimitRequest());
        updateMap.put("transferCustFieldDescription", companySettingsVO.isTransferCustFieldDescription());
        updateMap.put("companyIcon", companySettingsVO.getCompanyIcon());
        updateMap.put("noCompanyIcon", companySettingsVO.getNoCompanyIcon());
        updateMap.put("findVendorIcon", companySettingsVO.getFindVendorIcon());
        Map<String, String> notInCatalogFieldMapping = new HashMap<String, String>();
        notInCatalogFieldMapping.put("freeTextIdentifier", companySettingsVO.getFreeTextIdentifier());
        notInCatalogFieldMapping.put("limitItemIdentifier", companySettingsVO.getLimitItemIdentifier());
        notInCatalogFieldMapping.put("requiredDate", companySettingsVO.getRequiredDate());
        notInCatalogFieldMapping.put("deliveryDateFrom", companySettingsVO.getDeliveryDateFrom());
        notInCatalogFieldMapping.put("overallValue", companySettingsVO.getOverallValue());
        updateMap.put("notInCatalogFieldMapping", notInCatalogFieldMapping);
        updateMap.put("findVendorIcon", companySettingsVO.getFindVendorIcon());
        updateMap.put("sharedListMapping", companySettingsVO.getSharedListMapping());
        if (companySettingsVO.isSharedListAutoExpire()) {
          updateMap.put("sharedListExpiryDays", companySettingsVO.getSharedListExpiryDays());
        }
        updateMap.put("sharedListAutoExpire", companySettingsVO.isSharedListAutoExpire());
        
            if(companySettingsUtility.getCompanyImage() != null && !companySettingsUtility.getCompanyImage().isEmpty()){
                try{
                    BufferedImage image = ImageIO.read(companySettingsUtility.getCompanyImage().getInputStream());
                    int height = image.getHeight();
                    int width = image.getWidth();

                    if(companySettingsUtility.getCompanyImage().getBytes().length > 153600){
                        return "ERROR:Company Logo file size should not exceed 150kb.";
                    }

                    if(height > 60 || width > 200){
                        return "ERROR:Company Logo file dimensions have been exceeded, please resize accordingly(200 x 60).";
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            if(companySettingsUtility.getNoCompanyImage() != null && !companySettingsUtility.getNoCompanyImage().isEmpty()){
                try{
                    BufferedImage image = ImageIO.read(companySettingsUtility.getNoCompanyImage().getInputStream());

                    if(companySettingsUtility.getNoCompanyImage().getBytes().length > 153600){
                        return "ERROR:No Image Logo file size should not exceed 150kb.";
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        if(companySettingsUtility.getFindVendorImage() != null && !companySettingsUtility.getFindVendorImage().isEmpty()){
            try{
                BufferedImage image = ImageIO.read(companySettingsUtility.getFindVendorImage().getInputStream());

                if(companySettingsUtility.getFindVendorImage().getBytes().length > 153600){
                    return "ERROR:No Image Logo file size should not exceed 150kb.";
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
            if(companySettingsUtility.getCompanyImage() != null && !companySettingsUtility.getCompanyImage().isEmpty()) {
                String ext = companySettingsUtility.getCompanyImage().getOriginalFilename();
                ext = ext.replaceAll(" ","").toLowerCase();
                companyIcon= companySettings.getCompanyIcon();
                if (writeToFile(companySettingsUtility.getCompanyImage().getBytes(), companySettings.getId() + "_compImage_" + ext, appConfig.flagIconUploadPath)) {
                  String fileId = uploadFileToS3(companySettings, ext, updateMap);
                  if (StringUtils.isNotBlank(fileId)) {
                    companyIcon = String.format("%s/%s/%s", fileStorageServiceURI, "assets", fileId);
                  }
                }
            }
            if(companySettingsUtility.getNoCompanyImage() != null && !companySettingsUtility.getNoCompanyImage().isEmpty()) {
                String ext = companySettingsUtility.getNoCompanyImage().getOriginalFilename();
                ext = ext.replaceAll(" ","").toLowerCase();
                if(writeToFile(companySettingsUtility.getNoCompanyImage().getBytes(), companySettings.getId() +"_noImage_"+ ext, appConfig.flagIconUploadPath)){
                    try {
                        Utils.generateImage(appConfig.flagIconUploadPath+File.separator+companySettings.getId() +"_noImage_"+ ext, companySettings.getId() +"_noImage_"+ ext, appConfig.flagIconUploadPath, Double.parseDouble(appConfig.noImageWidth), Double.parseDouble(appConfig.noImageHeight));
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    updateMap.put("noCompanyIcon", companySettings.getId()+"_noImage_"+ext.trim());
                }
            }


        if(companySettingsUtility.getFindVendorImage() != null && !companySettingsUtility.getFindVendorImage().isEmpty()) {
            String ext = companySettingsUtility.getFindVendorImage().getOriginalFilename();
            ext = ext.replaceAll(" ","").toLowerCase();
            if(writeToFile(companySettingsUtility.getFindVendorImage().getBytes(), companySettings.getId() +"_findVendor_"+ ext, appConfig.flagIconUploadPath)){
                try {
                    Utils.generateImage(appConfig.flagIconUploadPath+File.separator+companySettings.getId() +"_findVendor_"+ ext, companySettings.getId() +"_findVendor_"+ ext, appConfig.flagIconUploadPath, Double.parseDouble(appConfig.noImageWidth), Double.parseDouble(appConfig.noImageHeight));
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                updateMap.put("findVendorIcon", companySettings.getId()+"_findVendor_"+ext.trim());
            }
        }

        try {
            companyService.updateCompanySettings(user.getUnitId(), updateMap);
            companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
            modelMap.addAttribute("companySettings",companySettings);
            HttpSession session = request.getSession(false);

            session.setAttribute("companySettings",companySettings);
            SessionUtil.updateS3FilePathInSession(session, companySettings,
                                                  fileStorageServiceURI, true);

        } catch (Exception e) {
            LOGGER.error("error saving company Settings",e);
        }
        return String.format("%s::%s::%s",companyIcon, companySettings.getNoCompanyIcon(),
                             companySettings.getFindVendorIcon());
    }

    @RequestMapping(value = "/getCompanySetting", method= RequestMethod.POST)
    public  String getCompanySettings(HttpServletRequest request, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
        modelMap.addAttribute("companySettings",companySettings);
        HttpSession session = request.getSession(false);
//        CompanyData cd = (CompanyData) session.getAttribute("companyData");
//        cd.setCompanyLogo(companySettings.getCompanyIcon());
        session.setAttribute("companySettings",companySettings);

        return COMPANY_DEFAULT_SECTION_PAGE;
    }
    
    @RequestMapping("/addAnnouncement")
    public String addAnnouncement(HttpServletRequest request,
                           HttpServletResponse response, @ModelAttribute CompanySettingsVO companySettingsVO, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        try {
          Announcements announcement = new Announcements();
            if(companySettingsVO.getMailingList() != null){
                announcement.setMailingList(Arrays.asList(companySettingsVO.getMailingList().split(",")));
            }
        	announcement.setId(companySettingsVO.getAnnounceId());
            if(companySettingsVO.getAnnounceId() == 0){
                announcement.setUpdated(false);
            } else {
                announcement.setUpdated(true);
            }
            announcement.setDate(new Date());
        	announcement.setAnnouncements(companySettingsVO.getAnnouncement());
        	announcement.setUnitId(Integer.parseInt(user.getUnitId()));
            announcement.setType(companySettingsVO.getType());
        	companyService.saveAnnouncement(announcement);
        	setAnnouncementsModel(modelMap, user.getUnitId());
        	
            return ANNOUNCEMENTS_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Announcements:",e);
            return FAILURE;
        }
    }

    @RequestMapping("/deleteAnnouncement")
    public String deleteAnnouncement(HttpServletRequest request,
                                     HttpServletResponse response, @RequestParam(value = "idString") String idString, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
            companyService.deleteAnnouncement(Integer.parseInt(idString));
            setAnnouncementsModel(modelMap, user.getUnitId());
            
            return ANNOUNCEMENTS_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Delete announcement",e);
            return FAILURE;
        }
    }
    @RequestMapping("/hide-announcment")
    public String hideAnnouncement(HttpServletRequest request,
                                     HttpServletResponse response, @RequestParam(value = "aid") Integer announcementId, @RequestParam(value = "userid") String userId, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
            companyService.hideAnnouncement(announcementId, userId);
            List<Announcements> announcements = companyService.getAnnouncementsByUser(Integer.parseInt(user.getUnitId()),user.getUserId());
            modelMap.addAttribute("announcements", announcements);
            return ANNOUNCEMENTS_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Hide Announcement",e);
            return FAILURE;
        }
    }
    
    @RequestMapping("/addMessage")
    public String addMessage(HttpServletRequest request,
                                  HttpServletResponse response, @ModelAttribute CompanySettingsVO companySettingsVO, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        try {
            WelcomeMessage message = new WelcomeMessage();
            if(companySettingsVO.getMailingList() != null){
                message.setMailingList(Arrays.asList(companySettingsVO.getMailingList().split(",")));
            }

            message.setId(companySettingsVO.getMessageId());
            message.setMessage(companySettingsVO.getMessage());
            message.setUnitId(Integer.parseInt(user.getUnitId()));

            companyService.saveMessage(message);
            modelMap.addAttribute("messages",companyService.getMessages(Integer.parseInt(user.getUnitId())));
            return MESSAGES_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Add message",e);
            return FAILURE;
        }
    }

    @RequestMapping("/deleteMessage")
    public String deleteMessage(HttpServletRequest request,
                                     HttpServletResponse response, @RequestParam(value = "idString") String idString, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
            companyService.deleteMessage(Integer.parseInt(idString));
            modelMap.addAttribute("messages",companyService.getMessages(Integer.parseInt(user.getUnitId())));
            return MESSAGES_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Delete message:",e);
            return FAILURE;
        }
    }
    
    @RequestMapping(value = "/company-settings" , produces = "application/json" , method = RequestMethod.PUT )
    public @ResponseBody CompanySettings updateCompanyAttributes(HttpServletRequest request,
            HttpServletResponse response, @RequestBody CompanySettings companySettingsVO) {
        
        User user = SessionDataRetriever.getLoggedInUser(request);

        CompanySettings companySettings = companyService.updateCompanyAttributes(user.getUnitId(), companySettingsVO);
        
        return companySettings;
    }
    
    
    @RequestMapping(value = "/welcomeSettings" , produces = "application/json" , method = RequestMethod.PUT )
    public @ResponseBody CompanySettings welcomeSettings(HttpServletRequest request,
            HttpServletResponse response, @RequestBody CompanySettings companySettingsVO) {
        
        User user = SessionDataRetriever.getLoggedInUser(request);
        CompanySettings companySetting = companyService.getCompanySettingsByUnitId(user.getUnitId());
        companySetting.setWelcomeWidgetText(companySettingsVO.isWelcomeWidgetText());
        companyService.updateField(user.getUnitId(), "welcomeWidgetText", companySettingsVO.isWelcomeWidgetText());
        return companySetting;
    }
    
    @RequestMapping("/updateCompanySettings")
    public String updateCompanySettings(HttpServletRequest request,
                                     HttpServletResponse response, @ModelAttribute CompanySettingsVO companySettingsVO, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
        //companySettings.setColName(companySettingsVO.getColName());
        try {
            if("faq".equalsIgnoreCase(companySettingsVO.getColName())){
              companySettings.setFaq(companySettingsVO.getFaq());
              companyService.updateField(user.getUnitId(), "faq", companySettingsVO.getFaq());
            } else if("policy".equalsIgnoreCase(companySettingsVO.getColName())){
              companySettings.setPolicy(companySettingsVO.getPolicies());
              companyService.updateField(user.getUnitId(), "policy", companySettingsVO.getPolicies());
            } else if("contactUs".equalsIgnoreCase(companySettingsVO.getColName())){
              companySettings.setContactUs(companySettingsVO.getContactUs());
              companyService.updateField(user.getUnitId(), "contactUs", companySettingsVO.getContactUs());
            }

            modelMap.addAttribute("companySettings",companySettings);
            if("faq".equalsIgnoreCase(companySettingsVO.getColName())){
                return FAQ_SECTION_PAGE;
            } else if("policy".equalsIgnoreCase(companySettingsVO.getColName())){
                return POLICY_SECTION_PAGE;
            } else if("contactUs".equalsIgnoreCase(companySettingsVO.getColName())){
                return CONTACT_SECTION_PAGE;
            }
            return FAILURE;
        } catch (Exception e) {
            LOGGER.error("Update company settings:",e);
            return FAILURE;
        }
    }
    
  @RequestMapping(value = "/companysettings/tabs", consumes = "application/json",
      produces = "application/json", method = RequestMethod.PUT)
  public String addOrUpdateCompanySettingsHelpTab(HttpServletRequest request,
      @RequestBody HelpContent helpContent, ModelMap modelMap) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      companyService.addOrUpdateCompanySettingsHelpTab(user.getUnitId(), helpContent);
      CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
      modelMap.addAttribute("companySettings", companySettings);
      return CUSTOM_HELP_CONTENT_SECTION;
    } catch (Exception e) {
      LOGGER.error("Update company settings:", e);
      return FAILURE;
    }
  }

  @RequestMapping(value = "/companysettings/tabs/{id}", method = RequestMethod.DELETE)
  public String removeCompanySettingsHelpTab(HttpServletRequest request,
      @PathVariable String id, ModelMap modelMap) {
    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      companyService.removeCompanySettingsHelpTab(user.getUnitId(), id);
      CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
      modelMap.addAttribute("companySettings", companySettings);
      return CUSTOM_HELP_CONTENT_SECTION;
    } catch (Exception e) {
      LOGGER.error("Update company settings:", e);
      return FAILURE;
    }
  }
    @RequestMapping("/updateCompanyWorkFlow")
    public String updateCompanyWorkFlow(HttpServletRequest request,
                                        HttpServletResponse response, @ModelAttribute CompanySettingsVO companySettingsVO, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());

        companySettings.setRequiredField(companySettingsVO.isRequiredField());
        companySettings.setItemAdded(companySettingsVO.isItemAdded());
        companySettings.setPriceChange(companySettingsVO.isPriceChange());
        companySettings.setPercentChanged(companySettingsVO.getPercentChanged());

        try {
            companySettings = companyService.updateCompanySettingsWorkFlow(companySettings);
            modelMap.addAttribute("companySettings",companySettings);

            return WORK_FLOW_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Update company workflow:",e);
            return FAILURE;
        }
    }
    
    @RequestMapping(value="/information", method= RequestMethod.GET)
    public String deleteInformation(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        
        try {
            setInformationsModel(modelMap, user.getUnitId());
            return INFORMATION_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Retrieve information failed.",e);
            return FAILURE;
        }
    }

    @RequestMapping(value="/information", method= RequestMethod.POST)
    public String addUpdateAnnouncement(HttpServletRequest request,
                           HttpServletResponse response, @ModelAttribute Information information, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        try {
            information.setUnitId(user.getUnitId());
            if(information.getId()==null || information.getId().length()==0) {
                information.setDate(new Date());
            }
            companyService.addUpdateInformation(information);
            setInformationsModel(modelMap, user.getUnitId());
            return INFORMATION_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error in add/update announcements.",e);
            return FAILURE;
        }
    }

    @RequestMapping(value="/information/{ids}", method= RequestMethod.DELETE)
    public String deleteInformation(HttpServletRequest request,
                                     HttpServletResponse response, @PathVariable(value = "ids") String ids, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);
        
        try {
            StringTokenizer tokens = new StringTokenizer(ids,",");
            List<String> infoIds= new LinkedList<String>();

            while (tokens.hasMoreTokens()){
                String token = tokens.nextToken();
                if(token!=null && !"".equalsIgnoreCase(token.trim())){
                    infoIds.add(token);
                }
            }
            
            companyService.deleteInformationByIds(infoIds);
            setInformationsModel(modelMap, user.getUnitId());
            return INFORMATION_SECTION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Delete announcement",e);
            return FAILURE;
        }
    }
    
  /**

   * 
   * @return
   */
  @RequestMapping(value = "/update-shared-list-mapping", method = RequestMethod.PUT)
  @ResponseBody
  public String updateSharedListMapping(HttpServletRequest request,
      @RequestParam(value = "sharedListMapping") String sharedListMapping) {

    try {
      User user = SessionDataRetriever.getLoggedInUser(request);
      Map<String, Object> updateMap = new HashMap<String, Object>();
      updateMap.put("sharedListMapping", sharedListMapping);

      companyService.updateCompanySettings(user.getUnitId(), updateMap);
    } catch (Exception e) {
      LOGGER.error("error saving company Settings", e);
      return FAILURE;
    }
    return SUCCESS;
  }

    private void setAnnouncementsModel(ModelMap modelMap, String unitId) {
        List<Announcements> announcements = companyService.getAnnouncements(Integer.parseInt(unitId));
        modelMap.addAttribute("announcements", announcements);
        
        List<ProfileGroup> profileGroups = new ArrayList<ProfileGroup>();
        try {
            profileGroups = profileGroupService.getActiveGroupsByUnitId(unitId);
        } catch (Exception e) {
            LOGGER.error("Exception in retrieving content view groups.");
        }
        
        Map<String, String> shoppersMap = new HashMap<String, String>();
        for (ProfileGroup profileGroup : profileGroups) {
            shoppersMap.put(profileGroup.getGroupId()+"", profileGroup.getGroupName());
        }
        modelMap.addAttribute("shoppers", shoppersMap);

        for (Announcements announcement : announcements) {
            List<String> newMailingList = new ArrayList<String>(announcement.getMailingList().size());
            for (String id : announcement.getMailingList()) {
                if(shoppersMap.get(id)!=null) {
                    newMailingList.add(id);
                }
            }
            announcement.setMailingList(newMailingList);
        }
    }
    
    private void setInformationsModel(ModelMap modelMap, String unitId) {
        List<Information> companyInfos = companyService.getInformationsByUnitId(unitId);
        Set<String> assignedCGroups = new HashSet<String>(); 
        for (Information info : companyInfos) {
            assignedCGroups.addAll(info.getContentViewGroups());
        }
        modelMap.addAttribute("informations", companyInfos);
        modelMap.addAttribute("infoAssignedGroups", assignedCGroups);
        
        List<ProfileGroup> profileGroups = new ArrayList<ProfileGroup>();
        try {
            profileGroups = profileGroupService.getActiveGroupsByUnitId(unitId);
        } catch (Exception e) {
            LOGGER.error("Exception in retrieving content view groups.");
        }

        Map<String, String> cGroupMap = new HashMap<String, String>();
        for (ProfileGroup profileGroup : profileGroups) {
            cGroupMap.put(profileGroup.getToken(), profileGroup.getGroupName());
        }
        modelMap.addAttribute("cvgroups", cGroupMap);
        
        for (Information info : companyInfos) {
            List<String> newCGroupsList = new ArrayList<String>(info.getContentViewGroups().size());
            for (String id : info.getContentViewGroups()) {
                if(cGroupMap.get(id)!=null) {
                    newCGroupsList.add(id);
                }
            }
            info.setContentViewGroups(newCGroupsList);
        }

    }

    public boolean writeToFile(byte[] source, String destFileName, String uploadPath){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(uploadPath + "/" + destFileName));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(source);bufferedOutputStream.flush();bufferedOutputStream.close();
        } catch (IOException exception) {
            LOGGER.error("Error in writing to file(" + destFileName + ")", exception);
            return false;
        }
        return true;
    }

  private String uploadFileToS3(CompanySettings companySettings, String ext, Map<String, Object>
      updateMap) {
    String fileId = null;
    if (companySettings != null && StringUtils.isNotBlank(ext) && updateMap != null) {
      try {
        fileId = uploadService.uploadFileToS3(String.format("%s/%s%s%s", appConfig
                                                                .flagIconUploadPath,
                                                            companySettings
                                                                .getId(),
                                                            "_compImage_", ext.trim()));
      } catch (Exception e) {
        LOGGER.error("Error in writing to S3", e);
      }
      if (StringUtils.isBlank(fileId)) {
        updateMap.put("companyIcon", companySettings.getId() + "_compImage_" + ext
            .trim());
      } else {
        updateMap.put("companyIcon", fileId);
      }
    }

    return fileId;
  }


}
