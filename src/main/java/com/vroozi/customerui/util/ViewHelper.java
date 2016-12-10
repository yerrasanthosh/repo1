package com.vroozi.customerui.util;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.appConfig.model.AdminUICache;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.supplier.controller.SupplierAttributeUIController;
import com.vroozi.customerui.supplier.model.SupplierAttributePair;
import com.vroozi.customerui.supplier.model.SupplierAttributes;
import com.vroozi.customerui.supplier.model.TimeZone;
import com.vroozi.customerui.user.services.user.model.User;

public class ViewHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewHelper.class);
    public static final List<String> dateFormats = Arrays.asList("dd.MM.yyyy", "MM/dd/yyyy", "MM-dd-yyyy", "yyyy.MM.dd", "yyyy/MM/dd", "yyyy-MM-dd");

    static public String GMTTime(Date date) {
        if (date == null) return "";

        return date.toGMTString();
    }
    
  public static String getFormattedDate(HttpServletRequest request, Date date) {
    User user = SessionDataRetriever.getLoggedInUser(request);
    try {
      /* if date doesn't contain 01-01-1970 which represents an empty value */
      if (date.after(DateTimeUtils.parseDate("1971", "yyyy"))) {
        TimeZone userTimeZone =
            AdminUICache.getTimeZonesMap().get(Integer.valueOf(user.getTimeZone()));
        return DateTimeUtils.formatDateForTimeZone(date,
            dateFormats.get(Integer.parseInt(user.getDateFormat()) - 1),
            userTimeZone.getTimeZoneValue());
      } else {
        return "";
      }
    } catch (Exception e) {
      LOGGER.error("Exception in Getting Formatted Date: {}", e.getMessage());
      return "";
    }
  }

  public static String getFormattedDate(HttpServletRequest request, String dateStr) {
    String formattedDate = "";
    User user = SessionDataRetriever.getLoggedInUser(request);
    try {
      TimeZone userTimeZone =
          AdminUICache.getTimeZonesList().get(Integer.valueOf(user.getTimeZone()));
      Date date = DateTimeUtils.parseDateForGMT(dateStr.substring(0, 10), "dd-MM-yyyy");
      formattedDate =
          DateTimeUtils.formatDateForTimeZone(date,
              dateFormats.get(Integer.parseInt(user.getDateFormat()) - 1),
              userTimeZone.getTimeZoneValue());

    } catch (Exception e) {
      LOGGER.error("Exception in Getting Formatted Date: {}", e.getMessage());
    }

    return formattedDate;
  }

    static public boolean isProfileAssigned(String[] profileIds, int targetProfileId){
        if(targetProfileId == 0 || profileIds == null || profileIds.length == 0) return false;

        String targetProfileIdStr = String.valueOf(targetProfileId);

        for(String profileId: profileIds){
            if(targetProfileIdStr.equals(profileId)) return true;
        }

        return false;
    }

    public static class Column{
        public String id;
        public String css;
        public List<? extends Object> data;

        public String getCss() {
            return css;
        }
        public void setCss(String css) {
            this.css = css;
        }

        public List<? extends Object> getData() {
            return data;
        }
        public void setData(List<? extends Object> data) {
            this.data = data;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }

    public static boolean isAssociated(List<SupplierAttributePair> supplierAttributePairs, String attributeId){
        if(supplierAttributePairs == null || supplierAttributePairs.size() == 0
                || attributeId == null || attributeId.length() == 0){
            return false;
        }

        Set<SupplierAttributePair> attributeSet = new HashSet<SupplierAttributePair>(supplierAttributePairs);
        SupplierAttributePair supplierAttributePair = new SupplierAttributePair(attributeId, ((SupplierAttributePair)supplierAttributePairs.get(0)).getSupplierId(), 0, ((SupplierAttributePair)supplierAttributePairs.get(0)).getAttributeName());
        return attributeSet.contains(supplierAttributePair);
    }

    public static boolean isAdmin(User user){
        if(user == null || user.getRoles() == null || user.getRoles().size() == 0) return false;
        return (user.isActive() && hadAdminRole(user.getRoles()));
    }

    public static boolean isMasterAdmin(User user){
        if(user == null || user.getRoles() == null || user.getRoles().size() == 0) return false;
        return (user.isActive() && hasMasterAdminRole(user.getRoles()));
    }

    public static boolean hadAdminRole(List<Role> roles){
        for(Role role: roles){
            if(role == Role.ADMINISTRATOR || role == Role.MASTER_ADMINISTRATOR) return true;
        }
        return false;
    }

    public static boolean hasMasterAdminRole(List<Role> roles){
        for(Role role: roles){
            if(role == Role.MASTER_ADMINISTRATOR) return true;
        }
        return false;
    }

    public static boolean isDefaulAttribute(SupplierAttributes supplierAttributes){
        for(String attributeName: SupplierAttributeUIController.attributeNames){
            if(supplierAttributes.getAttributeName().equalsIgnoreCase(attributeName)){
                return true;
            }
        }
        return false;
    }
    
    public static String escapeString(String str) {
    	return StringEscapeUtils.escapeJavaScript(str);
    }
}
