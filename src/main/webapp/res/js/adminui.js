/*  Supplier user methods */

ajaxInTransit = false;
function insertCheckBoxListInForm(form){

    //this wrapped in jQuery will give us the current .checkboxResetForm
    if($('#'+form).find("#checkedItems")==null || $('#'+form).find("#checkedItems").length==0){
        $('#'+form).append("<div id='checkedItems' style='display: none'></div>");

        $('#'+form).append("<div id='status-checkedItems' style='display: none'></div>");

        $('#'+form).append("<div id='status-item-checkedItems' style='display: none'></div>");
    }
}

$( document ).ready(function() {

    $('.checkboxResetForm').each(function(){
        //this wrapped in jQuery will give us the current .checkboxResetForm
        $(this).append("<div id='checkedItems' style='display: none'></div>");
        $(this).append("<div id='status-checkedItems' style='display: none'></div>");
        $(this).append("<div id='status-item-checkedItems' style='display: none'></div>");
    });
    $(".checkboxReset").on('click',function(){
        $(this).parents().find("form #checkedItems").html("");

    });
    $( "#datepicker" ).datepicker();

//    $('#CAT-TYPE-FORM input[id^="CAT-TYPE-ID-"]').each(function() {
//        if(!this.checked){
//            $(this).next().removeClass('ui-state-active');
//        }
//    });
//    $('#supplierContainer input[id^="SUPP-ID-"]').each(function() {
//        if(!this.checked){
//            $(this).next().removeClass('ui-state-active');
//        }
//    });
    if($('#filter').val()!='' && $('#filter').val()!='null' +
        '')
        $('#searchTerm').val($('#filter').val());
    $("#filterAction").val('');
    
});
function filterCatalogsType(obj){
    var catType='';
    $("#filterAction").val('true');

//    if(!(obj.id.split('CAT-TYPE-ID-')[1]==0 && !obj.checked)){
//        catType="0,";
//    }
    $('#CAT-TYPE-FORM input[id^="CAT-TYPE-ID-"]:checked').each(function() {
        catType+=this.id.split('CAT-TYPE-ID-')[1]+',';
    });
    $('#catType').val(catType);

    $('#filter-catalogs').attr("action", catalogfilterUrl);
    $('#filter-catalogs').submit();
}
function filterCatalogsSupplier(obj){
    var supplierId='';
    var oid=obj.id.split('SUPP-ID-')[1];
    var arr="";
    var flag=false;
    var flag2=false;
    var lisr=$('#supplierId').val().split(',');
    for(i = 0; i<lisr.length;i++){
        var id=lisr[i];
        if(obj.checked){
            if(oid==id){
                flag=true;
            }
        }
        else{
            if(oid!=id && id!=''){
                arr+=id+',';
            }
            flag=false;
            flag2=true;
        }
    }
    if(!flag){
        $('#supplierId').val(oid+','+$('#supplierId').val())
    }
    if(flag2){
        $('#supplierId').val(arr);
    }
    $("#filterAction").val('true');
    $('#supplierContainer input[id^="SUPP-ID-"]:checked').each(function() {
        supplierId+=this.id.split('SUPP-ID-')[1]+',';
    });
    $('#supplierId').val(supplierId);
    $('#filter-catalogs').attr("action", catalogfilterUrl);
    $('#filter-catalogs').submit();
}

function filterCatalogs(){
    var  filter = $('#searchTerm').val();
    $('#filter').val(filter);
    $("#filterAction").val('true');
    $('#filter-catalogs').attr("action", catalogfilterUrl);
    $('#filter-catalogs').submit();
}
function resetCheckBoxList(form){
    insertCheckBoxListInForm(form);
    $("#"+form).find("#checkedItems").html('');
    if($("#"+form).find("#status-checkedItems"))
        $("#"+form).find("#status-checkedItems").html('');
    if($("#"+form).find("#status-item-checkedItems"))
        $("#"+form).find("#status-item-checkedItems").html('');

    var checkedItems=$("#"+form+ " input[type='checkbox']");
    $(".checkboxReset").on('click',function(){
        $(this).closest("form #checkedItems").html("");
    });
    for(j=0;j<checkedItems.length;j++){
        var id=checkedItems[j].id;
        if(checkedItems[j]!='' && id!='flag' && id!='text' && id!='mediumtext' && id!='largetext' && id!='list' && id!='fixed'){
            $(checkedItems[j]).attr('checked', false); // Unchecks it
            var lbl=$(checkedItems[j]).parent().find("label");
            $(lbl).removeClass("ui-state-active");
        }
    }
}
function resetFilter(){
    $('#searchTerm').val('');
    $('#filter').val('');
    var catType='';
    $('#CAT-TYPE-FORM input[id^="CAT-TYPE-ID-"]').each(function() {
        //catType+=this.id.split('CAT-TYPE-ID-')[1]+',';
        this.checked = true;
//        $(this).next().addClass("ui-state-active");
    });
    $('#catType').val(catType);
    var supplierId='';
    $('#supplierContainer input[id^="SUPP-ID-"]').each(function() {
        //supplierId+=this.id.split('SUPP-ID-')[1]+',';
        this.checked = true;
        //$(this).next().addClass("ui-state-active");
    });
    $('#supplierId').val(supplierId);

    $("#filterAction").val('true');
    $('#filter-catalogs').attr("action", catalogfilterUrl);
    $('#filter-catalogs').submit();

}
function prepareQueryString(form,varName){
    persistCheckboxStateOnPageChange(form);
    var gIDs='';
    var data=$('#'+form).find("#checkedItems").html().split(",");
    for(j=0;j<data.length;j++){
        var gId=data[j];
        if(gId!='list' &&  gId!='fixed' && gId!='flag' && gId!='text' && gId!='mediumtext' && gId!='largetext')
            gIDs+=varName+'='+gId+'&';
    }
    gIDs=gIDs.substring(0,gIDs.length-1);
    return gIDs;
}

function prepareCSVString(form){
    persistCheckboxStateOnPageChange(form);
    var gIDs='';
    var data=$('#'+form).find("#checkedItems").html().split(",");
    for(j=0;j<data.length;j++){
        var gId=data[j];
        gIDs+='"'+gId+'",';
    }
    gIDs=gIDs.substring(0,gIDs.length-1);
    return gIDs;
}

function prepareCSVStringCustomField2(form){
    persistCheckboxStateOnPageChange(form);
    var gIDs='';
    var data=$('#'+form).find("#checkedItems").html().split(",");
    for(j=0;j<data.length;j++){
        var gId=data[j];
        gIDs+='"'+gId+'",';
    }
    gIDs=gIDs.substring(0,gIDs.length-1);
    return gIDs;
}

function prepareCSVStringCustomField(form){
    persistCheckboxStateOnPageChange(form);
    var gIDs='';
    var data=$('#'+form).find("#checkedItems").html().split(",");
    for(j=0;j<data.length;j++){
        var gId=data[j];
        gIDs+=''+gId.split('_')[1]+',';
    }
    gIDs=gIDs.substring(0,gIDs.length-1);
    return gIDs;
}

function copyCheckedItemList(form,list){
    insertCheckBoxListInForm(form);
    var checkboxList = $('#'+form).find("#checkedItems");
    $(checkboxList).html(list);
    var checkedItems=list.split(",");
    for(j=0;j<checkedItems.length;j++){
        if($('#'+form +' input[value='+checkedItems[j]+']')){
            if(checkedItems[j]!=''){
                $('#'+form +' td input[value='+checkedItems[j]+']').prop('checked', true);
                var lbl=$('#'+form +' td input[value='+checkedItems[j]+']').parent().find("label");
                $(lbl).attr('class',$(lbl).attr('class')+" ui-state-active");
            }
        }
    }
}
function copyCheckedItemListCustomField(form,list){
    insertCheckBoxListInForm(form);
    var checkboxList = $('#'+form).find("#checkedItems");
    $(checkboxList).html(list);
    var checkedItems=list.split(",");
    for(j=0;j<checkedItems.length;j++){
        if($('#'+form +' input[id='+checkedItems[j]+']')){
            if(checkedItems[j]!=''){
                $('#'+form +' td input[id='+checkedItems[j]+']').prop('checked', true);
                var lbl=$('#'+form +' td input[id='+checkedItems[j]+']').parent().find("label");
                $(lbl).attr('class',$(lbl).attr('class')+" ui-state-active");
            }
        }
    }
}
function persistCheckboxStateOnPageChangeCustomField(form,div){

    if($('#'+form).find("#checkedItems")==null){
        insertCheckBoxListInForm(form);
    }
    var checkboxList = $('#'+form).find("#checkedItems");
    if(checkboxList==null)insertCheckBoxListInForm(form);
    checkboxList = $('#'+form).find("#checkedItems");
    var checkboxFormChecked = $('#'+div).find("td input[type='checkbox']:checked");
    var checkboxFormUnChecked = $('#'+div).find("td input[type='checkbox']:not(:checked)");

    $(checkboxFormChecked).each(function(){
        if(!isAlreadySelected($(this).val(),$(checkboxList).html())){
            addValueToListCustomField(this,checkboxList)
        }
    });
    $(checkboxFormUnChecked).each(function(){
        if(isAlreadySelected($(this).val(),$(checkboxList).html())){
            removeValueFromListCustomField(this,checkboxList);
        }
    });
    return $(checkboxList).html();
}

function persistCheckboxStateOnPageChange(form){

    if($('#'+form).find("#checkedItems")==null || $('#'+form).find("#checkedItems").length==0){
        insertCheckBoxListInForm(form);
    }
    var checkboxList = $('#'+form).find("#checkedItems");
    if(checkboxList==null)insertCheckBoxListInForm(form);
    checkboxList = $('#'+form).find("#checkedItems");
    var checkboxFormChecked = $('#'+form).find("td input[type='checkbox']:checked");
    var checkboxFormUnChecked = $('#'+form).find("td input[type='checkbox']:not(:checked)");

    $(checkboxFormChecked).each(function(){
        if(!isAlreadySelected($(this).val(),$(checkboxList).html())){
            addValueToList(this,checkboxList)
        }
    });
    $(checkboxFormUnChecked).each(function(){
        if(isAlreadySelected($(this).val(),$(checkboxList).html())){
            removeValueFromList(this,checkboxList);
        }
    });
    return $(checkboxList).html();
}
function isAlreadySelected(item,list){
    var flag=false;
    if(list!=null){
        var arr=list.split(',');
        for(i=0;i<arr.length;i++){
            if(item==arr[i]){
                flag=true;
                break;
            }
        }
    }
    return flag;
}
function addValueToList(obj,list) {
    if ($(list).html() != '') {
        $(list).html($(list).html() + ',' + $(obj).val());
        if ($('input[id=status-' + $(obj).val() + ']')) {
            $("#status-checkedItems").html($("#status-checkedItems").html() + ',' + $('input[id=status-' + $(obj).val() + ']').val());
        }
        if ($('input[id=status-item-' + $(obj).val() + ']')) {
            $("#status-item-checkedItems").html($("#status-item-checkedItems").html() + ',' + $('input[id=status-item-' + $(obj).val() + ']').val());
        }
    }
    else {
        $(list).html($(obj).val());
        if ($('input[id=status-' + $(obj).val() + ']')) {
            $("#status-checkedItems").html($('input[id=status-' + $(obj).val() + ']').val());
        }
        if ($('input[id=status-item-' + $(obj).val() + ']')) {
            $("#status-item-checkedItems").html($('input[id=status-item-' + $(obj).val() + ']').val());
        }
    }
}

function addValueToListCustomField(obj,list) {
    if ($(list).html() != '') {
        $(list).html($(list).html() + ',' + $(obj).prop('id'));
        if ($('input[id=status-' + $(obj).prop('id') + ']')) {
            $("#status-checkedItems").html($("#status-checkedItems").html() + ',' + $('input[id=status-' + $(obj).prop('id') + ']').val());
        }
        if ($('input[id=status-item-' + $(obj).prop('id') + ']')) {
            $("#status-item-checkedItems").html($("#status-item-checkedItems").html() + ',' + $('input[id=status-item-' + $(obj).prop('id') + ']').val());
        }
    }
    else {
        $(list).html($(obj).prop('id'));
        if ($('input[id=status-' + $(obj).prop('id') + ']')) {
            $("#status-checkedItems").html($('input[id=status-' + $(obj).prop('id') + ']').val());
        }
        if ($('input[id=status-item-' + $(obj).prop('id') + ']')) {
            $("#status-item-checkedItems").html($('input[id=status-item-' + $(obj).prop('id') + ']').val());
        }
    }
}

function removeValueFromList(checkbox,list) {
    var input_status = $("#status-checkedItems").html();
    var input_status_item = $("#status-item-checkedItems").html();
    var input = $(list).html();
    var items = input.split(',');
    var output = '';
    var pointer = 0;
    var selIndex = -1;
    $.each(items, function (index, value) {
        if ($(checkbox).val() == value) {
            selIndex = pointer;
        }
        else {
            output += value + ",";
        }
        pointer++;
    });
    output = output.substring(0, output.length - 1);
    $(list).html(output);
    if (input_status != '') {
        output = '';
        var arr = input_status.split(',');
        $.each(arr, function (index, value) {
            if (index != selIndex) {
                output = arr[index] + ",";
            }
        });
        $("#status-checkedItems").html(output);
    }
    if (input_status_item != '') {
        output = '';
        var arr = input_status_item.split(',');
        $.each(arr, function (index, value) {
            if (index != selIndex) {
                output = arr[index] + ",";
            }
        });
        $("#status-item-checkedItems").html(output);
    }
}

function removeValueFromListCustomField(checkbox,list) {
    var input_status = $("#status-checkedItems").html();
    var input_status_item = $("#status-item-checkedItems").html();
    var input = $(list).html();
    var items = input.split(',');
    var output = '';
    var pointer = 0;
    var selIndex = -1;
    $.each(items, function (index, value) {
        if ($(checkbox).id == value) {
            selIndex = pointer;
        }
        else {
            output += value + ",";
        }
        pointer++;
    });
    output = output.substring(0, output.length - 1);
    $(list).html(output);
    if (input_status != '') {
        output = '';
        var arr = input_status.split(',');
        $.each(arr, function (index, value) {
            if (index != selIndex) {
                output = arr[index] + ",";
            }
        });
        $("#status-checkedItems").html(output);
    }
    if (input_status_item != '') {
        output = '';
        var arr = input_status_item.split(',');
        $.each(arr, function (index, value) {
            if (index != selIndex) {
                output = arr[index] + ",";
            }
        });
        $("#status-item-checkedItems").html(output);
    }
}

function resetCatalogForm(){
    $("#theCatalogId").reset();
}
function onSupplierCheckboxClick(control){}
function handleSearchAllowed(){
    if($('#check-external-catalog').is(":checked")){
        $('#search-allowed-check').show();
    }else{
        $('#search-allowed-check').hide();
    }
}

function showVersionMessageCat(){	
    if($('#catalogStateIdHidden').val()==4 || $('#catalogStateIdHidden').val()==2){
        $('#newVersionMessage').show();
    }
}

