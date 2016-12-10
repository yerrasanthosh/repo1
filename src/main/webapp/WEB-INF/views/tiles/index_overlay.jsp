<%@page import="com.vroozi.customerui.appConfig.model.AdminUICache"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
    Start Catalog
--%>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>
<c:set var="timeZones" value="<%= AdminUICache.getTimeZonesList() %>" />

<div class="lightbox-section">
    <div class="lightbox" id="lightbox-inline">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                    <h2 id="title-id"><spring:message code="com.adminui.index_overlay.CreateCatalog" text="default text" /></h2>
                </div>
                <div style="display:none" id="newVersionMessage">
                	<span style="color: red"><spring:message code="com.adminui.index_overlay.newVersion" text="default text" /></span>
                </div>
                <form action="createCatalog" class="form-create" id="theCatalogId" method="post" enctype="multipart/form-data">
                	<input type="hidden" id="hdnURL" value="${adminUIPath}"/>
                    <a id="quoteFileCross" onclick="replaceCatFile();" style="z-index: 101; margin-left: 680px;  position: absolute;" class="close1"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                    <a id="zipFileCross" onclick="replaceZipFile();" style="z-index: 101; margin-left: 680px;  position: absolute;" class="close1"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                    <fieldset>
                        <div class="area">
                            <div class="row" id="theCatalogNameId">
                                <label for="catalog-name"><span>*</span><spring:message code="com.adminui.index_overlay.CatalogName" text="default text" /></label>
                                <input type="text" name="catalogName" id="catalog-name" maxlength="64" class="required" value="">
                                <input type="hidden" id="catalogStateIdHidden" />
                            </div>

                            <div class="row" id="theQuoteIdfield">
                                <label for="quote-id"><span>*</span><spring:message code="com.adminui.index_overlay.QuoteID" text="default text" /></label>
                                <input type="text" name="quoteId" id="quote-id" maxlength="64" class="required" value="">
                                <input type="hidden" id="quoteIdStateIdHidden" />
                            </div>

                            <div class="row" id="theCatalogFileId">
                                <label class="txtFile"><spring:message code="com.adminui.index_overlay.CatalogFile" text="default text" /></label>
                                <input  type="file" name="catalogFile" onchange="showVersionMessageCat();" width="64" id="catalog-file"/>
                            </div>
                            <div class="row" id="theImageFileId">
                                <label><spring:message code="com.adminui.index_overlay.ImageFile" text="default text" /></label>
                                <input type="file" onchange="showVersionMessageCat();" name="imageFile" width="64" id="image-file" />
                            </div>

                            <c:if test="<%=aclManager.allow(request, Permission.CHANGE_SUPPLIER)%>">
                            <div class="row" id="SupplierCompany" style="overflow:visible">
                                <label><spring:message code="com.adminui.index_overlay.SupplierCompany" text="default text" /></label>
                                <input type="hidden" id="supplierName" name="supplierName" />
                    			<input type="text" id="supplierNameDisplay" style="width: 200px;" onclick="clearExistingSupplier()" />
                            </div>
                            </c:if>
                            <c:if test="<%=!aclManager.allow(request, Permission.CHANGE_SUPPLIER)%>">
   	                        	<input type="hidden" name="supplierName" id="supplierName" value="${supplierAdminId}"/>
                           </c:if>
							
							<div id="supplierHidden" style="display:none">
								
							</div>

                            <div class="row" id="external-catalog-check">
                                <input type="checkbox" class="check-allbox" onchange="handleSearchAllowed();" name="externalCatalog" id="check-external-catalog" />
                                <label for="check-external-catalog"><spring:message code="com.adminui.index_overlay.ExternalCatalog" text="default text" /></label>
                            </div>

                            <div class="row" style="display:none" id="search-allowed-check">
                                <input type="checkbox" class="check-allbox" name="searchAllowed" id="check-search-allowed" />
                                <label for="check-search-allowed"><spring:message code="com.adminui.index_overlay.SearchAllowed" text="default text" /></label>
                            </div>                            
                            
                            <div class="row" id="create-version-check" style="display:none">
                                <input type="checkbox" class="check-allbox" name="createVersion" id="check-create-version" />
                                <label for="check-create-version"><spring:message code="com.adminui.index_overlay.DirectItemUpdate" text="default text" /></label>
                            </div>
							
							<div class="row" id="quantity-locked-check">
                                <input type="checkbox" class="check-allbox" name="quantityLocked" id="check-quantity-locked" />
                                <label for="check-quantity-locked"><spring:message code="com.adminui.index_overlay.QuantityLocked" text="Restrict Quantity Changes" /></label>
                            </div>   
                        </div>
                        <div class="add-slide-blocks">
                            <div class="toggle-block">
                                <div class="title">
                                    <h2><a class="open-close" href="#"><spring:message code="com.adminui.index_overlay.AdditionalFields" text="default text" /></a></h2>
                                </div>
                                <div class="block">
                                    <div class="content">
                                        <div class="area">

                                            <div class="row" id="catalog-edit-options" style="display: block">
                                                <label><spring:message code="com.adminui.index_overlay.EditOptions" text="default text" /></label>
                                                <select name="catalogEditOption" id="edit-catalog-edit-option">
                                                    <option value="1" id="101"><spring:message code="com.adminui.index_overlay.MergeUpdate" text="default text" /></option>
                                                    <option value="2" id="102"><spring:message code="com.adminui.index_overlay.OverwriteAll" text="default text" /></option>
                                                </select>
                                            </div>

                                            <div class="row" id="catalog-id-field">
                                                <label for="catalog-id"><spring:message code="com.adminui.index_overlay.CatalogID" text="default text" /></label>
                                                <input type="text" id="catalog-id" name="clientCatalogId"  value=""/>
                                            </div>

                                            <div class="row" id="rfqNumberRow">
                                                <label for="catalog-id"><spring:message code="com.adminui.index_overlay.RFQNumber" text="default text" /></label>
                                                <input type="text" id="rfq-id" name="rfqNumber"  value=""/>
                                            </div>

                                            <div class="row">
                                                <label for="catalog-num"><spring:message code="com.adminui.index_overlay.ContractNumber" text="default text" /></label>
                                                <input type="text" id="catalog-num" name="contractNumber" value=""/>
                                            </div>
                                            <div class="row">
                                                <label for="contract-line"><spring:message code="com.adminui.index_overlay.ContractLineItem" text="default text" /></label>
                                                <input type="text" id="contract-line" name="contractLineItem" value=""/>
                                            </div>
                                            <div class="row">
                                                <label for="start-date"><spring:message code="com.adminui.index_overlay.CatalogStartDate" text="default text" /></label>
                                                <input type="text" class="timepicker-input" id="start-date" name="startDate"  value=""  readonly="readonly"/>
                                                <label for="start-date"></label>
                                                <label style="margin: 0 100px -13px;" id="start-date-label" hidden="true" class="error error-msg" generated="true" for="start-date-message"><spring:message code="com.adminui.index_overlay.InvalidStartDate" text="default text" /></label>
                                            </div>
											<div class="row">
                                                <label for="end-date"><spring:message code="com.adminui.index_overlay.CatalogEndDate" text="default text" /></label>
                                                <input type="text" class="timepicker-input" id="end-date" name="endDate"  value=""  readonly="readonly"/>
                                                <label for="end-date"></label>
                                                <label style="margin: 0 100px -13px;" id="end-date-label" hidden="true" class="error error-msg" generated="true" for="end-date-message"><spring:message code="com.adminui.index_overlay.InvalidEndDate" text="default text" /></label>
                                            </div>
                                            <div class="row">
                                            	<label for="time-zone"><spring:message code="com.adminui.index_overlay.TimeZone" text="default text" /></label>
                                            	<select name="timeZoneId" id="timeZone" style="display:block">
                                            		<c:forEach var="timeFormat" items="${timeZones}">
                                                    	<option id="${timeFormat.timeZoneId}" 
                                                    	<c:if test="${sessionScope.user.timeZone eq timeFormat.timeZoneId}"> selected="selected"</c:if> value="${timeFormat.timeZoneId}">${timeFormat.timeZoneName}</option>
                                                    </c:forEach>
                                            	</select>	                                                                                        	
                                            </div>
											<!-- 
                                            <div class="row">
                                                <label for="start-date">Start Date:</label>
                                                <input type="text" class="timepicker-input" id="start-date" name="startDate"  value=""  readonly="readonly"/>
                                            </div>
                                            <div class="row">
                                                <label for="end-date">End Date:</label>
                                                <input type="text" class="timepicker-input" id="end-date" name="endDate"  value=""  readonly="readonly"/>
                                                <label for="end-date"></label>
                                                <label style="margin: 0 100px -13px;" id="end-date-label" hidden="true" class="error error-msg" generated="true" for="end-date-message">Invalid End Date</label>
                                                
                                            </div> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="toggle-block" id="external-catalog-fields">
                                <div class="title">
                                    <h2><a class="open-close" href="#"><spring:message code="com.adminui.index_overlay.EXTERNALCATALOGDEFINITION" text="default text" /></a></h2>
                                </div>
                                <div class="block">
                                    <br>
                                    <div class="row" id="commMethod">
                                        <label><spring:message code="com.adminui.index_overlay.CommunicationStandard" text="default text" /></label>
                                        <select name="extCommunicationMethod" id="extCommunicationMethod" onChange="onSelectingCXML()">
                                            <option id="OCI" value="OCI"><spring:message code="com.adminui.index_overlay.OCI" text="default text" /></option>
                                            <option id="CXML" value="CXML"><spring:message code="com.adminui.index_overlay.CXML" text="default text" /></option>
                                        </select>
                                    </div>
                                    <div class="row" id="extMethod">
                                        <label><spring:message code="com.adminui.index_overlay.Method" text="default text" /></label>
                                        <select name="extCatalogMethod" id="extCatalogMethod">
                                            <option id="POST" value="POST"><spring:message code="com.adminui.index_overlay.POST" text="default text" /></option>
                                            <option id="GET" value="GET"><spring:message code="com.adminui.index_overlay.GET" text="default text" /></option>
                                        </select>
                                    </div>
                                    <div class="row" id="extImageField">
                                        <label><spring:message code="com.adminui.index_overlay.LookforImagesIn" text="default text" /></label>
                                        <select name="extCatalogImageField" id="extCatalogImageField">
                                        	<option value=""></option>
                                            <option id="NEW_ITEM-ATTACHMENT" value="NEW_ITEM-ATTACHMENT">NEW_ITEM-ATTACHMENT</option>
                                            <option id="NEW_ITEM-CUST_FIELD1" value="NEW_ITEM-CUST_FIELD1">NEW_ITEM-CUST_FIELD1</option>
                                            <option id="NEW_ITEM-CUST_FIELD2" value="NEW_ITEM-CUST_FIELD2">NEW_ITEM-CUST_FIELD2</option>
                                            <option id="NEW_ITEM-CUST_FIELD3" value="NEW_ITEM-CUST_FIELD3">NEW_ITEM-CUST_FIELD3</option>
                                            <option id="NEW_ITEM-CUST_FIELD4" value="NEW_ITEM-CUST_FIELD4">NEW_ITEM-CUST_FIELD4</option>
                                            <option id="NEW_ITEM-CUST_FIELD5" value="NEW_ITEM-CUST_FIELD5">NEW_ITEM-CUST_FIELD5</option>
                                        </select>
                                    </div>
                                    <div class="row" id="encodingTypeField">
                                        <label>Encoding:</label>
                                        <select name="encodingType" id="encodingType">
                                            <option id="URL_ENCODING" value="URL_ENCODING">URL Encoding</option>
                                            <option id="BASE64_ENCODING" value="BASE64_ENCODING">BASE64 Encoding</option>
                                        </select>
                                    </div>
                                    <div class="content reorder-widget editable-widget">
                                        <div class="btn-holder">
                                            <a class="btn-add-external-catalog" id="btn-add-external-catalog-id"><span><em><spring:message code="com.adminui.index_overlay.ADD" text="default text" /></em></span></a>
                                        </div>
                                        <table class="table-data external-catalog" id="table-external-catalog-id">
                                            <thead>
                                            <tr>
                                                <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all10" id="check10-1" onclick="toggleAllCheckboxItems(this.checked, 'table-external-catalog-id');" /><label for="check10-1"></label></th>
                                                <th class="a-center th-name" ><spring:message code="com.adminui.index_overlay.SEQ" text="default text" /></th>
                                                <th class="a-center th-name" ><spring:message code="com.adminui.index_overlay.NAME" text="default text" /></th>
                                                <th colspan="2"><spring:message code="com.adminui.index_overlay.Value" text="default text" /></th>
                                            </tr>
                                            </thead>
                                            <tbody  id="table-body-external-catalog">
                                                <!--tr class="added">
                                                    <td>
                                                        <a href="#" class="btn-up-down"><span class="up-arrow">up</span> <span class="down-arrow">down</span></a>
                                                        <input type="checkbox" name="check10" class="target-chbox" id="check10-2" /><label for="check10-2"></label>
                                                    </td>
                                                    <td class="a-left td-name-catalog">
                                                        <div>
                                                            <input type="text" value="Enter Name" name="fieldName" class="field-name" />
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div>
                                                            <input type="text" value="Enter Value" name="fieldValue" class="field-value" />
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div>
                                                            <select id="s-val" name ="dynamicValue">
                                                                <option value="">Or Select Dynamic Value</option>
                                                                <option>Option 2</option>
                                                            </select>
                                                        </div>
                                                    </td>
                                                </tr-->
                                            </tbody>
                                        </table>
                                        <div class="function">
                                            <ul>
                                                <!--li><a href="#" class="ico-edit"><span><em>EDIT</em></span></a></li-->
                                                <li><a href="#" class="ico-reorder" onclick="createExternalCatalogOverlayUrl('<fmt:bundle basename="adminui"><fmt:message key="cXMLPunchOutUrl"/></fmt:bundle>');" target="_parent"><span><em><spring:message code="com.adminui.index_overlay.ValidatePunchOut" text="default text" /></em></span></a></li>
                                                <li><a class="ico-remove" id="external-catalog-remove"><span><em><spring:message code="com.adminui.index_overlay.REMOVE" text="default text" /></em></span></a></li>
                                                <!--li><input type="button" value="Save" /></li-->
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                             <c:if test="<%=aclManager.allow(request, Permission.VIEW_CONTENT_VIEW)%>">
	                            <div class="toggle-block">
	                                <div class="title">
	                                    <h2><a class="open-close" href="#"><spring:message code="com.adminui.index_overlay.CONTENTVIEWS" text="default text" /></a></h2>
	                                </div>
	                                <div class="block" style="overflow-y:auto;">
	                                    <div class="content">
	                                        <div class="area">
	
	                                            <jsp:include page="index_overlay_profiles_fragment.jsp"/>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
                            </c:if>
                            
                            <c:if test="<%=!aclManager.allow(request, Permission.VIEW_CONTENT_VIEW)%>">
                            	<input type="hidden" name="profileIds" id="profileHiddenField" />
                            </c:if>
                            
                            <c:if test="<%=aclManager.allow(request, Permission.VIEW_APPROVER)%>">
	                            <div class="toggle-block">
	                                <div class="title">
	                                    <h2><a class="open-close " href="#"><spring:message code="com.adminui.index_overlay.Approvers" text="default text" /></a></h2>
	                                </div>
	                                <div class="block" style="overflow-y:auto;">
	                                    <div class="content">
	                                        <div class="area">
	                                            <jsp:include page="index_overlay_approvers_fragment.jsp"/>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
                            </c:if>
                            <c:if test="<%=!aclManager.allow(request, Permission.VIEW_APPROVER)%>">
								<input type="hidden" name="approverIds" id="approverHiddenField" />
                            </c:if>


                            
                            <div class="btns-holder">
                                <a href="#" class="btn-cancel" id="create-catalog-btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                                <a href="#" class="btn-reset" id="create-catalog-btn-reset" ><span><spring:message code="com.adminui.index_overlay.Reset" text="default text" /></span></a>
                                <!--a href="#" class="btn-create"><span>Create</span></a-->
                                <input type="submit" name="submitButton" value="Create" id="createCatalogSubmitButton"/>

                                <p><span class="required">* </span><spring:message code="com.adminui.index_overlay.RequiredField" text="default text" /></p>
                                <p><span id="createCatalogReplyDiv" style="font-style: italic; color: red;"></span></p>
                            </div>
                        </div>
                        <input type="hidden" name="catalogId" id="catalogId" value="" maxlength="10"/>
                        <input type="hidden" name="catalogTypeId" id="catalog-type-id" value=""/>
                    </fieldset>
                </form>
                <div class="contain-progress-bar" style="display: none;">
                    <div class="progress" style="width:840px;">
                        <div class="bar"></div>
                        <div class="percent">0%</div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>


