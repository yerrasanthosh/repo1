<%@page import="com.vroozi.customerui.profile.model.Profile"%>
<%@page import="com.vroozi.customerui.profile.model.ProfileProxy"%>
<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="index_overlay.jsp"/>
<script type="text/javascript">
var fieldExistsURL = '<c:url value="/customFieldExists" />';
</script>
<div class="lightbox-section">
    <div class="lightbox" id="create-approver">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.details_overlay.CreateApprover" text="default text" /></h2>
                </div>
                <form action="addApprover" class="form-create" id="createApprover" method="post">
                    <fieldset>
                        <h3><spring:message code="com.adminui.details_overlay.APPROVERSETTINGS" text="default text" /></h3>
                        <div class="area">
                            <div class="row">
                                <label for="first-name"><span>*</span><spring:message code="com.adminui.details_overlay.FirstName" text="default text" /></label>
                                <div id="first-name-div">
                                    <input type="text" name="firstName" id="first-name" onkeyup="if($('#first-name').val() != '')$('#first-name-div span').remove()"/>
                                </div>
                            </div>
                            <div class="row">
                                <label for="last-name"><span>*</span><spring:message code="com.adminui.details_overlay.LastName" text="default text" /></label>
                                <div id="last-name-div">
                                    <input type="text" name="lastName" id="last-name" onkeyup="if($('#last-name').val() != '')$('#last-name-div span').remove()"/>
                                </div>
                            </div>
                            <div class="row">
                                <label for="e-mail"><span>*</span><spring:message code="com.adminui.details_overlay.Email" text="default text" /></label>
                                <div id="e-mail-div">
                                    <input type="text" name="username" id="e-mail" onkeyup="if($('#e-mail').val() != '')$('#e-mail-div span').remove()"/>
                                </div>
                            </div>
                             <input type="hidden" name="catalogId" value="${param['catalogId']}"/>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                            <!-- <input type="submit" value="Save" name="Save">-->
                            <a class="btn-create"  onclick="createApprover();"><span><spring:message code="com.adminui.details_overlay_approvers_fragment.Save" text="default text" /></span></a>
                            <p><span class="required">* </span><spring:message code="com.adminui.details_overlay.RequiredField" text="default text" /></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="create-profile">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close" onclick="clearProfileFormErrors();"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.details_overlay.CreateContentView" text="default text" /></h2>
                </div>
                <form action="addCatalogProfile" class="form-create" id="createProfileId" method="post">
                    <fieldset>
                        <h3><spring:message code="com.adminui.details_overlay.CONTENTVIEWSETTINGS" text="default text" /></h3>
                        <div class="area">
                            <div class="row" id="theProfileNameId">
                                <label for="profile-name"><span>*</span><spring:message code="com.adminui.details_overlay.ContentViewName" text="default text" /></label>
                                <input type="text" name ="profileName" id="profile-name" class="required"/>
                                <input type="hidden" name="isActive" value="true"/> 
                            </div>
                            <div class="row" id="theProfileDescId">
                                <label for="profile-description"><spring:message code="com.adminui.details_overlay.ContentViewDescription" text="default text" /></label>
                                <textarea id="profile-description"  name ="profileDesc" cols="30" rows="10" class="textarea"></textarea>
                            </div>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel" onclick="clearProfileFormErrors();"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                            <a class="btn-create close"  onclick="addCatalogProfile();"><span><spring:message code="com.adminui.details_overlay.Save" text="default text" /></span></a>
                            <!-- input type="submit" value="Save" name="Save" -->
                            <p><span class="required">* </span><spring:message code="com.adminui.details_overlay.RequiredField" text="default text" /></p>
                            <input type="hidden" name="catalogId" value="${param['catalogId']}"/>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-catItems">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteCatItemCloseBtn" href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.details_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.details_overlay.deleteConfirm" text="default text" /></p>
                    <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteCatItemCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteCatItemBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.details_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="edit-custom-field">
        <div class="holder">
<div class="frame">
<div class="title">
    <a href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
    <h2><spring:message code="com.adminui.details_overlay.CustomField" text="default text" /></h2>
