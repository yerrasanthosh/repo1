<%@ page import="com.vroozi.customerui.acl.model.Permission" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="aclManager" scope="request" class="com.vroozi.customerui.acl.DisplayAccessControl"/>

<script type="text/javascript">
    var canEditCatalogItem = <%= aclManager.allow(request, Permission.EDIT_ITEM)? "true" : "false" %>
    var associateItemCustomFieldURL = '<c:url value="/catalogs/" />';
</script>


<script type="text/template" id="general-form-template">
  <form class="settings-form" action="#" id="item-details-form">
		<input type="hidden" id="txtReadOnly" value="{{readOnly}}"/>
      <div class="text-fields">
          <div class="row">
              <label><span class="required">*</span>Item Type:</label>

              <div class="area-col">
                  <input id="s-physical-product" name="itemType" type="radio" class="required" value="" {[if(itemType == "") { ]} {{ "checked" }}{[};]} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <span class="label-suffix">Physical Product</span>
                  <input id="s-service" name="itemType" type="radio" class="required" value="1" {[if(itemType != "") { ]} {{ "checked" }}{[};]} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <span class="label-suffix">Service</span>
              </div>
          </div>

          <div class="row ">
              <label for="s-short-description"><span class="required">*</span>Short Description:</label>
              <div class="area-col">
                  <input id="s-short-description" name="shortDescription" type="text"  class="required" value="{{ shortDescription }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row" >
              <label for="s-category"><span class="required">*</span>Category:</label>

              <!-- <div class="area-col">
                  <input id="s-category" type="text" class="required"/>
              </div> -->
               <div class="area-col">
                 <input id="s-category" name="newItemMatgroup" value="{{newItemMatgroup}}" type="text" class="required" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>

          </div>
          <div class="row">
              <label for="s-currency"><span class="required">*</span>Currency:</label>
              <div id="select-currency-div" class="area-col">
                  <select style="width:329px;height:25px;" id="s-currency" name ="currency" class="required" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}>
                  </select>
                  <!--script>
                      var comboCurrency = new dhtmlXCombo("s-currency");
                      comboCurrency.enableFilteringMode(true);
                      comboCurrency.DOMelem.style.border = "1px solid #C1C1C1";
                      comboCurrency.DOMelem.style.width = "232px";
                      comboCurrency.DOMelem.style.margin = "0 13px 0 0";
                      comboCurrency.DOMelem.style.height = "23px";
                      comboCurrency.DOMelem_button.src = "res/css/imgs/combo_select_dhx_terrace.gif";
                      comboCurrency.DOMelem_button.style.height = "23px";
                      comboCurrency.DOMelem_button.style.width = "15px";
                      comboCurrency.DOMelem_input.style.position = "absolute";
                      comboCurrency.DOMelem_input.style.border = "none";
                      comboCurrency.DOMlist.style.width = "232px";
                      comboCurrency.DOMlist.style.height = "25px";
                      comboCurrency.DOMlist.style.border = "1px solid #C1C1C1";
                  </script-->
              </div>
          </div>
          <div class="row">
              <label for="s-price"><span class="required">*</span>Price:</label>

              <div class="area-col">
                  <input id="s-price" name="price" type="text"  value="{{ price }}"  {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>&nbsp;
                  <%--<a class="link" id="link-price" href="#pricing-section">Tiered Pricing</a>--%>
              </div>
          </div>
          <div class="row">
              <label for="s-unit-of-measure"><span class="required">*</span>Unit of Measure:</label>

              <div class="area-col">
                  <input id="s-unit-of-measure" class="text" name ="unitOfMeasure" type="text" class="required" value="{{ unitOfMeasure }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-vendor-part-number"><span class="required">*</span>Vendor Part Number:</label>

              <div class="area-col">
                  <input id="s-vendor-part-number" class="text" type="text" name="vendorPartNumber" class="required" value="{{ vendorPartNumber }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-vendor-name">Supplier:</label>

              <div id="select-supplier-div" class="area-col">
                  <input id="s-vendor-name" readonly="readonly" class="text" type="text" name="supplier" value="{{ supplier }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-manufacturer-name">Manufacturer Name:</label>

              <div class="area-col">
                  <input id="s-manufacturer-name" name="manufactName" class="text" type="text" value="{{ manufactName }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-manufacturer-part-number">Manufacturer Part Number:</label>

              <div class="area-col">
                  <input id="s-manufacturer-part-number" name="manufactPart" class="text" type="text" value="{{ manufactPart }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-item-url">Item URL:</label>
              <div class="area-col">
                  <input id="s-item-url" name="itemUrl" class="text" type="text" value="{{ itemUrl }}" style="width: 438px;" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-contract">Contract:</label>

              <div class="area-col">
                  <input id="s-contract" name="contract" class="text" type="text" value="{{ contract }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-contract-line-item">Contract Line Item:</label>

              <div class="area-col">
                  <input id="s-contract-line-item" name="contractLineItem" class="text" type="text" value="{{ contractLineItem }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
		  <div class="row">
              <label for="s-model-no-line-item">Model No.:</label>

              <div class="area-col">
                  <input id="s-model-no-line-item" name="modelno" class="text" type="text" value="{{ modelno }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
		  <div class="row">
              <label for="s-brand-name-line-item">Brand Name:</label>

              <div class="area-col">
                  <input id="s-brand-name-line-item" name="brandName" class="text" type="text" value="{{ brandName }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-bundle-name-line-item">Bundle Number:</label>

              <div class="area-col">
                  <input id="s-bundle-name-line-item" name="itemBundleNo" class="text" type="text" value="{{ itemBundleNo }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>

          <div class="row">
              <label for="s-bundle-quantity-line-item">Bundle Quantity:</label>

              <div class="area-col">
                  <input id="s-bundle-quantity-line-item" name="bundleQuantity" class="text" type="text" value="{{ bundleQuantity }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
      </div>
      <div class="check-fields">
          <strong class="label">Configurable Item:</strong>

          <div class="area-col role">
              <div class="item">
                  <input id="cf-configurable-item" name="itemConfig" class="master" type="checkbox" {{ itemConfig=="true" ? "checked='checked'" : "" }} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <label for="cf-configurable-item"></label>
              </div>
          </div>
      </div>
      <div class="check-fields">
          <strong class="label">Item Policy:</strong>

          <div class="area-col role">
              <div class="item">
                  <input id="cf-policy-item" name="itemPolicy" class="master" type="checkbox" {{ itemPolicy ? "checked='checked'" : "" }} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <label for="cf-policy-item"></label>
              </div>
          </div>
      </div>

      <div class="check-fields">
          <strong class="label">Item Suppress:</strong>

          <div class="area-col role">
              <div class="item">
                  <input id="cf-item-suppress" name="itemSuppress" class="master" type="checkbox" {{ itemSuppress ? "checked='checked'" : "" }} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <label for="cf-item-suppress"></label>
              </div>
          </div>
      </div>
  </form>
</script>

<script type="text/template" id="long-description-form-template">
  <textarea cols="80" id="longDescription" name="longDescription" rows="10" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}>{{ longDescription }}</textarea>
