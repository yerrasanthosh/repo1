<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:forEach var="announcement" items="${announcements}">
    <a id="${announcement.id}" class="alt-close" onclick="deleteAnnouncement(this);"><spring:message code="com.adminui.announcements_section.close" text="default text" /></a>
	<div class="row" id="${announcement.id}" onclick="openAnnouncementDiv('${announcement.id}');" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;">
        <span>${announcement.announcements}</span>
        <c:if test="${not empty announcement.mailingList}">
	        <span style="margin-left: 30px; font: bold 12px/15px Helvetica,Arial,sans-serif;" ><spring:message code="com.adminui.announcements_section.isAssignedTo" text="default text" /></span>
	        <ul style="margin-top: 5px; line-height: 1;">
	            <c:forEach var="cgroup" items="${announcement.mailingList}">
	                <li>${shoppers[cgroup]}</li>
	            </c:forEach>
	        </ul>
        </c:if>
    </div>
	
	<input type="hidden" id="slc-${announcement.id}" value="${announcement.mailingList}"/>
    <input type="hidden" id="cat-${announcement.id}" value="${announcement.type}"/>
    <textarea style="display: none;" id="cgval-${announcement.id}">${announcement.announcements}</textarea>
</c:forEach>