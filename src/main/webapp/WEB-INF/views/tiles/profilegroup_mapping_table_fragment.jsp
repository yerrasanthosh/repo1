<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
						<div class="scrollable-area vscrollable">
                            <div class="text">
                            	<form id="deleteMappingForm" class="checkboxResetForm">
                                <table class="table-data alt-table-data" id="profile_group_mappings_table">
                                    
                                    <thead>
                                    <tr>
                                    	<th class="td-select">
  		 									<input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="checkAllMappings" onclick="toggleAllCheckboxItems(this.checked,'profile_group_mappings_table');" />
         									<label for="checkAllMappings" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
  
                                    	</th>
                                        <th><spring:message code="com.adminui.profilegroup_mapping_table_fragment.UniqueSystemID" text="default text" /></th>
                                    	<th><spring:message code="com.adminui.profilegroup_mapping_table_fragment.GroupName" text="default text" /></th>
                                        <th><spring:message code="com.adminui.profilegroup_mapping_table_fragment.UserName" text="default text" /></th>
                                    </tr>
                                    </thead>
                                    <tbody id="profile_group_table_rows_body">
                                    <tr id="trNewRec" class="groupNameSelectDiv">
                                    	
	                                    	<td>&nbsp;</td>
                                            <td><input type="text" value="${profileGroupMapping.uniqueSystemId}" id="uniqueSystemId"/></td>
	                                    	<td>
	                                    		<select name="txtGroupNameN" id="txtGroupNameN">
	                                    		    <c:if test="${empty profileGroupList}">
	                                    		        <option></option>
	                                    		    </c:if>
                                    				<c:forEach var="profileGroup" items="${profileGroupList}">
                                        				<option id="${profileGroup.groupName}" value="${profileGroup.groupName}">${profileGroup.groupName}</option>
                                    				</c:forEach>
                                				</select>
	                                    	
	                                    	
	                                    	
	                                    	<td><input type="text" value="${profileGroupMapping.sapUser}" id="txtSAPUserN"/> </td>
											<input type="hidden" value="${pgPageNumber}" id="pgNumberH" />
											<input type="hidden" id="selectId"/>
											<input type="hidden" id="addRow"/>
                                    </tr>
                                	<c:if test="${not empty profileGroups}">    
                                    	<c:forEach var="profileGroup" items="${profileGroups}" varStatus="cntr">
                                    	
	                                        <tr>
	 											<td class="td-select">
             <input  id="chk-${profileGroup.mappingId}"  type="checkbox" class="ui-helper-hidden-accessible" name="profileMappings" value="${profileGroup.mappingId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
             <label for="chk-${profileGroup.mappingId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
	 										


	                              				 </td>
                                                <td style="width:40%">
                                                    <div id="systemId-${profileGroup.mappingId}-V" style="display:none" class="groupNameLinkDiv">
                                                        <a href="#row-${profileGroup.mappingId}" onclick="editContentAccess('${profileGroup.mappingId}')" ><span id="spnName-${profileGroup.mappingId}">${profileGroup.uniqueSystemId}</span></a>
                                                    </div>
                                                    <div style="display:none" id="systemId-${profileGroup.mappingId}-E">
                                                        <input type="text" value="${profileGroup.uniqueSystemId}" id="txtsystemIdE-${profileGroup.mappingId}" class="contenMappinguniqueSupplierId" />
                                                    </div>
                                                </td>
	                                        	<td style="width:40%">
													<div id="grpName-${profileGroup.mappingId}-V" style="display:none" class="groupNameLinkDiv">
	                                                	<a href="#row-${profileGroup.mappingId}" onclick="editContentAccess('${profileGroup.mappingId}')" ><span id="spnName-${profileGroup.mappingId}">${profileGroup.groupName}</span></a>
													</div>
													<div id="grpName-${profileGroup.mappingId}-E" class="groupNameSelectDiv">
													
	                                    		<select name="txtGroupNameE" id="txtGroupNameE-${profileGroup.mappingId}">
                                    				<c:forEach var="profileGrp" items="${profileGroupList}">
                                        				<option id="gname-${profileGrp.groupName}" <c:if test="${profileGrp.groupName == profileGroup.groupName}">selected</c:if> value="${profileGrp.groupName}">${profileGrp.groupName}</option>
                                    				</c:forEach>
                                				</select>
	                                    			                                                	
													</div>
	                                            </td>
	                                            <td style="width:50%">
													<div id="sapUser-${profileGroup.mappingId}-V">
	                                                	<a href="#row-${profileGroup.mappingId}" onclick="editContentAccess('${profileGroup.mappingId}')" >${profileGroup.sapUser}</a>
													</div>
													<div style="display:none" id="sapUser-${profileGroup.mappingId}-E">
														<input type="text" value="${profileGroup.sapUser}" id="txtSapUsrE-${profileGroup.mappingId}" class="contenMappingUser" />		                                                	
													</div>
	                                            </td>


	                                         </tr>
                                       
                                    	</c:forEach>
                                	</c:if>
                                    </tbody>
                                   
                                </table>
                                </form>
                            </div>
                        </div>
                        
                        <div class="bottom-data" id="profileGroupBottomData" style="display:none;">
                        	<c:if test="${not empty profileGroups}">
                                <div class="pager" id="profileGroupPager">
                                    <span id="profile_group_mapping_current_page_num"><spring:message code="com.adminui.profilegroup_mapping_table_fragment.Page" text="default text" /> ${pgPageNumber} <spring:message code="com.adminui.profilegroup_mapping_table_fragment.of" text="default text" /> <c:out value="${pgPagesAvailable}"/> </span>
                                    <ul>
                                    	<li><c:choose><c:when test="${pgPageNumber > 1}">
	                                    		<a id="profile_group_mapping_prev_page" class="btn-prev-active" onclick="getProfileGroupMappingsPage(${pgPageNumber-1},$('#profileGroupSearchTerm').val());return false;"></a>
											</c:when><c:otherwise>
												<a id="profile_group_mapping_prev_page" class="btn-prev" ></a>
											</c:otherwise></c:choose></li>
                                        <li><c:choose><c:when test="${pgPageNumber < pgPagesAvailable}">
	                                    		<a id="profile_group_mapping_next_page" class="btn-next" onclick="getProfileGroupMappingsPage(${pgPageNumber+1},$('#profileGroupSearchTerm').val());return false;"></a>
											</c:when><c:otherwise>
												<a id="profile_group_mapping_next_page" class="btn-next-inactive"></a>
											</c:otherwise></c:choose></li>
                                    </ul>
                                </div>
                            	<strong class="pages" id="profile_group_mapping_current_record_range"><spring:message code="com.adminui.profilegroup_mapping_table_fragment.ShowingRecords" text="default text" /> <c:out value="${(pgPageNumber-1)*pgRecordsPerPage+1}"/> - <c:out value="${(pgTotalRecords-(pgPageNumber*pgRecordsPerPage)>0)?pgPageNumber*pgRecordsPerPage:pgTotalRecords}"/> <spring:message code="com.adminui.profilegroup_mapping_table_fragment.of" text="default text" /> <c:out value="${pgTotalRecords}"/></strong>
                            </c:if>
                        </div>
                        <br/>
                        <div class="function">
	               	        <ul>
	                        	<li><a class="ico-remove" id="removeMappingBtn" onclick="removeContenAccessConfirm(); return false;"><span><em><spring:message code="com.adminui.profilegroup_mapping_table_fragment.REMOVE" text="default text" /></em></span></a></li>
	                      		<li><a class="btn-add-mapping" id="addMappingBtn" onclick="addRow();return false;"><span><em><spring:message code="com.adminui.profilegroup_mapping_table_fragment.ADD" text="default text" /></em></span></a></li>
	                      		<a href="#" style="display:none" id="btnCancelE" onclick="cancelEdit();" class="btn-cancel"><spring:message code="com.adminui.profilegroup_mapping_table_fragment.Cancel" text="default text" /></a>
	                      		<a href="#" style="display:none" id="btnCancelN" onclick="cancelAdd();" class="btn-cancel"><spring:message code="com.adminui.profilegroup_mapping_table_fragment.Cancel" text="default text" /></a>
	                      		<li><a class="ico-approve" id="saveMappingBtn" style="display:none" onclick="createContentAccessMapping();return false;"><span><em><spring:message code="com.adminui.profilegroup_mapping_table_fragment.SAVE" text="default text" /></em></span></a></li>
	                      		<li><a class="ico-approve" id="saveMappingBtnE" style="display:none" onclick="updateContentAccess(true);return false;"><span><em><spring:message code="com.adminui.profilegroup_mapping_table_fragment.SAVE" text="default text" /></em></span></a></li>
	                        </ul>
                        </div>
                        



