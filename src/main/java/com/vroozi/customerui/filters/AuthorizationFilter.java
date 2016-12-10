package com.vroozi.customerui.filters;

import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.user.services.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.*;
import java.io.IOException;

/**
 * User: SURYA MANDADAPU
 * Date: 11/30/12
 * Time: 12:23 PM
 */
public class AuthorizationFilter  implements Filter {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private FilterConfig filterConfig = null;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    CompanyService companyService;

    @Autowired
    AppConfig appConfig;

    /**
     *
     * @param servletRequest The servlet request we are processing
     * @param servletResponse The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception java.io.IOException if an input/output error occurs
     * @exception javax.servlet.ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
//        try{
//
//            HttpServletRequest request = ((HttpServletRequest)servletRequest);
//            HttpSession session = request.getSession(false);
//
//
                chain.doFilter(servletRequest, servletResponse);
//        }
//        catch (Exception e){
//            logger.error("Authentication Filter:",e);
//            sendProcessingError(e, servletResponse);
//        }
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


}
