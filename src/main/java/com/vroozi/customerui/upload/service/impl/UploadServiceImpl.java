package com.vroozi.customerui.upload.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vroozi.customerui.upload.model.ImageAttachmentResource;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.upload.service.rest.FileStorageServiceRestClient;
import com.vroozi.customerui.upload.service.rest.UploadRestClient;

/**
 * User: SURYA MANDADAPU
 * Date: 10/23/12
 * Time: 12:45 PM
 */
@Service
public class UploadServiceImpl implements UploadService {
    private final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    UploadRestClient uploadRestClient;
    @Autowired
  FileStorageServiceRestClient fileStorageServiceRestClient;

    @Override
    public boolean writeToFile(InputStream source, String destFileName, String uploadPath){
    	FileOutputStream fileOutputStream = null;
    	try {
            fileOutputStream = new FileOutputStream(new File(uploadPath, destFileName));
            IOUtils.copy(source, fileOutputStream);
        } catch (IOException exception) {
            logger.error("Error in writing to file(" + destFileName + ")", exception);
            return false;
        } finally {
        	try {
				fileOutputStream.close();
			} catch (IOException e) {
				logger.error("Error in writing to file(" + destFileName + ")", e);
			}
        }
        return true;
    }
    
    @Override
    public String uploadAttachment(ImageAttachmentResource attachment){
        return uploadRestClient.createAttachment(attachment);
    }
    
    @Override
    public String uploadImage(ImageAttachmentResource image){
        return uploadRestClient.uploadImage(image);
    }

    @Override
    public String uploadFileToS3(String file) throws Exception {
        return fileStorageServiceRestClient.uploadCatalogExportFile(file);
    }
    
  }
