<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>
<%@page import="com.vroozi.customerui.supplier.model.Supplier"%>
<%--<jsp:include page="create_vendor_id_overlay.jsp"/>--%>

<script type="text/javascript">
    <%--var createSupplierServiceUrl = '<c:url value="/addSupplier" />';--%>
</script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style type="text/css">
.labelProp{
    width: 152px !important;
}

.inputProp{
    width: 133px !important;
}
.alt-select-supplier {width:219px;}
</style>

<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.edit_supplier.Vroozi" text="default text" /></a></li>
            <li><a href="catalog"><spring:message code="com.adminui.edit_supplier.ContentManager" text="default text" /></a></li>
            <li>Supplier</li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">

                <div id="error" >${errorMessage}</div>

                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.edit_supplier.Supplier" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content editable-widget">

                        <div >
                            <div class="text">
                            <form action="/edit-supplier" class="form-create" id="editSupplier" method="post">
                            <fieldset>
                                <h3><spring:message code="com.adminui.edit_supplier.SUPPLIERSSETTINGS" text="default text" /></h3>
                                <div class="area">
                                    <div class="row" id="CompanyContactName">
                                        <div id="companyName" style="float: left;width: 48%">
                                            <label for="company-name" class="labelProp" ><span>*</span><spring:message code="com.adminui.edit_supplier.CompanyName" text="default text" /></label>
                                            <input type="text" name ="companyName" value="${supplierDetails.companyName}" id="company-name" class="required inputProp" />
                                        </div>


                                        <div id="contactName" style="float: right;width: 48%">
                                            <label for="contact-name" class="labelProp"><span>*</span><spring:message code="com.adminui.edit_supplier.ContactName" text="default text" /></label>
                                            <input type="text" name ="contractName" value="${supplierDetails.contactName}" id="contact-name" class="required inputProp"/>
                                        </div>

                                    </div>
                                    <div class="row" id="Address1VendorId">
                                        <div id="address1" style="float: left;width: 48%">
                                            <label for="addressLine1" class="labelProp" ><span>*</span><spring:message code="com.adminui.edit_supplier.AddressLine1" text="default text" /></label>
                                            <input type="text" name ="address1" id="addressLine1" value="${supplierDetails.address1}" class="required inputProp" />
                                        </div>


                                        <div id="altVendorId" style="float: right;width: 48%">
                                            <label for="alt-vendorId" class="labelProp"><spring:message code="com.adminui.edit_supplier.AltVendorID" text="default text" /></label>
                                            <input type="text" name ="alternateVendorId" value="${supplierDetails.alternateVendorId}" id="alt-vendorId" class="required inputProp"/>
                                        </div>
                                    </div>
                                    <div class="row" id="Address2VendorId">
                                        <div id="address2" style="float: left;width: 48%">
                                            <label for="addressLine2" class="labelProp" ><spring:message code="com.adminui.edit_supplier.AddressLine2" text="default text" /></label>
                                            <input type="text" name ="address2" value="${supplierDetails.address2}" id="addressLine2" class="required inputProp" />
                                        </div>


                                        <div id="dunsNum" style="float: right;width: 48%">
                                            <label for="duns-number" class="labelProp"><span>*</span><spring:message code="com.adminui.edit_supplier.DUNSNumber" text="default text" /></label>
                                            <input type="text" name ="dunsNumber" value="${supplierDetails.dunsNumber}" id="duns-number" class="required inputProp"/>
                                        </div>
                                    </div>

                                    <div class="row" id="CountryPhone">
                                        <div id="countryRegion" style="float: left;width: 48%">
                                            <label class="labelProp" ><span>*</span><spring:message code="com.adminui.edit_supplier.CountryRegion" text="default text" /></label>

                                            <select name="countryList" id="countryRegionName">
                                                <option value="0"></option>
                                                <c:forEach var="country" items="${countriesList}">
                                                    <option id="${country.id}" <c:if test="${supplier.country) eq country.id}"> selected="selected"</c:if>  value="${country.id}">${country.name}</option>
                                                </c:forEach>
                                            </select>

                                        </div>


                                        <div id="PhoneNum" style="float: right;width: 48%">
                                            <label for="phone-num" class="labelProp"><spring:message code="com.adminui.edit_supplier.PhoneNumber" text="default text" /></label>
                                            <input type="text" name ="phone" id="phone-num" value="${supplierDetails.phone}" class="required inputProp" />
                                        </div>
                                    </div>

                                    <div class="row" id="theStateCountry">
                                        <div id="stateCountry" style="float: left;width: 48%">
                                            <label for="state-country" class="labelProp" ><spring:message code="com.adminui.edit_supplier.StateCountry" text="default text" /></label>
                                            <input type="text" name ="state" id="state-country" value="${supplierDetails.state}"  class="required inputProp" />
                                        </div>


                                        <div id="FaxNum" style="float: right;width: 48%">
                                            <label for="fax-number" class="labelProp"><span>*</span><spring:message code="com.adminui.edit_supplier.FaxNumber" text="default text" /></label>
                                            <input type="text" name ="fax" id="fax-number" value="${supplierDetails.fax}" class="required inputProp"/>
                                        </div>
                                    </div>
                                    <div class="row" id="theCityEmail">
                                        <div id="CityEmail" style="float: left;width: 48%">
                                            <label for="label-city" class="labelProp" ><spring:message code="com.adminui.edit_supplier.City" text="default text" /></label>
                                            <input type="text" name ="city" id="label-city" value="${supplierDetails.city}" class="required inputProp" />
                                        </div>


                                        <div id="emailAdd" style="float: right;width: 48%">
                                            <label for="email-address" class="labelProp"><span>*</span><spring:message code="com.adminui.edit_supplier.EmailAddress" text="default text" /></label>
                                            <input type="text" name ="email" id="email-address" value="${supplierDetails.email}" class="required inputProp"/>
                                        </div>
                                    </div>
                                    <div class="row" id="theZipPostal">
                                        <div id="ZipPostal" style="float: left;width: 48%">
                                            <label for="zip-postal" class="labelProp" ><spring:message code="com.adminui.edit_supplier.ZipPostalCode" text="default text" /></label>
                                            <input type="text" name ="zip" id="zip-postal" value="${supplierDetails.zip}" class="required inputProp" />
                                        </div>
                                    </div>
                                    <div class="row" id="theMinOrderQuantity">
                                        <div id="minOrder" style="float: left;width: 48%">
                                            <label for="min-order" class="labelProp" ><span>*</span><spring:message code="com.adminui.edit_supplier.MinOrderQty" text="default text" /></label>
                                            <input type="text" name ="minOrder" value="${supplierDetails.minOrder}" onkeyup="this.value=this.value.replace(/[^\d]/,'')" id="min-order" class="required inputProp" />
                                        </div>
                                    </div>
                                    <div class="row" id="theDefaultVendorId">
                                        <div id="defaultVendorId" style="float: left;width: 48%">
                                            <label for="default-vendorId" class="labelProp" ><span>*</span><spring:message code="com.adminui.edit_supplier.DefaultVendorID" text="default text" /></label>
                                            <input type="text" name ="defaultVendorId" id="default-vendorId" value="${supplierDetails.defaultVendorId}" class="required inputProp" />
                                        </div>
                                    </div>
                                    <div class="row" id="uploadLogo">
                                        <div id="theUploadLogo" style="float: left;width: 48%">
                                            <label for="catalog-file-supplier" class="labelProp"><span>*</span><spring:message code="com.adminui.edit_supplier.UploadLogo" text="default text" /></label>
                                            <input type="file" name="catalogFile" id="catalog-file-supplier" />
                                            <label for="catalog-file-supplier" generated="true" class="error inputProp"></label>

                                        </div>
                                    </div>

                                </div>
                            </fieldset>
                            <fieldset>
                                <div id="companyAttributes-section">
                                    <div class="title">
                                        <h2><a href="#" class="open-close">Assign Company Attributes</a></h2>
                                    </div>
                                    <div >
                                        <div class="content editable-widget" id="companyAttributesContentDiv">
                                            <table class="table-data" id="companyAttributesTable">
                                                <thead>
                                                <tr>
                                                    <th class="td-select">
                                                        <input type="checkbox" class="check-allbox" name="check-all3" id="check13-1"  onclick="if(this.checked)checkAllCompanyAttributsItems(true);else checkAllCompanyAttributsItems(false)" checked="checked"/>
                                                        <label for="check13-1"></label></th>
                                                    <th class="a-left"><spring:message code="com.adminui.edit_supplier.CompanyAttributes" text="default text" /></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <% int companyAttributeCounter = 0; %>
                                                <c:forEach var="attribute" items="${attributsForCompany}" varStatus="cntr1">
                                                    <tr>
                                                        <td class="td-select">
                                                            <input type="checkbox" class="target-chbox" name="companyAttributesIds"  id="approver-${attribute.attributeId}" value="${attribute.attributeId}" checked="checked"/>
                                                            <label for="approver-${attribute.attributeId}"></label>
                                                        </td>
                                                        <td class="a-left td-username">
                                                            <div class="field">
                                                                <span>${attribute.attributeName}</span>
                                                                <input type="text" value="${attribute.attributeName}" />
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <% ++companyAttributeCounter; %>
                                                </c:forEach>
                                                </tbody>
                                                <tfoot>
                                                <tr><td colspan="7"><spring:message code="com.adminui.edit_supplier.TotalRecords" text="default text" /> <%= companyAttributeCounter %></td></tr>
                                                </tfoot>
                                            </table>
                                            <input name="companyId" type="hidden" value='<c:out value="${param['companyId']}"/>' />
                                            <input name="companyName" type="hidden" value='<c:out value="${param['companyName']}"/>' />

                                            <div class="function">
                                                <ul>
                                                    <!-- <li><a href="#" class="ico-remove"><span><em>REMOVE</em></span></a></li> -->
                                                    <li class="delete-attributes"><a href="javascript:void(0)" class="ico-remove" ><span><em><spring:message code="com.adminui.edit_supplier.REMOVE" text="default text" /></em></span></a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset>
                                <div class="toggle-block" id="approvers-section">
                                    <div class="title">
                                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.edit_supplier.CATALOGAPPROVERS" text="default text" /></a></h2>
                                    </div>
                                    <div >
                                        <div class="content editable-widget" id="ApproversContentDiv">
                                            <table class="table-data" id="catalogApproverTable">
                                                <thead>
                                                <tr>
                                                    <th class="td-select">
                                                        <input type="checkbox" class="check-allbox" name="catalogApprovers" id="check34-1"  onclick="if(this.checked)checkAllApproversItems(true);else checkAllApproversItems(false)" checked="checked"/>
                                                        <label for="check34-1"></label></th>
                                                    <th class="a-left"><spring:message code="com.adminui.edit_supplier.USERNAME" text="default text" /></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <% int approverCounter = 0; %>
                                                <c:forEach var="approver" items="${approversForCompany}" varStatus="cntr1">
                                                    <tr>
                                                        <td class="td-select">
                                                            <input type="checkbox" class="target-chbox" name="approverIds" id="approver-${approver.approverId}" value="${approver.approverId}"
                                                                   checked="checked"/>
                                                            <label for="approver-${approver.approverId}"></label>
                                                        </td>
                                                        <td class="a-left td-username">
                                                            <div class="field">
                                                                <span>${approver.email}</span>
                                                                <input type="text" value="${approver.email}" />
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <% ++approverCounter; %>
                                                </c:forEach>
                                                </tbody>
                                                <tfoot>
                                                <tr><td colspan="7"><spring:message code="com.adminui.edit_supplier.TotalRecords" text="default text" /> <%= approverCounter %></td></tr>
                                                </tfoot>
                                            </table>
                                            <input name="catalogId" type="hidden" value='<c:out value="${param['catalogId']}"/>' />

                                            <div class="function">
                                                <ul>
                                                    <!-- <li><a href="#" class="ico-remove"><span><em>REMOVE</em></span></a></li> -->
                                                    <li class="delete-approvers"><a href="javascript:void(0)" class="ico-remove" ><span><em><spring:message code="com.adminui.edit_supplier.REMOVE" text="default text" /></em></span></a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>

                            <fieldset>

                                <div class="content-block toggle-block active" id="create-item-section1">
                                    <div class="headline">
                                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.edit_supplier.SystembasedvendorIDS" text="default text" /></a></h2>
                                    </div>
                                    <div class="block">
                                        <div class="top-box">
                                            <div class="btn-holder">
                                                <ul class="options ">
                                                    <li>
                                                        <a href="#create-vendorId" class="btn-create-profile open-popup"><span><em><spring:message code="com.adminui.edit_supplier.CreateVndrIDs" text="default text" /></em></span></a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="toggle-block" id="vendorId-section">
                                            <div class="title">
                                                <h2><a href="#" class="open-close"><spring:message code="com.adminui.edit_supplier.SystemBasedVendorIds" text="default text" /></a></h2>
                                            </div>
                                            <div >
                                                <div class="content editable-widget" id="vendorIdContentDiv">
                                                    <table class="table-data" id="sysVendorTable">
                                                        <thead>
                                                        <tr>
                                                            <th class="td-select">
                                                                <input type="checkbox" class="check-allbox" name="check-all3" id="check3-41"  onclick="if(this.checked)checkAllApproversItems(true);else checkAllApproversItems(false)" checked="checked"/>
                                                                <label for="check3-41"></label></th>
                                                            <th class="a-left"><spring:message code="com.adminui.edit_supplier.VendorID" text="default text" /></th>
                                                            <th class="a-left"><spring:message code="com.adminui.edit_supplier.SystemID" text="default text" /></th>
                                                            <th class="a-left"><spring:message code="com.adminui.edit_supplier.ClientID" text="default text" /></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <% int vendorIdCounter = 0; %>
                                                        <c:forEach var="systemVendor" items="${systemVendors}" varStatus="cntr1">
                                                            <tr>
                                                                <td class="td-select">
                                                                    <input type="checkbox" class="target-chbox" name="systemVendorIds" id="systemVendor-${systemVendor.row}" value="${systemVendor.row}" checked="checked"/>
                                                                    <label for="systemVendor-${systemVendor.row}"></label>
                                                                </td>
                                                                <td class="a-left td-username">
                                                                    <div class="field">
                                                                        <a class="btn-add-custom-fields open-popup" onclick="getVendorIdDetails('${systemVendor.vendorNumber}','${systemVendor.systemId}','${systemVendor.clientId}')"><span>${systemVendor.vendorNumber}</span></a>
                                                                        <input type="text" value="${systemVendor.vendorNumber}" />
                                                                    </div>
                                                                </td>
                                                                <td class="a-left td-username">
                                                                    <div class="field">
                                                                        <span>${systemVendor.systemId}</span>
                                                                        <input type="text" value="${systemVendor.systemId}" />
                                                                    </div>
                                                                </td>
                                                                <td class="a-left td-username">
                                                                    <div class="field">
                                                                        <span>${systemVendor.clientId}</span>
                                                                        <input type="text" value="${systemVendor.clientId}" />
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <% ++vendorIdCounter; %>
                                                        </c:forEach>
                                                        </tbody>
                                                        <tfoot>
                                                        <tr><td colspan="7">Total Records: <%= vendorIdCounter %></td></tr>
                                                        </tfoot>
                                                    </table>
                                                    <input name="catalogId" type="hidden" value='<c:out value="${param['catalogId']}"/>' />
                                                    <input name="vendorName" type="hidden" value='<c:out value="${param['companyName']}"/>' />

                                                    <div class="function">
                                                        <ul>
                                                            <!-- <li><a href="#" class="ico-remove"><span><em>REMOVE</em></span></a></li> -->
                                                            <li class="delete-approvers"><a href="javascript:void(0)" class="ico-remove" ><span><em><spring:message code="com.adminui.edit_supplier.REMOVE" text="default text" /></em></span></a></li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </fieldset>

                            <fieldset>
                                <h3><spring:message code="com.adminui.edit_supplier.SUPPLIERSSETTINGS" text="default text" /></h3>
                                <div class="area">
                                    <div class="row" id="LangDecimalNotation">
                                        <div id="language" style="float: left;width: 48%">
                                            <label class="labelProp" ><span>*</span><spring:message code="com.adminui.edit_supplier.Language" text="default text" /></label>
                                            <select name="languagesList" id="languagesList">
                                                <%--<option value="0"></option>--%>
                                                <%--<c:forEach var="language" items="${languagesList}">--%>
                                                    <%--<option id="${language.languageId}" <c:if test="${supplierDetails.languagesList.get(0) eq language.languageValue}"> selected="selected"</c:if>--%>
                                                            <%--value="${language.languageValue}">${language.languageName}</option>--%>
                                                <%--</c:forEach>--%>
                                                <option id="1" value="en"><spring:message code="com.adminui.edit_supplier.English" text="default text" /></option>
                                            </select>
                                        </div>


                                        <div id="decimalNotation" style="float: right;width: 48%">
                                            <label class="labelProp"><span>*</span><spring:message code="com.adminui.edit_supplier.DecimalNotation" text="default text" /></label>
                                            <select name="decimalNotationList" id="decimalNotationList">
                                                <option value="0"></option>
                                                <c:forEach var="decimalNotation" items="${decimalNotationList}">
                                                    <option id="${decimalNotation.decimalId}"
                                                            value="${decimalNotation.decimalNotationName}">
                                                            ${decimalNotation.decimalNotationName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                    </div>
                                </div>
                                <div class="area">
                                    <div class="row" id="TimeDateFormat">
                                        <div id="time" style="float: left;width: 48%">
                                            <label class="labelProp" ><span>*</span><spring:message code="com.adminui.edit_supplier.Time" text="default text" /></label>
                                            <select name="timeFormatList" id="timeFormatList">
                                                <option value="0"></option>
                                                <c:forEach var="timeFormat" items="${timeFormatList}">
                                                    <option id="${timeFormat.timeZoneId}"
                                                            <c:if test="${supplierDetails.timeFormatList.get(0) eq timeFormat.timeZoneValue}"> selected="selected"</c:if>
                                                            value="${timeFormat.timeZoneValue}">${timeFormat.timeZoneName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>


                                        <div id="dateFormat" style="float: right;width: 48%">
                                            <label class="labelProp"><span>*</span><spring:message code="com.adminui.edit_supplier.DateFormat" text="default text" /></label>
                                            <select name="dateFormatList" id="dateFormatList">
                                                <option value="0"></option>
                                                <c:forEach var="dateFormat" items="${dateFormatList}">
                                                    <option id="${dateFormat.dateId}"
                                                            <c:if test="${supplierDetails.dateFormatList.get(0) eq dateFormat.dataFormat}"> selected="selected"</c:if>
                                                            value="${dateFormat.dataFormat}">${dateFormat.dataFormat}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                    </div>
                                </div>








                            </fieldset>


                            <div class="btns-holder">
                                <a href="#" class="btn-cancel"><spring:message code="com.adminui.edit_supplier.Cancel" text="default text" /></a>
                                <%--<input type="submit" value="Save" onclick="createSupplier(); return false;"/>--%>
                                <a class="btn-create close"  onclick="editSupplier();"><span><spring:message code="com.adminui.edit_supplier.Save" text="default text" /></span></a>
                                <%--<input type="submit" value="Save" name="Save">--%>
                                <p><span class="required">* </span><spring:message code="com.adminui.edit_supplier.RequiredField" text="default text" /></p>
                            </div>

                            <div class="area alt-area"></div>

                            <div class="main-holder">
                                <div id="content">

                                    <div class="content-block toggle-block active" id="create-item-section">
                                        <div class="headline">
                                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.edit_supplier.Supplier" text="default text" /></a></h2>
                                        </div>
                                        <div >
                                            <div class="content editable-widget">
                                                <div class="top-box">
                                                    <div class="btn-holder">
                                                        <ul class="options ">
                                                            <li>
                                                                <a href="#companyUser" class="btn-create-profile open-popup"><span><em><spring:message code="com.adminui.edit_supplier.CreateCompanyAttributes" text="default text" /></em></span></a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="scrollable-area vscrollable">
                                                    <div class="text">

                                                        <table class="table-data">
                                                            <thead>
                                                            <tr>
                                                                <th class="td-select"><input type="checkbox" name="check-all" id="check1" onclick="checkAllSuppliers(this.checked)" /><label for="check1"></label></th>
                                                                <th class="a-left"><spring:message code="com.adminui.edit_supplier.CompanyName" text="default text" /></th>
                                                                <th><spring:message code="com.adminui.edit_supplier.Created" text="default text" /><spring:message code="com.adminui.edit_supplier.On" text="default text" /></th>
                                                                <th><spring:message code="com.adminui.edit_supplier.CreatedBy" text="default text" /></th>
                                                                <th><spring:message code="com.adminui.edit_supplier.Role" text="default text" /></th>
                                                                <th class="td-last"><spring:message code="com.adminui.edit_supplier.Active" text="default text" /></th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>

                                                            <% int companyUserCounter = 0; %>
                                                            <c:forEach var="companyUser" items="${companyUserList}" varStatus="cntr1">
                                                                <tr>
                                                                    <td class="td-select"><input type="checkbox" name="companyUserIds" id="comuserid-${companyUser.companyUserId}" value="${companyUser.companyUserId}" /><label for="comuserid-${companyUser.companyUserId}"></label></td>
                                                                    <td class="a-left td-description">
                                                                        <div class="field" style="width:150px">
                                                                            <%--<span><a href="edit-companyUser?companyEmail=${companyUser.emailAddress}&supplierId=<c:out value="${param['companyId']}"/>">${companyUser.emailAddress}</a></span>--%>

                                                                                <a class="btn-add-custom-fields open-popup" onclick="getCompanyDetails('${companyUser.emailAddress}','<c:out value="${param['companyId']}"/>')"><span>${companyUser.emailAddress}</span></a>
                                                                            <input type="text" value="${companyUser.emailAddress}"  />
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <div class="field" style="width:150px">
                                                                            <span>${companyUser.createdOn}</span>
                                                                            <input type="text" value="${companyUser.createdOn}" />
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <div class="field" style="width:150px">
                                                                            <span>${companyUser.createdBy}</span>
                                                                            <input type="text" value="${companyUser.createdBy}" />
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <div class="field" style="width:150px">
                                                                            <span>${companyUser.role}</span>
                                                                            <input type="text" value="${companyUser.role}" />
                                                                        </div>
                                                                    </td>
                                                                    <td class="td-last">${companyUser.active==true?'Yes':'No'}</td>
                                                                </tr>
                                                                <% ++companyUserCounter; %>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                        <div id="updateSuppliersFeedback" align="left" style="height:10px"></div>

                                                    </div>
                                                </div>
                                                <div class="bottom-data">
                                                    <!-- <div class="pager">
                                                        <span>Page 1 of 49 </span>
                                                        <ul>
                                                            <li><a href="#" class="btn-prev"></a></li>
                                                            <li><a href="#" class="btn-next"></a></li>
                                                        </ul>
                                                    </div>-->
                                                    <strong class="pages"><spring:message code="com.adminui.edit_supplier.TotalRecords" text="default text" /> <%= companyUserCounter %></strong>

                                                </div>
                                                <div class="move-items">
                                                </div>
                                                <div class="function">
                                                    <ul>
                                                        <li><a href="javascript:void(0)" class="ico-approve" onclick="activeSupplier(true);"><span><em><spring:message code="com.adminui.edit_supplier.Activate" text="default text" /></em></span></a></li>
                                                        <li><a href="javascript:void(0)" class="ico-reject" onclick="activeSupplier(false);"><span><em><spring:message code="com.adminui.edit_supplier.Deactivate" text="default text" /></em></span></a></li>
                                                        <!-- <li class="del"><a href="#delete" class="ico-delete" onclick="deleteProfiles();"><span><em>DELETE</em></span></a></li> -->
                                                        <li class="del"><a href="javascript:void(0)" class="ico-delete" onclick="deleteProfiles();"><span><em><spring:message code="com.adminui.edit_supplier.DELETE" text="default text" /></em></span></a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>


                            </form>
                            </div>
                        </div>
                        <div class="bottom-data">
                            <!-- <div class="pager">
                                <span>Page 1 of 49 </span>
                                <ul>
                                    <li><a href="#" class="btn-prev"></a></li>
                                    <li><a href="#" class="btn-next"></a></li>
                                </ul>
                            </div>-->
                            <strong class="pages"><spring:message code="com.adminui.edit_supplier.TotalRecords" text="default text" /> <%= companyUserCounter %></strong>

                        </div>
                        <div class="move-items">
                        </div>
                        <div class="function">
                            <ul>
                                <li><a href="javascript:void(0)" class="ico-approve" onclick="activeSupplier(true);"><span><em><spring:message code="com.adminui.edit_supplier.Activate" text="default text" /></em></span></a></li>
                                <li><a href="javascript:void(0)" class="ico-reject" onclick="activeSupplier(false);"><span><em><spring:message code="com.adminui.edit_supplier.Deactivate" text="default text" /></em></span></a></li>
                                <!-- <li class="del"><a href="#delete" class="ico-delete" onclick="deleteProfiles();"><span><em>DELETE</em></span></a></li> -->
                                <li class="del"><a href="javascript:void(0)" class="ico-delete" onclick="deleteProfiles();"><span><em><spring:message code="com.adminui.edit_supplier.DELETE" text="default text" /></em></span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>


        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href="index.html" class="ico-back"><span><spring:message code="com.adminui.edit_supplier.BACK" text="default text" /></span></a></li>
                <%--<li><a href="/catalog/create-supplier" class="btn-create-profile"><span>Create Supplier</span></a></li>--%>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">

    function checkAllCatalogItems(check){
        try{
            if(check){
                $("#createProfileId .table-data :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#createProfileId .table-data :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=false;
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
    function checkAllCompanyAttributsItems(check){
        try{
            if(check){
                $("#companyAttributesContentDiv :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                });
            }else{
                $("#companyAttributesContentDiv :checkbox").each(function(){
                    $(this).next().removeClass('ui-state-active');
                });
            }
        }catch(exp){
            alert(exp);
        }
    }
</script>