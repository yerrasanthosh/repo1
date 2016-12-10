package com.vroozi.customerui.util;

import com.vroozi.customerui.company.model.CompanySettings;
import javax.servlet.http.HttpSession;

/**
 * Created by sajjidak on 4/8/16.
 */
public class SessionUtil {

  public static void updateS3FilePathInSession(HttpSession session, CompanySettings cs, String
      fileStorageServiceURI, boolean canRemove) {
    if (cs != null && org.apache.commons.lang.StringUtils.isNotBlank(cs.getCompanyIcon()) && !cs.getCompanyIcon().contains
        (".")) {
      session.setAttribute("absolutePath", String.format("%s/%s", fileStorageServiceURI, "assets"));
    } else {
      if (canRemove) {
        session.removeAttribute("absolutePath");
      }

    }
  }
}
