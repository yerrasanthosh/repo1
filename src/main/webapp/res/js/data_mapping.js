/**
 * Created with IntelliJ IDEA. User: qureshit Date: 10/14/12 Time: 5:56 PM To
 * change this template use File | Settings | File Templates.
 */




var paginationInTransit = false;
var pgPagesAvailable, psPagesAvailable, pgProcessFailed = false;
var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var addPowerShopper, editPowerShopper;

function validateEmail(element) {
    if (!re.test($(element).val())) {
        noty({
            text: "Enter a valid email",
            type: 'error'
        });
        return false;
    }
}
function getMaterialGroupMappingsPage(pageNum, searchTerm, freshSearch) {
    if (null == freshSearch
        && ((pageNum < 1 || pageNum > gPagesAvailable) || paginationInTransit)) {
        return;
    }
    if (freshSearch != null && paginationInTransit) {
        return;
    }

    paginationInTransit = true;
    try {
        $.ajax({
            type: 'GET',
            async: false,
            url: gMaterialGroupMappingsPageURI,
            data: {
                pageNumber: pageNum,
                searchTerm: searchTerm
            },
            success: function (data, textStatus, jqXHR) {
                if (jqXHR.responseText != '') {

                    var responseArray = jqXHR.responseText.split('@@@');
                    gPagesAvailable = responseArray[1];
                    gPageNumber = responseArray[2];
                    gPageSize = responseArray[3];
                    gTotalRecords = responseArray[4];

                    $('#material_group_mappings_section').empty();
                    $('#material_group_mappings_section').append(
                        responseArray[0]);

                    if ((gTotalRecords - gPageSize - 1) < 0) {
                        $('#customCatergoryPager').hide();
                    } else {
                        if ($("#customCatergoryPager").is(':hidden')) {
                            $("#customCatergoryPager").show();
                        }
                        // $('span#material_group_mapping_current_page_num').html('Page
                        // '+ pageNum + ' of ' + gPagesAvailable);
                    }
                    $('#materialGroupBottomData').show();

                    var begingRecord = (gTotalRecords > 0) ? (pageNum - 1)
                    * gPageSize + 1 : 0;
                    var endRecord = (pageNum) * gPageSize;

                    endRecord = (endRecord > gTotalRecords) ? gTotalRecords
                        : endRecord;

                    // $('#material_group_mapping_current_record_range').html('Showing
                    // Records: ' + begingRecord + '- ' + endRecord + ' of ' +
                    // gTotalRecords);

                    gPageNumber = pageNum;
                    paginationInTransit = false;
                }
            }
        });
    } catch (exp) {
        alert(exp)
    }
}

var paginationInTransitSupplier = false;
function getSupplierMappingsPage(pageNum, searchTerm, freshSearch) {
    if (null == freshSearch
        && ((pageNum < 1 || pageNum > gPagesAvailableSupplier) || paginationInTransitSupplier)) {
        return;
    }
    if (freshSearch != null && paginationInTransitSupplier) {
        return;
    }

    paginationInTransitSupplier = true;
    try {
        $
            .ajax({
                type: 'GET',
                async: false,
                url: gSupplierMappingsPageURI,
                data: {
                    pageNumber: pageNum,
                    searchTerm: searchTerm
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {

                        var responseArray = jqXHR.responseText.split('@@@');
                        gPagesAvailableSupplier = responseArray[1];
                        gPageNumberSupplier = responseArray[2];
                        gPageSize = responseArray[3];
                        gTotalRecordsSupplier = responseArray[4];
                        // alert(responseArray[5]);
                        // alert(responseArray[6]);
                        $('#custom_supplier_mappings_section').empty();
                        $('#custom_supplier_mappings_section').append(
                            responseArray[0]);

                        if ((gTotalRecordsSupplier - gPageSize - 1) < 0) {
                            $('#customSupplierPager').hide();
                        } else {
                            if ($("#customSupplierPager").is(':hidden')) {
                                $("#customSupplierPager").show();
                                $("#supplierMappingBottomData").show();
                            }

                            // $('span#custom_supplier_mapping_current_page_num').html('Page
                            // '+ pageNum + ' of ' +
                            // gPagesAvailableSupplier);
                        }

                        // if(pageNum > 1){
                        // $("#material_group_mapping_prev_page").removeClass("btn-prev").addClass("btn-prev-second");
                        // }else if(pageNum == 1){
                        // $("#material_group_mapping_prev_page").removeClass("btn-prev-second").addClass("btn-prev");
                        // }
                        var begingRecord = (gTotalRecordsSupplier > 0) ? (pageNum - 1)
                        * gPageSize + 1
                            : 0;
                        var endRecord = (pageNum) * gPageSize;

                        endRecord = (endRecord > gTotalRecordsSupplier) ? gTotalRecordsSupplier
                            : endRecord;

                        // $('#custom_supplier_mapping_current_record_range').html('Showing
                        // Records: ' + begingRecord + '- ' + endRecord + '
                        // of ' + gTotalRecordsSupplier);

                        gPageNumberSupplier = pageNum;
                        paginationInTransitSupplier = false;
                    }
                }
            });
    } catch (exp) {
        alert(exp)
    }
}

function uploadFile(tab) {
    var ext = '';
    if (tab == 'materialGroup') {
        if ($('#materialGroupFile').val() == '') {
            $('#materialGroupFile').val('');
            alert('Select a valid file!');
            return false;
        }
        ext = $('#materialGroupFile').val().split('.').pop().toLowerCase();

        if ($.inArray(ext, ['xlsx', 'xls']) == -1) {
            $('#materialGroupFile').val('');
            alert('Invalid file!');
            return false;
        }

        $('#mappingFileForm').ajaxSubmit({
            success: function (responseText, statusText) {
                $('#materialGroupMappingData').hide();
                $('#info-section2').hide();
                // alert("here");
                worker();
            }
        });
    } else if (tab == 'supplierMapping') {
        if ($('#supplierFile').val() == '') {
            $('#supplierFile').val('');
            alert('Invalid file!');
            return false;
        }
        ext = $('#supplierFile').val().split('.').pop().toLowerCase();

        if ($.inArray(ext, ['xlsx', 'xls']) == -1) {
            $('#supplierFile').val('');
            alert('Invalid file!');
            return false;
        }

        $('#supplierMappingFileForm').ajaxSubmit({
            success: function (responseText, statusText) {
                $('#supplierMappingData').hide();
                $('#info-section4').hide();
                workerSupplierTab();
            }
        });
    } else if (tab == 'profileGroup') {
        if ($('#profileGroupFile').val() == '') {
            $('#profileGroupFile').val('');
            alert('Select a valid file!');
            return false;
        }
        ext = $('#profileGroupFile').val().split('.').pop().toLowerCase();

        if ($.inArray(ext, ['xlsx', 'xls']) == -1) {
            $('#profileGroupFile').val('');
            alert('Invalid file!');
            return false;
        }

        $('#profileGroupForm').ajaxSubmit({
            success: function (responseText, statusText) {
                $('#profileGroupMappingData').hide();
                $('#pg-info-section2').hide();
                $("#pgDownloadProcessedReportLink").hide();
                $("#pgLblDownloadProcessedReportLink").hide();
                workerProfileGroupTab();

            }
        });

    } else if (tab == 'uomMapping') {
        if ($('#uomMappingFile').val() == '') {
            $('#uomMappingFile').val('');
            alert('Select a valid file!');
            return false;
        }
        ext = $('#uomMappingFile').val().split('.').pop().toLowerCase();

        if ($.inArray(ext, ['xlsx', 'xls']) == -1) {
            $('#uomMappingFile').val('');
            alert('Invalid file!');
            return false;
        }

        $('#uomMappingForm').ajaxSubmit({
            success: function (responseText, statusText) {
                $('#uomMappingData').hide();
                $('#uom-info-section2').show();

                $("#uomDownloadProcessedReportLink").hide();
                $("#uomLblDownloadProcessedReportLink").hide();
                workerUOMMappingTab();
                jcf.customForms.replaceAll();

            }
        });

    } else if (tab == 'currencyMapping') {
        if ($('#currencyMappingFile').val() == '') {
            $('#currencyMappingFile').val('');
            alert('Select a valid file!');
            return false;
        }
        ext = $('#currencyMappingFile').val().split('.').pop().toLowerCase();

        if ($.inArray(ext, ['xlsx', 'xls']) == -1) {
            $('#currencyMappingFile').val('');
            alert('Invalid file!');
            return false;
        }

        $('#currencyMappingForm').ajaxSubmit({
            success: function (responseText, statusText) {
                $('#currencyMappingData').hide();
                $('#currency-info-section2').show();

                $("#currencyDownloadProcessedReportLink").hide();
                $("#currencyLblDownloadProcessedReportLink").hide();
                workerCurrencyMappingTab();
                jcf.customForms.replaceAll();

            }
        });

    }else if (tab == 'powerShopperMapping') {
        if ($('#powerShopperMappingFile').val() == '') {
            $('#powerShopperMappingFile').val('');
            alert('Select a valid file!');
            return false;
        }
        ext = $('#powerShopperMappingFile').val().split('.').pop().toLowerCase();

        if ($.inArray(ext, [ 'xlsx', 'xls' ]) == -1) {
            $('#powerShopperMappingFile').val('');
            alert('Invalid file!');
            return false;
        }

        $('#powerShopperUploadForm').ajaxSubmit({
            success : function(responseText, statusText) {
                $('#powsMappingData').hide();
                $('#pows-info-section2').show();

                $("#powerShopperMappingDownloadLink").hide();
                $("#powsMappingErrorReportLink").hide();
                workerPowerShopperTab();
                jcf.customForms.replaceAll();

            }
        });
    }
}

