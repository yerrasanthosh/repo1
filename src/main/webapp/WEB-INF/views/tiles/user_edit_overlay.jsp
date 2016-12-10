<%@page import="com.vroozi.customerui.profile.model.Profile"%>
<%@page import="com.vroozi.customerui.profile.model.ProfileProxy"%>
<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="index_overlay.jsp"/>

<div class="lightbox-section">
    <div class="lightbox" id="create-profile">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close" onclick="clearProfileFormErrors()"><spring:message code="com.adminui.user_edit_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.user_edit_overlay.CreateContentView" text="default text" /></h2>
                </div>
                <form action="addCatalogProfile" class="form-create" id="createProfileId" method="post">
                    <fieldset>
                        <h3><spring:message code="com.adminui.user_edit_overlay.CONTENTVIEWSETTINGS" text="default text" /></h3>
                        <div class="area">
                            <div class="row" id="theProfileNameId">
                                <label for="profile-name"><span>*</span><spring:message code="com.adminui.user_edit_overlay." text="default text" /></label>
                                <input type="text" name ="profileName" id="profile-name" class="required"/>
                                
                            </div>
                            <div class="row" id="theProfileDescId">
                                <label for="profile-description"><spring:message code="com.adminui.user_edit_overlay.ContentViewDescription" text="default text" /></label>
                                <textarea id="profile-description"  name ="profileDesc" cols="30" rows="10" class="textarea"></textarea>
                            </div>
                            <!-- <div class="row">
                                <label for="rating">Allow Product Rating:</label>
                                <input type="checkbox"  name="rating" id="rating" />
                            </div> -->
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel" onclick="clearProfileFormErrors()">Cancel</a>
                            <!-- <a class="btn-create close"  onclick="createUserProfile();"><span>Save</span></a> -->
                            <input type="submit" value="Save" onclick="return validateUserProfile();" name="Save" />
                            <p><span class="required">* </span><spring:message code="com.adminui.user_edit_overlay.RequiredField" text="default text" /></p>
							<p><span id="createProfileReplyDiv" style="font-style: italic; color: #ED1C24; height:10px"></span></p>
                            <input type="hidden" name="userId" value="${param['userId']}"/>
                        </div>
                        
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
	<div class="lightbox-section">
		<div class="lightbox" id="add-profile">
			<div class="holder">
				<div class="frame">
					<div class="title">
						<a href="#" class="close"><spring:message code="com.adminui.user_edit_overlay.Close" text="default text" /></a>
						<h2><spring:message code="com.adminui.user_edit_overlay.AddContentViews" text="default text" /></h2>
					</div>
					<form action="#" id="add-profiles-form" class="checkboxResetForm">
						<fieldset>
							<div class="content lightbox-content editable-widget" id="addUserProfilesSection">
								<table class="table-data">
									<thead>
										<tr>
											<th class="td-select"><input type="checkbox" class="check-allbox" name="check-lightbox-all" id="check-lightbox-1" /><label for="check-lightbox-1"></label></th>
											<th class="a-left"><spring:message code="com.adminui.user_edit_overlay.Name" text="default text" /> </th>
											<th class="a-left"><spring:message code="com.adminui.user_edit_overlay.Description" text="default text" /> </th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="profile" items="${profilesForCompany.pageList}" varStatus="cntr1">
										<tr>
											<td class="td-select"><input type="checkbox" class="target-chbox" name="assignedProfiles" id="profile-${profile.profileId}" value="${profile.profileId}" /><label for="profile-${profile.profileId}"></label></td>
											<td class="a-left td-description">
												<div class="field">
													<span><a href="#">${profile.profileName}</a></span>
													<input type="text" value="${profile.profileName}" />
												</div>
											</td>
											<td class="a-left">${profile.profileDesc}</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="bottom-data">
									<div class="pager">
										<span><spring:message code="com.adminui.user_edit_overlay.Page" text="default text" /> ${profilesForCompany.page+1} <spring:message code="com.adminui.user_edit_overlay.of" text="default text" /> ${profilesForCompany.pageCount}</span>
										<ul>
											<li><a href="javascript:void(0)" class="btn-prev" onclick="navigateProfilePage('${prevUserProfilesPage}', '${param['userId']}');"></a></li>
											<li><a href="javascript:void(0)" class="btn-next" onclick="navigateProfilePage('${nextUserProfilesPage}', '${param['userId']}');"></a></li>
										</ul>
									</div>
									<strong class="pages"><spring:message code="com.adminui.user_edit_overlay.TotalRecords" text="default text" /> ${profilesForCompany.firstElementOnPage+((profilesForCompany.lastElementOnPage ne -1)? 1: 0)}-${profilesForCompany.lastElementOnPage+1} <spring:message code="com.adminui.user_edit_overlay.of" text="default text" /> ${profilesForCompany.nrOfElements}</strong>
								</div>
							</div>
							<div class="btns-holder">
								<input type="hidden" name="userId" value="${param['userId']}"/>
								<a href="#" class="btn-cancel"><spring:message code="com.adminui.user_edit_overlay.Cancel" text="default text" /></a>
								<%--<input type="submit" value="Save" /> --%>
								<a class="btn-create close"  onclick="addUserProfiles();"><span><spring:message code="com.adminui.user_edit_overlay.Save" text="default text" /></span></a>
								<%--<p><span class="required">* </span>Required Field</p>--%>
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
                    <a id="lighboxDeleteDetailsCloseBtn" href="#" class="close"><spring:message code="com.adminui.user_edit_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.user_edit_overlay.deleteConfirm" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.user_edit_overlay.msg" text="default text" /></p>
                    <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.user_edit_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteDetailsBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.user_edit_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-confirm-role">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxRoleConfirmCloseBtn" href="#" class="close"><spring:message code="com.adminui.user_edit_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.user_edit_overlay.RoleUpdateConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p class="role-confirm-message"<spring:message code="com.adminui.user_edit_overlay.areyouSure" text="default text" /></p>
                    <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxRoleConfirmCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.user_edit_overlay.No" text="default text" /></a>
                        <a id="lighboxRoleConfirmBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.user_edit_overlay.Yes" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-reset-password">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.user_edit_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.user_edit_overlay.ResetPassword" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.user_edit_overlay.emailAddress" text="default text" /></p>
                    <!--p><strong>[lorem ipsum dolor sit amet]</strong></p-->
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" class="btn btn-ok"><span><spring:message code="com.adminui.user_edit_overlay.Ok" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function checkAllProfileItems(check){
        try{
            if(check){
                $("#add-profiles-form :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#add-profiles-form :checkbox").each(function(){
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
