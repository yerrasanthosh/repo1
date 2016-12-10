<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:if test="${numOfCatalogItems > 0}">
${numOfCatalogItems}
@@@
</c:if>
<thead>
<tr>
    <th class="td-select">
        <input type="checkbox" class="check-allbox" name="check-all" id="check153" onclick="checkAllCatalogItems(this.checked)"/>
        <label for="check153"></label>
    </th>
    <th class="a-left"><spring:message code="com.adminui.catalogitem_table_fragment.DESCRIPTION" text="default text" /></th>
    <th><spring:message code="com.adminui.catalogitem_table_fragment.VENDOR_MAT" text="default text" /></th>
    <th><spring:message code="com.adminui.catalogitem_table_fragment.PRICE" text="default text" /></th>
    <th><spring:message code="com.adminui.catalogitem_table_fragment.MFR_PART" text="default text" /></th>
    <th><spring:message code="com.adminui.catalogitem_table_fragment.STATUS" text="default text" /></th>
</tr>
</thead>
<tbody >
<c:forEach var="catalogItem" items="${catalogItems}" varStatus="cntr">
    <tr>
        <td class="td-select">
            <input type="checkbox" class="target-chbox" name="itemIds" id="${catalogItem.catalogItemId}" value="${catalogItem.catalogItemId}"/>
            <!-- label for="${catalogItem.catalogItemId}"></label -->
            <input type="hidden" name="itemStatus" id="status-${catalogItem.catalogItemId}" value="${catalogItem.ociItemStatusId}"/>
            <input type="hidden" name="itemIdStatusId" id="status-item-${catalogItem.catalogItemId}" value="${catalogItem.catalogItemId}~~${catalogItem.ociItemStatusId}"/>
        </td>
        <td class="a-left td-description">
            <div class="field">
                <a href="itemdetail?catalogId=<c:out value="${catalogItem.catalogId}"/>&catalogItemId=<c:out value="${catalogItem.catalogItemId}"/>"> <span>${catalogItem.newItemDescription}</span></a>
                <input type="text" value="${catalogItem.newItemDescription}" />
            </div>
        </td>
        <td>
            <div class="field">
                <span>${catalogItem.newItemVendormat}</span>
                <input type="text" value="${catalogItem.newItemVendormat}" />
            </div>
        </td>
        <td>
            <div class="field">
                <span>${catalogItem.price}</span>
                <input type="text" value="${catalogItem.price}" />
            </div>
        </td>
        <td class="td-matg">
            <div title="${catalogItem.newItemManufactmat}" class="field">
                <span>${catalogItem.newItemManufactmat}</span>
                <input type="text" value="${catalogItem.newItemManufactmat}" />
            </div>
        </td>
        <td>
            <div>
                <dd id='<c:out value="${catalogItem.catalogItemId}"/>approvedStatus'><c:out value="${catalogItem.approvedStatus}"/></dd>
            </div>
        </td>
    </tr>
</c:forEach>
</tbody>
<c:if test="${catalogItemTotalNumOfPages > 0}">@@@${catalogItemTotalNumOfPages}</c:if><c:if test="${not empty filterQuery}">@@@${filterQuery}</c:if>
<%--<input type="hidden" name="countOfDeletedItems" id="countOfDeletedItems" value="${countOfDeletedItems}"/>--%>
<script type="text/javascript">
    outputRecords = <c:out value="${outputRecords}"/>;
    pNo = <c:out value="${pNo}"/>;
</script>
