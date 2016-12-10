/* TODO:
Questions:
  Category - no data being sent; categories is null.
  Boolean consistency - Configurable is a string, pricing editable is a boolean
  Validations? Vendor Part Number has a 'required' star but not in the validation list
  Tiered Pricing styling is messed up

Issues:
  Saves should only occur on dirty models
  "Save All Models" - functionality to click on "Save" button - this isn't really needed as everything
    is either saved or in a state where something can't be saved. Need suggestions on this.
  Image remove refreshes too quickly showing a dead image that disappears on page refresh
  Long Description - CKEditor instance is a little too touchy on blur/focus events

  Salman: I have commented out "Save All Models" functionality as it was saving data without validating.
  Plus this functionality is not required anymore.
*/


/*------------------------- Common functions/settings ---------------*/

_.templateSettings = {
    evaluate : /\{\[(.+?)\]\}/g,
    interpolate : /\{\{(.+?)\}\}/g
}

window.saveInterval = 5000;
var priceRegex = /^(\d{1,7}([\.,]\d{1,4})?)$/;
var tieredPriceRegex = /^(\d{1,7}([\.,]\d{1,4})?\s*((((\[\s*\d+\s*\])|(\[\s*\d+-\d+\s*\]))(;|;?$))|((\[\s*\d+\+\s*\];?)$))\s*)*$/;
var wholeNumber = /^([1-9][0-9]*)$/;
var saveTimer;

$.validator.addMethod('priceFormat', function(value) {
    return priceRegex.test(value);
}, 'Invalid format');

$.validator.addMethod('valueWhole', function(value) {
       var test = wholeNumber.test(value);
        if(!test){
                $('#s-minimum-order-quantity').parent().parent().addClass('error');
                $('#s-minimum-order-quantity').parent().parent().removeClass('success');
                $('#s-minimum-order-quantity-lbl').parent().find('label').hide();
            } else {
                $('#s-minimum-order-quantity').parent().parent().addClass('success');
                $('#s-minimum-order-quantity-lbl').parent().find('label').show();
            }
       return test;
    }, 'Minimum Order Quantity can only be a whole number');

$.validator.addMethod("valueNotEquals", function(value, element, arg){
  return arg != value;
 }, "Value must not equal arg.");

$.validator.addMethod("valueNotEquals", function(value, element, arg){
	  return arg != value;
	 }, "Value must not equal arg.");
$.validator.addMethod('priceFormat', function(value) {
    return priceRegex.test(value);
}, 'Invalid format');
$.validator.addMethod('orderInterval', function(value) {
	var minOrder = $('input[name="minimumOrderQuantity"]').val();
	var orderInterval = $('input[name="itemOrderInterval"]').val();
    return (!minOrder || !orderInterval || minOrder == orderInterval);
}, 'Invalid format');
$.validator.addMethod('tierPrice', function(value) {
	if(value){
		return tieredPriceRegex.test(value);
	}else {return false;}
    
}, 'Invalid format');
$.validator.addMethod('tierOrderQuantity', function(value) {
    return validTierPrice()!=-2;
}, 'The Minimum Order Quantity for an item should be within the range specified for the 1st pricing tier');
$.validator.addMethod('tierOrderInteval', function(value) {
    return validTierPrice()!=-3;
}, 'Invalid format');
function validTierPrice() {
	
	var token;
	var r2 = -1;
	var r1 = -1;
	var prev = 0;
	
	var tieredPrice = $('input[name="tieredPricing"]').val();
	var minOrderQty = $('input[name="minimumOrderQuantity"]').val();
	var orderInterval = $('input[name="itemOrderInterval"]').val();
	
	var tokens = tieredPrice.split("]");
	for ( var i = 0; i < tokens.length; i++) {
		token = tokens[i];
		if (token.lastIndexOf('[') <= 0) {
			break;
		}
		token = token.substr(token.lastIndexOf('[') + 1).trim();
		
		if (token.indexOf('+') > -1) {
			r1 = parseInt(token.substr(0, token.lastIndexOf('+')));
			r2 = -1;
		} else if (token.indexOf('-') > -1 ) {
			r1 = parseInt(token.substr(0, token.lastIndexOf('-')));
			r2 = parseInt(token.substr(token.lastIndexOf('-') + 1));
		} else {
			r1 = parseInt(token);
			r2 = -1;
		}
			
		if(prev==0) {
			if(minOrderQty && minOrderQty.length>0) {
				if(r2!=-1 && (parseInt(minOrderQty)<r1 || parseInt(minOrderQty)>r2) ) {
					return -2;
				} else if(r2==-1 && parseInt(minOrderQty)!=r1){
					return -2;
				}
			}
			if(orderInterval && orderInterval.length>0) {
				if(r2!=-1 && (parseInt(orderInterval)!=r1) ) {
					return -3;
				} else if(r2==-1 && parseInt(orderInterval)!=r1){
					return -3;
				}
			}
		}
		if ((prev!=0 && r1+prev!=0 && r1 != prev+1) || (r2 != -1 && r2 <= r1)) {
			return -1;
		}
		prev = r2==-1? r1: r2;
	}
	return 1;
}
var delay = (function(){
 var timer = 0;
 return function(callback, ms){
   clearTimeout (timer);
   timer = setTimeout(callback, ms);
 };
});

window.useCKEditor = function () {
  var readOnlyField = $('#txtReadOnly').val(); 
  var config = {
    toolbar : [{ name: 'basicstyles', items : [ 'Font','FontSize','Bold','Italic','Underline' ],readOnly: (readOnlyField != 1) }]
  };
  var instance = CKEDITOR.instances['longDescription'];
  if(instance)
  {
      CKEDITOR.remove(instance);
  }
  
  CKEDITOR.replace('longDescription', config);
  if(readOnlyField !=1){
  	CKEDITOR.config.readOnly= true;
  }
  captureCKEditorEvents();
}

