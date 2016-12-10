<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form action="associateCatalogToProfile" id="associateCatalogToProfileForm" method="post" class="checkboxResetForm">
    <fieldset>
        <c:if test="${notAssociatedCatalogsCount > 0}">
            <div class="lightbox-content editable-widget">
            <table class="table-data">
                <thead>
                    <tr>
                <th class="td-select">
                    <input type="checkbox" class="ui-helper-hidden-accessible" name="check-lightbox-all" id="check-lightbox-1" onclick="toggleAllCheckboxItems(this.checked, 'associateCatalogToProfileForm');"/>
                    <label for="check-lightbox-1" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                </th>
                <th class="a-left"><a class="sorting" href="#" onclick="sortCatalogs();return false;"><spring:message code="com.adminui.profile_catalog_fragment.CatalogName" text="default text" /></a></th>
                <th class="a-left"><spring:message code="com.adminui.profile_catalog_fragment.CatalogType" text="default text" /></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="cat" items="${catalogs}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <input type="checkbox" class="ui-helper-hidden-accessible" name="associatedCatalogIds" id="profile-${cat.catalogId}" value="${cat.catalogId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
                                <label for="profile-${cat.catalogId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
                            </td>
                            <td class="a-left td-description">
                                <div class="field">
                                <span><a href='catalogDetail?catalogId=<c:out value="${cat.catalogId}"/>'><c:out value="${cat.name}"/></a></span>
                                <input type="text" value="${cat.name}" />
                                </div>
                            </td>
                            <td class="a-left">
                             <c:if test="${cat.catalogTypeId == 2}">
                                 <spring:message code="com.adminui.profile_catalog_fragment.Internal" text="default text" />
                             </c:if>
                             <c:if test="${cat.catalogTypeId == 1}">
                                 <spring:message code="com.adminui.profile_catalog_fragment.External" text="default text" />
                             </c:if>
                             </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${notAssociatedCatalogsCount > 7}">
                <div class="bottom-data">
                    <div class="pager">
                        <span><spring:message code="com.adminui.profile_catalog_fragment.Page" text="default text" /> <c:out value="${catalogCurrentPageNum}"/> <spring:message code="com.adminui.profile_catalog_fragment.of" text="default text" />  <c:out value="${catalogTotalPageNum}"/></span>
                        <ul>
                            <li><a href="#" class="btn-prev" onclick="goToPrevCatalogPage();return false;"></a></li>
                            <li><a href="#" class="btn-next"  onclick="goToNextCatalogPage();return false;"></a></li>
                        </ul>
                    </div>
                    <strong class="pages"><spring:message code="com.adminui.profile_catalog_fragment.TotalRecords" text="default text" /> <c:out value="${catalogPageFirstItemNum}"/> -<c:out value="${catalogPageLastItemNum}"/>  <spring:message code="com.adminui.profile_catalog_fragment.of" text="default text" /> <c:out value="${notAssociatedCatalogsCount}"/></strong>
                </div>
            </c:if>
        </div>
            <div class="btns-holder">
                <a href="#" class="btn-cancel" onclick="$('input#searchWithinCatalogTextId').val('');searchWithinCatalogs();$('input#searchWithinCatalogTextId').val('Search within')"><spring:message code="com.adminui.profile_catalog_fragment.Cancel" text="default text" /></a>
                <input type="submit" value="Save" onclick="associateCatalogToProfile();return false;"/>
                <p><span class="required">* </span><spring:message code="com.adminui.profile_catalog_fragment.RequiredField" text="default text" /></p>
            </div>
        </c:if>
    </fieldset>
    <input id="catalogTotalPageNum" type="hidden" value="${catalogTotalPageNum}" />
    <input id="profileId" name="profileId" type="hidden" value="${profileId}" />
    <input id="sortBy" type="hidden" value="${sortBy}" />
    <input id="sortDirection" type="hidden" value="${sortDirection}" />
    <input id="searchWithin" type="hidden" value="${searchWithin}" />
    <input id="naCatalogCurrentPageNum" type="hidden" value="${catalogCurrentPageNum}" />
</form>

<script language="javascript">


    $( document ).ready(function() {
        $("#associateCatalogToProfileForm").append("<div id='checkedItems' style='display: none'></div>");
        $("#associateCatalogToProfileForm").append("<div id='status-checkedItems' style='display: none'></div>");
        $("#associateCatalogToProfileForm").append("<div id='status-item-checkedItems' style='display: none'></div>");
    });
</script>