</div>
<form action="editCatalogCustomField" class="form-create" method="post" id="editCatalogCustomFormId">
<fieldset>
<input type="hidden" id="edit-catalog-id" name="catalogId"/>
<input type="hidden" id="edit-custom-field-id" name="customFieldId"/>
<input type="hidden" id="edit-id" name="edit-id"/>
<input type="hidden" id="defaultValue" name="defaultValue">
<input type="hidden" id="searchBoost" name="searchBoost" value="${customField.searchBoost}">
<h3><spring:message code="com.adminui.details_overlay.Customfieldsettings" text="default text" /></h3>
<div class="area">
    <div class="row" id="editCustomFieldNameId">
        <label for="field-name"><span></span><spring:message code="com.adminui.details_overlay.FieldName" text="default text" /></label>
        <input disabled="true"  type="text" value="" id="edit-field-name" class="input2" name="fieldName" style="border: none"/>
    </div>
    <div class="row" id="editCustomFieldDisplayNameId">
        <label for="display-name"><span></span><spring:message code="com.adminui.details_overlay.DisplayName" text="default text" /></label>
        <input disabled="true" type="text" value="" id="edit-display-name" class="input2" name="displayName" style="border: none"/>
    </div>
    <div class="row">
        <label for="description"><spring:message code="com.adminui.details_overlay.Description" text="default text" /></label>
        <input disabled="true" type="text" value="" id="edit-description" class="input2" name="fieldDesc" style="border: none"/>
    </div>
    <div class="row">
        <label>Type:</label>
        <input disabled="true" type="text" value="" id="edit-field-type" class="input2" name="fieldType" style="border: none"/>
        <!--select disabled="true"  onchange="resetDefaultValue();" id="edit-field-type" name="fieldType" style="border: none">
            <option value="list" title="#edit-area-1">Drop Down List</option>
            <option value="fixed" title="#edit-area-3">Fixed Value</option>
            <option value="flag" title="#edit-area-4">Flag</option>
            <option value="text" title="#edit-area-2">Input Field (Small)</option>
            <option value="mediumtext" title="#edit-area-2">Input Field (Medium)</option>
            <option value="largetext" title="#edit-area-2">Input Field (Large)</option>
        </select-->
    </div>