window.captureCKEditorEvents = function() {
  CKEDITOR.on("instanceReady", function() {
    var instance = CKEDITOR.instances['longDescription'];
    view = $(instance.element.$).parent().data("view");
    instance.on('blur', function() {
      if (instance.checkDirty()) {
        view.sumbitTextEditorChange(instance.name, instance.getData());
      }
    });
    instance.on('focus', function() {
      view.resetSaveButton();
    });
    instance.on('key', function() {
      delay(function() {
        view.setTextEditorChange(instance.name, instance.getData());
      }, 1000);
    });
  });
}

function actualSaveSingleAttribute(options, attrs, model) {
    options || (options = {});
    options.data = JSON.stringify(attrs);
    options.contentType = "application/json; charset=utf-8"
    options.success = function () {
    	if(!options.notyFlag){
    		noty({text: 'Saved!', type: 'warning'});	
    	}
    	delete options["notyFlag"];
        $('a.ico-save').addClass('inactive').find('span').text("Saved");
    	if (model.collection) {
            model.collection.fetch();
        }
        ;
    };
    options.error = function () {
        noty({text: 'Sorry, saved failed.', type: 'error'});
        $('a.ico-save').removeClass('inactive').find('span').text("Save");
    };
    Backbone.Model.prototype.save.call(model, attrs, options);
    return options;
}
window.saveSingleAttribute = function(model, attrs, options) {
//  stopSaveTimer();

    if($("#s-bundle-name-line-item").val()!='' && $("#s-bundle-quantity-line-item").val()==''){
        noty({text: 'Bundle Quantity is required if Bundle Number is given', type: 'error'});
  }
  else if($("#s-bundle-name-line-item").val()=='' && $("#s-bundle-quantity-line-item").val()!=''){
      noty({text: 'Bundle Number is required if Bundle Quantity is given', type: 'error'});
  }
  else if($("#s-bundle-name-line-item").val()!='' && $("#s-tiered-pricing").val()!=''){
          noty({text: 'ITEM-TIERED_PRICING cannot be specified if Bundle Number is given', type: 'error'});
  }

  else if($("#s-bundle-name-line-item").val()!='' && $("#s-bundle-quantity-line-item").val()!=''){
      try{
          $.ajax({
              type:'GET',
              url: 'catalogs/'+$('#catalog-id').val()+'/catalogitems/'+$("#s-bundle-name-line-item").val()+'/'+$("#unit-id").val(),
              dataType: 'text',
              success: function(data, textStatus, jqXHR) {
                  if(data != ''){
                      noty({text: data, type: 'error'});
                  }
                  else{
                      options = actualSaveSingleAttribute(options, attrs, model);
                  }
              }

          });
      }catch(exp){
          alert(exp);
      }
  }
    else{
        options = actualSaveSingleAttribute(options, attrs, model);
  }
}

window.resetSaveButton = function(){
//  startSaveTimer();
  $('a.ico-save').removeClass('inactive').find('span').text("Save");
  return false;
}

window.saveAllModels = function() {
//  stopSaveTimer();
  var models = [catalogItemDetail, catalogItemImages.models, catalogItemAttachments.models, catalogItemCustomFields.models];
  models = $.map(models, function(array) { return array; });
  $.each(models, function(index, model) {
    if (model.hasChanged()) {
      noty({text: 'Saving...', type: 'warning'});
      saveSingleAttribute(model, model.changedAttributes(), {});
    } else {
      $('a.ico-save').addClass('inactive').find('span').text("Saved");
    };
  });
}

//window.startSaveTimer = function() {
//  saveTimer = setTimeout(saveAllModels, saveInterval);
//}

//window.stopSaveTimer = function() {
//  clearTimeout(saveTimer);
//}

//window.restartSaveTimer = function() {
//  stopSaveTimer();
//  startSaveTimer();
//}

/*------------------------- END Common functions/settings ---------------*/

window.CatalogItemDetail = Backbone.Model.extend({
  urlRoot: "catalogs/" + catalogId + "/catalogitems/",
  url: function() {
    return this.urlRoot + this.id + "/details"
  },
  idAttribute: "catalogItemId",
  parse: function(response) {
    if (response == null) { return response };
    var itemDetail = response;
    itemDetail.categories = new Categories(itemDetail.categories);
    //itemDetail.suppliers = new Suppliers(itemDetail.suppliers);
    itemDetail.currencies = new Currencies(itemDetail.currencies);
    
    itemDetail.price = formatCurrency(itemDetail.price, itemDetail.currency);
    itemDetail.tieredPricing = formatCurrency(itemDetail.tieredPricing, itemDetail.currency);
    itemDetail.listPrice = formatCurrency(itemDetail.listPrice, itemDetail.currency);
    
    return itemDetail;
  },
  save: function(attrs, options) {
	if (attrs.price && attrs.price.length > 0) {
		attrs.price = parseCurrency(attrs.price, this.get("currency"));
	}
	if(attrs.tieredPricing && attrs.tieredPricing.length > 0) {
		attrs.tieredPricing = parseCurrency(attrs.tieredPricing, this.get("currency"));
	}
	if(attrs.listPrice && attrs.listPrice.length > 0) {
		attrs.listPrice = parseCurrency(attrs.listPrice, this.get("currency"));
	}
		saveSingleAttribute(this, attrs, options);
    
  }
});

catalogItemDetail = new CatalogItemDetail({catalogItemId: catalogItemId});
catalogItemDetail.fetch({
  success: function() {
    catalogItemGeneralView.render();
    catalogLongDescriptionView.render();
    catalogItemPricingView.render();
    catalogItemFulfillmentView.render();
    catalogItemSAPFieldsView.render(); 
  }
});

