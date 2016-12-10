package com.vroozi.customerui.catalog.services.rest;

import com.vroozi.customerui.catalog.model.*;
import com.vroozi.customerui.customfield.model.CustomFieldTypeMetrics;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.profile.model.Profile;

import java.util.List;
import java.util.Map;

public interface CatalogRestClient {
	int updateItemStatus(CatalogItemPost itemStatusPost)throws Exception ;
	
    List<CatalogSummary> getAllCatalogs(String unitId) throws Exception;

    List<CatalogSummary> getCatalogsByType(String unitId, int typeId) throws Exception;

    CatalogSummary getCatalog(String catalogId) throws Exception;

    void updateCatalogStatus(String catalogId, int status) throws Exception;

    /**
     * Retrieve a collections of catalog items
     * @param catalogId catalog ID
     * @param pageStartIndex page start index
     * @param pageSize page size
     * @param searchQuery search query
     * @return <code>CatalogItemCollection</code> catalog item collection
     * @throws AdminUIException Admin UI exception
     */
    CatalogItemCollection getCatalogItems(String catalogId, long pageStartIndex, long pageSize, String searchQuery) throws AdminUIException;

    String getCatalogItemsJson(String catalogId,int pageNo) throws Exception;

    Map<String, CustomField> getCustomFieldsByUnitId(String unitId) throws Exception;
    
    Map<String, CustomField> getCustomFieldsByActiveStatus(String unitId, String active) throws Exception;

    Map<String, CatalogCustomField> getCatalogCustomFieldByCatalogId( String catalogId) throws Exception;

    void updateCatalogProfiles(String catalogId, int[] profileId, boolean associate) throws Exception;

    CatalogSummary createCatalog(CreateCatalogPost createCatalogPost) throws Exception;

    CatalogSummary editCatalog(EditCatalogPost editCatalogPost) throws Exception;

    List <Profile> getProfileListForCatalog(String unitId,String catalogId) throws Exception;

    void updateCatalogStatus(String catalogId, String statusId,String userid) throws Exception;

    void deleteCatalog(String catalogId) throws Exception;

    String getCatalogJson(String catalogId) throws Exception;

    CustomField addCustomField(CustomField customField) throws Exception;

    CatalogCustomField addCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception;

    void updateCatalogCustomField(CatalogCustomField catalogCustomField) throws Exception;

    void updateCustomField(CustomField customField) throws Exception;

    CatalogCustomField getCatalogCustomField(String catalogCustomFieldId) throws Exception;

    CustomField getCustomField(String customFieldId) throws Exception;

    void deleteCatalogCustomFieldByCatalogId(String catalogId, String catalogCustomFieldIds) throws Exception;

    void updateCatalogApprovers(String catalogId, int[] approverIds) throws Exception;

    void createExternalCatalog(CreateCatalogPost createCatalogPost) throws Exception;

    public void deleteCustomFields(String catalogCustomFieldIds) throws Exception;

    public void activateDeactivateCustomFields(String catalogCustomFieldIds, boolean active) throws Exception;

    public List<CatalogSummary> getCatalogsByIds(String catalogIds) throws Exception;

    public void deleteCatalogCustomFieldByCustomFieldId(String customFieldId) throws Exception;

    public List<CatalogSummary> getCatalogsByCustomFieldId(String customFieldIdCat) throws Exception;

    public String getCatalogByNameJson(String catalogName, String unitId, int typeId) throws Exception;

    public List<CustomFieldTypeMetrics> getCustomFieldTypeMetrics(int unitId) throws Exception;
    
    public List<CustomField> getCustomFieldsByFieldTypes(int unitId, FieldFilter filter) throws Exception;
    
    public List<CustomField> getCustomFieldListByUnitId(int unitId) throws Exception;
    
    public int deleteCatalogItems(String catalogId, List<String> itemIds) throws Exception;
    
    public boolean customFieldExists(int unitId, String name, String type) throws Exception;

    public int getPostFilterCountByCompany(int unitId) throws Exception;
    
    public int getInactivePostFilterCountByIds(String ids) throws Exception;

    public void updateCatalogByCatalogId(String unitId, String catalogId, String outputRecords,String userid) throws Exception;
    
    public void updateCatalogCustomFields(List<CatalogCustomField> catalogCustomFieldList,String catalogid) throws Exception;

    CatalogCustomField getCustomFieldByCatIdFieldName(String catId, String fieldName);

    void deleteCatalogCustomFieldByIdAndCatId(String customFieldId, String catId) throws Exception;
    void updateCatalogState(String catalogId, String state , String unitId,String userid) throws Exception;
    public void rejectCatalog(String catalogId,String userid) throws Exception;
    void exportCatalog(String catalogid,String userid)throws Exception;
    String getExportedFilePath(String resourceid, String unitid)throws Exception;

    String getQuoteByQuoteIdJson(String quoteId, String unitId, int typeId) throws Exception;
    String getQuoteByQuoteIdJsonEdit(String quoteId, String unitId, int typeId, String catalogId, String parentcat) throws Exception;

    List<CatalogSummary> getAllCatalogsFiltered(String unitId, String supplierId, String filter, String catType);

    void catalogChangeHistory(String catalogid, String userid)throws Exception;

  /**
   * Gets catalog counts grouped according to given groupBy fields by invoking respective REST API
   *
   * @param unitId
   * @param groupBy
   * @return
   * @author Muhammad Haris
   */
  List<GroupedCatalogCountDTO> getGroupedCatalogCounts(String unitId, String groupBy);

  /**
   * Gets count of those catalogs which contain errors by invoking respective REST API
   *
   * @param unitId
   * @return
   * @throws Exception
   * @author Muhammad Haris
   */
  long getErroneousCatalogsCount(String unitId);
}
