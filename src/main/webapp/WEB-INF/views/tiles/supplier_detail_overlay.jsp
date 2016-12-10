<div class="lightbox-section">
	<div class="lightbox lightbox-small" id="lightbox-delete-suppliers">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a id="lighboxDeleteDetailsCloseBtn" href="#" class="close">Close</a>
					<h2>Deletion Confirmation</h2>
				</div>
				<div class="content">
					<p>Please click "DELETE" to confirm that you would like to
						remove the selected item(s).</p>
				</div>
				<div class="btns-holder">
					<div class="btns-frame">
						<a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel">Cancel</a>
						<a id="lighboxDeleteDetailsBtn" href="#"
							class="btn btn-red .delete-btn"><span>DELETE</span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="lightbox" id="create-approver">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a href="#" class="close">Close</a>
					<h2>Create Approver</h2>
				</div>
				<form action="addApprover" class="form-create" id="createApprover"
					method="post">
					<fieldset>
						<h3>APPROVER SETTINGS</h3>
						<div class="area">
							<div class="row">
								<label for="first-name"><span>*</span>First Name:</label>
								<div id="first-name-div">
									<input type="text" name="firstName" id="first-name"
										onkeyup="if($('#first-name').val() != '')$('#first-name-div span').remove()" />
								</div>
							</div>
							<div class="row">
								<label for="last-name"><span>*</span>Last Name:</label>
								<div id="last-name-div">
									<input type="text" name="lastName" id="last-name"
										onkeyup="if($('#last-name').val() != '')$('#last-name-div span').remove()" />
								</div>
							</div>
							<div class="row">
								<label for="e-mail"><span>*</span>E-mail:</label>
								<div id="e-mail-div">
									<input type="text" name="username" id="e-mail"
										onkeyup="if($('#e-mail').val() != '')$('#e-mail-div span').remove()" />
								</div>
							</div>
							<input type="hidden" name="supplierId"
								value="${param['supplierId']}" /> <input type="hidden"
								name="userId" value="${userId}" />
						</div>
						<div class="btns-holder">
							<a href="#" class="btn-cancel">Cancel</a>
							<!-- <input type="submit" value="Save" name="Save">-->
							<a class="btn-create" onclick="createApprover();"><span>Save</span></a>
							<p>
								<span class="required">* </span>Required Field
							</p>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

	<div class="lightbox" id="create-supplier-user">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a href="#" class="close">Close</a>
					<h2>
						<span id="suppAdminTitle">Create Supplier User</span>
					</h2>
				</div>
				<form action="addsupplieruser" class="form-create"
					id="createSupplierUser" method="post">
					<fieldset>
						<h3>SUPPLIER ADMIN</h3>
						<div class="area">
							<div class="row">
								<label for="first-name"><span>*</span>First Name:</label>
								<div id="sup-first-name-div">
									<input type="text" name="firstName" id="sup-first-name"
										onkeyup="if($('#sup-first-name').val() != '')$('#sup-first-name-div span').remove()" />
								</div>
							</div>
							<div class="row">
								<label for="last-name"><span>*</span>Last Name:</label>
								<div id="sup-last-name-div">
									<input type="text" name="lastName" id="sup-last-name"
										onkeyup="if($('#sup-last-name').val() != '')$('#sup-last-name-div span').remove()" />
								</div>
							</div>
							<div class="row">
								<label for="e-mail"><span>*</span>E-mail:</label>
								<div id="sup-e-mail-div">
									<input type="text" name="username" id="sup-e-mail"
										onkeyup="if($('#sup-e-mail').val() != '')$('#sup-e-mail-div span').remove()" />
								</div>
							</div>
							<div class="row">
								<label for="pass">Password:</label>
								<div id="pass-div">
									<input type="text" name="pass" id="pass"
										onkeyup="if($('#pass').val() != '')$('#createSupUserReplyDiv').html('')" />
								</div>
							</div>
							<div style="width: 325px;" class="row">
								<label for="e-mail">Send Invitation Email:</label>
								<div>
									<input type="checkbox" onchange="toggleCKEditor();"
										name="sendNotification" id="sendNotification" /> <label
										id="notiLabel" style="margin: -21px -2px;"
										for="sendNotification"></label>

								</div>
							</div>
							<div class="row">
								<textarea style="display: none;" name="notificationBody"
									id="notificationBody"></textarea>
							</div>
							</br>
							<div id="sendEmailDiv" style="display: none;" class="function">
								<ul>
									<li><a class="ico-approve" style="margin-right: -10px"
										onclick="$('#saveBtn').click();"><span><em>Send
													Email</em></span></a></li>
								</ul>
							</div>
							<input type="hidden" name="supplierid"
								value="${param['supplierId']}" /> <input type="hidden"
								name="userId" value="${userId}" id="userId" /> <input
								type="hidden" name="parentCompanyName"
								value="${parentCompanyName}" id="parentCompanyName" />

						</div>
						<p>
							<span id="createSupUserReplyDiv"
								style="font-style: italic; color: #ED1C24; height: 10px"></span>
						</p>
						<div class="btns-holder">
							<a href="#" class="btn-cancel">Cancel</a>
							<!-- <input type="submit" value="Save" name="Save">-->
							<a id="saveBtn" class="btn-create"
								onclick="createSupplierUser();"><span>Save</span></a>
							<p>
								<span class="required">* </span>Required Field
							</p>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<div class="lightbox" id="add-approver">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a href="#" class="close">Close</a>
					<h2>Add Approvers</h2>
				</div>
				<form action="addApprovers" id="addApprover-form" method="post"
					class="checkboxResetForm">
					<jsp:include page="supplier_non_approvers_fragment.jsp" />
				</form>
			</div>
		</div>
	</div>
	<%--
    <div class="lightbox" id="lightbox-inline-supplier-logo">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close">Close</a>
                    <h2 id="catalogPopupTitle2">Upload Image</h2>
                </div>
                <form action="uploadSupplierLogo" id="supplier-logo-form" method="post" enctype="multipart/form-data">
                    <fieldset>
                        <div class="area">
                            <div class="row" id="supplier-logo-file-row">
                                <label for="supplier-logo-file"><span id="edit-catalog-file-span"></span>Supplier Logo File:</label>
                                <input type="file" name="logoFile" id="supplier-logo-file"/>
                            </div>

                            <div class="btns-holder">
                                <a href="#" class="btn-cancel">Cancel</a>
                                <input type="submit" name="submitButton" value="Save" id="uploadSupplierImageBtn"/>
                            </div>

                            <input type="hidden" name="supplierId" id="supplier-id" value="${param["supplierId"]}" maxlength="10"/>
                        </div>
                    </fieldset>
                </form>
                <br />
            </div>
        </div>
    </div>
 --%>
