package com.vroozi.customerui.filters;

import org.apache.commons.lang.StringUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: SURYA MANDADAPU
 * Date: 10/19/12
 * Time: 11:55 AM
 */
public class SessionTimeOutFilter implements Filter {
    private String timeoutPage = "timeout";
    private static final String loginPage = "login";
    private final Logger logger = LoggerFactory.getLogger(SessionTimeOutFilter.class);


    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // is session expire control required for this request?
        if (isSessionControlRequiredForThisResource(httpServletRequest)) {
            logger.debug("httpServletRequest.getRequestURI() :" +httpServletRequest.getRequestURI());
            if((!httpServletRequest.getRequestURI().endsWith(loginPage))&& isSessionInvalid(httpServletRequest)) {
                String timeoutUrl = httpServletRequest.getContextPath() + "/" + timeoutPage;
                httpServletResponse.sendRedirect(timeoutUrl);
                return;
            }else{
                logger.debug("SessionTimoutFilter skipped for:  "+httpServletRequest.getRequestURI());
            }
        }
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {

        String requestPath = httpServletRequest.getRequestURI();
        boolean controlRequired = !StringUtils.contains(requestPath, timeoutPage);
       return controlRequired;

    }
    private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {

        boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null)
                && !httpServletRequest.isRequestedSessionIdValid();
        return sessionInValid;
    }
}
