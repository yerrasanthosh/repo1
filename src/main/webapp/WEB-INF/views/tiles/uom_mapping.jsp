<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="content-block toggle-block active" id="summary-section1">
        <div class="headline">
            <h2><a href="#" class="open-close">UOM Mapping</a></h2>
        </div>
        <div class="block">
            <div class="content reorder-widget editable-widget">

                <br />

                <form action="uploadUOMMapping" id="uomMappingForm" class="form-create" method="post" enctype="multipart/form-data">
                    <fieldset>
                        <div class="area file-area">
                            <div class="row" style="width:100%;font-weight:bold" id="theCatalogFileId">
                                <label for="uomMappingFile"><spring:message code="com.adminui.uom_mapping.UploadMappingFile" text="default text" /> </label>
								<div class="area-col" id="uomMapping">
									<input type="file" style="cursor: pointer;" name="uomMappingFile" id="uomMappingFile"/>
								</div>
                            </div>
                            <br />
                            <div class="row" style="width:100%;font-weight:bold;" id="downloadMapping">
                                <br/>
                                <label for="uomDownloadErrorReportLink" id="uomLblDownloadErrorReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;${pgErrorReportStyle}"><spring:message code="com.adminui.uom_mapping.errorInUpload" text="default text" /></label>
                                <a href="viewuommappingerrors?pgFileHandle=${pgFileHandle}" target="_blank" id="uomDownloadErrorReportLink" style="color:#0000FF;${pgErrorReportStyle}"><spring:message code="com.adminui.uom_mapping.downloadReport" text="default text" /></a>
                                <br/>
                                <label for="uomDownloadProcessedReportLink" id="uomLblDownloadProcessedReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;"><spring:message code="com.adminui.uom_mapping.processFile" text="default text" /></label><a href="viewuommapping.xlsx" target="_blank" id="uomDownloadProcessedReportLink" style="color:#0000FF;${pgProcessedReportStyle}"><spring:message code="com.adminui.uom_mapping.downloadData" text="default text" /></a>


                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
</div>

    <div class="add-slide-blocks">
        <input type="hidden" class="normal" value="" id="searchTerm" />
        <div class="toggle-block active" id="uom-info-section1">
            <div class="title">
                <h2><a href="#" class="open-close"><spring:message code="com.adminui.uom_mapping.UNITOFMEASURE" text="default text" /></a></h2>
            </div>
            <div class="block" id="profile_group_div">
                    <div class="content editable-widget" id="uomMappingData" style="display: block">
                        <div class="top-box">
                            <form action="#" class="add-search-form advanced" id="searchUOMForm" onsubmit="return false;" >
                                <fieldset>
                                    <input type="text" value="Search Unit of Measure" name="searchTextDisp" id="searchBox1" onclick='$("#searchBox1").hide();$("#searchbox2").show();$("#searchbox2").focus();jcf.customForms.replaceAll()'  />
                                    <input type="text" value="" name="searchText" id="searchbox2" style="display: none" />
                                    <input type="button" id="searchBtn" value="Submit" onclick="$('#searchTerm').val($('#searchbox2').val());getUOMMappingsPage(1,$('#searchTerm').val(),'freshSearch');"/>
                                    <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchbox2').val('');$('#searchTerm').val('');$('#searchbox2').hide();$('#searchBox1').show(); $('#searchBtn').click();" />
                                </fieldset>
                            </form>
                        </div>
                        <div id="uom_mappings_section">
                        	<jsp:include page="uom_mapping_table_fragment.jsp" />
                        </div>
                    </div>

                    <div id="uom-info-section2" >
                        <table>
                            <tr>
                                <td>
                                    <h4 id="uom_message"><c:out value="${pgMessage}"/></h4>
                                </td>
                                <td >
                    	<div id="uom-info-section2-loading">
                        	<img src="res/images/loading.gif" alt="" />
                        </div>
                                </td>
                            </tr>
                        </table>
                    </div>

            </div>
        </div>

    </div>