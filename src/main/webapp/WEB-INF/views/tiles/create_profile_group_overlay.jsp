<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="delete">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_profile_group_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_profile_group_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.create_profile_group_overlay.clickDelete" text="default text" /></p>
                    <p><strong><spring:message code="com.adminui.create_profile_group_overlay.clickDelete2" text="default text" /></strong></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" class="btn btn-red"><span><spring:message code="com.adminui.create_profile_group_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="add-profiles-group">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_profile_group_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_profile_group_overlay.AddContentViews" text="default text" /></h2>
                </div>
                <div class="search-box">
                    <form action="#" class="add-search-form" style="width: 415px;">
                        <fieldset>
                            <input id="searchWithinProfileTextId1"  type="text" value="Search within..." />
                            <input id="searchWithinCatalogTextIdBtn1" style="float: left;" type="submit" value="Submit" onclick="searchWithinNAProfiles(); return false;"/>
                            <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchWithinProfileTextId1').val('Search Within...'); $('#searchWithinCatalogTextIdBtn1').click();" />
                        </fieldset>
                    </form>
                </div>
                <div style="margin-top: 0" id="group_profiles-holder">
                    <jsp:include page="group_profile_fragment.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
