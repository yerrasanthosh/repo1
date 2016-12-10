function getDataForSpendByCategory(unitId){

	var ctx, data, categoryHtml = '';
	ctx = document.getElementById("chart-area");
	var chartType = 'category';
	if ($.browser.msie  && parseInt($.browser.version, 10) === 8) {
		ctx = G_vmlCanvasManager.initElement(ctx);
	}
	
	var chartType = 'category';
	$.ajax({
			type : 'GET',
			url : spendByCategoryURI + "/" + unitId + "?chartType=" + chartType + "&fromDate=" + '' + "&toDate=" + ' &stamp='+ new Date().getTime(),
			success : function(response) {
				if (response != "null") {
					data = response;
					window.myPie = new Chart(ctx.getContext("2d")).Pie(
							data, {
								tooltipFontSize : 11.5,
								responsive : true,
							});
					$.each(response,function(index,data) {
						categoryHtml += '<li style="font-size: 12px"><div class="'
								+ colourNameToHex(data.color)
								+ '"></div>'
								+ '<div style="display: inline;">'
								+ '<abbr title="'
								+ data.label
										.toString()
								+ '" >'
								+ trimLabel(data.label)
								+ '</abbr>'
								+ '<span style="float: right; font-weight: bold">'
								+ '$'
								+'<abbr title="'
								+ commaSeparateNumber(data.value)
								+ '" >'
								+ trimPrice(commaSeparateNumber(data.value));
								+ '</abbr>'
								+ '</span>'
								+ '</div>'
								+ '</li>';
						});
					$('.catagoyPanel').html(
							categoryHtml);
					$('.monthName')
							.html(
									monthName()
											+ ' Spend per Category');
				} else {
					categoryHtml = '<div style="padding-top:25%;font-size: 20px;padding-bottom:25%;align="middle";">'
							+ 'You have not spent anything yet'
							+ '</div>';
					$('.CategoryChartDiv').html(categoryHtml);
					$('.monthName').html(monthName()+ ' Spend per Category');
				}
			}
			});
}
	
function getDataForSpendBySupplier(unitId){

	var ctxx,categoryHtml='';
	ctxx = document.getElementById("chart-area1");
	var chartType = 'category';
	if ($.browser.msie  && parseInt($.browser.version, 10) === 8) {
		ctxx = G_vmlCanvasManager.initElement(ctxx);
	}
	
	var chartType = 'supplier'; 
	$.ajax({
			type : 'GET',
			url : spendByCategoryURI + "/" + unitId + "?chartType=" + chartType + "&fromDate=" + '' + "&toDate=" + ''+ "&stamp="+ new Date().getTime(),
			success : function(response) {
				if (response != "null") {
					data = response;
					window.myPie = new Chart(ctxx.getContext("2d")).Pie(
							data, {
								tooltipFontSize : 11.5,
								responsive : true,
							});
					$.each(response,function(index,data) {
						categoryHtml += '<li style="font-size: 12px"><div class="'
								+ colourNameToHex(data.color)
								+ '"></div>'
								+ '<div style="display: inline;">'
								+ '<abbr title="'
								+ data.label
										.toString()
								+ '" >'
								+ trimLabel(data.label)
								+ '</abbr>'
								+ '<span style="float: right; font-weight: bold">'
								+ '$'
								+'<abbr title="'
								+ commaSeparateNumber(data.value)
								+ '" >'
								+ trimPrice(commaSeparateNumber(data.value));
								+ '</abbr>'
								+ '</span>'
								+ '</div>'
								+ '</li>';
						});
					$('.catagoyPanel1').html(categoryHtml);
					$('.monthName1').html(monthName()+ ' Spend per Supplier');
				} else {
					categoryHtml = '<div style="padding-top:25%;font-size: 20px;padding-bottom:25%;align="middle";">'
							+ 'You have not spent anything yet'
							+ '</div>';
					$('.CategoryChartDiv1').html(categoryHtml);
					$('.monthName1').html(monthName()+ ' Spend per Supplier');
				}
			}
			});
}

 

