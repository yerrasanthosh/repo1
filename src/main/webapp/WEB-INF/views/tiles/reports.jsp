<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />

<%@ page contentType="text/html;charset=UTF-8" language="java"
	autoFlush="true" session="true"%>
	<script type="text/javascript" src="res/js/excanvas.js"></script>
<%@ page import="com.vroozi.customerui.acl.model.Permission"%>
<%@ page import="com.vroozi.customerui.user.services.user.model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="aclManager" scope="request"
	class="com.vroozi.customerui.acl.DisplayAccessControl" />
<script src="res/js/variables.js"></script>
<script src="res/js/moment.min.js"></script>
<script src="res/js/jquery.daterangepicker.js"></script>

<script src="res/js/Chart.js"></script>
<script src="res/js/Chart.HorizontalBar.js"></script>
<script src="res/js/demo.js"></script>
<script src="res/js/reports.js"></script>
<link media="all" rel="stylesheet" type="text/css"
	href="res/css/typeAhead.css" />
<link rel="stylesheet" href="res/css/daterangepicker.css" />
<!--[if !IE 8]><!-->
	<style>
		#chartjs-tooltip.below {
        -webkit-transform: translate(-50%, 0);
        transform: translate(-50%, 0);
    }
    #chartjs-tooltip.below:before {
        border: solid;
        border-color: #111 transparent;
        border-color: rgba(0, 0, 0, .8) transparent;
        border-width: 0 8px 8px 8px;
        bottom: 1em;
        content: "";
        display: block;
        left: 50%;
        position: absolute;
        z-index: 99;
        -webkit-transform: translate(-50%, -100%);
        transform: translate(-50%, -100%);
    }
    #chartjs-tooltip.above {
        -webkit-transform: translate(-50%, -100%);
        transform: translate(-50%, -100%);
    }
    #chartjs-tooltip.above:before {
        border: solid;
        border-color: #111 transparent;
        border-color: rgba(0, 0, 0, .8) transparent;
        border-width: 8px 8px 0 8px;
        bottom: 1em;
        content: "";
        display: block;
        left: 50%;
        top: 100%;
        position: absolute;
        z-index: 99;
        -webkit-transform: translate(-50%, 0);
        transform: translate(-50%, 0);
    } 
	</style>
 <!--<![endif]-->


<style>
    #chartjs-tooltip {
        opacity: 1;
        position: absolute;
        background: black;
        color: white;
        padding: 3px;
        border-radius: 3px;
        -webkit-transition: all .1s ease;
        transition: all .1s ease;
        pointer-events: none;
        -webkit-transform: translate(-50%, 0);
        transform: translate(-50%, 0);
    }
    </style>
<%
  String unitId = "";
			if (session.getAttribute("user") != null) {
				User loggedInUser = (User) session.getAttribute("user");
				unitId = loggedInUser.getUnitId();
			}
