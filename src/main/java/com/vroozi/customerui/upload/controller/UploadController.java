package com.vroozi.customerui.upload.controller;

import com.vroozi.customerui.catalogItem.service.rest.CatalogItemRestClient;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.upload.model.ImageAttachmentResource;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.upload.model.FileResourceForm;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    private final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    AppConfig appConfig;

    @Autowired
    UploadService uploadService;

    @Autowired
    CatalogItemRestClient catalogItemRestClient;

    @RequestMapping(value="/upload-attachment", produces="text/html")
    @ResponseBody
    public String uploadAttachment(HttpServletRequest request,  @ModelAttribute FileResourceForm fileForm, HttpServletResponse response, ModelMap modelMap){

        if (fileForm!= null && fileForm.getAttachmentFile() != null && !fileForm.getAttachmentFile().isEmpty()) {

            User user = (User) SessionDataRetriever.getLoggedInUser(request);
            File destinationDir = new File(appConfig.itemAttachmentsPath, fileForm.getCatalogId());
            if(!destinationDir.exists()) {
            	destinationDir.mkdirs();
            }
            String destinationPath = destinationDir.getAbsolutePath();
            try{
                initDir(appConfig.itemAttachmentsPath,fileForm.getCatalogId());
            }catch (Exception e){
                e.printStackTrace();
            }
            String fileName = fileForm.getAttachmentFile().getOriginalFilename();
            ImageAttachmentResource imageAttachmentResource = populateImageAttachment(fileForm,destinationPath,fileName);
            imageAttachmentResource.setUnitId(user.getUnitId());
            String attachment =  uploadService.uploadAttachment(imageAttachmentResource);
            ObjectMapper mapper = new ObjectMapper();

            try{
                ImageAttachmentResource imageAttachmentResource2 = mapper.readValue(attachment, ImageAttachmentResource.class);
                if(fileForm.getCatalogItemId()!=null){
                    imageAttachmentResource.setFileResourceId(imageAttachmentResource2.getFileResourceId());
                    associateAttachmentToCatalogItem(imageAttachmentResource,fileForm.getUnitId(),fileForm.getCatalogId(),fileForm.getCatalogItemId());
                }
            }catch (Exception e){
                LOGGER.error("Attachment:",e);
            }
            return "["+attachment+"]";
            // return "[Object { fileResourceId="+imageAttachmentResource.fileResourceId+",  fileResourceOrig="+imageAttachmentResource.getFileResourceOrig()+",  fileName="+imageAttachmentResource.getFileName()+",  fileTag="+imageAttachmentResource.getFileTag()+"}]";
            //return "[{\"fileResourceId\":\"508786fcf0c88a43cd8bebda\",\"fileResourceOrig\":\"C:/projects/junk/attachments/3Learning_the_vi_Editorsss.pdf\",\"fileName\":\"Learning_thedddddddd_vi_Editor.pdf\",\"fileTag\":null,\"unitId\":\"13\"}]";

        }
        return null;
    }

    @RequestMapping(value="/upload-image", produces="text/html")
    @ResponseBody
    public String uploadImage(HttpServletRequest request,  @ModelAttribute FileResourceForm fileForm, HttpServletResponse response, ModelMap modelMap){
        if (fileForm!= null && fileForm.getImageFile() != null && !fileForm.getImageFile().isEmpty()) {

            User user = (User)SessionDataRetriever.getLoggedInUser(request);
            String destinationPath= FilenameUtils.concat(appConfig.itemImagesPath, "thumbnails/"+fileForm.getCatalogId()+"/") ;
            String destinationPathM= FilenameUtils.concat(appConfig.itemImagesPath, "medium/"+fileForm.getCatalogId()+"/") ;
            String destinationPathL= FilenameUtils.concat(appConfig.itemImagesPath, "large/"+fileForm.getCatalogId()+"/") ;
            try{
                initDir(FilenameUtils.concat(appConfig.itemImagesPath, "thumbnails/"),fileForm.getCatalogId());
                initDir(FilenameUtils.concat(appConfig.itemImagesPath, "medium/"),fileForm.getCatalogId());
                initDir(FilenameUtils.concat(appConfig.itemImagesPath, "large/"),fileForm.getCatalogId());
            }catch (Exception e){
                e.printStackTrace();
            }
            String fileName = fileForm.getImageFile().getOriginalFilename().replaceAll(" ", "").toLowerCase();
            ImageAttachmentResource imageAttachmentResource = populateImageAttachment(fileForm,destinationPath,fileName);
            try {
                Utils.generateImage(destinationPath+fileName, fileName, destinationPathL, 275, 260);
                Utils.generateImage(destinationPath+fileName, fileName, destinationPathM, 115, 100);
                Utils.generateImage(destinationPath+fileName, fileName, destinationPath, 50, 40);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            imageAttachmentResource.setUnitId(user.getUnitId());
            String attachment =  uploadService.uploadImage(imageAttachmentResource);
            ObjectMapper mapper = new ObjectMapper();

            try{
                ImageAttachmentResource imageAttachmentResource2 = mapper.readValue(attachment, ImageAttachmentResource.class);
                if(fileForm.getCatalogItemId()!=null){
                    imageAttachmentResource.setFileResourceId(imageAttachmentResource2.getFileResourceId());
                    associateImageToCatalogItem(imageAttachmentResource,user.getUnitId(),fileForm.getCatalogId(),fileForm.getCatalogItemId());
                }
            }catch (Exception e){
                LOGGER.error("Attachment:",e);
            }
            return "["+attachment+"]";
        }
        return null;
    }

     private ImageAttachmentResource populateImageAttachment(FileResourceForm fileForm,  String destinationPath,String fileName){

         ImageAttachmentResource imageAttachmentResource = new ImageAttachmentResource();
            try {
				uploadService.writeToFile(fileForm.getImageFile()!=null?fileForm.getImageFile().getInputStream():fileForm.getAttachmentFile().getInputStream(), fileName, destinationPath);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
            File destinationFile = new File(destinationPath, fileName);
            imageAttachmentResource.setFileResourceOrig(destinationFile.getAbsolutePath());
            imageAttachmentResource.setFileName(fileName);
            imageAttachmentResource.setUnitId(fileForm.getUnitId());
            String fileType = fileName.substring(fileName.lastIndexOf('.'),fileName.length());
            imageAttachmentResource.setFileType(fileType);

         return imageAttachmentResource;
     }


    private void associateAttachmentToCatalogItem(ImageAttachmentResource imageAttachmentResource,String unitId,String catalogId,String catalogItemId){
        try{
            catalogItemRestClient.associateCatalogItemAttachments(catalogId,catalogItemId, imageAttachmentResource);
        }catch (Exception e){
            LOGGER.error("Attachment:",e);
        }
    }

    private void associateImageToCatalogItem(ImageAttachmentResource imageAttachmentResource,String unitId,String catalogId,String catalogItemId){
        try{
            catalogItemRestClient.associateCatalogItemImages(catalogId,catalogItemId, imageAttachmentResource);
        }catch (Exception e){
            LOGGER.error("Attachment:",e);
        }
    }

    public void  initDir(String path, String catalogId) throws Exception {
        this.setUpDirectories(path,"/"+catalogId);
    }

    private void setUpDirectories(String parentPath, String child) throws IOException {
        if(!FileUtils.directoryContains(new File(parentPath), new File(child))){
            FileUtils.forceMkdir(new File(parentPath+child));
        }
    }
}

