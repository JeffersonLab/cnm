<%@tag description="The Codes Page Template" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://jlab.org/jsp/smoothness"%>
<%@attribute name="title"%>
<%@attribute name="stylesheets" fragment="true"%>
<%@attribute name="scripts" fragment="true"%>
<s:page title="${title}" category="Codes">
    <jsp:attribute name="stylesheets">       
        <jsp:invoke fragment="stylesheets"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <jsp:invoke fragment="scripts"/>
    </jsp:attribute>
    <jsp:attribute name="secondaryNavigation">
        <ul>
            <li${'/codes/system' eq currentPath ? ' class="current-secondary"' : ''}><a href="${pageContext.request.contextPath}/codes/system">System</a></li>
            <li${'/codes/type' eq currentPath ? ' class="current-secondary"' : ''}><a href="${pageContext.request.contextPath}/codes/type">Type</a></li>
            <li${'/codes/sector' eq currentPath ? ' class="current-secondary"' : ''}><a href="${pageContext.request.contextPath}/codes/sector">Sector</a></li>
        </ul>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>         
</s:page>
