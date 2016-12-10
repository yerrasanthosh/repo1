<%@page import="com.vroozi.customerui.profile.model.Profile"%>
<%@page import="com.vroozi.customerui.profile.model.ProfileProxy"%>
<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="index_overlay.jsp"/>

<div class="lightbox-section">
    <div class="lightbox" id="create-user">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.users_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.users_overlay.CreateNewUser" text="default text" /></h2>
                </div>
                <form action="addUser" class="form-create" method="post" id="createUserForm">
                    <fieldset>
                        <h3><spring:message code="com.adminui.users_overlay.UserSettings" text="default text" /></h3>
                        <div class="area">
                        		<div class="row">
                                    <label class="alt-label"><spring:message code="com.adminui.users_overlay.SelectRoles" text="default text" /></label>
                                </div>
                                <div class="row">
                                    <input id="administratorRoleCheck" type="checkbox" name="roles" value="1" />
                                       		<label for="administratorRoleCheck"><spring:message code="com.adminui.users_overlay.Administrator" text="default text" /></label>
                                       		<input id="buyerRoleCheck" type="checkbox" name="roles"	value="7" />
                                       		<label for="buyerRoleCheck"><spring:message code="com.adminui.users_overlay.Buyer" text="default text" /></label>
                                       		<input id="catalogApproverRoleCheck" type="checkbox" name="roles"	value="3" />
                                       		<label for="catalogApproverRoleCheck"><spring:message code="com.adminui.users_overlay.CatalogApprover" text="default text" /></label>
                                       		<input id="masterAdministratorRoleCheck" type="checkbox" name="roles"	value="5" />
                                       		<label for="masterAdministratorRoleCheck"><spring:message code="com.adminui.users_overlay.MasterAdministrator" text="default text" /></label>
                                       		<input id="webSearchUserRoleCheck" type="checkbox" name="roles" value="0" />
                                       		<label for="webSearchUserRoleCheck"><spring:message code="com.adminui.users_overlay.WebSearchUser" text="default text" /></label>
                                       		<!--<input id="systemShopperRoleCheck" type="checkbox" name="roles"	value="4" />
                                       		<label for="systemShopperRoleCheck">System Shopper</label>
                                       		<input id="supplierRoleCheck" type="checkbox" name="roles"	value="6" />
                                       		<label for="supplierRoleCheck">Supplier</label>-->
                                       		<input id="supplierAdminRoleCheck" type="checkbox" name="roles"	value="2" />
                                       		<label for="supplierAdminRoleCheck"><spring:message code="com.adminui.users_overlay.SupplierAdmin" text="default text" /></label>
                                </div>
                                <div class="row">
                                    <label class="alt-label"><spring:message code="com.adminui.users_overlay.Company" text="default text" /></label>
	                                <select class="alt-select" name="unitId">
	                                	<option selected="selected" value="0"></option>
	                                	<option value="1"><spring:message code="com.adminui.users_overlay.Netsol" text="default text" /></option>
	                                	<option value="2"><spring:message code="com.adminui.users_overlay.Vroozi" text="default text" /></option>
	                                </select>
                                </div>
                           	<div class="row">
                                <label for="email"><span>*</span><spring:message code="com.adminui.users_overlay.Email" text="default text" /></label>
                                <input type="text" value="" id="email" class="input2" name="username"/>
                                <label for="password"><span>*</span><spring:message code="com.adminui.users_overlay.Password" text="default text" /></label>
                                <input type="password" value="" id="password" class="input2" name="password"/>
                            </div>
                            <div class="row" >
                                <label for="firstName"><span>*</span><spring:message code="com.adminui.users_overlay.FirstName" text="default text" /></label>
                                <input type="text" value="" id="firstName" class="input2" name="firstName"/>
                                <label for="lastName"><span>*</span><spring:message code="com.adminui.users_overlay.LastName" text="default text" /></label>
                                <input type="text" value="" id="lastName" class="input2" name="lastName"/>
                            </div>
                       </div>
                       <h3><spring:message code="com.adminui.users_overlay.DefaultPreferences" text="default text" /></h3>
                        <div class="area">
                        	<div class="row">
								<label class="alt-label"><spring:message code="com.adminui.users_overlay.Language" text="default text" /></label>
                                <select class="alt-select" name="language">
                                	<option value="English" selected="selected">English</option>
                                	<option value="Dutch">Dutch</option>
                                	<option value="French">French</option>
                                	<option value="German">German</option>
                                	<option value="Spanish">Spanish</option>
                                </select>
                                <label for="decimalNotation"><spring:message code="com.adminui.users_overlay.DecimalNotation" text="default text" /></label>
                                <select class="alt-select" name="decimalNotation">
                                	<option value="1 234 567,89">1 234 567,89</option>
                                	<option selected="selected" value="1,234,567.89">1,234,567.89</option>
                                	<option value="1.234.567,89">1.234.567,89</option>
                                </select>
                            </div>
                            <div class="row" >
                                <label class="alt-label"><spring:message code="com.adminui.users_overlay.TimeZone" text="default text" /></label>
                                <select class="alt-select" name="timeZone">
                                	<option selected="selected" value="0"></option>
                                	<option value="+00:00">(GMT) Western Europe Time, London, Lisbon, Casablanca</option><option value="+01:00">(GMT +1:00 hour) Brussels, Copenhagen, Madrid, Paris</option><option value="+02:00">(GMT +2:00) Kaliningrad, South Africa</option><option value="+03:00">(GMT +3:00) Baghdad, Riyadh, Moscow, St. Petersburg</option><option value="+03:30">(GMT +3:30) Tehran</option><option value="+04:00">(GMT +4:00) Abu Dhabi, Muscat, Baku, Tbilisi</option><option value="+04:30">(GMT +4:30) Kabul</option><option selected="selected" value="+05:00">(GMT +5:00) Ekaterinburg, Islamabad, Karachi, Tashkent</option><option value="+05:30">(GMT +5:30) Bombay, Calcutta, Madras, New Delhi</option><option value="+05:45">(GMT +5:45) Kathmandu</option><option value="+06:00">(GMT +6:00) Almaty, Dhaka, Colombo</option><option value="+07:00">(GMT +7:00) Bangkok, Hanoi, Jakarta</option><option value="+08:00">(GMT +8:00) Beijing, Perth, Singapore, Hong Kong</option><option value="+09:00">(GMT +9:00) Tokyo, Seoul, Osaka, Sapporo, Yakutsk</option><option value="+09:30">(GMT +9:30) Adelaide, Darwin</option><option value="+10:00">(GMT +10:00) Eastern Australia, Guam, Vladivostok</option><option value="+11:00">(GMT +11:00) Magadan, Solomon Islands, New Caledonia</option><option value="+12:00">(GMT +12:00) Auckland, Wellington, Fiji, Kamchatka</option><option value="-01:00">(GMT -1:00 hour) Azores, Cape Verde Islands</option><option value="-02:00">(GMT -2:00) Mid-Atlantic</option><option value="-03:00">(GMT -3:00) Brazil, Buenos Aires, Georgetown</option><option value="-03:30">(GMT -3:30) Newfoundland</option><option value="-04:00">(GMT -4:00) Atlantic Time (Canada), Caracas, La Paz</option><option value="-05:00">(GMT -5:00) Eastern Time (US &amp; Canada), Bogota, Lima</option><option value="-06:00">(GMT -6:00) Central Time (US &amp; Canada), Mexico City</option><option value="-07:00">(GMT -7:00) Mountain Time (US &amp; Canada)</option><option value="-08:00">(GMT -8:00) Pacific Time (US &amp; Canada)</option><option value="-09:00">(GMT -9:00) Alaska</option><option value="-10:00">(GMT -10:00) Hawaii</option><option value="-11:00">(GMT -11:00) Midway Island, Samoa</option><option value="-12:00">(GMT -12:00) Eniwetok, Kwajalein</option>
                                </select>
                                <label class="alt-label"><spring:message code="com.adminui.users_overlay.DateFormat" text="default text" /></label>
                                <select class="alt-select" name="dateFormat">
                                	<option selected="selected" value="DD.MM.YYYY">DD.MM.YYYY</option>
                                	<option value="MM-DD-YYYY">MM-DD-YYYY</option>
                                	<option value="MM/DD/YYYY">MM/DD/YYYY</option>
                                	<option value="YYYY-MM-DD">YYYY-MM-DD</option>
                                	<option value="YYYY.MM.DD">YYYY.MM.DD</option>
                                	<option value="YYYY/MM/DD">YYYY/MM/DD</option>
                                </select>
                            </div>
                        </div>
                        <h4><spring:message code="com.adminui.users_overlay.AssignContentViews" text="default text" /></h4>
                        <div class="area alt-area">
                        <table class="table-data">
									<thead>
										<tr>
											<th class="td-select"><input type="checkbox" name="check-lightbox-all" id="check-lightbox-1"  onclick="checkAllProfiles(this.checked)" /><label for="check-lightbox-1"></label></th>
											<th class="a-left"><spring:message code="com.adminui.users_overlay.ContentViewName" text="default text" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="profile" items="${profiles}" varStatus="cntr1">
										<tr>
											<td class="td-select"><input type="checkbox" name="assignedProfiles" id="profile-${profile.profileId}" value="${profile.profileId}"/><label for="profile-${profile.profileId}"></label></td>
											<td class="a-left td-description">
												<div class="field">
													<span><a href="#">${profile.profileName}</a></span>
													<input type="text" value="${profile.profileName}" />
												</div>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.users_overlay.Cancel" text="default text" /></a>
                            <a class="btn-create close"  onclick="createUser();"><span><spring:message code="com.adminui.users_overlay.Save" text="default text" /></span></a>
                            <p><span class="required">* </span><spring:message code="com.adminui.users_overlay.RequiredField" text="default text" /></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-profiles">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteDetailsCloseBtn" href="#" class="close"><spring:message code="com.adminui.users_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.users_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                	<%--<p>There are profiles associated to the user, are you sure you want to delete the selected users?</p>--%>
                    <p class="deletion-message"><spring:message code="com.adminui.users_overlay.msg" text="default text" /></p>
                    <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.users_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteDetailsBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.users_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
