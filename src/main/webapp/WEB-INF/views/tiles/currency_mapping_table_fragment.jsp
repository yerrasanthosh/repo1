<%@ page contentType="text/html;charset=UTF-8" language="java"
	autoFlush="true" session="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<div class="scrollable-area vscrollable">
	<div class="text">
		<form id="deleteMappingForm">
			<table class="table-data alt-table-data" id="currency_mappings_table">

				<thead>
					<tr>

						<th><spring:message code="com.adminui.currency_mapping_table_fragment.UniqueSystemID" text="default text" /></th>
						<%--<th>Unique Supplier ID</th>--%>
						<th><spring:message code="com.adminui.currency_mapping_table_fragment.SupplierCurrency" text="default text" /></th>
						<th><spring:message code="com.adminui.currency_mapping_table_fragment.CompanyCurrency" text="default text" /></th>
						<th><spring:message code="com.adminui.currency_mapping_table_fragment.Description" text="default text" /></th>
						<th><spring:message code="com.adminui.currency_mapping_table_fragment.Display" text="default text" /></th>
						<th><spring:message code="com.adminui.catalog_detail.CONTENTVIEWS" text="default text" /></th>
					</tr>
				</thead>
				<tbody id="currency_table_rows_body">
					<c:if test="${not empty currencyMappings}">
						<c:forEach var="currencyMapping" items="${currencyMappings}"
							varStatus="cntr">

							<tr>

								<td style="width: 20%">
									<div id="uniqueSystemId-${currencyMapping.mappingId}"
										class="contenMappingUser">${currencyMapping.uniqueSystemId}</div>
								</td>

								<%--<td style="width:20%">--%>
								<%--<div id="uniqueSupplierId-${currencyMapping.mappingId}" class="contenMappingUser">${currencyMapping.uniqueSupplierId}</div>--%>
								<%--</td>--%>

								<td style="width: 20%">
									<div id="supplierUnit-${currencyMapping.mappingId}"
										class="contenMappingUser">${currencyMapping.supplierCurrency}</div>

								</td>
								<td style="width: 20%">

									<div id="companyUnit-${currencyMapping.mappingId}"
										class="contenMappingUser">${currencyMapping.companyCurrency}</div>


								</td>
								<td style="width: 20%">

									<div id="companyUnit-${currencyMapping.mappingId}"
										class="contenMappingUser">
										<abbr title="${currencyMapping.description}">${currencyMapping.description!=null && currencyMapping.description.length()>10?currencyMapping.description.substring(0,9).concat("..."):currencyMapping.description}</abbr>
									</div>



								</td>
								<td style="width: 20%">

									<div id="companyUnit-${currencyMapping.mappingId}"
										class="contenMappingUser">${currencyMapping.display?"X":""}</div>


								</td>
								<td style="width: 20%">

									<div id="companyUnit-${currencyMapping.mappingId}"
										class="contenMappingUser">${currencyMapping.contentViewsToDisplay}</div>


								</td>
							</tr>

						</c:forEach>
					</c:if>
				</tbody>

			</table>
		</form>
	</div>
</div>

<div class="bottom-data" id="currencyBottomData" style="display: none;">
	<c:if test="${not empty currencyMappings}">
		<div class="pager" id="currencyMappingPager">
			<span id="currency_mapping_current_page_num"><spring:message code="com.adminui.currency_mapping_table_fragment.Page" text="default text" />
				${pgPageNumber} <spring:message code="com.adminui.currency_mapping_table_fragment.of" text="default text" /> <c:out value="${pgPagesAvailable}" />
			</span>
			<ul>
				<li><c:choose>
						<c:when test="${pgPageNumber > 1}">
							<a id="currency_mapping_prev_page" class="btn-prev-active"
								onclick="getCurrencyMappingsPage(${pgPageNumber-1},$('#searchTerm').val());return false;"></a>
						</c:when>
						<c:otherwise>
							<a id="currency_mapping_prev_page" class="btn-prev"></a>
						</c:otherwise>
					</c:choose></li>
				<li><c:choose>
						<c:when test="${pgPageNumber < pgPagesAvailable}">
							<a id="currency_mapping_next_page" class="btn-next"
								onclick="getCurrencyMappingsPage(${pgPageNumber+1},$('#searchTerm').val());return false;"></a>
						</c:when>
						<c:otherwise>
							<a id="currency_mapping_next_page" class="btn-next-inactive"></a>
						</c:otherwise>
					</c:choose></li>
			</ul>
		</div>
		<strong class="pages" id="currency_mapping_current_record_range"><spring:message code="com.adminui.currency_mapping_table_fragment.ShowingRecords" text="default text" />
			 <c:out value="${(pgPageNumber-1)*pgRecordsPerPage+1}" /> - <c:out
				value="${(pgTotalRecords-(pgPageNumber*pgRecordsPerPage)>0)?pgPageNumber*pgRecordsPerPage:pgTotalRecords}" />
			 <spring:message code="com.adminui.currency_mapping_table_fragment.of" text="default text" /> <c:out value="${pgTotalRecords}" />
		</strong>
	</c:if>
</div>
<br />