function workerProfileGroupTab() {

    try {
        $
            .ajax({
                url: 'profilegroupmappingstatus/page',
                cache: false,
                data: {
                    'pageNumber': '1',
                    'fileUploading': 'true'
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {
                        var responseArray = jqXHR.responseText.split('@@@');
                        pgPagesAvailable = responseArray[1];
                        pgPageNumber = responseArray[2];
                        pgPageSize = responseArray[3];
                        pgTotalRecords = responseArray[4];
                        pgProcessFailed = responseArray[7];
                        pgProcessedCount = responseArray[10];
                        pgUpdating = responseArray[11];
                        pgMessage = responseArray[12];
                        if (responseArray[6] == "true") {
                            $("#pgLblDownloadErrorReportLink").show();
                            $("#pgDownloadErrorReportLink").show();
                            $("#pgDownloadErrorReportLink").attr(
                                'href',
                                'viewmappingerrors?pgFileHandle='
                                + responseArray[5]);
                        } else {
                            $("#pgLblDownloadErrorReportLink").hide();
                            $("#pgDownloadErrorReportLink").hide();
                        }

                        $("#pgDownloadProcessedReportLink").show();
                        $("#pgLblDownloadProcessedReportLink").show();

                        // if($('#profileGroupFile').val() != ''){
                        // $('#profileGroupFile').after($('#profileGroupFile').clone(true)).remove();
                        // $('#profileGroupFile').remove(":contains('.')");
                        // }

                        if (pgUpdating == "false") {
                            $('#profile_group_mappings_section').empty();
                            $('#profile_group_mappings_section').append(
                                responseArray[0]);

                            if (typeof pgPagesAvailable !== 'undefined'
                                && pgPagesAvailable > 0) {
                                if ((pgTotalRecords - pgPageSize - 1) < 0) {
                                    $('#profileGroupPager').hide();
                                } else {
                                    if ($("#profileGroupPager").is(
                                            ':hidden')) {
                                        $("#profileGroupPager").show();
                                    }
                                }
                                var begingRecord = (pgTotalRecords > 0) ? (1 - 1)
                                * pgPageSize + 1
                                    : 0;
                                var endRecord = (1) * pgPageSize;

                                endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                                    : endRecord;
                            }

                            $('#profileGroupMappingData').show();
                            $('#profileGroupBottomData').show();
                            $('#profile_group_mappings_section').show();
                            $('#pg-info-section2').hide();
                            $("#profileGroupFile").remove();
                            $("#profileGroupFileDiv")
                                .html(
                                "<input type='file' style='cursor: pointer;' name='profileGroupFile' id='profileGroupFile'/>");
                            jcf.customForms.replaceAll();
                            jcf.customForms.refreshAll();
                            pgPageNumber = 1;
                            $("#profileGroupPager").show();
                            updateGroupTable();
                        } else {
                            if(pgMessage){
                                $('#pg_message').text(pgMessage);
                                $('#pg-info-section2').show();
                            }else{$('#pg-info-section2').hide();}
                        }
                        if (pgProcessFailed && pgProcessFailed == 'true') {
                            $('#pg-info-section2-loading').hide();
                        }
                        paginationInTransitForProfileGroup = false;
                    }
                },
                complete: function () {
                    // Schedule the next request when the current one's
                    // complete
                    // alert("completed call");
                    if (!pgPagesAvailable
                        && (!pgProcessFailed || pgProcessFailed == 'false')) {
                        setTimeout(workerProfileGroupTab, 5000);
                    }
                }
            });
    } catch (exp) {
        alert(exp)
    }
}

// Wrap this function in a closure so we don't pollute the namespace
function worker() {
    // alert('worker called');
    try {
        $
            .ajax({
                url: gMaterialGroupMappingsPageURI,
                cache: false,
                data: {
                    'pageNumber': '1',
                    'fileUploading': 'true'
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {
                        // alert('return from call');

                        var responseArray = jqXHR.responseText.split('@@@');
                        gPagesAvailable = responseArray[1];
                        gPageNumber = responseArray[2];
                        gPageSize = responseArray[3];
                        gTotalRecords = responseArray[4];
                        gProcessFailed = responseArray[7];
                        cmTotalRecords = responseArray[8];
                        cmProcessedCount = responseArray[9];

                        if (responseArray[6] == "true") {
                            $("#lblDownloadErrorReportLink").show();
                            $("#downloadErrorReportLink").show();
                            $("#downloadErrorReportLink").attr(
                                'href',
                                'view-material-group-error-report?fileHandle='
                                + responseArray[5]);
                        } else {
                            $("#lblDownloadErrorReportLink").hide();
                            $("#downloadErrorReportLink").hide();
                        }

                        $("#cmDownloadProcessedReportLink").show();
                        $("#cmLblDownloadProcessedReportLink").show();

                        if (typeof gPagesAvailable !== 'undefined'
                            && gPagesAvailable > 0) {
                            $('#material_group_mappings_section').empty();
                            $('#material_group_mappings_section').append(
                                responseArray[0]);

                            if ((gTotalRecords - gPageSize - 1) < 0) {
                                $('#customCatergoryPager').hide();
                            } else {
                                if ($("#customCatergoryPager")
                                        .is(':hidden')) {
                                    $("#customCatergoryPager").show();
                                }
                                // $('span#material_group_mapping_current_page_num').html('Page
                                // '+ 1 + ' of ' + gPagesAvailable);
                            }
                            $('#materialGroupBottomData').show();

                            // if(pageNum > 1){
                            // $("#material_group_mapping_prev_page").removeClass("btn-prev").addClass("btn-prev-second");
                            // }else if(pageNum == 1){
                            // $("#material_group_mapping_prev_page").removeClass("btn-prev-second").addClass("btn-prev");
                            // }
                            var begingRecord = (gTotalRecords > 0) ? (1 - 1)
                            * gPageSize + 1
                                : 0;
                            var endRecord = (1) * gPageSize;

                            endRecord = (endRecord > gTotalRecords) ? gTotalRecords
                                : endRecord;

                            // $('#material_group_mapping_current_record_range').html('Showing
                            // Records: ' + begingRecord + '- ' + endRecord
                            // + ' of ' + gTotalRecords);
                            $('#materialGroupMappingData').show();
                            $('#material_group_mappings_section').show();
                            $('#info-section2').hide();
                            $("#materialGroupFile").remove();
                            $("#materialGroupFileDiv")
                                .html(
                                "<input type='file' style='cursor: pointer;' name='materialGroupFile' id='materialGroupFile'/>");
                            jcf.customForms.replaceAll();
                            jcf.customForms.refreshAll();
                            gPageNumber = 1;
                        } else {

                            // alert('inside else');
                            $('#info-section2').show();
                            $('#info-section2').empty();
                            $('#info-section2').html(responseArray[0]);
                            if (gProcessFailed) {
                                $('#info-section2-loading').hide();
                            }
                        }
                        paginationInTransit = false;

                    }
                },
                complete: function () {

                    if (!gPagesAvailable
                        && (!gProcessFailed || gProcessFailed == false)) {
                        setTimeout(worker, 5000);
                    }
                }
            });
    } catch (exp) {
        alert(exp)
    }
}

