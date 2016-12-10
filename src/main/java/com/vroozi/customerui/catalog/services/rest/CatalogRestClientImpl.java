package com.vroozi.customerui.catalog.services.rest;

import com.vroozi.customerui.catalog.model.CatalogCustomField;
import com.vroozi.customerui.catalog.model.CatalogItemCollection;
import com.vroozi.customerui.catalog.model.CatalogItemPost;
import com.vroozi.customerui.catalog.model.CatalogPost;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.model.CreateCatalogPost;
import com.vroozi.customerui.catalog.model.CustomField;
import com.vroozi.customerui.catalog.model.EditCatalogPost;
import com.vroozi.customerui.catalog.model.FieldFilter;
import com.vroozi.customerui.catalog.model.GroupedCatalogCountDTO;
import com.vroozi.customerui.customfield.model.CustomFieldTypeMetrics;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.util.RestServiceUrl;
import com.vroozi.customerui.util.RestfulResponseErrorHandler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CatalogRestClientImpl implements CatalogRestClient {
	private final Logger logger = LoggerFactory.getLogger(CatalogRestClientImpl.class);

    @Autowired
    RestServiceUrl restServiceUrl;

    // Default Constructor
	public CatalogRestClientImpl() {
	}

	@Override
	public int updateItemStatus(CatalogItemPost itemStatusPost)throws Exception {
		new RestTemplate().put(restServiceUrl.getUpdateItemStatusURI(), itemStatusPost);
		return 0;
	}

	@Override
    public List<CatalogSummary> getAllCatalogs(String unitId) {
        List<CatalogSummary> catalogs = new ArrayList<CatalogSummary>();

        if(StringUtils.isEmpty(unitId)) throw new IllegalArgumentException("catalog 'unitId' can not be null or blank");

        try {
            CatalogSummary[] catalogSummaries =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitid/{unitId}", CatalogSummary[].class, unitId);
            catalogs = new ArrayList<CatalogSummary>(Arrays.asList(catalogSummaries));

        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }

        return catalogs;
    }

    @Override
    public CatalogCustomField getCustomFieldByCatIdFieldName(String catId, String fieldName) {
        CatalogCustomField catalogCustomField;
        try {
            catalogCustomField =  (CatalogCustomField)new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catId/{catId}/fieldName/{fieldName}",CatalogCustomField.class, catId, fieldName);
//            catalogs = new ArrayList<CatalogSummary>(Arrays.asList(catalogSummaries));

        } catch(RestClientException rse) {
            logger.error("Error retrieving catalog custom field!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalog custom field!", exp);
            throw new AdminUIException(exp);
        }

        return catalogCustomField;
    }

    @Override
    public List<CatalogSummary> getCatalogsByType(String unitId, int typeId) {
        List<CatalogSummary> catalogs = new ArrayList<CatalogSummary>();

        if(StringUtils.isEmpty(unitId)) throw new IllegalArgumentException("catalog 'unitId' can not be null or blank");

        try {
            CatalogSummary[] catalogSummaries =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitid/"+unitId+"/catalogtypeid/{catalogTypeId}", CatalogSummary[].class, typeId);
            catalogs = new ArrayList<CatalogSummary>(Arrays.asList(catalogSummaries));

        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }

        return catalogs;
    }


    @Override
    public CatalogSummary getCatalog(String catalogId) throws Exception {
        CatalogSummary catalogSummary = null;

        if(StringUtils.isEmpty(catalogId)) throw new IllegalArgumentException("catalogId can not be null or blank");

        try{
            catalogSummary =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}", CatalogSummary.class, catalogId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        if(catalogSummary == null){
            throw new Exception("Catalog with catalogId(" + catalogId  + ") does NOT exists");
        }

        return catalogSummary;
    }


    @Override
    public String getQuoteByQuoteIdJsonEdit(String quoteId, String unitId, int typeId, String catalogId, String parentcat) throws Exception {
        String catalogSummary = null;
        if(parentcat==null || parentcat.equals("")){
            parentcat = "-1";
        }
        if(StringUtils.isEmpty(quoteId)) throw new IllegalArgumentException("Quote ID can not be null or blank");

        try{
            catalogSummary =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitid/{unitId}/catalogtypeid/{typeId}/quoteid/{quoteId}/catId{catalogId}/parentcat/{parentcat}", String.class, unitId, typeId, quoteId, catalogId, parentcat);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return catalogSummary;
    }

    @Override
    public List<CatalogSummary> getAllCatalogsFiltered(String unitId, String supplierId, String filter, String catType) {
        List<CatalogSummary> catalogs = new ArrayList<CatalogSummary>();

        if(StringUtils.isEmpty(unitId)) throw new IllegalArgumentException("catalog 'unitId' can not be null or blank");

        try {
            CatalogSummary[] catalogSummaries =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitid/{unitId}/supplierId/{supplierId}/filter/{filter}/catType/{catType}", CatalogSummary[].class, unitId,supplierId,filter,catType);
            catalogs = new ArrayList<CatalogSummary>(Arrays.asList(catalogSummaries));

        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }

        return catalogs;
    }

    @Override
    public String getQuoteByQuoteIdJson(String quoteId, String unitId, int typeId) throws Exception {
        String catalogSummary = null;

        if(StringUtils.isEmpty(quoteId)) throw new IllegalArgumentException("Quote ID can not be null or blank");

        try{
            catalogSummary =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitid/{unitId}/catalogtypeid/{typeId}/quoteid/{quoteId}", String.class, unitId, typeId, quoteId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return catalogSummary;
    }
    @Override
    public String getCatalogByNameJson(String catalogName, String unitId, int typeId) throws Exception {
        String catalogSummary = null;

        if(StringUtils.isEmpty(catalogName)) throw new IllegalArgumentException("catalogName can not be null or blank");

        try{
            catalogSummary =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitid/{unitId}/catalogtypeid/{typeId}/catalogname/{catalogName}", String.class, unitId, typeId, catalogName);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return catalogSummary;
    }

  @Override
  public List<CatalogSummary> getCatalogsByIds(String catalogIds) throws Exception {
    List<CatalogSummary> catalogs = new ArrayList<CatalogSummary>();

    if (StringUtils.isEmpty(catalogIds))
      throw new IllegalArgumentException("catalogId can not be null or blank");

    try {
      CatalogPost catalogPost = new CatalogPost();
      catalogPost.setCatalogId(catalogIds);
      CatalogSummary[] catalogSummaries =
          new RestTemplate().postForObject(restServiceUrl.getCatalogListURI() + "catalogids/",
              catalogPost, CatalogSummary[].class);
      catalogs = new ArrayList<CatalogSummary>(Arrays.asList(catalogSummaries));

    } catch (RestClientException rse) {
      logger.error("Error retrieving catalogs!", rse);
      throw rse;
    } catch (Exception exp) {
      logger.error("Error retrieving catalogs!", exp);
      throw new AdminUIException(exp);
    }

    return catalogs;
  }

    @Override
    public CatalogItemCollection getCatalogItems(String catalogId, long pageStartIndex, long pageSize, String searchQuery)
    		throws AdminUIException {

        CatalogItemCollection itemCollection;
    	if(StringUtils.isEmpty(catalogId)) {
            throw new IllegalArgumentException("catalogId can not be null or blank");
        }

        try{
            itemCollection = new RestTemplate().getForObject(restServiceUrl.getCatalogServiceBaseURI() +
                    "/catalogs/{catalogId}/items?pageStartIndex={pageStartIndex}&pageSize={pageSize}&searchQuery={searchQuery}",
                    CatalogItemCollection.class, catalogId, pageStartIndex, pageSize, searchQuery);

        } catch (Exception e) {
            throw new AdminUIException(e);
        }

        return itemCollection;
    }

    //@Override
//    public List<Item> getCatalogItems(String catalogId,int pageNo) throws Exception {
//        if(StringUtils.isEmpty(catalogId)) throw new IllegalArgumentException("catalogId can not be null or blank");
//
//        List<Item> ociCatalogItems = new ArrayList<Item>();
//
//        try{
//           // OciCatalogItemProxy[] ociCatalogItemsArray =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}/items", OciCatalogItemProxy[].class, catalogId);
//            //ociCatalogItems = new ArrayList<OciCatalogItemProxy>(Arrays.asList(ociCatalogItemsArray));
//            // -- /catalogs/catalogid/{catalogId}/pageno/{pageNo}/items
//
//
//            Item[] ociCatalogItemsArray =  new RestTemplate().getForObject(restServiceUrl.getItemURI() + "/catalogid/{catalogId}/pageno/"+pageNo+"/items", Item[].class, catalogId);
//            ociCatalogItems = new ArrayList<Item>(Arrays.asList(ociCatalogItemsArray));
//
//        }catch (Exception exp) {
//            logger.error(exp.getMessage(),exp);
//            throw new AdminUIException(exp);
//        }
//
//        return ociCatalogItems;
//    }

    @Override
    public String getCatalogItemsJson(String catalogId,int pageNo) throws Exception {
        if(StringUtils.isEmpty(catalogId)) throw new IllegalArgumentException("catalogId can not be null or blank");

        String ociCatalogItems = null;

        try{
            ociCatalogItems =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}/pageno/"+pageNo+"/items", String.class, catalogId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return ociCatalogItems;
    }

    @Override
    public Map<String, CustomField> getCustomFieldsByUnitId(String unitId) throws Exception {
        if(StringUtils.isEmpty(unitId)) throw new IllegalArgumentException("unitId can not be null or blank");

        Map<String, CustomField> customFields = new HashMap<String, CustomField>();

        try{
            Map<String, Map<String, Object>> tempCustomFields =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitId/{unitId}/customFields", Map.class, unitId);

            for(Map.Entry<String, Map<String, Object>> entry :tempCustomFields.entrySet()){
                try{
                    customFields.put(entry.getKey(), new CustomField(entry.getValue()));
                }catch (Exception exp){}
            }

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return customFields;

    }

    @Override
    public Map<String, CustomField> getCustomFieldsByActiveStatus(String unitId, String active) throws Exception{
        if(StringUtils.isEmpty(unitId)) throw new IllegalArgumentException("unitId can not be null or blank");

        Map<String, CustomField> customFields = new HashMap<String, CustomField>();

        try{
            Map<String, Map<String, Object>> tempCustomFields =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitId/{unitId}/active/{active}/customFields", Map.class, unitId,active);

            for(Map.Entry<String, Map<String, Object>> entry :tempCustomFields.entrySet()){
                try{
                    customFields.put(entry.getKey(), new CustomField(entry.getValue()));
                }catch (Exception exp){}
            }

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return customFields;

    }

    @Override
    public List<CustomField> getCustomFieldsByFieldTypes(int unitId, FieldFilter filter) throws Exception {
        List<CustomField> list = new ArrayList<CustomField>();

        try{
        	CustomField[] customFields =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitId/"+ unitId+"/type/"+filter.getType(), CustomField[].class);
            list = new ArrayList<CustomField>(Arrays.asList(customFields));

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return list;

    }

    @Override
    public boolean customFieldExists(int unitId, String name, String type) throws Exception {
        boolean exists = false;
        try{
        	 exists =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitId/"+ unitId+"/name/"+ name+"/type/"+type, Boolean.class);

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return exists;

    }

    @Override
    public void updateCatalogByCatalogId(String unitId, String catalogId, String outputRecords,String userid) throws Exception {
        try{
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + "updateCatalogById/unitId/"+unitId+"/catalogId/"+ catalogId+"/outputRecords/"+outputRecords+"?userid="+userid,"");

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public List<CustomFieldTypeMetrics> getCustomFieldTypeMetrics(int unitId) throws Exception {

        List<CustomFieldTypeMetrics> metrics = new ArrayList<CustomFieldTypeMetrics>();

        try{
            CustomFieldTypeMetrics[] tempMetrics =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "customFieldTypeMetrics/unitId/{unitId}", CustomFieldTypeMetrics[].class, unitId);
            metrics = new ArrayList<CustomFieldTypeMetrics>(Arrays.asList(tempMetrics));
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
        return metrics;
    }

    @Override
    public Map<String, CatalogCustomField> getCatalogCustomFieldByCatalogId(String catalogId) throws Exception {
        if(StringUtils.isEmpty(catalogId)) throw new IllegalArgumentException("unitId can not be null or blank");

        Map<String, CatalogCustomField> customFields = new LinkedHashMap<String, CatalogCustomField>();

        try{
            Map<String, Map<String, Object>> tempCustomFields =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catalogId/{catalogId}/catalogCustomFields", Map.class, catalogId);

            for(Map.Entry<String, Map<String, Object>> entry :tempCustomFields.entrySet()){
                try{
                    customFields.put(entry.getKey(), new CatalogCustomField(entry.getValue()));
                }catch (Exception exp){}
            }

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return customFields;
    }

    @Override
    public CustomField addCustomField(CustomField customField) throws Exception {
        if(customField == null) {
            throw new IllegalArgumentException("custom Field can not be null or blank");
        }

        CustomField addedCustomField;

        try {
            addedCustomField = new RestTemplate().postForObject(restServiceUrl.getCatalogListURI() + "customField", customField, CustomField.class);
        } catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return addedCustomField;
    }

    @Override
    public CatalogCustomField addCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception {
        if(catalogCustomField == null) {
            throw new IllegalArgumentException("custom Field can not be null or blank");
        }

        CatalogCustomField addedCustomField;

        try {
            addedCustomField = new RestTemplate().postForObject(restServiceUrl.getCatalogListURI() + "catalogCustomField", catalogCustomField, CatalogCustomField.class);
        } catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return addedCustomField;
    }


    @Override
    public void updateCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception {
        if(catalogCustomField == null) {
            throw new IllegalArgumentException("custom Field can not be null or blank");
        }

        try {
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + "catalogCustomField", catalogCustomField);
        } catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }


    @Override
    public void updateCustomField(CustomField customField) throws Exception {
        if(customField == null) {
            throw new IllegalArgumentException("custom Field can not be null or blank");
        }

        try {
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + "customField/update", customField);
        } catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public CatalogCustomField getCatalogCustomField(String catalogCustomFieldId) throws Exception {
        if(catalogCustomFieldId == null) {
            throw new IllegalArgumentException("custom Field can not be null or blank");
        }

        try {

            return new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catalogCustomFieldId/{catalogCustomFieldId}", CatalogCustomField.class ,catalogCustomFieldId);
        } catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public CustomField getCustomField(String customFieldId) throws Exception {
        if(customFieldId == null) {
            throw new IllegalArgumentException("custom Field can not be null or blank");
        }

        try {

            return new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "customFieldId/{customFieldId}", CustomField.class ,customFieldId);
        } catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void updateCatalogStatus(String catalogId, int status) throws Exception {
        CatalogSummary catalogSummary = null;

        if(StringUtils.isEmpty(catalogId)) throw new IllegalArgumentException("catalogId can not be null or blank");

        try{
            new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}/statusid/{status}", null, catalogId, status);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void updateCatalogProfiles(String catalogId, int[] profileIds, boolean associate) throws Exception {
        if(StringUtils.isEmpty(catalogId) || profileIds.length == 0) throw new IllegalArgumentException("catalogId or profileId can not be null or blank");

        String profileIdArray = Arrays.toString(profileIds);
        profileIdArray = profileIdArray.substring(1,profileIdArray.length()-1);
        profileIdArray = profileIdArray.replace(", ", ",");
        try{
            RestTemplate restTemplate = new RestTemplate();
            if(associate){
                // Use PUT
                restTemplate.postForLocation(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}/profileids/{profileIdArray}", null, catalogId, profileIdArray);
            }else {
                // Use DELETE
                restTemplate.delete(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}/profileids/{profileIdArray}", catalogId, profileIdArray );
            }
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void updateCatalogApprovers(String catalogId, int[] approverIds) throws Exception {
        if(StringUtils.isEmpty(catalogId) || approverIds.length == 0) throw new IllegalArgumentException("catalogId or profileId can not be null or blank");

        String approverIdArray = Arrays.toString(approverIds);
        approverIdArray = approverIdArray.substring(1,approverIdArray.length()-1);
        approverIdArray = approverIdArray.replace(", ", ",");
        try{
            RestTemplate restTemplate = new RestTemplate();
                // Use DELETE
                restTemplate.delete(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}/approverids/{approverIds}", catalogId, approverIdArray);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }
    @Override
    public CatalogSummary createCatalog(CreateCatalogPost createCatalogPost) throws Exception {
        CatalogSummary catalogSummary = null;
        try{
            RestfulResponseErrorHandler customResponseErrorHandler = new RestfulResponseErrorHandler(logger);

            RestTemplate rest = new RestTemplate();
            rest.setErrorHandler(customResponseErrorHandler);

            ResponseEntity<CatalogSummary> response = rest.postForEntity(restServiceUrl.getCatalogListURI() + "staging", createCatalogPost, CatalogSummary.class);

            catalogSummary = response.getBody();

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }

        return catalogSummary;
    }

    @Override
    public void createExternalCatalog(CreateCatalogPost createCatalogPost) throws Exception {
        try{
            new RestTemplate().postForLocation(restServiceUrl.getCatalogListURI() + "external", createCatalogPost);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void updateCatalogStatus(String catalogId, String status,String userid) throws Exception {
        try{
            ///catalogs/{catalogId}/status/{status}
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + catalogId + "/status/" + status+"?userid="+userid, "");
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public List<Profile> getProfileListForCatalog(String unitId, String catalogId){
        Profile[] profiles = null;
        List <Profile> profileList= null;
        try{
            profiles = new RestTemplate().getForObject(restServiceUrl.catalogProfileURI() + "/unitid/"+unitId +"/catalogid/{catalogId}", Profile[].class, catalogId);
            profileList = new ArrayList<Profile>(Arrays.asList(profiles));

        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
        return profileList;
    }

    @Override
    public void deleteCatalog(String catalogId) throws Exception {
        try{
            new RestTemplate().delete(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}", catalogId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void rejectCatalog(String catalogId,String userid) throws Exception {
        try{
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + "reject/catalogid/"+ catalogId+"?userid="+userid, "");
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void catalogChangeHistory(String catalogid,String userid) throws Exception {
        try {
            new RestTemplate().getForObject(restServiceUrl.getCatalogChangeHistoryURI() + "catalogid/{catalogid}?userid="+userid, String.class, catalogid);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new AdminUIException(e);
        }

    }

  @Override
  public List<GroupedCatalogCountDTO> getGroupedCatalogCounts(String unitId, String groupBy) {
    try {
      return Arrays.asList(new RestTemplate()
                               .getForObject(restServiceUrl.getCatalogGroupWiseCountsURI(),
                                             GroupedCatalogCountDTO[].class, unitId, groupBy));
    } catch (RestClientException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public long getErroneousCatalogsCount(String unitId) {
    try {
      return new RestTemplate().getForObject(restServiceUrl.getCatalogErroneousCountURI(),
                                             Long.class, unitId);
    } catch (RestClientException e) {
      logger.error(e.getMessage(), e);
    }
    return 0;
  }

  @Override
    public void exportCatalog(String catalogid,String userid) throws Exception {
    	try {
    		 new RestTemplate().getForObject(restServiceUrl.getExportCatalogURI() + "catalogid/{catalogid}?userid="+userid, String.class, catalogid);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new AdminUIException(e);
		}

    }
	@Override
	public String getExportedFilePath(String resourceid, String unitid)
			throws Exception {
		try{

			 String path = new RestTemplate().getForObject(restServiceUrl.getExportedFileURI() + "?resourceid="+resourceid+"&unitid="+unitid, String.class);
			 return path;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "";
	}
    @Override
    public void deleteCatalogCustomFieldByCatalogId(String catalogId, String catalogCustomFieldIds) throws Exception {
        try{
            new RestTemplate().delete(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}/catalogCustomFieldIds/{catalogCustomFieldIds}", catalogId, catalogCustomFieldIds);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void updateCatalogCustomFields(List<CatalogCustomField> catalogCustomFieldList,String catalogid) throws Exception {
        try{
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + "catalogCustomFieldList?catalogid={catalogid}", catalogCustomFieldList,catalogid);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void deleteCatalogCustomFieldByCustomFieldId(String customFieldId) throws Exception {
        try{
            new RestTemplate().delete(restServiceUrl.getCatalogListURI() + "customFieldId/{customFieldId}", customFieldId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void deleteCatalogCustomFieldByIdAndCatId(String customFieldId, String catId) throws Exception {
        try{
            new RestTemplate().delete(restServiceUrl.getCatalogListURI() + "customFieldId/{customFieldId}/catId/{catId}", customFieldId, catId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public List<CatalogSummary> getCatalogsByCustomFieldId(String customFieldIdCat) throws Exception {
        List<CatalogSummary> catalogs = new ArrayList<CatalogSummary>();

//        if(StringUtils.isEmpty(catalogIds)) throw new IllegalArgumentException("catalogId can not be null or blank");

        try{
            CatalogSummary[] catalogSummaries =  new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "customFieldIdCat/{customFieldIdCat}", CatalogSummary[].class, customFieldIdCat);
            catalogs = new ArrayList<CatalogSummary>(Arrays.asList(catalogSummaries));
        } catch(RestClientException rse) {
            logger.error("Error retrieving catalogs!", rse);
            throw rse;
        } catch(Exception exp) {
            logger.error("Error retrieving catalogs!", exp);
            throw new AdminUIException(exp);
        }

        return catalogs;
    }

    @Override
    public void deleteCustomFields(String catalogCustomFieldIds) throws Exception {
        try{
            new RestTemplate().delete(restServiceUrl.getCatalogListURI() + "customFieldIds/{customFieldIds}", catalogCustomFieldIds);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public void activateDeactivateCustomFields(String customFieldIds, boolean active) throws Exception {
        try{
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + "active/"+active+"/customFieldIds/"+customFieldIds,"");
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }


    @Override
    public String getCatalogJson(String catalogId) throws Exception {
        try{
            return new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "catalogid/{catalogId}", String.class, catalogId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public int getPostFilterCountByCompany(int unitId) throws Exception {
        try{
            return new RestTemplate().getForObject(restServiceUrl.getCatalogServiceBaseURI() + "/postFilterCount/unitId/{unitId}", int.class, unitId);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public int getInactivePostFilterCountByIds(String ids) throws Exception {
        try{
            return new RestTemplate().getForObject(restServiceUrl.getCatalogServiceBaseURI() + "/inactivePostFilterCount/ids/{ids}", int.class, ids);
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
    }

    @Override
    public CatalogSummary editCatalog(EditCatalogPost editCatalogPost) throws Exception {
        CatalogSummary catalogSummary = null;
        try{
            RestfulResponseErrorHandler customResponseErrorHandler = new RestfulResponseErrorHandler(logger);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(customResponseErrorHandler);

            //restTemplate.put(restServiceUrl.getCatalogListURI() + "staging", editCatalogPost);
            HttpEntity<EditCatalogPost> editCatalogPostHttpEntity = new HttpEntity<EditCatalogPost>(editCatalogPost);
            ResponseEntity<CatalogSummary> catalogSummaryResponseEntity = restTemplate.exchange(restServiceUrl.getCatalogListURI() + "staging", HttpMethod.PUT, editCatalogPostHttpEntity, CatalogSummary.class);

            catalogSummary = catalogSummaryResponseEntity.getBody();

        }catch (Exception exp) {
           // logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
        return catalogSummary;
    }

    @Override
    public List<CustomField> getCustomFieldListByUnitId(int unitId) throws Exception {

        List<CustomField> list = new ArrayList<CustomField>();

        try{
            CustomField[] customFields = new RestTemplate().getForObject(restServiceUrl.getCatalogListURI() + "unitId/{unitId}/customFieldList", CustomField[].class, unitId);
            list = new ArrayList<CustomField>(Arrays.asList(customFields));
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
        return list;
    }

    @Override
    public int deleteCatalogItems(String catalogId, List<String> itemIds) throws Exception {
        int count = 0;
        try {
            String itemIdsString = StringUtils.join(itemIds, ',');
//                                                                    /catalogs/catalog/{catalogId}/catalogitems/{catalogItemIds}
            new RestTemplate().delete(restServiceUrl.getCatalogListURI() +"catalog/"+ catalogId + "/catalogitems/"+itemIdsString);
        } catch (RestClientException rse) {
            logger.error("Error deleting catalog Items!", rse);
            throw rse;
        } catch (Exception exp) {
            logger.error("Error deleting catalog Items!", exp);
            throw new AdminUIException(exp);
        }
        return itemIds.size();
    }

	@Override
	public void updateCatalogState(String catalogId, String state, String unitId,String userid)throws Exception {
        try{
            new RestTemplate().put(restServiceUrl.getCatalogListURI() + catalogId + "/state/" + state+"/unitid/"+unitId+"?userid="+userid, "");
        }catch (Exception exp) {
            logger.error(exp.getMessage(),exp);
            throw new AdminUIException(exp);
        }
	}
}
