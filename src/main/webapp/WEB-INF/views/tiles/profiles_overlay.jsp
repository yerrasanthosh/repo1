<%@page import="com.vroozi.customerui.catalog.model.CatalogSummary"%>
<%@page import="com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="lightbox-section">
    <div class="lightbox" id="create-profile">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.profiles_overlay.Close" text="default text" /></a>
                    <h2>Create Content View</h2>
                </div>
                <form action="addProfile" class="form-create" id="createProfile" method="post">
                    <fieldset>
                        <h3><spring:message code="com.adminui.profiles_overlay.CONTENTVIEWSETTINGS" text="default text" /></h3>
                        <div class="area">
                            <div class="row" id="theProfileNameId">
                                <label for="profile-name"><span>*</span><spring:message code="com.adminui.profiles_overlay.ContentViewName" text="default text" /></label>
                                <input type="text" name ="profileName" id="profile-name" class="required"/>
                            </div>
                            <div class="row" id="theProfileDescId">
                                <label for="profile-description"><span>*</span><spring:message code="com.adminui.profiles_overlay.ContentViewDescription" text="default text" /></label>
                                <textarea id="profile-description"  name ="profileDesc" cols="30" rows="10" class="textarea required"></textarea>
                            </div>
                            <div class="row">
                                <label for="rating"><spring:message code="com.adminui.profiles_overlay.AllowProductRating" text="default text" /></label>
                                <input type="checkbox"  name="rating" id="rating" />
                            </div>
                            <div class="row">
                                <label><spring:message code="com.adminui.profiles_overlay.Active" text="default text" /></label>
                                <select class="alt-select choose-tab-select" id="active" name="activeStr">
                                    <option value="Yes" title="#area-1"><spring:message code="com.adminui.profiles_overlay.Yes" text="default text" /></option>
                                    <option value="No" title="#area-2"><spring:message code="com.adminui.profiles_overlay.No" text="default text" /></option>
                                </select>
                            </div>
                        </div>
                        </fieldset>
                        <h4><spring:message code="com.adminui.profiles_overlay.AssociatedCatalogs" text="default text" /></h4>
                        <div class="area alt-area">
                        	<table class="table-data">
									<thead>
										<tr>
											<th class="td-select"><input type="checkbox" name="check-lightbox-all" id="check-lightbox-1"  onclick="checkAllCatalogItems(this.checked)" /><label for="check-lightbox-1"></label></th>
											<th class="a-left"><spring:message code="com.adminui.profiles_overlay.CatalogName" text="default text" /></th>
											<th class="a-left"><spring:message code="com.adminui.profiles_overlay.CatalogType" text="default text" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="cat" items="${catalogs}" varStatus="cntr1">
										<tr>
											<td class="td-select"><input type="checkbox" name="associatedCatalogs" id="profile-${cat.catalogId}" value="${cat.catalogId}"/><label for="profile-${cat.catalogId}"></label></td>
											<td class="a-left td-description">
												<div class="field">
													<span><a href="#">${cat.name}</a></span>
													<input type="text" value="${cat.name}" />
												</div>
											</td>
											<td class="a-left">${cat.catalogTypeDesc}</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
                        </div>
                        
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.profiles_overlay.Cancel" text="default text" /></a>
                            <!--a class="btn-create"  onclick="$('#createProfileId').submit();"><span>Save</span></a-->
                            <input type="submit" value="Save" name="Save">
                            <p><span class="required">* </span><spring:message code="com.adminui.profiles_overlay.RequiredField" text="default text" /></p>
                        </div>

                </form>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="lightbox-delete">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a id="lighboxDeleteCatalogCloseBtn" href="#" class="close"><spring:message code="com.adminui.profiles_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.profiles_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.profiles_overlay.msg" text="default text" /></p>
                    <%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a id="lighboxDeleteProfileBtn" href="#" class="btn btn-red .delete-btn"><span><spring:message code="com.adminui.profiles_overlay.DELETE" text="default text" /></span></a>
                    </div>
                </div>
            </div>
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
</script>