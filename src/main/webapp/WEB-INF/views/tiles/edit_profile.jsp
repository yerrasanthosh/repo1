<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="header">
    <div class="holder">
        <div class="panel">
            <strong class="logo"><a href="#"><spring:message code="com.adminui.edit_profile.SmartOCIDefiningB2bCommerce" text="default text" /></a></strong>
            <ul class="add-nav">
                <li>
                    <a class="user" href="#"><spring:message code="com.adminui.edit_profile.Welcome" text="default text" />,<strong><spring:message code="com.adminui.edit_profile.USERNAME" text="default text" /></strong></a>
                    <ul>
                        <li><a href="#"><spring:message code="com.adminui.edit_profile.Account" text="default text" /></a></li>
                        <li><a href="#"><spring:message code="com.adminui.edit_profile.Settings" text="default text" /></a></li>
                        <li><a href="#"><spring:message code="com.adminui.edit_profile.Selected" text="default text" /></a></li>
                        <li><a href="#"><spring:message code="com.adminui.edit_profile.Lorem" text="default text" /></a></li>
                    </ul>
                </li>
                <li><a href="#"><spring:message code="com.adminui.edit_profile.Messages" text="default text" />(0)</a></li>
                <li><a href="#"><spring:message code="com.adminui.edit_profile.Help" text="default text" /></a></li>
                <li><a class="cart" href="#"><spring:message code="com.adminui.edit_profile.cartItem" text="default text" /></a></li>
            </ul>
            <form action="#" class="search-form">
                <fieldset>
                    <label for="text-field"><spring:message code="com.adminui.edit_profile.SEARCH" text="default text" /></label>
                    <input id="text-field" type="text" value="" />
                    <select>
                        <option selected="selected"><spring:message code="com.adminui.edit_profile.ContentManager" text="default text" /></option>
                        <option><spring:message code="com.adminui.edit_profile.AtherCatalog" text="default text" /></option>
                    </select>
                    <input type="submit" value="Submit" />
                    <a href="#" class="link"><spring:message code="com.adminui.edit_profile.AdvancedSearch" text="default text" /></a>
                </fieldset>
            </form>
        </div>
        <ul id="nav">
            <li><a href="#"><spring:message code="com.adminui.edit_profile.HOME" text="default text" /></a></li>
            <li><a href="#"><spring:message code="com.adminui.edit_profile.SHOPPINGCART" text="default text" /></a></li>
            <li><a href="#"><spring:message code="com.adminui.edit_profile.STATISTICS" text="default text" /></a></li>
            <li><a href="#"><spring:message code="com.adminui.edit_profile.FAVORITES" text="default text" /></a></li>
            <li><a href="#" class="active"><spring:message code="com.adminui.edit_profile.MANAGE" text="default text" /></a></li>
        </ul>
    </div>
