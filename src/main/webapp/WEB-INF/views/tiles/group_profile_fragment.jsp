<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form action="associateProfileToGroup" id="associateProfileToGroup" method="post" class="checkboxResetForm">
    <fieldset>
        <c:if test="${notAssociatedProfileCount > 0}">
            <div class="lightbox-content editable-widget">
            <table class="table-data">
                <thead>
                    <tr>
                <th class="td-select">
                    <input type="checkbox" class="ui-helper-hidden-accessible" name="check-lightbox-all" id="check-lightbox-1" onclick="toggleAllCheckboxItems(this.checked, 'associateProfileToGroup');"/>
                    <label for="check-lightbox-1" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                </th>
                <th class="a-left"><a class="sorting" href="#" onclick="sortNAProfiles();return false;"><spring:message code="com.adminui.group_profile_fragment.ContentViewName" text="default text" /></a></th>
                <th class="a-left"><spring:message code="com.adminui.group_profile_fragment.Description" text="default text" /></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="profile" items="${profiles}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <input type="checkbox" class="ui-helper-hidden-accessible" name="associatedProfileIds" id="group-${profile.profileId}" value="${profile.profileId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                                <label for="group-${profile.profileId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                            </td>
                            <td class="a-left td-description">
                                <div class="field">
                                <span><a href='editProfile?profileId=<c:out value="${profile.profileId}"/>'><c:out value="${profile.profileName}"/></a></span>
                                <input type="text" value="${profile.profileName}" />
                                </div>
                            </td>
                            <td class="a-left">${profile.profileDesc}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${notAssociatedProfileCount > 7}">
                <div class="bottom-data">
                    <div class="pager">
                        <span><spring:message code="com.adminui.group_profile_fragment.Page" text="default text" /> <c:out value="${profileCurrentPageNum}"/> <spring:message code="com.adminui.group_profile_fragment.of" text="default text" />  <c:out value="${profilesTotalPageNum}"/></span>
                        <ul>
                            <li><a href="#" class="btn-prev" onclick="${goToPrevNAProfilePage}return false;"></a></li>
                            <li><a href="#" class="btn-next"  onclick="${goToNextNAProfilePage}return false;"></a></li>
                        </ul>
                    </div>
                    <strong class="pages"><spring:message code="com.adminui.group_profile_fragment.TotalRecords" text="default text" /> <c:out value="${profilePageFirstItemNum}"/> -<c:out value="${profilePageLastItemNum}"/>  <spring:message code="com.adminui.group_profile_fragment.of" text="default text" /> <c:out value="${notAssociatedProfileCount}"/></strong>
                </div>
            </c:if>
        </div>
            <div class="btns-holder">
                <a href="javascript:void(0);" onclick="$.colorbox.close();" class="btn-cancel"><spring:message code="com.adminui.group_profile_fragment.Cancel" text="default text" /></a>
                <input type="submit" value="Save" onclick="associateProfiletoGroup();return false;"/>
                <p><span class="required">* </span><spring:message code="com.adminui.group_profile_fragment.RequiredField" text="default text" /></p>
            </div>
        </c:if>
    </fieldset>
    <input id="groupProfileTotalPageNum" type="hidden" value="${profilesTotalPageNum}" />
    <input id="groupId" name="groupId" type="hidden" value="${groupId}" />
    <input id="sortBy" type="hidden" value="${sortBy}" />
    <input id="sortDirectionP" type="hidden" value="${sortDirection}" />
    <input id="searchWithinP" type="hidden" value="${searchWithin}" />
    <input id="groupProfileCurrentPageNum" type="hidden" value="${profileCurrentPageNum}" />
</form>