function workerSupplierTab() {
    try {
        $
            .ajax({
                url: gSupplierMappingsPageURI,
                cache: false,
                data: {
                    pageNumber: 1,
                    fileUploading: 'true'
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {
                        var responseArray = jqXHR.responseText.split('@@@');
                        gPagesAvailableSupplier = responseArray[1];
                        gPageNumberSupplier = responseArray[2];
                        gPageSize = responseArray[3];
                        gTotalRecordsSupplier = responseArray[4];
                        gSupplierProcessFailed = responseArray[7];
                        vmTotalRecords = responseArray[8];
                        vmProcessedCount = responseArray[9];

                        if (responseArray[5] == "true") {
                            $("#lblDownloadSupplierErrorReportLink").show();
                            $("#downloadSupplierErrorReportLink").show();
                            $("#downloadSupplierErrorReportLink").attr(
                                'href',
                                'view-supplier-mapping-error-report?fileHandle='
                                + responseArray[6]);
                        } else {
                            $("#lblDownloadSupplierErrorReportLink").hide();
                            $("#downloadSupplierErrorReportLink").hide();
                        }

                        $("#vmDownloadProcessedReportLink").show();
                        $("#vmLblDownloadProcessedReportLink").show();

                        if (typeof gPagesAvailableSupplier !== 'undefined'
                            && gPagesAvailableSupplier > 0) {
                            $('#custom_supplier_mappings_section').empty();
                            $('#custom_supplier_mappings_section').append(
                                responseArray[0]);

                            if ((gTotalRecordsSupplier - gPageSize - 1) < 0) {
                                $('#customSupplierPager').hide();
                            } else {
                                if ($("#customSupplierPager").is(':hidden')) {
                                    $("#customSupplierPager").show();
                                }
                            }

                            $('#supplierMappingBottomData').show();

                            // if(pageNum > 1){
                            // $("#material_group_mapping_prev_page").removeClass("btn-prev").addClass("btn-prev-second");
                            // }else if(pageNum == 1){
                            // $("#material_group_mapping_prev_page").removeClass("btn-prev-second").addClass("btn-prev");
                            // }
                            var begingRecord = (gTotalRecordsSupplier > 0) ? (1 - 1)
                            * gPageSize + 1
                                : 0;
                            var endRecord = (1) * gPageSize;

                            endRecord = (endRecord > gTotalRecordsSupplier) ? gTotalRecordsSupplier
                                : endRecord;

                            // $('#custom_supplier_mapping_current_record_range').html('Showing
                            // Records: ' + begingRecord + '- ' + endRecord
                            // + ' of ' + gTotalRecordsSupplier);
                            $('#custom_supplier_mappings_section').show();
                            $('#info-section4').hide();
                            $('#supplierMappingData').show();
                            $("#supplierFile").remove();
                            $("#supplierFileDiv")
                                .html(
                                "<input size='75' type='file' style='cursor: pointer;' name='supplierFile' id='supplierFile'/>");
                            jcf.customForms.replaceAll();
                            jcf.customForms.refreshAll();

                            gPageNumberSupplier = 1;
                        } else {

                            // alert('inside else');
                            $('#info-section4').show();
                            $('#info-section4').empty();
                            $('#info-section4').html(responseArray[0]);
                            if (gSupplierProcessFailed) {
                                $('#info-section4-loading').hide();
                            }
                        }
                        paginationInTransit = false;

                    }
                },
                complete: function () {
                    // Schedule the next request when the current one's
                    // complete
                    // alert("completed call");
                    if (!gPagesAvailableSupplier
                        && (!gSupplierProcessFailed || gSupplierProcessFailed == false)) {
                        setTimeout(workerSupplierTab, 5000);
                    }
                }
            });
    } catch (exp) {
        alert(exp)
    }
}

function workerPowerShopperTab() {
    try {
        addPowerShopper = false;
        editPowerShopper = false;
        $
            .ajax({
                url: 'content-sharing-mappings/page',
                cache: false,
                data: {
                    pageNumber: 1,
                    fileUploading: 'true'
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {
                        var responseArray = jqXHR.responseText.split('@@@');
                        pgPagesAvailable = responseArray[1];
                        pgPageNumber = responseArray[2];
                        pgPageSize = responseArray[3];
                        pgTotalRecords = responseArray[4];
                        pgProcessFailed = responseArray[7];
                        pgProcessedCount = responseArray[10];
                        pgUpdating = responseArray[11];
                        pgMessage = responseArray[12];

                        if (responseArray[6] == "true") {
                            $("#lblPowsMappingErrorReportLink").show();
                            		$("#powsMappingErrorReportLink").show();
                            		$("#powsMappingErrorReportLink").attr(
                                										'href',
                                										'contentsharemappingerrors?pgFileHandle='
                                												+ responseArray[5]);
                        }else{
                            $("#lblPowsMappingErrorReportLink").hide();
                            $("#powsMappingErrorReportLink").hide();
                        }

                        $("#powerShopperMappingDownloadLink").show();
                        $("#lblPowerShopperMappingDownloadLink").show();

                        if (pgUpdating == "false") {
                            $('.power_shopper_section').empty();
                            $('.power_shopper_section').append(
                                responseArray[0]);

                            if (typeof pgPagesAvailable !== 'undefined'
                                && pgPagesAvailable > 0) {
                                if ((pgTotalRecords - pgPageSize - 1) < 0) {
                                    $('#powerShopperPager').hide();
                                } else {
                                    if ($("#powerShopperPager").is(
                                            ':hidden')) {
                                        $("#powerShopperPager").show();
                                    }
                                }
                                var begingRecord = (pgTotalRecords > 0) ? (1 - 1)
                                * pgPageSize + 1
                                    : 0;
                                var endRecord = (1) * pgPageSize;

                                endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                                    : endRecord;
                            }

                            $('#power_shopper_section').show();
                            $('#pows_message').html('');
                            $('#pows_message').hide();
                            $('#pows-info-section2').hide();
                            $('#powsBottomData').show();
                            $('#powsMappingData').show();



                            $("#powerShopperMapping")
                                .html("<input type='file' style='cursor: pointer;' name='powerShopperMappingFile' id='powerShopperMappingFile'/>");

                            jcf.customForms.replaceAll();
                            jcf.customForms.refreshAll();

                            pgPageNumber = 1;
                        } else {

                            // alert('inside else');
                            $('#pows-info-section2').show();
                            $("#pows-info-section2-loading").show();

                            $('#pows_message').show();
                            $('#pows_message').html(pgMessage);
                            // $('#pows-info-section2').empty();
                            if (typeof pgPagesAvailable !== 'undefined'
                                && pgPagesAvailable > 0) {
                                $('#pows-info-section2').html(
                                    responseArray[0]);
                                if (pgProcessFailed) {
                                    $('#pows-info-section2-loading').hide();

                                }

                            }

                        }
                        paginationInTransit = false;

                    }
                },
                complete : function() {
                    // Schedule the next request when the current one's
                    // complete
                    // alert("completed call");
                    if ((pgPagesAvailable == 'false' || pgPagesAvailable == '')
                        && pgProcessFailed == 'false') {
                        setTimeout(workerPowerShopperTab, 5000);
                    }

                }
            });
    } catch (exp) {
        alert(exp)
    }
}


