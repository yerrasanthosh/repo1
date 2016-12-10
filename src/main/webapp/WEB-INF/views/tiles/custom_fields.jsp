<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.catalog.model.CatalogCustomField,
         com.vroozi.customerui.acl.model.Permission"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript" >

    var removeCustomFields = '<c:url value="/deleteCustomField" />';
    var activateDeactivateCustomFields = '<c:url value="/activateDeactivateCustomFields" />';
    var getFieldsByType = '<c:url value="/getFieldsByType" />';
    var navigateCustFieldServiceUrl = '<c:url value="/navigateCustFieldPage" />';
    var postFilterCountExceededUrl = '<c:url value="/postFilterCountExceeded" />';
    function removeCustomField() {

//    	var check = false;
        try{
//        	var arr = document.getElementsByName("catalogCustomFieldId");
//        	for(var i = 0; i < arr.length; i++){
//        		if(arr[i].checked){
//            		check = true;
//            	}
//        	}
            gIDs=prepareQueryString('summaryForm','catalogCustomFieldId');
        	if(isAnyItemChecked('summaryForm')){

            $.ajax({
                type:'POST',
                url: removeCustomFields,
                data: gIDs,
                dataType: 'text',
                success: function(response) {
//                    $("#custom-fields-table").html( response );
                    window.location.replace("/adminui/customFields");

                }
            });
        	} else {
				return false;
            	}
        }catch(exp){
            alert(exp);
        }
        return false;
    }

	function getCustomFieldsByType(strVal){
		$('[name="orderBy"]').val(strVal);
		try{
            $.ajax({
                type:'POST',
                url: getFieldsByType,
                data: $('#summaryForm').serialize(),
                dataType: 'text',
                success: function(response) {
                    $("#custom-fields-table").html( response );
                    initTableUI();
                    if(strVal == "fieldName"){
            			$('#fieldName').addClass("sorting");
            			$('#displayName').removeClass("sorting");
            			$('#typeToDisplay').removeClass("sorting");
            			$('#fieldDesc').removeClass("sorting");
            		} else if(strVal == "displayName"){
            			$('#fieldName').removeClass("sorting");
            			$('#displayName').addClass("sorting");
            			$('#typeToDisplay').removeClass("sorting");
            			$('#fieldDesc').removeClass("sorting");
            		} else if(strVal == "typeToDisplay"){
            			$('#fieldName').removeClass("sorting");
            			$('#displayName').removeClass("sorting");
            			$('#typeToDisplay').addClass("sorting");
            			$('#fieldDesc').removeClass("sorting");
            		} else if(strVal == "fieldDesc"){
            			$('#fieldName').removeClass("sorting");
            			$('#displayName').removeClass("sorting");
            			$('#typeToDisplay').removeClass("sorting");
            			$('#fieldDesc').addClass("sorting");
            		} else {
            			$('#fieldName').removeClass("sorting");
            			$('#displayName').removeClass("sorting");
            			$('#typeToDisplay').removeClass("sorting");
            			$('#fieldDesc').removeClass("sorting");
            		}
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
	}
	
	function countExceeded(idString) {
		var exceeded = false;
		$.ajax({
	        type:'POST',
	        url: postFilterCountExceededUrl+'?idString='+idString,
	        data:'',
	        async: false,
	        success: function(response) {
	        	exceeded = response;
	        	if(exceeded){
	        		$('#count_exceeded').css("display","block");
	        	} else {
	        		$('#count_exceeded').css("display","none");
	        	}
	        }
	    });
		return exceeded;
	}
	
    function actDeactCustomField(val) {
        document.getElementById("active").value = val;
        var check = false;
        var ids = "";
        try{
            var data=$("#summaryForm #checkedItems").html().split(",");

            gIDs=prepareQueryString('summaryForm','catalogCustomFieldId');
            if(isAnyItemChecked('summaryForm')){
                gIDs+='&active='+val;
                for(var i = 0; i < data.length; i++){
                    //if(arr[i].checked){
                        check = true;
                        if(i > 0 && ids != ""){
                            ids = ids + ",";
                        }
                        ids = ids + data[i];
                    //}
                }
                if(val != "false" && ids != ""){
                    if(countExceeded(ids))
                        return false;
                }
                if(check){
                $.ajax({
                    type:'POST',
                    url: activateDeactivateCustomFields,
                    data: gIDs,
                    dataType: 'text',
                    success: function(response) {
                            $("#custom-fields-table").html( response );
                            //initTableUI();
                        resetCheckBoxList('summaryForm');
                        getCustomFieldsByType();
                    }
                });
        	}
            else {
            	return false;
            	}
            }
        }catch(exp){
            alert(exp);
        }
        return false;
    }
    function navigateCustFieldPage(page){
        var list=persistCheckboxStateOnPageChange('summaryForm');
        var currPage = document.getElementById("currPage").value;
        try{
            $.ajax({
                type:'POST',
                url: navigateCustFieldServiceUrl+'?page='+page+'&currPage='+currPage,
                data: $('#summaryForm').serialize(),
                dataType: 'text',
                success: function(response) {
                    $('#custom-fields-table').html( response );
                    initTableUI();

                    copyCheckedItemList('summaryForm',list);
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }
</script>
<div id="wrapper">
<div id="main">
<div class="section">
    <ul class="breadcrumbs">
        <li><a href="vroozi"><spring:message code="com.adminui.custom_fields.Vroozi" text="default text" /></a></li>
        <li><a href="catalog"><spring:message code="com.adminui.custom_fields.ContentManager" text="default text" /></a></li>
        <li><spring:message code="com.adminui.custom_fields.CustomFieldsManagement" text="default text" /></li>
    </ul>
    <div class="video-btn-holder">
        <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
        <a href="#" class="btn"><span><em>Help video</em></span></a-->
    </div>
</div>
<div class="main-holder">
<div id="content">
<form action="#" onsubmit="return false;" id="summaryForm" class="checkboxResetForm">
<input type="hidden" name="orderBy"/>
<h1><spring:message code="com.adminui.custom_fields.CustomFieldsManagement" text="default text" /></h1>
<div class="content-block toggle-block active" id="summary-section">
    <div class="headline">
        <h2><a href="#" class="open-close"><spring:message code="com.adminui.custom_fields.CustomfieldSsummary" text="default text" /></a></h2>
    </div>
    <div class="block">
        <div class="content">
            <div class="summary-box">
            	
                <table class="summary-table">
                    <thead>
                    <tr>
                        <td class="a-center sep view"><spring:message code="com.adminui.custom_fields.View" text="default text" /></td>
                        <td><spring:message code="com.adminui.custom_fields.Summary" text="default text" /></td>
                        <td></td>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="metric" items="${metrics}" varStatus="cntr1">
                        <c:if test="${metric.count > 0}">
                            <tr>
                                <td class="a-center view"><input id="${metric.fieldType}" name="customFieldIdList" value="${metric.fieldType}" onchange="getCustomFieldsByType();" type="checkbox" checked="checked" /><label for="${metric.fieldType}"></label></td>
                                <th><c:if test="${metric.fieldType eq 'text'}">Input Field (Small)</c:if>
                                    <c:if test="${metric.fieldType eq 'mediumtext'}">Input Field (Medium)</c:if>
                                    <c:if test="${metric.fieldType eq 'largetext'}">Input Field (Large)</c:if>
                                    <c:if test="${metric.fieldType eq 'list'}">Drop Down List</c:if>
                                    <c:if test="${metric.fieldType eq 'fixed'}">Fixed Value</c:if>
                                    <c:if test="${metric.fieldType eq 'flag'}">Flag</c:if>:</th>
                                <td class="a-right">${metric.count}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <c:if test="${totalFields > 0}">
                        <tr>
                            <td class="a-center view"></td>
                            <td>Total:</td>
                            <td class="a-right">${totalFields}</td>
                        </tr>
                    </c:if>
                    </tfoot>
                </table>
                
            </div>
        </div>
    </div>
</div>
<div class="add-slide-blocks">
    <div class="toggle-block active" id="info-section">
        <div class="title">
            <h2><a href="#" class="open-close"><spring:message code="com.adminui.custom_fields.CustomFIELDS" text="default text" /></a></h2>
        </div>
        <div class="block">
            <div class="content editable-widget">
                <div class="top-box">
                    <div class="add-search-form advanced" style="border-right: none;">
                        <fieldset>
                            <input type="text" id="searchBox" name="search" value="Search Custom Fields" />
                            <input id="submitBtn" type="submit" onclick="getCustomFieldsByType();" class="searchbtn" value="Submit" />
                            <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchBox').val('Search Custom Fields'); $('#submitBtn').click();" />
                        </fieldset>
                    </div>
                    <label id="count_exceeded" style="color: red; width: 391px; float: left;font-weight: bold; display: none;"><spring:message code="com.adminui.custom_fields.postFilterLimit" text="default text" /></label>
                    <!-- <div class="form-sort">
                        <fieldset>
                            <label>Status:</label>
                            <select name="status" onchange="getCustomFieldsByType();">
                                <option></option>
                                <option value="active">Active</option>
                                <option value="inactive">Inactive</option>
                            </select>
                            <label>Sort By: </label>
                            <select name="orderBy" onchange="getCustomFieldsByType();">
                                <option value="">Sort By</option>
                                <option value="fieldName">Field Name</option>
                                <option value="displayName">Display Name</option>
                            </select>
                        </fieldset>
                    </div> -->
                </div>
                <div id="custom-fields-table">
                <table class="table-data">
                    <thead>
                    <tr>
                        <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all3" id="check3-1" /><label for="check3-1"></label></th>
                        <th class="a-left"><a id="fieldName" onclick="getCustomFieldsByType('fieldName');" style="cursor: pointer;" ><spring:message code="com.adminui.custom_fields.FieldName" text="default text" /></a></th>
                        <th><a id="displayName" onclick="getCustomFieldsByType('displayName');" style="cursor: pointer;"><spring:message code="com.adminui.custom_fields.DisplayNAME" text="default text" /></a></th>
                        <th><a id="typeToDisplay" onclick="getCustomFieldsByType('typeToDisplay');" style="cursor: pointer;"><spring:message code="com.adminui.custom_fields.FIELDTYPE" text="default text" /></a></th>
                        <th class="a-left"><a id="fieldDesc" onclick="getCustomFieldsByType('fieldDesc');" style="cursor: pointer;"><spring:message code="com.adminui.custom_fields.DESCRIPTION" text="default text" /></a></th>
                        <th>STATUS</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% int customFieldCounter = 0; %>
                    <c:forEach var="entry" items="${customFields.pageList}" varStatus="cntr1">
                        <tr>
                            <td class="td-select">
                                <a href="#" class="btn-up-down"><span class="up-arrow">up</span> <span class="down-arrow"><spring:message code="com.adminui.custom_fields.down" text="default text" /></span></a>
                                <input type="checkbox" name="catalogCustomFieldId" value="${entry.id}" class="target-chbox" id="${entry.id}" /><label for="${entry.id}"></label></td>
                            <td class="a-left td-name">
                                <div class="field">
                                    <span><a href='<c:out value="editCustomFieldPage"/>?customFieldId=<c:out value="${entry.id}"/>' >${entry.fieldName}</a></span>
                                    <input type="text" value="Size" />
                                </div>
                            </td>
                            <td>${entry.displayName}</td>
                            <td><c:if test="${entry.fieldType eq 'text'}"><spring:message code="com.adminui.custom_fields.InputFieldSmall" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'mediumtext'}"><spring:message code="com.adminui.custom_fields.InputFieldMedium" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'largetext'}"><spring:message code="com.adminui.custom_fields.InputFieldLarge" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'list'}"><spring:message code="com.adminui.custom_fields.DropDownList" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'fixed'}"><spring:message code="com.adminui.custom_fields.FixedValue" text="default text" /></c:if>
                                    <c:if test="${entry.fieldType eq 'flag'}"><spring:message code="com.adminui.custom_fields.Flag" text="default text" /></c:if></td>
                            <td class="a-left">${entry.fieldDesc}</td>
                            <c:if test="${entry.active}">
                                <td><spring:message code="com.adminui.custom_fields.Active" text="default text" /></td>
                            </c:if>
                            <c:if test="${!entry.active}">
                                <td><spring:message code="com.adminui.custom_fields.Inactive" text="default text" /></td>
                            </c:if>
                        </tr>
                        <% ++customFieldCounter; %>
                    </c:forEach>
                    <tr>

                    </tr>

                    </tbody>
                </table>
							<div class="bottom-data">
									<div class="pager">
									<c:if test="${customFields.nrOfElements eq 0}">
										<span>Page ${customFields.page} of ${customFields.pageCount-1}</span>
										</c:if>
										<c:if test="${customFields.nrOfElements > 0}">
										<span>Page ${customFields.page+1} of ${customFields.pageCount}</span>
										</c:if>
                                        <input type="hidden" id="currPage" value="${customFields.page}" />
										<ul>
                                            <li><a href="javascript:void(0)" class="<c:if test='${customFields.page > 0}'>btn-prev-active</c:if><c:if test='${customFields.page eq 0}'>btn-prev</c:if> onclick="navigateCustFieldPage('prev');"></a></li>
                                            <li><a href="javascript:void(0)" class="<c:if test='${customFields.page+1 < customFields.pageCount}'>btn-next</c:if>" onclick="navigateCustFieldPage('next');"></a></li>
										</ul>
									</div>
									<c:if test="${customFields.nrOfElements eq 0}">
									<strong class="pages"><spring:message code="com.adminui.custom_fields.TotalRecords" text="default text" /> ${customFields.firstElementOnPage}-${customFields.lastElementOnPage+1} <spring:message code="com.adminui.custom_fields.of" text="default text" /> ${customFields.nrOfElements}</strong>
									</c:if>
									<c:if test="${customFields.nrOfElements > 0}">
									<strong class="pages"><spring:message code="com.adminui.custom_fields.TotalRecords" text="default text" /> ${customFields.firstElementOnPage+1}-${customFields.lastElementOnPage+1} <spring:message code="com.adminui.custom_fields.of" text="default text" /> ${customFields.nrOfElements}</strong>
									</c:if>
								</div>
								</div>
                <div class="function">
                    <ul>
                        <c:if test="<%=aclManager.allow(request, Permission.DELETE_CUSTOM_FIELDS)%>">
                            <li><a class="ico-delete" onclick="removeCustomFieldConfirm();"><span><em><spring:message code="com.adminui.custom_fields.DELETE" text="default text" /></em></span></a></li>
                        </c:if>
                        <c:if test="<%=aclManager.allow(request, Permission.ACTIVATE_CUSTOM_FIELDS)%>">
                            <li><a class="ico-activate" onclick="actDeactCustomField('true'); return false;"><span><em><spring:message code="com.adminui.custom_fields.ACTIVATE" text="default text" /></em></span></a></li>
                        </c:if>
                        <c:if test="<%=aclManager.allow(request, Permission.DEACTIVATE_CUSTOM_FIELDS)%>">
                            <li><a class="ico-deactivate" onclick="actDeactCustomField('false'); return false;"><span><em><spring:message code="com.adminui.custom_fields.DeACTIVATE" text="default text" /></em></span></a></li>
                        </c:if>
                    </ul>
                </div>
                    <input type="hidden" name="active" id="active">
                
            </div>
        </div>
    </div>
</div>
</form>
</div>
<div id="sidebar">
    <ul class="sub-nav">
        <li><a href="<c:out value="catalog"/>" class="ico-back"><span><spring:message code="com.adminui.custom_fields.BACK" text="default text" /></span></a></li>
        <c:if test="<%=aclManager.allow(request, Permission.CREATE_CUSTOM_FIELDS)%>">
            <li><a href="<c:out value="createCustomFields"/>" class="ico-create-field"><span><spring:message code="com.adminui.custom_fields.CREATECUSTOMFIELD" text="default text" /></span></a></li>
        </c:if>
    </ul>
</div>
</div>
</div>
</div>

<script type="text/javascript">
    function removeCustomFieldConfirm(catalogId) {
    	if(!isAnyItemChecked('summaryForm')) {
    		alert('Please select an item');
    	} else {
    		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', 
    				removeCustomField);
    	}
    	return false;
    };

</script>
