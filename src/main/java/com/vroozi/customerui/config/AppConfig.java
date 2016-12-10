package com.vroozi.customerui.config;


import com.vroozi.customerui.appConfig.service.AppConfigService;
import com.vroozi.customerui.appConfig.service.AppConfigServiceImpl;
import com.vroozi.customerui.appConfig.service.rest.AppConfigRestClient;
import com.vroozi.customerui.appConfig.service.rest.AppConfigRestClientImpl;
import com.vroozi.customerui.approver.services.rest.ApproverRestClient;
import com.vroozi.customerui.approver.services.rest.ApproverRestClientImpl;
import com.vroozi.customerui.catalog.services.rest.CatalogRestClient;
import com.vroozi.customerui.catalog.services.rest.CatalogRestClientImpl;
import com.vroozi.customerui.catalogItem.service.rest.CatalogItemRestClient;
import com.vroozi.customerui.catalogItem.service.rest.CatalogItemRestClientImpl;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.company.service.CompanyServiceImpl;
import com.vroozi.customerui.company.service.rest.CompanyRestClient;
import com.vroozi.customerui.company.service.rest.CompanyRestClientImpl;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.profile.services.rest.ProfileGroupRestClientImpl;
import com.vroozi.customerui.profile.services.rest.ProfileRestClientImpl;
import com.vroozi.customerui.property.service.PropertyService;
import com.vroozi.customerui.property.service.rest.PropertyServiceImpl;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.supplier.services.SupplierServiceImpl;
import com.vroozi.customerui.supplier.services.rest.SupplierRestClient;
import com.vroozi.customerui.supplier.services.rest.SupplierRestClientImpl;
import com.vroozi.customerui.user.services.rest.UserRestClient;
import com.vroozi.customerui.user.services.rest.UserRestClientImpl;
import com.vroozi.customerui.util.RestServiceUrl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

import java.io.IOException;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.vroozi.customerui")
public class AppConfig extends WebMvcConfigurerAdapter {

  @Value("${findContentShareMappingByUserNameURI}")
  public String findContentShareMappingByUsernameURI;

  @Value("${deleteContentShareMappingsURI}")
  public String deleteContentShareMappingsURI;
  
  @Value("${findContentShareMappingsURI}")
  public String findContentShareMappingsURI;
  
  @Value("${contentShareMappingsURI}")
  public String contentShareMappingsURI;
  
  @Value("${updateContentShareMappingURI}")
  public String updateContentShareMappingURI;

  @Value("${uploadContentShareMappingURI}")
  public String uploadContentShareMappingURI;

  @Value("${contentShareMappingStatusURI}")
  public String contentShareMappingStatusURI;

  @Value("${generateContentShareMappingFileURI}")
  public String generateContentShareMappingFileURI;

  @Value("${updateUserSpecificKeysURI}")
  public String updateUserSpecificKeysURI;
  
  @Value("${userByUsernameURI}")
  public String userByUserNameURI;
  
    @Value("${shopperURI}")
    public String shopperURI;

    @Value("${messageCenterURI}")
    public String messageCenterURI;

    @Value("${cookieVSD}")
    public String cookieVSD;
    @Value("${encryption.aes.key}")
    public String encryptionAESKey;

    @Value("${redirectToHttps}")
    public String redirectToHttps;

    @Value("${ssoPath}")
    public String ssoPath;
    @Value("${loginURI}")
    public String loginURI;
    @Value("${logoutURI}")
    public String logoutURI;

    @Value("${environment.url}")
    public String environmentUrl;

    @Value("${file.error.reportPath}")
    public String errorReportPath;

    @Value("${noImageWidth}")
    public String noImageWidth;

    @Value("${noImageHeight}")
    public String noImageHeight;

    
    @Value("${file.processed.path}")
    public String processedMappingPath;

    @Value("${reportingApiURI}")
    public String reportingApiURI;
         
    @Value("${spendByMonthURI}")
    public String spendByMonth;
    