function workerUOMMappingTab() {

    try {
        $
            .ajax({
                url: 'uommappingstatus/page',
                cache: false,
                data: {
                    pageNumber: 1,
                    fileUploading: 'true'
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {
                        var responseArray = jqXHR.responseText.split('@@@');
                        pgPagesAvailable = responseArray[1];
                        pgPageNumber = responseArray[2];
                        pgPageSize = responseArray[3];
                        pgTotalRecords = responseArray[4];
                        pgProcessFailed = responseArray[7];
                        pgProcessedCount = responseArray[10];
                        pgUpdating = responseArray[11];
                        pgMessage = responseArray[12];

                        if (responseArray[6] == "true") {
                            $("#uomLblDownloadErrorReportLink").show();
                            $("#uomDownloadErrorReportLink").show();
                            $("#uomDownloadErrorReportLink").attr(
                                'href',
                                'viewuommappingerrors?pgFileHandle='
                                + responseArray[5]);
                        } else {
                            $("#uomLblDownloadErrorReportLink").hide();
                            $("#uomDownloadErrorReportLink").hide();
                        }

                        $("#uomDownloadProcessedReportLink").show();
                        $("#uomLblDownloadProcessedReportLink").show();

                        // if($('#profileGroupFile').val() != ''){
                        // $('#profileGroupFile').after($('#profileGroupFile').clone(true)).remove();
                        // $('#profileGroupFile').remove(":contains('.')");
                        // }

                        if (pgUpdating == "false") {
                            $('#uom_mappings_section').empty();
                            $('#uom_mappings_section').append(
                                responseArray[0]);

                            if (typeof pgPagesAvailable !== 'undefined'
                                && pgPagesAvailable > 0) {
                                if ((pgTotalRecords - pgPageSize - 1) < 0) {
                                    $('#uomMappingPager').hide();
                                } else {
                                    if ($("#uomMappingPager").is(':hidden')) {
                                        $("#uomMappingPager").show();
                                    }
                                }
                                var begingRecord = (pgTotalRecords > 0) ? (1 - 1)
                                * pgPageSize + 1
                                    : 0;
                                var endRecord = (1) * pgPageSize;

                                endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                                    : endRecord;
                            }

                            // $('#custom_supplier_mapping_current_record_range').html('Showing
                            // Records: ' + begingRecord + '- ' + endRecord
                            // + ' of ' + gTotalRecordsSupplier);
                            $('#uom_mappings_section').show();
                            $('#uom_message').html('');
                            $('#uom_message').hide();
                            $('#uom-info-section2').hide();
                            $('#uomBottomData').show();
                            $('#uomMappingData').show();
                            // $("#uomMapping").remove();
                            $("#uomMapping")
                                .html(
                                "<input size='75' type='file' style='cursor: pointer;' name='uomMappingFile' id='uomMappingFile'/>");

                            jcf.customForms.replaceAll();
                            jcf.customForms.refreshAll();

                            pgPageNumber = 1;
                        } else {

                            if(pgMessage) {
                                $('#uom-info-section2').show();
                                $("#uom-info-section2-loading").show();

                                $('#uom_message').show();
                                $('#uom_message').html(pgMessage);
                            }
                            // $('#uom-info-section2').empty();
                            if (typeof pgPagesAvailable !== 'undefined'
                                && pgPagesAvailable > 0) {
                                $('#uom-info-section2').html(
                                    responseArray[0]);
                                if (pgProcessFailed) {
                                    $('#uom-info-section2-loading').hide();
                                    // $('#uom_message').hide();
                                }

                            }

                        }
                        paginationInTransit = false;

                    }
                },
                complete: function () {
                    // Schedule the next request when the current one's
                    // complete
                    // alert("completed call");
                    if ((pgPagesAvailable == 'false' || pgPagesAvailable == '')
                        && pgProcessFailed == 'false') {
                        setTimeout(workerUOMMappingTab, 5000);
                    }

                }
            });
    } catch (exp) {
        alert(exp)
    }
}
function workerCurrencyMappingTab() {

    try {
        $
            .ajax({
                url: 'currencymappingstatus/page',
                cache: false,
                data: {
                    pageNumber: 1,
                    fileUploading: 'true'
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {
                        var responseArray = jqXHR.responseText.split('@@@');
                        pgPagesAvailable = responseArray[1];
                        pgPageNumber = responseArray[2];
                        pgPageSize = responseArray[3];
                        pgTotalRecords = responseArray[4];
                        pgProcessFailed = responseArray[7];
                        pgProcessedCount = responseArray[10];
                        pgUpdating = responseArray[11];
                        pgMessage = responseArray[12];

                        if (responseArray[6] == "true") {
                            $("#currencyLblDownloadErrorReportLink").show();
                            $("#currencyDownloadErrorReportLink").show();
                            $("#currencyDownloadErrorReportLink").attr(
                                'href',
                                'viewcurrencymappingerrors?pgFileHandle='
                                + responseArray[5]);
                        } else {
                            $("#currencyLblDownloadErrorReportLink").hide();
                            $("#currencyDownloadErrorReportLink").hide();
                        }

                        $("#currencyDownloadProcessedReportLink").show();
                        $("#currencyLblDownloadProcessedReportLink").show();

                        // if($('#profileGroupFile').val() != ''){
                        // $('#profileGroupFile').after($('#profileGroupFile').clone(true)).remove();
                        // $('#profileGroupFile').remove(":contains('.')");
                        // }

                        if (pgUpdating == "false") {
                            $('#currency_mappings_section').empty();
                            $('#currency_mappings_section').append(
                                responseArray[0]);

                            if (typeof pgPagesAvailable !== 'undefined'
                                && pgPagesAvailable > 0) {
                                if ((pgTotalRecords - pgPageSize - 1) < 0) {
                                    $('#currencyMappingPager').hide();
                                } else {
                                    if ($("#currencyMappingPager").is(
                                            ':hidden')) {
                                        $("#currencyMappingPager").show();
                                    }
                                }
                                var begingRecord = (pgTotalRecords > 0) ? (1 - 1)
                                * pgPageSize + 1
                                    : 0;
                                var endRecord = (1) * pgPageSize;

                                endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                                    : endRecord;
                            }

                            // $('#custom_supplier_mapping_current_record_range').html('Showing
                            // Records: ' + begingRecord + '- ' + endRecord
                            // + ' of ' + gTotalRecordsSupplier);
                            $('#currency_mappings_section').show();
                            $('#currency_message').html('');
                            $('#currency_message').hide();
                            $('#currency-info-section2').hide();
                            $('#currencyBottomData').show();
                            $('#currencyMappingData').show();
                            // $("#uomMapping").remove();
                            $("#currencyMapping")
                                .html(
                                "<input size='75' type='file' style='cursor: pointer;' name='currencyMappingFile' id='currencyMappingFile'/>");

                            jcf.customForms.replaceAll();
                            jcf.customForms.refreshAll();

                            pgPageNumber = 1;
                        } else {

                            // alert('inside else');
                            $('#currency-info-section2').show();
                            $("#currency-info-section2-loading").show();

                            $('#currency_message').show();
                            $('#currency_message').html(pgMessage);
                            // $('#uom-info-section2').empty();
                            if (typeof pgPagesAvailable !== 'undefined'
                                && pgPagesAvailable > 0) {
                                $('#currency-info-section2').html(
                                    responseArray[0]);
                                if (pgProcessFailed) {
                                    $('#currency-info-section2-loading')
                                        .hide();
                                    // $('#uom_message').hide();
                                }

                            }

                        }
                        paginationInTransit = false;

                    }
                },
                complete: function () {
                    // Schedule the next request when the current one's
                    // complete
                    // alert("completed call");
                    if ((pgPagesAvailable == 'false' || pgPagesAvailable == '')
                        && pgProcessFailed == 'false') {
                        setTimeout(workerCurrencyMappingTab, 5000);
                    }

                }
            });
    } catch (exp) {
        alert(exp)
    }
}
var addContentMapping, editContentMapping;

