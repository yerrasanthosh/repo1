<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false"
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
            <li><a href="<c:out value='${smartOCIAppBaseURI}'/>"><spring:message code="com.adminui.material_group.smartOCI" text="default text" /></a></li>
            <li><spring:message code="com.adminui.material_group.MaterialGroup" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>TUTORIAL</em></span></a-->
        </div>
    </div>
    
    <!-- Page Code starts from here -->
    <div class="main-holder"> 
    	<div id="content">
    		<div class="content-block toggle-block active" id="edit-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.material_group.QuickCategorySearch" text="default text" /></a></h2>
                </div>
				
				<form action="#" id="searchForm">
					<fieldset>
 						<div class="area">
                            <div class="row" id="theCatalogNameId">
                            	<input type="text" name="txtSearch" id="search-text" class="required" value="">
                            	<input type="submit" value="Create" name="Create">
								<a href="#" class="btn-reset"><span><spring:message code="com.adminui.material_group.Reset" text="default text" /></span></a>
                        	</div>
                        </div>
                    </fieldset>
				</form>
    		</div>
            <div class="content-block toggle-block active" id="edit-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.material_group.CategoryManagement" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content reorder-widget editable-widget">
                        <div class="col">
                            <h3><spring:message code="com.adminui.material_group.Summary" text="default text" /></h3>
                            <table style="width:275px;" class="summary-table">
                                <tfoot>
                                <tr>
                                    <td><spring:message code="com.adminui.material_group.Total" text="default text" /></td>
                                    <td class="a-right"><c:out value="0"/></td>
                                </tr>
                                </tfoot>
                                <tbody>
                                <tr>
                                    <th><spring:message code="com.adminui.material_group.Level1Categories" text="default text" /></th>
                                    <td class="a-right">
                                       	<div>
                                       		<dd id='level1Count'><c:out value="0"/></dd>
                                       	</div>
                                    </td>
                                </tr>
                                <tr>
                                    <th><spring:message code="com.adminui.material_group.Level2Categories" text="default text" /></th>
                                    <td class="a-right">
                                       	<div>
                                       		<dd id='level2Count'><c:out value="0"/></dd>
                                       	</div>
                                    </td>
                                </tr>
                                <tr>
                                    <th><spring:message code="com.adminui.material_group.Level3Categories" text="default text" /></th>
                                    <td class="a-right">
                                       	<div>
                                       		<dd id='level3Count'><c:out value="0"/></dd>
                                       	</div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="col alt-col">
                            <h3><spring:message code="com.adminui.material_group.UploadMappingFile" text="default text" /></h3>
							<form action="uploadMaterialGroup" id="mappingFileForm" class="form-create" method="post" enctype="multipart/form-data">
				            	<fieldset>
					            	<div class="area">
	                          		  <div class="row" style="width:100%" id="theCatalogFileId">
                                          <spring:message code="com.adminui.material_group.MappingFile" text="default text" />
	                             		  <input size="400"type="file" name="materialGroupFile" id="materialGroupFile" />
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
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.material_group.MaterialGroups" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content editable-widget">
                    <div class="scrollable-area vscrollable">
                        <div class="text">
                        	<form action="/approveItems" id="cat-items-form" method="post" enctype="text/plain">
                            <table class="table-data alt-table-data">
                                <thead>
                                    <tr>
                                        <th><spring:message code="com.adminui.material_group.VendorNumber" text="default text" /></th>
                                        <th><spring:message code="com.adminui.material_group.UNSPSCCode" text="default text" /></th>
                                        <th><spring:message code="com.adminui.material_group.CompanyCategoryId" text="default text" /></th>
                                        <th> <spring:message code="com.adminui.material_group.CompanyCategoryLabel" text="default text" /></th>
                                        <th class="td-last"><spring:message code="com.adminui.material_group.Parent" text="default text" /></th>
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
                            <span id="catalog_items_current_page_num"><spring:message code="com.adminui.material_group.Page" text="default text" /> 1 of <c:out value="${catalogItemTotalNumOfPages}"/> </span>
                            <ul>
                                <li><a id="catalog-item-prev-page" class="btn-prev" onclick="getCatalogItemsPage(gCatalogItemsPageNum-1);return false;"></a></li>
                                <li><a id="catalog-item-next-page" class="btn-next" onclick="getCatalogItemsPage(gCatalogItemsPageNum+1);return false;"></a></li>
                            </ul>
                        </div>
                        <strong class="pages" id="catalog_items_current_record_range"><spring:message code="com.adminui.material_group.TotalRecords" text="default text" /> 1- <c:out value="${catalogItemCurrentNumOfItems}"/> of <c:out value="${catalogItemTotalNumOfItems}"/></strong>
                    </div>
                    </c:if>
                    <div class="move-items">
                        <div class="holder">
                        </div>
                    </div>
                    <div class="function">
                        <ul>
                            <li>
                            <a href='findMaterialGroup' class="ico-approve" ><span><em><spring:message code="com.adminui.material_group.SearchAll" text="default text" /></em></span></a></li>
                        </ul>
                    </div>
                </div>
                </div>
            </div>

    		
    		
    	</div>
    </div>
    
    
    
    
</div>