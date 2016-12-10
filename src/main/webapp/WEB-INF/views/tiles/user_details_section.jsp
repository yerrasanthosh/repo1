<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>
<%@ page import="com.vroozi.customerui.acl.model.Role" %>
<%@ page import="com.vroozi.customerui.util.ViewHelper" %>


<table class="table-data">
													<thead>
														<tr>
															<c:choose>
																<c:when test="${userType eq 'system'}">
																	<th class="td-select"><input type="checkbox" class="check-allbox" name="check-all3" id="check3-11" /><label for="check3-11"></label></th>
																	<th class="a-left"><a class="a-left sorting" onclick="filterUsers('system'); toggleSort('system');"><spring:message code="com.adminui.user_details_section.Username" text="default text" /></a> </th>
																</c:when>
																<c:otherwise>
																	<th class="td-select"><input type="checkbox" class="check-allbox" name="check-all3" id="check3-1" /><label for="check3-1"></label></th>
																	<th class="a-left"><a class="a-left sorting" onclick="filterUsers('normal'); toggleSort('normal');"><spring:message code="com.adminui.user_details_section." text="default text" /></a> </th>
																</c:otherwise>
															</c:choose>
															
															<%--<th>Name</th>--%>
															<th><spring:message code="com.adminui.user_details_section.Role" text="default text" /></th>
															<th><spring:message code="com.adminui.user_details_section.STATUS" text="default text" /></th>
															<th><spring:message code="com.adminui.user_details_section.LASTLOGIN" text="default text" /></th>
														</tr>
													</thead>
													<tbody>
		                                       			<c:forEach var="user" items="${usersPage.pageItems}" varStatus="cntr1">
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
																  <c:when test="${loggedInUserId==user.userId && !fn:contains(userRoles, Role.MASTER_ADMINISTRATOR)}">
																 	 <td class="a-left td-item-name">${user.username}</td>
																  </c:when>
																  <c:otherwise>
																  	<td class="a-left td-item-name"><a href="editUser?userId=${user.userId}&userType=${userType}">${user.username}</a></td>
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
														<input type="hidden" name="pageNo" value="${usersPage.pageNumber}">
														<span>Page ${usersPage.pageNumber} of ${usersPage.pagesAvailable} </span>
														<ul>
															<li><a href="javascript:void(0)" 
																	<c:choose>
																	  <c:when test="${usersPage.pageNumber > 1}">class="btn-prev-active"  onclick="navigatePage('prev', '${userType}')"</c:when>
																	  <c:otherwise>class="btn-prev"</c:otherwise>
																	</c:choose>></a></li>
															<li><a href="javascript:void(0)" 
																	<c:choose>
																	  <c:when test="${usersPage.pageNumber eq usersPage.pagesAvailable}">class="btn-next-inactive"</c:when>
																	  <c:otherwise>class="btn-next"  onclick="navigatePage('next', '${userType}')"</c:otherwise>
																	</c:choose> ></a></li>
															
																
														</ul>
													</div>
													<strong class="pages"><spring:message code="com.adminui.user_details_section.TotalRecords" text="default text" /> ${usersPage.firstElementOnPage}-${usersPage.lastElementOnPage} of ${usersPage.totalRecords}</strong>
													
													
											<input type="hidden" id="txtMasterAdminCount" value="${masterAdminCount}" />
											<input type="hidden" id="txtAdministratorCount" value="${administratorCount}" />
		                                    <input type="hidden" id="txtBuyerCount" value="${buyerCount}" />
		                                    <input type="hidden" id="txtApproverCount" value="${approverCount}" />
		                                    <input type="hidden" id="txtSupplierCount" value="${supplierCount}" />
		                                    <input type="hidden" id="txtShopperViewOnlyCount" value="${shopperViewOnlyCount}" />
		                                    <input type="hidden" id="txtWebSearchUserCount" value="${webSearchUserCount}" />
		                                    <input type="hidden" id="txtSystemShopperCount" value="${systemShopperCount}" />
		                                    <input type="hidden" id="txtTotalNormalCount" value="${totalNormalCount}" />
		                                    <input type="hidden" id="txtTotalSystemCount" value="${totalSystemCount}" />
													
												</div>