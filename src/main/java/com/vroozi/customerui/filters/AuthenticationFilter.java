package com.vroozi.customerui.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vroozi.customerui.acl.service.AccessControlService;
import com.vroozi.customerui.appConfig.service.AppConfigService;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.schedulers.featuretoggles.FeatureAwareResource;
import com.vroozi.customerui.security.controller.CookieHandler;
import com.vroozi.customerui.security.controller.model.UserSessionData;
import com.vroozi.customerui.security.controller.model.VrooziCookie;
import com.vroozi.customerui.user.services.UserManagementService;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.util.SessionUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class AuthenticationFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private FilterConfig filterConfig = null;

    public static final String GO= "go-vroozi";
    public static final String HEALTH_CHECK_URI= "healthcheck";
    public static final String HTTPS= "X-Forwarded-Proto";
    public static final String REDIRECT_TO_HTTPS="true";
    public static final String NODE_TOKEN ="NODE_TOKEN";

    @Value("${fileStorageServiceURI}")
    private String fileStorageServiceURI;
    @Autowired
    AccessControlService accessControlService;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    CompanyService companyService;

  @Autowired
  FeatureAwareResource featureAwareResource;

    @Autowired
    AppConfig appConfig;

    @Autowired
    CookieHandler cookieHandler;
    
    @Autowired
    private AppConfigService appConfigService;
    
    /**
     *
     * @param servletRequest The servlet request we are processing
     * @param servletResponse The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        try{

            HttpServletRequest request = ((HttpServletRequest)servletRequest);
            HttpServletResponse response = ((HttpServletResponse)servletResponse);
            User user = null;
            String token = request.getParameter("token");
            boolean sendToLogin =false;
            boolean sendToNodeJSLogin =false;
            HttpSession session = request.getSession(false);

            String ssoToken = null;


           if(REDIRECT_TO_HTTPS.equals(appConfig.redirectToHttps)&& !isValidHttps(request,response)){
               sendToLogin =  true;
           }
           else if(request.getRequestURI().endsWith(GO) || (session!=null && session.getAttribute(NODE_TOKEN)!=null) ){

               if(session == null){
                    session = request.getSession(true);
               }

               String vss = cookieHandler.getCookie(request,appConfig.cookieVSD);
                if(vss != null){

                    ObjectMapper mapper = new ObjectMapper();
                    VrooziCookie vrooziCookie = (VrooziCookie)mapper.readValue(vss, VrooziCookie.class);

                    user = userManagementService.getUser(vrooziCookie.getUserId());
                    if(user == null){
                        logger.debug("Missing user session data for given token and sending to NodeJs login page.");
                        sendToNodeJSLogin = true;
                    }

                    session.setAttribute("NODE_TOKEN",user.getUserId());

                    populateUserData(session,user);
                }else{
                    sendToNodeJSLogin = true;
                }
           }
           else if(!request.getRequestURI().endsWith(HEALTH_CHECK_URI) && (request.isRequestedSessionIdValid())
                    || (token!=null && !"".equals(token)) ){

                    if(session != null){
                    	String vss = cookieHandler.getCookie(request,appConfig.cookieVSD);
                        if(vss != null){
                    		ssoToken = (String)session.getAttribute("SSO_TOKEN");
                    	}
                    }

                    if(ssoToken == null || "".equals(ssoToken.trim()) ){
                        logger.info("AuthenticationFilter: REQUEST-TOKEN:"+token);
                        logger.info("AuthenticationFilter:SESSION-TOKEN:"+ssoToken);

                        if(token == null || "".equals(token.trim())){
                            if(session != null){
                                session.invalidate();
                            }
                            logger.debug("No token exist and sending to login page");
                            sendToLogin = true;
                        } else{
                            session = request.getSession(true);
                            session.setAttribute("SSO_TOKEN",token);
                            UserSessionData userObject = userManagementService.getUserSessionData(token);
                            if(userObject == null){
                                logger.debug("Missing user session data for given token and sending to login page.");
                                sendToLogin = true;
                            }
                            populateData(session, userObject);
                        }
                    }else {
                        user = SessionDataRetriever.getLoggedInUser(request);
                        if(user == null){
                            logger.info("Missing logged in user in session and sending to login page.");
                            sendToLogin = true;
                        }
                    }
                }
                else {
                    logger.info("Session is null/bookmark link clicked  and sending to login page.");
                    sendToLogin = true;
                }

                if(sendToLogin){
                    logger.info("Send To Login is set to true and redirecting to login page.");
                    //session = request.getSession(true);
                    try{
                        if(session != null)
                            session.invalidate();

                    }catch (Exception e){
                        logger.error("Session Invalidate:",e);
                    }

                    authenticationFailed(request, response);
                }else{
                    chain.doFilter(servletRequest, servletResponse);
                }

        }
        catch (Exception e){
            logger.error("Authentication Filter:",e);
            sendProcessingError(e, servletResponse);
        }
    }

	public void authenticationFailed(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String cxt = request.getContextPath();
		String origin = request.getRequestURL().substring(0, request.getRequestURL().indexOf(cxt)+cxt.length()) +"/vroozi";

		if(isAjaxRequest(request)) {
//			String origin = request.getHeader("referer");
			String redirectUrl = getRedirectUrl(response, origin);
			String errorResponse = "{ \"redirectUrl\": \""+redirectUrl+"\"}";
			
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//          response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorised: " );
			response.getOutputStream().print(errorResponse);
			response.getOutputStream().flush();
			
		} else {
			if(request.getServletPath().equals("/downloadcatalog.csv")) {
			    origin = request.getRequestURL().substring(0, request.getRequestURL().indexOf(cxt)+cxt.length())+"/download";
				if(request.getQueryString()!=null && request.getQueryString().length()>0) {
					origin += "?" + request.getQueryString();
				}
			}
			String redirectUrl = getRedirectUrl(response, origin);
			response.sendRedirect(redirectUrl);
		}
	}

    private boolean isAjaxRequest(HttpServletRequest request) {
    	String xreq = request.getHeader("x-requested-with");
    	return xreq!=null && xreq.equalsIgnoreCase("XMLHttpRequest");
    }

	public String getRedirectUrl(HttpServletResponse response, String origin) throws IOException {
		if(REDIRECT_TO_HTTPS.equals(appConfig.redirectToHttps)){
		    origin = origin.replace("http:","https:");
		}
		String redirectUrl = appConfig.ssoPath+appConfig.loginURI+"?oru="+ response.encodeURL(encode(origin));
		return redirectUrl;
	}


    private boolean isValidHttps(HttpServletRequest request, HttpServletResponse response){
        //X-Forwarded-Proto
        String https = (String) request.getHeader(HTTPS);
        if("https".equalsIgnoreCase(https)){
            return true;
        }
        return false;
    }
    private void populateData(HttpSession session, UserSessionData userObject) throws Exception {

        //ogger.debug(" Populating user data...");
        if(userObject != null){
            User user = userManagementService.getUserByUserName(userObject.getUserName(),userObject.getUnitId());

            CompanySettings companySettings = companyService.getCompanySettingsByUnitId(userObject.getUnitId());
            session.setAttribute("companySettings", companySettings);
            session.setAttribute("user", user);
            session.setAttribute("userAclMap", accessControlService.loadUserACL(user));
            session.setAttribute("featureAwareResource", featureAwareResource);

            String appReleaseNumber = getAppReleaseNumber();
            session.setAttribute("appReleaseNumber", appReleaseNumber);
            SessionUtil.updateS3FilePathInSession(session, companySettings,fileStorageServiceURI,
                                                  false);

        }
    }

    private void populateUserData(HttpSession session, User user) throws Exception {

        //logger.debug(" Populating user data...");
        if(user != null){


            CompanySettings companySettings = companyService.getCompanySettingsByUnitId(user.getUnitId());
            session.setAttribute("companySettings", companySettings);
            session.setAttribute("user", user);
            session.setAttribute("userAclMap", accessControlService.loadUserACL(user));
            session.setAttribute("featureAwareResource", featureAwareResource);

            String appReleaseNumber = getAppReleaseNumber();
            session.setAttribute("appReleaseNumber", appReleaseNumber);
        }
    }

    private String getAppReleaseNumber() {
    	com.vroozi.customerui.appConfig.model.AppConfig appConfig = null;

        try {
            appConfig = appConfigService.getAppConfig();
        }catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }

        String relNum = "";
        if(appConfig != null){
            relNum = appConfig.getReleaseNum();
        }

        return (StringUtils.isNotEmpty(relNum))? relNum:"";
	}

	private String encode(String originUrl) throws IOException {
        //encoding  byte array into base 64
        byte[] encoded = Base64.encodeBase64(originUrl.getBytes());
        return new String(encoded);
    }


    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("NewFilter()");
        }
        StringBuffer sb = new StringBuffer("NewFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
