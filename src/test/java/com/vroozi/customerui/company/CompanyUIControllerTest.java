package com.vroozi.customerui.company;

import com.vroozi.customerui.company.controller.CompanyUIController;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.upload.service.UploadService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

/**
 * Created by sajjidak on 4/8/16.
 */
@PrepareForTest({CompanyUIController.class})
@RunWith(PowerMockRunner.class)
public class CompanyUIControllerTest {
  @Mock
  UploadService uploadService;
  @Mock
  AppConfig appConfig;
  @InjectMocks
  CompanyUIController companyUIController;

  @Test
  public void testOfUploadFileToS3WithAllParamsShlouldReturnFileId() throws Exception{

    //Given
    String testPath="testPath";
    String fileId="11111";
    String ext=".jgp";
    CompanySettings companySettings = new CompanySettings();
    companySettings.setId("123");
    Map<String, Object> modelMap = new HashMap<>();

    given(uploadService.uploadFileToS3(anyString())).willReturn(fileId);

    //When
    String result = Whitebox.<String> invokeMethod(companyUIController,"uploadFileToS3",companySettings,
                                   ext,modelMap);

    //then
    Assert.assertEquals(fileId, result);

  }

  @Test
  public void testOfUploadFileToS3WithExtParamMissingShouldReturnEmptyFileId() throws Exception{

    //Given
    String testPath="testPath";
    String fileId="11111";
    String ext=null;
    CompanySettings companySettings = new CompanySettings();
    companySettings.setId("123");
    Map<String, Object> modelMap = new HashMap<>();

    given(uploadService.uploadFileToS3(anyString())).willReturn(fileId);

    //When
    String result = Whitebox.<String> invokeMethod(companyUIController,"uploadFileToS3",companySettings,
                                                   ext,modelMap);

    //then
    Assert.assertNull(result);

  }

  @Test
  public void testOfUploadFileToS3WithCompanySettingParamMissingShouldReturnEmptyFileId() throws
                                                                                    Exception{

    //Given
    String testPath="testPath";
    String fileId="11111";
    String ext=".jgp";
    CompanySettings companySettings = null;
    Map<String, Object> modelMap = new HashMap<>();

    given(uploadService.uploadFileToS3(anyString())).willReturn(fileId);

    //When
    String result = Whitebox.<String> invokeMethod(companyUIController,"uploadFileToS3",companySettings,
                                                   ext,modelMap);

    //then
    Assert.assertNull(result);

  }

  @Test
  public void testOfUploadFileToS3WithModelMapParamMissingShouldReturnEmptyFileId() throws
                                                                                          Exception{

    //Given
    String testPath="testPath";
    String fileId="11111";
    String ext=".jgp";
    CompanySettings companySettings = null;
    Map<String, Object> modelMap = null;

    given(uploadService.uploadFileToS3(anyString())).willReturn(fileId);

    //When
    String result = Whitebox.<String> invokeMethod(companyUIController,"uploadFileToS3",companySettings,
                                                   ext,modelMap);

    //then
    Assert.assertNull(result);

  }

}
