var gCatalogItemsPageNumber = 1;
var paginatedCatalogItems;
var filterItemButtonToggle = false;
var filterCatalogItems;
var catalogItemPaginationClick;
var pageName;

$(document).ready(function () {
    if(typeof pageName === "undefined" || pageName !="catalog_detail") {
        return;
    }

    _.templateSettings.interpolate = /\{\{(.+?)\}\}/g;

    var PaginatedCatalogItems = Backbone.Model.extend({
        urlRoot: "catalogs",
        url: function() {
            var restUrl = this.urlRoot + '/' + this.get('catalogId') + '/paginated-items?pageNo=' + this.get('pageNo') +
                '&searchTerm=' + this.get('searchTerm');
            return restUrl;
        }
    });

    var CatalogItemView = Backbone.View.extend({
        template:null,
        initialize:function () {
            template = _.template($("#catalog-details-item").html());
        },
        render:function () {
        	this.model.price = formatCurrency(this.model.price, this.model.newItemCurrency);
        	this.el = template(this.model);
            this.delegateEvents(this.events);
            return this;

        }
    });

    var CatalogItemListView = Backbone.View.extend({
        el:"#catalog_item_table_rows_body",
        items_el:"#catalog_item_table_rows_body",
        render:function (model) {
            $(this.items_el).empty();

            if(model.has('catalogItems')) {
                this.model = model.get('catalogItems');
                for (var i = 0; i < this.model.length; i++) {
                    var catalogItem = this.model[i];
                    var view = new CatalogItemView({model:catalogItem}).render();
                    $(this.items_el).append(view.el);
                }
            }
            $("#cat-items-form .longfield").on('click',function(){

                var _this=this;
                $(_this).find("span").css('display','none');
                $(_this).find("input").css('display','block');
                $(_this).find("input").attr('readonly','readonly');

                $(_this).find("input").focus();
                $(_this).find("input").select();

                $(_this).find("input").blur(function() {
                    $(_this).find("span").css('display','block');
                    $(_this).find("input").css('display','none');
                    $(_this).find("input").removeAttr('readonly');
                });
            });
            initTableUI();
            fixTextWidth();
        }
    });
    var catalogItemListView = new CatalogItemListView();

    var CatalogItemPaginationView = Backbone.View.extend({
        render:function (catalogItemPageNo, model) {
            if(model.get("totalNumberOfPages") > 1) {
                $("#catalog-pagination-controls").show();
            } else {
                $("#catalog-pagination-controls").hide();
            }

            if(catalogItemPageNo > 1) {
                $("#catalog-item-prev-page").removeClass().addClass("btn-prev-second");
            } else {
                $("#catalog-item-prev-page").removeClass().addClass("btn-prev");
            }


            $("#catalog_items_current_page_num").text('Page ' + catalogItemPageNo + ' of ' + model.get('totalNumberOfPages'));
            //$("#catalog_items_current_page_span").text(catalogItemPageNo + ' of ' + model.get('totalNumberOfPages'));

            if(catalogItemPageNo < model.get('totalNumberOfPages')) {
                $("#catalog-item-next-page").removeClass().addClass("btn-next").addClass("ui-button");
            } else {
                $("#catalog-item-next-page").removeClass().addClass("btn-next-last").addClass("ui-button");
            }

            $("#catalog-item-count-start").text(model.get('recordStart'));
            $("#catalog-item-count-end").text(model.get('recordEnd'));
            $("#catalog-item-count-total").text(model.get('totalNumberOfItems'));
            
			var defaultValue = $('#searchCriteria').prop('defaultValue');
       		var searchTerm = $("#searchCriteria").val();
            
            if(model.get("totalNumberOfItems") == 0 && (defaultValue == searchTerm)) {
            	$("#create-item-section").hide();
            }
        }
    });
    var catalogItemPaginationView = new CatalogItemPaginationView();

    catalogItemPaginationClick = function catalogItemPaginationClick1(catalogId, pageNo) {

        if(pageNo < 1 || pageNo > paginatedCatalogItems.get('totalNumberOfPages')) {
            return;
        }
        filterCatalogItems(catalogId, pageNo);

//


    }

    filterCatalogItems = function filterItems(catalogId, pageNo, callback) {
        var list=persistCheckboxStateOnPageChange('cat-items-form');

        gCatalogItemsPageNumber = pageNo;
        var defaultValue = $('#searchCriteria').prop('defaultValue');
        var searchTerm = $("#searchCriteria").val();

        if(defaultValue == searchTerm) {
            searchTerm = ''
        }

        if(pageNo == null) {
            pageNo = 1;
        }

        // Retrieve some search results
        paginatedCatalogItems = new PaginatedCatalogItems({catalogId: catalogId, pageNo: pageNo, searchTerm: searchTerm});
        _.bindAll(paginatedCatalogItems);

        paginatedCatalogItems.fetch({
            async:true,
            error:function () {
                //console.log(arguments);
            },
            success: function() {
                catalogItemListView.render(paginatedCatalogItems);
                catalogItemPaginationView.render(gCatalogItemsPageNumber, paginatedCatalogItems);

                $(".checkboxReset").on('click',function(){
                    resetCheckBoxList('cat-items-form');

                });

                copyCheckedItemList('cat-items-form',list);

                if(callback) {
                    callback();
                }
            }
        });
    }

    filterCatalogItems(catalogId, 1);
});

function selectAllCfs(){
    $("#customFieldContentDiv input[type='checkbox']").each(function(){
        try{
            if(this.id.indexOf("check3") > -1 || (this.id.indexOf("required_") < 0 && this.id.indexOf("postFilterable_") < 0 && this.id.indexOf("searchable_") < 0)){
                $(this).next().addClass('ui-state-active');
                var checkbox = $(this)[0];
                checkbox.checked=true;
            }

//            $(this).closest('tr').addClass('active-tr');

        }catch(exp){
            alert(exp);
        }
    });
}

function fixTextWidth() {
	var showChar = 15;
    var ellipsestext = "...";
    $('.table-data .td-matg span').each(function() {
        var content = $(this).text();
        if(content && content.length > showChar) {
            var txt = content.substr(0, showChar) + ellipsestext;
            $(this).text(txt);
        }
    });
}