function setupProfileGroupMappingTab() {
    $(document).keyup(function (e) {
        if (addContentMapping) {
            if (e.keyCode == 13) {
                createContentAccessMapping();
            } // enter
            if (e.keyCode == 27) {
                cancelAdd();
            } // esc
        } else if (editContentMapping) {
            if (e.keyCode == 13) {
                updateContentAccess(true);
            } // enter
            if (e.keyCode == 27) {
                cancelEdit();
            } // esc
        }
    });

    if (typeof pgPagesAvailable == 'undefined'
        || (typeof pgPagesAvailable != 'undefined' && pgPagesAvailable < 1)) {
        $('#profileGroupMappingData').hide();
        $('#pg-info-section2').hide();
        $('#profileGroupBottomData').hide();
        workerProfileGroupTab();
    } else {
        $('#profileGroupMappingData').show();
        $('#profileGroupBottomData').show();
    }
    $("#profile_group_table_rows_body .groupNameSelectDiv").hide();
    $("#profile_group_table_rows_body .groupNameLinkDiv").show();
}

function setupUOMMappingTab() {
    $(document).keyup(function (e) {
        if (addContentMapping) {
            if (e.keyCode == 13) {
                createContentAccessMapping();
            } // enter
            if (e.keyCode == 27) {
                cancelAdd();
            } // esc
        } else if (editContentMapping) {
            if (e.keyCode == 13) {
                updateContentAccess(true);
            } // enter
            if (e.keyCode == 27) {
                cancelEdit();
            } // esc
        }
    });

    if (typeof pgPagesAvailable == 'undefined'
        || (typeof pgPagesAvailable != 'undefined' && pgPagesAvailable < 1)) {
        $('#uomMappingData').hide();
        $('#uom-info-section2').hide();
        $('#uomBottomData').hide();
        workerUOMMappingTab();
    } else {
        $('#uomMappingData').show();
        $('#uomBottomData').show();
    }
    $("#uom_table_rows_body .groupNameSelectDiv").hide();
    $("#uom_table_rows_body .groupNameLinkDiv").show();
}

function setupCategoryMappingTab() {
    if (typeof gPagesAvailable == 'undefined'
        || (typeof gPagesAvailable != 'undefined' && gPagesAvailable < 1)) {
        $('#materialGroupMappingData').hide();
        $('#info-section2').hide();
        // alert("here2");
        $('#materialGroupBottomData').hide();
        worker();
    } else {
        $('#materialGroupMappingData').show();
        $('#materialGroupBottomData').show();
    }
}

function setupSupplierMappingTab() {
    if (typeof gPagesAvailableSupplier == 'undefined'
        || (typeof gPagesAvailableSupplier != 'undefined' && gPagesAvailableSupplier < 1)) {
        $('#supplierMappingData').hide();
        $('#info-section4').hide();
        $('#supplierMappingBottomData').hide();
        workerSupplierTab();
    } else {
        $('#supplierMappingData').show();
        $('#supplierMappingBottomData').show();
    }
}

/* Section for ProfileGroupMapping functions */
var paginationInTransitForProfileGroup = false;
function getProfileGroupMappingsPage(pageNum, searchTerm, freshSearch) {
    var list = persistCheckboxStateOnPageChange('deleteMappingForm');
    if (null == freshSearch
        && ((pageNum < 1 || (pgPagesAvailable > 0 && pageNum > pgPagesAvailable))
        || paginationInTransitForProfileGroup)) {
        return;
    }
    if (freshSearch != null && paginationInTransit) {
        return;
    }
    paginationInTransitForProfileGroup = true;
    try {
        $
            .ajax({
                type: 'GET',
                async: false,
                cache: false,
                url: gProfileGroupMappingsPageURI,
                data: {
                    pageNumber: pageNum,
                    searchTerm: searchTerm
                },
                success: function (data, textStatus, jqXHR) {
                    if (jqXHR.responseText != '') {

                        var responseArray = jqXHR.responseText.split('@@@');
                        pgPagesAvailable = responseArray[1];
                        pgPageNumber = responseArray[2];
                        pgPageSize = responseArray[3];
                        pgTotalRecords = responseArray[4];

                        // $('#profile_group_mappings_section').empty();
                        $('#profile_group_mappings_section').html(
                            responseArray[0]);

                        // if ((pgTotalRecords - pgPageSize - 1) < 0) {
                        // $('#profileGroupPager').hide();
                        // } else {
                        // if ($("#profileGroupPager").is(':hidden')){
                        // $("#profileGroupPager").show();
                        // }
                        // }
                        $('#profileGroupBottomData').show();
                        $("#profileGroupPager").show();
                        var begingRecord = (pgTotalRecords > 0) ? (pageNum - 1)
                        * pgPageSize + 1
                            : 0;
                        var endRecord = (pageNum) * gPageSize;
                        endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                            : endRecord;
                        pgPageNumber = pageNum;
                        paginationInTransitForProfileGroup = false;
                        updateGroupTable();

                        copyCheckedItemList('deleteMappingForm', list);
                    }
                }
            });
    } catch (exp) {
        alert(exp);
    }
}

