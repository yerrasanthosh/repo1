package com.vroozi.customerui.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * User: qureshit
 * Date: 10/2/12
 * Time: 5:29 PM
 * Created utility class since Json cannot serialize CommonsMultiPartFile object. We do not have to make
 * the file property part of the object that needs to be serialized through Json anymore. Simply use this class
 * to pick up selected file object through UI, and use its values to populate object's properties that need to be sent
 * through Json.
 */
public class FileUtility {
    protected transient CommonsMultipartFile iconFile;
    private transient CommonsMultipartFile companyImage;
    private transient CommonsMultipartFile noCompanyImage;
    private transient CommonsMultipartFile welcomeImage;
    private transient CommonsMultipartFile findVendorImage;

    public CommonsMultipartFile getIconFile() {
        return iconFile;
    }

    public void setIconFile(CommonsMultipartFile iconFile) {
        this.iconFile = iconFile;
    }


    public CommonsMultipartFile getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(CommonsMultipartFile companyImage) {
        this.companyImage = companyImage;
    }

    public CommonsMultipartFile getNoCompanyImage() {
        return noCompanyImage;
    }

    public void setNoCompanyImage(CommonsMultipartFile noCompanyImage) {
        this.noCompanyImage = noCompanyImage;
    }

    public CommonsMultipartFile getWelcomeImage() {
        return welcomeImage;
    }

    public void setWelcomeImage(CommonsMultipartFile welcomeImage) {
        this.welcomeImage = welcomeImage;
    }

    public CommonsMultipartFile getFindVendorImage() {
        return findVendorImage;
    }

    public void setFindVendorImage(CommonsMultipartFile findVendorImage) {
        this.findVendorImage = findVendorImage;
    }
}
