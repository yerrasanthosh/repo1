<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${totalProfileCatalogCount > 0}">
    <form action="disAssociateCatalogToProfile" id="disAssociateCatalogFromProfileForm" method="post">
        <fieldset>
            <table class="table-data">
                <thead>
                    <tr>
                    <th class="td-select">
                        <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all4" id="check4-1" onclick="toggleAllCheckboxItems(this.checked, 'disAssociateCatalogFromProfileForm');"/>
                        <label for="check4-1" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                    </th>
                    <th class="a-left"><a class="sorting" href="#" onclick="sortProfileCatalogs();return false;"><spring:message code="com.adminui.profile_ass_catalogs_fragment.CatalogName" text="default text" /></a></th>
                    <th class="a-left"><spring:message code="com.adminui.profile_ass_catalogs_fragment.CatalogType" text="default text" /></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="asscat" items="${associatedCatalogs}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <input type="checkbox" class="ui-helper-hidden-accessible" name="associatedCatalogIds" id="profile-${asscat.catalogId}" value="${asscat.catalogId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                                <label for="profile-${asscat.catalogId}"  aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                            </td>
                            <td class="a-left td-description">
                                <div class="field">
                                    <span><a href='catalogDetail?catalogId=<c:out value="${asscat.catalogId}"/>'><c:out value="${asscat.name}"/></a></span>
                                    <input type="text" value="${asscat.name}" />
                                </div>
                            </td>
                            <td class="a-left">
                            
							<c:if test="${asscat.catalogTypeId == 2}">
                             	Internal
                             </c:if>
                             <c:if test="${asscat.catalogTypeId == 1}">
                             	External
                             </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${totalProfileCatalogCount > 7}">
                <div class="bottom-data">
                    <div class="pager">
                        <span>Page <c:out value="${profileCatalogCurrentPageNum}"/> of  <c:out value="${profileCatalogTotalPageNum}"/></span>
                        <ul>
                            <li><a class="btn-prev" href="#" onclick='goToProfileCatalogPage(<c:out value="${profileCatalogCurrentPageNum-1}"/>);return false;'></a></li>
                            <li><a class="btn-next" href="#" onclick='goToProfileCatalogPage(<c:out value="${profileCatalogCurrentPageNum+1}"/>);return false;' ></a></li>
                        </ul>
                    </div>
                    <strong class="pages"><spring:message code="com.adminui.profile_ass_catalogs_fragment.TotalRecords" text="default text" /> <c:out value="${profileCatalogPageFirstItemNum}"/> -<c:out value="${profileCatalogPageLastItemNum}"/>  <spring:message code="com.adminui.profile_ass_catalogs_fragment.of" text="default text" /> <c:out value="${totalProfileCatalogCount}"/></strong>
                </div>
            </c:if>
            <c:if test="${totalProfileCatalogCount > 0}">
                <div class="function">
                    <ul>
                        <li><a class="ico-remove" id="btnRemove" style="display: none;" onclick="deAssociateCatalogFromProfile();"><span><em><spring:message code="com.adminui.profile_ass_catalogs_fragment.REMOVE" text="default text" /></em></span></a></li>
                    </ul>
                </div>
            </c:if>
        </fieldset>
        <input id="profileCatalogCurrentPageNum" type="hidden" value="${profileCatalogCurrentPageNum}" />
        <input id="totalProfileCatalogCount" type="hidden" value="${totalProfileCatalogCount}" />
        <input id="profileCatalogTotalPageNum" type="hidden" value="${profileCatalogTotalPageNum}" />
        <input id="profileId" name="profileId" type="hidden" value="${profileId}" />
        <input id="sortDirection" type="hidden" value="${sortDirection}" />
        <input id="catalogCurrentPageNum" type="hidden" value="${catalogCurrentPageNum}" />
    </form>
</c:if>