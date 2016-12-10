<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="com.adminui.download.SmartOCI" text="default text" /></title>

<script>
	function download() {
		window.location = "downloadcatalog.csv"+location.search;
		//window.setTimeout(function() {window.location = 'vroozi';}, 1000);
	}
</script>

</head>
<body onload="download();">
<a href="vroozi"><spring:message code="com.adminui.download.msg" text="default text" /></a>
</body>
</html>
