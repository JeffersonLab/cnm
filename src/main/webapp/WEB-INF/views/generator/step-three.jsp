<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Generator"/>
<t:page title="${title} - Step 3">  
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
                    Step 3 - Choose a Sector:
                </li>
            </ul>  
        </div>        
        <section>
            <ul class="key-value-list">
                <li>
                    <div class="li-key"><label for="grouping">Grouping</label></div>
                    <div class="li-value">            
                        <form method="get" action="step-three">
                            <input type="hidden" name="system" value="${system.getSCode()}"/>
                            <input type="hidden" name="type" value="${type.getVvCode()}"/>
                            <select id="grouping" name="grouping" class="change-submit">
                                <option></option>
                                <c:forEach items="${groupingList}" var="grouping">
                                    <option value="${fn:escapeXml(grouping)}"${param.grouping eq grouping ? ' selected="selected"' : ''}><c:out value="${grouping}"/></option>
                                </c:forEach>                    
                            </select>      
                        </form>
                    </div>
                </li> 
                <li>
                    <div class="li-key"><label for="sector">Sector</label></div>
                    <div class="li-value">
                        <form method="get" action="step-four">
                            <input type="hidden" name="system" value="${system.getSCode()}"/>
                            <input type="hidden" name="type" value="${type.getVvCode()}"/>                
                            <select id="sector" name="sector" class="change-submit">
                                <option></option>
                                <c:forEach items="${sectorList}" var="sector">
                                    <option value="${sector.getXxCode()}"><c:out value="${sector.getXxCode()}"/> - <c:out value="${sector.description}"/></option>
                                </c:forEach>                    
                            </select>
                        </form>
                    </div>
                </li>
            </ul>
            <p>Don't see a good fit?  <a id="propose-link" href="mailto:cdubbe@jlab.org?subject=Nomenclature: New Sector Proposal">Propose a new sector</a></p>
        </section>
    </jsp:body>         
</t:page>
