package com.vroozi.customerui.approver.services.rest;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.catalog.services.rest.CatalogRestClientImpl;
import com.vroozi.customerui.util.RestServiceUrl;

/**
 * User: Administrator
 * Date: 8/31/12
 * Time: 3:32 PM
 */

public class ApproverRestClientImpl implements ApproverRestClient {

    private final Logger LOGGER = LoggerFactory.getLogger(CatalogRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;

    @Override
    public List<String> getApproversByCatalogId( String catalogId) throws Exception {
        try {
        	String[] approverArray =  new RestTemplate().getForObject(restServiceUrl.getCatalogServiceBaseURI()+ "/catalogs/"+catalogId+"/approvers", String[].class);
            List<String> approverList = null;
            if(approverArray!=null){
                approverList = Arrays.asList(approverArray);
            }
            return approverList;
        } catch(RestClientException rse) {
            LOGGER.error("Error retrieving approvers!", rse);
            throw rse;
        } catch(Exception exp) {
            LOGGER.error("Error retrieving approvers!", exp);
            throw new Exception(exp);
        }
    }


	@Override
	public int associateApprovers(String catalogId, List<String> approverIds) throws Exception {
		try {
			String approverIdStr = StringUtils.join(approverIds, ',');
			new RestTemplate().put(restServiceUrl.getCatalogServiceBaseURI() +"/catalogs/"+catalogId+ "/approvers/"+approverIdStr, "");
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting approver!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting approver!", exp);
			throw new Exception(exp);
		}
		return 0;
	}

	@Override
	public int disAssociateApprovers(String catalogId, List<String> approverIds) throws Exception {
		try {
			String approverIdStr = StringUtils.join(approverIds, ',');
			new RestTemplate().delete(
					restServiceUrl.getCatalogServiceBaseURI() +"/catalogs/"+catalogId+ "/approvers/"+approverIdStr, "");
		} catch (RestClientException rse) {
			LOGGER.error("Error deleting approver!", rse);
			throw rse;
		} catch (Exception exp) {
			LOGGER.error("Error deleting approver!", exp);
			throw new Exception(exp);
		}
		return 0;
	}
}
