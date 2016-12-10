package com.vroozi.customerui.supplier.services.rest;

// import com.vroozi.customerui.approver.model.ApproverProxy;
import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.supplier.model.CompanyUser;
import com.vroozi.customerui.supplier.model.Country;
import com.vroozi.customerui.supplier.model.CurrencyCode;
import com.vroozi.customerui.supplier.model.DateFormat;
import com.vroozi.customerui.supplier.model.DecimalNotation;
import com.vroozi.customerui.supplier.model.Language;
import com.vroozi.customerui.supplier.model.State;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierAttributes;
import com.vroozi.customerui.supplier.model.SupplierResult;
import com.vroozi.customerui.supplier.model.VendorMapping;
import com.vroozi.customerui.supplier.model.VendorMappingFilePost;
import com.vroozi.customerui.util.RestServiceUrl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: rashidha Date: 9/11/12 Time: 2:44 PM To change this template use
 * File | Settings | File Templates.
 */
public class SupplierRestClientImpl implements SupplierRestClient {
  private final Logger LOGGER = LoggerFactory.getLogger(SupplierRestClientImpl.class);

  @Autowired
  RestServiceUrl restServiceUrl;

  @Override
  public List<Supplier> getSupplierByUnitId(String unitId) throws Exception {
    List<Supplier> suppliers = new ArrayList<Supplier>();
    try {

      Supplier[] suppliersArray =
          new RestTemplate().getForObject(
              restServiceUrl.getSupplierPathURI() + "/unitid/" + unitId, Supplier[].class);
      suppliers = Arrays.asList(suppliersArray);

    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw exp;
    }

    return suppliers;
  }


  @Override
  public List<Supplier> getActiveSupplierByUnitId(String unitId) throws Exception {
    List<Supplier> suppliers = new ArrayList<Supplier>();
    try {

      Supplier[] suppliersArray =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI() + "/active/unitid/"
              + unitId, Supplier[].class);
      suppliers = Arrays.asList(suppliersArray);

    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw exp;
    }

