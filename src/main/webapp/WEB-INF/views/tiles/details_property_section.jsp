<%@page import="com.vroozi.customerui.property.model.PropertyProxy"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<thead>
<tr>
    <th class="td-select">
        <input type="checkbox" class="check-allbox" name="check-all" id="checkProperty1" onclick="if(this.checked)checkAllCatalogItems(true);else checkAllCatalogItems(false)"/>
        <label for="checkProperty1"></label>
    </th>
    <th class="a-left"><spring:message code="com.adminui.details_property_section.SeqNo" text="default text" /></th>
    <th><spring:message code="com.adminui.details_property_section.Key" text="default text" /></th>
    <th><spring:message code="com.adminui.details_property_section.Value" text="default text" /></th>
    <th></th>
</tr>
</thead>
<tbody>
<% int propertiesCounter = 0; %>
<c:forEach var="catalogProperties" items="${catalogProperties}" varStatus="cntr">
    <tr>
        <td class="td-select">
            <input type="checkbox" class="target-chbox" name="itemIds" id="${catalogProperties.id}" value="${catalogProperties.id}"/><label for="${catalogProperties.id}"></label>
        </td>
        <td>
            <div class="field">
                <span>${catalogProperties.seqNum}</span>
                <input type="text" value="${catalogProperties.seqNum}" />
            </div>
        </td>
        <td>
            <div class="field">
                <span>${catalogProperties.key}</span>
                <input type="text" value="${catalogProperties.key}" />
            </div>
        </td>
        <td>
            <div class="field">
                <span>${catalogProperties.value}</span>
                <input type="text" value="${catalogProperties.value}" />
            </div>
        </td>
        <td class="td-last"></td>
    </tr>
    <% ++propertiesCounter; %>
</c:forEach>
</tbody>
<tfoot>
	<tr>
		<td colspan="7"><spring:message code="com.adminui.details_property_section.TotalRecords" text="default text" /><%=propertiesCounter%></td>
	</tr>
</tfoot>