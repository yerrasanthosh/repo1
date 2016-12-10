<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<?xml version='1.0' encoding='UTF-8'?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    String unitId = "";
    String userId = "";
    HttpSession existingSession = request.getSession(false);
    if (null!=existingSession) {
        if (existingSession.getAttribute("user")!=null) {
            User loggedInUser = (User)existingSession.getAttribute("user");
            unitId = loggedInUser.getUnitId();
            userId = loggedInUser.getUserId();
        }
    }
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>SmartOCI</title>
    <link media="all" rel="stylesheet" type="text/css" href="res/css/colorbox.css" />
    <link media="all" rel="stylesheet" type="text/css" href="res/css/all-landing.css" />
    <script type="text/javascript" src="res/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="res/js/landing/jcf.js"></script>
    <script type="text/javascript" src="res/js/landing/jcf.scrollable.js"></script>
    <script type="text/javascript" src="res/js/jquery.main.js"></script>
    <script type="text/javascript" src="res/js/landing/jcf.select.js"></script>
    <script type="text/javascript" src="res/js/landing/jcf.file.js"></script>
    <script type="text/javascript" src="res/js/underscore.js"></script>
    <script type="text/javascript" src="res/js/backbone.js"></script>
    <script type="text/javascript">
        var unitId = '<%=unitId%>';
        var hideAnnouncementURI = '<c:url value="/hide-announcment" />';
        function displayLightBox(element){

            var date = jQuery(element).find(".date1").html()
            var mess = jQuery(element).find(".mess1").html()

//            alert(element.children[0].innerHTML);

//            alert(element.children[0].innerHTML);
//            alert(element.children[1].innerHTML);

            if(date.indexOf("null") > 0){
                $(".row1").html("");
            } else {
                $(".row1").html(date);
            }


            $(".row").html(mess);
            $.colorbox({
                href:'#annLightBox',
                inline:true,
                onComplete: function(){
                    $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                        $.colorbox.close();
                        return false;
                    });
                }
            });
            $("#cboxClose").hide();
        }

        function hideAnnouncement(announcementId, userId){
            deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', 
                function () {
                    hideAnnouncementById(announcementId, userId);
                });
        }
        function hideAnnouncementById(announcementId, userId){
        	try{
                $.ajax({
                    type:'POST',
                    url: hideAnnouncementURI+'?aid='+announcementId+'&userid='+userId,
                    data: '',
                    success: function(response) {
                    	updateCards();
                    }
                });
            }catch(exp){
                alert(exp);
            }
            return false;
        }
        
        function openCatalogPage(){
            this.parent.window.location = "catalog";
        }

        function activeSupplierPage(){
            this.parent.window.location = "activeSuppliers";
        }

        function inactiveSupplierPage(){
            this.parent.window.location = "inactiveSuppliers";
        }

        function masterAdminUsersPage(){
            this.parent.window.location = "user_management_master_admins";
        }

        function adminUsersPage(){
            this.parent.window.location = "user_management_admins";
        }

        function buyersPage(){
            this.parent.window.location = "user_management_buyers";
        }

        function approversPage(){
            this.parent.window.location = "user_management_approvers";
        }

        function shoppersPage(){
            this.parent.window.location = "user_management_shoppers";
        }

    </script>
    <script type="text/javascript" src="res/js/landing.js"></script>
    <!--[if lt IE 9]><link rel="stylesheet" type="text/css" href="res/css/ie.css" /><![endif]-->

