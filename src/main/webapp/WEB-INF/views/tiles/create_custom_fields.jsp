<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.catalog.model.CatalogCustomField"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="res/js/customFields.js"></script>
<script type="text/javascript" src="res/js/adminui_catalog_detail.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	$("form").bind("keypress", function (e) {
	    if (e.keyCode == 13) {
	    	getFilteredCatalogs();
	        return false;
	    }
	});
		
});

    var addCatalogsServiceUrl = '<c:url value="/addCatalogs" />';
    var addCatalogsFieldServiceUrl = '<c:url value="/addCatalogsToCustomField" />';
    var populateDropDown = '<c:url value="/populateDropDown" />';
    var removeCats = '<c:url value="/removeCatalogs" />';
    var navigateCatalogPageURL = '<c:url value="/navigateCatalogPage" />';
    var navigateCustomFieldCatalogPageURL = '<c:url value="/navigateCustFieldCatalogPage" />';
    var filterCatalogsURL = '<c:url value="/filterCatalogs" />';
    var fieldExistsURL = '<c:url value="/customFieldExists" />';
    var addCustomFieldURL = '<c:url value="/addCustomField" />';
</script>
<div id="wrapper">
<div id="main">
<div class="section">
    <ul class="breadcrumbs">
        <li><a href="vroozi"><spring:message code="com.adminui.create_custom_fields.Vroozi" text="default text" /></a></li>
        <li><a href="<c:out value="catalog"/>"><spring:message code="com.adminui.create_custom_fields.ContentManager" text="default text" /></a></li>
        <li><a href="<c:out value="customFields"/>"><spring:message code="com.adminui.create_custom_fields.CustomFieldsManagement" text="default text" /></a></li>
        <li><spring:message code="com.adminui.create_custom_fields.CreateCustomField" text="default text" /></li>
    </ul>
    <div class="video-btn-holder">
        <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
        <a href="#" class="btn"><span><em>Help video</em></span></a-->
    </div>
