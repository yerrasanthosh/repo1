<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" session="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
						<div class="">
                            <div class="text">
                            	<form id="systemDefinitionForm">
                                <table class="table-data alt-table-data" id="system_definitions_table">
                                    
                                    <thead>
                                    <tr>
                                    	<th class="td-select">
  		 									<input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="checkAllSystemDefs" onclick="toggleAllCheckboxItems(this.checked,'system_definitions_table');" />
         									<label for="checkAllSystemDefs" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
  
                                    	</th>
                                    	<th><spring:message code="com.adminui.system_definition_table_fragment.SystemName" text="default text" /></th>
                                        <th><spring:message code="com.adminui.system_definition_table_fragment.CommunicationMethod" text="default text" /></th>
                                        <th><spring:message code="com.adminui.system_definition_table_fragment.UniqueSystemID" text="default text" /></th>
                                    </tr>
                                    </thead>
                                    <tbody id="system_definitions_table_rows_body">
                                    <tr id="trNewRec" class="sysemDefinitionsSelectDiv">
                                    	
	                                    	<td>&nbsp;</td>
	                                    	<td><input type="text" name="systemName" value="${systemDefinition.systemName}" id="txtSystemNameN"/> </td>
	                                    	<td>
	                                    		<select name="txtCommunicationMethodN" id="txtCommunicationMethodN">
	                                        		<option id="OCI" value="OCI">OCI</option>
	                                        		<option id="CXML" value="CXML">CXML</option>
                                				</select>
	                                    	</td>
	                                    	<td>
									         	<input type="hidden" value="${systemDefinition.uniqueSystemId}" id="sysid-${systemDefinition.uniqueSystemId}-N" />
									            ${systemDefinition.uniqueSystemId}
									         </td>
										                                    	
											<input type="hidden" id="systemId"/>
											<input type="hidden" id="addRow"/>
                                    </tr>
                                	<c:if test="${not empty systemDefinitions}">    
                                    	<c:forEach var="systemDefinition" items="${systemDefinitions}" varStatus="cntr">
                                    	
	                                        <tr>
	 											<td class="td-select">
									             <input  id="chk-${systemDefinition.systemId}"  type="checkbox" class="ui-helper-hidden-accessible" name="systemIds" value="${systemDefinition.systemId}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
									             <label for="chk-${systemDefinition.systemId}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
	                              				 </td>
	                                                                    
	                                        	<td style="width:50%">
													<div id="sysName-${systemDefinition.systemId}-V">
	                                                	<a href="#row-${systemDefinition.systemId}" onclick="editContentAccess('${systemDefinition.systemId}')" >${systemDefinition.systemName}</a>
													</div>
													<div style="display:none" id="sysName-${systemDefinition.systemId}-E">
														<input type="text" value="${systemDefinition.systemName}" id="txtSystemNameE-${systemDefinition.systemId}" class="contenMappingUser" />		                                                	
													</div>
	                                            </td>

	                                            <td style="width:40%">
													<div id="commMethod-${systemDefinition.systemId}-V">
	                                                	<a href="#row-${systemDefinition.systemId}" onclick="editContentAccess('${systemDefinition.systemId}')" >${systemDefinition.communicationMethod}</a>
													</div>
													
	                                    			<div id="commMethod-${systemDefinition.systemId}-E" class="communicationMethodSelectDiv">
														<select name="communicationMethod" id="txtCommunicationMethodE-${systemDefinition.systemId}">
		                                        			<option id="cmethod-oci" <c:if test="${systemDefinition.communicationMethod == 'OCI'}">selected</c:if> value="OCI"><spring:message code="com.adminui.system_definition_table_fragment.OCI" text="default text" /></option>
		                                        			<option id="cmethod-cxml" <c:if test="${systemDefinition.communicationMethod == 'CXML'}">selected</c:if> value="CXML"><spring:message code="com.adminui.system_definition_table_fragment.CXML" text="default text" /></option>
		                                				</select>
	                                				</div>
	                                            </td>
									            
	                                            <td style="width:50%">
													${systemDefinition.uniqueSystemId}
													<input type="hidden" value="${systemDefinition.uniqueSystemId}" id="uniqueSysIdE-${systemDefinition.systemId}" />
	                                            </td>
	                                         </tr>
                                       
                                    	</c:forEach>
                                	</c:if>
                                    </tbody>
                                   
                                </table>
                                </form>
                            </div>
                        </div>
                        
                        <br/><br/><br/>
                        <div class="function">
	               	        <ul>
	                        	<li><a class="ico-remove" id="removeMappingBtn" onclick="removeContenAccessConfirm(); return false;"><span><em><spring:message code="com.adminui.system_definition_table_fragment.REMOVE" text="default text" /></em></span></a></li>
	                      		<li><a class="btn-add-mapping" id="addMappingBtn" onclick="addRow();return false;"><span><em><spring:message code="com.adminui.system_definition_table_fragment.ADD" text="default text" /></em></span></a></li>
	                      		<a style="display:none" id="btnCancelE" onclick="cancelEdit();" class="btn-cancel"><spring:message code="com.adminui.system_definition_table_fragment.Cancel" text="default text" /></a>
	                      		<a style="display:none" id="btnCancelN" onclick="cancelAdd();" class="btn-cancel"><spring:message code="com.adminui.system_definition_table_fragment.Cancel" text="default text" /></a>
	                      		<li><a class="ico-approve" id="saveMappingBtn" style="display:none" onclick="addSystemDefinition();return false;"><span><em><spring:message code="com.adminui.system_definition_table_fragment.SAVE" text="default text" /></em></span></a></li>
	                      		<li><a class="ico-approve" id="saveMappingBtnE" style="display:none" onclick="updateSystemDefinition();return false;"><span><em><spring:message code="com.adminui.system_definition_table_fragment.SAVE" text="default text" /></em></span></a></li>
	                        </ul>
                        </div>
                        