</div>
<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.edit_profile.Vroozi" text="default text" /></a></li>
            <li><spring:message code="com.adminui.edit_profile.ContentViewManagement" text="default text" /></li>
        </ul>
        <div class="video-btn-holder">
            <!--img src="images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
        </div>
    </div>
    <div class="main-holder">
        <div id="content">
            <h1><spring:message code="com.adminui.edit_profile.EditContentView" text="default text" /></h1>
            <div class="content-block toggle-block active" id="create-profile">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.edit_profile.ContentViewSettings" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content">
                        <form class="settings-form" action="#">
                            <div class="text-fields">
                                <div class="row">
                                    <label for="s-name"><span class="required">*</span><spring:message code="com.adminui.edit_profile.ContentViewName" text="default text" /></label>
                                    <div class="area-col">
                                        <input id="s-name" class="text" type="text" value="Apparel" />
                                        <span class="error-msg"><spring:message code="com.adminui.edit_profile.validContentView" text="default text" /></span>
                                    </div>
                                </div>
                                <div class="row">
                                    <label for="s-description"><spring:message code="com.adminui.edit_profile.Description" text="default text" /></label>
                                    <div class="area-col">
                                        <textarea id="s-description" cols="50" rows="3"><spring:message code="com.adminui.edit_profile.descOfClothing" text="default text" /></textarea>
                                    </div>
                                </div>
                                <div class="row">
                                    <strong class="label"><spring:message code="com.adminui.edit_profile.ProductRating" text="default text" /></strong>
                                    <div class="area-col">
                                        <div class="item">
                                            <input id="cf-1" type="checkbox" checked="checked" />
                                            <label for="cf-1"></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row active">
                                    <label for="s-active"><spring:message code="com.adminui.edit_profile.Active" text="default text" /></label>
                                    <div class="area-col">
                                        <a class="link" href="#"><spring:message code="com.adminui.edit_profile.Yes" text="default text" /></a>
                                    </div>
                                </div>
                            </div>
                            <div class="btns-holder">
                                <a class="btn-cancel" href="#"><spring:message code="com.adminui.edit_profile.Cancel" text="default text" /></a>
                                <input type="submit" value="Save" />
                                <p><span class="required">* </span><spring:message code="com.adminui.edit_profile.RequiredField" text="default text" /></p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="add-slide-blocks">
                <div class="toggle-block active" id="associate-catalogs">
                    <div class="title">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.edit_profile.CATALOGS" text="default text" /></a></h2>
                    </div>
                    <div class="block">
                        <div class="content editable-widget">
                            <div class="catalog-box">
                                <div class="btn-holder">
                                    <a href="#add-catalogs" class="btn-add-catalogs open-popup"><span><em class="checkboxReset"><spring:message code="com.adminui.edit_profile.ADDCATALOGS" text="default text" /></em></span></a>
                                </div>
                                <table class="table-data">
                                    <thead>
                                    <tr>
                                        <th class="td-select"><input type="checkbox" class="check-allbox" name="check-all4" id="check4-1" /><label for="check4-1"></label></th>
                                        <th class="a-left"><a class="sorting" href="#"><spring:message code="com.adminui.edit_profile.CatalogName" text="default text" /></a></th>
                                        <th class="a-left"><spring:message code="com.adminui.edit_profile.CatalogType" text="default text" /></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-2" /><label for="check4-2"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.Apparel" text="default text" /></a></span>
                                                <input type="text" value="Apparel" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.InternalCatalog" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-3" /><label for="check4-3"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.AutoRentals" text="default text" /></a></span>
                                                <input type="text" value="Auto Rentals" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.InternalCatalog" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-4" /><label for="check4-4"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.BB_Test" text="default text" /></a></span>
                                                <input type="text" value="BB_Test" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.InternalCatalog" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-5" /><label for="check4-5"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.DemoTest" text="default text" /></a></span>
                                                <input type="text" value="Demo Test" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.InternalCatalog" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-6" /><label for="check4-6"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.BusinessCardTemplate" text="default text" /></a></span>
                                                <input type="text" value="Business Card Template" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.Quotes" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-7" /><label for="check4-7"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.BustechQuote1" text="default text" /></a></span>
                                                <input type="text" value="Bustech Quote 1" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.Quotes" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-8" /><label for="check4-8"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.CDW" text="default text" /></a></span>
                                                <input type="text" value="CDW" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.InternalCatalog" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-9" /><label for="check4-9"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.ITTechnology" text="default text" /></a></span>
                                                <input type="text" value="IT Technology" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.Quotes" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-10" /><label for="check4-10"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.dellCustomField" text="default text" /></a></span>
                                                <input type="text" value="Dell Supplier Custom Field" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.InternalCatalog" text="default text" /></td>
                                    </tr>
                                    <tr>
                                        <td class="td-select"><input type="checkbox" class="target-chbox" name="check4" id="check4-11" /><label for="check4-11"></label></td>
                                        <td class="a-left td-username td-sorting">
                                            <div class="field">
                                                <span><a href="#"><spring:message code="com.adminui.edit_profile.Lorem" text="default text" /></a></span>
                                                <input type="text" value="Lorem Ipsum Dolor" />
                                            </div>
                                        </td>
                                        <td class="a-left"><spring:message code="com.adminui.edit_profile.InternalCatalog" text="default text" /></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="bottom-data">
                                    <div class="pager">
                                        <span><spring:message code="com.adminui.edit_profile.page" text="default text" /> 1 of 87 </span>
                                        <ul>
                                            <li><a class="btn-prev" href="#"></a></li>
                                            <li><a class="btn-next" href="#"></a></li>
                                        </ul>
                                    </div>
                                    <strong class="pages"><spring:message code="com.adminui.edit_profile.total" text="default text" /> 1-10 of 87</strong>
                                </div>
                                <div class="function">
                                    <ul>
                                        <li><a href="#" class="ico-remove"><span><em><spring:message code="com.adminui.edit_profile.REMOVE" text="default text" /></em></span></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <li><a href="index.html" class="ico-back"><span><spring:message code="com.adminui.edit_profile.BACK" text="default text" /></span></a></li>
                <li><a href="#create-profile" class="ico-profile alt-opener"><span><spring:message code="com.adminui.edit_profile.CREATECONTENTVIEW" text="default text" /></span></a></li>
                <li><a href="#associate-catalogs" class="ico-catalogs alt-opener"><span><spring:message code="com.adminui.edit_profile.ASSOCIATECATALOGS" text="default text" /></span></a></li>
            </ul>
        </div>
    </div>
</div>