</div>
<div class="main-holder">
<div id="content">
<form class="settings-form" style="padding: 0;" enctype="multipart/form-data"  action="addCustomField" method="post" id="theCustomFormId">
<input type="hidden" id="defaultValue" name="defaultValue" value="${customField.defaultValue}">
<input type="hidden" id="mappingId" name="mappingId" value="${customField.mappingId}">
<input type="hidden" id="searchBoost" name="searchBoost" value="${customField.searchBoost}">
<input type="hidden" id="dropDownValues" name="dropDownValues" value="${customField.dropDownValues}">
<h1><spring:message code="com.adminui.create_custom_fields.CreateCustomField" text="default text" /></h1>
<div class="content-block toggle-block active" id="create-field">
    <div class="headline">
        <h2><a href="#" class="open-close"><spring:message code="com.adminui.create_custom_fields.Customfieldssettings" text="default text" /></a></h2>
    </div>
    <div class="block">
        <div class="content">

                <div class="text-fields">

                    <div class="row" id="customFieldNameId">
                        <label for="field-name"><span style="color: red;">*</span><spring:message code="com.adminui.create_custom_fields.FieldName" text="default text" /></label>
                        <input type="text" value="" id="field-name" class="input2" name="fieldName"/>
                        <label id="field_name_error" class="error-msg" style="color: red; width: 446px; float: left;"><spring:message code="com.adminui.create_custom_fields.customFieldWithName" text="default text" /></label>
                        <label id="field_name_exceeded" class="error-msg" style="color: red; width: 391px; float: left;"><spring:message code="com.adminui.create_custom_fields.postFilter" text="default text" /></label>
                    </div>
                    <div class="row" id="customFieldDisplayNameId">
                        <label for="display-name"><span style="color: red;">*</span><spring:message code="com.adminui.create_custom_fields.DisplayName" text="default text" /></label>
                        <input type="text" value="" id="display-name" class="input2" name="displayName"/>
                    </div>
                    <div class="row">
                        <label for="description"><spring:message code="com.adminui.create_custom_fields.Description" text="default text" /></label>
                        <textarea id="description" cols="30" rows="10" class="textarea2" name="fieldDesc"></textarea>
                    </div>
                    <div class="row">
                        <label><spring:message code="com.adminui.create_custom_fields.Type" text="default text" /></label>
                        <select class="alt-select choose-tab-select" onchange="resetDefaultValue();" name="fieldType">
                            <option value="list" title="#area-1"><spring:message code="com.adminui.create_custom_fields.DropDownList" text="default text" /></option>
                            <option value="fixed" title="#area-3"><spring:message code="com.adminui.create_custom_fields.FixedValue" text="default text" /></option>
                            <option value="flag" title="#area-4"><spring:message code="com.adminui.create_custom_fields.Flag" text="default text" /></option>
                            <option value="text" title="#area-2"><spring:message code="com.adminui.create_custom_fields.InputFieldSmall" text="default text" /></option>
                            <option value="mediumtext" title="#area-2"><spring:message code="com.adminui.create_custom_fields.InputFieldMedium" text="default text" /></option>
                            <option value="largetext" title="#area-2"><spring:message code="com.adminui.create_custom_fields.InputFieldLarge" text="default text" /></option>
                        </select>
                    </div>


                </div>
                <h3><spring:message code="com.adminui.create_custom_fields.CustomFieldDefaults" text="default text" /></h3>
                <div class="alt-area">
                    <div class="area-holder" id="area-1">
                        <div class="row">
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.Values" text="default text" /></label>
                            <select class="alt-select" id="defDropDown">
                                <option value="0">&nbsp;</option>
                            </select>
                            <ul class="add-btns">
                                <li><a href="javascript:openDropDownOverlay();" class="ico-add"></a></li>
                                <li><a onclick="deleteDropDownValues();" class="ico-del"></a></li>
                                <li><a onclick="editDropDownValues();" class="ico-edit"></a></li>
                            </ul>
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.FieldMapping" text="default text" /></label>
                            <select class="alt-select" name="mapDropDown" onchange="setMappingId(this);" id="map1">
                                <option selected="selected" value="0"></option>
                                <option value="1">NEW_ITEM-LONGTEXT</option>
                                <option value="2">NEW_ITEM-CUST_FIELD1</option>
                                <option value="3">NEW_ITEM-CUST_FIELD2</option>
                                <option value="4">NEW_ITEM-CUST_FIELD3</option>
                                <option value="5">NEW_ITEM-CUST_FIELD4</option>
                                <option value="6">NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <label for="parameter1"><spring:message code="com.adminui.create_custom_fields.Required" text="default text" /></label>
                            <input type="checkbox" name="required" id="parameter1"/>
                        </div>
                        <div class="row">
                            <label for="parameter2"><spring:message code="com.adminui.create_custom_fields.PostFilterAttribute" text="default text" /></label>
                            <input type="checkbox" name="postFilterable" id="parameter2"/>
                        </div>
                        <div class="row">
                            <label for="parameter3"><spring:message code="com.adminui.create_custom_fields.Searchable" text="default text" /></label>
                            <input type="checkbox" name="searchable" id="parameter3"/>
                        </div>
                    </div>
                    <div class="area-holder" id="area-2">
                        <div class="row">
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.FieldMapping" text="default text" /></label>
                            <select class="alt-select" name="mapDropDown" onchange="setMappingId(this);" id="map2">
                                <option selected="selected" value="0"></option>
                                <option value="1">NEW_ITEM-LONGTEXT</option>
                                <option value="2">NEW_ITEM-CUST_FIELD1</option>
                                <option value="3">NEW_ITEM-CUST_FIELD2</option>
                                <option value="4">NEW_ITEM-CUST_FIELD3</option>
                                <option value="5">NEW_ITEM-CUST_FIELD4</option>
                                <option value="6">NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <label for="parameter2-1"><spring:message code="com.adminui.create_custom_fields.Required" text="default text" /></label>
                            <input type="checkbox" name="required" id="parameter2-1"/>
                        </div>
                    </div>
                    <div class="area-holder" id="area-3">
                        <div class="row">
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.DefaultValue" text="default text" /></label>
                            <textarea cols="30" rows="10" class="textarea3" onkeyup="setDefaultValue(this);" id="defArea3"></textarea>
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.FieldMapping" text="default text" /></label>
                            <select class="alt-select" name="mapDropDown" onchange="setMappingId(this);" id="map3">
                                <option selected="selected" value="0"></option>
                                <option value="1">NEW_ITEM-LONGTEXT</option>
                                <option value="2">NEW_ITEM-CUST_FIELD1</option>
                                <option value="3">NEW_ITEM-CUST_FIELD2</option>
                                <option value="4">NEW_ITEM-CUST_FIELD3</option>
                                <option value="5">NEW_ITEM-CUST_FIELD4</option>
                                <option value="6">NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <div class="col">
                                <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.ApplySearchResultsBoost" text="default text" /></label>
                                <select class="alt-select" onchange="setSearchBoost(this);" name="searchBoostDrop">
                                    <option value="10">10</option>
                                    <option value="9">9</option>
                                    <option value="8">8</option>
                                    <option value="7">7</option>
                                    <option value="6">6</option>
                                    <option value="5">5</option>
                                    <option value="4">4</option>
                                    <option value="3">3</option>
                                    <option value="2">2</option>
                                    <option value="1">1</option>
                                    <option selected="selected" value="0">0</option>
                                    <option value="-1">-1</option>
                                    <option value="-2">-2</option>
                                    <option value="-3">-3</option>
                                    <option value="-4">-4</option>
                                    <option value="-5">-5</option>
                                    <option value="-6">-6</option>
                                    <option value="-7">-7</option>
                                    <option value="-8">-8</option>
                                    <option value="-9">-9</option>
                                    <option value="-10">-10</option>
                                </select>
                            </div>
                            <label for="parameter3-1"><spring:message code="com.adminui.create_custom_fields.PostFilterAttribute" text="default text" /></label>
                            <input type="checkbox" name="postFilterable" id="parameter3-1"/>
                        </div>
                        <div class="row">
                            <label for="parameter3-2"><spring:message code="com.adminui.create_custom_fields.Searchable" text="default text" /></label>
                            <input type="checkbox" name="searchable" id="parameter3-2"/>
                        </div>
                    </div>
                    <div class="area-holder" id="area-4">
                        <div class="row">
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.DefaultValue" text="default text" /></label>
                            <textarea cols="30" rows="10" class="textarea3" onkeyup="setDefaultValue(this);" id="defArea4"></textarea>
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.FieldMapping" text="default text" /></label>
                            <select class="alt-select" name="mapDropDown" onchange="setMappingId(this);" id="map4">
                                <option selected="selected" value="0"></option>
                                <option value="1">NEW_ITEM-LONGTEXT</option>
                                <option value="2">NEW_ITEM-CUST_FIELD1</option>
                                <option value="3">NEW_ITEM-CUST_FIELD2</option>
                                <option value="4">NEW_ITEM-CUST_FIELD3</option>
                                <option value="5">NEW_ITEM-CUST_FIELD4</option>
                                <option value="6">NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <div class="col">
                                <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.ApplySearchResultsBoost" text="default text" /></label>
                                <select class="alt-select" onchange="setSearchBoost(this);" name="searchBoostDrop">
                                    <option value="10">10</option>
                                    <option value="9">9</option>
                                    <option value="8">8</option>
                                    <option value="7">7</option>
                                    <option value="6">6</option>
                                    <option value="5">5</option>
                                    <option value="4">4</option>
                                    <option value="3">3</option>
                                    <option value="2">2</option>
                                    <option value="1">1</option>
                                    <option selected="selected" value="0">0</option>
                                    <option value="-1">-1</option>
                                    <option value="-2">-2</option>
                                    <option value="-3">-3</option>
                                    <option value="-4">-4</option>
                                    <option value="-5">-5</option>
                                    <option value="-6">-6</option>
                                    <option value="-7">-7</option>
                                    <option value="-8">-8</option>
                                    <option value="-9">-9</option>
                                    <option value="-10">-10</option>
                                </select>
                            </div>
                            <label for="parameter4-1"><spring:message code="com.adminui.create_custom_fields.SearchResultsFilter" text="default text" /></label>
                            <input type="checkbox" name="postFilterable" id="parameter4-1"/>
                        </div>
                        <div class="row">
                            <label for="parameter4-2"><spring:message code="com.adminui.create_custom_fields.DefaultSearchFilterOn" text="default text" /></label>
                            <input type="checkbox" name="defaultPostFilter" id="parameter4-2"/>
                        </div>
                        <div class="alt-row">
                            <label class="alt-label"><spring:message code="com.adminui.create_custom_fields.FlagIcon" text="default text" /></label>
                            <div class="popup-holder">
                                <a href="#" class="btn-add-open open"><spring:message code="com.adminui.create_custom_fields.add" text="default text" /></a>
                                <div class="popup">
                                    <div class="popup-frame">
                                        <div class="popup-inner">
                                            <div class="top">
                                                <a href="#" id="iconFlagFileClose" class="alt-close"><spring:message code="com.adminui.create_custom_fields.Close" text="default text" /></a>
                                                <h3><spring:message code="com.adminui.create_custom_fields.UploadFile" text="default text" /></h3>
                                            </div>
                                            <div class="row">
                                                <input type="file" id="iconFlagFile" name="iconFile" onchange="setFlagIconFile()" value="${customFieldFileUtility.iconFile}" />
                                            </div>
                                            <div class="row" style="display:none;">
                                                <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                <a href="#" class="btn-upload"><spring:message code="com.adminui.create_custom_fields.UPload" text="default text" /></a>
                                                <div class="txt">
                                                    <span class="size"> 0.59 KB</span>
                                                    <p>icon_photo_upload_16px.gif</p>
                                                </div>
                                            </div>
                                            <div class="row" style="display:none;">
                                                <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                <div class="txt">
                                                    <div class="line-box"><span class="line" style="width:70%"></span></div>
                                                    <a href="#" class="btn-cancel"><spring:message code="com.adminui.create_custom_fields.Cancel" text="default text" /></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <em id="iconFlagFileEM" class="text"><spring:message code="com.adminui.create_custom_fields.noFile" text="default text" /></em>
                            </div>
                            <div class="upload-file" style="display:none;">
                                <div class="upload-file-holder">
                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                    <p>icon_photo_upload_16px.gif</p>
                                </div>
                                <a href="#" class="btn-del"></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btns-holder">
                    <a class="btn-cancel" href="<c:out value="customFields"/>"><spring:message code="com.adminui.create_custom_fields.Cancel" text="default text" /></a>
                    <input type="submit" onclick="setDefaultValueDropDown();" value="Save"/>
                    <p><span class="required">* </span><spring:message code="com.adminui.create_custom_fields.RequiredField" text="default text" /></p>
                </div>

        </div>
    </div>
