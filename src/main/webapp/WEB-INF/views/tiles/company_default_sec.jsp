<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row"></div>
<div class="row"></div>
<div class="row"></div>

<div>
    <input name="id" id="companySetId" type="hidden" value='<c:out value="${companySettings.id}"/>' />
    <input name="noCompanyIcon" id="noCompanyIconHidden" type="hidden" value='<c:out value="${companySettings.noCompanyIcon}"/>' />
    <input name="companyIcon" id="companyIconHidden" type="hidden" value='<c:out value="${companySettings.companyIcon}"/>' />
    <div class="alt-row">
        <label style="width: 150px;" class="alt-label"><spring:message code="com.adminui.company_default_sec.UploadCompanyLogo" text="default text" /></label>
        <div style="float: left;"><img id="defCompIcon" style="<c:if test='${empty companySettings.companyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.companyIcon}'>display:none;</c:if> padding-right: 20px;" src="res/images/logo.png" width="100" height="39" alt="image description" /></div>
        <div id="browseDiv" style="<c:if test='${empty companySettings.companyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.companyIcon}'>display:none;</c:if>" class="popup-holder">
            <a href="#" class="btn-add-open open" style="z-index: 0"><spring:message code="com.adminui.company_default_sec.add" text="default text" /></a>
            <div class="popup">
                <div class="popup-frame">
                    <div class="popup-inner">
                        <div class="top">
                            <a href="#" class="alt-close"><spring:message code="com.adminui.company_default_sec.Close" text="default text" /></a>
                            <h3><spring:message code="com.adminui.company_default_sec.UploadFile" text="default text" /></h3>
                        </div>
                        <div class="row">
                            <input type="file" id="companyImage" onchange="setFileName();" name="companyImage" value="${companySettingsUtility.companyIcon}" />
                        </div>
                        <div class="row" style="display:none;">
                            <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                            <a href="#" class="btn-upload"><spring:message code="com.adminui.company_default_sec.UPload" text="default text" /></a>
                            <div class="txt">
                                <span class="size"><spring:message code="com.adminui.company_default_sec.SIZE" text="default text" /></span>
                                <p>icon_photo_upload_16px.gif</p>
                            </div>
                        </div>
                        <div class="row" style="display:none;">
                            <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                            <div class="txt">
                                <div class="line-box"><span class="line" style="width:70%"></span></div>
                                <a href="#" class="btn-cancel"><spring:message code="com.adminui.company_default_sec.Cancel" text="default text" /></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <em id="companyImageEM" class="text"><spring:message code="com.adminui.company_default_sec.companyImageEM" text="default text" /></em>
        </div>
        <div id="iconDiv" class="upload-file" style="<c:if test='${not empty companySettings.companyIcon}'>display:block;</c:if><c:if test='${empty companySettings.companyIcon}'>display:none;</c:if>">
            <div class="upload-file-holder">
                <c:if test='${not empty companySettings.companyIcon}'>
                    <img id="fieldIcon" src="image/${companySettings.companyIcon}" alt="image description" style="padding-right: 40px; max-height: 39px; max-width: 100px;" />
                </c:if>
            </div>
            <a href="javascript:void(0)" onclick="removeIconImage(); return false;" class="btn-del"></a>
        </div>

    </div>
</div>

<div>
    <div class="alt-row">
        <label style="width: 150px;" class="alt-label"><spring:message code="com.adminui.company_default_sec.UploadNoImageLogo" text="default text" /></label>
        <div style="float: left;"><img id="defNoImg" style="<c:if test='${empty companySettings.noCompanyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.noCompanyIcon}'>display:none;</c:if> padding-right: 60px;" src="res/images/noimage.jpg" width="60" height="60" alt="image description" /></div>
        <div id="browseDiv1" style="<c:if test='${empty companySettings.noCompanyIcon}'>display:block;</c:if><c:if test='${not empty companySettings.noCompanyIcon}'>display:none;</c:if>" class="popup-holder">
            <a href="#" class="btn-add-open open" style="z-index: 0">add</a>
            <div class="popup">
                <div class="popup-frame">
                    <div class="popup-inner">
                        <div class="top">
                            <a href="#" class="alt-close"><spring:message code="com.adminui.company_default_sec.Close" text="default text" /></a>
                            <h3><spring:message code="com.adminui.company_default_sec.UploadFile" text="default text" /></h3>
                        </div>
                        <div class="row">
                            <input type="file" id="noCompanyImage" onchange="setFileNameNoImage();" name="noCompanyImage" value="${companySettingsUtility.noCompanyIcon}" />
                        </div>
                        <div class="row" style="display:none;">
                            <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                            <a href="#" class="btn-upload"><spring:message code="com.adminui.company_default_sec.UPload" text="default text" /></a>
                            <div class="txt">
                                <span class="size"><spring:message code="com.adminui.company_default_sec.SIZE" text="default text" /></span>
                                <p>icon_photo_upload_16px.gif</p>
                            </div>
                        </div>
                        <div class="row" style="display:none;">
                            <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
                            <div class="txt">
                                <div class="line-box"><span class="line" style="width:70%"></span></div>
                                <a href="#" class="btn-cancel"><spring:message code="com.adminui.company_default_sec.Cancel" text="default text" /></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <em id="NoCompanyImageEM" class="text"><spring:message code="com.adminui.company_default_sec.NoCompanyImageEM" text="default text" /></em>
        </div>
        <div id="iconDiv1" class="upload-file" style="<c:if test='${not empty companySettings.noCompanyIcon}'>display:block;</c:if><c:if test='${empty companySettings.noCompanyIcon}'>display:none;</c:if>">
            <div class="upload-file-holder">
                <c:if test='${not empty companySettings.noCompanyIcon}'>
                    <img id="fieldIcon1" src="image/${companySettings.noCompanyIcon}" style="padding-right: 40px; max-height: 39px; max-width: 100px;" alt="image description" />
                </c:if>
            </div>
            <a href="javascript:void(0)" onclick="removeIconNoImage(); return false;" class="btn-del"></a>
        </div>
        <div id="saveEM" style="float: left; text-align: left; padding-right: 250px; display: none;"><em class="text"><spring:message code="com.adminui.company_default_sec.saveEM" text="default text" /></em></div>
    </div>
</div>