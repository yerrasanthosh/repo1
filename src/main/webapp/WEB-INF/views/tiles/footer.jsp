<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<div id="footer">
    <ul class="menu">
        <li class="logotype">
            <a href="http://www.vroozi.com"><img alt="image description" src="res/images/logo-vroozi.png" width="85" height="17" /></a>
            <span class="sep"></span>
        </li>
                <% if(session.getAttribute("user")!=null){%>
            <li><a href="vroozi"> <spring:message code="com.adminui.footer.Home" text="default text" /></a></li>
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_CATALOG)%>">
            <li><a href="catalog"> <spring:message code="com.adminui.footer.ContentManager" text="default text" /></a></li>
        </c:if>
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_DATA_MAPPING)%>">
            <li><a href="datamapping"> <spring:message code="com.adminui.footer.DataMapping" text="default text" /></a></li>
        </c:if>
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_USER_MANAGEMENT)%>">
            <li><a href="user_management"> <spring:message code="com.adminui.footer.Users" text="default text" /></a></li>
        </c:if>
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_VIEW)%>">
            <li><a href="profiles"> <spring:message code="com.adminui.footer.ContentViews" text="default text" /></a></li>
        </c:if>
        <% }else{%>
            <li><a href="http://www.vroozi.com"> <spring:message code="com.adminui.footer.Home" text="default text" /></a></li>
        <%} %>
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_HELP)%>">
            <li><a href="help"> <spring:message code="com.adminui.footer.Help" text="default text" /></a></li>
        </c:if>        
        <li><a href="policy"> <spring:message code="com.adminui.footer.PrivacyPolicy" text="default text" /></a></li>
        <li><a href="contact"><spring:message code="com.adminui.footer.Contact" text="default text" /></a></li>

    </ul>
    <ul class="menu">
        <li>
        	Copyright &copy; 2016 Vroozi. All rights reserved.
        	<span class="sep"></span>
        </li>
        <li><a><spring:message code="com.adminui.footer.support" text="default text" /><span><%=(String) session.getAttribute("appReleaseNumber")%></span></a></li>

    </ul>
    
</div>
