<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.acl.model.Permission"%>
<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript" src="res/js/users.js"></script>

        <input id="adminFld" type="hidden" value="${filterUser}" />
		<div id="main">
			<input type="hidden" class="normal" value="${(empty userType)?param['userType']:userType}" />
			
			<c:forEach var="role1" items="${userRoles}">
				<input type="hidden" class="user-roles" value="${role1}" />
			</c:forEach>
		
			<div class="section">
				<ul class="breadcrumbs">
					<li><a href="vroozi"><spring:message code="com.adminui.user_management.Vroozi" text="default text" /></a></li>
            		<%--<li><a href="catalog">Content Manager</a></li> --%>
					<li><spring:message code="com.adminui.user_management.UserManagement" text="default text" /></li>
				</ul>
				<div class="video-btn-holder">
					<!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
					<a href="#" class="btn"><span><em>TUTORIAL</em></span></a-->
				</div>
			</div>
			<div class="main-holder">
				<div id="content">
					<h1><spring:message code="com.adminui.user_management.UserManagement" text="default text" /></h1>
					<div class="ui-tab-section users">
						<ul class="tabset">
							<li><a href="#tab1"><span class="checkboxReset"><spring:message code="com.adminui.user_management.USERS" text="default text" /></span></a></li>
					        <li>
                                    <a id="shopperTab" href="#tab2">
                                        <c:if test="<%=aclManager.allow(request, Permission.VIEW_ADMIN)%>">
                                            <span class="checkboxReset"><spring:message code="com.adminui.user_management.SYSTEMUSERS" text="default text" /></span>
                                        </c:if>
                                    </a>
                            </li>
						</ul>
						<div id="tab1">
							<div class="content-block toggle-block active" id="summary-section">
								<div class="headline">
									<h2><a href="#" class="open-close"><spring:message code="com.adminui.user_management.USERSummary" text="default text" /></a></h2>
								</div>
								<div class="block">
									<div class="content">
										<div class="summary-box">
											<form id="normalUserRolesForm" >
											<table class="summary-table">
												<thead>
													<tr>
														<td class="a-center sep view"><spring:message code="com.adminui.user_management.View" text="default text" /></td>
														<td><spring:message code="com.adminui.user_management.Summary" text="default text" /></td>
														<td></td>
													</tr>
												</thead>
												<tfoot>

												</tfoot>
												<tbody>
                                                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_MASTER_ADMIN)%>">
                                                        <tr>
                                                            <td class="a-center view"><input id="st0" type="checkbox" name="filterOptions" value="MASTER_ADMINISTRATOR" onclick="filterUsers('normal');" checked="checked" />
                                                                <label id="st0lbl" for="st0"></label>
                                                            </td>
                                                            <th><spring:message code="com.adminui.user_management.MasterAdministrator" text="default text" /></th>
                                                            <td class="a-right" id='outMasterAdminCount'><c:out value="${masterAdminCount}"/></td>
                                                        </tr>
                                                    </c:if>
                                                    <c:if test="<%=aclManager.allow(request, Permission.VIEW_ADMIN)%>">
                                                        <tr>
                                                            <td class="a-center view"><input id="st1" type="checkbox" name="filterOptions" value="ADMINISTRATOR" onclick="filterUsers('normal');" checked="checked" />
                                                                <label id="st1lbl" for="st1"></label>
                                                            </td>
                                                            <th><spring:message code="com.adminui.user_management.Administrator" text="default text" /></th>
                                                            <td class="a-right" id='outAdministratorCount'><c:out value="${administratorCount}"/></td>
                                                        </tr>
                                                    </c:if>
													<tr>
														<td class="a-center view"><input id="st2" type="checkbox" name="filterOptions" value="BUYER" onclick="filterUsers('normal');" checked="checked" />
                                       						<label id="st2lbl" for="st2"></label>
                                                        </td>
														<th><spring:message code="com.adminui.user_management.Buyers" text="default text" /></th>
														<td class="a-right" id='outBuyerCount'><c:out value="${buyerCount}"/></td>
													</tr>
													<tr>
														<td class="a-center view"><input id="st3" type="checkbox" name="filterOptions" value="APPROVER" onclick="filterUsers('normal');" checked="checked" />
                                       						<label id="st3lbl" for="st3"></label>
                                                        </td>
														<th><spring:message code="com.adminui.user_management.Approver" text="default text" /></th>
														<td class="a-right" id='outApproverCount'><c:out value="${approverCount}"/></td>
													</tr>
                                                    <tr>
                                                        <td class="a-center view"><input id="st4" type="checkbox" name="filterOptions" value="SHOPPER_VIEW_ONLY" onclick="filterUsers('normal');" checked="checked" />
                                                            <label id="st4lbl" for="st4"></label>
                                                        </td>
                                                        <th><spring:message code="com.adminui.user_management.ShopperViewOnly" text="default text" /></th>
                                                        <td class="a-right" id='outShopperViewOnlyCount'><c:out value="${shopperViewOnlyCount}"/></td>
                                                    </tr>
													<%--<tr>
														<td class="a-center view"><input id="st4" type="checkbox" name="filterOptions" 
                                       							value="4" ${userBean.supplier?"CHECKED":""} onclick="filterUsers('normal');" />
                                       						<label for="st4"></label></td>
														<th>Supplier:</th>
														<td class="a-right" id='outSupplierCount'><c:out value="${supplierCount}"/></td>
													</tr> --%>
												</tbody>
											</table>
											<input type="hidden" name="filterOptions" value="-1"/>
											</form>
										</div>
									</div>
								</div>
							</div>
							<div class="add-slide-blocks">
								<div class="toggle-block active" id="info-section">
									<div class="title">
										<h2><a href="#" class="open-close"><spring:message code="com.adminui.user_management.USERinformation" text="default text" /></a></h2>
									</div>
									<div class="block" id="usersTable">
										<div class="content editable-widget">
											<div class="top-box">
												<form action="#" class="add-search-form advanced" id="searchUserForm" onsubmit="return false;" >
													<fieldset>
														<input type="text" id="filterUserInput" value="Search Users" name="searchText" onchange="filterUsers('normal');"/>
														<input type="button" id="filterUserBtn" value="Submit" onclick="filterUsers('normal');"/>
                                                        <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#filterUserInput').val('Search Users'); $('#filterUserBtn').click();" />
													</fieldset>
												</form>
												<form action="#" class="form-sort" id="filterUserForm">
													<fieldset>
														<input type="hidden" name="sort" value="username">
														<input id="normalSortOrder" type="hidden" name="ascending" value="true">
														<%--<label>Status:</label>
														<select name="status" onchange="filterUsers('normal');">
						                                    <option value="1" selected="selected">All</option>
						                                    <option value="2">Active</option>
						                                    <option value="3">Inactive</option>
						                                </select>
														<label>Sort By: </label>
														<select name="sort" onchange="filterUsers('normal');">
															<option value="0" selected="selected">Select</option>
															<option value="username">E-mail Address</option>
														</select>--%>
													</fieldset>
												</form>
											</div>
											<form action="#" id="usersTableForm" class="checkboxResetForm">
												<table class="table-data">
													<thead>
														<tr>
															<th class="td-select"><input type="checkbox" class="check-allbox" name="check-all3" id="check3-1" /><label for="check3-1"></label></th>
															<th class="a-left"><a class="a-left sorting" onclick="filterUsers('normal'); toggleSort('normal');"><spring:message code="com.adminui.user_management.EmailAddress" text="default text" /></a></th>
															<%--<th>Name</th>--%>
															<th><spring:message code="com.adminui.user_management.Role" text="default text" /></th>
															<th><spring:message code="com.adminui.user_management.STATUS" text="default text" /></th>
															<th><spring:message code="com.adminui.user_management.LASTLOGIN" text="default text" /></th>
														</tr>
													</thead>
													<tbody>

                                                    	<c:forEach var="user" items="${normalUserPage.pageItems}" varStatus="cntr1">
															<tr>
																<% String roleNames = ""; String delim = ""; boolean master = false; %>
																<c:forEach var="roleId" items="${user.roles}">
																	<c:if test="${roleId eq 'WEB_SEARCH_USER'}"><% roleNames += delim + "Web Search User"; %></c:if>
																	<c:if test="${roleId eq 'ADMINISTRATOR'}"><% roleNames += delim + "Administrator"; %></c:if>
																	<c:if test="${roleId eq 'SHOPPER'}"><% roleNames += delim + "System Shopper"; %></c:if>
																	<c:if test="${roleId eq 'APPROVER'}"><% roleNames += delim + "Approver"; %></c:if>
																	<c:if test="${roleId eq 'SUPPLIER'}"><% roleNames += delim + "Supplier"; %></c:if>
																	<c:if test="${roleId eq 'SHOPPER_VIEW_ONLY'}"><% roleNames += delim + "Shopper View Only"; %></c:if>
																	<c:if test="${roleId eq 'MASTER_ADMINISTRATOR'}"><% roleNames += delim + "Master Admin"; master=true; %></c:if>
																	<c:if test="${roleId eq 'SUPPLIER_ADMIN'}"><% roleNames += delim + "Supplier Admin"; %></c:if>
																	<c:if test="${roleId eq 'BUYER'}"><% roleNames += delim + "Buyer"; %></c:if>
																	<% delim = ", "; %>
																</c:forEach>
																<c:set var="master1"> <%=master%> </c:set>  
																
																<td class="td-select"><input type="checkbox" <c:if test="${loggedInUserId==user.userId || (!fn:contains(userRoles, Role.MASTER_ADMINISTRATOR) && (!fn:contains(userRoles, Role.ADMINISTRATOR) || master1))}">disabled="disabled"</c:if> class="target-chbox" name="userIds" id="userid-${user.userId}" value="${user.userId}"/><label for="userid-${user.userId}"></label></td>
																<c:choose>
																  <c:when test="${loggedInUserId==user.userId}">
																 	 <td class="a-left td-item-name"><a href="myAccount">${user.username}</a></td>
																  </c:when>
																  <c:otherwise>
																  	<td class="a-left td-item-name"><a href="editUser?userId=${user.userId}&userType=normal">${user.username}</a></td>
																  </c:otherwise>
																</c:choose>
																
																
																<%--<td>${user.fullName}</td> --%>
																<td><%= roleNames %></td>
																<td>${user.active?'Active':'Inactive'}</td>
                                                                <td><%=ViewHelper.getFormattedDate(request, ((User)pageContext.getAttribute("user")).getLastLogin())%><br/></td>
																<td style="display: none;"> <input type="hidden" id="userprfs-${user.userId}" value="${fn:length(user.assignedProfiles)}" /> </td>
															</tr>
	                                        			</c:forEach>
													</tbody>
												</table>
												
												<div class="bottom-data">
													<div class="pager">
														<input type="hidden" name="pageNo" value="${normalUserPage.pageNumber}">
														<span>Page ${normalUserPage.pageNumber} of ${normalUserPage.pagesAvailable} </span>
														<ul>
															<li><a href="javascript:void(0)" 
																	<c:choose>
																	  <c:when test="${normalUserPage.pageNumber > 1}">class="btn-prev-active"  onclick="navigatePage('prev', 'normal')"</c:when>
																	  <c:otherwise>class="btn-prev"</c:otherwise>
																	</c:choose>></a></li>
															<li><a href="javascript:void(0)" 
																	<c:choose>
																	  <c:when test="${normalUserPage.pageNumber eq normalUserPage.pagesAvailable}">class="btn-next-inactive"</c:when>
																	  <c:otherwise>class="btn-next"  onclick="navigatePage('next', 'normal')"</c:otherwise>
																	</c:choose> ></a></li>
														</ul>
													</div>
													<strong class="pages"><spring:message code="com.adminui.user_management.TotalRecords" text="default text" /> ${normalUserPage.firstElementOnPage}-${normalUserPage.lastElementOnPage} <spring:message code="com.adminui.user_management.of" text="default text" /> ${normalUserPage.totalRecords}</strong>
												</div>
											</form>
											<div class="function">
												<ul>
													<%-- <li><a class="ico-activity" href="#"><span><em>ACTIVITY HISTORY</em></span></a></li>--%>
													<li><a class="ico-delete delete-users-normal" href="javascript:void(0)" onclick="setUserDeletionMessage('normal')"><span><em><spring:message code="com.adminui.user_management.DELETE" text="default text" /></em></span></a></li>
													<li><a class="ico-activate" href="javascript:void(0)" onclick="activateUsers('usersTableForm', 'normal', true);"><span><em><spring:message code="com.adminui.user_management.ACTIVATE" text="default text" /></em></span></a></li>
													<li><a class="ico-deactivate" href="javascript:void(0)" onclick="activateUsers('usersTableForm', 'normal', false);"><span><em><spring:message code="com.adminui.user_management.DeACTIVATE" text="default text" /></em></span></a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div id="tab2">
							<div class="content-block toggle-block active" id="summary-section">
								<div class="headline">
									<h2><a href="#" class="open-close"><spring:message code="com.adminui.user_management.SYSTEMUSERSummary" text="default text" /></a></h2>
								</div>
								<div class="block">
									<div class="content">
										<div class="summary-box">
											<form id="systemUserRolesForm" >
											<table class="summary-table">
												<thead>
													<tr>
														<td class="a-center sep view"><spring:message code="com.adminui.user_management.View" text="default text" /></td>
														<td><spring:message code="com.adminui.user_management.Summary" text="default text" /></td>
														<td></td>
													</tr>
												</thead>
												<tbody>
													<c:if test="<%=aclManager.allow(request, Permission.VIEW_WEB_SEARCH_USER)%>">												
														<tr>
															<td class="a-center view"><input id="st1-1" type="checkbox" name="filterOptions" 
	                                       							value="WEB_SEARCH_USER" onclick="filterUsers('system');" checked="checked" />
	                                       						<label for="st1-1"></label></td>
															<th><spring:message code="com.adminui.user_management.WebSearchUser" text="default text" /></th>
															<td class="a-right" id='outWebSearchUserCount'><c:out value="${webSearchUserCount}"/></td>
														</tr>
													</c:if>
													<tr>
														<td class="a-center view"><input id="st2-1" type="checkbox" name="filterOptions" 
                                       							value="SHOPPER" onclick="filterUsers('system');" checked="checked" />
                                       						<label for="st2-1"></label></td>
														<th><spring:message code="com.adminui.user_management.SystemShopper" text="default text" /></th>
														<td class="a-right" id='outSystemShopperCount'><c:out value="${systemShopperCount}"/></td>
													</tr>
												</tbody>
											</table>
											<input type="hidden" name="filterOptions" value="-1"/>
											</form>
										</div>
									</div>
								</div>
							</div>
							<div class="add-slide-blocks">
								<div class="toggle-block active" id="info-section">
									<div class="title">
										<h2><a href="#" class="open-close"><spring:message code="com.adminui.user_management.SystemUSERinformation" text="default text" /></a></h2>
									</div>
									<div class="block" id="systemUsersTable">
										<div class="content editable-widget">
											<div class="top-box">
												<form action="#" class="add-search-form advanced" id="searchSystemUserForm" onsubmit="return false;">
													<fieldset>
														<input type="text" id="filterUserInput1" value="Search Users" name="searchText" onchange="filterUsers('system');"/>
														<input type="button" id="filterUserBtn1" value="Submit" onclick="filterUsers('system');" />
                                                        <input id="resetBtn1" type="button" style="cursor: pointer;" onclick="$('#filterUserInput1').val('Search Users'); $('#filterUserBtn1').click();" />
													</fieldset>
												</form>
												<form action="#" class="form-sort" id="filterSystemUserForm">
													<fieldset>
														<input type="hidden" name="sort" value="username">
														<input id="systemSortOrder" type="hidden" name="ascending" value="true">
														<%-- <label>Status:</label>
														<select name="status" onchange="filterUsers('system');">
						                                    <option value="1" selected="selected">All</option>
						                                    <option value="2">Active</option>
						                                    <option value="3">Inactive</option>
						                                </select>
														<label>Sort By: </label>
														<select name="sort" onchange="filterUsers('system');">
															<option value="0" selected="selected">Select</option>
															<option value="username">Username</option>
														</select> --%>
													</fieldset>
												</form>
											</div>
											<form action="#" id="systemUserForm" class="checkboxResetForm">
											<table class="table-data">
												<thead>
													<tr>
														<th class="td-select"><input type="checkbox" class="check-allbox" name="check-all3" id="check3-11" /><label for="check3-11"></label></th>
														<th class="a-left"><a class="a-left sorting" onclick="filterUsers('system'); toggleSort('system');"><spring:message code="com.adminui.user_management.Username" text="default text" /></a></th>
														<%--<th>Name</th>--%>
														<th><spring:message code="com.adminui.user_management.Role" text="default text" /></th>
														<th><spring:message code="com.adminui.user_management.STATUS" text="default text" /></th>
														<th><spring:message code="com.adminui.user_management.LASTLOGIN" text="default text" /></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="user" items="${systemUserPage.pageItems}" varStatus="cntr1">
														
														<tr>
																<% String roleNames = ""; String delim = ""; boolean master = false; %>
																<c:forEach var="roleId" items="${user.roles}">
                                                                    <c:if test="${roleId eq 'WEB_SEARCH_USER'}"><% roleNames += delim + "Web Search User"; %></c:if>
                                                                    <c:if test="${roleId eq 'ADMINISTRATOR'}"><% roleNames += delim + "Administrator"; %></c:if>
                                                                    <c:if test="${roleId eq 'SHOPPER'}"><% roleNames += delim + "System Shopper"; %></c:if>
                                                                    <c:if test="${roleId eq 'APPROVER'}"><% roleNames += delim + "Approver"; %></c:if>
                                                                    <c:if test="${roleId eq 'SUPPLIER'}"><% roleNames += delim + "Supplier"; %></c:if>
                                                                    <c:if test="${roleId eq 'MASTER_ADMINISTRATOR'}"><% roleNames += delim + "Master Admin"; master=true; %></c:if>
                                                                    <c:if test="${roleId eq 'SUPPLIER_ADMIN'}"><% roleNames += delim + "Supplier Admin"; %></c:if>
                                                                    <c:if test="${roleId eq 'BUYER'}"><% roleNames += delim + "Buyer"; %></c:if>
 																	<% delim = ", "; %>
																</c:forEach>
																<c:set var="master1"> <%=master%> </c:set>  
																
																<td class="td-select"><input type="checkbox" <c:if test="${loggedInUserId==user.userId || (!fn:contains(userRoles, Role.MASTER_ADMINISTRATOR) && (!fn:contains(userRoles, Role.ADMINISTRATOR) || master1))}">disabled="disabled"</c:if> class="target-chbox" name="userIds" id="userid-${user.userId}" value="${user.userId}"/><label for="userid-${user.userId}"></label></td>
																
																<c:choose>
																  <c:when test="${loggedInUserId==user.userId}">
																 	 <td class="a-left td-item-name"><a href="myAccount">${user.username}</a></td>
																  </c:when>
																  <c:otherwise>
																  	<td class="a-left td-item-name"><a href="editUser?userId=${user.userId}&userType=system">${user.username}</a></td>
																  </c:otherwise>
																</c:choose>
																<%--<td>${user.fullName}</td>--%>
																<td><%= roleNames %></td>
																<td>${user.active?'Active':'Inactive'}</td>
                                                            <td><%=ViewHelper.getFormattedDate(request, ((User)pageContext.getAttribute("user")).getLastLogin())%><br/></td>
																<td style="display: none;"> <input type="hidden" id="userprfs-${user.userId}" value="${fn:length(user.assignedProfiles)}" /> </td>
															</tr>
															
															
                                        			</c:forEach>
												</tbody>
											</table>
											
											<div class="bottom-data">
												<div class="pager">
													<input type="hidden" name="pageNo" value="${systemUserPage.pageNumber}">
													<span>Page ${systemUserPage.pageNumber} of ${systemUserPage.pagesAvailable} </span>
													<ul>
															<li><a href="javascript:void(0)" 
																	<c:choose>
																	  <c:when test="${systemUserPage.pageNumber > 1}">class="btn-prev-active"  onclick="navigatePage('prev', 'system')"</c:when>
																	  <c:otherwise>class="btn-prev"</c:otherwise>
																	</c:choose>></a></li>
															<li><a href="javascript:void(0)" 
																	<c:choose>
																	  <c:when test="${systemUserPage.pageNumber eq systemUserPage.pagesAvailable}">class="btn-next-inactive"</c:when>
																	  <c:otherwise>class="btn-next"  onclick="navigatePage('next', 'system')"</c:otherwise>
																	</c:choose> ></a></li>												</ul>
												</div>
												<strong class="pages"><spring:message code="com.adminui.user_management.TotalRecords" text="default text" /> ${systemUserPage.firstElementOnPage}-${systemUserPage.lastElementOnPage} <spring:message code="com.adminui.user_management.of" text="default text" /> ${systemUserPage.totalRecords}</strong>
											</div>
											</form>
											<div class="function">
												<ul>
													<%--<li><a class="ico-activity" href="#"><span><em>ACTIVITY HISTORY</em></span></a></li>--%>
													<li><a class="ico-delete delete-users-system" href="javascript:void(0)" onclick="setUserDeletionMessage('system')"><span><em>DELETE</em></span></a></li>
													<%--<li><a class="ico-activate" href="javascript:void(0)" onclick="activateUsers('systemUserForm', 'system', true);"><span><em>ACTIVATE</em></span></a></li>
													<li><a class="ico-deactivate" href="javascript:void(0)" onclick="activateUsers('systemUserForm', 'system', false);"><span><em>DeACTIVATE</em></span></a></li>--%> 
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<script type="text/javascript">
                        var createUser = '<spring:message code="com.adminui.user_management.CREATEUSER" text="default text" />';
					    try{
					    	 var normal = $(':hidden.normal');

					 	    if (normal.length) {
					 	    	var index = (normal.val()=='system')?1:0;
					 	    	$('.ui-tab-section').tabs({ selected: index });
					 	    }
					 	    else {
					 	    	$('.ui-tab-section').tabs();
					 	    }
						    
					 	   $('.users.ui-tab-section').bind('tabsselect', function(event, ui) {
					 		   
					 		   if(ui.index == 1) {
					 			  $('#crtUserLbl').html('CREATE SYSTEM USER');
                                    $('#createUsrBtn').hide();
					 		   } else {
					 			  $('#crtUserLbl').html(createUser);
                                    $('#createUsrBtn').show();
					 		   }
					 		  
					 	   });
						   
					    }catch(exp){
					        //console.log(exp);
					    }
					</script>
				</div>
				<div id="sidebar">
					<ul class="sub-nav">
						<li><a href="<c:out value="vroozi"/>" class="ico-back"><span><spring:message code="com.adminui.user_management.BACK" text="default text" /></span></a></li>
	
						<li id="createUsrBtn" <c:if test="${(!fn:contains(userRoles, Role.MASTER_ADMINISTRATOR) && !fn:contains(userRoles, Role.ADMINISTRATOR))}">style="display: none;"</c:if> >
							<a href="javascript:addUser()" id="addUserLink" class="ico-user alt-opener"><span id="crtUserLbl"><spring:message code="com.adminui.user_management.CREATEUSER" text="default text" /></span></a>
							<script type="text/javascript">
                                var createUser = '<spring:message code="com.adminui.user_management.CREATEUSER" text="default text" />';
								if(($(':hidden.normal').val()=='system')) {
									$('#crtUserLbl').html('CREATE SYSTEM USER');
                                    $('#createUsrBtn').hide();
						 		   }else{
						 			  $('#crtUserLbl').html(createUser);
                                    $('#createUsrBtn').show();
						 		   }
							</script>
						</li>

					</ul>
				</div>
			</div>
		</div>

