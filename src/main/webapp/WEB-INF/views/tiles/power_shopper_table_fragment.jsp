<%@ page contentType="text/html;charset=UTF-8" language="java"
		 autoFlush="true" session="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>


<div class="scrollable-area vscrollable">
	<div class="text">
		<form id="deletePowerShopperForm">
			<table class="table-data alt-table-data" id="power_shopper_table">

				<thead>
				<tr>
					<th class="td-select">
						<input type="checkbox" class="ui-helper-hidden-accessible" name="check-all3" id="checkAllPowerShoppers" onclick="toggleAllCheckboxItems(this.checked,'power_shopper_table');" />
						<label for="checkAllPowerShoppers" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
					</th>
					<th>USER NAME</th>
					<th>FIRST NAME</th>
					<th>LAST NAME</th>
					<th>EMAIL</th>
					<th>SHARED LIST GROUP</th>
				</tr>
				</thead>
				<tbody id="power_shopper_table_rows_body">
				<tr id="trNewRecPowerShopper" style="display:none;">

					<td style="width: 2%">&nbsp;</td>
					<td style="width: 15%"><input style="width:100px" type="text"  id="newShopperUserName"/> </td>
					<td style="width: 13%"><input style="width:100px" type="text"  id="newShopperFirstName"/> </td>
					<td style="width: 15%"><input style="width:100px" type="text"  id="newShopperLastName"/> </td>
					<td style="width: 15%"><input style="width:130px" type="text" onblur="validateEmail(this)" id="newShopperEmail"/> </td>
					<td style="width: 40%">
						<select id="select-state" name="state[]" multiple class="demo-default" style="width:100%" placeholder="Select content group...">
							<c:if test="${empty profileGroupList}">
								<option></option>
							</c:if>
							<c:forEach var="profileGroup" items="${profileGroupList}">
								<option id="${profileGroup.groupName}" value="${profileGroup.groupName}-${profileGroup.token}">${profileGroup.groupName}</option>
							</c:forEach>
						</select>

					</td>
					<input type="hidden" value="${pgPageNumber}" id="pgNumberHPowerShopper" />
					<input type="hidden" id="selectIdPowerShopper"/>
					<input type="hidden" id="addRowPowerShopper"/>
				</tr>


				<c:if test="${not empty powerShoppers}">
					<c:forEach var="powerShopper" items="${powerShoppers}"
							   varStatus="cntr">

						<tr>
							<td class="td-select">
								<input  id="chk-${powerShopper.id}"  type="checkbox" class="ui-helper-hidden-accessible" name="powerShopper" value="${powerShopper.id}" onclick="if(this.checked){$(this).next().addClass('ui-state-active');$(this).closest('tr').addClass('active-tr');}else {$(this).next().removeClass('ui-state-active');$(this).closest('tr').removeClass('active-tr');}"/>
								<label for="chk-${powerShopper.id}" aria-pressed="false" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"></label>
							</td>
							<td style="width: 15%">
								<div id="psUserName-${powerShopper.id}-V"
									 class="contenMappingUser">
									<a href="#row-${powerShopper.id}" onclick="editPowerShopperFn('${powerShopper.id}')	" >
										<span id="spnName-${powerShopper.id}">${powerShopper.username}</span>
									</a>

								</div>
								<div style="display:none" id="psUserName-${powerShopper.id}-E">
										${powerShopper.username}
								</div>
							</td>
							<td style="width: 15%">
								<div id="psFirstName-${powerShopper.id}-V" class="contenMappingUser">
										${powerShopper.firstName}
								</div>
								<div style="display:none" id="psFirstName-${powerShopper.id}-E">
									<input type="text" style="width:100px" value="${powerShopper.firstName}" id="textPSFirstNameE-${powerShopper.id}" class="powerShopperFirstName" />
								</div>

							</td>
							<td style="width: 15%">
								<div id="psLastName-${powerShopper.id}-V"
									 class="contenMappingUser">${powerShopper.lastName}
								</div>
								<div style="display:none" id="psLastName-${powerShopper.id}-E">
									<input type="text" style="width:100px" value="${powerShopper.lastName}"  id="textPSLastNameE-${powerShopper.id}" class="powerShopperLastName" />
								</div>

							</td>

							<td style="width: 15%">
								<div id="psEmail-${powerShopper.id}-V"
									 class="contenMappingUser">${powerShopper.email}</div>

								<div style="display:none" id="psEmail-${powerShopper.id}-E">
									<input type="text" style="width:130px" value="${powerShopper.email}" onblur="validateEmail(this)" id="textEmailE-${powerShopper.id}" class="powerShopperEmail" />
								</div>

							</td>
							<td style="width: 40%" >
								<c:set var="contentGroups" value=" " />
								<c:set var="separater" value="," />
								<c:set var="more" value="..." />

								<c:forEach varStatus="jj" var="sharedContentGroup" items="${powerShopper.sharedContentGroups}" >
									<c:if test="${jj.count == 1}">
										<c:set var="contentGroups" value="${ sharedContentGroup}" />
									</c:if>
									<c:if test="${jj.count != 1}">
										<c:set var="contentGroups" value="${contentGroups}${separater} ${ sharedContentGroup}" />
									</c:if>
								</c:forEach>

								<div id="psContentGroup-${powerShopper.id}-V">
										<span style="cursor: pointer;" title="${contentGroups}">
											
											 <c:choose>
												 <c:when test="${fn:length(contentGroups)<41}">
													 ${contentGroups}
												 </c:when>

												 <c:otherwise>
													 ${fn:substring(contentGroups, 1, 40)}${more}
												 </c:otherwise>
											 </c:choose>
										</span>
								</div>

								<div style="display:none"  id="psContentGroup-${powerShopper.id}-E">
									<select id="select-state-${powerShopper.id}" name="state[]" multiple class="demo-default" style="width:100%" placeholder="Select content group...">
										<c:if test="${empty profileGroupList}">
											<option></option>
										</c:if>
										<c:forEach var="profileGroup" items="${profileGroupList}">
											<option id="${profileGroup.groupName}"
													value="${profileGroup.groupName}-${profileGroup.token}"
													<c:forEach  var="sharedContentGroup" items="${powerShopper.sharedContentGroups}" >
														<c:if test="${sharedContentGroup eq profileGroup.groupName}">
															selected
														</c:if>
													</c:forEach>
													>
													${profileGroup.groupName}
											</option>
										</c:forEach>
									</select>
								</div>


							</td>


						</tr>

					</c:forEach>
				</c:if>
				<%-- <c:if test="${empty powerShoppers}">
                <tr class="noDataYet">
                <td colspan="10" style="padding: 15px">You do not have any Power-Shoppers configured yet</td>
                </tr>
                </c:if> --%>
				</tbody>

			</table>
		</form>
	</div>
