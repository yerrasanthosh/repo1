<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<br />
<c:if test='${empty supplierLogo}'>
    <div class="block">
        <div class="content">
            <form action="#" class="settings-form">
                <div class="alt-area">
                    <div class="popup-holder">
                        <!--a class="open-popup cboxElement supplier-upload-logo" href="#lightbox-inline-supplier-logo">add</a-->
                        <a class="supplier-upload-logo" onclick="return popupUploadSupplierLogoOverlay();"><spring:message code="com.adminui.supplier_logo_fragment.add" text="default text" /></a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</c:if>
<c:if test='${not empty supplierLogo}'>
    <img src='<c:url value="/" />${supplierLogo}'>
    &nbsp;<a class="ico-remove" onclick="removeSupplierLogo('${param['supplierId']}')"><span><em><spring:message code="com.adminui.supplier_logo_fragment.REMOVE" text="default text" /></em></span></a>
</c:if>

<form>
    <input id="gSupplierLogo" value="${supplierLogo}" type="hidden"/>
</form>
