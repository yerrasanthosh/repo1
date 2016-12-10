<%@ page contentType="text/html;charset=UTF-8" language="java"
	autoFlush="true" session="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
  response.setHeader("Pragma", "no-cache");
  response.setHeader("Cache-Control", "no-cache");
  response.setDateHeader("Expires", 0);
%>
<div class="scrollable-area vscrollable">
	<div class="text">
		<form id="deleteMappingForm">
			<table class="table-data alt-table-data" id="uom_mappings_table">

				<thead>
					<tr>

						<th><spring:message code="com.adminui.uom_mapping_table_fragment.UniqueSystemID" text="default text" /></th>
						<th><spring:message code="com.adminui.uom_mapping_table_fragment.SupplierUnit" text="default text" /></th>
						<th><spring:message code="com.adminui.uom_mapping_table_fragment.CompanyUnit" text="default text" /></th>
						<th><spring:message code="com.adminui.uom_mapping_table_fragment.Description" text="default text" /></th>
						<th><spring:message code="com.adminui.uom_mapping_table_fragment.Display" text="default text" /></th>
						<th><spring:message code="com.adminui.catalog_detail.CONTENTVIEWS" text="default text" /></th>
					</tr>
				</thead>
				<tbody id="uom_table_rows_body">
					<c:if test="${not empty profileGroups}">
						<c:forEach var="profileGroup" items="${profileGroups}"
							varStatus="cntr">

							<tr>

								<td style="width: 20%">
									<div id="uniqueSystemId-${profileGroup.mappingId}"
										class="contenMappingUser">${profileGroup.uniqueSystemId}</div>
								</td>

								<td style="width: 20%">
									<div id="supplierUnit-${profileGroup.mappingId}"
										class="contenMappingUser">${profileGroup.supplierUnit}</div>

								</td>
								<td style="width: 20%">

									<div id="companyUnit-${profileGroup.mappingId}"
										class="contenMappingUser">${profileGroup.companyUnit}</div>


								</td>
								<td style="width: 20%">

									<div id="companyUnit-${profileGroup.mappingId}"
										class="contenMappingUser">
										<abbr title="${profileGroup.description}">${profileGroup.description!=null && profileGroup.description.length()>10?profileGroup.description.substring(0,9).concat("..."):profileGroup.description}</abbr>
									</div>



								</td>
								<td style="width: 20%">

									<div id="companyUnit-${profileGroup.mappingId}"
										class="contenMappingUser">${profileGroup.display?"X":""}</div>


								</td>
								<td style="width: 20%">

									<div id="companyUnit-${profileGroup.mappingId}"
										class="contenMappingUser">${profileGroup.contentViewsToDisplay}</div>


								</td>
							</tr>

						</c:forEach>
					</c:if>
				</tbody>

			</table>
		</form>
	</div>
</div>

<div class="bottom-data" id="uomBottomData" style="display: none;">
	<c:if test="${not empty profileGroups}">
		<div class="pager" id="uomMappingPager">
			<span id="uom_mapping_current_page_num"><spring:message code="com.adminui.uom_mapping_table_fragment.Page" text="default text" /> ${pgPageNumber}
				<spring:message code="com.adminui.uom_mapping_table_fragment.of" text="default text" /> <c:out value="${pgPagesAvailable}" />
			</span>
			<ul>
				<li><c:choose>
						<c:when test="${pgPageNumber > 1}">
							<a id="uom_mapping_prev_page" class="btn-prev-active"
								onclick="getUOMMappingsPage(${pgPageNumber-1},$('#profileGroupSearchTerm').val());return false;"></a>
						</c:when>
						<c:otherwise>
							<a id="uom_mapping_prev_page" class="btn-prev"></a>
						</c:otherwise>
					</c:choose></li>
				<li><c:choose>
						<c:when test="${pgPageNumber < pgPagesAvailable}">
							<a id="uom_mapping_next_page" class="btn-next"
								onclick="getUOMMappingsPage(${pgPageNumber+1},$('#profileGroupSearchTerm').val());return false;"></a>
						</c:when>
						<c:otherwise>
							<a id="uom_mapping_next_page" class="btn-next-inactive"></a>
						</c:otherwise>
					</c:choose></li>
			</ul>
		</div>
		<strong class="pages" id="profile_group_mapping_current_record_range"><spring:message code="com.adminui.uom_mapping_table_fragment.ShowingRecords" text="default text" />
			: <c:out value="${(pgPageNumber-1)*pgRecordsPerPage+1}" /> -
			<c:out
				value="${(pgTotalRecords-(pgPageNumber*pgRecordsPerPage)>0)?pgPageNumber*pgRecordsPerPage:pgTotalRecords}" />
			<spring:message code="com.adminui.uom_mapping_table_fragment.of" text="default text" /> <c:out value="${pgTotalRecords}" />
		</strong>
	</c:if>
</div>
<br />