</div>
<div class="bottom-data power-shopper" id="powerShopperBottomData">


	<c:if test="${not empty powerShoppers}">
		<div class="pager" id="uomMappingPager">
			<span id="uom_mapping_current_page_num">Page ${pgPageNumber}
				of <c:out value="${pgPagesAvailable}" />
			</span>
			<ul>
				<li><c:choose>
					<c:when test="${pgPageNumber > 1}">
						<a id="uom_mapping_prev_page" class="btn-prev-active"
						   onclick="getPowerShopperPage(${pgPageNumber-1},$('#searchboxShopper2').val());return false;"></a>
					</c:when>
					<c:otherwise>
						<a id="uom_mapping_prev_page" class="btn-prev"></a>
					</c:otherwise>
				</c:choose></li>
				<li><c:choose>
					<c:when test="${pgPageNumber < pgPagesAvailable}">
						<a id="uom_mapping_next_page" class="btn-next"
						   onclick="getPowerShopperPage(${pgPageNumber+1},$('#searchboxShopper2').val());return false;"></a>
					</c:when>
					<c:otherwise>
						<a id="uom_mapping_next_page" class="btn-next-inactive"></a>
					</c:otherwise>
				</c:choose></li>
			</ul>
		</div>
		<strong class="pages" id="profile_group_mapping_current_record_range">Showing
			Records: <c:out value="${(pgPageNumber-1)*pgRecordsPerPage+1}" /> -
			<c:out
					value="${(pgTotalRecords-(pgPageNumber*pgRecordsPerPage)>0)?pgPageNumber*pgRecordsPerPage:pgTotalRecords}" />
			of <c:out value="${pgTotalRecords}" />
		</strong>
	</c:if>
</div>
<br />


<div class="function">
	<ul>
		<li><a class="ico-remove" id="removePowerShopperBtn" onclick="removePowerShopperConfirm(); return false;"><span><em>REMOVE</em></span></a></li>
		<li><a class="btn-add-mapping" id="addPowerShopperBtn" onclick="addRowForPowerShopper();return false;"><span><em>ADD</em></span></a></li>
		<a href="#" style="display:none" id="btnCancelEPowerShopper" onclick="cancelEditShopper();" class="btn-cancel">Cancel</a>
		<a href="#" style="display:none" id="btnCancelNPowerShopper" onclick="cancelAddPowerShopper();" class="btn-cancel">Cancel</a>
		<li><a class="ico-approve" id="savePowerShopperBtn" style="display:none" onclick="savePowerShopper();return false;"><span><em>SAVE</em></span></a></li>
		<li><a class="ico-approve" id="savePowerShopperBtnE" style="display:none" onclick="updatePowerShopper(true);return false;"><span><em>SAVE</em></span></a></li>
	</ul>
</div>


			
