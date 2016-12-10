<%@ page import="com.vroozi.customerui.user.services.user.model.User" %>
<?xml version='1.0' encoding='UTF-8'?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<link rel="icon" type="image/x-icon" href="res/images/favicon.ico">
<link rel="shortcut icon" href="res/images/favicon.ico" type="image/x-icon" />
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"
import="com.vroozi.customerui.acl.model.Role"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.vroozi.customerui.acl.model.Role" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
        <%--<meta itemprop="image" content="res/images/favicon.ico">--%>
        <title>SmartOCI</title>
        <%--<link media="all" rel="stylesheet" type="text/css" href="res/css/jquery-ui-1.8.22.custom.css" /> --%>
        <link media="all" rel="stylesheet" type="text/css" href="res/css/colorbox.css" />
        <link media="all" rel="stylesheet" type="text/css" href="res/css/all.css" />

        <link media="all" rel="stylesheet" type="text/css" href="res/css/all-adminui.css" />
        <script type="text/javascript">
            var unitId = '<%=unitId%>';
            var userId = '<%=userId%>';
            var roles = <%=roles%>;
                        
            var gDeleteCatalogServiceUrl = '<c:url value="/deleteCatalog" />';
            var gRejectCatalogServiceUrl = '<c:url value="/rejectCatalog" />';
            var gCatalogJsonServiceUrl = '<c:url value="/getCatalogJson" />';
            var exportCatalogServiceUrl = '<c:url value="/exportcatalog" />';
            var changereportServiceUrl = '<c:url value="/changereport" />';


            var getCatalogStatusUrl = '<c:url value="/getCatalogStatus" />';
            var addProfileServiceUrl = '<c:url value="/addProfile" />';
            var activeProfilesServiceUrl = '<c:url value="/activeProfiles" />';
            var deleteProfilesServiceUrl = '<c:url value="/deleteProfiles" />';
            var removeCatalogProfilesServiceUrl = '<c:url value="/removeCatalogProfiles" />';
            var profilesPageUrl = '<c:url value="/profiles" />';
            var addProfilesServiceUrl = '<c:url value="/addCatalogProfiles" />';
            var createProfileServiceUrl = '<c:url value="/addCatalogProfile" />';
            var createPropertyServiceUrl = '<c:url value="/createProperty" />';
            var getCatalogCustomFieldServiceUrl = '<c:url value="/getCatalogCustomField" />';
            var getCustomFieldServiceUrl = '<c:url value="/getCustomFields" />';
            var getItemCustomFieldServiceUrl = '<c:url value="/getItemCustomFields" />';
            var applicationContext = '${pageContext.request.contextPath}';
            var activeSupplierServiceUrl = '<c:url value="/activeSuppliers" />';
            var gCatalogDivFragmentServiceUrl = '<c:url value="/catalogDivFragment" />';
            var updateItemStatusServiceUrl = '<c:url value="/updateItemsStatus" />';
            var addCatalogAproversServiceUrl = '<c:url value="/addApprovers" />';
            var createCatalogApproverServiceUrl = '<c:url value="/addApprover" />';
            var removeCatalogApproversServiceUrl = '<c:url value="/removeCatalogApprovers" />';
            var createUserServiceUrl = '<c:url value="/createUser" />';
            var deleteUsersServiceUrl = '<c:url value="/deleteUsers" />';
            var activateUsersServiceUrl = '<c:url value="/activateUsers" />';
            var filterUsersServiceUrl = '<c:url value="/filterUsers" />';
            var createUpdateUserPageUrl = '<c:url value="/createUpdateUser" />';
            var assignUserProfilesServiceUrl = '<c:url value="/assignUserProfiles" />';
            var deleteUserProfilesServiceUrl = '<c:url value="/deleteUserProfiles" />';
            var navigateUserPageServiceUrl = '<c:url value="/navigateUserPage" />';
            var searchUsersServiceUrl = '<c:url value="/searchUsers" />';
            var createUserProfileServiceUrl = '<c:url value="/addUserProfile" />';
            var addUserProfilesServiceUrl = '<c:url value="/addUserProfiles" />';
            var navigateProfilesServiceUrl = '<c:url value="/navigateProfilePage" />';
            var createSupplierServiceUrl = '<c:url value="/addSupplier" />';
            var editSupplierServiceUrl = '<c:url value="/editSupplier" />';
            var createSystemVendorIdUrl = '<c:url value="/addVendorId" />';
            var createCompanyUsersUrl = '<c:url value="/addCompanyUser" />';
            var editCompanyUsersUrl = '<c:url value="/editCompanyUser" />';
            var editCompanyUserUrl = '<c:url value="/edit-companyUser" />';
            var createCompanyAttributeServiceUrl = '<c:url value="/createCompanyAttribute"/>';
            var usernameServiceUrl = '<c:url value="/usernameExists"/>';
            var profileExistsServiceUrl = '<c:url value="/profileExists"/>';
           	var resetPasswordServiceUrl = '<c:url value="/resetPassword"/>';
            var verifyUserPasswordServiceUrl = '<c:url value="/verifyUserPassword"/>';
            var sortProfileGroupsServiceUrl = '<c:url value="/sortProfileGroups" />';
            var sortGroupProfileServiceUrl = '<c:url value="/sortGroupProfile" />';
            var profileGroupExistsServiceUrl = '<c:url value="/profileGroupExists"/>';
            var profileGroupTokenExistsServiceUrl = '<c:url value="/profileGroupTokenExists"/>';
            var profileGroupNewTokenServiceUrl = '<c:url value="/createToken"/>';
            
            var releaseNumServiceUrl = '<c:url value="/releaseNum"/>';
            var addProfileServiceUrl = '<c:url value="/addProfile" />';
            var sortProfilesServiceUrl = '<c:url value="/sortProfiles" />';
            var sortCatalogsServiceUrl = '<c:url value="/sortCatalogs" />';
            var sortProfileCatalogsServiceUrl = '<c:url value="/sortProfileCatalogs" />';
            var activeProfilesServiceUrl = '<c:url value="/activeProfiles" />';
            var deleteProfilesServiceUrl = '<c:url value="/deleteProfiles" />';
            var goToPageProfilesServiceUrl = '<c:url value="/goToPageProfiles" />';
            var filterProfilesServiceUrl = '<c:url value="/filterProfiles" />';
            var filterProfileGroupsServiceUrl = '<c:url value="/filterProfileGroups" />';
            var associateProfileToGroupServiceUrl =  '<c:url value="/associateProfileToGroup" />';
            var activeProfileGroupsServiceUrl = '<c:url value="/activeProfileGroups" />';
            var deleteProfileGroupsServiceUrl = '<c:url value="/deleteProfileGroups" />';
            var goToPageProfileGroupsServiceUrl = '<c:url value="/goToPageProfileGroups" />';
            var goToGroupProfilePageServiceUrl =  '<c:url value="/goToGroupProfilePage" />';
            var disAssociateGroupProfileServiceUrl =  '<c:url value="/disAssociateGroupProfile" />';
            var searchWithinProfileGroupServiceUrl  = '<c:url value="/searchWithinProfileGroup" />';
            var associateCatalogToProfileServiceUrl =  '<c:url value="/associateCatalogToProfile" />';

            var goToProfileCatalogPageServiceUrl =  '<c:url value="/goToProfileCatalogPage" />';
            var goToCatalogPageServiceUrl =  '<c:url value="/goToCatalogPage" />';
            var goToCatalogProfilePageServiceUrl =  '<c:url value="/goToCatalogProfilePage" />';
            var removeCatalogProfilesServiceUrl = '<c:url value="/removeCatalogProfiles" />';
            var goToCatalogNotAssProfilePageServiceUrl = '<c:url value="/goToCatalogNotAssProfilePage" />';
            var profilesPageUrl = '<c:url value="/profiles" />';
            var addCatalogProfilesServiceUrl = '<c:url value="/addCatalogProfiles" />';
            var addCatalogProfileServiceUrl = '<c:url value="/addCatalogProfile" />';
            var searchWithinProfileServiceUrl  = '<c:url value="/searchWithinProfile" />';
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
            var uniqueSystemDoesNotExist = '<spring:message code="com.adminui.msgs.uniqueSystemDoesNotExist" text="default text" />';
            var uniqueSystemIdCanNotBeEmpty = '<spring:message code="com.adminui.msgs.uniqueSystemIdCanNotBeEmpty" text="default text" />';
            var supplierIdEmpty = '<spring:message code="com.adminui.msgs.supplierIdEmpty" text="default text" />';
            var uniqueSystemMapping = '<spring:message code="com.adminui.msgs.uniqueSystemMapping" text="default text" />';
            var updateShopperView= '<spring:message code="com.adminui.msgs.updateShopperView" text="default text" />';
            var republishCatalog = '<spring:message code="com.adminui.msgs.republishCatalog" text="default text" />';
            var gCurrentCatalog = null;
        </script>
        <script type="text/javascript" src="res/js/jcf.js"></script>
        <script type="text/javascript" src="res/js/jquery-1.7.2.min.js"></script>
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
        <script type="text/javascript" src="res/js/underscore.js"></script>
        <script type="text/javascript" src="res/js/backbone.js"></script>
        <script type="text/javascript" src="res/js/jquery.noty.js"></script>
        <script type="text/javascript" src="res/js/jquery.noty-settings.js"></script>
        <script type="text/javascript" src="res/js/json2-min.js"></script>
         <script type="text/javascript" src="res/js/typeahead.bundle.min.js"></script>
        <script type="text/javascript">
	        function headerSearch(){
	            if (!$("#text-field").val()) {
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

            	jQuery.validator.addMethod("passwordValid", function(value, element) {
	           		if(value == ""){
                           return true;
                       }
                    var regex = /^([0-9a-zA-Z])+$/;

	           		if (regex.test(value)) {
		           		return true;
	           		}
	           		return false;
	           	},"");
            	jQuery.validator.addMethod("passwordEqual", function(value, element) {
            		var pass = $('input[name=password]').val();

	           		return pass == value;
	           	},"");
            	function systemUser() {
            		var normal =$(':hidden.normal');
            		return normal.length && normal.val()=='system'; 
            	}
            	function shopperViewOnlyUser() {
            		var shopperViewOnly = $('#createUserForm .role :checkbox.shopper_view_only');
            		return shopperViewOnly.length && shopperViewOnly[0].checked;
            	}
                function usernameValid(username, normal) {
                    var regex = /^[\w+\-.]+@[a-z\d\-.]+\.[a-z]+$/i;
                    var uregex = /^\w+[\w\d\-.@]+$/i;

                    if (!normal && uregex.test(username)) {
                        return true;
                    } else if(normal && regex.test(username)) {
                        return true;
                    }
                    return false;
                }
            	function checkUsernameExists() {
            	    var username = $('input[name=username]').val();
                    var userId = "${param['userId']}";
                    
                    return !usernameExists(username, userId);
                }
            	var message = "";
            	jQuery.validator.addMethod("usernameValidation", function(value, element) {
            		var shopperViewOnly = shopperViewOnlyUser();
            		var normal = !systemUser() && !shopperViewOnly;
            		var usname = normal? "E-mail" : "Username";
            		message = "";
            		
            		if(!value || value.length==0) {
                        message = "Enter your "+usname;
                        return false;
            		} else if(shopperViewOnly && value.length<4) {
                        message = "Please enter "+usname+" with at least 4 characters";
                        return false;
                    } else if(!shopperViewOnly && value.length<8) {
                        message = "Please enter "+usname+" with at least 8 characters";
                        return false;
                    } else if(!usernameValid(value, normal)) {
                        message = "Enter a Valid "+usname;
                        return false;
                    } else if(!checkUsernameExists()) {
                        message = usname+" already in use";
                        return false;
                    }
            		return true;
	           	}, function() {return message;});

            	jQuery.validator.addMethod("requiredNormal", function(value, element) {
	           		var normal =$(':hidden.normal');
	           		if ((normal.length && normal.val() == 'system') || (value && $.trim(value) != "")) {
		           		return true;
	           		}
	           		return false;
	           	},"");

                $("#Method:theCatalogId").validate({
                    rules: {
                        catalogFile: {
                            required: true,
                            accept: "csv|tsv|xlst|xlsx"
                        }
                    },
                    messages: {
                        catalogName: "<spring:message code="error.catalog.create.catalogname.required" />",
                        catalogFile: "<spring:message code="error.catalog.create.catalogfile.accept" />"
                    },
                    submitHandler: function(form) { form.submit(); }
                })

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
                    	// var profileName = $('input[name=profileName]').val();
                        // if(!profileExists(profileName)) {
                         //	$('#profilename_error').removeClass('error');
                         //	$('#profilename_error').addClass('error-msg');
                         	createUserProfile();
                         	$('#cboxClose').trigger('click');
                        // } else {
                        // 	$('#profilename_error').removeClass('error-msg');
                        // 	$('#profilename_error').addClass('error');
             			 //}
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
                    submitHandler: function(form) { form.submit(); }
                })

 				$("#createApprover").validate({
                    rules: {
                    	firstName: {
                            required: true
                        },
                        lastName: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true
                        }
                    },
                    messages: {
                    	firstName: "<spring:message code="error.catalog.create.catalogname.required" />",
                    	lastName: "<spring:message code="error.catalog.create.catalogname.required" />",
                    	email: {
                        	required: "<spring:message code="error.catalog.create.catalogname.required" />",
                    	    email: "<spring:message code="error.catalog.create.catalogname.email" />"
                       	}
                    },
                    submitHandler: function(form) { form.submit(); }
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
                	 onkeyup: false,
                     onclick: false,
                    rules: {
                    	firstName: {
                    		requiredNormal: true
                        },
                        lastName: {
                        	requiredNormal: true
                        },
                        username: {
                            usernameValidation: true
                        },
                        password: {
                        	required: true,
                        	passwordValid: true
                        },
                        passwordCofirm: {
                        	required: true,
                        	passwordEqual: "#password"
                        },
                        roles: {
                        	required: true
                        },
                        contentViewGroupToken: {
                            required: { depends: shopperViewOnlyUser }
                        },
                        shopperViewURL: {
                        	required: { depends: shopperViewOnlyUser }
                        }
                    },
                    messages: {
                    	firstName: "Enter a Valid First Name",
                    	lastName: "Enter a Valid Last Name",
                       	password: {
                       		required: "Enter a Password",
                       		passwordValid: "Password field only allow : a-z 0-9"
                        },
                        passwordCofirm: {
                       		required: "Confirm new Password",
                       		passwordEqual: "Passwords do not match"
                        },
                        contentViewGroupToken: "Select a content view group",
                   		roles: "Select at least one Role",
                   		shopperViewURL: "Click GENERATE to create Shoppper View URL"
                    },
                    submitHandler: function(form) {
                       var pass = $('input[name=password]').val();

                       if(systemUser() || pass != '') {
                     	  	$('#s_password_err').removeClass('error');
                        	$('#s_password_err').addClass('error-msg');

                        	createNewUser(form);
                       } else {
                        	$('#s_password_err').removeClass('error-msg');
                        	$('#s_password_err').addClass('error');
                       }
                    }
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
                                if($("#pwdOld").val() != ''){
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            passwordValid: function(element) {
                                if($("#pwdNew").val() == ''){
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        pwdNew2: {
                        	required: function(element) {
                                return ($("#pwdOld").val() != '');
                            },
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
                       	pwdNew: {
                        	required: "Enter new password",
                        	passwordValid: "Password field only allow : a-z 0-9"
                       	},
                       	pwdNew2: { 
                        	required: "Enter new password",
                        	equalTo: "Passwords do not match"
                       	}
                    },
                    submitHandler: function(form) { 
                   		var pwdold = $('input[name=pwdOld]').val();
                   		var pwdnew = $('input[name=pwdNew]').val();
                   		var pwdNew2 = $('input[name=pwdNew2]').val();
                   		
                        if(pwdold != '' && pwdnew == '') {
                        	$('#newpass_error').removeClass('error-msg');
                        	$('#newpass_error').addClass('error');
                        } else if(pwdold == '' && pwdnew != '') {
                        	$('#oldpass_error').removeClass('error-msg');
                        	$('#oldpass_error').addClass('error');
                        } else if(pwdnew != pwdNew2) {
                        	$('#passmatch_error').removeClass('error-msg');
                        	$('#passmatch_error').addClass('error');
                        } else {
                        	$('#newpass_error').removeClass('error');
                        	$('#newpass_error').addClass('error-msg');
                        	$('#passmatch_error').removeClass('error');
                        	$('#passmatch_error').addClass('error-msg');
                        	$('#oldpass_error').removeClass('error');
                        	$('#oldpass_error').addClass('error-msg');
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