%>
<script type="text/javascript">
	var unitId = "<%=unitId%>";
	var supplierTypeAheadURL = '<c:url value="/suppliers/search" />';
	var shopperTypeAheadURL = '<c:url value="/user/search" />';
	var spendByCategoryURI = '<c:url value="/getspendbycategory" />';
	var internalExternalChartURI = '<c:url value="/getinternalexternalchartdata" />';
	var spendByMonthChartURI = '<c:url value="/getspendbymonthchartdata" />';
	
	function showPannel(value) {
        $("#downloadCatalogs").hide();
		if (value == 0) {
			$("#GenerateReport").prop('disabled', true);
		} else {
			$("#GenerateReport").prop('disabled', false);
		}
		if (value == 1) {
			$("#shopperCompany").show();
			$("#SupplierCompany").hide();
            $("#startDate").show();
            $("#endDate").show();
		} else if (value == 2) {
			$("#SupplierCompany").show();
			$("#shopperCompany").hide();
            $("#startDate").show();
            $("#endDate").show();
		} else {
			$("#SupplierCompany").hide();
			$("#shopperCompany").hide();
            $("#startDate").show();
            $("#endDate").show();
		}

        if (value == 5) {
            $("#downloadCatalogs").show();
            $("#SupplierCompany").hide();
            $("#shopperCompany").hide();
            $("#startDate").hide();
            $("#endDate").hide();
        }
	}
	
	function generateReport() {
		var value = $("#reportOptions").val();
		var reportDateValidation = $("#validation").val();
		if (reportDateValidation) {
			if (value != 0) {
				var sdate = $("#start-datee").val();
				var edate = $("#end-date").val();
				if (value == 2) {
					var reportUrl = "spend-by-supplier.xlsx" + "?fromDate="
							+ sdate + "&toDate=" + edate;
					var sp = $("#supplierName").val();
					if (sp && sp != "All Suppliers") {
						reportUrl = reportUrl + "&supplierId=" + sp;
					}
					window.open(reportUrl);
				} else if (value == 1) {
					var reportUrl = "spend-by-shopper.xlsx" + "?fromDate="
							+ sdate + "&toDate=" + edate;
					var sp = $("#shopperId").val();
					if (sp && sp != "All Shoppers") {
						reportUrl = reportUrl + "&userId=" + sp;
					}
					window.open(reportUrl);
				} else if (value == 3) {
					window.open("FREQUENTLY_ORDERED_ITEMS.xlsx");
				} else if (value == 4) {
					var reportUrl = "spend-by-category.xlsx" + "?fromDate="
							+ sdate + "&toDate=" + edate;
					window.open(reportUrl);
				} else if (value == 5) {
                    var flag = true;
                    try{
                        var catalogExportTask={
                            "singleFile":$('input[name="individualCatalogFiles"]:checked').val()
                        };
                        if(ajaxInTransit) return;
                        ajaxInTransit = true;
                        $.ajax({
                                   type:'POST',
                                   url: downloadAllCatalogsUrl,
                                   data: catalogExportTask,
                                   dataType: 'text',
                                   success: function(data, textStatus, jqXHR) {
                                       if(data != null && data!= ""){
                                           noty({
                                                    text: data,
                                                    type: 'error'
                                                });
                                       }
                                       else {
                                           noty({
                                                    text: 'Your export file is being prepared. You will be notified when it is ready.',
                                                    type: 'success'
                                                });
                                       }
                                       ajaxInTransit = false;
                                   }
                               });
                    }catch(exp){
                        alert(exp);
                    }
                }
            }
		}
		return false;
	}

	$(document).ready(
			function() {
				var date = new Date();
				var lastMonthfirstDate = new Date(date.getFullYear(), date
						.getMonth() - 1, 1);
				$("#start-datee").datepicker().datepicker("setDate",
						lastMonthfirstDate);
				$("#end-date").datepicker().datepicker("setDate", new Date());
				Chart.defaults.global.customTooltips = function(tooltip) {

					// Tooltip Element
					var tooltipEl = $('#chartjs-tooltip');

					// Hide if no tooltip
					if (!tooltip) {
						tooltipEl.css({
							opacity : 0
						});
						return;
					}

					// Set caret Position
					tooltipEl.removeClass('above below');
					tooltipEl.addClass(tooltip.yAlign);

					// Set Text
					tooltipEl.html(tooltip.text);

					// Find Y Location on page
					var top;
					if (tooltip.yAlign == 'above') {
						top = tooltip.y - tooltip.caretHeight
								- tooltip.caretPadding;
					} else {
						top = tooltip.y + tooltip.caretHeight
								+ tooltip.caretPadding;
					}

					// Display, position, and set styles for font
					tooltipEl.css({
						opacity : 1,
						left : tooltip.chart.canvas.offsetLeft + tooltip.x
								+ 'px',
						top : tooltip.chart.canvas.offsetTop + top + 'px',
						fontFamily : tooltip.fontFamily,
						fontSize : tooltip.fontSize,
						fontStyle : tooltip.fontStyle,
					});
				};

				var ctx, data, categoryHtml = '';
				getDataForSpendByCategory(unitId);
				getDataForSpendBySupplier(unitId);
				getDataForInternalExternalCatelog(unitId);
				getDataForSpendByMonthly(unitId);

			});
</script>

