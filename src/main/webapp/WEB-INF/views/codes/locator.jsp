<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Locator Codes"/>
<t:codes-page title="${title}">  
    <jsp:attribute name="stylesheets">
    </jsp:attribute>
    <jsp:attribute name="scripts">
    </jsp:attribute>        
    <jsp:body>
        <section>                             
            <h2 id="page-header-title"><c:out value="${title}"/></h2>
            <div class="message-box"></div>
            <div id="chart-wrap" class="chart-wrap-backdrop">
                <table class="data-table stripped-table">
                    <thead>
                        <tr>
                            <th>Sector Code</th>
                            <th>Locator Code</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${locatorList}" var="locator">
                        <tr>
                            <td><c:out value="${locator.sectorCode.getXxCode()}"/></td>
                            <td><c:out value="${locator.getYyCode()}"/></td>
                            <td><c:out value="${locator.description}"/></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
        <div id="exit-fullscreen-panel">
            <button id="exit-fullscreen-button">Exit Full Screen</button>
        </div>
    </jsp:body>         
</t:codes-page>