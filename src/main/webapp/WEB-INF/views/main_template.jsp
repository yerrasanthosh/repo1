<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.vroozi.customerui.acl.model.Role" %>
<?xml version='1.0' encoding='UTF-8'?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<link rel="icon" type="image/x-icon" href="res/images/favicon.ico">
<link rel="shortcut icon" href="res/images/favicon.ico" type="image/x-icon" />
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"
         import="com.vroozi.customerui.acl.model.Role"%>

<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    String unitId = "";
    String userId = "";

    List<Role> tempRoles = new ArrayList<Role>();
    List<String> roles = new ArrayList<String>();
    HttpSession existingSession = request.getSession(false);
    if (null!=existingSession) {
        if (existingSession.getAttribute("user")!=null) {
            User loggedInUser = (User)existingSession.getAttribute("user");
            unitId = loggedInUser.getUnitId();
            userId = loggedInUser.getUserId();
            tempRoles = loggedInUser.getRoles();
            for(Role role:tempRoles){
                roles.add("'"+role.toString()+"'");
            }
        }
    }
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <link rel="icon" type="image/x-icon" href="res/images/favicon.ico">
    <link rel="shortcut icon" href="res/images/favicon.ico" type="image/x-icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>SmartOCI</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
        <link media="all" rel="stylesheet" type="text/css" href="res/css/jquery-ui-1.8.22.custom.css" />
        <link media="all" rel="stylesheet" type="text/css" href="res/css/colorbox.css" />
        <link media="all" rel="stylesheet" type="text/css" href="res/css/all.css" />
        <link media="all" rel="stylesheet" type="text/css" href="res/css/all-adminui.css" />
        <link media="all" rel="stylesheet" type="text/css" href="res/css/dhtmlxcombo.css" />
        <script type="text/javascript">
            var unitId = '<%=unitId%>';
            var catalogId = '<%=request.getParameter("catalogId")%>';
            var catalogItemId = '<%=request.getParameter("catalogItemId")%>';
            var userId = '<%=userId%>';
            var roles = <%=roles%>;
            var releaseNumServiceUrl = '<c:url value="/releaseNum"/>';
            var profileGroupTokenExistsServiceUrl = '<c:url value="/profileGroupTokenExists"/>';
            var profileGroupNewTokenServiceUrl = '<c:url value="/createToken"/>';
            var notAssociatedProfilePageServiceUrl =  '<c:url value="/gotonotassoicatedprofilepage" />';

            var searchWithinNAProfileServiceUrl =  '<c:url value="/searchWithinNAProfile" />';
            var sortNAGroupProfileServiceUrl = '<c:url value="/sortNAGroupProfiles" />';
            var sortGroupProfileServiceUrl = '<c:url value="/sortGroupProfile" />';
            var profileGroupExistsServiceUrl = '<c:url value="/profileGroupExists"/>';
            var gDeleteCatalogServiceUrl = '<c:url value="/deleteCatalog" />';
            var gRejectCatalogServiceUrl = '<c:url value="/rejectCatalog" />';
            var gCatalogJsonServiceUrl = '<c:url value="/getCatalogJson" />';
            var exportCatalogServiceUrl = '<c:url value="/exportcatalog" />';
            var changereportServiceUrl = '<c:url value="/changereport" />';
            var getCatalogStatusUrl = '<c:url value="/getCatalogStatus" />';
            var addProfileServiceUrl = '<c:url value="/addProfile" />';
            var sortProfilesServiceUrl = '<c:url value="/sortProfiles" />';
            var sortCatalogsServiceUrl = '<c:url value="/sortCatalogs" />';
            var sortProfileCatalogsServiceUrl = '<c:url value="/sortProfileCatalogs" />';
            var activeProfilesServiceUrl = '<c:url value="/activeProfiles" />';
            var deleteProfilesServiceUrl = '<c:url value="/deleteProfiles" />';
            var goToPageProfilesServiceUrl = '<c:url value="/goToPageProfiles" />';
            var filterProfilesServiceUrl = '<c:url value="/filterProfiles" />';
            var welcomeTextURI = '<c:url value="/welcomeSettings" />';
            var filterProfileGroupsServiceUrl = '<c:url value="/filterProfileGroups" />';
            var associateProfileToGroupServiceUrl =  '<c:url value="/associateProfileToGroup" />';
            var activeProfileGroupsServiceUrl = '<c:url value="/activeProfileGroups" />';
            var deleteProfileGroupsServiceUrl = '<c:url value="/deleteProfileGroups" />';
            var goToPageProfileGroupsServiceUrl = '<c:url value="/goToPageProfileGroups" />';
            var goToGroupProfilePageServiceUrl =  '<c:url value="/goToGroupProfilePage" />';
            var disAssociateGroupProfileServiceUrl =  '<c:url value="/disAssociateGroupProfile" />';

            var associateCatalogToProfileServiceUrl =  '<c:url value="/associateCatalogToProfile" />';
            var searchWithinCatalogServiceUrl =  '<c:url value="/searchWithinCatalog" />';
            var disAssociateCatalogFromProfileServiceUrl =  '<c:url value="/disAssociateCatalogFromProfile" />';
            var createNewProfileServiceUrl =  '<c:url value="/createNewProfile" />';
            var downloadAllCatalogsUrl =  '<c:url value="/download-all-catalogs" />';
            var goToProfileCatalogPageServiceUrl =  '<c:url value="/goToProfileCatalogPage" />';
            var goToCatalogPageServiceUrl =  '<c:url value="/goToCatalogPage" />';
            var goToCatalogProfilePageServiceUrl =  '<c:url value="/goToCatalogProfilePage" />';
            var removeCatalogProfilesServiceUrl = '<c:url value="/removeCatalogProfiles" />';
            var goToCatalogNotAssProfilePageServiceUrl = '<c:url value="/goToCatalogNotAssProfilePage" />';
            var profilesPageUrl = '<c:url value="/profiles" />';
            var addCatalogProfilesServiceUrl = '<c:url value="/addCatalogProfiles" />';
            var addCatalogProfileServiceUrl = '<c:url value="/addCatalogProfile" />';
            var searchWithinProfileServiceUrl  = '<c:url value="/searchWithinProfile" />';
            var filterSuppliersServiceUrl =  '<c:url value="/filterSuppliers" />';
            var deleteSuppliersServiceUrl =  '<c:url value="/deleteSuppliers" />';
            var activeSuppliersServiceUrl = '<c:url value="/activeSuppliers" />';
            var searchWithinSupplierServiceUrl = '<c:url value="/searchWithinSupplier" />';
            var goToPageSuppliersServiceUrl = '<c:url value="/goToPageSuppliers" />';
            var goToPageSupplierAttributesServiceUrl = '<c:url value="/goToPageSupplierAttributes" />';
            var filterSupplierAttributesServiceUrl = '<c:url value="/filterSupplierAttributes" />';
            var activeSupplierAttributesServiceUrl = '<c:url value="/activeSupplierAttributes" />';
            var deleteSupplierAttributesServiceUrl = '<c:url value="/deleteSupplierAttributes" />';
            var createSupplierAttributesServiceUrl = '<c:url value="/createSupplierAttribute" />';
            var sortSuppliersServiceUrl = '<c:url value="/sortSuppliers" />';
            var activateSupplierApproversServiceUrl = '<c:url value="/activateSupplierApprover" />';
            var createEditSupplierServiceUrl = '<c:url value="/createEditSupplier" />';
            var saveMappingsSupplierServiceUrl = '<c:url value="/savesuppliermappings" />';
            var supplierMappingsServiceUrl = '<c:url value="/suppliermappings" />';
            var deleteMappingsSupplierServiceUrl = '<c:url value="/deletesuppliermappings" />';
            var createPropertyServiceUrl = '<c:url value="/createProperty" />';
            var getCatalogCustomFieldServiceUrl = '<c:url value="/getCatalogCustomField" />';
            var getCustomFieldServiceUrl = '<c:url value="/getCustomFields" />';
            var getItemCustomFieldServiceUrl = '<c:url value="/getItemCustomFields" />';
            var applicationContext = '${pageContext.request.contextPath}';
            var activeSupplierServiceUrl = '<c:url value="/activeSuppliers" />';
            var removeSupplierLogoServiceUrl = '<c:url value="/deleteSupplierLogo" />';
            var gCatalogDivFragmentServiceUrl = '<c:url value="/catalogDivFragment" />';
            var updateItemStatusServiceUrl = '<c:url value="/updateItemsStatus" />';
            var addCatalogAproversServiceUrl = '<c:url value="/addApprovers" />';
            var createCatalogApproverServiceUrl = '<c:url value="/addApprover" />';
            var removeCatalogApproversServiceUrl = '<c:url value="/removeCatalogApprovers" />';
            var goToPageCatalogApproverServiceUrl = '<c:url value="/goToPageCatalogApprover" />';
            var goToPageNonCatalogApproverServiceUrl = '<c:url value="/goToPageNonCatalogApprover" />';
            var createUserServiceUrl = '<c:url value="/createUser" />';
            var deleteUsersServiceUrl = '<c:url value="/deleteUsers" />';
            var activateUsersServiceUrl = '<c:url value="/activateUsers" />';
            var filterUsersServiceUrl = '<c:url value="/filterUsers" />';
            var createUpdateUserPageUrl = '<c:url value="/createUpdateUser" />';
            var assignUserProfilesServiceUrl = '<c:url value="/assignUserProfiles" />';
            var assignSupplierContentViewServiceUrl = '<c:url value="/assignSupplierContentView" />';
            var deleteUserProfilesServiceUrl = '<c:url value="/deleteUserProfiles" />';
            var deleteSupplierContentViewServiceUrl = '<c:url value="/deleteSupplierContentView" />';
            var navigateUserPageServiceUrl = '<c:url value="/navigateUserPage" />';
            var searchUsersServiceUrl = '<c:url value="/searchUsers" />';
            var createUserProfileServiceUrl = '<c:url value="/addUserProfile" />';
            var addUserProfilesServiceUrl = '<c:url value="/addUserProfiles" />';
            var getUnassignedContentViewsUrl = '<c:url value="/getUnassignedContentViews" />';
            var navigateProfilesServiceUrl = '<c:url value="/navigateProfilePage" />';
            var navigateSupplierContentViewUrl = '<c:url value="/navigateSupplierContentView" />';
            var supplierContentViewServiceUrl = '<c:url value="/addSupplierContentViews"/>';
            var createSupplierServiceUrl = '<c:url value="/addSupplier" />';
            var editSupplierServiceUrl = '<c:url value="/editSupplier" />';
            var createSystemVendorIdUrl = '<c:url value="/addVendorId" />';
            var createCompanyUsersUrl = '<c:url value="/addCompanyUser" />';
            var editCompanyUsersUrl = '<c:url value="/editCompanyUser" />';
            var editCompanyUserUrl = '<c:url value="/edit-companyUser" />';
            var createCompanyAttributeServiceUrl = '<c:url value="/createCompanyAttribute"/>'
            var deleteSupplierServiceUrl = '<c:url value="/deleteSupplier"/>'
            var boostSupplierAttributeLevelServiceUrl = '<c:url value="/boostSupplierAttribute"/>'
            var changeAttributeAssociationServiceUrl  = '<c:url value="/changeAttributeAssociation"/>'
            var updateSupplierMinOrderServiceUrl = '<c:url value="/updateSupplierMinOrder"/>'
            var updateSupplierCatalogServiceUrl = '<c:url value="/updateSupplierCatalog"/>';
            var updateSingleSourceSupplierUrl = '<c:url value="/update-single-source-supplier"/>';
            var updateSupplierIncludeCardServiceUrl = '<c:url value="/updateSupplierIncludeCard"/>';
           	var updateSupplierCountryCodeUrl = '<c:url value="/updateSupplierCurrencyCode"/>'
            var updateSupplierDisableBrowseUrl = '<c:url value="/updateSupplierDisableBrowse"/>'
            var getVendorIdDetailsUrl = '<c:url value="/getVendorIdDetails"/>'
            var editSystemVendorUrl = '<c:url value="/editSystemVendor"/>'
            var getAttributeUrl = '<c:url value="/getAttributeDetails"/>'
            var editCompanyAttributeUrl = '<c:url value="/editAttributeDetails"/>'
            var removeCatalogItemss = '<c:url value="/removeCatalogItems"/>';
            var getCatalogByNameJsonServiceUrl = '<c:url value="/getCatalogByNameJson"/>';
            var getQuoteByQuoteIdJsonServiceUrl = '<c:url value="/getQuoteByQuoteIdJson"/>';
            var getQuoteByQuoteIdJsonEditServiceUrl = '<c:url value="/getQuoteByQuoteIdJsonEdit"/>';
            var profileExistsServiceUrl = '<c:url value="/profileExists"/>';
            var verifyUserPasswordServiceUrl = '<c:url value="/verifyUserPassword"/>';
            var supplierAttrExistsServiceUrl = '<c:url value="/supplierAttributeExists"/>';
            var catalogfilterUrl = '<c:url value="/catalog"/>';
            var userCanNotBeBlank = '<spring:message code="com.adminui.msgs.userCanNotBeBlank" text="default text" />';
            var selectOneItem = '<spring:message code="com.adminui.msgs.selectOneItem" text="default text" />';
            var fileIsBeingPrepared = '<spring:message code="com.adminui.msgs.fileIsBeingPrepared" text="default text" />';
            var changeReportFile =  '<spring:message code="com.adminui.msgs.changeReportFile" text="default text" />';
            var supplierRecord = '<spring:message code="com.adminui.msgs.supplierRecord" text="default text" />';
            var systemNameCanNotBeChange = '<spring:message code="com.adminui.msgs.systemNameCanNotBeChange" text="default text" />';
            var recordAlreadyExist = '<spring:message code="com.adminui.msgs.recordAlreadyExist" text="default text" />';
            var saveTheFields = '<spring:message code="com.adminui.msgs.saveTheFields" text="default text" />';
            var recordAlreadyExistUserName = '<spring:message code="com.adminui.msgs.recordAlreadyExistUserName" text="default text" />';
            var uniqueSystemIdMsg ='<spring:message code="com.adminui.msgs.uniqueSystemIdMsg" text="default text" />';
            var doesNotExist ='<spring:message code="com.adminui.msgs.doesNotExist" text="default text" />';
            var enterUserName = '<spring:message code="com.adminui.msgs.enterUserName" text="default text" />';
            var selectAGropupName = '<spring:message code="com.adminui.msgs.selectAGropupName" text="default text" />';
            var recordAlreadyExistUserName1 ='<spring:message code="com.adminui.msgs.recordAlreadyExistUserName1" text="default text" />';
            var saveTheFieldsEditMode ='<spring:message code="com.adminui.msgs.saveTheFieldsEditMode" text="default text" />';
            var saveTheFieldsAddMode= '<spring:message code="com.adminui.msgs.saveTheFieldsAddMode" text="default text" />';
            var savedMsg1 = '<spring:message code="com.adminui.msgs.savedMsg1" text="default text" />';
            var sorrySavedField = '<spring:message code="com.adminui.msgs.sorrySavedField" text="default text" />';
            var bundleQtyReq = '<spring:message code="com.adminui.msgs.bundleQtyReq" text="default text" />';
            var bundleNumberReq = '<spring:message code="com.adminui.msgs.bundleNumberReq" text="default text" />';
            var bundleNumberGiven = '<spring:message code="com.adminui.msgs.bundleNumberGiven" text="default text" />';
            var saving = '<spring:message code="com.adminui.msgs.saving" text="default text" />';
            var adding = '<spring:message code="com.adminui.msgs.adding" text="default text" />';
            var uniqueSystemIdCanNotBeEmpty = '<spring:message code="com.adminui.msgs.uniqueSystemIdCanNotBeEmpty" text="default text" />';
            var supplierIdEmpty = '<spring:message code="com.adminui.msgs.supplierIdEmpty" text="default text" />';
            var uniqueSystemMapping = '<spring:message code="com.adminui.msgs.uniqueSystemMapping" text="default text" />';
            var republishCatalog = '<spring:message code="com.adminui.msgs.republishCatalog" text="default text" />';
            var uniqueSystemDoesNotExist = '<spring:message code="com.adminui.msgs.uniqueSystemDoesNotExist" text="default text" />';
            var updateShopperView = '<spring:message code="com.adminui.msgs.updateShopperView" text="default text" />';

            var dhx_globalImgPath = 'res/images/combo/';

            var gCurrentCatalog = null;
        </script>
        <script type="text/javascript" src="res/js/jcf.js"></script>
        <script type="text/javascript" src="res/js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript" src="res/js/jquery.form.js"></script>
        <script type="text/javascript" src="res/js/jquery.main.adminui.js"></script>
        <script type="text/javascript" src="res/js/jquery.main.js"></script>
        <script type="text/javascript" src="res/js/jcf.scrollable.js"></script>
        <script type="text/javascript" src="res/js/jcf.select.js"></script>
        <script type="text/javascript" src="res/js/jcf.file.js"></script>
        <script type="text/javascript" src="res/js/jcf.scrollable.js"></script>
        <script type="text/javascript" src="res/js/jquery.validate.js"></script>
        <script type="text/javascript" src="res/js/mustache.js"></script>
        <script type="text/javascript" src="res/js/adminui.js"></script>
        <script type="text/javascript" src="res/ckeditor/ckeditor.js"></script>
        <script type="text/javascript" src="res/js/dhtmlxcommon.js"></script>
        <script type="text/javascript" src="res/js/dhtmlxcombo.js"></script>
        <script type="text/javascript" src="res/js/ext/dhtmlxcombo_extra.js"></script>
        <script type="text/javascript" src="res/js/underscore.js"></script>
        <script type="text/javascript" src="res/js/backbone.js"></script>
        <script type="text/javascript" src="res/js/jquery.noty.js"></script>
        <script type="text/javascript" src="res/js/jquery.noty-settings.js"></script>
        <script type="text/javascript" src="res/js/catalog-details.js"></script>
		<script type="text/javascript" src="res/js/json2-min.js"></script>
		<script type="text/javascript" src="res/js/typeahead.bundle.min.js"></script>
        <script type="text/javascript">
	        function headerSearch(){
	            if (!$("#text-field").val() || $("#text-field").val() == "Enter search here...") {
	                alert("Please enter keyword(s) to search on");
	                $("#text-field").focus();
	            } else {
	                popupSearch();
	            }
	            return false;
	        }

            $(document).ready(function(){

            	$(".search-form").submit(headerSearch);
            	$("#searchbtn").click(headerSearch);

                jQuery.validator.addMethod("verifyUserPassword", function(value, element) {
	          		 var username = $('input[name=username]').val();

	           		return (!value || verifyUserPassword(username, value));
	           	},"");

                jQuery.validator.addMethod("profileNameCheck", function(value, element) {
	           		var profileName = $('input[name=profileName]').val();
	           		return !profileExists(profileName);
	           	},"Profile already exists");

                <%--$("#theCatalogId").validate({--%>
                    <%--rules: {--%>
                        <%--catalogName: {--%>
                            <%--required: true--%>
                        <%--}--%>
                        <%--,--%>
                        <%--catalogFile: {--%>
                            <%--required: true,--%>
                            <%--accept: "csv|tsv|xlst|xlsx"--%>
                        <%--}--%>
                    <%--},--%>
                    <%--messages: {--%>
                        <%--catalogName: "<spring:message code="error.catalog.create.catalogname.required" />",--%>
                        <%--catalogFile: "<spring:message code="error.catalog.create.catalogfile.accept" />"--%>
                    <%--},--%>
                    <%--submitHandler: function(form) { form.submit(); }--%>
                <%--})--%>

//                $("#edit-catalog-form").validate({
//                    rules: {
//                        catalogFile: {
//                            required: true,
//                            accept: "csv|tsv|xlst|xlsx"
//                        }
//                    },
//                    messages: {
//                        catalogName: "This is required"
//                    }
//                    //submitHandler: function(form) { form.submit(); }
//                })

                $("#createProfileId").validate({
                    onkeyup: false,
                    onclick: false,
                    rules: {
                        profileName: {
                            required: true,
                            profileNameCheck: true
                        }
                    },
                    messages: {
                        profileName: {
                        	required: "<spring:message code="error.profile.create.profilename.required" />",
                        	profileNameCheck: "<spring:message code="error.profile.create.profilename.exists" />"
                        }
                    },
                    submitHandler: function(form) {
                    	addCatalogProfile();
					}
                })

                $("#theExtCatalogId").validate({
                    rules: {
                        catalogName: {
                            required: true
                        }
                    },
                    messages: {
                        catalogName: "<spring:message code="error.catalog.create.catalogname.required" />"
                    },
                    submitHandler: function(form) { form.submit(); }
                })

                $("#theCustomFormId").validate({
                    rules: {
                        fieldName: {
                            required: true
                        },
                        displayName: {
                            required: true
                        }
                    },
                    messages: {
                        fieldName: "<spring:message code="error.customgfield.create.fieldname.required" />",
                        displayName: "<spring:message code="error.customgfield.create.fielddesc.required" />"
                    },
                    submitHandler: function(form) {
                    	var name = $('input[name=fieldName]').val();
                    	var type = $('select[name=fieldType]').val();
                    	var oldType = $('input[name=oldFldType]').val();
                    	var edit = $('input[name=editForm]').val();
                    	var postFilter = false;
                    	if(edit){
                    		if(document.getElementById('edit-area-1-postFilterable').checked || document.getElementById('edit-area-3-postFilterable').checked || document.getElementById('edit-area-4-postFilterable').checked){
    							postFilter = true;
    						}
                        } else {
                        	if(document.getElementById('parameter2').checked || document.getElementById('parameter3-1').checked){
    							postFilter = true;
    						}
                        }
                    	if(edit){
                    		if(oldType != type){
                    			var returnStr = fieldExists(name, type, postFilter);
    							if(returnStr == 'false') {
    	                        	$('#field_name_error').removeClass('error');
    	                        	$('#field_name_error').addClass('error-msg');
    	                        	$('#field_name_exceeded').removeClass('error');
    	                        	$('#field_name_exceeded').addClass('error-msg');
    	                        	form.submit();
    	                        	$('#cboxClose').trigger('click');
    	                        }  else if(returnStr == 'exceeded'){
    	                        	$('#field_name_exceeded').removeClass('error-msg');
    	                        	$('#field_name_exceeded').addClass('error');
    	                        } else {
    	                        	$('#field_name_error').removeClass('error-msg');
    	                        	$('#field_name_error').addClass('error');
    	            			 }
    						} else {
    							form.submit();
    						}
                        } else {
                            var returnStr = fieldExists(name, type, postFilter);
                        	if(returnStr == 'false') {
	                        	$('#field_name_error').removeClass('error');
	                        	$('#field_name_error').addClass('error-msg');
	                        	$('#field_name_exceeded').removeClass('error');
	                        	$('#field_name_exceeded').addClass('error-msg');
	                        	form.submit();
	                        	$('#cboxClose').trigger('click');
	                        } else if(returnStr == 'exceeded'){
	                        	$('#field_name_exceeded').removeClass('error-msg');
	                        	$('#field_name_exceeded').addClass('error');
	                        } else {
	                        	$('#field_name_error').removeClass('error-msg');
	                        	$('#field_name_error').addClass('error');
	            			 }
                        }




                         }
                })

				<%--$("#createApprover").validate({--%>
                    <%--rules: {--%>
                    	<%--firstName: {--%>
                            <%--required: true--%>
                        <%--},--%>
                        <%--lastName: {--%>
                            <%--required: true--%>
                        <%--},--%>
                        <%--email: {--%>
                            <%--required: true,--%>
                            <%--email: true--%>
                        <%--}--%>
                    <%--},--%>
                    <%--messages: {--%>
                    	<%--firstName: "<spring:message code="error.catalog.create.catalogname.required" />",--%>
                    	<%--lastName: "<spring:message code="error.catalog.create.catalogname.required" />",--%>
                    	<%--email: { --%>
                        	<%--required: "<spring:message code="error.catalog.create.catalogname.required" />",--%>
                    	    <%--email: "<spring:message code="error.catalog.create.catalogname.email" />" --%>
                       	<%--}--%>
                    <%--},--%>
                    <%--submitHandler: function(form) { form.submit(); }--%>
                <%--})--%>

                $("#createSupplierContentViewId").validate({

                    submitHandler: function(form) {
                        createSupplierContentViews();
                                $('#cboxClose').trigger('click');
                                        }
                })


                $("form#createProfile").validate({
                    rules: {
                        profileName: {
                            required: true
                        },
                        profileDesc: {
                            required: true
                        }
                    },
                    messages: {
                        profileName: "<spring:message code="error.catalog.create.catalogname.required" />",
                        profileDesc: "<spring:message code="error.catalog.create.catalogname.required" />"
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                })
                $("#addUser").validate({
                    rules: {
                    	firstName: {
                            required: true
                        },
                        lastName: {
                            required: true
                        },
                        username: {
                            required: true,
                            email: true
                        }
                    },
                    messages: {
                    	firstName: "<spring:message code="error.catalog.create.catalogname.required" />",
                    	lastName: "<spring:message code="error.catalog.create.catalogname.required" />",
                    	username: {
                        	required: "<spring:message code="error.catalog.create.catalogname.required" />",
                    	    email: "<spring:message code="error.catalog.create.catalogname.email" />"
                       	}
                    },
                    submitHandler: function(form) { form.submit(); }
                })
                $("#createUserForm").validate({
                    rules: {
                    	firstName: {
                            required: true
                        },
                        lastName: {
                            required: true
                        },
                        username: {
                            required: true,
                            email: true
                        },
                        roles: {
                        	required: true
                        }
                    },
                    messages: {
                    	firstName: "You must enter a Valid First Name",
                    	lastName: "You must enter a Valid Last Name",
                    	username: {
                        	required: "You must enter a Valid E-mail",
                    	    email: "You must enter a Valid E-mail"
                       	},
                   		roles: "You must select at least one role"
                    },
                    submitHandler: function(form) { form.submit(); }
                })
                $("#frmMyAccountDetails").validate({
					onkeyup: false,
                    onclick: false,
                    rules: {
                    	contactName: {
                            required: true
                        },
                        contactTitle: {
                            required: true
                        },
                        address: {
                            required: true
                        },
                        city: {
                            required: true
                        },
                        zipCode: {
                            required: true
                        },
                        state: {
                            required: true
                        },
                        countryId: {
                            required: true
                        },
                        telephone: {
                            required: true
                        },
                        username: {
                            required: true,
                            email: true
                        },
                        pwdOld: {
                        	verifyUserPassword: true
                        },
                        pwdNew: {
                        	required: function(element) {
                                return ($("#pwdOld").val() != '');
                            }
                        },
                        pwdNew2: {
                        	equalTo: "#pwdNew"
                        }

                    },
                    messages: {
                    	contactName: "Enter a Contact Name",
                    	contactTitle: "Enter Contact Title",
                    	address: "Enter your Address",
                    	city: "Enter your City",
                    	zipCode: "Enter your Zip Code",
                    	state: "Select a State",
                    	countryId: "Select a Country",
                    	telephone: "Enter your Telephone Number",
                    	username: {
                        	required: "Enter a Valid E-mail",
                    	    email: "Enter a Valid E-mail"
                       	},
                       	pwdOld: "Incorrect old password",
                       	pwdNew: "Enter new password",
                       	pwdNew2: "Passwords do not match"
                    },
                    submitHandler: function(form) {
                   		var pwdold = $('input[name=pwdOld]').val();
                   		var pwdnew = $('input[name=pwdNew]').val();

                        if(pwdold != '' && pwdnew == '') {
                        	$('#newpass_error').removeClass('error-msg');
                        	$('#newpass_error').addClass('error');
                        } else {
                        	$('#newpass_error').removeClass('error');
                        	$('#newpass_error').addClass('error-msg');
                            submitAccountInfo();
                        }
                    }
                })
            });

            jcf.lib.domReady(function(){
                jcf.customForms.replaceAll();
            });
        </script>
    </head>
    <body>
        <div id="wrapper">

            <t:insertAttribute name="header"/>

            <t:insertAttribute name="body"/>

            <t:insertAttribute name="footer"/>

        </div>

        <t:insertAttribute name="common_overlay"/>

        <t:insertAttribute name="overlay"/>
    </body>
</html>
