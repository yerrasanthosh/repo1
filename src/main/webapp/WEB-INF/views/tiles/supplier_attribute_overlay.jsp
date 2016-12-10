<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete-supplier-attributes">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteDetailsCloseBtn" href="#" class="close"> <spring:message code="com.adminui.supplier_attribute_overlay.Close" text="default text" /></a>
                    <h2> <spring:message code="com.adminui.supplier_attribute_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p> <spring:message code="com.adminui.supplier_attribute_overlay.confirmDel" text="default text" /></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel"> <spring:message code="com.adminui.supplier_attribute_overlay.Cancel" text="default text" /></a>
                        <a id="lighboxDeleteDetailsBtn" href="#" class="btn btn-red .delete-btn"><span> <spring:message code="com.adminui.supplier_attribute_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="lightbox" id="lightbox-inline-create-supplier-attribute">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"> <spring:message code="com.adminui.supplier_attribute_overlay.Close" text="default text" /></a>
                    <h2 id="catalogPopupTitle2"> <spring:message code="com.adminui.supplier_attribute_overlay.CreateSupplierAttribute" text="default text" /></h2>
                </div>
                <form action="createSupplierAttribute" class="form-create" id="create-supplier-attribute-form" method="post" enctype="multipart/form-data">
                    <fieldset>
                        <div class="area">
                            <div class="row" id="attributeNameId">
                                <div class="area-col" id="attribute-name-div">
                                    <label for="attribute-name"><span>*</span> <spring:message code="com.adminui.supplier_attribute_overlay.AttributeName" text="default text" /></label>
                                    <input type="text" name="attributeName" id="attribute-name" maxlength="64" class="required" value="">
                                </div>
                            </div>
                            <div class="row">
                                <label for="attribute-description"><span id="attribute-description-span"></span> <spring:message code="com.adminui.supplier_attribute_overlay.CatalogFile" text="default text" /></label>
                                <input type="file" name="attributeDescription" id="attribute-description" />
                                <label  id="label-attribute-description" class="error"></label>
                            </div>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"> <spring:message code="com.adminui.supplier_attribute_overlay.Cancel" text="default text" /></a>
                            <input type="submit" name="submitButton" value="Save" id="createSupplierAttributeSubmitButton"/>
                            <p><span style="color: #ED1C24;">* </span> <spring:message code="com.adminui.supplier_attribute_overlay.RequiredField" text="default text" /></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>    
</div>

