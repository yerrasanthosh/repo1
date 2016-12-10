<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.supplier_attribute_management.Vroozi" text="default text" /></a></li>
            <li><spring:message code="com.adminui.supplier_attribute_management.SupplierAttributesManagement" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">
        <div id="content">
            <h1><spring:message code="com.adminui.supplier_attribute_management.SupplierAttributesManagement" text="default text" /></h1>
            <div class="content-block toggle-block active" id="summary-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.supplier_attribute_management.Supplierattributessummary" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content">
                        <div class="summary-box">
                            <table class="summary-table">
                                <thead>
                                <tr>
                                    <td class="a-center sep view"><spring:message code="com.adminui.supplier_attribute_management.View" text="default text" /></td>
                                    <td><spring:message code="com.adminui.supplier_attribute_management.Summary" text="default text" /></td>
                                    <td></td>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <td colspan="2"><spring:message code="com.adminui.supplier_attribute_management.Total" text="default text" /></td>
                                    <td class="a-right" id="totalSupplierAttributesCount">${totalSupplierAttributesCount}</td>
                                </tr>
                                </tfoot>
                                <tbody>
                                <tr>
                                    <td class="a-center view">
                                        <input id="viewActiveSupplierAttribute" type="checkbox" checked="checked" onclick="filterSupplierAttributeView();"/>
                                        <label for="viewActiveSupplierAttribute"></label>
                                    </td>
                                    <th><spring:message code="com.adminui.supplier_attribute_management.ActiveSupplierAttributes" text="default text" /></th>
                                    <td class="a-right" id="activeSupplierAttributesCount">${numOfActiveSupplierAttribute}</td>
                                </tr>
                                <tr>
                                    <td class="a-center view">
                                        <input id="viewInactiveSupplierAttribute" type="checkbox" checked="checked" onclick="filterSupplierAttributeView();"/>
                                        <label for="viewInactiveSupplierAttribute"></label>
                                    </td>
                                    <th><spring:message code="com.adminui.supplier_attribute_management.InactiveSupplierAttributes" text="default text" /></th>
                                    <td class="a-right" id="inactiveSupplierAttributesCount">${totalSupplierAttributesCount-numOfActiveSupplierAttribute}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="add-slide-blocks">
                <div class="content-block toggle-block active" id="info-section">
                    <div class="headline">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.supplier_attribute_management.SupplierAttributes" text="default text" /></a></h2>
                    </div>
                    <div class="block">
                        <div class="content editable-widget">
                            <!--div class="top-box">
                                <div class="btn-holder">
                                    <a class="btn-add-attribute" onclick="$('#addSupplierIdMapping').show();$('#addAttrBtnHolder').show();"><span><em>ADD</em></span></a>
                                </div>
                            </div-->
                            <div id="supplier_attribute_page_container_div">
                                <jsp:include page="supplier_attribute_table_fragment.jsp" />
                            </div>
                            
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href="<c:url value="/suppliers" />" class="ico-back"><span><spring:message code="com.adminui.supplier_attribute_management.BACK" text="default text" /></span></a></li>
                <!--li><a href='<c:url value="/supplierDetail" />' class="ico-mapping"><span>CREATE SUPPLIER</span></a></li-->
                <c:if test="<%=aclManager.allow(request, Permission.CREATE_SUPPLIER_ATTRIBUTE)%>">
                    <li><a href='<c:url value="/supplierAttributesCreation" />' class="ico-attributes"><span><spring:message code="com.adminui.supplier_attribute_management.ADDATTRIBUTE" text="default text" /></span></a></li>
                </c:if>
                    <!--h3>JUMP TO</h3-->
            </ul>
            <ul class="sub-nav">
                <!--li><a href='#' class="ico-company"><span>SUPPLIER COMPANY LIST</span></a></li-->
            </ul>
        </div>
    </div>
</div>
<script>
    var fromEditAttribute = !!"${param['fromEditAttribute']}";
    //if page redirect by editing supplier attribute.
    if(fromEditAttribute){
        noty({text: '<spring:message code="com.adminui.supplier_attribute_create.ChangesSavedRepublishCatalog" text="default text" />', type: 'success'});
    }
</script>

