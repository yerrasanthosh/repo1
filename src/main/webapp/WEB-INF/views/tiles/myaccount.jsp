<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.profile.model.Profile,
         com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="res/js/users.js"></script>


		<div id="main">
			<div class="section">
				<ul class="breadcrumbs">
					<li><a href="vroozi"><spring:message code="com.adminui.myaccount.Vroozi" text="default text" /></a></li>
					<li><spring:message code="com.adminui.myaccount.UserSettings" text="default text" /></li>
				</ul>
				<div class="video-btn-holder">
					<!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
					<a href="#" class="btn"><span><em>TUTORIAL</em></span></a-->
				</div>
			</div>
			
			
			<div class="main-holder">
				<div id="content">
					
					<h1><spring:message code="com.adminui.myaccount.MyAccount" text="default text" /></h1>
				
				    <form action="myAccount" id="frmMyAccountDetails" method="post">

                        <input type="hidden" value="${userDetail.unitId}" name="unitId"/>

                        <input type="hidden" value="${userDetail.userId}" name="userId"/>

                        <div class="content-block toggle-block active" id="create-user" style="margin: 0;">
                            <div class="headline">
                                <h2><a href="#" class="open-close"><spring:message code="com.adminui.myaccount.USERINFORMATION" text="default text" /></a></h2>
                            </div>

                            <div class="block">
                                <div class="content">
                                    <div class="settings-form" >
                                        <div class="text-fields">
                                            <div class="row">
                                                <label for="username"><span class="required">*</span><spring:message code="com.adminui.myaccount.Email" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="text" name="username" id="username"  maxlength="64"/>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.validEmail" text="default text" /></span>
                                                    <input id="usernameHidden" type="hidden" value="${userDetail.username}">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="contactName"><span class="required">*</span><spring:message code="com.adminui.myaccount.ContactName" text="default text" /></label>
                                                <div class="area-col">
                                                    <input id="contactName" type="text" name="contactName"  maxlength="64"/>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.contactName" text="default text" /></span>
                                                    <input id="contactNameHidden" type="hidden" value="${userDetail.details.contactName}">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="contactTitle"><span class="required">*</span><spring:message code="com.adminui.myaccount.ContactTitle" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="text" name="contactTitle" id="contactTitle"  maxlength="50" />
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.validTitle" text="default text" /></span>
                                                    <input id="contactTitleHidden" type="hidden" value="${userDetail.details.contactTitle}">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="txtAddress"><span class="required">*</span><spring:message code="com.adminui.myaccount.Address" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="text" name="address" id="txtAddress"  maxlength="64"/>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.validAddress" text="default text" /></span>
                                                    <input id="txtAddressHidden" type="hidden" value="${userDetail.details.address}">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="city"><span class="required">*</span><spring:message code="com.adminui.myaccount.City" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="text" name="city" id="city"  maxlength="64"/>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.validCity" text="default text" /></span>
                                                    <input id="cityHidden" type="hidden" value="${userDetail.details.city}">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="zipCode"><span class="required">*</span><spring:message code="com.adminui.myaccount.zipCode" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="text" name="zipCode" id="zipCode"  maxlength="20"/>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.validZipCode" text="default text" /></span>
                                                    <input id="zipCodeHidden" type="hidden" value="${userDetail.details.zipCode}">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="state"><span class="required">*</span><spring:message code="com.adminui.myaccount.State" text="default text" /></label>
                                                <div class="area-col">
                                                <input id="stateCounty" name="stateCounty" class="text" type="text"  onkeyup="if($('#stateCounty').val() != '')$('#state-div span').remove()"/>
                                                <div id="stateCountySelectBox">
                                                    <select name="state" id="state">
                                                 
                                                        
                                                        	<c:forEach var="state" items="${stateList}">
		                                                    <option id="stateid-${state.name}"  
		                                                    <c:if test="${userDetail.details.state eq state.name}"> selected="selected"</c:if>
		                                                 	value="${state.name}">${state.name}</option>
		                                                </c:forEach> 
                                                    </select>
                                                 </div>
                                                 <input type="hidden" id="stateCountyHidden" name="stateCountyHidden" value="${userDetail.details.state}"/>
                                                 <span class="error-msg"><spring:message code="com.adminui.myaccount.validState" text="default text" /></span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="country"><span class="required">*</span><spring:message code="com.adminui.myaccount.Country" text="default text" /></label>
                                                <div class="area-col">
                                                    <select onchange="toggleStatesSelect();" name="country" id="country">
                                                        <c:forEach var="country" items="${countryList}">
                                                            <option id="${country.id}"
                                                           		 <c:if test="${userDetail.details.country eq country.id}"> selected="selected"</c:if>
                                                                    value="${country.id}">
                                                                    ${country.name}</option>
                                                        </c:forEach>
                                                        
                                                    </select>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.Youmustselectacountry" text="default text" /></span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="telephone"><span class="required">*</span><spring:message code="com.adminui.myaccount.Telephone" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="text" name="telephone" id="telephone"  maxlength="64"/>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.validPhone" text="default text" /></span>
                                                    <input id="telephoneHidden" type="hidden" value="${userDetail.details.telephone}">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="fax"><spring:message code="com.adminui.myaccount.Fax" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="text" name="fax" id="fax" />
                                                    <input id="faxHidden" type="hidden" value="${userDetail.details.fax}"  maxlength="64">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="content-block toggle-block" id="change_password" style="margin: 0;">
                            <div class="headline">
                                <h2><a href="#" class="open-close"><spring:message code="com.adminui.myaccount.PASSWORDCHANGE" text="default text" /></a></h2>
                            </div>

                            <div class="block">
                                <div class="content">
                                    <div class="settings-form">
                                        <div class="text-fields">
                                            <div class="row">
                                                <label for="pwdOld"><spring:message code="com.adminui.myaccount.OldPassword" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="password" class="default" name="pwdOld" id="pwdOld"  />
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.validOldPassword" text="default text" /></span>
                                                    <label id="oldpass_error" class="error-msg"><spring:message code="com.adminui.myaccount.incorrectOldPassword" text="default text" /></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="pwdNew"><spring:message code="com.adminui.myaccount.NewPassword" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="password" class="default" name="pwdNew" id="pwdNew"/>
                                                    <label id="newpass_error" class="error-msg"><spring:message code="com.adminui.myaccount.Enternewpassword" text="default text" /></label>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <label for="pwdNew2"><spring:message code="com.adminui.myaccount.ConfirmPassword" text="default text" /></label>
                                                <div class="area-col">
                                                    <input type="password" class="default" name="pwdNew2" id="pwdNew2"/>
                                                    <span class="error-msg"><spring:message code="com.adminui.myaccount.confirmPassNew" text="default text" /></span>
                                                    <label id="passmatch_error" class="error-msg"><spring:message code="com.adminui.myaccount.passNotMatch" text="default text" /></label>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="content-block toggle-block" id="default_settings" style="margin: 0;">
                            <div class="headline">
                                <h2><a href="#" class="open-close"><spring:message code="com.adminui.myaccount.DEFAULTSETTINGS" text="default text" /></a></h2>
                            </div>

                            <div class="block">
                                <div class="content">
                                    <div class="settings-form">

                                <div class="text-fields">
                                            <div class="row">
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.myaccount.TimeZone" text="default text" /></label>
                                                    <select name="timeZone" id="timeFormatList" style="w">
                                                        <c:forEach var="timeFormat" items="${timeFormatList}">
                                                            <option id="${timeFormat.timeZoneId}" <c:if test="${userDetail.timeZone eq timeFormat.timeZoneId}"> selected="selected"</c:if>="" value="${timeFormat.timeZoneId}">${timeFormat.timeZoneName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        <div class="row">
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.myaccount.Language" text="default text" /></label>
                                                    <select name="language" id="languagesList">
                                                        <c:forEach var="language" items="${languagesList}">
                                                            <option id="${language.languageId}" <c:if test="${userDetail.language eq language.languageValue}"> selected="selected"</c:if>="" value="${language.languageValue}">${language.languageName}</option>
                                                        </c:forEach>
                                                        <%--<option id="1" value="en">English</option>--%>
                                                    </select>
                                                </div>
                                        </div>


                                            <div class="row">
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.myaccount.DateFormat" text="default text" /></label>
                                                    <select name="dateFormat" id="dateFormatList">
                                                        <c:forEach var="dateFormat" items="${dateFormatList}">
                                                            <option id="${dateFormat.dateId}"
                                                                    <c:if test="${userDetail.dateFormat eq dateFormat.dateId}"> selected="selected"</c:if>
                                                                    value="${dateFormat.dateId}">${dateFormat.dataFormat}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.myaccount.DecimalNotation" text="default text" /></label>
                                                    <select name="decimalNotation" id="decimalNotationList">
                                                        <c:forEach var="decimalNotation" items="${decimalNotationList}">
                                                            <option id="${decimalNotation.decimalId}" <c:if test="${userDetail.decimalNotation eq decimalNotation.decimalId}"> selected="selected"</c:if>="" value="${decimalNotation.decimalId}">
                                                                    ${decimalNotation.decimalNotationName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="area-col">
                                                    <label><spring:message code="com.adminui.myaccount.RecordsPerPage" text="default text" /></label>
                                                    <select name="rowsPerPage" id="rowsPerPage">
                                                        <option <c:if test="${userDetail.rowsPerPage eq 25}"> selected="selected"</c:if>
                                                                    value="25">25</option>
                                                        <option <c:if test="${userDetail.rowsPerPage eq 10}"> selected="selected"</c:if>
                                                                    value="10">10</option>
                                                        <option <c:if test="${userDetail.rowsPerPage eq 8}"> selected="selected"</c:if>
                                                                    value="8">8</option>
                                                        <option <c:if test="${userDetail.rowsPerPage eq 5}"> selected="selected"</c:if>
                                                                    value="5">5</option>

                                                    </select>
                                                </div>
                                            </div>

                                        </div>


                                        <!--div class="btns-holder">
                                            <a class="btn-cancel" href="#">Cancel</a>
                                            <input type="submit" value="Save" />
                                            <p><span class="required">* </span>Required Field</p>
                                        </div-->
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="settings-form" style="padding: 5px 0 0 0">
                            <div class="btns-holder" style="border-top: 0px solid #D8D8D8;">
                                <a class="btn-cancel" href='<c:out value="vroozi"/>'><spring:message code="com.adminui.home.Cancel" text="default text" /></a>
                                <input type="submit" value="<spring:message code="com.adminui.supplier_detail.Save" text="default text" />" />
                                <p><span class="required">* </span><spring:message code="com.adminui.myaccount.RequiredField" text="default text" /></p>
                                <p><span style="font-style: italic; color: #ED1C24; height:10px" id="MyAccountUpdateFeedbackDiv"><br></span></p>
                            </div>
                        </div>

					</form>
									
				</div>
				<div id="sidebar">
					<ul class="sub-nav">
						<li><a href="<c:out value="vroozi"/>" class="ico-back"><span><spring:message code="com.adminui.myaccount.BACK" text="default text" /></span></a></li>
					</ul>
                    <br />
					<span><spring:message code="com.adminui.myaccount.JUMPTO" text="default text" /></span>
					<ul class="sub-nav">
						<li><a href="#create-user" class="ico-user alt-opener"><span><spring:message code="com.adminui.myaccount.USERINFORMATION" text="default text" /></span></a></li>
						<li><a href="#change_password" class="ico-assign-profiles alt-opener"><span><spring:message code="com.adminui.myaccount.PASSWORDCHANGE" text="default text" /></span></a></li>
						<li><a href="#default_settings" class="ico-assign-profiles alt-opener"><span><spring:message code="com.adminui.myaccount.DEFAULTSETTINGS" text="default text" /></span></a></li>
					</ul>
				</div>
			</div>
		</div>

<script type="text/javascript">
    var form_modified = false;
    $(document).ready(function() {
        try{
            $('#contactName').val($('#contactNameHidden').val());
            $('#contactTitle').val($('#contactTitleHidden').val());
            $('#txtAddress').val($('#txtAddressHidden').val());
            $('#city').val($('#cityHidden').val());
            $('#zipCode').val($('#zipCodeHidden').val());
            $('#username').val($('#usernameHidden').val());
            $('#telephone').val($('#telephoneHidden').val());
            $('#fax').val($('#faxHidden').val());
            $('#stateCounty').val($('#stateCountyHidden').val());
            toggleStatesSelectOnLoad();
            
        }catch(exp){
            //alert(exp);
        }
    });
    function toggleStatesSelectOnLoad(){
        var countryId = $("#country").val();
        if(countryId == "1") {
			$("#stateCountySelectBox").show();
			$("#stateCounty").hide();
        } else {
        	$("#stateCountySelectBox").hide();
        	$("#stateCounty").show();
        }
    }
    function toggleStatesSelect(){
        var countryId = $("#country").val();
        if(countryId == "1") {
			$("#stateCountySelectBox").show();
			$("#stateCounty").hide();
        } else {
        	$("#stateCountySelectBox").hide();
        	$("#stateCounty").show();
        	$("#stateCounty").val("");
        }
    }
</script>