</div>
<h4><spring:message code="com.adminui.details_overlay.CustomFieldValues" text="default text" /></h4>
<div class="area alt-area">
    <div class="area-holder" id="edit-area-1">
        <div class="row">
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.Values" text="default text" /></label>
            <select disabled="true" class="alt-select" name="defaultValueDrop">
                <option value="0">&nbsp;</option>
            </select>
            <ul style="display: none;" class="add-btns">
                <li><a href="#" class="ico-add"></a></li>
                <li><a href="#" class="ico-del"></a></li>
                <li><a href="#" class="ico-edit"></a></li>
            </ul>
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
            <select disabled="true" class="alt-select" name="mappingId">
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
            <label for="edit-area-1-required" id="edit-area-1-required-label"><spring:message code="com.adminui.details_overlay.Required" text="default text" /></label>
            <input disabled="true" type="checkbox" name="required" id="edit-area-1-required" />
        </div>
        <div class="row">
            <label for="edit-area-1-postFilterable" id="edit-area-1-postFilterable-label"><spring:message code="com.adminui.details_overlay.PostFilterAttribute" text="default text" /></label>
            <input disabled="true" type="checkbox" name="postFilterable" id="edit-area-1-postFilterable" />
        </div>
        <div class="row">
            <label for="edit-area-1-searchable" id="edit-area-1-searchable-label"><spring:message code="com.adminui.details_overlay.Searchable" text="default text" /></label>
            <input disabled="true" type="checkbox" name="searchable" id="edit-area-1-searchable" />
        </div>
    </div>
    <div class="area-holder" id="edit-area-2">
        <div class="row">
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
            <select disabled="true" class="alt-select" name="mappingId">
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
            <label for="edit-area-2-required" id="edit-area-2-required-label"><spring:message code="com.adminui.details_overlay.Required" text="default text" /></label>
            <input disabled="true" type="checkbox" name="required" id="edit-area-2-required"/>
        </div>
    </div>
    <div class="area-holder" id="edit-area-3">
        <div class="row">
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.DefaultValue" text="default text" /></label>
            <textarea disabled="true" cols="30" rows="10" class="textarea3" onkeyup="setDefaultValue(this);" id="defArea3"></textarea>
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
            <select disabled="true" class="alt-select" name="mappingId">
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
                <label class="alt-label"><spring:message code="com.adminui.details_overlay.ApplySearchResultsBoost" text="default text" /></label>
                <select disabled="true" class="alt-select" onchange="setSearchBoost(this);" name="searchBoostDrop">
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
            <label for="edit-area-3-postFilterable" id="edit-area-3-postFilterable-label"><spring:message code="com.adminui.details_overlay.PostFilterAttribute" text="default text" /></label>
            <input disabled="true" type="checkbox" name="postFilterable" id="edit-area-3-postFilterable"/>
        </div>
        <div class="row">
            <label for="edit-area-3-searchable" id="edit-area-3-searchable-label"><spring:message code="com.adminui.details_overlay.Searchable" text="default text" /></label>
            <input disabled="true" type="checkbox" name="searchable" id="edit-area-3-searchable"/>
        </div>
    </div>
    <div class="area-holder" id="edit-area-4">
        <div class="row">
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.DefaultValue" text="default text" /></label>
            <textarea disabled="true" cols="30" rows="10" class="textarea3" onkeyup="setDefaultValue(this);" id="defArea4"></textarea>
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
            <select disabled="true" class="alt-select" name="mappingId">
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
                <label class="alt-label"><spring:message code="com.adminui.details_overlay.ApplySearchResultsBoost" text="default text" /></label>
                <select disabled="true" class="alt-select" onchange="setSearchBoost(this);" name="searchBoostDrop">
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
            <label for="edit-area-4-postFilterable" id="edit-area-4-postFilterable-label"><spring:message code="com.adminui.details_overlay.SearchResultsFilter" text="default text" /></label>
            <input disabled="true" type="checkbox" name="postFilterable" id="edit-area-4-postFilterable"/>
        </div>
        <div class="row">
            <label for="edit-area-4-defaultPostFilter" id="edit-area-4-defaultPostFilter-label"><spring:message code="com.adminui.details_overlay.DefaultSearchFilterOn" text="default text" /></label>
            <input disabled="true" type="checkbox" name="defaultPostFilter" id="edit-area-4-defaultPostFilter"/>
        </div>
        <div class="alt-row">
            <label class="alt-label"><spring:message code="com.adminui.details_overlay.FlagIcon" text="default text" /></label>

            <%--<div class="popup-holder">--%>
                <%--<a href="#" class="btn-add-open open"><spring:message code="com.adminui.details_overlay.add" text="default text" /></a>--%>
                <%--<div class="popup">--%>
                    <%--<div class="popup-frame">--%>
                        <%--<div class="popup-inner">--%>
                            <%--<div class="top">--%>
                                <%--<a href="#" class="alt-close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>--%>
                                <%--<h3>Upload File</h3>--%>
                            <%--</div>--%>
                            <%--<div class="row">--%>
                                <%--<input disabled="true" type="file" value="Enter Location of file or URL" />--%>
                            <%--</div>--%>
                            <%--<div class="row" style="display:none;">--%>
                                <%--<img src="res/images/ico01.png" width="39" height="39" alt="image description" />--%>
                                <%--<a href="#" class="btn-upload"><spring:message code="com.adminui.details_overlay.UPload" text="default text" /></a>--%>
                                <%--<div class="txt">--%>
                                    <%--<span class="size"> 0.59 KB</span>--%>
                                    <%--<p>icon_photo_upload_16px.gif</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="row" style="display:none;">--%>
                                <%--<img src="res/images/ico01.png" width="39" height="39" alt="image description" />--%>
                                <%--<div class="txt">--%>
                                    <%--<div class="line-box"><span class="line" style="width:70%"></span></div>--%>
                                    <%--<a href="#" class="btn-cancel">Cancel</a>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<em class="text">No File Chosen. Click the add button to the left, or drag and drop your file here.</em>--%>
            <%--</div>--%>
            <div class="upload-file" >
                <div class="upload-file-holder">
                    <img src="" id="icon" name="icon" width="39" height="39" alt="image description" />
                </div>
                <%--<a href="#" class="btn-del"></a>--%>
            </div>
        </div>
    </div>
