package com.vroozi.customerui.acl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.acl.model.Permission;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.schedulers.featuretoggles.FeatureAwareResource;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.acl.model.AccessControl;

import org.apache.commons.lang.StringUtils;


public class DisplayAccessControl {

    public AccessControl getByUserTypeAndPermission(HttpServletRequest request, Permission permission) throws Exception {
        //User user = SessionDataRetriever.getLoggedInUser(request);
        Map controlsMap = SessionDataRetriever.getUserAclMap(request);
        return controlsMap!=null?(controlsMap.get(permission)!=null?(AccessControl)controlsMap.get(permission):null):null;
    }

  public boolean isFeatureAvailable(HttpServletRequest request, String featureName) {
    FeatureAwareResource featureAwareResource = SessionDataRetriever.getFeatureAwareResource(request);
    User user = SessionDataRetriever.getLoggedInUser(request);
    String unitId = null;
    if (user!=null && !StringUtils.isBlank(user.getUnitId())) {
      unitId = user.getUnitId();
    }
    return featureAwareResource.isFeatureAvailable(unitId, featureName);
  }
    public boolean allow(HttpServletRequest request, Permission permission) throws Exception {
        return this.getByUserTypeAndPermission(request, permission) != null;
    }

    public boolean allowEditUser(HttpServletRequest request, Permission[] permissions, User target) throws Exception {
        if(target == null || target.getRoles() == null) return false;
        for(Permission permission:permissions){
            if(allowEditUser(request, permission, target)) return true;
        }
        return false;

    }
    public boolean allowEditUser(HttpServletRequest request, Permission permission, User target) throws Exception {
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_APPROVIER) && hasRole(target.getRoles(), Role.APPROVER)) return true;
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_BUYER) && hasRole(target.getRoles(), Role.BUYER)) return true;
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_SEARCH_USER) && hasRole(target.getRoles(), Role.WEB_SEARCH_USER)) return true;
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_SHOPPER) && hasRole(target.getRoles(), Role.SHOPPER)) return true;
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_MASTER_ADMIN) && hasRole(target.getRoles(), Role.MASTER_ADMINISTRATOR)) return true;
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_ADMIN) && hasRole(target.getRoles(), Role.ADMINISTRATOR)) return true;
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_SUPPLIER) && hasRole(target.getRoles(), Role.SUPPLIER_ADMIN)) return true;
        if((getByUserTypeAndPermission(request, permission) != null) && (permission == Permission.EDIT_SHOPPER_VIEW_ONLY) && hasRole(target.getRoles(), Role.SHOPPER_VIEW_ONLY)) return true;
        return false;
    }

    private boolean hasRole(List<Role> roles, Role targetRole){
        for(Role role:roles){
            if(role == targetRole) return true;
        }
        return false;
    }

//    public boolean allowCreate(HttpServletRequest request, Permission permission) throws Exception {
//        return this.getByUserTypeAndPermission(request, permission).isCanCreate();
//    }
//    public boolean allowUpdate(HttpServletRequest request, Permission permission) throws Exception {
//        return this.getByUserTypeAndPermission(request, permission).isCanUpdate();
//    }
//    public boolean allowDelete(HttpServletRequest request, Permission permission) throws Exception {
//        return this.getByUserTypeAndPermission(request, permission).isCanDelete();
//    }
}
