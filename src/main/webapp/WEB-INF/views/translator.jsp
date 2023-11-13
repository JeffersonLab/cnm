<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%> 
<c:set var="title" value="Translator"/>
<t:page title="${title}">  
    <jsp:attribute name="stylesheets">
        <style type="text/css">
            #translation-table {
                width: 100%;
            }
            #translation-table td:first-child {
                width: 200px;
                box-sizing: border-box;
            }
            #name-input {
                width: 100%;
                box-sizing: border-box;
            }
            .button-indicator {
                float: left;
            }
            #ced-link {
                float: right;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="scripts">    
        <script type="text/javascript">
            jlab.translate = function() {
                var query = $("#name-input").val();

                $("#meaning").text("").addClass("button-indicator");

                var request = jQuery.ajax({
                    url: "/cnm/translate",
                    type: "GET",
                    data: {
                        q: query
                    },
                    dataType: "json"
                });

                request.done(function(json) {
                    if (json.meaning !== null && json.meaning !== '' && json.meaning !== 'null') {
                        $("#meaning").removeClass("button-indicator").text(json.meaning);
                    } else {
                        $("#meaning").removeClass("button-indicator").text("Name not recognized");
                    }
                });

                request.error(function(xhr, textStatus) {
                    window.console && console.log('Unable to translate: Text Status: ' + textStatus + ', Ready State: ' + xhr.readyState + ', HTTP Status Code: ' + xhr.status);
                });

                request.always(function() {
                    $("#meaning").removeClass("button-indicator");
                });
            };

            $(document).on("keyup change", "#name-input", function() {
                jlab.translate();
            });
            $(document).on("click", "#ced-link", function() {
                var query = $("#name-input").val();
                window.open('https://ced.acc.jlab.org/inventory?q=' + encodeURIComponent(query) + '*');
                return false;
            });
        </script>
    </jsp:attribute>        
    <jsp:body>
        <section>
            <h2 id="page-header-title"><c:out value="${title}"/></h2>
            <table id="translation-table" class="data-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Meaning</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input id="name-input" type="text" placeholder="<Enter Name Here>"/></td>
                        <td><div id="meaning"></div></td>
                    </tr>
                </tbody>
            </table>
            <a id="ced-link" href="#" target="_blank">Search CED</a>
        </section>
    </jsp:body>         
</t:page>
