<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="main-holder">
    <div id="content">

        <div class="content-block toggle-block active" id="create-user-view">
            <div class="row" id="theEditRoleId">
                <label for="role-id-edit"><spring:message code="com.adminui.company_user_view.Role" text="default text" /></label>
                <input type="text" name ="role" value="Supplier Admin" id="role-id-edit" class="required" disabled="disabled"/>
            </div>
            <div class="row" id="theEditCompanyName">
                <label for="company-name-edit"><spring:message code="com.adminui.company_user_view.CompanyName" text="default text" /></label>
                <input type="text" name ="companyName" id="company-name-edit" value="${companyUserDetails.companyName}" class="required" disabled="disabled"/>

            </div>
            <div class="row" id="theEditEmailAdd">
                <label for="email-add-edit"><span>*</span><spring:message code="com.adminui.company_user_view.EmailAdd" text="default text" /></label>
                <input type="text" name ="emailAddress" value="${companyUserDetails.emailAddress}" id="email-add-edit" class="required"/>

            </div>
            <div class="row" id="theEditFirstName">
                <label for="first-name-edit"><span>*</span><spring:message code="com.adminui.company_user_view.FirstName" text="default text" /></label>
                <input type="text" name ="firstName" value="${companyUserDetails.firstName}" id="first-name-edit" class="required"/>

            </div>
            <div class="row" id="theEditLastName">
                <label for="last-name-edit"><span>*</span><spring:message code="com.adminui.company_user_view.LastName" text="default text" /></label>
                <input type="text" name ="lastName" id="last-name-edit" value="${companyUserDetails.lastName}" class="required"/>

            </div>
            <input name="supplierId" type="hidden" value="${companyUserDetails.supplierId}" />
            <input name="row" type="hidden" value="${companyUserDetails.companyUserId}" />
            <input name="unitId" type="hidden" value="${companyUserDetails.unitId}" />
        </div>

    </div>
</div>

<script type="text/javascript">


</script>
