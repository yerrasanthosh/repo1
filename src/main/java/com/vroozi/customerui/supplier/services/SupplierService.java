package com.vroozi.customerui.supplier.services;

import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.supplier.Exception.SupplierServiceException;
import com.vroozi.customerui.supplier.model.Country;
import com.vroozi.customerui.supplier.model.CurrencyCode;
import com.vroozi.customerui.supplier.model.DateFormat;
import com.vroozi.customerui.supplier.model.DecimalNotation;
import com.vroozi.customerui.supplier.model.Language;
import com.vroozi.customerui.supplier.model.State;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierAttributes;
import com.vroozi.customerui.supplier.model.TimeZone;

import java.util.List;
import java.util.Map;

/**
 * Provides Service layer for Supplier Controller.
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/9/12:5:19 PM
 */
public interface SupplierService {
    public ProfileGroup getSupplierProfileGroup(Supplier supplier) throws Exception;

    List<Country> getAllCountries() throws SupplierServiceException;
    
    List<State> getAllStates() throws SupplierServiceException;

    List<Language> getAllLanguages() throws SupplierServiceException;

    List<DecimalNotation> getAllDecimalNotation() throws SupplierServiceException;

    List<TimeZone> getAllTimeFormats() throws SupplierServiceException;

    List<DateFormat> getAllDateFormats() throws SupplierServiceException;
    
    List<CurrencyCode> getAllCurrencyCodes() throws SupplierServiceException;

    List<Supplier> getSupplierByUnitId(String unitId) throws SupplierServiceException;
    
    void updateSupplierStatus(boolean active, String[] suppliersIds)throws SupplierServiceException;

    void deleteSuppliers(String[] suppliersIds, String unitId) throws SupplierServiceException;

    Supplier addSupplier(Supplier supplier) throws SupplierServiceException;

    Supplier getSupplier(String unitId, String supplierId) throws SupplierServiceException;
    String getSupplierByAdmin(String unitId, String adminid) throws SupplierServiceException;
    
    void editSupplier(Supplier supplier) throws SupplierServiceException;

    List<SupplierAttributes> getSupplierAttributesByUnitId(String unitId) throws SupplierServiceException;

    void updateSupplierAttributesStatus(boolean state, String[] supplierAttributeIds) throws SupplierServiceException;

    void deleteSupplierAttributes(String[] supplierAttributesIds, String unitId) throws SupplierServiceException;

    SupplierAttributes addCompanyAttributes(SupplierAttributes supplierAttributes) throws Exception;

    SupplierAttributes updateSupplierAttributes(SupplierAttributes supplierAttributes) throws Exception;

    SupplierAttributes getSupplierAttributeDetails(String attributeId,String unitId) throws Exception;

    SupplierAttributes getSupplierAttributeByName(String attributeName, String unitId) throws Exception;
    
    boolean checkMappingExists(String supplierId, String uniqueSupplierId, String uniqueSystemId) throws Exception;

    void updateSupplierMapping(String supplierId, String uniqueSupplierId, String unitId, String mappingId, String uniqueSystemId) throws Exception;

    void addSupplierMapping(String supplierId, String uniqueSupplierId, String unitId, String uniqueSystemId, String companyName) throws Exception;
    
    void deleteSupplierMapping(String supplierMappingId) throws Exception;
    
    void setSupplierContentViews(String supplierId, List<Integer> profileIds);
    
    List<Supplier> getActiveSupplierByUnitId(String unitId) throws SupplierServiceException;
  
  /**
   * Finds suppliers against given unit id and keyword.
   * 
   * @param unitId
   * @param keyword {@link String} containing keyword to be matched with beginning of supplier name
   * @param nonCatalogSupplier
   * @param maxResults
   * @return {@link List} containing matched Suppliers
   * @author Muhammad Haris
   */
  public List<Supplier> getSuppliersAgainstKeywordAndUnitId(String unitId, String keyword,
      Boolean nonCatalogSupplier, int maxResults);
  
  /**
   * Finds suppliers against given unit id and keyword.
   * @param unitId
   * @param keyword {@link String} containing keyword to be matched with beginning of supplier name
   * @param currentPage
   * @param pageSize
   * @return
   */
  List<Supplier> searchSuppliers(String unitId, String keyword, int currentPage, int pageSize);

  /**
   * Gets suppliers counts against active status by invoking RestClient
   *
   * @param unitId
   * @return
   * @author Muhammad Haris
   */
  Map<String, Long> getStatusWiseSupplierCounts(String unitId);
}
