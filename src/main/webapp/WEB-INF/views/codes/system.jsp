<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://jlab.org/jsp/smoothness"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="System Codes"/>
<t:codes-page title="${title}">  
    <jsp:attribute name="stylesheets">
        <style type="text/css">
            #page-header-title {
                margin-bottom: 16px;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/v${initParam.releaseNumber}/js/system.js"></script>
    </jsp:attribute>        
    <jsp:body>
        <section>                              
            <h2 class="page-header-title"><c:out value="${title}"/></h2>
            <div id="chart-wrap" class="chart-wrap-backdrop">
                <c:set var="readonly" value="${!pageContext.request.isUserInRole('cnm-admin')}"/>
                <s:editable-row-table-controls excludeAdd="${readonly}" excludeDelete="${readonly}" excludeEdit="${readonly}">
                    <button id="inventory-button" type="button" class="selected-row-action" disabled="disabled">CED Inventory</button>
                </s:editable-row-table-controls>
                <table class="data-table stripped-table uniselect-table editable-row-table">
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${systemList}" var="system">
                            <tr data-id="${system.systemCodeId}">
                                <td><c:out value="${system.getSCode()}"/></td>
                                <td><c:out value="${system.description}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
        <s:editable-row-table-dialog>
            <form id="row-form">
                <ul class="key-value-list">
                    <li>
                        <div class="li-key">
                            <label for="row-code">Code</label>
                        </div>
                        <div class="li-value">
                            <input type="text" maxlength="1" pattern="[A-Z]{1}" title="System code is a single uppercase letter" required="required" id="row-code"/>
                        </div>
                    </li>                        
                    <li>
                        <div class="li-key">
                            <label for="row-description">Description</label>
                        </div>
                        <div class="li-value">
                            <input type="text" maxlength="256" title="Explanation of code" required="required" id="row-description"/>
                        </div>
                    </li>                                         
                </ul>  
            </form>
        </s:editable-row-table-dialog>
    </jsp:body>         
</t:codes-page>