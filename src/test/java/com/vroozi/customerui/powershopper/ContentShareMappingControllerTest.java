package com.vroozi.customerui.powershopper;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.catalog.model.MaterialGroupForm;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.query.Pagination;
import com.vroozi.customerui.query.Result;
import com.vroozi.customerui.user.services.user.model.User;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ModelMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.anyBoolean;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.atLeast;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@PrepareForTest({SessionDataRetriever.class, ContentShareMappingController.class, BufferedInputStream.class, IOUtils.class})
@RunWith(PowerMockRunner.class)
public class ContentShareMappingControllerTest {

  @Mock
  ContentShareMappingService contentShareMappingService;
  @Mock
  ProfileGroupService profileGroupService;
  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;
  @Mock
  IOUtils ioUtils;
  @Mock
  BufferedInputStream bufferedInputStream;
  @InjectMocks
  private static ContentShareMappingController contentShareMappingController;


  @Test
  public void testOfNavigateToPowerShopperWithModelMapAndRequestObjectModelMapShouldNotBeNull() throws Exception {

    List<ContentShareMapping> list = new ArrayList<ContentShareMapping>();
    List<ProfileGroup> listOfProfileGroup = new ArrayList<>();
    User user = new User();
    ContentShareMapping contentShareMapping = new ContentShareMapping();
    Pagination pagination = new Pagination();
    ProfileGroup profileGroup = new ProfileGroup();
    ModelMap modelMap = new ModelMap();
    Result<ContentShareMapping> contentShareMappings = new Result<ContentShareMapping>();

    // mock behaviour
    user.setUnitId("1024");
    contentShareMapping.setUsername("testCase");
    pagination.setCurrentPage(1);
    pagination.setItemCount(10);
    pagination.setPageCount(1);
    pagination.setPageSize(25);

    list.add(contentShareMapping);
    contentShareMappings.setDataItems(list);
    contentShareMappings.setPagination(pagination);
    profileGroup.setUnitId("1024");
    listOfProfileGroup.add(0, profileGroup);

    PowerMockito.mockStatic(SessionDataRetriever.class);
    // mock
    Mockito.when(contentShareMappingService.getContentShareMappings("1024", 1, 8, "", null)).thenReturn(
        contentShareMappings);
    Mockito.when(profileGroupService.getActiveGroupsByUnitId(user.getUnitId())).thenReturn(
        listOfProfileGroup);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

    // asserts
    String pageName = contentShareMappingController.navigateToContentShareMappingPage(modelMap, request, 1, "");
    Assert.assertEquals("power_shopper_table_fragment", pageName);
    List<ContentShareMapping> returnedMappings =
        (List<ContentShareMapping>) modelMap.get("powerShoppers");
    Assert.assertNotNull(returnedMappings);
  }

  @Test
  public void testOfDeletePowerShopperWithNullShouldReturnFailed() {
    User user = new User();
    user.setUnitId("1024");

    // mock
    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

    // Assert
    Mockito.when(contentShareMappingService.deleteContentShareMapping(null)).thenReturn(0);
    String result = contentShareMappingController.deleteContentShareMapping(request, null);
    Assert.assertEquals(result, "Failed to Delete");
  }