function validateUserProfile(){

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

function initSupUserPopup(){
    clearSupUserErrorMessages();
    $('input#sup-first-name').val('');
    $('input#sup-last-name').val('');
    $('input#sup-e-mail').val('');
}

function clearSupUserErrorMessages(){
    $('#sup-first-name-div span').remove();
    $('#sup-last-name-div span').remove();
    $('#sup-e-mail-div span').remove();
}


function goToSupplierUserPage(pageNum, supplierId){
    var totalPageNum = $('input#supplierUserTotalPageNum').val();

    if( (pageNum < 1 || pageNum > totalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    try{
        $.ajax({
            type:'POST',
            url: 'getsupplieruserpage?pagenum=' + pageNum +'&supplierid=' + supplierId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(data != ''){
                    $('#supplier-user-data').empty();
                    $("#supplier-user-data").html(data);
                    ajaxInTransit = false;
                    $.colorbox.close();
                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function deleteSupplierUsers() {
    try{
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var pageNum = $('#supplierUserCurrentPageNum').val();

        $.ajax({
            type:'POST',
            url: 'deletesupplieruser?pageNum='+pageNum+'&userSupplierId='+$('#userSupplierId').val()+'&uniquesupplierid='+$("#uniqueSupplierId").val(),
            data: $('#supplier-users-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    $('#supplier-user-data').empty();
                    $("#supplier-user-data").html(data);
                    ajaxInTransit = false;
                    $.colorbox.close();
                }
            },
            error: function (error){
                alert(error);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function validateSupUser(username,userid,pass){
    var pattern = /[a-zA-Z_+0-9.\-]+@[a-zA-Z0-9._\-]+?\.[a-zA-Z]{2,3}$/;
    if($('#sup-first-name').val() == ''){
        $('#sup-first-name-div span').remove();
        $('#sup-first-name-div').append('<span class="error-msg" style="display: block; top:25px;width:315px" xmlns="http://www.w3.org/1999/html">You must enter First Name</span>');
        return false;
    }else if($('#sup-last-name').val() == ''){
        $('#sup-last-name-div span').remove();
        $('#sup-last-name-div').append('<span class="error-msg" style="display: block; top:25px;width:315px" xmlns="http://www.w3.org/1999/html">You must enter Last Name</span>');
        return false;
    }else if(username!=username.match(pattern)){
        $('#sup-e-mail-div span').remove();
        $('#sup-e-mail-div').append('<span class="error-msg" style="display: block; top:25px;width:315px" xmlns="http://www.w3.org/1999/html">Please enter a valid email address</span>');
        return false;
    }else if(supUserExists(username, userid)){
        $('span#createSupUserReplyDiv').html("<br />User with name of '" + username + "' already exists");
        return false;
    }else if((pass.length > 0 && pass.length < 8) || (pass.length > 0 && pass.match(/^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]+$/i)==null)){
        $('span#createSupUserReplyDiv').html("<br />Password must be alphanumeric and 8 characters long.");
        return false;
    }
    return true;

}


function supUserExists(username, userId) {
    var userExists = false;
    $.ajax({
        type:'POST',
        url: 'usernameExists',
        data: 'userId='+userId+'&username=' + username,
        async: false,
        success: function(response) {
            userExists = (response == 'true')? true : false;
        }
    });
    return userExists;
}



function updateSupplierUsers(active) {
    try{
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var pageNum = $('#supplierUserCurrentPageNum').val();

        $.ajax({
            type:'POST',
            url: 'updatesupplieruser?active=' + active + '&pageNum=' + pageNum+'&userSupplierId='+$('#userSupplierId').val()+'&uniquesupplierid='+$("#uniqueSupplierId").val(),
            data: $('#supplier-users-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    $('#supplier-user-data').empty();
                    $("#supplier-user-data").html(data);
                    ajaxInTransit = false;
                    $.colorbox.close();
                }
            },
            error: function (error){
                alert(error);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function createSupplierUser() {
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    if(!validateSupUser($("#sup-e-mail").val(),$("#userId").val(),$("#pass").val())){
        ajaxInTransit = false;
        return;
    }
    var emailMessage = "";
    if(document.getElementById("sendNotification").checked){
        emailMessage = CKEDITOR.instances['notificationBody'].getData();
    }
    try{
        $.ajax({
            type:'POST',
            url: 'addsupplieruser?uniquesupplierid='+$("#uniqueSupplierId").val()+"&message="+emailMessage+"&notify="+document.getElementById("sendNotification").checked+"&password="+$("#pass").val(),
            data: $('#createSupplierUser').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {



                $("#supplier-user-data").html(jqXHR.responseText);
                $("#suppAdminTitle").html('Create Supplier User');
                //$('#tblSupplierUsers > tbody:last').append("<tr><td>asdasd</td><td>adasdasd</td><td>asdasdasd</td><td>asdadadad</td></tr>");
                ajaxInTransit = false;
                if(document.getElementById("sendNotification").checked){
                    CKEDITOR.instances['notificationBody'].destroy();
                }
                $('#sendNotification').prop('checked', false);
                $("#notiLabel").removeClass('ui-state-active');
                $('#notificationBody').hide();
                $('#notificationBody').val("");
                $('#pass').val("");
                $('#sendEmailDiv').hide();
                $.colorbox.close();
                initSupUserPopup();
            },
            error: function (error){
                ajaxInTransit = false;
                $.colorbox.close();
            }
        });
        $('#sup-first-name').val('');
        $('#sup-last-name').val('');
        $("#sup-e-mail").val('');
        $("#supplierid").val('');
        $("#userId").val('');
        $("#pass").val('');
    }catch(exp){
        alert(exp);
    }
    return false;
}


// ------------------------------------


function changeCatalogState(state,catalogid,columnid){
    $.ajax({
        type:'POST',
        url : "updateCatalogState"+"?catalogId="+catalogid+"&state="+state,
        dataType : 'text',
        success: function (data, textStatus, jqXHR){
            var catalogDiv = document.getElementById(catalogid);

            if(state=='D'){
                $('#'+catalogid).find('#lnkDeactivate').hide();
                $('#'+catalogid).find('#lnkActivate').show();
                $('#'+catalogid).find('#lnkActivate').show();
                $('#'+catalogid).find('#'+catalogid+'catStatus').show();
                $('#'+catalogid).find('#'+catalogid+'catStatus').html('<span>Off-Line</span>');
                $('#'+catalogid).find('#spnActiveSts').html('Not Active');
                $('#'+catalogid).find('#'+catalogid+'approvedStatus').html('Off-Line');

            }else{
                $('#'+catalogid).find('#lnkDeactivate').show();
                $('#'+catalogid).find('#lnkActivate').hide();
                $('#'+catalogid).find('#spnActiveSts').html('Active');
                $('#'+catalogid).find('#'+catalogid+'catStatus').hide();
                $('#'+catalogid).find('#'+catalogid+'approvedStatus').html('Published');
            }

        },
        error : function(error){
            alert("Unexpected Error happend. Please try later.");
        }
    });

//
}

/*  ********************************** PROFILE GROUP FUNCTIONS START HERE ***************/
function goToNAProfilePage(pageNum){
    try{
        var list=persistCheckboxStateOnPageChange('associateProfileToGroup');
        //var pageNum = $('#catalogCurrentPageNum').val();
        //groupProfileTotalPageNum
        var groupProfileTotalPageNum = $('input#profilesTotalPageNum').val();

        if( (pageNum < 1 || pageNum > groupProfileTotalPageNum) || ajaxInTransit) return;

        ajaxInTransit = true;

        var sortDirection = $('#sortDirectionP').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinProfileTextId1').val();
        var groupId = $('#groupId').val();

        $.ajax({
            type:'POST',
            url: notAssociatedProfilePageServiceUrl +'?pageNum=' + pageNum  +'&profileGroupId=' + groupId +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: $('#associateProfileToGroup').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);
                    $("#group_profiles-holder").html(jqXHR.responseText);
                    copyCheckedItemList('associateProfileToGroup',list);
                    ajaxInTransit = false;
                }
            },
            error: function (error){
                //alert(jqXHR.responseText);
                alert(error);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToNextNAProfilePage(){
    var currentPageNum = parseInt( $('#groupProfileCurrentPageNum').val() );
    currentPageNum = currentPageNum + 1;
    goToNAProfilePage(currentPageNum);
}

function goToPrevNAProfilePage(){
    var currentPageNum = parseInt( $('#groupProfileCurrentPageNum').val() );
    currentPageNum = currentPageNum - 1;
    goToNAProfilePage(currentPageNum);
}


function searchWithinNAProfiles(){
    try{
        if( ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirectionP').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinProfileTextId1').val();
        var groupId = $('#groupId').val();

        $.ajax({
            type:'POST',
            url: searchWithinNAProfileServiceUrl + '?profileGroupId=' + groupId +'&pageNum=' + 1 +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#group_profiles-holder").html(jqXHR.responseText);

                    ajaxInTransit = false;
                }
            },
            error: function (){
                //alert(jqXHR.responseText);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function sortNAProfiles(){
    try{
        if( ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirectionP').val();
        if(sortDirection == 'up') sortDirection = 'down'; else sortDirection = 'up';
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinProfileTextId1').val();
        var profileGroupId = $('#groupId').val();

        $.ajax({
            type:'POST',
            url: sortNAGroupProfileServiceUrl + '?profileGroupId=' + profileGroupId +'&pageNum=' + 1 +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#group_profiles-holder").html(jqXHR.responseText);

                    ajaxInTransit = false;
                }
            },
            error: function (){
                //alert(jqXHR.responseText);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function sortGroupProfiles(){
    try{
        if( ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirection2').val();
        if(sortDirection == 'up') sortDirection = 'down'; else sortDirection = 'up';
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var groupId = $('#groupId').val();

        $.ajax({
            type:'POST',
            url: sortGroupProfileServiceUrl+ '?profileGroupId=' + groupId +'&pageNum=' + 1 +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#group_profile_ass_holder").html(jqXHR.responseText);
                    $("#btnRemove").show();
                    ajaxInTransit = false;
                }
            },
            error: function (){
                //alert(jqXHR.responseText);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function searchWithinProfileGroups(){
    try{
        if(ajaxInTransit) return;

        ajaxInTransit = true;

        var viewFilter = getProfileGroupFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortBy= $('#sortBy').val();
        var sortDirection = $('#sortDirection1').val();
        var searchWithin = $('#searchWithinId1').val();

        $.ajax({
            type:'POST',
            url: searchWithinProfileGroupServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter + '&sortBy=' + sortBy + '&sortDirection=' + sortDirection + '&searchWithin=' + searchWithin,
            data: $('#searchWithinProfileGroupForm').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_group_page_container_div').empty();
                    $('#profile_group_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function sortProfileGroups(sortBy){
    try{
        if(ajaxInTransit) return;

        ajaxInTransit = true;

        var viewFilter = getProfileGroupFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection1').val();
        if(sortDirection == 'up') sortDirection = 'down'; else sortDirection = 'up';
        var searchWithin = $('#searchWithinId1').val();

        $.ajax({
            type:'POST',
            url: sortProfileGroupsServiceUrl+'?pageNum=' + pageNum +'&viewFilter=' + viewFilter + '&sortBy=' + sortBy + '&sortDirection=' + sortDirection + '&searchWithin=' + searchWithin,
            data: $('#profiles-group-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_group_page_container_div').empty();
                    $('#profile_group_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}



function disAssociateGroupProfile() {
    try{
        var gIDs=prepareQueryString('associatedProfilesToGroupForm','associatedProfileIds');
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var profileGroupId = $('#groupId').val();

        $.ajax({
            type:'POST',
            url: disAssociateGroupProfileServiceUrl + '?profileGroupId=' + profileGroupId + '&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(jqXHR.responseText);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedProfiles = responseArray[0];      // associatedCatalog info
                var nonAssociatedProfiles = responseArray[1];   // nonAssociatedCatalog data
                $("#group_profile_ass_holder").html(associatedProfiles);
                $("#group_profiles-holder").html(nonAssociatedProfiles);
                $("#btnRemove").show();
                $.colorbox.close();
                ajaxInTransit = false;


                resetCheckBoxList('associatedProfilesToGroupForm');
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToGroupProfilePage(pageNum){
    var groupProfileTotalPageNum = $('input#groupProfileTotalPageNum').val();
    var list=persistCheckboxStateOnPageChange('associatedProfilesToGroupForm');
    if( (pageNum < 1 || pageNum > groupProfileTotalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    var sortDirection = $('#sortDirection').val();
    var sortBy= $('#sortBy').val();
    var searchWithin = $('#searchWithinCatalogTextId').val();
    var groupId = $('#groupId').val();

    try{
        $.ajax({
            type:'POST',
            url: goToGroupProfilePageServiceUrl +'?pageNum=' + pageNum +'&profileGroupId=' + groupId + '&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#group_profile_ass_holder").html(jqXHR.responseText);
                    $("#btnRemove").show();
                    ajaxInTransit = false;
                    copyCheckedItemList('associatedProfilesToGroupForm',list);

                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToGroupPage(pageNum){
    var list=persistCheckboxStateOnPageChange('profiles-group-form');
    var groupProfileTotalPageNum = $('input#groupProfileTotalPageNum').val();
    if( (pageNum < 1 || pageNum > groupProfileTotalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    var viewFilter = getProfileGroupFilterOption();
    var sortDirection = $('#sortDirection').val();
    var sortBy= $('#sortBy').val();
    var searchWithin = '';

    try{
        $.ajax({
            type:'POST',
            url: goToPageProfileGroupsServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_group_page_container_div').empty();
                    $('#profile_group_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();
                    copyCheckedItemList('profiles-group-form',list);
                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function deleteProfileProfiles(btnDelete) {
    var gIDs='';
    if(!isAnyItemChecked('profiles-form')) {
        alert('Please select an item');
        return;
    }else{
        handleDeleteMenuActionProfiles('lightbox-delete-profile-profiles', btnDelete, deleteActualProfileProfiles);
    }
}

function deleteProfileGroupsConfirm() {
    if(!isAnyItemChecked('profiles-group-form')) {
        alert('Please select an item');
        return;
    }
    deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', deleteProfileGroups);
}

function deleteProfileGroups() {
    try{
        var gIDs=prepareQueryString('profiles-group-form','profileGroupIds');
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getProfileGroupFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = '';

        $.ajax({
            type:'POST',
            url: deleteProfileGroupsServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_group_page_container_div').empty();
                    $('#profile_group_page_container_div').append(jqXHR.responseText);

                    $('#activeProfileGroupsCount').html($('input#numOfActiveProfileGroup').val());
                    $('#totalProfileGroupsCount').html($('input#totalProfilesGroupCount').val());
                    $('#groupProfileTotalPageNum').html($('input#groupProfileTotalPageNum').val());

                    $('#inactiveProfileGroupsCount').html($('input#totalProfilesGroupCount').val() - $('input#numOfActiveProfileGroup').val());

                    reAttachProfileHandlers();
                    resetCheckBoxList('profiles-group-form');

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function activeProfileGroups(active) {
    try{
        var gIDs=prepareQueryString('profiles-group-form','profileGroupIds');
        //gIDs+='&active='+active;
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getProfileGroupFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = '';

        $.ajax({
            type:'POST',
            url: activeProfileGroupsServiceUrl+'?active='+active+'&pageNum=' + pageNum +'&viewFilter=' + viewFilter  +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_group_page_container_div').empty();
                    $('#profile_group_page_container_div').append(jqXHR.responseText);

                    $('#activeProfileGroupsCount').html($('input#numOfActiveProfileGroup').val());
                    $('#totalProfileGroupsCount').html($('input#totalProfilesGroupCount').val());
                    $('#inactiveProfileGroupsCount').html($('input#totalProfilesGroupCount').val() - $('input#numOfActiveProfileGroup').val());

                    reAttachProfileHandlers();
                    resetCheckBoxList('profiles-group-form');

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function associateProfiletoGroup() {
    try{
        if(!isAnyItemChecked('associateProfileToGroup')){
            alert('Please select an item');
            return;
        }
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinProfileTextId').val();
        var groupId = $('#groupId').val();
        var gIDs=prepareQueryString('associateProfileToGroup','associatedProfileIds');

        gIDs+='&groupId='+groupId;
        $.ajax({
            type:'POST',
            url: associateProfileToGroupServiceUrl + '?groupId=' + groupId + '&profileGroupId=' + groupId + '&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(jqXHR.responseText);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedProfiles = responseArray[0];      // associatedCatalog info
                var nonAssociatedProfiles = responseArray[1];   // nonAssociatedCatalog data
                $("#group_profile_ass_holder").html(associatedProfiles);
                $("#group_profiles-holder").html(nonAssociatedProfiles);
                $("#btnRemove").show();
                $.colorbox.close();
                ajaxInTransit = false;
                resetCheckBoxList('associateProfileToGroup');
                resetCheckBoxList('associatedProfilesToGroupForm');

            },
            error: function (){
                ajaxInTransit = false;
                alert("Error");
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function saveProfileGroup(){
    var groupId = $('#profileGroupId').val();
    var groupName = $('#profile-group-name').val();
    var groupToken = $('#profile-group-token').val();
    groupName = $.trim(groupName);
    $('#profile-group-name').val(groupName);

    if(validatePofileGroup(groupId,groupName,groupToken)){
        createNewProfileGroup();
    }

}


function validatePofileGroup(groupId, groupName , groupToken){
    if(groupName == ''){
        $('#profile-group-name-div').append('<span class="error-msg" style="display: block; top:25px;width:315px" xmlns="http://www.w3.org/1999/html">You must enter a Valid Content View Group Name</span>');
        return false;
    }else if(profileGroupExists(groupId, groupName)){
        $('span#createProfileGroupReplyDiv').html("<br />Content View Group with name of '" + groupName + "' already exists");
        return false;
    }else if(profileGroupTokenExists(groupId, groupToken)){
        $('span#createProfileGroupReplyDiv').html("<br />Content View Group with token of '" + groupToken + "' already exists");
        $("#lnkNewToken").show();
        return false;

    }
    return true;
}

function createToken(){

    $.ajax({
        type:'GET',
        url: profileGroupNewTokenServiceUrl,
        async: false,
        success: function(response) {
            $('#profile-group-token').val(response);
            $('#profile-group-token2').val(response);
            $("#lnkNewToken").hide();
            $('span#createProfileGroupReplyDiv').html("");
        }
    });
}

function profileGroupTokenExists(groupId, groupToken) {
    var profileGroupTokenExists = false;
    $.ajax({
        type:'POST',
        url: profileGroupTokenExistsServiceUrl,
        data:'groupId=' + groupId + '&token=' + groupToken,
        async: false,
        success: function(response) {
            profileGroupTokenExists = (response == 'true')? true : false;
        }
    });
    return profileGroupTokenExists;
}

function profileGroupExists(groupId, groupName) {
    var profileGroupExists = false;
    $.ajax({
        type:'POST',
        url: profileGroupExistsServiceUrl,
        data:'groupId=' + groupId + '&groupName=' + groupName,
        async: false,
        success: function(response) {
            profileGroupExists = (response == 'true')? true : false;
        }
    });
    return profileGroupExists;
}

function getProfileGroupFilterOption(){
    var bViewActiveProfileGroup= $('#viewActiveProfileGroup').is(':checked');
    var bViewInactiveProfileGroup= $('#viewInactiveProfileGroup').is(':checked');
    var viewFilter = null;
    if(!bViewActiveProfileGroup && bViewInactiveProfileGroup) viewFilter = "INACTIVE";
    if(bViewActiveProfileGroup && !bViewInactiveProfileGroup) viewFilter = "ACTIVE";
    if(!bViewInactiveProfileGroup && !bViewActiveProfileGroup) viewFilter = "ALL";
    if(bViewInactiveProfileGroup && bViewActiveProfileGroup) viewFilter = "NONE";

    return viewFilter
}

function filterProfileGroupView(){
    if(ajaxInTransit) return;

    ajaxInTransit = true;

    var viewFilter = getProfileGroupFilterOption();
    var pageNum = $('#currentPageNum').val();
    var searchWithin = '';
    try{
        $.ajax({
            type:'POST',
            url: filterProfileGroupsServiceUrl +'?pageNum=1&viewFilter=' + viewFilter + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_group_page_container_div').empty();
                    $('#profile_group_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function createNewProfileGroup(){
    var flag = true;
    try{
        $('#createNewProfileGroup').submit();

    }catch(exp){
        alert(exp);
    }
    return false;
}

/*  ********************************** PROFILE GROUP FUNCTIONS START HERE ***************/

var externalRowIncrementId = 0;
var cXMLDefaultFields = ["URL", "FROM_DOMAIN", "FROM_IDENTITY" , "TO_DOMAIN", "TO_IDENTITY", "SENDER_DOMAIN", "SENDER_IDENTITY","SHARED_SECRET"];
var cxmlToken = true;
$(document).ready(function() {
    var options = {
        beforeSubmit:  showRequest,  // pre-submit callback
        success:       showResponse,  // post-submit callback
        uploadProgress: uploadProgress
    };
    // bind form using 'ajaxForm'
    $('#edit-catalog-form').ajaxForm(options);

    var options2 = {
        beforeSubmit:  showRequest2,  // pre-submit callback
        success:       showResponse2,  // post-submit callback
        uploadProgress: uploadProgress2
    };
    // bind form using 'ajaxForm'
    $('#theCatalogId').ajaxForm(options2);


    //$("#create-catalog-btn-reset").click(function() {
//        if('editCatalog' == $('#theCatalogId').get(0).getAttribute('action')){
//            populateCatalogForm(gCurrentCatalog);
//        }
    //});

   
    // Add external catalog field row
    $("#btn-add-external-catalog-id").click(function() {

//         var master = $("#table-external-catalog-id")
//        // Get a new row based on the prototype row
//        var prot = master.find(".added").clone();
//        prot.attr("class", "")
//        //contacts[${status.index}].firstname
//        prot.find(".target-chbox").attr("id", "check10-"+id);
//        prot.find(".target-chbox").attr("name", "check10_"+id);
//        prot.find(".field-name").attr("id", "field-name-"+id);
//        prot.find(".field-name").attr("name", "fieldName_"+id);
//        prot.find(".field-value").attr("id", "field-value-"+id);
//        prot.find(".field-value").attr("name", "fieldValue_"+id);//
//        prot.find(".v-sel").attr("id", "dynamic-value-"+id);
//        prot.find(".v-sel").attr("name", "dynamicValue_"+id);
//        //master.find("tbody").append(prot);
//        $('#table-external-catalog-id tr:last').after(prot);

        addExternalCatalogRowToTable(externalRowIncrementId);
        externalRowIncrementId++;
        //var clonedRow = $('.added').clone().html();
        //var appendRow = '<tr class = ".added">' + clonedRow + '</tr>';
        // $('#table-external-catalog-id tr:last').after(appendRow);
        //$('#table-external-catalog-id").jcf.refreshState();
        //$('#table-external-catalog-id').jcf.refreshState();
    });

//    // Remove
    
    $("#external-catalog-remove").click(function(){
        $("#table-body-external-catalog :checkbox").each(
            function() {
                if($(this).is(":checked")){
                    var rowId = $(this).attr('id')
                    //   alert(rowId.substring(8,rowId.length));
                    deleteExternalCatalogRow(rowId.substring(8,rowId.length));
                    externalRowIncrementId--;
                }
            });
    });

    $('#update-external-fields-form').ajaxForm();
});

function deleteExternalCatalogRow(rowIndex){
    var row1 = 'row_'+ rowIndex+"_1";
    $("#"+row1).remove();
    var row = 'row_'+ rowIndex;
    $("#"+row).remove();
}

function onSelectingCXML(){

	if($("#extCommunicationMethod").val()== 'CXML' && cxmlToken && !$("#table-body-external-catalog").html()){
		cxmlToken= false;
		for (var i = 0; i < cXMLDefaultFields.length; i++) {

			var $row = $('<tr class="added" id="row_' + externalRowIncrementId
					+ '"></tr>');

			var $cell0 = $('<td class="td-select"><input type="checkbox" name="'
					+ externalRowIncrementId
					+ ' " class="target-chbox" id="check10-'
					+ externalRowIncrementId
					+ '"/><label for="check'
					+ externalRowIncrementId
					+ '" id="lcheck'
					+ externalRowIncrementId + '"></label></td>');

			var $cell1 = $('<td class="a-left td-sequence-catalog"><div><input class="field-sequence" type="text" style="width:14px;" value="'
					+ externalRowIncrementId
					+ '" name="fields['
					+ externalRowIncrementId + '].sequence" /></div></td>');
			var $cell2 = $('<td class="a-left td-name-catalog"><div><input id="name-'
					+ externalRowIncrementId
					+ '" class="field-name" type="text" value="'
					+ cXMLDefaultFields[i]
					+ '" name="fields['
					+ externalRowIncrementId + '].name" /></div></td>');
			var $cell3 = $('<td><div><input class="field-value" type="text" onblur="resetToValueDefault('
					+ externalRowIncrementId
					+ ')" onfocus="clearValue('
					+ externalRowIncrementId
					+ ');" onkeyup="disableCombo('
					+ externalRowIncrementId
					+ ')" id="id-'
					+ externalRowIncrementId
					+ '" value="Enter Value" name="fields['
					+ externalRowIncrementId
					+ '].value " /><label style="margin-top: 6px" class="td-select"> OR </label></div></td>');
			var $cell4 = $('<td><div> <select onchange="disableInput('
					+ externalRowIncrementId
					+ ')" class="field-dynamic-value" id="s-val-'
					+ externalRowIncrementId
					+ '" name ="fields['
					+ externalRowIncrementId
					+ '].dynamicValue "><option value=""> Select Dynamic Value</option><option  value="NEW_ITEM-VENDORMAT">NEW_ITEM-VENDORMAT</option><option value="SY-UNAME">SY-UNAME</option></select></div></td>');

			$row.append($cell0);
			$row.append($cell1);
			$row.append($cell2);
			$row.append($cell3);
			$row.append($cell4);
			$("#table-external-catalog-id").append($row);

			externalRowIncrementId++;
		}
	}

	if($("#extCommunicationMethod").val()== 'CXML'){
		$("#encodingTypeField").show();
	}else{
		$("#encodingTypeField").hide();
	}
} 

function addExternalCatalogRowToTable(intIndex){
    var $row = $('<tr class="added" id="row_'+ intIndex +'"></tr>');

    //var $cell0 = $('<td class="td-select"><a href="#" class="btn-up-down"><span class="up-arrow">up</span> <span class="down-arrow">down</span></a> <input type="checkbox" name="'+ intIndex +' " class="target-chbox" id="check10-'+ intIndex +'"/><label for="check'+ intIndex +'" id="lcheck'+ intIndex +'"></label></td>');
    var $cell0 = $('<td class="td-select"><input type="checkbox" name="'+ intIndex +' " class="target-chbox" id="check10-'+ intIndex +'"/><label for="check'+ intIndex +'" id="lcheck'+ intIndex +'"></label></td>');

    //var $cell = $('<td class="td-select"><a href="#" class="btn-up-down"><span class="up-arrow">up</span> <span class="down-arrow">down</span></a><input type="checkbox" name="check10" class="target-chbox" id="check10-'+ intIndex +'" /></td>');
    var $cell1 =$('<td class="a-left td-sequence-catalog"><div><input class="field-sequence" type="text" style="width:14px;" value="'+ intIndex +'" name="fields['+ intIndex +'].sequence" /></div></td>');
    var $cell2 =$('<td class="a-left td-name-catalog"><div><input onblur="resetToDefault('+intIndex+')" onfocus="clearName('+intIndex+');" id="name-' +intIndex+ '" class="field-name" type="text" value="Enter Name" name="fields['+ intIndex +'].name" /></div></td>');
    var $cell3 =$('<td><div><input class="field-value" type="text" onblur="resetToValueDefault('+intIndex+')" onfocus="clearValue('+intIndex+');" onkeyup="disableCombo('+intIndex+')" id="id-' + intIndex + '" value="Enter Value" name="fields['+ intIndex +'].value " /><label style="margin-top: 6px" class="td-select"> OR </label></div></td>');
    var $cell4 =$('<td><div> <select onchange="disableInput('+intIndex+')" class="field-dynamic-value" id="s-val-'+intIndex+'" name ="fields['+ intIndex +'].dynamicValue "><option value=""> Select Dynamic Value</option><option  value="NEW_ITEM-VENDORMAT">NEW_ITEM-VENDORMAT</option><option value="SY-UNAME">SY-UNAME</option></select></div></td>');

    //var $cell4 =$('<td><div class="jcf-unselectable select-field-dynamic-value select-area" style="width: 151px;"><span class="left"></span><span class="center jcf-unselectable">Or Select Dynamic Value</span><a class="select-opener"></a></div><select class="field-dynamic-value jcf-hidden" id="s-val" name="fields[1].dynamicValue "><option value="">Or Select Dynamic Value</option><option value="NEW_ITEM-VENDORMAT">NEW_ITEM-VENDORMAT</option><option value="SY-UNAME">SY-UNAME</option></select></div></td>');


    $row.append($cell0);
    $row.append($cell1);
    $row.append($cell2);
    $row.append($cell3);
    $row.append($cell4);
    $("#table-external-catalog-id").append($row);
}


function resetToDefault(fieldId){

    if($('#name-'+fieldId).val()==null || $('#name-'+fieldId).val()==''){
        $('#name-'+fieldId).val('Enter Name')
    }

}

function clearName(fieldId){

    if($('#name-'+fieldId).val()=='Enter Name'){
        $('#name-'+fieldId).val('')
    }

}


function resetToValueDefault(fieldId){

    if($('#id-'+fieldId).val()==null || $('#id-'+fieldId).val()==''){
        $('#id-'+fieldId).val('Enter Value')
    }

}

function clearValue(fieldId){

    if($('#id-'+fieldId).val()=='Enter Value'){
        $('#id-'+fieldId).val('')
    }

}

function submitAccountInfo(){
    $('span#MyAccountUpdateFeedbackDiv').html("");

    if($('input[id=pwdNew]').val() != $('input[id=pwdNew2]').val()){
        alert("Password do not match");
        return;
    }

    if(ajaxInTransit) return;
    ajaxInTransit = false;
    $.ajax({
        type:'POST',
        url : "myAccount?newPassword="+$('input[id=pwdNew]').val(),
        data: $('#frmMyAccountDetails').serialize(),
        dataType : 'text',
        success: function(data, textStatus, jqXHR) {
            //alert(jqXHR.responseText);
            if(jqXHR.responseText.indexOf("Error") == -1){
                $('span#MyAccountUpdateFeedbackDiv').html("<br />Submission was successful");
                window.location.reload(true);
            }else{
                $('span#MyAccountUpdateFeedbackDiv').html("<br />Submission Failed: " + data);
            }
            ajaxInTransit = false;
        },
        error: function (){
            ajaxInTransit = false;
        }
    });

}

function getUrlParamValue(fieldNames, fieldValues){
    for(var i = 0; i < fieldNames.length; ++i){
        if(fieldNames[i].toUpperCase() == 'URL') return i;
    }
    return -1;

}


function openLink(linkValue) {
    if (linkValue != "" && linkValue != "#") {
        var leftPosition = (screen.width) ? (screen.width - 1000) / 2 : 0;
        var topPosition = (screen.height) ? (screen.height - 700) / 2 : 0;
        var features = "width=" + 1000 + ", height=" + 700 + ",scrollbars=yes,status=no,toolbar=no,menubar=no,location=no, top=" + topPosition + ",left=" + leftPosition;
        features += ",top=" + topPosition + ",left=" + leftPosition;
        var character = '&';

        if (linkValue.indexOf('?') < 0)
            character = '?';
        if(linkValue.indexOf("HOOK_URL=") < 0){
            linkValue+="&HOOK_URL="+$("#hdnURL").val();
        }
        win = window.open(linkValue + character, null, features);

    }
}

function createExternalCatalogOverlayUrl(punchOutUrl){
    var method = $('#extCatalogMethod').val();
    var extComMethod = $("#extCommunicationMethod").val();
    if(extComMethod == "CXML"){
        createExternalCatalogCXML(punchOutUrl, method, '#table-body-external-catalog ');
    }else{
        if(method.indexOf('POST') == -1) {
            createExternalCatalogGet(method, '#table-body-external-catalog ');
        } else {
            createExternalCatalogPost(method, '#table-body-external-catalog ');
        }
    }
}
function createExternalCatalogUrl(punchOutUrl){
    var method = $('#extCatalogMethodSelect').val();
    var extComMethod = $("#extCommunicationMethodInput").val();
    if(extComMethod == "CXML"){
        createExternalCatalogCXML(punchOutUrl, method, '#table-body-external-catalog2 ');
    }else{
        if(method.indexOf('POST') == -1) {
            createExternalCatalogGet(method, '#table-body-external-catalog2 ');
        } else {
            createExternalCatalogPost(method, '#table-body-external-catalog2 ');
        }
    }
}
function createExternalCatalogGet(){
    try{
        var fieldNames = [];
        var fieldValues = [];
        var fieldDynamicValues = [];
        var hookUrlIndex = -1;

        $('.field-name').each(function(index) {
            fieldNames[index] = $(this).val();
        });
        $('.field-value').each(function(index) {
            fieldValues[index] = $(this).val();
        });
        $('.field-dynamic-value').each(function(index) {
            fieldDynamicValues[index] = $(this).val();
        });

        var urlIndex = getUrlParamValue(fieldNames, fieldValues);
        if(urlIndex == -1){
            alert('Error: URL field is missing');
            return;
        }

        var queryUrl= fieldValues[urlIndex] + '?';

        for(var i = 0; i < fieldNames.length ; ++i){
            var value = fieldDynamicValues[i];
            if(value == '' || value == null){
                value = fieldValues[i];
            }
            if(i != urlIndex && i != hookUrlIndex){
                queryUrl = queryUrl + fieldNames[i] + '=' + value + "&";
            }
        }
        var hookUrl = $("#hdnURL").val();
        if(hookUrlIndex>-1 && fieldValues[hookUrlIndex]!=null && fieldValues[hookUrlIndex].length>0) {
            hookUrl = fieldValues[hookUrlIndex];
        }

        queryUrl += "HOOK_URL=" + encodeURIComponent(hookUrl);

        //alert(queryUrl);
        openLink(queryUrl);
        //window.open(, "_blank");

    }catch(exp){
        alert(exp);
    }
}
function createExternalCatalogPost(method, parent) {
    var fieldNames = [];
    var fieldValues = [];
    var fieldDynamicValues = [];

    $(parent+'.field-name').each(function(index) {
        fieldNames[index] = $(this).val();
    });
    $(parent+'.field-value').each(function(index) {
        fieldValues[index] = $(this).val();
    });
    $(parent+'.field-dynamic-value').each(function(index) {
        fieldDynamicValues[index] = $(this).val();
    });

    var urlIndex = getUrlParamValue(fieldNames, fieldValues);
    if(urlIndex == -1){
        alert('Error: URL field is missing');
        return null;
    }
    var url = fieldValues[urlIndex];
    if(url.indexOf('http://')==-1 && url.indexOf('https://')==-1) {
        url = 'http://' + url;
    }

    var windowName = "popupform";
    var onsubmit = "return window.open('','" + windowName + "','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,copyhistory=no,scrollbars=yes,width=960,height=1200')";
    form = $('<form target="' + windowName + '" action="' + url + '" method="' + method + '" onsubmit="' + onsubmit + '" >' + '</form>');
    $('body').append(form);

    var hookURLGiven = 0;
    for(var i = 0; i < fieldNames.length ; ++i){
        if(i != urlIndex){
            var value = fieldDynamicValues[i];
            if(value == '' || value == null){
                value = fieldValues[i];
            }
            if(fieldNames[i]=='HOOK_URL'){
                hookURLGiven = 1;
                if(value == '' || value == null){
                    value = $("#hdnURL").val();
                }
            }
            var input = '<input class="categoryInput" type="hidden" name="' + fieldNames[i] +'" value="' + value + '"/>';
            form.append(input);
        }
    }

    if(hookURLGiven ==0){
        var input = '<input class="categoryInput" type="hidden" name="HOOK_URL" value="' + $("#hdnURL").val() + '"/>';
        form.append(input);
    }
    form.submit().remove();
}
//--
function createExternalCatalogCXML(actionUrl, method, parent) {
    var fieldNames = [];
    var fieldValues = [];
    var fieldDynamicValues = [];

    $(parent+'.field-name').each(function(index) {
        fieldNames[index] = $(this).val();
    });
    $(parent+'.field-value').each(function(index) {
        fieldValues[index] = $(this).val();
    });
    $(parent+'.field-dynamic-value').each(function(index) {
        fieldDynamicValues[index] = $(this).val();
    });

    var urlIndex = getUrlParamValue(fieldNames, fieldValues);
    if(urlIndex == -1){
        alert('Error: URL field is missing');
        return null;
    }
    var url = fieldValues[urlIndex];
    if(url.indexOf('http://')==-1 && url.indexOf('https://')==-1) {
        url = 'http://' + url;
    }

    var windowName = "popupform";
    var onsubmit = "return window.open('','" + windowName + "','toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,copyhistory=no,scrollbars=yes,width=960,height=1200')";
    form = $('<form target="' + windowName + '" action="' + actionUrl + '" method="POST" onsubmit="' + onsubmit + '" >' + '</form>');
    $('body').append(form);

    var hookURLGiven = 0;
    for(var i = 0; i < fieldNames.length ; ++i){
        if(i != urlIndex){
            var value = fieldDynamicValues[i];
            if(value == '' || value == null){
                value = fieldValues[i];
            }
            if(fieldNames[i]=='HOOK_URL'){
                hookURLGiven = 1;
                if(value == '' || value == null){
                    value = $("#hdnURL").val();
                }
            }
            var input = '<input class="categoryInput" type="hidden" name="' + fieldNames[i] +'" value="' + value + '"/>';
            form.append(input);
        }
    }

    form.append('<input type="hidden" name="extCatalogMethod" value="' + method + '"/>');
    form.append('<input type="hidden" name="extCatalogUrl" value="' + url + '"/>');

    if(hookURLGiven ==0){
        var input = '<input class="categoryInput" type="hidden" name="HOOK_URL_ADMIN" value="' + $("#hdnURL").val() + '"/>';
        form.append(input);
    }
    form.submit().remove();
}
//====
function updateCatalogStatus(sender, ui, id){

    var status ="NEW";
    var catalogId= ui.item.attr('id');
    var srcColumn= sender.attr('id');
    var destColumn= ui.item.parents('.col').attr('id');

    if('working'==srcColumn && 'approved' == destColumn){
        status ="APPROVE";
    }else if('approved'==srcColumn && 'live' == destColumn){
        status ="PUBLISH";
    }
    else if('live'==srcColumn && 'approved' == destColumn){
        status ="UNPUBLISH";
    }else if('approved'==srcColumn && 'working' == destColumn){
        status ="DEACTIVATE";
    }

    var childCatId =  $('#childcat-'+catalogId).val();
    var parentCatId = $('#parentcat-'+catalogId).val();


    if(parentCatId != 'undefined' && parentCatId != null && parentCatId!=''){
        var parentColumn =  $('#columnId-'+parentCatId).val();
        if(parentColumn == destColumn){
            $('#'+parentCatId).hide();
        }
    }

    if(childCatId != 'undefined' && childCatId != null && childCatId!=''){
        var parentColumn =  $('#columnId-'+childCatId).val();
        if(parentColumn == destColumn){
            $('#'+childCatId).hide();
        }
    }


    var data = "{catalogId:"+catalogId+", status:"+ status +" }";
    /*
     'applicationContext' should be a global JS variable declared in the file that includes
     this JS. Just in case, for whatever reason,  'applicationContext' is not defined in including file,
     its value will be set to empty string.
     */
//    if (typeof applicationContext == 'undefined' || applicationContext == '/') {
//        applicationContext = "";
//    }

    $.ajaxSetup({async: false});
    //$.ajax({type:'POST', url:applicationContext + '/updateCatalogStatus?catalogId='+catalogId+'&status='+status, data:data, success: function(response) {
    $.ajax({type:'POST', url:'updateCatalogStatus?catalogId='+catalogId+'&status='+status, data:data, success: function(response) {
        //catalogJson(catalogId);
        //alert(response);
        if(response != ''){
            $("#"+catalogId +"approvedStatus").html(response);
            $("#"+catalogId +"catStatus span").html(response);
//            if(childCatId != 'undefined' && childCatId != null && childCatId!=''){
//	            $("#"+childCatId +"approvedStatus").html(response);
//	            $("#"+childCatId +"catStatus span").html(response);
//	            $("#"+childCatId +"catStatus").show();
//            }
        }
    },
        error: function (){
            alert("Processing request failed. Please try later.");
            window.location.reload();
            return false;
        }


    });

    return false;
}

/**
 * Approve Items from catalogDetail Page
 **/
function approveItems(catalogId){
    updateItemStatus(2, catalogId, "Approved"); //--call generic update function
}

/**
 * Reject Item from catalogDetail page
 * @param catalogId
 */
function rejectItems(catalogId){
    updateItemStatus(3, catalogId, "Rejected"); //--call generic update function
}

/**
 * Publish Item from catalogDetail page
 * @param catalogId
 */
function publishItems(catalogId){
    updateItemStatus(4, catalogId, "Published"); //--call generic update function
}

/**
 * Update Summary Section in catalogDetailPage, This function will update counts of Approved, Rejected,Pending items
 * @param newStatus
 * @returns {Boolean}
 */
function updateSummarySection(newStatus , newStatusText){
    //var statuses = $('input[name=itemIds]:checked'); //--- get all selected items
    var statuses =  $("#checkedItems").html().split(',');
    var statuses2 = $("#status-checkedItems").html().split(','); //--- get all selected items
    var statuses3 = $("#status-item-checkedItems").html().split(','); //--- get all selected items
    var approved=0,rejected=0,pending=0; //--variables to hold number updated of items in each status that

    for(var i= 0 ;i<statuses.length;i++){ //iterate over items to check how many in each status have changed to new status
        var id = statuses[i];
        if(statuses2[i] == 1)
            pending++;
        if(statuses2[i] == 2 || $("#status-"+id).val() == 4)
            approved++;
        if(statuses2[i] == 3)
            rejected++;
    }
    var oldApprovedCount = parseInt($('input[id=txtApproveCount]').val()); //old count of approvedItems
    var oldPendingCount = parseInt($('input[id=txtPendingCount]').val()); //old count of pendingItems
    var oldRejectedCount = parseInt($('input[id=txtRejectCount]').val()); //old count of rejectedItems

    if(newStatus == 2){ //--if status is approved then
        oldApprovedCount += statuses.length; //--add number of selected items into old approveditem count
        oldPendingCount -= pending; //--subtract selected number of pending items from oldPendingCount
        oldRejectedCount -= rejected;//--subtract selected number of items from oldRejectedCount

        //--update hidden fields in page
        $('input[id=txtApproveCount]').val(oldApprovedCount);
        $('input[id=txtRejectCount]').val(oldRejectedCount);
        $('input[id=txtPendingCount]').val(oldPendingCount);

        if(oldPendingCount == 0 && oldRejectedCount ==0)
            $("#approvedStatus").html(newStatusText);

    }else if(newStatus == 3){
        oldRejectedCount += statuses.length;
        oldPendingCount -= pending;
        oldApprovedCount-= approved;
        $('input[id=txtApproveCount]').val(oldApprovedCount);
        $('input[id=txtRejectCount]').val(oldRejectedCount);
        $('input[id=txtPendingCount]').val(oldPendingCount);

        if(oldPendingCount == 0 && oldApprovedCount == 0)
            $("#approvedStatus").html(newStatusText);

    }

    //update counts on screen

    $("#approvedCount").html(oldApprovedCount);
    $("#rejectedCount").html(oldRejectedCount);
    $("#pendingCount").html(oldPendingCount);
    return true;
}

/**
 * Update Item status send request to server for updation of item status
 * @param newStatus
 * @param catalogId
 * @param newStatusText
 * @returns {Boolean}
 */
function updateItemStatus(newStatus , catalogId , newStatusText){
    if(!isAnyItemChecked('cat-items-form')){
        noty({text: 'Select at least one item', type: 'warning'});
        return false;
    }
    try{
        if(validateItems(newStatus,newStatusText)){
            // var statuses = $('input[name=itemIds]:checked'); //--- get all selected items
            var statuses = $("#checkedItems").html().split(','); //--- get all selected items
            var statuses2 = $("#status-checkedItems").html().split(','); //--- get all selected items
            var statuses3 = $("#status-item-checkedItems").html().split(','); //--- get all selected items

            var approved=0,rejected=0,pending=0; //--variables to hold number updated of items in each status that
            for(var i= 0 ;i<statuses.length;i++){ //iterate over items to check how many in each status have changed to new status
                var id = statuses[i];
                if(statuses2[i] == 1)
                    pending++;
                if(statuses2[i]  == 2 || statuses2[i] == 4)
                    approved++;
                if(statuses2[i]  == 3)
                    rejected++;

                if($('input[id=status-item-' + id + ']')){
                    $('input[id=status-item-' + id + ']').val(id+'~~'+newStatus);
                }
                if($('input[id=status-' +id + ']')){
                    $('input[id=status-' +id + ']').val(newStatus);
                }
            }
            var gIDs=prepareQueryString('cat-items-form','itemIds');
            $.ajax({
                type:'POST',
                url : updateItemStatusServiceUrl+"?catalogId="+catalogId+"&newStatusId="+newStatus+"&changedItemsCount="+pending+"~~"+approved+"~~"+rejected,
                data: gIDs,
                dataType : 'text',
                success: function (response){
                    //location.reload();
                    updateSummarySection(newStatus , newStatusText);
                    var arr = $("#checkedItems").html().split(',');
                    var arr2 = $("#status-checkedItems").html().split(',');
                    var arr3 = $("#status-item-checkedItems").html().split(',');
                    var str2 = '';
                    var str3 = '';
                    $.each(arr , function(index, value) {
                        if($("#"+arr[index] +"approvedStatus"))
                            $("#"+arr[index] +"approvedStatus").html(newStatusText);

                        str2 += newStatus;
                        str3 += arr[index]+"~~"+newStatus;
                    });
                    $("#status-checkedItems").html(str2);
                    $("#status-item-checkedItems").html(str3);
                    populateCatalogCounts();
                    resetCheckBoxList('cat-items-form');

                },
                error : function(error){
                    alert("Unexpected Error happend. Please try later.");
                }
            });
        }

    }catch(exp){
        alert(exp);
    }
    return false;
}

/**
 * Validate oldItem status should not be same as NewitemStatus
 * @param newStatus
 * @returns {Boolean}
 */
function validateItems(newStatus,newStatusText){
    var statuses = $("#checkedItems").html().split(',');
    var statuses2 = $("#status-checkedItems").html().split(','); //--- get all selected items
    var statuses3 = $("#status-item-checkedItems").html().split(','); //--- get all selected items
    for(var i= 0 ;i<statuses.length;i++){
        var id = statuses[i];
        if(statuses2[i]==newStatus){
            alert('You have selected already '+newStatusText+' item(s)');
            return false;
        }else if(statuses2[i]== 4 && newStatus == 2){
            alert('You have selected already published item(s)');
            return false;

        }else if(statuses2[i]== 1 && newStatus == 4){
            alert('You have selected pending approval item(s)');
            return false;

        }else if(statuses2[i]== 3 && newStatus == 4){
            alert('You have selected rejected item(s)');
            return false;
        }
    }
    return true;
}

function rejectCatalog(catalogId) {
    //alert(gDeleteCatalogServiceUrl + ' ,' + catalogId  + ', ' + columnId);
    try{
        $.ajax({
            type:'POST', url: gRejectCatalogServiceUrl,
            data:'catalogId=' + catalogId,
            success: function(response) {
                if(response == 'success'){
//                    var catalogDiv = document.getElementById(catalogId);
//                    var catalogColumn  = document.getElementById(columnId);
//                    catalogColumn.removeChild(catalogDiv);
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function deleteCatalog(catalogId, columnId) {
    //alert(gDeleteCatalogServiceUrl + ' ,' + catalogId  + ', ' + columnId);
    try{
        $.ajax({
            type:'POST', url: gDeleteCatalogServiceUrl,
            data:'catalogId=' + catalogId,
            success: function(response) {
                if(response == 'success'){
                    var catalogDiv = document.getElementById(catalogId);
                    var catalogColumn  = document.getElementById(columnId);
                    catalogColumn.removeChild(catalogDiv);
                    $('#resetBtn').click();
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function setDefaultUpdateMethod(){

    $('#edit-catalog-edit-option').attr("disabled",false);
    $("select#edit-catalog-edit-option").val('1');
     selectedText = $('option#101').text();
     $('#catalog-edit-options span.center').html(selectedText);
}

function catalogJson(catalogId) {
    //alert(gCatalogJsonServiceUrl + ' ,' + catalogId);
    try{
        $.ajax({
            type:'POST', url: getCatalogStatusUrl,
            data:'catalogId=' + catalogId,
            success: function(response) {
                if(response != ''){
                    $("#"+catalogId +"approvedStatus").html(response);
                    $("#"+catalogId +"catStatus span").html(response);
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function editCatalog(catalogId, fromCatalogDetailFlag) {
	externalRowIncrementId= 0;
	cxmlToken= true;
    //alert(gCatalogJsonServiceUrl + ' ,' + catalogId);
    try{
        $.ajax({
            type:'POST', url: gCatalogJsonServiceUrl,
            data:'catalogId=' + catalogId,
            success: function(response) {
                if(response != ''){
                    //alert(response);
                    var catalog = eval( "(" + response + ")" );
                    gCurrentCatalog = catalog;
                    clearCatalogErrors();
                    populateCatalogForm(gCurrentCatalog,fromCatalogDetailFlag);
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function editQuote(catalogId) {
    //alert(gCatalogJsonServiceUrl + ' ,' + catalogId);
    try{
        $.ajax({
            type:'POST', url: gCatalogJsonServiceUrl,
            data:'catalogId=' + catalogId,
            success: function(response) {
                if(response != ''){
                    //alert(response);
                    var catalog = eval( "(" + response + ")" );
                    gCurrentCatalog = catalog;
                    clearCatalogErrors();
                    populateQuoteForm(gCurrentCatalog);
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function exportCatalog(catalogId) {
    try{
        $.ajax({
            type:'POST', url: exportCatalogServiceUrl,
            data:'catalogId=' + catalogId,
            success: function(response) {
                noty({text: 'Your export file is being prepared. You will be notified when it is ready.', type: 'success'});
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function catalogChangeHistory(catalogId) {
    try{
        $.ajax({
            type:'POST', url: changereportServiceUrl,
            data:'catalogId=' + catalogId,
            success: function(response) {
                noty({text: 'Your change report file is being prepared. You will be notified when it is ready.', type: 'success'});
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function resetForm(form) {	
    inputFile = form.find('input:file');
    form.get(0).reset();
    inputFile.each(function(){
        this.jcf.refreshState();
    });

    try {
    	
    	replaceCatFile();
    	replaceZipFile();
    	
        cxmlToken= true; //Reset CXML Token
        if($("#search-allowed-check")){
        	$("#search-allowed-check").css("display","none");
        }
    	$('#catalogStateIdHidden').val('');
    	$("input#supplierName").unbind("change");
        $("#supplierNameDisplay").typeahead('destroy');
        $("#colorbox").css({'height': 640 + 'px'});
        $("#cboxContent").css({'height': 625 + 'px'});
        $("#cboxLoadedContent").css({'height': 625 + 'px'});
        $('#createCatalogReplyDiv').html("");
        setDefaultUpdateMethod();
        var editCatalogNameLabel = document.getElementById('catalog-name-label');
        var editCatalogNameDiv  = document.getElementById('catalog-name-div');
        editCatalogNameDiv.removeChild(editCatalogNameLabel);        
    } catch(exp){
    	
    }
}

//function getCatalogValidFromTimezone(timeZone){
//    if(timeZone == null || timeZone == ''){
//        return "+0000"; // GMT is default
//    }
//    var numOffset = date.getTimezoneOffset()/60;
//    for (i = 0; i < gTimezoneList2.length; i++)  {
//        if(gTimezoneList2[i].value == numOffset){
//            return gTimezoneList2[i].label;
//        }
//    }
//    return catalog.validFromTimeZone;
//}

//function populateCatalogForm(catalog){
//    try{
//        var editCatalogForm = $('#edit-catalog-form');
//
//        resetForm(editCatalogForm);
//
//        $('input#edit-catalog-name').val(catalog.name);
//
//        if(catalog.supplierID != null){
//            //$('select#supplierName option:selected').val(catalog.supplierID);
//            $("select#edit-catalog-supplier-name").val(catalog.supplierID);
//            var selectedText = $('option#' + catalog.supplierID).text();
//            $('#edit-catalog-supplier-names span.center').html(selectedText);
//        }
//
//        if(catalog.externalCatalogId != null){
//            //$('select#externalCatalogId option:selected').val(catalog.externalCatalogId);
//            $("select#edit-catalog-external-catalog").val(catalog.externalCatalogId);
//            selectedText = $('option#' + catalog.externalCatalogId).text();
//            $('#edit-catalog-external-catalogs span.center').html(selectedText);
//        }
//
//        if(catalog.clientCatalogId != null){
//            $('input#edit-catalog-client-catalog-id').val(catalog.clientCatalogId);
//        }
//
//        var startDatePicker = $('input#edit-catalog-start-date').datetimepicker({
//            showOn: 'both',
//            showTimezone: true,
//            timeFormat: 'hh:mm z',
//            useLocalTimezone: false,
//            timezone: (catalog.validFromTimeZone==null || catalog.validFromTimeZone=='')? "+0000":catalog.validFromTimeZone,
//            beforeShow: function(){
//                $('#ui-datepicker-div').css({visibility:'visible'});
//            },
//            timezoneList: gTimezoneList,
//            onClose: function(){
//                $('#ui-datepicker-div').css({visibility:'hidden'});
//            }
//        });
//
//        var untilDatePicker = $('input#edit-catalog-end-date').datetimepicker({
//            showOn: 'both',
//            showTimezone: true,
//            timeFormat: 'hh:mm z',
//            useLocalTimezone: false,
//            timezone: (catalog.validUntilTimeZone==null || catalog.validUntilTimeZone=='')? "+0000":catalog.validUntilTimeZone,
//            beforeShow: function(){
//                $('#ui-datepicker-div').css({visibility:'visible'});
//            },
//            timezoneList: gTimezoneList,
//            onClose: function(){
//                $('#ui-datepicker-div').css({visibility:'hidden'});
//            }
//        });
//
//        var fromDate = null; var untilDate = null;
//        if(catalog.validFrom != null && catalog.validFrom != '') fromDate = new Date(catalog.validFrom);
//        if(catalog.validUntil != null && catalog.validUntil != '') untilDate = new Date(catalog.validUntil);
//
//        if(fromDate != null){
//            startDatePicker.datetimepicker('setDate', (fromDate) );
//        }
//        if(untilDate != null){
//            untilDatePicker.datetimepicker('setDate', (untilDate) );
//        }
//
//        $('input#edit-catalog-id').val(catalog.catalogId);
//        $('input#edit-catalog-type-id').val(catalog.catalogTypeID);
//
//        try{
//            $('.error').innerHTML ='';
//        }catch(exp){}
//
//        // popup catalog form
//        popupEditCatalogForm();
//
//        $('#edit-catalog-edit-option').attr("disabled",true);
//
//        // disable Save button
//        $('#editCatalogSubmitButton').attr("disabled",true);
//
//    }catch(exp){
//        alert(exp);
//    }
//}

function createCatalog(){
	externalRowIncrementId=0;
    $('#theCatalogId').css('style','display:block');

    resetForm($('#theCatalogId'));
    clearCatalogErrors();
    $('#theCatalogId').get(0).setAttribute('action', 'createCatalog');
    $("#createCatalogSubmitButton").val("Create");
    $("#title-id").html("Create Catalog");

    $('#SupplierCompany span.center').html('');

    $("#table-body-external-catalog").empty(); // clear first    
    $('#external-catalog-fields').show();

    $("#catalog-edit-options").css("display","none");

    $("#external-catalog-fields").css("display","block");
    $('#catalog-id-field').css("display","block");
    $("#theQuoteIdfield").css("display","none");
    $("#rfqNumberRow").css("display","none");
    $("#create-version-check").css("display","block");
    $("#external-catalog-check").css("display","block");
    $("#SupplierCompany label").html("<label>Supplier Company:</label>");


//    $("#quoteFileCross").css('style','margin-top:112px');
//    $("#zipFileCross").css('style','margin-top:156px');

    $("#quoteFileCross").css('margin-top','65px');
    $("#zipFileCross").css('margin-top','110px');

    $("#theCatalogFileId .txtFile").html("Catalog File:");

    $("#theCatalogNameId label").html("<span>*</span>Catalog Name:");
    $('#check-external-catalog').removeAttr('checked');
    
    $('#extCatalogMethod').val("POST");
    $('#extCommunicationMethod').val("OCI");
    $('#encodingTypeField').hide();
    
    var option = $('option#POST');
    if(option != null){
        var selectedText = option.text();
        $('#extMethod span.center').html(selectedText);
    }
    
    var optionForExtCommunication = $('option#OCI');
    if(optionForExtCommunication != null){
        var selectedTextForExtCommunication = optionForExtCommunication.text();
        $('#commMethod span.center').html(selectedTextForExtCommunication);
    }
    
    $("div.toggle-block").removeClass("active");
    $("div.toggle-block .block").hide();

    $("#supplierName").removeAttr("disabled");
    $("#catalog-num").removeAttr("disabled");
    $("#contract-line").removeAttr("disabled");
    $("#supplierHidden").html('');
    $('#SupplierCompany input#supplierName').val('');
    
    popupEditCatalogForm();    
}

/**
 * Initializes the Supplier Name text field to enable Type Ahead functionality.
 * 
 * @param nonCatalogSupplier 
 * 	<code>null</code> - nonCatalogSupplier flag is to be ignored.
 *  <code>true</code> - nonCatalogSuppliers are to be fetched.
 *  <code>false</code> - catalog suppliers are to be fetched.
 * @author: Muhammad Haris
 * Last Modified: 27-03-2015 
 */
 function initializeSupplierTypeAhead(nonCatalogSupplier) {
	
	var typeAheadURL = supplierTypeAheadURL + '/' + unitId + '?maxResults=50';	
	if (nonCatalogSupplier != null) {
		typeAheadURL += '&nonCatalogSupplier=' + nonCatalogSupplier;
	}
	
	var suppliers = new Bloodhound({
	  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
	  queryTokenizer: Bloodhound.tokenizers.whitespace,
	  limit: 10,
	  prefetch: typeAheadURL,
	  remote: typeAheadURL + '&keyword=%QUERY'
	});
		 
	suppliers.initialize();
		 
	$('#supplierNameDisplay').typeahead({
		highlight: true		
	},
	{
	  name: 'suppliers',	  
	  displayKey: 'companyName',
	  source: suppliers.ttAdapter(),	  
	}).on('typeahead:selected', function(obj, selected, name) {
		// if user has made a valid selection, set the value in hidden field
		selectedValue = "";
		if (selected) {
			selectedValue = selected.companyId;			
		}
		$('#supplierName').val(selectedValue);
		$('#supplierName').trigger("change");		
	}).on('typeahead:closed', function() {	
		// check whether user has made a valid selection or not
		checkSupplierSelection();
	});	
}
function initializeSupplierTypeAheadSearch() {
	
	var typeAheadURL = supplierTypeAheadURL  + '?currentPage=0&pageSize=10';		
	
	var suppliers = new Bloodhound({
	  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
	  queryTokenizer: Bloodhound.tokenizers.whitespace,
	  limit: 10,
	  prefetch: typeAheadURL,
	  remote: typeAheadURL + '&keyword=%QUERY'
	});
		 
	suppliers.initialize();
		 
	$('#supplierNameDisplay').typeahead({
		highlight: true		
	},
	{
	  name: 'suppliers',	  
	  displayKey: 'companyName',
	  source: suppliers.ttAdapter(),	  
	}).on('typeahead:selected', function(obj, selected, name) {
		// if user has made a valid selection, set the value in hidden field
		selectedValue = "";
		if (selected) {
			selectedValue = selected.uniqueSupplierId;			
		}
		$('#supplierName').val(selectedValue);
		$('#supplierName').trigger("change");		
	}).on('typeahead:closed', function() {	
		// check whether user has made a valid selection or not
		checkSupplierSelection();
	});	
}
function initializeShopperTypeAhead() {
	
	var typeAheadURL = shopperTypeAheadURL + '?roles=SHOPPER&currentPage=0&pageSize=10';
	
	var shoppers = new Bloodhound({
	  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
	  queryTokenizer: Bloodhound.tokenizers.whitespace,
	  limit: 10,
	  prefetch: typeAheadURL,
	  remote: typeAheadURL + '&keyword=%QUERY'
	});
		 
	shoppers.initialize();
		 
	$('#shopperNameDisplay').typeahead({
		highlight: true		
	},
	{
	  name: 'shoppers',	  
	  displayKey: 'username',
	  source: shoppers.ttAdapter(),	  
	}).on('typeahead:selected', function(obj, selected, name) {
		// if user has made a valid selection, set the value in hidden field
		selectedValue = "";
		if (selected) {
			selectedValue = selected.userId;			
		}
		$('#shopperId').val(selectedValue);
		$('#shopperId').trigger("change");		
	}).on('typeahead:closed', function() {	
		// check whether user has made a valid selection or not
		checkSupplierSelection();
	});	
}
function createQuoteCatalog(){

	$("div.toggle-block").removeClass("active");
	$("div.toggle-block .block").hide();
	    
    resetForm($('#theCatalogId'));
    clearCatalogErrors();
    $('#theCatalogId').get(0).setAttribute('action', 'createCatalog');
    $("#createCatalogSubmitButton").val("Create");
    $("#title-id").html("Create Quote");

    $('#SupplierCompany span.center').html('');

    $("#table-body-external-catalog").empty(); // clear first

    //$('#external-catalog-fields').show();

    $("#catalog-edit-options").css("display","none");

    $('#check-external-catalog').removeAttr('checked');
    $('#catalog-id-field').css("display","none");
    $("#theQuoteIdfield").css("display","block");
    $("#external-catalog-fields").css("display","none");
    $("#rfqNumberRow").css("display","block");
    $("#create-version-check").css("display","none");
    $("#external-catalog-check").css("display","none");
    $("#theCatalogFileId .txtFile").html("Quote File:");
    $("#SupplierCompany label").html("<label><span>*</span>Supplier Company:</label>");
    $("#quoteFileCross").css('margin-top','112px');
    $("#zipFileCross").css('margin-top','156px');

//    $("#quoteFileCross").css('style','margin-top:65px');
//    $("#zipFileCross").css('style','margin-top:110px');

    $("#theCatalogNameId label").html("<span>*</span>Quote Name:");
    $('#extCatalogMethod').val("POST");
    var option = $('option#POST');
    if(option != null){
        var selectedText = option.text();
        $('#extMethod span.center').html(selectedText);
    }

    $("#supplierName").removeAttr("disabled");
    $("#catalog-num").removeAttr("disabled");
    $("#contract-line").removeAttr("disabled");
    $("#supplierHidden").html('');
    $('#SupplierCompany input#supplierName').val('');
    
    popupEditQuoteForm();


}

function initExternalCatalogRowToTable(intIndex, field){
    try{
        var $row = $('<tr class="added" id="row_'+ intIndex +'"></tr>');

        //var $cell0 = $('<td class="td-select"><a href="#" class="btn-up-down"><span class="up-arrow">up</span> <span class="down-arrow">down</span></a><input type="checkbox" name="check10" class="target-chbox" id="check10-'+ intIndex +'" /></td>');
        var $cell0 = $('<td class="td-select"><input type="checkbox" name="check10" class="target-chbox" id="check10-'+ intIndex +'" /></td>');

        var $cell1 =$('<td class="a-left td-sequence-catalog"><div><input class="field-sequence" type="text" style="width:14px;" value="'+ field.sequence +'" name="fields['+ intIndex +'].sequence" /></div></td>');

        var $cell2 =$('<td class="a-left td-name-catalog"><div><input type="text" value="' + field.name + '" name=fields['+ intIndex +'].name class="field-name" /></div></td>');

        var $cell3 =$('<td><div><input type="text" onkeyup="disableCombo('+intIndex+')" id="id-' + intIndex + '" value="' + field.value + '" name=fields[' + intIndex + '].value class="field-value" /><label style="margin-top: 6px" class="td-select"> OR </label></div></td>');

        var op1sel = (field.dynamicValue == "")? "selected" : "";
        var op2sel = (field.dynamicValue == "NEW_ITEM-VENDORMAT")? "selected" : "";
        var op3sel = (field.dynamicValue == "SY-UNAME")? "selected" : "";

        var $cell4 =$('<td><div> <select class="field-dynamic-value" onchange="disableInput('+intIndex+')"  id="s-val-'+intIndex+'" name ="fields[' + intIndex + '].dynamicValue"><option value="" ' + op1sel + '>Select Dynamic Value</option><option value="NEW_ITEM-VENDORMAT"  ' + op2sel + '>NEW_ITEM-VENDORMAT</option><option value="SY-UNAME"  ' + op3sel + '>SY-UNAME</option></select></div></td>');

        $row.append($cell0);
        $row.append($cell1);
        $row.append($cell2);
        $row.append($cell3);
        $row.append($cell4);

        $("#table-body-external-catalog").append($row);
        if($("#id-"+intIndex).val() == 'null' ){
            $("#id-"+intIndex).val('Enter Value');
        }


        disableCombo(intIndex);
        disableInput(intIndex);
    }catch(exp){
        alert(exp);
    }
}

function disableCombo(rowId){
    if($("#id-"+rowId).val()!= null && $("#id-"+rowId).val()!= '' && $("#id-"+rowId).val() != 'Enter Value'){
        $("#s-val-"+rowId).prop("disabled", true);
    }else{
        $("#s-val-"+rowId).prop("disabled", false);
    }


}


function disableInput(rowId){
    if($("#s-val-"+rowId).val()!= null && $("#s-val-"+rowId).val()!= '' && $("#s-val-"+rowId).val() != 'Select Dynamic Value'){
        $("#id-"+rowId).prop("disabled", true);
    }else{
        $("#id-"+rowId).prop("disabled", false);
    }
}

function populateCatalogForm(catalog, populateCatalogForm){
    if(catalog == null) return;
    
    try{
    	if(!populateCatalogForm){
    	$("div.toggle-block").removeClass("active");
    	$("div.toggle-block .block").hide();
    		
    	}
        
        var editCatalogForm = $('#theCatalogId');

        editCatalogForm.get(0).setAttribute('action', 'editCatalog');
        $("#catalog-edit-options").css("display","block");
        $("#createCatalogSubmitButton").val("Save");
        $("#title-id").html("Edit Catalog");
        $("#external-catalog-fields").css("display","block");
        $('#catalog-id-field').css("display","block");
        $("#theQuoteIdfield").css("display","none");
        $("#rfqNumberRow").css("display","none");
        $("#external-catalog-check").css("display","block");
        $("#create-version-check").css("display","block");
        $("#theCatalogFileId .txtFile").html("Catalog File:");
        $("#SupplierCompany label").html("<label>Supplier Company:</label>");
        $("#theCatalogNameId label").html("<span>*</span>Catalog Name:");
        //onclick="populateCatalogForm(gCurrentCatalog); return false;"
//        $("#quoteFileCross").css('style','margin-top:112px');
//        $("#zipFileCross").css('style','margin-top:156px');

        $("#quoteFileCross").css('margin-top','65px');
        $("#zipFileCross").css('margin-top','110px');

        resetForm(editCatalogForm);

        $('input#catalog-name').val(catalog.name);
        
        if (catalog.supplierId != null && catalog.supplierId) {
        	
        	$('#SupplierCompany input#supplierName').val(catalog.supplierId)
        	
        	if (catalog.supplierName) {
        		$('#SupplierCompany input#supplierNameDisplay').val(catalog.supplierName)	
        	}	
        }        
        
        $("#table-body-external-catalog").empty(); // clear first
        if(catalog.fields != null){
            for(var i =0; i < catalog.fields.length; ++i){
                initExternalCatalogRowToTable(i, catalog.fields[i]);
                ++externalRowIncrementId;
            }
        }

        if(catalog.catalogTypeId == 1){
            $('#check-external-catalog').attr('checked','checked');
            $('#search-allowed-check').show();
        }else{
            $('#check-external-catalog').removeAttr('checked');
        }

        if(catalog.searchAllowed){
            $('#check-search-allowed').attr('checked','checked');
        }else{
            $('#check-search-allowed').removeAttr('checked');
        }

        if(catalog.quantityLocked){
            $('#check-quantity-locked').attr('checked','checked');
        }else{
            $('#check-quantity-locked').removeAttr('checked');
        }
        
        if(catalog.rfqNumber!= null && catalog.rfqNumber!=''){
            $('#rfq-id').val(catalog.rfqNumber);
        }


        if(catalog.quoteId!= null && catalog.quoteId!=''){
            $('#quote-id').val(catalog.quoteId);
        }

        if(catalog.catalogTypeId == 1){ // External catalog
        	$('#external-catalog-fields').show();
        }        
        var extCommunicationMethod = catalog.extCommunicationMethod;
        var encodingType = catalog.encodingType;
        var catalogExtMethod = catalog.extCatalogMethod;
        var extCatalogImageField = catalog.extCatalogImageField;
        
        if((extCommunicationMethod != null) && (extCommunicationMethod != "")){
        	
        	if(extCommunicationMethod=='CXML'){
        		cxmlToken=false;
        		$('#encodingTypeField').show();
                $('#encodingType').val(encodingType);
                var option = $('option#' + encodingType);
                if(option != null){
                    if($('#encodingTypeField span.center').html() != encodingType) $('#encodingTypeField span.center').html(encodingType);
                }
        	}else{
        		$('#encodingTypeField').hide();
        	}
            $('#extCommunicationMethod').val(extCommunicationMethod);
            var option = $('option#' + extCommunicationMethod);
            if(option != null){
                if($('#commMethod span.center').html() != extCommunicationMethod) $('#commMethod span.center').html(extCommunicationMethod);
            }
        }else{
        	 $('#extCommunicationMethod').val("OCI");
        	 var optionForExtCommunication = $('option#OCI');
     	     if(optionForExtCommunication != null){
     	        var selectedTextForExtCommunication = optionForExtCommunication.text();
     	        $('#commMethod span.center').html(selectedTextForExtCommunication);
     	     }
     	    $('#encodingTypeField').hide();
        }
       
        
        if((catalogExtMethod != null) && (catalogExtMethod != "")){
            $('#extCatalogMethod').val(catalogExtMethod);
            var option = $('option#' + catalogExtMethod);
            if(option != null){
                var selectedText = option.text();
                if($('#extMethod span.center').html() != catalogExtMethod) $('#extMethod span.center').html(catalogExtMethod);
            }
        }else{
        	$('#extCatalogMethod').val("POST");
    	    var option = $('option#POST');
    	    if(option != null){
    	        var selectedText = option.text();
    	        $('#extMethod span.center').html(selectedText);
    	    }
        }
        
        if((extCatalogImageField != null) && (extCatalogImageField != "")){
            $('#extCatalogImageField').val(extCatalogImageField);
            var option = $('option#' + extCatalogImageField);
            if(option != null){
                var selectedText = option.text();
                if($('#extImageField span.center').html() != extCatalogImageField) $('#extImageField span.center').html(extCatalogImageField);
            }
        }

        if(catalog.clientCatalogId != null){
            $('input#catalog-id').val(catalog.clientCatalogId);
        }
        if(catalog.contractLineItem != null){
            $('input#contract-line').val(catalog.contractLineItem);
        }
        if(catalog.contractNumber != null){
            $('input#catalog-num').val(catalog.contractNumber);
        }

        if(catalog.catalogStatus == "PUBLISHED" || catalog.catalogStatus == "UNPUBLISHED" || catalog.catalogStatus == "APPROVED") {        	
            $("input#supplierName, input#contract-line, input#catalog-num, input#check-create-version, input#quote-id").change(function() {
                var contractLine = $('input#contract-line').val();
                var contractNumber = $('input#catalog-num').val();
                var supplierId = $('input#supplierName').val();
                var quoteId = $('input#quote-id').val();
                if($('#check-create-version').is(":checked") || catalog.contractLineItem!=contractLine || catalog.contractNumber!=contractNumber || catalog.quoteId!=quoteId
                    || catalog.supplierId != supplierId) {
                    $('#newVersionMessage').show();
                }else{
                    $('#newVersionMessage').hide();
                }
            });
        } else {        	
            $("input#supplierName, input#contract-line, input#catalog-num, input#check-create-version, input#quote-id").unbind("change");
        }

        var startDatePicker = $('input#start-date').datetimepicker({
            showOn: 'both',

            beforeShow: function(){
                $('#ui-datepicker-div').css({visibility:'visible'});
            },

            onClose: function(){
                $('#ui-datepicker-div').css({visibility:'hidden'});
            }
        });

        var untilDatePicker = $('input#end-date').datetimepicker({
            showOn: 'both',

            beforeShow: function(){
                $('#ui-datepicker-div').css({visibility:'visible'});
            },

            onClose: function(){
                $('#ui-datepicker-div').css({visibility:'hidden'});
            }
        });

        var fromDate = null; var untilDate = null;
        
        if(catalog.validFrom) {
        	fromDate = getTimeZoneSpecificDate(catalog.validFrom, catalog.catalogTimeZone);        	
        }
        
        if(catalog.validUntil) {
        	untilDate = getTimeZoneSpecificDate(catalog.validUntil, catalog.catalogTimeZone);
        }

        console.log("TimeZone: " + catalog.timeZoneId);
        if (catalog.timeZoneId != null) {
        	$("select#timeZone").val(catalog.timeZoneId);
        }
        
        if(fromDate != null){        	
    		startDatePicker.datetimepicker('setDate', (fromDate) );
    	}

        /* Disable date picker for activation date if catalog is PUBLISHED */
        if (catalog.catalogStatus == "PUBLISHED") {
        	startDatePicker.datetimepicker('destroy');        	
        }
        
        if(untilDate != null){
            untilDatePicker.datetimepicker('setDate', (untilDate) );
        }

        $('input#catalogId').val(catalog.catalogId);
        $('input#catalog-type-id').val(catalog.catalogTypeId);

        if(catalog.catalogStateId == 2 || catalog.catalogStateId == 4){
            $('#create-version-check').show();
        } else {
            $('#create-version-check').hide();
        }

        $('#supplierName').val(catalog.supplierId);

        $('#catalogStateIdHidden').val(catalog.catalogStateId);



        if(catalog.approvers != null){
            for(var i =0; i < catalog.approvers.length; ++i){
                $('#approvers-'+catalog.approvers[i]).attr('checked','checked');
            }

            $("#approverHiddenField").val(catalog.approvers);
        }

        if(catalog.profileList != null){
            for(var i =0; i < catalog.profileList.length; ++i){
                $('#profiles-'+catalog.profileList[i]).attr('checked','checked');
            }
            $("#profileHiddenField").val(catalog.profileList);

        }




        try{
            $('.error').innerHTML ='';
        }catch(exp){}

        jcf.customForms.refreshAll();
        // popup catalog form
        popupEditCatalogForm();
        /*if(catalog.catalogId != null){
         if(catalog.supplierId != null && supplier.){
         $("#supplierHidden").html('<input type="hidden" value="'+catalog.supplierId+'" name="supplierName"/><input type="hidden" value="'+catalog.contractNumber+'" name="contractNumber"/><input type="hidden" value="'+catalog.contractLineItem+'" name="contractLineItem"/>');
         }

         //VD-322
         //	$("#	").prop('disabled', 'disabled');
         //	$("#catalog-num").prop('disabled', 'disabled');
         //	$("#contract-line").prop('disabled', 'disabled');

         }*/

    }catch(exp){
        alert(exp);
    }
}
function populateQuoteForm(catalog){
    if(catalog == null) return;

    try{
        var editCatalogForm = $('#theCatalogId');

        editCatalogForm.get(0).setAttribute('action', 'editCatalog');
        $("#catalog-edit-options").css("display","block");
        $("#createCatalogSubmitButton").val("Save");
        $("#title-id").html("Edit Quote");

        $('#catalog-id-field').css("display","none");
        $("#theQuoteIdfield").css("display","block");
        $("#rfqNumberRow").css("display","block");
        $("#external-catalog-check").css("display","none");
        $("#create-version-check").css("display","none");
        $("#theCatalogFileId .txtFile").html("Quote File:");
        $("#theCatalogNameId label").html("<span>*</span>Quote Name:");
        //onclick="populateCatalogForm(gCurrentCatalog); return false;"
        $("#quoteFileCross").css('margin-top','112px');
        $("#zipFileCross").css('margin-top','156px');
        $("#SupplierCompany label").html("<label><span>*</span>Supplier Company:</label>");
//    $("#quoteFileCross").css('style','margin-top:65px');
//    $("#zipFileCross").css('style','margin-top:110px');

        resetForm(editCatalogForm);
        $("#external-catalog-fields").css("display","none");
        $('input#catalog-name').val(catalog.name);

        $('#SupplierCompany input#supplierName').val('');
        if (catalog.supplierId != null && catalog.supplierId) {
        	
        	$('#SupplierCompany input#supplierName').val(catalog.supplierId)
        	
        	if (catalog.supplierName) {
        		$('#SupplierCompany input#supplierNameDisplay').val(catalog.supplierName)	
        	}	
        }
        
        $("#table-body-external-catalog").empty(); // clear first
        if(catalog.fields != null){
            for(var i =0; i < catalog.fields.length; ++i){
                initExternalCatalogRowToTable(i, catalog.fields[i]);
                ++externalRowIncrementId;
            }
        }

        if(catalog.catalogTypeId == 1){
            $('#check-external-catalog').attr('checked','checked');
            $('#search-allowed-check').show();
        }else{
            $('#check-external-catalog').removeAttr('checked');
        }

        if(catalog.searchAllowed){
            $('#check-search-allowed').attr('checked','checked');
        }else{
            $('#check-search-allowed').removeAttr('checked');
        }
        if(catalog.rfqNumber!= null && catalog.rfqNumber!=''){
            $('#rfq-id').val(catalog.rfqNumber);
        }


        if(catalog.quoteId!= null && catalog.quoteId!=''){
            $('#quote-id').val(catalog.quoteId);
        }
        if(catalog.catalogTypeId == 2){ // Internal catalog
            //$('#external-catalog-fields').hide();
        }else{
            //$('#external-catalog-fields').show();
            var catalogExtMethod = catalog.extCatalogMethod;
            if((catalogExtMethod != null) && (catalogExtMethod != "")){
                $('#extCatalogMethod').val(catalogExtMethod);
                var option = $('option#' + catalogExtMethod);
                if(option != null){
                    var selectedText = option.text();
                    if($('#extMethod span.center').html() != catalogExtMethod) $('#extMethod span.center').html(catalogExtMethod);
                }
            }
            var extCatalogImageField = catalog.extCatalogImageField;
            if((extCatalogImageField != null) && (extCatalogImageField != "")){
                $('#extCatalogImageField').val(extCatalogImageField);
                var option = $('option#' + extCatalogImageField);
                if(option != null){
                    var selectedText = option.text();
                    if($('#extImageField span.center').html() != extCatalogImageField) $('#extImageField span.center').html(extCatalogImageField);
                }
            }
        }

        if(catalog.clientCatalogId != null){
            $('input#catalog-id').val(catalog.clientCatalogId);
        }
        if(catalog.contractLineItem != null){
            $('input#contract-line').val(catalog.contractLineItem);
        }
        if(catalog.contractNumber != null){
            $('input#catalog-num').val(catalog.contractNumber);
        }

        if(catalog.catalogStatus == "PUBLISHED" || catalog.catalogStatus == "UNPUBLISHED" || catalog.catalogStatus == "APPROVED") {
            $("input#supplierName, input#contract-line, input#catalog-num, input#check-create-version, input#quote-id").change(function() {
                var contractLine = $('input#contract-line').val();
                var contractNumber = $('input#catalog-num').val();
                var supplierId = $('input#supplierName').val();
                var quoteId = $('input#quote-id').val();
                if($('#check-create-version').is(":checked") || catalog.contractLineItem!=contractLine || catalog.contractNumber!=contractNumber || catalog.quoteId!=quoteId
                    || catalog.supplierId != supplierId) {
                    $('#newVersionMessage').show();
                }else{
                    $('#newVersionMessage').hide();
                }
            });
        } else {
            $("input#supplierName, input#contract-line, input#catalog-num, input#check-create-version, input#quote-id").unbind( "change" );
        }

        var startDatePicker = $('input#start-date').datetimepicker({
            showOn: 'both',
            format: 'yyyy-MM-dd',

            beforeShow: function(){
                $('#ui-datepicker-div').css({visibility:'visible'});
            },
            timezoneList: gTimezoneList,
            onClose: function(){
                $('#ui-datepicker-div').css({visibility:'hidden'});
            }
        });

        var untilDatePicker = $('input#end-date').datetimepicker({
            showOn: 'both',
            format: 'yyyy-MM-dd',
            beforeShow: function(){
                $('#ui-datepicker-div').css({visibility:'visible'});
            },
            timezoneList: gTimezoneList,
            onClose: function(){
                $('#ui-datepicker-div').css({visibility:'hidden'});
            }
        });

        var fromDate = null; var untilDate = null;
        if(catalog.validFrom != null && catalog.validFrom != '') fromDate = new Date(catalog.validFrom);
        if(catalog.validUntil != null && catalog.validUntil != '') untilDate = new Date(catalog.validUntil);



        if(fromDate != null){
            startDatePicker.datetimepicker('setDate', (fromDate) );
        }
        if(untilDate != null){
            untilDatePicker.datetimepicker('setDate', (untilDate) );
        }

        $('input#catalogId').val(catalog.catalogId);
        $('input#catalog-type-id').val(catalog.catalogTypeId);

        if(catalog.catalogStateId == 2 || catalog.catalogStateId == 4){
            $('#create-version-check').show();
        } else {
            $('#create-version-check').hide();
        }

        $('#supplierName').val(catalog.supplierId);

        $('#catalogStateIdHidden').val(catalog.catalogStateId);



        if(catalog.approvers != null){
            for(var i =0; i < catalog.approvers.length; ++i){
                $('#approvers-'+catalog.approvers[i]).attr('checked','checked');
            }

            $("#approverHiddenField").val(catalog.approvers);
        }

        if(catalog.profileList != null){
            for(var i =0; i < catalog.profileList.length; ++i){
                $('#profiles-'+catalog.profileList[i]).attr('checked','checked');
            }
            $("#profileHiddenField").val(catalog.profileList);

        }




        try{
            $('.error').innerHTML ='';
        }catch(exp){}

        // popup catalog form
        popupEditQuoteForm();
        /*if(catalog.catalogId != null){
         if(catalog.supplierId != null && supplier.){
         $("#supplierHidden").html('<input type="hidden" value="'+catalog.supplierId+'" name="supplierName"/><input type="hidden" value="'+catalog.contractNumber+'" name="contractNumber"/><input type="hidden" value="'+catalog.contractLineItem+'" name="contractLineItem"/>');
         }

         //VD-322
         //	$("#	").prop('disabled', 'disabled');
         //	$("#catalog-num").prop('disabled', 'disabled');
         //	$("#contract-line").prop('disabled', 'disabled');

         }*/

    }catch(exp){
        alert(exp);
    }
}

function popupEditCatalogForm(){
    $('#theImageFileId').find("#create-catalog-catalog-image-file-label").remove();
    $.colorbox({
        href:'#lightbox-inline',
        inline:true,
        onComplete: function(){
            $('#newVersionMessage').hide();
            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                $.colorbox.close();
                return false;
            });
        }
    });
    
    initializeSupplierTypeAhead(false);
}

function popupEditQuoteForm(){
    $('#theImageFileId').find("#create-catalog-catalog-image-file-label").remove();
    $.colorbox({
        href:'#lightbox-inline',
        inline:true,
        onComplete: function(){
            $('#newVersionMessage').hide();
            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                $.colorbox.close();
                return false;
            });

        }
    });
    
    initializeSupplierTypeAhead(false);
}
var click;

function popupSearch() {
    click = true;
    $.colorbox({
        href:'#lightbox-inline-search-loading',
        inline:true,
        onComplete:function () {
            $('#cboxContent .close').unbind('click').bind('click', function () {
                $.colorbox.close();
                return false;
            });
            if (click) {
                $("#cboxContent #searchPopupTitle").html("Searching...");
                $("#cboxContent #divContent").html("");
                $('#cboxContent #executeSearch').click();
            }
        }
    });
}

function executeSearchFunction(pageNumber){
    $.ajax({url:"search",data:"keyword="+$("#text-field").val()+"&unitid="+unitId+"&pageNumber="+pageNumber ,success:function(result){
//                        alert(result);
        $("#cboxContent #searchPopupTitle").html("Search Results for: " + $("#text-field").val());
        $("#cboxContent #divContent").html(result);
        click = false;
        $.fn.colorbox.load();
    }});


}

//function validateEditCatalog(){
//    var form = document.forms['edit-catalog-form'];
//    if(form['catalogName'].value == ''){
//        $('#label-edit-catalog-name').innerHTML ='catalog name is required';
//        alert('Catalog Name is required');
//         return;
//    }
//    if(form['catalogEditOption'].selectedIndex > 0 && form['catalogFile'].value == ''){
//        $('#label-edit-catalog-file').innerHTML ='catalog file is required';
//        alert('Catalog File is required');
//        return;
//    }
//    form.submit();
//}

// prepare the form when the DOM is ready
$(document).ready(function() {
    var options = {
        beforeSubmit:  showRequest,  // pre-submit callback
        success:       showResponse,  // post-submit callback
        uploadProgress: uploadProgress
    };
    // bind form using 'ajaxForm'
    $('#edit-catalog-form').ajaxForm(options);
    var options3 = {
        beforeSubmit:  showRequest3,  // pre-submit callback
        success:       showResponse2,  // post-submit callback
        uploadProgress: uploadProgress2
    };
    var options2 = {
        beforeSubmit:  showRequest2,  // pre-submit callback
        success:       showResponse2,  // post-submit callback
        uploadProgress: uploadProgress2
    };
    // bind form using 'ajaxForm'
    $('#theCatalogId').ajaxForm(options2);

});


// post-submit callback
function showResponse2(responseText, statusText, xhr, $form) {
    $('#create-catalog-name-label').empty();
    $('#create-catalog-catalog-file-label').empty();
    $("#create-catalog-catalog-image-file-label").empty();
    $("#create-catalog-supplier-label").empty();
    $("#create-quote-id-label").empty();
    $("#create-quote-supplier-label").empty();    

    if(responseText.indexOf('Failed') != -1){
        var errorMsg = responseText.substring(responseText.indexOf(':')+1);
        $("#colorbox").css({'height': 640 + 'px'});
        $("#cboxContent").css({'height': 625 + 'px'});
        $("#cboxLoadedContent").css({'height': 625 + 'px'});
        $('span#createCatalogReplyDiv').html("<br />Submission Failed: " + errorMsg);
    }else{
//        if('createCatalog' == $form.get(0).getAttribute('action')){
//            updateCatalogDiv(responseText);
//        }else{
//            $('div#lightbox-inline a.close').click();
//            $('span#message-board').html('&nbsp;&nbsp;&nbsp;Catalog updated successfully...');
//             var t=setTimeout(trunOffMessageBoard, 2000);
//            $('#create-catalog-btn-reset').click();
//            $('span#createCatalogReplyDiv').html("");
//        }
        //window.location.reload( true )
        window.location.href = 'catalog';
    }

    uploadProgress2(null, 0, 0, 100);
    $(".contain-progress-bar").fadeOut(100);

    return true;
}
// pre-submit callback
function showRequest2(formData, jqForm, options) {	
    clearCatalogErrors();
    $('span#createCatalogReplyDiv').html("<br />");


    if($("#catalog-id-field").attr('style')== "display: none;"){
        if(formData[0].value == '' ){
            $("#colorbox").css({'height': 640 + 'px'});
            $("#cboxContent").css({'height': 625 + 'px'});
            $("#cboxLoadedContent").css({'height': 625 + 'px'});
            if(formData[0].value == '') {
                $('#theCatalogNameId').append('<label id="create-catalog-name-label" for="catalog-name" generated="true" class="error" >Quote name is required</label>');
            }

            return false;
        }
        if($("#quote-id").val()==''){
            $('#theQuoteIdfield').append('<label id="create-quote-id-label" for="quote-id" generated="true" class="quote-error" >Quote Id is required</label>');
            return false;
        }
        
        if ($("#create-catalog-supplier-label").length) {        	
        	return false;
        } else if ($("#theCatalogId #supplierName").val()==''){        	
            $('#SupplierCompany').append('<label id="create-quote-supplier-label" for="supplierName" generated="true" class="quote-error" >Supplier Name is required</label>');
            return false;
        }

    }
    else{
        //if(formData[0].value == '' || formData[1].value == '') {
        if(formData[0].value == '' ){
            $("#colorbox").css({'height': 640 + 'px'});
            $("#cboxContent").css({'height': 625 + 'px'});
            $("#cboxLoadedContent").css({'height': 625 + 'px'});
            if(formData[0].value == '') {
                $('#theCatalogNameId').append('<label id="create-catalog-name-label" for="catalog-name" generated="true" class="error">Catalog name is required</label>');
            }

            return false;
        }       
        
        /* if supplier name field contains a value but corresponding id is not set in hidden field */
        if (($("#supplierName").val().length == 0 && $.trim($("#supplierNameDisplay").val()).length > 0)) {        	
        	//$('#SupplierCompany').append('<label id="create-catalog-supplier-label" for="supplierName" generated="true" class="quote-error" >Select a valid Supplier</label>');
        	return false;
        }       
       
    }
    
    if (!validateDateRange()) {
    	return false;
    }


//    if(formData[1].value == '') {
//    	$('#theCatalogFileId').find("#create-catalog-catalog-file-label").remove();
//    	$('#theCatalogFileId').append('<label id="create-catalog-catalog-file-label" for="catalog-file" generated="true" class="error">Catalog files can only be loaded as .csv, .tsv, .xlsx files</label>');
//    	return false;
//    } 

    if(jqForm.find('#catalog-file').val() != '') {
        var ext = jqForm.find('#catalog-file').val().split('.').pop().toLowerCase();

        if($.inArray(ext, ['xlsx','csv','tsv','zip','rar']) == -1) {
            $('#theCatalogFileId').find("#create-catalog-catalog-file-label").remove();
            $('#theCatalogFileId').append('<label id="create-catalog-catalog-file-label" for="catalog-file" generated="true" class="error">Catalog files can only be loaded as .csv, .tsv, .xlsx, .zip, .war files</label>');
            return false;
        }
    }




    if(jqForm.find('#image-file').val() != '') {
        var ext = jqForm.find('#image-file').val().split('.').pop().toLowerCase();

        if($.inArray(ext, ['zip']) == -1) {
            $('#theImageFileId').find("#create-catalog-catalog-image-file-label").remove();
            $('#theImageFileId').append('<label id="create-catalog-catalog-image-file-label" for="image-file" generated="true" class="error">Image files can only be loaded as .zip files</label>');
            return false;
        }
    }



    if('createCatalog' == jqForm.get(0).getAttribute('action')){

        if(getCatalogByName(formData[0].value) != null){
            $("#colorbox").css({'height': 640 + 'px'});
            $("#cboxContent").css({'height': 625 + 'px'});
            $("#cboxLoadedContent").css({'height': 625 + 'px'});
            $('span#createCatalogReplyDiv').html("<br />Submission Failed: The given catalog/quote name is already used.");
            return false;
        }

        if($("#quote-id").val()!= '' && getQuoteByQuoteIdnew($("#quote-id").val()) != null){
            $("#colorbox").css({'height': 640 + 'px'});
            $("#cboxContent").css({'height': 625 + 'px'});
            $("#cboxLoadedContent").css({'height': 625 + 'px'});
            $('span#createCatalogReplyDiv').html("<br />Submission Failed: The given Quote ID is already used.");
            return false;
        }
    }
    if('editCatalog' == jqForm.get(0).getAttribute('action')){
        var parentcat=   $('#parentcat-'+$("#catalogId").val()).val();
        if(parentcat=== undefined){
        	parentcat=$("#catalogParentId").val();
        }
        
        
        var catalogId="";
        catalogId=$("#theCatalogId #catalogId").val();
        
        
        var cat= getCatalogByName(formData[0].value);
        if(cat != null){
        	cat= JSON.parse(cat);
        } 
        if(cat != null && cat.catalogId != $("#catalogId").val() && cat.catalogId != parentcat){
            $("#colorbox").css({'height': 640 + 'px'});
            $("#cboxContent").css({'height': 625 + 'px'});
            $("#cboxLoadedContent").css({'height': 625 + 'px'});
            $('span#createCatalogReplyDiv').html("<br />Submission Failed: The given catalog/quote name is already used.");
            return false;
        }
        
        if($("#quote-id").val()!= '' && getQuoteByQuoteId($("#quote-id").val(),catalogId,parentcat) == null){
            $("#colorbox").css({'height': 640 + 'px'});
            $("#cboxContent").css({'height': 625 + 'px'});
            $("#cboxLoadedContent").css({'height': 625 + 'px'});
            $('span#createCatalogReplyDiv').html("<br />Submission Failed: The given Quote ID is already used.");
            return false;
        }
    }


    try{
        var bar = $('.bar');
        var percent = $('.percent');

        $(".contain-progress-bar").show();
        var percentVal = '0%';
        if(bar){
            bar.width(percentVal)
        }
        if(percent){
            percent.html(percentVal);
        }
    }catch(exp){
        alert(exp);
    }
    return true;
}
function showRequest3(formData, jqForm, options) {
    clearCatalogErrors();
    $('span#createCatalogReplyDiv').html("<br />");

    //if(formData[0].value == '' || formData[1].value == '') {
    if(formData[0].value == '' ){
        $("#colorbox").css({'height': 640 + 'px'});
        $("#cboxContent").css({'height': 625 + 'px'});
        $("#cboxLoadedContent").css({'height': 625 + 'px'});
        if(formData[0].value == '') {
            $('#theCatalogNameId').append('<label id="create-catalog-name-label" for="catalog-name" generated="true" class="error" >Quote name is required</label>');
        }



        return false;
    }
    if($("#quote-id").val()==''){
        $('#theQuoteIdfield').append('<label id="create-quote-id-label" for="quote-id" generated="true" class="quote-error" >Quote Id is required</label>');
        return false;
    }
    if($("#theCatalogId #supplierName option:selected").text()==''){
        $('#SupplierCompany').append('<label id="create-quote-supplier-label" for="supplierName" generated="true" class="quote-error" >Supplier Name is required</label>');
        return false;
    }

    if (!validateDateRange()) {
    	return false;
    }


//    if(formData[1].value == '') {
//    	$('#theCatalogFileId').find("#create-catalog-catalog-file-label").remove();
//    	$('#theCatalogFileId').append('<label id="create-catalog-catalog-file-label" for="catalog-file" generated="true" class="error">Catalog files can only be loaded as .csv, .tsv, .xlsx files</label>');
//    	return false;
//    }

    if(jqForm.find('#catalog-file').val() != '') {
        var ext = jqForm.find('#catalog-file').val().split('.').pop().toLowerCase();

        if($.inArray(ext, ['xlsx','csv','tsv','zip','rar']) == -1) {
            $('#theCatalogFileId').find("#create-catalog-catalog-file-label").remove();
            $('#theCatalogFileId').append('<label id="create-catalog-catalog-file-label" for="catalog-file" generated="true" class="error">Catalog files can only be loaded as .csv, .tsv, .xlsx, .zip, .war files</label>');
            return false;
        }
    }




    if(jqForm.find('#image-file').val() != '') {
        var ext = jqForm.find('#image-file').val().split('.').pop().toLowerCase();

        if($.inArray(ext, ['zip']) == -1) {
            $('#theImageFileId').find("#create-catalog-catalog-image-file-label").remove();
            $('#theImageFileId').append('<label id="create-catalog-catalog-image-file-label" for="image-file" generated="true" class="error">Image files can only be loaded as .zip files</label>');
            return false;
        }
    }



    if('createCatalog' == jqForm.get(0).getAttribute('action')){

        if(getCatalogByName(formData[0].value) != null){
            $("#colorbox").css({'height': 640 + 'px'});
            $("#cboxContent").css({'height': 625 + 'px'});
            $("#cboxLoadedContent").css({'height': 625 + 'px'});
            $('span#createCatalogReplyDiv').html("<br />Submission Failed: The given catalog name is already used.");
            return false;
        }
    }

    if(formData[1].value != '') {
        try{
            var bar = $('.bar');
            var percent = $('.percent');

            $(".contain-progress-bar").show();
            var percentVal = '0%';
            if(bar){
                bar.width(percentVal)
            }
            if(percent){
                percent.html(percentVal);
            }
        }catch(exp){
            alert(exp);
        }

    }
    return true;
}

/**
 * Creates a {@link Date} object specific from given milliseconds according to the given TimeZone
 * @param millis - UTC date represented as milliseconds
 * @param timeZoneOffset - offset of the time zone selected by user e.g., -08:00
 * @returns {Date}
 * @author Muhammad Haris
 */
function getTimeZoneSpecificDate(millis, timeZoneOffset) {
	/* default Date constructor creates Date according to client machine's time zone */
	var date = new Date(millis);
	var colonIndex = timeZoneOffset.indexOf(':');
	var clientTimeZoneOffset = date.getTimezoneOffset() * 60000; // convert to milliseconds
	var selectedTimeZoneOffset = parseInt(timeZoneOffset.substring(0,colonIndex)) * 60 * 60000;
	return new Date(date.getTime() + clientTimeZoneOffset + selectedTimeZoneOffset);
}

function validateDateRange() {
	
	var startDate = $('#start-date').val();
    var endDate = $('#end-date').val();
    var currentDate = new Date();
    
    /* if (startDate != null) {
    	if (startDate.getTime() < currentDate.getTime()) {
    		$('label[for="start-date-message"]').show();
            return false;
    	}
    }
    
    if (endDate != null) {
    	if (endDate.getTime() < currentDate.getTime()) {
    		$('label[for="end-date-message"]').show();
            return false;
    	}
    } */

    if (startDate != null && endDate !=null) {        
    	startDate = new Date(startDate);
    	endDate = new Date(endDate);
    	
        if(endDate.getTime() <= startDate.getTime()){
            $('label[for="end-date-message"]').show();
            return false;
        }
        
        var timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
        var diffDays = Math.floor(timeDiff / (1000 * 3600 * 24));
        
        if (diffDays == 0) {
        	$('label[for="end-date-message"]').show();
            return false;
        }
    }
    
    return true;
}

function clearCatalogErrors() {
    $('#theCatalogNameId #create-catalog-name-label').remove();
    $('#theCatalogFileId #create-catalog-catalog-file-label').remove();
    $('#theImageFileId #create-catalog-catalog-image-file-label').remove();
    $("#theQuoteIdfield #create-quote-id-label").remove();
    $("#SupplierCompany #create-quote-supplier-label").remove();
    $("#SupplierCompany #create-catalog-supplier-label").remove();
    $('label[for="start-date-message"]').hide();
    $('label[for="end-date-message"]').hide();
}
function getCatalogByName(catalogName){
    var catalog = null;
    try{
        $.ajax({
            type:'POST', url: getCatalogByNameJsonServiceUrl,
            data:'catalogName=' + catalogName,
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                if(data != null && data != ''){
                    catalog = data;
                }
            },
            error : function(data, textStatus, jqXHR){
                //alert(data);
                alert("textStatus=" + textStatus);
            },
            async:false
        });
    }catch(exp){
        alert(exp);
    }
    return catalog;
}
function getQuoteByQuoteIdnew(quoteId){
    var catalog = null;
    try{
        $.ajax({
            type:'POST', url: getQuoteByQuoteIdJsonServiceUrl,
            data:'quoteId=' + quoteId,
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                if(data != null && data != ''){
                    catalog = data;
                }
            },
            error : function(data, textStatus, jqXHR){
                //alert(data);
                alert("textStatus=" + textStatus);
            },
            async:false
        });
    }catch(exp){
        alert(exp);
    }
    return catalog;
}
function getQuoteByQuoteId(quoteId,catalogId,parentcat){
    var catalog = null;
    try{
        $.ajax({
            type:'POST', url: getQuoteByQuoteIdJsonEditServiceUrl,
            data:'quoteId=' + quoteId+'&catalogId='+catalogId+'&parentcat='+parentcat,
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                if(data != null && data != ''){
                    catalog = data;
                }
            },
            error : function(data, textStatus, jqXHR){
                //alert(data);
                alert("textStatus=" + textStatus);
            },
            async:false
        });
    }catch(exp){
        alert(exp);
    }
    return catalog;
}

function uploadProgress2(event, position, total, percent){
    try{
        var percentVal = percent + '%';

        var bar = $('.bar');
        if(bar){
            bar.width(percentVal)
        }

        var percentObj = $('.percent');
        if(percentObj){
            percentObj.html(percentVal);
        }

        // console.log('position=' + position + ", total=" + total + ", percent=" + percent);
    }catch(exp){
        alert(exp);
    }
}

function uploadProgress(event, position, total, percent){
    try{
        var percentVal = percent + '%';

        var bar = $('.bar');
        if(bar){
            bar.width(percentVal)
        }

        var percentObj = $('.percent');
        if(percentObj){
            percentObj.html(percentVal);
        }

        //console.log('position=' + position + ", total=" + total + ", percent=" + percent);
    }catch(exp){
        alert(exp);
    }
}
// pre-submit callback
function showRequest(formData, jqForm, options) {
    if(formData[0].value == '') {
        $('#edit-catalog-name-div').append('<label id="edit-catalog-name-label" for="catalog-name" generated="true" class="error" style="color:red">Required</label>');
        $("#colorbox").css({'height': 640 + 'px'});
        $("#cboxContent").css({'height': 625 + 'px'});
        $("#cboxLoadedContent").css({'height': 625 + 'px'});
        $('#editCatalogSubmitButton').attr("disabled",true);
        return false;
    }
    var queryString = $.param(formData);

    try{
        var bar = $('.bar');
        var percent = $('.percent');

        $(".contain-progress-bar").show();
        var percentVal = '0%';
        if(bar){
            bar.width(percentVal)
        }
        if(percent){
            percent.html(percentVal);
        }
    }catch(exp){
        alert(exp);
    }

    return true;
}

// post-submit callback
function showResponse(responseText, statusText, xhr, $form)  {
    if(responseText.indexOf('Failed') != -1){
        var errorMsg = responseText.substring(responseText.indexOf(':')+1);
        //alert('status: ' + statusText + '\n\nresponseText: \n' + responseText + '\n\nThe output div should have already been updated with the responseText.');
        $("#colorbox").css({'height': 640 + 'px'});
        $("#cboxContent").css({'height': 625 + 'px'});
        $("#cboxLoadedContent").css({'height': 625 + 'px'});
        $('span#editCatalogReplyDiv').html("<br />Submission Failed: " + errorMsg);
    }else{
        var catalog = null;
        try{
            //alert(responseText);
            catalog = jQuery.parseJSON(responseText);
        }catch(exp){}

//        if(catalog != null){
//            if(catalog.catalogId != gCurrentCatalog.catalogId){
//                updateCatalogDiv(catalog.catalogId);
//            }
//
//            // update the catalog in summary page
//            //$('div#' + catalog.catalogId + ' a').first().html(catalog.name);
//        }

        $('div#lightbox-inline-edit-catalog a.close').click();
        $('span#message-board').html('&nbsp;&nbsp;&nbsp;Catalog Saved Successfully...');
        var t=setTimeout(trunOffMessageBoard, 3000);

        uploadProgress(null, 0, 0, 100);
        $(".contain-progress-bar").fadeOut(100);
        window.location.reload( true )
    }
}

function trunOffMessageBoard(){
    $('span#message-board').text('');
}

function trunOffCreateProfileMessageBoard(){
    $('span#createProfileReplyDiv').html("");
}

function updateCatalogDiv(catalogId) {
    try{
        $.ajax({
            type:'POST', url: gCatalogDivFragmentServiceUrl,
            data:'catalogId=' + catalogId,
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                if(data != ''){
                    $('div#lightbox-inline a.close').click();
                    $('#working').append(data);
                    $('span#message-board').html('&nbsp;&nbsp;&nbsp;Catalog Created Successfully...');
                    var t=setTimeout(trunOffMessageBoard, 2000);
                    $('#create-catalog-btn-reset').click();
                    $('span#createCatalogReplyDiv').html("");
                }
            },
            error : function(jqXHR, textStatus, data){
                // nFor the Chrome browser the backend API will be called but JQuery will fire the "error" event
                // alert("textStatus=" + textStatus);
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function addCatalogProfile(catalogid) {

    //  alert(catalogid);
    $('input#catalogId').val(catalogid);

    return false;
}
function addCatalogApprover(catalogid) {

    //  alert(catalogid);
    $('input#addApproverCatalogId').val(catalogid);
    $('input#addApproversCatalogId').val(catalogid);
    return false;
}





//---------------------------------- PROFILE MANAGEMENT FUNCTIONS START HERE -------------------------------------------

function getProfileViewFilterOption(){
    var bViewActiveProfile= $('#viewActiveProfile').is(':checked');
    var bViewInactiveProfile= $('#viewInactiveProfile').is(':checked');
    var viewFilter = null;
    if(!bViewActiveProfile && bViewInactiveProfile) viewFilter = "INACTIVE";
    if(bViewActiveProfile && !bViewInactiveProfile) viewFilter = "ACTIVE";
    if(!bViewInactiveProfile && !bViewActiveProfile) viewFilter = "ALL";
    if(bViewInactiveProfile && bViewActiveProfile) viewFilter = "NONE";

    return viewFilter
}

ajaxInTransit = false;

function filterProfileView(){
    if(ajaxInTransit) return;

    ajaxInTransit = true;

    var viewFilter = getProfileViewFilterOption();
    var pageNum = $('#currentPageNum').val();
    var searchWithin = '';
    try{
        $.ajax({
            type:'POST',
            url: filterProfilesServiceUrl +'?pageNum=1&viewFilter=' + viewFilter + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_page_container_div').empty();
                    $('#profile_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function filterItemsRemoveMe(catalogId){
    var searchValue = $("#searchCriteria").val();

    /* if(searchValue == null || searchValue == '' || searchValue == 'Search Within...' ){
     alert('Enter search criteria');
     return;
     }*/

    try {
        $.ajax({
            type:'POST',
            url: "filterItems?catalogId="+catalogId+"&searchValue="+searchValue,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){

                    var responseArray = jqXHR.responseText.split('@@@');
                    var numOfItems = responseArray[0];      // header info
                    var itemsFragment = responseArray[1];   // fragment data
                    $('#catalog_item_table_rows_body').empty();
                    $('#catalog_item_table_rows_body').append(itemsFragment);
                    $('#catalog_items_current_page_num').html("Page 1 of "+responseArray[2]);
                    gCatalogItemTotalNumOfPages = responseArray[2];
                    ajaxInTransit = false;
                }
            },
            error: function (response){
                alert("Unexpected Error happend. Please try later.");
                ajaxInTransit = false;
            }
        });
    } catch (e) {
        alert("Unexpected Error happend. Please try later.");
    }

}



function goToProfileCatalogPage(pageNum){
    var profileCatalogTotalPageNum = $('input#profileCatalogTotalPageNum').val();
    if( (pageNum < 1 || pageNum > profileCatalogTotalPageNum) || ajaxInTransit) return;
    var list=persistCheckboxStateOnPageChange('disAssociateCatalogFromProfileForm');
    ajaxInTransit = true;

    var sortDirection = $('#sortDirection').val();
    var sortBy= $('#sortBy').val();
    var searchWithin = $('#searchWithinCatalogTextId').val();
    var profileId = $('#profileId').val();

    try{
        $.ajax({
            type:'POST',
            url: goToProfileCatalogPageServiceUrl +'?pageNum=' + pageNum +'&profileId=' + profileId + '&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#profile_ass_catalogs-holder").html(jqXHR.responseText);
                    $("#btnRemove").show();
                    ajaxInTransit = false;
                    copyCheckedItemList('disAssociateCatalogFromProfileForm',list);
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToCatalogProfilePage(pageNum){
    var profileCatalogTotalPageNum = $('input#profileCatalogTotalPageNum').val();
    var list=persistCheckboxStateOnPageChange('profiles-form');
    var catalogId = $('input#catalogId').val();
    if( (pageNum < 1 || pageNum > profileCatalogTotalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    try{
        $.ajax({
            type:'POST',
            url: goToCatalogProfilePageServiceUrl +'?pageNum=' + pageNum +'&catalogId=' + catalogId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#profile_page_container_div").html(jqXHR.responseText);
                    copyCheckedItemList('profiles-form',list);
                    ajaxInTransit = false;

                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function goToProfilePage(pageNum){
    var profileTotalPageNum = $('input#profileTotalPageNum').val();
    if( (pageNum < 1 || pageNum > profileTotalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    var viewFilter = getProfileViewFilterOption();
    var sortDirection = $('#sortDirection').val();
    var sortBy= $('#sortBy').val();
    var searchWithin = '';

    try{
        var list=persistCheckboxStateOnPageChange('profiles-form');
        $.ajax({
            type:'POST',
            url: goToPageProfilesServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_page_container_div').empty();
                    $('#profile_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();
                    copyCheckedItemList('profiles-form',list);
                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function activeProfiles(active) {

    var gIDs=prepareQueryString('profiles-form','profileIds');
    gIDs+='&active='+active;

    try{
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getProfileViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = '';

        $.ajax({
            type:'POST',
            url: activeProfilesServiceUrl+'?active='+active+'&pageNum=' + pageNum +'&viewFilter=' + viewFilter  +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_page_container_div').empty();
                    $('#profile_page_container_div').append(jqXHR.responseText);

                    $('#activeProfilesCount').html($('input#numOfActiveProfile').val());
                    $('#totalProfilesCount').html($('input#totalProfilesCount').val());
                    $('#inactiveProfilesCount').html($('input#totalProfilesCount').val() - $('input#numOfActiveProfile').val());

                    reAttachProfileHandlers();
                    resetCheckBoxList('profiles-form');

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function isAnyItemChecked(formId){
    persistCheckboxStateOnPageChange(formId);
    try{
        return ($("#" + formId).find("#checkedItems").html()!='');
    }catch(exp){
        alert(exp);
    }
    return false;
}

function deleteActualProfileProfiles() {
    try{
        if(!isAnyItemChecked('profiles-form')) {
            alert('Please select an item');
            handleDeleteMenuActionProfiles('lightbox-delete-profile-profiles', btnDelete, deleteProfileProfiles);
            return;
        }
        var gIDs=prepareQueryString('profiles-form','profileIds');


        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getProfileViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = '';

        $.ajax({
            type:'POST',
            url: deleteProfilesServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_page_container_div').empty();
                    $('#profile_page_container_div').append(jqXHR.responseText);

                    $('#activeProfilesCount').html($('input#numOfActiveProfile').val());
                    $('#totalProfilesCount').html($('input#totalProfilesCount').val());
                    $('#inactiveProfilesCount').html($('input#totalProfilesCount').val() - $('input#numOfActiveProfile').val());

                    reAttachProfileHandlers();
                    resetCheckBoxList('profiles-form');

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function reAttachProfileHandlers(){
    $(".delete-profile-profiles").click(function() {
        handleDeleteMenuActionProfiles('lightbox-delete-profile-profiles', this, deleteProfileProfiles);
    });

    // Reset button visibility
    setFunctionBtnsVisibility();
}

function handleDeleteMenuActionProfiles(id, elem, deleteAction) {
    $.colorbox({
        href:'#'+id,
        inline:true,
        onComplete: function(){
            $('#lighboxDeleteDetailsBtn').unbind('click').bind('click', function(){
                flag = true;

                deleteAction();

                $.colorbox.close();
                return false;
            });
            $('#lighboxDeleteDetailsCloseBtn').unbind('click').bind('click', function(){

                $.colorbox.close();
                return false;
            });
            $('#lighboxDeleteDetailsCancelLink').unbind('click').bind('click', function(){

                $.colorbox.close();
                return false;
            });
        }
    });
}

function goToNextCatalogPage(){
    var currentPageNum = parseInt( $('#naCatalogCurrentPageNum').val() );
    currentPageNum = currentPageNum + 1;
    goToCatalogPage(currentPageNum);
}

function goToPrevCatalogPage(){
    var currentPageNum = parseInt( $('#naCatalogCurrentPageNum').val() );
    currentPageNum = currentPageNum - 1;
    goToCatalogPage(currentPageNum);
}
function goToCatalogPage(pageNum){
    try{
        var list=persistCheckboxStateOnPageChange('associateCatalogToProfileForm');

        //var pageNum = $('#catalogCurrentPageNum').val();
        var catalogTotalPageNum = $('input#catalogTotalPageNum').val();

        if( (pageNum < 1 || pageNum > catalogTotalPageNum) || ajaxInTransit) return;

        ajaxInTransit = true;

        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var profileId = $('#profileId').val();

        $.ajax({
            type:'POST',
            url: goToCatalogPageServiceUrl +'?pageNum=' + pageNum  +'&profileId=' + profileId +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#profile_catalogs-holder").html(jqXHR.responseText);

                    $.colorbox({
                        href:'#add-catalogs',
                        open :true,
                        inline:true,
                        onComplete: function(){
                            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                                $.colorbox.close();
                                return false;
                            });
                        }
                    });
                    $("#btnRemove").show();
                    copyCheckedItemList('associateCatalogToProfileForm',list);
                    ajaxInTransit = false;
                }
            },
            error: function (){
                //alert(jqXHR.responseText);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function searchWithinCatalogs(){
    try{
        if( ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var profileId = $('#profileId').val();

        $.ajax({
            type:'POST',
            url: searchWithinCatalogServiceUrl + '?profileId=' + profileId +'&pageNum=' + 1 +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#profile_catalogs-holder").html(jqXHR.responseText);

                    ajaxInTransit = false;
                }
            },
            error: function (){
                //alert(jqXHR.responseText);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function sortCatalogs(){
    try{
        if( ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirection').val();
        if(sortDirection == 'up') sortDirection = 'down'; else sortDirection = 'up';
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var profileId = $('#profileId').val();

        $.ajax({
            type:'POST',
            url: sortCatalogsServiceUrl + '?profileId=' + profileId +'&pageNum=' + 1 +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#profile_catalogs-holder").html(jqXHR.responseText);

                    ajaxInTransit = false;
                }
            },
            error: function (){
                //alert(jqXHR.responseText);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function sortProfileCatalogs(){
    try{
        if( ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirection').val();
        if(sortDirection == 'up') sortDirection = 'down'; else sortDirection = 'up';
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var profileId = $('#profileId').val();

        $.ajax({
            type:'POST',
            url: sortProfileCatalogsServiceUrl + '?profileId=' + profileId +'&pageNum=' + 1 +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $("#profile_ass_catalogs-holder").html(jqXHR.responseText);
                    $("#btnRemove").show();
                    ajaxInTransit = false;
                }
            },
            error: function (){
                //alert(jqXHR.responseText);
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function associateCatalogToProfile() {
    try{
        if(!isAnyItemChecked('associateCatalogToProfileForm')){
            alert('Please select an item');
            return;
        }
        if(ajaxInTransit) return;
        ajaxInTransit = true;
        var gIDs=prepareQueryString('associateCatalogToProfileForm','associatedCatalogIds');
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var profileId = $('#profileId').val();
        $('input#searchWithinCatalogTextId').val('');
        searchWithinCatalogs();
        $('input#searchWithinCatalogTextId').val('Search within');
        $.ajax({
            type:'POST',
            url: associateCatalogToProfileServiceUrl + '?profileId=' + profileId + '&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(jqXHR.responseText);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedCatalog = responseArray[0];      // associatedCatalog info
                var nonAssociatedCatalog = responseArray[1];   // nonAssociatedCatalog data
                $("#profile_ass_catalogs-holder").html(associatedCatalog);
                $("#profile_catalogs-holder").html(nonAssociatedCatalog);
                $.colorbox.close();
                $("#btnRemove").show();
                ajaxInTransit = false;
                resetCheckBoxList('associateCatalogToProfileForm');
                resetCheckBoxList('disAssociateCatalogFromProfileForm');
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function disAssociateCatalogToProfile() {
    try{
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = $('#searchWithinCatalogTextId').val();
        var profileId = $('#profileId').val();
        var gIDs=prepareQueryString('disAssociateCatalogFromProfileForm','associatedCatalogIds');

        $.ajax({
            type:'POST',
            url: disAssociateCatalogFromProfileServiceUrl + '?profileId=' + profileId + '&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(jqXHR.responseText);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedCatalog = responseArray[0];      // associatedCatalog info
                var nonAssociatedCatalog = responseArray[1];   // nonAssociatedCatalog data
                $("#profile_ass_catalogs-holder").html(associatedCatalog);
                $("#profile_catalogs-holder").html(nonAssociatedCatalog);
                $("#btnRemove").show();
                $.colorbox.close();
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function validateCreateNewProfile(profileId, profileName){
    if(profileName == ''){
        $('#profile-name-div').append('<span class="error-msg" style="display: block; top:25px;width:315px" xmlns="http://www.w3.org/1999/html">You must enter a Valid Content View Name</span>');
        return false;
    }else if(profileExists(profileId, profileName)){
        $('span#createProfileReplyDiv').html("<br />Content View with name of '" + profileName + "' already exists");
        return false;
    }
    return true;
}

function createNewProfile(){
    $('#profile-name-div span').empty();
    $('span#createProfileReplyDiv').empty();

    var profileId = $('#profileId').val();
    var profileName = $('#profile-name').val();
    profileName = $.trim(profileName);
    $('#profile-name').val(profileName);

    if(!validateCreateNewProfile(profileId, profileName)) return;

    createNewProfileFollowUp();
}

function createNewProfileFollowUp(){
    var flag = true;
    try{

        if(ajaxInTransit) return;
        ajaxInTransit = true;
        $.ajax({
            type:'POST',
            url: createNewProfileServiceUrl,
            data: $('#createNewProfile').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {

                var responseArray = jqXHR.responseText.split('@@@');
                //alert(jqXHR.responseText);
                if(responseArray[0].indexOf("Error") == -1){

                    var associatedCatalog = responseArray[0];      // associatedCatalog info
                    var nonAssociatedCatalog = responseArray[1];   // nonAssociatedCatalog data
                    $("#profile_ass_catalogs-holder").html(associatedCatalog);
                    $("#profile_catalogs-holder").html(nonAssociatedCatalog);

                    if($('#profileId').val()=='') { // Create Profile
                        $('#profile-name').val('');
                        $('#profile-description').val('');
                        $('div.item label').removeClass('ui-state-active');
                        $('#rating').attr('checked', false);
                        $('#stateLinkId').html('Yes');
                    }
                    flag = true;
                }else{
                    flag = false;
                    $('span#createProfileReplyDiv').html("<br />Submission Failed: " + jqXHR.responseText);
                }
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            },
            complete: function () {
                if(flag){
                    window.location.href = profilesPageUrl;
                }
            }
        });
    }catch(exp){
        alert(exp);
    }                                                                               4
    return false;
}



// Associating Profiles to Catalog from overlay popup in catalog details page
function addCatalogProfiles() {
    if(!isAnyItemChecked('not-ass-profiles-form')){
        alert('Please select an item');
        return;
    }
    var gIDs=prepareQueryString('not-ass-profiles-form','profileIds');
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    try{
        var catalogId = $('#catalogId').val();
        $.ajax({
            type:'POST',
            url: addCatalogProfilesServiceUrl + "?catalogId=" + catalogId,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(jqXHR.responseText);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedProfile = responseArray[0];      // associatedProfile info
                var nonAssociatedProfile = responseArray[1];   // nonAssociatedProfile data

                $("#profile_page_container_div").html(associatedProfile);
                $("#catalog_notass_profiles-holder").html( nonAssociatedProfile );
                resetCheckBoxList('not-ass-profiles-form');
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
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

function clearProfileFormErrors() {
    clearProfileForm();
    $('#create-profile label.error').remove();
}
function clearProfileForm() {
    $("input[name='profileName']").val("");
    $("textarea[name='profileDesc']").val('');
}
// Creates a new profile and associates it to Catalog in catalog details page
function addCatalogProfile() {
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    if (!$("#createProfileId").validate().form()) {
        return;
    }
    try{
        $.ajax({
            type:'POST',
            url: addCatalogProfileServiceUrl,
            data: $('#createProfileId').serialize(),
            dataType: 'text',
            success: function(response) {
                //alert(response);
                $('#profile-name').val('');
                $('#profile-description').val('');
                $("#profile_page_container_div").html(response);
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    clearProfileFormErrors();
    return false;
}


function deleteCatalogProfiles(catalogId) {
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    var gIDs=prepareQueryString('profiles-form','profileIds');
    try{
        $.ajax({
            type:'POST',
            url: removeCatalogProfilesServiceUrl + "?catalogId=" + catalogId,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedProfile = responseArray[0];      // associatedProfile info
                var nonAssociatedProfile = responseArray[1];   // nonAssociatedProfile data

                $("#profile_page_container_div").html(associatedProfile);
                $("#catalog_notass_profiles-holder").html( nonAssociatedProfile );
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToCatalogNotAssProfilePage(pageNum){
    var profileTotalPageNum = $('input#notAssProfileTotalPageNum').val();
    if( (pageNum < 1 || pageNum > profileTotalPageNum) || ajaxInTransit) return;
    var list=persistCheckboxStateOnPageChange('not-ass-profiles-form');
    ajaxInTransit = true;
    try{
        var catalogId = $('#catalogId').val();
        $.ajax({
            type:'POST',
            url: goToCatalogNotAssProfilePageServiceUrl + "?catalogId=" + catalogId + '&pageNum=' + pageNum,
            data: $('#not-ass-profiles-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(jqXHR.responseText);
                $("#catalog_notass_profiles-holder").html(data);
                ajaxInTransit = false;
                copyCheckedItemList('not-ass-profiles-form',list);

            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function toggleCheckboxes(checkbox){
    try{
        if(!checkbox.checked){

            try{
                $(checkbox).next().addClass('ui-state-active');
                var checkbox = $(checkbox);
                checkbox.checked=true;


            }catch(exp){
                alert(exp);
            }

        }else{
            try{
                $(checkbox).next().removeClass('ui-state-active');
                var checkbox = $(checkbox);
                checkbox.checked=false;

            }catch(exp){
                alert(exp);
            }
        }
    }catch(exp){
        alert(exp);
    }
}
function toggleSupplierAttributeCheckboxes(checked){
    try{
        if(checked){
            $("#supplier_attribute_page_container_div input[type='checkbox']").each(function(){
                try{
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                    $(this).closest('tr').addClass('active-tr');

                }catch(exp){
                    alert(exp);
                }
            });
        }else{
            $("#supplier_attribute_page_container_div input[type='checkbox']").each(function(){
                try{
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                    $(this).closest('tr').removeClass('active-tr');
                }catch(exp){
                    alert(exp);
                }
            });
        }
    }catch(exp){
        alert(exp);
    }
}

function isAnySupplierAttributeChecked(formId){
    var isChecked = false;
    try{
        if(isAnyItemChecked('supplier-attributes-form')) isChecked=true;
    }catch(exp){
        alert(exp);
    }
    return isChecked;
}


function toggleAllProfileCheckbox(checked, formId){
    try{
        if(checked){
            $("#" + formId + " :checkbox").each(function(){
                try{
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                    //class="active-tr"
                    $(this).closest('tr').addClass('active-tr');
                }catch(exp){
                    alert(exp);
                }
            });
        }else{
            $("#" + formId + " :checkbox").each(function(){
                try{
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                    $(this).closest('tr').removeClass('active-tr');
                }catch(exp){
                    alert(exp);
                }
            });
        }
    }catch(exp){
        alert(exp);
    }
}



function toggleAllCheckboxItems(checked, formId){
    try{
        if(checked){
            $("#" + formId + " :checkbox").each(function(){
                try{
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                    //class="active-tr"
                    $(this).closest('tr').addClass('active-tr');
                }catch(exp){
                    alert(exp);
                }
            });
        }else{
            $("#" + formId + " :checkbox").each(function(){
                try{
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                    $(this).closest('tr').removeClass('active-tr');
                }catch(exp){
                    alert(exp);
                }
            });
        }
    }catch(exp){
        alert(exp);
    }
}

function sortProfiles(sortBy){
    try{
        if(ajaxInTransit) return;

        ajaxInTransit = true;

        var viewFilter = getProfileViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        if(sortDirection == 'up') sortDirection = 'down'; else sortDirection = 'up';
        var searchWithin = '';

        $.ajax({
            type:'POST',
            url: sortProfilesServiceUrl+'?pageNum=' + pageNum +'&viewFilter=' + viewFilter + '&sortBy=' + sortBy + '&sortDirection=' + sortDirection + '&searchWithin=' + searchWithin,
            data: $('#profiles-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_page_container_div').empty();
                    $('#profile_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function searchWithinProfiles(){
    try{
        if(ajaxInTransit) return;

        ajaxInTransit = true;

        var viewFilter = getProfileViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortBy= $('#sortBy').val();
        var sortDirection = $('#sortDirection').val();
        var searchWithin = $('#searchWithinId').val();

        $.ajax({
            type:'POST',
            url: searchWithinProfileServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter + '&sortBy=' + sortBy + '&sortDirection=' + sortDirection + '&searchWithin=' + searchWithin,
            data: $('#searchWithinProfileForm').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#profile_page_container_div').empty();
                    $('#profile_page_container_div').append(jqXHR.responseText);

                    reAttachProfileHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

//---------------------------------- PROFILE MANAGEMENT FUNCTIONS END HERE ---------------------------------------------


//---------------------------------- SUPPLIER MANAGEMENT FUNCTIONS START HERE -------------------------------------------
ajaxInTransit = false;

function getSupplierViewFilterOption(){
    var bViewActiveProfile= $('#viewActiveSupplier').is(':checked');
    var bViewInactiveProfile= $('#viewInactiveSupplier').is(':checked');
    var viewFilter = null;
    if(!bViewActiveProfile && bViewInactiveProfile) viewFilter = "INACTIVE";
    if(bViewActiveProfile && !bViewInactiveProfile) viewFilter = "ACTIVE";
    if(!bViewInactiveProfile && !bViewActiveProfile) viewFilter = "ALL";
    if(bViewInactiveProfile && bViewActiveProfile) viewFilter = "NONE";

    return viewFilter
}

function reAttachSupplierHandlers(){
    $(".delete-supplier-suppliers").click(function() {
        handleDeleteMenuActionSuppliers('lightbox-delete-suppliers', this, deleteSuppliers);
    });
}

function handleDeleteMenuActionSuppliers(id, elem, deleteAction) {
    if(!isAnyItemChecked('suppliers-form')) {
        alert('Please select an item');
        return;
    }
    $.colorbox({
        href:'#'+id,
        inline:true,
        onComplete: function(){
            $('#lighboxDeleteDetailsBtn').unbind('click').bind('click', function(){
                flag = true;

                deleteAction();

                $.colorbox.close();
                return false;
            });
            $('#lighboxDeleteDetailsCloseBtn').unbind('click').bind('click', function(){

                $.colorbox.close();
                return false;
            });
            $('#lighboxDeleteDetailsCancelLink').unbind('click').bind('click', function(){

                $.colorbox.close();
                return false;
            });
        }
    });
}

// Filter Active/Inactive suppliers
function filterSupplierView(val){
    if(ajaxInTransit) return;

    ajaxInTransit = true;

    var viewFilter = getSupplierViewFilterOption();
    if(val && val.length > 0){
        viewFilter = val;
    }
    var pageNum = $('#currentPageNum').val();
    var supplierType = $('#supplierType').val();

    var searchWithin = '';
    try{
        $.ajax({
            type:'POST',
            url: filterSuppliersServiceUrl +'?pageNum=1&viewFilter=' + viewFilter + '&searchWithin=' + searchWithin + '&supplierType=' + supplierType,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#supplier_page_container_div').empty();
                    $('#supplier_page_container_div').append(jqXHR.responseText);

                    reAttachSupplierHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function activeSuppliers(active) {
    try{
        var gIDs=prepareQueryString('suppliers-form','companyIds');
        gIDs+='&active='+active;
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getSupplierViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = '';
        var supplierType = $('#supplierType').val();
        $.ajax({
            type:'POST',
            url: activeSuppliersServiceUrl+'?active='+active+'&pageNum=' + pageNum +'&viewFilter=' + viewFilter  +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin + '&supplierType=' + supplierType,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#supplier_page_container_div').empty();
                    $('#supplier_page_container_div').append(jqXHR.responseText);

                    $('#activeSuppliersCount').html($('input#numOfActiveSupplier').val());
                    $('#totalSuppliersCount').html($('input#totalSuppliersCount').val());
                    $('#inactiveSuppliersCount').html($('input#totalSuppliersCount').val() - $('input#numOfActiveSupplier').val());

                    reAttachSupplierHandlers();
                    resetCheckBoxList('suppliers-form');

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

// Delete suppliers
function deleteSuppliers() {
    try{

        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getSupplierViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var searchWithin = '';
        var gIDs=prepareQueryString('suppliers-form','companyIds');
        var supplierType = $('#supplierType').val();
        $.ajax({
            type:'POST',
            url: deleteSuppliersServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter  +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin + '&supplierType=' +supplierType,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#supplier_page_container_div').empty();
                $('#supplier_page_container_div').append(data);

                $('#activeSuppliersCount').html($('input#numOfActiveSupplier').val());
                $('#totalSuppliersCount').html($('input#totalSuppliersCount').val());
                $('#inactiveSuppliersCount').html($('input#totalSuppliersCount').val() - $('input#numOfActiveSupplier').val());

                reAttachSupplierHandlers();
                resetCheckBoxList('suppliers-form');
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function searchWithinSuppliers(){
    try{
        if(ajaxInTransit) return;

        ajaxInTransit = true;

        var viewFilter = getSupplierViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortBy= $('#sortBy').val();
        var sortDirection = $('#sortDirection').val();
        var searchWithin = $('#searchWithinId').val();
        var supplierType = $('#supplierType').val();
        $.ajax({
            type:'POST',
            url: searchWithinSupplierServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter + '&sortBy=' + sortBy + '&sortDirection=' + sortDirection + '&searchWithin=' + searchWithin + '&supplierType=' + supplierType,
            data: $('#searchWithinProfileForm').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(data != ''){
                    //alert(jqXHR.responseText);
                    $('#supplier_page_container_div').empty();
                    $('#supplier_page_container_div').append(data);

                    reAttachSupplierHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function goToSupplierPage(pageNum){
    var supplierTotalPageNum = $('input#supplierTotalPageNum').val();
    if( (pageNum < 1 || pageNum > supplierTotalPageNum) || ajaxInTransit) return;
    var list=persistCheckboxStateOnPageChange('suppliers-form');
    ajaxInTransit = true;

    var viewFilter = getSupplierViewFilterOption();
    var sortDirection = $('#sortDirection').val();
    var sortBy= $('#sortBy').val();
    var searchWithin = $('#searchWithinId').val();
    var supplierType = $('#supplierType').val();
    if(searchWithin == 'Search Within...' ){
        searchWithin = '';
    }
    try{
        $.ajax({
            type:'POST',
            url: goToPageSuppliersServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + '&searchWithin=' + searchWithin + '&supplierType=' + supplierType,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#supplier_page_container_div').empty();
                    $('#supplier_page_container_div').append(jqXHR.responseText);

                    reAttachSupplierHandlers();

                    ajaxInTransit = false;
                    copyCheckedItemList('suppliers-form',list);
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function sortSuppliers(sortBy){
    try{
        if(ajaxInTransit) return;

        ajaxInTransit = true;

        var viewFilter = getSupplierViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        if(sortDirection == 'up') sortDirection = 'down'; else sortDirection = 'up';
        var searchWithin = '';
        var supplierType = $('#supplierType').val();
        $.ajax({
            type:'POST',
            url: sortSuppliersServiceUrl+'?pageNum=' + pageNum +'&viewFilter=' + viewFilter + '&sortBy=' + sortBy + '&sortDirection=' + sortDirection + '&searchWithin=' + searchWithin + '&supplierType=' + supplierType,
            data: $('#suppliers-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#supplier_page_container_div').empty();
                    $('#supplier_page_container_div').append(jqXHR.responseText);

                    reAttachSupplierHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function clearExitingErroMessags(){
    $('#company-name-div span').remove();
    $('#duns-number-div span').remove();
    $('#default-vendorId-div span').remove();
    $('#address-line1-div span').remove();
    $('#phoneNo-Div span').remove();
//    $('#state-div span').remove();
    $('#city-div span').remove();
    $('#zipcode-div span').remove();
    $('#contact-div span').remove();
//    $('#email-Div span').remove();
}

function validateCreateEditSupplierForm(){
    var retValue = true;
    var regex = /^([\w\s\n\t\.,"'?!;:#$%&\(\)\*\+-\/<>=@\[\]\^_{}\|~])*$/;
    var emailregex = /^[\w+\-.]+@[a-z\d\-.]+\.[a-z]+$/i;

    clearExitingErroMessags();

    if($('#companyName').val() == '' || !regex.test($('#companyName').val())){
        //$('#company-name-div').append('<br/>');
        $('#company-name-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid Company Name</span>');
        retValue = false;
    }
    /*if($('#dunsNumber').val() == '' || !regex.test($('#companyName').val())){
     $('#duns-number-div').append('<br/>');
     $('#duns-number-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid DUNS Name</span>');
     retValue = false;
     }*/
    if($('#defaultVendorId').val() == '' || !regex.test($('#defaultVendorId').val())){
        //$('#default-vendorId-div').append('<br/>');
        $('#default-vendorId-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid Default Supplier Id</span>');
        retValue = false;
    }
    if($('#addressLine1').val() == '' || !regex.test($('#addressLine1').val())){
        //$('#address-line1-div').append('<br/>');
        $('#address-line1-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid Address</span>');
        retValue = false;
    }

    if($('#countryRegion').val() == '1'){
        if($('#stateCountySelect').val() == '' || !regex.test($('#stateCountySelect').val())){
            //$('#state-div').append('<br/>');
            $('#state-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid State</span>');
            retValue = false;
        }
        if($('#city').val() == '' || !regex.test($('#city').val())){
            //$('#city-div').append('<br/>');
            $('#city-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid City</span>');
            retValue = false;
        }
        if($('#zipPostalCode').val() == '' || !regex.test($('#zipPostalCode').val())){
            //$('#zipcode-div').append('<br/>');
            $('#zipcode-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid Zip</span>');
            retValue = false;
        }

    }

    if($('#contactName').val() == '' || !regex.test($('#contactName').val())){
        //$('#contact-div').append('<br/>');
        $('#contact-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid Contact Name</span>');
        retValue = false;
    }
    /*if($('#phoneNumber').val() == '' || !regex.test($('#phoneNumber').val())){
     //$('#phoneNo-Div').append('<br/>');
     $('#phoneNo-Div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid Phone Number</span>');
     retValue = false;
     }
     if($('#emailAddress').val() == '' || !emailregex.test($('#emailAddress').val())){
     //$('#email-div').append('<br/>');
     $('#email-div').append('<span class="error-msg" style="display: block; top:25px;width:315px">You must enter a Valid Email Address</span>');
     retValue = false;
     }*/

    return retValue;
}
function createEditSupplier(returlUrl){

    $('#create-edit-supplier-div').html('');

    //Validation
    if(!validateCreateEditSupplierForm()) return;



    if(ajaxInTransit) return;
    ajaxInTransit = true;
    try{
        $.ajax({
            type:'POST',
            url: createEditSupplierServiceUrl,
            data: $('#supplier-form').serialize() +'&'+$('#supplierConfigForm').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);
                    if(jqXHR.responseText == 'Success'){
                        $('span#create-edit-supplier-div').html("<br />Submission was successful");
                    }else{
                        var message = jqXHR.responseText.substring(jqXHR.responseText.indexOf(':') + 1);
                        $('span#create-edit-supplier-div').html("<br />Submission Failed: " + message);
                    }
                }
                if(jqXHR.responseText == 'Success'){
                    for(i = 0; i < 2000; i++);
                    window.location.href = returlUrl;
                }
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function getSupplierAttributeViewFilterOption(){
    var bViewActiveProfile= $('#viewActiveSupplierAttribute').is(':checked');
    var bViewInactiveProfile= $('#viewInactiveSupplierAttribute').is(':checked');
    var viewFilter = null;
    if(!bViewActiveProfile && bViewInactiveProfile) viewFilter = "INACTIVE";
    if(bViewActiveProfile && !bViewInactiveProfile) viewFilter = "ACTIVE";
    if(!bViewInactiveProfile && !bViewActiveProfile) viewFilter = "ALL";
    if(bViewInactiveProfile && bViewActiveProfile) viewFilter = "NONE";

    return viewFilter
}

function goToSupplierAttributePage(pageNum){
    var checkbox=$("#checkedItems").html();
    var supplierAttributeTotalPageNum = $('input#supplierAttributeTotalPageNum').val();
    var list=persistCheckboxStateOnPageChange('supplier-attributes-form');
    if( (pageNum < 1 || pageNum > supplierAttributeTotalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    var viewFilter = getSupplierAttributeViewFilterOption();
    var sortDirection = $('#sortDirection').val();
    var sortBy= $('#sortBy').val();

    try{
        $.ajax({
            type:'POST',
            url: goToPageSupplierAttributesServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#supplier_attribute_page_container_div').empty();
                    $('#supplier_attribute_page_container_div').append(jqXHR.responseText);

                    reAttachSupplierAttributesHandlers();

                    ajaxInTransit = false;
                    copyCheckedItemList('supplier-attributes-form',list);
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

// Filter Active/Inactive supplierAttributes
function filterSupplierAttributeView(){
    if(ajaxInTransit) return;

    ajaxInTransit = true;

    var viewFilter = getSupplierAttributeViewFilterOption();
    var pageNum = $('#currentPageNum').val();

    try{
        $.ajax({
            type:'POST',
            url: filterSupplierAttributesServiceUrl +'?pageNum=1&viewFilter=' + viewFilter,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#supplier_attribute_page_container_div').empty();
                    $('#supplier_attribute_page_container_div').append(jqXHR.responseText);

                    reAttachSupplierAttributesHandlers();

                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function getSelectedSupplierAttributesNameValue(){
    var queryStr = "";
    $("#supplier_attribute_page_container_div input[type='checkbox']").each(function(){
        try{
            var checkbox = $(this)[0];
            if(checkbox.checked){
                if(checkbox.name == 'supplierAttributeIds'){
                    queryStr += '&' + checkbox.name + '=' + checkbox.value;
                }
            }

        }catch(exp){
            alert(exp);
        }
    });

    return queryStr;
}
function activeSupplierAttributes(active) {
    try{
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getSupplierAttributeViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var gIDs=prepareQueryString('supplier-attributes-form','supplierAttributeIds');

        $.ajax({
            type:'POST',
            url: activeSupplierAttributesServiceUrl+'?active='+active+'&pageNum=' + pageNum +'&viewFilter=' + viewFilter  +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy +'&' +gIDs,
//            data: $('#supplier-attributes-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    //alert(jqXHR.responseText);

                    $('#supplier_attribute_page_container_div').empty();
                    $('#supplier_attribute_page_container_div').append(jqXHR.responseText);

                    $('#activeSupplierAttributesCount').html($('input#numOfActiveSupplierAttribute').val());
                    $('#totalSupplierAttributesCount').html($('input#totalSupplierAttributesCount').val());
                    $('#inactiveSupplierAttributesCount').html($('input#totalSupplierAttributesCount').val() - $('input#numOfActiveSupplierAttribute').val());

                    reAttachSupplierAttributesHandlers();
                    noty({text: 'The supplier record has been updated, you may need to republish associated catalogs for the changes to take effect.', type: 'warning'});
                    ajaxInTransit = false;
                    resetCheckBoxList('supplier-attributes-form');
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

// Delete suppliers
function deleteSupplierAttributes() {
    try{

        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var viewFilter = getSupplierAttributeViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();
        var gIDs=prepareQueryString('supplier-attributes-form','supplierAttributeIds');

        $.ajax({
            type:'POST',
            url: deleteSupplierAttributesServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter  +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy +'&'+ gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {

                noty({text: 'The supplier record has been updated, you may need to republish associated catalogs for the changes to take effect.', type: 'warning'});

                $('#supplier_attribute_page_container_div').empty();
                $('#supplier_attribute_page_container_div').append(data);

                reAttachSupplierAttributesHandlers();
                resetCheckBoxList('supplier-attributes-form');
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


// Create Supplier Attributes
function createSupplierAttributes() {
    if($('#attributeName').val() == ''){
        alert("'AttributeName' can not be empty!");
        return false;
    }
    try{
        if(ajaxInTransit) return false;
        ajaxInTransit = true;

        var viewFilter = getSupplierAttributeViewFilterOption();
        var pageNum = $('#currentPageNum').val();
        var sortDirection = $('#sortDirection').val();
        var sortBy= $('#sortBy').val();

        var queryStr = "&attributeName=" + $('#attributeName').val();
        queryStr += "&attributeDescription=" + $('#attributeDescription').val();

        $.ajax({
            type:'POST',
            url: createSupplierAttributesServiceUrl +'?pageNum=' + pageNum +'&viewFilter=' + viewFilter  +'&sortDirection=' + sortDirection + '&sortBy=' + sortBy + queryStr,
            //data: $('#supplier-attributes-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#supplier_attribute_page_container_div').empty();
                $('#supplier_attribute_page_container_div').append(data);

                $('#activeSupplierAttributesCount').html($('input#numOfActiveSupplierAttribute').val());
                $('#totalSupplierAttributesCount').html($('input#totalSupplierAttributesCount').val());
                $('#inactiveSupplierAttributesCount').html($('input#totalSupplierAttributesCount').val() - $('input#numOfActiveSupplierAttribute').val());


                reAttachSupplierAttributesHandlers();
                noty({text: 'The supplier record has been updated, you may need to republish associated catalogs for the changes to take effect.', type: 'warning'});
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
        $('#addSupplierIdMapping').hide();
        $('#addAttrBtnHolder').hide();
    }catch(exp){
        alert(exp);
    }
    return false;
}
//Create Supplier Attributes
function removeSupplierAttributes() {
    $('#addSupplierIdMapping input').val('');
    $('#addSupplierIdMapping').hide();
    $('#addAttrBtnHolder').hide();
}
//
//// pre-submit callback
//function beforeCreateSupplierAttributes(formData, jqForm, options) {
//    $('span#createSupplierAttributeReplyDiv').html("<br />");
//
//    if(formData[0].value == '') {
//        $('#theCatalogNameId').append('<label id="create-catalog-name-label" for="catalog-name" generated="true" class="error">Required</label>');
//        return false;
//    }
//    return true;
//}
//
//// post-submit callback
//function afterCreateSupplierAttributes(responseText, statusText, xhr, $form) {
//    $('#create-catalog-name-label').empty();
//    $('#create-catalog-catalog-file-label').empty();
//
//    if(responseText.indexOf('Failed') != -1){
//        var errorMsg = responseText.substring(responseText.indexOf(':')+1);
//        $('span#createCatalogReplyDiv').html("<br />Submission Failed: " + errorMsg);
//        alert($('span#createCatalogReplyDiv').html());
//    }else{
//        //alert(responseText);
//        $('#supplier_attribute_page_container_div').empty();
//        $('#supplier_attribute_page_container_div').append(responseText);
//    }
//    return true;
//}
//
//// prepare the form when the DOM is ready
//$(document).ready(function() {
//    var createSupplierAttributesOptions = {
//        beforeSubmit:  beforeCreateSupplierAttributes,  // pre-submit callback
//        success:       afterCreateSupplierAttributes  // post-submit callback
//    };
//    // bind form using 'ajaxForm'
//    $('#create-supplier-attribute-form').ajaxForm(createSupplierAttributesOptions);
//});

//// Create Supplier Attributes
//function createSupplierAttributes() {
//// ATTEMPT TO DUPLICATE FILE INPUT, NO USE
////    var uploadedFile = $("input[type='file'][name='iconFileCopy']").val();
////    try{
////        var iconFile = $("input[type='file'][name='iconFileCopy']").clone().attr('id', 'iconFile').attr('name', 'iconFile').attr('value', uploadedFile);
////    }catch(exp){
////        alert(exp);
////    }
////    var lastInput = $('#attributeDescription');
////    $('#attributeDescription').after(iconFile);
//
//    $('#create-supplier-attribute-form').submit();
//}

function reAttachSupplierAttributesHandlers(){
    $(".delete-supplier-attributes").click(function() {
        handleDeleteMenuActionSupplierAttributes('lightbox-delete-supplier-attributes', this, deleteSupplierAttributes);
    });
}

function handleDeleteMenuActionSupplierAttributes(id, elem, deleteAction) {
    if(!isAnyItemChecked('supplier-attributes-form')) {
        alert("Please select an item");
        return;
    }
    $.colorbox({
        href:'#'+id,
        inline:true,
        onComplete: function(){
            $('#lighboxDeleteDetailsBtn').unbind('click').bind('click', function(){
                flag = true;

                deleteAction();

                $.colorbox.close();
                return false;
            });
            $('#lighboxDeleteDetailsCloseBtn').unbind('click').bind('click', function(){

                $.colorbox.close();
                return false;
            });
            $('#lighboxDeleteDetailsCancelLink').unbind('click').bind('click', function(){

                $.colorbox.close();
                return false;
            });
        }
    });
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function integerInRange(n) {
    var intRegex = /^-?\d+$/;
    if(intRegex.test(n)) {
        return !(n < -2147483648 || n > 2147483647);
    }
    return false;
}

function changeSupplierAttributeBoostLevel(selectObject, supplierId){
    var boostLevel = selectObject.value;
    if (typeof boostLevel == undefined || !integerInRange(boostLevel)) {
        return false;
    }
    var attributeId = selectObject.id;
    attributeId = attributeId.substring(attributeId.lastIndexOf("_") + 1, attributeId.length);
    try{
        $.ajax({
            type:'POST',
            url: boostSupplierAttributeLevelServiceUrl +'?attributeId=' + attributeId +'&supplierId=' + supplierId +'&boostLevel=' + boostLevel,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
                noty({text: 'The supplier record has been updated, you may need to republish associated catalogs for the changes to take effect.', type: 'warning'});
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return true;
}

function associateSupplierAttribute(checkboxObject, supplierId, boostValueId){
    var attributeId = checkboxObject.id;
    attributeId = attributeId.substring(attributeId.lastIndexOf("_") + 1, attributeId.length);
    var associate = checkboxObject.checked;
    var boostLevel = $('#' + boostValueId).val();
    var attributeName = $('#row_'+attributeId+' strong').html()
    try{
        $.ajax({
            type:'POST',
            url: changeAttributeAssociationServiceUrl +'?attributeId=' + attributeId +'&supplierId=' + supplierId +'&associate=' + associate +'&boostLevel=' + boostLevel +'&attributeName=' + attributeName,
            dataType: 'text',
            open:true,
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
                noty({text: 'The supplier record has been updated, you may need to republish associated catalogs for the changes to take effect.', type: 'warning'});
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function popupUploadSupplierLogoOverlay(){
    $.colorbox({
        href:'#lightbox-inline-supplier-logo',
        inline:true,
        onComplete: function(){
            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                $.colorbox.close();
                return false;
            });
        }
    });
    return false;
}

function changeSupplierMinOrder(newValue, supplierId){
    if(supplierId == '') supplierId = '000';
    try{
        $.ajax({
            type:'POST',
            url: updateSupplierMinOrderServiceUrl +'?newValue=' + newValue +'&editSupplierId=' + supplierId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}
var resetSupplierCatalog = false;
function updateSupplierCatalog(newValue, supplierId){
	if(resetSupplierCatalog) {
		resetSupplierCatalog = false;
	} else if(supplierId == '') {
		updateNonSupplierCatalog(newValue, supplierId);
	} else {
		approveNonCatalogSupplierAction(
				function() {
					updateNonSupplierCatalog(newValue, supplierId);
				}, 
				function() {
					resetSupplierCatalog = true;
					$('#nonCatalogSupplier').trigger("click");
				});		
	}
}
function updateNonSupplierCatalog(newValue, supplierId){
    if(supplierId == '') supplierId = '000';
    try{
        $.ajax({
            type:'POST',
            url: updateSupplierCatalogServiceUrl +'?newValue=' + newValue +'&editSupplierId=' + supplierId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function updateSingleSourceSupplier(newValue, supplierId) {
    if (!supplierId) supplierId = '000';
    try {
        var url = updateSingleSourceSupplierUrl +'?newValue=' + newValue +'&editSupplierId=' + supplierId;
        $.ajax({
            type:'POST',
            url: url,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    } catch(exp) {
        alert(exp);
    }
}

function updateSupplierIncludeCard(newValue, supplierId){
    if(supplierId == '') supplierId = '000';
    try{
        $.ajax({
            type:'POST',
            url: updateSupplierIncludeCardServiceUrl +'?newValue=' + newValue +'&editSupplierId=' + supplierId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function updateSupplierDisableBrowse(newValue, supplierId){
    if(supplierId == '') supplierId = '000';
    try{
        $.ajax({
            type:'POST',
            url: updateSupplierDisableBrowseUrl +'?newValue=' + newValue +'&editSupplierId=' + supplierId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function updateSupplierCountryCode(newValue, supplierId){
    if(supplierId == '') supplierId = '000';
    try{
        $.ajax({
            type:'POST',
            url: updateSupplierCountryCodeUrl +'?newValue=' + newValue +'&editSupplierId=' + supplierId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

// prepare the form when the DOM is ready
$(document).ready(function() {
    var uploadSupplierLogoOptions = {
        beforeSubmit:  beforeUploadSupplierLogo,  // pre-submit callback
        success:       afterUploadSupplierLogo  // post-submit callback
    };
    // bind form using 'ajaxForm'
    $('#supplier-logo-form').ajaxForm(uploadSupplierLogoOptions);
});

function beforeUploadSupplierLogo(responseText, statusText, xhr, $form){
    return true;
}
function afterUploadSupplierLogo(responseText, statusText, xhr, $form){
//    $('#supplier-logo-file-row span em').text('');
//    $.colorbox.close();
//    $('#supplier-logo-fragment-holder').empty();
//    $('#supplier-logo-fragment-holder').append(responseText);
//    $('#supplierLogoId').val($('#gSupplierLogo').val());
    $("#supplier-logo-form .popup .alt-close").click();
    $("#supplier-logo-form input[name='supplierLogo']").val(responseText);
    $("#supplier-form input[name='logo']").val(responseText);

    addShowSupplierLogo();
    return true;
}

function removeSupplierLogo(supplierId){
    try{
        $.ajax({
            type:'POST',
            url: removeSupplierLogoServiceUrl +'?supplierId=' + supplierId,
            dataType: 'text',
            open:true,
            success: function(responseText, textStatus, jqXHR) {
//                $('#supplierLogoId').val('');
//                $('#supplier-logo-fragment-holder').empty();
//                $('#supplier-logo-fragment-holder').append(responseText);
                $('#supplierLogoId').val('');
                ajaxInTransit = false;
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
}

function activateSupplierUsers(active) {
    try{
        if(ajaxInTransit) return;
        ajaxInTransit = true;

        var pageNum = $('#supplierApproverCurrentPageNum').val();

        $.ajax({
            type:'POST',
            url: activateSupplierUsersServiceUrl + '?active=' + active + '&pageNum=' + pageNum,
            data: $('#supplier-users-form').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    $('#supplier-user-data').empty();
                    $("#supplier-user-data").html(data);
                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function validateCreateAttributesForm(){
    var attributeId = '';
    var attributeName = $('#attribute-name').val();
    if(attributeName == ''){

        $('#attribute-name-div').append('<span class="error-msg" style="display: inline; width: 215px; text-align: left;">You must enter a valid Attribute Name</span>');

        return false;
    }

    if(attributeExists(attributeId, attributeName)) {
        $('#attribute-name-div').append('<span class="error-msg" style="display: inline; width: 215px; text-align: left;">Attribute name already exists</span>');

        return false;
    }

    return true;
}
function attributeExists(attributeId, attributeName) {
    var attributeExists = false;
    $.ajax({
        type:'POST',
        url: supplierAttrExistsServiceUrl,
        data:'attributeId=' + attributeId + '&attributeName=' + attributeName,
        async: false,
        success: function(response) {
            attributeExists = (response == 'true')? true : false;
        }
    });
    return attributeExists;
}
//---------------------------------- SUPPLIER MANAGEMENT FUNCTIONS END HERE ---------------------------------------------

function createProperty() {
    try{
        $.ajax({
            type:'POST',
            url: createPropertyServiceUrl,
            data: $('#propertiesForm').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#propertiesTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function activeSupplier(active) {
//	var profileIds = [];
//	$('.table-data input:checked').each(function() {
//		profileIds.push(this.id);
//    });
//	var postData = { 'profileIds': profileIds };
    try{
        $.ajax({
            type:'POST',
            url: activeSupplierServiceUrl+'?active='+active,
            data: $('#suppliers-form').serialize(),
            dataType: 'text',
            success: function(response) {
//                if(response == 'success'){
//                    alert('Form submitted successfully.');
//                	$('#updateCatalogProfilesFeedback').html("<font color='red' size='2'>Saving "+ response + "</font>");
//                    var t=setTimeout(trunOffMessage,1000);
                location.reload();
//                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function deleteSupplier() {
    try{
        $.ajax({
            type:'POST',
            url: removeCatalogProfilesServiceUrl,
            data: $('#suppliers-form').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#supplierTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function validateCreateApproversForm(){
    var retValue = true;

    if($('#first-name').val() == ''){
        //$('#first-name-div').append('<br/>');
        $('#first-name-div').append('<span class="error-msg" style="display: block; top:25px;width:315px;margin-left:308px">You must enter a valid First Name</span>');
        retValue = false;
    }

    if($('#last-name').val() == ''){
        $('#last-name-div').append('<span class="error-msg" style="display: block; top:25px;width:315px;margin-left:308px">You must enter a valid Last Name</span>');
        retValue = false;
    }

    if($('#e-mail').val() == ''){
        $('#e-mail-div').append('<span class="error-msg" style="display: block; top:25px;width:315px;margin-left:308px">You must enter a valid E-mail</span>');
        retValue = false;
    }

    return retValue;
}

ajaxInTransit = false;

function initCreateApproversPopup(){
    clearCreateErrorMessages();
    $('input#first-name').val('');
    $('input#last-name').val('');
    $('input#e-mail').val('');
}

function clearCreateErrorMessages(){
    $('#first-name-div span').remove();
    $('#last-name-div span').remove();
    $('#e-mail-div span').remove();
}

function editSupplier() {
    try{
        $.ajax({
            type:'POST',
            url: editSupplierServiceUrl,
            data: $('#editSupplier').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#supplierTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}




//---------------------------------------- CATALOG APPROVER FUNCTIONS START HERE ---------------------------------------
ajaxInTransit = false;

function createApprover() {
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    clearCreateErrorMessages();

    if(!validateCreateApproversForm()){
        return;
    }
    try{
        $.ajax({
            type:'POST',
            url: 'create-new-approver',
            data: $('#createApprover').serialize(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedProfile = responseArray[0];      // associatedApprovers info
                var nonAssociatedProfile = responseArray[1];   // nonAssociatedApprovers data
                $("#catalog-suppliers-approvers-div").html(associatedProfile);
                $("#catalogNotAssociateApproverDiv").html( nonAssociatedProfile );

                $("#first-name").val('');
                $("#last-name").val('');
                $("#e-mail").val('');


                reAttachRemoveCatalogApproverHandlers();
                ajaxInTransit = false;
                $.colorbox.close();
                //reAttachRemoveSupplilerApproverHandlers();
            },
            error: function (){
                ajaxInTransit = false;
                $.colorbox.close();
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function addCatalogApprovers(){
    var  catalogId=$("#addApprover-form").find("input[name=catalogId]").val();
    var gIDs=prepareQueryString('addApprover-form','approverIds');
    gIDs+='&catalogId='+catalogId;
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    try{
        $.ajax({
            type:'POST',
            url: addCatalogAproversServiceUrl,
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedProfile = responseArray[0];      // associatedApprovers info
                var nonAssociatedProfile = responseArray[1];   // nonAssociatedApprovers data
                $("#catalog-suppliers-approvers-div").html(associatedProfile);
                $("#catalogNotAssociateApproverDiv").html( nonAssociatedProfile );
                reAttachRemoveCatalogApproverHandlers();
                resetCheckBoxList('addApprover-form');
                ajaxInTransit = false;
                $.colorbox.close();
            },
            error: function (){
                ajaxInTransit = false;
                $.colorbox.close();
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function addSupplierApprovers(){
    var gIDs=prepareQueryString('addApprover-form','approverIds');
    var supplierId=$("#addApprover-form").find("input[name=supplierId]").val();
    gIDs+='&supplierId='+supplierId;
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    try{
        $.ajax({
            type:'POST',
            url: 'addSupplierApprovers',
            data: gIDs,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedApprovers = responseArray[0];      // associated Approvers info
                var nonAssociatedApprovers = responseArray[1];   // nonAssociated Approvers data
                $("#catalog-suppliers-approvers-div").html(associatedApprovers);
                $("#supplierNotAssociateApproverDiv").html( nonAssociatedApprovers );
                ajaxInTransit = false;
                resetCheckBoxList('addApprover-form');
                $.colorbox.close();
            },
            error: function (){
                ajaxInTransit = false;
                $.colorbox.close();
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function removeSupplierApprovers(){
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    var gIDs=prepareQueryString('assignApproversForm','approverIds');
    try{
        $.ajax({
            type:'POST',
            url: 'removeSupplierApprovers',
            data: gIDs+'&supplierId='+$('#approverSupplierId').val()+'&check-all3=on',
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedApprovers = responseArray[0];      // associated Approvers info
                var nonAssociatedApprovers = responseArray[1];   // nonAssociated Approvers data
                $("#catalog-suppliers-approvers-div").html(associatedApprovers);
                $("#supplierNotAssociateApproverDiv").html( nonAssociatedApprovers );
                //reAttachRemoveSupplilerApproverHandlers();
                ajaxInTransit = false;
                $.colorbox.close();
            },
            error: function (){
                ajaxInTransit = false;
                $.colorbox.close();
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}


function goToSupplierApproverPage(pageNum, supplierId){
    var approverTotalPageNum = $('input#approverTotalPageNum').val();
    var list=persistCheckboxStateOnPageChange('assignApproversForm');
    if( (pageNum < 1 || pageNum > approverTotalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    try{
        $.ajax({
            type:'POST',
            url: 'goToPageSupplierApprover?pageNum=' + pageNum +'&supplierId=' + supplierId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(data != ''){
                    //alert(data);
                    var responseArray = data.split('@@@');
                    var associatedApprovers = responseArray[0];      // associated Approvers info
                    var nonAssociatedApprovers = responseArray[1];   // nonAssociated Approvers data
                    $("#catalog-suppliers-approvers-div").html(associatedApprovers);
                    $("#supplierNotAssociateApproverDiv").html( nonAssociatedApprovers );
                    //reAttachRemoveSupplilerApproverHandlers();
                    copyCheckedItemList('assignApproversForm',list);
                    ajaxInTransit = false;
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToNonSupplierApproverPage(pageNum){
    var nonApproverTotalPageNum = $('input#nonApproverTotalPageNum').val();
    var list=persistCheckboxStateOnPageChange('addApprover-form');
    if( (pageNum < 1 || pageNum > nonApproverTotalPageNum) || ajaxInTransit) return;

    ajaxInTransit = true;

    try{
        $.ajax({
            type:'POST',
            url: 'goToPageNonSupplierApprover?pageNum=' + pageNum,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(data != ''){
                    //alert(data);
                    var responseArray = data.split('@@@');
                    var associatedApprovers = responseArray[0];      // associated Approvers info
                    var nonAssociatedApprovers = responseArray[1];   // nonAssociated Approvers data
                    $("#catalog-suppliers-approvers-div").html(associatedApprovers);
                    $("#supplierNotAssociateApproverDiv").html( nonAssociatedApprovers );
                    //reAttachRemoveSupplilerApproverHandlers();
                    ajaxInTransit = false;
                    copyCheckedItemList('addApprover-form',list);
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function deleteCatalogApprovers() {
    if(ajaxInTransit) return;
    ajaxInTransit = true;
    var gIDs=prepareQueryString('assignApproversForm','approverIds');
    var catalogId=$("#catalogId").val();
    try{
        $.ajax({
            type:'POST',
            url: removeCatalogApproversServiceUrl,
            data: gIDs+'&catalogId='+catalogId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                //alert(data);
                var responseArray = jqXHR.responseText.split('@@@');
                var associatedApprovers = responseArray[0];      // associated Approvers info
                var nonAssociatedApprovers = responseArray[1];   // nonAssociated Approvers data
                $("#catalog-suppliers-approvers-div").html(associatedApprovers);
                $("#supplierNotAssociateApproverDiv").html( nonAssociatedApprovers );
                reAttachRemoveCatalogApproverHandlers();
                ajaxInTransit = false;
                resetCheckBoxList('assignApproversForm');
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToCatalogApproverPage(pageNum, catalogId){
    var approverTotalPageNum = $('input#approverTotalPageNum').val();
    var list=persistCheckboxStateOnPageChange('assignApproversForm');
    if( (pageNum < 1 || pageNum > approverTotalPageNum) || ajaxInTransit || catalogId==null || catalogId == '') return;

    ajaxInTransit = true;

    try{
        $.ajax({
            type:'POST',
            url: goToPageCatalogApproverServiceUrl +'?pageNum=' + pageNum +'&catalogId=' + catalogId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(data != ''){
                    //alert(data);
                    var responseArray = data.split('@@@');
                    var associatedProfile = responseArray[0];      // associatedApprovers info
                    var nonAssociatedProfile = responseArray[1];   // nonAssociatedApprovers data
                    $("#catalog-suppliers-approvers-div").html(associatedProfile);
                    $("#catalogNotAssociateApproverDiv").html( nonAssociatedProfile );
                    reAttachRemoveCatalogApproverHandlers();
                    ajaxInTransit = false;
                    copyCheckedItemList('assignApproversForm',list);
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function goToNonCatalogApproverPage(pageNum, catalogId){
    var nonApproverTotalPageNum = $('input#nonApproverTotalPageNum').val();
    var list=persistCheckboxStateOnPageChange('addApprover-form');
    if( (pageNum < 1 || pageNum > nonApproverTotalPageNum) || ajaxInTransit || catalogId==null || catalogId == '') return;

    ajaxInTransit = true;

    try{
        $.ajax({
            type:'POST',
            url: goToPageNonCatalogApproverServiceUrl +'?pageNum=' + pageNum +'&catalogId=' + catalogId,
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                if(data != ''){
                    //alert(data);
                    var responseArray = data.split('@@@');
                    var associatedProfile = responseArray[0];      // associatedApprovers info
                    var nonAssociatedProfile = responseArray[1];   // nonAssociatedApprovers data
                    $("#catalog-suppliers-approvers-div").html(associatedProfile);
                    $("#catalogNotAssociateApproverDiv").html( nonAssociatedProfile );
                    reAttachRemoveCatalogApproverHandlers();
                    ajaxInTransit = false;
                    copyCheckedItemList('addApprover-form',list);
                }
            },
            error: function (){
                ajaxInTransit = false;
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function reAttachRemoveCatalogApproverHandlers(){
    $(".delete-approvers").click(function() {
        persistCheckboxStateOnPageChange('assignApproversForm');
        if($('#assignApproversForm').find("#checkedItems").html()==''){
            alert('Please select an item');
            return ;
        }
        handleDeleteMenuActionProfiles('lightbox-delete-profiles', this, deleteCatalogApprovers);
    });
}

//------------------------------------------ CATALOG APPROVER FUNCTIONS END HERE ---------------------------------------
function getCustomFields(catalogId,catalogType) {
    return getCustomFields(catalogId,catalogType,1);
}
function getCustomFields(catalogId,catalogType, pageNumber) {
    var list=persistCheckboxStateOnPageChange('add-custom-field-form-id');
    if(pageNumber==undefined) {
        pageNumber = 1;
    }
    try{
        $.ajax({
            type:'POST',
            url: getCustomFieldServiceUrl+"?catalogType="+catalogType+"&pageNumber="+pageNumber,
            data:'catalogId=' + catalogId,
            success: function(response) {
                if(response != ''){
//                    var customField = eval( "(" + response + ")" );
//                    populateCustomFields(customField);
                    $.colorbox({
                        href:'#add-custom-field',
                        inline:true,
                        onComplete: function(){
                            $("#add-custom-field-form-id").html( response );
                            initTableUI();
//                            $("#cboxLoadedContent").css({'height': 625 + 'px'});
                            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                                $.colorbox.close();
                                return false;
                            });
                            copyCheckedItemList('add-custom-field-form-id',list);
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
function getAttributeDetails(attId,unitId) {
    try{
        $.ajax({
            type:'POST',
            url: getAttributeUrl+"?attributeId="+attId+"&unitId="+unitId,
            data:"",
            success: function(response) {
                if(response != ''){
//                    var customField = eval( "(" + response + ")" );
//                    populateCustomFields(customField);
                    $("#theCompanyAttributeDiv").html( response );
                    $.colorbox({
                        href:'#companyAttributeDetails',
                        inline:true,
                        onComplete: function(){
                            $("#cboxLoadedContent").css({'height': 625 + 'px'});
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
function getCompanyDetails(email,supplierId) {
    try{
        $.ajax({
            type:'POST',
            url: editCompanyUserUrl+"?supplierId="+supplierId+"&emailAddress="+email,
            data:"",
            success: function(response) {
                if(response != ''){
//                    var customField = eval( "(" + response + ")" );
//                    populateCustomFields(customField);
                    $("#theCompanyUserDiv").html( response );
                    $.colorbox({
                        href:'#editCompanyUser',
                        inline:true,
                        onComplete: function(){
                            $("#cboxLoadedContent").css({'height': 625 + 'px'});
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

function getVendorIdDetails(vendorName,systemId,clientId) {
    try{
        $.ajax({
            type:'POST',
            url: getVendorIdDetailsUrl+"?vendorName="+vendorName+"&systemId="+systemId+"&clientId="+clientId,
            data:"",
            success: function(response) {
                if(response != ''){
//                    var customField = eval( "(" + response + ")" );
//                    populateCustomFields(customField);
                    $("#theSystemVendorDiv").html( response );
                    $.colorbox({
                        href:'#systemVendorDetails',
                        inline:true,
                        onComplete: function(){
                            $("#cboxLoadedContent").css({'height': 625 + 'px'});
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



function populateCustomFields(customField){
    try{

        $.colorbox({
            href:'#add-custom-field',
            inline:true,
            onComplete: function(){
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    $.colorbox.close();
                    return false;
                });
            }
        });

        $('#edit-custom-field-id').val(catalogCustomField.customFieldId);
        $('#edit-id').val(catalogCustomField.id);
        $('[name="catalogId"]').val(catalogCustomField.catalogId);
        $('#edit-field-name').val(catalogCustomField.fieldName);
        $('#edit-display-name').val(catalogCustomField.displayName);
        $('#edit-description').val(catalogCustomField.fieldDesc);
        $('[name="fieldType"]').val(catalogCustomField.fieldType);
        $('[name="searchBoost"]').val(catalogCustomField.searchBoost);
        $('[name="defaultValue"]').val(catalogCustomField.defaultValue);

    }catch(exp){
        alert(exp);
    }
}


function getCatalogCustomField(catalogCustomFieldId) {
    try{
        $.ajax({
            type:'POST', url: getCatalogCustomFieldServiceUrl,
            data:'catalogCustomFieldId=' + catalogCustomFieldId,
            success: function(response) {
                if(response != ''){
                    var catalogCustomField = eval( "(" + response + ")" );
                    populateCatalogCustomField(catalogCustomField);
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}
function createSupplier() {
    try{
        $.ajax({
            type:'POST',
            url: createSupplierServiceUrl,
            data: $('#createSupplier').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#catalogApproverTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function createSystemVendorId() {
    try{
        $.ajax({
            type:'POST',
            url: createSystemVendorIdUrl,
            data: $('#systemVendors').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#sysVendorTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function createCompanyUsers() {
    try{
        $.ajax({
            type:'POST',
            url: createCompanyUsersUrl,
            data: $('#usersForm').serialize(),

            dataType: 'text',
            success: function(response) {
                $("#companyUsersTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function editCompanyUsers() {
    try{
        $.ajax({
            type:'POST',
            url: editCompanyUsersUrl,
            data: $('#editUsersForm').serialize(),

            dataType: 'text',
            success: function(response) {
                $("#companyUsersTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function editCompanyAttribute() {
    try{
        $.ajax({
            type:'POST',
            url: editCompanyAttributeUrl,
            data: $('#editCompanyAttributeForm').serialize(),

            dataType: 'text',

            success: function(response) {
                if(response != ''){
                    $("#companyAttributesTableId").html( response );
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function editSystemVendor() {
    try{
        $.ajax({
            type:'POST',
            url: editSystemVendorUrl,
            data: $('#editSystemVendorForm').serialize(),

            dataType: 'text',
            success: function(response) {
                $("#sysVendorTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function createCompanyAttribute() {
    try{
        $.ajax({
            type:'POST',
            url: createCompanyAttributeServiceUrl,
            data: $('#companyAttributeForm').serialize(),
            dataType: 'text',
            success: function(response) {
                $("#companyAttributesTable").html( response );
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function setDefaultValue(field){
    $("#defaultValue").val(field.value);
}

function resetDefaultValue(){
    $("#defaultValue").val("");
    $("#defArea3").val("");
    $("#defArea4").val("");
}

function setSearchBoost(field){
    $("#searchBoost").val(field.options[field.selectedIndex].value);
}
var outputRecords;
var pNo;
//function removeCatalogItems() {
//    try{
//        $.ajax({
//            type:'POST',
//            url: removeCatalogItemss+"?pageNum="+gCatalogItemsPageNum,
//            data: $('#cat-items-form').serialize(),
//            dataType: 'text',
//            success: function(response) {
//                $("#catalog_item_table_rows_body").html( response );
//                var pageNum = gCatalogItemsPageNum;
//                if(pNo != pageNum)
//                    pageNum = pNo;
////                    alert("here,pageNum"+pageNum);                     //leaving alerts for future debugging.
////                    var responseArray = jqXHR.responseText.split('@@@');
////                    var numOfItems = responseArray[0];      // header info
////                    var itemsFragment = responseArray[1];   // fragment data
////                    alert("outputRecords"+outputRecords);
//                gCatalogItemTotalNumOfItems = outputRecords;
////                    alert("gCatalogItemTotalNumOfItems: "+gCatalogItemTotalNumOfItems+"gCatalogItemPageSize: "+gCatalogItemPageSize);
//                gCatalogItemTotalNumOfPages = Math.ceil(gCatalogItemTotalNumOfItems/ gCatalogItemPageSize);
////                    alert("gCatalogItemTotalNumOfPages: "+gCatalogItemTotalNumOfPages);
////                      $('#catalog_item_table_rows_body').empty();
////                    $('#catalog_item_table_rows_body').append(itemsFragment);
//                $('span#catalog_items_current_page_num').html('Page '+ pageNum + ' of ' + gCatalogItemTotalNumOfPages);
//                if(pageNum!=0){
//                    var begingRecord = (pageNum-1)*gCatalogItemPageSize+1;
////                      alert("begingRecord"+begingRecord);
//                    var endRecord = (pageNum-1)*gCatalogItemPageSize + gCatalogItemPageSize;
//                }else{
//                    var begingRecord = 0;
//                    var endRecord = 0;
//                }
////                    alert("endRecord"+endRecord);
//                endRecord = (endRecord > gCatalogItemTotalNumOfItems) ? gCatalogItemTotalNumOfItems:endRecord ;
////                      alert("endRecord"+endRecord);
////                      alert("gCatalogItemTotalNumOfItems"+gCatalogItemTotalNumOfItems);
//                $('#catalog_items_current_record_range').html('Total Records: ' + begingRecord + '- ' + endRecord + ' of ' + gCatalogItemTotalNumOfItems);
//
//                gCatalogItemsPageNum = pageNum;
//                initTableUI();
//            }
//        });
//    }catch(exp){
//        alert(exp);
//    }
//    return false;
//}



function populateCatalogCounts(){
    $.ajax({
        type:'POST', url: gCatalogJsonServiceUrl,
        data:'catalogId=' + catalogId,
        success: function(response) {
            if(response != ''){
                //alert(response);
                var catalog = eval( "(" + response + ")" );
                gCurrentCatalog = catalog;

                $("#pendingCount").text(catalog.pendingItems);
                $("#rejectedCount").text(catalog.rejectedItems);
                $("#approvedCount").text(catalog.approvedItems);
                $("#total-items-count").text(catalog.outputRecords);

            }
        }
    });

}
function removeCatalogItems() {
    var gIDs=prepareQueryString('cat-items-form','itemIds');
    gIDs+='&catalogId='+$("#catalogId").val();
    try{
        $.ajax({
            type:'POST',
            url: removeCatalogItemss+"?pageNum="+gCatalogItemsPageNumber,
            data: gIDs,
            dataType: 'text',
            success: function(response) {
                populateCatalogCounts();
                var callback = function (){
                    resetCheckBoxList('cat-items-form');
                }
                filterCatalogItems(catalogId,gCatalogItemsPageNumber,callback);
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function showCatalogUploadingErrors(catalogName,errorDescription,catalogId){
    $("#labelForCatalogError").html(errorDescription);

    /*    <c:if test="${not empty catalog.errorDescription}">
     <span>
     , 		<c:out value="Error with processing"/></span>

     </span>
    $("#reportDiv").html("<a href='view-error-report?catId="+catalogId+"' target='_blank'> Click  Here to view error report.</a> ");
    */
    $("#catalogNameOfErrorCatalog").html("An error occurred while processing catalog: \""+catalogName+"\""+" :");
    $.colorbox({
        href:'#catalogUploadingError',
        inline:true,
        onComplete: function(){
            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                $.colorbox.close();
                return false;
            });
        }
    });
}



function populateCatalogCustomField(catalogCustomField){
    try{

        $.colorbox({
            href:'#edit-custom-field',
            inline:true,
            onComplete: function(){
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    $.colorbox.close();
                    return false;
                });
            }
        });

        $('#edit-custom-field-id').val(catalogCustomField.customFieldId);
        $('#edit-id').val(catalogCustomField.id);
        $('[name="catalogId"]').val(catalogCustomField.catalogId);
        $('#edit-field-name').val(catalogCustomField.fieldName);

        if(catalogCustomField.displayName != "null"){
            $('#edit-display-name').val(catalogCustomField.displayName);
        }

        if(catalogCustomField.fieldDesc != "null"){
            $('#edit-description').val(catalogCustomField.fieldDesc);
        }

        $('#icon').attr('src', 'image/'+catalogCustomField.icon);

        if('list'==catalogCustomField.fieldType){
            $('[name="fieldType"]').val('Drop Down List');
        }else if('fixed'==catalogCustomField.fieldType){
            $('[name="fieldType"]').val('Fixed Value');
        } else if('flag'==catalogCustomField.fieldType){
            $('[name="fieldType"]').val('Flag');
        } else if('text'==catalogCustomField.fieldType){
            $('[name="fieldType"]').val('Input Field (Small)');
        }  else if('mediumtext'==catalogCustomField.fieldType){
            $('[name="fieldType"]').val('Input Field (Medium)');
        } else if('largetext'==catalogCustomField.fieldType){
            $('[name="fieldType"]').val('Input Field (Large)');
        }

        $('[name="searchBoost"]').val(catalogCustomField.searchBoost);
        if(document.getElementById("searchBoost").value == "null"){
            $('[name="searchBoostDrop"]').val("0");
        } else {
            $('[name="searchBoostDrop"]').val(document.getElementById("searchBoost").value);
        }

        if(catalogCustomField.defaultValue == "null"){
            $('[name="defaultValue"]').val("");
            $('#defArea3').val("");
            $('#defArea4').val("");
        } else {
            $('[name="defaultValue"]').val(catalogCustomField.defaultValue);
            $('#defArea3').val(catalogCustomField.defaultValue);
            $('#defArea4').val(catalogCustomField.defaultValue);
        }

        var fieldType = catalogCustomField.fieldType;

        $('#edit-area-1').hide();
        $('#edit-area-2').hide();
        $('#edit-area-3').hide();
        $('#edit-area-4').hide();

        var selected_area;

        if(fieldType.toLowerCase() == "Drop Down List".toLowerCase() || fieldType.toLowerCase() == "list".toLowerCase() || fieldType.toLowerCase() == "dropDownList".toLowerCase()) selected_area = "#edit-area-1";
        else if (fieldType.toLowerCase() == "Fixed Value".toLowerCase() || fieldType.toLowerCase() == "fixed".toLowerCase() || fieldType.toLowerCase() == "fixedValue".toLowerCase()) selected_area = "#edit-area-3";
        else if(fieldType.toLowerCase() == "Flag".toLowerCase() || fieldType.toLowerCase() == "flag".toLowerCase()) selected_area = "#edit-area-4";
        else selected_area = "#edit-area-2";

        $(selected_area + ' [name="mappingId"]').val(catalogCustomField.mappingId);

        var form = $('#editCatalogCustomFormId');
        inputFile = form.find('select');
        inputFile.each(function(){
            this.jcf.refreshState();
        });

        if(catalogCustomField.searchable) {
            $(selected_area + '-searchable-label').addClass('ui-state-active');
            $(selected_area + '-searchable').attr('checked', true);
        }

        if(catalogCustomField.required) {
            $(selected_area + '-required-label').addClass('ui-state-active');
            $(selected_area + '-required').attr('checked', true);
        }

        if(catalogCustomField.postFilterable) {
            $(selected_area + '-postFilterable-label').addClass('ui-state-active');
            $(selected_area + '-postFilterable').attr('checked', true);
        }

        if(catalogCustomField.defaultPostFilter) {
            $(selected_area + '-defaultPostFilter-label').addClass('ui-state-active');
            $(selected_area + '-defaultPostFilter').attr('checked', true);
        }


        $(selected_area).show();
    }catch(exp){
        alert(exp);
    }
}




//function getCatalog(catalogId) {
//    //alert(gCatalogJsonServiceUrl + ' ,' + catalogId);
//    try{
//        $.ajax({
//            type:'GET', url: getCatalogStatusUrl,
//            data:'catalogId=' + catalogId,
//            success: function(response) {
//                if(response != ''){
//                    $("#"+catalogId +"approvedStatus").html(response);
//                    $("#"+catalogId +"catStatus span").html(response);
//                }
//            }
//        });
//    }catch(exp){
//        alert(exp);
//    }
//    return false;
//}

function trunOffMessage(){
    $('#updateCatalogProfilesFeedback').html('');
    $('#updateSuppliersFeedback').html('');
}

function initTableUI() {
    initEditTable();
    initUiCheckboxes();
    checkAll();
}

var gAppReleaseNum = '';

function getAppReleaseNum(unitId) {
    try{
        $.ajax({
            type:'GET',
            async: false,
            url : releaseNumServiceUrl + '?company=' + unitId,
            dataType : 'text',
            success: function(data, textStatus, jqXHR) {
                gAppReleaseNum = data;
            },
            error: function (){
            }
        });
    }catch(exp){}
}

function formatCurrency(price, currency) {
	if (price && price.length > 0 && currency == 'EUR') {
//		itemPrice = parseFloat(price).toLocaleString('de-DE', { style: 'currency', currency: 'EUR' });
		price = price.replace(/\./g, ",");
	}
	return price;
}
function parseCurrency(price, currency) {
	price = price.replace(/,/g, ".");
	return price;
}

function setFlagIconFile() {
    try{
        var fileName = document.getElementById('iconFlagFile').value;
        if(fileName != ''){
            var ext = $('#iconFlagFile').val().split('.').pop().toLowerCase();
            if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                alert('Invalid file: '+$('#iconFlagFile').val() +'. Allowed files types are [ gif, png, jpg, jpeg ]');
                resetLogoImage();
                return;
            }

            var indexPos=0;
            //ie8 security restriction hack
            if( fileName.indexOf("C:\\fakepath\\") == 0 || fileName.indexOf("C://fakepath//") == 0  ){
                indexPos = "C:\\fakepath\\".length-1;
            }

            $('#iconFlagFileEM').html(fileName.substr(indexPos, fileName.length) + '<a style="margin-top:4px;margin-left: 20px;cursor: pointer;" onclick="resetFlagIconFile();" class="alt-close"></a>');
        } else {
            alert('Error: Can not get filename!');
            $('#iconFlagFileEM').html('');
        }
        $('#iconFlagFileClose').click();
    }catch(exp){
        alert(exp);
    }
}
function resetFlagIconFile(){
    $('#iconFlagFile').val('');
    $('#iconFlagFileEM').html('No File Chosen.');
    jcf.customForms.replaceAll();
}

/* Checks whether user has selected a valid supplier or not */
function checkSupplierSelection() {
	// if hidden field contains a valid value
	if ($('#supplierName').val().length > 0) {		
		$('#SupplierCompany #create-catalog-supplier-label').remove();
	} else if ($.trim($('#supplierNameDisplay').val()).length > 0) {		
		// user didn't select a suggested option but typed some text on his own */
		$('#SupplierCompany #create-quote-supplier-label').remove();
		$('#SupplierCompany #create-catalog-supplier-label').remove();
		$('#SupplierCompany').append('<label id="create-catalog-supplier-label" for="supplierName" generated="true" class="quote-error" >Select a valid Supplier</label>');			
		$('#supplierNameDisplay').typeahead('val', '');
	}
}

/* clears existing supplier whenever user clicks on Supplier name field */
function clearExistingSupplier() {
	$('#supplierNameDisplay').typeahead('val', '');
	$('#supplierName').val("");
	$('#supplierName').trigger("change");
}
function clearExistingShopper() {
	$('#shopperNameDisplay').typeahead('val', '');
	$('#shopperId').val("");
	$('#shopperId').trigger("change");
}
function selectProfile(obj){
    if(obj.checked){
        $(obj).next().addClass('ui-state-active');
        $(obj).closest('tr').addClass('active-tr');
    }else {
        $(obj).next().removeClass('ui-state-active');
        $(obj).closest('tr').removeClass('active-tr');
    }
}
function replaceCatFile() {
    $("#theCatalogFileId").html("<label>Catalog File:</label><input  type='file' name='catalogFile' onchange='showVersionMessageCat();' width='64' id='catalog-file'/>");
    jcf.customForms.replaceAll();
    jcf.customForms.refreshAll();
}

function replaceZipFile() {
    $("#theImageFileId").html("<label>Image File (.zip):</label><input type='file' onchange='showVersionMessageCat();' name='imageFile' width='64' id='image-file' />");
    jcf.customForms.replaceAll();
    jcf.customForms.refreshAll();
}