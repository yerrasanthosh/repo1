<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="content-block toggle-block active" id="summary-section1">
        <div class="headline">
            <h2><a href="#" class="open-close"><spring:message code="com.adminui.profile_group_mapping.ContentAccessMapping" text="default text" /></a></h2>
        </div>
        <div class="block">
            <div class="content reorder-widget editable-widget">

                <br />

                <form action="uploadProfileGroup" id="profileGroupForm" class="form-create" method="post" enctype="multipart/form-data">
                    <fieldset>
                        <div class="area file-area">
                            <div class="row" style="width:100%;font-weight:bold" id="theCatalogFileId">
                                <label for="profileGroupFile"><spring:message code="com.adminui.profile_group_mapping.UploadMappingFile" text="default text" /> </label>
								<div class="area-col" id="profileGroupFileDiv">
									<input type="file" style="cursor: pointer;" name="profileGroupFile" id="profileGroupFile"/>
								</div>
                            </div>
                            <br />
                            <div class="row" style="width:100%;font-weight:bold;" id="downloadMapping">
                                <br/>
                                <label for="pgDownloadErrorReportLink" id="pgLblDownloadErrorReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;${pgErrorReportStyle}"><spring:message code="com.adminui.profile_group_mapping.errorUpload" text="default text" /></label><a href="viewmappingerrors?pgFileHandle=${pgFileHandle}" target="_blank" id="pgDownloadErrorReportLink" style="color:#0000FF;${pgErrorReportStyle}"><spring:message code="com.adminui.profile_group_mapping.Downloaderrorreport" text="default text" /></a>
                                <br/>
                                <label for="pgDownloadProcessedReportLink" id="pgLblDownloadProcessedReportLink" style="padding-top:0px;padding-left: 200px;color:#FF0000;width: 180px;"><spring:message code="com.adminui.profile_group_mapping.ProcessedFile" text="default text" /></label><a href="viewprocessedmapping.xlsx" target="_blank" id="pgDownloadProcessedReportLink" style="color:#0000FF;${pgProcessedReportStyle}"><spring:message code="com.adminui.profile_group_mapping.Downloadprocesseddata" text="default text" /></a>


                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
</div>

    <div class="add-slide-blocks">
        <input type="hidden" class="normal" value="" id="profileGroupSearchTerm" />
        <div class="toggle-block active" id="pg-info-section1">
            <div class="title">
                <h2><a href="#" class="open-close"><spring:message code="com.adminui.profile_group_mapping.ContentAccess" text="default text" /></a></h2>
            </div>
            <div class="block" id="profile_group_div">
                    <div class="content editable-widget" id="profileGroupMappingData" style="display: block">
                        <div class="top-box">
                            <form action="#" class="add-search-form advanced" id="searchProfileGroupForm" onsubmit="return false;" >
                                <fieldset>
                                    <input type="text" value="Search Content Access" name="searchTextDisp"  id="pgSearchTextDisp" onclick='$("#pgSearchTextDisp").hide();$("#pgSearchText").show();$("#pgSearchText").focus();jcf.customForms.replaceAll()' onblur='$("#pgSearchText").hide();$("pgSearchTextDisp").show();$("#pgSearchTextDisp").focus();jcf.customForms.replaceAll()'/>
                                    <input type="text" value="" name="searchText" id="pgSearchText"  style="display: none"/>
                                    <input type="button" id="subBtn3" value="Submit" onclick="$('#profileGroupSearchTerm').val($('#pgSearchText').val());getProfileGroupMappingsPage(1,$('#profileGroupSearchTerm').val(),'freshSearch');"/>
                                    <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#pgSearchText').val('');$('#pgSearchText').hide();$('#pgSearchTextDisp').show(); $('#subBtn3').click();" />
                                </fieldset>
                            </form>
                        </div>
                        <div id="profile_group_mappings_section">
                        	<jsp:include page="profilegroup_mapping_table_fragment.jsp" />
                        </div>
                    </div>

                    <div id="pg-info-section2" style="display: none">
                        <table>
                            <tr>
                                <td>
                                    <h4 id="pg_message"><c:out value="${pgMessage}"/></h4>
                                </td>
                                <td >
                    	<div id="pg-info-section2-loading">
                        	<img src="res/images/loading.gif" alt="" />
                        </div>
                                </td>
                            </tr>
                        </table>
                    </div>

            </div>
        </div>

    </div>