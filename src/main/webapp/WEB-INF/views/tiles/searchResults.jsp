<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"
         import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.search.model.SearchResultVO"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
%>
    <c:if test="${renderTable}">
    <table>
        <tr>
            <td class="a-left td-description">
                <div class="field" style="width:150px">
                    <span><b><spring:message code="com.adminui.searchResults.Name" text="default text" /></b></span>
                </div>
            </td>
            <td>
                <div class="field" style="width:200px">
                    <span><b><spring:message code="com.adminui.searchResults.Description" text="default text" /></b></span>
                </div>
            </td>
            <td>
                <div class="field" style="margin-left: 25px;width:50px">
                    <span><b><spring:message code="com.adminui.searchResults.Currency" text="default text" /></b></span>
                </div>
            </td>
            <td>
                <div class="field" style="margin-left: 25px;width:50px">
                    <span><b><spring:message code="com.adminui.searchResults.Price" text="default text" /></b></span>
                </div>
            </td>
            <td>
                <div class="field" style="margin-left: 25px;width:150px">
                    <span><b><spring:message code="com.adminui.searchResults.MaterialGroup" text="default text" /></b></span>
                </div>
            </td>
            <td>
                <div class="field" style="margin-left: 25px;width:150px">
                    <span><b><spring:message code="com.adminui.searchResults.CatalogName" text="default text" /></b></span>
                </div>
            </td>
        </tr>
        <c:forEach var="result" items="${searchPage.pageItems}" varStatus="cntr">
    <tr>
        <td class="a-left td-description">
            <div class="field" style="width:150px">
                <span><a href="itemdetail?catalogId=${result.catalogId}&catalogItemId=${result.id}"> ${result.name} </a></span>
            </div>
        </td>
        <td>
            <div class="field" style="width:200px">
                <span>${result.description}</span>
            </div>
        </td>
        <td>
            <div class="field" style="margin-left: 25px;width:50px">
                <span>${result.currency}</span>
            </div>
        </td>
        <td>
            <div class="field" style="margin-left: 25px;width:50px">
                <span>${result.price}</span>
            </div>
        </td>
        <td>
            <div class="field" style="margin-left: 25px;width:50px">
                <span>${result.materialGroup}</span>
            </div>
        </td>
		<td>
            <div class="field" style="margin-left: 25px;width:50px">
                <span><a href="catalogDetail?catalogId=${result.catalogId}">${result.catalogName}</a></span>
            </div>
        </td>
    </tr>

    </c:forEach>
    </table>
    
    <div class="bottom-data">
		<div class="pager">
			<input type="hidden" name="pageNumber" value="${searchPage.pageNumber}">
			<span>Page ${searchPage.pageNumber} of ${searchPage.pagesAvailable} </span>
			<ul>
				<li><a href="javascript:void(0)" 
						<c:choose>
						  <c:when test="${searchPage.pageNumber > 1}">class="btn-prev-active"  onclick="executeSearchFunction(${searchPage.pageNumber-1});"</c:when>
						  <c:otherwise>class="btn-prev"</c:otherwise>
						</c:choose>></a></li>
				<li><a href="javascript:void(0)" 
						<c:choose>
						  <c:when test="${searchPage.pageNumber eq searchPage.pagesAvailable}">class="btn-next-inactive"</c:when>
						  <c:otherwise>class="btn-next"  onclick="executeSearchFunction(${searchPage.pageNumber+1});"</c:otherwise>
						</c:choose> ></a></li>												</ul>
		</div>
		<strong class="pages">Total Records: ${searchPage.firstElementOnPage}-${searchPage.lastElementOnPage} of ${searchPage.totalRecords}</strong>
	</div>
    </c:if>
    <c:if test="${!renderTable}">
        <h3><spring:message code="com.adminui.searchResults.noResultsFound" text="default text" /></h3>
    </c:if>