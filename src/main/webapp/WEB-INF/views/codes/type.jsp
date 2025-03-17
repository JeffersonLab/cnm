<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://jlab.org/jsp/smoothness"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Type Codes"/>
<t:codes-page title="${title}">  
    <jsp:attribute name="stylesheets">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/v${initParam.releaseNumber}/css/type.css"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/v${initParam.releaseNumber}/js/type.js"></script>
    </jsp:attribute>        
    <jsp:body>
        <section>      
            <s:filter-flyout-widget clearButton="true">
                <form class="filter-form" method="get" action="type">
                    <div id="filter-form-panel">
                        <fieldset>
                            <legend>Filter</legend>
                            <ul class="key-value-list">
                                <li>
                                    <div class="li-key"><label for="system" class="key-label">System</label></div>
                                    <div class="li-value">
                                        <select name="system" id="system">
                                            <option></option>
                                            <c:forEach items="${systemList}" var="system">
                                                <option value="${system.getSCode()}"${param.system eq system.getSCode().toString() ? ' selected="selected"' : ''}><c:out value="${system.getSCode()}"/> - <c:out value="${system.description}"/></option>
                                            </c:forEach>                    
                                        </select>
                                    </div>
                                </li>
                                <li>
                                    <div class="li-key"><label for="type" class="key-label">Type</label></div>
                                    <div class="li-value">
                                        <input type="text" name="type" id="type" maxlength="2" value="${fn:escapeXml(param.type)}"/>
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
                    <input class="filter-form-submit-button" type="submit" value="Apply"/>
                </form>
            </s:filter-flyout-widget>
            <h2 id="page-header-title"><c:out value="${title}"/></h2>
            <div class="message-box"><c:out value="${selectionMessage}"/></div>
            <div id="chart-wrap" class="chart-wrap-backdrop">
                <c:set var="readonly" value="${!pageContext.request.isUserInRole('cnm-admin')}"/>
                <s:editable-row-table-controls excludeAdd="${readonly}" excludeDelete="${readonly}" excludeEdit="${readonly}">
                    <button id="inventory-button" type="button" class="selected-row-action" disabled="disabled">CED Inventory</button>
                    <button id="attributes-button" type="button" class="selected-row-action" disabled="disabled">Attributes</button>
                    <c:if test="${not readonly}">
                        <button id="editable-attributes-button" type="button" class="selected-row-action" disabled="disabled">Edit Attributes</button>    
                    </c:if>
                </s:editable-row-table-controls>
                <table class="data-table outer-table">
                    <thead>
                        <tr>
                            <th>System Code</th>
                            <th>Type Code</th>
                            <th>Description</th>
                            <th>Grouping</th>
                            <th class="scrollbar-header"><span class="expand-icon" title="Expand Table"></span></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="inner-table-cell" colspan="5">
                                <div class="pane-decorator">
                                    <div class="table-scroll-pane">
                                        <table class="data-table inner-table stripped-table uniselect-table editable-row-table">
                                            <tbody>                        
                                                <c:forEach items="${typeList}" var="type">
                                                    <tr data-id="${type.typeCodeId}" data-attributes="${fn:escapeXml(type.jsonAttributes)}">
                                                        <td><c:out value="${type.systemCode.getSCode()}"/></td>
                                                        <td><c:out value="${type.getVvCode()}"/></td>
                                                        <td><c:out value="${type.description}"/></td>
                                                        <td><c:out value="${type.grouping}"/></td>
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
                            <label for="row-scode">System Code</label>
                        </div>
                        <div class="li-value">
                            <input type="text" maxlength="1" pattern="[A-Z]{1}" title="System code is a single uppercase letter" required="required" id="row-scode"/>
                        </div>
                    </li>   
                    <li>
                        <div class="li-key">
                            <label for="row-vvcode">Type Code</label>
                        </div>
                        <div class="li-value">
                            <input type="text" maxlength="2" pattern="[A-Z0-9]{2}" title="Type code is a pair of uppercase letters or numbers" required="required" id="row-vvcode"/>
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
        <div id="attributes-dialog" class="dialog" title="Attributes">
            <div id="attributes-panel"></div>
        </div>
        <div id="editable-attributes-dialog" class="dialog" title="Attributes">
            <div id="edit-attributes-wrapper">
                <div id="editable-attributes-panel"></div>
                <div id="attribute-add-button-panel">
                    <button type="button" id="add-attribute-button">Add</button>
                </div>
            </div>
            <div class="dialog-button-panel">     
                <button type="button" id="edit-save-button" class="dialog-submit-button">Save</button>
                <button type="button" id="edit-cancel-button" class="dialog-close-button">Cancel</button>                
            </div>
        </div>                
    </jsp:body>         
</t:codes-page>