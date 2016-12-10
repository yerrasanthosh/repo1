package com.vroozi.customerui.upload.service.rest;

import com.vroozi.customerui.upload.model.FileObject;
import com.vroozi.customerui.upload.model.FileObjectStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.vroozi.customerui.upload.model.FileObjectStatus.SAVED;


/**
 * @author Sajid Akram Kashmiri
 *
 */
@Component
public class FileStorageServiceRestClientImpl implements FileStorageServiceRestClient {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${fileStorageServiceURI}")
  public String fileStorageServiceURI;

  @Value("${fileStorageServiceDownloadURLPath}")
  public String fileStorageServiceDownloadURLPath;


  public String uploadCatalogExportFile(String file) throws Exception {

    logger.info("Invoking File Storage Service to upload company logo  to Amazon S3");
    MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
    parts.add("file", new FileSystemResource(file));

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    HttpEntity<MultiValueMap<String, Object>> requestEntity =
        new HttpEntity<MultiValueMap<String, Object>>(parts, headers);

    ResponseEntity<FileObject> response = null;
    response = restTemplate.exchange(fileStorageServiceURI, HttpMethod.POST,
                                     requestEntity, FileObject.class);

    if (response != null) {
      FileObjectStatus status = response.getBody().getStatus();
      logger.info("Upload Status: {}", status);

      if (status == SAVED) {
        return response.getBody().getId();
      } else {
        logger.error("File {} Couldn't Be Saved on S3", file);
        throw new Exception("File Couldn't be Saved on S3");
      }
    } else {
      throw new Exception("Null Response Received from File Upload Service");
    }

  }
}