</div>
<div class="lightbox-section">
	<div class="lightbox lightbox-small" id="approve-non-catalog-supplier">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a href="#" id="closeCatalogSupplierBtn" class="close">Close</a>
					<h2>Non Catalog Supplier</h2>
				</div>
				<div class="content">
					<p>There may be catalogs associated with this supplier. Are you
						sure you want to make this change?</p>
				</div>
				<div class="btns-holder">
					<div class="btns-frame">
						<a href="#" id="noCatalogSupplierBtn" class="btn-cancel">No</a> <a
							href="#" id="yesCatalogSupplierBtn" class="btn btn-green"><span>Yes</span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="lightbox-section">
	<div class="lightbox" id="add-profile">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a href="#" class="close">Close</a>
					<h2>Add Content Views</h2>
				</div>
				<form action="#" id="add-profiles-form" class="checkboxResetForm">
					<fieldset>
						<div class="content lightbox-content editable-widget"
							id="addUserProfilesSection">
							<table class="table-data">
								<thead>
									<tr>
										<th class="td-select"><input type="checkbox"
											class="check-allbox" name="check-lightbox-all"
											id="check-lightbox-1" /><label for="check-lightbox-1"></label></th>
										<th class="a-left">Name</th>
										<th class="a-left">Description</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="profile" items="${profilesForCompany.pageList}"
										varStatus="cntr1">
										<tr>
											<td class="td-select"><input type="checkbox"
												class="target-chbox" name="assignedProfiles"
												id="profile-${profile.profileId}"
												value="${profile.profileId}" /><label
												for="profile-${profile.profileId}"></label></td>
											<td class="a-left td-description">
												<div class="field">
													<span><a href="#">${profile.profileName}</a></span> <input
														type="text" value="${profile.profileName}" />
												</div>
											</td>
											<td class="a-left">${profile.profileDesc}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<input type="hidden" name="supplierId" value="${supplierId}"
								id="supplierId" />
							<div class="bottom-data">
								<div class="pager">
									<span>Page ${profilesForCompany.page+1} of
										${profilesForCompany.pageCount}</span>
									<ul>
										<li><a href="javascript:void(0)" class="btn-prev"
											onclick="navigateProfilePage('${prevUserProfilesPage}', '${supplierId}');"></a></li>
										<li><a href="javascript:void(0)" class="btn-next"
											onclick="navigateProfilePage('${nextUserProfilesPage}', '${supplierId}');"></a></li>
									</ul>
								</div>
								<strong class="pages">Total Records:
									${profilesForCompany.firstElementOnPage+((profilesForCompany.lastElementOnPage ne -1)? 1: 0)}-${profilesForCompany.lastElementOnPage+1}
									of ${profilesForCompany.nrOfElements}</strong>
							</div>
						</div>
						<div class="btns-holder">
							<input type="hidden" name="userId" value="${supplierId}" /> <a
								href="#" class="btn-cancel">Cancel</a>
							<%--<input type="submit" value="Save" /> --%>
							<a class="btn-create close" onclick="addSupplierContentView();"><span>Save</span></a>
							<%--<p><span class="required">* </span>Required Field</p>--%>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="lightbox-section">
	<div class="lightbox" id="create-profile">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a href="#" class="close" onclick="clearProfileFormErrors()">Close</a>
					<h2>Create Content View</h2>
				</div>
				<form action="createSupplierContentViews" class="form-create"
					id="createSupplierContentViewId" method="post">
					<fieldset>
						<h3>CONTENT VIEW SETTINGS</h3>
						<div class="area">
							<div class="row" id="theProfileNameId">
								<label for="profile-name"><span>*</span>Content View
									Name:</label> <input type="text" name="profileName" id="profile-name"
									class="required" />

							</div>
							<div class="row" id="theProfileDescId">
								<label for="profile-description">Content View
									Description:</label>
								<textarea id="profile-description" name="profileDesc" cols="30"
									rows="10" class="textarea"></textarea>
							</div>
							<!-- <div class="row">
                                <label for="rating">Allow Product Rating:</label>
                                <input type="checkbox"  name="rating" id="rating" />
                            </div> -->
						</div>
						<div class="btns-holder">
							<a href="#" class="btn-cancel" onclick="clearProfileFormErrors()">Cancel</a>
							<!-- <a class="btn-create close"  onclick="createUserProfile();"><span>Save</span></a> -->
							<input type="submit" value="Save"
								onclick="return validateSupplierContentView();" name="Save" />
							<p>
								<span class="required">* </span>Required Field
							</p>
							<p>
								<span id="createProfileReplyDiv"
									style="font-style: italic; color: #ED1C24; height: 10px"></span>
							</p>
							<input type="hidden" name="userId" value="${param['userId']}" />
						</div>

					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="lightbox-section">
	<div class="lightbox lightbox-small" id="lightbox-delete-profiles">
		<div class="holder">
			<div class="frame">
				<div class="title">
					<a id="lighboxDeleteDetailsCloseBtn" href="#" class="close">Close</a>
					<h2>Deletion Confirmation</h2>
				</div>
				<div class="content">
					<p>Please click "DELETE" to confirm that you would like to
						remove the selected item(s).</p>
					<%--<p><strong>[lorem ipsum dolor sit amet]</strong></p>--%>
				</div>
				<div class="btns-holder">
					<div class="btns-frame">
						<a id="lighboxDeleteDetailsCancelLink" href="#" class="btn-cancel">Cancel</a>
						<a id="lighboxDeleteDetailsBtn" href="#"
							class="btn btn-red .delete-btn"><span>DELETE</span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>