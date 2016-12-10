package com.vroozi.customerui.catalog.services;

import com.vroozi.customerui.catalog.exception.CatalogServiceException;
import com.vroozi.customerui.catalog.model.*;
import com.vroozi.customerui.customfield.model.CustomFieldTypeMetrics;
import com.vroozi.customerui.profile.model.Profile;

import java.util.List;
import java.util.Map;

public interface CatalogService {
    
	 /* update catalog item status 
	 * @author Khansaj
	 * @param itemIds
	 * @param catalogIds
	 * @param newStatus
	 * @return
	 */
	int updateCatalogItemStatus(CatalogItemPost itemStatusPost)throws CatalogServiceException;

    List<CatalogSummary> getAllCatalogs(String unitId) throws CatalogServiceException;

    List<CatalogSummary> getCatalogsByType(String unitId, int typeId) throws CatalogServiceException;

    CatalogSummary getCatalog(String catalogId) throws CatalogServiceException;

    CatalogItemCollection getCatalogItems(String catalogId, long pageStartIndex, long pageSize, String searchQuery) throws Exception;
    
    String getCatalogItemsJson(String catalogId,int pageNo) throws Exception;

    Map<String, CustomField> getCustomFieldsByUnitId(String unitId) throws Exception;
    Map<String, CustomField> getCustomFieldsByStatus(String unitId,String status) throws Exception;
    
    Map<String, CatalogCustomField> getCatalogCustomFieldByCatalogId(String catalogId) throws Exception;

    List <Profile> getProfileListForCatalog(String unitId,String catalogId) throws Exception;

    void updateCatalogProfiles(String catalogId, int profileId[], boolean associate) throws Exception;

    CatalogSummary createCatalog(CreateCatalogPost createCatalogPost) throws Exception;

    void updateCatalogStatus(String catalogId, String statusId,String userid) throws Exception;

    void deleteCatalog(String catalogId) throws Exception;

    String getCatalogJson(String catalogId) throws Exception;

    CatalogSummary editCatalog(EditCatalogPost editCatalogPost) throws Exception;

    String getQuoteByQuoteIdJsonEdit(String catalogName, String unitId, int typeId, String catalogId, String parentcat) throws Exception;

    String getQuoteByQuoteIdJson(String catalogName, String unitId, int typeId) throws Exception;

    String getCatalogByNameJson(String catalogName, String unitId, int typeId) throws Exception;

    CustomField addCustomField(CustomField customField) throws Exception;

    CatalogCustomField addCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception;

    void updateCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception;

    void updateCustomField(CustomField customField) throws Exception;

    void deleteCatalogCustomFieldByCatalogId(String catalogId, String catalogCustomFieldIds) throws Exception;

    CatalogCustomField getCatalogCustomField(String catalogCustomFieldId) throws Exception;

    CustomField getCustomField(String customFieldId) throws Exception;

    void updateCatalogApprovers(int catalogId, int[] approverIds);

    void createExternalCatalog(CreateCatalogPost createCatalogPost) throws CatalogServiceException;

    public void deleteCustomFields(String catalogCustomFieldIds) throws Exception;

    public void activateDeactivateCustomFields(String catalogCustomFieldIds, boolean active) throws Exception;

    public List<CatalogSummary> getCatalogsByIds(String catIds) throws CatalogServiceException;

    public List<CatalogSummary> getCatalogsByIds(List<String> catIds) throws CatalogServiceException;

    public void deleteCatalogCustomFieldByCustomFieldId(String customFieldId) throws Exception;

    public List<CatalogSummary> getCatalogsByCustomFieldId(String customFieldId) throws CatalogServiceException;

    public List<CustomFieldTypeMetrics> getCustomFieldTypeMetrics(int unitId) throws CatalogServiceException;
    
    public List<CustomField> getCustomFieldsByFieldTypes(int unitId, FieldFilter filter) throws CatalogServiceException;
    
    public List<CustomField> getCustomFieldListByUnitId(int unitId) throws CatalogServiceException;
    
    int deleteCatalogItems(String catalogId, List<String> itemIds) throws CatalogServiceException;
    
    public boolean customFieldExists(int unitId, String name, String type) throws Exception;
    
    public int getPostFilterCountByCompany(int unitId) throws CatalogServiceException;
    
    public int getInactivePostFilterCountByIds(String ids) throws CatalogServiceException;

    public void updateCatalogByCatalogId(String unitId, String catalogId, String outputRecords,String userid) throws Exception;
    
    public void updateCatalogCustomFields(List<CatalogCustomField> catalogCustomFieldList , String catalogid) throws Exception;

    CatalogCustomField getCustomFieldByCatIdFieldName(String catId, String fieldName) throws Exception;

    void deleteCatalogCustomFieldByIdAndCatId(String customFieldId, String catId) throws Exception;
    void updateCatalogState(String catalogId, String state , String unitId,String userid) throws Exception;
    void rejectCatalog(String catalogId,String userid)throws Exception;
    void exportCatalog(String catalogid,String userid)throws CatalogServiceException;
    String getExportedFilePath(String resourceid, String unitid);

    List<CatalogSummary> getAllCatalogsFiltered(String unitId, String supplierId, String filter, String catType);

    void catalogChangeHistory(String catalogId, String userId)throws CatalogServiceException;

  /**
   * Gets catalog counts grouped against given groupBy field
   *
   * @param unitId
   * @param groupBy
   * @return
   * @author Muhammad Haris
   */
  List<GroupedCatalogCountDTO> getGroupedCatalogCounts(String unitId, String groupBy);

  /**
   * Gets count of those catalogs which contain errors
   *
   * @param unitId
   * @return
   * @throws Exception
   * @author Muhammad Haris
   */
  long getErroneousCatalogsCount(String unitId);
}