<%--
    End External Catalog
--%>

<!--div class="lightbox-section">
    <div class="lightbox" id="lightbox-inline">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close">Close</a>
                    <h2 id="catalogPopupTitle">Create Catalog</h2>
                </div>
                <form action="createCatalog" class="form-create" id="theCatalogId" method="post" enctype="multipart/form-data">
                    <fieldset>
                        <div class="area">
                            <div class="row" id="theCatalogNameId">
                                <label for="catalog-name"><span>*</span>Catalog Name:</label>
                                <input type="text" name="catalogName" id="catalog-name" maxlength="64" class="required" value="">
                            </div>
                            <div class="row" id="theCatalogFileId">
                                <label for="catalog-file"><span>*</span>Catalog File:</label>
                                <input type="file" name="catalogFile" id="catalog-file" />
                                <label for="catalog-file" generated="true" class="error"></label>
                            </div>
                            <div class="row">
                                <label>Image File (.zip):</label>
                                <input type="file" value="" name="imageFile" />
                            </div>
                            <div class="row" id="SupplierCompany">
                                <label>Supplier Company:</label>
                                <select name="supplierName" id="supplierName">
                                    <option value="0"></option>
                                    <%--<c:forEach var="supplierCompany" items="${supplierCompanyList}">--%>
                                        <%--<option id="${supplierCompany.companyId}" value="${supplierCompany.companyId}">${supplierCompany.companyName}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
                            </div>
                            <div class="row" id="ExternalCatalogs">
                                <label>External Catalogs:</label>
                                <select name="externalCatalogId" id="externalCatalogId">
                                    <option value="0"></option>
                                    <%--<c:forEach var="externalCatalog" items="${externalCatalogList}">--%>
                                        <%--<option value="${externalCatalog.catalogId}">${externalCatalog.name}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
                            </div>
                            <div class="row">
                                <label for="catalog-id">Catalog ID:</label>
                                <input type="text" id="catalog-id" name="clientCatalogId"  value=""/>
                            </div>
                            <div class="row">
                                <label for="start-date">Start Date:</label>
                                <input type="text" class="timepicker-input" id="start-date" name="startDate"  value=""  readonly="readonly"/>
                            </div>
                            <div class="row">
                                <label for="end-date">End Date:</label>
                                <input type="text" class="timepicker-input" id="end-date" name="endDate"  value=""  readonly="readonly"/>
                            </div>
                        </div>

                        <div class="btns-holder">
                            <a href="#" class="btn-cancel">Cancel</a>
                            <a href="#" class="btn-reset" id="create-catalog-btn-reset"><span>Reset</span></a>
                            <input type="submit" name="submitButton" value="Create" id="createCatalogSubmitButton"/>
                            <p><span style="color: #ED1C24;">* </span>Required Field</p>
                            <p><span id="createCatalogReplyDiv" style="font-style: italic; color: red;"></span></p>
                        </div>
                        <input type="hidden" name="catalogId" id="catalogId" value="" maxlength="10"/>
                    </fieldset>
                </form>

                <div class="contain-progress-bar" style="display: none;">
                    <div class="progress" style="width:840px;">
                        <div class="bar"></div>
                        <div class="percent">0%</div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div-->

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-confirmation">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.APPROVE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.ApproveConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content" id="msg-popup-text">
                        <p><spring:message code="com.adminui.index_overlay.confirmApprove" text="default text" /></p>
                        <!--p><strong>[lorem ipsum dolor sit amet]</strong></p-->
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.APPROVE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermission" text="default text" /></p>
                    </div>
                </c:if>
                <div class="btns-holder">
                    <div class="btns-frame">

                       	<a id="cancelLink" href="#" class="btn-cancel"><span id="btnCancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></span></a>
                       	<a id="okLink" href="#" class="btn-cancel btn"><span><spring:message code="com.adminui.index_overlay.OK" text="default text" /></span></a>
                        
                        <c:if test="<%=aclManager.allow(request, Permission.APPROVE_CATALOG)%>">
                            <a id="btnOK" href="#" class="btn btn-approve"><span><spring:message code="com.adminui.index_overlay.APPROVE" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-deactivate">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxDeactivateCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.DeactivateConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.clickDeactivate" text="default text" /></p>
                        <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxDeactivateCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermissionD" text="default text" /></p>
                    </div>
                </c:if>                
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeactivateCatalogCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                            <a id="lighboxDeactivateCatalogBtn" href="#" class="btn btn-red "><span><spring:message code="com.adminui.index_overlay.DEACTIVATE" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-activate">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxActivateCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.ActivateConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.confirmActivate" text="default text" /></p>
                        <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxActivateCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermissionA" text="default text" /></p>
                    </div>
                </c:if>                
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxActivateCatalogCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                            <a id="lighboxActivateCatalogBtn" href="#" class="btn btn-red "><span><spring:message code="com.adminui.index_overlay.ACTIVATE" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-reject">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxRejectCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.RejectConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.RejectConfirmation" text="default text" /></p>
                        <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxRejectCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermissionR" text="default text" /></p>
                    </div>
                </c:if>                
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxRejectCatalogCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.REJECT_CATALOG)%>">
                            <a id="lighboxRejectCatalogBtn" href="#" class="btn btn-red "><span><spring:message code="com.adminui.index_overlay.REJECT" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-publish">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.PUBLISH_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.PublishConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content" id="msg-publish-text">
                        <p><spring:message code="com.adminui.index_overlay.confirmPublish" text="default text" /></p>
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.PUBLISH_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermissionP" text="default text" /></p>
                    </div>
                </c:if>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="cancelPublishLink" href="#" class="btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                        <a id="okPublishLink" href="#" class="btn-cancel btn"><span><spring:message code="com.adminui.index_overlay.OK" text="default text" /></span></a>
                        <c:if test="<%=aclManager.allow(request, Permission.PUBLISH_CATALOG)%>">
                            <a id="btnPublishOK" href="#" class="btn btn-green publish-btn"><span><spring:message code="com.adminui.index_overlay.PUBLISH" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.OFFLINE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.UnPublishConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p></p>
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.OFFLINE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.UnPublishConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.confirmUnP" text="default text" /></p>
                    </div>
                </c:if>

                <div class="btns-holder">
                    <div class="btns-frame">
                        <c:if test="<%=aclManager.allow(request, Permission.OFFLINE_CATALOG)%>">
                            <a href="#" class="btn btn-green"><span><spring:message code="com.adminui.index_overlay.UNPUBLISH" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-inactive">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.DEACTIVATE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.DisapprovalConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content" id="msg-disapprove-text">
                        <p><spring:message code="com.adminui.index_overlay.clickDisapprove" text="default text" /></p>
                        <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.DEACTIVATE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermissionDisapprove" text="default text" /></p>
                    </div>
                </c:if>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" id="cancelDisapproveLink" class="btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                        <a id="okDisapproveLink" href="#" class="btn-cancel btn"><span><spring:message code="com.adminui.index_overlay.OK" text="default text" /></span></a>
                        <c:if test="<%=aclManager.allow(request, Permission.DEACTIVATE_CATALOG)%>">
                            <a href="#" id="btnDisapproveOK" class="btn btn-red publish-btn"><span><spring:message code="com.adminui.index_overlay.DISAPPROVE" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>




