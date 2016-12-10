
<link media="all" rel="stylesheet" type="text/css" href="res/css/multi-select/selectize.default.css" />
<!--[if lt IE 9]><script src="http://cdnjs.cloudflare.com/ajax/libs/es5-shim/2.0.8/es5-shim.min.js"></script><![endif]-->

<div class="content-block toggle-block active" id="summary-section1">
    <div class="headline">
        <h2><a href="#" class="open-close">Power Shopper Mapping</a></h2>
    </div>
    <div class="block">
        <div class="content reorder-widget editable-widget">

            <br />

            <form action="powershopper/upload" id="powerShopperUploadForm" class="form-create" method="post" enctype="multipart/form-data">
                <fieldset>
                    <div class="area file-area">
                        <div class="row" style="width:100%;font-weight:bold" id="thePowerShopperMappingFileId">
                            <label for="powerShopperMappingFile">Upload Power Shopper File: </label>
                            <div class="area-col" id="powerShopperMapping">
                                <input type="file" style="cursor: pointer;" name="powerShopperMappingFile" id="powerShopperMappingFile"/>
                            </div>
                        </div>
                        <br />
                        <div class="row" style="width:100%;font-weight:bold;" id="powerShopperMappingDownload">
                            <br/>
                            <label for="powsMappingErrorReportLink" id="lblPowsMappingErrorReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;${pgErrorReportStyle}">Errors occurred during upload:</label>
                            <a href="contentsharemappingerrors?pgFileHandle=${pgFileHandle}" target="_blank" id="powsMappingErrorReportLink" style="color:#0000FF;${pgErrorReportStyle}">Download error report</a>
                            <br/>
                            <label for="powerShopperMappingDownloadLink" id="lblPowerShopperMappingDownloadLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;">Processed File :</label>
                            <a href="contentsharemapping.xlsx" target="_blank" id="powerShopperMappingDownloadLink" style="color:#0000FF;${pgProcessedReportStyle}">Download processed data</a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<div class="add-slide-blocks">
    <input type="hidden" class="normal" value="" id="searchTermPS" />
    <div class="toggle-block active" id="uom-info-section1">
        <div class="title">
            <h2><a href="#" class="open-close">Power Shoppers</a></h2>
        </div>
        <div class="block" id="profile_group_div">
            <div class="content editable-widget" id="powsMappingData" style="display: block">
                <div class="top-box">
                    <form action="#" class="add-search-form advanced" id="searchUOMForm" onsubmit="return false;" >
                        <fieldset>
                            <input type="text" value="Search Power Shopper" name="searchTextDisp" id="searchBoxShopper1" onclick='$("#searchBoxShopper1").hide();$("#searchboxShopper2").show();$("#searchboxShopper2").focus();jcf.customForms.replaceAll()'  />
                            <input type="text" value="" name="searchText" id="searchboxShopper2" style="display: none" />
                            <input type="button" id="searchBtn" class="searchBtnForPowerShopper" value="Submit" onclick="$('#searchTermPS').val($('#searchboxShopper2').val());getPowerShopperPage(1,$('#searchTermPS').val(),'freshSearch');"/>
                            <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchboxShopper2').val('');$('#searchTerm').val('');$('#searchboxShopper2').hide();$('#searchBoxShopper1').show(); $('.searchBtnForPowerShopper').click();" />
                        </fieldset>
                    </form>
                </div>
                <div id="profile_group_mappings_section" class="power_shopper_section">
                    <jsp:include page="power_shopper_table_fragment.jsp" />
                </div>
            </div>

            <div id="pows-info-section2" >
                <table>
                    <tr>
                        <td>
                            <h4 id="pows_message"><c:out value="${pgMessage}"/></h4>
                        </td>
                        <td >
                            <div id="pows-info-section2-loading">
                                <img src="res/images/loading.gif" alt="" />
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

        </div>
    </div>

</div>
<script type="text/javascript" src="res/js/ext/multi-select/selectize.js"></script>