/* Section for ProfileGroupMapping functions */
var paginationInTransitForProfileGroup = false;
function getUOMMappingsPage(pageNum, searchTerm, freshSearch) {
    if (null == freshSearch
        && ((pageNum < 1 || (pgPagesAvailable > 0 && pageNum > pgPagesAvailable))
        || paginationInTransitForProfileGroup)) {
        return;
    }
    if (freshSearch != null && paginationInTransit) {
        return;
    }
    paginationInTransitForProfileGroup = true;
    try {
        $.ajax({
            type: 'GET',
            async: false,
            cache: false,
            url: gUOMMappingsPageURI,
            data: {
                pageNumber: pageNum,
                searchTerm: searchTerm
            },
            success: function (data, textStatus, jqXHR) {
                if (jqXHR.responseText != '') {

                    var responseArray = jqXHR.responseText.split('@@@');
                    pgPagesAvailable = responseArray[1];
                    pgPageNumber = responseArray[2];
                    pgPageSize = responseArray[3];
                    pgTotalRecords = responseArray[4];

                    // $('#profile_group_mappings_section').empty();
                    $('#uom_mappings_section').html(responseArray[0]);

                    // if ((pgTotalRecords - pgPageSize - 1) < 0) {
                    // $('#profileGroupPager').hide();
                    // } else {
                    // if ($("#profileGroupPager").is(':hidden')){
                    // $("#profileGroupPager").show();
                    // }
                    // }
                    $('#uomBottomData').show();
                    $("#uomMappingPager").show();
                    var begingRecord = (pgTotalRecords > 0) ? (pageNum - 1)
                    * pgPageSize + 1 : 0;
                    var endRecord = (pageNum) * gPageSize;
                    endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                        : endRecord;
                    pgPageNumber = pageNum;
                    paginationInTransitForProfileGroup = false;
                    updateUOMGroupTable();

                }
            }
        });
    } catch (exp) {
        alert(exp);
    }
}

/* Section for Power Shopper functions */
var paginationInTransitForPowerShopper = false;
function getPowerShopperPage(pageNum, searchTerm, freshSearch) {
    if (null == freshSearch
        && ((pageNum < 1 || (psPagesAvailable > 0 && pageNum > psPagesAvailable))
        || paginationInTransitForPowerShopper)) {
        return;
    }
    if (freshSearch != null && paginationInTransit) {
        return;
    }
    paginationInTransitForPowerShopper = true;

    $("#power_shopper_table_rows_body tr").length;
    try {
        $.ajax({
            type: 'GET',
            async: false,
            cache: false,
            url: 'content-sharing-mappings/page',
            data: {
                pageNumber: pageNum,
                searchTerm: searchTerm
            },
            success: function (data, textStatus, jqXHR) {
                if (jqXHR.responseText != '') {

                    var responseArray = jqXHR.responseText.split('@@@');
                    psPagesAvailable = responseArray[1];
                    pgPageNumber = responseArray[2];
                    pgPageSize = responseArray[3];
                    pgTotalRecords = responseArray[4];

                    $('.power_shopper_section').html(responseArray[0]);

                    $('#powerShopperBottomData').show();
                    $("#uomMappingPager").show();
                    var begingRecord = (pgTotalRecords > 0) ? (pageNum - 1)
                    * pgPageSize + 1 : 0;
                    var endRecord = (pageNum) * gPageSize;
                    endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                        : endRecord;
                    pgPageNumber = pageNum;
                    paginationInTransitForPowerShopper = false;
                    updatePowerShopperTable();

                }
            }
        });
    } catch (exp) {
        alert(exp);
    }
}

var paginationInTransitForProfileGroup = false;
function getCurrencyMappingsPage(pageNum, searchTerm, freshSearch) {
    if (null == freshSearch
        && ((pageNum < 1 || (pgPagesAvailable > 0 && pageNum > pgPagesAvailable)))) {
        return;
    }
    if (freshSearch != null && paginationInTransit) {
        return;
    }

    try {
        $.ajax({
            type: 'GET',
            async: false,
            cache: false,
            url: gCurrencyMappingsPageURI,
            data: {
                pageNumber: pageNum,
                searchTerm: searchTerm
            },
            success: function (data, textStatus, jqXHR) {
                if (jqXHR.responseText != '') {

                    var responseArray = jqXHR.responseText.split('@@@');
                    pgPagesAvailable = responseArray[1];
                    pgPageNumber = responseArray[2];
                    pgPageSize = responseArray[3];
                    pgTotalRecords = responseArray[4];

                    // $('#profile_group_mappings_section').empty();
                    $('#currency_mappings_section').html(responseArray[0]);

                    // if ((pgTotalRecords - pgPageSize - 1) < 0) {
                    // $('#profileGroupPager').hide();
                    // } else {
                    // if ($("#profileGroupPager").is(':hidden')){
                    // $("#profileGroupPager").show();
                    // }
                    // }
                    $('#currencyBottomData').show();
                    $("#currencyMappingPager").show();
                    var begingRecord = (pgTotalRecords > 0) ? (pageNum - 1)
                    * pgPageSize + 1 : 0;
                    var endRecord = (pageNum) * gPageSize;
                    endRecord = (endRecord > pgTotalRecords) ? pgTotalRecords
                        : endRecord;
                    pgPageNumber = pageNum;

                    updateCurrencyGroupTable();

                }
            }
        });
    } catch (exp) {
        alert(exp);
    }
}
function updateGroupTable() {
    jcf.customForms.replaceAll();
    $("#profile_group_table_rows_body .groupNameSelectDiv").hide();
    jcf.customForms.replaceAll();
    $("#profile_group_table_rows_body .groupNameLinkDiv").show();
}
function updateUOMGroupTable() {
    jcf.customForms.replaceAll();
    $("#uom_table_rows_body .groupNameSelectDiv").hide();
    jcf.customForms.replaceAll();
    $("#uom_table_rows_body .groupNameLinkDiv").show();
}

function updatePowerShopperTable() {
    jcf.customForms.replaceAll();
    $("#power_shopper_table_rows_body .groupNameSelectDiv").hide();
    jcf.customForms.replaceAll();
    $("#power_shopper_table_rows_body .groupNameLinkDiv").show();
}

function updateCurrencyGroupTable() {
    jcf.customForms.replaceAll();
    $("#currency_table_rows_body .groupNameSelectDiv").hide();
    jcf.customForms.replaceAll();
    $("#currency_table_rows_body .groupNameLinkDiv").show();
}