  @Test
  public void testOfNavigateToPowerShopperWithUserNotLoggedInShouldRedirectToInvalidSessionPage()
      throws Exception {

    List<ContentShareMapping> list = new ArrayList<ContentShareMapping>();
    List<ProfileGroup> listOfProfileGroup = new ArrayList<>();
    ContentShareMapping contentShareMapping = new ContentShareMapping();
    Pagination pagination = new Pagination();
    ProfileGroup profileGroup = new ProfileGroup();
    ModelMap modelMap = new ModelMap();
    Result<ContentShareMapping> contentShareMappings = new Result<ContentShareMapping>();

    // mock behaviour

    contentShareMapping.setUsername("testCase");
    pagination.setCurrentPage(1);
    pagination.setItemCount(10);
    pagination.setPageCount(1);
    pagination.setPageSize(25);

    list.add(contentShareMapping);
    contentShareMappings.setDataItems(list);
    contentShareMappings.setPagination(pagination);
    profileGroup.setUnitId("1024");
    listOfProfileGroup.add(0, profileGroup);

    PowerMockito.mockStatic(SessionDataRetriever.class);
    // mock
    Mockito.when(contentShareMappingService.getContentShareMappings("1024", 1, 8, "", null)).thenReturn(
        contentShareMappings);
    Mockito.when(profileGroupService.getActiveGroupsByUnitId("1024"))
        .thenReturn(listOfProfileGroup);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(null);

    // asserts
    String pageName = contentShareMappingController.navigateToContentShareMappingPage(modelMap, request, 1, "");
    Assert.assertEquals("invalid_session", pageName);
    List<ContentShareMapping> returnedMappings =
        (List<ContentShareMapping>) modelMap.get("powerShoppers");
    Assert.assertNull(returnedMappings);
  }

  @Test
  public void testOfNavigateToPowerShopperWithUserAndActiveGroupsAsNullShouldReturnNull()
      throws Exception {

    List<ContentShareMapping> list = new ArrayList<ContentShareMapping>();
    ContentShareMapping contentShareMapping = new ContentShareMapping();
    Pagination pagination = new Pagination();
    ModelMap modelMap = new ModelMap();
    Result<ContentShareMapping> contentShareMappings = new Result<ContentShareMapping>();

    // mock behaviour
    contentShareMapping.setUsername("testCase");
    pagination.setCurrentPage(1);
    pagination.setItemCount(10);
    pagination.setPageCount(1);
    pagination.setPageSize(25);

    list.add(contentShareMapping);
    contentShareMappings.setDataItems(list);
    contentShareMappings.setPagination(pagination);

    PowerMockito.mockStatic(SessionDataRetriever.class);
    // mock
    Mockito.when(contentShareMappingService.getContentShareMappings("1024", 1, 8, "", null)).thenReturn(
        contentShareMappings);
    Mockito.when(profileGroupService.getActiveGroupsByUnitId("1024")).thenReturn(null);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(null);

    // asserts
    String pageName = contentShareMappingController.navigateToContentShareMappingPage(modelMap, request, 1, "");
    Assert.assertEquals("invalid_session", pageName);
    List<ContentShareMapping> returnedMappings =
        (List<ContentShareMapping>) modelMap.get("profileGroupList");
    Assert.assertNull(returnedMappings);
  }

  @Test
  public void testOfAddPowerShopperWithUserNotAlreadyExistsShouldReturnSuccess() throws Exception {

    User user = new User();
    user.setUnitId("1024");
    ContentShareMapping csm = new ContentShareMapping();
    csm.setUsername("testCase");

    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);
    Mockito.when(contentShareMappingService.getContentShareMappingByUsername("testCase"))
        .thenReturn(null);
    Mockito.when(contentShareMappingService.createContentShareMapping(csm)).thenReturn(csm);
    Mockito.when(contentShareMappingService.getUserByUserName("1024", "testName")).thenReturn(null);

