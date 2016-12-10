<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"
    import="com.vroozi.customerui.catalog.model.CatalogSummary,
    com.vroozi.customerui.profile.model.Profile,
    com.vroozi.customerui.util.ViewHelper,
    com.vroozi.customerui.catalog.model.ExternalCatalogFields,
    com.vroozi.customerui.acl.model.Permission"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript">
    var pageName = "catalog_detail";

    $(document).ready(function() {
    	$('#searchCatItemsForm').submit(function() {
    			filterCatalogItems("${param['catalogId']}", 1);
                return false;
        });
    });

    var gCurrentCatalogId = '${param['catalogId']}';
    var gCatalogItemTableFragmentServiceUrl =  '<c:url value="/getCatalogItemsTableFragment" />';
    var removeCatalogCustomField = '<c:url value="/deleteCatalogCustomField" />';
    var editCatalogCustomField = '<c:url value="/editCatalogCustomField" />';
    var addCatalogCustomField = '<c:url value="/addCatalogCustomField" />';
    var associateCatalogCustomFieldURL = '<c:url value="/associateCatalogCustomField" />';
    var updateCatalogCustomFieldListURL = '<c:url value="/updateCatalogCustomFieldList" />';

    // todo: clean-up.
//    var gCatalogItemsPageNum = 1;
    <%--var gCatalogItemPageSize = ${catalogItemPageSize};--%>
    <%--var gCatalogItemTotalNumOfItems = ${catalogItemTotalNumOfItems};--%>
    <%--var gCatalogItemCurrentNumOfItems = ${catalogItemCurrentNumOfItems};--%>
    <%--var gCatalogItemTotalNumOfPages = ${catalogItemTotalNumOfPages};--%>
</script>

<script type="text/javascript" src="res/js/adminui_catalog_detail.js"></script>

