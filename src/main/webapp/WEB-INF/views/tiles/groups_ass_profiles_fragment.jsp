<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${totalProfileGroupsCount > 0}">
    <form action="associatedProfilesToGroupForm" id="associatedProfilesToGroupForm" method="post">
        <fieldset>
            <table class="table-data">
                <thead>
                    <tr>
                    <th class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all4" id="check4-1" onclick="toggleAllCheckboxItems(this.checked, 'associatedProfilesToGroupForm');"/>
                        <label for="check4-1" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                    </th>
                    <th class="a-left"><a class="sorting" href="#" onclick="sortGroupProfiles();return false;"><spring:message code="com.adminui.groups_ass_profiles_fragment.ContentViewName" text="default text" /></a></th>
                    <th class="a-left"><spring:message code="com.adminui.groups_ass_profiles_fragment.ContentViewDescription" text="default text" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="associatedProfile" items="${associatedProfiles}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <input type="checkbox" class="ui-helper-hidden-accessible" name="associatedProfileIds" id="profile-${associatedProfile.profileId}" value="${associatedProfile.profileId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                                <label for="profile-${associatedProfile.profileId}"  aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                            </td>
                            <td class="a-left td-description">
                                <div class="field">
                                    <span><a href='editProfile?profileId=<c:out value="${associatedProfile.profileId}"/>'><c:out value="${associatedProfile.profileName}"/></a></span>
                                    <input type="text" value="${associatedProfile.profileName}" />
                                </div>
                            </td>
                            <td class="a-left">${associatedProfile.profileDesc}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${totalProfileGroupsCount > 7}">
                <div class="bottom-data">
                    <div class="pager">
                        <span><spring:message code="com.adminui.groups_ass_profiles_fragment.Page" text="default text" /> <c:out value="${groupProfileCurrentPageNum}"/> <spring:message code="com.adminui.groups_ass_profiles_fragment.of" text="default text" />  <c:out value="${groupProfileTotalPageNum}"/></span>
                        <ul>
                            <li><a class="btn-prev" href="#" onclick='goToGroupProfilePage(<c:out value="${groupProfileCurrentPageNum-1}"/>);return false;'></a></li>
                            <li><a class="btn-next" href="#" onclick='goToGroupProfilePage(<c:out value="${groupProfileCurrentPageNum+1}"/>);return false;' ></a></li>
                        </ul>
                    </div>
                    <strong class="pages"><spring:message code="com.adminui.groups_ass_profiles_fragment.TotalRecords" text="default text" /> <c:out value="${groupProfilePageFirstItemNum}"/> -<c:out value="${groupProfilePageLastItemNum}"/>  <spring:message code="com.adminui.groups_ass_profiles_fragment.of" text="default text" /> <c:out value="${totalProfileGroupsCount}"/></strong>
                </div>
            </c:if>
            <c:if test="${totalProfileGroupsCount > 0}">
                <div class="function">
                    <ul>
                        <li><a class="ico-remove" id="btnRemove" style="display: none;" onclick="deAssociateGroupProfile()"><span><em><spring:message code="com.adminui.groups_ass_profiles_fragment.REMOVE" text="default text" /></em></span></a></li>
                    </ul>
                </div>
            </c:if>
        </fieldset>
        <%--<input id="groupProfileCurrentPageNum" type="hidden" value="${groupProfileCurrentPageNum}" />--%>
        <input id="totalGroupProfileCount" type="hidden" value="${totalProfileGroupsCount}" />
        <input id="groupProfileTotalPageNum" type="hidden" value="${groupProfileTotalPageNum}" />
        <input id="groupId" name="groupId" type="hidden" value="${groupId}" />
        <input id="sortDirection2" type="hidden" value="${sortDirection}" />
        <input id="groupProfileCurrentPageNum" type="hidden" value="${groupProfileCurrentPageNum}" />
    </form>
</c:if>