<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.DELETE_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxDeleteCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.DeletionConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.confirmDel" text="default text" /></p>
                        <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.DELETE_CATALOG)%>">
                    <div class="title">
                        <a id="lighboxDeleteCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermissionDel" text="default text" /></p>
                    </div>
                </c:if>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteCatalogCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                        <c:if test="<%=aclManager.allow(request, Permission.DELETE_CATALOG)%>">
                            <a id="lighboxDeleteCatalogBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.index_overlay.DELETE" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-offline">
        <div class="holder">
            <div class="frame">
                <c:if test="<%=aclManager.allow(request, Permission.OFFLINE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.OfflineConfirmation" text="default text" /></h2>
                    </div>
                    <div class="content" id="msg-offline-text">
                        <p><spring:message code="com.adminui.index_overlay.clickOfline" text="default text" /></p>
                        <!--p><strong>[lorem ipsum dolor sit amet]</strong></p-->
                    </div>
                </c:if>
                <c:if test="<%=!aclManager.allow(request, Permission.OFFLINE_CATALOG)%>">
                    <div class="title">
                        <a href="#" class="close"><spring:message code="com.adminui.index_overlay.Close" text="default text" /></a>
                        <h2><spring:message code="com.adminui.index_overlay.Alert" text="default text" /></h2>
                    </div>
                    <div class="content">
                        <p><spring:message code="com.adminui.index_overlay.noPermissionOfline" text="default text" /></p>
                    </div>
                </c:if>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" id="cancelOfflineLink" class="btn-cancel"><spring:message code="com.adminui.index_overlay.Cancel" text="default text" /></a>
                        <a id="okOfflineLink" href="#" class="btn-cancel btn"><span><spring:message code="com.adminui.index_overlay.OK" text="default text" /></span></a>
                        <c:if test="<%=aclManager.allow(request, Permission.OFFLINE_CATALOG)%>">
                            <a href="#" id="btnOfflineOK" class="btn btn-red offline-btn"><span><spring:message code="com.adminui.index_overlay.OFFLINE" text="default text" /></span></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">


    var supplierTypeAheadURL = '<c:url value="/getSuppliersForTypeAhead" />';
</script>