</div>
<div class="add-slide-blocks">

    <div class="toggle-block active" id="associate-catalogs">
        <div class="title">
            <h2><a href="#" class="open-close"><spring:message code="com.adminui.create_custom_fields.CATALOGS" text="default text" /></a></h2>
        </div>
        <div class="block">
            <div class="content editable-widget">
                <div class="catalog-box">
                    <div class="btn-holder">
                        <a href="#add-catalogs" class="btn-add-catalogs checkboxReset" onclick="showAddCatalogs(); return false;"><span><em class="checkboxReset"><spring:message code="com.adminui.create_custom_fields.ADDCATALOGS" text="default text" /></em></span></a>
                    </div>
                    <div id="associatedCatalogs">
                    <table  class="table-data">
                        <thead>
                        <tr>
                            <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all4" id="check4-1" /><label for="check4-1"></label></th>
                            <th class="a-left"><a class="sorting" href="#"><spring:message code="com.adminui.create_custom_fields.CatalogName" text="default text" /></a></th>
                            <th><spring:message code="com.adminui.create_custom_fields.CatalogType" text="default text" /></th>
                            <th class="td-empty"></th>
                        </tr>
                        </thead>
                    </table>
                    </div>
                    <%--<div class="bottom-data">--%>
                        <%--<div class="pager">--%>
                            <%--<span>Page 1 of 87 </span>--%>
                            <%--<ul>--%>
                                <%--<li><a class="btn-prev" href="#"></a></li>--%>
                                <%--<li><a class="btn-next" href="#"></a></li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                        <%--<strong class="pages">Total Records: 1-10 of 87</strong>--%>
                    <%--</div>--%>
                    <div class="function">
                        <ul>
                            <li><a class="ico-remove" onclick="removeAttachedCatsConfirm();"><span><em><spring:message code="com.adminui.create_custom_fields.REMOVE" text="default text" /></em></span></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</form>
