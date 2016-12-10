var counter = 0;

var config = { toolbar :
    [
        { name: 'basicstyles', items : [ 'Font','FontSize','Bold','Italic','Underline' ] }
    ]};

function toggleCKEditor(){
    if(document.getElementById("sendNotification").checked){
        if($('#notificationBody').val().length == 0){
            $('#notificationBody').val("Hello "+$('#sup-first-name').val()+" "+$('#sup-last-name').val()+",<br/>"+"You have been invited to the "+$('#parentCompanyName').val()+" Marketplace.");
        }
        $('#sendEmailDiv').show();
        try{
            CKEDITOR.replace( 'notificationBody', config );
        } catch(err){}

    } else {
        try{
            $('#notificationBody').val(CKEDITOR.instances['notificationBody'].getData());
            CKEDITOR.instances['notificationBody'].destroy();
        } catch(err){}
        $('#notificationBody').hide();
        $('#sendEmailDiv').hide();
    }
}

function openNewUserPopup(){
    $.colorbox({
        href:'#create-supplier-user',
        open :true,
        inline:true,
	        onComplete: function(){
	            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    if(document.getElementById("sendNotification").checked){
                        CKEDITOR.instances['notificationBody'].destroy();
                    }
                    $('#sendNotification').prop('checked', false);
                    $("#notiLabel").removeClass('ui-state-active');
//                    document.getElementById("").checked = ;
                    $('#notificationBody').hide();
                    $('#notificationBody').val("");
                    $('#sendEmailDiv').hide();
					initSupUserPopup();
	            	$.colorbox.close();	
	                return false;
	            });
	        }
        });
}

function openEditUser(firstName, lastName, email, supplierId, userId, pass, invited){
		$("#suppAdminTitle").html('Edit Supplier User');
		$("#sup-first-name").val(firstName);
		$("#sup-last-name").val(lastName);
		$("#sup-e-mail").val(email);
		$("#supplierid").val(supplierId);
		$("#userId").val(userId);
        $("#pass").val(pass);

        
	    if(invited && invited == "Invite Supplier"){
	    	$('#sendNotification').prop('checked', true);
	    	$("#notiLabel").addClass('ui-state-active');
	    	toggleCKEditor();
	    }
        $.colorbox({
        href:'#create-supplier-user',
        open :true,
        inline:true,
	        onComplete: function(){
	            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
	                $('#sup-first-name').val('');
	                $('#sup-last-name').val('');
					$("#sup-e-mail").val('');
					$("#supplierid").val('');
					$("#userId").val('');
                    $("#pass").val('');
					$("#suppAdminTitle").html('Create Supplier User');
                    if(document.getElementById("sendNotification").checked){
                        CKEDITOR.instances['notificationBody'].destroy();
                        $('#sendNotification').prop('checked', false);
                        $("#notiLabel").removeClass('ui-state-active');
                        $('#notificationBody').hide();
                        $('#notificationBody').val("");
                    }
                    $('#sendEmailDiv').hide();
                    $.colorbox.close();
	                return false;
	            });
	        }
        });
    
}

function addNewRowToTable(tableId){

    if ($('#'+tableId+ ' tr').eq(1).length<=0){
        $('#'+tableId+' > tbody:last').append('<tr class="edit"><td class="td-select"><input type="checkbox" class="" name="supplierMappingIds" id="supplierMappingIds" value="" onclick=""/></td><td><div><span><a href="#"></a></span><input type="text" id="supplier_Id" style="width:215px" name="supplier_Id" value=""><em></em></div></td><td><div><span></span><input type="text" id="systemId" style="width:215px;float: none;" name="systemId" value=""><em></em></div></td></tr>');
    } else {
        $('#'+tableId+ ' tr').eq(1).before('<tr class="edit"><td class="td-select"><input type="checkbox" class="" name="supplierMappingIds" id="supplierMappingIds" value="" onclick=""/></td><td><div><span><a href="#"></a></span><input type="text" id="supplier_Id" style="width:215px" name="supplier_Id" value=""><em></em></div></td><td><div><span></span><input type="text" id="systemId" style="width:215px;float: none;" name="systemId" value=""><em></em></div></td></tr>');
    }
    
    
	$('#addModeOn').val('true');
    $('#supplier-mapping-cancel-save-btn').show();
    $('#supplier-mapping-save-btn').show();
    $('#supplier-mapping-remove-btn').hide();
    
    counter++;
}

