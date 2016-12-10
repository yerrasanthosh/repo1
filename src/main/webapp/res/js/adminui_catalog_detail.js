var paginationInTransit = false;



function getCatalogItemsPageFilter(pageNum , criteria){
    if( (pageNum < 1 || pageNum > gCatalogItemTotalNumOfPages) || paginationInTransit) return;

    paginationInTransit = true;
    try{
	        $.ajax({
	            type:'GET',
	            async: false,
	            url: gCatalogItemTableFragmentServiceUrl+"?searchData="+criteria,
	            data: { catalogId: gCurrentCatalogId, pageNum: pageNum },
	            success: function(data, textStatus, jqXHR) {
	                if(jqXHR.responseText != ''){
	                    //alert(jqXHR.responseText);
	                    var responseArray = jqXHR.responseText.split('@@@');
	                    var numOfItems = responseArray[0];      // header info
	                    var itemsFragment = responseArray[1];   // fragment data
	
	                    $('#catalog_item_table_rows_body').empty();
	                    $('#catalog_item_table_rows_body').append(itemsFragment);
	
	                    var begingRecord = (pageNum-1)*gCatalogItemPageSize+1;
	                    var endRecord = (pageNum-1)*gCatalogItemPageSize + gCatalogItemPageSize;
	
	                    
	                    if(pageNum > 1){
	                    	$("#catalog-item-prev-page").removeClass("btn-prev").addClass("btn-prev-second");
	                    }else if(pageNum == 1){
	                    	$("#catalog-item-prev-page").removeClass("btn-prev-second").addClass("btn-prev");
	                    }


	                    if(pageNum < gCatalogItemTotalNumOfPages){
	                    	$("#catalog-item-next-page").removeClass("btn-next-last").addClass("btn-next");
	                    }else if(pageNum == gCatalogItemTotalNumOfPages){
	                    	$("#catalog-item-next-page").removeClass("btn-next").addClass("btn-next-last");
	                    }	                    
	                    
	                    endRecord = (endRecord > gCatalogItemTotalNumOfItems) ? gCatalogItemTotalNumOfItems:endRecord ;
	                    $('span#catalog_items_current_page_num').html('Page '+ pageNum + ' of ' + gCatalogItemTotalNumOfPages);
	                    $('#catalog_items_current_record_range').html('Total Records: ' + begingRecord + '- ' + endRecord + ' of ' + gCatalogItemTotalNumOfItems);
	                }
                    gCatalogItemsPageNum = pageNum;
                    paginationInTransit = false;
	               
	            },
	            error: function(error){
	            	alert(error.status);
	            }
	        });
    	
    }catch(exp){
        alert(exp)
    }
}

function getCatalogItemsPage(pageNum){
    if( (pageNum < 1 || pageNum > gCatalogItemTotalNumOfPages) || paginationInTransit) return;

    paginationInTransit = true;
    try{
        $.ajax({
            type:'GET',
            async: false,
            url: gCatalogItemTableFragmentServiceUrl,
            data: { catalogId: gCurrentCatalogId, pageNum: pageNum },
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);
                    var responseArray = jqXHR.responseText.split('@@@');
                    var numOfItems = responseArray[0];      // header info
                    var itemsFragment = responseArray[1];   // fragment data

                    $('#catalog_item_table_rows_body').empty();
                    $('#catalog_item_table_rows_body').append(itemsFragment);

                    $('span#catalog_items_current_page_num').html('Page '+ pageNum + ' of ' + gCatalogItemTotalNumOfPages);

                    if(pageNum > 1){
                    	$("#catalog-item-prev-page").removeClass("btn-prev").addClass("btn-prev-second");
                    }else if(pageNum == 1){
                    	$("#catalog-item-prev-page").removeClass("btn-prev-second").addClass("btn-prev");
                    }
                    var begingRecord = (pageNum-1)*gCatalogItemPageSize+1;
                    var endRecord = (pageNum-1)*gCatalogItemPageSize+1 + gCatalogItemPageSize;

                    endRecord = (endRecord > gCatalogItemTotalNumOfItems) ? gCatalogItemTotalNumOfItems:endRecord ;

                    $('#catalog_items_current_record_range').html('Total Records: ' + begingRecord + '- ' + endRecord + ' of ' + gCatalogItemTotalNumOfItems);

                    gCatalogItemsPageNum = pageNum;
                    paginationInTransit = false;
                }
            }
        });
    }catch(exp){
        alert(exp)
    }
}


