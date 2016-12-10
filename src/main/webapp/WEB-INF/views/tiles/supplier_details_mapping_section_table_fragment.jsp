<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<thead id="supplier_mapping_table_head">
  <tr>
     <th class="td-select">
		 <input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="supplierMapping-checkall" onclick="toggleAllCheckboxItems(this.checked,'supplier-mapping-form');" />
         <label for="supplierMapping-checkall" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
     </th>
     <th style="width: 255px;"><spring:message code="com.adminui.supplier_details_mapping_section_table_fragment.SUPPLIERID" text="default text" /></th>
     <th style="width: 315px;"><spring:message code="com.adminui.supplier_details_mapping_section_table_fragment.UNIQUESYSTEMID" text="default text" /></th>
 </tr>
 </thead>
 <tbody>
 <c:forEach var="supplierMapping" items="${supplierMappings}" varStatus="cntr1">
     <tr>
         <td class="td-select">
             <input  id="supp-${supplierMapping.row}"  type="checkbox" class="ui-helper-hidden-accessible" name="supplierMappingIds" value="${supplierMapping.row}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
             <label for="supp-${supplierMapping.row}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
         </td>
         <td>
             <input type="hidden" value="${supplierMapping.vendorNumber}" id="vend-${supplierMapping.row}" />
             <a href="#info-section" onclick="editTableRow('supplier_mapping_table','${supplierMapping.row}');">${supplierMapping.vendorNumber}</a>
         </td>
         <td>
         	<input type="hidden" value="${supplierMapping.uniqueSystemId}" id="sysid-${supplierMapping.row}" />
            ${supplierMapping.uniqueSystemId}
         </td>
     </tr>
 </c:forEach>
		<input type="hidden" id="editModeOn" value="" />
		<input type="hidden" id="addModeOn" value="" />
		<input type="hidden" id="editRowId" value="" />
 </tbody>