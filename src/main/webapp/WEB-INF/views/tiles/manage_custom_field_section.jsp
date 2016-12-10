<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<table class="table-data">
                    <thead>
                    <tr>
                        <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all3" id="check3-1" /><label for="check3-1"></label></th>
                        <th class="a-left"><a id="fieldName" onclick="getCustomFieldsByType('fieldName');" style="cursor: pointer;"><spring:message code="com.adminui.manage_custom_field_section.FieldName" text="default text" /></a></th>
                        <th><a id="displayName" onclick="getCustomFieldsByType('displayName');" style="cursor: pointer;"><spring:message code="com.adminui.manage_custom_field_section.DisplayNAME" text="default text" /></a></th>
                        <th><a id="typeToDisplay" onclick="getCustomFieldsByType('typeToDisplay');" style="cursor: pointer;"><spring:message code="com.adminui.manage_custom_field_section.FIELDTYPE" text="default text" /></a></th>
                        <th class="a-left"><a id="fieldDesc" onclick="getCustomFieldsByType('fieldDesc');" style="cursor: pointer;"><spring:message code="com.adminui.manage_custom_field_section.DESCRIPTION" text="default text" /></a></th>
                        <th><spring:message code="com.adminui.manage_custom_field_section.STATUS" text="default text" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% int customFieldCounter = 0; %>
                    <c:forEach var="entry" items="${customFields.pageList}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <a href="#" class="btn-up-down"><span class="up-arrow"><spring:message code="com.adminui.manage_custom_field_section.up" text="default text" /></span> <span class="down-arrow"><spring:message code="com.adminui.manage_custom_field_section.down" text="default text" /></span></a>
                                <input type="checkbox" name="catalogCustomFieldId" value="${entry.id}" class="target-chbox" id="${entry.id}" /><label for="${entry.id}"></label></td>
                            <td class="a-left td-name">
                                <div class="field">
                                    <span><a href='<c:out value="editCustomFieldPage"/>?customFieldId=<c:out value="${entry.id}"/>' >${entry.fieldName}</a></span>
                                    <input type="text" value="Size" />
                                </div>
                            </td>
                            <td>${entry.displayName}</td>
                            <td><c:if test="${entry.fieldType eq 'text'}"><spring:message code="com.adminui.manage_custom_field_section.InputFieldSmall" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'mediumtext'}"><spring:message code="com.adminui.manage_custom_field_section.InputFieldMedium" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'largetext'}"><spring:message code="com.adminui.manage_custom_field_section.InputFieldLarge" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'list'}"><spring:message code="com.adminui.manage_custom_field_section.DropDownList" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'fixed'}"><spring:message code="com.adminui.manage_custom_field_section.FixedValue" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'flag'}"><spring:message code="com.adminui.manage_custom_field_section.Flag" text="default text" /></c:if></td>
                            <td class="a-left">${entry.fieldDesc}</td>
                            <c:if test="${entry.active}">
                                <td><spring:message code="com.adminui.manage_custom_field_section.Active" text="default text" /></td>
                            </c:if>
                            <c:if test="${!entry.active}">
                                <td><spring:message code="com.adminui.manage_custom_field_section.Inactive" text="default text" /></td>
                            </c:if>
                        </tr>
                        <% ++customFieldCounter; %>
                    </c:forEach>
                    <tr>

                    </tr>

                    </tbody>
                </table>
							<div class="bottom-data">
									<div class="pager">
									<c:if test="${customFields.nrOfElements eq 0}">
										<span><spring:message code="com.adminui.manage_custom_field_section.Page" text="default text" /> ${customFields.page} <spring:message code="com.adminui.manage_custom_field_section.of" text="default text" /> ${customFields.pageCount-1}</span>
										</c:if>
										<c:if test="${customFields.nrOfElements > 0}">
										<span><spring:message code="com.adminui.manage_custom_field_section.Page" text="default text" /> ${customFields.page+1} <spring:message code="com.adminui.manage_custom_field_section.of" text="default text" /> ${customFields.pageCount}</span>
										</c:if>
                                        <input type="hidden" id="currPage" value="${customFields.page}" />
										<ul>
                                            <li><a href="javascript:void(0)" class="<c:if test='${customFields.page > 0}'>btn-prev-active</c:if><c:if test='${customFields.page eq 0}'>btn-prev</c:if>" onclick="navigateCustFieldPage('prev');"></a></li>
                                            <li><a href="javascript:void(0)" class="<c:if test='${customFields.page+1 eq customFields.pageCount}'>btn-next-inactive</c:if><c:if test='${customFields.page+1 < customFields.pageCount}'>btn-next</c:if>" onclick="navigateCustFieldPage('next');"></a></li>
										</ul>
									</div>
									<c:if test="${customFields.nrOfElements eq 0}">
									<strong class="pages"><spring:message code="com.adminui.manage_custom_field_section.TotalRecords" text="default text" /> ${customFields.firstElementOnPage}-${customFields.lastElementOnPage+1} of ${customFields.nrOfElements}</strong>
									</c:if>
									<c:if test="${customFields.nrOfElements > 0}">
									<strong class="pages"><spring:message code="com.adminui.manage_custom_field_section.TotalRecords" text="default text" /> ${customFields.firstElementOnPage+1}-${customFields.lastElementOnPage+1} of ${customFields.nrOfElements}</strong>
									</c:if>
								</div>