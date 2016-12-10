<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h3><spring:message code="com.adminui.policy_section.Policies" text="default text" /></h3>
<c:if test='${not empty companySettings.policy}'>
    <div class="alt-area1">
        <div class="row" id="policyDiv" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;cursor: default;">${companySettings.policy}</div>
    </div>
</c:if>
<c:if test='${empty companySettings.policy}'>
    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.policy_section.addBtn" text="default text" /></em>
        <ul>
            <li><a onclick="openPolicyLightBox(); return false;" class="btn-add-profile"><span><em><spring:message code="com.adminui.policy_section.addPolicy" text="default text" /></em></span></a></li>
        </ul>
    </div>
</c:if>
<c:if test='${not empty companySettings.policy}'>
    <div class="function btn-holder" style="margin: 0;padding-top: 10px;"><em class="text"><spring:message code="com.adminui.policy_section.editPolicyBtn" text="default text" /></em>
        <ul>
            <li><a onclick="openPolicyLightBox(document.getElementById('policyDiv')); return false;" class="ico-edit"><span><em><spring:message code="com.adminui.policy_section.EditPolicy" text="default text" /></em></span></a></li>
        </ul>
    </div>
</c:if>