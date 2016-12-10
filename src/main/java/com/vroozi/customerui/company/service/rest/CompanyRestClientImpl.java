package com.vroozi.customerui.company.service.rest;

import com.vroozi.customerui.catalog.services.rest.CatalogRestClientImpl;
import com.vroozi.customerui.company.model.Announcements;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.model.HelpContent;
import com.vroozi.customerui.company.model.Information;
import com.vroozi.customerui.company.model.WelcomeMessage;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.util.RestServiceUrl;

import freemarker.template.utility.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CompanyRestClientImpl implements CompanyRestClient{

	private final Logger logger = LoggerFactory.getLogger(CatalogRestClientImpl.class);
	
	@Autowired
    RestServiceUrl restServiceUrl;
	
	@Override
    public CompanySettings getCompanySettings(String unitId) {
		CompanySettings companySettings;
        try {
        	companySettings =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/companysettings/unitid/{unitId}", CompanySettings.class, unitId);

        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }

        return companySettings;
    }
	
	@Override
    public void saveAnnouncement(Announcements announcement) {
        try {
        	new RestTemplate().postForObject(restServiceUrl.userDataServiceURI() + "/announcements", announcement, Announcements.class);
        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void saveMessage(WelcomeMessage message) {
        try {
            new RestTemplate().postForObject(restServiceUrl.userDataServiceURI() + "/message", message, WelcomeMessage.class);
        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }
    }
	
	@Override
    public List<Announcements> getAnnouncements(int unitId) {
		List<Announcements> announcements;
        try {
        	Announcements[] tempAnnouncements =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/announcements/unitid/{unitid}", Announcements[].class, unitId);
        	announcements = new ArrayList<Announcements>(Arrays.asList(tempAnnouncements));
        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }
        return announcements;
    }

    @Override
    public List<WelcomeMessage> getMessages(int unitId) {
        List<WelcomeMessage> messages;
        try {
            WelcomeMessage[] tempMessages =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/messages/unitid/{unitid}", WelcomeMessage[].class, unitId);
            messages = new ArrayList<WelcomeMessage>(Arrays.asList(tempMessages));
        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }
        return messages;
    }

    @Override
    public List<Announcements> getAnnouncementsByUser(int unitId, String userId) {
        try{
            Announcements[] announcements = new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/announcements/unitid/{unitid}?userid="+userId, Announcements[].class, unitId);
            return Arrays.asList(announcements);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void deleteAnnouncement(int id) {
        try {
            new RestTemplate().delete(restServiceUrl.userDataServiceURI() + "/deleteannouncements/id/{id}", id); ;
        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void hideAnnouncement(int announcementId, String userId) {
        try {
            new RestTemplate().delete(restServiceUrl.userDataServiceURI() + "/hideannouncements/aid/{aid}/userid/{userid}", announcementId, userId);
        } catch(RestClientException rse) {
            logger.error("Error hiding announcement for user!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error hiding announcement for user!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void deleteMessage(int id) {
        try {
            new RestTemplate().delete(restServiceUrl.userDataServiceURI() + "/deletemessage/id/{id}", id); ;
        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public String[] getAllUsers(int unitId) {
        String[] tempAnnouncements;
        try {
            tempAnnouncements =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/usersArray/unitId/{unitId}", String[].class, unitId);
        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }
        return tempAnnouncements;
    }

    @Override
    public CompanySettings updateCompanyAttributes(String unitId, CompanySettings companySettings) {
        try {
            new RestTemplate().put(restServiceUrl.userDataServiceURI() + "/companyattributes/unitid/{unitid}", companySettings, unitId);

        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }

        return companySettings;
    }

    /**
     * Use instead updateCompanySettings(String unitId, Map<String, Object> map).
     * Updating full object is a risk for other applications.
     */
    @Deprecated
    @Override
    public CompanySettings updateCompanySettings(CompanySettings companySettings) {
        try {
            companySettings =  new RestTemplate().postForObject(restServiceUrl.userDataServiceURI() + "/companysettings/update", companySettings, CompanySettings.class);

        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }

        return companySettings;
    }

    @Override
    public void updateCompanySettings(String unitId, Map<String, Object> map) {
        try {
      new RestTemplate().put(restServiceUrl.userDataServiceURI() + "/companysettings/" + unitId,
          map, CompanySettings.class);

        } catch(RestClientException rse) {
            logger.error("Error updating company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error updating company!", exp);
            throw new AdminUIException(exp);
        }
    }
    
  @Override
  public void addOrUpdateCompanySettingsHelpTab(String unitId, HelpContent helpContent) {
    try {
      new RestTemplate().put(
          restServiceUrl.userDataServiceURI() + "/companysettings/" + unitId + "/tabs", helpContent);

    } catch (RestClientException rse) {
      logger.error("Error updating company tabs!", rse);
      throw rse;
    } catch (Exception exp) {
      logger.error("Error updating company tabs!", exp);
      throw new AdminUIException(exp);
    }
  }
  
  @Override
  public void removeCompanySettingsHelpTab(String unitId, String id) {
    try {
      new RestTemplate().delete(
          restServiceUrl.userDataServiceURI() + "/companysettings/" + unitId + "/tabs/"+id);
    } catch (RestClientException rse) {
      logger.error("Error removing company tab!", rse);
      throw rse;
    } catch (Exception exp) {
      logger.error("Error removing company tab!", exp);
      throw new AdminUIException(exp);
    }
  }
    
    @Override
    public CompanySettings updateCompanySettingsWorkFlow(CompanySettings companySettings) {
        try {
            companySettings =  new RestTemplate().postForObject(restServiceUrl.userDataServiceURI() + "/companysettings/updateworkflow", companySettings, CompanySettings.class);

        } catch(RestClientException rse) {
            logger.error("Error retrieving company!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving company!", exp);
            throw new AdminUIException(exp);
        }

        return companySettings;
    }

    @Override
    public void updateAnnouncementStatus(Announcements announcement) {
    	try{
    		new RestTemplate().put(restServiceUrl.userDataServiceURI()+"/announcements", announcement,Announcements.class);

    	}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    	
    }
    
    @Override
    public List<Information> getInformationsByUnitId(String unitId) {
        List<Information> informations;
        try {
            Information[] tempInformation =  new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/information/unitid/{unitid}", Information[].class, unitId);
            informations = new ArrayList<Information>(Arrays.asList(tempInformation));
        } catch(RestClientException rse) {
            logger.error("Error in retrieving information!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error in retrieving information!", exp);
            throw new AdminUIException(exp);
        }
        return informations;
    }
    
    @Override
    public void addUpdateInformation(Information information) {
        try {
            new RestTemplate().postForObject(restServiceUrl.userDataServiceURI() + "/information", information, Information.class);
        } catch(RestClientException rse) {
            logger.error("Error in add/update information!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error in add/update information!", exp);
            throw new AdminUIException(exp);
        }
    }
   @Override
    public void deleteInformationByIds(List<String> ids) {
        try {
            String infoIds = StringUtils.join(ids, ",");
            new RestTemplate().delete(restServiceUrl.userDataServiceURI() + "/information/{ids}", infoIds); ;
        } catch(RestClientException rse) {
            logger.error("Error in deleting information!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error in deleting information!", exp);
            throw new AdminUIException(exp);
        }
    }

}
