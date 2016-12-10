<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<%--<div class="lightbox-section">--%>
    <%--<div class="lightbox" id="lightbox-inline-edit-catalog">--%>
        <%--<div class="holder">--%>
            <%--<div class="frame">--%>
                <%--<div class="title">--%>
                    <%--<a href="#" class="close">Close</a>--%>
                    <%--<h2>Edit Catalog</h2>--%>
                <%--</div>--%>
                <%--<form action="editCatalog" class="form-create" id="edit-catalog-form" method="post" enctype="multipart/form-data">--%>
                    <%--<fieldset>--%>
                        <%--<div class="area">--%>
                            <%--<div class="row" id="theCatalogNameId">--%>
                                <%--<label for="catalog-name"><span>*</span>Catalog Name:</label>--%>
                                <%--<input type="text" name="catalogName" id="catalog-name" maxlength="64" class="required" value="">--%>
                            <%--</div>--%>
                            <%--<div class="row" id="theCatalogFileId">--%>
                                <%--<label for="catalog-file">Catalog File:</label>--%>
                                <%--<input type="file" name="catalogFile" id="catalog-file"/>--%>
                            <%--</div>--%>
                            <%--<div class="row">--%>
                                <%--<label>Image File (.zip):</label>--%>
                                <%--<input type="file" value="" name="imageFile" />--%>
                            <%--</div>--%>

                            <%--<div class="row" id="SupplierCompany">--%>
                                <%--<label>Supplier Company:</label>--%>
                                <%--<select name="supplierName" id="supplierName">--%>
                                    <%--<option value="0"></option>--%>
                                    <%--<c:forEach var="supplierCompany" items="${supplierCompanyList}">--%>
                                        <%--<option id="${supplierCompany.companyId}" value="${supplierCompany.companyId}">${supplierCompany.companyName}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="add-slide-blocks">--%>
                            <%--<div class="toggle-block">--%>
                                <%--<div class="title">--%>
                                    <%--<h2><a class="open-close" href="#">Additional Fields</a></h2>--%>
                                <%--</div>--%>
                                <%--<div class="block">--%>
                                    <%--<div class="content">--%>
                                        <%--<div class="area">--%>
                                            <%--<div class="row">--%>
                                                <%--<label for="catalog-id">Catalog ID:</label>--%>
                                                <%--<input type="text" id="catalog-id" name="clientCatalogId"  value=""/>--%>
                                            <%--</div>--%>

                                            <%--<div class="row">--%>
                                                <%--<label for="catalog-num">Contract Number:</label>--%>
                                                <%--<input type="text" id="catalog-num" />--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<label for="contract-line">Contract Line Item:</label>--%>
                                                <%--<input type="text" id="contract-line" />--%>
                                            <%--</div>--%>


                                            <%--<div class="row">--%>
                                                <%--<label for="start-date">Start Date:</label>--%>
                                                <%--<input type="text" class="timepicker-input" id="start-date" name="startDate"  value=""  readonly="readonly"/>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<label for="end-date">End Date:</label>--%>
                                                <%--<input type="text" class="timepicker-input" id="end-date" name="endDate"  value=""  readonly="readonly"/>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="toggle-block">--%>
                                <%--<div class="title">--%>
                                    <%--<h2><a class="open-close" href="#">EXTERNAL CATALOG DEFINITION</a></h2>--%>
                                <%--</div>--%>
                                <%--<div class="block">--%>
                                    <%--<br>--%>
                                    <%--<div class="row" id="extMethod">--%>
                                        <%--<label>Method:</label>--%>
                                        <%--<select name="extCatalogMethod" id="extCatalogMethod">--%>
                                            <%--<option value=""></option>--%>
                                            <%--<option value="POST">POST</option>--%>
                                            <%--<option value="GET">GET</option>--%>
                                        <%--</select>--%>
                                    <%--</div>--%>
                                    <%--<div class="content reorder-widget editable-widget">--%>
                                        <%--<div class="btn-holder">--%>
                                            <%--<a class="btn-add-external-catalog" id="btn-add-external-catalog-id"><span><em>ADD</em></span></a>--%>
                                        <%--</div>--%>
                                        <%--<table class="table-data external-catalog" id="table-external-catalog-id">--%>
                                            <%--<thead>--%>
                                            <%--<tr>--%>
                                                <%--<!--th class="td-select"><input type="checkbox" class="check-allbox" name="check-all10" id="check10-1" onclick="toggleAllCheckboxItems(this.checked, 'theCatalogId');" /><label for="check10-1"></label></th-->--%>
                                                <%--<th class="td-select"><input type="checkbox" class="check-allbox" name="check-all10" id="check10-1" onclick="toggleAllCheckboxItems(this.checked, 'theCatalogId');" /><label for="check10-1"></label></th>--%>
                                                <%--<th class="a-left th-name">NAME</th>--%>
                                                <%--<th colspan="2">Value</th>--%>
                                            <%--</tr>--%>
                                            <%--</thead>--%>
                                            <%--<tbody>--%>

                                            <%--<!--tr class="added">--%>
                                                <%--<td>--%>
                                                    <%--<a href="#" class="btn-up-down"><span class="up-arrow">up</span> <span class="down-arrow">down</span></a>--%>
                                                    <%--<input type="checkbox" name="check10" class="target-chbox" id="check10-2" /><label for="check10-2"></label>--%>
                                                <%--</td>--%>
                                                <%--<td class="a-left td-name-catalog">--%>
                                                    <%--<div>--%>
                                                        <%--<input type="text" value="Enter Name" name="fieldName" class="field-name" />--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                                <%--<td>--%>
                                                    <%--<div>--%>
                                                        <%--<input type="text" value="Enter Value" name="fieldValue" class="field-value" />--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                                <%--<td>--%>
                                                    <%--<div>--%>
                                                        <%--<select id="s-val" name ="dynamicValue">--%>
                                                            <%--<option value="">Or Select Dynamic Value</option>--%>
                                                            <%--<option>Option 2</option>--%>
                                                        <%--</select>--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                            <%--</tr-->--%>
                                            <%--</tbody>--%>
                                        <%--</table>--%>
                                        <%--<div class="function">--%>
                                            <%--<ul>--%>
                                                <%--<!--li><a href="#" class="ico-edit"><span><em>EDIT</em></span></a></li-->--%>
                                                <%--<!--li><a href="#" class="ico-reorder"><span><em>REORDER</em></span></a></li-->--%>
                                                <%--<li><a class="ico-remove" id="external-catalog-remove"><span><em>REMOVE</em></span></a></li>--%>
                                                <%--<!--li><input type="button" value="Save" /></li-->--%>
                                            <%--</ul>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="btns-holder">--%>
                                <%--<a href="#" class="btn-cancel">Cancel</a>--%>
                                <%--<a href="#" class="btn-reset" id="create-catalog-btn-reset"><span>Reset</span></a>--%>
                                <%--<!--a href="#" class="btn-create"><span>Create</span></a-->--%>
                                <%--<input type="submit" name="submitButton" value="Create" id="createCatalogSubmitButton"/>--%>

                                <%--<p><span class="required">* </span>Required Field</p>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<input type="hidden" name="catalogId" id="catalogId" value="" maxlength="10"/>--%>
                    <%--</fieldset>--%>
                <%--</form>--%>
                <%--<div class="contain-progress-bar" style="display: none;">--%>
                    <%--<div class="progress" style="width:840px;">--%>
                        <%--<div class="bar"></div>--%>
                        <%--<div class="percent">0%</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<%--<div class="lightbox-section">--%>
    <%--<div class="lightbox" id="lightbox-inline-edit-catalog">--%>
        <%--<div class="holder">--%>
            <%--<div class="frame">--%>
                <%--<div class="title">--%>
                    <%--<a href="#" class="close">Close</a>--%>
                    <%--<h2 id="catalogPopupTitle2">Edit Catalog</h2>--%>
                <%--</div>--%>
                <%--<form action="editCatalog" class="form-create" id="edit-catalog-form" method="post" enctype="multipart/form-data">--%>
                    <%--<fieldset>--%>
                        <%--<div class="area">--%>
                            <%--<div class="row" id="theEditCatalogNameId">--%>
                                <%--<div class="area-col" id="edit-catalog-name-div">--%>
                                    <%--<label for="edit-catalog-name"><span>*</span>Catalog Name:</label>--%>
                                    <%--<input type="text" name="catalogName" id="edit-catalog-name" maxlength="64" class="required" value="">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="row">--%>
                                <%--<label for="edit-catalog-file"><span id="edit-catalog-file-span"></span>Catalog File:</label>--%>
                                <%--<input type="file" name="catalogFile" id="edit-catalog-file" onchange="setDefaultUpdateMethod();" />--%>
                                <%--<label  id="label-edit-catalog-file" class="error"></label>--%>
                            <%--</div>--%>
                            <%--<div class="row">--%>
                                <%--<label>Image File (.zip):</label>--%>
                                <%--<input type="file" name="imageFile" />--%>
                            <%--</div>--%>
                            <%--<div class="row" id="edit-catalog-supplier-names">--%>
                                <%--<label>Supplier Company:</label>--%>
                                <%--<select name="supplierName" id="edit-catalog-supplier-name">--%>
                                    <%--<option value="0">&nbsp;</option>--%>
                                    <%--<c:forEach var="supplierCompany" items="${supplierCompanyList}">--%>
                                        <%--<option id="${supplierCompany.companyId}" value="${supplierCompany.companyId}">${supplierCompany.companyName}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="row" id="edit-catalog-external-catalogs">--%>
                                <%--<label>External Catalogs:</label>--%>
                                <%--<select name="externalCatalogId" id="edit-catalog-external-catalog">--%>
                                    <%--<option value="0">&nbsp;</option>--%>
                                    <%--<c:forEach var="externalCatalog" items="${externalCatalogList}">--%>
                                        <%--<option id="${externalCatalog.catalogId}" value="${externalCatalog.catalogId}">${externalCatalog.name}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <%--<div class="row" id="edit-catalog-edit-options">--%>
                                <%--<label>Edit Options:</label>--%>
                                <%--<select name="catalogEditOption" id="edit-catalog-edit-option">--%>
                                    <%--<option value="1" id="101">Merge & Update</option>--%>
                                    <%--<option value="2" id="102">Overwrite All</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>

                            <%--<div class="row">--%>
                                <%--<label for="edit-catalog-client-catalog-id">Catalog ID:</label>--%>
                                <%--<input type="text" id="edit-catalog-client-catalog-id" name="clientCatalogId"  value=""/>--%>
                            <%--</div>--%>
                            <%--<div class="row">--%>
                                <%--<label for="edit-catalog-start-date">Start Date:</label>--%>
                                <%--<input type="text" class="timepicker-input" id="edit-catalog-start-date" name="startDate" readonly="readonly" value=""/>--%>
                            <%--</div>--%>
                            <%--<div class="row">--%>
                                <%--<label for="edit-catalog-end-date">End Date:</label>--%>
                                <%--<input type="text" class="timepicker-input" id="edit-catalog-end-date" name="endDate" readonly="readonly" value=""/>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<div class="btns-holder">--%>
                            <%--<a href="#" class="btn-cancel">Cancel</a>--%>
                            <%--<a href="#" class="btn-reset" onclick="populateCatalogForm(gCurrentCatalog); return false;"><span>Reset</span></a>--%>
                            <%--<input type="submit" name="submitButton" value="Save" id="editCatalogSubmitButton"/>--%>
                            <%--<p><span style="color: #ED1C24;">* </span>Required Field</p>--%>
                            <%--<!--div id="editCatalogReplyDiv" style="font-style: italic; color: red; height: 10px"></div-->--%>
                            <%--<p><span id="editCatalogReplyDiv" style="font-style: italic; color: red;"></span></p>--%>
                        <%--</div>--%>
                        <%--<input type="hidden" name="catalogId" id="edit-catalog-id" value="" maxlength="10"/>--%>
                        <%--<input type="hidden" name="catalogTypeId" id="edit-catalog-type-id" value=""/>--%>

                    <%--</fieldset>--%>
                <%--</form>--%>
                <%--<br />--%>
                <%--<div class="contain-progress-bar" style="display: none;">--%>
                    <%--<div class="progress" style="width:840px;">--%>
                        <%--<div class="bar"></div>--%>
                        <%--<div class="percent">0%</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>


<div class="lightbox-section">
    <div class="lightbox" id="lightbox-inline-search-loading">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.common_overlay.Close" text="default text" /></a>
                    <h2 id="searchPopupTitle"><spring:message code="com.adminui.common_overlay.Searching" text="default text" /></h2>
                </div>
                <div id="divContent"></div>
                <input type="button" id="executeSearch" style="display: none" onclick="executeSearchFunction(1);" />
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-common">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteCloseBtn" href="#" class="close"><spring:message code="com.adminui.common_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.common_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p class="deletion-message"><spring:message code="com.adminui.common_overlay.DeletionConfirmationMsg" text="default text" /></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.common_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.common_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
