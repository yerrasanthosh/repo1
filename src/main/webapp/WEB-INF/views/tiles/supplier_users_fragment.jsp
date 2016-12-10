<%@ page import="com.vroozi.customerui.util.ViewHelper" %>
<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="content editable-widget">

    <form action="/supplierUsers" id="supplier-users-form" method="post" enctype="text/plain">
        <table class="table-data">
        
            <thead>
            <tr>
                <th class="td-select">
                    <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="supplier-users-checkall" onclick="toggleAllCheckboxItems(this.checked, 'supplier-users-form');" />
                    <label for="supplier-users-checkall" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                </th>
                <th class="a-left"><spring:message code="com.adminui.supplier_users_fragment.USERNAME" text="default text" /></th>
                <th><spring:message code="com.adminui.supplier_users_fragment.Createdon" text="default text" /></th>
                <th><spring:message code="com.adminui.supplier_users_fragment.createdby" text="default text" /></th>
                <th><spring:message code="com.adminui.supplier_users_fragment.status" text="default text" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="supplierUser" items="${pagedSupplierUserList}" varStatus="cntr1">
                <tr>
                    <td class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="supplierUserIds" id="user-${supplierUser.userId}" value="${supplierUser.userId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                        <label for="user-${supplierUser.userId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>

                    </td>
                    <% String roleNames = ""; String delim = ""; boolean master = false; %>
                    <c:set var="master1"> <%=master%> </c:set>
                    <c:choose>
                        <c:when test="${loggedInUserId==supplierUser.userId}">
                            <td class="a-left td-item-name"><a href="myAccount">${supplierUser.username}</a></td>
                        </c:when>
                        <c:otherwise>
                            <td class="a-left td-item-name" style="cursor: pointer;"><a onclick="openEditUser('${supplierUser.firstName}','${supplierUser.lastName}','${supplierUser.username}','${supplierId}','${supplierUser.userId}','${supplierUser.password}','${supplierUser.userStatus}');">${supplierUser.username}</a></td>
                        </c:otherwise>
                    </c:choose>
                    <td><%=ViewHelper.getFormattedDate(request, ((User) pageContext.getAttribute("supplierUser")).getCreatedOn())%></td>
                    <td>${supplierUser.createdByName}</td>
                    <c:choose>
                    <c:when test="${supplierUser.userStatus=='Inactive'}">
                        <td>${supplierUser.userStatus}</td>
                    </c:when>
                    <c:otherwise>
                        <td class="a-center td-item-name"><a style="cursor: pointer;" onclick="openEditUser('${supplierUser.firstName}','${supplierUser.lastName}','${supplierUser.username}','${supplierId}','${supplierUser.userId}','${supplierUser.password}','${supplierUser.userStatus}');">${supplierUser.userStatus}</a></td>
                    </c:otherwise>
                    </c:choose>
                    <td style="display: none;"><input type="hidden" id="userprfs-${supplierUser.userId}" value="${fn:length(supplierUser.assignedProfiles)}" /> </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <input id="supplierUserCurrentPageNum" type="hidden" value="${supplierUserCurrentPageNum}" />
        <input id="totalSupplierUserCount" type="hidden" value="${totalSupplierUserCount}" />
        <input id="supplierUserTotalPageNum" type="hidden" value="${supplierUserTotalPageNum}" />
        <input id="userSupplierId" type="hidden" value='${supplierId}' />
    </form>
    <br />
    <c:if test="${totalSupplierUserCount > 7}">
        <div class="bottom-data">
            <div class="pager">
                <span><spring:message code="com.adminui.supplier_users_fragment.Page" text="default text" /> <c:out value="${supplierUserCurrentPageNum}"/> <spring:message code="com.adminui.supplier_users_fragment.of" text="default text" />  <c:out value="${supplierUserTotalPageNum}"/></span>
                <ul>
                    <li><a href="#" class="btn-prev" onclick="goToSupplierUserPage(${(supplierUserCurrentPageNum-1)},${supplierId});return false;"></a></li>
                    <li><a href="#" class="btn-next" onclick="goToSupplierUserPage(${(supplierUserCurrentPageNum+1)},${supplierId});return false;"></a></li>
                </ul>
            </div>
            <strong class="pages"><spring:message code="com.adminui.supplier_users_fragment.TotalRecords" text="default text" /> <c:out value="${supplierUserPageFirstItemNum}"/> -<c:out value="${supplierUserPageLastItemNum}"/>  <spring:message code="com.adminui.supplier_users_fragment.of" text="default text" /> <c:out value="${totalSupplierUserCount}"/></strong>
        </div>
    </c:if>
    <div class="function">
        <ul>
            <c:if test="${(totalSupplierUserCount > 0)}">
                <li><a class="ico-delete" onclick="deleteSupplierUsersConfirm();"><span><em><spring:message code="com.adminui.supplier_users_fragment.DELETE" text="default text" /></em></span></a></li>
                <li><a class="ico-activate" onclick="if(isAnyItemChecked('supplier-users-form'))updateSupplierUsers(true);else alert('Please select an item');" ><span><em><spring:message code="com.adminui.supplier_users_fragment.ACTIVATE" text="default text" /></em></span></a></li>
                <li><a class="ico-deactivate" onclick="if(isAnyItemChecked('supplier-users-form'))updateSupplierUsers(false);else alert('Please select an item');"><span><em><spring:message code="com.adminui.supplier_users_fragment.DeACTIVATE" text="default text" /></em></span></a></li>
            </c:if>
        </ul>
    </div>
</div>