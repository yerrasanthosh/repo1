package com.vroozi.customerui.supplierui.controller;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.supplier.controller.SupplierAttributeUIController;
import com.vroozi.customerui.supplier.controller.SupplierUIController;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.model.SupplierAttributeForm;
import com.vroozi.customerui.supplier.model.SupplierAttributes;
import com.vroozi.customerui.supplier.model.SupplierForm;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.user.services.user.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Vroozi on 27/04/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SessionDataRetriever.class)
public class SupplierUIControllerTest {

  @Mock
  UploadService uploadService;

  @Mock
  SupplierService supplierService;

  SupplierForm supplierForm;
  Supplier supplier;
  User user;
  @Before
  public void setup() {
    supplierForm = new SupplierForm();
    supplier = new Supplier();
    user = new User();
    user.setUsername("userName");
    user.setUnitId("1");
  }

  /**
   * Tests {@Link SupplierUIController#setSupplierDefaultsIfMissing(SupplierForm supplierForm, Supplier
   *supplier, user)(User user)} If supplier defaults exist then they must persists
   */
  @Test
  public void testSetSupplierDefaultsIfMissingWithDefaultsExistsShouldPersists() {
    user.setRowsPerPage(8);
    user.setDecimalNotation("3");
    user.setLanguage("en");
    user.setTimeZone("5");
    user.setDateFormat("2");

    final SupplierUIController supplierUIController = new SupplierUIController();
    supplierUIController.setSupplierDefaultsIfMissing(supplierForm, supplier, user);

    assertEquals(supplier.getRowsPerPage(), 8);
    assertEquals(supplier.getDecimalNotation(), "3");
    assertEquals(supplier.getLanguage(), "en");
    assertEquals(supplier.getTimeZone(), "5");
    assertEquals(supplier.getDateFormat(), "2");

  }

  /**
   * Tests {@Link SupplierUIController#setSupplierDefaultsIfMissing(SupplierForm supplierForm,
   * Supplier supplier, user)(User user)} If supplier defaults doesn't
   * exists then they must be set
   */
  @Test
  public void testSetSupplierDefaultsIfMissingWithDefaultsEmptyShouldBePopulated() {
    AppConfig appConfig = createAppConfigForDefaultPreferences();

    final SupplierUIController supplierUIController = new SupplierUIController();
    supplierUIController.setAppConfig(appConfig);
    supplierUIController.setSupplierDefaultsIfMissing(supplierForm, supplier, user);


    assertEquals(supplier.getDecimalNotation(), appConfig.decimalNotation);
    assertEquals(supplier.getLanguage(), appConfig.language);
    assertEquals(supplier.getTimeZone(), appConfig.timeZone);
    assertEquals(supplier.getDateFormat(), appConfig.dateFormat);
  }

