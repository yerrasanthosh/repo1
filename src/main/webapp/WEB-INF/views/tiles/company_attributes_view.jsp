<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="main-holder">
    <div id="content">

        <div class="content-block toggle-block active" id="create-attribute-view">
            <div class="row" id="theEditAttributeId">
                <label for="attribute-id-edit"><spring:message code="com.adminui.company_attributes_view.Name" text="default text" /></label>
                <input type="text" name ="attributeName" id="attribute-id-edit" value="${companyAttributeDetails.attributeName}" class="required"/>
            </div>
            <div class="row" id="theEditCompanyName">
                <label for="discription-name-edit"><spring:message code="com.adminui.company_attributes_view.Description" text="default text" /></label>
                <input type="text" name ="attributeDescription" id="discription-name-edit" value="${companyAttributeDetails.attributeDescription}" class="required"/>

            </div>
            <%--<div class="row" id="theEditEmailAdd">--%>
                <%--<label for="logo-add-edit"><span>*</span>Logo:</label>--%>
                <%--<input type="text" name ="logo" value="" id="logo-add-edit" class="required" disabled="disabled"/>--%>

            <%--</div>--%>

            <input name="active" type="hidden" value="${companyAttributeDetails.active}" />
            <input name="unitId" type="hidden" value="${companyAttributeDetails.unitId}" />
            <input name="attributeId" type="hidden" value="${companyAttributeDetails.attributeId}" />

        </div>

    </div>
</div>

<script type="text/javascript">


</script>
