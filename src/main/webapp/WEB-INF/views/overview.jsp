<%@taglib prefix="sql" uri="jakarta.tags.sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@taglib prefix="s" uri="jlab.tags.smoothness"%>
<c:set var="title" value="Overview"/>
<s:page title="${title}">
    <jsp:attribute name="stylesheets">
        <style type="text/css">
            .overview table {
                border-collapse: collapse;
                border: 1px solid black;
            }
            .overview tr, 
            .overview td, 
            .overview th {
                border: 1px solid black;
                padding: 0.25em;
            } 
        </style>
    </jsp:attribute>
    <jsp:attribute name="scripts"> 
        <script type="text/javascript">
            $(document).on("click", "#excel-menu-item", function () {
                $("#excel-export").submit();
            });
            $(document).on("click", "#html-menu-item", function () {
                $("#html-export").submit();
            });
            $(document).on("click", "#pdf-menu-item", function () {
                $("#pdf-export").submit();
            });
        </script>
    </jsp:attribute>        
    <jsp:body>
        <section>
            <div id="report-page-actions">
                <div id="export-widget">
                    <button id="export-menu-button">Export</button>
                    <ul id="export-menu">
                        <li id="excel-menu-item">Excel</li>
                        <li id="html-menu-item">HTML</li>
                        <li id="pdf-menu-item">PDF</li>                         
                    </ul>
                </div>
            </div>            
            <h2 class="page-header-title"><c:out value="${title}"/></h2>
            <div class="overview">
                <%@include file="/WEB-INF/includes/overview.html" %>
            </div>
        </section>
        <form id="excel-export" action="/cnm/export/cnd.xlsx" method="get"></form>
        <form id="html-export" action="/cnm/export/cnd.html" method="get">
            <input type="hidden" name="attachment" value=""/>
        </form>
        <form id="pdf-export" action="${pageContext.request.contextPath}/export/cnd.pdf" method="get">
        </form>
    </jsp:body>         
</s:page>
