package com.vroozi.customerui.supplier.services.rest;

import com.vroozi.customerui.supplier.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rashidha
 * Date: 9/11/12
 * Time: 12:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SupplierRestClient {
    List<Supplier> getSupplierByUnitId(String unitId) throws Exception;

    int updateSupplierStatus(boolean active, List<String> suppliersIds) throws Exception;

    void deleteSuppliers(List<String> suppliersIds, String unitid) throws Exception;

    Supplier addSupplier(Supplier supplier) throws Exception;

    List<Country> getAllCountries() throws Exception;

    List<State> getAllStates() throws Exception;
    
    List<Language> getAllLanguages() throws Exception;

    List<DecimalNotation> getAllDecimalNotation() throws Exception;

    List<DateFormat> getAllDateFormats() throws Exception;

    List<CurrencyCode> getAllCurrencyCodes() throws Exception;
    
    Supplier getSupplier(String unitId, String supplierId) throws Exception;
    String getSupplierByAdmin(String unitId, String adminid) throws Exception;
    
    List<SupplierAttributes> getSupplierAttributesByUnitId(String unitId) throws Exception;

    void updateSupplierAttributesStatus(boolean state, String[] supplierAttributeIds) throws Exception;

    void editSupplier(Supplier supplier) throws Exception;

    //List<ApproverProxy> getApproverList(String unitId) throws Exception;

    VendorMapping addVendorMapping(VendorMapping vendorMapping) throws Exception;
    
    List<VendorMapping> getVendorMappingByDunsAndUnitId(String unitId, String dunsNumber) throws Exception;
    
    List<Supplier> getSupplierByDuns(String duns) throws Exception;
    
    Supplier getCompanyDetails(String supplier,String dunsNumber) throws Exception;

    void uploadMaterialGroup(VendorMappingFilePost createCatalogPost) throws Exception;

    CompanyUser addCompanyUser(CompanyUser companyUser) throws Exception;

    List<CompanyUser> getAllCompanyUsersBySupplierId(String supplierId) throws Exception;
    
    CompanyUser getComanyUser(String email, String supplierId) throws Exception;

    CompanyUser editCompanyUser(CompanyUser companyUser) throws Exception;
    
    SupplierAttributes addCompanyAttributes(SupplierAttributes supplierAttributes) throws Exception;

    SupplierAttributes editCompanyAttributes(SupplierAttributes supplierAttributes) throws Exception;

    VendorMapping getVendorDetails(String vendorName, String systemId, String clientId) throws Exception;

    VendorMapping editSystemVendor(VendorMapping supplier,String vendorId,String systemId, String clientId) throws Exception;

    SupplierAttributes getAttributeDetails(String attributeId,String unitId) throws Exception;

    SupplierAttributes getSupplierAttributeByName(String attributeName,String unitId) throws Exception;
    
    void deleteSupplierAttributes(String[] suppliersIds, String unitid) throws Exception;
    void updateSupplierMapping(String supplierId, String uniqueSupplierId, 
            String unitId, String mappingId, String uniqueSystemId) throws Exception;

    boolean checkMappingExists(String supplierId, String uniqueSupplierId, String uniqueSystemId) throws Exception;

    void addSupplierMapping(String supplierId, String uniqueSupplierId, String unitId, String uniqueSystemId, String companyName) throws Exception;
    
    void deleteSupplierMapping(String supplierMappingId) throws Exception;
    
    void addSupplierContentViews(String userId, List<Integer> profileIds);
    
    List<Supplier> getActiveSupplierByUnitId(String unitId) throws Exception;
 
  /**
   * Invokes the User Data RESTFul API to fetch suppliers against given keyword and unit id
   * 
   * @param unitId
   * @param keyword
   * @param nonCatalogSupplier
   * @param maxResults
   * @return
   * @author Muhammad Haris
   */
  public List<Supplier> findSuppliersAgainstKeywordAndUnitId(String unitId, String keyword,
      Boolean nonCatalogSupplier, int maxResults);
  
  /**
   * Invokes the User Data RESTFul API to fetch suppliers against given keyword and unit id
   * 
   * @param unitId
   * @param keyword
   * @param currentPage
   * @param pageSize
   * @return
   */
  List<Supplier> searchSuppliers(String unitId, String keyword, int currentPage, int pageSize);

  /**
   * Gets suppliers counts against active status by invoking respective REST API
   *
   * @param unitId
   * @return
   * @author Muhammad Haris
   */
  Map<String, Long> getStatusWiseSupplierCounts(String unitId);
}
