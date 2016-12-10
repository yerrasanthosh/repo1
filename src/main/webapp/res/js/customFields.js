$( document ).ready(function() {
    navigateCatalogPage(1);
});

function isAnyItemChecked(formId){
    persistCheckboxStateOnPageChange(formId);
    try{
        return ($("#" + formId).find("#checkedItems").html()!='');
    }catch(exp){
        alert(exp);
    }
    return false;
}
function removeAttachedCatsConfirm(){
	if(!isAnyItemChecked('theCustomFormId')) {
		alert('Please select an item');
	} else {
		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', 
				removeAttachedCats);
	}
	return false;
}
function removeAttachedCats(){
    try{
        var catalogIds = $('#associatedCatalogs #catalogIds').val();
        var gIDs = 'catalogIds='+catalogIds;
        var catalogCustomFieldId=prepareQueryString('theCustomFormId','catalogCustomFieldId');
        //gIDs+='catalogIds='+catalogIds;
        gIDs+='&'+catalogCustomFieldId;
        gIDs+='&fieldId='+$('#fieldId').val();
        
        
    	if(isAnyItemChecked('theCustomFormId')){
        $.ajax({
            type:'POST',
            url: removeCats,
            data: gIDs,
            dataType: 'text',
            success: function(response) {
                if(response != ''){
                    $("#associatedCatalogs").html( response );
                    initTableUI();
                    $("#check4-1").trigger('click');
                    $("#check4-1").trigger('click');
                    navigateCatalogPage('1');
                }
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

function attachClass(){
	$("#addBtnLink").addClass('active');
}
function removeClass(){
	$("#addBtnLink").removeClass('active');
}

function removeIcon(){
	$("#browseDiv").css("display","block");
	$("#iconDiv").css("display","none");
}
//    try{
//    	var icon = document.getElementById("fieldIcon").src;
//
//        $.ajax({
//            type:'POST',
//            url: removeIconURL+'?icon='+icon,
//            data:'',
//            async: false,
//            success: function(response) {
//            	if(response == "deleted"){
//            		$("#browseDiv").css('display:block');
//            		$("#iconDiv").css('display:none');
//            	} else {
//            		$("#browseDiv").css('display:none');
//            		$("#iconDiv").css('display:block');
//            	}
//            }
//        });
//
//    }catch(exp){
//        alert(exp);
//    }
//    return false;
//}

function getFilteredCatalogs(){
    try{
        $.ajax({
            type:'POST',
            url: filterCatalogsURL,
            data: $('#searchSortForm').serialize(),
            dataType: 'text',
            success: function(response) {
                if(response != ''){
                    $("#associateCatalogs").html( response );
                    initTableUI();
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}



function deleteDropDownValues(){
    var val = $("#defDropDown").val();
    if(val != null && val != "0"){
        $("#defDropDown option[value='"+val+"']").remove();
        jcf.customForms.destroyAll();
        jcf.customForms.replaceAll();
    }
}

function editDropDownValues(){
    var val = $("#defDropDown").val();
    
    
    
    if(val != null && val != "0"){
    
    	var valueVal = val.substring(0,val.indexOf(":"));
		var partValIndex = val.indexOf("||");
		var priceval = '';
		var partVal = '';
		if(partValIndex != '-1'){
			  priceval = val.substring(val.indexOf(":")+1,partValIndex);
			  partVal = val.substring(val.indexOf("||")+2);	
		}else{
			  priceval = val.substring(val.indexOf(":")+1);
			  partVal = '';
		}
    	
    	
    
        $("#value-field").val(valueVal);
        $("#price").val(priceval);
        $("#partNumber").val(partVal);
        $.colorbox({
            href:'#drop_down_overlay',
            inline:true,
            onComplete: function(){
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    document.getElementById("value-field").value = "";
                    document.getElementById("price").value = "";
                    document.getElementById("partNumber").value = "";
                    $.colorbox.close();
                    return false;
                });
            }
        });
    }
}

function validateDropDownValues(){
   if($('#value-field').val() == null || $('#value-field').val() == ''){
   		$('span#errorReplyDivCF').html("<br />Value is required.");
   		return false;
   }
   
   
   
   
   if($('#price').val() == null || $('#price').val() == ''){
   		$('span#errorReplyDivCF').html("<br />Price is required.");
   		return false;
   }
   
   return true;
   
}

function populateDropDownValues(){

	if(!validateDropDownValues()){
		return false;
	}

	var value1 = document.getElementById("value-field").value;
	var price  = document.getElementById("price").value;
	var partNumber = document.getElementById("partNumber").value;
	
    var str = value1;
    if(price != null && price !=''){
    	str+=":"+price;
    }

    if(partNumber != null && partNumber !=''){
    	str+="||"+partNumber;
    }


  //  var str = document.getElementById("value").value + ":" + document.getElementById("price").value + "||" + document.getElementById("partNumber").value
    $('#defDropDown').append('<option value="' + str + '">' + str + '</option>');
    $('span#errorReplyDivCF').html('');
    $('input#value-field').val('');
    $('input#price').val('');
    $('input#partNumber').val('');
    $.colorbox.close();
    var val = $("#defDropDown").val();
    if(val != null && val != "0"){
        $("#defDropDown option[value='"+val+"']").remove();
    }
    jcf.customForms.destroyAll();
    jcf.customForms.replaceAll();

    return false;
}

function addCatalogsToCustomField(){
    var gIDs=prepareQueryString('associateCatalogsForm','catalogCustomFieldId');
    var catalogIds = $('#associateCatalogsForm #catalogIds').val();
    if(catalogIds && catalogIds.length>0) {
    	gIDs += '&catalogIds='+catalogIds;	
    }    
    var customFieldId = $('#fieldId').val();
    if(customFieldId && customFieldId.length>0) {
    	gIDs += '&customFieldId='+customFieldId;	
    }
    
    try{
    	if(isAnyItemChecked('associateCatalogsForm')){
        $.ajax({
            type:'POST',
            url: addCatalogsFieldServiceUrl,
            data: gIDs,
            dataType: 'text',
            success: function(response) {
                if(response != ''){
                    $("#associatedCatalogs").html( response );
                    initTableUI();
                    $("#check4-1").trigger('click');
                    $("#check4-1").trigger('click');
                    $.colorbox.close();
                }
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
function showAddCatalogs() {

    try{
        $.ajax({
            type:'POST',
            url: addCatalogsServiceUrl,
            data: $('#theCustomFormId').serialize(),
            dataType: 'text',
            success: function(response) {
                if(response != ''){
                    $("#associateCatalogs").html( response );
                    $("#checkedItems").html($("#catalogIds").val());
                    initTableUI();
                    $.colorbox({
                        href:'#add-catalogs',
                        inline:true,
                        onComplete: function(){
                            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                                $.colorbox.close();
                                return false;
                            });
                        }
                    });

                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function navigateCatalogPage(page) {
    var list=persistCheckboxStateOnPageChange('associateCatalogsForm');
    var gIDs=prepareQueryString('associateCatalogsForm','catalogCustomFieldId');
    var data=$("#checkedItems").html().split(",");
    for(j=0;j<data.length;j++){
        var gId=data[j];
        gIDs=gIDs+'catalogCustomFieldId='+gId+'&';
    }
    gIDs=gIDs.substring(0,gIDs.length-1);
    
    var catalogIds = $('#theCustomFormId #catalogIds').val();
    gIDs += '&catalogIds='+catalogIds;
    
    var navigatePage = parseInt($('#associateCatalogs #allCatalogsPage').val());
    var totalPages = parseInt($('#associateCatalogs #allCatalogsPages').val());

    if(page == "next"){
    	navigatePage = navigatePage + 1;
    } else {
    	navigatePage = navigatePage - 1;
    }
    
    var searchText=$("#searchBox").val();
    if(searchText && searchText !== "Search Catalogs") {
    	gIDs += '&search='+searchText;
    }

    if(navigatePage >= 0 && navigatePage < totalPages){
        try{
            $.ajax({
                type:'POST',
                url: navigateCatalogPageURL+'?page='+navigatePage,
                data: gIDs,
                dataType: 'text',
                success: function(response) {
                    $('#associateCatalogs').html( response );
                    initTableUI();

                    copyCheckedItemList('associateCatalogsForm',list);
                }
            });
        }catch(exp){
            alert(exp);
        }
    }
    return false;
}

function navigateCustomFieldCatalogPage(page) {
	var list=persistCheckboxStateOnPageChange('theCustomFormId');
    var navigatePage = parseInt($('#associatedCatalogs #catalogListPage').val());
    var totalPages = parseInt($('#associatedCatalogs #catalogListPages').val());
    var catalogIds = $('#associatedCatalogs #catalogIds').val();
    if(page == "next"){
    	navigatePage = navigatePage + 1;
    } else {
    	navigatePage = navigatePage - 1;
    }
    var customFieldId = $('#fieldId').val();
    var serviceUrl = navigateCustomFieldCatalogPageURL+'?page='+navigatePage;
    if(customFieldId && customFieldId != '') {
    	serviceUrl += '&customfieldid=' + customFieldId;
    }

    if(navigatePage >= 0 && navigatePage < totalPages){
        try{
            $.ajax({
                type:'POST',
                url: serviceUrl,
                data: catalogIds,
                dataType: 'text',
                contentType: 'application/json',
                success: function(response) {
                    $('#associatedCatalogs').html( response );
                    initTableUI();
                    copyCheckedItemList('theCustomFormId',list);
                }
            });
        }catch(exp){
            alert(exp);
        }
    }
    return false;
}

function refreshDropDowns(){
    var fieldType = document.getElementById("fldType").value;

    $('[name="fieldType"]').val(fieldType);
    $('[name="mapDropDown"]').val(document.getElementById("mappingId").value);
    $('[name="searchBoostDrop"]').val(document.getElementById("searchBoost").value);
    $("#defArea3").val(document.getElementById("defaultValue").value);
    $("#defArea4").val(document.getElementById("defaultValue").value);
    $("#description").val(document.getElementById("desc").value);
    $("#display-name").val(document.getElementById("disName").value);
    $("#field-name").val(document.getElementById("fldName").value);

    $('#edit-area-1').hide();
    $('#edit-area-2').hide();
    $('#edit-area-3').hide();
    $('#edit-area-4').hide();

    var selected_area;

    if(fieldType.toLowerCase() == "Drop Down List".toLowerCase() || fieldType.toLowerCase() == "list".toLowerCase() || fieldType.toLowerCase() == "dropDownList".toLowerCase()) selected_area = "#edit-area-1";
    else if (fieldType.toLowerCase() == "Fixed Value".toLowerCase() || fieldType.toLowerCase() == "fixed".toLowerCase() || fieldType.toLowerCase() == "fixedValue".toLowerCase()) selected_area = "#edit-area-3";
    else if(fieldType.toLowerCase() == "Flag".toLowerCase() || fieldType.toLowerCase() == "flag".toLowerCase()) selected_area = "#edit-area-4";
    else selected_area = "#edit-area-2";

//        var form = $('#editCatalogCustomFormId');
//        inputFile = form.find('select');
//        inputFile.each(function(){
//            this.jcf.refreshState();
//        });

    if(document.getElementById("searchable").value == 'true') {
        $(selected_area + '-searchable-label').addClass('ui-state-active');
        $(selected_area + '-searchable').attr('checked', true);
    }

    if(document.getElementById("required").value == 'true') {
        $(selected_area + '-required-label').addClass('ui-state-active');
        $(selected_area + '-required').attr('checked', true);
    }

    if(document.getElementById("postFilter").value == 'true') {
        $(selected_area + '-postFilterable-label').addClass('ui-state-active');
        $(selected_area + '-postFilterable').attr('checked', true);
    }

    if(document.getElementById("defPostFilter").value == 'true') {
        $(selected_area + '-defaultPostFilter-label').addClass('ui-state-active');
        $(selected_area + '-defaultPostFilter').attr('checked', true);
    }


    $(selected_area).show();
    

}

function addCustomField() {

    	$.ajax({
            type:'POST',
            url: addCustomFieldURL,
            data: $('#theCustomFormId').serialize(),
            dataType: 'text',
            async: true,
            success: function(response) {
            }
        });
}

function fieldExists(name, fType, postFilter) {
	var nameExists = false;
	$.ajax({
        type:'POST',
        url: fieldExistsURL+'?name='+name+'&fType='+fType+'&postFilter='+postFilter,
        data:'',
        async: false,
        success: function(response) {
        	nameExists = response;
        }
    });
	return nameExists;
}


function setDefaultValue(field){
    $("#defaultValue").val(field.value);
}

function setDefaultValueDropDown(){
    var values = [];
    $('#defDropDown option').each(function() {
        values.push( $(this).attr('value') );
    });
    $("#dropDownValues").val(values);
}

function resetDefaultValue(){
    $("#defaultValue").val("");
    $("#defArea3").val("");
    $("#defArea4").val("");
}

function setSearchBoost(field){
    $("#searchBoost").val(field.options[field.selectedIndex].value);
}

function setMappingId(field){
    $("#mappingId").val(field.options[field.selectedIndex].value);
}
