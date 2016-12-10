package com.vroozi.customerui.util;

import org.slf4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.vroozi.customerui.user.services.user.model.User;

public class HttpUtils {
    public static final void forwardToPage(ServletRequest request, ServletResponse response, String pageName) throws Exception {
        RequestDispatcher rd = request.getRequestDispatcher(pageName);
        rd.forward(request, response);
    }

//    public static final User getLoggedInUser(HttpServletRequest request, Logger logger){
//        HttpSession session = request.getSession(false);
//        if(session == null || StringUtils.isEmpty(session.getId())){
//            return null;
//        }
//
//        User user = (User)session.getAttribute("user");
//        if( user == null || StringUtils.isEmpty(user.getSessionId()) || StringUtils.isEmpty(user.getUnitId()) ){
//            return null;
//        }
//        if (user.getRowsPerPage()<1) {
//            user.setRowsPerPage(Consts.PAGE_SIZE);
//        }
//        return user;
//    }
}
