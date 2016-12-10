<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<thead>
	<tr>
		<th class="td-select"><input type="checkbox" class="check-allbox"
			name="check-all3" id="check3-1"
			onclick="if(this.checked)checkAllApproversItems(true);else checkAllApproversItems(false)" />
			<label for="check3-1"></label></th>
		<th class="a-left"><spring:message code="com.adminui.details_approvers_section.USERNAME" text="default text" /></th>
	</tr>
</thead>
<tbody>
	<%	int approverCounter = 0; %>
	<c:forEach var="approver" items="${allApprovers}" varStatus="cntr1">
		<tr>
			<td class="td-select">
                <input type="checkbox" class="target-chbox" name="approvers" id="${approver.userId}" />
                <label for="${approver.username}"></label>
            </td>
			<td class="a-left td-username">
				<div class="field">
					<span>${approver.username}</span>
                    <input type="text" value="${approver.username}" />
				</div>
			</td>
		</tr>
		<%++approverCounter; %>
	</c:forEach>
</tbody>
<tfoot>
	<tr>
		<td colspan="7"><spring:message code="com.adminui.details_approvers_section.TotalRecords" text="default text" /> <%=approverCounter%></td>
	</tr>
</tfoot>