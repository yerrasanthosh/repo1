<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.supplier_attribute_create.Vroozi" text="default text" /></a></li>
            <li><a href='<c:url value="/supplierAttributes"/>'><spring:message code="com.adminui.supplier_attribute_create.SupplierAttributesManagement" text="default text" /></a></li>
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
            <div>
                <div class="content-block toggle-block active" id="info-section">
                    <div class="headline">
                        <h2><a href="#" class="open-close">${pageName}</a></h2>
                    </div>
                    <form action="addSupplierAttribute" class="settings-form" id="create-supplier-attribute-form" method="post" enctype="multipart/form-data">
                        <div class="block">
                            <div class="content">
                                <div class="add-supplier-attribute-summary-box alt-area" style="width: 488px;">
                                    <table class="summary-table">
                                        <tr>
                                            <th style="width: 115px;"><span style="color:red">*</span><spring:message code="com.adminui.supplier_attribute_create.AttributeName" text="default text" /></th>
                                            <td class="a-right" id="activeSupplierAttributesCount">
                                                <!--input type="text" name="attributeName" id="attribute-name" maxlength="64" size="40" class="required" value=""-->

                                                <div class="area-col" id="attribute-name-div">
                                                    <input id="attribute-name" name="attributeName" class="text" type="text" maxlength="64" size="40"  onkeyup="if($('#attributeName').val() != '')$('#attribute-name-div span').remove()"/>
                                                    <input type="hidden" id="companyNameHidden" name="companyNameHidden" value="${supplier.companyName}"/>
                                                    <input type="hidden" id="attributeNameHidden" name="attributeNameHidden" value="${attributeName}" />
                                                </div>

                                            </td>
                                        </tr>
                                        <br />
                                        <tr>
                                            <th style="width: 115px;"><spring:message code="com.adminui.supplier_attribute_create.AttributeDescription" text="default text" /></th>
                                            <td class="a-right" id="inactiveSupplierAttributesCount">
                                                <input type="text" name="attributeDescription" id="attribute-description" size="40" />
                                                <input type="hidden" id="attributeDescriptionHidden" name="attributeDescriptionHidden" value="${attributeDescription}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th style="width: 115px;"><spring:message code="com.adminui.supplier_attribute_create.LogoFile" text="default text" /></th>
                                            <td class="a-right">
		                                            <div id="browseDiv1" style="display:block;" class="popup-holder">
                                                        <c:if test="${empty attributeImagePath}">
                                                            <a id="closeFileUploadPopup" href="#" class="btn-add-open open" style="z-index: 0; text-align: left;">add</a>
                                                        </c:if>
                                                        <c:if test="${not empty attributeImagePath}">
                                                            <a id="closeFileUploadPopup" href="#" class="btn-add-open open" style="z-index: 0; text-align: left;">Edit</a>
                                                        </c:if>

											            <div class="popup">
											                <div class="popup-frame">
											                    <div class="popup-inner">
											                        <div class="top">
											                            <a href="#" class="alt-close"></a>
											                            <h3 style="text-align: left;"><spring:message code="com.adminui.supplier_attribute_create.UploadFile" text="default text" /></h3>
											                        </div>
											                        <div class="row">
											                            <input type="file" name="iconFile" id="attribute-logo-file" onchange="setIconFile();"/>
											                        </div>
											                        <div class="row" style="display:none;">
											                            <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
											                            <a href="#" class="btn-upload"><spring:message code="com.adminui.supplier_attribute_create.Upload" text="default text" /></a>
											                            <div class="txt">
											                                <span class="size"> 0.59 KB</span>
											                                <p>icon_photo_upload_16px.gif</p>
											                            </div>
											                        </div>
											                        <div class="row" style="display:none;">
											                            <img src="res/images/ico01.png" width="39" height="39" alt="image description" />
											                            <div class="txt">
											                                <div class="line-box"><span class="line" style="width:70%"></span></div>
											                                <a href="#" class="btn-cancel"><spring:message code="com.adminui.supplier_attribute_create.Cancel" text="default text" /></a>
											                            </div>
											                        </div>
											                    </div>
											                </div>
											            </div>

                                                        <c:if test="${empty attributeImagePath}">
											                <em id="attribute-logo-fileEM" class="text"><spring:message code="com.adminui.supplier_attribute_create.NoFileChosen" text="default text" /></em>
                                                        </c:if>
                                                        <c:if test="${not empty attributeImagePath}">
                                                            <img src='<c:url value="/image/"/>${attributeImagePath}?type=supplier' alt="image description">
                                                            <em id="attribute-logo-fileEM" class="text"></em>
                                                        </c:if>
											        </div>
		                                                
                                            </td>
                                           </tr>
                                        </tbody>
                                    </table>

                                </div>

                             </div>
                        </div>

                        <div id="btnHolder" style="display: block;" class="btns-holder">
                            <a href="/adminui/supplierAttributes" class="btn-cancel checkboxReset"><spring:message code="com.adminui.supplier_attribute_create.Cancel" text="default text" /></a>
                            <input type="submit" value="Save" onclick="return validateSupplierAttributeForm();">
                            <p><span class="required">* </span><spring:message code="com.adminui.supplier_attribute_create.RequiredField" text="default text" /></p>
                        </div>
                        <input type="hidden" id="attributeId" name="attributeId" value="${attributeId}" />
                    </form>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href="<c:url value="/supplierAttributes" />" class="ico-back"><span><spring:message code="com.adminui.supplier_attribute_create.BACK" text="default text" /></span></a></li>
                <!--li><a href='<c:url value="/supplierDetail" />' class="ico-mapping"><span>CREATE SUPPLIER</span></a></li-->
                <!--h3>JUMP TO</h3-->
            </ul>
            <ul class="sub-nav">
                <!--li><a href='#' class="ico-company"><span>SUPPLIER COMPANY LIST</span></a></li-->
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    var preAttributeName="";
    var preAttributeDes="";
    var preAttributeLogo="";
    var attributeId="";
    $( document ).ready(function() {
        <%--var attributeId = ${attributeId};--%>
        try{
            preAttributeName=$('#attributeNameHidden').val();
            preAttributeDes=$('#attributeDescriptionHidden').val();
            attributeId=$('#attributeId').val();
            preAttributeFile=$('#attribute-logo-file').val();

            $('#attribute-name').val($('#attributeNameHidden').val());
            $('#attribute-description').val($('#attributeDescriptionHidden').val());
            $('#attribute-logo-fileEM').val($('#attribute-logo-fileEMHidden').val());
        } catch (e){
            print("Unable to render Edit Attribute page");
            print(e.name + ': ' + e.msg);
        }

    });
    function validateSupplierAttributeForm(){
        if(attributeId){
            //Edit mode
            if(preAttributeName && preAttributeName === $('#attribute-name').val()){
                if(preAttributeDes !== $('#attribute-description').val() || preAttributeFile !== $('#attribute-logo-file').val()){
                    //Attribute name is same but description or logo is edited.
                    return true;
                } else{
                    //none of field is edited redirect supllier attribute management page.
                    window.location.href = 'supplierAttributes';
                    return false;
                }
            } else{//attribute name is edited
                if(validateCreateAttributesForm()){
                    return true;
                } else {
                    return false;
                }
            }
        } else{
            //Add mode
            validateCreateAttributesForm();
        }

    }
