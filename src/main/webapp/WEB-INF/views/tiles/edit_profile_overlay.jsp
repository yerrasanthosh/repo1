<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="delete">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.edit_profile_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.edit_profile_overlay.DeletionConfirmation" text="default text" /></h2>
                </div>
                <div class="content">
                    <p><spring:message code="com.adminui.edit_profile_overlay.confirmDelete" text="default text" /></p>
                    <p><strong><spring:message code="com.adminui.edit_profile_overlay.lorem" text="default text" /></strong></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" class="btn btn-red"><span><spring:message code="com.adminui.edit_profile_overlay.DELETE" text="default text" /></span></a>
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
                    <a href="#" class="close"><spring:message code="com.adminui.edit_profile_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.edit_profile_overlay.AddCatalogs" text="default text" /></h2>
                </div>
                <div class="search-box">
                    <form action="#" class="add-search-form">
                        <fieldset>
                            <input type="text" value="Search Catalogs" />
                            <input type="submit" value="Submit" />
                        </fieldset>
                    </form>
                    <form action="#" class="form-sort">
                        <fieldset>
                            <label><spring:message code="com.adminui.edit_profile_overlay.SortBy" text="default text" /> </label>
                            <select>
                                <option><spring:message code="com.adminui.edit_profile_overlay.All" text="default text" /></option>
                                <option><spring:message code="com.adminui.edit_profile_overlay.Option2" text="default text" /></option>
                            </select>
                        </fieldset>
                    </form>
                </div>
                <form action="#">
                    <fieldset>
                        <div class="lightbox-content editable-widget">
                            <table class="table-data">
                                <thead>
                                <tr>
                                    <th class="td-select"><input type="checkbox" class="check-allbox" name="check-lightbox-all" id="check-lightbox-1" /><label for="check-lightbox-1"></label></th>
                                    <th class="a-left"><a class="sorting" href="#"><spring:message code="com.adminui.edit_profile_overlay.CatalogName" text="default text" /></a></th>
                                    <th class="a-left"><spring:message code="com.adminui.edit_profile_overlay.CatalogType" text="default text" /></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-2" /><label for="check-lightbox-2"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.Apparel" text="default text" /></a></span>
                                            <input type="text" value="Apparel" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.InternalCatalog" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-3" /><label for="check-lightbox-3"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.AutoRentals" text="default text" /></a></span>
                                            <input type="text" value="Auto Rentals" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.InternalCatalog" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-4" /><label for="check-lightbox-4"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.BB_Test" text="default text" /></a></span>
                                            <input type="text" value="BB_Test" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.InternalCatalog" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-5" /><label for="check-lightbox-5"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.DemoTest" text="default text" /></a></span>
                                            <input type="text" value="Demo Test" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.InternalCatalog" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-6" /><label for="check-lightbox-6"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.BusinessCardTemplate" text="default text" /></a></span>
                                            <input type="text" value="Business Card Template" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.Quotes" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-7" /><label for="check-lightbox-7"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.BustechQuote1" text="default text" /></a></span>
                                            <input type="text" value="Bustech Quote 1" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.Quotes" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-8" /><label for="check-lightbox-8"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.CDW" text="default text" /></a></span>
                                            <input type="text" value="CDW" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.InternalCatalog" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-9" /><label for="check-lightbox-9"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.ITTechnology" text="default text" /></a></span>
                                            <input type="text" value="IT Technology" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.Quotes" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-10" /><label for="check-lightbox-10"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.dell" text="default text" /></a></span>
                                            <input type="text" value="Dell Supplier Custom Field" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.InternalCatalog" text="default text" /></td>
                                </tr>
                                <tr>
                                    <td class="td-select"><input type="checkbox" class="target-chbox" name="check-lightbox" id="check-lightbox-11" /><label for="check-lightbox-11"></label></td>
                                    <td class="a-left td-username td-sorting">
                                        <div class="field">
                                            <span><a href="#"><spring:message code="com.adminui.edit_profile_overlay.lorem" text="default text" /></a></span>
                                            <input type="text" value="Lorem Ipsum Dolor" />
                                        </div>
                                    </td>
                                    <td class="a-left"><spring:message code="com.adminui.edit_profile_overlay.InternalCatalog" text="default text" /></td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="bottom-data">
                                <div class="pager">
                                    <span><spring:message code="com.adminui.edit_profile_overlay.page" text="default text" /> 1 of 87 </span>
                                    <ul>
                                        <li><a href="#" class="btn-prev"></a></li>
                                        <li><a href="#" class="btn-next"></a></li>
                                    </ul>
                                </div>
                                <strong class="pages"><spring:message code="com.adminui.edit_profile_overlay.total" text="default text" /> 1-10 of 87</strong>
                            </div>
                        </div>
                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.edit_profile_overlay.Cancel" text="default text" /></a>
                            <input type="submit" value="Save" />
                            <p><span class="required">* </span><spring:message code="com.adminui.edit_profile_overlay.RequiredField" text="default text" /></p>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>