window.CatalogItemBlockView = Backbone.View.extend({
  model: catalogItemDetail,
  initialize: function(options){
    _.bindAll(this, 'render');
    this.template = _.template( $(options.templateId).html() );
    this.validationOptions = options.validationOptions;
    this.callback = options.callback;
    this.selectViews = options.selectViews;
  },
  render: function(){
    this.$el.html( this.template(this.model.toJSON()) );
    if (this.selectViews) {
      var model = this.model
      $.each(this.selectViews, function(index, selectView) {
        var newSelectField = new SelectFieldView({el: $(selectView.el), collection: model.get(selectView.collection), model: model, column: selectView.column })
        newSelectField.render();
      })
    };
    jcf.customForms.replaceAll();
    initUiCheckboxes();
    if (this.callback) {
      this.callback.call();
    };
    this.$el.find('form').validate(this.validationOptions);
    $(this.el).data('view', this);
    return this.el;
  },
  events: {
    "change input" : "submitInputChange",
    "focus input" : "resetSaveButton",
    "keyup input" : "setInputChange"
  },
  submitInputChange: function(e) {
    valid = 0;

    var element = e.target;
    if (element.type == "checkbox") {
      $(element).val($(element).is(':checked'));
    };
    valid = $(element).valid();
    
    if (valid) {
      var elementVal= $(element).attr("id");
      var tiPrice=$("#s-tiered-pricing").val();
      var itemPrice=$("#s-price").val();
      
      if(elementVal=='s-tiered-pricing'){
    	  if(!tiPrice && !itemPrice){
    		  noty({text: 'You Have to specify price or tiered price', type: 'warning'});
    		  return false;
    	  }else if(tiPrice){
    		  var orderQuantity = tiPrice.split("[")[1].split("-")[0].replace('+','');
    		  if(orderQuantity.indexOf("]") > -1){
    			  orderQuantity=orderQuantity.split("]")[0];
    		  }
    		  
    		  if(orderQuantity != 1 && $("#s-minimum-order-quantity").val()!= orderQuantity){
    			  $("#s-minimum-order-quantity").val(orderQuantity);
    			  settings = {};
    		      settings["minimumOrderQuantity"] = orderQuantity;
    		      this.model.save(settings, {notyFlag:true});
    		  }else if (orderQuantity==1 && $("#s-minimum-order-quantity").val()){
    			  $("#s-minimum-order-quantity").val("");
    			  settings = {};
    		      settings["minimumOrderQuantity"] = "";
    		      this.model.save(settings, {notyFlag:true});
    		  }
    	  }
      }
      noty({text: 'Saving...', type: 'warning'});
      settings = {};
      settings[element.name] = $(element).val().replace(/["]/g, "&quot;");
      this.model.save(settings, {});
          
      if(elementVal=='s-tiered-pricing' /*&& itemPrice*/){
    	  	notyFlag=false;
    	  	settings["price"] = "";
    	  	this.model.save(settings, {notyFlag:true});
    	  	$("#s-price").val("");	
    	  	
      }else if(elementVal=='s-price' /*&& tiPrice*/){
    	  notyFlag=false;
    	  settings["tieredPricing"] = "";
    	  this.model.save(settings, {notyFlag:true});
    	  $("#s-tiered-pricing").val("");
    	  settings["minimumOrderQuantity"] = "";
    	  this.model.save(settings, {notyFlag:true});
    	  $("#s-minimum-order-quantity").val("");
    	  
      }
     
      
    };
  },
  
  sumbitTextEditorChange: function(name, value) {  
    noty({text: 'Saving...', type: 'warning'});
    settings = {};
    settings[name] = value;
    this.model.save(settings, {});
  },
  resetSaveButton: resetSaveButton,
  setInputChange: function(e) {
//    restartSaveTimer();
    var element = e.target;
    settings = {};
    settings[element.name] = $(element).val();
    this.model.set(settings, {silent: true});
  },
  setTextEditorChange: function(name, value) {
//    restartSaveTimer();
    noty({text: 'Saving...', type: 'warning'});
    settings = {};
    settings[name] = value;
    this.model.set(settings, {silent: true});
  }
});

window.Category = Backbone.Model.extend({});
window.Categories = Backbone.Collection.extend({model: Category});
window.Supplier = Backbone.Model.extend({});
window.Suppliers = Backbone.Collection.extend({model: Supplier});
window.Currency = Backbone.Model.extend({});
window.Currencies = Backbone.Collection.extend({model: Currency});
window.TieredPrice = Backbone.Model.extend({
  urlRoot: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/tiered-prices"
});
window.TieredPrices = Backbone.Collection.extend({
  model: TieredPrice,
  url: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/tiered-prices"
});

tieredPrices = new TieredPrices();
tieredPrices.fetch();

window.SelectFieldView = Backbone.View.extend({
  initialize: function  (options) {
    this.column = options.column;
  },
  events: {
    'change': 'submitInputChange'
  },
  render: function(){
    attrs = this.collection.models;
    if (attrs.length > 0) {
      for (var i=0;i<attrs.length;i++) {
        var attr = attrs[i].toJSON();
        if (this.model.get(this.column) == attr.id) {
          $(this.el).append('<option id="' + attr.id + '" value="' + attr.id + '" selected>' + attr.name + '</option>');
        } else {
          $(this.el).append('<option id="' + attr.id + '" value="' + attr.id + '">' + attr.name + '</option>');
        }
      }
    };
  },
  submitInputChange: function(e) {
    noty({text: 'Saving...', type: 'warning'});
    var element = e.target;
    valid = $(element).valid();
    if (valid) {
      settings = {};
      settings[element.name] = $(element).val();
      this.model.set(settings);
      this.model.save(settings, {});
    };
  }
});

var TieredPricingView = Backbone.View.extend({
  initialize: function(){
    this.template = _.template( $("#tiered-pricing-form-template").html() );
    _.bindAll(this, 'render');
    this.collection.on("reset", this.render);
    this.render();
  },
  collection: tieredPrices,
  className: 'row',
  events: {
    'click #add-price': 'addPrice'
  },
  render: function(){
    this.$el.html( this.template(this.collection.toJSON()) );
    var collection = this.collection;
    if (collection.length > 0) {
      for (var i=0;i<collection.length;i++) {
        var model = collection.models[i];
        var view = new TieredPricingRowView( { model: model } ).render();
        $("#tiered-pricing-area").append(view);
      }
    } else {
      // $(this.tableArea).append(this.emptyMessage);
    }
    jcf.customForms.replaceAll();
    $("#pricing-form-fields").prepend(this.el);
    return this.el;
  },
  addPrice: function(e) {
    noty({text: 'Adding...', type: 'warning'});
    var element = e.target;
    // need to grab the to, from and price input values
    // and create a new TieredPrice
    // valid = $(element).valid();
    // if (valid) {
    //   settings = {};
    //   settings[element.name] = $(element).val();
    //   this.model.set(settings);
    //   this.model.save(settings, {});
    // };

    // Backbone wants to POST when there is no ID field. But adding an
    // id field causes it to call tiered-prices/<id> which the server doesn't like
    newPrice = new TieredPrice({from: 1, to: 5, price: "$50.00"});
    newPrice.save({}, {isNew: false});
  }
});

var TieredPricingRowView = Backbone.View.extend({
  initialize: function() {
    this.template = _.template( $('#tiered-pricing-row-template').html() );
  },
  render:function () {
    this.$el.html( this.template(this.model.toJSON()) );
    return this.el;
  }
});

tieredPricingView = new TieredPricingView();

catalogItemGeneralView = new CatalogItemBlockView({
  templateId: "#general-form-template",
  el: $("#details-box"),
  selectViews: [
    {el: '#s-currency', collection: "currencies", column: 'currency' }
  ],
  validationOptions: {
    rules: {
        itemType: {
            required: true
        },
        shortDescription: {
            required: true
        },
        newItemExtCatagory: {
            required: true
        },
        vendorPartNumber: {
            required: true
        },
        unitOfMeasure: {
            required: true
        },
        price: {
            required: true,
            priceFormat: true
        },
        currency: {
            required: true
        },
        newItemMatgroup: {
          valueNotEquals: "default",
          required: true
        },
        minimumOrderQuantity:{
          valueWhole: "default"
        }
    },
    highlight: function(label) {
        $(label).closest('.row').addClass('error').removeClass('success');
    },
    success: function(label) {
        label.addClass('valid').closest('.row').addClass('success');
    },
    messages: {
        itemType: "Item type is required.",
        shortDescription: "Short Description is required.",
        vendorPartNumber: "Vendor part number is required.",
        unitOfMeasure: "Unit of Measure is required.",
//        vendorPartNumber: "",
        price: {
          required: "Price is required.",
          priceFormat: "Invalid price value."
        },
        currency: "Currency is required.",
        newItemMatgroup: {
          valueNotEquals: "Category is required."
        },
        minimumOrderQuantity:{
          valueWhole:"Minimum Order Quantity can only be a whole number."
        }
},
    onfocusout: false
  }
});
catalogLongDescriptionView = new CatalogItemBlockView({ templateId: "#long-description-form-template", el: $("#long-description-box"), callback: useCKEditor });
catalogItemPricingView = new CatalogItemBlockView({ 
	templateId: "#pricing-form-template", 
	el: $("#pricing-box"),
	validationOptions: {
	    rules: {
	    	listPrice: {
	          priceFormat: true
	    	},
	    	tieredPricing: {
	    		tierPrice: true,
	    		tierOrderInteval: true
		    }
	    },
	    highlight: function(label) {
	        $(label).closest('.row').addClass('error').removeClass('success');
	    },
	    success: function(label) {
	        label.addClass('valid').closest('.row').addClass('success');
	    },
	    messages: {
	    	listPrice: {
	          priceFormat: "Invalid price value."
	        },
	        tieredPricing: {
	        	tierPrice: "Invalid tiered pricing format.",
	        	tierOrderInteval: "The minimum value of 1st tier must be the same as the Order Interval"
	        }
	    }
	}
	
});
catalogItemFulfillmentView = new CatalogItemBlockView({ 
	templateId: "#fulfillment-form-template", 
	el: $("#fulfillment-box") ,
	validationOptions: {
	    rules: {
	    	minimumOrderQuantity:{
	    	  digits: true,
	    	  orderInterval: true,
	    	  tierOrderQuantity: true
	        },
	        itemOrderInterval:{
	          digits: true,
	    	  orderInterval: true
	        }
	    },
	    highlight: function(label) {
	        $(label).closest('.row').addClass('error').removeClass('success');
	    },
	    success: function(label) {
	        label.addClass('valid').closest('.row').addClass('success');
	    },
	    messages: {
	    	minimumOrderQuantity:{
		      digits:"Minimum Order Quantity can only be a whole number.",
		      orderInterval: "Minimum Order Quantity must be same as Order Interval."
		    },
		   itemOrderInterval:{
	         digits: "Order Interval can only be a whole number.",
	         orderInterval: "Minimum Order Quantity must be same as Order Interval.",
	         tierOrderQuantity: "The Minimum Order Quantity for an item should be within the range specified for the 1st pricing tier."
	       }
	        
	    }
	}
});
catalogItemSAPFieldsView = new CatalogItemBlockView({ templateId: "#sap-specific-fields-form-template", el: $("#sap-specific-fields-box") });

window.CatalogItemImage = Backbone.Model.extend({
  urlRoot: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/images",
  idAttribute: "fileResourceId",
  save: function(attrs, options) {
    saveSingleAttribute(this, attrs, options);
  }
});

window.CatalogItemImages = Backbone.Collection.extend({
  model: CatalogItemImage,
  url: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/images"
});

catalogItemImages = new CatalogItemImages();
catalogItemImages.fetch({cache: false});

window.CatalogItemAttachment = Backbone.Model.extend({
  urlRoot: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/attachments",
  idAttribute: "fileResourceId",
  save: function(attrs, options) {
    saveSingleAttribute(this, attrs, options);
  }
});

window.CatalogItemAttachments = Backbone.Collection.extend({
  model: CatalogItemAttachment,
  url: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/attachments"
});

catalogItemAttachments = new CatalogItemAttachments();
catalogItemAttachments.fetch({cache: false});

window.CatalogItemPaginationView = Backbone.View.extend({
	  initialize: function(options) {
	    this.template = _.template( $('#custom-fields-pagination-template').html() );
	  },
	  render:function () {
          var list=persistCheckboxStateOnPageChangeCustomField('custom-fields-boxFrm','custom-fields-box');
	    this.$el.html( this.template(this.model.toJSON()) );
          copyCheckedItemListCustomField('custom-fields-boxFrm',list);
	    return this.el;
	  },
	  events: {
	    "click a#next-cust-field" : "next",
	    "click a#prev-cust-field" : "prev"
	  },
	  next: function(e) {

		this.model.next();

	  },
	  prev: function(e) {

		  this.model.prev();

	  }
	});

window.CatalogItemTableView = Backbone.View.extend({
  initialize: function(options){
    this.template = _.template( $(options.templateId).html());
    _.bindAll(this, 'render');
    this.collection.on("reset", this.render);
    this.rowTemplateId = options.rowTemplateId;
    this.tableArea = options.tableArea;
    this.emptyMessage = options.emptyMessage;
    this.renderCallback = options.renderCallback;
    this.paginationArea = options.paginationArea;
    this.pagination = options.pagination;
    if(this.pagination) {
    	this.pagination.on('change', this.render);
    }
  },
  render: function() {
	  var collection = this.collection;
//	    
	  if(this.paginationArea) {
		  var collection = this.collection = this.pagination.get('customFields');
      }
    this.$el.html( this.template(this.collection.toJSON()));
    
    
    if (collection.length > 0) {
        if(this.rowTemplateId == "#image-row-template") {
            $("#upload-image").hide();
        } else if(this.rowTemplateId == "#attachment-row-template") {
            $("#add-new-attachment").hide();
        }

      for (var i=0;i<collection.length;i++) {
        var model = collection.models[i];
        var view = new CatalogItemTableRowView( { model: model, templateId: this.rowTemplateId } ).render();
        $(this.tableArea).append(view);
          if(model.toJSON().fileName != null){
              if(model.toJSON().fileName.indexOf('http') != -1 || model.toJSON().fileName.indexOf('www') != -1){
                  $("#itemImg_"+model.toJSON().fileResourceId).attr('src', model.toJSON().fileName);
              }
          }
      }
      if(this.paginationArea && this.pagination.toJSON().pagesAvailable>1) {
    	  customFieldsPaginationView = new CatalogItemPaginationView({model: this.pagination});
    	  $(this.paginationArea).append(customFieldsPaginationView.render());
      }
      
    } else {
      $(this.tableArea).append(this.emptyMessage);
    }
    if (this.renderCallback) {
      this.renderCallback(collection.length);
    };
    initColorBox();
    

    initUiCheckboxes();
    jcf.customForms.replaceAll();
    return this.el;
  },
  events: {
	  	"click #image-remove" : "removeImages",
	    "click #attachment-remove" : "removeAttachements",
	    "click #cust-field-remove" : "removeCustFields",
	    "click #cust-field-activate" : "submitStatusUpdate",
	    "click #cust-field-dectivate" : "submitStatusUpdate"
  },
  removeImages: function() {
  	if($('#txtReadOnly').val() ==1){
	    checkedRows = $('#images-box input[id^="check_"]:checked');
	    if(checkedRows.length<=0) {
	    	alert('Please select an item');
	    } else {
	    	deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', function() {
	    		var collection;
			    checkedRows.each(function(index, element) {
			      view = $(element).parent().parent().data("view");
			      model = view.model;
			      collection = model.collection;
			      collection.remove(model);
			      model.destroy();
			      view.remove();
			      view.unbind();
			    });
                collection.fetch({cache: false});
                $("#imageFileDiv").html("<input type='file' value='' id='imageFile' name='imageFile' />");
                jcf.customForms.replaceAll();
                jcf.customForms.refreshAll();
	    	});  
		    
	    }
	    return false;
    }
  },
  removeAttachements: function() {
    	if($('#txtReadOnly').val() ==1){
  	    checkedRows = $('#attachments-box input[id^="check_"]:checked');
  	    if(checkedRows.length<=0) {
  	    	alert('Please select an item');
  	    } else {
  	    	deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', function() {
  	    		var collection;
  			    checkedRows.each(function(index, element) {
  			      view = $(element).parent().parent().data("view");
  			      model = view.model;
  			      collection = model.collection;
  			      collection.remove(model);
  			      model.destroy();
  			      view.remove();
  			      view.unbind();
  			    });
                  collection.fetch({cache: false});
                  $("#imageFileDiv").html("<input type='file' value='' id='imageFile' name='imageFile' />");
                  jcf.customForms.replaceAll();
                  jcf.customForms.refreshAll();
  	    	});  
  		    
  	    }
  	    return false;
      }
  },
  removeCustFields: function() {
	  	if($('#txtReadOnly').val() ==1){
		    checkedRows = $('#custom-fields-box input[id^="check_"]:checked');

            var list=persistCheckboxStateOnPageChangeCustomField('custom-fields-boxFrm','custom-fields-box');

		    if(list.length==0) {
		    	alert('Please select an item');
		    } else {
		    	deleteConfirmAction('Please click "DELETE" to confirm that you would like to remove the selected item(s).', function() {
		    		var collection;
		    		var modelIds = '';
				    checkedRows.each(function(index, element) {
				      view = $(element).parent().parent().data("view");
				      modelIds+=view.model.id+',';
				      collection = view.model.collection;
//				      collection.remove(view.model);
//				      view.model.destroy();
//				      view.remove();
//				      view.unbind();
				    });
                    modelIds=prepareCSVStringCustomField('custom-fields-boxFrm');
                    //modelIds='['+modelIds+']';
	                
	                var url = 'catalogs/'+this.catalogId+'/catalogitems/'+this.catalogItemId+'/customfields/' + modelIds,
	                options = {
	                    url:url,
	                    type:'DELETE',
	                    success: function() {
	                    	customFieldsPage.fetch({cache: false});
	                    }
	                };
	                _.extend(options, options);
	                return  (this.sync || Backbone.sync).call(this, null, this, options);
		    	});  
			    
		    }
		    return false;
	    }
	  },
	  submitStatusUpdate: function(e) {
		  if($('#txtReadOnly').val() ==1){
			    var tableView = this;
			    var collection, model;
			    var modelIds = [];
			    checkedRows = $('#custom-fields-box input[id^="check_"]:checked');
			    if(checkedRows.length<=0) {
			    	alert('Please select an item');
			    	return;
			    }
			    checkedRows.each(function(index, element) {
			      var view = $(element).parent().parent().data("view");
			      model = view.model;
			      modelIds.push(model.id);
			    });
			    paramString = "?status=" + $(e.target).text();
			    url = model.urlRoot + "/" + modelIds + paramString;
			    options = {
	                    url:url,
	                    type:'PUT',
	                    success: function() {
	                    	model.collection.fetch({cache: false});
	                    }
	                };
	                _.extend(options, options);
	                return  (this.sync || Backbone.sync).call(this, null, this, options);
			  }
		 }
});