function editTableRow(tableId , rowId){
	
	var vendorId = $('#vend-'+rowId).val();
	var systemId = $('#sysid-'+rowId).val();
	var editMode = $('#editModeOn').val();
	
	var addMode = $('#addModeOn').val();
	
	if(editMode == 'true'){
		$('#errorMsgDiv').show();
		return;
	}
	
	if(addMode == 'true'){
		$('#errorMsgDiv').show();
		return;
	}
	
	$('#editRowId').val('sysid-'+rowId);
	$('#editModeOn').val('true');
	$('#supplier-mapping-save-btn').hide();
	$('#addMappingBtn').hide();
	$('#supplier-mapping-update-btn').show();
	$('#supplier-mapping-cancel-btn').show();
	$('#supplier-mapping-remove-btn').hide();
	 //$('#supp-'+rowId).trigger('click');
    if ($('#'+tableId+ ' tr').eq(1).length<=0){
        $('#'+tableId+' > tbody:last').append('<tr class="edit"><td class="td-select"></td><td><div><span><a href="#"></a></span><input type="text" id="supplier_Id" style="width:215px" name="supplier_Id" value=""><em></em></div></td><td><div><span></span><input type="text" id="systemId" style="width:215px;float: none;" name="systemId" value=""><em></em></div></td></tr>');
    } else {
        $('#'+tableId+ ' tr').eq(1).before('<tr class="edit"><td class="td-select"><input type="hidden" class="" name="supplierMappingIds" id="supplierMappingIds" value="'+rowId+'" onclick=""/></td><td><div><span><a href="#"></a></span><input type="text" id="supplier_Id" style="width:215px;float: none;" name="supplier_Id" value="'+vendorId+'"><em></em></div></td><td><div><span></span><input type="text" id="systemId" style="width:215px" name="systemId" value="'+systemId+'"><em></em></div></td></tr>');
    }
    counter++;

}
function validateSupplierContentView(){

    var profileName = $('#profile-name').val();
    profileName = $.trim(profileName);
    $('#profile-name').val(profileName);

    if(profileName == ''){
        $('span#createProfileReplyDiv').html("<br />You must enter a Valid Content View Name");
        return false;
    }else if(profileExists('', profileName)){
        $('span#createProfileReplyDiv').html("<br />Content View with name of '" + profileName + "' already exists");
        return false;
    }
    $('span#createProfileReplyDiv').html('');
    return true;
}

function cancelCreation() {
    try{
        $.ajax({
            type:'POST',
            url: 'canclemappingupdate',
            data: $('#supplier-mapping-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    var responseArray = jqXHR.responseText.split('@@@');
                    $('#supplier_mapping_table').empty();
                    $('#supplier_mapping_table').append(responseArray[0]);

	
	
					$('#supplier-mapping-save-btn').hide();
					$('#addMappingBtn').show();
					$('#supplier-mapping-cancel-save-btn').hide();
					$('#supplier-mapping-remove-btn').show();
					$('#errorMsgDiv').hide();
					
					initTableUI();
                }
            }
        });
    }catch(exp){
        alert(exp)
    }
}


function cancleUpdate() {
    try{
        $.ajax({
            type:'POST',
            url: 'canclemappingupdate',
            data: $('#supplier-mapping-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    var responseArray = jqXHR.responseText.split('@@@');
                    $('#supplier_mapping_table').empty();
                    $('#supplier_mapping_table').append(responseArray[0]);

                    $('#supplier-mapping-remove-btn').show();
                    $('#addMappingBtn').show();

					$('#supplier-mapping-update-btn').hide();
					$('#supplier-mapping-cancel-btn').hide();
					
					$('#errorMsgDiv').hide();
					
					initTableUI();
                }
            }
        });
    }catch(exp){
        alert(exp)
    }
}

function updateSupplierMapping() {
    try{
        $.ajax({
            type:'POST',
            url: 'updatesuppliermappings',
            data: $('#supplier-mapping-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    var responseArray = jqXHR.responseText.split('@@@');
                    $('#supplier_mapping_table').empty();
                    $('#supplier_mapping_table').append(responseArray[0]);
                    $('#supplier-mapping-save-btn').hide();
                    $('#supplier-mapping-remove-btn').show();
                    $('#addMappingBtn').show();
					$('#supplier-mapping-update-btn').hide();
					$('#supplier-mapping-cancel-btn').hide();
					$('#errorMsgDiv').hide();
					initTableUI();
                }
            }
        });
    }catch(exp){
        alert(exp)
    }
}

