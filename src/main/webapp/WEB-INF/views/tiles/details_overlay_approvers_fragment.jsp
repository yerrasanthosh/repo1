<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div id="catalogNotAssociateApproverDiv">
    <table class="table-data" id="catalogNotAssociateApproverTable">

            <fieldset>
                <thead>
                    <tr>
                        <th class="td-select">
                            <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all-lightbox-3" id="check-lightbox-3-1" onclick="toggleAllCheckboxItems(this.checked, 'addApprover-form');" />
                            <label for="check-lightbox-3-1" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                        </th>
                        <th class="a-left"><spring:message code="com.adminui.details_overlay_approvers_fragment.NAME" text="default text" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="approver" items="${nonApprovers}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <input type="checkbox" class="ui-helper-hidden-accessible" name="approverIds" id="approver-${approver.userId}" value="${approver.userId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                                <label for="approver-${approver.userId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                            </td>
                            <td class="a-left td-username">
                                <div class="field">
                                    <span>${approver.username}</span>
                                    <input type="text" value="${approver.username}" />
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                </tfoot>
            </fieldset>
            <input type="hidden" name="catalogId" value="${param['catalogId']}"/>
            <input id="nonApproverCurrentPageNum" type="hidden" value="${nonApproverCurrentPageNum}" />
            <input id="totalNonApproverCount" type="hidden" value="${totalNonApproverCount}" />
            <input id="nonApproverTotalPageNum" type="hidden" value="${nonApproverTotalPageNum}" />

    </table>

    <c:if test="${totalNonApproverCount > 7}">
        <div class="bottom-data">
            <div class="pager">
                <span>Page <c:out value="${nonApproverCurrentPageNum}"/> <spring:message code="com.adminui.details_overlay_approvers_fragment.of" text="default text" />  <c:out value="${nonApproverTotalPageNum}"/></span>
                <ul>
                    <li><a href="#" class="btn-prev" onclick="goToNonCatalogApproverPage(${(nonApproverCurrentPageNum-1)}, '<c:out value="${param['catalogId']}"/>');return false;"></a></li>
                    <li><a href="#" class="btn-next"  onclick="goToNonCatalogApproverPage(${(nonApproverCurrentPageNum+1)}, '<c:out value="${param['catalogId']}"/>');return false;"></a></li>
                </ul>
            </div>
            <strong class="pages"><spring:message code="com.adminui.details_overlay_approvers_fragment.TotalRecords" text="default text" /> <c:out value="${nonApproverPageFirstItemNum}"/> -<c:out value="${nonApproverPageLastItemNum}"/>  <spring:message code="com.adminui.details_overlay_approvers_fragment.of" text="default text" /> <c:out value="${totalNonApproverCount}"/></strong>
        </div>
    </c:if>
    <br />
    <c:if test="${totalNonApproverCount > 0}">
        <div class="btns-holder">
            <a class="btn-cancel" onclick="$.colorbox.close();"><spring:message code="com.adminui.details_overlay_approvers_fragment.Cancel" text="default text" /></a>
            <a class="btn-create close"  onclick="addCatalogApprovers();"><span><spring:message code="com.adminui.details_overlay_approvers_fragment.Save" text="default text" /></span></a>
            <p><span class="required">* </span><spring:message code="com.adminui.details_overlay_approvers_fragment.RequiredField" text="default text" /></p>
        </div>
    </c:if>
</div>
