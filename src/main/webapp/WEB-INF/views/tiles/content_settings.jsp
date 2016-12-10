<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
    import="com.vroozi.customerui.util.ViewHelper,
            com.vroozi.customerui.catalog.model.CatalogCustomField,
            com.vroozi.customerui.acl.model.Permission,
            com.vroozi.customerui.util.Consts"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript" src="res/js/ui.dropdownchecklist-1.4-min.js"></script>

<script type="text/javascript" src="res/js/content_settings.js"></script>

<script type="text/javascript">

    var addAnnouncementURI = '<c:url value="/addAnnouncement" />';
    var deleteAnnouncementURI = '<c:url value="/deleteAnnouncement" />';

    var addInformationURI = '<c:url value="/information" />';
    var deleteInformationURI = '<c:url value="/information/" />';

    var addMessageURI = '<c:url value="/addMessage" />';
    var deleteMessageURI = '<c:url value="/deleteMessage" />';

    var updateCompanySettingsURI = '<c:url value="/updateCompanySettings" />';
    var addUpdateCompanySettingsTabURI = '<c:url value="/companysettings/tabs" />';
    var updateCompanyWorkFlowURI = '<c:url value="/updateCompanyWorkFlow" />';
	var welcomeTextURI = '<c:url value="/welcomeSettings" />';
    var addCompanySettingURI = '<c:url value="/addCompanySetting" />';
    var getCompanySettingURI = '<c:url value="/getCompanySetting" />';
    var companySettingsURI = '<c:url value="/company-settings" />';

    var removeWelcomeImageURI = '<c:url value="/removeWelcomeImage" />';

    var systemDefinitionsUri =  '<c:url value="/system-definitions" />';
    var updateSharedListMappingURI = '<c:url value="/update-shared-list-mapping" />';
    
    var strings = new Array();
    strings['commons.delete.confirmation'] = '<spring:message code="com.adminui.common_overlay.DeletionConfirmationMsg" text="default text" />';
    strings['company.help.tab'] = '<spring:message code="com.adminui.content_settings.HelpTab" text="default text" />';

    var configAllToolbars = {
    		language: '${pageContext.response.locale}',
    toolbar:[
        { name: 'document', items : [ 'Source','-' ] },
        { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
        { name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
        { name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton',
            'HiddenField' ] },
        '/',
        { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
        { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',
            '-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
        { name: 'links', items : [ 'Link','Unlink','Anchor' ] },
        { name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },
        '/',
        { name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
        { name: 'colors', items : [ 'TextColor','BGColor' ] },
        { name: 'tools', items : [ 'ShowBlocks','-','About' ] }
    ]
    };

    var config = { language: '${pageContext.response.locale}', toolbar :
            [
                { name: 'basicstyles', items : [ 'Font','FontSize','Bold','Italic','Underline' ] },
                { name: 'links', items : [ 'Link','Unlink'] }
            ]};
    config.fontSize_sizes = '8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;';

    var config1 = { language: '${pageContext.response.locale}', toolbar :
        [
         { name: 'basicstyles', items : [ 'Font','FontSize','Bold','Italic','Underline' ] },
         { name: 'links', items : [ 'Link','Unlink'] }
     ]};
    config1.fontSize_sizes = '8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;';


    $(document).ready(function() {
    	function notInCatalogEnabled() { 
    		return $("#notInCatalog").val()=="true"; 
    	}
    	jQuery.validator.addMethod("duplicateProductMapping", function(value, element, params) {
    		if($("#notInCatalog").val()!="true") {
    			return true;
    		}
            var freeTextIdentifier = $('#contentForm select[name=freeTextIdentifier]').val();
            return freeTextIdentifier != value;
        }, function() {return 'The selected field is already mapped.'; });

    	jQuery.validator.addMethod("duplicateLineItemMapping", function(value, element) {
    		if($("#notInCatalog").val()!="true") {
                return true;
            }
            var limitItemIdentifier = $('#contentForm select[name=limitItemIdentifier]').val();
            var deliveryDateFrom = $('#contentForm select[name=deliveryDateFrom]').val();
            if(element.id == 'deliveryDateFrom') {
            	return limitItemIdentifier != value;
            } else {
            	return limitItemIdentifier != value && deliveryDateFrom != value;
            }
        }, function() {return 'The selected field is already mapped.'; });
    	
    	$("#contentForm").validate({
    		rules: {
    			requiredDate: "duplicateProductMapping",
                deliveryDateFrom: "duplicateLineItemMapping",
                overallValue: "duplicateLineItemMapping",
                sharedListExpiryDays: {
                	required:true,
                	digits: true
                }
            },
            messages: {
            	sharedListExpiryDays: "Please a number between 1 and 365",
            }
        });
        var options = {
        		beforeSubmit: function () {
                    return $("#contentForm").valid();
                },
                success:       showImageResponse  // post-submit callback
            //  uploadProgress: uploadProgress
        };
        var welcomeOptions = {
            success:       showWelcomeImageResponse  // post-submit callback
            //  uploadProgress: uploadProgress
        };
        $('#contentForm').ajaxForm(options);
        $('#welcomeImageForm').ajaxForm(welcomeOptions);
        $("#announceUserList").dropdownchecklist( {icon: {placement: 'right'}} );
        $("#infoContentGroupList").dropdownchecklist( {icon: {placement: 'right'}} );
        $("#messageUserList").dropdownchecklist( {icon: {placement: 'right'}} );

        bindSelectUnselectAll("announceUserList");
        bindSelectUnselectAll("infoContentGroupList");
        
        setupSystemDefinitionsSection();
		

        var sharedListAutoExpire = $("#sharedListAutoExpire");
        sharedListAutoExpire.bind('change', function(){
        	if(sharedListAutoExpire.prop( "checked" ) ) {
        		$("#sharedListExpiryDays").prop('disabled', false);
        	} else {
        		$("#sharedListExpiryDays").prop('disabled', true);
        	}
        });
        
//        $('#companyImage').bind('change', function() {
//
//            var iSize = 0;
//            if($.browser.msie)
//            {
//                var objFSO = new ActiveXObject("Scripting.FileSystemObject");
//                var sPath = $("#companyImage")[0].value;
//                var objFile = objFSO.getFile(sPath);
//                iSize = objFile.size;
//            }
//            else{
//                iSize = this.files[0].size;
//            }
//
//           if (iSize > 153600){
//                alert('File size should not exceed 150kb.');
//            }
//        });

//        $('#companyNoImage').bind('change', function() {
//            if(this.files[0].size > 153600){
//                alert('File size should not exceed 150kb.');
//            }
//        });
    });

    function bindSelectUnselectAll(selectId) {
        var selectedAll = false;
        var unSelectedAll = false;

        $("#"+selectId).bind('change', function() {
            if($("#"+selectId).children()[0].selected){
                selectedAll = true;
                unSelectedAll = false;
            }else if(selectedAll && !($("#"+selectId).children()[0].selected)){
                selectedAll = false;
                unSelectedAll = true;
            }else{
                selectedAll = false;
                unSelectedAll = false;
            }

            $("#"+selectId+" option:not([disabled])").each(function(){
                if(selectedAll) {
                    $(this).attr('selected', 'selected');
                }else if(unSelectedAll){
                    $(this).removeAttr('selected');
                }
            });
            $(this).dropdownchecklist("refresh");
        });
    }
    
    function openMessageDiv(val){
        $(".sopper-select").each(function(index) {
            $(this).removeAttr("selected");
        });
        if(val!=null){
            $('#messageAreaLightBox').val(val.innerHTML);
            $('#messageId').val(val.id);
            var mailingList = $('#msc-'+val.id).val();
            if(mailingList != null){
            var strArray= mailingList.substring(1,mailingList.length-1).split(", ");
            for(i=0; i<strArray.length; i++) {
                $("#bid-"+strArray[i]).attr("selected", "selected");
            }
        }

        }
        $("#messageUserList").dropdownchecklist("refresh");

        $.colorbox({
            href:'#messageLightBox',
            inline:true,
            onComplete: function(){
            	CKEDITOR.replace( 'messageAreaLightBox', config );
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    CKEDITOR.instances['messageAreaLightBox'].destroy();
                    $.colorbox.close();
                    $('#messageAreaLightBox').val('');
                    $('#messageId').val('');
                    return false;
                });
            }
        });
    }


    function openAnnouncementDiv(id){
    	$(".sopper-select").each(function(index) {
            $(this).removeAttr("selected");
         });
        if(id!=null){
            $('#announcementAreaLightBox').val($("#cgval-"+id).val());
            $('#announceId').val(id);
            var mailingList = $('#slc-'+id).val();
            var strArray= mailingList.substring(1,mailingList.length-1).split(", ");
            for(i=0; i<strArray.length; i++) {
                $("#aid-"+strArray[i]).attr("selected", "selected");
           }
        }
        $("#announceUserList").dropdownchecklist("refresh");
        
        $.colorbox({
            href:'#announcementLightBox',
            inline:true,
            onComplete: function(){
            	CKEDITOR.replace( 'announcementAreaLightBox', config );
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    CKEDITOR.instances['announcementAreaLightBox'].destroy();
                    $.colorbox.close();
                    $('#announcementAreaLightBox').val('');
                    $('#announceId').val('');
                    return false;
                });
            }
        });
//        if()
        if($("#cat-"+id).val()){
            $("#type").val($("#cat-"+id).val());
        } else {
            $("#type").val("none");
        }

        jcf.customForms.replaceAll();
        jcf.customForms.refreshAll();
//        $("#type").val();
    }

    function openInformationDiv(id){
        $(".info-select").each(function(index) {
            $(this).removeAttr("selected");
         });
        $("#infoContentGroupList option").removeAttr("disabled");
        var strArray = [];
        if(id != null){
            $('#informationTextArea').val($('#infoval-'+id).val());
            $('#informationId').val(id);
            var mailingList = $('#infocg-'+id).val();
            strArray= mailingList.substring(1,mailingList.length-1).split(", ");
            for(var i = 0; i<strArray.length; i++) {
                $("#cgid-"+strArray[i]).attr("selected", "selected");
            }
        }
        var assigned = $('#infoAssignedGroups').val();
        if(assigned.length > 2) {
        	assigned = assigned.substr(1, assigned.length-2);
        	var assignedArr = assigned.split(', ');
            for(var i = 0; i < assignedArr.length; i++) {
            	if($.inArray( assignedArr[i], strArray ) == -1) {
                    $("#cgid-"+assignedArr[i]).attr("disabled", true);
            	}
            }
        }
        $("#infoContentGroupList").dropdownchecklist("refresh");
        
        $.colorbox({
            href:'#informationLightBox',
            inline:true,
            onComplete: function(){
                CKEDITOR.replace( 'informationTextArea', config1 );
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    CKEDITOR.instances['informationTextArea'].destroy();
                    $.colorbox.close();
                    $('#informationTextArea').val('');
                    $('#informationId').val('');
                    return false;
                });
            }
        });

        jcf.customForms.replaceAll();
        jcf.customForms.refreshAll();