    String result = contentShareMappingController.addContentShareMapping(request, csm);
    Assert.assertEquals(result, "success");
  }

  @Test
  public void testOfAddPowerShopperWithUserAlreadyExistsShouldReturnUserExists() throws Exception {


    User user = new User();
    user.setUnitId("1024");
    ContentShareMapping csm = new ContentShareMapping();
    csm.setUsername("testCase");

    // Mock
    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);
    Mockito.when(contentShareMappingService.getContentShareMappingByUsername("testCase"))
        .thenReturn(csm);
    Mockito.when(contentShareMappingService.createContentShareMapping(csm)).thenReturn(csm);


    // Asserts
    String result = contentShareMappingController.addContentShareMapping(request, csm);
    Assert.assertEquals(result, "mappingExists");
  }

  @Test
  public void testOfAddPowerShopperWhenNoValidSessionShouldReturnToInvalidSessionPage() throws Exception {


    User user = new User();
    user.setUnitId("1024");
    ContentShareMapping csm = new ContentShareMapping();
    csm.setUsername("testCase");

    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(null);
    Mockito.when(contentShareMappingService.getContentShareMappingByUsername("testCase"))
        .thenReturn(csm);
    Mockito.when(contentShareMappingService.createContentShareMapping(csm)).thenReturn(csm);
    String result = contentShareMappingController.addContentShareMapping(request, csm);

    Assert.assertEquals(result, "invalid_session");
  }

  @Test
  public void testOfUpdateUserRoleWithUserHasPowerRoleShouldUpdateRoleWithPowerRoleAndReturnTrue() throws Exception {
    User user = new User();
    List<Role> roles = new ArrayList<>();
    roles.add(Role.SHOPPER);
    user.setRoles(roles);
    boolean result = contentShareMappingController.updateUserRole(user);
    Assert.assertEquals(result, true);
  }

  @Test
  public void testOfUpdateUserRoleWithUserHasNoRoleShouldNotUpdateAnythingAndReturnFalse() throws Exception {
    User user = new User();
    List<Role> roles = new ArrayList<>();
    user.setRoles(roles);
    boolean result = contentShareMappingController.updateUserRole(user);
    Assert.assertEquals(result, false);
  }

  @Test
  public void getOfFindAndUpdateUserRoleWithUserHasRoleButNotShopperRoleShouldNotUpdateAnything()
      throws Exception {
    User user = new User();
    List<Role> roles = new ArrayList<>();
    roles.add(Role.MASTER_ADMINISTRATOR);
    user.setRoles(roles);
    boolean result = contentShareMappingController.updateUserRole(user);
    Assert.assertEquals(result, false);
  }

  @Test
  public void testOfDeletePowerShopperWithListOfIdsShouldReturnsuccess() {
    User user = new User();
    user.setUnitId("1024");
    List<String> ids = new ArrayList<>();
    ids.add("1111");

    // mock
    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);
    Mockito.when(contentShareMappingService.deleteContentShareMapping(ids)).thenReturn(1);

    // Assert
    String result = contentShareMappingController.deleteContentShareMapping(request, ids);
    Assert.assertEquals(result, "content share mapping deleted successfully");
  }

  @Test
  public void testOfDeletePowerShopperWithEmptyReturnFailed() {
    User user = new User();
    user.setUnitId("1024");
    List<String> ids = new ArrayList<>();

    // mock
    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

    // Assert
    Mockito.when(contentShareMappingService.deleteContentShareMapping(ids)).thenReturn(1);
    String result = contentShareMappingController.deleteContentShareMapping(request, ids);
    Assert.assertEquals(result, "Failed to Delete");
  }

  @Test
  public void testOfUpdatePowerShopperWithCurrentUserNameIsEqualToNewUserNameShouldUpdate() {

    List<ContentShareMapping> list = new ArrayList<ContentShareMapping>();
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setUnitId("1024");

    ContentShareMapping contentShareMapping = new ContentShareMapping();
    ContentShareMapping contentShareMapping2 = new ContentShareMapping();
    Pagination pagination = new Pagination();
    ProfileGroup profileGroup = new ProfileGroup();
    Result<ContentShareMapping> contentShareMappings = new Result<ContentShareMapping>();

    // mock behaviour
    contentShareMapping.setUsername("testCase");
    contentShareMapping.setId("1111");
    contentShareMapping2.setUsername("testCase");
    contentShareMapping2.setId("1111");
    pagination.setCurrentPage(1);
    pagination.setItemCount(10);
    pagination.setPageCount(1);
    pagination.setPageSize(25);

    list.add(contentShareMapping);
    contentShareMappings.setDataItems(list);
    contentShareMappings.setPagination(pagination);
    profileGroup.setUnitId("1024");

    // mock
    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);
    Mockito.when(contentShareMappingService.getContentShareMappings("1024", null, null, null, "testCase"))
        .thenReturn(contentShareMappings);
    Mockito.when(contentShareMappingService.updateContentShareMapping("1111", contentShareMapping)).thenReturn(1);
    Mockito.when(contentShareMappingService.getUserByUserName(user.getUnitId(),
                                                              contentShareMapping.getUsername())).thenReturn(users);

    //Assert
    String result = contentShareMappingController.updateContentShareMapping(request, "1111", contentShareMapping);
    Assert.assertEquals(result, "updated");

  }

  @Test
  public void testOfUpdatePowerShopperWithUserNameDoesNotExistsInDbShouldUpdate() {

    List<ContentShareMapping> list = new ArrayList<ContentShareMapping>();
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setUnitId("1024");

    ContentShareMapping contentShareMapping = new ContentShareMapping();
    ContentShareMapping contentShareMapping2 = new ContentShareMapping();
    Pagination pagination = new Pagination();
    ProfileGroup profileGroup = new ProfileGroup();
    Result<ContentShareMapping> contentShareMappings = new Result<ContentShareMapping>();

    // mock behaviour
    contentShareMapping.setUsername("testCase");
    contentShareMapping.setId("1111");
    contentShareMappings.setPagination(pagination);
    profileGroup.setUnitId("1024");

    contentShareMapping2.setUsername("testCase");
    contentShareMapping2.setId("1111");

    // mock
    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);
    Mockito.when(contentShareMappingService.getContentShareMappings("1024", null, null, null, "testCase"))
        .thenReturn(contentShareMappings);
    Mockito.when(contentShareMappingService.updateContentShareMapping("1111", contentShareMapping)).thenReturn(1);
    Mockito.when(contentShareMappingService.getUserByUserName(user.getUnitId(),
                                                              contentShareMapping.getUsername())).thenReturn(users);

    //Assert
    String result = contentShareMappingController.updateContentShareMapping(request, "1111", contentShareMapping);
    Assert.assertEquals(result, "updated");

  }

  @Test
  public void testOfUpdatePowerShopperWithUserNameThatExistsShouldNotUpdate() {

    List<ContentShareMapping> list = new ArrayList<ContentShareMapping>();
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setUnitId("1024");

    ContentShareMapping contentShareMapping = new ContentShareMapping();
    ContentShareMapping contentShareMapping2 = new ContentShareMapping();
    Pagination pagination = new Pagination();
    ProfileGroup profileGroup = new ProfileGroup();
    Result<ContentShareMapping> contentShareMappings = new Result<ContentShareMapping>();

    // mock behaviour
    contentShareMapping.setUsername("testCase");
    contentShareMapping.setId("1111");

    contentShareMapping2.setUsername("testCase");
    contentShareMapping2.setId("561");

    pagination.setCurrentPage(1);
    pagination.setItemCount(10);
    pagination.setPageCount(1);
    pagination.setPageSize(25);

    list.add(contentShareMapping2);
    contentShareMappings.setDataItems(list);
    contentShareMappings.setPagination(pagination);
    profileGroup.setUnitId("1024");

    // mock
    PowerMockito.mockStatic(SessionDataRetriever.class);
    Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);
    Mockito.when(contentShareMappingService.getContentShareMappings("1024", null, null, null, "testCase"))
        .thenReturn(contentShareMappings);
    Mockito.when(contentShareMappingService.updateContentShareMapping("1111", contentShareMapping)).thenReturn(1);
    Mockito.when(contentShareMappingService.getUserByUserName(user.getUnitId(),
                                                              contentShareMapping.getUsername())).thenReturn(users);

    //Assert
    String result = contentShareMappingController.updateContentShareMapping(request, "1111", contentShareMapping);
    Assert.assertEquals(result, "mappingExists");

  }

  @Test
  public void testOfUploadMappingWithUserNotLoggedInShouldReturnToInvalidSessionPage() {

    //Given
    ModelMap map = new ModelMap();
    PowerMockito.mockStatic(SessionDataRetriever.class);
    given(SessionDataRetriever.getLoggedInUser(any(HttpServletRequest.class))).willReturn(null);

    //When
    String result = contentShareMappingController.uploadMapping(request, null, map);

    //Then
    Assert.assertEquals(result, "invalid_session");

  }

  @Test
  public void testOfUploadMappingWithMappingFormNullShouldReturnMessageSayingUploadFailed() {

    //Given
    User user = new User() {{
      setUnitId("1024");
      setUsername("test");
      setUserId("001");
    }};
    ModelMap map = new ModelMap();
    PowerMockito.mockStatic(SessionDataRetriever.class);
    given(SessionDataRetriever.getLoggedInUser(any(HttpServletRequest.class))).willReturn(user);

    //When
    String result = contentShareMappingController.uploadMapping(request, null, map);

    //Then
    Assert.assertEquals(result, "Failure");
    Assert.assertEquals(map.get("pgMessage").toString(), "Upload failed. null");
  }

  @Test
  public void testOfUploadMappingWithMappingFormButPowerShopperMappingFileIsNullShouldReturnFailed() {

    //Given
    MaterialGroupForm materialGroupForm = new MaterialGroupForm() {{
      setPowerShopperMappingFile(null);
    }};
    User user = new User() {{
      setUnitId("1024");
      setUsername("test");
      setUserId("001");
    }};
    ModelMap map = new ModelMap();
    PowerMockito.mockStatic(SessionDataRetriever.class);
    given(SessionDataRetriever.getLoggedInUser(any(HttpServletRequest.class))).willReturn(user);

    //When
    String result = contentShareMappingController.uploadMapping(request, materialGroupForm, map);

    //Then
    Assert.assertEquals(result, "Failure");
  }

  @Test
  public void testOfUploadMappingWithMappingFormAndPowerShopperMappingFileShouldReturnSuccess() throws Exception {

    MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());

    MaterialGroupForm materialGroupForm = new MaterialGroupForm();
    materialGroupForm.setPowerShopperMappingFile(file);
    User user = new User() {{
      setUnitId("1024");
      setUsername("test");
      setUserId("001");
    }};
    ModelMap map = new ModelMap();
    PowerMockito.mockStatic(SessionDataRetriever.class);
    given(SessionDataRetriever.getLoggedInUser(any(HttpServletRequest.class))).willReturn(user);

    doNothing().when(contentShareMappingService).uploadContentShareMapping(anyString(), anyString(), anyString());

    ContentShareMappingController spied = getSpyContentShareMappingController();
    PowerMockito.doReturn("fakePath").when(spied, "writeToFile", any(Byte[].class), anyString());

    //When
    String result = spied.uploadMapping(request, materialGroupForm, map);

    //Then
    Assert.assertEquals(map.get("pgMessage").toString(), "The unit of measure mapping file has been submitted for processing.");
    Assert.assertEquals(result, "success");
  }

  @Test
  public void testOfUploadMappingWithMappingFormButFilePathNullShouldReturnFailure() throws Exception {

    //Given
    MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
    MaterialGroupForm materialGroupForm = new MaterialGroupForm();
    materialGroupForm.setPowerShopperMappingFile(file);
    User user = new User() {{
      setUnitId("1024");
      setUsername("test");
      setUserId("001");
    }};
    ModelMap map = new ModelMap();
    PowerMockito.mockStatic(SessionDataRetriever.class);
    given(SessionDataRetriever.getLoggedInUser(any(HttpServletRequest.class))).willReturn(user);

    doNothing().when(contentShareMappingService).uploadContentShareMapping(anyString(), anyString(), anyString());

    ContentShareMappingController spied = getSpyContentShareMappingController();

    PowerMockito.doReturn(null).when(spied, "writeToFile", any(Byte[].class), anyString());

    //When
    String result = spied.uploadMapping(request, materialGroupForm, map);

    //Then
    Assert.assertEquals(result, "Failure");
  }

  @Test
  public void testOfDownloadPowerShopperMappingWithValidParamsShouldGenenateFile() throws Exception {

    //Given
    ModelMap map = new ModelMap();
    User user = new User() {{
      setUnitId("1024");
      setUsername("test");
      setUserId("001");
    }};
    PowerMockito.mockStatic(SessionDataRetriever.class);
    given(SessionDataRetriever.getLoggedInUser(any(HttpServletRequest.class))).willReturn(user);
    PowerMockito.mockStatic(IOUtils.class);
    ContentShareMappingController spied = getSpyContentShareMappingController();
    PowerMockito.doReturn(bufferedInputStream).when(spied, "getBufferdInputStream", anyString());

    //When
    spied.downloadContentShareMapping(request, response, map);

    //Then
    verify(contentShareMappingService, times(1)).generateContentShareMappingFile(anyString(), anyString());
    verify(response, times(1)).setContentType(anyString());
  }

  @Test
  public void testOfDownloadPowerShopperMappingWithUserNoInSessionShouldNotGenerateReport() throws Exception {

    //Given
    ModelMap map = new ModelMap();
    User user = new User() {{
      setUnitId("1024");
      setUsername("test");
      setUserId("001");
    }};
    PowerMockito.mockStatic(SessionDataRetriever.class);
    given(SessionDataRetriever.getLoggedInUser(any(HttpServletRequest.class))).willReturn(null);
    PowerMockito.mockStatic(IOUtils.class);
    ContentShareMappingController spied = getSpyContentShareMappingController();
    PowerMockito.doReturn(bufferedInputStream).when(spied, "getBufferdInputStream", anyString());

    //When
    spied.downloadContentShareMapping(request, response, map);

    //Then
    verify(contentShareMappingService, never()).generateContentShareMappingFile(anyString(), anyString());
    verify(response, never()).setContentType(anyString());
  }

  @Test
  public void testOfViewCSMErrorReportWithValidParamsShouldGenerateErrorReport() throws Exception {

    //Given
    ModelMap map = new ModelMap();
    ContentShareMappingController spied = getSpyContentShareMappingController();
    PowerMockito.doReturn(bufferedInputStream).when(spied, "getBufferedInputStreamForErrorReport", anyString(), anyBoolean());
    PowerMockito.mockStatic(IOUtils.class);
    //When
    spied.viewCSMErrorReport(request, response, map);

    //Then
    verify(response, atLeast(1)).setContentType(anyString());
    verify(ioUtils, times(1)).copy(any(BufferedInputStream.class), any(ServletOutputStream.class));

  }

  @Test
  public void testOfViewCSMErrorReportWithExceptionInProcessShouldNotGenerateReport() throws Exception {

    //Given
    ModelMap map = new ModelMap();
    ContentShareMappingController spied = getSpyContentShareMappingController();
    PowerMockito.doThrow(new Exception()).when(spied, "getBufferedInputStreamForErrorReport", anyString(), anyBoolean());
    PowerMockito.mockStatic(IOUtils.class);
    //When
    spied.viewCSMErrorReport(request, response, map);

    //Then
    verify(response, never()).setContentType(anyString());
    verify(ioUtils, never()).copy(any(BufferedInputStream.class), any(ServletOutputStream.class));


  }


  private ContentShareMappingController getSpyContentShareMappingController() {
    ContentShareMappingController spied = PowerMockito.spy(new ContentShareMappingController());
    spied.setContentShareMappingService(contentShareMappingService);
    return spied;
  }
}
