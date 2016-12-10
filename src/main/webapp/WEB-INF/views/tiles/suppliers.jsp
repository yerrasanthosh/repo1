<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"
         import="com.vroozi.customerui.util.ViewHelper,
         com.vroozi.customerui.supplier.model.Supplier,
         com.vroozi.customerui.supplier.model.Country"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.suppliers.Vroozi" text="default text" /></a></li>
            <li><a href="catalog"><spring:message code="com.adminui.suppliers.ContentManager" text="default text" /></a></li>
            <li><spring:message code="com.adminui.suppliers.Supplier" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">
        <div id="content">

            <div class="content-block toggle-block active" id="create-item-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.suppliers.Supplier" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content editable-widget">
                        <%--<div class="top-box">--%>
                            <%--<div class="btn-holder">--%>
                                <%--<ul class="options ">--%>
                                    <%--<li>--%>
                                        <%--<a href="#create-supplier" class="btn-create-profile open-popup"><span><em>Create Supplier</em></span></a>--%>
                                    <%--</li>--%>
                                <%--</ul>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="scrollable-area vscrollable">
                            <div class="text">
                                <form action="/suppliers" id="suppliers-form" method="post" enctype="text/plain" class="checkboxResetForm">
                                    <table class="table-data" name="supplierTable">
                                        <thead>
                                        <tr>
                                            <th class="td-select"><input type="checkbox" name="check-all" id="check1" onclick="checkAllSuppliers(this.checked)" /><label for="check1"></label></th>
                                            <th class="a-left"><spring:message code="com.adminui.suppliers.CompanyName" text="default text" /></th>
                                            <th><spring:message code="com.adminui.suppliers.CreatedOn" text="default text" /></th>
                                            <th><spring:message code="com.adminui.suppliers.CreatedBy" text="default text" /></th>
                                            <th class="td-last"><spring:message code="com.adminui.suppliers.Active" text="default text" /></th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <% int supplierCounter = 0; %>
                                        <c:forEach var="supplier" items="${suppliers}" varStatus="cntr1">
                                            <tr>
                                                <td class="td-select"><input type="checkbox" name="companyUserIds" id="${supplier.companyId}" value="${supplier.companyId}" /><label for="${supplier.companyId}"></label></td>
                                                <td class="a-left td-description">
                                                    <div class="field" style="width:150px">
                                                        <span><a href="edit-supplier?companyId=${supplier.companyId}&companyName=${supplier.companyName}&dunsNumber=${supplier.dunsNumber}">${supplier.companyName}</a></span>
                                                        <input type="text" name="" class="ico-edit alt-opener" value="${supplier.companyName}" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="field" style="width:150px">
                                                        <span>${supplier.createdOn}</span>
                                                        <input type="text" value="${supplier.createdOn}" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="field" style="width:150px">
                                                        <span>${supplier.createdBy}</span>
                                                        <input type="text" value="${supplier.createdBy}" />
                                                    </div>
                                                </td>
                                                <%--<td>--%>
                                                    <%--<div class="field" style="width:150px">--%>
                                                        <%--<span>${supplier.active == 'true' ? 'Yes' : 'No'}</span>--%>
                                                        <%--<input type="text" value="${supplier.active == 'true' ? 'Yes' : 'No'}" />--%>
                                                    <%--</div>--%>
                                                <%--</td>--%>
                                                <td class="td-last">${supplier.active==true?'Yes':'No'}</td>
                                            </tr>
                                            <% ++supplierCounter; %>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div id="updateSuppliersFeedback" align="left" style="height:10px"></div>
                                </form>
                            </div>
                        </div>
                        <div class="bottom-data">
                            <!-- <div class="pager">
                                <span>Page 1 of 49 </span>
                                <ul>
                                    <li><a href="#" class="btn-prev"></a></li>
                                    <li><a href="#" class="btn-next"></a></li>
                                </ul>
                            </div>-->
                            <strong class="pages"><spring:message code="com.adminui.suppliers.TotalRecords" text="default text" /> <%= supplierCounter %></strong>

                        </div>
                        <div class="move-items">
                        </div>
                        <div class="function">
                            <ul>
                                <li><a href="javascript:void(0)" class="ico-approve" onclick="activeSupplier(true);"><span><em><spring:message code="com.adminui.suppliers.Activate" text="default text" /></em></span></a></li>
                                <li><a href="javascript:void(0)" class="ico-reject" onclick="activeSupplier(false);"><span><em><spring:message code="com.adminui.suppliers.Deactivate" text="default text" /></em></span></a></li>
                                <!-- <li class="del"><a href="#delete" class="ico-delete" onclick="deleteProfiles();"><span><em>DELETE</em></span></a></li> -->
                                <li class="del"><a href="javascript:void(0)" class="ico-delete" onclick="deleteProfiles();"><span><em><spring:message code="com.adminui.suppliers.DELETE" text="default text" /></em></span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href="index.html" class="ico-back"><span><spring:message code="com.adminui.suppliers.BACK" text="default text" /></span></a></li>
                <li><a href="/adminui/create-supplier" class="btn-create-profile"><span><spring:message code="com.adminui.suppliers.CreateSupplier" text="default text" /></span></a></li>
                <li><a href="/adminui/company-attributes" class="btn-create-profile"><span><spring:message code="com.adminui.suppliers.CreateCompanyAttributes" text="default text" /></span></a></li>
                <li><a href="/adminui/vendor-mapping" class="btn-create-profile"><span><spring:message code="com.adminui.suppliers.CreateVendorMapping" text="default text" /></span></a></li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    function checkAllSuppliers(check){
        try{
            if(check){
                $("#suppliers-form :checkbox").each(function(){
                    $(this).next().addClass('ui-state-active');
                    var checkbox = $(this)[0];
                    checkbox.checked=true;
                });
            }else{
                $("#suppliers-form :checkbox").each(function(){
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