<div id="main">

	<div class="section">
		<ul class="breadcrumbs">
			<li><a href="vroozi">Vroozi</a></li>
			<li>Reports</li>
		</ul>
		
		<div class="video-btn-holder">
			<!--img src="res/images/img-1.gif" alt="" width="26" height="27" />
            <a href="#" class="btn"><span><em>Help video</em></span></a-->
		</div>
	</div>
	<div class="main-holder">
		<div id="content">
			<h1>Reports</h1>
			<div class="content-block toggle-block active" id="dashBoard-section" style="overflow:visible">
				<div class="headline">
					<h2>
						<a href="#" class="open-close dashboard">Dashboard</a>
					</h2>
				</div>

				<div class="block dashboardPannel">
					<div class="content" style="overflow: visible">

						<div
							style="display: inline-block;float:left; width: 375px; height:427px; padding: 0; border: 1px solid #e6e6e6; background-color: #fff; text-align: center; font-size: 16px;">
							<header>
							<div class="monthName" style="border-bottom: 1px solid #f7f7f7; padding: 15px 0 15px 0; font-family: font-header; font-size: 24px; color: #777;"> 
								</div>
							</header>

							<div class="CategoryChartDiv"
								style="display: inline-block; margin: 5px 0px 5px 0px; padding-top: 25px; padding-bottom: 25px">
								<div id="scanvas-holder" style="width=320; height=320;" >
									<canvas id="chart-area"  />
								</div>
								<div class="listView" style="display: table-cell; float: right;">
									<ul style="width: 299px; margin-top: 30px;" class="catagoyPanel"></ul>

								</div>
							</div>
						</div>
						<div
							style="display: inline-block;float:right; width: 375px; height:427px;  margin-left: 7px; padding: 0; border: 1px solid #e6e6e6; background-color: #fff; text-align: center; font-size: 16px;">
							<header>
							<div class="monthName1" style="border-bottom: 1px solid #f7f7f7; padding: 15px 0 15px 0; font-family: font-header; font-size: 24px; color: #777;"> 
								</div>								
							</header>
							<div class="CategoryChartDiv1"
								style="display: inline-block; margin: 5px 0px 5px 0px; padding-top: 25px; padding-bottom: 25px">
								<div id="canvas-holder" style="width=320; height=320;">
									<canvas id="chart-area1" />
								</div>
								<div class="listView" style="display: table-cell; float: right;">
									<ul style="width: 299px; margin-top: 30px;" class="catagoyPanel1"></ul>
								</div>
							</div>
						</div>
						
						<div
							style="display: inline-block; width: 761px; margin-top: 9px; padding: 0; border: 1px solid #e6e6e6; background-color: #fff; text-align: center; font-size: 16px;">
							<header>
								<div style="border-bottom: 1px solid #f7f7f7; padding: 15px 0 15px 0; font-family: font-header; font-size: 24px; color: #777;">Monthly
								Spend 
								</div>	
								</header>

							<div style="padding: 15px">
								<div  class="CanvasChart" >
									<canvas id="3rdCanvas" height="240" width="750"></canvas>
								</div>
							</div>
		
						</div>
						
						<div
							style="margin-top:10px;   border: 1px solid #e6e6e6; background-color: #fff; text-align: center; font-size: 16px; width: 761px;">
							<header>
								<div style="border-bottom: 1px solid #f7f7f7; padding: 15px 0 15px 0; font-family: font-header; font-size: 24px; color: #777;">
								Catalog Compliance
								</div>
								</header>
							
							<div class="InternalExternal1"
								style="display: inline-block; margin: 5px 0px 5px 0px; padding: 15px; ">
								<div id="canvas-holder" style="display: inline-block;">
									<canvas id="chart-area2" width="725" height="240"></canvas>
								</div>
							</div>
							<div style="text-align:center; margin-top: -6px; margin-top:30px; margin-bottom:30px; font-size:20px; font-family: font-header; color: #777;">Percentage Breakdown</div>
						</div>
						<div id="chartjs-tooltip"></div>
					</div>
				</div>
			</div>


			<div class="content-block toggle-block active" id="report_pannel">
				<div class="headline">
					<h2>
						<a href="#" class="open-close report">Reports</a>
					</h2>
				</div>

				<div class="block reportPannel">
					<form action="" class="settings-form" method="post"
						onsubmit="return generateReport();">
						<div class="block">
							<div class="content" style="height: 350px">

								<div class="text-fields">


									<div class="row">
										<label for="s-username" id="usernameLabel"
											class="username-label"><span class="required">*</span>Select
											Report:</label>
										<div class="area-col">
											<select id="reportOptions" class="settings-form alt"
												style="width: 300px" onchange="showPannel(this.value)">

												<option selected="selected" value="0">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Select
													Report</option>
												<option value="1">Total spend per shopper</option>
												<option value="2">Total spend per supplier</option>
												<option value="4">Total spend per category</option>
												<!-- <option value="3">Most frequently ordered items</option> -->
                                                <c:if test="${downloadAllCatalogs}">
                                                    <option value="5">Download all catalogs</option>
                                                </c:if>
											</select>
										</div>
									</div>

									<div class="row" id="SupplierCompany"
										style="overflow: visible; display: none;">
										<label>Supplier Company:</label> <input type="hidden"
											id="supplierName" name="supplierName" /> <input type="text"
											id="supplierNameDisplay" class="typehead"
											placeholder="All Suppliers" style="width: 287px;"
											onclick="clearExistingSupplier();" />
									</div>
									<div class="row" id="shopperCompany"
										style="overflow: visible; display: none;">
										<label>Shopper User:</label> <input type="hidden"
											id="shopperId" name="shopperId" /> <input type="text"
											id="shopperNameDisplay" style="width: 287px;"
											placeholder="All Shoppers"
											onclick="clearExistingShopper();" />
									</div>

                                    <div class="row" id="downloadCatalogs"
                                         style="overflow: visible; display: none;">
                                        <label>Options :</label>
                                        <span style="float: left;padding: 4px 0px 0 0;">Individual Catalog Files :</span>
                                        <input style="float: left;" type="radio" name="individualCatalogFiles" value="false" checked>
                                        <span style="float: left;padding: 4px 0px 0 20px;">Single Catalog File :</span>
                                        <input style="float: left;" type="radio"  name="individualCatalogFiles" value="true">
                                    </div>

									<div class="row" id="startDate">
									<label for="start-date">Start Date:</label> 
									<input type="text" style="width: 287px"
										class="datepicker-input" id="start-datee" name="startDate"
										value="" readonly="readonly" />
								</div>
								<div class="row" id="endDate">
									<label for="end-date">End Date:</label> 
									<input type="text" style="width: 287px"
										class="datepicker-input" id="end-date" name="endDatee"
										value="" readonly="readonly" /> <label for="end-date"></label>
									<label style="margin: 0 100px -13px;" id="end-datee-label"
										hidden="true" class="error error-msg" generated="true"
										for="end-date-message">Invalid End Date</label>
								</div>
									<div class="row" style="display: none">
										<label for="date-range0">Date Range:</label> <input
											class="inputDateRange" id="date-range0" value="">
									</div>

						</div>
								<input type="hidden" id="validation" value="true">
								<button class="btnnn" id="generateRep" onclick="dateValidation();" >Generate Report</button>
							</div>

						</div>




					</form>
				</div>
			</div>
		</div>
		<div id="sidebar">
			<ul class="sub-nav">
				<li><a href="<c:url value="/vroozi" />" class="ico-back"><span>BACK</span></a></li>
				<li onclick="moveToDashBoard()"><a href="#">
						<div style="display: inline; float: left;">
							<img src="res/images/dashboard.png" width="16px">
						</div> <span style="display: inline; float: left; padding-left: 9px">DASHBOARD</span>
				</a></li>
				<li onclick="moveToReport()"><a href="#">

						<div style="display: inline; float: left;">
							<img src="res/images/report.png" width="16px">
						</div> <span style="display: inline; float: left; padding-left: 9px">REPORT</span>
				</a>
				</li>
			</ul>
			<ul class="sub-nav">
				<!--li><a href='#' class="ico-company"><span>SUPPLIER COMPANY LIST</span></a></li-->
			</ul>
		</div>
	</div>

