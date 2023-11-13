<%@tag description="The Site Page Template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="http://jlab.org/jsp/smoothness"%>
<%@attribute name="title"%>
<%@attribute name="category"%>
<%@attribute name="stylesheets" fragment="true"%>
<%@attribute name="scripts" fragment="true"%>
<%@attribute name="secondaryNavigation" fragment="true"%>
<s:tabbed-page title="${title}" category="${category}" keycloakClientIdKey="KEYCLOAK_CLIENT_ID_CNM" resourceLocation="CDN">
    <jsp:attribute name="stylesheets">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/v${initParam.resourceVersionNumber}/css/cnm.css"/>
        <jsp:invoke fragment="stylesheets"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/v${initParam.resourceVersionNumber}/js/cnm.js"></script>
        <jsp:invoke fragment="scripts"/>
    </jsp:attribute>
    <jsp:attribute name="primaryNavigation">
                    <ul>
                        <li${'/overview' eq currentPath ? ' class="current-primary"' : ''}>
                            <a href="${pageContext.request.contextPath}/overview">Overview</a>
                        </li>
                        <li${fn:startsWith(currentPath, '/codes') ? ' class="current-primary"' : ''}>
                            <a href="${pageContext.request.contextPath}/codes/system">Codes</a>
                        </li>
                        <li${fn:startsWith(currentPath, '/translator') ? ' class="current-primary"' : ''}>
                            <a href="${pageContext.request.contextPath}/translator">Translator</a>
                        </li>
                        <li${fn:startsWith(currentPath, '/generator') ? ' class="current-primary"' : ''}>
                            <a href="${pageContext.request.contextPath}/generator/step-one">Generator</a>
                        </li>
                        <li${'/help' eq currentPath ? ' class="current-primary"' : ''}>
                            <a href="${pageContext.request.contextPath}/help">Help</a>
                        </li>
                    </ul>
    </jsp:attribute>
    <jsp:attribute name="secondaryNavigation">
        <jsp:invoke fragment="secondaryNavigation"/>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</s:tabbed-page>