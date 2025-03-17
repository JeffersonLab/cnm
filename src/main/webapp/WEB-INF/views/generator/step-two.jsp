<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Generator"/>
<t:page title="${title} - Step 2">  
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
                    Step 2 - Choose a Type:
                </li>
            </ul>  
        </div>        
        <section>
            <ul class="key-value-list">
                <li>
                    <div class="li-key"><label for="grouping">Grouping</label></div>
                    <div class="li-value">            
                        <form method="get" action="step-two">
                            <input type="hidden" name="system" value="${system.getSCode()}"/>
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
                    <div class="li-key"><label for="type">Type</label></div>
                    <div class="li-value">
                        <form method="get" action="step-three">
                            <input type="hidden" name="system" value="${system.getSCode()}"/>                
                            <select id="type" name="type" class="change-submit">
                                <option></option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.getVvCode()}"><c:out value="${type.getVvCode()}"/> - <c:out value="${type.description}"/></option>
                                </c:forEach>                    
                            </select>
                        </form>
                    </div>
                </li>
            </ul>            
            <p>Don't see a good fit?  <a id="propose-link" href="mailto:cdubbe@jlab.org?subject=Nomenclature: New Type Proposal">Propose a new type</a></p>
        </section>
    </jsp:body>         
</t:page>
