package com.vroozi.customerui.common;

import com.vroozi.customerui.schedulers.featuretoggles.FeatureAwareResource;
import com.vroozi.customerui.user.services.user.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * User: SURYA MANDADAPU
 * Date: 11/8/12
 * Time: 11:58 AM
 */
@Component
public class SessionDataRetriever {

    public static User getLoggedInUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return (User)session.getAttribute("user");
    }
    public static Map getUserAclMap(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return (Map)session.getAttribute("userAclMap");
    }

  public static FeatureAwareResource getFeatureAwareResource (HttpServletRequest request){
    HttpSession session = request.getSession(false);
    return (FeatureAwareResource)session.getAttribute("featureAwareResource");
  }

}
