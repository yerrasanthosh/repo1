package com.vroozi.customerui.profile;

import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.profile.controller.ProfileGroupUIController;
import com.vroozi.customerui.profile.model.Profile;
import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.profile.services.ProfileGroupService;
import com.vroozi.customerui.profile.services.ProfileService;
import com.vroozi.customerui.user.services.user.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.mockito.Matchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.vroozi.customerui.util.Consts.INVALID_SESSION_PAGE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by sajjidak on 1/28/16.request
 */

@PrepareForTest(SessionDataRetriever.class)
@RunWith(PowerMockRunner.class)
public class ProfileGroupUIControllerTest {


    @Mock
    private ProfileService profileService;
    @Mock
    private ProfileGroupService profileGroupService;
    @Mock
    HttpServletRequest request;
    @Mock
    BindingResult bindingResult;
    @Mock
    HttpSession httpSession;
    @InjectMocks
    ProfileGroupUIController profileGroupUIController;


    @Test
    public void testSaveProfileGroupWithOutUserSessionShouldReturnToInvalidSessionPage() {

        //Given
        ProfileGroup profileGroup = new ProfileGroup();
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(null);

        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        Assert.assertEquals(result, INVALID_SESSION_PAGE);

    }

    @Test
    public void testSaveProfileGroupWithProfileGroupAsNullShouldNotAddOrUpdateModelMapObject() throws Exception {

        //Given
        String unitId = "1024";
        ProfileGroup profileGroup = null;
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        User user = new User();
        user.setUserId("101");
        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        verify(profileService, never()).getActiveProfilesByUnitId(unitId);
        verify(profileGroupService, never()).addProfileGroup(any(ProfileGroup.class));
        verify(profileGroupService, never()).getProfileGroupsById(any(String.class));
        verify(profileGroupService, never()).updateProfileGroup(any(ProfileGroup.class));

        Assert.assertEquals(result, "forward:/profiles");
        Assert.assertNull(modelMap.get("associatedProfileCount"));
        Assert.assertNull(modelMap.get("NotAssociatedProfileCount"));
    }

    @Test
    public void testSaveProfileGroupWithProfileGroupObjectAngGroupIdIsZeroAndAssociatedProfilesInSessionNullShouldNotAddOrUpdateProfileGroup() throws Exception {

        //Given
        String unitId = "1024";
        ProfileGroup profileGroup = new ProfileGroup();
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        User user = new User();
        user.setUserId("101");
        user.setUsername("test");
        user.setUnitId(unitId);
        user.setFullName("test Vroozi");
        profileGroup.setGroupId(0);

        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        verify(profileService, never()).getActiveProfilesByUnitId(unitId);
        verify(profileGroupService, never()).addProfileGroup(any(ProfileGroup.class));
        verify(profileGroupService, never()).getProfileGroupsById(any(String.class));
        verify(profileGroupService, never()).updateProfileGroup(any(ProfileGroup.class));

        Assert.assertEquals(result, "forward:/profiles");
        Assert.assertNull(modelMap.get("associatedProfileCount"));
        Assert.assertNull(modelMap.get("NotAssociatedProfileCount"));
    }

    @Test
    public void testSaveProfileGroupWithProfileGroupObjectAngGroupIdIsZeroAndAssociatedProfilesInSessionNotNullButActiveStrIsNullShouldNotAddProfileGroup() throws Exception {

        //Given
        String unitId = "1024";
        ProfileGroup profileGroup = new ProfileGroup();
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        User user = new User();
        user.setUserId("101");
        user.setUsername("test");
        user.setUnitId(unitId);
        user.setFullName("test Vroozi");
        profileGroup.setGroupId(0);
        Profile profile = new Profile();
        profile.setUnitId(unitId);
        profile.setProfileId(12121);
        List<Profile> list = new ArrayList<>(1);
        list.add(profile);
        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

        given(profileService.getActiveProfilesByUnitId(unitId)).willReturn(list);
        given(httpSession.getAttribute("associatedProfiles")).willReturn(list);
        given(request.getSession()).willReturn(httpSession);
        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        verify(profileService, never()).getActiveProfilesByUnitId(unitId);
        verify(profileGroupService, never()).addProfileGroup(any(ProfileGroup.class));
        verify(profileGroupService, never()).getProfileGroupsById(any(String.class));
        verify(profileGroupService, never()).updateProfileGroup(any(ProfileGroup.class));

        Assert.assertEquals(result, "forward:/profiles");
        Assert.assertNull(modelMap.get("associatedProfileCount"));
        Assert.assertNull(modelMap.get("notAssociatedProfileCount"));
    }
    @Test
    public void testSaveProfileGroupWithProfileGroupObjectAngGroupIdIsZeroAndAssociatedProfilesInSessionNotNullShouldAddProfileGroup() throws Exception {

        //Given
        String unitId = "1024";
        ProfileGroup profileGroup = new ProfileGroup();
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        User user = new User();
        user.setUserId("101");
        user.setUsername("test");
        user.setUnitId(unitId);
        user.setFullName("test Vroozi");
        profileGroup.setGroupId(0);
        Profile profile = new Profile();
        profile.setUnitId(unitId);
        profile.setProfileId(12121);
        List<Profile> list = new ArrayList<>(1);
        list.add(profile);
        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

        given(profileService.getActiveProfilesByUnitId(unitId)).willReturn(list);
        given(httpSession.getAttribute("associatedProfiles")).willReturn(list);
        given(request.getSession()).willReturn(httpSession);
        given(request.getParameter("activeStr")).willReturn("yes");
        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        verify(profileGroupService).addProfileGroup(any(ProfileGroup.class));
        verify(profileGroupService, never()).getProfileGroupsById(any(String.class));
        verify(profileGroupService, never()).updateProfileGroup(any(ProfileGroup.class));

        Assert.assertEquals(result, "forward:/profiles");
        Assert.assertEquals(profileGroup.getCreatedBy(), user.getUsername());
        Assert.assertEquals(modelMap.get("associatedProfileCount"), 0);
        Assert.assertEquals(modelMap.get("notAssociatedProfileCount"), 1);

    }

