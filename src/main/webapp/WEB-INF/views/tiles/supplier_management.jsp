<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>


<script type="text/javascript">
    var gSupplierMappingsPageURI =  '<c:url value="/supplierbulkupload/supplier_upload/page" />';
    var searchWithinSupplierServiceUrl   = '<c:url value="/searchWithinSupplier" />';
</script>
<div id="main">
    <input type="hidden" id="activeSup" value="${activeSup}" />
    <input type="hidden" id="inactiveSup" value="${inactiveSup}" />
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.supplier_management.Vroozi" text="default text" /></a></li>
            <li><spring:message code="com.adminui.supplier_management.SupplierManagement" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">
        <div id="content">
            <h1><spring:message code="com.adminui.supplier_management.SupplierManagement" text="default text" /></h1>
            <div class="content-block toggle-block active" id="summary-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.supplier_management.SupplierManagement" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content">
                        <div class="summary-box">
                            <table class="summary-table">
                                <thead>
                                    <tr>
                                        <td class="a-center sep view"><spring:message code="com.adminui.supplier_management.View" text="default text" /></td>
                                        <td><spring:message code="com.adminui.supplier_management.Summary" text="default text" /></td>
                                        <td></td>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <td colspan="2"><spring:message code="com.adminui.supplier_management.Total" text="default text" /></td>
                                        <td class="a-right" id="totalSuppliersCount">${totalSuppliersCount}</td>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <tr>
                                        <td class="a-center view">
                                            <input id="viewActiveSupplier" type="checkbox" checked="checked" onclick="filterSupplierView();"/>
                                            <label id="viewActiveSupplierlbl" for="viewActiveSupplier"></label>
                                        </td>
                                        <th><spring:message code="com.adminui.supplier_management.ActiveSuppliers" text="default text" /></th>
                                        <td class="a-right" id="activeSuppliersCount">${numOfActiveSupplier}</td>
                                    </tr>
                                    <tr>
                                        <td class="a-center view">
                                            <input id="viewInactiveSupplier" type="checkbox" checked="checked" onclick="filterSupplierView();"/>
                                            <label id="viewInactiveSupplierlbl" for="viewInactiveSupplier"></label>
                                        </td>
                                        <th><spring:message code="com.adminui.supplier_management.InactiveSuppliers" text="default text" /></th>
                                        <td class="a-right" id="inactiveSuppliersCount">${totalSuppliersCount-numOfActiveSupplier}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="content reorder-widget editable-widget">

                            <br />

                            <form action="supplierbulkupload" id="supplierMappingFileForm" class="form-create" method="post" enctype="multipart/form-data">
                                <fieldset>
                                    <div class="area file-area">
                                        <div class="row" style="width:100%;font-weight:bold" id="theSupplierFileId">
                                            <label for="supplierFile"><spring:message code="com.adminui.supplier_management.SupplierBulkUploadFile" text="default text" /></label>
                                            <div class="area-col" id="supplierFileDiv">
                                                <input size="75" type="file" name="supplierFile" style="cursor: pointer;" id="supplierFile"/>
                                            </div>
                                        </div>
                                        <br />
                                        <div class="row" style="width:100%;font-weight:bold;" id="downloadSupplierMapping">
                                            <br/>
                                            <label for="downloadSupplierErrorReportLink" id="lblDownloadSupplierErrorReportLink" style="padding-top:0px; padding-left: 200px;color:#FF0000;width: 180px;<c:if test="${!errorReportSupplier}">display: none;</c:if>"><spring:message code="com.adminui.supplier_management.errorUpload" text="default text" /></label><a href='view-supplier-bulk-upload-error-report?fileHandle=<c:out value="${fileHandleSupplier}"/>' target="_blank" id="downloadSupplierErrorReportLink" style="color:#0000FF;<c:if test="${!errorReportSupplier}">display: none;</c:if>"><spring:message code="com.adminui.supplier_management.downloadError" text="default text" /></a>
                                            <br/>
                                            <label for="vmDownloadProcessedReportLink" id="vmLblDownloadProcessedReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;">Processed File :</label><a href="supplierbulkupload.xlsx" target="_blank" id="vmDownloadProcessedReportLink" style="color:#0000FF;${vmProcessedReportStyle}"><spring:message code="com.adminui.supplier_management.downloadProcessData" text="default text" /></a>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>

                            <div id="info-section4" style="display: none">
                                <table>
                                    <tr>
                                        <td>
                                            <h4 id="pg_message"><c:out value="${messageSupplier}"/></h4>
                                        </td>
                                        <td >
                                            <div id="pg-info-section2-loading">
                                                <img src="res/images/loading.gif" alt="" />
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <%--</div>--%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="add-slide-blocks">
                <div class="content-block toggle-block active" id="info-section">
                    <div class="headline">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.supplier_management.SupplierCompanyList" text="default text" /></a></h2>
                    </div>

                    <div class="block">
                        <div class="content editable-widget">
                            <div class="top-box">
                                <form id="searchWithinSupplierForm" action="#" style="text-align: right; font-size: 14;font-weight: bold;">
                                    <fieldset class="add-search-form advanced">

                                        <input id="searchWithinId" type="text" placeholder="Search Within..." onfocus="this.placeholder = ''" onblur="this.placeholder = 'Search Within...'" />
                                        <input type="submit" id="searchCriteriaBtn" value="Submit" onclick="searchWithinSuppliers();return false;"/>
                                        <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchWithinId').val(''); $('#searchCriteriaBtn').click();" />

                                    </fieldset>
                                    <label for="supplierType" id="supplierTypelbl" style="padding-right: 15px;position: relative;top:5px;font-weight: bold !important;font-color:'#252525' !important; "><span class="ui-button-text"><spring:message code="com.adminui.supplier_management.Type" text="default text" /></span></label>
                                        <fieldset style="float: right;cursor: pointer;font-weight:normal !important;">

                                        <select name="supplierType" id="supplierType" style="width: 200px;font-weight:normal !important;cursor: pointer" onchange="filterSupplierView();">

                                            <option value="All" id="1" ><spring:message code="com.adminui.supplier_management.All" text="default text" /></option>

                                            <option value="Catalog Suppliers" id="2" selected="selected" ><spring:message code="com.adminui.supplier_management.CatalogSuppliers" text="default text" /></option>

                                            <option value="Non Catalog Suppliers" id="3"><spring:message code="com.adminui.supplier_management.NonCatalogSuppliers" text="default text" /></option>

                                        </select>
                                    </fieldset>
                                </form>
                            </div>
                            <div id="supplier_page_container_div">
                                <jsp:include page="supplier_table_fragment.jsp" />
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            </div>
        <div id="sidebar">
                <ul class="sub-nav">
                        <li><a href="<c:url value="/vroozi" />" class="ico-back"><span><spring:message code="com.adminui.supplier_management.BACK" text="default text" /></span></a></li>
                    <c:if test="<%=aclManager.allow(request, Permission.CREATE_SUPPLIER)%>">
                        <li><a href='<c:url value="/supplierDetail" />' class="ico-mapping"><span><spring:message code="com.adminui.supplier_management.CREATESUPPLIER" text="default text" /></span></a></li>
                    </c:if>
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_SUPPLIER_ATTRIBUTE)%>">
                        <li><a href='<c:url value="/supplierAttributes" />' class="ico-attributes" style="white-space: nowrap"><span><spring:message code="com.adminui.supplier_management.SUPPLIERATTRIBUTE" text="default text" /></span></a></li>
                    </c:if>
                    <!--h3>JUMP TO</h3-->
                </ul>
                <ul class="sub-nav">
                    <!--li><a href='#' class="ico-company"><span>SUPPLIER COMPANY LIST</span></a></li-->
                </ul>
            </div>
    </div>
</div>
<script type="text/javascript" src="res/js/supplier_managment.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        if($("#activeSup").val() == "true" && $("#inactiveSup").val() == "false"){
//            $("#viewInactiveSupplier").click();
            $("#viewInactiveSupplier").attr('checked', false);
            $("#viewInactiveSupplierlbl").removeClass("ui-state-active");
            filterSupplierView("ACTIVE");
        } else if($("#activeSup").val() == "false" && $("#inactiveSup").val() == "true"){
//            $("#viewActiveSupplier").click();
            $("#viewActiveSupplier").attr('checked', false);
            $("#viewActiveSupplierlbl").removeClass("ui-state-active");
            filterSupplierView("INACTIVE");
        }
    });
</script>