</div>


<div id="sidebar">
    <ul class="sub-nav">
        <li><a href="<c:out value="customFields"/>" class="ico-back"><span><spring:message code="com.adminui.create_custom_fields.BACK" text="default text" /></span></a></li>
        <li><a href="#create-field" class="ico-create-field alt-opener"><span><spring:message code="com.adminui.create_custom_fields.CREATECUSTOMFIELD" text="default text" /></span></a></li>
        <li><a href="#associate-catalogs" class="ico-catalogs alt-opener"><span><spring:message code="com.adminui.create_custom_fields.ASSOCIATECATALOGS" text="default text" /></span></a></li>
    </ul>
</div>
</div>
</div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="delete">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_custom_fields.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_custom_fields.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.create_custom_fields.confirmDel" text="default text" /></p>
                    <p><strong><spring:message code="com.adminui.create_custom_fields.lorem" text="default text" /></strong></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" class="btn btn-red"><span><spring:message code="com.adminui.create_custom_fields.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="add-catalogs">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_custom_fields.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_custom_fields.AddCatalogs" text="default text" /></h2>
                </div>
                <div class="search-box">
                <form action="return false;" id="searchSortForm">
                    <div class="add-search-form" style="width: 415px;">
                        <fieldset>
                            <input type="text" id="searchBox" name="search" value="Search Catalogs" />
                            <input type="button" id="searchBox1" onclick="getFilteredCatalogs();" value="Submit" />
                            <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchBox').val('Search Catalogs'); $('#searchBox1').click();" />
                        </fieldset>
                    </div>
                   	<input type="hidden" id="sortFieldId" name="orderBy" />
                    
                    </form>
                </div>
                <form id="associateCatalogsForm" action="#" class="checkboxResetForm">
                    <fieldset>
                        <div class="lightbox-content editable-widget">
	                        <div id="associateCatalogs">
							</div>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.create_custom_fields.Cancel" text="default text" /></a>
                            <input type="submit" onclick="addCatalogsToCustomField(); return false;" value="Save" />
                         </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
