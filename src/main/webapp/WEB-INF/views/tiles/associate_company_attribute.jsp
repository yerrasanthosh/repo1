<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<thead>
<tr>
    <th class="td-select"><input type="checkbox" class="check-allbox79" name="check-all4" id="check43-1" /><label for="check43-1"></label></th>
    <th class="a-left"><a class="sorting" href="#"><spring:message code="com.adminui.associate_attachments.Name" text="default text" /></a></th>
    <th><spring:message code="com.adminui.associate_attachments.Description" text="default text" /></th>
    <th><spring:message code="com.adminui.associate_attachments.Active" text="default text" /></th>

</tr>
</thead>
<tbody>
    <% int supplierCounter = 0; %>
    <c:forEach var="attribute" items="${attributsForCompany}" varStatus="cntr1">
        <tr>
            <td class="td-select"><input type="checkbox" name="supplierIds" id="${attribute.attributeId}" value="${attribute.attributeId}" /><label for="${attribute.attributeId}"></label></td>
            <td class="a-left td-description">
                <div class="field" style="width:150px">
                        <%--<span><a href="edit-supplier?companyId=${supplier.companyId}&companyName=${supplier.companyName}">${supplier.companyName}</a></span>--%>
                    <a class="btn-add-custom-fields open-popup" onclick="getAttributeDetails('${attribute.attributeId}','${attribute.unitId}')"><span>${attribute.attributeName}</span></a>
                        <%--<span><a href="">${attribute.attributeName}</a></span>--%>
                    <input type="text" name="" class="ico-edit alt-opener" value="${attribute.attributeName}" />
                </div>
            </td>
            <td>
                <div class="field" style="width:150px">
                    <span>${attribute.attributeDescription}</span>
                    <input type="text" value="${attribute.attributeDescription}" />
                </div>
            </td>

            <td class="td-last">${attribute.active==true?'Yes':'No'}</td>
        </tr>
        <% ++supplierCounter; %>
    </c:forEach>
    </tbody>