/*
function setIconFile(){
    if($('#attribute-logo-file').value != ''){

        var ext = $('#attribute-logo-file').val().split('.').pop().toLowerCase();
        if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
            alert('Invalid file: '+$('#attribute-logo-file').val() +'. Allowed files types are [ gif, png, jpg, jpeg ]');
            resetLogoImage();
            return;
        }

        var fileName = $('#attribute-logo-file').value;
        var indexPos=0;
        //ie8 security restriction hack
        if( $('#attribute-logo-file').value.startsWith("C:\\fakepath\\") || $('#attribute-logo-file').value.startsWith("C://fakepath//")  )
            indexPos = "C:\\fakepath\\".length-1;

        $('#attribute-logo-fileEM').html(fileName.substr(indexPos,fileName.length)+'<a style="margin-top:4px;margin-left: 20px;cursor: pointer;" onclick="resetLogoImage();" class="alt-close"></a>');
    } else {
        $('#attribute-logo-fileEM').html('');
    }
}
*/
function setIconFile() {
    try{
        var fileName = document.getElementById('attribute-logo-file').value;
        if(fileName != ''){
            var ext = $('#attribute-logo-file').val().split('.').pop().toLowerCase();
            if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
                alert('Invalid file: '+$('#attribute-logo-file').val() +'. Allowed files types are [ gif, png, jpg, jpeg ]');
                resetLogoImage();
                return;
            }

            var indexPos=0;
            //ie8 security restriction hack
            if( fileName.indexOf("C:\\fakepath\\") == 0 || fileName.indexOf("C://fakepath//") == 0  ){
                indexPos = "C:\\fakepath\\".length-1;
            }

            $('#attribute-logo-fileEM').html(fileName.substr(indexPos, fileName.length) + '<a style="margin-top:4px;margin-left: 20px;cursor: pointer;" onclick="resetLogoImage();" class="alt-close"></a>');
        } else {
            alert('Error: Can not get filename!');
            $('#attribute-logo-fileEM').html('');
        }
        $('#closeFileUploadPopup').click();
    }catch(exp){
        alert(exp);
    }
}

function resetLogoImage(){
    $('#attribute-logo-file').val('');
    $('#attribute-logo-fileEM').html('No File Chosen.');
    $('#attribute-logo-file').val('');
    jcf.customForms.replaceAll();
}
</script>