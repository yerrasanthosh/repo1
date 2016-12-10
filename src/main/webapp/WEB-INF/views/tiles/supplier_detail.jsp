<%@ page contentType="text/html;charset=UTF-8" language="java"
	autoFlush="true" session="true"
	import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.supplier.model.SupplierAttributes,
         com.vroozi.customerui.supplier.model.Supplier,
         com.vroozi.customerui.acl.model.Permission"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="aclManager" scope="request"
	class="com.vroozi.customerui.acl.DisplayAccessControl" />
<script type="text/javascript" src="res/js/supplier_detail.js"></script>
<div id="main">
	<div class="section">
		<ul class="breadcrumbs">
			<li><a href="#">smartOCI</a></li>
			<li><a href='<c:url value="/suppliers" />'> <spring:message code="com.adminui.supplier_detail.SupplierManagement" text="default text" /></a></li>
			<li>${pageName}</li>
		</ul>
		<div class="video-btn-holder">
			<!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
		</div>
	</div>
	<div class="main-holder">
		<div id="content">
			<h1>${pageName}</h1>
			<div class="content-block toggle-block active"
				id="company-information">
				<div class="headline">
					<h2>
						<a href="#" class="open-close"></span><spring:message code="com.adminui.supplier_detail.Companyinformation" text="default text" /></a>
					</h2>
				</div>
				<div class="block">
					<div class="content">
						<form id="supplier-form" class="settings-form alt"
							action="/createEditSupplier">
							<div class="text-fields">
								<div class="row">
									<label for="uniqueSupplierId"><spring:message code="com.adminui.supplier_detail.UniqueSupplierID" text="default text" /></label>
									<div class="area-col" id="unique-supplier-div">
										<label style="width: 65px;">${uniqueSupplierId}</label> <input
											type="hidden" id="uniqueSupplierId" name="uniqueSupplierId"
											value="${uniqueSupplierId}" />
									</div>
								</div>
								<div class="row">
									<label for="companyName"><span class="required">*</span><spring:message code="com.adminui.supplier_detail.CompanyName" text="default text" /></label>
									<div class="area-col" id="company-name-div">
										<input id="companyName" name="companyName" class="text"
											type="text"
											onkeyup="if($('#companyName').val() != '')$('#company-name-div span').remove()" />
										<input type="hidden" id="companyNameHidden"
											name="companyNameHidden" value="${supplier.companyName}" />
									</div>
								</div>
								<div class="row">
									<label for="dunsNumber"><spring:message code="com.adminui.supplier_detail.DUNSNumber" text="default text" /></label>
									<div class="area-col" id="duns-number-div">
										<input id="dunsNumber" name="dunsNumber" class="text"
											type="text"
											onkeyup="if($('#dunsNumber').val() != '')$('#duns-number-div span').remove()" />
										<input type="hidden" id="dunsNumberHidden"
											name="dunsNumberHidden" value="${supplier.dunsNumber}" />
									</div>
								</div>
								<div class="row">
									<label for="defaultVendorId"><span class="required">*</span><spring:message code="com.adminui.supplier_detail.DefaultSupplierID" text="default text" /></label>
									<div class="area-col" id="default-vendorId-div">
										<input id="defaultVendorId" name="defaultVendorId"
											class="text" type="text"
											onkeyup="if($('#defaultVendorId').val() != '')$('#default-vendorId-div span').remove()" />
										<input type="hidden" id="defaultVendorIdHidden"
											name="defaultVendorIdHidden"
											value="${supplier.defaultVendorId}" />
									</div>
								</div>
								<div class="row">
									<label for="addressLine1"><span class="required">*</span><spring:message code="com.adminui.supplier_detail.AddressLine1" text="default text" /></label>
									<div class="area-col" id="address-line1-div">
										<input id="addressLine1" name="addressLine1" class="text"
											type="text"
											onkeyup="if($('#addressLine1').val() != '')$('#address-line1-div span').remove()" />
										<input type="hidden" id="addressLine1Hidden"
											name="addressLine1Hidden" value="${supplier.address1}" />
									</div>
								</div>
								<div class="row">
									<label for="addressLine2"><spring:message code="com.adminui.supplier_detail.AddressLine2" text="default text" /></label>
									<div class="area-col">
										<input id="addressLine2" name="addressLine2" class="text"
											type="text" /> <input type="hidden" id="addressLine2Hidden"
											name="addressLine2Hidden" value="${supplier.address2}" />
									</div>
								</div>
								<div id="countryRegionRow" class="row">
									<label for="countryRegion"><spring:message code="com.adminui.supplier_detail.CountryRegion" text="default text" /></label>
									<div class="area-col">

										<select id="countryRegion" name="countryRegion"
											onchange="toggleStatesSelect();"
											onkeyup="toggleStatesSelect();">
											<c:forEach var="countrie" items="${countries}">
												<option id="${countrie.id}"
                                                        <c:if test="${(fn:toLowerCase(supplier.country) eq fn:toLowerCase(countrie.name))
                                                            || (fn:toLowerCase(supplier.country) eq fn:toLowerCase(countrie.iso2))
                                                            || (fn:toLowerCase(supplier.country) eq fn:toLowerCase(countrie.iso3))
                                                            || (fn:toLowerCase(supplier.country) eq fn:toLowerCase(countrie.id))}"> selected="selected" </c:if>
                                                        value="${countrie.id}">${countrie.name}</option>
											</c:forEach>
										</select> <input type="hidden" id="countryRegionHidden"
											name="countryRegionHidden" value="${supplier.country}" />
									</div>
								</div>
								<div class="row">
									<label for="stateCounty"><spring:message code="com.adminui.supplier_detail.StateCounty" text="default text" /></label>
									<div class="area-col" id="state-div">
										<input id="stateCounty" name="stateCounty" class="text"
											type="text"
											onkeyup="if($('#stateCounty').val() != '')$('#state-div span').remove()" />
										<div id="stateCountySelectBox">
											<select id="stateCountySelect" name="stateCountySelect"
												onchange="setCountryValue();">
												<c:forEach var="state" items="${states}">
													<option id="stateid-${state.name}"
														<c:if test="${(fn:toLowerCase(supplier.state) eq fn:toLowerCase(state.name)) 
																|| fn:toLowerCase(supplier.state) eq fn:toLowerCase(state.code)}"> selected="selected"</c:if>
														value="${state.name}">${state.name}</option>
												</c:forEach>
											</select>
										</div>
										<input type="hidden" id="stateCountyHidden"
											name="stateCountyHidden" value="${supplier.state}" />
									</div>
								</div>
								<div class="row">
									<label for="city"><spring:message code="com.adminui.supplier_detail.City" text="default text" /></label>
									<div class="area-col" id="city-div">
										<input id="city" name="city" class="text" type="text"
											onkeyup="if($('#city').val() != '')$('#city-div span').remove()" />
										<input type="hidden" id="cityHidden" name="cityHidden"
											value="${supplier.city}" />
									</div>
								</div>
								<div class="row">
									<label for="zipPostalCode"><spring:message code="com.adminui.supplier_detail.zip" text="default text" /></label>
									<div class="area-col" id="zipcode-div">
										<input id="zipPostalCode" name="zipPostalCode" class="text"
											type="text"
											onkeyup="if($('#zipPostalCode').val() != '')$('#zipcode-div span').remove()" />
										<input type="hidden" id="zipPostalCodeHidden"
											name="zipPostalCodeHidden" value="${supplier.zip}" />
									</div>
								</div>
							</div>
							<h3><spring:message code="com.adminui.supplier_detail.info" text="default text" /></h3>
							<div class="text-fields">
								<div class="row">
									<label for="contactName"><span class="required">*</span><spring:message code="com.adminui.supplier_detail.ContactName" text="default text" /></label>
									<div class="area-col" id="contact-div">
										<input id="contactName" name="contactName" class="text"
											type="text"
											onkeyup="if($('#contactName').val() != '')$('#contact-div span').remove()" />
										<input type="hidden" id="contactNameHidden"
											name="contactNameHidden" value="${supplier.contactName}" />
									</div>
								</div>
								<div class="row">
									<label for="contactRole"><spring:message code="com.adminui.supplier_detail.ContactRole" text="default text" /></label>
									<div class="area-col">
										<input id="contactRole" name="contactRole" class="text"
											type="text" /> <input type="hidden" id="contactRoleHidden"
											name="contactRoleHidden" value="${supplier.contactRole}" />
									</div>
								</div>
								<div class="row">
									<label for="phoneNumber"><spring:message code="com.adminui.supplier_detail.PhoneNumber" text="default text" /></label>
									<div class="area-col" id="phoneNo-Div">
										<input id="phoneNumber" name="phoneNumber" class="text"
											type="text"
											onkeyup="if($('#phoneNumber').val() != '')$('#phoneNo-Div span').remove()" />
										<input type="hidden" id="phoneNumberHidden"
											name="phoneNumberHidden" value="${supplier.phone}" />
									</div>
								</div>
								<div class="row">
									<label for="faxNumber"><spring:message code="com.adminui.supplier_detail.FaxNumber" text="default text" /></label>
									<div class="area-col">
										<input id="faxNumber" name="faxNumber" class="text"
											type="text" /> <input type="hidden" id="faxNumberHidden"
											name="faxNumberHidden" value="${supplier.fax}" />
									</div>
								</div>
								<div class="row">
									<label for="emailAddress"><spring:message code="com.adminui.supplier_detail.EmailAddress" text="default text" /></label>
									<div class="area-col" id="email-div">
										<input id="emailAddress" name="emailAddress" class="text"
											type="text" /> <input type="hidden" id="emailAddressHidden"
											name="emailAddressHidden" value="${supplier.email}" />
									</div>
								</div>
								<br />
							</div>
							<h3><spring:message code="com.adminui.supplier_detail.DefaultPreferences" text="default text" /></h3>
							<div class="preferences-fields">
								<div class="row">
									<div id="languageRow" class="area-col">
										<label><spring:message code="com.adminui.supplier_detail.Language" text="default text" /></label> <select id="language" name="language">
											<c:forEach var="language" items="${languages}">
												<option id="${language.languageId}"
													value="${language.languageValue}">${language.languageName}</option>
											</c:forEach>
										</select> <input type="hidden" id="languageHidden"
											name="languageHidden" value="${supplier.language}" />
									</div>
									<div id="decimalNotationRow" class="area-col">
										<label><spring:message code="com.adminui.supplier_detail.DecimalNotation" text="default text" /></label> <select id="decimalNotation"
											name="decimalNotation">
											<c:forEach var="decimalNotation" items="${decimalNotations}">
												<option id="${decimalNotation.decimalId}"
													value="${decimalNotation.decimalId}">${decimalNotation.decimalNotationName}</option>
											</c:forEach>
										</select> <input type="hidden" id="decimalNotationHidden"
											name="decimalNotationHidden"
											value="${supplier.decimalNotation}" />
									</div>
								</div>
								<div class="row">
									<div id="timezoneRow" class="area-col">
										<label><spring:message code="com.adminui.supplier_detail.TimeZone" text="default text" /></label> <select id="timezone"
											name="timezone">
											<c:forEach var="timeZone" items="${timeZones}">
												<option id="${timeZone.timeZoneId}"
													value="${timeZone.timeZoneValue}">${timeZone.timeZoneName}</option>
											</c:forEach>
										</select> <input type="hidden" id="timezoneHidden"
											name="timezoneHidden" value="${supplier.timeZone}" />
									</div>
									<div id="dateFormatRow" class="area-col">
										<label><spring:message code="com.adminui.supplier_detail.DateFormat" text="default text" /></label> <select id="dateFormat"
											name="dateFormat">
											<c:forEach var="dateFormat" items="${dateFormats}">
												<option id="${dateFormat.dateId}"
													value="${dateFormat.dateId}">${dateFormat.dataFormat}</option>
											</c:forEach>
										</select> <input type="hidden" id="dateFormatHidden"
											name="dateFormatHidden" value="${supplier.dateFormat}" />
									</div>
								</div>
							</div>
							<div class="btns-holder">
								<c:if
									test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
									<input type="submit" name="submit-btn" value="<spring:message code="com.adminui.supplier_detail.Save" text="default text" />"
										id="supplier-submit-btn"
										onclick='savingSupplier();createEditSupplier("<c:url value="/suppliers"/>");return false;' />
								</c:if>
								<p>
									<span class="required">* </span><spring:message code="com.adminui.supplier_detail.RequiredField" text="default text" />
								</p>
								<p>
									<span id="create-edit-supplier-div"
										style="font-style: italic; color: red;"></span>
								</p>
							</div>
							<input id="supplierId" type="hidden" name="supplierId"
								value="${supplier.companyId}" /> <input id="supplierLogoId"
								type="hidden" name="logo" value="${supplierLogo}" />
						</form>
					</div>
				</div>
			</div>
			<div class="content-block toggle-block active alt" id="upload-logo">
				<div class="headline">
					<h2>
						<a href="#" class="open-close"><spring:message code="com.adminui.supplier_detail.Uploadlogo" text="default text" /></a>
					</h2>
				</div>
				<div class="block">
					<div class="content">
						<form action="uploadSupplierLogo" id="supplier-logo-form"
							class="settings-form" method="post" enctype="multipart/form-data">
							<div id="supplier-logo-fragment-holder" class="alt-area">

								<c:if
									test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
									<div id="browseDiv1" style="display: block;"
										class="popup-holder">
										<div id="uploadSupplierLogoAddBtn">
											<a href="#" class="btn-add-open open"
												style="z-index: 0; text-align: left;"><spring:message code="com.adminui.supplier_detail.add" text="default text" /></a> <em
												id="supplier-logo-fileEM" class="text"><spring:message code="com.adminui.supplier_detail.NoFileChosen" text="default text" /></em>
										</div>
										<div class="popup">
											<div class="popup-frame">
												<div class="popup-inner">
													<div class="top">
														<a href="#" class="alt-close"></a>
														<h3 style="text-align: left;"><spring:message code="com.adminui.supplier_detail.UploadFile" text="default text" /></h3>
													</div>
													<div class="row">
														<input type="file" name="logoFile" id="supplier-logo-file"
															onchange="setLogoFile();" /> <input type="hidden"
															name="supplierId" id="supplier-id"
															value="${param['supplierId']}" /> <input type="hidden"
															name="supplierLogo" id="supplier-logo"
															value="${supplierLogo}" />
													</div>
													<div class="row" style="display: none;">
														<img src="res/images/ico01.png" width="39" height="39"
															alt="image description" /> <a href="#"
															class="btn-upload"><spring:message code="com.adminui.supplier_detail.Upload" text="default text" /></a>
														<div class="txt">
															<span class="size"> 0.59 KB</span>
															<p>icon_photo_upload_16px.gif</p>
														</div>
													</div>
													<div class="row" style="display: none;">
														<img src="res/images/ico01.png" width="39" height="39"
															alt="image description" />
														<div class="txt">
															<div class="line-box">
																<span class="line" style="width: 70%"></span>
															</div>
															<a href="#" class="btn-cancel"><spring:message code="com.adminui.supplier_detail.Cancel" text="default text" /></a>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:if>
								<div id="uploadSupplierLogoImg">
									<img style="max-height: 39px; max-width: 100px;">&nbsp;
									<c:if
										test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
										<a
											style="position: absolute; margin-top: 8px; margin-left: 20px; cursor: pointer;"
											onclick="resetLogoImageConfirm();" class="alt-close"></a>
									</c:if>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="add-slide-blocks">
				<div class="content-block toggle-block" id="info-section">
					<div class="headline">
						<h2>
							<a href="#" class="open-close"><spring:message code="com.adminui.supplier_detail.SUPPLIERIDMAPPING" text="default text" /></a>
						</h2>
					</div>
					<div class="block">
						<div class="content editable-widget">
							<div id="errorMsgDiv" style="display: none">
								<span class="error-msg"
									style="display: block; top: 25px; width: 385px; color: #FF0000"><spring:message code="com.adminui.supplier_detail.saveCancel" text="default text" /></span>
							</div>
							<div class="top-box">
								<div class="btn-holder">
									<c:if
										test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
										<a class="btn-add-mapping" id="addMappingBtn"
											onclick="addNewRowToTable('supplier_mapping_table');return false;"><span><em><spring:message code="com.adminui.supplier_detail.ADD" text="default text" /></em></span></a>
									</c:if>

								</div>
							</div>
							<div id="supplier_mappings_page_container_div">
								<form id="supplier-mapping-form"
									action="/adminui/savesuppliermappings" method="post"
									class="settings-form" enctype="text/plain">
									<input type="hidden" id="uniqueSupplierIdForm"
										name="uniqueSupplierIdForm" value="${uniqueSupplierId}" /> <input
										type="hidden" id="unitIdMappingForm" name="unitIdMappingForm"
										value="${unitId}" /> <input type="hidden"
										id="companyNameForMapping" name="companyNameForMapping"
										value="" />



									<table class="table-data wb" id="supplier_mapping_table">
										<jsp:include
											page="supplier_details_mapping_section_table_fragment.jsp" />
									</table>


									<div class="function" id="btnHolder">

										<c:if
											test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
											<ul>
												<input type="submit" name="submit-btn" value="Save"
													style="display: none" id="supplier-mapping-save-btn"
													onclick='validateAndPostFormData();return false;' />&nbsp;
												<input type="submit" name="submit-btn" value="Update"
													style="display: none" id="supplier-mapping-update-btn"
													onclick='validateAndUpdateFormData();return false;' />&nbsp;
												<li><a href="javascript:void(0)" class="ico-remove"
													id="supplier-mapping-remove-btn"
													onclick="deleteSupplierMappingsConfirm();"><span><em><spring:message code="com.adminui.supplier_detail.REMOVE" text="default text" /></em></span></a></li>
												<a class="btn-cancel" value="Cancel" style="display: none"
													id="supplier-mapping-cancel-btn"
													onclick='cancleUpdate();return false;'><spring:message code="com.adminui.supplier_detail.Cancel" text="default text" /></a>
												<a class="btn-cancel" value="Cancel" style="display: none"
													id="supplier-mapping-cancel-save-btn"
													onclick='cancelCreation();return false;'><spring:message code="com.adminui.supplier_detail.Cancel" text="default text" /></a>
											</ul>
										</c:if>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="content-block toggle-block active alt"
				id="supplier-configuration">
				<div class="headline">
					<h2>
						<a href="#" class="open-close"><spring:message code="com.adminui.supplier_detail.Supplierconfiguration" text="default text" /></a>
					</h2>
				</div>
				<div class="block">
					<div class="content">
						<form action="#" id="supplierConfigForm" class="settings-form">
							<div class="text-fields">
								<div class="row">
									<label for="minValue"><spring:message code="com.adminui.supplier_detail.MinimumOrderValue" text="default text" /></label>
									<div class="area-col">
										<input id="minValue" name="minOrder" class="text" type="text"
											<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                               onchange="changeSupplierMinOrder(this.value, '${param["supplierId"]}');"
                                            </c:if> />
										<input type="hidden" id="minValueHidden" name="minValueHidden"
											value="${minOrder}" />
									</div>
								</div>
								<div class="row">
									<label><spring:message code="com.adminui.supplier_detail.NonCatalogSupplier" text="default text" /></label>
									<div class="area-col">
										<div class="item">
											<input id="nonCatalogSupplier" name="nonCatalogSupplier"
												type="checkbox"
												<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                                   onclick="updateNonSupplierCatalog(this.checked, '${param["supplierId"]}');"
                                                </c:if> />
											<label for="nonCatalogSupplier"></label> <input type="hidden"
												id="nonCatalogSupplierHidden"
												name="nonCatalogSupplierHidden"
												value="${nonCatalogSupplier}" />
										</div>
									</div>
								</div>
								<div class="row">
									<label><spring:message code="com.adminui.supplier_detail.SingleSourceSupplier" /></label>
									<div class="area-col">
										<div>
											<input id="singleSourceSupplier" name="singleSourceSupplier"
												type="checkbox"
												<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
													onclick="updateSingleSourceSupplier(this.checked, '${param["supplierId"]}');"
												</c:if>/>
											<label for="singleSourceSupplier"></label> <input
												type="hidden" id="singleSourceSupplierHidden"
												name="singleSourceSupplierHidden"
												value="${singleSourceSupplier}" />
										</div>
									</div>
								</div>
								<div class="row">
									<label><spring:message code="com.adminui.supplier_detail.IncludeinSupplierCard" text="default text" /></label>
									<div class="area-col">
										<div class="item">
											<input id="includeSupplierCard" name="includeSupplierCard"
												type="checkbox"
												<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                                   onclick="updateSupplierIncludeCard(this.checked, '${param["supplierId"]}');"
                                                </c:if> />
											<label for="includeSupplierCard"></label> <input
												type="hidden" id="includeSupplierCardHidden"
												name="includeSupplierCardHidden"
												value="${includeSupplierCard}" />
										</div>
									</div>
								</div>
								<div class="row">
									<label><spring:message code="com.adminui.supplier_detail.DisableBrowseonVendorTile" text="default text" /></label>
									<div class="area-col">
										<div class="item">
											<input id="disableBrowse" name="disableBrowse"
												type="checkbox"
												<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                                        onclick="updateSupplierDisableBrowse(this.checked, '${param["supplierId"]}');"
                                                    </c:if> />
											<label for="disableBrowse"></label> <input type="hidden"
												id="disableBrowseHidden" name="disableBrowseHidden"
												value="${disableBrowse}" />
										</div>
									</div>
								</div>
								<div class="row">
									<label><spring:message code="com.adminui.supplier_detail.CurrencyCode" text="default text" /></label>
									<div class="area-col">
										<select id="currencyCode" name="currencyCode"
											<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                                onchange="updateSupplierCountryCode(this.value, '${param["supplierId"]}');"
                                            </c:if>>
											<c:forEach var="currencyCode" items="${currencyCodes}">
												<option id="${currencyCode.code}"
													value="${currencyCode.code}"
													<c:if test="${currencyCode.code eq supplier.currencyCode}"> selected="selected"</c:if>>${currencyCode.code}</option>
											</c:forEach>
										</select> <input type="hidden" id="currencyCodeHidden"
											name="currencyCodeHidden" value="${supplier.currencyCode}" />
									</div>
								</div>

							</div>
						</form>
					</div>
				</div>
			</div>
			<!--div class="content-block toggle-block active alt" id="supplier-mapping">
                <div class="headline">
                    <h2><a href="#" class="open-close">Supplier ID Mapping</a></h2>
                </div>
                <div class="block">
                    <div class="content editable-widget">
                        <div class="top-box">
                            <div class="btn-holder">
                                <a href="#" class="btn-add-mapping"><span><em>ADD</em></span></a>
                            </div>
                        </div>
                        <table class="table-data">
                            <thead>
                            <tr>
                                <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all6" id="check6-1" /><label for="check6-1"></label></th>
                                <th>SUPPLIER Id</th>
                                <th>system id</th>
                                <th>client id</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="td-select"><input type="checkbox" class="target-chbox" name="check6" id="check6-2" /><label for="check6-2"></label></td>
                                <td><input class="input-text" type="text" /></td>
                                <td><input class="input-text" type="text" /></td>
                                <td><input class="input-text" type="text" /></td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="function">
                            <ul>
                                <li><a href="#" class="ico-remove"><span><em>REMOVE</em></span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div-->
			<div class="content-block add-slide-blocks toggle-block active alt"
				id="company-attributes">
				<div class="headline">
					<h2>
						<a href="#" class="open-close"><spring:message code="com.adminui.supplier_detail.CompanyAttributes" text="default text" /></a>
					</h2>
				</div>
				<div class="block">
					<div class="content">
						<!--div class="btn-holder">
                            <a href="#" class="btn-create-attribute"><span><em>Create ATTRIBUTE</em></span></a>
                        </div-->
						<form class="company-form" action="#">
							<c:forEach var="supplierAttribute" items="${supplierAttributes}">
								<c:if test='${supplierAttribute.active}'>
									<div class="row" id="row_${supplierAttribute.attributeId}">
										<strong class="label">${supplierAttribute.attributeName}</strong>
										<div class="area-col">
											<c:if test='${not empty param["supplierId"]}'>
												<div class="check-item">
													<input type="checkbox"
														id="chk_${supplierAttribute.attributeId}"
														name="${supplierAttribute.attributeId}"
														class="ui-helper-hidden-accessible" <c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                                               onclick="associateSupplierAttribute(this, '${supplier.companyId}', 'boostValue_${supplierAttribute.attributeId}' );"
                                                            </c:if>
                                                            <%=(ViewHelper.isAssociated( ((Supplier)request.getAttribute("supplier")).getSupplierAttributePairs(),  ((SupplierAttributes)pageContext.getAttribute("supplierAttribute")).getAttributeId()   ))?"checked":""%> />
													<label for="chk_${supplierAttribute.attributeId}"></label>
												</div>
											</c:if>
											<c:if test='${empty param["supplierId"]}'>
												<div class="check-item">
													<input type="checkbox"
														id="chk_${supplierAttribute.attributeId}"
														name="chk_${supplierAttribute.attributeId}"
														class="ui-helper-hidden-accessible"
														<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                                               onclick="associateSupplierAttribute(this, '', 'boostValue_${supplierAttribute.attributeId}' );"
                                                            </c:if> />
													<label for="chk_${supplierAttribute.attributeId}"></label>
												</div>
											</c:if>
											<div class="icon-item">
												<%
                                                        if(ViewHelper.isDefaulAttribute((SupplierAttributes)pageContext.getAttribute("supplierAttribute"))) {
                                                    %>
												<img width="15" height="15"
													src='res/images/${supplierAttribute.attributeImagePath}'
													alt="image description">
												<%
                                                    } else {
                                                    %>
												<img width="15" height="15"
													src='<c:url value="/image/"/>${supplierAttribute.attributeImagePath}?type=supplier'
													alt="image description">
												<%
                                                        }
                                                    %>

											</div>
											<input id="boostValue_${supplierAttribute.attributeId}"
												value="${supplierAttribute.boostValue}" name="boostValue"
												title="${supplierAttribute.boostValue}"
												<c:if test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
                                                        onchange="if (!changeSupplierAttributeBoostLevel(this, '${supplier.companyId}')) {alert('only numeric boost values in the range of -2147483648, 2147483647 are accepted');}"
                                                    </c:if>>
											</input>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</form>
					</div>
				</div>
			</div>
			<div class="content-block add-slide-blocks toggle-block active alt"
				id="assign-profiles">
				<form class="settings-form" action="#" id="supplierContentView">
					<div class="headline">
						<h2>
							<a href="#" class="open-close"><spring:message code="com.adminui.create_profile_group.CONTENTVIEWS" text="default text" /></a>
						</h2>
					</div>
					<div class="block">
						<div class="content">
							<div class="content editable-widget">
								<div class="profiles-box user-all">
									<div class="btn-holder">
										<a href="javascript:void(0);" class="btn-add-profile"
											onclick="showSupplierProfiles('${param['supplierId']}')"><span><em
												class="checkboxReset">ADD CONTENT VIEW</em></span></a>
										<c:if
											test="<%=aclManager.allow(request, Permission.CREATE_CONTENT_VIEW)%>">
											<a href="#create-profile"
												class="btn-create-profile open-popup"><span><em><spring:message code="com.adminui.details_overlay.CreateContentView" text="default text" /></em></span></a>
										</c:if>
									</div>
									<div id="assignUserProfilesTable">
										<jsp:include page="supplier_detail_contentview.jsp" />
									</div>
									<input type="hidden" name="suplierID" id="suplierID"
										value="${param['supplierId']}" />
									<div class="function">
										<ul>
											<li><a href="javascript:void(0)"
												class="ico-remove btn-remove-profile"
												onclick="deleteSupplierProfilesConfirm();"><span><em><spring:message code="com.adminui.edit_profile.REMOVE" text="default text" /></em></span></a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>


			<div class="content-block add-slide-blocks toggle-block active alt"
				id="approvers-section">
				<div class="headline">
					<h2>
						<a href="#" class="open-close"><spring:message code="com.adminui.supplier_detail.APPROVERS" text="default text" /></a>
					</h2>
				</div>
				<div class="block">
					<div class="content editable-widget">
						<div class="btn-holder">
							<c:if
								test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER)%>">
								<a href="#add-approver" class="btn-add-approver open-popup"><span><em
										class="checkboxReset"><spring:message code="com.adminui.supplier_detail.ADDApprover" text="default text" /></em></span></a>
							</c:if>
							<c:if
								test="<%=aclManager.allow(request, Permission.EDIT_SUPPLIER) && aclManager.allow(request, Permission.CREATE_APPROVER) %>">
								<a href="#create-approver"
									class="btn-create-approver open-popup"><span><em><spring:message code="com.adminui.supplier_detail.CreateApprover" text="default text" /></em></span></a>
							</c:if>
						</div>
						<form id='assignApproversForm' onsubmit='return false;'
							method='post'>
							<jsp:include page="supplier_approvers_fragment.jsp" />
						</form>
					</div>
				</div>
			</div>


			<div class="content-block add-slide-blocks toggle-block active alt"
				id="supplier-users">
				<div class="headline">
					<h2>
						<a href="#" class="open-close"><spring:message code="com.adminui.supplier_detail.Supplierusers" text="default text" /></a>
					</h2>
				</div>
				<div class="block" id="supplier-users-section">
					<div class="content editable-widget">
						<div class="btn-holder">
							<a class="btn-create-user" onclick="openNewUserPopup();"><span><em><spring:message code="com.adminui.supplier_detail.ADDUSER" text="default text" /></em></span></a>
						</div>
						<div id="supplier-user-data">
							<jsp:include page="supplier_users_fragment.jsp" />
						</div>
					</div>

				</div>
			</div>
		</div>
		<div id="sidebar">
			<ul class="sub-nav">
				<li><a href='<c:url value="/suppliers" />' class="ico-back"><span><spring:message code="com.adminui.supplier_detail.BACK" text="default text" /></span></a></li>
				<li><a href="#upload-logo" class="ico-mapping alt-opener"><span><spring:message code="com.adminui.supplier_detail.UploadLogo" text="default text" /></span></a></li>
				<li><a href="#info-section" class="ico-mapping alt-opener"><span><spring:message code="com.adminui.supplier_detail.SupplierIDMapping" text="default text" /></span></a></li>
				<li><a href="#company-attributes"
					class="ico-attributes alt-opener"><span><spring:message code="com.adminui.supplier_detail.CompanyAttributes" text="default text" /></span></a></li>
				<li><a href="#assign-profiles" class="ico-mapping alt-opener"><span><spring:message code="com.adminui.user_edit.AssignContentViews" text="default text" /></span></a></li>
			</ul>
			<!--h3>JUMP TO</h3>
            <ul class="sub-nav">
                <li><a href="#supplier-mapping" class="ico-mapping alt-opener"><span>Supplier iD Mapping</span></a></li>
                <li><a href="#company-attributes" class="ico-attributes alt-opener"><span>Supplier attributes</span></a></li>
            </ul-->
		</div>
	</div>
