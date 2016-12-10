<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<%@ page import="com.vroozi.customerui.util.ViewHelper" %>
<%@ page import="com.vroozi.customerui.supplier.model.Supplier" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<form action="/suppliers" id="suppliers-form" method="post" enctype="text/plain" class="checkboxResetForm">
    <table class="table-data wb">
        <thead>
            <tr>
                <th class="td-select">
                    <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="supplier-checkall" onclick="toggleAllCheckboxItems(this.checked, 'suppliers-form');" />
                    <label for="supplier-checkall" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                </th>
                <th class="a-left">
                    <a onclick="sortSuppliers('Name');" class="a-left sorting"><spring:message code="com.adminui.supplier_table_fragment.CompanyName" text="default text" /></a>
                </th>
                <th>
                    <a onclick="sortSuppliers('Date');" class="a-left sorting"><spring:message code="com.adminui.supplier_table_fragment.CreatedON" text="default text" /></a>
                </th>
                <th>
                    <a onclick="sortSuppliers('Creator');" class="sorting"><spring:message code="com.adminui.supplier_table_fragment.CREATEDBY" text="default text" /></a>
                </th>
                <th  style="width: 112px;">
                    <a onclick="sortSuppliers('Status')" class="sorting"><spring:message code="com.adminui.supplier_attribute_table_fragment.STATUS" text="default text" /></a>
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="supplier" items="${suppliers}" varStatus="cntr1">
                <tr>
                    <td class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="companyIds" id="${supplier.companyId}" value="${supplier.companyId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                        <label for="${supplier.companyId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                    </td>
                    <td class="a-left td-item-name">
                        <a href='<c:url value="/supplierDetail?unitId=${supplier.unitId}&supplierId=${supplier.companyId}" />'>${supplier.companyName}</a>
                    </td>
                    <td>
                        <%=ViewHelper.getFormattedDate(request, ((Supplier) pageContext.getAttribute("supplier")).getCreatedOn())%>
                    </td>
                    <td>
                        ${supplier.createdBy}
                    </td>
                    <td>${(supplier.active)?'Active':'Inactive'}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <input id="currentPageNum" type="hidden" value="${supplierCurrentPageNum}" />
    <input id="totalSuppliersCount" type="hidden" value="${totalSuppliersCount}" />
    <input id="numOfActiveSupplier" type="hidden" value="${numOfActiveSupplier}" />
    <input id="supplierTotalPageNum" type="hidden" value="${supplierTotalPageNum}" />
    <input id="catalogId" type="hidden" value="${catalogId}" />
    <input id="sortBy" type="hidden" value="${sortBy}" />
    <input id="sortDirection" type="hidden" value="${sortDirection}" />
    <input id="searchWithin" type="hidden" value="${searchWithin}" />
</form>
<c:if test="${totalFilteredSuppliersCount > 7}">
    <div class="bottom-data">
        <div class="pager">
            <span><spring:message code="com.adminui.supplier_table_fragment.Page" text="default text" /> <c:out value="${supplierCurrentPageNum}"/> <spring:message code="com.adminui.supplier_table_fragment.of" text="default text" />  <c:out value="${supplierTotalPageNum}"/></span>
            <ul>
                <li><a href="#" class="btn-prev" onclick="${goToPrevSupplierPage}return false;"></a></li>
                <li><a href="#" class="btn-next"  onclick="${goToNextSupplierPage}return false;"></a></li>
            </ul>
        </div>
        <strong class="pages"><spring:message code="com.adminui.supplier_table_fragment.TotalRecords" text="default text" /> <c:out value="${supplierPageFirstItemNum}"/> -<c:out value="${supplierPageLastItemNum}"/>  of <c:out value="${totalFilteredSuppliersCount}"/></strong>
    </div>
</c:if>
<c:if test="${ (totalFilteredSuppliersCount > 0) && isSupplierPage}">
    <div class="function">
        <ul>
        <c:if test="<%=aclManager.allow(request, Permission.DELETE_SHOPPER)%>">
            <li><a href="javascript:void(0)" class="ico-delete delete-supplier-suppliers" ><span><em><spring:message code="com.adminui.supplier_table_fragment.DELETE" text="default text" /></em></span></a></li>
        </c:if>
        <c:if test="<%=aclManager.allow(request, Permission.ACTIVATE_SUPPLIER)%>">
            <li><a href="javascript:void(0)" class="ico-approve" onclick="if(isAnyItemChecked('suppliers-form'))activeSuppliers(true);else alert('Please select an item');"><span><em><spring:message code="com.adminui.supplier_table_fragment.Activate" text="default text" /></em></span></a></li>
        </c:if>
        <c:if test="<%=aclManager.allow(request, Permission.DEACTIVATE_SUPPLIER)%>">
            <li><a href="javascript:void(0)" class="ico-reject" onclick="if(isAnyItemChecked('suppliers-form'))activeSuppliers(false);else alert('Please select an item');"><span><em><spring:message code="com.adminui.supplier_table_fragment.Deactivate" text="default text" /></em></span></a></li>
        </c:if>
        </ul>
    </div>
</c:if>

