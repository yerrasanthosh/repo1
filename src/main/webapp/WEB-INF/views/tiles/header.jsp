<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>
<%@ page import="java.util.ResourceBundle, com.vroozi.customerui.util.ViewHelper" %>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<% if(session.getAttribute("user")!=null){
    User loggedInUser = (User)session.getAttribute("user");
    String unitId = loggedInUser.getUnitId();
    String userId = loggedInUser.getUserId();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("adminui");
    String purchaseRequestURI = resourceBundle.getString("purchaseRequestURI");
    String messageCenterURI = resourceBundle.getString("messageCenterURI");
    String shopperURI = resourceBundle.getString("shopperURI");
    
    String loggedInUserName;
    if(loggedInUser.getDetails()==null || loggedInUser.getDetails().getContactName()==null){
    	loggedInUserName = loggedInUser.getFirstName()+" "+loggedInUser.getLastName();
    } else {
    	loggedInUserName = loggedInUser.getDetails().getContactName();
    }
    if(loggedInUserName.length()>=11) {
    	loggedInUserName = loggedInUserName.substring(0,11);
    }
    
    
%>

<div id="header">
  <div class="holder">
    <h1 id="defStrong" class="logo" <c:if test='${not empty companySettings.companyIcon}'>style="display: none;"</c:if> ><a href="vroozi"><spring:message code="com.adminui.header.b2bCom" text="default text" /></a></h1>
    <h1 id="custStrong" <c:if test='${empty companySettings.companyIcon}'>style="display: none;"</c:if> class="customLogo"><div><a href="vroozi"><img id="headerLogo" class="customLogoImg"
                                                                                                                                                        <c:set var="imgPath"
                                                                                                                                                               value="image/${companySettings.companyIcon}" />
                                                                                                                                                        <c:if test='${not empty absolutePath}'>
                                                                                                                                                            <c:set var="imgPath"
                                                                                                                                                                   value="${absolutePath}/${companySettings.companyIcon}"/>
                                                                                                                                                        </c:if>
                                                                                                                                                      src="${imgPath}" /></a></div></h1>

    <% if(session.getAttribute("user")!=null){%>
      <ul class="add-nav">
        <li>
          <a href="#" class="account"><spring:message code="com.adminui.header.Hi" text="default text" /> <span class="name"><c:out value='<%=loggedInUserName%>'/></span> <span class="ico-account"><!--em class="number">5</em--><spring:message code="com.adminui.header.Account" text="default text" /></span></a>
            <div class="drop">
              <ul>
                <li><a href="myAccount" class="ico1"><spring:message code="com.adminui.header.Preferences" text="default text" /></a></li>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_MESSAGE)%>">
                    <%--<li><a href="message-center" class="ico2"><em class="number">!</em>Messages</a></li>--%>
                    <li><a href="<%=messageCenterURI%>" class="ico2"><spring:message code="com.adminui.header.Messages" text="default text" /></a></li>
                </c:if>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_HELP)%>">
                    <li><a href="help" class="ico10"><spring:message code="com.adminui.header.HELP" text="default text" /></a></li>
                </c:if>
                <li><a href="logout" class="ico3"><spring:message code="com.adminui.header.Signout" text="default text" /></a></li>
              </ul>
            </div>
          </li>
          <li>
            <a class="settings" href="#"><spring:message code="com.adminui.header.Settings" text="default text" /></a>
            <div class="drop">
              <ul>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_COMPANY_SETTING)%>">
                  <li><a href="company" class="ico4"><spring:message code="com.adminui.header.Companysettings" text="default text" /></a></li>
                </c:if>
                  <%--<li><a href="help" class="ico10">Help</a></li>--%>
								<!--li><a href="#" class="ico5">homepage cards</a></li>
								<li><a href="#" class="ico6">help content</a></li>
								<li><a href="#" class="ico7">notifications</a></li>
								<li><a href="#" class="ico8">security</a></li>
								<li><a href="#" class="ico9">workflow</a></li-->
							</ul>
						</div>
					</li>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_SHOPPER_VIEW)%>">
					<li class="alt-item"><a class="cart" href="<%=shopperURI%>" target="_blank"><spring:message code="com.adminui.header.ShopperView" text="default text" /></a></li>
                </c:if>
					<li class="alt-item2"><a href="#" class="btn-top"><spring:message code="com.adminui.header.top" text="default text" /></a></li>
				</ul>

        <c:if test="<%=aclManager.allow(request, Permission.SEARCH)%>">
  				<!-- search form -->
  				<form action="#" class="search-form">
  					<fieldset>
  						<div class="field">
  							<input type="text" id="text-field" value="Enter search here..." />
  							<select title="&nbsp;" class="drop-search no-wrap-select">
  								<option selected="selected"><spring:message code="com.adminui.header.Catalogs" text="default text" /></option>
                  <option>All</option>
  								<!--option>Custom Fields</option>
  								<option>Images</option>
  								<option>Suppliers</option>
  								<option>Users</option-->
  							</select>
  						</div>
              <input id="searchbtn" class="searchbtn" type="submit" value="Submit"/>
  					</fieldset>
  				</form>

        </c:if>
    <% }%>

        <script type="text/javascript">
            var unitId = '<%=unitId%>';
            var userId = '<%=userId%>';
            var purchaseRequestURI = '<%=purchaseRequestURI%>';
            var messageCenterURI = '<%=messageCenterURI%>';

        </script>

        <ul id="nav">
          <c:if test="<%=aclManager.allow(request, Permission.VIEW_CATALOG)%>">
  					<li>
  						<a href="#"><spring:message code="com.adminui.header.CONTENTMANAGER" text="default text" /></a>
  						<div class="drop">
  							<ul>
  								<li><a id="catPage" href="catalog"><spring:message code="com.adminui.header.Catalogmanagement" text="default text" /></a></li>
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_VIEW)%>">
  								    <li><a href="profiles" class="checkboxReset"><spring:message code="com.adminui.header.Contentviews" text="default text" /></a></li>
                    </c:if>

  								<!--li><a href="#">custom fields</a></li>
  								<li><a href="#">image store</a></li-->
  							</ul>
  						</div>
  					</li>
          </c:if>

          <c:if test="<%=aclManager.allow(request, Permission.VIEW_USER_MANAGEMENT)%>">
            <li><a href="user_management"><spring:message code="com.adminui.header.Companyusers" text="default text" /></a></li>
          </c:if>
          <c:if test="<%=aclManager.allow(request, Permission.VIEW_DATA_MAPPING)%>">
  					<li>
  						<a href="datamapping"><spring:message code="com.adminui.header.DATAMAPPING" text="default text" /></a>
  						<!--div class="drop">
  							<ul>
  								<li><a href="#">Category</a></li>
  								<li><a href="#">unit of measure</a></li>
  								<li><a href="#">currency</a></li>
  							</ul>
  						</div-->
  					</li>
          </c:if>

          <c:if test="<%=aclManager.allow(request, Permission.VIEW_SUPPLIER)%>">
            <li><a href="suppliers" class="checkboxReset"><spring:message code="com.adminui.header.SUPPLIERS" text="default text" /></a></li>
              <li><a href="reports" class="checkboxReset">REPORTS</a></li
          </c:if>
           <c:if test="<%=aclManager.allow(request, Permission.VIEW_REPORTS)%>">
			<c:if test="${companySettings.showReportsMenu}">
				<li><a href="reports" class="checkboxReset">REPORTS</a></li>
			</c:if>
		   </c:if>
          <c:if test="<%=aclManager.allow(request, Permission.VIEW_PURCHASE_REQUEST)%>">
            <c:if test='<%=resourceBundle.getString("ereq").equals("true")%>'>
              <li><a href="#" onclick="document.location.href=purchaseRequestURI+'?userId='+userId+'&code='+unitId; return false;"><spring:message code="com.adminui.header.EREQ" text="default text" /></a></li>
            </c:if>
          </c:if>

				</ul>
    <% } %>

			</div>
		</div>
