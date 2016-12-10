<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.catalog.model.CatalogSummary,
         com.vroozi.customerui.profile.model.Profile,
         com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.acl.model.Permission"%>
<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript" src="res/js/users.js"></script>
<input type="hidden" class="normal" value="${param['userType']}" />
<input type="hidden" class="useredit" value="${param['userId']}" />
<input type="hidden" id="shopperViewUrl" value="${shopperViewUrl}" />

		<div id="main">
			<div class="section">
				<c:if test="${supplierID == null || supplierID.length() == 0}">
					<ul class="breadcrumbs">
						<li><a href="vroozi"><spring:message code="com.adminui.user_edit.Vroozi" text="default text" /></a></li>
						<%--<li><a href="catalog">Content Manager</a></li> --%>
						<li><a href="user_management"><spring:message code="com.adminui.user_edit.UserManagement" text="default text" /></a></li>
						
						<c:if test="${empty param['userId']}">
							<li><spring:message code="com.adminui.user_edit.AddUser" text="default text" /></li>
						</c:if>
						<c:if test="${not empty param['userId']}">
							<li><spring:message code="com.adminui.user_edit.EditUser" text="default text" /></li>
						</c:if>
					</ul>
				</c:if>
				<c:if test="${supplierID != null && supplierID.length()>0}">
					<ul class="breadcrumbs">
						<li><a href="vroozi"><spring:message code="com.adminui.user_edit.Vroozi" text="default text" /></a></li>
						<%--<li><a href="catalog">Content Manager</a></li> --%>
            			<li><a href='<c:url value="/suppliers" />'> <spring:message code="com.adminui.user_edit.SupplierManagement" text="default text" /></a></li>
            			<li><a href='<c:url value="/supplierDetail?unitId=${unitID}&supplierId=${supplierID}" />'> <spring:message code="com.adminui.user_edit.EditSupplier" text="default text" /></a></li>
						<c:if test="${empty param['userId']}">
							<li><spring:message code="com.adminui.user_edit.AddUser" text="default text" /></li>
						</c:if>
						<c:if test="${not empty param['userId']}">
							<li><spring:message code="com.adminui.user_edit.EditUser" text="default text" /></li>
						</c:if>
					</ul>
				</c:if>
				
				<div class="video-btn-holder">
					<!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
					<a href="#" class="btn"><span><em>TUTORIAL</em></span></a-->
				</div>
			</div>
			<div class="main-holder">
				<div id="content">
					
					<c:if test="${empty param['userId']}">
						<h1><spring:message code="com.adminui.user_edit.AddUser" text="default text" /></h1>
					</c:if>
					<c:if test="${not empty param['userId']}">
						<h1>Edit User</h1>
					</c:if>
					<form class="settings-form" style="padding: 0px;" action="createUser" id="createUserForm" method="post">
                        <input type="hidden" name="userId" value="${param['userId']}" />
                        <div class="content-block toggle-block active" id="create-user">
                            <div class="headline">
                                <h2><a href="#" class="open-close"><spring:message code="com.adminui.user_edit.USERSETTINGS" text="default text" /></a></h2>
                            </div>
                            <div class="block">
                                <div class="content">

                                        <div class="text-fields">
                                            <c:if test="${param['userType'] eq 'normal'}">
                                                <div class="row">
                                                    <label for="s-username" id="usernameLabel" class="username-label"><span class="required">*</span><spring:message code="com.adminui.user_edit.Email" text="default text" /></label>
                                                    <div class="area-col">
                                                        <input id="s-username" class="default text" type="text" value="${user.username}" name="username" />
                                                        <label id="username_error" class="error-msg" style="color: red;"><spring:message code="com.adminui.user_edit.alreadyInUse" text="default text" /></label>
                                                        <%--<span class="error-msg">You must enter a Valid E-mail</span> --%>
                                                    </div>
                                                </div>
                                                <div class="row" id="firstNameDiv" <c:if test="${param['userType'] ne 'normal'}">style="display: none;"</c:if> >
                                                    <label for="s-name"><span class="required">*</span><spring:message code="com.adminui.user_edit.FirstName" text="default text" /></label>
                                                    <div class="area-col">
                                                        <input id="s-name" class="default text" type="text" value="${user.firstName}" name="firstName"/>
                                                        <%--<span class="error-msg">You must enter a Valid First Name</span> --%>
                                                    </div>
                                                </div>
                                                <div class="row" id="lastNameDiv" <c:if test="${param['userType'] ne 'normal'}">style="display: none;"</c:if> >
                                                    <label for="s-last-name"><span class="required">*</span><spring:message code="com.adminui.user_edit.LastName" text="default text" /></label>
                                                    <div class="area-col">
                                                        <input id="s-last-name" class="default text" type="text" value="${user.lastName}" name="lastName"/>
                                                        <%--<span class="error-msg">You must enter a Valid Last Name</span> --%>
                                                    </div>
                                                </div>
                                                <div class="row" id="contentViewGroupDiv" >
                                                    <label for="c-group"><span class="required">*</span><spring:message code="com.adminui.user_edit.ContentViewGroup" text="default text" /></label>
                                                    <div class="area-col">
                                                        <select class="c_group_select" name="contentViewGroupToken" id="contentViewGroupToken">
                                                            <option value=""></option>
	                                                        <c:forEach var="cgroup" items="${contentViewGroupList}">
	                                                            <option <c:if test="${user.contentViewGroupToken eq cgroup.token}"> selected="selected"</c:if>
	                                                                    value="${cgroup.token}">
	                                                                    ${cgroup.groupName}</option>
	                                                        </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="row" id="shopperLoginUrlDiv" >
                                                    <label for="s-view-url"><span class="required">*</span><spring:message code="com.adminui.user_edit.ShopperViewURL" text="default text" /></label>
                                                    <div class="area-col">
                                                        <input id="generateShopperViewUrlBtn" type="button" value="Generate">
                                                        <input id="s-view-url" class="default text" type="text" value="${user.shopperViewURL}" name="shopperViewURL" readonly="readonly"/>
                                                        
                                                    </div>
                                                </div>
                                                <div class="row" id="resetPassDiv" >
                                                    <label for="s-password"><spring:message code="com.adminui.user_edit.Password" text="default text" /></label>
                                                    <div class="area-col">
                                                        <a class="link" href="#" onclick="resetUserPassword('${param['userId']}');"><spring:message code="com.adminui.user_edit.ResetPassword" text="default text" /></a>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${param['userType'] eq 'system'}">
                                                <div class="row">
                                                    <label for="s-mail"><span class="required">*</span><spring:message code="com.adminui.user_edit.Username" text="default text" /></label>
                                                    <div class="area-col">
                                                        <input id="s-mail" class="default text"  type="text" value="${user.username}" disabled="disabled" name="username" />
                                                        <label id="username_error" class="error-msg" style="color: red;"><spring:message code="com.adminui.user_edit.Emailalreadyinuse" text="default text" /></label>
                                                        <%--<span class="error-msg">You must enter a Valid E-mail</span> --%>
                                                    </div>
                                                </div>
                                                <c:if test="${isShopper}">
	                                                <div class="row">
	                                                    <label for="s-mail"><span class="required">*</span><spring:message code="com.adminui.user_edit.LastUsedContentGroup" text="default text" /></label>
	                                                    <div class="area-col">
	                                                        <input id="s-mail" class="default text" type="text" value="${groupToken}" name="username" disabled="disabled" />
	                                                    </div>
	                                                </div>
                                                </c:if>
                                                <c:if test="${!isShopper}">
	                                                <div class="row" >
	                                                    <label for="s-password-fld"><span class="required">*</span><spring:message code="com.adminui.user_edit.Password" text="default text" /></label>
	                                                    <div class="area-col">
	                                                        <input id="s-password-fld" class="default" type="password" name="password" value="${user.password}"/>
	                                                        <label id="s_password_err" class="error-msg"><spring:message code="com.adminui.user_edit.EnteraPassword" text="default text" /></label>
	                                                    </div>
	                                                </div>
	                                                <div class="row"  >
	                                                    <label for="s-repassword-fld"><span class="required">*</span><spring:message code="com.adminui.user_edit.ReenterPassword" text="default text" /></label>
	                                                    <div class="area-col">
	                                                        <input id="s-repassword-fld" class="default" type="password" name="passwordCofirm" value="${user.password}"/>
	                                                        <label id="s_repassword_err" class="error-msg"><spring:message code="com.adminui.user_edit.ReenterPassword" text="default text" /></label>
	                                                    </div>
	                                                </div>
                                                </c:if>
                                            </c:if>
                                        </div>
                                        
                                        
                                        
                                    	
	                                        <div class="check-fields">
	                                            <strong class="label"><span class="required">*</span><spring:message code="com.adminui.user_edit.Role" text="default text" /></strong>
	                                            <c:if test="${!isSupplier}">
	                                            <c:set var="containsMasterAdminRole" value="false" />
	                                            <c:set var="containsAdminRole" value="false" />
	                                            <c:set var="containsApprover" value="false" />
	                                            <c:set var="containsBuyer" value="false" />
	                                            <c:set var="containsSupplier" value="false" />
	                                            <c:set var="containsShopperViewOnly" value="false" />
	                                            <c:set var="containsWebSearchUser" value="false" />
	                                            <c:set var="containsSystemUser" value="false" />
	                                            <c:forEach var="role" items="${user.roles}">
	                                              <c:if test="${role eq 'MASTER_ADMINISTRATOR'}">
	                                                <c:set var="containsMasterAdminRole" value="true" />
	                                              </c:if>
	                                              <c:if test="${role eq 'ADMINISTRATOR'}">
	                                                <c:set var="containsAdminRole" value="true" />
	                                              </c:if>
	                                              <c:if test="${role eq 'APPROVER'}">
	                                                <c:set var="containsApprover" value="true" />
	                                              </c:if>
	                                              <c:if test="${role eq 'BUYER'}">
	                                                <c:set var="containsBuyer" value="true" />
	                                              </c:if>
	                                              <c:if test="${role eq 'SUPPLIER'}">
	                                                <c:set var="containsSupplier" value="true" />
	                                              </c:if>
	                                              <c:if test="${role eq 'SHOPPER_VIEW_ONLY'}">
													<c:set var="containsShopperViewOnly" value="true" />
												  </c:if>
	                                              <c:if test="${role eq 'WEB_SEARCH_USER'}">
	                                                <c:set var="containsWebSearchUser" value="true" />
	                                              </c:if>
	                                              <c:if test="${role eq 'SHOPPER'}">
	                                                <c:set var="containsSystemUser" value="true" />
	                                              </c:if>
	                                            </c:forEach>
	                                            <input id="masterCheck" type="checkbox" style="display: none;" value="5" <c:if test="${containsMasterAdminRole}"> checked="checked" </c:if> />
	                                            <input id="adminCheck" type="checkbox" style="display: none;" value="5" <c:if test="${containsAdminRole}"> checked="checked" </c:if> />
	                                            <div class="area-col role" <c:if test="${param['userType'] ne 'normal'}">style="display: none;"</c:if>>
	                                                <c:if test="<%=aclManager.allow(request, Permission.CRETAE_MASTER_ADMIN)%>">
	                                                    <div class="item">
	                                                        <input id="cf-1" class="master" type="checkbox" name="roles" value="MASTER_ADMINISTRATOR" <c:if test="${containsMasterAdminRole}"> checked="checked" </c:if> <c:if test="${!fn:contains(userRoles, Role.MASTER_ADMINISTRATOR)}">disabled="disabled"</c:if> />
	                                                        <label for="cf-1"><spring:message code="com.adminui.user_edit.MasterAdministrator" text="default text" /></label>
	                                                    </div>
	                                                </c:if>
	                                                <c:if test="<%=aclManager.allow(request, Permission.CREATE_ADMIN)%>">
	                                                    <div class="item">
	                                                        <input id="cf-2" class="administrator" type="checkbox" name="roles" value="ADMINISTRATOR" <c:if test="${containsAdminRole}"> checked="checked" </c:if>/>
	                                                        <label for="cf-2"><spring:message code="com.adminui.user_edit.Administrator" text="default text" /></label>
	                                                    </div>
	                                                </c:if>
	                                                <div class="item">
	                                                    <input id="cf-3" class="approver" type="checkbox" name="roles" value="APPROVER" <c:if test="${containsApprover}"> checked="checked" </c:if> />
	                                                    <label for="cf-3"><spring:message code="com.adminui.user_edit.Approver" text="default text" /></label>
	                                                </div>
	                                                <div class="item">
	                                                    <input id="cf-4" type="checkbox" name="roles" value="BUYER" <c:if test="${containsBuyer}"> checked="checked" </c:if> />
	                                                    <label for="cf-4"><spring:message code="com.adminui.user_edit.Buyer" text="default text" /></label>
	                                                </div>
	                                                
	                                                <%--<div class="item">
	                                                    <input id="cf-5" type="checkbox" name="roles" value="4" <c:if test="${containsSupplier}"> checked="checked" </c:if> disabled="disabled" />
	                                                    <label for="cf-5">Supplier</label>
	                                                </div> --%>
                                                    
                                                    <div class="item">
                                                        <input id="cf-6" class="shopper_view_only" type="checkbox" name="roles" value="SHOPPER_VIEW_ONLY" <c:if test="${containsShopperViewOnly}"> checked="checked" </c:if> />
                                                        <label for="cf-6"><spring:message code="com.adminui.user_edit.ShopperViewOnly" text="default text" /></label>
                                                    </div>

	                                            </div>
	                                            
	                                            
	                                            <div class="area-col role" <c:if test="${param['userType'] ne 'system'}">style="display: none;"</c:if> >
	                                                <div class="item">
	                                                    <input id="cf-2-1" class="sysuser" type="checkbox" name="roles" value="SHOPPER" <c:if test="${containsSystemUsers || isShopper}"> checked="checked" </c:if> disabled="disabled"/>
	                                                    <label for="cf-2-1"><spring:message code="com.adminui.user_edit.SystemShopper" text="default text" /></label>
	                                                    <c:if test="${containsSystemUsers || isShopper}"> 
	                                                   		 <input id="roleShopper" type="hidden" name="roles" value="SHOPPER" />
	                                                    </c:if> 
	                                                </div>
	                                            </div>
	                                            </c:if>
		                                        <c:if test="${isSupplier}">
		                                        		<div class="area-col role">
		                                        			<div class="item">
			                                                    <input id="cf-4" type="checkbox" name="roles" value="SUPPLIER_ADMIN" <c:if test="${isSupplier}"> checked="checked" </c:if> />
			                                                    <label for="cf-4"><spring:message code="com.adminui.user_edit.SupplierAdmin" text="default text" /></label>
			                                                </div>	
		                                        		</div>
		                                        </c:if>	                                            
	                                        </div>
                                        

                                        
                                        <h3><spring:message code="com.adminui.user_edit.DefaultPreferences" text="default text" /></h3>
                                        <div class="preferences-fields">
                                            <div class="row">
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.user_edit.Language" text="default text" /></label>
                                                    <select name="language" id="languagesList" >
                                                        <c:forEach var="language" items="${languagesList}">
                                                            <option id="${language.languageId}" <c:if test="${user.language eq language.languageValue}"> selected="selected"</c:if>
                                                             value="${language.languageValue}">${language.languageName}</option>
                                                        </c:forEach>
                                                        <%--<option id="1" value="en">English</option>--%>
                                                    </select>
                                                </div>
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.user_edit.DecimalNotation" text="default text" /></label>
                                                    <select name="decimalNotation" id="decimalNotationList">
                                                        <c:forEach var="decimalNotation" items="${decimalNotationList}">
                                                            <option id="${decimalNotation.decimalId}"
                                                                <c:if test="${user.decimalNotation eq decimalNotation.decimalId}"> selected="selected"</c:if>
                                                                    value="${decimalNotation.decimalId}">
                                                                    ${decimalNotation.decimalNotationName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.user_edit.TimeZone" text="default text" /></label>
                                                    <select name="timeZone" id="timeFormatList" style="w">
                                                        <c:forEach var="timeFormat" items="${timeFormatList}">
                                                            <option id="${timeFormat.timeZoneId}"
                                                                    <c:if test="${user.timeZone eq timeFormat.timeZoneId}"> selected="selected"</c:if>
                                                                    value="${timeFormat.timeZoneId}">${timeFormat.timeZoneName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.user_edit.DateFormat" text="default text" /></label>
                                                    <select name="dateFormat" id="dateFormatList">
                                                        <c:forEach var="dateFormat" items="${dateFormatList}">
                                                            <option id="${dateFormat.dateId}"
                                                                    <c:if test="${user.dateFormat eq dateFormat.dateId}"> selected="selected"</c:if>
                                                                    value="${dateFormat.dateId}">${dateFormat.dataFormat}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="btns-holder">
                                            <a class="btn-cancel" href="user_management?userType=${param['userType']}"><spring:message code="com.adminui.user_edit.Cancel" text="default text" /></a>
                                                <c:if test="${not empty param['userId']}">
                                                    <c:if test='<%=aclManager.allowEditUser(request, new Permission[]{Permission.EDIT_MASTER_ADMIN,Permission.EDIT_SUPPLIER,Permission.EDIT_ADMIN,Permission.EDIT_APPROVIER, Permission.EDIT_BUYER, Permission.EDIT_SEARCH_USER, Permission.EDIT_SHOPPER, Permission.EDIT_SHOPPER_VIEW_ONLY}, (User)request.getAttribute("user"))%>'>
                                                        <input type="submit" value="Save"/>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${empty param['userId']}">
                                                    <input type="submit" value="Save"/>
                                                </c:if>
                                            <!-- <a class="btn-create close"  onclick="createUser();"><span>Save</span></a>  -->
                                            <p><span class="required">* </span><spring:message code="com.adminui.user_edit.RequiredField" text="default text" /></p>
                                        </div>

                                </div>
                            </div>
                        </div>
                        <div class="add-slide-blocks">
                            <div class="toggle-block active" id="assign-profiles">
                                <div class="title">
                                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.user_edit.ContentViews" text="default text" /></a></h2>
                                </div>
                                <div class="block">
                                    <div class="content editable-widget">
                                        <div class="profiles-box user-all">
                                            <div class="btn-holder">
                                                <a href="javascript:void(0);" class="btn-add-profile" onclick="showAddProfiles('${param['userId']}')"><span><em class="checkboxReset"><spring:message code="com.adminui.user_edit.ADDCONTENTVIEW" text="default text" /></em></span></a>
                                                <c:if test="<%=aclManager.allow(request, Permission.CREATE_CONTENT_VIEW)%>">
                                                   <a href="#create-profile" class="btn-create-profile open-popup"><span><em><spring:message code="com.adminui.user_edit.CreateContentView" text="default text" /></em></span></a>
                                                </c:if>
                                            </div>
                                            <div id="assignUserProfilesTable">
                                                <jsp:include page="user_edit_profiles.jsp" />
                                            </div>
                                            <div class="function">
                                                <ul>
                                                    <li><a href="javascript:void(0)" class="ico-remove btn-remove-profile" onclick="deleteUserProfilesConfirm();"><span><em><spring:message code="com.adminui.user_edit.REMOVE" text="default text" /></em></span></a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
					</form>
				</div>
				<div id="sidebar">
					<ul class="sub-nav">
						<c:if test="${supplierID != null && supplierID.length()>0}">
							<li><a href="supplierDetail?unitId=${unitID}&supplierId=${supplierID}" class="ico-back"><span><spring:message code="com.adminui.user_edit.BACK" text="default text" /></span></a></li>
						</c:if>
						<c:if test="${supplierID == null || supplierID.length() == 0}">
							<li><a href="user_management?userType=${param['userType']}" class="ico-back"><span><spring:message code="com.adminui.user_edit.BACK" text="default text" /></span></a></li>
						</c:if>
						<%-- <li <c:if test="${!fn:contains(userRoles, 5) && !fn:contains(userRoles, 1)}">style="display: none;"</c:if> ><a href="#create-user" class="ico-user alt-opener"><span>CREATE USER</span></a></li> --%>
						<li <c:if test="${!fn:contains(userRoles, Role.MASTER_ADMINISTRATOR) && !fn:contains(userRoles, Role.ADMINISTRATOR)}">style="display: none;"</c:if> ><a href="#assign-profiles" class="ico-assign-profiles alt-opener" id="assignCVL" style="white-space: nowrap"><span><spring:message code="com.adminui.user_edit.AssignContentViews" text="default text" /></span></a></li>
					</ul>
				</div>
			</div>
		</div>

<script type="text/javascript">
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
</script>
