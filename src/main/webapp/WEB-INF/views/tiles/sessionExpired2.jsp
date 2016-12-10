<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=windows-1252" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en-US" xml:lang="en-US">

<%
    String url = request.getRequestURL().toString();
    url = url.substring(0, url.lastIndexOf("/oci")).replaceAll("https", "http");
%>

<head>
    <title>Welcome to smartOCI</title>

    <link rel="stylesheet" href="templates/system/css/system.css" type="text/css"/>
    <link rel="stylesheet" href="templates/system/css/general.css" type="text/css"/>
    <link rel="stylesheet" href="templates/smart-oci/css/template.css" type="text/css"/>
    <link rel="stylesheet" href="templates/smart-oci/css/print.css" type="text/css" media="Print"/>
    <link href="<%=url%>/templates/smart-oci/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
    <!--[if lte IE 6]>
    <link href="templates/smart-oci/css/ieonly.css" rel="stylesheet" type="text/css"/>
    <![endif]-->
    <!--[if IE 7]>
    <link href="templates/smart-oci/css/ie7only.css" rel="stylesheet" type="text/css"/>
    <![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

</head>

<body style="overflow:auto">

<div id="content-wrapper">
    <a name="top"></a>

    <div id="header">
        <div id="logo">

            <img src="res/images/smart-oci-logo.png" alt="smartOCI"/>
        </div>

        <div id="search">
            <div class="moduletable">
                <div><a id="clientLogin" href="login"><img src="res/images/stories/log-in.png" border="0" /></a></div>
            </div>
        </div>

    </div>

    <div id="left-hor-nav"></div>
    <div id="horizonial-nav">
        <div class="moduletable_menu">
            <ul class="menu">
                <li class="item1"></li>
            </ul>
        </div>

    </div>
    <div id="right-hor-nav"></div>


    <div id="content">
        <div class="clear"></div>
        <div id="content-contanier">

            <div id="content-top-left"></div>
            <div id="content-top"></div>
            <div id="content-top-right"></div>


            <div id="content-inner-contanier">

                <div id="left-column">
                    <div class="moduletable">
                        <div class="left-contact" style="width: 172px; margin-left: 11px;"><br/>

                            <div style="padding-left: 10px;"><span class="headline"></span><br/><br/><br/><span
                                    class="blue-contact"></span><br/><br/></div>
                            <div style="padding-left: 10px;"></div>
                            <div style="padding-left: 10px;"><br/><span class="blue-contact"></span></div>
                            <div style="padding-left: 10px;"><span class="blue-contact"></span><br/><br/><span
                                    class="blue-contact"> </span><span
                                    style="color: #758fa1; padding-bottom:10px;"></span></div>
                            <br/></div>
                        <!--p style="margin-left:11px;"><img src="images/stories/sap-contact.png" border="0" /></p-->
                    </div>

                </div>

                <div id="maincol2">

                    <table class="contentpaneopen" width="100%">

                        <tr>
                            <td valign="top">
                                <div>
                                     style="padding-top: 15px;padding-left:10px;text-align:left;font-weight:bold;">

                                    <span class="headline"><spring:message code="com.adminui.sessionExpired2.sessionExpired" text="default text" /></span>

                                </div>

                            </td>
                        </tr>
                        <tr>
                            <td valign="top">
                                <p>

                                <div style="width:100%; height:10px; background-image: url('res/images/support-bg.jpg'); background-repeat:repeat-x;"></div>
                                </p>
                            </td>
                        </tr>
                        <tr>
                            <td valign=top width="50%">
                                <table>
                                    <tr>
                                        <td align=right>
                                            <div id="authform"
                                                 style="padding-top: 15px;padding-left:10px;text-align:left;">
                                                <spring:message code="com.adminui.sessionExpired2.msg" text="default text" />
                                                <br> <br>
                                                <spring:message code="com.adminui.sessionExpired2.detail" text="default text" />
                                                <br> <br>
                                                <a href="/oci/HeartBeat"><spring:message code="com.adminui.sessionExpired2.detail2" text="default text" /></a>
                                            </div>
                                        </td>
                                    </tr>
                                </table>

                            </td>
                        </tr>

                    </table>

                </div>

                <div class="clear"></div>
            </div>


            <div id="content-bottom-left"></div>
            <div id="content-bottom"></div>
            <div id="content-bottom-right"></div>

            <div class="clear"></div>

        </div>
    </div>
    <div id="content-wraper-bottom"></div>

    <div id="footer">

        <div id="custom-footer">
            <div class="moduletable">
                <div style="padding-top: 15px;padding-left:10px;text-align:center;">2011 © All rights reserved.  Netsol Technologies, Inc
                </div>
            </div>
            <div class="moduletable" style="height:60px;">
                <table border="0" align="center">
                    <tbody>
                    <tr>
                        <td>
                            <p style="text-align: center;"><a href="<%=url%>/index.php"><img
                                    src="images/stories/smart-oci-logo-small.png" border="0"/></a></p>
                        </td>
                        <td><a href="http://www.netsoltech.com" target="_blank"><img
                                src="images/stories/net-sol-logo.png" border="0"/></a></td>
                        <td><a href="http://www.facebook.com/smartoci" target="_blank" title="Facebook"><img
                                src="images/stories/Facebook-icon.png" border="0" width="33" height="33"/></a>
                        </td>
                        <td><a href="http://www.twitter.com/smartoci" target="_blank" title="Twitter"><img
                                src="images/stories/Twitter-icon.png" border="0" width="33" height="33"/></a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>
</body>
</html>


