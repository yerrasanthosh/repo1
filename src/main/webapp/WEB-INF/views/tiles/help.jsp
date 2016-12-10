<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="wrapper">
<div id="main">
    <div class="section">
        <ul class="breadcrumbs">
            <li><a href="vroozi"><spring:message code="com.adminui.help.Vroozi" text="default text" /></a></li>
            <li><spring:message code="com.adminui.help.Help" text="default text" /></li>
        </ul>
        <%--<div class="video-btn-holder">--%>
            <%--<img src="images/img-1.gif" alt="" width="26" height="27" />--%>
            <%--<a href="#" class="btn"><span><em>Help video</em></span></a>--%>
        <%--</div>--%>
    </div>
    <div class="main-holder">
        <div id="content">
            <h1><spring:message code="com.adminui.help.Help" text="default text" /></h1>
            <div class="ui-tab-section">
                <ul class="tabset">
                    <li class="ui-tabs-selected" style="border: 0px;"><a href="#tab1"><span style="font-weight: normal; color: #284257;"><spring:message code="com.adminui.help.FAQs" text="default text" /></span></a></li>
                    <%--<li><a href="#tab2"><span class="font-weight: normal;">VIDEOS</span></a></li>--%>
                    <%--<li><a href="#tab3"><span class="font-weight: normal;">DOWNLOADS</span></a></li>--%>
                    <li style="border: 0px;"><a href="#tab4"><span style="font-weight: normal; color: #284257;"><spring:message code="com.adminui.help.POLICIES" text="default text" /></span></a></li>
                    <li style="border: 0px;"><a href="#tab5"><span style="font-weight: normal; color: #284257;"><spring:message code="com.adminui.help.CONTACTUS" text="default text" /></span></a></li>

					<c:if test='${not empty companySettings.helpTabs}'>
						<c:forEach var="helpTab" items="${companySettings.helpTabs}">
							<li style="border: 0px;"><a href="#cust-tab-${helpTab.id}"><span style="font-weight: normal; color: #284257;">${helpTab.title}</span></a></li>
						</c:forEach>
					</c:if>                    
                </ul>
                <div id="tab1">
                    <div class="content-block toggle-block active" id="asked-questions">
                        <div class="headline">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.help.FREQUENTLYASKEDQUESTIONS" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <div class="content">
                                <div class="content-box">
                                    ${companySettings.faq}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--<div id="tab2">--%>
                    <%--<div class="content-block toggle-block active" id="videos">--%>
                        <%--<div class="headline">--%>
                            <%--<h2><a href="#" class="open-close">VIDEOS</a></h2>--%>
                        <%--</div>--%>
                        <%--<div class="block">--%>
                            <%--<div class="content">--%>
                                <%--<div class="content-box">--%>
                                    <%--<h3><span>Please view the Help Video</span></h3>--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.</p>--%>
                                    <%--<div class="player">--%>
                                        <%--<img alt="image description" src="images/img-video01.jpg" width="640" height="430" />--%>
                                    <%--</div>--%>
                                    <%--<h3><span>More Help Videos:</span></h3>--%>
                                    <%--<div class="wall">--%>
                                        <%--<div class="row">--%>
                                            <%--<div class="item">--%>
                                                <%--<a href="#"><img alt="image description" src="images/img-video02.jpg" width="185" height="104" /></a>--%>
                                                <%--<span class="name"><a href="#">Enterprise Catalog Search Made Easy</a></span>--%>
                                            <%--</div>--%>
                                            <%--<div class="item">--%>
                                                <%--<a href="#"><img alt="image description" src="images/img-video03.jpg" width="185" height="104" /></a>--%>
                                                <%--<span class="name"><a href="#">Search Multiple Supplier<br/>Catalogs Simultaneously</a></span>--%>
                                            <%--</div>--%>
                                            <%--<div class="item">--%>
                                                <%--<a href="#"><img alt="image description" src="images/img-video04.jpg" width="185" height="104" /></a>--%>
                                                <%--<span class="name"><a href="#">Supplier Catalog Managment</a></span>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div id="tab3">--%>
                    <%--<div class="content-block toggle-block active" id="downloads">--%>
                        <%--<div class="headline">--%>
                            <%--<h2><a href="#" class="open-close">Downloads</a></h2>--%>
                        <%--</div>--%>
                        <%--<div class="block">--%>
                            <%--<div class="content">--%>
                                <%--<div class="content-box">--%>
                                    <%--<h3>Lorem ipsum dolor sit amet, consetetur sadipscing elitr</h3>--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore:<br/><a class="link-download" href="#">Lo sea takimata sanctus est Lorem ipsum dolor sit amet.</a></p>--%>
                                    <%--<h3>Stet clita kasd gubergren, no sea takimata sanctus es</h3>--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam lorem ipusm used diam voluptua:<br/><a class="link-download" href="#">At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren.</a></p>--%>
                                    <%--<h3>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat</h3>--%>
                                    <%--<p>Vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui<br/><a class="link-download" href="#">Blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.</a></p>--%>
                                    <%--<h3>Ut wisi enim ad minim veniam</h3>--%>
                                    <%--<p>Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat:<br/><a class="link-download" href="#">Vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim</a></p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div id="tab4">
                    <div class="content-block toggle-block active" id="policies">
                        <div class="headline">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.help.POLICIES" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <div class="content">
                                <div class="content-box">
                                    ${companySettings.policy}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="tab5">
                    <div class="content-block toggle-block active" id="contact-us">
                        <div class="headline">
                            <h2><a href="#" class="open-close"><spring:message code="com.adminui.help.Contactus" text="default text" /></a></h2>
                        </div>
                        <div class="block">
                            <div class="content">
                                <div class="content-box">
                                    ${companySettings.contactUs}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <c:if test='${not empty companySettings.helpTabs}'>
					<c:forEach var="helpTab" items="${companySettings.helpTabs}">
						<div id="cust-tab-${helpTab.id}">
	                    	<div class="content-block toggle-block active" id="help-${helpTab.id}">
	                        	<div class="headline">
	                            	<h2><a href="#" class="open-close">${helpTab.title}</a></h2>
	                        	</div>
	                        	<div class="block">
	                            	<div class="content">
	                                	<div class="content-box">
	                                    	${helpTab.content}
	                                	</div>
	                            	</div>
	                        	</div>
	                    	</div>
	                	</div>
					</c:forEach>
				</c:if>
            </div>
        </div>
        <div id="sidebar">
            <ul class="sub-nav">
                <%--<li><a href="index.html" class="ico-back"><span>BACK</span></a></li>--%>
                <li><a href="vroozi" class="ico-back"><span><spring:message code="com.adminui.help.BACK" text="default text" /></span></a></li>
            </ul>
        </div>
    </div>
</div>
</div>