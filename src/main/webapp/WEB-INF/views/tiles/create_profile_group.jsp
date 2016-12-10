<%@ page contentType="text/html;charset=UTF-8" language="java"
	autoFlush="true" session="true"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission"%>
<%@ page import="com.vroozi.customerui.util.Consts" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request"
	class="com.vroozi.customerui.acl.DisplayAccessControl" />

<script type="text/javascript">
    $(document).ready(function() {
        if($.inArray('MASTER_ADMINISTRATOR', roles) != -1){
            $("#associateProfile").css({ display: "block" })
            $("#btnHolder").css({ display: "block" })
            $("#addProfiles").css({ display: "block" })
            $("#btnRemove").css({ display: "block" })
        }
    });
</script>
<div id="main">
	<div class="section">
		<ul class="breadcrumbs">
			<li><a href="vroozi"><spring:message code="com.adminui.create_profile_group.Vroozi" text="default text" /></a></li>
			<li><a href="profiles?selectedTab=2"><spring:message code="com.adminui.create_profile_group.ContentViewGroupManagement" text="default text" /></a></li>
			<li>${pageName}</li>
		</ul>
		<div class="video-btn-holder">
			<!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
		</div>
	</div>
	<div class="main-holder">
		<div id="content">
			<h1>${pageName}</h1>
			<div class="content-block toggle-block active" id="create-profile">
				<div class="headline">
					<h2>
						<a href="#" class="open-close"><spring:message code="com.adminui.create_profile_group.ContentViewGroupsettings" text="default text" /></a>
					</h2>
				</div>
				<div class="block">
					<div class="content">
						<form action="saveProfileGroup" class="settings-form"
							id="createNewProfileGroup" method="post">
							<div class="text-fields">
								<div class="row" id="theProfileGroupNameId">
									<label for="profile-group-name"><span
										style="color: #ED1C24;">*</span><spring:message code="com.adminui.create_profile_group.ContentViewGroupName" text="default text" /></label>
									<div class="area-col" id="profile-group-name-div">
										<input type="text" name="groupName" id="profile-group-name"
											class="required"
											onkeyup="if($('#profile-group-name').val() != '')$('#profile-group-name-div span').remove()"
											maxlength="64" />
									</div>
								</div>
								<div class="row" id="theProfileGroupDescId">
									<label for="profile-group-description"><spring:message code="com.adminui.create_profile_group.ContentGroupToken" text="default text" /></label>
									<div class="area-col" id="profile-description-div">
										<input type="text" id="profile-group-description" disabled="disabled" readonly="readonly"
											id="profile-group-token2" value="${token}" name="token2"
											class="textarea required">
										</textarea>
										<input type="hidden" id="profile-group-token" value="${token}"
											name="token" /> <a href="#" onclick="createToken()"
											id="lnkNewToken" class="link" style="display: none"><spring:message code="com.adminui.create_profile_group.NewToken" text="default text" /></a>
									</div>
								</div>
                                <c:if test="<%=aclManager.isFeatureAvailable(request, Consts.FEATURE_PURCHASE_EXPRESS)%>">
								<div class="row" id="theProfileGroupDisplayOnRegistrationForm">
									<label>Display On Registration Form</label>
									<div class="area-col" id="profile-group-display-on-registration-form-div">
										<input type="radio" name="displayOnRegistrationForm" value="true" <c:if test='${displayOnRegistrationForm eq true}'>checked="checked"</c:if>>Yes&nbsp;&nbsp;<input type="radio" name="displayOnRegistrationForm" value="false" <c:if test='${displayOnRegistrationForm eq false}'>checked="checked"</c:if>>No
									</div>
								</div>
                                </c:if>
								<input type="hidden" name="activeStr" id="activeStr" value="${profileGroupActive}" />
								<div class="row active">
									<label for="stateLinkId"><spring:message code="com.adminui.create_profile_group.Active" text="default text" /></label>
									<div class="area-col">
										<a class="link" href="#" id="stateLinkId"
											onclick="if($('#stateLinkId').html()=='Yes'){$('#stateLinkId').html('No');$('#activeStr').val('No');}else {$('#stateLinkId').html('Yes');$('#activeStr').val('Yes');} return false;">${profileGroupActive}</a>
									</div>
								</div>
							</div>
							<div class="btns-holder" style="display: none;" id="btnHolder">
								<c:if test='${pageName.equals("Edit Content View Group")}'>
									<a class="btn-cancel" href='<c:url value="/profiles?selectedTab=2" />'><spring:message code="com.adminui.create_profile_group.Cancel" text="default text" /></a>
								</c:if>
								<c:if test='${pageName.equals("Create Content View Group")}'>
									<a class="btn-cancel" href='<c:url value="/profiles?selectedTab=2" />'><spring:message code="com.adminui.create_profile_group.Cancel" text="default text" /></a>
								</c:if>
								<c:if
									test="<%=aclManager.allow(request, Permission.EDIT_CONTENT_VIEW_GROUP)%>">
									<input type="submit" value="Save"
										onclick="saveProfileGroup();return false;" />
								</c:if>
								<p>
									<span class="required">* </span><spring:message code="com.adminui.create_profile_group.RequiredField" text="default text" />
								</p>
								<p>
									<span id="createProfileGroupReplyDiv"
										style="font-style: italic; color: #ED1C24; height: 10px"></span>
								</p>
							</div>
							<input id="profileGroupId" name="groupId" type="hidden"
								value="${groupId}" /> <input id="profileGroupNameHiddenId"
								name="" type="hidden" value="${groupName}" /> <input
								id="tokenHiddenId" name="" type="hidden" value="${token}" />
						</form>
						<script type="text/javascript">
                            $(function () {
                                $('input').keypress(function (e) {
                                    var code = null;
                                    code = (e.keyCode ? e.keyCode : e.which);
                                    return (code == 13) ? false : true;
                                });
                            });
                        </script>
					</div>
				</div>
			</div>
			<div class="add-slide-blocks">
				<div class="toggle-block active" id="associate-profiles">
					<div class="title">
						<h2>
							<a href="#" class="open-close"><spring:message code="com.adminui.create_profile_group.CONTENTVIEWS" text="default text" /></a>
						</h2>
					</div>
					<div class="block">
						<div class="content editable-widget">
							<div class="catalog-box">
								<div class="btn-holder" id="addProfiles" style="display: none;">
									<c:if
										test="<%=aclManager.allow(request, Permission.EDIT_CONTENT_VIEW_GROUP)%>">
										<a href="#add-profiles-group" class="open-popup"><span><em class="checkboxReset"><spring:message code="com.adminui.create_profile_group.ADDCONTENTVIEWS" text="default text" /></em></span></a>
									</c:if>
								</div>

								<div id="group_profile_ass_holder">
									<jsp:include page="groups_ass_profiles_fragment.jsp" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="sidebar">
			<ul class="sub-nav">
				<li><a href='<c:url value="/profiles?selectedTab=2" />'
					class="ico-back"><span><spring:message code="com.adminui.create_profile_group.BACK" text="default text" /></span></a></li>
				<c:if
					test="<%=aclManager.allow(request, Permission.EDIT_CONTENT_VIEW_GROUP)%>">
					<li id="associateProfile" style="display: none;"><a
						href="#associate-profiles" class="ico-catalogs alt-opener x-height"><span><spring:message code="com.adminui.create_profile_group.ASSOCIATECONTENTVIEW" text="default text" /></span></a></li>
				</c:if>
			</ul>
		</div>
	</div>
</div>

<script type="text/javascript">
    function checkAllItems(formId, checked){
        try{
            if(checked){
                $("#" + formId + " :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#" + formId + " :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                });
            }
        }catch(exp){
            alert(exp);
        }
    }

    $(document).ready(function() {
        $('#profile-group-name').val($('#profileGroupNameHiddenId').val());
        $('#profile-group-token').val($('#tokenHiddenId').val());
        $('#profile-group-token2').val($('#tokenHiddenId').val());
        
    });
    
    function deAssociateGroupProfile() {
    	if(isAnyItemChecked('associatedProfilesToGroupForm')){
    		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', disAssociateGroupProfile);
    	} else {
    		alert('Please select an item');
    	}
    }
</script>