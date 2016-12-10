<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"
         import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>
<%@ page import="com.vroozi.customerui.acl.model.Permission, com.vroozi.customerui.util.Consts" %>

<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<script type="text/javascript">
    var gMaterialGroupMappingsPageURI =  '<c:url value="/datamapping/materialgroupmapping/page" />';
    var gSupplierMappingsPageURI =  '<c:url value="/datamapping/vendormapping/page" />';
    var gUOMMappingsPageURI =  '<c:url value="/uommapping/page" />';
    var gCurrencyMappingsPageURI =  '<c:url value="/currencymappings/page" />';
    var gUploadMaterialGroupMappingFileURI =  '<c:url value="/uploadMaterialGroup" />';
    var gUploadSupplierMappingFileURI = '<c:url value="/uploadsuppliermapping" />';
    var gProfileGroupMappingsPageURI =  '<c:url value="/profilegroupmapping/page" />';


    var gPagesAvailable;
    var gPageNumber;
    var gPageSize;
    var gTotalRecords;

    var pgPagesAvailable;
    var pgPageNumber;
    var pgPageSize;
    var pgTotalRecords;

    var gTotalRecordsSupplier;
    var gPagesAvailableSupplier;
    var gPageNumberSupplier;
    var gProcessFailed = false;
    var gSupplierProcessFailed = false;

    <c:if test="${not empty pgPagesAvailable}">
    pgPagesAvailable = ${pgPagesAvailable};
    </c:if>
    <c:if test="${not empty pgPageNumber}">
    pgPageNumber = ${pgPageNumber};
    </c:if>

    <c:if test="${not empty pgPageSize}">
    pgPageSize = ${pgPageSize};
    </c:if>

    <c:if test="${not empty pgTotalRecords}">
    pgTotalRecords = ${pgTotalRecords};
    </c:if>


    <c:if test="${not empty pagesAvailable}">
    gPagesAvailable = ${pagesAvailable};
    </c:if>
    <c:if test="${not empty pageNumber}">
    gPageNumber = ${pageNumber};
    </c:if>

    <c:if test="${not empty recordsPerPage}">
    gPageSize = ${recordsPerPage};
    </c:if>

    <c:if test="${not empty totalRecords}">
    gTotalRecords = ${totalRecords};
    </c:if>

    <c:if test="${not empty totalRecordsSupplier}">
    gTotalRecordsSupplier = ${totalRecordsSupplier};
    </c:if>

    <c:if test="${not empty pagesAvailableSupplier}">
    gPagesAvailableSupplier = ${pagesAvailableSupplier};
    </c:if>

    <c:if test="${not empty processFailed}">
    gProcessFailed = ${processFailed};
    </c:if>
    <c:if test="${not empty supplierProcessFailed}">
    gSupplierProcessFailed = ${supplierProcessFailed};
    </c:if>

    <c:if test="${not empty pageNumberSupplier}">
    gPageNumberSupplier = ${pageNumberSupplier};
    </c:if>
    $(document).ready(function() {
        $("form").bind("keypress", function (e) {
            if (e.keyCode == 13) {
                return false;
            }
        });

    });
</script>

<script type="text/javascript" src="res/js/data_mapping.js"></script>


