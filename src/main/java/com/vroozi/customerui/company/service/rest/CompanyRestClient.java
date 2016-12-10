package com.vroozi.customerui.company.service.rest;

import com.vroozi.customerui.company.model.Announcements;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.model.HelpContent;
import com.vroozi.customerui.company.model.Information;
import com.vroozi.customerui.company.model.WelcomeMessage;

import java.util.List;
import java.util.Map;

public interface CompanyRestClient {

	public CompanySettings getCompanySettings(String unitId);

	void saveAnnouncement(Announcements announcement);

	List<Announcements> getAnnouncements(int unitId);

    public List<Announcements> getAnnouncementsByUser(int unitId,String userId) ;
   // public CompanySettings saveCompanySettings(CompanySettings companySettings);

    void deleteAnnouncement(int id);
    
    void hideAnnouncement(int announcementId, String userId);
    
    CompanySettings updateCompanySettings(CompanySettings companySettings);

    void updateCompanySettings(String unitId, Map<String, Object> map);
    
    void addOrUpdateCompanySettingsHelpTab(String unitId, HelpContent helpContent);
    
    void removeCompanySettingsHelpTab(String unitId, String id);
    
    CompanySettings updateCompanyAttributes(String unitId,
            CompanySettings companySettings);
    
    String[] getAllUsers(int unitId);

    CompanySettings updateCompanySettingsWorkFlow(CompanySettings companySettings);

    void saveMessage(WelcomeMessage message);

    List<WelcomeMessage> getMessages(int unitId);

    void deleteMessage(int id);
    public void updateAnnouncementStatus(Announcements announcement) ;

    List<Information> getInformationsByUnitId(String unitId);

    void addUpdateInformation(Information information);

    void deleteInformationByIds(List<String> ids);
}
