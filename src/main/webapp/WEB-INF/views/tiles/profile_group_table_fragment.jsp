<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<%@ page import="com.vroozi.customerui.profile.model.ProfileGroup" %>
<%@ page import="com.vroozi.customerui.util.ViewHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<form action="/profileGroups" id="profiles-group-form" method="post" enctype="text/plain" class="checkboxResetForm">
    <table class="table-data">
        <thead>
        <tr>
            <th class="td-select">
                <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="profile-group-checkall" onclick="toggleAllCheckboxItems(this.checked, 'profiles-group-form');" />
                <label for="profile-group-checkall" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
            </th>
            <c:if test='${ empty catalogId}'>
                <th class="a-left">
                    <a onclick="sortProfileGroups('Name');" class="a-left sorting"><spring:message code="com.adminui.profile_group_table_fragment.Name" text="default text" /></a>
                </th>
            </c:if>
            <c:if test='${ not empty catalogId}'>
                <th class="a-left">
                    <spring:message code="com.adminui.profile_group_table_fragment.Name" text="default text" />
                </th>
            </c:if>
            <th class="a-left"><spring:message code="com.adminui.profile_group_table_fragment.Token" text="default text" /></th>
            <c:if test='${ empty catalogId}'>
                <th class="a-left">
                    <a onclick="sortProfileGroups('Date');" class="a-left sorting"><spring:message code="com.adminui.profile_group_table_fragment.CreatedON" text="default text" /></a>
                </th>
            </c:if>
            <c:if test='${ not empty catalogId}'>
                <th class="a-left">
                    <spring:message code="com.adminui.profile_group_table_fragment.CreatedON" text="default text" />
                </th>
            </c:if>
            <th><spring:message code="com.adminui.profile_group_table_fragment.CREATEDBY" text="default text" /></th>
            <th><spring:message code="com.adminui.profile_group_table_fragment.STATUS" text="default text" /></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="profileGroup" items="${profileGroups}" varStatus="cntr1">
  				<tr>
                    <td class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="profileGroupIds" id="pg-${profileGroup.groupId}" value="${profileGroup.groupId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                        <label for="pg-${profileGroup.groupId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                    </td>
                    <td class="a-left td-description">
                        <div class="field" style="width:150px">

                            <a href='editProfileGroup?profileGroupId=<c:out value="${profileGroup.groupId}"/>'><span>${profileGroup.groupName}</span></a>
                            <input type="text" value="${profileGroup.groupName}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="white-space:nowrap; width:150px">
                            <span>${profileGroup.token}</span>
                            <input type="text" value="${profileGroup.token}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="width:150px">
                            <span><%=ViewHelper.getFormattedDate(request, ((ProfileGroup) pageContext.getAttribute("profileGroup")).getCreatedOn())%></span>
                            <input type="text" value="${profileGroup.createdOn}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="width:150px">
                            <span>${profileGroup.createdBy}</span>
                            <input type="text" value="${profileGroup.createdBy}" />
                        </div>
                    </td>
                    <td class="a-left td-last">${(profileGroup.active)?'Active':'Inactive'}</td>
                </tr>				

            </c:forEach>
        </tbody>
    </table>
    <input id="currentPageNum" type="hidden" value="${profileGroupCurrentPageNum}" />
    <input id="totalProfilesGroupCount" type="hidden" value="${totalProfilesGroupCount}" />
    <input id="numOfActiveProfileGroup" type="hidden" value="${numOfActiveProfileGroup}" />
    <input id="profileGroupTotalPageNum" type="hidden" value="${groupProfileTotalPageNum}" />
    <input id="groupId" type="hidden" value="${groupId}" />
    <input id="sortBy" type="hidden" value="${sortBy}" />
    <input id="sortDirection1" type="hidden" value="${sortDirection}" />
    <input id="searchWithin1" type="hidden" value="${searchWithin}" />
</form>
<c:if test="${totalFilteredProfileGroupCount > 7}">
    <div class="bottom-data">
        <div class="pager">
            <span><spring:message code="com.adminui.profile_group_table_fragment.Page" text="default text" /> <c:out value="${profileGroupCurrentPageNum}"/> <spring:message code="com.adminui.profile_group_table_fragment.of" text="default text" />  <c:out value="${profileGroupTotalPageNum}"/></span>
            <ul>
                <li><a href="#" class="btn-prev" onclick="${goToPrevGroupPage}return false;"></a></li>
                <li><a href="#" class="btn-next"  onclick="${goToNextGroupPage}return false;"></a></li>
            </ul>
        </div>
        <strong class="pages"><spring:message code="com.adminui.profile_group_table_fragment.TotalRecords" text="default text" /> <c:out value="${profileGroupPageFirstItemNum}"/> -<c:out value="${profileGroupPageLastItemNum}"/>  <spring:message code="com.adminui.profile_group_table_fragment.of" text="default text" /> <c:out value="${totalFilteredProfileGroupCount}"/></strong>
    </div>
</c:if>
<c:if test="${(totalFilteredProfileGroupCount > 0) && isProfileGroupPage}">
    <div class="function">
        <ul>
            <c:if test="<%=aclManager.allow(request, Permission.DELETE_CONTENT_VIEW_GROUP)%>">
                <li><a href="javascript:void(0)" style="display: none;" id="btnDelete1" class="ico-delete" onclick="deleteProfileGroupsConfirm()" ><span><em><spring:message code="com.adminui.profile_group_table_fragment.DELETE" text="default text" /></em></span></a></li>
            </c:if>
            <c:if test="<%=aclManager.allow(request, Permission.ACTIVATE_CONTENT_VIEW_GROUP)%>">
                <li><a href="javascript:void(0)" style="display: none;" id="btnActive1" class="ico-approve" onclick="if(isAnyItemChecked('profiles-group-form'))activeProfileGroups(true);else alert('Please select an item');"><span><em><spring:message code="com.adminui.profile_group_table_fragment.Activate" text="default text" /></em></span></a></li>
            </c:if>
            <c:if test="<%=aclManager.allow(request, Permission.DEACTIVATE_CONTENT_VIEW_GROUP)%>">
                <li><a href="javascript:void(0)" style="display: none;" id="btnInactive1" class="ico-reject" onclick="if(isAnyItemChecked('profiles-group-form'))activeProfileGroups(false);else alert('Please select an item');"><span><em><spring:message code="com.adminui.profile_group_table_fragment.Deactivate" text="default text" /></em></span></a></li>
            </c:if>
        </ul>
    </div>
</c:if>
