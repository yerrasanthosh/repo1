<%@page import="com.vroozi.customerui.profile.model.ProfileProxy"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<thead>
<tr>
    <th class="td-select">
        <input type="checkbox" class="check-allbox" name="check-all2-lightbox" id="check-lightbox-5-1" /><label for="check-lightbox-5-1"></label></th>
    <th class="a-left"><spring:message code="com.adminui.custom_field_section.NAME" text="default text" /></th>
    <th><spring:message code="com.adminui.custom_field_section.FIELDTYPE" text="default text" /></th>
    <th><spring:message code="com.adminui.custom_field_section.FIELDMAPPING" text="default text" /></th>
    <th><spring:message code="com.adminui.custom_field_section.REQUIRED" text="default text" /></th>
    <th><spring:message code="com.adminui.custom_field_section.POSTFILTER" text="default text" /></th>
    <th class="td-last"><spring:message code="com.adminui.custom_field_section.SEARCHABLE" text="default text" /></th>
</tr>
</thead>
<tbody>
                                    <% int customFieldCounter = 0; %>
                                    <c:forEach var="entry" items="${catalogCustomFields}" varStatus="cntr1">
                                        <tr>
                                            <td id="td_${entry.key}"  class="td-select"> <div onclick="if(document.getElementById(${entry.key}).checked){$('#${entry.key}').prop('checked', false);$('#td_${entry.key} div label').removeClass('ui-state-active');}else{$('#${entry.key}').prop('checked', true);$('#td_${entry.key} div label').addClass('ui-state-active');}return false;">
                                                <a href="#" class="btn-up-down"><span class="up-arrow"><spring:message code="com.adminui.custom_field_section.up" text="default text" /></span> <span class="down-arrow"><spring:message code="com.adminui.custom_field_section.down" text="default text" /></span></a>
                                                <input type="checkbox" name="catalogCustomFieldId" class="target-chbox" value="${entry.value.id}" class="target-chbox" id="${entry.key}" /><label for="${entry.key}"></label></div></td>
                                            <td class="a-left td-name">
                                                <div class="field">
                                                    <span><a href="#" onclick="getCatalogCustomField(${entry.value.id});" class="open-popup">${entry.value.fieldName}</a></span>
                                                    <input type="text" value="Size" />
                                                </div>
                                            </td>
                                            <td>
	                                            <c:if test="${entry.value.fieldType eq 'text'}"><spring:message code="com.adminui.custom_field_section.InputFieldSmall" text="default text" /></c:if>
			                                    <c:if test="${entry.value.fieldType eq 'mediumtext'}"><spring:message code="com.adminui.custom_field_section.InputFieldMedium" text="default text" /></c:if>
			                                    <c:if test="${entry.value.fieldType eq 'largetext'}"><spring:message code="com.adminui.custom_field_section.InputFieldLarge" text="default text" /></c:if>
			                                    <c:if test="${entry.value.fieldType eq 'list'}"><spring:message code="com.adminui.custom_field_section.DropDownList" text="default text" /></c:if>
			                                    <c:if test="${entry.value.fieldType eq 'fixed'}"><spring:message code="com.adminui.custom_field_section.FixedValue" text="default text" /></c:if>
			                                    <c:if test="${entry.value.fieldType eq 'flag'}"><spring:message code="com.adminui.custom_field_section.Flag" text="default text" /></c:if>
                                            </td>
                                            <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><select name="mappingIdList">
                                                <option value="0_${entry.value.id}" <c:if test="${entry.value.mappingId eq null || entry.value.mappingId eq '0'}">selected="selected"</c:if>></option>
                                                <option value="1_${entry.value.id}" <c:if test="${entry.value.mappingId eq '1'}">selected="selected"</c:if>>NEW_ITEM-LONGTEXT</option>
                                                <option value="2_${entry.value.id}" <c:if test="${entry.value.mappingId eq '2'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD1</option>
                                                <option value="3_${entry.value.id}" <c:if test="${entry.value.mappingId eq '3'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD2</option>
                                                <option value="4_${entry.value.id}" <c:if test="${entry.value.mappingId eq '4'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD3</option>
                                                <option value="5_${entry.value.id}" <c:if test="${entry.value.mappingId eq '5'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD4</option>
                                                <option value="6_${entry.value.id}" <c:if test="${entry.value.mappingId eq '6'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD5</option>
                                            </select></td>
                                            <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><input type="checkbox" name="catalogCustomRequired" value="required_${entry.value.id}" id="required_${entry.value.id}" <c:if test="${entry.value.required}">checked="checked"</c:if> /><label <c:if test="${entry.value.fieldType eq 'fixed' || entry.value.fieldType eq 'flag'}">class="disable"</c:if> for="required_${entry.value.id}"></label></td>
                                            <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><input type="checkbox" name="catalogCustomPostFilterable" value="postFilterable_${entry.value.id}" id="postFilterable_${entry.value.id}" <c:if test="${entry.value.postFilterable}">checked="checked"</c:if> /><label <c:if test="${entry.value.fieldType ne 'fixed' && entry.value.fieldType ne 'list'}">class="disable"</c:if> for="postFilterable_${entry.value.id}"></label></td>
                                            <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><input type="checkbox" name="catalogCustomSearchable" value="searchable_${entry.value.id}" id="searchable_${entry.value.id}" <c:if test="${entry.value.searchable}">checked="checked"</c:if> /><label <c:if test="${entry.value.fieldType ne 'list' && entry.value.fieldType ne 'fixed'}">class="disable"</c:if> for="searchable_${entry.value.id}"></label></td>
                                        </tr>
                                        <% ++customFieldCounter; %>
                                    </c:forEach>
                                </tbody>
<tfoot>
<tr><td colspan="7"><spring:message code="com.adminui.custom_field_section.TotalRecords" text="default text" /> <%=customFieldCounter%></td></tr>
</tfoot>