<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:forEach var="info" items="${informations}">
    <a id="inflnk-${info.id}" class="alt-close" onclick="deleteInformation('${info.id}');"><spring:message code="com.adminui.information_section.Close" text="default text" /></a>
    <div class="row" id="${info.id}" onclick="openInformationDiv('${info.id}');" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;">
        <span>${info.information}</span>
        <c:if test="${not empty info.contentViewGroups}">
	        <span style="margin-left: 30px; font: bold 12px/15px Helvetica,Arial,sans-serif;" ><spring:message code="com.adminui.information_section.isassignedto" text="default text" /></span>
	        <ul style="margin-top: 5px; line-height: 1;">
	            <c:forEach var="cgroup" items="${info.contentViewGroups}">
	                <li>${cvgroups[cgroup]}</li>
	            </c:forEach>
	        </ul>
	    </c:if>
    </div>
    <input type="hidden" id="infoid-${info.id}" value="${info.id}"/>
    <input type="hidden" id="infocg-${info.id}" value="${info.contentViewGroups}"/>
    <textarea style="display: none;" id="infoval-${info.id}">${info.information}</textarea>
</c:forEach>
<input type="hidden" id="infoAssignedGroups" value="${infoAssignedGroups}" />