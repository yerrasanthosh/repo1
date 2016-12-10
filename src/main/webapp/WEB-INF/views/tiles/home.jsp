<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.util.ViewHelper,com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
    var gCanApproveCatalog = <%= aclManager.allow(request, Permission.APPROVE_CATALOG)? "true" : "false" %>
    var gCanRejectCatalog = <%= aclManager.allow(request, Permission.REJECT_CATALOG)? "true" : "false" %>
    var gCanOfflineCatalog = <%= aclManager.allow(request, Permission.OFFLINE_CATALOG)? "true" : "false" %>
    var gCanDeactivateCatalog = <%= aclManager.allow(request, Permission.DEACTIVATE_CATALOG)? "true" : "false" %>
    var gCanPublishCatalog = <%= aclManager.allow(request, Permission.PUBLISH_CATALOG)? "true" : "false" %>
    var quote = '<spring:message code="com.adminui.home.Quote" text="default text" />';
    var internalCatalog = '<spring:message code="com.adminui.home.InternalCatalog" text="default text" />';
    var externalCatalog = '<spring:message code="com.adminui.home.ExternalCatalog" text="default text" />';
</script>
<script type="text/template" id="catalogTypeTemplate">
    <div class="row">
        <input type="checkbox" id="CAT-TYPE-ID-4" class="default" onclick="filterCatalogsType(this)" >
        <label for="CAT-TYPE-ID-4"><span class="ui-button-text" onclick="filterCatalogsType($(this).previous())"><spring:message code="com.adminui.home.Quote" text="default text" /></span></label>
    </div>
    <div class="row">
        <input type="checkbox" id="CAT-TYPE-ID-2" class="default" onclick="filterCatalogsType(this)" >
        <label for="CAT-TYPE-ID-2" ><span class="ui-button-text" onclick="filterCatalogsType($(this).previous())"><spring:message code="com.adminui.home.InternalCatalog" text="default text" /></span></label>
    </div>
    <div class="row">
        <input type="checkbox" id="CAT-TYPE-ID-1" class="default" onclick="filterCatalogsType(this)" >
        <label for="CAT-TYPE-ID-1" onclick="filterCatalogsType($(this).previous())"><span class="ui-button-text"><spring:message code="com.adminui.home.ExternalCatalog" text="default text" /></span></label>
    </div>