</div>



<script>
	initializeSupplierTypeAheadSearch();
	initializeShopperTypeAhead();
	
	 $("#start-datee").change(function () {
	        var startDate = new Date($('#start-datee').val());
	        validateDate(startDate,1);
	  });
	 
	 $("#end-date").change(function () {
	        var endDate = new Date($('#end-date').val());
	        validateDate(endDate,2);
	  });
	 
	function validateDate(date,dateType){
		var todayDate = new Date();
		var startDateNoty = 'Start date cannot be a future date';
		var endDateNoty = 'End date cannot be a future date';
		var msg = startDateNoty;
		if(dateType == 2) {
			msg = endDateNoty; 
			if(date < new Date($('#start-datee').val())) {
				noty({text: 'End Date cannot be less then Start Date', type: 'warning'});
				enableDisableButton(false);
				return;
			}
		} else {
			if(date > new Date($('#end-date').val())){
				noty({text: msg, type: 'warning'});
				enableDisableButton(false);
				return;
			}
		}
		if (date > todayDate) {
			noty({text: msg, type: 'warning'});
			enableDisableButton(false);
		} else {
			enableDisableButton(true);
		};
	}
	
	function dateValidation(){
		$('#validation').val('true');
		if(new Date($('#start-datee').val()) >= new Date($('#end-date').val())) {
			noty({text: 'Start date cannot be greater than End Date', type: 'warning'});
			enableDisableButton(false);
		} else {
			enableDisableButton(true);
		}
	}
	function enableDisableButton(validationFlag){
		if(validationFlag){
			$('#validation').val('true');
			$('#generateRep').attr("disabled", false);
			$('#generateRep').css('background-color','#88abca');
		} else {
			$('#validation').val('false');
			$('#generateRep').attr("disabled", true);
			$('#generateRep').css('background-color','#c0c0c0');
		}
		
	}
	
	function moveToDashBoard() {
		$('html,body').animate({
			scrollTop : $(".dashboard").offset().top
		}, 'slow');
		if ($("#dashBoard-section").hasClass("active")) {
		} else {
			$(".open-close.dashboard").click();
		}
	}
	function moveToReport() {
		$('html,body').animate({
			scrollTop : $(".report").offset().top
		}, 'slow');
		if ($("#report_pannel").hasClass("active")) {
		} else {
			$(".open-close.report").click();
		}
	}
	function relaodChart(value) {
		window.myPie.destroy();
		if (value == 1) {
			$("#summaryData").html(purchaseData);
			ctx = document.getElementById("chart-area").getContext("2d");
			window.myPie = new Chart(ctx).Pie(purchases, {
				tooltipFontSize : 9,
				percentageInnerCutout : 50
			});
		} else if (value == 2) {
			$("#summaryData").html(venderData);
			ctx = document.getElementById("chart-area").getContext("2d");
			window.myPie = new Chart(ctx).Pie(vender, {
				tooltipFontSize : 9,
				percentageInnerCutout : 50
			});
		}
	}
	var purchases = [ {
		value : 5101,
		color : "#F7464A",
		highlight : "#FF5A5E",
		label : "Total"
	}, {
		value : 8545,
		color : "#46BFBD",
		highlight : "#5AD3D1",
		label : "Total"
	}, {
		value : 19644,
		color : "#FDB45C",
		highlight : "#FFC870",
		label : "Total"
	}, {
		value : 22952,
		color : "#4D5360",
		highlight : "#616774",
		label : "Total"
	}, {
		value : 34916,
		color : "#949FB1",
		highlight : "#A8B3C5",
		label : "Total"
	}
	];
	var vender = [ {
		value : 7316,
		color : "#60bd68",
		highlight : "#4dcc58",
		label : "Total"
	}, {
		value : 10943,
		color : "#307d99",
		highlight : "#338aaa",
		label : "Total"
	}, {
		value : 15764,
		color : "#ba942e",
		highlight : "#b79435",
		label : "Total"
	}, {
		value : 5443,
		color : "#aa4644",
		highlight : "#b24e4c",
		label : "Total"
	}, {
		value : 35174,
		color : "#f17cb0",
		highlight : "#f97fb4",
		label : "Total"
	} ];
	var randomScalingFactor = function() {
		return Math.round(Math.random() * 100)
	};
	var lineChartData = {
		labels : [ "Mar-14", "Apr-14", "May-14", "Jun-14", "Jul-14", "Aug-14",
				"Sep-14", "Oct-14", "Nov-14", "Dec-14", "Sep-14", "Jan-15",
				"Feb-15", "Mar-15" ],
		datasets : [ {
			label : "My First dataset",
			fillColor : "rgba(220,220,220,0.2)",
			strokeColor : "rgba(220,220,220,1)",
			pointColor : "rgba(220,220,220,1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(220,220,220,1)",
			data : [ 2450, 1150, 950, 1075, 1200, 1800, 2100, 2800, 4500, 5874,
					3000, 1800, 2250 ]
		} ]
	}
	var purchaseData = '<ul><li><div class="yellow"></div><div style="display: inline;">'
			+ '	Computer Hardware<span style="float: right;">$600</span>'
			+ '</div></li>'
			+ '<li><div class="lightBlue"></div>'
			+ '	<div style="display: inline;">'
			+ '		Telecom. Equipment<span'
				+ '		style="float: right; margin-left: 20px">$1000</span>'
			+ '</div></li>'
			+ '<li><div class="red"></div>'
			+ '<div style="display: inline;">'
			+ '	Apparel<span style="float: right;">$600</span>'
			+ '</div></li>'
			+ '<li>'
			+ '<div class="darkBlue"></div>'
			+ '<div style="display: inline;">'
			+ '	Peripherals Office Supplies <span style="float: right;">$1500</span>'
			+ '</div>' + '</li>' + '</ul>';
	var venderData = '<ul><li><div class="yellow"></div><div style="display: inline;">W.W. Grainger, Inc.<span style="float: right;">$2000</span></div></li>'
			+ '<li><div class="lightBlue"></div><div style="display: inline;">MSC Industrial Supply<span'
		+'		style="float: right; margin-left: 20px">$1000</span></div></li>'
			+ '	<li><div class="red"></div><div style="display: inline;">Dell Computer<span style="float: right;">$600</span></div></li>'
			+ '	<li><div class="darkBlue"></div><div style="display: inline;">HD Supply<span style="float: right;">$800</span></div></li>'
			+ '</ul>';
</script>