</script>
<script type="text/template" id="images-form-template">
  <div class="btn-holder" id="upload-image" >
      <!--a href="#load-image" class="btn-load-image"><span><em>UPLOAD IMAGE</em></span></a-->
      <a href="javascript:void(0);" id="uplImageLink" onclick="openUploadDiv('lightbox-item-image');"><span><em>UPLOAD IMAGE</em></span></a>
  </div>
  <div class="btn-holder" id="add-new-image">
      <!--a href="#load-image" class="btn-load-image"><span><em>UPLOAD IMAGE</em></span></a-->
      <!-- <a href="#lightbox-item-image" class="open-popup"><span><em>ADD IMAGE</em></span></a> -->
  </div>
  <!-- <div class="scrollable-area anyscrollable" style="height:100px;"> -->
  <div>
      <table class="table-data">
          <thead>
              <tr>
                  <th class="td-select"></th>
                  <th class="a-left">CURRENT IMAGES</th>
              </tr>
          </thead>
          <tbody style="padding-top: 6px;padding-bottom: 6px" id="images-table-area"></tbody>
      </table>
  </div>
  <br><br>
  <div class="function">
      <!--strong class="pages">Total Images: 1</strong-->
      <ul>
          <!-- <li style="margin-top:10px;"><a href="#" class="ico-reorder"><span><em>REORDER</em></span></a></li> -->
          <li><a href="javascript:void(0);" id="image-remove" class="btn ico-remove"><span><em>REMOVE</em></span></a></li>
      </ul>
  </div>
</script>
<script type="text/template" id="image-row-template">
  <td class="td-select" style="padding-top: 6px;padding-bottom: 6px" id="current-image-checkbox">
    <input type="checkbox" name="check" class="target-chbox" id="check_{{ fileResourceId }}"/>
    <label for="check_{{ fileResourceId }}"></label>
  </td>

  <td class="a-left td-description"  id="current-image">
      <!--img src="res/images/no_image.gif" width="50" height="37" alt=""/-->
      <img id="itemImg_{{ fileResourceId }}" src="{{ '/adminui/image/thumbnails/' + catalogId + '/' + fileName }}" width="39" height="39" alt="image" />
      <div class="row">
        <label for="fileName_{{ fileResourceId }}">Title:</label>
        <input type="text" name="fileName" id="fileName_{{ fileResourceId }}" maxlength="64" value={{ fileName }}>
      </div>
      <div class="row">
        <label for="fileTag_{{ fileResourceId }}">Tag: </label>
        <input type="text" name="fileTag" id="fileTag_{{ fileResourceId }}" maxlength="64" value={{ fileTag }}>
      </div>
  </td>
</script>
<script type="text/template" id="attachments-form-template">
  <div class="btn-holder" id="add-new-attachment">
      <!--a class="open-popup"><span><em>ADD ATTACHMENT</em></span></a-->
     <a href="javascript:void(0);" onclick="openUploadDiv('lightbox-item-attachment');"><span><em>UPLOAD ATTACHMENT</em></span></a>
  </div>
  <!-- <div class="scrollable-area anyscrollable" id="item_attachments"> -->
  <div>
      <table class="table-data">
          <thead>
          <tr>
              <th class="td-select"></th>
              <th class="a-left">CURRENT ATTACHMENTS</th>
          </tr>
          </thead>
          <tbody style="padding-top: 6px;padding-bottom: 6px" id="attachments-table-area"></tbody>
      </table>
  </div>
  <br><br>
  <div class="function">
      <!--strong class="pages">Total Attachments: 1-1 of 1</strong-->
      <ul>
          <!--li style="margin-top:10px;"><a href="#" class="ico-reorder"><span><em>REORDER</em></span></a>
          </li-->
          <li><a id="attachment-remove" href="javascript:void(0);"  class="btn ico-remove"><span><em>REMOVE</em></span></a></li>
      </ul>
  </div>
</script>
<script type="text/template" id="attachment-row-template">
  <td class="td-select" style="padding-top: 6px;padding-bottom: 6px" id="current-attachment-checkbox">
    <input type="checkbox" name="check" class="target-chbox"  id="check_{{ fileResourceId }}"/>
    <label for="check_{{ fileResourceId }}"></label>
  </td>
  <td class="a-left td-description" style="padding-top: 6px;padding-bottom: 6px" id="current-attachment">
    <img src="res/images/img06.png" width="50" height="37" alt="img"/>
    <div class="row">
      <label for="fileName_{{ fileResourceId }}">Title:</label>
      <input type="text" name="fileName" id="fileName_{{ fileResourceId }}" maxlength="64" value={{ fileName }}>
    </div>
    <div class="row">
      <label for="fileTag_{{ fileResourceId }}">Tag: </label>
      <input type="text" name="fileTag" id="fileTag_{{ fileResourceId }}" maxlength="64" value={{ fileTag }}>
    </div>
  </td>
