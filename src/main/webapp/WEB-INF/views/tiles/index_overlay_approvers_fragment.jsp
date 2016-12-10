<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div id="approversDiv">
    <table class="table-data" id="approversTable">

            <fieldset>
                <thead>
                    <tr>
                        <th class="td-select">
                            <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all-lightbox-3" id="check-lightbox-3-1A" onclick="toggleAllCheckboxItems(this.checked, 'approversTable');" />
                            <label for="check-lightbox-3-1A" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                        </th>
                        <th class="a-left"><spring:message code="com.adminui.index_overlay_approvers_fragment.NAME" text="default text" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="approver" items="${approverList}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <input type="checkbox" class="ui-helper-hidden-accessible" name="approverIds" id="approvers-${approver.userId}" value="${approver.userId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                                <label for="approvers-${approver.userId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
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
            
    </table>
</div>