function removeCatalogCustomFields() {

    if($('#updateCatalogCustomFieldId [name="catalogCustomFieldId"]:checked').length <= 0){
    	alert('Please select an item');
    }else{
    	deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', deleteCatalogCustomFields);        
    }
    return false;
}
function deleteCatalogCustomFields() {
	try{
        $.ajax({
            type:'POST',
            url: removeCatalogCustomField,
            data: $('#updateCatalogCustomFieldId').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#custom-field-table").html( response );
                initTableUI();
                jcf.customForms.replaceAll();
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function updateCatalogCustomFieldList() {

    try{
        $.ajax({
            type:'POST',
            url: updateCatalogCustomFieldListURL,
            data: $('#updateCatalogCustomFieldId').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#custom-field-table").html( response );
                initTableUI();
                jcf.customForms.replaceAll();
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function checkCheckbox(chkbox){
    try{
    if(chkbox.checked){
    $(chkbox).next().addClass('ui-state-active');
    }else{
    $(chkbox).next().removeClass('ui-state-active');
    }
}catch(exp){
    alert(exp);
    }
}

// todo: this is probably never called
function getCatalogItemsPage(pageNum){
    if(pageNum < 1 || pageNum > gCatalogItemTotalNumOfPages) return;

    try{
    	if($("#searchCriteria").val() == null || $("#searchCriteria").val()=='' || $("#searchCriteria").val()=='Search Within...'){
	        $.ajax({
	            type:'GET',
	            async: false,
	            url: gCatalogItemTableFragmentServiceUrl,
	            data: { catalogId: gCurrentCatalogId, pageNum: pageNum },
	            success: function(data, textStatus, jqXHR) {
	                if(jqXHR.responseText != ''){
	                    //alert(jqXHR.responseText);
	                    var responseArray = jqXHR.responseText.split('@@@');
	                    var numOfItems = responseArray[0];      // header info
	                    var itemsFragment = responseArray[1];   // fragment data
	
	                    $('#catalog_item_table_rows_body').empty();
	                    $('#catalog_item_table_rows_body').append(itemsFragment);
	
	                    $('span#catalog_items_current_page_num').html('Page '+ pageNum + ' of ' + gCatalogItemTotalNumOfPages);
	                    var begingRecord = (pageNum-1)*gCatalogItemPageSize+1;
	                    var endRecord = (pageNum-1)*gCatalogItemPageSize + gCatalogItemPageSize;
	                    if(pageNum > 1){
	                    	$("#catalog-item-prev-page").removeClass("btn-prev").addClass("btn-prev-second");
	                    }else if(pageNum == 1){
	                    	$("#catalog-item-prev-page").removeClass("btn-prev-second").addClass("btn-prev");
	                    }


	                    if(pageNum < gCatalogItemTotalNumOfPages){
	                    	$("#catalog-item-next-page").removeClass("btn-next-last").addClass("btn-next");
	                    }else if(pageNum == gCatalogItemTotalNumOfPages){
	                    	$("#catalog-item-next-page").removeClass("btn-next").addClass("btn-next-last");
	                    }

	                    
	                    
	                    endRecord = (endRecord > gCatalogItemTotalNumOfItems) ? gCatalogItemTotalNumOfItems:endRecord ;
	
	                    $('#catalog_items_current_record_range').html('Total Records: ' + begingRecord + '- ' + endRecord + ' of ' + gCatalogItemTotalNumOfItems);
                        initTableUI();
	                }
	
	                gCatalogItemsPageNum = pageNum;
	            }
	        });
    	}else{
    		getCatalogItemsPageFilter(pageNum, $("#searchCriteria").val());
    	}

    }catch(exp){
        alert(exp)
    }
}


function editCatalogCustomFieldAJAX() {

    try{
        $.ajax({
            type:'POST',
            url: editCatalogCustomField,
            data: $('#editCatalogCustomFormId').serialize(),
            dataType: 'text',
            success: function(response) {
                $.colorbox.close();
                $("#custom-field-table").html( response );
                initTableUI();
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
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

function resizeDiv(){
	 $("#colorbox").css({'height': ($('#colorbox').height()) + 'px'});
     $("#cboxContent").css({'height': ($('#colorbox').height()) + 'px'});
     $("#cboxLoadedContent").css({'height': ($('#colorbox').height()) + 'px'});
}

function setDefaultValueDropDown(){
    var values = [];
    $('#defDropDown option').each(function() {
        values.push( $(this).attr('value') );
    });
    $("#dropDownValues").val(values);
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

    $("#createCustField").click();



    return false;
}

function openDropDownOverlay(){
        $.colorbox({
        href:'#drop_down_overlay',
        overlayClose:false,
        inline:true,
        onComplete: function(){
            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){            	
                $.colorbox.close();
                return false;
            });
        },onClosed: function() {
                $("#createCustField").click();
        }
    });
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
        $("#value1").val(val.substr(0,val.indexOf(":")));
        $("#price").val(val.substr(val.indexOf(":")+1,val.indexOf("|")-val.indexOf(":")-1));
        $("#partNumber").val(val.substr(val.lastIndexOf("|")+1,val.length));
        $.colorbox({
            href:'#drop_down_overlay',
            inline:true,
            onComplete: function(){
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    document.getElementById("value1").value = "";
                    document.getElementById("price").value = "";
                    document.getElementById("partNumber").value = "";
                    $.colorbox.close();
                    return false;
                });
            },onClosed: function() {
                document.getElementById("value1").value = "";
                document.getElementById("price").value = "";
                document.getElementById("partNumber").value = "";
                $("#createCustField").click();
            }
        });
    }
}


function removeCFValidationError(){
	alert('');
	if($('#field-name-create').val != null && $('#field-name-create').val != ""){
		$('#field_name_required').removeClass('error');
	}
	
	if($('#display-name-create').val != null && $('#isplay-name-create').val != ""){
		$('#display_name_required').removeClass('error');
	}    
}

function addCatalogCustomFieldAJAX() {
    setDefaultValueDropDown();
	if(document.getElementById("field-name-create").value == ""){
		$('#field_name_required').addClass('error');
		resizeDiv();
		return false;
	}
	if(document.getElementById("display-name-create").value == ""){
		$('#display_name_required').addClass('error');
		$('#field_name_required').removeClass('error');
		resizeDiv();
		return false;
	}
	
	var flag = fieldExists(document.getElementById("field-name-create").value, document.getElementById("fieldTypeDrop").value, false);
	
	if(flag == "false"){

	$('#field_name_required').removeClass('error');
	$('#display_name_required').removeClass('error');
		
    try{
        $.ajax({
            type:'POST',
            url: addCatalogCustomField,
            data: $('#theCustomFormIdAJAX').serialize(),
            dataType: 'text',
            success: function(response) {            	
                $.colorbox.close();
                $("#custom-field-table").html( response );
                initTableUI();

            }
        });
    }catch(exp){
        alert(exp);
    }
	} else {
		$('#display_name_required').removeClass('error');
		$('#field_name_required').removeClass('error');
		$('#field_name_error').addClass('error');
		resizeDiv();
		return false;
	}
    return false;
}

function resetOverlay() {
	$(':input', "#theCustomFormIdAJAX")
			.not(':button, :submit, :reset, :hidden').val('').removeAttr(
					'checked').removeAttr('selected');
	var form = $('#theCustomFormIdAJAX');
	form.get(0).reset();
	jcf.customForms.replaceAll();
}

function associateCustomFields() {

    var gIDs=prepareQueryString('add-custom-field-form-id','customFieldIdList');

    var catalogId=$('#add-custom-field-form-id').find("input[name=catalogId]").val();
    var pageNumber=$('#add-custom-field-form-id').find("input[name=pageNumber]").val();
    var finalData='catalogId='+catalogId+'&'+gIDs+'&'+'pageNumber='+pageNumber;
    try{
        $.ajax({
            type:'POST',
            url: associateCatalogCustomFieldURL,
            data: finalData,
            dataType: 'text',
            success: function(response) {
                $.colorbox.close();
                $("#custom-field-table").html( response );
                initTableUI();
                jcf.customForms.replaceAll();
                resetCheckBoxList('add-custom-field-form-id');
            }
        });


    }catch(exp){
        alert(exp);
    }
    return false;
}