</script>
<script type="text/template" id="pricing-form-template">
  <form class="settings-form" action="#">
      <div class="text-fields" id="pricing-form-fields">
        <div class="row">
          <label for="s-tiered-pricing">Tiered Pricing:</label>
          <div class="area-col">
            <input id="s-tiered-pricing" value="{{tieredPricing}}" name="tieredPricing" class="text" type="text" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]} />
          </div>
        </div>
          <div class="row">
              <label for="s-list-price">List Price:</label>

              <div class="area-col">
                  <input id="s-list-price" name="listPrice" class="text" type="text" value="{{ listPrice }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]} />
              </div>
          </div>
          <div class="row">
              <label for="s-list-price">Price Unit:</label>

              <div class="area-col">
                  <input id="s-list-price" name="priceUnit" class="text" type="text" value="{{ priceUnit }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]} />
              </div>
          </div>
      </div>
      <div class="check-fields">
          <strong class="label">Price Editable:</strong>

          <div class="area-col role">
              <div class="item">
                  <input id="cf-price-editable" name="priceEditable" class="master" type="checkbox" {{ priceEditable ? "checked='checked'" : ''}} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <label for="cf-price-editable"></label>
              </div>
          </div>
      </div>

      <div class="check-fields">
          <strong class="label">Suppress Price:</strong>
          <div class="area-col role">
              <div class="item">
                  <input id="cf-Suppress-item" name="itemSuppressPrice" class="master" type="checkbox" {{ itemSuppressPrice ? "checked='checked'" : ''}} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <label for="cf-Suppress-item"></label>
              </div>
          </div>
      </div>
  </form>
</script>
<script type="text/template" id="tiered-pricing-form-template">
</script>
<script type="text/template" id="tiered-pricing-row-template">
  <i>From</i>
  <input id="s-tiered-pricing-from" class="text" type="text" style="width: 59px;" value="{{ from }}" />
  <i>to</i>
  <input id="s-tiered-pricing-to" class="text" type="text" style="width: 59px;" value="{{ to }}" />
  <i>is</i>
  <input id="s-tiered-pricing-is" class="text" type="text" style="width: 59px;" value="{{ price }}" />