function savePowerShopper() {
    var userName = $.trim($('#newShopperUserName').val());
    var email = $.trim($('#newShopperEmail').val());
    var selectedGroups = $("#select-state").val();
    var firstName = $.trim($("#newShopperFirstName").val());
    var lastName = $.trim($("#newShopperLastName").val());
    var pageName = $("#pgNumberHPowerShopper").val();
    var groupToken = [];
    var groupName = [];

    if (!firstName || !lastName || !userName || !email || !selectedGroups) {
        noty({
            text: "All fields are mandatory",
            type: 'error'
        });
        return false;
    } else if (!re.test(email)) {
        noty({
            text: "Enter a valid email",
            type: 'error'
        });
        return false;
    }

    $(selectedGroups).each(function (index, element) {
        var result = element.split("-");
        groupName.push(result[0]);
        groupToken.push(result[1]);
    });

    var data = {
        username: userName,
        firstName: firstName,
        lastName: lastName,
        email: email,
        sharedContentGroupTokens: groupToken,
        sharedContentGroups: groupName
    };

    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "content-sharing-mappings/save",
        'data': JSON.stringify(data)
    }).done(function (response) {
        if (response === 'success') {
            getPowerShopperPage(pageName, $('#searchboxShopper2').val());
        } else if (response === 'Failed') {
            noty({
                text: 'some error occurred while adding power shopper ',
                type: 'error'
            });
        } else if (response === 'mappingExists') {
            noty({
                text: 'A mapping for this username already exists ',
                type: 'error'
            });
        }

    });
}
function createContentAccessMapping() {
    var groupName = $.trim($('#txtGroupNameN').val());
    var sapUser= $.trim($('#txtSAPUserN').val());
    sapUser = encodeURIComponent(sapUser);

    var uniqueSystemId = $.trim($('#uniqueSystemId').val());

    if (sapUser == null || sapUser == '') {
        noty({
            text: 'User cannot be blank ',
            type: 'error'
        });
        return false;
    }

    if (groupName == null || groupName == '') {
        return false;
    }

    var mappingId = $('#mappingId').val();
    var pageNum = $('#pgNumberH').val();

    addContentMapping = false;

    try {

        $
            .ajax({
                type: 'POST',
                url: 'validatecontentaccess',
                data: 'groupname=' + groupName + '&user=' + sapUser
                + '&uniqueSystemId=' + uniqueSystemId,
                async: false,
                success: function (response) {

                    if (response == true) {
                        noty({
                            text: "A record already exists with this Username: "
                            + decodeURIComponent(sapUser),
                            type: 'error'
                        });
                        return;
                    } else {

                        $
                            .ajax({
                                type: 'POST',
                                url: 'validateuniqueSystemSAPUser',
                                data: 'groupname=' + groupName
                                + '&user=' + sapUser
                                + '&uniqueSystemId='
                                + uniqueSystemId,
                                async: false,
                                success: function (response) {

                                    if (response == true) {
                                        noty({
                                            text: "Unique System ID "
                                            + uniqueSystemId
                                            + " does not exist.",
                                            type: 'error'
                                        });
                                        return;

                                    } else {
                                        try {
                                            $
                                                .ajax({
                                                    type: 'POST',
                                                    async: false,
                                                    url: 'savecontentaccess',
                                                    data: {
                                                        groupName: groupName,
                                                        sapUser: $
                                                            .trim($(
                                                                '#txtSAPUserN')
                                                                .val()),
                                                        mappingId: mappingId,
                                                        uniqueSystemId: uniqueSystemId
                                                    },
                                                    success: function (data,
                                                                       textStatus,
                                                                       jqXHR) {

                                                        getProfileGroupMappingsPage(
                                                            pageNum,
                                                            $(
                                                                '#profileGroupSearchTerm')
                                                                .val());
                                                    }
                                                });
                                        } catch (exp) {
                                            alert(exp)
                                        }
                                    }
                                }
                            });

                    }

                }
            });

    } catch (exp) {
        alert(exp)
    }

}

function updateContentAccess(refresh) {
    var selectedId = $('#selectId').val();
    var groupName = $.trim($('#txtGroupNameE-' + selectedId).val());
    var sapUser = $.trim($('#txtSapUsrE-' + selectedId).val());
    var uniqueSystemId = $.trim($('#txtsystemIdE-' + selectedId).val());

    if (sapUser == null || sapUser == '') {
        noty({
            text: "Please enter a username.",
            type: 'error'
        });
        return false;
    }

    if (groupName == null || groupName == '') {
        noty({
            text: "Please select a group name.",
            type: 'error'
        });
        return false;
    }

    $(
        "#profile_group_mappings_table div[style='display:none'] input[value='"
        + sapUser + "'].contenMappingUser")
        .each(
        function () {
            if ($("#profile_group_mappings_table div[style='display:none'] input[value='"
                    + uniqueSystemId
                    + "'].contenMappinguniqueSupplierId").length > 0) {
                noty({
                    text: "A record already exists with this Username: "
                    + sapUser,
                    type: 'error'
                });
                return false;
            }
        });

    editContentMapping = false;

    var mappingId = selectedId;
    var pageNum = $('#pgNumberH').val();

    try {
        $.ajax({
            type: 'POST',
            async: false,
            url: 'savecontentaccess',
            data: {
                groupName: groupName,
                sapUser: sapUser,
                mappingId: mappingId,
                uniqueSystemId: uniqueSystemId
            },
            success: function (data, textStatus, jqXHR) {
                $('#selectId').val(null);
                $('#spnName-' + mappingId).html(groupName);
                if (refresh) {
                    getProfileGroupMappingsPage(pageNum, $(
                        '#profileGroupSearchTerm').val());
                }
            }
        });
    } catch (exp) {
        alert(exp)
    }
}

function updatePowerShopper(refresh) {
    var selectedId = $('#selectIdPowerShopper').val();
    var firstName = $.trim($('#textPSFirstNameE-' + selectedId).val());
    var lastName = $.trim($('#textPSLastNameE-' + selectedId).val());
    var userName = $.trim($('#psUserName-' + selectedId + '-E').html());
    var email = $.trim($('#textEmailE-' + selectedId).val());
    var selectedGroups = $('#select-state-' + selectedId).val();
    var groupToken = [];
    var groupName = [];

    if (!firstName || !lastName || !userName || !email || !selectedGroups) {
        noty({
            text: "All fields are mandatory",
            type: 'error'
        });
        return false;
    } else if (!re.test(email)) {
        noty({
            text: "Enter a valid email",
            type: 'error'
        });
        return false;
    }

    $(selectedGroups).each(function (index, element) {
        var result = element.split("-");
        groupName.push(result[0]);
        groupToken.push(result[1]);
    });

    editPowerShopper = false;

    var pageNum = $('#pgNumberHPowerShopper').val();

    var data = {
        username: userName,
        firstName: firstName,
        lastName: lastName,
        email: email,
        sharedContentGroupTokens: groupToken,
        sharedContentGroups: groupName,
        id: selectedId
    };

    try {
        $
            .ajax({
                headers: {
                    'Content-Type': 'application/json'
                },
                type: 'PUT',
                url: 'content-sharing-mappings/' + selectedId,
                data: JSON.stringify(data),
                success: function (response) {
                    if (refresh) {
                        if (response === 'updated') {
                            $('#selectIdPowerShopper').val(null);
                            getPowerShopperPage(pageNum, $(
                                '#searchboxShopper2').val());
                        } else if (response === 'Failed') {
                            noty({
                                text: 'some error occured while updating power shopper ',
                                type: 'error'
                            });
                        } else if (response === 'mappingExists') {
                            noty({
                                text: 'A mapping with this username already exists ',
                                type: 'error'
                            });
                        }
                    }
                }
            });
    } catch (exp) {
        alert(exp);
    }
}

function addRow() {
    addContentMapping = true;
    var selectedRow = $('#selectId').val();
    if (selectedRow !== null && selectedRow !== '') {
        noty({
            text: "Please save the fields which are in edit mode before adding a new row or editing another row.",
            type: 'error'
        });
        return false;

    }
    $('#addRow').val('yes');
    $('#trNewRec').show();
    $('#addMappingBtn').hide();
    $('#removeMappingBtn').hide();
    $('#btnCancelN').show();
    $('#saveMappingBtn').show();
}

function addRowForPowerShopper() {
    $(".noDataYet").toggle();
    addPowerShopper = true;
    var selectedRow = $('#selectId').val();
    if (selectedRow !== null && selectedRow !== '') {
        noty({
            text: "Please save the fields which are in edit mode before adding a new row or editing another row.",
            type: 'error'
        });
        return false;
    }
    $('#addRowPowerShopper').val('yes');
    $('#trNewRecPowerShopper').show();
    $('#addPowerShopperBtn').hide();
    $('#removePowerShopperBtn').hide();
    $('#btnCancelNPowerShopper').show();
    $('#savePowerShopperBtn').show();
    $('#select-state').selectize({
        maxItems: 900
    });
}

