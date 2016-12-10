<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<table class="table-data">
<thead>
<tr>
    <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all4" id="check4-1" /><label for="check4-1"></label></th>
    <th class="a-left"><spring:message code="com.adminui.associate_catalogs.catalogName" text="default text" /></th>
    <th><spring:message code="com.adminui.associate_catalogs.catalogType" text="default text" /></th>
    <th class="td-empty"></th>
</tr>
</thead>
<tbody>
<c:set var="idString" value="${catIdList}"/>
<c:forEach var="catalog" items="${catalogList.pageList}">
<tr>
    <td class="td-select"><input type="checkbox" class="target-chbox" name="catalogCustomFieldId" id="check4-${catalog.catalogId}" value="${catalog.catalogId}" /><label for="check4-${catalog.catalogId}"></label></td>

    <td class="a-left td-username td-sorting">
		${catalog.name}
    </td>
    <td>${catalog.catalogTypeDesc}</td>
    <td></td>
</tr>
</c:forEach>
<c:if test="${not empty catIdList}">
<input type="hidden" id="catalogIds" name="catalogIds" value="${catIdList}" />
</c:if>
</tbody>
</table>
<div class="bottom-data">
									<div class="pager">
									<c:if test="${catalogList.nrOfElements eq 0}">
										<span><spring:message code="com.adminui.associate_catalogs.page" text="default text" /> ${catalogList.page} <spring:message code="com.adminui.associate_catalogs.of" text="default text" /> ${catalogList.pageCount-1}</span>
										</c:if>
										<c:if test="${catalogList.nrOfElements > 0}">
										<span><spring:message code="com.adminui.associate_catalogs.page" text="default text" /> ${catalogList.page+1} <spring:message code="com.adminui.associate_catalogs.of" text="default text" /> ${catalogList.pageCount}</span>
										</c:if>
										<ul>
											<li><a href="javascript:void(0)" class="btn-prev" onclick="navigateCustomFieldCatalogPage('prev');"></a></li>
											<li><a href="javascript:void(0)" class="btn-next" onclick="navigateCustomFieldCatalogPage('next');"></a></li>
										</ul>
									</div>
									<c:if test="${catalogList.nrOfElements eq 0}">
									<strong class="pages"><spring:message code="com.adminui.associate_catalogs.totalRecords" text="default text" /> ${catalogList.firstElementOnPage}-${catalogList.lastElementOnPage+1} <spring:message code="com.adminui.associate_catalogs.of" text="default text" /> ${catalogList.nrOfElements}</strong>
									</c:if>
									<c:if test="${catalogList.nrOfElements > 0}">
									<strong class="pages"><spring:message code="com.adminui.associate_catalogs.totalRecords" text="default text" /> ${catalogList.firstElementOnPage+1}-${catalogList.lastElementOnPage+1} <spring:message code="com.adminui.associate_catalogs.of" text="default text" /> ${catalogList.nrOfElements}</strong>
									</c:if>
									<input type="hidden" id="catalogListPage" name="catalogListPage" value="${catalogList.page}" />
									<input type="hidden" id="catalogListPages" name="catalogListPages" value="${catalogList.pageCount}" />
</div>
