jQuery(function(){
	$('#assign-profiles .open-close').trigger( "click" );

	$("#checkedItems").each(function(){
        $(this).html("");
    });

    $('#generateShopperViewUrlBtn').unbind('click').bind('click', generateShopperViewUrl);
    $('#s-username').bind('change', function() { 
		if(isShopperViewUrl()) {
			$('#generateShopperViewUrlBtn').val('Update');
			noty({text: updateShopperView , type: 'warning'});
		}
    });
    $('#contentViewGroupToken').bind('change', function() {
		if(isShopperViewUrl()) {
			$('#generateShopperViewUrlBtn').val('Update');
			noty({text: updateShopperView, type: 'warning'});
		}
    });
    
    if(isShopperViewUrl()) {
    	$('#generateShopperViewUrlBtn').val('Update');
    }

});
function isShopperViewUrl() {
	var shopperViewOnly = $('#createUserForm .role :checkbox.shopper_view_only');
	var shopperViewUrl = $('#s-view-url').val();
	if(shopperViewOnly.length && shopperViewOnly[0].checked
			&& shopperViewUrl && shopperViewUrl.length > 0) {
		return true;
	}
	return false;
}
function filterUsers(userType) {
//	var formName = (userType == 'system')? '#filterUserForm': '#filterSystemUserForm';
//	var updateTable = (userType == 'system')? '#usersTable': '#systemUsersTable';
    try{
    	var updateTable = "";
    	if(userType == 'system') {
    		updateTable = "systemUserForm";
    	} else {
    		updateTable = "usersTableForm";
    	}
    	
        $.ajax({
            type:'POST', 
            url: filterUsersServiceUrl+'?userType='+userType,
            data: getFilterData(userType),
            dataType: 'text',
            success: function(response) {
            	$('#'+updateTable).html( response );
            	updateUserCount();
            	initTableUI();
                if(userType == 'system')
                    resetCheckBoxList('systemUserForm');
                else
                    resetCheckBoxList('usersTableForm');

            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}

function getFilterData(userType) {



	var formData = "";
	if(userType == 'system') {
        var gIDs=prepareQueryString('systemUserForm','userIds');
        gIDs+='&pageNo='+$("#systemUserForm input[name=pageNo]").val();
		formData = $('#systemUserRolesForm').serialize() + "&" + $('#filterSystemUserForm').serialize()+"&"+$('#searchSystemUserForm').serialize()+"&"+gIDs;//+$('#systemUserForm').serialize();
	} else {
        var gIDs=prepareQueryString('usersTableForm','userIds');
        gIDs+='&pageNo='+$("#usersTableForm input[name=pageNo]").val();
		formData = $('#normalUserRolesForm').serialize() + "&" + $('#searchUserForm').serialize()+"&"+$('#filterUserForm').serialize()+"&"+gIDs;//+$('#usersTableForm').serialize();
	}
	return formData;
}

function activateUsers(updateTable, userType, active) {


    var formName = (userType == 'system') ? 'systemUserForm': 'usersTableForm';
	if(!isAnyItemChecked(formName)) {
		alert("Please select an item");
		return false;
	}
    try{
        $.ajax({
            type:'POST', 
            url: activateUsersServiceUrl+'?userType='+userType+'&active='+active,
            data: getFilterData(userType),
            dataType: 'text',
            success: function(response) {
            	$("#"+updateTable).html( response );
            	initTableUI();
                resetCheckBoxList(formName);


            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}

function deleteNormalUsers() {
	deleteUsers('usersTableForm', 'normal');
}

function deleteSystemUsers() {
	deleteUsers('systemUserForm', 'system');
}

function deleteUsers(updateTable, userType) {



    try{
        $.ajax({
            type:'POST', 
            url: deleteUsersServiceUrl+'?userType='+userType,
            data: getFilterData(userType),
            dataType: 'text',
            success: function(response) {
            	$('#'+updateTable).html( response );
            	updateUserCount();
            	initTableUI();


            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}

function navigatePage(page, userType) {

    var updateTable = (userType == 'system')? 'systemUserForm': 'usersTableForm';

    var list=persistCheckboxStateOnPageChange(updateTable);

    try{
        $.ajax({
            type:'POST', 
            url: navigateUserPageServiceUrl+'?page='+page+'&userType='+userType,
            data: getFilterData(userType),
            dataType: 'text',
            success: function(response) {
            	$('#'+updateTable).html( response );
            	initTableUI();

                copyCheckedItemList(updateTable,list);
            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}
function createUser(formName, updateTable, userType) {
    try{
        $.ajax({
            type:'POST', 
            url: createUserServiceUrl+'?userType='+userType,
            data: $('#'+formName).serialize(),
            dataType: 'text',
            success: function(response) {
            	$('#'+updateTable).html( response );
            	updateUserCount();
            	initTableUI();
                if(window.location.href.indexOf('?')<0){
                    window.location=window.location+ '?language=' + $("#languagesList").val();
                }
                else{
                    window.location=window.location+ '&language=' + $("#languagesList").val();
                }
            }
        });
    }catch(exp){
      alert(exp);
    }
    return false;
}
function createNewUser(form) {
	var oldMaster = $("#createUserForm input[id='masterCheck']").get(0).checked;
	var oldAdmin = $("#createUserForm input[id='adminCheck']").get(0).checked;
	var newMaster = $("#createUserForm input.master").get(0).checked;
	var newAdmin = $("#createUserForm input.administrator").get(0).checked;
	appendAssignedProfileFields();

	if(oldMaster && !newMaster || oldAdmin && !newAdmin) {
    	if(oldMaster && !newMaster) {
    		$('p.role-confirm-message').html('Are you sure you wish to remove the Master Administrator role from this user?');
        } else {
        	$('p.role-confirm-message').html('Are you sure you wish to remove the Administrator role from this user?');
        }
    	$.colorbox({
            href:'#lightbox-confirm-role',
            inline:true,
            onComplete: function(){
                $('#lighboxRoleConfirmBtn').unbind('click').bind('click', function(){
                	form.submit();
                    $.colorbox.close();
                    return false;
                });
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    $('#cboxClose').trigger('click');
                    return false;
                });
            }
        });
	} else {
		form.submit();
	}
}
function createUserProfile() {
    try{
        $.ajax({
            type:'POST', 
            url: createUserProfileServiceUrl,
            data: $('#createProfileId').serialize() + '&' + getAssignedProfiles(),
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

function addUserProfiles() {
    var gIDs=prepareQueryString('add-profiles-form','assignedProfiles');
    var catalogIds=$("#catalogIds")
    var userId=$('#add-profiles-form').find("input[name=userId]").val();
        gIDs+='&userId='+userId;

    try{
        $.ajax({
            type:'POST', 
            url: assignUserProfilesServiceUrl,
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
function deleteUserProfilesConfirm() {
	if($("#createUserForm input[name='selectedProfileIds']:checked").length <= 0) {
		alert("Please select an item");
	} else {
		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', deleteUserProfiles);
	}
}
function deleteUserProfiles() {
    try{
    		var profileIds = '';
    		var concat = '';
    		
    		$("#createUserForm input[name='selectedProfileIds']:checked").each(function() {
    			profileIds = profileIds + concat + 'assignedProfiles='+$(this).val();
      		  	concat = '&';
      		  	
    			var elementId = 'tr-pr-'+$(this).val();
    			  $('#'+elementId).remove();
    		});
    		
    		var userId = $("#createUserForm input[name='userId']").val();
    		if(userId && userId.length>0) {
		        $.ajax({
		            type:'POST', 
		            url: deleteUserProfilesServiceUrl,
		            data: 'userId='+userId + '&' + profileIds,
		            dataType: 'text',
		            success: function(response) {
		            	$("#assignUserProfilesTable").html( response );
		            	initTableUI();
		            }
		        });
    		}
    }catch(exp){
      alert(exp);
    }
    return false;
}
//function deleteUserProfiles() {
//	if($("#createUserForm input[name='selectedProfileIds']:checked").length <= 0) {
//		alert("No Content View Selected");
//	} else {
//		$("#createUserForm input[name='selectedProfileIds']:checked").each(function() {
//			var elementId = 'tr-pr-'+$(this).val();
//			  $('#'+elementId).remove();
//		});
//	}
//	
//}


function getAssignedProfiles() {
	var profileIds = '';
	var concat = '';
	$("#createUserForm input[name='selectedProfileIds']").each(function() {
	  profileIds = profileIds + concat + 'assignedProfiles='+$(this).val();
	  concat = '&';
	});
	return profileIds;
}


function appendAssignedProfileFields() {
	$("#createUserForm input[name='selectedProfileIds']").each(function() {
		var input = $("<input>").attr("type", "hidden").attr("name", "assignedProfiles").val($(this).val());
		$("#createUserForm").append(input);
	});
}

function updateUserCount() {
	$('#outMasterAdminCount').html($('#txtMasterAdminCount').val());
	$('#outAdministratorCount').html($('#txtAdministratorCount').val());
	$('#outBuyerCount').html($('#txtBuyerCount').val());
	$('#outApproverCount').html($('#txtApproverCount').val());
	$('#outSupplierCount').html($('#txtSupplierCount').val());
	$('#outShopperViewOnlyCount').html($('#txtShopperViewOnlyCount').val());
	$('#outWebSearchUserCount').html($('#txtWebSearchUserCount').val());
	$('#outSystemShopperCount').html($('#txtSystemShopperCount').val());
	$('#outTotalNormalUserCount').html($('#txtTotalNormalCount').val());
	$('#outTotalSystemUserCount').html($('#txtTotalSystemCount').val());
}

function showAddProfiles(userId) {
	 try{
		 $.ajax({
			 type:'GET',
			 url: addUserProfilesServiceUrl,
			 data: getAssignedProfiles(),
			 success: function(response) {
				 if(response != ''){
					 $("#addUserProfilesSection").html( response );
					 $.colorbox({
						 href:'#add-profile',
						 inline:true,
						 onComplete: function(){
							 initTableUI();
							 $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
								 $.colorbox.close();
								 return false;
							 });
						 }
					 });
                     if(userType == 'system')
                         resetCheckBoxList('systemUserForm');
                     else
                         resetCheckBoxList('usersTableForm');
				 }	
			 }
		 });
	 }catch(exp){
		 alert(exp);
	 }
    return false;
}

function navigateProfilePage(page, userId) {
    try{
        var list=persistCheckboxStateOnPageChange('add-profiles-form');
        $.ajax({
            type:'POST', 
            url: navigateProfilesServiceUrl+'?page='+page+'&userId='+userId,
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
function usernameExists(username, userId) {
	var userExists = false;
	$.ajax({
        type:'POST',
        url: usernameServiceUrl,
        data: 'userId='+userId+'&username=' + encodeURIComponent(username),
        async: false,
        success: function(response) {
        	userExists = (response == 'true')? true : false;
        }
    });
	return userExists;
}

function resetUserPassword(userId) {
	var reset = false;
	$.ajax({
        type:'POST',
        url: resetPasswordServiceUrl,
        data:'userId=' + userId,
        async: false,
        success: function(response) {
        	reset = (response == 'true')? true : false;
        	 $.colorbox({
                 href:'#lightbox-reset-password',
                 inline:true,
                 onComplete: function(){
                	 $('#lightbox-reset-password .close, #lightbox-reset-password .btn-cancel , #lightbox-reset-password .btn-ok').unbind('click').bind('click', function(){

                         $.colorbox.close();
                         return false;
                     });
                 }
             });
        	
        }
    });
	return reset;
}

function setUserDeletionMessage(userType) {
	var profilesAssociated = false;
	
	if(userType == 'system') {
		$("#systemUserForm input[name='userIds']:checked").each(function() {
			var profilesAssigned = 'userprfs-'+$(this).val();
			  if($('#'+profilesAssigned).val()>0) {
				  profilesAssociated = true;
			  };
		});
	} else {
		$("#usersTableForm input[name='userIds']:checked").each(function() {
			var profilesAssigned = 'userprfs-'+$(this).val();
			  if($('#'+profilesAssigned).val()>0) {
				  profilesAssociated = true;
			  };
		});
	}
	
	if(profilesAssociated) {
		$('p.deletion-message').html('There are profiles associated to the user, are you sure you want to delete the selected user(s)?');
	} else {
		$('p.deletion-message').html('Please click "DELETE" to confirm that you would like to remove the selected item(s).');
	}
}

function verifyUserPassword(username, pass) {
	if(!username || !pass) {
		return false;
    }
    var userData = {
        username : username,
        password : pass

    };

	var verify = false;
	$.ajax({
        type:'POST',
        url: verifyUserPasswordServiceUrl,
        data:userData,
        async: false,
        success: function(response) {
        	verify = (response == 'true')? true : false;
        }
    });
	return verify;
}

function generateShopperViewUrl() {
	
	var cgroup = $('#contentViewGroupToken').val();
	var uname = $('#s-username').val();
	var shopperViewUrl = $('#shopperViewUrl').val() + '&UNAME=' + uname + '&SVIEW=on&CGROUPTOKEN=' + cgroup;
	$('#s-view-url').val(shopperViewUrl);
}