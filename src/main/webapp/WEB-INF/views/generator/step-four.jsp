<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Generator"/>
<t:page title="${title} - Step 4">  
    <jsp:attribute name="stylesheets">
    </jsp:attribute>
    <jsp:attribute name="scripts">        
    </jsp:attribute>        
    <jsp:body>
        <div class="banner-breadbox">
            <ul class="breadcrumb">
                <li>
                    <a href="${pageContext.request.contextPath}/generator/step-one"><c:out value="${system.getSCode()}"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/generator/step-two?system=${fn:escapeXml(system.getSCode())}"><c:out value="${type.getVvCode()}"/></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/generator/step-three?system=${fn:escapeXml(system.getSCode())}&amp;type=${fn:escapeXml(type.getVvCode())}"><c:out value="${sector.getXxCode()}"/></a>
                </li>                
                <li>
                    Step 4 - Choose a Locator:
                </li>
            </ul>  
        </div>        
        <section>
            <form method="get" action="step-five">
                <input type="hidden" name="system" value="${system.getSCode()}"/>
                <input type="hidden" name="type" value="${type.getVvCode()}"/> 
                <input type="hidden" name="sector" value="${sector.getXxCode()}"/>
                <select name="locator" class="change-submit">
                    <option></option>
                    <option value="__">__ - No Locator</option>
                    <option value="##">## - Sequential Numeric Locator</option>
                    <option value="??">?? - Custom Locator</option>
                    <!--
                    <c:forEach items="${locatorList}" var="locator">
                        <option value="${locator.getYyCode()}"><c:out value="${locator.getYyCode()}"/> - <c:out value="${locator.description}"/></option>
                    </c:forEach>  
                    -->
                </select>
            </form>
            <p>Don't see a good fit?  <a id="propose-link" href="mailto:cdubbe@jlab.org?subject=Nomenclature: New Locator Proposal">Propose a new locator</a></p>
        </section>
    </jsp:body>         
</t:page>