    return suppliers;
  }

  @Override
  public List<Supplier> findSuppliersAgainstKeywordAndUnitId(String unitId, String keyword,
      Boolean nonCatalogSupplier, int maxResults) {

    List<Supplier> suppliers = new ArrayList<Supplier>();
    try {
      String url =
          restServiceUrl.getSupplierPathURI() + "/typeahead/" + unitId + "?maxResults="
              + maxResults;

      if (keyword != null && keyword.length() > 0) {
        url += "&keyword=" + keyword;
      }

      if (nonCatalogSupplier != null) {
        url += "&nonCatalogSupplier=" + nonCatalogSupplier;
      }

      Supplier[] suppliersArray = new RestTemplate().getForObject(url, Supplier[].class);
      suppliers = Arrays.asList(suppliersArray);

    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw exp;
    }

    return suppliers;
  }

  @Override
  public List<Supplier> searchSuppliers(String unitId, String keyword, int currentPage, int pageSize) {
    List<Supplier> suppliers = new ArrayList<Supplier>();
    try {
      String url =
          restServiceUrl.getSupplierPathURI() + "?unitId=" + unitId + "&code=" + keyword
          + "&pageSize=" + pageSize + "&currentPage=" + currentPage + "&includeCount=false";

      SupplierResult result = new RestTemplate().getForObject(url, SupplierResult.class);
      suppliers = result.getDataItems();

    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw exp;
    }

    return suppliers;
  }

  @Override
  public Map<String, Long> getStatusWiseSupplierCounts(String unitId) {
    Map<String, Long> statusWiseCountsMap = new HashMap<>();
    try {
      return new RestTemplate()
          .getForObject(restServiceUrl.getSupplierStatusWiseCountsURI(), Map.class, unitId);
    } catch (RestClientException e) {
      LOGGER.error("Exception in getting role wise user counts for unitId " + unitId, e);
    }

    return statusWiseCountsMap;
  }

  @Override
  public int updateSupplierStatus(boolean state, List<String> supplierIds) throws Exception {
    try {
      String supplierIdString = StringUtils.join(supplierIds, ',');

      // suppliers/{supplierids}/activate/{action}
      new RestTemplate().put(restServiceUrl.getSupplierPathURI() + "/" + supplierIdString
          + "/activate/" + state, "");

    } catch (RestClientException rse) {
      LOGGER.error("Error updating supplier!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error updating supplier!", exp);
      throw exp;
    }
    return 0;
  }

  @Override
  public void deleteSuppliers(List<String> suppliersIds, String unitid) throws Exception {
    try {
      String supplierIdString = StringUtils.join(suppliersIds, ',');

      // /suppliers/{supplierids}/unitid/{unitid}
      new RestTemplate().delete(restServiceUrl.getSupplierPathURI() + "/" + supplierIdString
          + "/unitid/" + unitid);

    } catch (RestClientException rse) {
      LOGGER.error("Error deleting supplier!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error deleting supplier!", exp);
      throw exp;
    }
    return;
  }

  @Override
  public Supplier addSupplier(Supplier supplier) throws Exception {
    Supplier retSupplier = null;

    try {



      retSupplier =
          new RestTemplate().postForObject(restServiceUrl.getSupplierPathURI(), supplier,
              Supplier.class);
    } catch (RestClientException rse) {
      LOGGER.error("Error saving supplier !", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error saving supplier!", exp);
      throw new AdminUIException(exp);
    }

    return retSupplier;

  }

  @Override
  public List<SupplierAttributes> getSupplierAttributesByUnitId(String unitId) throws Exception {
    List<SupplierAttributes> supplierAttributesList = new ArrayList<SupplierAttributes>();
    try {
      SupplierAttributes[] supplierAttributesArray =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/companyattributes/unitid/" + unitId, SupplierAttributes[].class);
      supplierAttributesList = Arrays.asList(supplierAttributesArray);

    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving approvers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving approvers!", exp);
      throw new AdminUIException(exp);
    }
    return supplierAttributesList;
  }

  @Override
  public void updateSupplierAttributesStatus(boolean state, String[] supplierAttributeIds)
      throws Exception {
    try {
      String supplierAttributeIdString = StringUtils.join(supplierAttributeIds, ',');

      String stateString = state ? "A" : "D";

      // /suppliers/action/{action}/companyattributesids/{attributesids}
      new RestTemplate().put(restServiceUrl.getSupplierPathURI() + "/action/" + stateString
          + "/companyattributesids/" + supplierAttributeIdString, "");

    } catch (RestClientException rse) {
      LOGGER.error("Error updating supplier!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error updating supplier!", exp);
      throw exp;
    }
  }

  @Override
  public void deleteSupplierAttributes(String[] supplierAttributeIds, String unitId)
      throws Exception {
    try {
      String supplierAttributeIdString = StringUtils.join(supplierAttributeIds, ',');

      // /suppliers/deletecompanyattributes/companyattributesIds/{companyattributesids}/unitid/{unitid}
      new RestTemplate().put(restServiceUrl.getSupplierPathURI()
          + "/deletecompanyattributes/companyattributesids/" + supplierAttributeIdString
          + "/unitid/" + unitId, "");

    } catch (RestClientException rse) {
      LOGGER.error("Error deleting supplier attributeIds!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error deleting supplier attributeIds!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public boolean checkMappingExists(String supplierId, String uniqueSupplierId,
      String uniqueSystemId) throws Exception {
    try {
      Boolean result =
          new RestTemplate().getForObject(restServiceUrl.getVerifyMappingExistsURI(),
              Boolean.class, uniqueSupplierId, supplierId, uniqueSystemId);
      return result;
    } catch (Exception e) {
      LOGGER.error("error retrieving mappings", e);
      throw new AdminUIException(e);
    }
  }

  @Override
  public void addSupplierMapping(String supplierId, String uniqueSupplierId, String unitId,
      String uniqueSystemId, String companyName) throws Exception {
    try {
      VendorMapping vendorMapping = new VendorMapping();
      vendorMapping.setVendorNumber(supplierId);
      vendorMapping.setUniqueSupplierId(uniqueSupplierId);
      vendorMapping.setUnitId(Integer.parseInt(unitId));
      vendorMapping.setUniqueSystemId(uniqueSystemId);
      vendorMapping.setVendorName(companyName);
      new RestTemplate().postForObject(restServiceUrl.getAddNewMappingURI(), vendorMapping,
          VendorMapping.class);
    } catch (Exception e) {
      LOGGER.error("error adding mapping", e);
      throw new AdminUIException(e);
    }
  }

  @Override
  public void updateSupplierMapping(String supplierId, String uniqueSupplierId, String unitId,
      String mappingId, String uniqueSystemId) throws Exception {
    try {
      VendorMapping vendorMapping = new VendorMapping();
      vendorMapping.setVendorNumber(supplierId);
      vendorMapping.setUniqueSupplierId(uniqueSupplierId);
      vendorMapping.setUnitId(Integer.parseInt(unitId));
      vendorMapping.setUniqueSystemId(uniqueSystemId);
      vendorMapping.setRow(Long.parseLong(mappingId));
      new RestTemplate().postForObject(restServiceUrl.getUpdateMappingURI(), vendorMapping,
          VendorMapping.class);
    } catch (Exception e) {
      LOGGER.error("error adding mapping", e);
      throw new AdminUIException(e);
    }


  }

  @Override
  public void deleteSupplierMapping(String supplierMappingId) throws Exception {
    new RestTemplate().delete(restServiceUrl.getDeleteSupplierMappingURI(), supplierMappingId);
  }

  @Override
  public List<Country> getAllCountries() throws Exception {
    List<Country> list = null;
    try {
      Country[] listOfCountries =
          new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/countryregion/",
              Country[].class);
      list = Arrays.asList(listOfCountries);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }

    return list;
  }

  @Override
  public List<State> getAllStates() throws Exception {
    List<State> list = null;
    try {
      State[] listOfStates =
          new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/statescounty/",
              State[].class);
      list = Arrays.asList(listOfStates);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }

    return list;
  }

  @Override
  public List<Language> getAllLanguages() throws Exception {
    List<Language> list = null;
    try {
      Language[] listOfLanguages =
          new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/language/",
              Language[].class);
      list = Arrays.asList(listOfLanguages);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }

    return list;
  }

  @Override
  public List<DecimalNotation> getAllDecimalNotation() throws Exception {
    List<DecimalNotation> list = null;
    try {
      DecimalNotation[] listOfLanguages =
          new RestTemplate().getForObject(
              restServiceUrl.userDataServiceURI() + "/decimalnotation/", DecimalNotation[].class);
      list = Arrays.asList(listOfLanguages);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }

    return list;
  } 

  @Override
  public List<DateFormat> getAllDateFormats() throws Exception {
    List<DateFormat> list = null;
    try {
      DateFormat[] listOfDateFormats =
          new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/dateformat/",
              DateFormat[].class);
      list = Arrays.asList(listOfDateFormats);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }

    return list;
  }

  @Override
  public List<CurrencyCode> getAllCurrencyCodes() throws Exception {
    List<CurrencyCode> list = null;
    try {
      CurrencyCode[] listOfCurrencyCodes =
          new RestTemplate().getForObject(restServiceUrl.userDataServiceURI() + "/currencycodes/",
              CurrencyCode[].class);
      list = Arrays.asList(listOfCurrencyCodes);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }

    return list;
  }

  @Override
  public Supplier getSupplier(String unitId, String supplierId) throws Exception {
    Supplier supplier = null;
    try {
      supplier =
          new RestTemplate().getForObject(restServiceUrl.userDataServiceURI()
              + "/company/{unitId}/supplier/{supplierId}", Supplier.class, unitId, supplierId);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving supplier by unitId & supplierId!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving supplier by unitId & supplierId!", exp);
      throw new AdminUIException(exp);
    }
    return supplier;
  }


  @Override
  public String getSupplierByAdmin(String unitId, String adminid) throws Exception {
    String supplier = "";
    try {
      supplier =
          new RestTemplate().getForObject(restServiceUrl.userDataServiceURI()
              + "/suppliers/adminid/{adminid}?unitid={unitid}", String.class, adminid, unitId);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving supplier by unitId & supplierId!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving supplier by unitId & supplierId!", exp);
      throw new AdminUIException(exp);
    }
    return supplier;
  }

  // @Override
  // public List<ApproverProxy> getApproverList(String unitId) {
  // try {
  // ApproverProxy[] approverArray = new
  // RestTemplate().getForObject(restServiceUrl.catalogApproverURI()+ "/unitId/"+unitId,
  // ApproverProxy[].class);
  // List<ApproverProxy> approverList = Arrays.asList(approverArray);
  // return approverList;
  // } catch(RestClientException rse) {
  // LOGGER.error("Error retrieving approvers!", rse);
  // throw rse;
  // } catch(Exception exp) {
  // LOGGER.error("Error retrieving approvers!", exp);
  // throw new AdminUIException(exp);
  // }
  // }

  @Override
  public VendorMapping addVendorMapping(VendorMapping vendorMapping) throws Exception {
    try {
      vendorMapping =
          new RestTemplate().postForObject(restServiceUrl.getSupplierPathURI() + "/addSysVendorId",
              vendorMapping, VendorMapping.class);
    } catch (RestClientException rse) {
      LOGGER.error("Error saving vendor Mapping!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error saving vendor Mapping!", exp);
      throw new AdminUIException(exp);
    }
    return vendorMapping;

  }

  @Override
  public List<VendorMapping> getVendorMappingByDunsAndUnitId(String unitId, String dunsNumber)
      throws Exception {
    try {
      VendorMapping[] systemVendorArray =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/systemVendor/unitId/" + unitId + "/dunsNumber/" + dunsNumber,
              VendorMapping[].class);
      List<VendorMapping> systemVendors = Arrays.asList(systemVendorArray);
      return systemVendors;
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public List<Supplier> getSupplierByDuns(String duns) throws Exception {
    Supplier[] suppliersArray;
    try {
      suppliersArray =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/dunsUnique/dunsNumber/" + duns, Supplier[].class);

      List<Supplier> supplierList = Arrays.asList(suppliersArray);
      return supplierList;
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving suppliers!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving suppliers!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public Supplier getCompanyDetails(String supplier, String dunsNumber) throws Exception {
    Supplier supplier1 = new Supplier();
    try {
      supplier1 =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/supplierDetails/supplierId/" + supplier + "/dunsNumber/" + dunsNumber,
              Supplier.class);
      return supplier1;
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving supplier record!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving supplier record!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public void editSupplier(Supplier supplier) throws Exception {
    try {
      new RestTemplate().put(restServiceUrl.getSupplierPathURI(), supplier);
    } catch (RestClientException rse) {
      LOGGER.error("Error saving supplier !", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error saving supplier!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public void uploadMaterialGroup(VendorMappingFilePost createCatalogPost) throws Exception {
    try {
      // new RestTemplate().postForLocation(restServiceUrl.getUploadMaterialGroupURI(),
      // createCatalogPost);
      new RestTemplate().postForLocation(restServiceUrl.getSupplierPathURI() + "/vendorMapping",
          createCatalogPost);
    } catch (Exception exp) {
      throw new AdminUIException(exp);
    }
  }

  @Override
  public CompanyUser addCompanyUser(CompanyUser companyUser) throws Exception {
    try {
      companyUser =
          new RestTemplate().postForObject(restServiceUrl.getSupplierPathURI() + "/companyUser",
              companyUser, CompanyUser.class);
    } catch (RestClientException rse) {
      LOGGER.error("Error saving supplier !", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error saving supplier!", exp);
      throw new AdminUIException(exp);
    }
    return companyUser;

  }

  @Override
  public List<CompanyUser> getAllCompanyUsersBySupplierId(String supplierId) throws Exception {
    try {
      CompanyUser[] companyUser =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/allCompanyUsersBySupplierId/supplierId/" + supplierId, CompanyUser[].class);
      return Arrays.asList(companyUser);
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving Company Users!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving Company Users!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public CompanyUser getComanyUser(String email, String supplierId) throws Exception {
    try {
      CompanyUser companyUser =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/companyUserBySupplierId/supplierId/" + supplierId + "/email/" + email,
              CompanyUser.class);
      return companyUser;
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving Company User!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving Company User!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public CompanyUser editCompanyUser(CompanyUser companyUser) throws Exception {
    try {
      companyUser =
          new RestTemplate().postForObject(
              restServiceUrl.getSupplierPathURI() + "/editCompanyUser", companyUser,
              CompanyUser.class);
    } catch (RestClientException rse) {
      LOGGER.error("Error editing companyUser !", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error edit companyUser!", exp);
      throw new AdminUIException(exp);
    }
    return companyUser;
  }

  @Override
  public SupplierAttributes addCompanyAttributes(SupplierAttributes supplierAttributes)
      throws Exception {
    try {
      supplierAttributes =
          new RestTemplate().postForObject(restServiceUrl.getSupplierPathURI()
              + "/companyattributes", supplierAttributes, SupplierAttributes.class);
    } catch (RestClientException rse) {
      LOGGER.error("Error saving supplierAttributes !", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error saving supplierAttributes!", exp);
      throw new AdminUIException(exp);
    }
    return supplierAttributes;
  }

  @Override
  public SupplierAttributes editCompanyAttributes(SupplierAttributes supplierAttributes)
      throws Exception {
    try {
      supplierAttributes =
          new RestTemplate().postForObject(restServiceUrl.getSupplierPathURI()
              + "/editcompanyattributes", supplierAttributes, SupplierAttributes.class);
    } catch (RestClientException rse) {
      LOGGER.error("Error editing supplierAttributes !", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error edit supplierAttributes!", exp);
      throw new AdminUIException(exp);
    }
    return supplierAttributes;
  }

  @Override
  public VendorMapping getVendorDetails(String vendorName, String systemId, String clientId)
      throws Exception {
    VendorMapping systemVendor = new VendorMapping();
    try {
      systemVendor =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/vendorDetails/vendorId/" + vendorName + "/systemId/" + systemId + "/clientId/"
              + clientId, VendorMapping.class);
      return systemVendor;
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving system vendor record!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving system vendor record!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public VendorMapping editSystemVendor(VendorMapping systemVendor, String vendorIdOld,
      String systemIdOld, String clientIdOld) throws Exception {
    try {
      systemVendor =
          new RestTemplate().postForObject(restServiceUrl.getSupplierPathURI()
              + "/editSystemVendor/vendorIdOld/" + vendorIdOld + "/systemIdOld/" + systemIdOld
              + "/clientIdOld/" + clientIdOld, systemVendor, VendorMapping.class);
    } catch (RestClientException rse) {
      LOGGER.error("Error editing systemVendor !", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error editing systemVendor!", exp);
      throw new AdminUIException(exp);
    }
    return systemVendor;
  }

  @Override
  public SupplierAttributes getAttributeDetails(String attributeId, String unitId) throws Exception {
    SupplierAttributes supplierAttributes = new SupplierAttributes();
    try {
      supplierAttributes =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/companyuserdetails/attributeid/" + attributeId + "/unitid/" + unitId,
              SupplierAttributes.class);
      return supplierAttributes;
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving company Details!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving company Details!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public SupplierAttributes getSupplierAttributeByName(String attributeName, String unitId)
      throws Exception {
    SupplierAttributes supplierAttributes = new SupplierAttributes();
    try {
      supplierAttributes =
          new RestTemplate().getForObject(restServiceUrl.getSupplierPathURI()
              + "/companyuserdetails/attributeName/" + attributeName + "/unitid/" + unitId,
              SupplierAttributes.class);
      return supplierAttributes;
    } catch (RestClientException rse) {
      LOGGER.error("Error retrieving company Details!", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error retrieving company Details!", exp);
      throw new AdminUIException(exp);
    }
  }

  @Override
  public void addSupplierContentViews(String supplierId, List<Integer> profileIDs) {
    String profileIds = StringUtils.join(profileIDs, ',');
    try {
      new RestTemplate().put(restServiceUrl.getSupplierPathURI() + "/supplierid/" + supplierId,
          profileIds);
    } catch (RestClientException rse) {
      LOGGER.error("Error adding Supplier Content Views", rse);
      throw rse;
    } catch (Exception exp) {
      LOGGER.error("Error adding Supplier Content Views", exp);
      throw new AdminUIException(exp);
    }
  }
}
