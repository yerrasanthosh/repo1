<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-profile-profiles">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteDetailsCloseBtn" href="#" class="close"><spring:message code="com.adminui.profile_management_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.profile_management_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.profile_management_overlay.youWantToDel" text="default text" /></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.profile_management_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteDetailsBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.profile_management_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>








