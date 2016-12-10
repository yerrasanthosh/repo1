<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="message" items="${messages}">
    <a id="${message.id}" class="alt-close" onclick="deleteMessage(this);">Close</a>
    <div name="detailMessage" class="row" id="${message.id}" onclick="openMessageDiv(this);" style="padding: 0;line-height: 2;border-bottom: 1px solid #D0D0D0;">${message.message}</div>
    <input type="hidden" id="msc-${message.id}" value="${message.mailingList}"/>
</c:forEach>