window.CatalogItemTableRowView = Backbone.View.extend({
  initialize: function(options) {
    this.template = _.template( $(options.templateId).html() );
  },
  render:function () {
    this.$el.html( this.template(this.model.toJSON()) );
    $(this.el).data('view', this);
    return this.el;
  },
  tagName: "tr",
  events: {
    "change input:text" : "submitInputChange",
    "focus input:text" : "resetSaveButton",
    "keyup input:text" : "setInputChange"
  },
  submitInputChange: function(e) {
    noty({text: 'Saving...', type: 'warning'});
    var element = e.target;
    valid = true //$(element).valid(); The input isn't part of a form, but doesn't have any validations, so... could skip this altogether
    if (valid) {
      settings = {};
      settings[element.name] = $(element).val();
      this.model.save(settings, {});
    };
  },
  resetSaveButton: resetSaveButton,
  setInputChange: function(e) {
//    restartSaveTimer();
    var element = e.target;
    settings = {};
    settings[element.name] = $(element).val();
    this.model.set(settings, {silent: true});
  }
});

imagesView = new CatalogItemTableView({
  collection: catalogItemImages,
  el: $("#images-box"),
  templateId: '#images-form-template',
  rowTemplateId: '#image-row-template',
  tableArea: "#images-table-area",
  emptyMessage: '<tr><td colspan="2" style="height:55px; font:10pt Helvetica; color:#9da2a7;">Make me pretty. Click upload button to add images.</td></tr>'
});

