<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false" %>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:if test="${not empty supplierMappings}">
    					<div class="scrollable-area vscrollable">
                            <div class="text">
                                <table class="table-data alt-table-data" id="custom_supplier_mappings_table">
                                    <c:if test="${not empty supplierMappings}">
                                    <thead>
                                    <tr>
                                        <th><spring:message code="com.adminui.suppliermapping_table_fragment.UniqueSupplierID" text="default text" /></th>
                                        <th><spring:message code="com.adminui.suppliermapping_table_fragment.SupplierName" text="default text" /></th>
                                        <th><spring:message code="com.adminui.suppliermapping_table_fragment.SupplierID" text="default text" /></th>
                                        <th><spring:message code="com.adminui.suppliermapping_table_fragment.UniqueSystemID" text="default text" /></th>
                                    </tr>
                                    </thead>
                                    <tbody id="supplier_mappings_table_rows_body">
                                    <c:forEach var="supplierMapping" items="${supplierMappings}" varStatus="cntr">
                                        <tr>
                                            <td style="width:20%">
                                                <div>
                                                    <span>${supplierMapping.uniqueSupplierId}</span>

                                                </div>
                                            </td>
                                            <td style="width:20%">
                                                <div>
                                                    <span>${supplierMapping.vendorName}</span>

                                                </div>
                                            </td>
                                            <td style="width:20%">
                                                <div>
                                                    <span>${supplierMapping.vendorNumber}</span>

                                                </div>
                                            </td>
                                            <td style="width:20%">
                                                <div>
                                                    <span>${supplierMapping.uniqueSystemId}</span>

                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    </c:if>
                                </table>
                            </div>
                        </div>
                        <div class="bottom-data" id="supplierMappingBottomData" style="display:none;">
                                <div class="pager" id="customSupplierPager">
                                    <span id="custom_supplier_mapping_current_page_num"><spring:message code="com.adminui.suppliermapping_table_fragment.Page" text="default text" /> ${pageNumberSupplier} <spring:message code="com.adminui.suppliermapping_table_fragment.of" text="default text" /> <c:out value="${pagesAvailableSupplier}"/> </span>
                                    <ul>
                                    	<li><c:choose><c:when test="${pageNumberSupplier > 1}">
	                                    		<a id="custom_supplier_mapping_prev_page" class="btn-prev-active" onclick="getSupplierMappingsPage(${pageNumberSupplier-1},$('#supplierMappingSearchTerm').val());return false;"></a>
											</c:when><c:otherwise>
												<a id="custom_supplier_mapping_prev_page" class="btn-prev"></a>
											</c:otherwise></c:choose></li>
                                        <li><c:choose><c:when test="${pageNumberSupplier < pagesAvailableSupplier}">
	                                    		<a id="custom_supplier_mapping_next_page" class="btn-next" onclick="getSupplierMappingsPage(${pageNumberSupplier+1},$('#supplierMappingSearchTerm').val());return false;"></a>
											</c:when><c:otherwise>
												<a id="custom_supplier_mapping_next_page" class="btn-next-inactive"></a>
											</c:otherwise></c:choose></li>
                                    </ul>
                                </div>
                            <strong class="pages" id="custom_supplier_mapping_current_record_range"><spring:message code="com.adminui.suppliermapping_table_fragment.ShowingRecords" text="default text" /> <c:out value="${(pageNumberSupplier-1)*recordsPerPage+1}"/> - <c:out value="${(totalRecordsSupplier-(pageNumberSupplier*recordsPerPage)>0)?pageNumberSupplier*recordsPerPage:totalRecordsSupplier}"/> <spring:message code="com.adminui.suppliermapping_table_fragment.of" text="default text" /> <c:out value="${totalRecordsSupplier}"/></strong>
                        </div>
</c:if>
<c:if test="${empty supplierMappings}">
    <c:if test="${not empty messageSupplier}">
        <table>
            <tr>
                <td>
                    <h4><c:out value="${messageSupplier}"/></h4>
                </td>
                <td id="info-section4-loading">
                    <img src="res/images/loading.gif" alt="" />
                </td>
            </tr>
        </table>
    </c:if>
</c:if>
<c:if test="${(not empty updating && updating) || (not empty supplierProcessFailed && supplierProcessFailed)}">
@@@${pagesAvailableSupplier}@@@${pageNumberSupplier}@@@${recordsPerPage}@@@${totalRecordsSupplier}@@@${errorReportSupplier}@@@${fileHandleSupplier}@@@${supplierProcessFailed}@@@${vmProcessedReportStyle}@@@${vmProcessedCount}@@@
</c:if>