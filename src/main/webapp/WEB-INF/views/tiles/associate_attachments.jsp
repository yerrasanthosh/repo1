<%@page import="com.vroozi.customerui.profile.model.ProfileProxy"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fieldset>
        <div class="lightbox-content">
            <div style="margin-bottom:15px;">
                <input type="text" value="Search Catalogs" />
                <input type="buttom" value="Submit" id="searchIcon"/>
                <div style="float:right;">
                    <label><spring:message code="com.adminui.associate_attachments.sortBy" text="default text" /> </label>
                    <select style="width:50px;">
                        <option><spring:message code="com.adminui.associate_attachments.sortByName" text="default text" /></option>
                    </select>
                </div>
            </div>

            <div style="border:1px solid #C5C5C5;-webkit-box-shadow: inset 0px 0px 4px 1px rgba(0, 0, 0, 0.2); box-shadow: inset 0px 0px 4px 1px rgba(0, 0, 0, 0.2);">
                <div class="scrollable-area anyscrollable" style="width:841px;">
                    <div class="text" style="width:800px;margin:0 12px;">
                        <table class="table-data">
                            <tbody>
                            <tr>
                                <td class="td-select"><input type="checkbox" name="check" class="target-chbox" id="check-1" /><label for="check-1"></label></td>
                                <td class="a-left td-description">
                                    <img src="res/images/img06.png" width="80" height="58" alt="img" />
                                    <div class="field">
                                        <label><spring:message code="com.adminui.associate_attachments.title" text="default text" /> </label><span><a href="#"><spring:message code="com.adminui.associate_attachments.manualForWidget" text="default text" /></a></span><br />
                                        <label><spring:message code ="com.adminui.associate_attachments.name" text="default text" /> </label>manual_for_widget.pdf
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div style="margin-top:10px;">
                <label><spring:message code ="com.adminui.associate_attachments.fileName" text="default text" /></label>
                <input type="text" style="width:530px;"/>
                <select>
                    <option><spring:message code="com.adminui.associate_attachments.allFiles" text="default text" /></option>
                </select>
            </div>
        </div>
        <div class="btns-holder" style="margin-top:35px;">
            <a href="#" class="btn-cancel"><spring:message code="com.adminui.associate_attachments.cancel" text="default text" /></a>
            <input type="submit" value="Save" />
        </div>
    </fieldset>