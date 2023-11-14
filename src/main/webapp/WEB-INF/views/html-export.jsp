<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="CEBAF Nomenclature Document (CND)"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${title}"/></title>
        <style>
            table {
                border-collapse: collapse;
                border: 1px solid black;
            }
            tr, td, th {
                border: 1px solid black;
                padding: 0.25em;
            }
            body {
                margin: 1em;
            }
        </style>
    </head>
    <body>       
        <h1><c:out value="${title}"/></h1>
        <section>  
            <h2>System Codes</h2>
            <table>
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
            <h2>Type Codes</h2>            
            <table>
                <thead>
                    <tr>
                        <th>System Code</th>
                        <th>Type Code</th>
                        <th>Description</th>
                        <th>Grouping</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${typeList}" var="type">
                        <tr data-id="${type.typeCodeId}">
                            <td><c:out value="${type.systemCode.getSCode()}"/></td>
                            <td><c:out value="${type.getVvCode()}"/></td>
                            <td><c:out value="${type.description}"/></td>
                            <td><c:out value="${type.grouping}"/></td>
                        </tr>
                    </c:forEach>                        
                </tbody>
            </table>
            <h2>Sector Codes</h2>
            <table>
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Description</th>
                        <th>Grouping</th>
                    </tr>
                </thead>
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
        </section>  
    </body>
</html>