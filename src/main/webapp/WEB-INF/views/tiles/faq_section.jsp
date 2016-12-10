<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h3><spring:message code="com.adminui.faq_section.FAQs" text="default text" /></h3>
<c:if test='${not empty companySettings.faq}'>
    <div class="alt-area1">
        <div class="row" id="faqDiv" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;cursor: default;">${companySettings.faq}</div>
    </div>
</c:if>
<c:if test='${empty companySettings.faq}'>
    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.faq_section.forFAQ" text="default text" /></em>
        <ul>
            <li><a onclick="openFaqLightBox(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.faq_section.AddFAQ" text="default text" /></em></span></a></li>
        </ul>
    </div>
</c:if>
<c:if test='${not empty companySettings.faq}'>
    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.faq_section.forEditFAQ" text="default text" /></em>
        <ul>
            <li><a onclick="openFaqLightBox(document.getElementById('faqDiv')); return false;" class="ico-edit"><span><em><spring:message code="com.adminui.faq_section.EditFAQ" text="default text" /></em></span></a></li>
        </ul>
    </div>
</c:if>