<div class="lightbox" id="drop_down_overlay">
<div class="holder">
<div class="frame">
<div class="title">
    <a href="#" onclick="$('span#errorReplyDivCF').html('');$('input#value-field').val('');$('input#price').val('');$('input#partNumber').val('');" class="close">Close</a>
    <h2><spring:message code="com.adminui.create_custom_fields.DropDownValues" text="default text" /></h2>
</div>
<form class="settings-form" method="post" id="dropDownOverlay">
<fieldset>
<div class="area">
    <div class="row" id="valueDiv">
        <label for="value-field"><span>*</span><spring:message code="com.adminui.create_custom_fields.Value" text="default text" /></label>
       	<input type="text" value="" id="value-field" class="input2" name="value"/>
 		
    </div>
    <div class="row" id="priceModifier">
        <label for="price"><span>*</span><spring:message code="com.adminui.create_custom_fields.PriceModifier" text="default text" /></label>
        <input type="text" value="" id="price" class="input2" name="price"/>
    </div>
    <div class="row">
        <label for="partNumber"><spring:message code="com.adminui.create_custom_fields.DynamicPartNumber" text="default text" /></label>
        <input type="text" value="" id="partNumber" class="input2" name="partNumber"/>
    </div>
</div>
	<div class="btns-holder">
    <a href="#" class="btn-cancel" onclick="$('span#errorReplyDivCF').html('');$('input#value-field').val('');$('input#price').val('');$('input#partNumber').val('');"><spring:message code="com.adminui.create_custom_fields.Cancel" text="default text" /></a>
    <input type="submit" value="Save" onclick="populateDropDownValues(); return false;"/>
    <p>
    <span class="required">* </span><spring:message code="com.adminui.create_custom_fields.RequiredField" text="default text" />
    </p>
	<p><span id="errorReplyDivCF" style="font-style: italic; color: #ED1C24; height:10px"></span></p>

    </div>
</fieldset>
</form>
</div>
</div>
</div>
</div>