<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.catalog.model.CatalogSummary,
         com.vroozi.customerui.profile.model.Profile,
         com.vroozi.customerui.util.ViewHelper"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="res/js/adminui_catalog_detail.js"></script>

<div id="main">
    
    <!-- BreadCrumbs code -->
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.vendor_mapping.Vroozi" text="default text" /></a></li>
            <li><spring:message code="com.adminui.vendor_mapping.MaterialGroup" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>TUTORIAL</em></span></a-->
        </div>
    </div>
    
    <!-- Page Code starts from here -->
    <div class="main-holder"> 
    	<div id="content">
    		<div class="content-block toggle-block active" id="edit-section_mapping">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.vendor_mapping.QuickCategorySearch" text="default text" /></a></h2>
                </div>
				
				<form action="#" id="searchForm">
					<fieldset>
 						<div class="area">
                            <div class="row" id="theCatalogNameId">
                            	<input type="text" name="txtSearch" id="search-text" class="required" value="">
                            	<input type="submit" value="Create" name="Create">
								<a href="#" class="btn-reset"><span><spring:message code="com.adminui.vendor_mapping.Reset" text="default text" /></span></a>
                        	</div>
                        </div>
                    </fieldset>
				</form>
    		</div>
            <div class="content-block toggle-block active" id="edit-section-vendor">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.vendor_mapping.CategoryManagement" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content reorder-widget editable-widget">
                        <div class="col">
                            <h3><spring:message code="com.adminui.vendor_mapping.Summary" text="default text" /></h3>

                        </div>
                        <div class="col alt-col">
                            <h3><spring:message code="com.adminui.vendor_mapping.UploadMappingFile" text="default text" /></h3>
							<form action="uploadVendorMapping" id="vendorMappingGroupForm" class="form-create" method="post"  enctype="multipart/form-data">
				            	<fieldset>
					            	<div class="area">
	                          		  <div class="row" style="width:100%" id="theCatalogFileId">
                                          <spring:message code="com.adminui.vendor_mapping.MappingFile" text="default text" />
	                             		  <input size="400" type="file" name="vendorMappingFile" id="vendorMappingFileId" />
	                                	  <input type="submit" value="submit"/>
	                            	  </div>
	                            	</div>
                            	</fieldset>
							</form>
                        </div>
                    </div>
                </div>
                
            </div>



            <div class="content-block toggle-block active" id="create-item-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.vendor_mapping.MaterialGroups" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content editable-widget">
                    <div class="scrollable-area vscrollable">
                        <div class="text">
                        	<form action="/vendor-mapping" id="cat-items-form" method="post" enctype="text/plain">
                            <table class="table-data alt-table-data">
                                <thead>
                                    <tr>
                                        <th><spring:message code="com.adminui.vendor_mapping.VendorNumber" text="default text" /></th>
                                        <th><spring:message code="com.adminui.vendor_mapping.UNSPSCCode" text="default text" /></th>
                                        <th><spring:message code="com.adminui.vendor_mapping.CompanyCategoryId" text="default text" /></th>
                                        <th> <spring:message code="com.adminui.vendor_mapping.CompanyCategoryLabel" text="default text" /></th>
                                        <th class="td-last"><spring:message code="com.adminui.vendor_mapping.Parent" text="default text" /></th>
                                    </tr>
                                </thead>
                                <tbody id="catalog_item_table_rows_body">
                                    <c:forEach var="materialGroup" items="${materialGroups}" varStatus="cntr">
                                        <tr>
                                            <td style="width:15%">
                                                <div>
                                                    <span>${materialGroup.supplierId}</span>
                                                   
                                                </div>
                                            </td>
                                            <td style="width:15%">
                                                <div>
                                                    <span>${materialGroup.catalogCategoryCode}</span>
                                                
                                                </div>
                                            </td>
                                            <td style="width:20%">
                                                <div>
                                                    <span>${materialGroup.companyCategoryCode}</span>
                                                
                                                </div>
                                            </td>
                                            <td style="width:35%">
                                                <div>
                                                    <span>${materialGroup.companyLabel}</span>
                                                
                                                </div>
                                            </td>
                                            <td style="width:15%">
                                                <div>
                                                    <span>${materialGroup.parent}</span>
                                                
                                                </div>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                           </form>
                        </div>
                    </div>
                    <c:if test="${catalogItemTotalNumOfPages > 1}">
                        <div class="bottom-data">
                        <div class="pager">
                            <span id="catalog_items_current_page_num"><spring:message code="com.adminui.vendor_mapping.Page" text="default text" /> 1 <spring:message code="com.adminui.vendor_mapping.of" text="default text" /> <c:out value="${catalogItemTotalNumOfPages}"/> </span>
                            <ul>
                                <li><a id="catalog-item-prev-page" class="btn-prev" onclick="getCatalogItemsPage(gCatalogItemsPageNum-1);return false;"></a></li>
                                <li><a id="catalog-item-next-page" class="btn-next" onclick="getCatalogItemsPage(gCatalogItemsPageNum+1);return false;"></a></li>
                            </ul>
                        </div>
                        <strong class="pages" id="catalog_items_current_record_range"><spring:message code="com.adminui.vendor_mapping.TotalRecords" text="default text" /> 1- <c:out value="${catalogItemCurrentNumOfItems}"/> <spring:message code="com.adminui.vendor_mapping.of" text="default text" /> <c:out value="${catalogItemTotalNumOfItems}"/></strong>
                    </div>
                    </c:if>
                    <div class="move-items">
                        <div class="holder">
                        </div>
                    </div>
                    <div class="function">
                        <ul>
                            <li>
                            <a href='findMaterialGroup' class="ico-approve" ><span><em>Search All</em></span></a></li>
                        </ul>
                    </div>
                </div>
                </div>
            </div>

    		
    		
    	</div>
    </div>
    
    
    
    
</div>