<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.profile.model.Profile"%>
<%@ page import="com.vroozi.customerui.acl.model.Role" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.profiles.Vroozi" text="default text" /></a></li>
            <li><a href="catalog"><spring:message code="com.adminui.profiles.ContentManager" text="default text" /></a></li>
            <li><spring:message code="com.adminui.profiles.ContentViews" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">
    <div id="content">
        <div class="content-block toggle-block active" id="create-item-section">
    <div class="headline">
        <h2><a href="#" class="open-close"><spring:message code="com.adminui.profiles.ContentViews" text="default text" /></a></h2>
    </div>
    <div class="block">
    <div class="content editable-widget">
    <div class="top-box">
		<div class="btn-holder">
			<ul class="options ">
	       		<li>
	            	<a href="#create-profile" class="btn-create-profile open-popup"><span><em><spring:message code="com.adminui.profiles.CreateContentView" text="default text" /></em></span></a>
				</li>
			</ul>
		</div>
    </div>
    <div class="scrollable-area vscrollable">
    <div class="text">
    <form action="/profiles" id="profiles-form" method="post" enctype="text/plain" class="checkboxResetForm">
    <table class="table-data">
    <thead>
    <tr>
        <th class="td-select"><input type="checkbox" name="check-all" id="check1" onclick="checkAllProfilesItems(this.checked)" /><label for="check1"></label></th>
        <th class="a-left"><spring:message code="com.adminui.profiles.ContentViewName" text="default text" /></th>
        <th><spring:message code="com.adminui.profiles.ContentViewDescription" text="default text" /></th>
        <th><spring:message code="com.adminui.profiles.CreatedOn" text="default text" /></th>
        <th><spring:message code="com.adminui.profiles.CreatedBy" text="default text" /></th>
        <th class="td-last"><spring:message code="com.adminui.profiles.Active" text="default text" /></th>
    </tr>
    </thead>
    <tbody>
        <% int profileCounter = 0; %>
        <c:forEach var="profile" items="${profiles}" varStatus="cntr1">
        <tr>
            <td class="td-select"><input type="checkbox" name="profileIds" id="${profile.profileId}" value="${profile.profileId}" /><label for="${profile.profileId}"></label></td>
            <td class="a-left td-description">
                <div class="field" style="width:150px">
                    <span>${profile.profileName}</span>
                    <input type="text" value="${profile.profileName}" />
                </div>
            </td>
            <td>
                <div class="field" style="width:150px">
                    <span>${profile.profileDesc}</span>
                    <input type="text" value="${profile.profileDesc}" />
                </div>
            </td>
            <td>
                <div class="field" style="width:150px">
                    <span>${profile.createdOn}</span>
                    <input type="text" value="${profile.createdOn}" />
                </div>
            </td>
            <td>
                <div class="field" style="width:150px">
                    <span>${profile.createdBy}</span>
                    <input type="text" value="${profile.createdBy}" />
                </div>
            </td>
            <td class="td-last">${profile.active==true?'Yes':'No'}</td>
        </tr>
        <% ++profileCounter; %>
        </c:forEach>
    </tbody>
    </table>
    <div id="updateCatalogProfilesFeedback" align="left" style="height:10px"></div>
    </form>
    </div>
    </div>
    <div class="bottom-data">
        <!-- <div class="pager">
            <span>Page 1 of 49 </span>
            <ul>
                <li><a href="#" class="btn-prev"></a></li>
                <li><a href="#" class="btn-next"></a></li>
            </ul>
        </div>-->
        <strong class="pages"><spring:message code="com.adminui.profiles.TotalRecords" text="default text" /> <%= profileCounter %></strong>
        
    </div>
    <div class="move-items">
    </div>
    <div class="function">
        <ul>
            <li><a href="javascript:void(0)" class="ico-approve" onclick="activeProfiles(true);"><span><em><spring:message code="com.adminui.profiles.Activate" text="default text" /></em></span></a></li>
            <li><a href="javascript:void(0)" class="ico-reject" onclick="activeProfiles(false);"><span><em><spring:message code="com.adminui.profiles.Deactivate" text="default text" /></em></span></a></li>
            <li class="del"><a href="javascript:void(0)" class="ico-delete"><span><em><spring:message code="com.adminui.profiles." text="default text" /></em></span></a></li>
        </ul>
    </div>
    </div>
    </div>
    </div>
    </div>
    <div id="sidebar">
        <ul class="sub-nav">
            <li><a href="index.html" class="ico-back"><span><spring:message code="com.adminui.profiles.BACK" text="default text" /></span></a></li>
        </ul>
    </div>
    </div>
</div>

<script type="text/javascript">
    function checkAllProfilesItems(check){
        try{
            if(check){
                $("#profiles-form :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#profiles-form :checkbox").each(function(){
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