</head>
<body class="admin-page">
<!-- wrapper -->
<div id="wrapper">

    <!-- main -->
    <div id="main">
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_ANNOUNCEMENT)%>">
            <!-- announce block -->
            <div id="announce-card" class="announce-block">
                <div class="title">
                    <h2><spring:message code="com.adminui.landing_frame.Announcements" text="default text" /></h2>
                </div>
                <!-- div id="announcements-area" class="scrollable-area" -->
                    <div class="scrollable-area" id="announcements-area" >
                        <!-- Announcements go here -->
                    </div>
            </div>
        </c:if>

        <!-- container -->
        <c:if test="<%=aclManager.allow(request, Permission.VIEW_HOME_CARDS)%>">
            <div class="container">
                <div class="column">
                    <!-- content block -->
                    <div class="content-block">
                        <div class="title">
                            <%--<div class="edit-btn">--%>
                                <%--<a href="#" class="btn"></a>--%>
                                <%--<div class="drop">--%>
                                    <%--<ul>--%>
                                        <%--<li><a href="#">Edit Settings</a></li>--%>
                                        <%--<li><a href="#">Delete this Card</a></li>--%>
                                        <%--<li><a href="#">Minimize this Card</a></li>--%>
                                        <%--<li><a href="#">Maximize this Card</a></li>--%>
                                        <%--<li><a href="#">Share this Card</a></li>--%>
                                        <%--<li><a href="#">You might also like...</a></li>--%>
                                        <%--<li><a href="#">About this Card</a></li>--%>
                                    <%--</ul>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <h2><a href="#" class="ico-summary"><spring:message code="com.adminui.landing_frame.SUMMARY" text="default text" /></a></h2>
                        </div>
                        <div class="content">
                            <div class="col">
                                <h2><spring:message code="com.adminui.landing_frame.Catalog" text="default text" /></h2>
                                <ul>
                                    <li style="cursor: pointer;" onclick="openCatalogPage();"><spring:message code="com.adminui.landing_frame.TotalCatalogs" text="default text" /> <span id="totalCatalogsCount"></span></li>
                                    <li style="cursor: pointer;" onclick="openCatalogPage();"><span id="approvalWaitCatalogsCount"></span> <spring:message code="com.adminui.landing_frame.AwaitingApproval" text="default text" /></li>
                                    <li style="cursor: pointer;" onclick="openCatalogPage();"><span id="rejectedCatalogsCount"></span> <spring:message code="com.adminui.landing_frame.RejectedCatalogs" text="default text" /></li>
                                    <li style="cursor: pointer;" onclick="openCatalogPage();"><span id="errorCatalogsCount"></span> <spring:message code="com.adminui.landing_frame.CatalogswithErrors" text="default text" /></li>
                                    <li style="cursor: pointer;" onclick="openCatalogPage();"><span id="publishWaitCatalogsCount"></span> <spring:message code="com.adminui.landing_frame.CatalogstoPublish" text="default text" /></li>
                                </ul>
                            </div>
                            <div id="divUserInfo" style="display:none" class="col alt-col">
                                <h2><spring:message code="com.adminui.landing_frame.Supplier" text="default text" /></h2>
                                <ul>
                                    <li style="cursor: pointer;" onclick="activeSupplierPage();"><spring:message code="com.adminui.landing_frame.ActiveSuppliers" text="default text" /> <span id="activeSuppliersCount"></span></li>
                                    <li style="cursor: pointer;" onclick="inactiveSupplierPage();"><spring:message code="com.adminui.landing_frame.InactiveSuppliers" text="default text" /> <span id="inactiveSuppliersCount"></span></li>
                                </ul>
                                <h2>User:</h2>
                                <ul>
                                    <li style="cursor: pointer;" onclick="masterAdminUsersPage();"><spring:message code="com.adminui.landing_frame.AdminUsers" text="default text" /><span id="adminUsersCount"></span></li>
   	                                <li style="cursor: pointer;" onclick="shoppersPage();"><spring:message code="com.adminui.landing_frame.Shoppers" text="default text" /> <span id="shopperUsersCount"></span></li>

                                   	<li style="cursor: pointer;" onclick="approversPage();"><spring:message code="com.adminui.landing_frame.Approvers" text="default text" /> <span id="approverUsersCount"></span></li>
									<li style="cursor: pointer;" onclick="buyersPage();"><spring:message code="com.adminui.landing_frame.Buyers" text="default text" /> <span id="buyerUsersCount"></span></li>

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                    <%--	<div class="column">
                            <!-- content block -->
                            <div class="content-block">
                                <div class="title">
                                    <div class="edit-btn">
                                        <a href="#" class="btn"></a>
                                        <div class="drop">
                                            <ul>
                                                <li><a href="#">Edit Settings</a></li>
                                                <li><a href="#">Delete this Card</a></li>
                                                <li><a href="#">Minimize this Card</a></li>
                                                <li><a href="#">Maximize this Card</a></li>
                                                <li><a href="#">Share this Card</a></li>
                                                <li><a href="#">You might also like...</a></li>
                                                <li><a href="#">About this Card</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <h2><a href="#" class="ico-message">MESSAGE CENTER</a></h2>
                                </div>
                                <div class="content">
                                    <div class="headline">
                                        <a href="#" class="btn-refresh">refresh</a>
                                        <a href="#" class="btn-magnify">magnify</a>
                                        <a href="#" class="btn-compose">compose</a>
                                        <h3><a href="#">Inbox (4)</a></h3>
                                    </div>
                                    <div class="form-box">
                                        <div class="scrollable-area">
                                            <form action="#">
                                                <fieldset>
                                                    <ul class="form-list">
                                                        <li>
                                                            <input type="checkbox" name="check" id="check1" />
                                                            <label for="check1"><strong>A message from Shari Manor</strong>Subject: Which laptop model  is assigned for sales team members?</label>
                                                        </li>
                                                        <li class="mark">
                                                            <input type="checkbox" name="check" id="check2" />
                                                            <label for="check2"><strong>A message from  Mark Dougherty</strong>Subject:  Price negotiation on bulk purchases</label>
                                                        </li>
                                                        <li>
                                                            <input type="checkbox" name="check" id="check3" />
                                                            <label for="check3"><strong>5 Catalogs have been loaded</strong>Grainger, Sysco, Dell, TotallyClean, and Panera have been loaded</label>
                                                        </li>
                                                        <li class="mark">
                                                            <input type="checkbox" name="check" id="check4" />
                                                            <label for="check4"><strong>Catalog, OfficeMax, has an update </strong>16 items with price change > 5%</label>
                                                        </li>
                                                    </ul>
                                                </fieldset>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                --%>
             </div>
        </c:if>

                    <!-- cases-holder -->
            <%--		<div class="cases-holder">
                        <!-- case -->
                        <div class="case">
                            <!-- content block -->
                            <div class="content-block">
                                <div class="title">
                                    <div class="edit-btn">
                                        <a href="#" class="btn"></a>
                                        <div class="drop">
                                            <ul>
                                                <li><a href="#">Edit Settings</a></li>
                                                <li><a href="#">Delete this Card</a></li>
                                                <li><a href="#">Minimize this Card</a></li>
                                                <li><a href="#">Maximize this Card</a></li>
                                                <li><a href="#">Share this Card</a></li>
                                                <li><a href="#">You might also like...</a></li>
                                                <li><a href="#">About this Card</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <h2><a href="#" class="ico-catalog">Catalog</a></h2>
                                </div>
                                <div class="content">
                                    <form action="#" class="add-search-form">
                                        <fieldset>
                                            <input type="text" value="Search Within..." />
                                            <input type="submit" value="Submit" />
                                        </fieldset>
                                    </form>
                                    <ul class="function function-small">
                                        <li class="active"><a href="#">Working <span>5</span></a></li>
                                        <li><a href="#">Approved <span>3</span></a></li>
                                        <li><a href="#">Live <span>4</span></a></li>
                                    </ul>
                                    <div class="thead">
                                        <span class="td">Catalog Name</span>
                                        <span class="td a-center">Last Updated</span>
                                    </div>
                                    <div class="table-holder">
                                        <div class="scrollable-area">
                                            <table>
                                                <tr>
                                                    <td><a href="#">Grainger</a></td>
                                                    <td class="a-center">11/13/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Sysco </a></td>
                                                    <td class="a-center">11/13/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Dell</a></td>
                                                    <td class="a-center">11/13/2012</td>
                                                </tr>

                                            </table>
                                        </div>
                                    </div>
                                    <div class="tfoot">
                                        <a href="#" class="btn-create-new"><em>+</em> Create New</a>
                                        <strong>TOTAL: 12</strong>
                                    </div>
                                    <div class="thead">
                                        <span>Currently Being Processed</span>
                                    </div>
                                    <ul class="add-list">
                                        <li><a href="#">Hardware and Fasteners</a> - Pending</li>
                                        <li><a href="#">Air Tools</a> - Published</li>
                                        <li><a href="#">Craftsman Tools</a> - Approved</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- case -->
                        <div class="case">
                            <!-- content block -->
                            <div class="content-block">
                                <div class="title">
                                    <div class="edit-btn">
                                        <a href="#" class="btn"></a>
                                        <div class="drop">
                                            <ul>
                                                <li><a href="#">Edit Settings</a></li>
                                                <li><a href="#">Delete this Card</a></li>
                                                <li><a href="#">Minimize this Card</a></li>
                                                <li><a href="#">Maximize this Card</a></li>
                                                <li><a href="#">Share this Card</a></li>
                                                <li><a href="#">You might also like...</a></li>
                                                <li><a href="#">About this Card</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <h2><a href="#" class="ico-supplier">Supplier</a></h2>
                                </div>
                                <div class="content">
                                    <form action="#" class="add-search-form">
                                        <fieldset>
                                            <input type="text" value="Search Within..." />
                                            <input type="submit" value="Submit" />
                                        </fieldset>
                                    </form>
                                    <ul class="function">
                                        <li class="active"><a href="#">Active <span>10</span></a></li>
                                        <li><a href="#">Inactive <span>2</span></a></li>
                                    </ul>
                                    <div class="thead">
                                        <span class="td">Supplier List</span>
                                        <span class="td a-center">Last Activity</span>
                                    </div>
                                    <div class="table-holder">
                                        <div class="scrollable-area">
                                            <table>
                                                <tr>
                                                    <td><a href="#">Dell</a></td>
                                                    <td class="a-center">11/13/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Acme Industries</a></td>
                                                    <td class="a-center">10/07/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Home Depot </a></td>
                                                    <td class="a-center">09/28/2012</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="tfoot">
                                        <a href="#" class="btn-create-new"><em>+</em> Create New</a>
                                        <strong>TOTAL: 12</strong>
                                    </div>
                                    <div class="thead">
                                        <span>Latest Active Suppliers</span>
                                    </div>
                                    <ul class="add-list">
                                        <li><a href="#">Home Depot</a></li>
                                        <li><a href="#">Ace Supplies and Hardware</a></li>
                                        <li><a href="#">Sears Roebuck Co.</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- case -->
                        <div class="case">
                            <!-- content block -->
                            <div class="content-block">
                                <div class="title">
                                    <div class="edit-btn">
                                        <a href="#" class="btn"></a>
                                        <div class="drop">
                                            <ul>
                                                <li><a href="#">Edit Settings</a></li>
                                                <li><a href="#">Delete this Card</a></li>
                                                <li><a href="#">Minimize this Card</a></li>
                                                <li><a href="#">Maximize this Card</a></li>
                                                <li><a href="#">Share this Card</a></li>
                                                <li><a href="#">You might also like...</a></li>
                                                <li><a href="#">About this Card</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <h2><a href="#" class="ico-users">USERS</a></h2>
                                </div>
                                <div class="content">
                                    <form action="#" class="add-search-form">
                                        <fieldset>
                                            <input type="text" value="Search Within..." />
                                            <input type="submit" value="Submit" />
                                        </fieldset>
                                    </form>
                                    <ul class="function function-small">
                                        <li class="active"><a href="#">Super Admin <span>3</span></a></li>
                                        <li><a href="#">Admin User <span>10</span></a></li>
                                        <li><a href="#">Buyer <span>30</span></a></li>
                                        <li><a href="#">Approver <span>12</span></a></li>
                                        <li><a href="#">Shopper <span>3461</span></a></li>
                                        <li><a href="#">Search User <span>15</span></a></li>
                                    </ul>
                                    <div class="thead">
                                        <span class="td">Catalog Name</span>
                                        <span class="td a-center">Last Updated</span>
                                    </div>
                                    <div class="table-holder table-holder-alt">
                                        <div class="scrollable-area">
                                            <table>
                                                <tr>
                                                    <td><a href="#">Cynthia Dunlop</a><br/>Admin</td>
                                                    <td class="a-center">10/01/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">John Schazenbacher</a><br/>Buyer</td>
                                                    <td class="a-center">10/13/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Rich Chala</a><br/>Super Admin</td>
                                                    <td class="a-center">10/16/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Shaz Kahn</a><br/>Admin</td>
                                                    <td class="a-center">10/16/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Jane Dorothy</a><br/>Admin</td>
                                                    <td class="a-center">11/02/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Tom Jackson</a><br/>Buyer</td>
                                                    <td class="a-center">11/11/2012</td>
                                                </tr>
                                                <tr>
                                                    <td><a href="#">Janie Addison</a><br/>Admin</td>
                                                    <td class="a-center">11/11/2012</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="tfoot">
                                        <a href="#" class="btn-create-new"><em>+</em> Create New</a>
                                        <strong>TOTAL: 3531</strong>
                                    </div>
                                    <div class="thead">
                                        <span>Last Users Logged In</span>
                                    </div>
                                    <ul class="add-list">
                                        <li><a href="#">James Smith</a> - Super Admin</li>
                                        <li><a href="#">Jane Doe</a> - Buyer</li>
                                        <li><a href="#">Michael Smith</a> - Approver</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- case -->
                        <div class="case">
                            <!-- content block -->
                            <div class="content-block">
                                <div class="title">
                                    <div class="edit-btn">
                                        <a href="#" class="btn"></a>
                                        <div class="drop">
                                            <ul>
                                                <li><a href="#">Edit Settings</a></li>
                                                <li><a href="#">Delete this Card</a></li>
                                                <li><a href="#">Minimize this Card</a></li>
                                                <li><a href="#">Maximize this Card</a></li>
                                                <li><a href="#">Share this Card</a></li>
                                                <li><a href="#">You might also like...</a></li>
                                                <li><a href="#">About this Card</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <h2><a href="#" class="ico-systems">SYSTEMS</a></h2>
                                </div>
                                <div class="content">
                                    <h2>Subscribed:</h2>
                                    <ul class="logos">
                                        <li><a href="#" class="logo-sap">SAP</a></li>
                                    </ul>
                                    <h2>Available:</h2>
                                    <ul class="logos logos-alt">
                                        <li><a href="#" class="logo-oracle">Oracle</a></li>
                                        <li><a href="#" class="logo-peoplesoft">PeopleSoft</a></li>
                                        <li><a href="#" class="logo-jd-edwards">JD Edwards</a></li>
                                        <li><a href="#" class="logo-workday">Workday</a></li>
                                        <li><a href="#" class="logo-lawson">Lawson</a></li>


                                    </ul>
                                </div>
                            </div>
                            <!-- content block -->
                            <div class="content-block">
                                <div class="title">
                                    <div class="edit-btn">
                                        <a href="#" class="btn"></a>
                                        <div class="drop">
                                            <ul>
                                                <li><a href="#">Edit Settings</a></li>
                                                <li><a href="#">Delete this Card</a></li>
                                                <li><a href="#">Minimize this Card</a></li>
                                                <li><a href="#">Maximize this Card</a></li>
                                                <li><a href="#">Share this Card</a></li>
                                                <li><a href="#">You might also like...</a></li>
                                                <li><a href="#">About this Card</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <h2><a href="#" class="ico-help">HELP</a></h2>
                                </div>
                                <div class="content">
                                    <ul class="add-list2">
                                        <li><a href="#">View FAQ’s</a></li>
                                        <li><a href="#">Watch Help Videos</a></li>
                                        <li><a href="#">Log an Issue</a></li>
                                        <li><a href="#">Downloads</a></li>
                                        <li><a href="#">Make a Suggestion</a></li>
                                        <li><a href="#">Browse Forum</a></li>
                                        <li><a href="#">Policies</a></li>
                                        <li><a href="#">Contact</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
        --%>

    </div>
