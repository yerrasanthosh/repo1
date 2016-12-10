<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="icon" type="image/x-icon" href="../../../res/images/favicon.ico">
<link rel="shortcut icon" href="../../../res/images/favicon.ico" type="image/x-icon" />

<html lang="en">
<head>
    <link rel="icon" type="image/x-icon" href="../../../res/images/favicon.ico">
    <link rel="shortcut icon" href="../../../res/images/favicon.ico" type="image/x-icon" />
    <meta charset="utf-8">
    <title>Log in</title>
    <link rel="icon" href="../favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="../favicon.ico" type="image/x-icon" />
    <link media="all" rel="stylesheet" type="text/css" href="res/css/jquery-ui-1.8.23.custom.css" />
    <link media="all" rel="stylesheet" type="text/css" href="res/css/login.css" />
    <link media="all" rel="stylesheet" type="text/css" href="res/css/all.css" />
    <script type="text/javascript" src="res/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="res/js/jquery-ui-1.8.23.custom.min.js"></script>
    <script type="text/javascript" src="res/js/login.js"></script>

    <script type="text/javascript">
        var authenticateUrl = '<c:url value="/authenticate" />';
        var forgotPassowrdUrl = '<c:url value="/forgotpassword" />';
        </script>

</head>
<body>
<div id="content-wrapper">
    <div id="header">
        <div id="logo">
            <a href="#"><img alt="smartOCI" src="res/images/logo.png"></a>
        </div>
    </div>
    <div id="horizonial-bar" class="ui-state-default ui-corner-tl ui-corner-tr"></div>
    <div id="login-wrapper" class="ui-corner-bl ui-corner-br">
        <div id="login-block" class="ui-corner-all">
            <div id="block-left" class="ft-left">
                <h3 class="ui-state-default ui-corner-all"> <spring:message code="com.adminui.login.ContactInformation" text="default text" /></h3>
                <div style="margin: 15px 0 0 10px;">
                    <label> <spring:message code="com.adminui.login.Address" text="default text" /></label> <br />
                    Vroozi, Inc.<br />
                    15000 Ventura Blvd<br />
                    Suite 201<br />
                    Sherman Oaks, CA 91403<br /><br />
					<br />
					
                    <!-- <label>Phone:</label> (818) 222-9195<br />
                    <label>Fax:</label> (818) 222-9197<br /><br /> -->

                    <label>Email:</label> <a href="mailto:info@smartoci.com">info@smartoci.com</a><br /><br />
                    <img border="0" alt="" src="res/images/sap-contact.png" />
                </div>
            </div>



            <div id="block-main" class="ft-left">
                <h3 class="ui-state-default ui-corner-all"> <spring:message code="com.adminui.login.Login" text="default text" /></h3>
                <div id="login-form">
                    <form name="loginForm" action="<c:url value='/authenticate'/>" method='POST'>

                    <p class="desc"></p>
                    <ul class="field-set">
                        <li class="label ft-left txt-h-right"> <spring:message code="com.adminui.login.Username" text="default text" /></li>
                        <li class="ft-left"><input type="text" name='username' id="username" /></li>
                    </ul>
                    <ul class="field-set">
                        <li class="label ft-left txt-h-right"> <spring:message code="com.adminui.login.Password" text="default text" /></li>
                        <li class="ft-left"><input type="password" name='password' id="password" /></li>
                    </ul>
                    <ul class="field-set">
                        <li class="label ft-left txt-h-right"> <spring:message code="com.adminui.login.CompanyCode" text="default text" /></li>
                        <li class="ft-left"><input type="text" name='unitId' id="unitId" /></li>
                    </ul>
                    <ul class="field-set">
                        <li class="ft-left txt-h-right" style="width: 262px;">
                            <button  id="btn-login"  > <spring:message code="com.adminui.login.Login" text="default text" /></button>
                        </li>
                    </ul>
                    <ul class="field-set">
                        <li class="ft-left txt-h-right" style="width: 260px;">
                            <!--a href="#" id="forgot-my-password">I forgot my password</a-->
                        </li>
                    </ul>
                    </form>
                </div>

                <div id="forgot-passwd">
                    <form name="forgotPasswordForm" action="<c:url value='/forgotPassword'/>" method='POST'>
                    <p class="desc clear">
                        <spring:message code="com.adminui.login.IhaveforgottenmysmartOCIpassword" text="default text" /><br />
                        <spring:message code="com.adminui.login.sendmeNew" text="default text" />
                    </p>
                    <ul class="field-set">
                        <li class="label ft-left txt-h-right"> <spring:message code="com.adminui.login.Username" text="default text" /></li>
                        <li class="ft-left"><input type="text" name ="username" name id="username0" /></li>
                    </ul>
                    <ul class="field-set">
                        <li class="label ft-left txt-h-right"> <spring:message code="com.adminui.login.CompanyCode" text="default text" /></li>
                        <li class="ft-left"><input type="text" name ="unitId" id="companyCode0" /></li>
                    </ul>
                    <ul class="field-set">
                        <li class="ft-left txt-h-right" style="width: 275px;">
                            <button id="btn-send"> <spring:message code="com.adminui.login.Send" text="default text" /></button>
                        </li>
                    </ul>
                    <ul class="field-set">
                        <li class="ft-left txt-h-right" style="width: 275px;">
                            <a href="/login" id="remember-it"> <spring:message code="com.adminui.login.waitToRem" text="default text" /></a>
                        </li>
                    </ul>
                    </form>

                </div>
                <div id="msg-box" class="clear">
                </div>
                <c:if  test="${not empty error}">
                    <div id="error-box" class="errorblock">
                        <c:out value="${error}"/>
                    </div>
                </c:if>

            </div>

            <div id="block-right" class="ft-left txt-h-center">
                <!--a href="#"><img border="0" alt="" src="res/images/connect-with-smartoci.png" /></a-->

            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>