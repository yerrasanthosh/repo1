<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.catalog.model.CatalogSummary"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class='slide-block <c:if test="${catalog.failedRecords gt 0}">red-block</c:if>'> <div class="slide-block <c:if test="${catalog.failedRecords eq 0}">blue-block</c:if>' id='<c:out value="${catalog.catalogId}"/>'>
    <div class="holder">
        <div class="frame">
            <div class="title">
                <h2><a href='catalogDetail?catalogId=<c:out value="${catalog.catalogId}"/>'><c:out value="${catalog.name}"/></a><img src="res/images/mask.png" alt="" width="34" height="27" /></h2>
                <a href="#" class="open-close"><span><spring:message code="com.adminui.catalog_div_fragment.CloseBlock" text="default text" />	</span><em><spring:message code="com.adminui.catalog_div_fragment.OpenBlock" text="default text" /></em></a>
                <div class="edit-btn alt-edit-btn">
                    <div class="drop">
                        <div class="drop-holder">
                            <div class="drop-frame">
                                <div class="drop-c">
                                    <ul>

                                            <li class="edit" ><a href="#" onclick='editCatalog(<c:out value="${catalog.catalogId}" />);'><spring:message code="com.adminui.catalog_div_fragment.EDIT" text="default text" />	</a></li>

                                        <li class="history"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.History" text="default text" /></a></li>
                                        <li class="marge"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.MERGE_WITH" text="default text" />	</a></li>
                                        <li class="export"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.EXPORT" text="default text" />	</a>
                                            <div class="drop-2">
                                                <div class="drop-2-holder">
                                                    <div class="drop-2-frame">
                                                        <div class="drop-2-c">
                                                            <ul>
                                                                <li class="xml"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.XML" text="default text" /></a></li>
                                                                <li class="xls"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.XLS" text="default text" />	</a></li>
                                                                <li class="csv"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.CSV" text="default text" />	</a></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <c:if test="${catalog.catalogStateId==1}">
                                            <li class="reject"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.reject" text="default text" /></a></li>
                                        </c:if>
                                        <li class="del"><a href="#"><spring:message code="com.adminui.catalog_div_fragment.Delete" text="default text" /></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${catalog.showSubHeader}">
                <div id='<c:out value="${catalog.catalogId}"/>catStatus' class="status">
                  <span><spring:message code="com.adminui.catalog_div_fragment.Processing" text="default text" /></span>
                 </div>
            </c:if>
            <div class="block">
                <dl>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.VersionNumber" text="default text" /></dt>
                    <dd><c:out value="${catalog.revision}"/>&nbsp;&nbsp;&nbsp;<a href="#"><spring:message code="com.adminui.catalog_div_fragment.PreviousVersions" text="default text" /></a></dd>
                    <c:if test="${not empty catalog.supplierName}">
                        <dt><spring:message code="com.adminui.catalog_div_fragment.Supplier" text="default text" />	</dt>
                        <dd><c:out value="${catalog.supplierName}"/></dd>
                    </c:if>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.CreatedOn" text="default text" /></dt>
                    <dd>${catalog.createdOnGMTTime}</dd>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.CreatedBy" text="default text" /></dt>
                    <dd><c:out value="${catalog.creatorName}"/></dd>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.Type" text="default text" /></dt>
                    <dd><c:out value="${catalog.catalogTypeDesc}"/></dd>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.Method" text="default text" /></dt>
                    <dd><c:out value="${catalog.originationMethodDesc}"/></dd>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.NumberofItems" text="default text" /></dt>
                    <dd><c:out value="${catalog.numberOfItem}"/></dd>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.Status" text="default text" /></dt>
                    <dd id='<c:out value="${catalog.catalogId}"/>approvedStatus'><c:out value="${catalog.approvedStatus}"/></dd>
                    <dt><spring:message code="com.adminui.catalog_div_fragment.Active" text="default text" /></dt>
                    <dd><c:out value="${catalog.activeStatus}"/></dd>
                </dl>
            </div>

        </div>
    </div>
</div>
