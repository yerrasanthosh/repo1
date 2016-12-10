
$(document).ready(function(){

    $( document ).on('change','#supplierFile' , function(){ uploadFile('supplierMapping');return false; });
    setupSupplierMappingTab();

});

function setupSupplierMappingTab() {
    if( typeof gPagesAvailableSupplier == 'undefined' || (typeof gPagesAvailableSupplier != 'undefined' && gPagesAvailableSupplier<1) ) {
        $('#supplierMappingData').hide();
        $('#info-section4').hide();
        $('#supplierMappingBottomData').hide();
        workerSupplierTab();
    }  else {
        $('#supplierMappingData').show();
        $('#supplierMappingBottomData').show();
    }
}


function uploadFile(tab) {
    var ext = '';
    if (tab == 'supplierMapping') {
        if($('#supplierFile').val() == '') {
            $('#supplierFile').val('');
            alert('Invalid file!');
            return false;
        }
        ext = $('#supplierFile').val().split('.').pop().toLowerCase();

        if($.inArray(ext, ['xlsx','xls']) == -1) {
            $('#supplierFile').val('');
            alert('Invalid file!');
            return false;
        }

        $('#supplierMappingFileForm').ajaxSubmit({ success: function(responseText, statusText) {
            $('#supplierMappingData').hide();
            $('#info-section4').hide();
            workerSupplierTab();
        } });
    }
}

function workerSupplierTab() {
    try {
        $.ajax({
            url: gSupplierMappingsPageURI,
            cache: false,
            data: { pageNumber: 1, fileUploading: 'true'},
            success: function(data, textStatus, jqXHR) {
                if(jqXHR.responseText != ''){
                    var responseArray = jqXHR.responseText.split('@@@');
                    gPagesAvailableSupplier = responseArray[1];
                    gPageNumberSupplier = responseArray[2];
                    gPageSize = responseArray[3];
                    gTotalRecordsSupplier = responseArray[4];
                    gSupplierProcessFailed = responseArray[7];
                    vmTotalRecords = responseArray[8];
                    vmProcessedCount = responseArray[9];

                    if(responseArray[5]=="true"){
                        $("#lblDownloadSupplierErrorReportLink").show();
                        $("#downloadSupplierErrorReportLink").show();
                        $("#downloadSupplierErrorReportLink").attr('href','view-supplier-bulk-upload-error-report?fileHandle='+responseArray[6]);
                    } else {
                        $("#lblDownloadSupplierErrorReportLink").hide();
                        $("#downloadSupplierErrorReportLink").hide();
                    }

                    $("#vmDownloadProcessedReportLink").show();
                    $("#vmLblDownloadProcessedReportLink").show();

                    if (typeof gPagesAvailableSupplier !== 'undefined' && gPagesAvailableSupplier > 0) {
                        $('#custom_supplier_mappings_section').empty();
                        $('#custom_supplier_mappings_section').append(responseArray[0]);

                        if ((gTotalRecordsSupplier - gPageSize - 1) < 0) {
                            $('#customSupplierPager').hide();
                        } else {
                            if ($("#customSupplierPager").is(':hidden')){
                                $("#customSupplierPager").show();
                            }
                        }

                        $('#supplierMappingBottomData').show();

//                    if(pageNum > 1){
//                        $("#material_group_mapping_prev_page").removeClass("btn-prev").addClass("btn-prev-second");
//                    }else if(pageNum == 1){
//                        $("#material_group_mapping_prev_page").removeClass("btn-prev-second").addClass("btn-prev");
//                    }
                        var begingRecord = (gTotalRecordsSupplier>0)?(1-1)*gPageSize+1:0;
                        var endRecord = (1)*gPageSize;

                        endRecord = (endRecord > gTotalRecordsSupplier) ? gTotalRecordsSupplier:endRecord ;

//                        $('#custom_supplier_mapping_current_record_range').html('Showing Records: ' + begingRecord + '- ' + endRecord + ' of ' + gTotalRecordsSupplier);
                        $('#custom_supplier_mappings_section').show();
                        $('#info-section4').hide();
                        $('#supplierMappingData').show();
                        $("#supplierFile").remove();
                        $("#supplierFileDiv").html("<input size='75' type='file' style='cursor: pointer;' name='supplierFile' id='supplierFile'/>");
                        jcf.customForms.replaceAll();
                        jcf.customForms.refreshAll();

                        gPageNumberSupplier = 1;
                        $("#searchWithinId").val('');
                        $('#searchCriteriaBtn').click();
                    } else {

//                    alert('inside else');
                        $('#info-section4').show();
                        $('#info-section4').empty();
                        $('#info-section4').html(responseArray[0]);
                        if(gSupplierProcessFailed) {
                            $('#info-section4-loading').hide();
                        }
                    }
                    paginationInTransit = false;

                }
            },
            complete: function() {
                // Schedule the next request when the current one's complete
//            alert("completed call");
                if (!gPagesAvailableSupplier && (!gSupplierProcessFailed || gSupplierProcessFailed==false)){
                    setTimeout(workerSupplierTab, 5000);
                }
            }
        });
    }catch(exp){
        alert(exp)
    }
}