</div>

<script type="text/javascript">
	var form_modified = false;
    $(document).ready(function() {
        try{
        	
            $('#companyName').val($('#companyNameHidden').val());
            $('#dunsNumber').val($('#dunsNumberHidden').val());
            $('#defaultVendorId').val($('#defaultVendorIdHidden').val());
            $('#addressLine1').val($('#addressLine1Hidden').val());
            $('#addressLine2').val($('#addressLine2Hidden').val());
            $('#stateCounty').val($('#stateCountyHidden').val());
            $('#city').val($('#cityHidden').val());
            $('#zipPostalCode').val($('#zipPostalCodeHidden').val());
            $('#contactName').val($('#contactNameHidden').val());
            $('#contactRole').val($('#contactRoleHidden').val());
            $('#phoneNumber').val($('#phoneNumberHidden').val());
            $('#faxNumber').val($('#faxNumberHidden').val());
            $('#emailAddress').val($('#emailAddressHidden').val());
            $('#minValue').val($('#minValueHidden').val());

            if($('#nonCatalogSupplierHidden').val()=='true'){
                $('#nonCatalogSupplier').next().addClass('ui-state-active');
                var checkbox = $('#nonCatalogSupplier')[0];
                checkbox.checked=true;
            }
            if($('#singleSourceSupplierHidden').val()=='true'){
                $('#singleSourceSupplier').next().addClass('ui-state-active');
                var checkbox = $('#singleSourceSupplier')[0];
                checkbox.checked=true;
            }
            if($('#includeSupplierCardHidden').val()=='true'){
                $('#includeSupplierCard').next().addClass('ui-state-active');
                var checkbox = $('#includeSupplierCard')[0];
                checkbox.checked=true;
            }

            if($('#disableBrowseHidden').val()=='true'){
                $('#disableBrowse').next().addClass('ui-state-active');
                var checkbox = $('#disableBrowse')[0];
                checkbox.checked=true;
            } else {
                $('#disableBrowse').next().removeClass('ui-state-active');
                var checkbox = $('#disableBrowse')[0];
                checkbox.checked=false;
            }

            addShowSupplierLogo();
            toggleStatesSelect();

            var language = $('#languageHidden').val();
            if(language == '1'){
                language = "en";
            } else if(language == '2'){
                language = "de";
            } else if(language == '3'){
                language = "nl";
            } else if(language == '4'){
                language = "es";
            } else if(language == '5'){
                language = "fr";
            }
            var selectedText = "";
            if(language != ''){
                $("select#language").val(language);
                selectedText = $('option#' + language).text();
                $('#languageRow span.center').html(selectedText);
            }

            var decimalNotation = $('#decimalNotationHidden').val();
            var countryId = $("#supplier-form select[name='countryRegion']").val();
            selectedText = "";
            if(decimalNotation != ''){
                $("select#decimalNotation").val(decimalNotation);
                selectedText = $('option#' + countryId).text();
                $('#decimalNotationRow span.center').html(selectedText);
            }

            var timezone = $('#timezoneHidden').val();
            selectedText = "";
            if(timezone != ''){
                $("select#timezone").val(timezone);
                selectedText = $('option#' + timezone).text();
                $('#timezoneRow span.center').html(selectedText);
            }

            var dateFormat = $('#dateFormatHidden').val();
            selectedText = "";
            if(dateFormat != ''){
                $("select#dateFormat").val(dateFormat);
                selectedText = $('option#' + dateFormat).text();
                $('#dateFormatRow span.center').html(selectedText);
            }

            $("#supplier-form input, #supplier-form select").change(function(){
                form_modified = true;
            });
        }catch(exp){
            //alert(exp);
        }
        toggleStatesSelect();
    });
    
    function toggleStatesSelect(){
        var countryId = $("#supplier-form select[name='countryRegion']").val();
        if(countryId == "1") {
			$("#stateCountySelectBox").show();
			$("#stateCounty").hide();
        } else {
        	$("#stateCountySelectBox").hide();
        	$("#stateCounty").show();
        }
    }
    function setCountryValue() {
    	$("#supplier-form input[name='stateCounty']").val($("#supplier-form select[name='stateCountySelect']").val());
    }

    function savingSupplier() {
    	form_modified = false;
    }
    window.onbeforeunload = function() { 
      if (form_modified) {
    	  return 'You have made changes on this page. If you navigate away, you will loose your unsaved changes.';
      }
    }

    function setLogoFile() {
        try{
            var fileName = document.getElementById('supplier-logo-file').value;
            if(fileName != ''){
                var ext = $('#supplier-logo-file').val().split('.').pop().toLowerCase();
                if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                    alert('Invalid file: '+$('#supplier-logo-file').val() +'. Allowed files types are [ gif, png, jpg, jpeg ]');
                    resetLogoImage();
                    return;
                }

                var indexPos=0;

                //ie8 security restriction hack
                if( fileName.indexOf("C:\\fakepath\\") == 0 || fileName.indexOf("C://fakepath//") == 0  ){
                    indexPos = "C:\\fakepath\\".length-1;
                }

                $('#supplier-logo-form').submit();
                noty({text: 'The supplier record has been updated, you may need to republish associated catalogs for the changes to take effect.', type: 'warning'});
            } else {
                alert('Error: Can not get filename!');
                $('#supplier-logo-fileEM').html('');
            }
        }catch(exp){
            alert(exp);
        }
    }
    function resetLogoImageConfirm(){
    	deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', 
    			resetLogoImage);
    }
    function resetLogoImage(){
        $("#supplier-logo-form").resetForm();
        try{
            var supplierId = "${param['supplierId']}";
            if(supplierId != '') {
                removeSupplierLogo(supplierId);
            }
            //$("#supplier-logo-form input[name='logoFile']").val('');
            $("#supplier-logo-form input[name='supplierLogo']").val('');
            addShowSupplierLogo();
        }catch(exp){
            alert(exp);
        }
    }
    function addShowSupplierLogo() {
        try{
            var contextPath = "<c:url value='/' />";
            var supplierLogo = $("#supplier-logo-form input[name='supplierLogo']").val();
            if(supplierLogo == '') {
                $('#uploadSupplierLogoAddBtn').show();
                $('#uploadSupplierLogoImg').hide();
            } else {
                $('#uploadSupplierLogoImg img').attr('src', contextPath+supplierLogo+'?type=supplier');
                $('#uploadSupplierLogoAddBtn').hide();
                $('#uploadSupplierLogoImg').show();
            }
        }catch(exp){
            alert(exp);
        }
	}
    
    function deleteSupplierUsersConfirm() {
    	if(isAnyItemChecked('supplier-users-form')) {
    		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', deleteSupplierUsers);
    	} else { 
    		alert('Please select an item');
    	}
    }

    function deleteSupplierApproversConfirm() {
    	if(isAnyItemChecked('assignApproversForm')) {
    		deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', removeSupplierApprovers);
    	} else { 
    		alert('Please select an item');
    	}
    }



    </script>
<script type="text/javascript" src="res/js/supplier_detail.js"></script>