function getDataForInternalExternalCatelog(unitId){

	var ctxx,categoryHtml='',intenalExternalChartLabel='';
	ctxx =  $('#chart-area2').get(0);
	if ($.browser.msie  && parseInt($.browser.version, 10) === 8) {
		ctxx = G_vmlCanvasManager.initElement(ctxx);
	}
	var chartType = 'category'; 
	$.ajax({
			type : 'GET',
			url : internalExternalChartURI + "/" + unitId +"?fromDate=" + '' + "&toDate=" + ''+ "&stamp="+ new Date().getTime(),
			success : function(response) {
				if (response.datasets[0].data[0] != null) {
					data = response;
					  var options = {
					    barStrokeWidth : 1,
					    responsive: true,
					    animation: false,
					    barShowStroke: false,
					    tooltipTemplate: "<%if (label){%><%=label%>: <%}%><%= value %>%",
					  };
					 new Chart(ctxx.getContext("2d")).HorizontalBar(data, options);
				} else {
					categoryHtml = '<div style="padding-top:25%;font-size: 20px;padding-bottom:25%;align="middle";">'
							+ 'You have not spent anything yet'
							+ '</div>';
					$('.InternalExternal1').html(categoryHtml);
					$('.monthName1').html(monthName()+ ' Spend per Supplier');
				}
			}
			});
}


function getDataForSpendByMonthly(unitId){

	var ctx, data, categoryHtml = '';
	ctx = document.getElementById("3rdCanvas");
	if ($.browser.msie  && parseInt($.browser.version, 10) === 8) {
		ctx = G_vmlCanvasManager.initElement(ctx);
	}
	$.ajax({
			type : 'GET',
			url : spendByMonthChartURI + "/" + unitId +"?fromDate=" + '' + "&toDate=" +''+ "&stamp="+ new Date().getTime(),
			success : function(response) {
				if (response != "null") {
					data = response;
					window.myLine = new Chart(ctx.getContext('2d')).Line(data, {
						responsive : true
					});
				} else {
					categoryHtml = '<div style="padding-top:25%;font-size: 20px;padding-bottom:25%;align="middle";">'
							+ 'You have not spent anything yet'
							+ '</div>';
					$('.CanvasChart').html(categoryHtml);
					$('.monthName').html(monthName()+ ' Spend per Category');
				}
			}
			});
}



	function commaSeparateNumber(val){
	    while (/(\d+)(\d{3})/.test(val.toString())){
	      val = val.toString().replace(/(\d+)(\d{3})/, '$1'+','+'$2');
	    }
	    return val;
	}


	function monthName() {
		var monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" ];
		var fromMonth = new Date();
		var toMonth = new Date();
		fromMonth = new Date(fromMonth.setMonth(fromMonth.getMonth() - 1));
		return monthNames[fromMonth.getMonth()] + " - "
				+ monthNames[toMonth.getMonth()];
	}

	function trimLabel(label) {
		var finalLabel = '';
		if (label.length > 26) {
			label = label.substring(0, 25);
			finalLabel = label.concat('...');
		} else {
			finalLabel = label;
		}
		return finalLabel;
	}
	
	function trimPrice(label) {
		var finalLabel = '';
		if (label.length > 13) {
			label = label.substring(0, 12);
			finalLabel = label.concat('...');
		} else {
			finalLabel = label;
		}
		return finalLabel;
	}

	function colourNameToHex(color) {
		var colours = {
			"#ffa500" : "orange",
			"#ADD8E6" : "lightBlue",
			"#ff0000" : "red",
			"#000080" : "darkBlue",
			"#00FF00" : "green",
			"#b2912f" : "mahandi",
			"#307d99" : "brightBlue",
			"#aa4644" : "rehroon"
		};
		var colorName = colours[color];
		return colorName;
	}
