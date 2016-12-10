<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript">
    function setFunctionBtnsVisibility(){
        if($.inArray('MASTER_ADMINISTRATOR', roles) != -1){
            $("#createContent").css({ display: "block" })
            $("#btnDelete").css({ display: "block" })
            $("#btnActive").css({ display: "block" })
            $("#btnInactive").css({ display: "block" })
            $("#btnDelete1").css({ display: "block" })
            $("#btnActive1").css({ display: "block" })
            $("#btnInactive1").css({ display: "block" })
        }
    }
    $(document).ready(function() {
        setFunctionBtnsVisibility();
        
    });
</script>
<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.profile_management.Vroozi" text="default text" /></a></li>
            <li><spring:message code="com.adminui.profile_management.ContentViewManagement" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">
        <div id="content">
            <h1><spring:message code="com.adminui.profile_management.ContentViewManagement" text="default text" /></h1>
            
            <input type="hidden" class="normal" value="${(empty selectedTab)?param['selectedTab']:selectedTab}" />

            <div class="ui-tab-section user">
				<ul class="tabset user">
					<li><a href="#tab1"><span class="checkboxReset"><spring:message code="com.adminui.profile_management.CONTENTVIEW" text="default text" /></span></a></li>
			        <li>
                         <a href="#tab2">
                             <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_VIEW_GROUP)%>">
                                 <span class="checkboxReset"><spring:message code="com.adminui.profile_management.CONTENTGROUP" text="default text" /></span>
                             </c:if>
                         </a>
					</li>
				</ul>
				
				<div id="tab1">
					<div class="content-block toggle-block active" id="summary-section">
		                <div class="headline">
		                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.profile_management.ContentViewsummary" text="default text" /></a></h2>
		                </div>
		                <div class="block">
		                    <div class="content">
		                        <div class="summary-box">
		                            <table class="summary-table">
		                                <thead>
		                                    <tr>
		                                        <td class="a-center sep view"><spring:message code="com.adminui.profile_management.View" text="default text" /></td>
		                                        <td><spring:message code="com.adminui.profile_management.Summary" text="default text" /></td>
		                                        <td></td>
		                                    </tr>
		                                </thead>
		                                <tfoot>
		                                    <tr>
		                                        <td class="a-center view"></td>
		                                        <td><spring:message code="com.adminui.profile_management.Total" text="default text" /></td>
		                                        <td class="a-right" id="totalProfilesCount">${totalProfilesCount}</td>
		                                    </tr>
		                                </tfoot>
		                                <tbody>
		                                    <tr>
		                                        <td class="a-center view">
		                                            <input id="viewActiveProfile" type="checkbox" checked="checked" onclick="filterProfileView();"/>
		                                            <label for="viewActiveProfile"></label>
		                                        </td>
		                                        <th><spring:message code="com.adminui.profile_management.ActiveContentViews" text="default text" /></th>
		                                        <td class="a-right" id="activeProfilesCount">${numOfActiveProfile}</td>
		                                    </tr>
		                                    <tr>
		                                        <td class="a-center view">
		                                            <input id="viewInactiveProfile" type="checkbox" checked="checked" onclick="filterProfileView();"/>
		                                            <label for="viewInactiveProfile"></label>
		                                        </td>
		                                        <th><spring:message code="com.adminui.profile_management.InactiveContentViews" text="default text" /></th>
		                                        <td class="a-right" id="inactiveProfilesCount">${totalProfilesCount-numOfActiveProfile}</td>
		                                    </tr>
		                                </tbody>
		                            </table>
		                        </div>
		                    </div>
		                </div>
	           	 	</div>
	            	<div class="add-slide-blocks">
	               		 <div class="toggle-block active" id="info-section">
	                   		 <div class="title">
	                       		 <h2><a href="#" class="open-close"><spring:message code="com.adminui.profile_management.CONTENTVIEW" text="default text" /></a></h2>
	                  		 </div>
	
		                    <div class="block">
		                        <div class="content editable-widget">
		                            <div class="top-box">
		                                <form id="searchWithinProfileForm" action="#" class="add-search-form advanced">
		                                    <fieldset>
												<input id="searchWithinId" type="text" placeholder="Search Within..." onfocus="this.placeholder = ''" onblur="this.placeholder = 'Search Within...'" />
		                                        <input type="submit" id="searchWithinIdBtn" value="Submit" onclick="searchWithinProfiles();return false;"/>
												<input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchWithinId').val(''); $('#searchWithinIdBtn').click();" />
		                                    </fieldset>
		                                </form>
		                            </div>
		                            <div id="profile_page_container_div">
		                                <jsp:include page="profile_table_fragment.jsp" />
		                            </div>
		                        </div>
		                    </div>
	
	               		 </div>
	           		 </div>				
				</div>
				<div id="tab2">
					<jsp:include page="profile_group_management.jsp" />
				</div>
            </div>
            </div>
            <div id="sidebar">
                <ul class="sub-nav">
                    <li><a href="vroozi" class="ico-back"><span><spring:message code="com.adminui.profile_management.BACK" text="default text" /></span></a></li>
                    <c:if test="<%=aclManager.allow(request, Permission.CREATE_CONTENT_VIEW)%>">
                    	<div id="btnTab1">
	                        <li style="display: none;" id="createContent">
	                        	<a href='<c:url value="/createProfile" />' class="ico-profile alt-opener">
	                        		<span id="crtLable"><spring:message code="com.adminui.profile_management.CREATECONTENTVIEW" text="default text" /></span>
	                        	</a>
	                        </li>
                        </div>
                        <div id="btnTab2" style="display:none;" class="checkboxReset">
                        	<li id="createContentGroup"><a href='<c:url value="/createProfileGroup" />' class="ico-profile alt-opener"><span class="checkboxReset" style="width: 170px"><spring:message code="com.adminui.profile_management.CREATECONTENTGROUP" text="default text" /></span></a></li>
                        </div>
                    </c:if>
                    
                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    try{

	   	var normal = $(':hidden.normal');
	    if (normal.length) {
	    	var index = (normal.val()=='2')?1:0;
	    	$('.ui-tab-section').tabs({ selected: index });
	    }else {
	    	$('.ui-tab-section').tabs();
	    }

		
	    if(normal.val()=='2'){
			  $('#btnTab2').show();
 			  $('#btnTab1').hide();
		}else{
			  $('#btnTab1').show();
	 		  $('#btnTab2').hide();
		}
        
    	$('.ui-tab-section').bind('tabsselect', function(event, ui) {
	 		   if(ui.index == 1) {
	 			  $('#btnTab2').show();
	 			  $('#btnTab1').hide();

	 		   } else {
	 			  $('#btnTab1').show();
		 		  $('#btnTab2').hide();
	 		   }
	 		  
	 	   });
	   
    }catch(exp){
        //console.log(exp);
    }
</script>
