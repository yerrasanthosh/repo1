<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
    import="com.vroozi.customerui.acl.model.Permission"
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<div id='catalog-suppliers-approvers-div' class="fixed-table-wrapper" style="position: relative;">
    <table class="table-data">

            <thead>
                <tr>
                    <th class="td-select" style="width: 17px; height: 26px;"></th>
                    <th class="a-left" style="width: 734px; height: 15px;"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="approver" items="${approversForCatalogSupplier}" varStatus="cntr1">
                    <tr>
                        <td class="td-select">
                            <input type="checkbox" id="dapprover-${approver.userId}" name="approverIds" class="target-chbox ui-helper-hidden-accessible" value="${approver.userId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}" >
                            <label for="dapprover-${approver.userId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text"></span></label>
                        </td>
                        <td class="a-left td-username">
                            <div class="field">
                                <span>${approver.username}</span>
                                <input type="text" value="${approver.username}" />
                                <em></em>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            </tfoot>
            <input name="catalogId" type="hidden" value='<c:out value="${param['catalogId']}"/>' />
            <input id="approverCurrentPageNum" type="hidden" value="${approverCurrentPageNum}" />
            <input id="totalApproverCount" type="hidden" value="${totalApproverCount}" />
            <input id="approverTotalPageNum" type="hidden" value="${approverTotalPageNum}" />

    </table>
    <br />
    <c:if test="${totalApproverCount > pageSize}">
        <div class="bottom-data">
            <div class="pager">
                <span><spring:message code="com.adminui.details_approvers_fragment.Page" text="default text" /> <c:out value="${approverCurrentPageNum}"/> <spring:message code="com.adminui.details_approvers_fragment.of" text="default text" />  <c:out value="${approverTotalPageNum}"/></span>
                <ul>
                    <li><a href="#" class="btn-prev" onclick="goToCatalogApproverPage(${(approverCurrentPageNum-1)}, '<c:out value="${param['catalogId']}"/>');return false;"></a></li>
                    <li><a href="#" class="btn-next"  onclick="goToCatalogApproverPage(${(approverCurrentPageNum+1)},'<c:out value="${param['catalogId']}"/>');return false;"></a></li>
                </ul>
                </div>
                <strong class="pages"><spring:message code="com.adminui.details_approvers_fragment.TotalRecords" text="default text" /> <c:out value="${approverPageFirstItemNum}"/> -<c:out value="${approverPageLastItemNum}"/>  <spring:message code="com.adminui.details_approvers_fragment.of" text="default text" /> <c:out value="${totalApproverCount}"/></strong>
            </div>
    </c:if>
    <br /><br />
    <c:if test="${totalApproverCount > 0}">
        <div class="function">
            <ul>
                <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                    <li class="delete-approvers" id="remove-approver-btn" style="padding-right:0px;" ><a href="javascript:void(0)" class="ico-remove" ><span><em><spring:message code="com.adminui.details_approvers_fragment.REMOVE" text="default text" /></em></span></a></li>
                </c:if>
            </ul>
        </div>
    </c:if>
    <div class="fixed-table-header" style="width: 770px; height: 26px; position: absolute; top: 0px; left: 0px; z-index: 10;">
        <table class="table-data">
            <thead>
                <tr>
                    <th class="td-select" style="width: 17px;">
                        <input type="checkbox" id="check-all3" name="check-all3" class="check-allbox ui-helper-hidden-accessible"   onclick="toggleAllCheckboxItems(this.checked, 'assignApproversForm');"/>
                        <label for="check-all3" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text"></span></label>
                    </th>
                    <th class="a-left" style="width: 734px;"><spring:message code="com.adminui.details_approvers_fragment.EMAIL" text="default text" /></th>
                </tr>
            </thead>
        </table>
    </div>
 </div>