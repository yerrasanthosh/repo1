package com.vroozi.customerui.servlets;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
/**
 * User: SURYA MANDADAPU
 * Date: 7/2/12
 * Time: 11:55 PM
 */
public class HealthCheck  extends HttpServlet {

    private final org.slf4j.Logger LOG = LoggerFactory
            .getLogger(HealthCheck.class);
    /**
     * Constructor of the object.
     */
    public HealthCheck() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("health Check got called at:"+new Date(System.currentTimeMillis()));
             writeResponse(
                     response.getWriter(),
                     "OK");
            }
    private void writeResponse(PrintWriter os, String message) throws IOException {
        os.print(message);
        os.close();
    }
}