attachmentsView = new CatalogItemTableView({
  collection: catalogItemAttachments,
  el: $("#attachments-box"),
  templateId: '#attachments-form-template',
  rowTemplateId: '#attachment-row-template',
  tableArea: "#attachments-table-area",
  emptyMessage: '<tr><td colspan="2" style="height:55px; font:10pt Helvetica; color:#9da2a7;">Make me pretty. Click upload button to add attachments.</td></tr>'
});


window.CatalogItemCustomField = Backbone.Model.extend({
  urlRoot: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/customfields",
  idAttribute: "customFieldId",
  save: function(attrs, options) {
    saveSingleAttribute(this, attrs, options);
  }
});

window.CatalogItemCustomFields = Backbone.Collection.extend({
  model: CatalogItemCustomField,
  url: "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/customfields"
});

window.CatalogItemCustomFieldPage = Backbone.Model.extend({
	initialize: function() {
//      _.bindAll(this,"updateParams");
//      this.bind('change', this.updateParams);
//      this.bind('change:searchName', this.updateSearchBar);
  },
  url: function() {
	return "catalogs/" + catalogId + "/catalogitems/" + catalogItemId + "/customfields/paginated?pageNumber=" + this.get('pageNumber');
  },
  defaults: {
  	pageNumber: 1,
    pageSize: 7,
    totalRecords: 0,
    pagesAvailable: 0,
    firstElementOnPage: 0,
    lastElementOnPage: 0
  },
  parse:function (response) {
	response.customFields = new CatalogItemCustomFields(response.pageItems);
  	response.pageNumber = response.pageNumber;
  	response.pageSize = response.pageSize;
  	response.totalRecords = response.totalRecords;
  	response.pagesAvailable = response.pagesAvailable;
  	response.firstElementOnPage = response.firstElementOnPage;
  	response.lastElementOnPage = response.lastElementOnPage;
    return response;
  },
  next: function() {
      var list=persistCheckboxStateOnPageChangeCustomField('custom-fields-boxFrm','custom-fields-box');
	  pageNumber = this.get("pageNumber");
	  pagesAvailable = this.get("pagesAvailable");
	  if(pageNumber < pagesAvailable) {
		  	this.set("pageNumber", (pageNumber + 1));
	    	this.fetch({cache: false});

  	  }
      copyCheckedItemListCustomField('custom-fields-boxFrm',list);
  },
  prev: function() {
      var list=persistCheckboxStateOnPageChangeCustomField('custom-fields-boxFrm','custom-fields-box');
	  pageNumber = this.get("pageNumber");
	  pagesAvailable = this.get("pagesAvailable");
	  if(pageNumber > 1) {
		  this.set("pageNumber", (pageNumber - 1));
		  this.fetch({cache: false});

	  }

  }
});