</script>
<script type="text/template" id="fulfillment-form-template">
  <form id="item-fulfillment-form"  class="settings-form" action="#">
      <div class="text-fields">
          <div class="row">
              <label for="s-lead-time">Lead Time:</label>

              <div class="area-col">
                  <input id="s-lead-time" name="leadTime" class="text" type="text" value="{{ leadTime }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-minimum-order-quantity">Minimum Order Quantity:</label>

              <div class="area-col">
                  <input id="s-minimum-order-quantity" name="minimumOrderQuantity" type="text" value="{{ minimumOrderQuantity }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <label id="s-minimum-order-quantity-lbl" class="valid" style="display: none;"></label>
              </div>
          </div>
          <!--<div class="row">
            <label for="s-order-multiple-quantity">Order Multiple Quantity:</label>
            <div class="area-col">
              <input id="s-order-multiple-quantity" name="orderMultiplyQuantity" class="text" type="text" value="{{ orderMultiplyQuantity }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
            </div>
          </div>-->
          <div class="row">
            <label for="s-order-interval">Order Interval:</label>
            <div class="area-col">
              <input id="s-order-interval" name="itemOrderInterval" class="text" type="text" value="{{ itemOrderInterval }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
            </div>
          </div>
      </div>
      <!-- <div class="check-fields">
          <strong class="label">In Stock:</strong>

          <div class="area-col role">
              <div class="item">
                  <input id="cf-in-stock" name="inStock" class="master" type="checkbox" {{ inStock ? 'checked="checked"' : '' }} {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
                  <label for="cf-in-stock"></label>
                  <i>Yes</i>
              </div>
          </div>
      </div> -->
      <div class="text-fields">
          <div class="row">
              <label for="s-quantity-on-hand">Quantity on Hand:</label>

              <div class="area-col">
                  <input id="s-quantity-on-hand" name="quantityOnHand" class="text" type="text" value="{{ quantityOnHand }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
      </div>
  </form>
</script>
<script type="text/template" id="custom-fields-form-template">
    <!-- <div class="top-box">
        <form action="#" class="form-sort">
            <fieldset>
                <label>Status:</label>
                <select>
                    <option>All</option>
                </select>
                <label>Sort By: </label>
                <select>
                    <option>Name</option>
                </select>
            </fieldset>
        </form>
    </div> -->
    <table class="table-data">
        <thead>
        <tr>
            <th class="td-select">
              <input type="checkbox" class="check-allbox" onclick="checkAllCustomFields(this.checked)" name="check-all3" id="check3-1"/>
              <label for="check3-1"></label>
            </th>
            <th class="a-left">FIELD NAME</th>
            <th>FIELD TYPE</th>
            <th>VALUE</th>
            <th>STATUS</th>
        </tr>
        </thead>
        <tbody id="custom-fields-table-area"></tbody>
    </table>
    <div class="bottom-data" id="custom-fields-pagination"></div>
    <br><br>
    <div class="function" id="custom-fields-functions">
        <ul>
            <li><a href="javascript:void(0);" id="cust-field-remove" class="ico-remove"><span><em>REMOVE</em></span></a></li>
          <%--  <li><a class="ico-activate" id="cust-field-activate" href="javascript:void(0);"><span><em>ACTIVATE</em></span></a></li>
            <li><a class="ico-deactivate" id="cust-field-dectivate" href="javascript:void(0);"><span><em>DEACTIVATE</em></span></a></li>--%>
        </ul>
    </div>
</script>
<script type="text/template" id="custom-field-row-template">
  <td class="td-select">
    <input type="checkbox" class="target-chbox" name="check_{{ customFieldId }}" id="check_{{ customFieldId }}"/>
    <label for="check_{{ customFieldId }}"></label>
  </td>
  <td class="a-left td-item-name">{{ fieldName }}</td>
  <td>{{ fieldType }}</td>
    {[ if (fieldType == "list") { ]}
    <td><select>
        <option value=":null:"></option>
        {[ $(options).each(function() { ]}
        <option value="{{ this }}">{{ this }}</option>
        {[ }); ]}
    </select></td>
    {[ } else if(fieldType == "text" || fieldType == "mediumtext" || fieldType == "largetext" || fieldType == "flag") { ]}
    <td></td>
    {[ } else { ]}
    <td>{{ value }}</td>
    {[ } ]}
  <td>{{ active ? "Active" : "Inactive" }}</td>
</script>
<script type="text/template" id="custom-fields-pagination-template">
	<br>
	<div class="bottom-data" id="custom-fields-pagination">
    	<div class="pager">
        	<span>Page {{pageNumber}}  of {{pagesAvailable}}</span>
            <ul>
			{[ if (pageNumber > 1) { ]}
            	<li><a id="prev-cust-field" class="btn-prev-active"></a></li>
    		{[ } else { ]}
				<li><a id="prev-cust-field" class="btn-prev"></a></li>
    		{[ } ]}

			{[ if (pageNumber == pagesAvailable) { ]}
            	<li><a id="next-cust-field" class="btn-next-inactive"></a></li>
    		{[ } else { ]}
				<li><a id="next-cust-field" class="btn-next"></a></li>
    		{[ } ]}
                
            </ul>
        </div>
        <strong class="pages">Total Records: {{firstElementOnPage}} -{{lastElementOnPage}}  of {{totalRecords}}</strong>
    </div>
    <br>
</script>
<script type="text/template" id="sap-specific-fields-form-template">
  <form class="settings-form" action="#">
      <div class="text-fields">
          <div class="row">
              <label for="s-new-item-matnr">NEW_ITEM-MATNR:</label>

              <div class="area-col">
                  <input id="s-new-item-matnr" name="newItemMatnr" class="text" type="text" value="{{ newItemMatnr }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-ext-product-id">NEW_ITEM-EXT_PRODUCT_ID:</label>

              <div class="area-col">
                  <input id="s-new-item-ext-product-id" name="newItemExtProductId" class="text" type="text" value="{{ newItemExtProductId }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-cust-field1">NEW_ITEM-CUST_FIELD1:</label>

              <div class="area-col">
                  <input id="s-new-item-cust-field1" name="newItemCustField1" class="text" type="text" value="{{ newItemCustField1 }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-cust-field2">NEW_ITEM-CUST_FIELD2:</label>

              <div class="area-col">
                  <input id="s-new-item-cust-field2" name="newItemCustField2" class="text" type="text" value="{{ newItemCustField2 }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-cust-field3">NEW_ITEM-CUST_FIELD3:</label>

              <div class="area-col">
                  <input id="s-new-item-cust-field3" name="newItemCustField3" class="text" type="text" value="{{ newItemCustField3 }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-cust-field4">NEW_ITEM-CUST_FIELD4:</label>

              <div class="area-col">
                  <input id="s-new-item-cust-field4" name="newItemCustField4" class="text" type="text" value="{{ newItemCustField4 }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-cust-field5">NEW_ITEM-CUST_FIELD5:</label>

              <div class="area-col">
                  <input id="s-new-item-cust-field5" name="newItemCustField5" class="text" type="text" value="{{ newItemCustField5 }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-ext-schema-type">NEW_ITEM-EXT_SCHEMA_TYPE:</label>

              <div class="area-col">
                  <input id="s-new-item-ext-schema-type" name="newItemExtSchemaType" class="text" type="text" value="{{ newItemExtSchemaType }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-ext-category-id">NEW_ITEM-EXT_CATEGORY_ID:</label>

              <div class="area-col">
                  <input id="s-new-item-ext-category-id" name="newItemExtCatagoryId" class="text" type="text" value="{{ newItemExtCatagoryId }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-ext-category">NEW_ITEM-EXT_CATEGORY:</label>

              <div class="area-col">
                  <input id="s-new-item-ext-category" name="newItemExtCatagory" class="text" type="text" value="{{ newItemExtCatagory }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>
          <div class="row">
              <label for="s-new-item-sld-sys-name">NEW_ITEM-SLD_SYS_NAME</label>

              <div class="area-col">
                  <input id="s-new-item-sld-sys-name" name="newItemSldSysName" class="text" type="text" value="{{ newItemSldSysName }}" {[if(readOnly != "1") { ]} {{ "disabled" }}{[};]}/>
              </div>
          </div>

          <div class="row">
              <label for="s-new-item-ext-quote-item">NEW_ITEM-EXT_QUOTE_ITEM</label>

              <div class="area-col">
                  <input id="s-new-item-ext-quote-item" name="newItemextQuoteItem" class="text" type="text" value="{{ newItemExtQuoteItem }}"  disabled="disabled" />
              </div>
          </div>
      </div>
      <div class="btns-holder">
          <p><span class="required">* </span>Required Field</p>
      </div>
  </form>
</script>
<div id="main">
<div class="section">
    <ul class="breadcrumbs">
        <li><a href="vroozi">Vroozi</a></li>
        <li><a href="catalog">Content Manager</a></li>
        <li><a href="catalogDetail">Item Inventory</a></li>
        <li>Item Name</li>
    </ul>
    <div class="video-btn-holder">
        <!--img src="images/img-1.gif" alt="" width="26" height="27"/-->
        <%--<a href="#" class="btn"><span><em>Help video</em></span></a>--%>
    </div>
</div>
<div class="main-holder">
<div id="content">
<h1 id="catalog-item-detail-page">Item Details</h1>

<div class="content-block toggle-block active" id="details-section">
    <div class="headline">
        <h2><a href="#" class="open-close">General</a></h2>
    </div>
    <div class="block">
        <div class="content" style="padding-top: 0;">
            <div class="details-box" id="details-box"></div>
        </div>
    </div>
</div>
<div class="add-slide-blocks">
<div class="toggle-block" id="long-description-section">
    <div class="title">
        <h2><a href="#" class="open-close">LONG DESCRIPTION</a></h2>
    </div>
    <div class="block" id="long-description-box"></div>
</div>
<div class="toggle-block" id="images-section">
    <div class="title">
        <h2><a href="#" class="open-close">Images</a></h2>
    </div>
    <div class="block">
        <div class="content reorder-widget editable-widget">
            <div class="images-box" id="images-box"></div>
        </div>
    </div>
</div>
<div class="toggle-block" id="attachments-section">
    <div class="title">
        <h2><a href="#" class="open-close">Attachments</a></h2>
    </div>
    <div class="block">
        <div class="content reorder-widget editable-widget">
            <div class="attachments-box" id="attachments-box"></div>
        </div>
    </div>
</div>
<div class="toggle-block" id="pricing-section">
  <div class="title">
      <h2><a href="#" class="open-close">PRICING</a></h2>
  </div>
  <div class="block">
      <div class="content editable-widget" style="padding-top: 0;">
        <div class="pricing-box" id="pricing-box"></div>
      </div>
    </div>
</div>
<div class="toggle-block" id="fulfillment-section">
    <div class="title">
        <h2><a href="#" class="open-close">FULFILLMENT</a></h2>
    </div>
    <div class="block">
        <div class="content editable-widget" style="padding-top: 0;">
            <div class="fulfillment-box" id="fulfillment-box"></div>
        </div>
    </div>
</div>

<%--

<div class="toggle-block" id="attachments-section">
    <div class="title">
        <h2><a href="#" class="open-close">Attachments</a></h2>
    </div>
    <div class="block">
        <div class="content reorder-widget editable-widget">
            <div class="attachments-box">
                <div class="btn-holder">
                    <a href="#add-attachment" class="btn-add-attachment open-popup"><span><em>ADD ATTACHMENT</em></span></a>
                    <a href="#load-attachment" class="btn-load-attachment"><span><em>UPLOAD ATTACHMENT</em></span></a>
                </div>
                <div class="scrollable-area anyscrollable">
                    <table class="table-data">
                        <thead>
                        <tr>
                            <th class="td-select"></th>
                            <th class="a-left">CURRENT ATTACHMENTS</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="td-select"><input type="checkbox" name="check" class="target-chbox"
                                                         id="check-1"/><label for="check-1"></label></td>
                            <td class="a-left td-description">
                                <img src="res/images/img06.png" width="50" height="37" alt="img"/>

                                <div class="field">
                                    <label>Title: </label><span><a href="#">Manual for Widget</a></span><br/>
                                    <label>Name: </label>manual_for_widget.pdf
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td-select"><input type="checkbox" name="check" class="target-chbox"
                                                         id="check-2"/><label for="check-2"></label></td>
                            <td class="a-left td-description">
                                <img src="res/images/img07.png" width="50" height="37" alt="img"/>

                                <div class="field">
                                    <label>Title: </label><span><a href="#">Manual for Widget</a></span><br/>
                                    <label>Name: </label>manual_for_widget.pdf
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td-select"><input type="checkbox" name="check" class="target-chbox"
                                                         id="check-3"/><label for="check-3"></label></td>
                            <td class="a-left td-description">
                                <img src="res/images/img06.png" width="50" height="37" alt="img"/>

                                <div class="field">
                                    <label>Title: </label><span><a href="#">Manual for Widget</a></span><br/>
                                    <label>Name: </label>manual_for_widget.pdf
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td-select"><input type="checkbox" name="check" class="target-chbox"
                                                         id="check-4"/><label for="check-4"></label></td>
                            <td class="a-left td-description">
                                <img src="res/images/img06.png" width="50" height="37" alt="img"/>

                                <div class="field">
                                    <label>Title: </label><span><a href="#">Manual for Widget</a></span><br/>
                                    <label>Name: </label>manual_for_widget.pdf
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td-select"><input type="checkbox" name="check" class="target-chbox"
                                                         id="check-5"/><label for="check-5"></label></td>
                            <td class="a-left td-description">
                                <img src="res/images/img08.png" width="50" height="37" alt="img"/>

                                <div class="field">
                                    <label>Title: </label><span><a href="#">Manual for Widget</a></span><br/>
                                    <label>Name: </label>manual_for_widget.pdf
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="function">
                    <strong class="pages">Total Attachments: 1-49 of 49</strong>
                    <ul>
                        <li style="margin-top:10px;"><a href="#" class="ico-reorder"><span><em>REORDER</em></span></a>
                        </li>
                        <li><a href="#" class="btn ico-remove"><span><em>REMOVE</em></span></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
--%>

<%--

<div class="toggle-block" id="other-section">
    <div class="title">
        <h2><a href="#" class="open-close">Other</a></h2>
    </div>
    <div class="block">
        <div class="content editable-widget" style="padding-top: 0;">
            <div class="other-box">
                <form class="settings-form" action="#">
                    <div class="text-fields">
                        <div class="row">
                            <label for="s-brand-name">Brand Name:</label>

                            <div class="area-col">
                                <input id="s-brand-name" class="text" type="text"/>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-model-number">Model Number:</label>

                            <div class="area-col">
                                <input id="s-model-number" class="text" type="text"/>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-upc">UPC:</label>

                            <div class="area-col">
                                <input id="s-upc" class="text" type="text"/>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-case-ups">Case UPC:</label>

                            <div class="area-col">
                                <input id="s-case-ups" class="text" type="text"/>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-colors">Colors:</label>

                            <div class="area-col">
                                <select opt_type="image" style="width:200px;" id="s-colors" name="alfa">
                                    <option value="red" img_src="res/images/attr-img09.png">Red</option>
                                    <option value="blue" img_src="res/images/attr-img09.png">Blue</option>
                                </select>
                                <script>
                                    var comboColor = new dhtmlXCombo("s-colors", "alfa", 200, "image");
                                    comboColor.enableFilteringMode(true);
                                    comboColor.DOMelem.style.border = "1px solid #C1C1C1";
                                    comboColor.DOMelem.style.width = "232px";
                                    comboColor.DOMelem.style.margin = "0 13px 0 0";
                                    comboColor.DOMelem.style.height = "23px";
                                    comboColor.DOMelem_button.src = "res/css/imgs/combo_select_dhx_terrace.gif";
                                    comboColor.DOMelem_button.style.height = "23px";
                                    comboColor.DOMelem_button.style.width = "15px";
                                    comboColor.DOMelem_input.style.position = "absolute";
                                    comboColor.DOMelem_input.style.border = "none";
                                    comboColor.DOMlist.style.width = "232px";
                                    comboColor.DOMlist.style.border = "1px solid #C1C1C1";
                                    comboColor.attachEvent("onChange", function () {

                                    });
                                </script>
                                <div class="btn-holder">
                                    <a href="#add-color" class="btn-add-color"><span><em>ADD</em></span></a>
                                </div>
                                <div class="color-ext-panel ext-panel">

                                </div>
                            </div>
                        </div>
                        <div class="row" style="padding-top:13px;">
                            <label for="s-size">Size:</label>

                            <div class="area-col">
                                <select opt_type="image" style="width:200px;" id="s-size" name="alfa">
                                    <option value="red" img_src="res/images/attr-img09.png">SM</option>
                                    <option value="blue" img_src="res/images/attr-img09.png">XL</option>
                                </select>
                                <script>
                                    var comboSize = new dhtmlXCombo("s-size", "alfa", 200, "image");
                                    comboSize.enableFilteringMode(true);
                                    comboSize.DOMelem.style.border = "1px solid #C1C1C1";
                                    comboSize.DOMelem.style.width = "232px";
                                    comboSize.DOMelem.style.margin = "0 13px 0 0";
                                    comboSize.DOMelem.style.height = "23px";
                                    comboSize.DOMelem_button.src = "res/css/imgs/combo_select_dhx_terrace.gif";
                                    comboSize.DOMelem_button.style.height = "23px";
                                    comboSize.DOMelem_button.style.width = "15px";
                                    comboSize.DOMelem_input.style.position = "absolute";
                                    comboSize.DOMelem_input.style.border = "none";
                                    comboSize.DOMlist.style.width = "232px";
                                    comboSize.DOMlist.style.border = "1px solid #C1C1C1";

                                </script>
                                <div class="btn-holder">
                                    <a href="#add-size" class="btn-add-size"><span><em>ADD</em></span></a>
                                </div>
                                <div class="size-ext-panel ext-panel">

                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
--%>

<%--

<div class="toggle-block" id="electronics-section">
    <div class="title">
        <h2><a href="#" class="open-close">Electronics</a></h2>
    </div>
    <div class="block">
        <div class="content editable-widget" style="padding-top: 0;">
            <div class="electronics-box">
                <form class="settings-form" action="#">
                    <div class="text-fields">
                        <div class="row">
                            <label for="s-ram">Ram:</label>

                            <div class="area-col">
                                <select opt_type="image" style="width:200px;" id="s-ram" name="alfa">
                                    <option value="value1" img_src="res/images/attr-img09.png">some text1</option>
                                    <option value="value2" img_src="res/images/attr-img09.png">some text2</option>
                                </select>
                                <script>
                                    var comboRam = new dhtmlXCombo("s-ram", "alfa", 200, "image");
                                    comboRam.enableFilteringMode(true);
                                    comboRam.DOMelem.style.border = "1px solid #C1C1C1";
                                    comboRam.DOMelem.style.width = "232px";
                                    comboRam.DOMelem.style.margin = "0 13px 0 0";
                                    comboRam.DOMelem.style.height = "23px";
                                    comboRam.DOMelem_button.src = "res/css/imgs/combo_select_dhx_terrace.gif";
                                    comboRam.DOMelem_button.style.height = "23px";
                                    comboRam.DOMelem_button.style.width = "15px";
                                    comboRam.DOMelem_input.style.position = "absolute";
                                    comboRam.DOMelem_input.style.border = "none";
                                    comboRam.DOMlist.style.width = "232px";
                                    comboRam.DOMlist.style.border = "1px solid #C1C1C1";
                                </script>
                                <div class="btn-holder">
                                    <a href="#add-ram" class="btn-add-ram"><span><em>ADD</em></span></a>
                                </div>
                                <div class="ram-ext-panel ext-panel">

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-cpu">CPU:</label>

                            <div class="area-col">
                                <select opt_type="image" style="width:200px;" id="s-cpu" name="alfa">
                                    <option value="value1" img_src="res/images/attr-img09.png">some text1</option>
                                    <option value="value2" img_src="res/images/attr-img09.png">some text2</option>
                                </select>
                                <script>
                                    var comboCpu = new dhtmlXCombo("s-cpu", "alfa", 200, "image");
                                    comboCpu.enableFilteringMode(true);
                                    comboCpu.DOMelem.style.border = "1px solid #C1C1C1";
                                    comboCpu.DOMelem.style.width = "232px";
                                    comboCpu.DOMelem.style.margin = "0 13px 0 0";
                                    comboCpu.DOMelem.style.height = "23px";
                                    comboCpu.DOMelem_button.src = "res/css/imgs/combo_select_dhx_terrace.gif";
                                    comboCpu.DOMelem_button.style.height = "23px";
                                    comboCpu.DOMelem_button.style.width = "15px";
                                    comboCpu.DOMelem_input.style.position = "absolute";
                                    comboCpu.DOMelem_input.style.border = "none";
                                    comboCpu.DOMlist.style.width = "232px";
                                    comboCpu.DOMlist.style.border = "1px solid #C1C1C1";
                                </script>
                                <div class="btn-holder">
                                    <a href="#add-cpu" class="btn-add-cpu"><span><em>ADD</em></span></a>
                                </div>
                                <div class="cpu-ext-panel ext-panel">

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-storage-capacity">Storage Capacity:</label>

                            <div class="area-col">
                                <select opt_type="image" style="width:200px;" id="s-storage-capacity" name="alfa">
                                    <option value="value1" img_src="res/images/attr-img09.png">some text1</option>
                                    <option value="value2" img_src="res/images/attr-img09.png">some text2</option>
                                </select>
                                <script>
                                    var comboStorage = new dhtmlXCombo("s-storage-capacity", "alfa", 200, "image");
                                    comboStorage.enableFilteringMode(true);
                                    comboStorage.DOMelem.style.border = "1px solid #C1C1C1";
                                    comboStorage.DOMelem.style.width = "232px";
                                    comboStorage.DOMelem.style.margin = "0 13px 0 0";
                                    comboStorage.DOMelem.style.height = "23px";
                                    comboStorage.DOMelem_button.src = "res/css/imgs/combo_select_dhx_terrace.gif";
                                    comboStorage.DOMelem_button.style.height = "23px";
                                    comboStorage.DOMelem_button.style.width = "15px";
                                    comboStorage.DOMelem_input.style.position = "absolute";
                                    comboStorage.DOMelem_input.style.border = "none";
                                    comboStorage.DOMlist.style.width = "232px";
                                    comboStorage.DOMlist.style.border = "1px solid #C1C1C1";
                                </script>
                                <div class="btn-holder">
                                    <a href="#add-storage" class="btn-add-storage"><span><em>ADD</em></span></a>
                                </div>
                                <div class="storage-ext-panel ext-panel">

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-screen-size">Screen Size:</label>

                            <div class="area-col">
                                <input id="s-screen-size" class="text" type="text"/>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-screen-resolution">Screen Resolution:</label>

                            <div class="area-col">
                                <input id="s-screen-resolution" class="text" type="text"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
--%>

<%--
<div class="toggle-block" id="bundle-info-section">
    <div class="title">
        <h2><a href="#" class="open-close">Bundle Info</a></h2>
    </div>
    <div class="block">
        <div class="content editable-widget" style="padding-top: 0;">
            <div class="bundle-info-box">
                <form class="settings-form" action="#">
                    <div class="text-fields">
                        <div class="row">
                            <label for="s-bundle-name">Bundle Name:</label>

                            <div class="area-col">
                                <input id="s-bundle-name" class="text" type="text"/>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-bundle-number">Bundle Number:</label>

                            <div class="area-col">
                                <input id="s-bundle-number" class="text" type="text"/>
                            </div>
                        </div>
                        <div class="row">
                            <label for="s-bundle-quantity">Bundle Quantity:</label>

                            <div class="area-col">
                                <input id="s-bundle-quantity" class="text" type="text"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
--%>

<%--

<div class="toggle-block" id="attributes-section">
    <div class="title">
        <h2><a href="#" class="open-close">Attributes</a></h2>
    </div>
    <div class="block">
        <div class="content editable-widget" style="padding-top: 0;">
            <div class="attributes-box">
                <form class="settings-form" action="#">
                    <div class="text-fields">
                        <div class="row">
                            <strong class="label">Green:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-green" class="master" type="checkbox"/>
                                    <label for="cf-green"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img01.png" alt=""/>
                                </div>
                                <div class="green-sel item">
                                    <select id="s-green" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">Energy Star Compliant:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-energy-star-compliant" class="master" type="checkbox"/>
                                    <label for="cf-energy-star-compliant"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img02.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-energy-star-compliant" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">RoHS Compliant:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-rohs-compliant" class="master" type="checkbox"/>
                                    <label for="cf-rohs-compliant"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img03.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-rohs-compliant" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">Free Shipping:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-free-shipping" class="master" type="checkbox"/>
                                    <label for="cf-free-shipping"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img04.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-free-shipping" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">Clearance:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-clearance" class="master" type="checkbox"/>
                                    <label for="cf-clearance"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img05.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-clearance" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">On Sale:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-on-sale" class="master" type="checkbox"/>
                                    <label for="cf-on-sale"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img06.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-on-sale" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">Preferred:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-preferred" class="master" type="checkbox"/>
                                    <label for="cf-preferred"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img07.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-preferred" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">Refurbished:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-refurbished" class="master" type="checkbox"/>
                                    <label for="cf-refurbished"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img08.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-refurbished" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <strong class="label">Contract:</strong>

                            <div class="area-col role">
                                <div class="item">
                                    <input id="cf-contract" class="master" type="checkbox"/>
                                    <label for="cf-contract"></label>
                                </div>
                                <div class="item imgs">
                                    <img src="res/images/attr-img09.png" alt=""/>
                                </div>
                                <div class="item">
                                    <select id="s-contract" class="choose-tab-select">
                                        <option title="#area-1">&nbsp;</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

--%>

<div class="toggle-block" id="custom-fields-section">
    <div class="title">
        <h2><a href="#" class="open-close">Custom Fields</a></h2>
    </div>
    <div class="block">
        <div class="content editable-widget">
	        <div class="btn-holder">
	       		<c:if test="<%=aclManager.allow(request, Permission.EDIT_ITEM)%>">
	            	<a class="btn-add-custom-fields" onclick="getItemCustomFields('<c:out value="${param['catalogId']}"/>','${param['catalogItemId']}');"><span><em class="checkboxReset">ADD CUSTOM FIELDS</em></span></a>
	            </c:if>
	       	</div>
            <form id="custom-fields-boxFrm" class="checkboxResetForm">
                <div class="custom-fields-box" id="custom-fields-box"></div>
            </form>
        </div>
    </div>
</div>

<div class="toggle-block" id="sap-specific-fields-section">
    <div class="title">
        <h2><a href="#" class="open-close">Sap Specific Fields</a></h2>
    </div>
    <div class="block">
        <div class="content editable-widget" style="padding-top: 0;">
            <div class="sap-specific-fields-box" id="sap-specific-fields-box"></div>
        </div>
    </div>
</div>

</div>
</div>
<div id="sidebar">
    <ul class="sub-nav">
        <li><a href="catalogDetail" class="ico-back"><span>BACK</span></a></li>
        <li><a href="#" class="ico-save inactive"><span>Saved</span></a></li>
    </ul>
    <h3>JUMP TO</h3>
    <ul class="sub-nav">
        <li><a href="#long-description-section" class="ico-desc alt-opener"><span>LONG DESCRIPTION</span></a></li>
        <li><a href="#images-section" class="ico-image alt-opener"><span>IMAGES</span></a></li>
        <li><a href="#attachments-section" class="ico-attachment alt-opener"><span>ATTACHMENTS</span></a></li>
        <li><a href="#pricing-section" class="ico-pricing alt-opener"><span>PRICING</span></a></li>
        <li><a href="#fulfillment-section" class="ico-fulfillment alt-opener"><span>FULFILLMENT</span></a></li>
<%--
        <li><a href="#other-section" class="ico-other alt-opener"><span>OTHER</span></a></li>
        <li><a href="#electronics-section" class="ico-electronic alt-opener"><span>ELECTRONICS</span></a></li>
        <li><a href="#bundle-info-section" class="ico-bundle alt-opener"><span>BUNDLE INFO</span></a></li>
        <li><a href="#attributes-section" class="ico-attribute alt-opener"><span>ATTRIBUTES</span></a></li>
--%>
        <li><a href="#custom-fields-section" class="ico-custom alt-opener"><span>CUSTOM FIELDS</span></a></li>
        <li><a href="#sap-specific-fields-section" class="ico-specific alt-opener"><span>SAP SPECIFIC FIELDS</span></a></li>
    </ul>
</div>
</div>
</div>
<div class="lightbox-section">
    <div class="lightbox lightbox-small" id="publish">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close">Close</a>

                    <h2>Publish Confirmation</h2>
                </div>
                <div class="content">
                    <p>Please click â€œPUBLISHâ€ to confirm that you would like to publish the following item:</p>

                    <p><strong>[lorem ipsum dolor sit amet]</strong></p>
                </div>
                <div class="btns-holder">
                    <div class="btns-frame">
                        <a href="#" class="btn btn-green"><span>PUBLISH</span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="lightbox-section">
    <div class="lightbox" id="add-image" style="height:508px;">
        <div class="holder">
            <div class="frame">
                <div class="title">
                    <a href="#" class="close">Close</a>

                    <h2>Add Image</h2>
                </div>
                <form action="#">
                    <fieldset>
                        <div class="lightbox-content">
                            <div style="margin-bottom:15px;">
                                <input type="text" value="Search Catalogs"/>
                                <input type="buttom" value="Submit" id="searchIcon"/>

                                <div style="float:right;">
                                    <label>Sort By: </label>
                                    <select style="width:50px;">
                                        <option>Name</option>
                                    </select>
                                </div>
                            </div>
                            <div style="border:1px solid #C5C5C5;-webkit-box-shadow: inset 0px 0px 4px 1px rgba(0, 0, 0, 0.2); box-shadow: inset 0px 0px 4px 1px rgba(0, 0, 0, 0.2);">
                                <div class="scrollable-area anyscrollable" style="width:841px;">
                                    <div class="text" style="width:800px;margin:0 12px;">
                                        <table class="table-data">
                                            <tbody>
                                            <tr>
                                                <td class="td-select"><input type="checkbox" name="check"
                                                                             class="target-chbox" id="check2"/><label
                                                        for="check2"></label></td>
                                                <td class="a-left td-description">
                                                    <img src="res/images/img02.png" width="80" height="58" alt="img"/>

                                                    <div class="field">
                                                        <label>Title: </label><span><a href="#">Nuts and bolts on black
                                                        background</a></span><br/>
                                                        <label>Name: </label>400px-Hexagon_nuts.jpg
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td-select"><input type="checkbox" name="check"
                                                                             class="target-chbox" id="check3"/><label
                                                        for="check3"></label></td>
                                                <td class="a-left td-description">
                                                    <img src="res/images/img03.png" width="80" height="58" alt="img"/>

                                                    <div class="field">
                                                        <label>Title: </label><span><a href="#">Nuts and bolts on black
                                                        background</a></span><br/>
                                                        <label>Name: </label>400px-Hexagon_nuts.jpg
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td-select"><input type="checkbox" name="check"
                                                                             class="target-chbox" id="check4"/><label
                                                        for="check4"></label></td>
                                                <td class="a-left td-description">
                                                    <img src="res/images/img04.png" width="80" height="58" alt="img"/>

                                                    <div class="field">
                                                        <label>Title: </label><span><a href="#">Nuts and bolts on black
                                                        background</a></span><br/>
                                                        <label>Name: </label>400px-Hexagon_nuts.jpg
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td-select"><input type="checkbox" name="check"
                                                                             class="target-chbox" id="check5"/><label
                                                        for="check5"></label></td>
                                                <td class="a-left td-description">
                                                    <img src="res/images/img05.png" width="80" height="58" alt="img"/>

                                                    <div class="field">
                                                        <label>Title: </label><span><a href="#">Nuts and bolts on black
                                                        background</a></span><br/>
                                                        <label>Name: </label>400px-Hexagon_nuts.jpg
                                                    </div>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div style="margin-top:10px;">
                                <label>File Name </label>
                                <input type="text" style="width:530px;"/>
                                <select>
                                    <option>All Files</option>
                                </select>
                            </div>
                        </div>
                        <div class="btns-holder" style="margin-top:35px;">
                            <a href="#" class="btn-cancel">Cancel</a>
                            <input type="submit" value="Save"/>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="res/js/item-details.js"></script>