<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.catalog_detail.Vroozi" text="default text" /></a></li>
            <li><a href="catalog"><spring:message code="com.adminui.catalog_detail.ContentManager" text="default text" /></a></li>
            <li><spring:message code="com.adminui.catalog_detail.CatalogOverview" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
          <!--  <img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>TUTORIAL</em></span></a>-->
        </div>
    </div>
    <div class="main-holder">
        <div id="content">
        <input type="hidden" id="catalogParentId" name="catalogParentId" value="${catalogSummary.parentCatalogId}"/>
            <h1><c:out value="${catalogSummary.name}"/></h1>
            <div class="content-block toggle-block active" id="edit-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.catalog_detail.SUMMARY" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content reorder-widget editable-widget">
                        <div class="col">
                            <dl>
                                <dt><spring:message code="com.adminui.catalog_detail.VersionNumber" text="default text" /></dt>
                                <dd><span><c:out value="${catalogSummary.revision}"/> <%--&nbsp;&nbsp;&nbsp;<a href="#">(Previous Versions)</a> --%> 
                                	</span><br/></dd>
                                <c:if test="${not empty catalogSummary.supplierName}">
                                    <dt><spring:message code="com.adminui.catalog_detail.Supplier" text="default text" /></dt>
                                    <dd><span><c:out value="${catalogSummary.supplierName}"/></span><br/></dd>
                                </c:if>
                                <dt><spring:message code="com.adminui.catalog_detail.CreatedOn" text="default text" /></dt>
                                <dd><span><%=ViewHelper.getFormattedDate(request, ((CatalogSummary)request.getAttribute("catalogSummary")).getCreatedOn())%></span><br/></dd>
                                <dt><spring:message code="com.adminui.catalog_detail.CreatedBy" text="default text" /></dt>
                                <dd><span><c:out value="${catalogSummary.creatorName}"/></span><br/></dd>
                                <dt><spring:message code="com.adminui.catalog_detail.Type" text="default text" /></dt>
                                <dd><span><c:out value="${catalogSummary.catalogType}"/></span><br/></dd>
                                <dt><spring:message code="com.adminui.catalog_detail.Method" text="default text" /></dt>
                                <dd><span><c:out value="${catalogSummary.originationMethodDesc}"/></span><br/></dd>
                                <dt><spring:message code="com.adminui.catalog_detail.TotalNumberofItems" text="default text" /></dt>
                                <dd><span><c:out value="${catalogSummary.outputRecords}"/></span><br/></dd>
                                <dt><spring:message code="com.adminui.catalog_detail.Status" text="default text" /></dt>
                                <dd><span><c:out value="${catalogSummary.approvedStatus}"/></span><br/></dd>
                                <dt><spring:message code="com.adminui.catalog_detail.UpdatedOn" text="default text" /></dt>
                                <dd><span><%=ViewHelper.getFormattedDate(request, ((CatalogSummary)request.getAttribute("catalogSummary")).getLastUpdated())%></span><br/></dd>
                                <dt><spring:message code="com.adminui.catalog_detail.UpdatedBy" text="default text" /></dt>
                                <dd><span><c:out value="${catalogSummary.updatorName}"/></span><br/></dd>
                            </dl>
                        </div>
                        <div class="col alt-col">
                            <h3><spring:message code="com.adminui.catalog_detail.ItemApprovalSummary" text="default text" /></h3>
                            <table class="summary-table">
                                <tfoot>
                                <tr>
                                    <td><spring:message code="com.adminui.catalog_detail.Total" text="default text" /></td>
                                    <td class="a-right" id="total-items-count"><c:out value="${catalogSummary.approvedItems + catalogSummary.rejectedItems + catalogSummary.pendingItems}"/></td>
                                </tr>
                                </tfoot>
                                <tbody>
                                <tr>
                                    <th><spring:message code="com.adminui.catalog_detail.Approved" text="default text" /></th>
                                    <td class="a-right">
                                    	<input type="hidden" id="txtApproveCount" value="${catalogSummary.approvedItems}" />
                                       	<div>
                                       		<dd id='approvedCount'><c:out value="${catalogSummary.approvedItems}"/></dd>
                                       	</div>

                                    
                                    </td>
                                </tr>
                                <tr>
                                    <th><spring:message code="com.adminui.catalog_detail.Rejected" text="default text" /></th>
                                    <td class="a-right">
                                    	<input type="hidden" id="txtRejectCount" value="${catalogSummary.rejectedItems}" />
                                       	<div>
                                       		<dd id='rejectedCount'><c:out value="${catalogSummary.rejectedItems}"/></dd>
                                       	</div>                                    
                                    </td>
                                </tr>
                                <tr>
                                    <th><spring:message code="com.adminui.catalog_detail.PendingApproval" text="default text" /></th>
                                    <td class="a-right">
                                    	<input type="hidden" id="txtPendingCount" value="${catalogSummary.pendingItems}" />
                                       	<div>
                                       		<dd id='pendingCount'><c:out value="${catalogSummary.pendingItems}"/></dd>
                                       	</div>                                      
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_CATALOG)%>">
                    <div class="content-block toggle-block active" id="create-item-section">
                <div class="headline">
                    <c:if test="${catalogSummary.quoteId eq null || catalogSummary.quoteId eq ''}">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.catalog_detail.CatalogItems" text="default text" /></a></h2>
                    </c:if>
                    <c:if test="${catalogSummary.quoteId ne null && catalogSummary.quoteId ne ''}">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.catalog_detail.QuoteItems" text="default text" /></a></h2>
                    </c:if>
                </div>
                <div class="block">
                    <div class="content editable-widget">
                    <div class="top-box">
                    	<form action="javascript:void(0)" id="searchCatItemsForm" class="add-search-form" >
                            <fieldset>
                                <input id="searchCriteria" type="text" value="Search Within..." />
                                <input id="searchCriteriaButton" type="submit"  />
                                <input id="resetBtn" class="checkboxReset" type="button" style="cursor: pointer;" onclick="$('#searchCriteria').val('Search Within...'); $('#searchCriteriaButton').click();" />
                            </fieldset>
                        </form>
                       <!-- <form action="#" class="status-form">
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
                        <form action="#" class="form-sort">
                            <fieldset>
                                <label>View:</label>
                                <select>
                                    <option>All</option>
                                </select>
                                <label>Sort By: </label>
                                <select>
                                    <option>Name</option>
                                </select>
                            </fieldset>
                        </form>-->
                    </div>
                 <!--    <div class="scrollable-area vscrollable">
                        <div class="text"> -->
                        	<form action="/approveItems" id="cat-items-form" method="post" enctype="text/plain" class="checkboxResetForm">
                                <input name="catalogId" type="hidden" value='<c:out value="${param['catalogId']}"/>' />
                            <table class="table-data alt-table-data">
                                <thead>
                                    <tr>
                                        <th class="td-select">
                                            <input type="checkbox" class="check-allbox" name="check-all" id="check153" onclick="checkAllCatalogItems(this.checked)"/>
                                            <label for="check153"></label>
                                        </th>
                                        <th class="a-left"><spring:message code="com.adminui.catalog_detail.DESCRIPTION" text="default text" /></th>
                                        <th><spring:message code="com.adminui.catalog_detail.VENDORMAT" text="default text" /></th>
                                        <th><spring:message code="com.adminui.catalog_detail.PRICE" text="default text" /></th>
                                        <th><spring:message code="com.adminui.catalog_detail.MFR_PART" text="default text" /></th>
                                        <th><spring:message code="com.adminui.catalog_detail.STATUS" text="default text" /></th>
                                    </tr>
                                </thead>
                                <tbody id="catalog_item_table_rows_body">
                                    <!-- Catalog Item Content -->
                                </tbody>
                            </table>
                           </form>
                     <!--    </div>
                    </div>
 -->
                    <%--<c:if test="${catalogItemTotalNumOfPages > 1}">--%>
                    <div id="catalog-pagination-controls" class="bottom-data" style="display: none; padding-bottom: 9px; padding-top: 9px">
                        <div class="pager">
                            <!--Page <span id="catalog_items_current_page_num"> </span> of</span><span id="catalog_items_last_page_num"></span>-->
                            <span id="catalog_items_current_page_num"><spring:message code="com.adminui.catalog_detail.Page" text="default text" /></span>
                            <ul>
                                <li><a id="catalog-item-prev-page" class="btn-prev" onclick="catalogItemPaginationClick('${param['catalogId']}', gCatalogItemsPageNumber-1)"></a></li>
                                <li><a id="catalog-item-next-page" class="btn-next" onclick="catalogItemPaginationClick('${param['catalogId']}', gCatalogItemsPageNumber+1)"></a></li>
                            </ul>
                        </div>
                        <strong class="pages" id="catalog_items_current_record_range"><spring:message code="com.adminui.catalog_detail.TotalRecords" text="default text" /><span id="catalog-item-count-start"></span>-<span id="catalog-item-count-end"></span> of <span id="catalog-item-count-total"></span></strong>
                        <input type="hidden" value="gCatalogItemsPageNum" id="pNo"/>
                    </div>
                    <%--</c:if>--%>
					
                    <div class="move-items">
                        <div class="holder">
                        	<!-- 
                            <label>Move Selected Items to: </label>
                            <select>
                                <option>002 Bolts</option>
                            </select>
                            <input type="button" value="Move Item(s)" />
                             -->
                        </div>
                    </div>
                    <div class="function">
                        <ul>
                            <c:if test="<%=aclManager.allow(request, Permission.APPROVE_ITEM)%>">
                            	<c:if test="${catalogSummary.catalogStateId != 4}">
                                	<li><a href="javascript:void(0)" class="ico-approve" onclick='approveItems("<c:out value='${catalogSummary.catalogId}'/>");return false;'><span><em><spring:message code="com.adminui.catalog_detail.Approve" text="default text" /></em></span></a></li>
                                </c:if>
                            </c:if>
                            <c:if test="<%=aclManager.allow(request, Permission.REJECT_ITEM)%>">
                            	<c:if test="${catalogSummary.catalogStateId==1 || catalogSummary.catalogStateId==3}">
                                	<li><a href="javascript:void(0)" class="ico-reject" onclick='rejectItems("<c:out value='${catalogSummary.catalogId}'/>");return false;'><span><em><spring:message code="com.adminui.catalog_detail.Reject" text="default text" /></em></span></a></li>
                                </c:if>
                            </c:if>
                            <%--<li><a href="#lightbox-delete-catItems" class="ico-delete open-popup"><span><em>DELETE</em></span></a></li>--%>
                            <c:if test="<%=aclManager.allow(request, Permission.DELETE_ITEM)%>">
                            	<c:if test="${catalogSummary.catalogStateId==1 || catalogSummary.catalogStateId==3}">
                                	<li class="delete-catItems"><a href="javascript:void(0)" class="ico-delete"><span><em><spring:message code="com.adminui.catalog_detail.DELETE" text="default text" /></em></span></a></li>
                                </c:if>
                            </c:if>
                            <c:if test="<%=aclManager.allow(request, Permission.PUPLISH_ITEM)%>">
                                <c:if test="${catalogSummary.catalogStateId == 2}">
                                    <li><a href="javascript:void(0)" class="ico-publish" onclick='publishItems("<c:out value='${catalogSummary.catalogId}'/>");return false;'><span><em><spring:message code="com.adminui.catalog_detail.PUBLISH" text="default text" /></em></span></a></li>
                                </c:if>
                            </c:if>
                        </ul>
                    </div>
                </div>
                </div>
            </div>
                </c:if>


            <c:if test="${catalogSummary.catalogTypeId==1 && (catalogExternalFields != null && catalogExternalFields.size() > 0)}">
                <div class="add-slide-blocks" style="margin-top:5px;">
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_CATALOG)%>">
                        <div class="content-block toggle-block active" id="catalog-prop">
                    <div class="headline">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.catalog_detail.CatalogProperties" text="default text" /></a></h2>
                    </div>
                    <div class="block">
                        <div class="content reorder-widget editable-widget">
                            <form id="update-external-fields-form" action="updateExternalFields" method="POST">
                            	<input type="hidden" id="extCommunicationMethodInput" name="extCommunicationMethodInput" value="${catalogSummary.extCommunicationMethod}" />
                                <div class="row" id="extMethod2">
                                    <select name="extCatalogMethodSelect" id="extCatalogMethodSelect" disabled>
                                        <option id="POST" value="POST" <%= ("POST".equalsIgnoreCase(((String)request.getAttribute("extCatalogMethod"))))? "selected" :"" %> ><spring:message code="com.adminui.catalog_detail.CatalogProperties.POST" text="default text" /></option>
                                        <option id="GET" value="GET" <%= ("GET".equalsIgnoreCase(((String)request.getAttribute("extCatalogMethod"))))? "selected" :"" %> ><spring:message code="com.adminui.catalog_detail.CatalogProperties.GET" text="default text" /></option>
                                    </select>
                                </div>
                                <br />
                                <!--div class="btn-holder">
                                    <a class="btn-add-external-catalog" id="btn-add-external-catalog-id"><span><em>ADD</em></span></a>
                                </div-->
                                <table class="table-data external-catalog" id="table-external-catalog-id-1">
                                    <thead>
                                    <tr>
                                        <th class="td-select"><!--input type="checkbox" class="check-allbox" name="check-all10" id="check10-1" onclick="toggleAllCheckboxItems(this.checked, 'update-external-fields-form');" /><label for="check10-1"></label--></th>
                                        <th class="a-center th-name" ><spring:message code="com.adminui.catalog_detail.CatalogProperties.SEQ" text="default text" /></th>
                                        <th class="a-center th-name" ><spring:message code="com.adminui.catalog_detail.CatalogProperties.NAME" text="default text" /></th>
                                        <th colspan="2"><spring:message code="com.adminui.catalog_detail.CatalogProperties.Value" text="default text" /></th>
                                    </tr>
                                    </thead>
                                    <tbody  id="table-body-external-catalog2">
                                        <c:forEach var="field" items="${catalogExternalFields}" varStatus="cntr1">
                                            <tr class="added" id="row_${cntr1.count}_1">
                                                <td class="td-select">
                                                </td>

                                                <td class="a-left td-sequence-catalog"><div><input class="field-sequence" type="text" style="width:14px;" value="${field.sequence}" name="fields[${cntr1.count}].sequence"  disabled /></div></td>
                                                <td class="a-left td-name-catalog"><div><input type="text" style="width:240px;" value="${field.name}" name=fields[${cntr1.count}].name class="field-name"  disabled /></div></td>
                                                <td><div><input type="text" style="width:240px;" value="${field.value}" name=fields[${cntr1.count}].value class="field-value"  disabled /><label style="margin-top: 6px" class="td-select"> OR </label></div></td>
                                                <td>
                                                    <!--select name="fields[${cntr1.count}].dynamicValue " id="s-val" class="field-dynamic-value"><option value="">Or Select Dynamic Value</option><option value="NEW_ITEM-VENDORMAT">NEW_ITEM-VENDORMAT</option><option value="SY-UNAME">SY-UNAME</option></select-->
                                                    <select class="alt-select choose-tab-select" id="s-val_${cntr1.count}" name ="fields[${cntr1.count}].dynamicValue" disabled>
                                                        <option value=""><spring:message code="com.adminui.catalog_detail.SelectDynamicValue" text="default text" /></option>
                                                        <option  value="NEW_ITEM-VENDORMAT" <%= ("NEW_ITEM-VENDORMAT".equalsIgnoreCase(((ExternalCatalogFields)pageContext.getAttribute("field")).getDynamicValue()))? "selected" :"" %> >NEW_ITEM-VENDORMAT</option>
                                                        <option value="SY-UNAME" <%= ("SY-UNAME".equalsIgnoreCase(((ExternalCatalogFields)pageContext.getAttribute("field")).getDynamicValue()))? "selected" :"" %>>SY-UNAME</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                                <br /><br />
                                <div class="function">
                                    <ul>
                                        <li><a href="#" class="ico-reorder" onclick="createExternalCatalogUrl('<fmt:bundle basename="adminui"><fmt:message key="cXMLPunchOutUrl"/></fmt:bundle>');"><span><em><spring:message code="com.adminui.catalog_detail.ValidatePunchOut" text="default text" /></em></span></a></li>
                                        <!--li><a class="ico-remove" id="external-catalog-remove2"><span><em>REMOVE</em></span></a></li>
                                        <li><input type="button" value="Save" onclick="$('#update-external-fields-form').submit();" /></li-->
                                    </ul>
                                </div>
                                <input type="hidden" name="catalogId" id="catalogId2" value='<c:out value="${param['catalogId']}"/>' maxlength="10"/>
                            </form>
                        </div>
                    </div>
                </div>
                    </c:if>
                </div>
            </c:if>

                <div class="add-slide-blocks">
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_CUSTOM_FIELDS)%>">
                        <div class="toggle-block" id="create-field-section">
                        <div class="title">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.catalog_detail.Customfields" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <form action="/updateCatalogCustomField" id="updateCatalogCustomFieldId" method="post">
                                <input name="catalogId" type="hidden" value='<c:out value="${param['catalogId']}"/>' />
                                <div class="content reorder-widget editable-widget" id="customFieldContentDiv">
                                <div class="btn-holder">
                                    <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                                        <a class="btn-add-custom-fields" onclick="getCustomFields('<c:out value="${param['catalogId']}"/>','${catalogSummary.catalogTypeId}');"><span><em><spring:message code="com.adminui.catalog_detail.ADDCUSTOMFIELDS" text="default text" /></em></span></a>
                                        <c:if test="<%=aclManager.allow(request, Permission.CREATE_CUSTOM_FIELDS)%>">
                                            <a href="#create-custom-field" onclick="resetOverlay();" class="btn-create-custom-fields open-popup"><span><em><spring:message code="com.adminui.catalog_detail.CREATECUSTOMFIELDS" text="default text" /></em></span></a>
                                        </c:if>
                                    </c:if>
                                </div>
                                <table class="table-data" id="custom-field-table">
                                    <thead>
                                        <tr>
                                            <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all3" id="check3-1" /><label for="check3-1"></label></th>
                                            <th class="a-left"><spring:message code="com.adminui.catalog_detail.NAME" text="default text" /></th>
                                            <th><spring:message code="com.adminui.catalog_detail.FIELDTYPE" text="default text" /></th>
                                            <th><spring:message code="com.adminui.catalog_detail.FIELDMAPPING" text="default text" /></th>
                                            <th><spring:message code="com.adminui.catalog_detail.REQUIRED" text="default text" /></th>
                                            <th><spring:message code="com.adminui.catalog_detail.POSTFILTER" text="default text" /></th>
                                            <th class="td-last"><spring:message code="com.adminui.catalog_detail.SEARCHABLE" text="default text" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% int customFieldCounter = 0; %>
                                        <c:forEach var="entry" items="${catalogCustomFields}" varStatus="cntr1">
                                            <tr>
                                                <td id="td_${entry.key}" class="td-select"> <div onclick="if(document.getElementById(${entry.key}).checked){$('#${entry.key}').prop('checked', false);$('#td_${entry.key} div label').removeClass('ui-state-active');}else{$('#${entry.key}').prop('checked', true);$('#td_${entry.key} div label').addClass('ui-state-active');}return false;">
                                                    <a href="#" class="btn-up-down"><span class="up-arrow">up</span> <span class="down-arrow">down</span></a>
                                                    <input type="checkbox" name="catalogCustomFieldId" class="target-chbox" value="${entry.value.id}" class="target-chbox" id="${entry.key}" /><label for="${entry.key}"></label>
                                                </div>
                                                </td>

                                                    <td class="a-left td-name">
                                                    <div class="field">
                                                        <!-- Business requested to comment this one for now. 11/05/2012-->
                                                        <!--span><a href="#" onclick="getCatalogCustomField(--$--{entry.value.id});" class="open-popup">--$--{entry.value.fieldName}</a></span-->
                                                        <span>${entry.value.fieldName}</span>
                                                        <input type="text" value="Size" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <c:if test="${entry.value.fieldType eq 'text'}"><spring:message code="com.adminui.catalog_detail.InputFieldSmall" text="default text" /></c:if>
                                                    <c:if test="${entry.value.fieldType eq 'mediumtext'}"><spring:message code="com.adminui.catalog_detail.InputFieldMedium" text="default text" /></c:if>
                                                    <c:if test="${entry.value.fieldType eq 'largetext'}"><spring:message code="com.adminui.catalog_detail.InputFieldLarge" text="default text" /></c:if>
                                                    <c:if test="${entry.value.fieldType eq 'list'}"><spring:message code="com.adminui.catalog_detail.DropDownList" text="default text" /></c:if>
                                                    <c:if test="${entry.value.fieldType eq 'fixed'}"><spring:message code="com.adminui.catalog_detail.FixedValue" text="default text" /></c:if>
                                                    <c:if test="${entry.value.fieldType eq 'flag'}"><spring:message code="com.adminui.catalog_detail.Flag" text="default text" /></c:if>
                                                </td>
                                                <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><select name="mappingIdList">
                                                    <option value="0_${entry.value.id}" <c:if test="${entry.value.mappingId eq null || entry.value.mappingId eq '0'}">selected="selected"</c:if>></option>
                                                    <option value="1_${entry.value.id}" <c:if test="${entry.value.mappingId eq '1'}">selected="selected"</c:if>>NEW_ITEM-LONGTEXT</option>
                                                    <option value="2_${entry.value.id}" <c:if test="${entry.value.mappingId eq '2'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD1</option>
                                                    <option value="3_${entry.value.id}" <c:if test="${entry.value.mappingId eq '3'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD2</option>
                                                    <option value="4_${entry.value.id}" <c:if test="${entry.value.mappingId eq '4'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD3</option>
                                                    <option value="5_${entry.value.id}" <c:if test="${entry.value.mappingId eq '5'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD4</option>
                                                    <option value="6_${entry.value.id}" <c:if test="${entry.value.mappingId eq '6'}">selected="selected"</c:if>>NEW_ITEM-CUST_FIELD5</option>
                                                </select></td>
                                                <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><input type="checkbox" name="catalogCustomRequired" value="required_${entry.value.id}" id="required_${entry.value.id}" <c:if test="${entry.value.required}">checked="checked"</c:if> /><label <c:if test="${entry.value.fieldType eq 'fixed' || entry.value.fieldType eq 'flag'}">class="disable"</c:if> for="required_${entry.value.id}"></label></td>
                                                <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><input type="checkbox" name="catalogCustomPostFilterable" value="postFilterable_${entry.value.id}" id="postFilterable_${entry.value.id}" <c:if test="${entry.value.postFilterable}">checked="checked"</c:if> /><label <c:if test="${entry.value.fieldType ne 'fixed' && entry.value.fieldType ne 'list'}">class="disable"</c:if> for="postFilterable_${entry.value.id}"></label></td>
                                                <td onclick="$('#${entry.key}').prop('checked', true);$('#td_${entry.key} label').addClass('ui-state-active');"><input type="checkbox" name="catalogCustomSearchable" value="searchable_${entry.value.id}" id="searchable_${entry.value.id}" <c:if test="${entry.value.searchable}">checked="checked"</c:if> /><label <c:if test="${entry.value.fieldType ne 'list' && entry.value.fieldType ne 'fixed'}">class="disable"</c:if> for="searchable_${entry.value.id}"></label></td>
                                            </tr>
                                            <% ++customFieldCounter; %>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr><td colspan="7"><spring:message code="com.adminui.catalog_detail.TotalRecords" text="default text" /><%=customFieldCounter%></td></tr>
                                    </tfoot>
                                </table>
                                <div class="function">
                                    <ul>
                                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                                            <li><a href="#" class="ico-reorder" onclick="selectAllCfs(); return false;"><span><em><spring:message code="com.adminui.catalog_detail.REORDER" text="default text" /></em></span></a></li>
                                            <li><a class="ico-remove" onclick="removeCatalogCustomFields(); return false;"><span><em><spring:message code="com.adminui.catalog_detail.REMOVE" text="default text" /></em></span></a></li>
                                            <li><input type="button" onclick="updateCatalogCustomFieldList(); return false;" value="Save" /></li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_APPROVER)%>">
                        <div class="toggle-block" id="approvers-section">
                        <div class="title">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.catalog_detail.APPROVERS" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <div class="content editable-widget" id="ApproversContentDiv">
                                <div class="btn-holder">
                                    <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                                        <a href="#add-approver" class="btn-add-approver open-popup"><span><em class="checkboxReset" ><spring:message code="com.adminui.catalog_detail.ADDApprover" text="default text" /></em></span></a>
                                        <c:if test="<%=aclManager.allow(request, Permission.CREATE_APPROVER)%>">
                                            <a href="#create-approver" class="btn-create-approver  open-popup" onclick="initCreateApproversPopup();"><span><em><spring:message code="com.adminui.catalog_detail.CreateApprover" text="default text" /></em></span></a>
                                        </c:if>
                                    </c:if>
                                </div>
                                <form id='assignApproversForm' onsubmit='return false;' method='post' class="checkboxResetForm">
                                    <jsp:include page="details_approvers_fragment.jsp" />
                                </form>
                            </div>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_VIEW)%>">
                        <div class="toggle-block" id="profiles-section">
                        <div class="title">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.catalog_detail.CONTENTVIEWS" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <div class="content editable-widget" id="ProfilesContentDiv">
                                <div class="btn-holder">
                                    <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                                        <!--a href="#create-profile" class="btn-create-profile open-lightbox"><span><em>Create PROFILE</em></span></a-->
                                        <a href="#add-profile" class="btn-add-profile open-popup"><span><em class="checkboxReset"><spring:message code="com.adminui.catalog_detail.ADDCONTENTVIEW" text="default text" /></em></span></a>
                                        <c:if test="<%=aclManager.allow(request, Permission.CREATE_CONTENT_VIEW)%>">
                                            <a href="#create-profile" class="btn-create-profile open-popup"><span><em><spring:message code="com.adminui.catalog_detail.CreateContentView" text="default text" /></em></span></a>
                                        </c:if>
                                    </c:if>
                                </div>
                                <div id="profile_page_container_div">
                                    <jsp:include page="profile_table_fragment.jsp"/>
                                </div>
                                <div style="margin: 0;" class="function">
                                    <ul>
                                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                                            <li><a class="ico-remove" onclick="deleteCatalogProfilesCheck('<c:out value="${catalogSummary.catalogId}"/>')"><span><em><spring:message code="com.adminui.catalog_detail.REMOVE" text="default text" /></em></span></a></li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:if>
                </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href="/adminui/catalog" class="ico-back"><span><spring:message code="com.adminui.catalog_detail.BACK" text="default text" /></span></a></li>
                <c:if test="<%=aclManager.allow(request, Permission.EDIT_CATALOG)%>">
                    <c:if test="${catalogSummary.quoteId eq null || catalogSummary.quoteId eq ''}">
                    <li><a class="ico-edit alt-opener" onclick='editCatalog("<c:out value='${catalogSummary.catalogId}'/>", true);'><span><spring:message code="com.adminui.catalog_detail.EditCatalog" text="default text" /></span></a></li>
                    </c:if>
                    <c:if test="${catalogSummary.quoteId ne null && catalogSummary.quoteId ne ''}">
                        <li><a class="ico-edit alt-opener" onclick='editQuote("<c:out value='${catalogSummary.catalogId}'/>");'><span><spring:message code="com.adminui.catalog_detail.EditQuote" text="default text" /></span></a></li>
                    </c:if>
                </c:if>
                <!-- 
                <c:if test="<%=aclManager.allow(request, Permission.CREATE_ITEM)%>">
                    <li><a href="#create-item-section" class="ico-create alt-opener"><span>Create item</span></a></li>
                </c:if>
				 -->
				<c:if test="${catalogSummary.catalogTypeId==1 && (catalogExternalFields != null && catalogExternalFields.size() > 0)}">
                    <li><a href="#catalog-prop" id="catalogPropLink" class="ico-create alt-opener"><span><spring:message code="com.adminui.catalog_detail.CatalogProperties" text="default text" /></span></a></li>
                </c:if>

                <c:if test="<%=aclManager.allow(request, Permission.CREATE_CUSTOM_FIELDS)%>">
                    <li><a href="#create-custom-field" onclick="resetOverlay();" id="createCustField" class="ico-create-custom-field open-popup"><span><spring:message code="com.adminui.catalog_detail.CreateCustomField" text="default text" /></span></a></li>
                </c:if>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_CUSTOM_FIELDS)%>">
                    <li><a href="#create-field-section" class="ico-custom-field alt-opener"><span><spring:message code="com.adminui.catalog_detail.CustomFields" text="default text" /></span></a></li>
                </c:if>

                <c:if test="<%=aclManager.allow(request, Permission.VIEW_APPROVER)%>">
                    <li><a href="#approvers-section" class="ico-approvers alt-opener"><span><spring:message code="com.adminui.catalog_detail.APPROVERS" text="default text" /></span></a></li>
                </c:if>
                <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_VIEW)%>">
                    <li><a href="#profiles-section" class="ico-assign-profiles alt-opener"><span><spring:message code="com.adminui.catalog_detail.ASSIGNCONTENTVIEW" text="default text" /></span></a></li>
                </c:if>
                <!--li><a href="#" class="ico-recycle-bin"><span>RECYCLE BIN</span></a></li-->
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
	// $(document).ready(function(){
	//	var checkoutExtCatalog = ${(param['checkoutExtCatalog']==null)?false:param['checkoutExtCatalog']};
	//	if(checkoutExtCatalog && checkoutExtCatalog == true) {
	//		alert("You need to log in as Shopper in order to check out item back to smartOCI");	
	//		window.location.search = window.location.search.replace(/(\?|&)checkoutExtCatalog=true$/,'');
	//	}
	//});
    function trunOffMessage(){
        $('#updateCatalogProfilesFeedback').html('');
        $('span#message-board').html('');
    }

    function checkAllCatalogItems(check){
        try{
            if(check){
                $("#cat-items-form :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#cat-items-form :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
    function checkAllApproversItems(check){
        try{
            if(check){
                $("#ApproversContentDiv :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                });
            }else{
                $("#ApproversContentDiv :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
    function checkAllCustomFields(check){
        try{
            if(check){
                $("#customFieldContentDiv .target-chbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                });
            }else{
                $("#customFieldContentDiv .target-chbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                });
            }
        }catch(exp){
            alert(exp);
        }
    }

    function checkAllPropertiesFields(check){
        try{
            if(check){
                $("#propertiesContentDiv .target-chbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                });
            }else{
                $("#propertiesContentDiv .target-chbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
    
    function deleteCatalogProfilesCheck(catalogId) {
    	if(isAnyItemChecked('profiles-form')) {
    		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', 
    				function() { deleteCatalogProfiles(catalogId);	});
    	} else {
    		alert('Please select an item');
    	}
    	return false;
    };

</script>

<script type="text/template" id="catalog-details-item">
    <tr>
        <td class="td-select">
            <!--input type="checkbox" class="target-chbox ui-helper-hidden-accessible" name="itemIds" id="{{catalogItemId}}" value="{{catalogItemId}}"/-->
            <input  class="target-chbox" type="checkbox" name="itemIds" id="{{catalogItemId}}" value="{{catalogItemId}}"/>
            <label for="{{catalogItemId}}"></label>
            <!--label for="{{catalogItemId}}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label-->
            <input type="hidden" name="itemStatus" id="status-{{catalogItemId}}" value="{{ociItemStatusId}}"/>
            <input type="hidden" name="itemIdStatusId" id="status-item-{{catalogItemId}}" value="{{catalogItemId}}~~{{ociItemStatusId}}"/>
        </td>
        <td class="a-left td-description">
            <div title="{{newItemDescription}}" class="field">
                <a href="itemdetail?catalogId={{catalogId}}&catalogItemId={{catalogItemId}}"><span>{{newItemDescription}}</span></a>
                <input type="text" value="{{newItemDescription}}" />
            </div>
        </td>
        <td class="longfield">
            <div title="{{newItemVendormat}}" class="field">
                <span>{{newItemVendormat}}</span>
                <input type="text" value="{{newItemVendormat}}" />
            </div>
        </td>
        <td>
            <div title="{{price}}" class="field">
                <span>{{price}}</span>
                <input type="text" value="{{price}}" />
            </div>
        </td>
        <td class="td-matg longfield">
            <div title="{{newItemManufactmat}}" class="field">
                <span>{{newItemManufactmat}}</span>
                <input type="text" value="{{newItemManufactmat}}" />
            </div>
        </td>
        <td>
            <div title="{{approvedStatus}}">
                <dd id="{{catalogItemId}}approvedStatus">{{approvedStatus}}</dd>
            </div>
        </td>
    </tr>
</script>


