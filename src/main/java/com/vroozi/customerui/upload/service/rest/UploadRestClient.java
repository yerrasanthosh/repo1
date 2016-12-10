package com.vroozi.customerui.upload.service.rest;

import com.vroozi.customerui.upload.model.ImageAttachmentResource;
import com.vroozi.customerui.util.RestServiceUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * User: SURYA MANDADAPU
 * Date: 10/23/12
 * Time: 1:12 PM
 */
@Service
public class UploadRestClient {
    private final Logger logger = LoggerFactory.getLogger(UploadRestClient.class);

    @Autowired
    RestServiceUrl restServiceUrl;
    public String createAttachment(ImageAttachmentResource proxy){
        RestTemplate template =new RestTemplate();
        return (String)template.postForObject(restServiceUrl.getCatalogServiceBaseURI() +"/organization/"+proxy.getUnitId()+restServiceUrl.getItemAttachmentURI(), proxy, String.class);
    }
    public String uploadImage(ImageAttachmentResource proxy){
        RestTemplate template =new RestTemplate();
        return (String)template.postForObject(restServiceUrl.getCatalogServiceBaseURI() +"/organization/"+proxy.getUnitId()+restServiceUrl.getItemImageURI(), proxy, String.class);
    }

}
