<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://jlab.org/jsp/smoothness"%>
<c:set var="title" value="Generator"/>
<s:page title="${title} - Step 5">
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
                    <a href="${pageContext.request.contextPath}/generator/step-four?system=${fn:escapeXml(system.getSCode())}&amp;type=${fn:escapeXml(type.getVvCode())}&amp;sector=${fn:escapeXml(sector.getXxCode())}"><c:out value="${param.locator}"/></a>
                </li>                 
                <li>
                    Step 5 (Optional) - Create a new Element Identifier:
                </li>
            </ul>  
        </div>        
        <section>

            <p>The following names are already taken:</p>
            <ul></ul>
            <a target="_blank" href="${env['CED_SERVER_URL']}/inventory?q=${fn:escapeXml(system.getSCode())}${fn:escapeXml(type.getVvCode())}${fn:escapeXml(sector.getXxCode())}*">CED Inventory</a>
            
            
            <p>Want to reserve your new element name?   <a href="mailto:cedadm@jlab.org?subject=Nomenclature">Contact the CED administrator</a></p>
        </section>
    </jsp:body>         
</s:page>
