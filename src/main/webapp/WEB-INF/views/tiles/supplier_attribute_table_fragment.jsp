<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
    import="com.vroozi.customerui.util.ViewHelper,
            com.vroozi.customerui.supplier.model.SupplierAttributes,
            com.vroozi.customerui.acl.model.Permission" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form id="supplier-attributes-form" action="/supplierAttributes" method="post" enctype="text/plain" class="checkboxResetForm">
<table class="table-data wb">
    <thead>
        <tr>
            <th class="td-select">
                <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="supplierAttribute-checkall" onclick="toggleSupplierAttributeCheckboxes(this.checked);" />
                <label for="supplierAttribute-checkall" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
            </th>
            <th class="a-left" style="width: 34px;"><spring:message code="com.adminui.supplier_attribute_table_fragment.ICON" text="default text" /></th>
            <th class="a-left" style="width: 235px;"><spring:message code="com.adminui.supplier_attribute_table_fragment.ATTRIBUTEname" text="default text" /></th>
            <th style="width: 339px;"><spring:message code="com.adminui.supplier_attribute_table_fragment.DESCRIPTION" text="default text" /></th>
            <th style="width: 96px;"><spring:message code="com.adminui.supplier_attribute_table_fragment.STATUS" text="default text" /></th>
        </tr>
    </thead>
    <tbody>

            <c:forEach var="supplierAttribute" items="${supplierAttributes}" varStatus="cntr1">
                <tr>
                    <td class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="supplierAttributeIds" id="${supplierAttribute.attributeId}" value="${supplierAttribute.attributeId}" 
                        		<c:if test="${supplierAttribute.unitId=='-1'}">disabled="disabled"</c:if>
                        		onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');onSupplierCheckboxClick(this);}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');onSupplierCheckboxClick(this);}"/>
                        <label for="${supplierAttribute.attributeId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                    </td>
                    <td class="td-icon">
                        <div class="current-icon">
                            <%
                            if(ViewHelper.isDefaulAttribute((SupplierAttributes)pageContext.getAttribute("supplierAttribute"))) {
                            %>
                                <img src='res/images/${supplierAttribute.attributeImagePath}' alt="image description">
                            <%
                            } else {
                            %>
                            	
                            	<c:if test="${supplierAttribute.attributeImagePath != null }">
                                	<img src='<c:url value="/image/"/>${supplierAttribute.attributeImagePath}?type=supplier' alt="image description">
                                </c:if>
                                
                            <%
                            }
                            %>
                        </div>
                    </td>
                    <td class="a-left td-item-name">
                        <c:if test="${supplierAttribute.unitId != '-1'}">
                            <a href='<c:url value="/supplierAttributesCreation?unitId=${supplierAttribute.unitId}&supplierAttribute=${supplierAttribute.attributeId}" />'>${supplierAttribute.attributeName}</a>
                        </c:if>
                        <c:if test="${supplierAttribute.unitId == '-1'}">
                            <a>${supplierAttribute.attributeName}</a>
                        </c:if>
                    </td>
                    <td>
                        ${supplierAttribute.attributeDescription}
                    </td>
                    <td>
                        ${(supplierAttribute.active)?'Active':'Inactive'}
                    </td>
                </tr>
            </c:forEach>
            <input id="currentPageNum" type="hidden" value="${supplierAttributeCurrentPageNum}" />
            <input id="totalSupplierAttributesCount" type="hidden" value="${totalSupplierAttributesCount}" />
            <input id="numOfActiveSupplierAttribute" type="hidden" value="${numOfActiveSupplierAttribute}" />
            <input id="supplierAttributeTotalPageNum" type="hidden" value="${supplierAttributeTotalPageNum}" />
            <input id="catalogId" type="hidden" value="${catalogId}" />
            <input id="sortBy" type="hidden" value="${sortBy}" />
            <input id="sortDirection" type="hidden" value="${sortDirection}" />
            <input id="searchWithin" type="hidden" value="${searchWithin}" />

    </tbody>
</table>
</form>
<div class="function" id="addAttrBtnHolder" style="display: none;">
	<ul>
		<li><a class="ico-remove" onclick="removeSupplierAttributes(); return false;"><span><em><spring:message code="com.adminui.supplier_attribute_table_fragment.REMOVE" text="default text" /></em></span></a></li>
		<li><input type="button" onclick="createSupplierAttributes(); return false;" value="Save" /></li>
	</ul>
</div>
<c:if test="${totalFilteredSupplierAttributesCount > 7}">
    <div class="bottom-data">
        <div class="pager">
            <span><spring:message code="com.adminui.supplier_attribute_table_fragment.Page" text="default text" /> <c:out value="${supplierAttributeCurrentPageNum}"/> <spring:message code="com.adminui.supplier_attribute_table_fragment.of" text="default text" />  <c:out value="${supplierAttributeTotalPageNum}"/></span>
            <ul>
                <li><a href="#" class="btn-prev" onclick="${goToPrevSupplierAttributePage}return false;"></a></li>
                <li><a href="#" class="btn-next"  onclick="${goToNextSupplierAttributePage}return false;"></a></li>
            </ul>
        </div>
        <strong class="pages"><spring:message code="com.adminui.supplier_attribute_table_fragment.TotalRecords" text="default text" /> <c:out value="${supplierAttributePageFirstItemNum}"/> -<c:out value="${supplierAttributePageLastItemNum}"/>  <spring:message code="com.adminui.supplier_attribute_table_fragment.of" text="default text" /> <c:out value="${totalFilteredSupplierAttributesCount}"/></strong>
    </div>
</c:if>
<c:if test="${(totalFilteredSupplierAttributesCount > 0) && isSupplierAttributePage}">
    <div class="function">
        <ul>
            <c:if test="<%=aclManager.allow(request, Permission.DELETE_SUPPLIER_ATTRIBUTE)%>">
                <li><a href="javascript:void(0)" class="ico-delete delete-supplier-attributes" ><span><em><spring:message code="com.adminui.supplier_attribute_table_fragment.DELETE" text="default text" /></em></span></a></li>
            </c:if>
            <c:if test="<%=aclManager.allow(request, Permission.ACTIVATE_SUPPLIER_ATTRIBUTE)%>">
                <li><a href="javascript:void(0)" class="ico-approve" onclick="if(isAnySupplierAttributeChecked())activeSupplierAttributes(true);else alert('Please select an item');"><span><em><spring:message code="com.adminui.supplier_attribute_table_fragment.Activate" text="default text" /></em></span></a></li>
            </c:if>
            <c:if test="<%=aclManager.allow(request, Permission.DEACTIVATE_SUPPLIER_ATTRIBUTE)%>">
                <li><a href="javascript:void(0)" class="ico-reject" onclick="if(isAnySupplierAttributeChecked())activeSupplierAttributes(false);else alert('Please select an item');"><span><em><spring:message code="com.adminui.supplier_attribute_table_fragment.Deactivate" text="default text" /></em></span></a></li>
            </c:if>
        </ul>
    </div>
</c:if>