<script type="text/javascript">

function addUser() {
	var tabIndex = $('.ui-tab-section').tabs('option', 'selected');
	var userType = tabIndex==0 ? "normal": "system";
	window.location.href = "editUser?userType="+userType;
}

    function checkAllUsers(check){
        try{
            if(check){
                $("#UsersContentDiv :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#UsersContentDiv :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
    
    function toggleSort(userType) {
        if(userType == 'normal') {
        	$('#normalSortOrder').val($('#normalSortOrder').val() == 'false');        	
        } else {
        	$('#systemSortOrder').val($('#systemSortOrder').val() == 'false');
        }
    }
$(document).ready(function() {
    if($("#adminFld").val() == "masterAdmins") {
//        $("#st1").attr('checked', false);
//        $("#st1lbl").removeClass("ui-state-active");
        $("#st2").attr('checked', false);
        $("#st2lbl").removeClass("ui-state-active");
        $("#st3").attr('checked', false);
        $("#st3lbl").removeClass("ui-state-active");
        filterUsers('normal');
    } else if($("#adminFld").val() == "admins") {
        $("#st0").attr('checked', false);
        $("#st0lbl").removeClass("ui-state-active");
        $("#st2").attr('checked', false);
        $("#st2lbl").removeClass("ui-state-active");
        $("#st3").attr('checked', false);
        $("#st3lbl").removeClass("ui-state-active");
        filterUsers('normal');
    } else if($("#adminFld").val() == "buyers") {
        $("#st1").attr('checked', false);
        $("#st1lbl").removeClass("ui-state-active");
        $("#st0").attr('checked', false);
        $("#st0lbl").removeClass("ui-state-active");
        $("#st3").attr('checked', false);
        $("#st3lbl").removeClass("ui-state-active");
        filterUsers('normal');
    } else if($("#adminFld").val() == "approvers") {
        $("#st1").attr('checked', false);
        $("#st1lbl").removeClass("ui-state-active");
        $("#st2").attr('checked', false);
        $("#st2lbl").removeClass("ui-state-active");
        $("#st0").attr('checked', false);
        $("#st0lbl").removeClass("ui-state-active");
        filterUsers('normal');
    } else if($("#adminFld").val() == "shoppers") {
        $("#shopperTab").click();
    }
});




</script>
