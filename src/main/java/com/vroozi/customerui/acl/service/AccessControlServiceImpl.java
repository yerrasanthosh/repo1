package com.vroozi.customerui.acl.service;

import com.vroozi.customerui.acl.model.AccessControl;
import com.vroozi.customerui.acl.model.Permission;
import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.RestServiceUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * User: SURYA MANDADAPU
 * Date: 11/30/12
 * Time: 11:42 AM
 */
@Component
public class AccessControlServiceImpl implements AccessControlService{

    @Autowired
    RestServiceUrl restServiceUrl;

    public Map loadUserACL(User user) throws Exception {
        ///organizations/{unitid}/roles/{roleid}/acl

        StringBuffer roles = new StringBuffer();
        Map controlsMap = new HashMap();

        for(Role role: user.getRoles()){
            roles.append(role.toString() + ",");
        }

        if(roles.length() > 0){
            roles.deleteCharAt(roles.length()-1);
        }

        AccessControl[] accessControlsArray =  new RestTemplate().getForObject(restServiceUrl.accessControlListURI(), AccessControl[].class,user.getUnitId(), roles.toString());
        List<AccessControl> controlsList = new ArrayList<AccessControl>(Arrays.asList(accessControlsArray));
        for(AccessControl accessControl:controlsList){
            controlsMap.put(accessControl.getPermission(),accessControl);
        }
        return controlsMap;
    }
}