</script>
<script type="text/template" id="supplierTemplate">
    <div class="row">
        <input type="checkbox" id="SUPP-ID-0" class="default" onclick="toggleCheckboxes(this);filterCatalogsSupplier(this)" >
        <label for="SUPP-ID-0" onclick="filterCatalogsSupplier($(this).previous())"><span class="ui-button-text"><spring:message code="com.adminui.home.SELECTALL" text="default text" /></span></label>
    </div>
    {{#.}}
    <div class="row">
        <input type="checkbox" id="SUPP-ID-{{companyId}}" class="default" onclick="toggleCheckboxes(this);filterCatalogsSupplier(this)" checked="true">
        <label for="SUPP-ID-{{companyId}}" onclick="filterCatalogsSupplier($(this).previous())"><span class="ui-button-text">{{companyName}}</span></label>
    </div>
    {{/.}}
</script>


<div id="main">
<div class="section">
    <ul class="breadcrumbs">
        <li><a href="vroozi"><spring:message code="com.adminui.home.Vroozi" text="default text" /></a></li>
        <li><spring:message code="com.adminui.home.ContentManager" text="default text" /></li>
    </ul>
    <div class="video-btn-holder">
        <!-- <img src="res/images/img-1.gif" alt="" width="26" height="27" />
         <a href="#" class="btn"><span><em>TUTORIAL</em></span></a>-->
    </div>
</div>
<div class="text-holder">
    <p<spring:message code="com.adminui.home.changeStatus" text="default text" /><img width="13" height="14" alt="" src="res/images/img-2.gif">	the button.</p>
</div>
<div class="navigation">
    <div class="btn-holder">
        <c:if test="<%=aclManager.allow(request, Permission.CREATE_CATALOG)%>">
            <div class="popup-holder create-popup">
                <a class="btn btn-select open"  id="create-menue-id"><span><em><strong><spring:message code="com.adminui.home.Create" text="default text" /></strong></em></span></a>
                <div class="popup">
                    <ul>

                        <a class="btn" style="background: url('')" onclick="createCatalog();$('#create-menue-id').click();return false;"><span style="background: url('')"><em style="background: url('')" class="catalog-hover"><spring:message code="com.adminui.home.Catalog" text="default text" /></em></span></a>
                        <a class="btn" style="background: url('')" onclick="createQuoteCatalog();$('#create-menue-id').click();return false;"><span style="background: url('')"><em style="background: url('')" class="catalog-hover"><spring:message code="com.adminui.home.Quote" text="default text" /></em></span></a>
                        <!--li><a href="#lightbox-inline" class="-open-popup-" onclick="alert('Test');createCatalog();">Catalog</a></li-->
                        <!--li><a href="#">SALES QUOTE</a></li>
                        <li><a href="#">SHOPPING LIST</a></li-->
                    </ul>
                </div>
            </div>
        </c:if>

        <c:if test="<%=aclManager.allow(request, Permission.VIEW_CUSTOM_FIELDS)%>">
            <a href="<c:out value="customFields"/>" class="btn alt-opener"><span><em><spring:message code="com.adminui.home.CustomFields" text="default text" /></em></span></a>
        </c:if>
        <a href="#" class="refresh alt-opener" onclick="resetFilter();"><span><em><spring:message code="com.adminui.home.Refresh" text="default text" /></em></span></a>
        <!--a href="#" class="btn hist"><span><em>ACTIVITY HISTORY</em></span></a-->
    </div>

    <div class="filter">
        <form class="status-form" action = 'catalogfilterUrl' id="filter-catalogs" method="post">
            <input type="hidden" id="supplierId" name="supplierId" value="${supplierId}"/>
            <input type="hidden" id="catType" name="catType" value="${catType}"/>
            <input type="hidden" id="filter" name="filter" value="${filter}"/>
            <input type="hidden" id="filterAction" name="filterAction" value=""/>
            <input type="hidden" id="noSupplier" name="noSupplier" value="${noSupplier}"/>
            <fieldset>
                <ul class="options ">
                    <li class="">
                        <span class="li-class"><em><spring:message code="com.adminui.home.FILTEROPTIONS" text="default text" /></em></span>
                        <ul class="accordion">
                            <li>
                                <a class="opener" href="#"><spring:message code="com.adminui.home.SUPPLIER" text="default text" /></a>
                                <div class="slide" id="supplierContainer" style="height: 150px;overflow:auto;">

                                    <%--<div class="row">--%>
                                    <%--<input type="checkbox" id="SUPP-ID-0" class="ui-helper-hidden-accessible" onclick="toggleCheckboxes(this);filterCatalogsSupplier(this)" ${noSupplier}>--%>
                                    <%--<label for="SUPP-ID-0" aria-pressed="true" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-active" role="button" onclick="filterCatalogsSupplier($(this).previous())"><span class="ui-button-text">No Supplier</span></label>--%>
                                    <%--</div>--%>
                                    <c:forEach var="supplier" items="${supplierList}" varStatus="cntr">
                                        <div class="row">
                                            <input type="checkbox" class="default" id="SUPP-ID-${supplier.companyId}" onclick="toggleCheckboxes(this);filterCatalogsSupplier(this)" ${supplier.checked}>
                                            <label for="SUPP-ID-${supplier.companyId}" onclick="filterCatalogsSupplier($(this).previous())"><span class="ui-button-text">${supplier.companyName}</span></label>
                                        </div>
                                    </c:forEach>

                                </div>

                            </li>
                            <li class="active">
                                <a class="opener" href="#"><spring:message code="com.adminui.home.type" text="default text" /></a>
                                <div class="slide" id="CAT-TYPE-FORM">
                                    <c:forEach var="catType1" items="${catTypeList}" varStatus="cntr">
                                        <div class="row">
                                            <input type="checkbox" id="CAT-TYPE-ID-${catType1.id}" class="default" onclick="filterCatalogsType(this)" <c:out value="${catType1.checked}"/> />
                                            <label for="CAT-TYPE-ID-${catType1.id}" ><span class="ui-button-text" onclick="filterCatalogsType($(this).previous())">${catType1.name}</span></label>
                                        </div>
                                    </c:forEach>
                                </div>
                            </li>

                        </ul>
                    </li>
                </ul>
            </fieldset>
        </form>


        <form class="add-search-form" action="#">
            <fieldset class=" ">
                <input type="text" value="Search Within..." class=" " id="searchTerm" name="searchTerm">
							<span class="input-button">
								<input id="searchBtn" type="button" value="Submit" onclick="filterCatalogs()">
                                <input id="resetBtn" type="button" style="cursor: pointer;" onclick="resetFilter();" />
								<span class="loading"></span>
							</span>
            </fieldset>
        </form>
    </div>

    <span style="height: 12px; font-size: 12px; font-style:inherit; color:#ff0000;" id="message-board"></span>
    <!--div class="filter">
        <form action="#" class="status-form">
            <fieldset>
                <ul class="options ">
                    <li>
                        <span class="li-class"><em>FILTER OPTIONS</em></span>
                        <ul class="accordion" >
                            <li>
                                <img class="shadow"  src="res/images/shadow.png" alt="" width="151" height="9" />
                                <a href="#" >SUPPLIER</a>
                            </li>
                            <li class="active"><a href="#" class="opener">type</a>
                                <div class="slide">
                                    <div class="row">
                                        <input id="check-1" type="checkbox" />
                                        <label for="check-1">PENDING</label>
                                    </div>
                                    <div class="row">
                                        <input id="check-2" type="checkbox" />
                                        <label for="check-2">APPROVED</label>
                                    </div>
                                </div>
                            </li>
                            <li><a href="#">method</a></li>
                        </ul>
                    </li>
                </ul>
            </fieldset>
        </form>
    </div-->
</div>

<c:if test="<%=aclManager.allow(request, Permission.VIEW_CATALOG)%>">
    <div class="columns">
        <div class="heading">
            <div class="holder">
                <h2><spring:message code="com.adminui.cataloglanding.workingColumn" text="default text" /></h2>
                <h2><spring:message code="com.adminui.cataloglanding.approvedColumn" text="default text" /></h2>
                <h2><spring:message code="com.adminui.cataloglanding.liveColumn" text="default text" /></h2>
            </div>
        </div>
        <div class="columns-holder">
            <c:forEach var="column" items="${columns}" varStatus="cntr">
                <div class="col ${column.css} connectedSortable" id="${column.id}">
                    <div class="overlay-box">

                    </div>
                    <c:forEach var="catalog" items="${column.data}" varStatus="cntr">
                        <div class="slide-block <%=((CatalogSummary)pageContext.getAttribute("catalog")).isValidationFailed()==true?"red-block":"blue-block"%>"  id='<c:out value="${catalog.catalogId}"/>'>
                            <div class="holder">
                                <div class="frame">
                                    <div class="title">
                                        <h2>
                                            <c:choose>
                                                <c:when test="${catalog.approvedStatus eq 'Processing'
                                                    					or catalog.approvedStatus eq 'Publishing'
                                                    					or catalog.approvedStatus eq 'Approving'
                                                    					or catalog.approvedStatus eq 'Disapproving'
                                                    					or catalog.approvedStatus eq 'Unpublishing'
                                                    					
                                                    					}">
                                                    <a  onclick="alert('Catalog in processing cannot be accessed.');"><c:out value="${catalog.name}" /></a><img src="res/images/mask.png" alt="" width="34" height="27" />
                                                </c:when>
                                                <c:otherwise>
                                                    <a href='catalogDetail?catalogId=<c:out value="${catalog.catalogId}"/>'><c:out value="${catalog.name}" /></a><img src="res/images/mask.png" alt="" width="34" height="27" />
                                                </c:otherwise>
                                            </c:choose>
                                            <input type="hidden" id="parentcat-${catalog.catalogId}" value="${catalog.parentCatalogId}" />
                                            <input type="hidden" id="childcat-${catalog.parentCatalogId}" value="${catalog.catalogId}" />
                                            <input type="hidden" id="columnId-${catalog.catalogId}" value="${column.id}" />
                                            <input type="hidden" id="state-${catalog.catalogId}" value="${catalog.catalogStateId}" />
                                            <input type="hidden" id="appStatus-${catalog.catalogId}" value="${catalog.approvedStatus}"/>

                                        </h2>
                                        <a href="#" class="open-close"><span><spring:message code="com.adminui.home.Closeblock" text="default text" /></span><em><spring:message code="com.adminui.home.Openblock" text="default text" /></em></a>
                                        <div class="edit-btn alt-edit-btn">
                                            <div class="drop">
                                                <div class="drop-holder">
                                                    <div class="drop-frame">
                                                        <div class="drop-c">
                                                            <ul>
                                                                <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                                                                    <c:if test="${catalog.quoteId eq null || catalog.quoteId eq ''}">
                                                                        <li class="edit"><a href="#" onclick='editCatalog("<c:out value='${catalog.catalogId}'/>");'><spring:message code="com.adminui.home.EDIT" text="default text" /></a></li>
                                                                    </c:if>
                                                                    <c:if test="${catalog.quoteId ne null && catalog.quoteId ne ''}">
                                                                        <li class="edit"><a href="#" onclick='editQuote("<c:out value='${catalog.catalogId}'/>");'><spring:message code="com.adminui.home.EDIT" text="default text" /></a></li>
                                                                    </c:if>
                                                                </c:if>
                                                                <!--
                                                                <li class="history"><a href="#">History</a></li>
                                                                <li class="marge"><a href="#">MERGE WITH...</a></li>
                                                                <li class="export"><a href="#">EXPORT</a>
                                                                    <div class="drop-2">
                                                                        <div class="drop-2-holder">
                                                                            <div class="drop-2-frame">
                                                                                <div class="drop-2-c">
                                                                                    <ul>
                                                                                        <li class="xml"><a href="#">XML</a></li>
                                                                                        <li class="xls"><a href="#">XLS</a></li>
                                                                                        <li class="csv"><a href="#">CSV</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </li>-->
                                                                <c:if test="<%=aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                                                                    <c:if test="${catalog.catalogStateId==1}">
                                                                        <li class="reject"><a href="#">reject</a></li>
                                                                    </c:if>
                                                                </c:if>

                                                                <c:if  test="${catalog.active && column.id=='live'}">
                                                                    <li id="lnkDeactivate"> <a href="#" class="deactivate"><spring:message code="com.adminui.home.Deactivate" text="default text" /></a></li>
                                                                    <li id="lnkActivate" class="activate" style="display:none;"><a href="#"><spring:message code="com.adminui.home.Activate" text="default text" /></a></li>
                                                                </c:if>

                                                                <c:if  test="${catalog.active && column.id=='live' && catalog.revision >1}">
                                                   
                                                                    <li id="lnkHistory"> <a href="#" class="history" onclick='catalogChangeHistory("<c:out value='${catalog.catalogId}'/>");'><spring:message code="com.adminui.home.ChangeReport" text="default text" /></a></li>
                                                                </c:if>

                                                                <c:if test="${(!catalog.active) && column.id=='live'}">
                                                                    <li id="lnkActivate" ><a href="#" class="activate"><spring:message code="com.adminui.home.Activate" text="default text" /></a></li>
                                                                    <li id="lnkDeactivate" style="display:none;"><a href="#" class="deactivate"><spring:message code="com.adminui.home.Deactivate" text="default text" /></a></li>
                                                                </c:if>

                                                                <c:if test="<%=aclManager.allow(request, Permission.DELETE_CATALOG)%>">
                                                                    <li class="del"><a href="#"><spring:message code="com.adminui.home.Delete" text="default text" /></a></li>
                                                                </c:if>
                                                                <li class="edit"><a href="#" onclick='exportCatalog("<c:out value='${catalog.catalogId}'/>");'><spring:message code="com.adminui.home.EXPORT" text="default text" /></a></li>

                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${catalog.showSubHeader}">

                                        <div id='<c:out value="${catalog.catalogId}"/>catStatus' class="status">
                                            <c:if test="${not empty catalog.errorDescription}">
                                                    <span class="type">
                                                        <%--<a href="view-catalog-creation-error?catalogId=<c:out value="${catalog.catalogId}"/>&errorDescription=<c:out value="${catalog.errorDescription}"/>" target="_blank"> View Catalog Creation errors</a>--%>
                                                        <a href="javascript:void(0)" onclick="showCatalogUploadingErrors('${ViewHelper.escapeString(catalog.name)}','${ViewHelper.escapeString(catalog.errorDescription)}','${catalog.catalogId}')"><spring:message code="com.adminui.home.Errorwithprocessing" text="default text" /></a>
                                                    </span>


                                            </c:if>

                                            <c:if test='${empty catalog.errorDescription && catalog.failedRecords >0}'>
                                                    <span><a href='view-error-report?catId=<c:out value="${catalog.catalogId}"/>' target="_blank"> <spring:message code="com.adminui.home.View" text="default text" /><c:out value="${catalog.failedRecords}"/> <spring:message code="com.adminui.home.issues" text="default text" /></a>
                                                    , <c:out value="${catalog.approvedStatus}"/></span>
                                            </c:if>
                                            <c:if test="${empty catalog.errorDescription && (catalog.failedRecords==null || catalog.failedRecords==0 )}">
                                                <span><c:out value="${catalog.approvedStatus}"/></span>
                                            </c:if>
                                        </div>

                                    </c:if>
                                    <c:if test="${!catalog.showSubHeader}">

                                        <div id='<c:out value="${catalog.catalogId}"/>catStatus' class="status" style="display:none;">
                                            <c:if test="${not empty catalog.errorDescription}">
                                                    <span class="type">
                                                        <%--<a href="view-catalog-creation-error?catalogId=<c:out value="${catalog.catalogId}"/>&errorDescription=<c:out value="${catalog.errorDescription}"/>" target="_blank"> View Catalog Creation errors</a>--%>
                                                        <a href="javascript:void(0)" onclick="showCatalogUploadingErrors('${ViewHelper.escapeString(catalog.name)}','${ViewHelper.escapeString(catalog.errorDescription)}')"><spring:message code="com.adminui.home.Errorwithprocessing" text="default text" /></a>
                                                    </span>
                                            </c:if>
                                            <c:if test='${empty catalog.errorDescription && catalog.failedRecords >0}'>
                                                    <span><a href='view-error-report?catId=<c:out value="${catalog.catalogId}"/>' target="_blank"> <spring:message code="com.adminui.home.View" text="default text" /><c:out value="${catalog.failedRecords}"/> <spring:message code="com.adminui.home.errors" text="default text" /></a>
                                                    , <c:out value="${catalog.approvedStatus}"/></span>
                                            </c:if>
                                            <c:if test="${empty catalog.errorDescription && (catalog.failedRecords==null || catalog.failedRecords==0 )}">
                                                <span><c:out value="${catalog.approvedStatus}"/></span>
                                            </c:if>
                                        </div>

                                    </c:if>
                                    <div class="block">
                                        <dl>
                                            <dt><spring:message code="com.adminui.home.VersionNumber" text="default text" /></dt>
                                            <dd><c:out value="${catalog.revision}"/> <%--&nbsp;&nbsp;&nbsp;<a href="#">(Previous Versions)</a> --%>
                                                <br/></dd>
                                            <c:if test="${not empty catalog.supplierName}">
                                                <dt><spring:message code="com.adminui.home.Supplier" text="default text" /></dt>
                                                <dd><c:out value="${catalog.supplierName}"/><br/></dd>
                                            </c:if>
                                            <dt><spring:message code="com.adminui.home.CreatedOn" text="default text" /></dt>
                                            <dd><%=ViewHelper.getFormattedDate(request, ((CatalogSummary)pageContext.getAttribute("catalog")).getCreatedOn())%><br/></dd>
                                            <dt><spring:message code="com.adminui.home.CreatedBy" text="default text" /></dt>
                                            <dd><c:out value="${catalog.creatorName}"/><br/></dd>
                                            <dt><spring:message code="com.adminui.home.Type" text="default text" /></dt>
                                            <dd><c:out value="${catalog.catalogType}"/><br/></dd>
                                            <dt><spring:message code="com.adminui.home.Method" text="default text" /></dt>
                                            <dd><c:out value="${catalog.originationMethodDesc}"/><br/></dd>
                                            <dt><spring:message code="com.adminui.home.NumberofItems" text="default text" /></dt>
                                            <dd><c:out value="${catalog.numberOfItem}"/><br/></dd>
                                            <dt><spring:message code="com.adminui.home.Status" text="default text" /></dt>
                                            <dd id='<c:out value="${catalog.catalogId}"/>approvedStatus'><c:out value="${catalog.approvedStatus}"/><br/></dd>
                                            <dt><spring:message code="com.adminui.home.Active" text="default text" /></dt>
                                            <dd> <span id="spnActiveSts">${catalog.activeStatus}</span><br/></dd>
                                                   <c:set var="updatorName" value="${fn:trim(catalog.updatorName)}" /> 
                                           <c:if test="${fn:length(updatorName)>0}">
                                            	<dt><spring:message code="com.adminui.home.UpdatedOn" text="default text" /></dt>
                                				<dd><span><%=ViewHelper.getFormattedDate(request, ((CatalogSummary)pageContext.getAttribute("catalog")).getLastUpdated())%><br/>
                                				
                                				</dd>
                                				
                                				<dt><spring:message code="com.adminui.home.UpdatedBy" text="default text" /></dt>
                                				<dd><span><c:out value="${catalog.updatorName}"/></span><br/></dd>
                                            
                                            </c:if>
                                        </dl>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>

<div class="navigation">
    <div class="btn-holder">
        <c:if test="<%=aclManager.allow(request, Permission.CREATE_CATALOG)%>">
            <div class="popup-holder create-popup">

                <a href="#" class="btn btn-select open" id="create-menue-id2"><span><em><strong><spring:message code="com.adminui.home.Create" text="default text" /></strong></em></span></a>
                <div class="popup">
                    <ul>
                        <a class="btn" style="background: url('')" onclick="createCatalog();$('#create-menue-id').click();return false;"><span style="background: url('')"><em style="background: url('')"><strong><spring:message code="com.adminui.home.Catalog" text="default text" /></strong></em></span></a>
                        <!--li><a href="#lightbox-inline" class="-open-popup-" onclick="alert('Test');return false;createCatalog();">Catalog</a></li-->
                            <%--<li><a href="#lightbox-inline-extCatalog" class="open-popup">PunchOut Catalog</a></li>--%>
                            <%--<li><a href="#">SALES QUOTE</a></li>--%>
                            <%--<li><a href="#">SHOPPING LIST</a></li>--%>
                    </ul>
                </div>
            </div>
        </c:if>
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_CUSTOM_FIELDS)%>">
            <a href="<c:out value="customFields"/>" class="btn alt-opener"><span><em><spring:message code="com.adminui.home.CustomFields" text="default text" /></em></span></a>
        </c:if>
        <!--a href="#" class="btn hist"><span><em>ACTIVITY HISTORY</em></span></a-->
        <!--a href="#" class="btn bin"><span><em>RECYCLE BIN</em></span></a-->
    </div>
</div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="catalogUploadingError">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.home.Close" text="default text" /></a>
                    <h2><div id="catalogNameOfErrorCatalog"></div></h2>
                </div>

                <div id="labelForCatalogError"></div>
                <br/>
                <div id="reportDiv"></div>
                <a href="#" class="btn-cancel"><spring:message code="com.adminui.home.Cancel" text="default text" /></a>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //$(document).ready(function(){
    //var checkoutExtCatalog = ${(param['checkoutExtCatalog']==null)?false:param['checkoutExtCatalog']};
    //if(checkoutExtCatalog && checkoutExtCatalog == true) {
    //	alert("You need to log in as Shopper in order to check out item back to smartOCI");
    //	window.location.search = window.location.search.replace(/(\?|&)checkoutExtCatalog=true$/,'');
    //}
    //});

</script>
