var updateCards;
$(document).ready(function () {
    _.templateSettings.interpolate = /\{\{(.+?)\}\}/g;

    var Announcement = Backbone.Model.extend({
        parse:function (resp, xhr) {
            return {
                "id":resp.id,
                "type":resp.type,
                "userId":resp.userid,
                "message":resp.announcements,
                "date":resp.formatedDate,
                "dateLabel":getDateLabel(resp.updated),
                "messagePreview":scrubHtml(resp.announcements)
            }
        }

    });

    function scrubHtml(val){
        if(val!=null && val.indexOf("export file") < 0 && val.indexOf("change report") < 0){
            if($(val).text().length > 100){
                return $(val).text().substr(0,100)+"...";
            } else {
                return $(val).text();
            }
        } else {
            if(val==null) return "";
            else return val.substr(0,val.indexOf("Click")-2)+" for download.";
        }
    }

    function getDateLabel(val){
        if(!val){
            return "Date";
        } else {
            return "Updated Date";
        }
    }

    var AnnouncementCollection = Backbone.Collection.extend({
        model: Announcement,
        url: 'announcements'
    });

    var AnnouncementView = Backbone.View.extend({
        render:function () {
            var type = this.model.get("type");
            var template;
            if (type == "success") {
                template = _.template($("#success-announcement").html());
            } else if (type == "important") {
                template = _.template($("#important-announcement").html());
            } else if (type == "attention") {
                template = _.template($("#attention-announcement").html());
            } else {
                template = _.template($("#normal-announcement").html());
            }

            this.el = template(this.model.toJSON());
//            this.delegateEvents(this.events);
            return this;
        }
    });

    var AnnouncementCardView = Backbone.View.extend({
        el:"#announce-card",
        announcements_el:"#announcements-area",
        render:function () {
            if (this.model.length > 0) {
                $(this.el).show();
                $(this.announcements_el).empty();
                $(".vscrollbar").hide();
                $("#announce-card").width(972);
                if(this.model.length == 1){
                    $("#announce-card").height(60);
                    $("#announcements-area").height(60);
                    $("#announcements-area").width(972);
                    $(".scrollable-area-wrapper").height(60);
                } else if(this.model.length == 2){
                    $("#announce-card").height(90);
                    $("#announcements-area").height(90);
                    $("#announcements-area").width(972);
                    $(".scrollable-area-wrapper").height(90);
                } else if(this.model.length == 3){
                    $("#announce-card").height(120);
                    $("#announcements-area").height(120);
                    $("#announcements-area").width(972);
                    $(".scrollable-area-wrapper").height(120);
                } else if(this.model.length == 4){
                    $("#announce-card").height(150);
                    $("#announcements-area").height(150);
                    $("#announcements-area").width(972);
                    $(".scrollable-area-wrapper").height(150);
                } else if(this.model.length == 5){
                    $("#announce-card").height(180);
                    $("#announcements-area").height(180);
                    $("#announcements-area").width(972);
                    $(".scrollable-area-wrapper").height(180);
                } else if(this.model.length == 6){
                    $("#announce-card").height(210);
                    $("#announcements-area").height(210);
                    $("#announcements-area").width(972);
                    $(".scrollable-area-wrapper").height(210);
                } else if(this.model.length == 7){
                    $("#announcements-area").width(972);
                    $("#announce-card").height(240);
                    $("#announcements-area").height(240);
                    $(".scrollable-area-wrapper").height(240);
                } else if(this.model.length > 7){
                    $(".vscrollbar").hide();
                    $("#announce-card").width(973);
                    $("#announce-card").height(245);
                    $("#announcements-area").height(216);
                    $("#announcements-area").width(973);
                    $("#announcements-area").css("padding", "0");
                    $("#announcements-area").css("overflow-y", "auto");
                    $(".scrollable-area-wrapper").height(216);
                    $(".scrollable-area-wrapper").width(973);
                }

                for (var i=0;i<this.model.length;i++) {
                    var announcement = this.model.at(i);
                    //alert(announcement.attributes.message);
                    
                    announcement.attributes.message = announcement.attributes.message.replace("${announcement.id}",announcement.id);
                    
                    var view = new AnnouncementView({model:announcement}).render();
                    $(this.announcements_el).append(view.el);
//                    $(".vscrollbar").show();
                }
            } else {
                $(this.el).hide();
            }
        }
    });

    var SummaryCardModel = Backbone.Model.extend({
        urlRoot: "organization",
        url: function() {
            return this.urlRoot + '/' + this.id + '/summary';
        }
    });

    var SummaryCardView = Backbone.View.extend({
        render:function () {
            $("#totalCatalogsCount").text(this.model.get("totalCatalogsCount"));
            $("#approvalWaitCatalogsCount").text(this.model.get("approvalWaitCatalogsCount"));
            $("#rejectedCatalogsCount").text(this.model.get("rejectedCatalogsCount"));
            $("#publishWaitCatalogsCount").text(this.model.get("publishWaitCatalogsCount"));
            $("#errorCatalogsCount").text(this.model.get("errorCatalogsCount"));

            $("#activeSuppliersCount").text(this.model.get("activeSuppliersCount"));
            $("#inactiveSuppliersCount").text(this.model.get("inactiveSuppliersCount"));

            $("#adminUsersCount").text(this.model.get("adminUsersCount"));
            $("#shopperUsersCount").text(this.model.get("shopperUsersCount"));
            $("#approverUsersCount").text(this.model.get("approverUsersCount"));
            $("#buyerUsersCount").text(this.model.get("buyerUsersCount"));
            
            if(this.model.get("masterAdmin") ==  1){
             	$("#divUserInfo").show();
            }else if(this.model.get("masterAdmin") ==  0){
             	$("#divUserInfo").hide();
            
            }
            
            
         // $("#webSearchUsersCount").text(this.model.get("webSearchUsersCount"));
        }
    });

    var announcementCollection = new AnnouncementCollection();
    var announcementCardView = new AnnouncementCardView({model:announcementCollection});
    announcementCardView.render();

    var summaryCard = new SummaryCardModel({id: unitId});
    var summaryCardView = new SummaryCardView({model: summaryCard});
    summaryCardView.render();

    updateCards = function() {
        announcementCollection.fetch({
            cache:false,
            async: true,
            error:function () {
                //console.log(arguments);
            },
            success: function() {
                announcementCardView.render();
            }
        });

        summaryCard.fetch({
            cache:false,
            async: true,
            error:function () {
                //console.log(arguments);
            },
            success: function() {
                summaryCardView.render();
            }
        });
    }

    updateCards();
    var updateCardsPid = window.setInterval(updateCards, 15000);

});

