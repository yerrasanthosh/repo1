<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="main-holder">
    <div id="content">

        <div class="content-block toggle-block active" id="create-user-view">
            <div class="row" id="theVendorId">
                <label for="system-vendor-edit"><spring:message code="com.adminui.system_vendor_view.VendorId" text="default text" /></label>
                <input type="text" name ="vendorNumber" id="system-vendor-edit" value="${vendorIdDetails.vendorNumber}" class="required" />

            </div>
            <div class="row" id="thesystemId">
                <label for="system-sytem-edit"><spring:message code="com.adminui.system_vendor_view.SystemId" text="default text" /></label>
                <input type="text" name ="systemId" id="system-sytem-edit" value="${vendorIdDetails.systemId}" class="required" />

            </div>
            <div class="row" id="theClientId">
                <label for="system-client-edit"><spring:message code="com.adminui.system_vendor_view.ClientId" text="default text" /></label>
                <input type="text" name ="clientId" id="system-client-edit" value="${vendorIdDetails.clientId}" class="required" />

            </div>
            <input name="vendorIdOld" type="hidden" value="${vendorIdDetails.vendorNumber}" />
            <input name="systemIdOld" type="hidden" value="${vendorIdDetails.systemId}" />
            <input name="clientIdOld" type="hidden" value="${vendorIdDetails.clientId}" />
            <input name="vendorDuns" type="hidden" value="${vendorIdDetails.vendorDuns}" />
            <input name="active" type="hidden" value="${vendorIdDetails.active}" />
            <input name="row" type="hidden" value="${vendorIdDetails.row}"/>
            <input name="vendorName" type="hidden" value="${vendorIdDetails.vendorName}"/>

        </div>

    </div>
</div>

<script type="text/javascript">


</script>