function editContentAccess(rowId) {
    if (addContentMapping) {
        noty({
            text: "Please save the fields which are in add mode before adding a new row or editing another row.",
            type: 'error'
        });
        return false;
    }
    if (editContentMapping) {
        updateContentAccess(true);
    }
    editContentMapping = true;

    var selectedRow = $('#selectId').val();
    var addRow = $('#addRow').val();

    if ((addRow !== null && addRow !== '')
        || (selectedRow !== null && selectedRow !== '')) {
        noty({
            text: "Please save the fields which are in edit mode before adding a new row or editing another row.",
            type: 'error'
        });
        return false;
    }

    $('#trNewRecPowerShopper').hide();
    $('#addMappingBtn').hide();
    $('#removeMappingBtn').hide();
    $('#saveMappingBtn').hide();
    $('#txtGroupNameN').val(null);
    $('#saveMappingBtnE').show();
    $('#txtSAPUserN').val(null);
    $('#btnCancelE').show();
    $('#grpName-' + rowId + '-V').hide();
    $('#sapUser-' + rowId + '-V').hide();
    $('#systemId-' + rowId + '-V').hide();
    $('#grpName-' + rowId + '-E').show();
    $('#sapUser-' + rowId + '-E').show();
    $('#systemId-' + rowId + '-E').show();
    $('#selectId').val(rowId);
}

function editPowerShopperFn(rowId) {
    if (editPowerShopper) {
        noty({
            text: "Please save the fields which are in add mode before adding a new row or editing another row.",
            type: 'error'
        });
        return false;
    }
    if (editContentMapping) {
        updateContentAccess(true);
    }

    var selectedRow = $('#selectIdPowerShopper').val();
    var addRow = $('#addRowPowerShopper').val();

    if ((addRow !== null && addRow !== '')
        || (selectedRow !== null && selectedRow !== '')) {
        noty({
            text: "Please save the fields which are in edit mode before adding a new row or editing another row.",
            type: 'error'
        });
        return false;
    }
    editPowerShopper = true;
    $('#trNewRec').hide();
    $('#addPowerShopperBtn').hide();
    $('#removePowerShopperBtn').hide();
    $('#savePowerShopperBtn').hide();
    $('#savePowerShopperBtnE').show();
    $('#btnCancelEPowerShopper').show();
    $('#psFirstName-' + rowId + '-V').hide();
    $('#psLastName-' + rowId + '-V').hide();
    $('#psUserName-' + rowId + '-V').hide();
    $('#psEmail-' + rowId + '-V').hide();
    $('#psContentGroup-' + rowId + '-V').hide();
    $('#psFirstName-' + rowId + '-E').show();
    $('#psLastName-' + rowId + '-E').show();
    $('#psUserName-' + rowId + '-E').show();
    $('#psEmail-' + rowId + '-E').show();
    $('#psContentGroup-' + rowId + '-E').show();
    $('#selectIdPowerShopper').val(rowId);
    $('#select-state-' + rowId + '').selectize({
        maxItems: 900
    });
}

function cancelEdit() {
    editContentMapping = false;
    var rowId = $('#selectId').val();
    $('#grpName-' + rowId + '-V').show();
    $('#sapUser-' + rowId + '-V').show();
    $('#systemId-' + rowId + '-V').show();
    $('#grpName-' + rowId + '-E').hide();
    $('#sapUser-' + rowId + '-E').hide();
    $('#systemId-' + rowId + '-E').hide();
    $('#saveMappingBtnE').hide();
    $('#addMappingBtn').show();
    $('#removeMappingBtn').show();
    $('#btnCancelE').hide();
    $('#selectId').val(null);
}

function cancelEditShopper() {

    editPowerShopper = false;
    var rowId = $('#selectIdPowerShopper').val();

    $('#psFirstName-' + rowId + '-V').show();
    $('#psLastName-' + rowId + '-V').show();
    $('#psUserName-' + rowId + '-V').show();
    $('#psEmail-' + rowId + '-V').show();
    $('#psContentGroup-' + rowId + '-V').show();

    $('#psFirstName-' + rowId + '-E').hide();
    $('#psLastName-' + rowId + '-E').hide();
    $('#psUserName-' + rowId + '-E').hide();
    $('#psEmail-' + rowId + '-E').hide();
    $('#psContentGroup-' + rowId + '-E').hide();

    $('#savePowerShopperBtnE').hide();
    $('#addPowerShopperBtn').show();
    $('#removePowerShopperBtn').show();
    $('#btnCancelEPowerShopper').hide();
    $('#selectIdPowerShopper').val(null);
}

function cancelAdd() {
    addContentMapping = false;
    $('#saveMappingBtnE').hide();

    $('#selectId').val(null);
    $('#addRow').val(null);
    $('#trNewRec').hide();
    $('#addMappingBtn').show();
    $('#removeMappingBtn').show();
    $('#saveMappingBtn').hide();
    $('#txtGroupNameN').val(null);
    $('#txtGroupNameN').prop('selectedIndex', -1);
    $('#txtSAPUserN').val(null);
    $('#btnCancelN').hide();
}

function cancelAddPowerShopper() {
    $(".noDataYet").toggle();
    addPowerShopper = false;
    $('#savePowerShopperBtnE').hide();
    $('#newShopperUserName').val('');
    $('#newShopperEmail').val('');
    $('#newShopperFirstName').val('');
    $('#newShopperLastName').val('');
    var $select = $('#select-state').selectize();
    var control = $select[0].selectize;
    control.clear();
    $('#selectIdPowerShopper').val(null);
    $('#addRowPowerShopper').val(null);
    $('#trNewRecPowerShopper').hide();
    $('#addPowerShopperBtn').show();
    $('#removePowerShopperBtn').show();
    $('#savePowerShopperBtn').hide();
    $('#btnCancelNPowerShopper').hide();
}

function removeContenAccessConfirm() {
    if (!isAnyItemChecked('deleteMappingForm')) {
        alert('Please select an item');
        return;
    }

    deleteConfirmAction(
        'Please click "DELETE" to confirm that you would like to remove the selected item(s).',
        removeContenAccess);
}
function removePowerShopperConfirm() {
    if (!isAnyItemChecked('deletePowerShopperForm')) {
        alert('Please select a power shopper');
        return;
    }

    deleteConfirmAction(
        'Please click "DELETE" to confirm that you would like to remove the selected power shopper(s).',
        removePowerShopper);
}

function removeContenAccess() {
    var gIDs = prepareQueryString('deleteMappingForm', 'profileMappings');

    $.ajax({
        type: 'POST',
        url: 'deletecontentaccess',
        data: gIDs,
        dataType: 'text',
        success: function (response) {
            var pageNum = $('#pgNumberH').val();
            getProfileGroupMappingsPage(pageNum, $('#profileGroupSearchTerm')
                .val());
        },
        error: function (error) {
            alert("Unexpected Error happened. Please try later.");
        }
    });
}

function removePowerShopper() {
    var sIDs = $('#deletePowerShopperForm').find("#checkedItems").html().split(
        ",");
    var pageNum = $('#pgNumberHPowerShopper').val();

    if ((($("#power_shopper_table_rows_body tr").length - 1) == sIDs.length) && pageNum != 1) {
        pageNum = pageNum - 1;
    }
    var numOfRecords = $("#power_shopper_table_rows_body tr").length;

    $.ajax({
        headers: {
            'Content-Type': 'application/json'
        },
        type: 'POST',
        url: 'content-sharing-mappings/delete',
        data: JSON.stringify(sIDs),
        dataType: 'text',
        success: function (response) {
            getPowerShopperPage(pageNum, $('#searchboxShopper2').val());
        },
        error: function (error) {
            alert("Unexpected Error happened. Please try later.");
        }
    });
}