customFieldsPage = new CatalogItemCustomFieldPage();
customFieldsPage.fetch({cache: false, success: function() {
	catalogItemCustomFieldsView = new CatalogItemTableView({
		  collection: customFieldsPage.get('customFields'),
		  el: $("#custom-fields-box"),
		  templateId: "#custom-fields-form-template",
		  rowTemplateId: '#custom-field-row-template',
		  tableArea: "#custom-fields-table-area",
		  paginationArea: "#custom-fields-pagination",
		  pagination: customFieldsPage,
		  emptyMessage: '<tr><td colspan="2" style="height:55px; font:10pt Helvetica; color:#9da2a7;">No custom fields available for this catalog.</td></tr>',
		  renderCallback: function(collectionLength) {
		    if (collectionLength > 0) {
		      $("#custom-fields-functions").show();
		    } else {
		      $("#custom-fields-functions").hide();
		    };
		  }
		});
	catalogItemCustomFieldsView.render();
}});


var index = 0;
var colorIndex = 0;
var sizeIndex = 0;
var ramIndex = 0;
var cpuIndex = 0;
var storageIndex = 0;

var itemId = null;
var ociItemStatusId = 0;

var tag = null;
var attachmentName={};
var imagePath=null;
var longText = null;


$(document).ready(function(){

    if( catalogItemId == null || '' == catalogItemId || 'null' == catalogItemId ) {
        return;
    }

    $("#unit-id").val(unitId);
    $("#catalog-id").val(catalogId);
    $("#catalog-id-image").val(catalogId);
    $("#catalog-item-id").val(catalogItemId);
    $("#catalog-item-id-image").val(catalogItemId);
    $("a[href='catalogDetail']").attr('href', 'catalogDetail?catalogId='+catalogId);

    var imageOptions = {
        success:       showImageResponse
    };

    var attachmentOptions = {
        success:       showAttachmentResponse
    };
    // bind upload forms using 'ajaxForm'
    $('#item-image-form').ajaxForm(imageOptions);
    $('#item-attachment-form').ajaxForm(attachmentOptions);

    $(".btn-load-image").click(function(){
			$("#uploadPopup").css("display", "block");
	});

	$("#add-price").click(function(e){
		index++;
		$("#add-price-area").append('<li id="list-price'+index+'" style="float:left; margin-bottom:10px;"><div class="area-col"><div class="btn-holder" style="width:32px;"><a href="#" id="from-price" class="btn-from-pricing" style="width:25px;"><span style="min-width: 20px;width:18px;"><em style="background:url(images/price-add.png) no-repeat;padding:0;margin:0; width:15px;"></em></span></a></div><input id="s-tiered-pricing-from'+index+'" class="text" type="text" style="width: 59px;" value="'+$("#s-tiered-pricing-from").val()+'"/><i>to</i><input id="s-tiered-pricing-to'+index+'" class="text" type="text" style="width: 59px;"  value="'+$("#s-tiered-pricing-to").val()+'"/><i>is</i><input id="s-tiered-pricing-is'+index+'" class="text" type="text" style="width: 59px;" value="'+$("#s-tiered-pricing-is").val()+'"/><div class="btn-del-holder"><a id="del-price'+index+'" onclick="deletePriceData('+index+')" class="btn-del-pricing"></a></div></div></li>');
	});

	$(".btn-add-color").click(function(e){
		var imgVar = comboColor.getSelectedValue();
		colorIndex++;
		$(".color-ext-panel").append('<div class="row" id="color-row'+colorIndex+'"><div class="cell"><div class="clr"><div class="bg-FE0000"></div></div></div><div class="cell"><span>Price +/-:</span></div><div class="cell"><input type="text" /></div><div class="cell"><span>Part Suffix:</span></div><div class="cell"><input type="text" /></div><div class="cell"><a class="btn-x" style="cursor:pointer;" onclick="deleteColorData('+colorIndex+')"><span></span></a></div></div>');
	});
	$(".btn-add-size").click(function(e){
		var imgVar = comboSize.getSelectedValue();
		sizeIndex++;
		$(".size-ext-panel").append('<div class="row" id="size-row'+sizeIndex+'"><div class="cell"><div class="clr"><div class="bg-size">SM</div></div></div><div class="cell"><span>Price +/-:</span></div><div class="cell"><input type="text" /></div><div class="cell"><span>Part Suffix:</span></div><div class="cell"><input type="text" /></div><div class="cell"><a class="btn-x" style="cursor:pointer;"  onclick="deleteSizeData('+sizeIndex+')"><span></span></a></div></div>');
	});
	$(".btn-add-storage").click(function(e){
		var imgVar = comboStorage.getSelectedValue();
		storageIndex++;
		$(".storage-ext-panel").append('<div class="row" id="storage-row'+storageIndex+'"><div class="cell"><div class="clr"><div class="bg-size">SM</div></div></div><div class="cell"><span>Price +/-:</span></div><div class="cell"><input type="text" /></div><div class="cell"><span>Part Suffix:</span></div><div class="cell"><input type="text" /></div><div class="cell"><a class="btn-x" style="cursor:pointer;"  onclick="deleteStorageData('+storageIndex+')"><span></span></a></div></div>');
	});
	$(".btn-add-cpu").click(function(e){
		var imgVar = comboCpu.getSelectedValue();
		cpuIndex++;
		$(".cpu-ext-panel").append('<div class="row" id="cpu-row'+cpuIndex+'"><div class="cell"><div class="clr"><div class="bg-size">SM</div></div></div><div class="cell"><span>Price +/-:</span></div><div class="cell"><input type="text" /></div><div class="cell"><span>Part Suffix:</span></div><div class="cell"><input type="text" /></div><div class="cell"><a class="btn-x" style="cursor:pointer;"  onclick="deleteCpuData('+cpuIndex+')"><span></span></a></div></div>');
	});
	$(".btn-add-ram").click(function(e){
		var imgVar = comboRam.getSelectedValue();
		ramIndex++;
		$(".ram-ext-panel").append('<div class="row" id="ram-row'+ramIndex+'"><div class="cell"><div class="clr"><div class="bg-size">SM</div></div></div><div class="cell"><span>Price +/-:</span></div><div class="cell"><input type="text" /></div><div class="cell"><span>Part Suffix:</span></div><div class="cell"><input type="text" /></div><div class="cell"><a class="btn-x" style="cursor:pointer;"  onclick="deleteRamData('+ramIndex+')"><span></span></a></div></div>');
	});
	$("#cf-green").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-green").removeAttr("disabled");
		}else{
			$("#s-green").attr("disabled", "");
		}
	});
	$("#cf-energy-star-compliant").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-energy-star-compliant").removeAttr("disabled");
		}else{
			$("#s-energy-star-compliant").attr("disabled", "");
		}
	});
	$("#cf-rohs-compliant").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-rohs-compliant").removeAttr("disabled");
		}else{
			$("#s-rohs-compliant").attr("disabled", "");
		}
	});
	$("#cf-free-shipping").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-free-shipping").removeAttr("disabled");
		}else{
			$("#s-free-shipping").attr("disabled", "");
		}
	});
	$("#cf-clearance").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-clearance").removeAttr("disabled");
		}else{
			$("#s-clearance").attr("disabled", "");
		}
	});
	$("#cf-on-sale").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-on-sale").removeAttr("disabled");
		}else{
			$("#s-on-sale").attr("disabled", "");
		}
	});
	$("#cf-preferred").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-preferred").removeAttr("disabled");
		}else{
			$("#s-preferred").attr("disabled", "");
		}
	});
	$("#cf-refurbished").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-refurbished").removeAttr("disabled");
		}else{
			$("#s-refurbished").attr("disabled", "");
		}
	});
	$("#cf-contract").change(function(){
		if($(this).parent().find('label').attr("aria-pressed") == "true"){
			$("#s-contract").removeAttr("disabled");
		}else{
			$("#s-contract").attr("disabled", "");
		}
	});
	    