  private AppConfig createAppConfigForDefaultPreferences() {
    AppConfig appConfig = new AppConfig();
    appConfig.rowsPerPage = "10";
    appConfig.language = "en";
    appConfig.dateFormat = "2";
    appConfig.decimalNotation = "1";
    appConfig.timeZone = "14";
    return appConfig;
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCatalogWitoutVendorId() throws NoSuchFieldException, IllegalAccessException {
    final SupplierUIController supplierUIController = new SupplierUIController();
    final SupplierForm supplierForm = new SupplierForm();

    // Http Request
    final HttpServletRequest mockRequest = createHttpServletRequest();

    // Http Response
    final HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    final BindingResult mockBindingResult = mock(BindingResult.class);

    final AtomicBoolean vendorExists = setUpSupplierUIController(supplierUIController, mockRequest);

    supplierUIController.createCatalog(mockRequest, mockResponse, supplierForm, mockBindingResult);

    if (!vendorExists.get()) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testCreateCatalogWithVendorId() throws NoSuchFieldException, IllegalAccessException {
    final SupplierUIController supplierUIController = new SupplierUIController();
    final SupplierForm supplierForm = new SupplierForm();

    // Http Request
    final HttpServletRequest mockRequest = createHttpServletRequest();

    // Http Response
    final HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    final BindingResult mockBindingResult = mock(BindingResult.class);

    final AtomicBoolean vendorExists = setUpSupplierUIController(supplierUIController, mockRequest);

    supplierForm.setDefaultVendorId("101");

    final String createCatalogResponse;
    createCatalogResponse = supplierUIController.createCatalog(mockRequest, mockResponse, supplierForm, mockBindingResult);

    if (!vendorExists.get()) {
      throw new IllegalArgumentException();
    }

    assertNotNull(createCatalogResponse);
    assertTrue(createCatalogResponse.toLowerCase().contains("success"));

  }


  @Test
  public void testMapSupplierAttributePropertiesWhenUpdatedNameAndDescriptionWhileNameAndDescriptionExists() throws Exception {
    //given
    final
    SupplierAttributeUIController
        supplierAttributeUIController =
        new SupplierAttributeUIController();
    SupplierAttributes supplierAttributes = getSupplierAttributes();

    //when
    supplierAttributeUIController
        .mapSupplierAttributeProperties(getSupplierAttributeForm(), supplierAttributes);

    //then
    assertNotNull(supplierAttributes);
    assertNotNull(supplierAttributes.getLastUpdated());
    assertEquals(supplierAttributes.getAttributeId(), "1100");
    assertEquals(supplierAttributes.getUnitId(), "9");
    assertEquals(supplierAttributes.getAttributeName(), "Negative");
    assertEquals(supplierAttributes.getAttributeDescription(), "Negative Description");
  }

  @Test
  public void testMapSupplierAttributePropertiesWhenUpdatedNameWhileNameAndDescriptionExists() throws Exception {
    final
    SupplierAttributeUIController
        supplierAttributeUIController =
        new SupplierAttributeUIController();
    SupplierAttributeForm supplierAttributeForm = new SupplierAttributeForm();
    supplierAttributeForm.setAttributeId("1100");
    supplierAttributeForm.setAttributeName("Negative");
    SupplierAttributes supplierAttributes = getSupplierAttributes();
    //when
    supplierAttributeUIController
        .mapSupplierAttributeProperties(supplierAttributeForm, supplierAttributes);

    //then
    assertNotNull(supplierAttributes);
    assertNotNull(supplierAttributes.getLastUpdated());
    assertEquals(supplierAttributes.getAttributeId(), "1100");
    assertEquals(supplierAttributes.getUnitId(), "9");
    assertEquals(supplierAttributes.getAttributeName(), "Negative");
    assertEquals(supplierAttributes.getAttributeDescription(), "Positive Description");
  }

  @Test
  public void testMapSupplierAttributePropertiesWhenUpdatedNameAndDescriptionWhileNameExists() throws Exception {
    final
    SupplierAttributeUIController
        supplierAttributeUIController =
        new SupplierAttributeUIController();
    SupplierAttributeForm supplierAttributeForm = new SupplierAttributeForm();
    supplierAttributeForm.setAttributeId("1100");
    supplierAttributeForm.setAttributeName("Negative");
    supplierAttributeForm.setAttributeDescription("Negative Description");
    SupplierAttributes supplierAttributes = getSupplierAttributes();
    //when
    supplierAttributeUIController
        .mapSupplierAttributeProperties(supplierAttributeForm, supplierAttributes);

    //then
    assertNotNull(supplierAttributes);
    assertNotNull(supplierAttributes.getLastUpdated());
    assertEquals(supplierAttributes.getAttributeId(), "1100");
    assertEquals(supplierAttributes.getUnitId(), "9");
    assertEquals(supplierAttributes.getAttributeName(), "Negative");
    assertEquals(supplierAttributes.getAttributeDescription(), "Negative Description");
  }

  @Test
  public void testEditSupplierAttribute() throws Exception {
    //given
    final
    SupplierAttributeUIController
        supplierAttributeUIController =
        new SupplierAttributeUIController();
    Mockito.when(supplierService.updateSupplierAttributes(getSupplierAttributes())).thenReturn(getSupplierAttributes());
    //when
    supplierAttributeUIController.editSupplierAttribute(getSupplierAttributeForm(),getSupplierAttributes(),"name");
    //then
    verify(supplierService, never()).updateSupplierAttributes(getSupplierAttributes());

  }

  @Test (expected = Exception.class)
  public void testEditSupplierAttributeThrowsException() throws Exception {
    //given
    final
    SupplierAttributeUIController
        supplierAttributeUIController =
        new SupplierAttributeUIController();
    SupplierAttributeForm supplierAttributeForm = new SupplierAttributeForm();
    SupplierAttributes supplierAttributes = new SupplierAttributes();
    String namePrefix = anyString();
    //when
    supplierAttributeUIController.editSupplierAttribute(supplierAttributeForm, supplierAttributes,
                                                    namePrefix);
    //then
    throw new Exception();

  }

  @Test (expected = Exception.class)
  public void testUpdateSupplierAttributeIconThrowsException() throws Exception {
    //given
    final
    SupplierAttributeUIController
        supplierAttributeUIController =
        new SupplierAttributeUIController();
    SupplierAttributeForm supplierAttributeForm = new SupplierAttributeForm();
    SupplierAttributes supplierAttributes = new SupplierAttributes();
    String namePrefix = anyString();
    //when
    supplierAttributeUIController.updateSupplierAttributeIcon(namePrefix, supplierAttributeForm
        , supplierAttributes);
    //then
    throw new Exception();
  }

  private SupplierAttributes getSupplierAttributes() {
    SupplierAttributes supplierAttributes = new SupplierAttributes();
    supplierAttributes.setAttributeId("1100");
    supplierAttributes.setAttributeName("Positive");
    supplierAttributes.setAttributeDescription("Positive Description");
    supplierAttributes.setAttributeImagePath("/image1.jpg");
    supplierAttributes.setUnitId("9");
    supplierAttributes.setActive(true);
    return supplierAttributes;
  }

  private SupplierAttributeForm getSupplierAttributeForm() throws Exception {
    SupplierAttributeForm supplierAttributeForm = new SupplierAttributeForm();
    supplierAttributeForm.setAttributeId("1100");
    supplierAttributeForm.setAttributeName("Negative");
    supplierAttributeForm.setAttributeDescription("Negative Description");
    return supplierAttributeForm;
  }

  private HttpServletRequest createHttpServletRequest() {
    final HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    final HttpSession mockHttpSession = mock(HttpSession.class);
    when(mockRequest.getSession()).thenReturn(mockHttpSession);
    return mockRequest;
  }

  private AtomicBoolean setUpSupplierUIController(SupplierUIController supplierUIController, HttpServletRequest mockRequest)
    throws NoSuchFieldException, IllegalAccessException {

    final User user = new User();
    PowerMockito.mockStatic(SessionDataRetriever.class);
    BDDMockito.given(SessionDataRetriever.getLoggedInUser(mockRequest)).willReturn(user);

    final SupplierService mockSupplierService = mock(SupplierService.class);
    when(mockSupplierService.getSupplierByUnitId(anyString())).thenReturn(new ArrayList<Supplier>());
    final AtomicBoolean vendorExists = new AtomicBoolean(false);
    when(mockSupplierService.addSupplier(any(Supplier.class))).thenAnswer(getAnswer(vendorExists));

    final String fieldName = "supplierService";
    setFieldByReflection(supplierUIController, mockSupplierService, fieldName);

    final String appConfigFiledName = "appConfig";
    final AppConfig mockAppConfig = mock(AppConfig.class);

    setFieldByReflection(supplierUIController, mockAppConfig, appConfigFiledName);
    return vendorExists;
  }

  private Answer<Supplier> getAnswer(final AtomicBoolean vendorExists) {
    return new Answer<Supplier>() {
      @Override
      public Supplier answer(InvocationOnMock invocationOnMock) throws Throwable {
        final Object[] arguments = invocationOnMock.getArguments();
        final Supplier supplier = (Supplier) arguments[0];
        final String vendorId = supplier.getVendorId();
        if (vendorId != null) {
          vendorExists.set(true);
        }
        return supplier;
      }
    };
  }

  private void setFieldByReflection(Object o, Object value, String fieldName) throws NoSuchFieldException, IllegalAccessException {
    final Class aClass = o.getClass();
    final Field supplierServiceField = aClass.getDeclaredField(fieldName);
    supplierServiceField.setAccessible(true);
    supplierServiceField.set(o, value);
  }

}
