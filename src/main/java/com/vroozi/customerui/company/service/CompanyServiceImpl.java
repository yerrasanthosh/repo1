package com.vroozi.customerui.company.service;

import com.vroozi.customerui.catalog.exception.CatalogServiceException;
import com.vroozi.customerui.company.exception.CompanyServiceException;
import com.vroozi.customerui.company.model.Announcements;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.model.HelpContent;
import com.vroozi.customerui.company.model.Information;
import com.vroozi.customerui.company.model.WelcomeMessage;
import com.vroozi.customerui.company.service.rest.CompanyRestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
    CompanyRestClient companyRestClient;
	
	// Default Constructor
    public CompanyServiceImpl() {
    }
    
    @Override
    public CompanySettings getCompanySettingsByUnitId(String unitId) throws CompanyServiceException {
        try{
            return companyRestClient.getCompanySettings(unitId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public void saveAnnouncement(Announcements announcement) throws CompanyServiceException {
        try{
            companyRestClient.saveAnnouncement(announcement);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void saveMessage(WelcomeMessage message) throws CompanyServiceException {
        try{
            companyRestClient.saveMessage(message);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public List<WelcomeMessage> getMessages(int unitId) {
    	return companyRestClient.getMessages(unitId);
    }

    @Override
    public List<Announcements> getAnnouncements(int unitId) {
        return companyRestClient.getAnnouncements(unitId);
    }

    @Override
    public List<Announcements> getAnnouncementsByUser(int unitId,String userId){
    	List<Announcements> announcements = companyRestClient.getAnnouncementsByUser(unitId,userId);
    	for (Announcements announcement : announcements) {
    		announcement.setUserid(userId);
		}
        return announcements;
    }
//
//    @Override
//    public CompanySettings saveCompanySettings(CompanySettings companySettings) throws Exception {
//        try{
//            return companyRestClient.saveCompanySettings(companySettings);
//        }catch (Exception exp){
//            throw new CatalogServiceException(exp);
//        }
//    }

    @Override
    public void deleteAnnouncement(int id) {
        companyRestClient.deleteAnnouncement(id);
    }

    @Override
    public void hideAnnouncement(int announcementId, String userId) {
    	companyRestClient.hideAnnouncement(announcementId, userId);
    }
    
    @Override
    public void deleteMessage(int id) {
        companyRestClient.deleteMessage(id);
    }

    @Override
    public String[] getAllUsers(int unitId) throws Exception {
        try{
            return companyRestClient.getAllUsers(unitId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void updateAnnouncementStatus( Announcements announcement) {
    	companyRestClient.updateAnnouncementStatus(announcement);
    	
    }
    
    @Override
    public CompanySettings updateCompanyAttributes(String unitId, CompanySettings companySettings) {
        return companyRestClient.updateCompanyAttributes(unitId, companySettings);
    }
    
    @Deprecated
    @Override
    public CompanySettings updateCompanySettings(CompanySettings companySettings) {
        return companyRestClient.updateCompanySettings(companySettings);
    }

    @Override
    public void updateCompanySettings(String unitId, Map<String, Object> map) {
        companyRestClient.updateCompanySettings(unitId, map);
    }
    
    @Override
    public void updateField(String unitId, String fieldName, Object fieldValue) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put(fieldName, fieldValue);
      companyRestClient.updateCompanySettings(unitId, map);
    }
    
    
    @Override
    public void addOrUpdateCompanySettingsHelpTab(String unitId, HelpContent helpContent) {
      companyRestClient.addOrUpdateCompanySettingsHelpTab(unitId, helpContent);
    }
    
    @Override
    public void removeCompanySettingsHelpTab(String unitId, String id) {
      companyRestClient.removeCompanySettingsHelpTab(unitId, id);
    }
    
    @Override
    public CompanySettings updateCompanySettingsWorkFlow(CompanySettings companySettings) {
        return companyRestClient.updateCompanySettingsWorkFlow(companySettings);
    }
    
    @Override
    public List<Information> getInformationsByUnitId(String unitId) {
        return companyRestClient.getInformationsByUnitId(unitId);
    }
    
    @Override
    public void addUpdateInformation(Information information) {
        companyRestClient.addUpdateInformation(information);
    }
    
   @Override
    public void deleteInformationByIds(List<String> ids) {
       companyRestClient.deleteInformationByIds(ids);
    }
}