function findDuplicateSystemIds() {
	var editMode = $('#editModeOn').val();
	var editRowId = $('#editRowId').val();
	
	var systemIds = [];
	$( "#supplier_mapping_table input[name='systemId'], " +
		"#supplier_mapping_table input[id^='sysid-']" ).each(function() {
	        if(editMode != 'true' || $(this).attr('id') != editRowId ) {
	        	systemIds.push($(this).val());
	        }
	    });

	var uniqueSystemIds = [];
	var duplicates = [];
	$.each(systemIds, function(i, e) {
		if ($.inArray(e.toLowerCase(), uniqueSystemIds) == -1) { 
			uniqueSystemIds.push(e.toLowerCase()); 
		} else {
			duplicates.push(e);
		}
	});
	return duplicates;
}
function validateSystemIds(onSuccess) {
	var systemIds = $( "#supplier_mapping_table input[name='systemId']" ).map(function() {
	    return $.trim($( this ).val());
	  })
	  .get()
	  .join( "," );
    
	$.ajax({
        type:'GET',
        url: supplierMappingsServiceUrl+'/validate/'+systemIds,
        async: false,
        cache: false,
        success: function(response, textStatus, jqXHR) {
        	if(response && response.length > 0){
        		noty({text: uniqueSystemDoesNotExist +response, type: 'error'});
        	}else{
        		onSuccess();
        	}	
        }
    });
}
function profileExists(profileId, profileName) {
    var profileExists = false;
    $.ajax({
        type:'POST',
        url: profileExistsServiceUrl,
        data:'profileId=' + profileId + '&profileName=' + profileName,
        async: false,
        success: function(response) {
            profileExists = (response == 'true')? true : false;
        }
    });
    return profileExists;
}

