<%@page import="com.vroozi.customerui.profile.model.ProfileProxy"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<thead>
	<tr>
		<th class="td-select">
			<input type="checkbox" class="check-allbox"
				name="check-all4" id="check4-1"
				onclick="if(this.checked)checkAllProfilesItems(true);else checkAllProfilesItems(false)" />
			<label for="check4-1"></label></th>
		<th class="a-left"><spring:message code="com.adminui.details_profiles_section.ProfileName" text="default text" /></th>
	</tr>
</thead>
<tbody>
	<%	int profileCounter = 0;	%>
	<c:forEach var="profile" items="${profilesForCompany}"
		varStatus="cntr1">
		<c:if test="${profile.attached}">
			<tr id="profilediv-${profile.profileId}">
				<td class="td-select">
					<input type="checkbox"
						class="target-chbox" name="profileIds"
						id="profileid-${profile.profileId}" value="${profile.profileId}"
						checked /> 
					<label for="profileid-${profile.profileId}"></label>
				</td>
				<td class="a-left td-username">
					<div class="field">
						<span>${profile.profileName}</span> 
						<input type="text" value="${profile.profileName}" />

					</div>
				</td>
			</tr>
			<%	++profileCounter; %>
		</c:if>
	</c:forEach>
</tbody>
<tfoot>
	<tr>
		<td colspan="7"><spring:message code="com.adminui.details_profiles_section.TotalRecords" text="default text" /><%=profileCounter%></td>
	</tr>
</tfoot>