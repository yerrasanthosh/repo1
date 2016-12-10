<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="lightbox-section">
    <div class="lightbox" id="create-vendorId">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_vendor_id_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_vendor_id_overlay.CreateSystemBasedVendorID" text="default text" /></h2>
                </div>
                <form action="addSystemVendorId" class="form-create" id="systemVendors" method="post">
                    <input name="companyId" type="hidden" value='<c:out value="${param['companyId']}"/>' />
                    <input name="vendorName" type="hidden" value='<c:out value="${param['companyName']}"/>' />
                    <fieldset>
                        <%--<h3>PROFILE SETTINGS</h3>--%>
                        <div class="area">
                            <div class="row" id="theVendorId">
                                <label for="vendor-id"><span>*</span><spring:message code="com.adminui.create_vendor_id_overlay.VendorId" text="default text" /></label>
                                <input type="text" name ="vendorNumber" id="vendor-id" class="required"/>
                            </div>
                            <div class="row" id="theSystemId">
                                <label for="system-id"><span>*</span><spring:message code="com.adminui.create_vendor_id_overlay.SystemId" text="default text" /></label>
                                <input type="text" name ="systemId" id="system-id" class="required"/>

                            </div>
                            <div class="row" id="theClientId">
                                <label for="client-id"><span>*</span><spring:message code="com.adminui.create_vendor_id_overlay.ClientId" text="default text" /></label>
                                <input type="text" name ="clientId" id="client-id" class="required"/>

                            </div>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.create_vendor_id_overlay.Cancel" text="default text" /></a>
                            <a class="btn-create close"  onclick="createSystemVendorId();"><span><spring:message code="com.adminui.create_vendor_id_overlay.Save" text="default text" /></span></a>
                            <!-- input type="submit" value="Save" name="Save" -->
                            <p><span class="required">* </span><spring:message code="com.adminui.create_vendor_id_overlay.RequiredField" text="default text" /></p>
                            <input type="hidden" name="companyId" value="${param['companyId']}"/>
                            <input type="hidden" name="dunsNumber" value="${supplierDetails.dunsNumber}"/>
                            <input type="hidden" name="supplierId" value="${supplierDetails.companyId}"/>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="companyUser">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_vendor_id_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_vendor_id_overlay.CreateCompanyUsers" text="default text" /></h2>
                </div>
                <form action="addCompanyUser" class="form-create" id="usersForm" method="post">
                    <input name="companyId" type="hidden" value='<c:out value="${param['companyId']}"/>' />
                    <input name="companyName" type="hidden" value='<c:out value="${param['companyName']}"/>' />
                    <fieldset>
                        <%--<h3>PROFILE SETTINGS</h3>--%>
                        <div class="area">
                            <div class="row" id="theRoleId">
                                <label for="role-id"><spring:message code="com.adminui.create_vendor_id_overlay.Role" text="default text" /></label>
                                <input type="text" name ="role" value="Supplier Admin" id="role-id" class="required" disabled="disabled"/>
                            </div>
                            <div class="row" id="theCompanyName">
                                <label for="company-name"><spring:message code="com.adminui.create_vendor_id_overlay.CompanyName" text="default text" /></label>
                                <input type="text" name ="companyName" id="company-name" value='<c:out value="${param['companyName']}"/>' class="required" disabled="disabled"/>

                            </div>
                            <div class="row" id="theEmailAdd">
                                <label for="email-add"><span>*</span><spring:message code="com.adminui.create_vendor_id_overlay.EmailAdd" text="default text" /></label>
                                <input type="text" name ="emailAddress" id="email-add" class="required"/>

                            </div>
                            <div class="row" id="theFirstName">
                                <label for="first-name"><span>*</span><spring:message code="com.adminui.create_vendor_id_overlay.FirstName" text="default text" /></label>
                                <input type="text" name ="firstName" id="first-name" class="required"/>

                            </div>
                            <div class="row" id="theLastName">
                                <label for="last-name"><span>*</span><spring:message code="com.adminui.create_vendor_id_overlay.LastName" text="default text" /></label>
                                <input type="text" name ="lastName" id="last-name" class="required"/>

                            </div>
                            <input name="companyId" type="hidden" value='<c:out value="${param['companyId']}"/>' />
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel">Cancel</a>
                            <a class="btn-create close"  onclick="createCompanyUsers();"><span><spring:message code="com.adminui.create_vendor_id_overlay.Save" text="default text" /></span></a>
                            <!-- input type="submit" value="Save" name="Save" -->
                            <p><span class="required">* </span><spring:message code="com.adminui.create_vendor_id_overlay.RequiredField" text="default text" /></p>
                            <%--<input type="hidden" name="companyId" value="${param['companyId']}"/>--%>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="editCompanyUser">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_vendor_id_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_vendor_id_overlay.EditCompanyUsers" text="default text" /></h2>
                </div>
                <form action="editCompanyUser" class="form-create" id="editUsersForm" method="post">
                    <input name="companyId" type="hidden" value='<c:out value="${param['companyId']}"/>' />
                    <input name="companyName" type="hidden" value='<c:out value="${param['companyName']}"/>' />
                    <fieldset>
                        <%--<h3>PROFILE SETTINGS</h3>--%>
                        <div class="area" id="theCompanyUserDiv">


                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.create_vendor_id_overlay.Cancel" text="default text" /></a>
                            <a class="btn-create close"  onclick="editCompanyUsers();"><span>Save</span></a>
                            <!-- input type="submit" value="Save" name="Save" -->
                            <p><span class="required">* </span><spring:message code="com.adminui.create_vendor_id_overlay.RequiredField" text="default text" /></p>
                            <%--<input type="hidden" name="companyId" value="${param['companyId']}"/>--%>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="companyAttribute">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_vendor_id_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_vendor_id_overlay.CreateCompanyAttributes" text="default text" /></h2>
                </div>
                <form action="" class="form-create" id="companyAttributeForm" method="post">
                    <%--<input name="companyId" type="hidden" value='<c:out value="${param['companyId']}"/>' />--%>
                    <%--<input name="companyName" type="hidden" value='<c:out value="${param['companyName']}"/>' />--%>
                    <fieldset>
                        <%--<h3>PROFILE SETTINGS</h3>--%>
                        <div class="area">
                            <div class="row" id="theAttributeName">
                                <label for="attribute-name"><spring:message code="com.adminui.create_vendor_id_overlay.Name" text="default text" /></label>
                                <input type="text" name ="attributeName" id="attribute-name" class="required"/>

                            </div>
                            <div class="row" id="theAttributeDescription">
                                <label for="attribute-description"><span>*</span><spring:message code="com.adminui.create_vendor_id_overlay.Description" text="default text" /></label>
                                <input type="text" name ="attributeDescription" id="attribute-description" class="required"/>

                            </div>
                            <%--<input name="companyId" type="hidden" value='<c:out value="${param['companyId']}"/>' />--%>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.create_vendor_id_overlay.Cancel" text="default text" /></a>
                            <a class="btn-create close"  onclick="createCompanyAttribute();"><span><spring:message code="com.adminui.create_vendor_id_overlay.Save" text="default text" /></span></a>
                            <!-- input type="submit" value="Save" name="Save" -->
                            <p><span class="required">* </span><spring:message code="com.adminui.create_vendor_id_overlay.RequiredField" text="default text" /></p>
                            <%--<input type="hidden" name="companyId" value="${param['companyId']}"/>--%>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-supplier">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteDetailsCloseBtn" href="#" class="close"><spring:message code="com.adminui.create_vendor_id_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_vendor_id_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <%--<p>There are profiles associated to the user, are you sure you want to delete the selected users?</p>--%>
                    <p><spring:message code="com.adminui.create_vendor_id_overlay.msg" text="default text" /></p>
                    <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel"><spring:message code="com.adminui.create_vendor_id_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteDetailsBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.create_vendor_id_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="systemVendorDetails">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_vendor_id_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_vendor_id_overlay.EditSystemVendors" text="default text" /></h2>
                </div>
                <form action="editSystemVendor" class="form-create" id="editSystemVendorForm" method="post">
                    <fieldset>
                        <%--<h3>PROFILE SETTINGS</h3>--%>
                        <div class="area" id="theSystemVendorDiv">


                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.create_vendor_id_overlay.Cancel" text="default text" /></a>
                            <a class="btn-create close"  onclick="editSystemVendor();"><span><spring:message code="com.adminui.create_vendor_id_overlay.Save" text="default text" /></span></a>
                            <!-- input type="submit" value="Save" name="Save" -->
                            <p><span class="required">* </span><spring:message code="com.adminui.create_vendor_id_overlay.RequiredField" text="default text" /></p>
                            <%--<input type="hidden" name="companyId" value="${param['companyId']}"/>--%>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="companyAttributeDetails">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_vendor_id_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_vendor_id_overlay.EditCompanyAttribute" text="default text" /></h2>
                </div>
                <form action="editCompanyAttribute" class="form-create" id="editCompanyAttributeForm" method="post">
                    <fieldset>
                        <%--<h3>PROFILE SETTINGS</h3>--%>
                        <div class="area" id="theCompanyAttributeDiv">


                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.create_vendor_id_overlay.Cancel" text="default text" /></a>
                            <a class="btn-create close"  onclick="editCompanyAttribute();"><span><spring:message code="com.adminui.create_vendor_id_overlay.Save" text="default text" /></span></a>
                            <!-- input type="submit" value="Save" name="Save" -->
                            <p><span class="required">* </span><spring:message code="com.adminui.create_vendor_id_overlay.RequiredField" text="default text" /></p>
                            <%--<input type="hidden" name="companyId" value="${param['companyId']}"/>--%>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

