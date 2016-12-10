package com.vroozi.customerui.catalog.services;

import com.vroozi.customerui.catalog.exception.CatalogServiceException;
import com.vroozi.customerui.catalog.model.*;
import com.vroozi.customerui.catalog.services.rest.CatalogRestClient;
import com.vroozi.customerui.customfield.model.CustomFieldTypeMetrics;
import com.vroozi.customerui.profile.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    CatalogRestClient catalogRestClient;

    // Default Constructor
    public CatalogServiceImpl() {
    }

    
    @Override
    public int updateCatalogItemStatus(CatalogItemPost itemStatusPost) throws CatalogServiceException {
    	
		try{
			catalogRestClient.updateItemStatus(itemStatusPost);
		}catch (Exception e) {
			throw new CatalogServiceException();
		}
		return 0;
    	
    	
    	
    }    
    
    @Override
    public List<CatalogSummary> getAllCatalogs(String unitId) throws CatalogServiceException {
        try{
            return catalogRestClient.getAllCatalogs(unitId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public List<CatalogSummary> getCatalogsByType(String unitId, int typeId) throws CatalogServiceException {
        try{
            return catalogRestClient.getCatalogsByType(unitId, typeId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public List<CatalogSummary> getCatalogsByIds(String catIds) throws CatalogServiceException {
        try{
            return catalogRestClient.getCatalogsByIds(catIds);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public List<CatalogSummary> getCatalogsByIds(List<String> catIdList) throws CatalogServiceException {
        List<CatalogSummary> catalogSummaries = new ArrayList<CatalogSummary>();
        try{
            if(catIdList != null && catIdList.size() > 0){
                StringBuffer sb = new StringBuffer();
                for(String catId :catIdList) {
                    if(catId != null && catId.length() > 0){
                        sb.append(catId + ",");
                    }
                }
                String catIds = sb.toString().substring(0, sb.toString().length()-1);
                catalogSummaries = getCatalogsByIds(catIds);
            }
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
        return catalogSummaries;
    }


    @Override
    public List<CatalogSummary> getCatalogsByCustomFieldId(String customFieldId) throws CatalogServiceException {
        try{
            return catalogRestClient.getCatalogsByCustomFieldId(customFieldId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public CatalogSummary getCatalog(String catalogId) throws CatalogServiceException {
        try{
            return catalogRestClient.getCatalog(catalogId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public String getQuoteByQuoteIdJsonEdit(String quoteId, String unitId, int typeId, String catalogid, String parentcat) throws Exception{
        try{
            return catalogRestClient.getQuoteByQuoteIdJsonEdit(quoteId, unitId, typeId, catalogid,parentcat);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    @Override
    public String getQuoteByQuoteIdJson(String quoteId, String unitId, int typeId) throws Exception{
        try{
            return catalogRestClient.getQuoteByQuoteIdJson(quoteId, unitId, typeId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public String getCatalogByNameJson(String catalogName, String unitId, int typeId) throws Exception{
        try{
            return catalogRestClient.getCatalogByNameJson(catalogName, unitId, typeId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public CatalogItemCollection getCatalogItems(String catalogId, long pageStartIndex, long pageSize, String searchQuery)
    		throws Exception {
        try{
            return catalogRestClient.getCatalogItems(catalogId, pageStartIndex, pageSize, searchQuery);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }

    }
    
//    @Override
//    public List<Item> getCatalogItems(String catalogId,int pageNo) throws CatalogServiceException {
//        try{
//            return catalogRestClient.getCatalogItems(catalogId, pageNo);
//        }catch (Exception exp){
//            throw new CatalogServiceException(exp);
//        }
//    }

    @Override
    public String getCatalogItemsJson(String catalogId,int pageNo) throws CatalogServiceException {
        try{
            return catalogRestClient.getCatalogItemsJson(catalogId, pageNo);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public Map<String, CustomField> getCustomFieldsByStatus(String unitId,String status) throws Exception {
        try{
            return catalogRestClient.getCustomFieldsByActiveStatus(unitId, status);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }

    }
    
    @Override
    public Map<String, CustomField> getCustomFieldsByUnitId(String unitId) throws CatalogServiceException {
        try{
            return catalogRestClient.getCustomFieldsByUnitId(unitId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public List<CustomField> getCustomFieldListByUnitId(int unitId) throws CatalogServiceException {
        try{
            return catalogRestClient.getCustomFieldListByUnitId(unitId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public int deleteCatalogItems(String catalogId, List<String> itemIds) throws CatalogServiceException {
        try{
            return catalogRestClient.deleteCatalogItems(catalogId,itemIds);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public int getPostFilterCountByCompany(int unitId) throws CatalogServiceException {
        try{
            return catalogRestClient.getPostFilterCountByCompany(unitId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public int getInactivePostFilterCountByIds(String ids) throws CatalogServiceException {
        try{
            return catalogRestClient.getInactivePostFilterCountByIds(ids);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public List<CustomField> getCustomFieldsByFieldTypes(int unitId, FieldFilter filter) throws CatalogServiceException {
        try{
        	List<CustomField> fieldList = catalogRestClient.getCustomFieldsByFieldTypes(unitId, filter);
        	List<CustomField> tempList = new LinkedList<CustomField>();
            List<CustomField> returnList = new LinkedList<CustomField>();
            
            if(fieldList != null && fieldList.size() > 0){
            	if(filter.getSearch() != null && filter.getSearch().length() > 0){
            		for(CustomField cf : fieldList){
                		if(cf.getFieldName().toLowerCase().contains(filter.getSearch().toLowerCase())){
                			tempList.add(cf);
                		}
                	}
            		
            		fieldList = new LinkedList<CustomField>();
                	fieldList.addAll(tempList);
                	tempList = new LinkedList<CustomField>();
            	}
            	
            	if(filter.getStatus() != null && filter.getStatus().length() > 0){
            		for(CustomField cf : fieldList){
                		if(filter.getStatus().equalsIgnoreCase("active")){
                			if(cf.isActive()){
                				tempList.add(cf);
                			}
                		}
                		if(filter.getStatus().equalsIgnoreCase("inactive")){
                			if(!cf.isActive()){
                				tempList.add(cf);
                			}
                		}
                	}
            		
            		fieldList = new LinkedList<CustomField>();
                	fieldList.addAll(tempList);
            	}
            	
            	return fieldList;
            	
            } else {
            	return fieldList;
            }
        	
        	
            
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public boolean customFieldExists(int unitId, String name, String type) throws Exception {
    	try {
    		return catalogRestClient.customFieldExists(unitId, name, type);
    	}catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void updateCatalogByCatalogId(String unitId, String catalogId, String outputRecords,String userid) throws Exception {
        try {
            catalogRestClient.updateCatalogByCatalogId(unitId,catalogId,outputRecords,userid);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public List<CustomFieldTypeMetrics> getCustomFieldTypeMetrics(int unitId) throws CatalogServiceException {
        try{
            return catalogRestClient.getCustomFieldTypeMetrics(unitId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public Map<String, CatalogCustomField> getCatalogCustomFieldByCatalogId(String catalogId) throws CatalogServiceException {
        try{
            return catalogRestClient.getCatalogCustomFieldByCatalogId(catalogId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void updateCatalogProfiles(String catalogId, int profileId[], boolean associate) throws Exception {
        try{
            catalogRestClient.updateCatalogProfiles(catalogId, profileId, associate);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public List<Profile> getProfileListForCatalog(String unitId,String catalogId) throws Exception {
        try{
            return catalogRestClient.getProfileListForCatalog(unitId,catalogId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public CatalogSummary createCatalog(CreateCatalogPost createCatalogPost) throws CatalogServiceException {
        try{
             return catalogRestClient.createCatalog(createCatalogPost);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void createExternalCatalog(CreateCatalogPost createCatalogPost) throws CatalogServiceException {
        try{
            catalogRestClient.createCatalog(createCatalogPost);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void updateCatalogStatus(String catalogId, String status, String userid) throws CatalogServiceException {
        try{
            catalogRestClient.updateCatalogStatus(catalogId, status,userid);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void deleteCatalog(String catalogId) throws Exception {
        try{
            catalogRestClient.deleteCatalog(catalogId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void rejectCatalog(String catalogId , String userid) throws Exception {
        try{
            catalogRestClient.rejectCatalog(catalogId,userid);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    public String getCatalogJson(String catalogId) throws Exception{
        try{
            return catalogRestClient.getCatalogJson(catalogId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    public CatalogSummary editCatalog(EditCatalogPost editCatalogPost) throws Exception{
        try{
            return catalogRestClient.editCatalog(editCatalogPost);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    public CustomField addCustomField(CustomField customField) throws Exception {
        try{
            return catalogRestClient.addCustomField(customField);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public CatalogCustomField addCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception {
        try {
            return catalogRestClient.addCatalogCustomField(catalogCustomField);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void updateCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception {
        try {
            catalogRestClient.updateCatalogCustomField(catalogCustomField);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void updateCustomField(CustomField customField) throws Exception {
        try {
            catalogRestClient.updateCustomField(customField);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }


    @Override
    public void deleteCatalogCustomFieldByCatalogId(String catalogId, String catalogCustomFieldIds) throws Exception {
        try {
            catalogRestClient.deleteCatalogCustomFieldByCatalogId(catalogId, catalogCustomFieldIds);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public void updateCatalogCustomFields(List<CatalogCustomField> catalogCustomFieldList,String catalogid) throws Exception {
        try {
            catalogRestClient.updateCatalogCustomFields(catalogCustomFieldList,catalogid);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void deleteCatalogCustomFieldByCustomFieldId(String customFieldId) throws Exception {
        try {
            catalogRestClient.deleteCatalogCustomFieldByCustomFieldId(customFieldId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void deleteCatalogCustomFieldByIdAndCatId(String customFieldId, String catId) throws Exception {
        try {
            catalogRestClient.deleteCatalogCustomFieldByIdAndCatId(customFieldId,catId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public CatalogCustomField getCustomFieldByCatIdFieldName(String catId, String fieldName) throws Exception {
        try {
            return catalogRestClient.getCustomFieldByCatIdFieldName(catId, fieldName);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }
    
    @Override
    public void deleteCustomFields(String catalogCustomFieldIds) throws Exception {
        try {
            catalogRestClient.deleteCustomFields(catalogCustomFieldIds);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public void activateDeactivateCustomFields(String catalogCustomFieldIds, boolean active) throws Exception {
        try {
            catalogRestClient.activateDeactivateCustomFields(catalogCustomFieldIds, active);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
    public CatalogCustomField getCatalogCustomField(String catalogCustomFieldId) throws Exception {
        try {
            return catalogRestClient.getCatalogCustomField(catalogCustomFieldId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }


    @Override
    public CustomField getCustomField(String customFieldId) throws Exception {
        try {
            return catalogRestClient.getCustomField(customFieldId);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

    @Override
	public void updateCatalogApprovers(int catalogId, int[] approverIds) {
		try{
            catalogRestClient.updateCatalogApprovers(""+catalogId, approverIds);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
		
	}
	@Override
	public void updateCatalogState(String catalogId, String state, String unitId,String userid)
			throws Exception {
        try{
            catalogRestClient.updateCatalogState(catalogId, state, unitId,userid);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
		
	}

    @Override
    public void catalogChangeHistory(String catalogid,String userid) throws CatalogServiceException {
        try{
            catalogRestClient.catalogChangeHistory(catalogid, userid);

        }catch (Exception e) {
            throw new CatalogServiceException(e);
        }

    }

  @Override
  public List<GroupedCatalogCountDTO> getGroupedCatalogCounts(String unitId, String groupBy) {
    return catalogRestClient.getGroupedCatalogCounts(unitId, groupBy);
  }

  @Override
  public long getErroneousCatalogsCount(String unitId) {
    return catalogRestClient.getErroneousCatalogsCount(unitId);
  }

  @Override
	public void exportCatalog(String catalogid,String userid) throws CatalogServiceException {
		try{
			catalogRestClient.exportCatalog(catalogid,userid);
			
		}catch (Exception e) {
			throw new CatalogServiceException(e);
		}
		
	}
	
	@Override
	public String getExportedFilePath(String resourceid, String unitid) {
		try{
			return catalogRestClient.getExportedFilePath(resourceid, unitid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

    @Override
    public List<CatalogSummary> getAllCatalogsFiltered(String unitId, String supplierId, String filter, String catType) {
        try{
            return catalogRestClient.getAllCatalogsFiltered(unitId,supplierId,filter,catType);
        }catch (Exception exp){
            throw new CatalogServiceException(exp);
        }
    }

}
