package com.vroozi.customerui.supplier.services;

import com.vroozi.customerui.locale.services.rest.LocaleRestClient;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.model.ProfileProxy;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.profile.services.ProfileService;
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
import com.vroozi.customerui.supplier.services.rest.SupplierRestClient;
import com.vroozi.customerui.user.services.rest.UserRestClient;
import com.vroozi.customerui.user.services.user.model.UsersDTO;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.RandomStringUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.vroozi.customerui.util.Consts.FORMAT_DATE_DD_MM_YYYY_HH_MM_SS;

/**
 * Provides ....
 *
 * @author <a href="mailto:ata.tous@vroozi.com">Ara Tatous</a>
 * @version 1.0
 * @since 10/9/12:5:29 PM
 */
public class SupplierServiceImpl implements SupplierService {
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

    @Autowired
    SupplierRestClient supplierRestClient;
    
    @Autowired
    private LocaleRestClient localeRestClient;
    
    @Autowired
    UserRestClient userRestClient;

    @Autowired
    ProfileService profileService;

    @Autowired
    private ProfileGroupService profileGroupService;
    
    @Override
    public List<Supplier> getSupplierByUnitId(String unitId) throws SupplierServiceException {
        List<Supplier> suppliers = null;

        try{
            suppliers  = supplierRestClient.getSupplierByUnitId(unitId);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return suppliers;
    }

    @Override
    public List<Supplier> getActiveSupplierByUnitId(String unitId) throws SupplierServiceException {
        List<Supplier> suppliers = null;

        try{
            suppliers  = supplierRestClient.getActiveSupplierByUnitId(unitId);
            Collections.sort(suppliers, new SupplierNameComparator());
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }

        return suppliers;
    }
    
  @Override
  public List<Supplier> getSuppliersAgainstKeywordAndUnitId(String unitId, String keyword,
      Boolean nonCatalogSupplier, int maxResults) {
    return supplierRestClient.findSuppliersAgainstKeywordAndUnitId(unitId, keyword, nonCatalogSupplier, maxResults);
  }
    
  @Override
  public List<Supplier> searchSuppliers(String unitId, String keyword, int currentPage, int pageSize) {
    return supplierRestClient.searchSuppliers(unitId, keyword, currentPage, pageSize);
  }

  @Override
  public Map<String, Long> getStatusWiseSupplierCounts(String unitId) {
    return supplierRestClient.getStatusWiseSupplierCounts(unitId);
  }

  public class SupplierNameComparator implements Comparator<Supplier> {
        public int compare(Supplier s1, Supplier s2) {
            return s1.getCompanyName().toLowerCase().compareTo(s2.getCompanyName().toLowerCase());
        }
    }

    @Override
    public void deleteSuppliers(String[] suppliersIds, String unitid) throws SupplierServiceException {
        try{
            supplierRestClient.deleteSuppliers(Arrays.asList(suppliersIds), unitid);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
    }

    @Override
    public void updateSupplierStatus(boolean active, String[] suppliersIds) throws SupplierServiceException {
        try{
            supplierRestClient.updateSupplierStatus(active, Arrays.asList(suppliersIds));
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
    }

  @Override
  public Supplier addSupplier(Supplier supplier) throws SupplierServiceException {
    Supplier retSupplier = null;
    try {

      List<User>
          supplierAdmins =
          userRestClient
              .getUserBySupplierAndNotify(supplier.getUnitId(), supplier.getUniqueSupplierId());

      retSupplier = supplierRestClient.addSupplier(supplier);

      ProfileProxy profile = createNewSupplierProfile(retSupplier);
      List<Integer> profileIds = new ArrayList<Integer>();
      if (profile != null) {
        profileIds.add(profile.getProfileId());
        ProfileGroup profileGroup = createNewSupplierProfileGroup(retSupplier, profileIds);
      }
      List<String> admins = new ArrayList<String>();

      if (null != supplierAdmins && supplierAdmins.size() > 0) {
        for (User user : supplierAdmins) {
          user.setAssignedProfiles(profileIds);
          admins.add(user.getUserId());
        }
        try {

          UsersDTO usersDTO = new UsersDTO();
          usersDTO.setUsers(supplierAdmins);
          userRestClient.addUserBatch(usersDTO);
          userRestClient.deleteTempUser(admins);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      retSupplier.setSupplierAdmins(admins);
      supplierRestClient.editSupplier(retSupplier);
      //retSupplier = supplierRestClient.addSupplier(supplier);

    } catch (Exception exp) {
      throw new SupplierServiceException(exp);
    }
    return retSupplier;
  }

    @Override
    public ProfileGroup getSupplierProfileGroup(Supplier supplier) throws Exception{

        String groupName = "Profile_Group_"+supplier.getUniqueSupplierId();
        ProfileGroup profileGroup = profileGroupService.getProfileGroupByName(groupName,supplier.getUnitId());
        return profileGroup;

    }
    private ProfileGroup createNewSupplierProfileGroup(Supplier supplier, List<Integer> profileIds) throws Exception{

        ProfileGroup profileGroup = new ProfileGroup();
        String groupName = "Profile_Group_"+supplier.getUniqueSupplierId();
        profileGroup.setAssociatedProfiles(profileIds);
        profileGroup.setCreatedBy(supplier.getCreatedBy());
        profileGroup.setUnitId(supplier.getUnitId());
        profileGroup.setCreatedByName(supplier.getCreatedByName());
        String newToken = RandomStringUtil.createToken();
        profileGroup.setToken(newToken);
        profileGroup.setGroupName(groupName);
        profileGroup.setActive(true);
        profileGroup.setSupplierAssociated(true);
        profileGroup.setCreatedOn(new Date());
        profileGroup =  profileGroupService.addProfileGroup(profileGroup);

        return profileGroup;
    }

    private ProfileProxy createNewSupplierProfile(Supplier supplier) throws Exception{

        ProfileProxy proxy = new ProfileProxy();
        proxy.setProfileName("Profile_"+supplier.getUniqueSupplierId());
        proxy.setProfileDesc("Profile_"+supplier.getCompanyName());
        proxy.setActive(true);
        proxy.setProductRating(0);
        proxy.setCreatedOnView("Profiles_"+supplier.getUniqueSupplierId());
        proxy.setCreatedBy(supplier.getCreatedBy());
        proxy.setCreatedByName(supplier.getCreatedByName());
        proxy.setUnitId(supplier.getUnitId());
        proxy.setCreatedOn(new Date());
        proxy.setSupplierAssociated(true);
        return profileService.addProfile(proxy);

    }
    @Override
    public List<Country> getAllCountries() throws SupplierServiceException {
        List<Country> countries = null;
        try{
            countries = supplierRestClient.getAllCountries();
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return countries;
    }

    @Override
    public List<State> getAllStates() throws SupplierServiceException {
        List<State> states = null;
        try{
            states = supplierRestClient.getAllStates();
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return states;
    }
    
    @Override
    public List<Language> getAllLanguages() throws SupplierServiceException {
        List<Language> languages = null;
        try{
            languages = supplierRestClient.getAllLanguages();
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return languages;
    }

    @Override
    public List<DecimalNotation> getAllDecimalNotation() throws SupplierServiceException {
        List<DecimalNotation> decimalNotations = null;
        try{
            decimalNotations = supplierRestClient.getAllDecimalNotation();
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return decimalNotations;
    }

    @Override
    public List<TimeZone> getAllTimeFormats() throws SupplierServiceException {
        List<TimeZone> timeZones = null;
        try{
            timeZones = localeRestClient.getAllTimeZones();
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return timeZones;
    }

    @Override
    public List<DateFormat> getAllDateFormats() throws SupplierServiceException {
        List<DateFormat> dateFormats = null;
        try{
            dateFormats = supplierRestClient.getAllDateFormats();
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return dateFormats;
    }

    @Override
    public List<CurrencyCode> getAllCurrencyCodes() throws SupplierServiceException {
    	List<CurrencyCode> currencyCodes = null;
        try{
            currencyCodes = supplierRestClient.getAllCurrencyCodes();
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return currencyCodes;
    }
    
    @Override
    public Supplier getSupplier(String unitId, String supplierId) throws SupplierServiceException {
        Supplier supplier = null;
        try{
            supplier = supplierRestClient.getSupplier(unitId, supplierId);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return supplier;
    }
    
    @Override
    public String getSupplierByAdmin(String unitId, String adminid)throws SupplierServiceException {
        String supplier = null;
        try{
            supplier = supplierRestClient.getSupplierByAdmin(unitId, adminid);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return supplier;
    }

    @Override
    public void editSupplier(Supplier supplier) throws SupplierServiceException {
        try{
            supplierRestClient.editSupplier(supplier);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
    }

    @Override
    public List<SupplierAttributes> getSupplierAttributesByUnitId(String unitId) throws SupplierServiceException {
        List<SupplierAttributes> suppliers = null;

        try{
            suppliers  = supplierRestClient.getSupplierAttributesByUnitId(unitId);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }

        return suppliers;
    }

    public void updateSupplierAttributesStatus(boolean state, String[] supplierAttributeIds) throws SupplierServiceException{
        try{
            supplierRestClient.updateSupplierAttributesStatus(state, supplierAttributeIds);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
    }

    @Override
    public void deleteSupplierAttributes(String[] supplierAttributesIds, String unitId) throws SupplierServiceException {
        try{
            supplierRestClient.deleteSupplierAttributes(supplierAttributesIds, unitId);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
    }

    @Override
    public SupplierAttributes addCompanyAttributes(SupplierAttributes supplierAttributes) throws Exception {
        SupplierAttributes returnSupplierAttributes = null;
        try{
            returnSupplierAttributes = supplierRestClient.addCompanyAttributes(supplierAttributes);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return returnSupplierAttributes;
    }

    @Override
    public SupplierAttributes updateSupplierAttributes(SupplierAttributes supplierAttributes) throws Exception {
        SupplierAttributes returnSupplierAttributes = null;
        try{
            returnSupplierAttributes = supplierRestClient.editCompanyAttributes(supplierAttributes);
        }catch (Exception exp){

            throw new SupplierServiceException(exp);
        }
        return returnSupplierAttributes;
    }

    @Override
    public SupplierAttributes getSupplierAttributeDetails(String attributeId, String unitId) throws Exception {
        SupplierAttributes returnSupplierAttributes = null;
        try{
            returnSupplierAttributes = supplierRestClient.getAttributeDetails(attributeId, unitId);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return returnSupplierAttributes;
    }

    @Override
    public SupplierAttributes getSupplierAttributeByName(String attributeName, String unitId) throws Exception {
        SupplierAttributes returnSupplierAttributes = null;
        try{
            returnSupplierAttributes = supplierRestClient.getSupplierAttributeByName(attributeName, unitId);
        }catch (Exception exp){
            throw new SupplierServiceException(exp);
        }
        return returnSupplierAttributes;
    }
    @Override
    public boolean checkMappingExists(String supplierId, String uniqueSupplierId, String uniqueSystemId) throws Exception {
        try {
            return supplierRestClient.checkMappingExists(supplierId, uniqueSupplierId, uniqueSystemId);
        } catch (Exception e) {
            throw new SupplierServiceException(e);
        }
    }

    @Override
    public void addSupplierMapping(String supplierId, String uniqueSupplierId, String unitId, String uniqueSystemId, String companyName) throws Exception {
        try {
            supplierRestClient.addSupplierMapping(supplierId, uniqueSupplierId, unitId, uniqueSystemId, companyName);
        } catch (Exception e) {
            throw new SupplierServiceException(e);
        }
    }

    @Override
    public void updateSupplierMapping(String supplierId, String uniqueSupplierId, 
            String unitId, String mappingId, String uniqueSystemId) throws Exception {

        try {
            supplierRestClient.updateSupplierMapping(supplierId, uniqueSupplierId, unitId, mappingId, uniqueSystemId);
        } catch (Exception e) {
            throw new SupplierServiceException(e);
        }

    	
    }
    
    @Override
    public void deleteSupplierMapping(String supplierMappingId) throws Exception {
        try {
            supplierRestClient.deleteSupplierMapping(supplierMappingId);
        } catch (Exception e) {
            throw new SupplierServiceException(e);
        }
    }

	@Override
	public void setSupplierContentViews(String supplierId,
			List<Integer> profileIds) {
		supplierRestClient.addSupplierContentViews(supplierId, profileIds);
		
	}
}

