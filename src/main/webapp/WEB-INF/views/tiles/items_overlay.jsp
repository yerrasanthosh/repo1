<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="lightbox-section">
    <div class="lightbox" id="lightbox-item-image">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.items_overlay.Close" text="default text" /></a>
                    <h2 id="uploadImagePopupTitle"><spring:message code="com.adminui.items_overlay.UploadImage" text="default text" /></h2>
                </div>
                <form action="upload-image" class="form-upload" id="item-image-form" method="post" enctype="multipart/form-data">
                  <input type="hidden" name="catalogId" id="catalog-id-image" value="" maxlength="10"/>
                  <input type="hidden" name="catalogItemId" id="catalog-item-id-image" value="" maxlength="10"/>
                    <fieldset>
                        <div class="row">
                            <label><spring:message code="com.adminui.items_overlay.ImageFile" text="default text" /></label>
                            <div id="imageFileDiv">
                                <input type="file" value="" id="imageFile" name="imageFile" />
                            </div>
                        </div>

                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.items_overlay.Cancel" text="default text" /></a>
                            <input type="submit" name="submitButton" value="Upload" id="uploadImageButton"/>
                        </div>



                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="lightbox-item-attachment">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.items_overlay.Close" text="default text" /></a>
                    <h2 id="uploadAttachmentPopupTitle"><spring:message code="com.adminui.items_overlay.UploadAttachment" text="default text" /></h2>
                </div>
                <form action="upload-attachment" class="form-upload" id="item-attachment-form" method="post" enctype="multipart/form-data">
                    <fieldset>

                        <div class="row">
                            <label><spring:message code="com.adminui.items_overlay.AttachmentFile" text="default text" /></label>
                            <input type="file" value="" name="attachmentFile" />
                        </div>

                        <div class="btns-holder">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.items_overlay.Cancel" text="default text" /></a>
                            <input type="submit" name="submitButton" value="Upload" id="uploadAttachmentButton"/>
                        </div>
                        <input type="hidden" name="catalogId" id="catalog-id" value="" maxlength="10"/>
                        <input type="hidden" name="catalogItemId" id="catalog-item-id" value="" maxlength="10"/>
                        <input type="hidden" name="unitId" id="unit-id" value="" maxlength="10"/>

                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="add-attachment" style="height:508px;">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.items_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.items_overlay.AddAttachments" text="default text" /></h2>
                </div>
                <form action="#" id="add-attachment-form-id">
                    <fieldset>
                        <div class="lightbox-content">
                            <div style="margin-bottom:15px;">
                                <input type="text" value="Search Catalogs" />
                                <input type="buttom" value="Submit" id="searchIcon"/>
                                <div style="float:right;">
                                    <label><spring:message code="com.adminui.items_overlay.SortBy" text="default text" /> </label>
                                    <select style="width:50px;">
                                        <option><spring:message code="com.adminui.items_overlay.Name" text="default text" /></option>
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
                                                        <label><spring:message code="com.adminui.items_overlay.Title" text="default text" /> </label><span><a href="#">Manual for Widget</a></span><br />
                                                        <label>Name: </label>manual_for_widget.pdf
                                                    </div>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div style="margin-top:10px;">
                                <label><spring:message code="com.adminui.items_overlay.FileName" text="default text" /> </label>
                                <input type="text" style="width:530px;"/>
                                <select>
                                    <option><spring:message code="com.adminui.items_overlay.AllFiles" text="default text" /></option>
                                </select>
                            </div>
                        </div>
                        <div class="btns-holder" style="margin-top:35px;">
                            <a href="#" class="btn-cancel"><spring:message code="com.adminui.items_overlay.Cancel" text="default text" /></a>
                            <input type="submit" value="Save" />
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="lightbox-section">
    <div class="lightbox" id="add-custom-field">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close"><spring:message code="com.adminui.items_overlay.Close" text="default text" /></a>
                    <h2><spring:message code="com.adminui.items_overlay.AddCustomFields" text="default text" /></h2>
                </div>
                <form action="#" id="add-custom-field-form-id" class="checkboxResetForm">
                </form>
            </div>
        </div>
    </div>
</div>