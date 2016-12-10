<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h3>Contact Us</h3>
<c:if test='${not empty companySettings.contactUs}'>
    <div class="alt-area1">
        <div class="row" id="contactDiv" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;cursor: default;">${companySettings.contactUs}</div>
    </div>
</c:if>
<c:if test='${empty companySettings.contactUs}'>
    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.contact_section.ContactUs" text="default text" /></em>
        <ul>
            <li><a onclick="openContactLightBox(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.contact_section.ContactInfo" text="default text" /></em></span></a></li>
        </ul>
    </div>
</c:if>
<c:if test='${not empty companySettings.contactUs}'>
    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.contact_section.ForContactUs" text="default text" /></em>
        <ul>
            <li><a onclick="openContactLightBox(document.getElementById('contactDiv')); return false;" class="ico-edit"><span><em><spring:message code="com.adminui.contact_section.EditContactInfo" text="default text" /></em></span></a></li>
        </ul>
    </div>
</c:if>