function createSupplierContentViews() {
    try{
    	var supplierId = $("#supplierContentView input[name='suplierID']").val();
    	var proName = $("#createSupplierContentViewId input[name='profileName']").val();
    	var proDes = $("#createSupplierContentViewId input[name='profileDesc']").val();
        $.ajax({
            type:'POST', 
            url: supplierContentViewServiceUrl,
            data: $('#createSupplierContentViewId').serialize() + '&' + getAssignedSupplierProfiles(supplierId),
            dataType: 'text',
            success: function(response) {
            	$("#assignUserProfilesTable").html( response );
            	clearProfileForm();
            	initTableUI();
            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}


function showSupplierProfiles(supplierId) {
	try {
		$.ajax({
			type : 'GET',
			url : getUnassignedContentViewsUrl,
			data :getAssignedSupplierProfiles(supplierId),
			success : function(response) {
				if (response != '') {
					$("#addUserProfilesSection").html(response);
					$.colorbox({
						href : '#add-profile',
						inline : true,
						onComplete : function() {
							initTableUI();
							$('#cboxContent .close,#cboxContent .btn-cancel')
									.unbind('click').bind('click', function() {
										$.colorbox.close();
										return false;
									});
						}
					});
				}
			}
		});
	} catch (exp) {
		alert(exp);
	}
	return false;
}

function navigateProfilePage(page, supplierId) {
    try{
        var list=persistCheckboxStateOnPageChange('add-profiles-form');
        var supplierID = $('#supplierId').val();
    	console.log(supplierID);
        $.ajax({
            type:'POST', 
            url: navigateSupplierContentViewUrl+'?page='+page+'&supplierId='+supplierID,
            data: getAssignedProfiles(),
            dataType: 'text',
            success: function(response) {
            	$('#addUserProfilesSection').html( response );
            	initTableUI();

                copyCheckedItemList('add-profiles-form',list);
            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}


function addSupplierContentView() {
    var gIDs=prepareQueryString('add-profiles-form','assignedProfiles');
    var supplierID = $('#supplierId').val();
        gIDs+='&supplierId='+supplierID;

    try{
        $.ajax({
            type:'POST', 
            url: assignSupplierContentViewServiceUrl,
            data: gIDs + '&' + getAssignedProfiles(),
            dataType: 'text',
            success: function(response) {
            	$("#assignUserProfilesTable").html( response );
            	initTableUI();
                $("#checkedItems").html('')

            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}

function deleteSupplierProfilesConfirm() {
	if($("#supplierContentView input[name='selectedProfileIds']:checked").length <= 0) {
		alert("Please select an item");
	} else {
		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', deleteSupplierProfiles);
	}
}
function deleteSupplierProfiles() {
    try{
    		
    		var supplierId = $("#supplierContentView input[name='suplierID']").val();
    		var profileIds = '';
    		var concat = '';
    		$("#supplierContentView input[name='selectedProfileIds']:checked").each(function() {
    			profileIds = profileIds + concat + 'assignedProfiles='+$(this).val();
      		  	concat = '&';
    			var elementId = 'tr-pr-'+$(this).val();
    			  $('#'+elementId).remove();
    		});
    		  profileIds = profileIds + concat + '&supplierId=' + supplierId;
		        $.ajax({
		            type:'POST', 
		            url: deleteSupplierContentViewServiceUrl,
		            data :profileIds,
		            dataType: 'text',
		            success: function(response) {
		            	$("#assignUserProfilesTable").html( response );
		            	initTableUI();
		            }
		        });
    }catch(exp){
      alert(exp);
    }
    return false;
}


	function getAssignedProfiles() {
		var profileIds = '';
		var concat = '';
		$("#supplierContentView input[name='selectedProfileIds']").each(function() {
		  profileIds = profileIds + concat + 'assignedProfiles='+$(this).val();
		  concat = '&';
		});
		return profileIds;
	}
	function getAssignedSupplierProfiles(supplierId) {
		var profileIds = '';
		var concat = '';
		$("#supplierContentView input[name='selectedProfileIds']").each(function() {
			profileIds = profileIds + concat + 'assignedProfiles=' + $(this).val();
			concat = '&';
		});
		
		profileIds = profileIds + concat + '&supplierId=' + supplierId;
		console.log(profileIds);
		return profileIds;
	}





function validateAndPostFormData() {
	validateFormData(postFormData);
}
function validateAndUpdateFormData() {
	validateFormData(updateSupplierMapping);
}
function validateFormData(onSuccess) {
	
	var comName = $("#companyName").val();
	if(comName){
		$("#companyNameForMapping").val(comName);
	}else{
		alert("Company name is a required filed to add mappings");
		return false;
	}
	
	var emptySystemName = false;
	var emptyUniqueSystemId = false;
	$( "#supplier_mapping_table input[name='supplier_Id']" ).each(function() {
        if($.trim($(this).val()).length == 0 ) {
        	emptySystemName = true;
        }
    });
	$( "#supplier_mapping_table input[name='systemId']" ).each(function() {
        if($.trim($(this).val()).length == 0 ) {
        	emptyUniqueSystemId = true;
        }
    });
	
	var duplicates = findDuplicateSystemIds();
	if(emptyUniqueSystemId) {
		noty({text: uniqueSystemIdCanNotBeEmpty, type: 'error'});
	} else if(emptySystemName) {
		noty({text: supplierIdEmpty , type: 'error'});
	} else if(duplicates.length > 0) {
		noty({text: uniqueSystemMapping +duplicates, type: 'error'});
	} else {
		validateSystemIds(onSuccess);
	}
}

function postFormData() {
    try{
        $.ajax({
            type:'POST',
            url: saveMappingsSupplierServiceUrl,
            data: $('#supplier-mapping-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {

                if(jqXHR.responseText != ''){

                    var responseArray = jqXHR.responseText.split('@@@');
//                    gPagesAvailable = responseArray[1];
//                    gPageNumber = responseArray[2];
//                    gPageSize = responseArray[3];
//                    gTotalRecords = responseArray[4];

                    $('#supplier_mapping_table').empty();
                    $('#supplier_mapping_table').append(responseArray[0]);
                    $('#supplier-mapping-remove-btn').show();
                    $('#supplier-mapping-cancel-save-btn').hide();
                    $('#supplier-mapping-save-btn').hide();
                    $('#errorMsgDiv').hide();
					initTableUI();
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
}
function deleteSupplierMappingsConfirm() {
	name="supplierMappingIds"
	if($('input[name="supplierMappingIds"]:checked').length <= 0){
		alert('Please select an item');
	} else {
		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', deleteFormData);
	}
	return false;
}
function deleteFormData() {
    try{
    
    
    	var editMode = $('#editModeOn').val();
    	if(editMode == 'true'){
			$('#errorMsgDiv').show();
			return;
    	}
    	
        $.ajax({
            type:'POST',
            url: deleteMappingsSupplierServiceUrl,
            data: $('#supplier-mapping-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    var responseArray = jqXHR.responseText.split('@@@');
                    $('#supplier_mapping_table').empty();
                    $('#supplier_mapping_table').append(jqXHR.responseText);
                    initTableUI();
                }
            }
        });
    }catch(exp){
        alert(exp)
    }
}

function onClickingCheckbox() {
//    alert("test");
//    if(this.checked){
//        $(this).next().addClass("ui-state-active");$(this).closest("tr").addClass("active-tr");
//    }else {
//        $(this).next().removeClass("ui-state-active");$(this).closest("tr").removeClass("active-tr");
//    }
}