</div>

<!--
 -- Content templates for announcements
-->

    <!-- Normal Announcement -->
    <div id="normal-announcement" style="display:none">
        <div onclick="displayLightBox(this);" style="cursor: pointer" class="line">
            <!--a href="#" class="btn-close">close</a-->
            <div class="date1" style="display: none;">{{dateLabel}}: {{date}}</div>
            <div class="mess1" style="display: none;">{{message}}</div>
            <p>{{messagePreview}}</p>
        </div>
        <a onclick="hideAnnouncement({{id}},'{{userId}}')" style="z-index: 5; margin-left: 935px; margin-top: -22px; position: absolute;" class="close1"><spring:message code="com.adminui.landing_frame.Close" text="default text" /></a>
    </div>

    <!-- Success Announcement -->
    <div id="success-announcement" style="display:none">
        <div onclick="displayLightBox(this);" style="cursor: pointer" class="line line-successfully">
            <!--a href="#" class="btn-close">close</a-->
            <div class="date1" style="display: none;">{{dateLabel}}: {{date}}</div>
            <div class="mess1" style="display: none;">{{message}}</div>
            <p>{{messagePreview}}</p>
        </div>
        <a onclick="hideAnnouncement({{id}},'{{userId}}')" style="z-index: 5; margin-left: 935px; margin-top: -22px; position: absolute;" class="close1"><spring:message code="com.adminui.landing_frame.Close" text="default text" /></a>
    </div>

    <!-- Important Announcement -->
    <div id="important-announcement" style="display:none">
        <div onclick="displayLightBox(this);" style="cursor: pointer" class="line line-important">
            <!--a href="#" class="btn-close">close</a-->
            <div class="date1" style="display: none;">{{dateLabel}}: {{date}}</div>
            <div class="mess1" style="display: none;">{{message}}</div>
            <p>{{messagePreview}}</p>
        </div>
        <a onclick="hideAnnouncement({{id}},'{{userId}}')" style="z-index: 5; margin-left: 935px; margin-top: -22px; position: absolute;" class="close1"><spring:message code="com.adminui.landing_frame.Close" text="default text" /></a>
    </div>

    <!-- Attention Announcement -->
    <div id="attention-announcement" style="display:none">
        <div onclick="displayLightBox(this);" style="cursor: pointer" class="line line-attention">
            <!--a href="#" class="btn-close">close</a-->
            <div class="date1" style="display: none;">{{dateLabel}}: {{date}}</div>
            <div class="mess1" style="display: none;">{{message}}</div>
            <p>{{messagePreview}}</p>
        </div>
        <a onclick="hideAnnouncement({{id}},'{{userId}}')" style="z-index: 10; margin-left: 935px; margin-top: -22px; position: absolute;" class="close1"><spring:message code="com.adminui.landing_frame.Close" text="default text" /></a>
    </div>

</body>
</html>

<div style="display: none;" class="lightbox-section">
    <div class="lightbox" id="annLightBox">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <%--<a href="#" class="close">Close</a>--%>
                    <h2 style="margin-top: 5px;"><spring:message code="com.adminui.landing_frame.Announcement" text="default text" /></h2>
                </div>
                <form method="post" id="annForm">
                    <fieldset>
                        <div class="area">
                            <div style="width: 940px !important; margin-bottom: 5px;" class="row1">
                            </div>
                            <div style="width: 940px !important;" class="row">
                            </div>
                        </div>
                    </fieldset>
                    <div class="btns-holder" style="margin-top: 5px;">
                        <a class="btn-cancel" href="#"><spring:message code="com.adminui.landing_frame.Close" text="default text" /></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-common">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteCloseBtn" href="#" class="close"><spring:message code="com.adminui.landing_frame.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.landing_frame.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p class="deletion-message"><spring:message code="com.adminui.landing_frame.confirmDel" text="default text" /></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.landing_frame.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.landing_frame.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>