<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.data_mapping.Vroozi" text="default text" /></a></li>
            <li><spring:message code="com.adminui.data_mapping.DataMapping" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">
        <div id="content">
            <h1><spring:message code="com.adminui.data_mapping.DataMapping" text="default text" /></h1>
            <div class="ui-tab-section users">
                <ul class="tabset">
                    <li><a href="#tab1"><span class="checkboxReset"><spring:message code="com.adminui.data_mapping.CATEGORY" text="default text" /></span></a></li>
                    <li><a href="#tab2"><span class="checkboxReset"><spring:message code="com.adminui.data_mapping.SUPPLIER" text="default text" /></span></a></li>
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_ACCESS)%>">
                        <li><a href="#tab3"><span class="checkboxReset"><spring:message code="com.adminui.data_mapping.ContentAccess" text="default text" /></span></a></li>
                    </c:if>
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_UOM_MAPPING)%>">
                        <li><a href="#tab4" onclick="workerUOMMappingTab()"><span class="checkboxReset"><spring:message code="com.adminui.data_mapping.UnitofMeasure" text="default text" /></span></a></li>
                    </c:if>
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_CURRENCY_MAPPING)%>">
                        <li><a href="#tab5" onclick="workerCurrencyMappingTab()"><span class="checkboxReset"><spring:message code="com.adminui.data_mapping.Currency" text="default text" /></span></a></li>
                    </c:if>
                    <c:if test="<%=aclManager.isFeatureAvailable(request, Consts.FEATURE_PURCHASE_EXPRESS) && aclManager.allow(request, Permission.VIEW_CONTENT_SHARE_MAPPING)%>">
                        <li><a href="#tab6" onclick="workerPowerShopperTab()"><span class="checkboxReset"><spring:message code="com.adminui.data_mapping.PowerShopper" text="default text" /></span></a></li>
                    </c:if>
                </ul>
                <div id="tab1">
                    <div class="content-block toggle-block active" id="summary-section1">
                        <div class="headline">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.data_mapping.CategoryMapping" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <div class="content reorder-widget editable-widget">

                                <br />

                                <form action="uploadMaterialGroup" id="mappingFileForm" class="form-create" method="post" enctype="multipart/form-data">
                                    <fieldset>
                                        <div class="area file-area">
                                            <div class="row" style="width:100%;font-weight:bold" id="theCatalogFileId">
                                                <label for="materialGroupFile"><spring:message code="com.adminui.data_mapping.UploadMappingFile" text="default text" /> </label>
                                                <div class="area-col" id="materialGroupFileDiv">
                                                    <input type="file" name="materialGroupFile" style="cursor: pointer;" id="materialGroupFile"/>
                                                </div>
                                            </div>
                                            <br />
                                            <div class="row" style="width:100%;font-weight:bold;" id="downloadMapping">
                                                <br/>
                                                <label for="downloadErrorReportLink" id="lblDownloadErrorReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;<c:if test="${!errorReport}">display: none;</c:if>"><spring:message code="com.adminui.data_mapping.Errorsoccurredduringupload" text="default text" /></label><a href='view-material-group-error-report?fileHandle=<c:out value="${fileHandle}"/>' target="_blank" id="downloadErrorReportLink" style="color:#0000FF;<c:if test="${!errorReport}">display: none;</c:if>"><spring:message code="com.adminui.data_mapping.Downloaderrorreport" text="default text" /></a>
                                                <br/>
                                                <label for="cmDownloadProcessedReportLink" id="cmLblDownloadProcessedReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;"><spring:message code="com.adminui.data_mapping.ProcessedFile" text="default text" /></label><a href="categorymapping.xlsx" target="_blank" id="cmDownloadProcessedReportLink" style="color:#0000FF;${cmProcessedReportStyle}"><spring:message code="com.adminui.data_mapping.Downloadprocesseddata" text="default text" /></a>

                                            </div>
                                        </div>
                                    </fieldset>
                                </form>

                                <%--</div>--%>
                            </div>
                        </div>
                    </div>
                    <div class="add-slide-blocks">
                        <input type="hidden" class="normal" value="" id="customCategorySearchTerm" />
                        <div class="toggle-block active" id="info-section">
                            <div class="title">
                                <h2><a href="#" class="open-close"><spring:message code="com.adminui.data_mapping.CUSTOMCATEGORY" text="default text" /></a></h2>
                            </div>
                            <div class="block" id="material_group_div">
                                <div class="content editable-widget" id="materialGroupMappingData" style="display: block">
                                    <div class="top-box">
                                        <form action="#" class="add-search-form advanced" id="searchCustomCategoryForm" onsubmit="return false;" >
                                            <fieldset>

                                                <input type="text" value="Search Custom Category" name="searchTextDisp" id="searchTextDisp" onclick='$(this).hide();$("#searchText").show();$("#searchText").focus();' />
                                                <input type="text" value="" name="searchText" id="searchText" style="display: none" />
                                                <input type="button" id="subBtn1" value="Submit" onclick="$('#customCategorySearchTerm').val($('#searchText').val());getMaterialGroupMappingsPage(1,$('#customCategorySearchTerm').val(),'freshSearch');"/>
                                                <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchText').val('');$('#searchText').hide();$('#searchTextDisp').show(); $('#subBtn1').click();" />
                                            </fieldset>
                                        </form>
                                    </div>
                                    <div id="material_group_mappings_section">
                                        <jsp:include page="materialgroup_mapping_table_fragment.jsp" />
                                    </div>
                                </div>

                                <div id="info-section2" style="display: none">
                                    <table>
                                        <tr>
                                            <td>
                                                <h4><c:out value="${message}"/></h4>
                                            </td>
                                            <td id="info-section2-loading">
                                                <img src="res/images/loading.gif" alt="" />
                                            </td>
                                        </tr>
                                    </table>
                                </div>

                            </div>
                        </div>

                    </div>

                </div>

                <div id="tab2">
                    <div class="content-block toggle-block active" id="summary-section2">
                        <div class="headline">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.data_mapping.SUPPLIERIDMAPPING" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <div class="content reorder-widget editable-widget">

                                <br />

                                <form action="uploadsuppliermapping" id="supplierMappingFileForm" class="form-create" method="post" enctype="multipart/form-data">
                                    <fieldset>
                                        <div class="area file-area">
                                            <div class="row" style="width:100%;font-weight:bold" id="theSupplierFileId">
                                                <label for="supplierFile"><spring:message code="com.adminui.data_mapping.UploadMappingFile" text="default text" /></label>
                                                <div class="area-col" id="supplierFileDiv">
                                                    <input size="75" type="file" name="supplierFile" style="cursor: pointer;" id="supplierFile"/>
                                                </div>
                                            </div>
                                            <br />
                                            <div class="row" style="width:100%;font-weight:bold;" id="downloadSupplierMapping">
                                                <br/>
                                                <label for="downloadSupplierErrorReportLink" id="lblDownloadSupplierErrorReportLink" style="padding-top:0px; padding-left: 200px;color:#FF0000;width: 180px;<c:if test="${!errorReportSupplier}">display: none;</c:if>"><spring:message code="com.adminui.data_mapping.Errorsoccurredduringupload" text="default text" /></label><a href='view-supplier-mapping-error-report?fileHandle=<c:out value="${fileHandleSupplier}"/>' target="_blank" id="downloadSupplierErrorReportLink" style="color:#0000FF;<c:if test="${!errorReportSupplier}">display: none;</c:if>"><spring:message code="com.adminui.data_mapping.Downloaderrorreport" text="default text" /></a>
                                                <br/>
                                                <label for="vmDownloadProcessedReportLink" id="vmLblDownloadProcessedReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;"><spring:message code="com.adminui.data_mapping.ProcessedFile" text="default text" /></label><a href="vendormapping.xlsx" target="_blank" id="vmDownloadProcessedReportLink" style="color:#0000FF;${vmProcessedReportStyle}"><spring:message code="com.adminui.data_mapping.Downloadprocesseddata" text="default text" /></a>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>

                                <%--</div>--%>
                            </div>
                        </div>
                    </div>
                    <div class="add-slide-blocks">
                        <input type="hidden" class="normal" value="" id="supplierMappingSearchTerm" />
                        <div class="toggle-block active" id="info-section3">
                            <div class="title" >
                                <h2><a href="#" class="open-close"><spring:message code="com.adminui.data_mapping.CUSTOMSUPPLIERID" text="default text" /></a></h2>
                            </div>
                            <div class="block">

                                <div class="content editable-widget" id="supplierMappingData" style="display:block">
                                    <div class="top-box">
                                        <form action="#" class="add-search-form advanced" id="searchSupplierMappingForm" onsubmit="return false;" >
                                            <fieldset>
                                                <input type="text" value="Search Custom Supplier ID" name="supplierSearchTextDisp" id="supplierSearchTextDisp" onclick='$(this).hide();$("#supplierSearchText").show();$("#supplierSearchText").focus();' />
                                                <input type="text" value="" name="supplierSearchText" id="supplierSearchText" style="display: none"/>
                                                <input type="button" id="subBtn2" value="Submit" onclick="$('#supplierMappingSearchTerm').val($('#supplierSearchText').val());getSupplierMappingsPage(1,$('#supplierMappingSearchTerm').val(),'freshSearch');"/>
                                                <input id="resetBtn1" type="button" style="cursor: pointer;" onclick="$('#supplierSearchText').val('');$('#supplierSearchText').hide();$('#supplierSearchTextDisp').show(); $('#subBtn2').click();" />
                                            </fieldset>
                                        </form>
                                    </div>
                                    <div id="custom_supplier_mappings_section">
                                        <jsp:include page="suppliermapping_table_fragment.jsp" />
                                    </div>
                                </div>
                                <div class="add-slide-blocks">
                                    <lable style="font-size: 7px;">&nbsp;</lable>
                                    <div id="info-section4" style="display: none">
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
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_ACCESS)%>">
                    <div id="tab3">
                        <jsp:include page="profile_group_mapping.jsp" />
                    </div>
                </c:if>

                <c:if test="<%=aclManager.allow(request, Permission.VIEW_UOM_MAPPING)%>">
                    <div id="tab4">
                        <jsp:include page="uom_mapping.jsp" />
                    </div>
                </c:if>

                <c:if test="<%=aclManager.allow(request, Permission.VIEW_CURRENCY_MAPPING)%>">
                    <div id="tab5">
                        <jsp:include page="currency_mapping.jsp" />
                    </div>
                </c:if>
                <c:if test="<%=aclManager.isFeatureAvailable(request, Consts.FEATURE_PURCHASE_EXPRESS) && aclManager.allow(request, Permission.VIEW_CONTENT_SHARE_MAPPING)%>">
                    <div id="tab6">
                        <jsp:include page="power_shopper.jsp" />
                    </div>
                </c:if>

            </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href="vroozi" class="ico-back"><span><spring:message code="com.adminui.data_mapping.BACK" text="default text" /></span></a></li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $( document ).on('change','#materialGroupFile' , function(){ uploadFile('materialGroup');return false; });
        $( document ).on('change','#supplierFile' , function(){ uploadFile('supplierMapping');return false; });
        $( document ).on('change','#profileGroupFile' , function(){ uploadFile('profileGroup');return false; });
        $( document ).on('change','#uomMappingFile' , function(){

            $("#uom_mappings_section").hide();
            $("#uom-info-section2").show();

            uploadFile('uomMapping');return false; });

        $( document ).on('change','#powerShopperMappingFile' , function(){
            $("#power_shopper_section").hide();
            $("#pows-info-section2").show();
            uploadFile('powerShopperMapping');
            return false;
        });

        $( document ).on('change','#currencyMappingFile' , function(){

            $("#currency_mappings_section").hide();
            $("#currency-info-section2").show();

            uploadFile('currencyMapping');return false; });

        $("#searchText").blur(function(){
            if (!$("#searchText").val()) {
                $("#searchTextDisp").show();
                $("#searchText").hide();
            }
        });

        $("#supplierSearchText").blur(function(){
            if (!$("#supplierSearchText").val()) {
                $("#supplierSearchTextDisp").show();
                $("#supplierSearchText").hide();
            }
        });

        setupCategoryMappingTab();

        setupSupplierMappingTab();
        setupProfileGroupMappingTab();

        setupUOMMappingTab();
        $('#uom-info-section2-loading').hide();
        $('#pows-info-section2-loading').hide();
//                $('#uom_message').hide();
//
        $('#uomBottomData').hide();
        $('#uom_mappings_section').hide();
        $('#powsBottomData').hide();
        $('#power_shopper_section').hide();
    });

</script>
