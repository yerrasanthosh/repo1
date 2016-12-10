var addContentMapping, editContentMapping;
function setupSystemDefinitionsSection() {
	$(document).keyup(function(e) {
		if(addContentMapping) {
			if (e.keyCode == 13) { addSystemDefinition(); }     // enter
			if (e.keyCode == 27) { cancelAdd(); }   				// esc
		} else if (editContentMapping) {
			if (e.keyCode == 13) { updateSystemDefinition(); }     // enter
			if (e.keyCode == 27) { cancelEdit(); }   				// esc
		}
	});
	$('#trNewRec').hide();
	$('div[id^="commMethod-"].communicationMethodSelectDiv').hide();
}

/* Section for SystemDefinitions functions */
function getSystemDefinitionsSection(){
    try{
        $.ajax({
            type:'GET',
            async: false,
            cache: false,
            url: systemDefinitionsUri,
            success: function(data, textStatus, jqXHR) {
            	$('#system_definition_section').html(jqXHR.responseText);
            	$('#trNewRec').hide();
            	$('div[id^="commMethod-"].communicationMethodSelectDiv').hide();
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function saveSystemDefinition(systemId, systemName, communicationMethod, uniqueSystemId, onSuccess){
	if(systemName == null || systemName==''){
		noty({text: systemNameCanNotBeChange, type: 'error'});
		return false;
	}
	
	if(communicationMethod == null || communicationMethod==''){
		return false;
	}
	
	return systemNameExists(systemId, systemName, function() {
			submitSystemDefinition(systemId, systemName, communicationMethod, uniqueSystemId, onSuccess);
		});
    return true;
}

function submitSystemDefinition(systemId, systemName, communicationMethod, uniqueSystemId, onSuccess) {
	try{
		$.ajax({
			type:'POST',
			async: false,
			cache: false,
			url: systemDefinitionsUri,
			data: { systemId: systemId, systemName: systemName, communicationMethod: communicationMethod, uniqueSystemId: uniqueSystemId},
			success: function(data, textStatus, jqXHR) {
            	$('#system_definition_section').html(jqXHR.responseText);
            	$('#trNewRec').hide();
            	$('div[id^="commMethod-"].communicationMethodSelectDiv').hide();
            	onSuccess();
            }
		});
    }catch(exp){
        alert(exp);
        return false;
    }
    return true;
}

function systemNameExists(systemId, systemName, onSuccess) {
	try{
		$.ajax({
	        type:'GET',
	        url: systemDefinitionsUri+'/name/'+systemName,
	        async: false,
	        cache: false,
	        success: function(response) {
	        	if(response && response != systemId){
	        		noty({text: recordAlreadyExist +systemName, type: 'error'});
	        		return;
	        	}else{
	        		onSuccess();
	        	}	
	        }
	    });
	}catch(exp){
        alert(exp);
        return false;
    }
	return true;
}

function addSystemDefinition(){
	var systemName = $('#txtSystemNameN').val();
	var communicationMethod = $('#txtCommunicationMethodN').val();

	saveSystemDefinition(null, systemName, communicationMethod, null, cancelAdd);
}
function updateSystemDefinition(){
	var selectedId = $('#systemId').val();
	var systemName = $('#txtSystemNameE-'+selectedId).val();
	var communicationMethod   =$('#txtCommunicationMethodE-'+selectedId).val();
	var uniqueSystemId   =$('#uniqueSysIdE-'+selectedId).val();
	
	saveSystemDefinition(selectedId, systemName, communicationMethod, uniqueSystemId, cancelEdit);
}


function addRow(){
	addContentMapping = true;
	var selectedRow = $('#systemId').val();
	if(selectedRow != null && selectedRow != ''){
		noty({text: saveTheFields, type: 'error'});
		return false;
		
	}
	$('#addRow').val('yes');
	
	$('#trNewRec').show();
	$('#addMappingBtn').hide();
	$('#removeMappingBtn').hide();
	$('#btnCancelN').show();
	$('#saveMappingBtn').show();
	
}

function editContentAccess(rowId){
	if(addContentMapping) {
		noty({text: saveTheFields, type: 'error'});
		return false;
	}
	if(editContentMapping) {
		updateSystemDefinition();
	}
	editContentMapping = true;
	
	var selectedRow = $('#systemId').val();
	var addRow = $('#addRow').val();
	

	if((addRow != null && addRow != '') || (selectedRow != null && selectedRow != '')){
		noty({text: saveTheFields, type: 'error'});
		return false;
		
	}
	
	$('#trNewRec').hide();
	$('#addMappingBtn').hide();
	$('#removeMappingBtn').hide();
	$('#saveMappingBtn').hide();	
	$('#saveMappingBtnE').show();
	
	$('#txtSystemNameN').val(null);
	$('#txtCommunicationMethodN').val(null);
	$('#btnCancelE').show();
	$('#sysName-'+rowId+'-V').hide();
	$('#commMethod-'+rowId+'-V').hide();
	$('#sysName-'+rowId+'-E').show();
	$('#commMethod-'+rowId+'-E').show();
	$('#systemId').val(rowId);
}

function cancelEdit(){
	editContentMapping = false;
	var rowId = $('#systemId').val();
	$('#sysName-'+rowId+'-V').show();
	$('#commMethod-'+rowId+'-V').show();
	$('#sysName-'+rowId+'-E').hide();
	$('#commMethod-'+rowId+'-E').hide();
	$('#saveMappingBtnE').hide();
	$('#addMappingBtn').show();
	$('#removeMappingBtn').show();
	$('#btnCancelE').hide();
	$('#systemId').val(null);
}


function cancelAdd(){
	addContentMapping = false;
	$('#saveMappingBtnE').hide();
	
	$('#systemId').val(null);
	$('#addRow').val(null);
	$('#trNewRec').hide();
	$('#addMappingBtn').show();
	$('#removeMappingBtn').show();
	$('#saveMappingBtn').hide();	
	$('#commMethod').val(null);
	$('#commMethod').prop('selectedIndex', -1);
	$('#txtSystemNameN').val(null);
	$('#btnCancelN').hide();
}

function removeContenAccessConfirm(){
	if($("#systemDefinitionForm .td-select input[name='systemIds']:checked").length<1) {
		alert('Please select an item');
		return;
	}
	deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', removeContenAccess);
}

function removeContenAccess(){
	var systemIds = $( "#systemDefinitionForm input[name='systemIds']:checked" ).map(function() {
	    return $( this ).val();
	  })
	  .get()
	  .join( "," );
	
	$.ajax({
        type:'DELETE',
        url : systemDefinitionsUri+'/'+systemIds,
        dataType : 'text',
        success: function(data, textStatus, jqXHR) {
        	$('#system_definition_section').html(jqXHR.responseText);
        	$('#trNewRec').hide();
        	$('div[id^="commMethod-"].communicationMethodSelectDiv').hide();
        },
        error : function(error){
            alert("Unexpected Error happened. Please try later.");
        }
    });
}
var newTabCount = 0;
function openCustomHelpContentBox(id){
    if(id){
    	
    	var content = $('#help-content-'+id).html();
    	var title = $('#help-title-'+id).text();
    	$('#helpContentId').val(id);
    	$('#helpContentValue').val(content);
        $('#customHelpContentText').val(content);
        $('#helpContentTitleHeader').text(title);
        $('#helpContentTitle').val(title);
    } else {
    	$('#helpContentId').val('');
    	$('#helpContentValue').val('');
    	$('#customHelpContentText').val('');
    	var title = strings['company.help.tab'] + " " + ++newTabCount;
    	$('#helpContentTitleHeader').text(title);
        $('#helpContentTitle').val(title);
    }

    $.colorbox({
        href:'#customHelpContentLightBox',
        inline:true,
        onComplete: function(){
        	CKEDITOR.replace( 'customHelpContentText' , configAllToolbars );
            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                $.colorbox.close();
                return false;
            });
        },
        onClosed: function(){
        	CKEDITOR.instances['customHelpContentText'].destroy();
    	}
    });
}
function editHelpContentTitle() {
	$("#customHelpContentTitleDiv").hide();
	$("#customHelpContentTitleEditDiv").show();
	return false;
}

function updateHelpContentTitle() {
	$('#helpContentTitleHeader').text($('#helpContentTitle').val());
	$("#customHelpContentTitleEditDiv").hide();
	$("#customHelpContentTitleDiv").show();
	
	return false;
}
function addCustomHelpContent(){
    try{
        
        var id = $('#helpContentId').val();
        var title = $('#helpContentTitle').val();
        var content = CKEDITOR.instances['customHelpContentText'].getData();
        var formData = { id: id, title: title, content: content};
        $.ajax({
            type: 'PUT',
            url: addUpdateCompanySettingsTabURI,
            data: JSON.stringify(formData),
            dataType: 'text',
            contentType: 'application/json',
            success: function(response) {
                if(response != ''){
                    $("#customHelpContentDiv").html( response );
                    $.colorbox.close();
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function removeHelpTabConfirm(id){
	deleteConfirmAction(strings['commons.delete.confirmation'], function(){removeHelpTab(id);});
}

function removeHelpTab(id){
	$.ajax({
        type:'DELETE',
        url : addUpdateCompanySettingsTabURI+'/'+id,
        dataType : 'text',
        success: function(response) {
        	if(response != ''){
                $("#customHelpContentDiv").html( response );
            }
        },
        error : function(error){
            alert("Unexpected Error happened. Please try later.");
        }
    });
}