    @Test
    public void testSaveProfileGroupWithProfileGroupObjectAngGroupIdIsZeroAndAssociatedProfilesInSessionNotNullShouldAddProfileGroupButnotAssociatedProfileCountIsNull() throws Exception {

        //Given
        String unitId = "1024";
        ProfileGroup profileGroup = new ProfileGroup();
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        User user = new User();
        user.setUserId("101");
        user.setUsername("test");
        user.setUnitId(unitId);
        user.setFullName("test Vroozi");
        profileGroup.setGroupId(0);
        Profile profile = new Profile();
        profile.setUnitId(unitId);
        profile.setProfileId(12121);
        List<Profile> list = new ArrayList<>(1);
        list.add(profile);
        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

        given(profileService.getActiveProfilesByUnitId(unitId)).willReturn(null);
        given(httpSession.getAttribute("associatedProfiles")).willReturn(list);
        given(request.getSession()).willReturn(httpSession);
        given(request.getParameter("activeStr")).willReturn("yes");

        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        verify(profileGroupService).addProfileGroup(any(ProfileGroup.class));
        verify(profileGroupService, never()).getProfileGroupsById(any(String.class));
        verify(profileGroupService, never()).updateProfileGroup(any(ProfileGroup.class));

        Assert.assertEquals(result, "forward:/profiles");
        Assert.assertEquals(profileGroup.getCreatedBy(), user.getUsername());
        Assert.assertNull(modelMap.get("notAssociatedProfileCount"));


    }

    @Test
    public void testSaveProfileGroupWithProfileGroupObjectAngGroupIdIsGreaterThanZeroAndAssociatedProfilesInSessionNotNullShouldupdateProfileGroupButnotAssociatedProfileCountIsNull() throws Exception {

        //Given
        String unitId = "1024";
        ProfileGroup profileGroup = new ProfileGroup();
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        User user = new User();
        user.setUserId("101");
        user.setUsername("test");
        user.setUnitId(unitId);
        user.setFullName("test Vroozi");
        profileGroup.setGroupId(1);
        Profile profile = new Profile();
        profile.setUnitId(unitId);
        profile.setProfileId(12121);
        List<Profile> list = new ArrayList<>(1);
        list.add(profile);
        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

        given(profileService.getActiveProfilesByUnitId(unitId)).willReturn(null);
        given(httpSession.getAttribute("associatedProfiles")).willReturn(list);
        given(request.getSession()).willReturn(httpSession);
        given(request.getParameter("activeStr")).willReturn("yes");
        given(profileGroupService.getProfileGroupsById(any(String.class))).willReturn(profileGroup);
        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        verify(profileGroupService).updateProfileGroup(any(ProfileGroup.class));
        verify(profileGroupService, never()).addProfileGroup(any(ProfileGroup.class));

        Assert.assertEquals(result, "forward:/profiles");
        Assert.assertNull(modelMap.get("notAssociatedProfileCount"));
    }

    @Test
    public void testSaveProfileGroupWithProfileGroupObjectAngGroupIdIsGreaterThanZeroAndAssociatedProfilesInSessionNotNullShouldupdateProfileGroup() throws Exception {

        //Given
        String unitId = "1024";
        ProfileGroup profileGroup = new ProfileGroup();
        ModelMap modelMap = new ModelMap();
        PowerMockito.mockStatic(SessionDataRetriever.class);
        User user = new User();
        user.setUserId("101");
        user.setUsername("test");
        user.setUnitId(unitId);
        user.setFullName("test Vroozi");
        profileGroup.setGroupId(1);
        Profile profile = new Profile();
        profile.setUnitId(unitId);
        profile.setProfileId(12121);
        List<Profile> list = new ArrayList<>(1);
        list.add(profile);
        Mockito.when(SessionDataRetriever.getLoggedInUser(request)).thenReturn(user);

        given(profileService.getActiveProfilesByUnitId(unitId)).willReturn(list);
        given(httpSession.getAttribute("associatedProfiles")).willReturn(list);
        given(request.getSession()).willReturn(httpSession);
        given(request.getParameter("activeStr")).willReturn("yes");
        given(profileGroupService.getProfileGroupsById(any(String.class))).willReturn(profileGroup);
        //When
        String result = profileGroupUIController.saveProfileGroup(request, profileGroup, bindingResult, modelMap);

        //Then
        verify(profileGroupService).updateProfileGroup(any(ProfileGroup.class));
        verify(profileGroupService, never()).addProfileGroup(any(ProfileGroup.class));

        Assert.assertEquals(result, "forward:/profiles");
        Assert.assertEquals(modelMap.get("notAssociatedProfileCount"), 1);
    }

}
