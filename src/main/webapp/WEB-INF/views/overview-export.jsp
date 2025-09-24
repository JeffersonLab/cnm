<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="CEBAF Nomenclature Document (CND)"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${title}"/></title>
        <style type="text/css">
            table {
                border-collapse: collapse;
                border: 1px solid black;
            }
            tr, td, th {
                border: 1px solid black;
                padding: 0.25em;
            }
        </style>
    </head>
    <body>       
        <h1><c:out value="${title}"/></h1>
        <section>  
            <%@include file="/WEB-INF/includes/overview.html" %>            
        </section>  
    </body>
</html>