//	disableColorBox1();

}); // Document ready end

function openUploadDiv(targetDiv){
	var readOnlyField = $('#txtReadOnly').val();
	if(readOnlyField== 1){
		//$('#upload-image').show();
	    $.colorbox({
	    	href:'#'+targetDiv,
    	  	inline:true,
	        onComplete: function(){
            $('#cboxContent .close, #cboxContent .btn-cancel').unbind('click').bind('click', function(){
                $('#cboxClose').trigger('click');
                return false;
            });
        }
    	});
	}	
}

// Unused?
// function deleteColorData(dataIndex){
//   $("#color-row"+dataIndex).remove();
// }
// function deletePriceData(dataIndex){
//   $("#list-price"+dataIndex).remove();
// }
// function deleteSizeData(dataIndex){
//   $("#size-row"+dataIndex).remove();
// }
// function deleteStorageData(dataIndex){
//   $("#storage-row"+dataIndex).remove();
// }
// function deleteCpuData(dataIndex){
//   $("#cpu-row"+dataIndex).remove();
// }
// function deleteRamData(dataIndex){
//   $("#ram-row"+dataIndex).remove();
// }

// post-submit callback
function showImageResponse(responseText, statusText, xhr, $form)  {
    if(responseText.indexOf('Failed') != -1){
        var errorMsg = responseText.substring(responseText.indexOf(':')+1);
        //alert('status: ' + statusText + '\n\nresponseText: \n' + responseText + '\n\nThe output div should have already been updated with the responseText.');
        $("#colorbox").css({'height': 640 + 'px'});
        $("#cboxContent").css({'height': 625 + 'px'});
        $("#cboxLoadedContent").css({'height': 625 + 'px'});
      //  $('span#editCatalogReplyDiv').html("<br />Submission Failed: " + errorMsg);
    }else{
        $('div#lightbox-item-image a.close').click();
        catalogItemImages.fetch({cache: false});
        $("#imageFileDiv").html("<input type='file' value='' id='imageFile' name='imageFile' />");
        jcf.customForms.replaceAll();
        jcf.customForms.refreshAll();

        //reset form
        inputFile = $form.find('input:file');
        $form.get(0).reset();
        inputFile.each(function(){
            this.jcf.refreshState();
        });
    }
}