//        $("#type").val();
    }
    
    function openFaqLightBox(val){
        if(val!=null){
            $('#faqAreaLightBox').val(val.innerHTML);
        }
        
        $.colorbox({
            href:'#faqLightBox',
            inline:true,
            onComplete: function(){
            	CKEDITOR.replace( 'faqAreaLightBox' , configAllToolbars );
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    $.colorbox.close();
                    return false;
                });
            },
            onClosed: function(){
            	CKEDITOR.instances['faqAreaLightBox'].destroy();
        	}
        });
    }

    function openPolicyLightBox(val){
        if(val!=null){
            $('#policyAreaLightBox').val(val.innerHTML);
        }

        $.colorbox({
            href:'#policyLightBox',
            inline:true,
            onComplete: function(){
            	CKEDITOR.replace( 'policyAreaLightBox' , configAllToolbars );
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    $.colorbox.close();
                    return false;
                });
            },
            onClosed: function(){
            	CKEDITOR.instances['policyAreaLightBox'].destroy();
        	}
        });
    }

    function openContactLightBox(val){
        if(val!=null){
            $('#contactAreaLightBox').val(val.innerHTML);
        }

        $.colorbox({
            href:'#contactLightBox',
            inline:true,
            onComplete: function(){
            	CKEDITOR.replace( 'contactAreaLightBox' , configAllToolbars );
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    $.colorbox.close();
                    return false;
                });
            },
            onClosed: function(){
            	CKEDITOR.instances['contactAreaLightBox'].destroy();
        	}
        });
    }

    function updateWorkFlow(){
        try{
            $('#workCompId').val(document.getElementById('companySetId').value);
            $.ajax({
                type:'POST',
                url: updateCompanyWorkFlowURI,
                data: $('#workFlowForm').serialize(),
                dataType: 'text',
                success: function(response) {
                    if(response != ''){
                        $("#workFlowDiv").html( response );
                        initTableUI();
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function removeWelcomeIconImage(){
        try{
            $.ajax({
                type:'POST',
                url: removeWelcomeImageURI,
                dataType: 'text',
                success: function(response) {
//                    $('input[type=file]').each(function(){
//                        if($('#companyWelcomeImage').val() != ''){
//                            $('#companyWelcomeImage').after($('#fieldWelcomeIcon').clone(true)).remove();
//                            $('#companyWelcomeImage').remove(":contains('.')");
//                        }
//                    });
                    $('#companyWelcomeImage').val('');
                    jcf.customForms.replaceAll();
                    jcf.customForms.refreshAll();
                    $("#fieldWelcomeIcon").attr('src', "image/"+response);
                    $("#fieldWelcomeIcon").hide();
                    $("#iconWelcomeDiv").hide();
                    $("#browseWelcomeDiv").show();
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function addFaq(){
        try{
            $('#faq').val(CKEDITOR.instances['faqAreaLightBox'].getData());
            $('#faqCompId').val(document.getElementById('companySetId').value);
            $('#colName').val('faq');
            $('#faqAreaLightBox').val('');
            $.ajax({
                type:'POST',
                url: updateCompanySettingsURI,
                data: $('#faqForm').serialize(),
                dataType: 'text',
                success: function(response) {
                    if(response != ''){
                        $("#faqDivComplete").html( response );
                        $.colorbox.close();
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function addPolicy(){
        try{
            $('#policies').val(CKEDITOR.instances['policyAreaLightBox'].getData());
            $('#policyCompId').val(document.getElementById('companySetId').value);
            $('#policyColName').val('policy');
            $('#policyAreaLightBox').val('');
            $.ajax({
                type:'POST',
                url: updateCompanySettingsURI,
                data: $('#policyForm').serialize(),
                dataType: 'text',
                success: function(response) {
                    if(response != ''){
                        $("#policyDivComplete").html( response );
                        $.colorbox.close();
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function addContact(){
        try{
            $('#contact').val(CKEDITOR.instances['contactAreaLightBox'].getData());
            $('#contactCompId').val(document.getElementById('companySetId').value);
            $('#contactColName').val('contactUs');
            $('#contactAreaLightBox').val('');
            $.ajax({
                type:'POST',
                url: updateCompanySettingsURI,
                data: $('#contactForm').serialize(),
                dataType: 'text',
                success: function(response) {
                    if(response != ''){
                        $("#contactDivComplete").html( response );
                        $.colorbox.close();
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }
    
    function addAnnouncement(){
        try{
            if(CKEDITOR.instances['announcementAreaLightBox'].getData() == ""){
                alert("Please provide some text in order to save the announcement.")
            } else {
            if ($("#announcementForm").validate().form() == true) {
                $('#announcement').val(CKEDITOR.instances['announcementAreaLightBox'].getData());
                $.ajax({
                    type:'POST',
                    url: addAnnouncementURI,
                    data: $('#announcementForm').serialize(),
                    dataType: 'text',
                    success: function(response) {
                        if(response != ''){
                            $("#announcementDiv").html( response );
                            CKEDITOR.instances['announcementAreaLightBox'].destroy();
                            $.colorbox.close();
                            $('#announcementAreaLightBox').val('');
                            $('#announceId').val('');
                        }
                    }
                });
            }
        }
        }catch(exp){
            alert(exp);
        }
        return false;
    }



    function addMessage(){
        try{
            if ($("#messageForm").validate().form() == true) {
                $('#message').val(CKEDITOR.instances['messageAreaLightBox'].getData());
                $.ajax({
                    type:'POST',
                    url: addMessageURI,
                    data: $('#messageForm').serialize(),
                    dataType: 'text',
                    success: function(response) {
                        if(response != ''){
                            $("#messageDiv").html( response );
                            CKEDITOR.instances['messageAreaLightBox'].destroy();
                            $.colorbox.close();
                            $('#messageAreaLightBox').val('');
                            $('#messageId').val('');
                            $("#addLink").hide();
                            $("#editLink").show();
                            $("#addLinkEm").hide();
                            $("#editLinkEm").show();
                        }
                    }
                });
            }
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function deleteMessage(val){
        try{
            $.ajax({
                type:'POST',
                url: deleteMessageURI+'?idString='+val.id,
                data: '',
                success: function(response) {
                    if(response != ''){
                        $("#messageDiv").html( response );
                        $("#addLink").show();
                        $("#editLink").hide();
                        $("#addLinkEm").show();
                        $("#editLinkEm").hide();
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function deleteAnnouncement(val){
        try{
            $.ajax({
                type:'POST',
                url: deleteAnnouncementURI+'?idString='+val.id,
                data: '',
                success: function(response) {
                    if(response != ''){
                        $("#announcementDiv").html( response );
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function addInformation(){
        try{
            if(CKEDITOR.instances['informationTextArea'].getData() == ""){
                alert("Please provide some text in order to save the information.");
            } else if($('#infoContentGroupList').val()==null || $('#infoContentGroupList').val().length==0){
                alert("Please select content view group in order to save the information.");
            } else {
            if ($("#informationForm").validate().form() == true) {
           /*  	var check=CKEDITOR.instances['informationTextArea'].getData();
            	check= check.replace("<p>","");
            	check= check.replace("</p>","");
            	
            	if(check.includes("<")){
            		check=	check.replace(/</g,"&lt;");
            		check=	check.replace(/>/g,"&gt;");
            		
            		 $('#information').val('<p>'+check+'</p>');
            	}else{
            		 $('#information').val(CKEDITOR.instances['informationTextArea'].getData());
            	} */
            	
           // 	console.log(CKEDITOR.instances['informationTextArea'].getData());
                $('#information').val(CKEDITOR.instances['informationTextArea'].getData());
                $.ajax({
                    type:'POST',
                    url: addInformationURI,
                    data: $('#informationForm').serialize(),
                    dataType: 'text',
                    success: function(response) {
                        if(response != ''){
                            $("#informationDiv").html( response );
                            CKEDITOR.instances['informationTextArea'].destroy();
                            $.colorbox.close();
                            $('#informationTextArea').val('');
                            $('#informationId').val('');
                        }
                    }
                });
            }
        }
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function deleteInformation(val){
        try{
            $.ajax({
                type:'DELETE',
                url: deleteInformationURI+val,
                data: '',
                success: function(response) {
                    if(response != ''){
                        $("#informationDiv").html( response );
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }

    function updateSharedListMapping() {

        var sharedListMapping = $('#sharedListMapping').val();
        $("#sharedListMappingVal").val(sharedListMapping);
        try{
            $.ajax({
                type:'PUT',
                url: updateSharedListMappingURI+"?sharedListMapping="+sharedListMapping,
                data: '',
                success: function(response) {
                    if(response == 'success'){
                        noty({text: 'Shared List Mapping updated successfully.', type: 'success'});
                    }
                }
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }
    
    function removeIconImage(){
        resetCompanyImg();
        $('#companyIconHidden').val('');
        $("#browseDiv").css("display","block");
        $("#iconDiv").css("display","none");
        $("#defCompIcon").css("display","block");
        $("#saveEM").css("display","block");

    }
    function removeIconNoImage(){
        resetNoCompanyImg();
        $('#noCompanyIconHidden').val('');
        $("#browseDiv1").css("display","block");
        $("#iconDiv1").css("display","none");
        $("#defNoImg").css("display","block");
        $("#saveEM").css("display","block");
    }

    function removeIconFindVendor(){
        resetFindVendorImg();
        $('#findVendorIconHidden').val('');
        $("#browseDiv2").css("display","block");
        $("#iconDiv2").css("display","none");
        $("#defFindVendor").css("display","block");
        $("#saveEM2").css("display","block");
    }

    function resetCompanyImg(){
//        document.getElementById('companyImage').parentNode.innerHTML = document.getElementById('companyImage').parentNode.innerHTML;
//        $("#companyImage").replaceWith($("#companyImage").clone(true));
        $('input[type=file]').each(function(){
            if($('#companyImage').val() != ''){
                $('#companyImage').after($('#companyImage').clone(true)).remove();
                $('#companyImage').remove(":contains('.')");
            }
        });
        $('#removeCompanyIcon').val('true');
        $('#companyImage').val('');
        $('#companyImageEM').html('Click the add button to the left to change the image.');
//        jcf.customForms.destroyAll();
//        jcf.customForms.replaceAll();
        jcf.customForms.refreshAll();
        $("#saveEM").css("display","block");
    }


    String.prototype.startsWith = function(s)
    {
        if( this.indexOf(s) == 0 ) return true;
        return false;
    }



    function setWelcomeFileName(){
        $('#welcomeImageForm').submit();
    }


    function resetNoCompanyImg(){
        $('input[type=file]').each(function(){
            if($('#noCompanyImage').val() != ''){
                $('#noCompanyImage').after($('#noCompanyImage').clone(true)).remove();
                $('#noCompanyImage').remove(":contains('.')");
            }
        });
        $('#removeNoCompanyIcon').val('true');
        $('#noCompanyImage').val('');
        $('#NoCompanyImageEM').html('Click the add button to the left to change the image.');
        jcf.customForms.refreshAll();
        $("#saveEM").css("display","block");
    }
    function resetFindVendorImg(){
        $('input[type=file]').each(function(){
            if($('#findVendorImage').val() != ''){
                $('#findVendorImage').after($('#findVendorImage').clone(true)).remove();
                $('#findVendorImage').remove(":contains('.')");
            }
        });
        $('#removeFindVendorIcon').val('true');
        $('#findVendorImage').val('');
        $('#FindVendorImageEM').html('Click the add button to the left to change the image.');
        jcf.customForms.refreshAll();
        $("#saveEM2").css("display","block");
    }

    function setFileName(){
        if(document.getElementById('companyImage').value != ''){
            $('#removeCompanyIcon').val('false');
            var ext = $('#companyImage').val().split('.').pop().toLowerCase();
            if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                alert('Invalid file: '+$('#companyImage').val() +'. Allowed files types are [ gif, png, jpg, jpeg ]');
                resetCompanyImg();
                return;
            }

            var fileName = document.getElementById('companyImage').value;
            var indexPos=0;
            //ie8 security restriction hack
            if( document.getElementById('companyImage').value.startsWith("C:\\fakepath\\") || document.getElementById('companyImage').value.startsWith("C://fakepath//")  )
                indexPos = "C:\\fakepath\\".length-1;

            $('#companyImageEM').html(fileName.substr(indexPos,fileName.length)+'<a style="margin-top:4px;margin-left: 20px;cursor: pointer;" onclick="resetCompanyImg();" class="alt-close"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>');
        } else {
            $('#companyImageEM').html('');
        }
        $("#saveEM").css("display","block");
    }

    function setFileNameNoImage(){
        if(document.getElementById('noCompanyImage').value != ''){
            $('#removeNoCompanyIcon').val('false');
            var ext = $('#noCompanyImage').val().split('.').pop().toLowerCase();
            if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                alert('Invalid file: '+$('#noCompanyImage').val() +'. Allowed files types are [ gif, png, jpg, jpeg ]');
                resetNoCompanyImg();
                return;
            }

            var fileName = document.getElementById('noCompanyImage').value;
            var indexPos=0;
            //ie8 security restriction hack
            if( document.getElementById('noCompanyImage').value.startsWith("C:\\fakepath\\") || document.getElementById('noCompanyImage').value.startsWith("C://fakepath//")  )
                indexPos = "C:\\fakepath\\".length-1;

            $('#NoCompanyImageEM').html(fileName.substr(indexPos,fileName.length)+'<a style="margin-top:4px;margin-left: 20px;cursor: pointer;" onclick="resetNoCompanyImg();" class="alt-close"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>');
        } else {
            $('#NoCompanyImageEM').html('');
        }
        $("#saveEM").css("display","block");
    }

    function setFileNameFindVendor(){
        if(document.getElementById('findVendorImage').value != ''){
            $('#removeFindVendorIcon').val('false');
            var ext = $('#findVendorImage').val().split('.').pop().toLowerCase();
            if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                alert('Invalid file: '+$('#findVendorImage').val() +'. Allowed files types are [ gif, png, jpg, jpeg ]');
                resetNoCompanyImg();
                return;
            }

            var fileName = document.getElementById('noCompanyImage').value;
            var indexPos=0;
            //ie8 security restriction hack
            if( document.getElementById('noCompanyImage').value.startsWith("C:\\fakepath\\") || document.getElementById('noCompanyImage').value.startsWith("C://fakepath//")  )
                indexPos = "C:\\fakepath\\".length-1;

            $('#NoCompanyImageEM').html(fileName.substr(indexPos,fileName.length)+'<a style="margin-top:4px;margin-left: 20px;cursor: pointer;" onclick="resetNoCompanyImg();" class="alt-close"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>');
        } else {
            $('#NoCompanyImageEM').html('');
        }
        $("#saveEM").css("display","block");
    }

    function showWelcomeImageResponse(responseText, statusText, xhr, $form)  {
        if(responseText.indexOf('ERROR') != -1){
            alert(responseText);
        } else {
            if(responseText != "" && responseText != null && responseText != "null"){
                $("#fieldWelcomeIcon").attr('src', "image/"+responseText);
                $("#fieldWelcomeIcon").show();
                $("#iconWelcomeDiv").show();
                $("#browseWelcomeDiv").hide();
            } else {
                $("#fieldWelcomeIcon").hide();
            }
        }
    }

    function showImageResponse(responseText, statusText, xhr, $form)  {

        if(responseText.indexOf('ERROR') != -1){
            alert(responseText);
        } else {
            $("#saveEM").hide();
            var strArr = responseText.split("::");
            if(strArr[0] != "" && strArr[0] != null && strArr[0] != "null"){
                if(strArr[0].startsWith("https")){
                    $("#fieldIcon").attr('src', strArr[0]);
                    $("#headerLogo").attr('src',strArr[0]);
                }else{
                    $("#fieldIcon").attr('src', "image/"+strArr[0]);
                    $("#headerLogo").attr('src', "image/"+strArr[0]);
                }
                $("#defCompIcon").hide();
                $("#fieldIcon").show();
                $("#iconDiv").show();
                $("#browseDiv").hide();
                $("#defStrong").hide();//css("display","none");
                $("#custStrong").show();//css("display","block");
            } else {
                $("#fieldIcon").hide();
                $("#defCompIcon").show();
                $("#defStrong").show();
                $("#custStrong").hide();
            }

            if(strArr[1] != "" && strArr[1] != null && strArr[1] != "null"){
                $("#fieldIcon1").attr('src', "image/"+strArr[1]);
                $("#defNoImg").hide();
                $("#fieldIcon1").show();
                $("#iconDiv1").show();
                $("#browseDiv1").hide();
            } else {
                $("#fieldIcon1").hide();
                $("#defNoImg").show();
            }
            if(strArr[2] != "" && strArr[2] != null && strArr[2] != "null"){
                $("#fieldIcon2").attr('src', "image/"+strArr[2]);
                $("#defFindVendor").hide();
                $("#fieldIcon2").show();
                $("#iconDiv2").show();
                $("#browseDiv2").hide();
            } else {
                $("#fieldIcon2").hide();
                $("#defFindVendor").show();
            }

//            $("#fieldIcon").src
        }

    }
function saveDefSection(){
    $("#contentForm").submit();

//    if(navigator.appName=="Microsoft Internet Explorer"){
//        $("#contentForm").submit();
//        try{
//            $.ajax({
//                type:'POST',
//                url: addCompanySettingURI,
//                data: $("#contentForm").serialize(),
//                dataType: 'text',
//                async: false,
//                success: function(response) {
//                    if(response != '' &&  response.indexOf('ERROR') != -1){
//                        var errorMsg = response.substring(response.indexOf(':')+1);
//                        alert(errorMsg);
//                        return false;
//                        //  $("#defSection").html( response );
//                    }else{
//                        geCompanySetting();
//                    }
//                }
//            });
//        }catch(exp){
//            alert(exp);
//        }
//    } else {
//        try{
//            $.ajax({
//                type:'POST',
//                url: addCompanySettingURI,
//                data: new FormData(document.getElementById("contentForm")),
//                dataType: 'text',
//                async: false,
//                processData: false,
//                contentType: false,
//                success: function(response) {
//                    if(response != '' &&  response.indexOf('ERROR') != -1){
//                        var errorMsg = response.substring(response.indexOf(':')+1);
//                        alert(errorMsg);
//                        return false;
//                        //  $("#defSection").html( response );
//                    }else{
//                        geCompanySetting();
//                    }
//                }
//            });
//        }catch(exp){
//            alert(exp);
//        }
//    }
//
//    return false;
}


  function geCompanySetting(){
        try{
            $.ajax({
                type:'POST', url: getCompanySettingURI,
                data:'',
                success: function(data, textStatus, jqXHR) {
                    //alert(data);
                    if(data != null && data != ''){
                         $("#defSection").html( data );
                        if(document.getElementById('fieldIcon') != null){
                            $("#defStrong").css("display","none");
                            $("#custStrong").css("display","block");
                            document.getElementById('headerLogo').src = document.getElementById('fieldIcon').src
                        } else {
                            $("#defStrong").css("display","block");
                            $("#custStrong").css("display","none");
                        }
                    }
                },
                async:false
            });
        }catch(exp){
            alert(exp);
        }
        return false;
    }
  function updateWelcomeText(){
      try{
    	  var welcomeWidgetText ="";
    	  if($("input[type='radio'].radioBtnClass").is(':checked')) {
    		  welcomeWidgetText = $("input[type='radio'].radioBtnClass:checked").val();
    		}
    	  var supplierCardOrder = $('#welcomeImageForm #supplierCardOrder #welcomeTextForm').val();
    	  var formData = { supplierCardOrder: supplierCardOrder, welcomeWidgetText : welcomeWidgetText };
          $.ajax({
              type:'PUT',
              url: welcomeTextURI,
              data: JSON.stringify(formData),
              dataType: 'text',
              contentType: 'application/json',
              success: function(data, textStatus, jqXHR) {
                  if(data != null && data != ''){
                  }
              },
              async:false
          });
      }catch(exp){
          alert(exp);
      }
      return false;
  }

  function updateCompanySettings(){
      try{
    	  var supplierCardOrder = $('#welcomeImageForm #supplierCardOrder').val();
    	  var formData = { supplierCardOrder: supplierCardOrder };
          $.ajax({
              type:'PUT',
              url: companySettingsURI,
              data: JSON.stringify(formData),
              dataType: 'text',
              contentType: 'application/json',
              success: function(data, textStatus, jqXHR) {
                  if(data != null && data != ''){
                  }
              },
              async:false
          });
      }catch(exp){
          alert(exp);
      }
      return false;
  }
</script>
<spring:message code="com.adminui.content_settings.Save" text="default text" var="saveLabel"/> 
<div id="wrapper">
<div id="main">
<div class="section">
    <ul class="breadcrumbs">
        <li><a href="vroozi">Vroozi</a></li>
        <li>Company Settings</li>
    </ul>
    <div class="video-btn-holder">
        <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
        <a href="#" class="btn"><span><em>Help video</em></span></a-->
    </div>
</div>
<div class="main-holder">
<div id="content" style="padding: 0;">
    <form class="settings-form" action="addCompanySetting" enctype="multipart/form-data" style="padding: 0;" method="post" id="contentForm">
        <h1><spring:message code="com.adminui.content_settings.CompanySettings" text="default text" /></h1>
        <div class="content-block toggle-block active" id="create-field" style="margin: 0;border-bottom: 0;">
            <div class="headline">
                <h2><a href="#" class="open-close"><spring:message code="com.adminui.content_settings.Defaults" text="default text" /></a></h2>
            </div>
            <div class="block">
                <div class="content">
              		<div class="alt-area">
                		<div class="row">
                        	<label><spring:message code="com.adminui.content_settings.CompanyName" text="default text" /></label>
                            <!--div class="area-col">
                            <span id="comp-name" class="text" style="padding: 10px 0 0 0" ><c:out value="NetSol"/></span-->
                            <label style="text-align: left;font-weight:normal;"><c:out value="${companySettings.companyName}"/></label>
                            <!--/div-->
                        </div>
                        <div class="row">
                        	<label><spring:message code="com.adminui.content_settings.CompanyCode" text="default text" /></label>
                           	<!--div class="area-col">
                           	<span id="comp-code" class="text"><1--c:out value="$--{userDetail.unitId}"/></span>
                          	</div-->
                        	<label style="text-align: left;font-weight:normal;"><c:out value="${companySettings.companyCode}"/></label>
                		</div>
 						<c:if test="<%=aclManager.allow(request, Permission.VIEW_USER_NAME)%>">
	                        <div class="row">
	                        	<label><spring:message code="com.adminui.content_settings.CompanyUsername" text="default text" /></label>
	                           	<!--div class="area-col">
	                           	<span id="comp-code" class="text"><1--c:out value="$--{userDetail.unitId}"/></span>
	                          	</div-->
	                        	<label style="text-align: left;font-weight:normal;"><c:out value="${companySettings.userName}"/></label>
	                		</div>
						</c:if>
						<c:if test="<%=aclManager.allow(request, Permission.VIEW_PASSWORD)%>">
	                        <div class="row">
	                        	<label><spring:message code="com.adminui.content_settings.CompanyPassword" text="default text" /></label>
	                           	<!--div class="area-col">
	                           	<span id="comp-code" class="text"><1--c:out value="$--{userDetail.unitId}"/></span>
	                          	</div-->
	                        	<label style="text-align: left;font-weight:normal;"><c:out value="${companySettings.password}"/></label>
	                		</div>
						</c:if>


                    </div>
                    <h3><spring:message code="com.adminui.content_settings.Images" text="default text" /></h3>
                    <div class="alt-area" id="defSection">

                        <div class="row"></div>
                        <div class="row"></div>
                        <div class="row"></div>

                        <div>

                            <input name="id" id="companySetId" type="hidden" value='<c:out value="${companySettings.id}"/>' />
                            <input name="noCompanyIcon" id="noCompanyIconHidden" type="hidden" value='<c:out value="${companySettings.noCompanyIcon}"/>' />
                            <input name="findVendorIcon" id="findVendorIconHidden" type="hidden" value='<c:out value="${companySettings.findVendorIcon}"/>' />
                            <input name="companyIcon" id="companyIconHidden" type="hidden" value='<c:out value="${companySettings.companyIcon}"/>' />
                            <div class="alt-row">
                                <label style="width: 150px;" class="alt-label"><spring:message code="com.adminui.content_settings.UploadCompanyLogo" text="default text" /></label>
                                <div style="float: left;"><img id="defCompIcon" style="<c:if test='${empty companySettings.companyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.companyIcon}'>display:none;</c:if> padding-right: 20px;" src="res/images/logo.png" width="100" height="39" alt="image description" /></div>
                                <div id="browseDiv" style="<c:if test='${empty companySettings.companyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.companyIcon}'>display:none;</c:if>" class="popup-holder">
                                    <a href="#" class="btn-add-open open" style="z-index: 0"><spring:message code="com.adminui.content_settings.add" text="default text" /></a>
                                    <div class="popup">
                                        <div class="popup-frame">
                                            <div class="popup-inner">
                                                <div class="top">
                                                    <a href="#" class="alt-close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                                                    <h3><spring:message code="com.adminui.content_settings.UploadFile" text="default text" /></h3>
                                                </div>
                                                <div class="row">
                                                    <input type="file" id="companyImage" onchange="setFileName();" name="companyImage" value="${companySettingsUtility.companyIcon}" />
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <a href="#" class="btn-upload"><spring:message code="com.adminui.content_settings.UPload" text="default text" /></a>
                                                    <div class="txt">
                                                        <span class="size"> 0.59 KB</span>
                                                        <p>icon_photo_upload_16px.gif</p>
                                                    </div>
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <div class="txt">
                                                        <div class="line-box"><span class="line" style="width:70%"></span></div>
                                                        <a href="#" class="btn-cancel"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <em id="companyImageEM" class="text"><spring:message code="com.adminui.content_settings.AddBtnChngImg" text="default text" /></em>
                                </div>
                                <div id="iconDiv" class="upload-file" style="<c:if test='${not empty companySettings.companyIcon}'>display:block;</c:if><c:if test='${empty companySettings.companyIcon}'>display:none;</c:if>">
                                    <div class="upload-file-holder">
                                        <%--<c:if test='${not empty companySettings.companyIcon}'>--%>
                                        <c:set var="imgPath"
                                               value="image/${companySettings.companyIcon}" />

                                        <c:if test='${not empty absolutePath}'>
                                            <c:set var="imgPath"
                                                   value="${absolutePath}/${companySettings.companyIcon}"/>
                                        </c:if>
                                            <img id="fieldIcon"
                                                 src="${imgPath}"
                                                 alt="image description" style="padding-right: 40px; max-height: 39px; max-width: 100px;"/>
                                        <%--</c:if>--%>
                                    </div>
                                    <a href="javascript:void(0)" onclick="removeIconImage(); return false;" class="btn-del"></a>
                                </div>


                            </div>
                        </div>

                        <div>
                            <div class="alt-row">
                                <label style="width: 150px;" class="alt-label"><spring:message code="com.adminui.content_settings.UploadNoImageLogo" text="default text" /></label>
                                <div style="float: left;"><img id="defNoImg" style="<c:if test='${empty companySettings.noCompanyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.noCompanyIcon}'>display:none;</c:if> padding-right: 60px;" src="res/images/noimage.jpg" width="60" height="60" alt="image description" /></div>
                                <div id="browseDiv1" style="<c:if test='${empty companySettings.noCompanyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.noCompanyIcon}'>display:none;</c:if>" class="popup-holder">
                                    <a href="#" class="btn-add-open open" style="z-index: 0"><spring:message code="com.adminui.content_settings.add" text="default text" /></a>
                                    <div class="popup">
                                        <div class="popup-frame">
                                            <div class="popup-inner">
                                                <div class="top">
                                                    <a href="#" class="alt-close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                                                    <h3><spring:message code="com.adminui.content_settings.UploadFile" text="default text" /></h3>
                                                </div>
                                                <div class="row">
                                                    <input type="file" id="noCompanyImage" onchange="setFileNameNoImage();" name="noCompanyImage" value="${companySettingsUtility.noCompanyIcon}" />
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <a href="#" class="btn-upload"><spring:message code="com.adminui.content_settings.UPload" text="default text" /></a>
                                                    <div class="txt">
                                                        <span class="size"> 0.59 KB</span>
                                                        <p>icon_photo_upload_16px.gif</p>
                                                    </div>
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <div class="txt">
                                                        <div class="line-box"><span class="line" style="width:70%"></span></div>
                                                        <a href="#" class="btn-cancel"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <em id="NoCompanyImageEM" class="text"><spring:message code="com.adminui.content_settings.AddBtnChngImg" text="default text" /></em>
                                </div>
                                <div id="iconDiv1" class="upload-file" style="<c:if test='${not empty companySettings.noCompanyIcon}'>display:block;</c:if><c:if test='${empty companySettings.noCompanyIcon}'>display:none;</c:if>">
                                    <div class="upload-file-holder">
                                        <%--<c:if test='${not empty companySettings.noCompanyIcon}'>--%>
                                            <img id="fieldIcon1" src="image/${companySettings.noCompanyIcon}" style="padding-right: 40px; max-height: 39px; max-width: 100px;" alt="image description" />
                                        <%--</c:if>--%>
                                    </div>
                                    <a href="javascript:void(0)" onclick="removeIconNoImage(); return false;" class="btn-del"></a>
                                </div>
                                <div id="saveEM" style="float: left; text-align: left; padding-right: 300px; display: none;"><em class="text"><spring:message code="com.adminui.content_settings.saveBtn" text="default text" /></em></div>
                            </div>
                        </div>

                        <div>
                            <div class="alt-row">
                                <label style="width: 150px;" class="alt-label"><spring:message code="com.adminui.content_settings.UploadFindVendorLogo" text="default text" /></label>
                                <div style="float: left;"><img id="defFindVendor" style="<c:if test='${empty companySettings.findVendorIcon}'>display:block;</c:if><c:if test='${not empty companySettings.findVendorIcon}'>display:none;</c:if> padding-right: 60px;" src="res/images/noimage.jpg" width="60" height="60" alt="image description" /></div>
                                <div id="browseDiv2" style="<c:if test='${empty companySettings.findVendorIcon}'>display:block;</c:if><c:if test='${not empty companySettings.findVendorIcon}'>display:none;</c:if>" class="popup-holder">
                                    <a href="#" class="btn-add-open open" style="z-index: 0"><spring:message code="com.adminui.content_settings.add" text="default text" /></a>
                                    <div class="popup">
                                        <div class="popup-frame">
                                            <div class="popup-inner">
                                                <div class="top">
                                                    <a href="#" class="alt-close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                                                    <h3><spring:message code="com.adminui.content_settings.UploadFile" text="default text" /></h3>
                                                </div>
                                                <div class="row">
                                                    <input type="file" id="findVendorImage" onchange="setFileNameFindVendor();" name="findVendorImage" value="${companySettingsUtility.findVendorIcon}" />
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <a href="#" class="btn-upload"><spring:message code="com.adminui.content_settings.UPload" text="default text" /></a>
                                                    <div class="txt">
                                                        <span class="size"> 0.59 KB</span>
                                                        <p>icon_photo_upload_16px.gif</p>
                                                    </div>
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <div class="txt">
                                                        <div class="line-box"><span class="line" style="width:70%"></span></div>
                                                        <a href="#" class="btn-cancel"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <em id="FindVendorImageEM" class="text"><spring:message code="com.adminui.content_settings.AddBtnChngImg" text="default text" /></em>
                                </div>
                                <div id="iconDiv2" class="upload-file" style="<c:if test='${not empty companySettings.findVendorIcon}'>display:block;</c:if><c:if test='${empty companySettings.findVendorIcon}'>display:none;</c:if>">
                                    <div class="upload-file-holder">
                                        <%--<c:if test='${not empty companySettings.noCompanyIcon}'>--%>
                                        <img id="fieldIcon2" src="image/${companySettings.findVendorIcon}" style="padding-right: 40px; max-height: 39px; max-width: 100px;" alt="image description" />
                                        <%--</c:if>--%>
                                    </div>
                                    <a href="javascript:void(0)" onclick="removeIconFindVendor(); return false;" class="btn-del"></a>
                                </div>
                                <div id="saveEM2" style="float: left; text-align: left; padding-right: 300px; display: none;"><em class="text"><spring:message code="com.adminui.content_settings.saveBtn" text="default text" /></em></div>
                            </div>
                        </div>

                    </div>

                    <h3>Mapping</h3>
                    <div class="alt-area">
                        <div class="row" >
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.CategoryMapping" text="default text" /></label>
                            <div style="margin-top: 1px;" >
                            <input type="radio" name="group1" style="margin-top: 0px;"  value="on" onclick="$('#categoryMapping').val(true);" <c:if test='${companySettings.categoryMapping}'>checked</c:if> >On 	<input type="radio" style="margin-top: 0px;" onclick="$('#categoryMapping').val(false);" name="group1" value="off" <c:if test='${!companySettings.categoryMapping}'>checked</c:if>>Off
                            <input type="hidden" name="categoryMapping" value="${companySettings.categoryMapping}" id="categoryMapping"/>
                        	</div>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.UnitOfMeasure" text="default text" /></label>
                             <div style="margin-top: 1px;" >
                            <input type="radio" name="group2" style="margin-top: 0px;"  value="on" onclick="$('#unitOfMeasure').val(true);"  <c:if test='${companySettings.unitOfMeasure}'>checked</c:if>>On 	<input type="radio" style="margin-top: 0px;" onclick="$('#unitOfMeasure').val(false);" name="group2" value="off"  <c:if test='${!companySettings.unitOfMeasure}'>checked</c:if>>Off
                            <input type="hidden" name="unitOfMeasure" value="${companySettings.unitOfMeasure}" id="unitOfMeasure"/>
                        	</div>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.CurrencyMapping" text="default text" /></label>
                            <div style="margin-top: 1px;" >
                            <input type="radio" name="group3" style="margin-top: 0px;" value="on" onclick="$('#currencyMapping').val(true);"  <c:if test='${companySettings.currency}'>checked</c:if>>On 	<input type="radio" style="margin-top: 0px;" onclick="$('#currencyMapping').val(false);" name="group3" value="off" <c:if test='${!companySettings.currency}'>checked</c:if>>Off
                            <input type="hidden" name="currencyMapping" value="${companySettings.currency}" id="currencyMapping"/>
                        	</div>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.TransferCustomFieldDescription" text="default text" /></label>
                            <div style="margin-top: 1px;" >
                            <input type="radio" name="group4" style="margin-top: 0px;" value="on" onclick="$('#transferCustFieldDescription').val(true);"  <c:if test='${companySettings.transferCustFieldDescription}'>checked</c:if>>On 	<input type="radio" style="margin-top: 0px;" onclick="$('#transferCustFieldDescription').val(false);" name="group4" value="off" <c:if test='${!companySettings.transferCustFieldDescription}'>checked</c:if>>Off
                            <input type="hidden" name="transferCustFieldDescription" value="${companySettings.transferCustFieldDescription}" id="transferCustFieldDescription"/>
                        	</div>
                        </div>

                    </div>

                    <h3>Forms</h3>
                    <div class="alt-area">
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.NotinCatalog" text="default text" /></label>
                            <div style="margin-top: 1px;" >
                            <input type="radio" name="group5" style="margin-top: 0px;" value="on" onclick="$('#notInCatalog').val(true);" <c:if test='${companySettings.notInCatalog}'>checked</c:if> >On 	<input type="radio" style="margin-top: 0px;" onclick="$('#notInCatalog').val(false);" name="group5" value="off" <c:if test='${!companySettings.notInCatalog}'>checked</c:if>>Off
                            <input type="hidden" name="notInCatalog" value="${companySettings.notInCatalog}" id="notInCatalog"/>
                        	</div>
                        </div>
                        <c:if test="${companySettings.downloadAllCatalogs != null}">
                            <div class="row">
                                <label style="width: 350px;padding-top: 0px;" class="alt-label">Download all catalogs:</label>
                                <input type="radio" name="downloadRadio" style="margin-top: -5px;" value="on" onclick="$('#downloadAllCatalogs').val(true);" <c:if test='${companySettings.downloadAllCatalogs}'>checked</c:if> >On 	<input type="radio" style="margin-top: -5px;" onclick="$('#downloadAllCatalogs').val(false);" name="downloadRadio" value="off" <c:if test='${!companySettings.downloadAllCatalogs}'>checked</c:if>>Off
                                <input type="hidden" name="downloadAllCatalogs" value="${companySettings.downloadAllCatalogs}" id="downloadAllCatalogs"/>
                            </div>
                        </c:if>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.FreeTextIdentifier" text="default text" /></label>
                             <div style="margin-top: 1px;" >
                            <input type="radio" name="group6" style="margin-top: 0px;" value="on" onclick="$('#limitRequest').val(true);" <c:if test='${companySettings.limitRequest}'>checked</c:if> >On 	<input type="radio" style="margin-top: 0px;" onclick="$('#limitRequest').val(false);" name="group6" value="off" <c:if test='${!companySettings.limitRequest}'>checked</c:if>>Off
                            <input type="hidden" name="limitRequest" value="${companySettings.limitRequest}" id="limitRequest"/>
                        	</div>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label">Free Text Identifier:</label>
                            <select name="freeTextIdentifier" id="freeTextIdentifier" style="width:181px;">
                                <option value="NEW_ITEM-CUST_FIELD1" <c:if test="${companySettings.notInCatalogFieldMapping.freeTextIdentifier eq 'NEW_ITEM-CUST_FIELD1'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD1</option>
                                <option value="NEW_ITEM-CUST_FIELD2" <c:if test="${companySettings.notInCatalogFieldMapping.freeTextIdentifier eq 'NEW_ITEM-CUST_FIELD2'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD2</option>
                                <option value="NEW_ITEM-CUST_FIELD3" <c:if test="${companySettings.notInCatalogFieldMapping.freeTextIdentifier eq 'NEW_ITEM-CUST_FIELD3'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD3</option>
                                <option value="NEW_ITEM-CUST_FIELD4" <c:if test="${companySettings.notInCatalogFieldMapping.freeTextIdentifier eq 'NEW_ITEM-CUST_FIELD4'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD4</option>
                                <option value="NEW_ITEM-CUST_FIELD5" <c:if test="${companySettings.notInCatalogFieldMapping.freeTextIdentifier eq 'NEW_ITEM-CUST_FIELD5'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.LimitItemIdentifier" text="default text" /></label>
                            <select name="limitItemIdentifier" id="limitItemIdentifier" style="width:181px;">
                                <option value="NEW_ITEM-CUST_FIELD1" <c:if test="${companySettings.notInCatalogFieldMapping.limitItemIdentifier eq 'NEW_ITEM-CUST_FIELD1'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD1</option>
                                <option value="NEW_ITEM-CUST_FIELD2" <c:if test="${companySettings.notInCatalogFieldMapping.limitItemIdentifier eq 'NEW_ITEM-CUST_FIELD2'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD2</option>
                                <option value="NEW_ITEM-CUST_FIELD3" <c:if test="${companySettings.notInCatalogFieldMapping.limitItemIdentifier eq 'NEW_ITEM-CUST_FIELD3'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD3</option>
                                <option value="NEW_ITEM-CUST_FIELD4" <c:if test="${companySettings.notInCatalogFieldMapping.limitItemIdentifier eq 'NEW_ITEM-CUST_FIELD4'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD4</option>
                                <option value="NEW_ITEM-CUST_FIELD5" <c:if test="${companySettings.notInCatalogFieldMapping.limitItemIdentifier eq 'NEW_ITEM-CUST_FIELD5'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.RequiredDate" text="default text" /></label>
                            <select name="requiredDate" id="requiredDate" style="width:181px;">
                                <option value="NEW_ITEM-CUST_FIELD1" <c:if test="${companySettings.notInCatalogFieldMapping.requiredDate eq 'NEW_ITEM-CUST_FIELD1'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD1</option>
                                <option value="NEW_ITEM-CUST_FIELD2" <c:if test="${companySettings.notInCatalogFieldMapping.requiredDate eq 'NEW_ITEM-CUST_FIELD2'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD2</option>
                                <option value="NEW_ITEM-CUST_FIELD3" <c:if test="${companySettings.notInCatalogFieldMapping.requiredDate eq 'NEW_ITEM-CUST_FIELD3'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD3</option>
                                <option value="NEW_ITEM-CUST_FIELD4" <c:if test="${companySettings.notInCatalogFieldMapping.requiredDate eq 'NEW_ITEM-CUST_FIELD4'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD4</option>
                                <option value="NEW_ITEM-CUST_FIELD5" <c:if test="${companySettings.notInCatalogFieldMapping.requiredDate eq 'NEW_ITEM-CUST_FIELD5'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.DeliveryDateFrom" text="default text" /></label>
                            <select name="deliveryDateFrom" id="deliveryDateFrom" style="width:181px;">
                                <option value="NEW_ITEM-CUST_FIELD1" <c:if test="${companySettings.notInCatalogFieldMapping.deliveryDateFrom eq 'NEW_ITEM-CUST_FIELD1'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD1</option>
                                <option value="NEW_ITEM-CUST_FIELD2" <c:if test="${companySettings.notInCatalogFieldMapping.deliveryDateFrom eq 'NEW_ITEM-CUST_FIELD2'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD2</option>
                                <option value="NEW_ITEM-CUST_FIELD3" <c:if test="${companySettings.notInCatalogFieldMapping.deliveryDateFrom eq 'NEW_ITEM-CUST_FIELD3'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD3</option>
                                <option value="NEW_ITEM-CUST_FIELD4" <c:if test="${companySettings.notInCatalogFieldMapping.deliveryDateFrom eq 'NEW_ITEM-CUST_FIELD4'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD4</option>
                                <option value="NEW_ITEM-CUST_FIELD5" <c:if test="${companySettings.notInCatalogFieldMapping.deliveryDateFrom eq 'NEW_ITEM-CUST_FIELD5'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.OverallValue" text="default text" /></label>
                            <select name="overallValue" id="overallValue" style="width:181px;">
                                <option value="NEW_ITEM-CUST_FIELD1" <c:if test="${companySettings.notInCatalogFieldMapping.overallValue eq 'NEW_ITEM-CUST_FIELD1'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD1</option>
                                <option value="NEW_ITEM-CUST_FIELD2" <c:if test="${companySettings.notInCatalogFieldMapping.overallValue eq 'NEW_ITEM-CUST_FIELD2'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD2</option>
                                <option value="NEW_ITEM-CUST_FIELD3" <c:if test="${companySettings.notInCatalogFieldMapping.overallValue eq 'NEW_ITEM-CUST_FIELD3'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD3</option>
                                <option value="NEW_ITEM-CUST_FIELD4" <c:if test="${companySettings.notInCatalogFieldMapping.overallValue eq 'NEW_ITEM-CUST_FIELD4'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD4</option>
                                <option value="NEW_ITEM-CUST_FIELD5" <c:if test="${companySettings.notInCatalogFieldMapping.overallValue eq 'NEW_ITEM-CUST_FIELD5'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD5</option>
                            </select>
                        </div>
                    </div>

					<h3>Shared Shopping Lists</h3>
                    <div class="alt-area">
                        <div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label">Expire in (days):</label>
                            <input type="number" name="sharedListExpiryDays" min="1" max="365" style="width:50px;" value="${companySettings.sharedListExpiryDays}" <c:if test='${!companySettings.sharedListAutoExpire}'>disabled</c:if> id="sharedListExpiryDays"/>
                        </div>
						<div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label">Auto Expire:</label>
                            <input type="checkbox" name="sharedListAutoExpire" class="alt-label" style="width: 350px;padding: 3px;" <c:if test='${companySettings.sharedListAutoExpire}'>checked</c:if> id="sharedListAutoExpire" /><label for="sharedListAutoExpire" style="margin-left: -148px;"></label>
                        </div>
                    </div>
                    
                    <div class="btns-holder">
                        <a class="btn-cancel" href="<c:out value="vroozi"/>"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" onclick="saveDefSection(); return false;" value="Save"/>
                        </c:if>
                    </div>

                </div>
            </div>
        </div>
        <input type="hidden" name="sharedListMapping" id="sharedListMappingVal" value="${companySettings.sharedListMapping}">
    </form>
</div>
<div id="content" style="padding: 0;">

        <div class="content-block toggle-block" id="system-definitions" style="margin: 0;border-bottom: 0;">
            	<div class="headline">
               		<h2><a href="#" class="open-close"><spring:message code="com.adminui.content_settings.SystemDefinitions" text="default text" /></a></h2>
            	</div>

            	<div class="block">
                    <div class="content editable-widget" id="system-def-content-widget" style="display: block">
                        <div id="system_definition_section">
                        	<jsp:include page="system_definition_table_fragment.jsp" />
                        </div>
                    </div>
            	</div>
        </div>


</div>

<div id="content" style="padding: 0;">

        <div class="content-block toggle-block" id="home-page" style="margin: 0;border-bottom: 0;">
            <form class="settings-form" style="padding: 0;" action="return false;" method="post" id="welcomeTextForm">
            <div class="headline">
                <h2><a href="#" class="open-close"><spring:message code="com.adminui.content_settings.HomepageCards" text="default text" /></a></h2>
            </div>
            <div class="block">
                <div class="content">
                    <h3><spring:message code="com.adminui.content_settings.Announcements" text="default text" /></h3>
                    <div class="alt-area1" id="announcementDiv">
                        <c:forEach var="announcement" items="${announcements}">
                            <a id="${announcement.id}" class="alt-close" onclick="deleteAnnouncement(this);"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                            <div class="row" id="${announcement.id}" onclick="openAnnouncementDiv('${announcement.id}');" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;">
                                <span>${announcement.announcements}</span>
                                <c:if test="${(not empty announcement.mailingList)}">
	                                <span style="margin-left: 30px; font: bold 12px/15px Helvetica,Arial,sans-serif;" ><spring:message code="com.adminui.content_settings.isAssignedto" text="default text" /></span>
	                                <ul style="margin-top: 5px; line-height: 1;">
	                                    <c:forEach var="cgroup" items="${announcement.mailingList}">
	                                        <li>${shoppers[cgroup]}</li>
	                                    </c:forEach>
	                                </ul>
	                            </c:if>
                            </div>

							<%--<c:if test="${(!fn:contains( , shopper.key) )}">selected="selected"</c:if> --%>
							<input type="hidden" id="slc-${announcement.id}" value="${announcement.mailingList}"/>
                            <input type="hidden" id="cat-${announcement.id}" value="${announcement.type}"/>
                            <textarea style="display: none;" id="cgval-${announcement.id}">${announcement.announcements}</textarea>
                        </c:forEach>



                    </div>
                    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.addPublishAnnouncements" text="default text" /></em>
                        <ul>
                            <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                                <li><a onclick="openAnnouncementDiv(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.content_settings.addAnnouncements" text="default text" /></em></span></a></li>
                            </c:if>
                        </ul>
                    </div>


                </div>
            </div>

            <div class="block">
                <div class="content">
                    <h3><spring:message code="com.adminui.content_settings.Information" text="default text" /></h3>
                    <div class="alt-area1" id="informationDiv">
                        <c:forEach var="info" items="${informations}">
						    <a id="inflnk-${info.id}" class="alt-close" onclick="deleteInformation('${info.id}');"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
						    <div class="row" id="${info.id}" onclick="openInformationDiv('${info.id}');" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;">
						      <span>${info.information}</span>
						      <c:if test="${not empty info.contentViewGroups}">
							      <span style="margin-left: 30px; font: bold 12px/15px Helvetica,Arial,sans-serif;" ><spring:message code="com.adminui.content_settings.isAssignedto" text="default text" /></span>
	                              <ul style="margin-top: 5px; line-height: 1;">
	                                <c:forEach var="cgroup" items="${info.contentViewGroups}">
	                                    <li>${cvgroups[cgroup]}</li>
	                                </c:forEach>
	                              </ul>
                              </c:if>
						    </div>
						    <input type="hidden" id="infoid-${info.id}" value="${info.id}"/>
						    <input type="hidden" id="infocg-${info.id}" value="${info.contentViewGroups}"/>
						    <textarea style="display: none;" id="infoval-${info.id}">${info.information}</textarea>
						</c:forEach>
						<input type="hidden" id="infoAssignedGroups" value="${infoAssignedGroups}" />
                    </div>
                    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.addPublishInformation" text="default text" /></em>
                        <ul>
                            <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                                <li><a onclick="openInformationDiv(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.content_settings.AddInformation" text="default text" /></em></span></a></li>
                            </c:if>
                        </ul>
                    </div>

                </div>
            </div>

            <div class="block">
                <div class="content">
                    <h3><spring:message code="com.adminui.content_settings.ShopperWelcome" text="default text" /></h3>
                    	<div class="row">
                            <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.WelcomeType" text="default text" /></label>
                            <input type="radio" name="textRadio" style="margin-top: -5px;" class="radioBtnClass" value="true" onchange="updateWelcomeText();" <c:if test='${companySettings.welcomeWidgetText}'>checked</c:if> >Text 	<input type="radio" style="margin-top: -5px;" onchange="updateWelcomeText();" name="textRadio" class="radioBtnClass" value="false" <c:if test='${!companySettings.welcomeWidgetText}'>checked</c:if>> Image
                         </div>
                    <div class="alt-area1" id="messageDiv">
                        <c:forEach var="message" items="${messages}">
                            <a id="${message.id}" class="alt-close" onclick="deleteMessage(this);"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                            <div name="detailMessage" class="row" id="${message.id}" onclick="openMessageDiv(this);" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;">${message.message}</div>
                            <%--<c:if test="${(!fn:contains( , shopper.key) )}">selected="selected"</c:if> --%>
                            <input type="hidden" id="msc-${message.id}" value="${message.mailingList}"/>
                        </c:forEach>



                    </div>
                    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em id="addLinkEm" style="<c:if test='${messageSize == 0}'>display: block;</c:if><c:if test='${messageSize > 0}'>display: none;</c:if>" class="text">Click on the "Add" button to publish Welcome Messages</em><em id="editLinkEm" style="<c:if test='${messageSize > 0}'>display: block;</c:if><c:if test='${messageSize == 0}'>display: none;</c:if>" class="text">To change the Welcome Message: Click on "Edit Welcome Message"</em>
                        <ul>
                            <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">

                                <li><a id="addLink" onclick="openMessageDiv(); return false;" style="<c:if test='${messageSize == 0}'>display: block;</c:if><c:if test='${messageSize > 0}'>display: none;</c:if>" class="btn-add-profile"><span><em><spring:message code="com.adminui.content_settings.AddWelcomeMessage" text="default text" /></em></span></a></li>
                                <li><a id="editLink" onclick="$('#messageDiv div').click(); return false;" style="<c:if test='${messageSize > 0}'>display: block;</c:if><c:if test='${messageSize == 0}'>display: none;</c:if>" class="btn-add-profile"><span><em><spring:message code="com.adminui.content_settings.EditWelcomeMessage" text="default text" /></em></span></a></li>

                            </c:if>
                        </ul>
                    </div>

                </div>
            </div>
    </form>
            <form action="upload-welcome-image" style="padding: 0px;" class="settings-form" enctype="multipart/form-data" method="post" id="welcomeImageForm">
            <div class="block">
                <div class="content">
                    <div class="alt-area">

                            <div class="row" style="padding-top: 100px;">
                                <label style="width: 150px;" class="alt-label"><spring:message code="com.adminui.content_settings.UploadWelcomeImage" text="default text" /></label>
                                <div id="browseWelcomeDiv" style="<c:if test='${empty companySettings.welcomeImage}'>display:block;</c:if><c:if test='${not empty companySettings.welcomeImage}'>display:none;</c:if>" class="popup-holder">
                                    <a href="#" class="btn-add-open open" style="z-index: 0"><spring:message code="com.adminui.content_settings.add" text="default text" /></a>
                                    <div class="popup">
                                        <div class="popup-frame">
                                            <div class="popup-inner">
                                                <div class="top">
                                                    <a href="#" class="alt-close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                                                    <h3><spring:message code="com.adminui.content_settings.UploadFile" text="default text" /></h3>
                                                </div>
                                                <div class="row">
                                                    <input type="file" id="companyWelcomeImage" onchange="setWelcomeFileName();" name="welcomeImage" value="${companySettingsUtility.welcomeImage}" />
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <a href="#" class="btn-upload"><spring:message code="com.adminui.content_settings.UPload" text="default text" /></a>
                                                    <div class="txt">
                                                        <span class="size"> 0.59 KB</span>
                                                        <p>icon_photo_upload_16px.gif</p>
                                                    </div>
                                                </div>
                                                <div class="row" style="display:none;">
                                                    <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                                                    <div class="txt">
                                                        <div class="line-box"><span class="line" style="width:70%"></span></div>
                                                        <a href="#" class="btn-cancel"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <em id="companyWelcomeImageEM" class="text"><spring:message code="com.adminui.content_settings.AddBtnChngImg" text="default text" /></em>
                                </div>
                                <div id="iconWelcomeDiv" class="upload-file" style="<c:if test='${not empty companySettings.welcomeImage}'>display:block;</c:if><c:if test='${empty companySettings.welcomeImage}'>display:none;</c:if>">
                                    <div class="upload-file-holder">
                                        <%--<c:if test='${not empty companySettings.companyIcon}'>--%>
                                        <img id="fieldWelcomeIcon" src="image/${companySettings.welcomeImage}" alt="image description" style="padding-right: 40px; max-height: 39px; max-width: 100px;"/>
                                        <%--</c:if>--%>
                                    </div>
                                    <a href="javascript:void(0)" onclick="removeWelcomeIconImage(); return false;" class="btn-del"></a>
                                </div>


                            </div>

                    </div>
                </div>
            </div>
            
            <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
            <div class="block">
                <div class="content">
                    <h3><spring:message code="com.adminui.content_settings.SupplierCard" text="default text" /></h3>
                    <div class="function btn-holder" style="margin: 0;padding-top: 10px;">
                    	<em class="text"><spring:message code="com.adminui.content_settings.OrderBy" text="default text" /> </em>
                    	<ul><li>
							<select name="supplierCardOrder" id="supplierCardOrder" style="width:178px;" onchange="updateCompanySettings();">
								<option value="DATE_ASCENDING" <c:if test="${companySettings.supplierCardOrder eq 'DATE_ASCENDING' || companySettings.supplierCardOrder eq null}">selected="selected"</c:if> ><spring:message code="com.adminui.content_settings.DateAscending" text="default text" /></option>
		                        <option value="DATE_DESCENDING" <c:if test="${companySettings.supplierCardOrder eq 'DATE_DESCENDING'}">selected="selected"</c:if> ><spring:message code="com.adminui.content_settings.DateDescending" text="default text" /></option>
		                        <option value="NAME_ASCENDING" <c:if test="${companySettings.supplierCardOrder eq 'NAME_ASCENDING'}">selected="selected"</c:if> ><spring:message code="com.adminui.content_settings.NameAscending" text="default text" /></option>
		                        <option value="NAME_DESCENDING" <c:if test="${companySettings.supplierCardOrder eq 'NAME_DESCENDING'}">selected="selected"</c:if> ><spring:message code="com.adminui.content_settings.NameDescending" text="default text" /></option>
							</select>
						</li></ul>
                    </div>
                </div>
            </div>
			</c:if>
			
            </form>
        </div>


</div>
<div id="content" style="padding: 0;">
<form class="settings-form" style="padding: 0;" action="return false;" method="post" >
<div class="content-block toggle-block" id="help-content" style="margin: 0;border-bottom: 0;">
    <div class="headline">
        <h2><a href="#" class="open-close"><spring:message code="com.adminui.content_settings.HELPCONTENT" text="Help Content" /></a></h2>
    </div>
    <div class="block">
        <div class="content" id="faqDivComplete" style="border-bottom: 1px solid #D0D0D0;">
            <h3><spring:message code="com.adminui.content_settings.FAQs" text="default text" /></h3>
            <c:if test='${not empty companySettings.faq}'>
                <div class="alt-area1">
                    <div class="row" id="faqDiv" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;cursor: default;">${companySettings.faq}</div>
                </div>
            </c:if>
            <c:if test='${empty companySettings.faq}'>
                <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.addFAQBtn" text="default text" /></em>
                    <ul>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <li><a onclick="openFaqLightBox(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.content_settings.AddFAQ" text="default text" /></em></span></a></li>
                        </c:if>
                    </ul>
                </div>
            </c:if>
            <c:if test='${not empty companySettings.faq}'>
                <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.editFAQBtn" text="default text" /></em>
                    <ul>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <li><a onclick="openFaqLightBox(document.getElementById('faqDiv')); return false;" class="ico-edit"><span><em><spring:message code="com.adminui.content_settings.EditFAQ" text="default text" /></em></span></a></li>
                        </c:if>
                    </ul>
                </div>
            </c:if>
        </div>
        <div class="content" id="policyDivComplete" style="border-bottom: 1px solid #D0D0D0;">
            <h3>Policies</h3>
            <c:if test='${not empty companySettings.policy}'>
                <div class="alt-area1">
                    <div class="row" id="policyDiv" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;cursor: default;">${companySettings.policy}</div>
                </div>
            </c:if>
            <c:if test='${empty companySettings.policy}'>
                <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.AddPoliciesBtn" text="default text" /></em>
                    <ul>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <li><a onclick="openPolicyLightBox(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.content_settings.AddPolicy" text="default text" /></em></span></a></li>
                        </c:if>
                    </ul>
                </div>
            </c:if>
            <c:if test='${not empty companySettings.policy}'>
                <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.EditPoliciesBtn" text="default text" /></em>
                    <ul>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <li><a onclick="openPolicyLightBox(document.getElementById('policyDiv')); return false;" class="ico-edit"><span><em><spring:message code="com.adminui.content_settings.EditPolicy" text="default text" /></em></span></a></li>
                        </c:if>
                    </ul>
                </div>
            </c:if>
        </div>
        <div class="content" id="contactDivComplete" style="border-bottom: 1px solid #D0D0D0;">
            <h3><spring:message code="com.adminui.content_settings.ContactUs" text="default text" /></h3>
            <c:if test='${not empty companySettings.contactUs}'>
                <div class="alt-area1">
                    <div class="row" id="contactDiv" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;cursor: default;">${companySettings.contactUs}</div>
                </div>
            </c:if>
            <c:if test='${empty companySettings.contactUs}'>
                <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.AddContactUSBtn" text="default text" /></em>
                    <ul>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <li><a onclick="openContactLightBox(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.content_settings.AddContactInfo" text="default text" /></em></span></a></li>
                        </c:if>
                    </ul>
                </div>
            </c:if>
            <c:if test='${not empty companySettings.contactUs}'>
                <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.content_settings.EditContactInformationBtn" text="default text" /></em>
                    <ul>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <li><a onclick="openContactLightBox(document.getElementById('contactDiv')); return false;" class="ico-edit"><span><em>Edit Contact Info</em></span></a></li>
                        </c:if>
                    </ul>
                </div>
            </c:if>
        </div>
        
        <div id="customHelpContentDiv" class="help-tabs">
	        <jsp:include page="custom_help_content_section.jsp" />
        </div>
        
    </div>
</div>
</form>
</div>

<div id="content" style="padding: 0;">
    <form class="settings-form" id="workFlowForm" style="padding: 0;" action="return false;" method="post" >
        <div class="content-block toggle-block" id="workFlow" style="margin: 0;">
            <div class="headline">
                <h2><a href="#" class="open-close"><spring:message code="com.adminui.content_settings.APPROVAL_WORKFLOW" text="default text" /></a></h2>
            </div>
            <div class="block">
                <div class="content">
                        <input type="hidden" id="workCompId" name="id"/>
                    <div class="alt-area" id="workFlowDiv">
                        <div class="row">
                            <input type="checkbox" <c:if test='${companySettings.requiredField}'>checked</c:if> name="requiredField" class="target-chbox" id="requiredField" /><label for="requiredField"></label>
                            <label class="alt-label" style="width: 350px;padding: 3px;"><spring:message code="com.adminui.content_settings.AddQuotesBt" text="default text" /></label>
                        </div>
                        <div class="row">
                            <input type="checkbox" <c:if test='${companySettings.itemAdded}'>checked</c:if> name="itemAdded" class="target-chbox" id="itemAdded" /><label for="itemAdded"></label>
                            <label class="alt-label" style="width: 300px;padding: 3px;"><spring:message code="com.adminui.content_settings.AddNewItemToCatalog" text="default text" /></label>
                        </div>
                        <div class="row">
                            <input type="checkbox" <c:if test='${companySettings.priceChange}'>checked</c:if> name="priceChange" class="target-chbox" id="priceChange" /><label for="priceChange"></label>
                            <label class="alt-label" style="width: 178px;padding: 3px;"><spring:message code="com.adminui.content_settings.IfPriceChange" text="default text" /></label><input type="text" name="percentChanged" value="${companySettings.percentChanged}" id="field-name" class="input2 default" style="width: 30px;margin: 0;"/><label class="alt-label" style="width: 195px;padding: 3px;"><spring:message code="com.adminui.content_settings.percentInCatalog" text="default text" /></label>
                        </div>
                    </div>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="<c:out value="vroozi"/>"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" onclick="updateWorkFlow();return false;" value="Save"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<c:if test="<%=aclManager.isFeatureAvailable(request, Consts.FEATURE_PURCHASE_EXPRESS)%>">
    <div id="content" class="settings-form" style="padding: 0;">

        <div class="content-block toggle-block" id="purchase-express" style="margin: 0;">
            <div class="headline">
                <h2><a href="#" class="open-close"><spring:message code="com.adminui.content_settings.PurchaseExpress" text="default text" /></a></h2>
            </div>

            <div class="block">
                <div class="content">
                    <h3><spring:message code="com.adminui.content_settings.SharedListIDMapping" text="default text" /></h3>
                    <div class="alt-area">
                        <div class="row" >
                    <label style="width: 350px;padding-top: 0px;" class="alt-label"><spring:message code="com.adminui.content_settings.SendSharedList" text="default text" /></label>
                    <select name="sharedListMapping" id="sharedListMapping" style="width:181px;" onchange="updateSharedListMapping();" >
                        <option value=""> </option>
                        <option value="custField1" <c:if test="${companySettings.sharedListMapping eq 'custField1'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD1</option>
                        <option value="custField2" <c:if test="${companySettings.sharedListMapping eq 'custField2'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD2</option>
                        <option value="custField3" <c:if test="${companySettings.sharedListMapping eq 'custField3'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD3</option>
                        <option value="custField4" <c:if test="${companySettings.sharedListMapping eq 'custField4'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD4</option>
                        <option value="custField5" <c:if test="${companySettings.sharedListMapping eq 'custField5'}">selected="selected"</c:if> >NEW_ITEM-CUST_FIELD5</option>
                    </select>
	                    </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</c:if>

<div id="sidebar">
    <ul class="sub-nav">
        <li><a href="<c:out value="vroozi"/>" class="ico-back"><span><spring:message code="com.adminui.content_settings.BACK" text="default text" /></span></a></li>
        <li><a href="#create-field" class="ico-create-field alt-opener"><span><spring:message code="com.adminui.content_settings.Defaults" text="default text" /></span></a></li>
        <li><a href="#system-definitions" class="ico-create-field alt-opener"><span><spring:message code="com.adminui.content_settings.SYSTEMDEFINITIONS" text="default text" /></span></a></li>
        <li><a href="#home-page" class="ico-create-field alt-opener"><span><spring:message code="com.adminui.content_settings.HOMEPAGECARDS" text="default text" /></span></a></li>
        <li><a href="#help-content" class="ico-create-field alt-opener"><span><spring:message code="com.adminui.content_settings.HELPCONTENT" text="default text" /></span></a></li>
        <li><a href="#workFlow" class="ico-create-field alt-opener"><span><spring:message code="com.adminui.content_settings.APPROVALWORKFLOW" text="default text" /></span></a></li>
        <c:if test="<%=aclManager.isFeatureAvailable(request, Consts.FEATURE_PURCHASE_EXPRESS)%>">
        	<li><a href="#purchase-express" class="ico-create-field alt-opener"><span><spring:message code="com.adminui.content_settings.PURCHASEEXPRESS" text="default text" /></span></a></li>
        </c:if>
    </ul>
</div>
</div>
</div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="faqLightBox">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                    <h2>Faq's</h2>
                </div>
                <form method="post" id="faqForm">
                    <input type="hidden" id="colName" name="colName"/>
                    <input type="hidden" id="faq" name="faq"/>
                    <input type="hidden" id="faqCompId" name="id"/>
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <textarea id="faqAreaLightBox"></textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" value="Save" onclick="addFaq(); return false;">
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="policyLightBox">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                    <h2>Policies</h2>
                </div>
                <form method="post" id="policyForm">
                    <input type="hidden" id="policyColName" name="colName"/>
                    <input type="hidden" id="policies" name="policies"/>
                    <input type="hidden" id="policyCompId" name="id"/>
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <textarea id="policyAreaLightBox"></textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" value="Save" onclick="addPolicy(); return false;">
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="contactLightBox">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                    <h2>Contact Us</h2>
                </div>
                <form method="post" id="contactForm">
                    <input type="hidden" id="contactColName" name="colName"/>
                    <input type="hidden" id="contact" name="contactUs"/>
                    <input type="hidden" id="contactCompId" name="id"/>
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <textarea id="contactAreaLightBox"></textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" value="Save" onclick="addContact(); return false;">
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="customHelpContentLightBox">
        <div class="holder">
            <div class="frame">
                <form method="post" id="customHelpContentForm">
	                <div class="title">
	                    <a href="#" class="close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
	                    <div id="customHelpContentTitleDiv">
	                    	<h2 id="helpContentTitleHeader" class="edit" onclick="editHelpContentTitle();">Custom Tab</h2>
	                    	<a class="btn-edit" onclick="editHelpContentTitle(); return false;" href="javascript:void(0)"></a>
	                    </div>
	                    <div id="customHelpContentTitleEditDiv" style="display:none;">
		                    <input class="edit-title" id="helpContentTitle" name="title" onkeypress="if (event.keyCode == 13) {updateHelpContentTitle(); return false;} "/>
	                    	<a class="btn-accept" onclick="updateHelpContentTitle(); return false;" href="javascript:void(0)"></a>
	                    </div>
	                </div>
                    <input type="hidden" id="helpContentId" name="id"/>
                    <input type="hidden" id="helpContentValue" name="content"/>
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <textarea id="customHelpContentText"></textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" value="${saveLabel}" onclick="addCustomHelpContent(); return false;">
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="announcementLightBox">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                    <h2>Announcement</h2>
                </div>

                <form method="post" id="announcementForm">
                    <h3 style="margin-left: 10px !important; width: 160px;"><spring:message code="com.adminui.content_settings.SelectCONTENTGROUP" text="default text" /></h3>
                    <div class="section" id="autocompleteDiv" style="padding-left: 11px;" >
						<select name="mailingList" id="announceUserList" multiple="multiple" title="Basic example">
                            <option id="aid-0"
                                    value="ALL" class="sopper-select" ><spring:message code="com.adminui.content_settings.ALL" text="default text" /></option>
                            <c:forEach var="shopper" items="${shoppers}">
	                             <option id="aid-${shopper.key}" 
	                              value="${shopper.key}" class="sopper-select">${shopper.value}</option>
	                        </c:forEach>
						</select>
                    </div>
                    <h3 style="margin-left: 10px !important; width: 160px; margin-top: 24px;"><spring:message code="com.adminui.content_settings.SELECTACATEGORY" text="default text" /></h3>
                    <div class="section autocompleteDiv" style="margin-top: -30px; padding-left: 180px; width: 250px;" >
                        <select name="type" id="type">
                            <option value="none" ><spring:message code="com.adminui.content_settings.NONE" text="default text" /></option>
                            <option value="attention" ><spring:message code="com.adminui.content_settings.RED" text="default text" /></option>
                            <option value="important" ><spring:message code="com.adminui.content_settings.YELLOW" text="default text" /></option>
                            <option value="success" ><spring:message code="com.adminui.content_settings.GREEN" text="default text" /></option>
                        </select>
                    </div>
                    <input type="hidden" id="announceId" name="announceId"/>
                    <input type="hidden" id="announcement" name="announcement"/>
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <textarea id="announcementAreaLightBox"></textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" value="Save" onclick="addAnnouncement(); return false;">
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="informationLightBox">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                    <h2>Information</h2>
                </div>

                <form method="post" id="informationForm">
                    <h3 style="margin-left: 10px !important; width: 160px;"><spring:message code="com.adminui.content_settings.SelectCONTENTGROUP" text="default text" /></h3>
                    <div class="section" id="autocompleteDiv" style="padding-left: 11px;" >
                        <select name="contentViewGroups" id="infoContentGroupList" multiple="multiple" title="Basic example">
                            <option id="cgid-0"
                                    value="ALL" class="info-select" ><spring:message code="com.adminui.content_settings.ALL" text="default text" /></option>
                            <c:forEach var="cgroup" items="${cvgroups}">
                                 <option id="cgid-${cgroup.key}" 
                                  value="${cgroup.key}" class="info-select">${cgroup.value}</option>
                            </c:forEach>
                        </select>
                     </div>
                    <input type="hidden" id="informationId" name="id"/>
                    <input type="hidden" id="information" name="information"/>
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <textarea id="informationTextArea"></textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" value="Save" onclick="addInformation(); return false;">
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="messageLightBox">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.content_settings.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.content_settings.WelcomeMessage" text="default text" /></h2>
                </div>
                <form method="post" id="messageForm">
                    <%--<h3>Select Search Users</h3>--%>
                    <%--<div class="section" id="autocompleteMessageDiv" style="padding-left: 11px;" >--%>

                        <%--<select name="mailingList" id="messageUserList" multiple="multiple" title="Basic example">--%>
                            <%--<c:forEach var="shopper" items="${shoppers}">--%>
                                <%--<option id="bid-${shopper.key}"--%>
                                        <%--value="${shopper.key}" class="sopper-select">${shopper.value}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</div>--%>

                    <input type="hidden" id="messageId" name="messageId"/>
                    <input type="hidden" id="message" name="message"/>
                    <fieldset>
                        <div class="area">
                            <div class="row">
                                <textarea id="messageAreaLightBox"></textarea>
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.content_settings.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
                            <input type="submit" value="Save" onclick="addMessage(); return false;">
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
