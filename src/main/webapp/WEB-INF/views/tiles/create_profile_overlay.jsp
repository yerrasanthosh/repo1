<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="delete">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.create_profile_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_profile_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.create_profile_overlay.confirmDelete" text="default text" /></p>
                    <p><strong><spring:message code="com.adminui.create_profile_overlay.confirmDelete2" text="default text" /></strong></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" class="btn btn-red"><span><spring:message code="com.adminui.create_profile_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="add-catalogs">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close" onclick="$('input#searchWithinCatalogTextId').val('');searchWithinCatalogs();$('input#searchWithinCatalogTextId').val('Search within')"><spring:message code="com.adminui.create_profile_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.create_profile_overlay.AddCatalogs" text="default text" /></h2>
                </div>
                <div class="search-box">
                    <form action="#" style="width: 415px;" class="add-search-form">
                        <fieldset>
                            <input id="searchWithinCatalogTextId"  type="text" value="Search within" />
                            <input type="submit" style="float: left;" id="searchWithinCatalogTextIdBtn" value="Submit" onclick="searchWithinCatalogs(); return false;"/>
                            <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchWithinCatalogTextId').val('Search Within'); $('#searchWithinCatalogTextIdBtn').click();" />
                        </fieldset>
                    </form>
                </div>
                <div id="profile_catalogs-holder">
                    <jsp:include page="profile_catalog_fragment.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>