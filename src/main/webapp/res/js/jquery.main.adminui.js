var  gTimezoneList =
[
    {"value": "-1100", "label": "Midway Island, Samoa"},
    {"value": "-1000", "label": "Hawaii"},
    {"value": "-0900", "label": "Alaska"},
    {"value": "-0800", "label": "Pacific Time,(US & Canada)"},
    {"value": "-0700", "label": "Mountain Time,(US & Canada)"},
    {"value": "-0600", "label": "Central Time,(US & Canada)"},
    {"value": "-0500", "label": "Eastern Time,(US & Canada)"},
    {"value": "-0400", "label": "Atlantic Time, Canada"},
    {"value": "-0330", "label": "Newfoundland"},
    {"value": "-0300", "label": "Brazil, Georgetown"},
    {"value": "-0200", "label": "Mid-Athlantic"},
    {"value": "-0100", "label": "Azores, Cape Verde"},
    {"value": "+0000", "label": "Western Europe, London"},
    {"value": "+0100", "label": "Brussels, Paris"},
    {"value": "+0200", "label": "Kaliningrad, South Africa"},
    {"value": "+0300", "label": "Moscow, Baghdad"},
    {"value": "+0400", "label": "Abu Dhahi, Baku"},
    {"value": "+0430", "label": "Kabul"},
    {"value": "+0500", "label": "Islamabad, Karachi"},
    {"value": "+0530", "label": "Bombay, New Delhi"},
    {"value": "+0545", "label": "Kathmandu"},
    {"value": "+0600", "label": "Almaty, Dhaka"},
    {"value": "+0700", "label": "BangKok, Jakarta"},
    {"value": "+0800", "label": "Benjing, HongKong"},
    {"value": "+0900", "label": "Tokyo, Seoul"},
    {"value": "+0930", "label": "Adelaide, Darwin"},
    {"value": "+1000", "label": "Easter Australia"},
    {"value": "+1100", "label": "Magaden, Solomon Islands"},
    {"value": "+1200", "label": "Auckland, Fiji"}
];

// init timepicker
function initTimepicker_adminui(){
    $('div#lightbox-inline .timepicker-input').datetimepicker({
        showOn: 'both',
        format: 'yyyy-MM-dd',
        beforeShow: function(){
            $('#ui-datepicker-div').css({visibility:'visible'});
        },
        onClose: function(){
            $('#ui-datepicker-div').css({visibility:'hidden'});
        },
        timezoneList: gTimezoneList
    });
}

function initCreateCatalog_adminui(){
    $('#theCatalogId').each(function(){
        var form = $(this),
            btnReset = form.find('.btn-reset'),
            inputFile = form.find('input:file');
            btnReset.bind('click', function(){
                form.get(0).reset();
                inputFile.each(function(){
                    this.jcf.refreshState();
                });
                return false;
            }
        );
    });

    $('#theQuoteId').each(function(){
        var form = $(this),
            btnReset = form.find('.btn-reset'),
            inputFile = form.find('input:file');
        btnReset.bind('click', function(){
                form.get(0).reset();
                inputFile.each(function(){
                    this.jcf.refreshState();
                });
                return false;
            }
        );
    });
}

function initEditCatalog_adminui(){
    $('#edit-catalog-form').each(function(){
        var form = $(this);
        //btnReset = form.find('.btn-reset'),
        //inputFile = form.find('input:file');
        //btnReset.bind('click', function(){
        // COMMENTED ON PURPOSE
        //form.get(0).reset();
        //inputFile.each(function(){
        //    this.jcf.refreshState();
        //});eturn false;
        //});
        var textInputs = form.find("input[type='text']");
        textInputs.each(function(){
            var eventType = "keyup";
            if($(this).attr('name') == 'startDate' || $(this).attr('name') == 'endDate'){
                eventType = "change";
            }
            $(this).bind(eventType, function(){
                $('#editCatalogSubmitButton').removeAttr("disabled");
            });
        });
        var fileInputs = form.find("input[type='file']");
        fileInputs.each(function(){
            $(this).bind('change', function(){
                $('#editCatalogSubmitButton').removeAttr("disabled");
            });
        });
        var selects = form.find('select');
        selects.each(function(){
            $(this).bind('change', function(){
                $('#editCatalogSubmitButton').removeAttr("disabled");
            });
        });
    });
}

// init timepicker
function initTimepicker_adminui(){
    initTimepicker_adminui();

    initCreateCatalog_adminui();

    initEditCatalog_adminui();
}