package com.vroozi.customerui.company.service;

import com.vroozi.customerui.company.exception.CompanyServiceException;
import com.vroozi.customerui.company.model.Announcements;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.model.HelpContent;
import com.vroozi.customerui.company.model.Information;
import com.vroozi.customerui.company.model.WelcomeMessage;

import java.util.List;
import java.util.Map;

public interface CompanyService {

	public CompanySettings getCompanySettingsByUnitId(String unitId) throws CompanyServiceException;

	void saveAnnouncement(Announcements announcement) throws CompanyServiceException;

	List<Announcements> getAnnouncements(int unitId);
    List<Announcements> getAnnouncementsByUser(int unitId,String userId);

   // public CompanySettings saveCompanySettings (CompanySettings companySettings) throws Exception;

    void deleteAnnouncement(int id);
    
    void hideAnnouncement(int announcementId, String userId);

    String[] getAllUsers (int unitId)throws Exception;

    CompanySettings updateCompanySettings(CompanySettings companySettings);

    void updateCompanySettings(String unitId, Map<String, Object> map);
    
    void updateField(String unitId, String fieldName, Object fieldValue);
    
    void addOrUpdateCompanySettingsHelpTab(String unitId, HelpContent helpContent);
    
    void removeCompanySettingsHelpTab(String unitId, String id);
    
    CompanySettings updateCompanyAttributes(String unitId,
            CompanySettings companySettings);

    CompanySettings updateCompanySettingsWorkFlow(CompanySettings companySettings);

    void saveMessage(WelcomeMessage message) throws CompanyServiceException;

    List<WelcomeMessage> getMessages(int unitId);

    void deleteMessage(int id);
    void updateAnnouncementStatus(Announcements announcement);

    List<Information> getInformationsByUnitId(String unitId);

    void addUpdateInformation(Information information);

    void deleteInformationByIds(List<String> ids);
}
