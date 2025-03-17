<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Generator"/>
<t:page title="${title} - Step 1">  
    <jsp:attribute name="stylesheets">
    </jsp:attribute>
    <jsp:attribute name="scripts">    
    </jsp:attribute>        
    <jsp:body>
        <section>
            <h2 class="page-header-title">Name <c:out value="${title}"/></h2>
            <h3>Step 1 - Choose a System:</h3>
            <form method="get" action="step-two">
                <select name="system" class="change-submit">
                    <option></option>
                    <c:forEach items="${systemList}" var="system">
                        <option value="${system.getSCode()}"><c:out value="${system.getSCode()}"/> - <c:out value="${system.description}"/></option>
                    </c:forEach>                    
                </select>
            </form>
            <p>Don't see a good fit?  <a id="propose-link" href="mailto:cdubbe@jlab.org?subject=Nomenclature: New System Proposal">Propose a new system</a></p>
        </section>
    </jsp:body>         
</t:page>
