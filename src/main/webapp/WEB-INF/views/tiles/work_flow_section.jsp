<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row">
    <input type="checkbox" <c:if test='${companySettings.requiredField}'>checked</c:if> name="requiredField" class="target-chbox" id="requiredField" /><label for="requiredField"></label>
    <label class="alt-label" style="width: 350px;padding: 3px;"><spring:message code="com.adminui.work_flow_section.forAnyChange" text="default text" /></label>
</div>
<div class="row">
    <input type="checkbox" <c:if test='${companySettings.itemAdded}'>checked</c:if> name="itemAdded" class="target-chbox" id="itemAdded" /><label for="itemAdded"></label>
    <label class="alt-label" style="width: 300px;padding: 3px;"><spring:message code="com.adminui.work_flow_section.forAnyNew" text="default text" /></label>
</div>
<div class="row">
    <input type="checkbox" <c:if test='${companySettings.priceChange}'>checked</c:if> name="priceChange" class="target-chbox" id="priceChange" /><label for="priceChange"></label>
    <label class="alt-label" style="width: 178px;padding: 3px;"><spring:message code="com.adminui.work_flow_section.ifPrice" text="default text" /> </label><input type="text" name="percentChanged" value="${companySettings.percentChanged}" id="field-name" class="input2" style="width: 30px;margin: 0;"/><label class="alt-label" style="width: 195px;padding: 3px;">percent in catalogs and/or quotes</label>
</div>