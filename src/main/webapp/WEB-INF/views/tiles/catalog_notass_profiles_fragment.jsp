<%@page import="com.vroozi.customerui.profile.model.Profile"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form action="/profiles" id="not-ass-profiles-form" method="post" enctype="text/plain" class="checkboxResetForm">
    <table class="table-data">
        <thead>
            <tr>
                <th class="td-select">
                    <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="nonass-profile-checkall" onclick="toggleAllCheckboxItems(this.checked, 'not-ass-profiles-form')" />
                    <label for="nonass-profile-checkall" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                </th>
                <th class="a-left"><spring:message code="com.adminui.catalog_notass_profiles_fragment.Name" text="default text" /></th>
                <th class="a-center"><spring:message code="com.adminui.catalog_notass_profiles_fragment.Description" text="default text" /></th>
                <th><spring:message code="com.adminui.catalog_notass_profiles_fragment.CreatedON" text="default text" /></th>
                <th><spring:message code="com.adminui.catalog_notass_profiles_fragment.CREATEDBY" text="default text" /></th>
                <th><spring:message code="com.adminui.catalog_notass_profiles_fragment.STATUS" text="default text" /></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="profile" items="${notAssProfiles}" varStatus="cntr1">
                <tr>
                    <td class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="notass-profileIds" id="profile-${profile.profileId}" value="${profile.profileId}" onclick="selectProfile(this);"/>
                        <label for="profile-${profile.profileId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                    </td>
                    <td class="a-left td-description">
                        <div class="field" style="width:150px">

                            <a href='editProfile?profileId=<c:out value="${profile.profileId}"/>'><span>${profile.profileName}</span></a>
                            <input type="text" value="${profile.profileName}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="width:150px;white-space:nowrap;">
                            <span>${profile.profileDesc}</span>
                            <input type="text" value="${profile.profileDesc}" />
                        </div>
                    </td>
                    <td>
                        <div class="a-left field" style="width:150px">
                            <span><%= ViewHelper.getFormattedDate(request,((Profile) pageContext.getAttribute("profile")).getCreatedOn()) %></span>
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
    <input id="currentPageNum" type="hidden" value="${notAssProfileCurrentPageNum}" />
    <input id="totalProfilesCount" type="hidden" value="${totalNotAssProfilesCount}" />
    <input id="notAssProfileTotalPageNum" type="hidden" value="${notAssProfileTotalPageNum}" />
    <input id="catalogId" type="hidden" value="${catalogId}" />
</form>
<c:if test="${totalNotAssProfilesCount > 7}">
    <div class="bottom-data">
        <div class="pager">
            <span><spring:message code="com.adminui.catalog_notass_profiles_fragment.page" text="default text" /> <c:out value="${notAssProfileCurrentPageNum}"/> <spring:message code="com.adminui.catalog_notass_profiles_fragment.of" text="default text" />  <c:out value="${notAssProfileTotalPageNum}"/></span>
            <ul>
                <li><a href="#" class="btn-prev" onclick="${goToPrevNotAssProfilePage}return false;"></a></li>
                <li><a href="#" class="btn-next"  onclick="${goToNextNotAssProfilePage}return false;"></a></li>
            </ul>
        </div>
        <strong class="pages"><spring:message code="com.adminui.catalog_notass_profiles_fragment.TotalRecords" text="default text" /> <c:out value="${notAssProfilePageFirstItemNum}"/> -<c:out value="${notAssProfilePageLastItemNum}"/>  of <c:out value="${totalNotAssProfilesCount}"/></strong>
    </div>
</c:if>
