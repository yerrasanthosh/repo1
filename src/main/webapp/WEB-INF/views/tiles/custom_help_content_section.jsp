<%@ page contentType="text/html;charset=UTF-8" language="java"
	autoFlush="true" session="true"
	import="com.vroozi.customerui.acl.model.Permission"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="aclManager" scope="request"
	class="com.vroozi.customerui.acl.DisplayAccessControl" />

<c:if test='${not empty companySettings.helpTabs}'>
	<c:forEach var="customHelpContent" items="${companySettings.helpTabs}">
		<div class="content" id="custom-help-content-${customHelpContent.id}"
			style="border-bottom: 1px solid #D0D0D0;">
			<h3 id="help-title-${customHelpContent.id}">${customHelpContent.title}</h3>

			<div class="alt-area1">
				<div class="row" id="help-content-${customHelpContent.id}"
					style="padding: 0; line-height: 2; border-bottom: 1px solid #D0D0D0; cursor: default;">${customHelpContent.content}</div>
			</div>

			<div class="function btn-holder"
				style="margin: 0; padding-top: 10px;">
				<ul>
					<c:if
						test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
						<li><a
							onclick="removeHelpTabConfirm('${customHelpContent.id}'); return false;"
							class="ico-delete"><span><em><spring:message 
											code="com.adminui.common_overlay.DELETE"
											text="Delete" /></em></span></a></li>
						<li><a
							onclick="openCustomHelpContentBox('${customHelpContent.id}'); return false;"
							class="ico-edit"><span><em><spring:message
											code="com.adminui.content_settings.EditTab"
											text="Edit Tab" /></em></span></a></li>
					</c:if>
				</ul>
			</div>

		</div>
	</c:forEach>
</c:if>
<div class="function btn-holder"
	style="margin: 0 0 5px 10px; padding-top: 10px;">
	<c:if
		test="<%=aclManager.allow(request, Permission.EDIT_COMPANY_SETTING)%>">
		<a onclick="openCustomHelpContentBox(); return false;"
			class="ico-add-tab"><span><em><spring:message
							code="com.adminui.content_settings.AddTab" text="default text" /></em></span></a>
	</c:if>
</div>