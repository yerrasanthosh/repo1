<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript">
    $(document).ready(function() {
        if($.inArray('MASTER_ADMINISTRATOR', roles) != -1){
            $("#associateCat").css({ display: "block" })
            $("#btnHolder").css({ display: "block" })
            $("#addCatalogs").css({ display: "block" })
            $("#btnRemove").css({ display: "block" })
        }
    });
</script>
<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.create_profile.Vroozi" text="default text" /></a></li>
            <li><a href="profiles"><spring:message code="com.adminui.create_profile.ContentViewManagement" text="default text" /></a></li>
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
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.create_profile.ContentViewsettings" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content">
                        <form action="createNewProfile" class="settings-form" id="createNewProfile" method="post">
                            <div class="text-fields">
                                <div class="row" id="theProfileNameId">
                                    <label for="profile-name"><span style="color:#ED1C24;">*</span><spring:message code="com.adminui.create_profile.ContentViewName" text="default text" /></label>
                                    <div class="area-col" id="profile-name-div">
                                        <c:if test="<%=aclManager.allow(request, Permission.EDIT_CONTENT_VIEW)%>">
                                            <input type="text" name ="profileName" id="profile-name" class="required" onkeyup="if($('#profile-name').val() != '')$('#profile-name-div span').remove()" maxlength="64"/>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="row" id="theProfileDescId">
                                    <label for="profile-description"><spring:message code="com.adminui.create_profile.ContentViewDescription" text="default text" /></label>
                                    <div class="area-col" id="profile-description-div">
                                        <textarea id="profile-description"  name ="profileDesc" cols="30" rows="10" class="textarea required" maxlength="250"></textarea>
                                    </div>
                                </div>
                                <input type="hidden" name="activeStr"  id="activeStr"  value="${profileProductActive}" />
                                <div class="row active">
                                    <label for="stateLinkId"><spring:message code="com.adminui.create_profile.Active" text="default text" /></label>
                                    <div class="area-col">
                                        <a class="link" href="#" id="stateLinkId" onclick="if($('#stateLinkId').html()=='Yes'){$('#stateLinkId').html('No');$('#activeStr').val('No');}else {$('#stateLinkId').html('Yes');$('#activeStr').val('Yes');} return false;" >${profileProductActive}</a>
                                    </div>
                                </div>
                            </div>
                            <div class="btns-holder" style="display: none;" id="btnHolder">
                                <c:if test='${pageName.equals("Edit Content View")}'>
                                    <a class="btn-cancel" href='<c:url value="/profiles" />' ><spring:message code="com.adminui.create_profile.Cancel" text="default text" /></a>
                                </c:if>
                                <c:if test='${pageName.equals("Create Content View")}'>
                                    <a class="btn-cancel" href='<c:url value="/profiles" />' ><spring:message code="com.adminui.create_profile.Cancel" text="default text" /></a>
                                </c:if>
                                <c:if test="<%=aclManager.allow(request, Permission.EDIT_CONTENT_VIEW)%>">
                                    <input type="submit" value="Save" onclick="createNewProfile();return false;"/>
                                </c:if>
                                <p><span class="required">* </span><spring:message code="com.adminui.create_profile.RequiredField" text="Required Field" /></p>
                                <p><span id="createProfileReplyDiv" style="font-style: italic; color: #ED1C24; height:10px"></span></p>
                            </div>
                            <input id="profileId" name="profileId" type="hidden" value="${profileId}" />
                            <input id="profileNameHiddenId" name="" type="hidden" value="${profileName}" />
                            <input id="profileDescriptionHiddenId" name="" type="hidden" value="${profileDesc}" />
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
                <div class="toggle-block active" id="associate-catalogs">
                    <div class="title">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.create_profile.CATALOGS" text="default text" /></a></h2>
                    </div>
                    <div class="block">
                        <div class="content editable-widget">
                            <div class="catalog-box">
                                <div class="btn-holder" id="addCatalogs" style="display: none;">
                                    <c:if test="<%=aclManager.allow(request, Permission.EDIT_CONTENT_VIEW)%>">
                                        <a href="javascript:void(0);" class="btn-add-catalogs" onclick="openAddCatalogs(); return false;"><span><em class="checkboxReset"><spring:message code="com.adminui.create_profile.ADD_CATALOGS" text="default text" /></em></span></a>
                                    </c:if>
                                </div>

                                <div id="profile_ass_catalogs-holder">
                                    <jsp:include page="profile_ass_catalogs_fragment.jsp"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href='<c:url value="/profiles" />' class="ico-back"><span><spring:message code="com.adminui.create_profile.BACK" text="default text" /></span></a></li>
                <c:if test="<%=aclManager.allow(request, Permission.EDIT_CONTENT_VIEW)%>">
                    <li id="associateCat" style="display: none;"><a href="#associate-catalogs" class="ico-catalogs alt-opener"><span>ASSOCIATE CATALOGS</span></a></li>
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
        $('#profile-name').val($('#profileNameHiddenId').val());
        $('#profile-description').val($('#profileDescriptionHiddenId').val());
    });

    function openAddCatalogs(){
        $.colorbox({
            href:'#add-catalogs',
            overlayClose:false,
            inline:true,
            onComplete: function(){
                $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                    $.colorbox.close();
                    return false;
                });
            }

        });
    }
    function deAssociateCatalogFromProfile() {
    	if(isAnyItemChecked('disAssociateCatalogFromProfileForm')) {
    		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', disAssociateCatalogToProfile);
    	} else {
    		alert('Please select an item');
    	}
    }
    
</script>