    @Value("${spendByCategoryURI}")
    public String spendByCategory;
    
    @Value("${spendTotalByCategoryURI}")
    public String spendTotalByCategoryURI;
    
    @Value("${categorySpendingURI}")
    public String categorySpendingURI;
    
    @Value("${spendTotalByShopperAndCategoryURI}")
    public String spendTotalByShopperAndCategoryURI;
    
    @Value("${spendByCatalogTypeURI}")
    public String spendByCatalogType;
    
    @Value("${spendBySupplierURI}")
    public String spendBySupplier;
    
    @Value("${fileUploadPath}")
    public String fileUploadPath;
    @Value("${itemImagesPath}")
    public String itemImagesPath;
    @Value("${itemAttachmentsPath}")
    public String itemAttachmentsPath;
    @Value("${flagIconUploadPath}")
    public String flagIconUploadPath;
    @Value("${catalogServiceURI}")
    public String catalogServiceURI;
    @Value("${catalogListServicePath}")
    public String catalogListServicePath;
    @Value("${catalogApproverPath}")
    public String catalogApproverPath;
    @Value("${catalogPropertyPath}")
    public String catalogPropertyPath;
    @Value("${catalogUserPath}")
    public String catalogUserPath;
    @Value("${catalogProfilePath}")
    public String catalogProfilePath;
    @Value("${catalogServiceSearchPath}")
    public String catalogServiceSearchPath;

    @Value("${catalogGroupWiseCountsURI}")
    public String catalogGroupWiseCountsURI;

    @Value("${catalogErroneousCountURI}")
    public String catalogErroneousCountURI;

    @Value("${exportCatalogPath}")
    public String exportCatalogPath;


    @Value("${catalogChangeHistoryPath}")
    public String catalogChangeHistoryPath;

    @Value("${exportedCatalogPath}")
    public String exportedCatalogPath;
    
    @Value("${catalog.schedule.dateFormat}")
    public String catalogScheduleDateFormat;
    
    @Value("${userBatch}")
    public String userBatchPath;

    @Value("${tempUser}")
    public String tempUserPath;
    
    @Value("${updateTempUser}")
    public String updateTempUser;
    
    @Value("${attachmentsURI}")
    public String attachmentsURI;

    @Value("${imagesURI}")
    public String imagesURI;

    @Value("${findUserURI}")
    public String findUserURI;

    @Value("${catalogItemsPath}")
    public String catalogItemsPath;
    @Value("${fileUploadTempPath}")
    public String fileUploadTempPath;
    @Value("${fileUploadMaxSize}")
    public String fileUploadMaxSize;
    @Value("${updateItemStatusServicePath}")
    public String updateItemStatusServicePath;

    @Value("${supplierCompanyURI}")
    public String supplierCompanyURI;

    @Value("${checkMappingURI}")
    public String checkMappingURI;

    @Value("${addMappingURI}")
    public String addMappingURI;

    @Value("${updateMappingURI}")
    public String updateMappingURI;


    //smaroci
    @Value("${smartOCIAppBaseURI}")
    public String smartOCIAppBaseURI;

    //userdataservice
    @Value("${userDataServiceURI}")
    public String userDataServiceURI;
    @Value("${accessControlListURI}")
    public String accessControlListURI;

    @Value("${userRoleWiseCountsURI}")
    public String userRoleWiseCountsURI;

    @Value("${supplierStatusWiseCountsURI}")
    public String supplierStatusWiseCountsURI;

    @Value("${materialGroupURI}")
    public String materialGroupURI;
    @Value("${saveMaterialGroupURI}")
    public String saveMaterialGroupURI;
    @Value("${findMaterialGroupURI}")
    public String findMaterialGroupURI;
    @Value("${findMaterialGroupMappingPaginationURI}")
    public String findMaterialGroupMappingPaginationURI;
    @Value("${countMaterialGroupMappingsURI}")
    public String countMaterialGroupMappingsURI;
    @Value("${materialGroupMappingStatusPath}")
    public String materialGroupMappingStatusPath;
    @Value("${materialGroupMappingErrorStatusPath}")
    public String materialGroupMappingErrorStatusPath;

