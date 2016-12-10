<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="content-block toggle-block active" id="summary-section1">
        <div class="headline">
            <h2><a href="#" class="open-close"><spring:message code="com.adminui.currency_mapping.CurrencyMapping" text="default text" /></a></h2>
        </div>
        <div class="block">
            <div class="content reorder-widget editable-widget">

                <br />

                <form action="currencymappings/upload" id="currencyMappingForm" class="form-create" method="post" enctype="multipart/form-data">
                    <fieldset>
                        <div class="area file-area">
                            <div class="row" style="width:100%;font-weight:bold" id="theCatalogFileId">
                                <label for="currencyMappingFile"><spring:message code="com.adminui.currency_mapping.UploadMappingFile" text="default text" /> </label>
								<div class="area-col" id="currencyMapping">
									<input type="file" style="cursor: pointer;" name="currencyMappingFile" id="currencyMappingFile"/>
								</div>
                            </div>
                            <br />
                            <div class="row" style="width:100%;font-weight:bold;" id="downloadMapping">
                                <br/>
                                <label for="currencyDownloadErrorReportLink" id="currencyLblDownloadErrorReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;${pgErrorReportStyle}"><spring:message code="com.adminui.currency_mapping.Errorsoccurredduringupload" text="default text" /></label>
                                <a href="viewcurrencymappingerrors?pgFileHandle=${pgFileHandle}" target="_blank" id="currencyDownloadErrorReportLink" style="color:#0000FF;${pgErrorReportStyle}"><spring:message code="com.adminui.currency_mapping.Downloaderrorreport" text="default text" /></a>
                                <br/>
                                <label for="currencyDownloadProcessedReportLink" id="currencyLblDownloadProcessedReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;"><spring:message code="com.adminui.currency_mapping.ProcessedFile" text="default text" /></label><a href="viewcurrencymapping.xlsx" target="_blank" id="currencyDownloadProcessedReportLink" style="color:#0000FF;${pgProcessedReportStyle}"><spring:message code="com.adminui.currency_mapping.Downloadprocesseddata" text="default text" /></a>


                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
</div>

    <div class="add-slide-blocks">
        <input type="hidden" class="normal" value="" id="searchTerm" />
        <div class="toggle-block active" id="currency-info-section1">
            <div class="title">
                <h2><a href="#" class="open-close"><spring:message code="com.adminui.currency_mapping.Currency" text="default text" /></a></h2>
            </div>
            <div class="block" id="profile_group_div">
                    <div class="content editable-widget" id="currencyMappingData" style="display: block">
                        <div class="top-box">
                            <form action="#" class="add-search-form advanced" id="searchCurrencyForm" onsubmit="return false;" >
                                <fieldset>
                                    <input type="text" value="Search Currency" name="searchTextDisp" id="searchBox1" onclick='$("#searchBox1").hide();$("#searchbox2").show();$("#searchbox2").focus();jcf.customForms.replaceAll()'  />
                                    <input type="text" value="" name="searchText" id="searchbox2" style="display: none" />
                                    <input type="button" id="searchBtn" value="Submit" onclick="$('#searchTerm').val($('#searchbox2').val());getCurrencyMappingsPage(1,$('#searchTerm').val(),'freshSearch');"/>
                                    <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchbox2').val('');$('#searchTerm').val('');$('#searchbox2').hide();$('#searchBox1').show(); $('#searchBtn').click();" />
                                </fieldset>
                            </form>
                        </div>
                        <div id="currency_mappings_section">
                        	<jsp:include page="currency_mapping_table_fragment.jsp" />
                        </div>
                    </div>

                    <div id="currency-info-section2" >
                        <table>
                            <tr>
                                <td>
                                    <h4 id="currency_message"><c:out value="${pgMessage}"/></h4>
                                </td>
                                <td >
                    	<div id="currency-info-section2-loading">
                        	<img src="res/images/loading.gif" alt="" />
                        </div>
                                </td>
                            </tr>
                        </table>
                    </div>

            </div>
        </div>

    </div>