<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="true"%>
<%@ page import="com.vroozi.customerui.acl.model.Permission" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript">
</script>
            <div class="content-block toggle-block active" id="summary-section">
                <div class="headline">
                    <h2><a href="#" class="open-close"><spring:message code="com.adminui.profile_group_management.groupSummary" text="default text" /></a></h2>
                </div>
                <div class="block">
                    <div class="content">
                        <div class="summary-box">
                            <table class="summary-table">
                                <thead>
                                    <tr>
                                        <td class="a-center sep view"><spring:message code="com.adminui.profile_group_management.View" text="default text" /></td>
                                        <td><spring:message code="com.adminui.profile_group_management.Summary" text="default text" /></td>
                                        <td></td>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <td class="a-center view"></td>
                                        <td><spring:message code="com.adminui.profile_group_management.Total" text="default text" /></td>
                                        <td class="a-right" id="totalProfileGroupsCount">${totalProfilesGroupCount}</td>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <tr>
                                        <td class="a-center view">
                                            <input id="viewActiveProfileGroup" type="checkbox" checked="checked" onclick="filterProfileGroupView();"/>
                                            <label for="viewActiveProfileGroup"></label>
                                        </td>
                                        <th><spring:message code="com.adminui.profile_group_management.ActiveContentViewGroups" text="default text" /></th>
                                        <td class="a-right" id="activeProfileGroupsCount">${numOfActiveProfileGroup}</td>
                                    </tr>
                                    <tr>
                                        <td class="a-center view">
                                            <input id="viewInactiveProfileGroup" type="checkbox" checked="checked" onclick="filterProfileGroupView();"/>
                                            <label for="viewInactiveProfileGroup"></label>
                                        </td>
                                        <th><spring:message code="com.adminui.profile_group_management.InactiveContentViewGroups" text="default text" /></th>
                                        <td class="a-right" id="inactiveProfileGroupsCount">${totalProfilesGroupCount-numOfActiveProfileGroup}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="add-slide-blocks">
                <div class="toggle-block active" id="info-section">
                    <div class="title">
                        <h2><a href="#" class="open-close"><spring:message code="com.adminui.profile_group_management.CONTENTVIEWGROUPS" text="default text" /></a></h2>
                    </div>

                    <div class="block">
                        <div class="content editable-widget">
                            <div class="top-box">
                                <form id="searchWithinProfileGroupForm" action="#" class="add-search-form advanced">
                                    <fieldset>
                                        <input type="text" id="searchWithinId1" />
                                        <input type="submit" id="searchWithinIdBtn1" value="Submit" onclick="searchWithinProfileGroups();return false;"/>
                                        <input id="resetBtn" type="button" style="cursor: pointer;" onclick="$('#searchWithinId1').val('Search Within...'); $('#searchWithinIdBtn1').click();" />
                                    </fieldset>
                                </form>
                            </div>
                            <div id="profile_group_page_container_div">
                                <jsp:include page="profile_group_table_fragment.jsp" />
                            </div>
                        </div>
                    </div>

                </div>
            </div>

<script type="text/javascript">

    $(document).ready(function() {
        $('#searchWithinId1').val('Search within...');
    });
</script>
