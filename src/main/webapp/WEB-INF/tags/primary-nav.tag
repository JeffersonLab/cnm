<%@tag description="Primary Navigation Tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@taglib prefix="s" uri="jlab.tags.smoothness"%>
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
    <c:if test="${pageContext.request.isUserInRole('cnm-admin')}">
        <li${fn:startsWith(currentPath, '/setup') ? ' class="current-primary"' : ''}>
            <a href="${pageContext.request.contextPath}/setup/settings">Setup</a>
        </li>
    </c:if>
    <li${'/help' eq currentPath ? ' class="current-primary"' : ''}>
        <a href="${pageContext.request.contextPath}/help">Help</a>
    </li>
</ul>