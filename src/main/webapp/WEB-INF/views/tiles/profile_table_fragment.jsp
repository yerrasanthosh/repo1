<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<%@ page import="com.vroozi.customerui.util.ViewHelper" %>
<%@ page import="com.vroozi.customerui.profile.model.Profile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<form action="/profiles" id="profiles-form" method="post" enctype="text/plain" class="checkboxResetForm">
    <table class="table-data">
        <thead>
        <tr>
            <th class="td-select">
                <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="profile-checkall" onclick="toggleAllCheckboxItems(this.checked, 'profiles-form');" />
                <label for="profile-checkall" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
            </th>
            <c:if test='${ empty catalogId}'>
                <th class="a-left">
                    <a onclick="sortProfiles('Name');" class="a-left sorting"><spring:message code="com.adminui.profile_table_fragment.Name" text="default text" /></a>
                </th>
            </c:if>
            <c:if test='${ not empty catalogId}'>
                <th class="a-left">
                    <spring:message code="com.adminui.profile_table_fragment.Name" text="default text" />
                </th>
            </c:if>
            <th class="a-left"><spring:message code="com.adminui.profile_table_fragment.Description" text="default text" /></th>
            <c:if test='${ empty catalogId}'>
                <th class="a-left">
                    <a onclick="sortProfiles('Date');" class="a-left sorting"><spring:message code="com.adminui.profile_table_fragment.CreatedON" text="default text" /></a>
                </th>
            </c:if>
            <c:if test='${ not empty catalogId}'>
                <th class="a-left">
                    <spring:message code="com.adminui.profile_table_fragment.CreatedON" text="default text" />
                </th>
            </c:if>
            <th><spring:message code="com.adminui.profile_table_fragment.CREATEDBY" text="default text" /></th>
            <th><spring:message code="com.adminui.profile_table_fragment.STATUS" text="default text" /></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="profile" items="${profiles}" varStatus="cntr1">
                <tr>
                    <td class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="profileIds" id="profile-${profile.profileId}" value="${profile.profileId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                        <label for="profile-${profile.profileId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                    </td>
                    <td class="a-left td-description">
                        <div class="field" style="width:150px">

                            <a href='editProfile?profileId=<c:out value="${profile.profileId}"/>'><span>${profile.profileName}</span></a>
                            <input type="text" value="${profile.profileName}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="white-space:nowrap; width:150px">
                            <span>${profile.profileDesc}</span>
                            <input type="text" value="${profile.profileDesc}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="width:150px">
                            <span><%=ViewHelper.getFormattedDate(request, ((Profile) pageContext.getAttribute("profile")).getCreatedOn())%></span>
                            <input type="text" value="${profile.createdOn}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="width:150px">
                            <span>${profile.createdBy}</span>
                            <input type="text" value="${profile.createdBy}" />
                        </div>
                    </td>
                    <td class="a-left td-last">${(profile.active)?'Active':'Inactive'}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <input id="currentPageNum" type="hidden" value="${profileCurrentPageNum}" />
    <input id="totalProfilesCount" type="hidden" value="${totalProfilesCount}" />
    <input id="numOfActiveProfile" type="hidden" value="${numOfActiveProfile}" />
    <input id="profileTotalPageNum" type="hidden" value="${profileTotalPageNum}" />
    <input id="catalogId" type="hidden" value="${catalogId}" />
    <input id="sortBy" type="hidden" value="${sortBy}" />
    <input id="sortDirection" type="hidden" value="${sortDirection}" />
    <input id="searchWithin" type="hidden" value="${searchWithin}" />
</form>
<c:if test="${totalFilteredProfilesCount > 7}">
    <div class="bottom-data">
        <div class="pager">
            <span><spring:message code="com.adminui.profile_table_fragment.Page" text="default text" /> <c:out value="${profileCurrentPageNum}"/> <spring:message code="com.adminui.profile_table_fragment.of" text="default text" />  <c:out value="${profileTotalPageNum}"/></span>
            <ul>
                <li><a href="#" class="btn-prev" onclick="${goToPrevProfilePage}return false;"></a></li>
                <li><a href="#" class="btn-next"  onclick="${goToNextProfilePage}return false;"></a></li>
            </ul>
        </div>
        <strong class="pages"><spring:message code="com.adminui.profile_table_fragment.TotalRecords" text="default text" /> <c:out value="${profilePageFirstItemNum}"/> -<c:out value="${profilePageLastItemNum}"/>  <spring:message code="com.adminui.profile_table_fragment.of" text="default text" /> <c:out value="${totalFilteredProfilesCount}"/></strong>
    </div>
</c:if>
<c:if test="${(totalFilteredProfilesCount > 0) && isProfilePage}">
    <div class="function">
        <ul>
            <c:if test="<%=aclManager.allow(request, Permission.DELETE_CONTENT_VIEW)%>">
                <li><a href="javascript:void(0)" style="display: none;" id="btnDelete" class="ico-delete" onclick="deleteProfileProfiles(this)" ><span><em><spring:message code="com.adminui.profile_table_fragment.DELETE" text="default text" /></em></span></a></li>
            </c:if>
            <c:if test="<%=aclManager.allow(request, Permission.ACTIVATE_CONTENT_VIEW)%>">
                <li><a href="javascript:void(0)" style="display: none;" id="btnActive" class="ico-approve" onclick="if(isAnyItemChecked('profiles-form'))activeProfiles(true);else alert('Please select an item');"><span><em><spring:message code="com.adminui.profile_table_fragment.Activate" text="default text" /></em></span></a></li>
            </c:if>
            <c:if test="<%=aclManager.allow(request, Permission.DEACTIVATE_CONTENT_VIEW)%>">
                <li><a href="javascript:void(0)" style="display: none;" id="btnInactive" class="ico-reject" onclick="if(isAnyItemChecked('profiles-form'))activeProfiles(false);else alert('Please select an item');"><span><em><spring:message code="com.adminui.profile_table_fragment.Deactivate" text="default text" /></em></span></a></li>
            </c:if>
        </ul>
    </div>
</c:if>