// post-submit callback
function showAttachmentResponse(responseText, statusText, xhr, $form)  {
//    if(responseText.indexOf('Failed') != -1){
//        var errorMsg = responseText.substring(responseText.indexOf(':')+1);
//        //alert('status: ' + statusText + '\n\nresponseText: \n' + responseText + '\n\nThe output div should have already been updated with the responseText.');
//        $("#colorbox").css({'height': 640 + 'px'});
//        $("#cboxContent").css({'height': 625 + 'px'});
//        $("#cboxLoadedContent").css({'height': 625 + 'px'});
//        //  $('span#editCatalogReplyDiv').html("<br />Submission Failed: " + errorMsg);
//    }else{

        $('div#lightbox-item-attachment a.close').click();
        catalogItemAttachments.fetch({cache: false});

        //reset form
        inputFile = $form.find('input:file');
        $form.get(0).reset();
        inputFile.each(function(){
            this.jcf.refreshState();
        });
    }

function checkAllCustomFields(check){
    try{
        if(check){
            $("#custom-fields-box :checkbox").each(function(){
                $(this).next().addClass('ui-state-active');
                var checkbox = $(this)[0];
                checkbox.checked=true;
            });
        }else{
            $("#custom-fields-box :checkbox").each(function(){
                $(this).next().removeClass('ui-state-active');
                var checkbox = $(this)[0];
                checkbox.checked=false;
            });
        }
    }catch(exp){
        alert(exp);
    }
}

function getItemCustomFields(catalogId,catalogItemId, pageNumber) {
   if($('#txtReadOnly').val() !=1 ){
		return;
	}
    var list=persistCheckboxStateOnPageChange('add-custom-field-form-id');
	if(pageNumber==undefined) {
		pageNumber = 1;	
	}
    try{
        $.ajax({
            type:'POST',
            url: getItemCustomFieldServiceUrl+"?catalogItemId="+catalogItemId+"&pageNumber="+pageNumber,
            data:'catalogId=' + catalogId,
            success: function(response) {
                if(response != ''){
//                    var customField = eval( "(" + response + ")" );
//                    populateCustomFields(customField);
                    $.colorbox({
                        href:'#add-custom-field',
                        inline:true,
                        onComplete: function(){
                            $("#add-custom-field-form-id").html( response );
                            initTableUI();
//                            $("#cboxLoadedContent").css({'height': 625 + 'px'});
                            $('#cboxContent .close,#cboxContent .btn-cancel').unbind('click').bind('click', function(){
                                $.colorbox.close();
                                return false;
                            });


                            copyCheckedItemList('add-custom-field-form-id',list);
                        }
                    });
                }
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}

function associateItemCustomFields(catalogId, catalogItemId) {
    try{
    	var custFieldIds = 
    		$('#add-custom-field-form-id input[name="customFieldIdList"]:checked').map(function(){
    	      return $(this).val();
    		}).get();

        var gIDs=prepareCSVStringCustomField2('add-custom-field-form-id');

        gIDs='['+gIDs+']';

        $.ajax({
            type:'POST',
            url: associateItemCustomFieldURL+catalogId+'/catalogitems/'+catalogItemId+'/customfields',
            data: gIDs,
            dataType: 'text',
            contentType: 'application/json',
            success: function(response) {
                $.colorbox.close();
                customFieldsPage.fetch({cache: false});
                resetCheckBoxList('add-custom-field-form-id');
//                $("#custom-field-table").html( response );
//                initTableUI();
//                jcf.customForms.replaceAll();
            }
        });
    }catch(exp){
        alert(exp);
    }
    return false;
}