    // search api
    @Value("${searchApiBaseURI}")
    public String searchApiBaseURI;
    @Value("${searchByUnitIdPath}")
    public String searchByUnitIdPath;

    //vendorMapping
    @Value("${supplierMappingURI}")
    public String supplierMappingURI;
    @Value("${uploadSupplierMapping}")
    public String uploadSupplierMapping;

    // Supllier Bulk Upload
    @Value("${supplierBulkUploadURI}")
    public String supplierBulkUploadURI;

    @Value("${supplierBulkUploadStatusPath}")
    public String supplierBulkUploadStatusPath;


    @Value("${supplierBulkUpload}")
    public String supplierBulkUpload;


    @Value("${findSupplierBulkUploadPaginationURI}")
    public String findSupplierBulkUploadPaginationURI;


    @Value("${downloadSupplierBulkUploadURI}")
    public String downloadSupplierBulkUploadURI;
    //------------------------------

    @Value("${findSupplierMappingPaginationURI}")
    public String findSupplierMappingPaginationURI;
    
    
    @Value("${downloadSupplierMappingURI}")
    public String downloadSupplierMappingURI;

    @Value("${countSupplierMappingsURI}")
    public String countSupplierMappingsURI;
        
    @Value("${countSupplierBulkUploadURI}")
    public String countSupplierBulkUploadURI;
    @Value("${supplierMappingStatusPath}")
    public String supplierMappingStatusPath;
    @Value("${deleteSupplierMappingURI}")
    public String deleteSupplierMappingURI;

    //loginDataService
    @Value("${loginDataServiceURI}")
    public String loginDataServiceURI;

    @Value("${forgotPasswordPath}")
    public String forgotPasswordPath;

    @Value("${authenticatePath}")
    public String authenticatePath;

    @Value("${userSessionDataPath}")
    public String userSessionDataPath;
    
    @Value("${maxPostFilters}")
    public String maxPostFilters;

    @Value("${welcomeImageUploadSize}")
    public String welcomeImageUploadSize;

    //Notification Service
    @Value("${notificationApiBaseURI}")
    public String notificationApiBaseURI;

    @Value("${notificationPath}")
    public String notificationPath;

    
    @Value("${profileGroupPath}")
    public String profileGroupPath;

    @Value("${uomMappingPath}")
    public String uomMappingPath;

    @Value("${profileGroupMappingStatus}")
    public String profileGroupMappingStatus;

    @Value("${profileGroupMappingUpload}")
    public String profileGroupMappingUpload;

    @Value("${profileGroupMappingPage}")
    public String profileGroupMappingPage;

    @Value("${uomMappingPage}")
    public String uomMappingPage;

    @Value("${profileGroupMappingCount}")
    public String profileGroupMappingCount;


    @Value("${uomMappingCount}")
    public String uomMappingCount;

    @Value("${profileGroupPage}")
    public String profileGroupPage;
    
    @Value("${contentAccessURI}")
    public String contentAccessURI;
    
    @Value("${deleteContentAccessURI}")
    public String deleteContentAccessURI;

    @Value("${downloadContentAccessURI}")
    public String downloadContentAccessURI;
    
    @Value("${userProfiles}")
    public String userProfiles;

    @Value("${uomMappingUpload}")
    public String uomMappingUpload;

    @Value("${uomMappingStatus}")
    public Object uomMappingStatus;

    @Value("${downloadUOMMappingURI}")
    public String downloadUOMMappingURI;

    @Value("${currencyMappingPath}")
    public String currencyMappingPath;


    @Value("${currencyMappingUpload}")
    public String currencyMappingUpload;

    @Value("${currencyMappingStatus}")
    public Object currencyMappingStatus;

    @Value("${downloadCurrencyMappingURI}")
    public String downloadCurrencyMappingURI;

