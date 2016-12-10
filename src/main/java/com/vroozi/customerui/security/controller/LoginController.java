package com.vroozi.customerui.security.controller;

import static com.vroozi.customerui.util.Consts.FORMAT_DATE_DD_MM_YYYY_HH_MM_SS;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.UserProxy;

@Controller
public class LoginController {
    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private final static String SSO_TOKEN = "SSO_TOKEN";

    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    CompanyService companyService;

    @Autowired
    AppConfig appConfig;

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String token =null;
        if(request.getSession()!=null){

            try{
                token = (String)request.getSession().getAttribute("SSO_TOKEN");

                request.getSession().invalidate();
            }catch(Exception e){
                LOG.error("Session Invalidate:"+e);
            }
            request.getSession(true);
        }
        response.sendRedirect(appConfig.ssoPath+appConfig.logoutURI+"?token="+token);

        // return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception{
      UserProxy user = (UserProxy)request.getSession().getAttribute("user");
      return "redirect:vroozi?language="+user.getLanguage();
    }
    @RequestMapping("/go-vroozi")
    public String go(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return ("redirect:"+request.getParameter("forward"));
    }

}


