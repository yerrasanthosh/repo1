<%@ page contentType="text/html;charset=UTF-8" language="java"
	autoFlush="true" session="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:if test="${not empty materialGroups}">
	<div class="scrollable-area vscrollable">
		<div class="text">
			<table class="table-data alt-table-data"
				id="material_group_mappings_table">
				<c:if test="${not empty materialGroups}">
					<thead>
						<tr>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.SystemID" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.SupplierID" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.CatalogCategoryCode" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.CompanyCategoryCode" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.CompanyLabel" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.Level1" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.Level2" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.Level3" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.Description" text="default text" /></th>
							<th><spring:message code="com.adminui.materialgroup_mapping_table_fragment.Display" text="default text" /></th>
							<th><spring:message code="com.adminui.catalog_detail.CONTENTVIEWS" text="default text" /></th>
						</tr>
					</thead>
					<tbody id="catalog_item_table_rows_body">
						<c:forEach var="materialGroup" items="${materialGroups}"
							varStatus="cntr">
							<tr>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.uniqueSystemId}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.supplierId}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.catalogCategoryCode}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.companyCategoryCode}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.companyLabel}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.level1Val}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.level2Val}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.level3Val}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span><abbr title="${materialGroup.description}">${materialGroup.description!=null && materialGroup.description.length()>10?materialGroup.description.substring(0,9).concat("..."):materialGroup.description}</abbr></span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.display?"X":""}</span>

									</div>
								</td>
								<td style="width: 10%">
									<div>
										<span>${materialGroup.contentViewsToDisplay}</span>

									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
			</table>
		</div>
	</div>
	<div class="bottom-data" id="materialGroupBottomData"
		style="display: none;">
		<div class="pager" id="customCatergoryPager">
			<span id="material_group_mapping_current_page_num"><spring:message code="com.adminui.materialgroup_mapping_table_fragment.Page" text="default text" />
				${pageNumber} <spring:message code="com.adminui.materialgroup_mapping_table_fragment.of" text="default text" /> <c:out value="${pagesAvailable}" />
			</span>
			<ul>
				<li><c:choose>
						<c:when test="${pageNumber > 1}">
							<a id="material_group_mapping_prev_page" class="btn-prev-active"
								onclick="getMaterialGroupMappingsPage(${pageNumber-1},$('#customCategorySearchTerm').val());return false;"></a>
						</c:when>
						<c:otherwise>
							<a id="material_group_mapping_prev_page" class="btn-prev"></a>
						</c:otherwise>
					</c:choose></li>
				<li><c:choose>
						<c:when test="${pageNumber < pagesAvailable}">
							<a id="material_group_mapping_next_page" class="btn-next"
								onclick="getMaterialGroupMappingsPage(${pageNumber+1},$('#customCategorySearchTerm').val());return false;"></a>
						</c:when>
						<c:otherwise>
							<a id="material_group_mapping_next_page"
								class="btn-next-inactive"></a>
						</c:otherwise>
					</c:choose></li>
			</ul>
		</div>
		<strong class="pages" id="material_group_mapping_current_record_range"><spring:message code="com.adminui.materialgroup_mapping_table_fragment.ShowingRecords" text="default text" />
			: <c:out value="${(pageNumber-1)*recordsPerPage+1}" /> - <c:out
				value="${(totalRecords-(pageNumber*recordsPerPage)>0)?pageNumber*recordsPerPage:totalRecords}" />
            <spring:message code="com.adminui.materialgroup_mapping_table_fragment.of" text="default text" />
            <c:out value="${totalRecords}" />
		</strong>
	</div>

</c:if>
<c:if test="${empty materialGroups}">
	<c:if test="${not empty message}">
		<table>
			<tr>
				<td>
					<h4>
						<c:out value="${message}" />
					</h4>
				</td>
				<td id="info-section2-loading"><img
					src="res/images/loading.gif" alt="" /></td>
			</tr>
		</table>
	</c:if>
</c:if>
<c:if
	test="${(not empty updating && updating) || (not empty processFailed && processFailed)}">
@@@${pagesAvailable}@@@${pageNumber}@@@${recordsPerPage}@@@${totalRecords}@@@${fileHandle}@@@${errorReport}@@@${processFailed}@@@${cmProcessedReportStyle}@@@${cmProcessedCount}@@@
</c:if>
