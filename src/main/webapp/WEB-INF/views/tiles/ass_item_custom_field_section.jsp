<%@page import="com.vroozi.customerui.profile.model.ProfileProxy,
com.vroozi.customerui.acl.model.Permission
"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>
<fieldset>
    <div class="lightbox-content">
        <table class="table-data">
            <thead>
            <tr>
                <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all4-lightbox" id="check-lightbox-4-1" /><label for="check-lightbox-4-1"></label></th>
                <th class="a-left"><spring:message code="com.adminui.ass_item_custom_field_section.name" text="default text" /></th>
                <th class="td-value"><spring:message code="com.adminui.ass_item_custom_field_section.fieldType" text="default text" /></th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tfoot>
            </tfoot>
            <tbody>
            <input type="hidden" name="catalogId" value="${catalogId}">
            <c:forEach var="customField" items="${customFieldsPage.pageItems}" varStatus="cnt">
            <tr>
                <td class="td-select"><input type="checkbox" class="target-chbox" name="customFieldIdList" id="check-lightbox-4-${customField.id}" value="${customField.customFieldId}"/><label for="check-lightbox-4-${customField.id}"></label></td>
                <td class="a-left td-name">
                    <div class="field">
						<c:if test="<%=aclManager.allow(request, Permission.EDIT_CUSTOM_FIELDS)%>">
		                    <span><a href='<c:out value="editCustomFieldPage"/>?customFieldId=<c:out value="${customField.id}"/>' >${customField.fieldName}</a></span>
							<input type="text" value="Size" />
                        </c:if>
						<c:if test="<%=!aclManager.allow(request, Permission.EDIT_CUSTOM_FIELDS)%>">                    
	                        <span>${customField.fieldName}</span>
	                        <input type="text" value="Size" />
                        </c:if>
                        
                    </div>
                </td>
                <td class="td-value">
                <c:if test="${customField.fieldType eq 'text'}"><spring:message code="com.adminui.ass_item_custom_field_section.inputFieldSmall" text="default text" /></c:if>
			    <c:if test="${customField.fieldType eq 'mediumtext'}"><spring:message code="com.adminui.ass_item_custom_field_section.inputFieldMedium" text="default text" /></c:if>
			    <c:if test="${customField.fieldType eq 'largetext'}"><spring:message code="com.adminui.ass_item_custom_field_section.inputFieldLarge" text="default text" /></c:if>
			    <c:if test="${customField.fieldType eq 'list'}"><spring:message code="com.adminui.ass_item_custom_field_section.inputFieldDropDownList" text="default text" /></c:if>
			    <c:if test="${customField.fieldType eq 'fixed'}"><spring:message code="com.adminui.ass_item_custom_field_section.inputFieldFixedValue" text="default text" /></c:if>
			    <c:if test="${customField.fieldType eq 'flag'}"><spring:message code="com.adminui.ass_item_custom_field_section.inputFieldFlag" text="default text" /></c:if>
                </td>
                <td>&nbsp;</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="bottom-data">
			<div class="pager">
				<input type="hidden" name="pageNumber" value="${customFieldsPage.pageNumber}">
				<span>Page ${customFieldsPage.pageNumber} of ${customFieldsPage.pagesAvailable} </span>
				<ul>
					<li><a href="javascript:void(0)" 
							<c:choose>
							  <c:when test="${customFieldsPage.pageNumber > 1}">class="btn-prev-active"  onclick="getItemCustomFields('${catalogId}', '${catalogItemId}', '${customFieldsPage.pageNumber-1}')"</c:when>
							  <c:otherwise>class="btn-prev"</c:otherwise>
							</c:choose>></a></li>
					<li><a href="javascript:void(0)" 
							<c:choose>
							  <c:when test="${customFieldsPage.pageNumber eq customFieldsPage.pagesAvailable}">class="btn-next-inactive"</c:when>
							  <c:otherwise>class="btn-next"  onclick="getItemCustomFields('${catalogId}', '${catalogItemId}', '${customFieldsPage.pageNumber+1}')"</c:otherwise>
							</c:choose> ></a></li>
				</ul>
			</div>
			<strong class="pages"><spring:message code="com.adminui.ass_item_custom_field_section.totalRecords" text="default text" /> ${customFieldsPage.firstElementOnPage}-${customFieldsPage.lastElementOnPage} of ${customFieldsPage.totalRecords}</strong>
		</div>
    </div>
    <div class="btns-holder">
        <a href="#" class="btn-cancel"><spring:message code="com.adminui.ass_item_custom_field_section.cancel" text="default text" /></a>
        <input type="submit" value="Save" onclick="associateItemCustomFields('${catalogId}', '${catalogItemId}'); return false;"/>
    </div>
</fieldset>