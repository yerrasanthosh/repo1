package com.vroozi.customerui.upload.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.vroozi.customerui.upload.model.ImageAttachmentResource;

/**
 * User: SURYA MANDADAPU
 * Date: 10/23/12
 * Time: 1:06 PM
 */
@Service
public interface UploadService {
    public String uploadAttachment(ImageAttachmentResource attachment);
    public String uploadImage(ImageAttachmentResource image);
    public String uploadFileToS3(String file) throws Exception;

    public boolean writeToFile(InputStream source, String destFileName, String uploadPath);


}