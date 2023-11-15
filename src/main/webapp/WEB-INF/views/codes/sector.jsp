<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://jlab.org/jsp/smoothness"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Sector Codes"/>
<t:codes-page title="${title}">  
    <jsp:attribute name="stylesheets">
        <style type="text/css">
            .outer-table thead th:first-child {
                width: 35px;
            }
            .inner-table tbody td:first-child {
                width: 39px;
            }      
            .outer-table thead th:nth-child(3) {
                width: 135px;
            }
            .inner-table tbody td:nth-child(3) {
                width: 135px;
                max-width: 135px;
                word-wrap: break-word;
            }            
            .inner-table tbody td:nth-child(2) {
                width: 500px;
                max-width: 500px;
                word-wrap: break-word;
            } 
        </style>        
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/v${initParam.releaseNumber}/js/sector.js"></script>
    </jsp:attribute>        
    <jsp:body>
        <section>    
            <s:filter-flyout-widget clearButton="true">
                <form id="filter-form" method="get" action="sector">
                    <div id="filter-form-panel">
                        <fieldset>
                            <legend>Filter</legend>
                            <ul class="key-value-list">
                                <li>
                                    <div class="li-key"><label for="sector" class="key-label">Sector</label></div>
                                    <div class="li-value">
                                        <input type="text" name="sector" id="sector" maxlength="2" value="${param.sector}"/>
                                        (use % as a wildcard)
                                    </div>
                                </li>                                
                                <li>
                                    <div class="li-key"><label for="grouping" class="key-label">Grouping</label></div>
                                    <div class="li-value">
                                        <select name="grouping" id="grouping">
                                            <option></option>
                                            <c:forEach items="${groupingList}" var="grouping">
                                                <option value="${fn:escapeXml(grouping)}"${param.grouping eq grouping ? ' selected="selected"' : ''}><c:out value="${grouping}"/></option>
                                            </c:forEach>                    
                                        </select>
                                    </div>
                                </li>                               
                            </ul>
                        </fieldset>	
                    </div>
                    <input id="filter-form-submit-button" type="submit" value="Apply"/>
                </form>
            </s:filter-flyout-widget>
            <h2 id="page-header-title"><c:out value="${title}"/></h2>
            <div class="message-box"><c:out value="${selectionMessage}"/></div>
            <div id="chart-wrap" class="chart-wrap-backdrop">
                <c:set var="readonly" value="${!pageContext.request.isUserInRole('cnm-admin')}"/>
                <s:editable-row-table-controls excludeAdd="${readonly}" excludeDelete="${readonly}" excludeEdit="${readonly}">
                    <button id="inventory-button" class="selected-row-action" type="button" disabled="disabled">CED Inventory</button>
                </s:editable-row-table-controls>
                <table class="data-table outer-table">
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Description</th>
                            <th>Grouping</th>
                            <th class="scrollbar-header"><span class="expand-icon" title="Expand Table"></span></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="inner-table-cell" colspan="4">
                                <div class="pane-decorator">
                                    <div class="table-scroll-pane">
                                        <table class="data-table inner-table stripped-table uniselect-table editable-row-table">
                                            <tbody>
                                                <c:forEach items="${sectorList}" var="sector">
                                                    <tr data-id="${sector.sectorCodeId}">
                                                        <td><c:out value="${sector.getXxCode()}"/></td>
                                                        <td><c:out value="${sector.description}"/></td>
                                                        <td><c:out value="${sector.grouping}"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </td>
                        </tr>
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
                            <input type="text" maxlength="2" pattern="[A-Z0-9]{2}" title="Sector code is a pair of uppercase letters or numbers" required="required" id="row-code"/>
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
                    <li>
                        <div class="li-key">
                            <label for="row-grouping">Grouping</label>
                        </div>
                        <div class="li-value">
                            <input type="text" maxlength="16" title="Grouping Label" id="row-grouping"/>
                        </div>
                    </li>                    
                </ul>  
            </form>
        </s:editable-row-table-dialog>
    </jsp:body>         
</t:codes-page>