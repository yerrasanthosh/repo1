<%@page import="com.vroozi.customerui.profile.model.Profile"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table id="tblProfiles" class="table-data">
        <thead>
            <tr>
                <th class="td-select">
                    <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="nonass-profile-checkall23" onclick="toggleAllCheckboxItems(this.checked, 'tblProfiles')" />
                    <label for="nonass-profile-checkall23" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                </th>
                <th class="a-left"><spring:message code="com.adminui.index_overlay_profiles_fragment.Name" text="default text" /></th>
                <th class="a-center"><spring:message code="com.adminui.index_overlay_profiles_fragment.Description" text="default text" /></th>
                <th><spring:message code="com.adminui.index_overlay_profiles_fragment.CreatedON" text="default text" /></th>
                <th><spring:message code="com.adminui.index_overlay_profiles_fragment.CREATEDBY" text="default text" /></th>
                <th><spring:message code="com.adminui.index_overlay_profiles_fragment.STATUS" text="default text" /></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="profile" items="${profileList}" varStatus="cntr1">
                <tr>
                    <td class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="profileIds" id="profiles-${profile.profileId}" value="${profile.profileId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                        <label for="profiles-${profile.profileId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
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

