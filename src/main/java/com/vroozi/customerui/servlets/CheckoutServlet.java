package com.vroozi.customerui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Servlet implementation class CheckoutServlet
 * 
 * @author Mamoon Habib
 */
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckoutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String html = "<html><head><script type=\"text/javascript\">\n"
				+" function closeWindow() { \n"
				+"alert('You need to log in as Shopper in order to check out item back to smartOCI');\n"
//				+ "var url = window.opener.location.search;\n"
//				+ "if(!url.contains('checkoutExtCatalog')) { \n"
//				+ "url += url.contains('?')?'&checkoutExtCatalog=true':'?checkoutExtCatalog=true';\n"
//				+ "}\n"
//				+ "window.opener.location.search = url;\n"
				+ "window.close();\n"
				+ "} </script></head>\n"
				+ "<body onload=\"closeWindow();\"></html>";
		response.setContentType("text/html");
		response.getOutputStream().write(html.getBytes());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
