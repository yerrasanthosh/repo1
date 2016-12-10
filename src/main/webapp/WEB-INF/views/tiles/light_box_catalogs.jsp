<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table class="table-data">
<thead>
<tr>
    <th class="td-select"><input type="checkbox" class="check-allbox" name="check-lightbox-all" id="check-lightbox-1" /><label for="check-lightbox-1"></label></th>
    <th class="a-left">Catalog Name</th>
    <th>Catalog Type</th>
    <th class="td-empty"></th>
</tr>
</thead>
<tbody>
<c:set var="idString" value="${catIdList}"/>
<c:forEach var="catalog" items="${catalogList.pageList}">
<tr>
    <td class="td-select"><input type="checkbox" class="target-chbox" name="catalogCustomFieldId" id="check-lightbox-${catalog.catalogId}" value="${catalog.catalogId}" /><label for="check-lightbox-${catalog.catalogId}"></label></td>

    <td class="a-left td-username td-sorting">
		${catalog.name}
    </td>
    <td>${catalog.catalogTypeDesc}</td>
    <td></td>
</tr>
</c:forEach>
</tbody>
</table>
	<input type="hidden" id="catalogIds" name="catalogIds" value="${catIdList}" />
								
								<div class="bottom-data">
									<div id="pager1" class="pager">
									<c:if test="${catalogList.nrOfElements eq 0}">
										<span>Page ${catalogList.page} of ${catalogList.pageCount-1}</span>
										</c:if>
										<c:if test="${catalogList.nrOfElements > 0}">
										<span>Page ${catalogList.page+1} of ${catalogList.pageCount}</span>
										</c:if>
										<ul>
											<li><a href="javascript:void(0)" class="btn-prev" onclick="navigateCatalogPage('prev');"></a></li>
											<li><a href="javascript:void(0)" class="btn-next" onclick="navigateCatalogPage('next');"></a></li>
										</ul>
									</div>
									<c:if test="${catalogList.nrOfElements eq 0}">
									<strong class="pages">Total Records: ${catalogList.firstElementOnPage}-${catalogList.lastElementOnPage+1} of ${catalogList.nrOfElements}</strong>
									</c:if>
									<c:if test="${catalogList.nrOfElements > 0}">
									<strong class="pages">Total Records: ${catalogList.firstElementOnPage+1}-${catalogList.lastElementOnPage+1} of ${catalogList.nrOfElements}</strong>
									</c:if>
									<input type="hidden" id="allCatalogsPage" name="allCatalogsPage" value="${catalogList.page}" />
									<input type="hidden" id="allCatalogsPages" name="allCatalogsPages" value="${catalogList.pageCount}" />
								</div>