</div>
<div class="btns-holder">
    <a href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
</div>
</fieldset>
</form>
</div>
</div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="create-custom-field">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2>Create Custom Field</h2>
                </div>
                <form action="addCatalogCustomField" class="form-create" enctype="multipart/form-data" method="post" id="theCustomFormIdAJAX">
                <input type="hidden" id="dropDownValues" name="dropDownValues" value="${customField.dropDownValues}">
                    <fieldset>
                        <input type="hidden" name="catalogId" value="${param['catalogId']}"/>
                        <h3>Custom field settings</h3>
                        <div class="area">
                            <div class="row" id="customFieldNameId">
                                <label for="field-name-create"><span>*</span><spring:message code="com.adminui.details_overlay.FieldName" text="default text" /></label>
                                <input type="text" value="" id="field-name-create" class="input2" name="fieldName"/>
                                <label id="field_name_error" class="error-msg" style="color: red; width: 490px; float: left;"><spring:message code="com.adminui.details_overlay.CustomFieldwiththisnamealreadyexists" text="default text" /></label>
                        		<label id="field_name_required" class="error-msg" style="color: red; width: 300px; float: left;"><spring:message code="com.adminui.details_overlay.Required" text="default text" /></label>
                            </div>
                            <div class="row" id="customFieldDisplayNameId">
                                <label for="display-name-create"><span>*</span><spring:message code="com.adminui.details_overlay.DisplayName" text="default text" /></label>
                                <input type="text" value="" id="display-name-create" class="input2" name="displayName"/>
                                <label id="display_name_required" class="error-msg" style="color: red; width: 300px; float: left;"><spring:message code="com.adminui.details_overlay.Required" text="default text" /></label>
                            </div>
                            <div class="row">
                                <label for="description"><spring:message code="com.adminui.details_overlay.Description" text="default text" /></label>
                                <textarea id="description" cols="30" rows="10" class="textarea2" name="fieldDesc"></textarea>
                            </div>
                            <div class="row">
                                <label>Type:</label>
                                <select id="fieldTypeDrop" class="alt-select choose-tab-select" name="fieldType">
                                   <c:if test="${catalogSummary.catalogTypeId != 1}"> <option value="list" title="#area-1"><spring:message code="com.adminui.details_overlay.DropDownList" text="default text" /></option></c:if>
                                    
                                    <option value="fixed" title="#area-3"><spring:message code="com.adminui.details_overlay.FixedValue" text="default text" /></option>
                                    <c:if test="${catalogSummary.catalogTypeId != 1}"> <option value="flag" title="#area-4"><spring:message code="com.adminui.details_overlay.Flag" text="default text" /></option> </c:if>
                                    <option value="text" title="#area-2"><spring:message code="com.adminui.details_overlay.InputFieldSmall" text="default text" /></option>
                                    <option value="mediumtext" title="#area-2"><spring:message code="com.adminui.details_overlay.InputFieldMedium" text="default text" /></option>
                                    <option value="largetext" title="#area-2"><spring:message code="com.adminui.details_overlay.InputFieldLarge" text="default text" /></option>
                                </select>
                            </div>
                        </div>
                        <h4>Custom Field Values</h4>
                        <div class="area alt-area">
                            <div class="area-holder" id="area-1">
                                <div class="row">
                                    <label class="alt-label">Value(s):</label>
                                    <select class="alt-select" id="defDropDown">
                                        <option value="0">&nbsp;</option>
                                    </select>
                                    <ul class="add-btns">
                                        <li><a href="javascript:openDropDownOverlay();" class="ico-add"></a></li>
                                        <li><a onclick="deleteDropDownValues();" class="ico-del"></a></li>
                                        <li><a onclick="editDropDownValues();" class="ico-edit"></a></li>
                                    </ul>
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
                                    <select class="alt-select" name="mappingId">
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
						            <label for="area-1-required" id="area-1-required-label"><spring:message code="com.adminui.details_overlay.Required" text="default text" /></label>
						            <input type="checkbox" name="required" id="area-1-required" />
						        </div>
						        <div class="row">
						            <label for="area-1-postFilterable" id="area-1-postFilterable-label"><spring:message code="com.adminui.details_overlay.PostFilterAttribute" text="default text" /></label>
						            <input type="checkbox" name="postFilterable" id="area-1-postFilterable" />
						        </div>
						        <div class="row">
						            <label for="area-1-searchable" id="area-1-searchable-label"><spring:message code="com.adminui.details_overlay.Searchable" text="default text" /></label>
						            <input type="checkbox" name="searchable" id="area-1-searchable" />
						        </div>
                            </div>
                            <div class="area-holder" id="area-2">
                                <div class="row">
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.DefaultValue" text="default text" /></label>
                                    <textarea cols="30" rows="10" class="textarea3" name="defaultValue"></textarea>
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
                                    <select class="alt-select" name="mappingId">
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
                                    <label for="parameter2-1"><spring:message code="com.adminui.details_overlay.Required" text="default text" /></label>
                                    <input type="checkbox" name="required" id="parameter2-1"/>
                                </div>
                            </div>
                            <div class="area-holder" id="area-3">
                                <div class="row">
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.DefaultValue" text="default text" /></label>
                                    <textarea cols="30" rows="10" class="textarea3" name="defaultValue"></textarea>
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
                                    <select class="alt-select" name="mappingId">
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
                                        <label class="alt-label"><spring:message code="com.adminui.details_overlay.ApplySearchResultsBoost" text="default text" /></label>
                                        <select class="alt-select" name="searchBoost">
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
                                    <label for="area-3-postFilterable" id="area-3-postFilterable-label"><spring:message code="com.adminui.details_overlay.PostFilterAttribute" text="default text" /></label>
						            <input type="checkbox" name="postFilterable" id="area-3-postFilterable"/>
						        </div>
						        <div class="row">
						            <label for="area-3-searchable" id="area-3-searchable-label"><spring:message code="com.adminui.details_overlay.Searchable" text="default text" /></label>
						            <input type="checkbox" name="searchable" id="area-3-searchable"/>
						        </div>
                            </div>
                            <div class="area-holder" id="area-4">
                                <div class="row">
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.DefaultValue" text="default text" /></label>
                                    <textarea cols="30" rows="10" class="textarea3" name="defaultValue"></textarea>
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.FieldMapping" text="default text" /></label>
                                    <select class="alt-select" name="mappingId">
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
                                        <label class="alt-label"><spring:message code="com.adminui.details_overlay.ApplySearchResultsBoost" text="default text" /></label>
                                        <select class="alt-select" name="searchBoost">
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
                                    <label for="area-4-postFilterable" id="area-4-postFilterable-label"><spring:message code="com.adminui.details_overlay.SearchResultsFilter" text="default text" /></label>
            						<input type="checkbox" name="postFilterable" id="area-4-postFilterable"/>
                                </div>
                                <div class="row">
                                    <label for="area-4-defaultPostFilter" id="area-4-defaultPostFilter-label"><spring:message code="com.adminui.details_overlay.DefaultSearchFilterOn" text="default text" /></label>
            						<input type="checkbox" name="defaultPostFilter" id="area-4-defaultPostFilter"/>
                                </div>
                                <div class="alt-row">
                                    <label class="alt-label"><spring:message code="com.adminui.details_overlay.FlagIcon" text="default text" /></label>
                                    <div class="popup-holder">
                                        <a href="#" class="btn-add-open open"><spring:message code="com.adminui.details_overlay.add" text="default text" /></a>
                                        <div class="popup">
                                            <div class="popup-frame">
                                                <div class="popup-inner">
                                                    <div class="top">
                                                        <a href="#" id="iconFlagFileClose" class="alt-close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                                                        <h3><spring:message code="com.adminui.details_overlay.UploadFile" text="default text" /></h3>
                                                    </div>
                                                    <div class="row">
                                                        <input type="file" id="iconFlagFile" name="iconFile" onchange="setFlagIconFile();" value="${customFieldFileUtility.iconFile}" />
                                                    </div>
                                                    <div class="row" style="display:none;">
                                                        <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                        <a href="#" class="btn-upload"><spring:message code="com.adminui.details_overlay.UPload" text="default text" /></a>
                                                        <div class="txt">
                                                            <span class="size"> 0.59 KB</span>
                                                            <p>icon_photo_upload_16px.gif</p>
                                                        </div>
                                                    </div>
                                                    <div class="row" style="display:none;">
                                                        <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                        <div class="txt">
                                                            <div class="line-box"><span class="line" style="width:70%"></span></div>
                                                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <em id="iconFlagFileEM" class="text"><spring:message code="com.adminui.details_overlay.iconFlagFileEM" text="default text" /></em>
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
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                            <input type="submit" value="Save" onclick="addCatalogCustomFieldAJAX(); return false;"/>
                            <p><span class="required">* </span><spring:message code="com.adminui.details_overlay.RequiredField" text="default text" /></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="add-custom-field">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.details_overlay.AddCustomFields" text="default text" /></h2>
                </div>
                <form action="#" id="add-custom-field-form-id" class="checkboxResetForm">
                    <fieldset>
                        <div class="lightbox-content editable-widget">
                            <table class="table-data">
                                <thead>
                                <tr>
                                    <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all2-lightbox" id="check-lightbox-2-1" /><label for="check-lightbox-2-1"></label></th>
                                    <th class="a-left"><spring:message code="com.adminui.details_overlay.NAME" text="default text" /></th>
                                    <th class="td-value"><spring:message code="com.adminui.details_overlay.FIELDTYPE" text="default text" /></th>
                                    <th>&nbsp;</th>
                                </tr>
                                </thead>
                                <tfoot>
                                </tfoot>
                                <tbody>
                                <tr>
                                    <td class="td-select"><input type="checkbox" name="check2-lightbox" id="check-lightbox-2-2" /><label for="check-lightbox-2-2"></label></td>
                                    <td class="a-left td-name">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.details_overlay.Size" text="default text" /></a></span>
                                            <input type="text" value="Size" />
                                        </div>
                                    </td>
                                    <td class="td-value"><spring:message code="com.adminui.details_overlay.DropDownList" text="default text" /></td>
                                    <td>&nbsp;</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                            <input type="submit" value="Save" />
                            <p><span class="required">* </span><spring:message code="com.adminui.details_overlay.RequiredField" text="default text" /></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="add-approver">
        <div class="holder">
            <div class="frame">
                <div class="lightbox-content">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.details_overlay.AddApprovers" text="default text" /></h2>
                    </div>
                    <form action="addApprovers" id="addApprover-form" method="post" class="checkboxResetForm">
                        <jsp:include page="details_overlay_approvers_fragment.jsp" />
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="lightbox-section">
    <div class="lightbox" id="add-profile">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.details_overlay.AddContentView" text="default text" /></h2>
                </div>
                <div id="catalog_notass_profiles-holder">
                    <jsp:include page="catalog_notass_profiles_fragment.jsp"/>
                </div>
                <div class="btns-holder">
                    <a href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                    <a class="btn-create close"  onclick="addCatalogProfiles();"><span><spring:message code="com.adminui.details_overlay.Save" text="default text" /></span></a>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="lightbox-section">
    <div class="lightbox" id="lightbox-inline-property">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2 id="propertiesPopupTitle"><spring:message code="com.adminui.details_overlay.AddProperties" text="default text" /><spring:message code="com.adminui.details_overlay.AddProperties" text="default text" /></h2>
                </div>
                <form action="createProperty" class="form-create" id="propertiesForm" method="post">
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <label for="seq-no"><span>*</span><spring:message code="com.adminui.details_overlay.SeqNo" text="default text" /></label>
                                <input type="text" name="seqNum" id="seq-no" class="required" value="">
                            </div>
                            <div class="row">
                                <label for="key"><span>*</span><spring:message code="com.adminui.details_overlay.Key" text="default text" /></label>
                                <input type="text" name="key" id="key" class="required" value="">
                            </div>
                            <div class="row">
                                <label for="value"><spring:message code="com.adminui.details_overlay.Value" text="default text" />:</label>
                                <input type="text" id="value" name="value"/>
                                <%--<select  id="dynamicValue" onchange="resetField(this);">--%>
                                    <%--<option value="0">Select dynamic value</option>--%>
                                    <%--<option value="SY-UNAME">SY-UNAME</option>--%>
                                <%--</select>--%>
                            </div>

                        </div>
                        <input type="hidden" name="catalogId" value="${param['catalogId']}"/>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                            <a href="#" class="btn-reset"><span><spring:message code="com.adminui.details_overlay.Reset" text="default text" /></span></a>
                            <a class="btn-create close"  onclick="createProperty();"><span><spring:message code="com.adminui.details_overlay.Save" text="default text" /></span></a>
                            <%--<a class="btn-create"  onclick="$('#theCatalogId').submit();"><span>Create</span></a>--%>
                            <p><span class="required">* </span><spring:message code="com.adminui.details_overlay.RequiredField" text="default text" /></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-profiles">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteDetailsCloseBtn" href="#" class="close"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.details_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.details_overlay.confirmDelete" text="default text" /></p>
                    <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.details_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteDetailsBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.details_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="drop_down_overlay">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close" onclick="$('span#errorReplyDivCF').html('');$('input#value-field').val('');$('input#price').val('');$('input#partNumber').val('');"><spring:message code="com.adminui.details_overlay.Close" text="default text" /></a>
                    <h2>Drop Down Values</h2>
                </div>
                <form class="settings-form" method="post" id="dropDownOverlay">
                    <fieldset>
                        <div class="area">
                            <div class="row" id="valueDiv">
                                <label for="value"><span>*</span><spring:message code="com.adminui.details_overlay.Value" text="default text" /></label>
                                <input type="text" value="" id="value-field" class="input2" name="value"/>
                            </div>
                            <div class="row" id="priceModifier">
                                <label for="price"><span>*</span><spring:message code="com.adminui.details_overlay.PriceModifier" text="default text" /></label>
                                <input type="text" value="" id="price" class="input2" name="price"/>
                            </div>
                            <div class="row">
                                <label for="partNumber"><spring:message code="com.adminui.details_overlay.DynamicPartNumber" text="default text" /></label>
                                <input type="text" value="" id="partNumber" class="input2" name="partNumber"/>
                            </div>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel" onclick="$('span#errorReplyDivCF').html('');$('input#value-field').val('');$('input#price').val('');$('input#partNumber').val('');">Cancel</a>
                            <input type="submit" value="Save" onclick="populateDropDownValues(); return false;"/>
                            <p><span class="required">* </span><spring:message code="com.adminui.details_overlay.RequiredField" text="default text" /></p>
                            <p><span id="errorReplyDivCF" style="font-style: italic; color: #ED1C24; height:10px"></span></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function checkAllApproverItems(check){
        try{
            if(check){
                $("#addApprover-form :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#addApprover-form :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
    function checkAllProfileItems(check){
        try{
            if(check){
                $("#add-profiles-form :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#add-profiles-form :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
    function resetField(field){
        //alert(field.id.substring(0,field.id.length-1));
        document.getElementById("value").value = 'enter value...';
        document.getElementById("value").style.color = 'grey';
    }

    function resetDropDown(field){
        //alert(field.id+"1");
        document.getElementById("dynamicValue").selectedIndex = 0;
        field.style.color = 'black';
    }

    function setVal(field){
        if (field.value == 'enter value...') {
            field.value='';
            $('#dynamicValue').val('Select dynamic value');
            //$(".select-area span").text("Select dynamic value");
        }
        field.style.color = 'black';
    }

    $(document).ready(function(){
        $('#value').focus(function() {
            setVal(document.getElementById("value"));
        });
    });

</script>