    @Value("${currencyMappingPage}")
    public String currencyMappingPage;

    @Value("${currencyMappingCount}")
    public String currencyMappingCount;

    @Value("${cXMLPunchOutUrl}")
    public String cXMLPunchOutUrl;

  @Value("${rowsPerPage}")
  public String rowsPerPage;

  @Value("${language}")
  public String language;

  @Value("${dateFormat}")
  public String dateFormat;

  @Value("${decimalNotation}")
  public String decimalNotation;

  @Value("${timeZone}")
  public String timeZone;

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();

        Resource[] resources = new ClassPathResource[] {
                new ClassPathResource("adminui.properties")
        };
        ppc.setLocations(resources);
        ppc.setIgnoreResourceNotFound(false);
        ppc.setIgnoreUnresolvablePlaceholders(false);

        return ppc;
    }

    @Bean(name="restServiceUrl")
    public RestServiceUrl getRestServiceUrl() {
        return new RestServiceUrl(this);
    }

    @Bean(name="catalogRestClient")
    public CatalogRestClient getCatalogRestClient(){
        return new CatalogRestClientImpl();
    }

    @Bean(name="userRestClient")
    public UserRestClient getUserRestClientImpl(){
        return new UserRestClientImpl();
    }

    @Bean(name="catalogItemRestClient")
    CatalogItemRestClient getCatalogItemRestClient(){
        return new CatalogItemRestClientImpl();
    }

    @Bean(name="profileService")
    public ProfileService getProfileService(){
        return new ProfileRestClientImpl();
    }

    @Bean(name="profileGroupService")
    public ProfileGroupService getProfileGroupService(){
        return new ProfileGroupRestClientImpl();
    }

    
    @Bean(name="propertyService")
    public PropertyService getPropertyService(){
        return new PropertyServiceImpl();
    }
    @Bean(name="supplierRestClient")
    public SupplierRestClient getSupplierRestClient(){
        return new SupplierRestClientImpl();
    }
    @Bean(name="supplierService")
    public SupplierService getSupplierService(){
        return new SupplierServiceImpl();
    }
    @Bean(name="approverRestClient")
    public ApproverRestClient getApproverRestClient(){
        return new ApproverRestClientImpl();
    }
    @Bean(name="companyRestClient")
    public CompanyRestClient getCompanyRestClient(){
        return new CompanyRestClientImpl();
    }
    @Bean(name="companyService")
    public CompanyService getCompanyService(){
        return new CompanyServiceImpl();
    }

    @Bean(name="appConfigRestClient")
    public AppConfigRestClient getAppConfigRestClient(){
        return new AppConfigRestClientImpl();
    }
    @Bean(name="appConfigService")
    public AppConfigService getAppConfigService(){
        return new AppConfigServiceImpl();
    }

    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver tilesViewResolver  = new TilesViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        tilesViewResolver.setRedirectHttp10Compatible(false);
        return tilesViewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        String[] definitions = {"/WEB-INF/views/**/views.xml" };
        tilesConfigurer.setDefinitions(definitions);

        return tilesConfigurer;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(Long.parseLong(fileUploadMaxSize));
        multipartResolver.setMaxInMemorySize(1024*1024*100);
        try {
            multipartResolver.setUploadTempDir(new FileSystemResource(fileUploadTempPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        String[] basenames = {"classpath:messages"};

        ReloadableResourceBundleMessageSource resourceBundle = new ReloadableResourceBundleMessageSource();
        resourceBundle.setBasenames(basenames);
        resourceBundle.setDefaultEncoding("UTF-8");
        resourceBundle.setAlwaysUseMessageFormat(true);
        return resourceBundle;
    }

  @Bean
  public LocaleResolver localeResolver(){
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setDefaultLocale(new Locale("en"));
    resolver.setCookieName("myLocaleCookie");
    resolver.setCookieMaxAge(4800);
    return resolver;
  }
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("language");
    registry.addInterceptor(interceptor);
  }


}
